const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");
let chatHistory = [];
let socket = null;
let messageTextArea = $('#messageTextArea').val();
let messageData = {
        roomId: '',
        sender: '',
        type: 'TALK',
        message: '',
};

console.log(messageData.sender)

// 이벤트 핸들
$(document).on('click', '#joined', () => {
    $('#joinedMemList').toggleClass('d-none');
})

$(document).on('click', '#exitRoom', () => {
    socket.close();
    location.href = '/chat';
})

$(document).on('click', '#clearHist', () => {
    // localStorage.clear();
    sessionStorage.clear();
    chatHistory = [];
    $('#messageView').empty();
})

$(document).on('click', 'button[name="chatRoomBtn"]', function () {
    $('#rommId').val($(this).attr('id'));
})

$(document).on('click', '#messageSend', function () {
    sendMsgToServer();
    scrollToBottom();
})

$(document).on('keyup', 'input[name="message"]', function (e) {
    if (e.keyCode == 13) {
        sendMsgToServer();
        scrollToBottom();
    }
})

$()

$('#enterRoom').on('click', () => {
    const checkedRadio = $('input[name="roomId"]:checked');
    const accessId = checkedRadio.parents('div').children('input[name="roomAccessId"]').val();
   
    $.ajax({
        url: '/chat/room',
        type: 'POST',
        dataType: 'text',
        data: {
            roomAccessId: accessId,
            roomId: checkedRadio.val(),
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        cache: false,
        success: function (frag) {
            $('#chatRoom').replaceWith(frag);
            socket = new WebSocket("ws://localhost/ws/chat");
            
            socket.onopen = () => {
                console.log('Socket [ ' + socket.url + ' ] CONNECTED');
                messageData.type = 'ENTER';
                socket.send(JSON.stringify(messageData))
                getSavedChatHistory(messageData.roomId);
            }

            socket.onmessage = message => {
                const getMessage = JSON.parse(message.data)
                appendMsg(getMessage, true);
                scrollToBottom();
                saveChatHistoryToSession(getMessage);
            }

            messageData.sender = $('input[name="senderName"]').val();
            messageData.roomId = checkedRadio.val();
        },
        error: function(jqXHR, status, error) {
            alert('error')
            console.log(jqXHR)
        },
    })
})

// 기능
function scrollToBottom() {
    $('#messageView').scrollTop($('#messageView').prop('scrollHeight'));
}

function sendMsgToServer() {
    messageData.type = 'TALK'
    messageData.message = $('input[name="message"]').val();
    if (messageData.message == '') return false;
    socket.send(JSON.stringify(messageData));
    $('input[name="message"]').val('')
}

function appendMsg (getMessage, isMessage) {
    const messageOut = $('#messageView');
    const currentUserName = $('input[name="senderName"]').val();
    const senderName = getMessage.sender;
    const $div = $('<div></div>'); 
    
    if (getMessage.type == 'TALK') {
        const time = new Date();
        const am_pm = time.getHours() < 12 ? '오전 ' : '오후 ';
        const hour = time.getHours() < 13 ? time.getHours() : time.getHours() - 12;
        const minute = time.getMinutes() < 10 ? '0' + time.getMinutes() : time.getMinutes();
        const msgTime = $('<p>' + am_pm + hour + ':' + minute + '</p>').addClass('info');
        const msgText = $div.clone().text(getMessage.message);
        const msgTextTime = $div.clone().addClass('d-flex align-items-end');
        
        if (currentUserName != senderName) {
            const msgBox = $div.clone().addClass('mb-2');
            const msgUserName = $('<p>' + senderName + '</p>').addClass('senderName');
            msgText.addClass('msg reciveMsg rounded');
            msgBox.append(msgUserName);
            msgTextTime.append(msgText);
            msgTextTime.append(msgTime);
            msgBox.append(msgTextTime);
            messageOut.append(msgBox);
        } else {
            msgText.addClass('msg sendMsg rounded');
            msgTextTime.addClass('justify-content-end mb-2')
            msgTextTime.append(msgTime);
            msgTextTime.append(msgText);
            messageOut.append(msgTextTime);
        }
    } else {
        $div.text(getMessage.message);
        $div.addClass('entry text-center');
        messageOut.append($div)
        if (isMessage) {
            const joinedMemUl = $('#joinedMemList').empty();
            getMessage.joinedMember.forEach(member => {
                let memLi = $('<li></li>').text(member);
                joinedMemUl.append(memLi)
            })
        }
    }
}

function saveChatHistoryToSession(msgData) {
    // const prevList = JSON.parse(localStorage.getItem(msgData.roomId));
    const prevList = JSON.parse(sessionStorage.getItem(msgData.roomId));
    if (prevList != null && prevList.length != 0) {
        chatHistory = prevList;
    }
    if (chatHistory.length > 100) {
        chatHistory.slice(0, 1);
    }
    chatHistory.push(msgData);
    // localStorage.setItem(msgData.roomId, JSON.stringify(chatHistory));
    sessionStorage.setItem(msgData.roomId, JSON.stringify(chatHistory));
}

function getSavedChatHistory (roomId) {
    // let chatMsg = JSON.parse(localStorage.getItem(roomId));
    let chatMsg = JSON.parse(sessionStorage.getItem(roomId));
    if (chatMsg != null) {
        chatMsg.forEach(msg => {
            appendMsg(msg, false)
        });
    }
}

