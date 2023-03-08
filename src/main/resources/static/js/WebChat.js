let chatSocket = new WebSocket("ws://localhost/ws/chat");
let messageData = {
    roomId: '',
    sender: '',
    type: 'TALK',
    message: '',
};

chatSocket.onopen = () => {
    console.log('chatSocket [ ' + chatSocket.url + ' ] CONNECTED');
}

chatSocket.onmessage = message => {
    const getMessage = JSON.parse(message.data)
    appendMsg(getMessage, true);
    scrollToBottom();
    saveChatHistoryToSession(getMessage);
}

chatSocket.onclose = () => {
    console.log('chatSocket [ ' + chatSocket.url + ' ] CLOSED');
}

// 이벤트 핸들
$(document).on('click', '#joined', () => {
    $('#joinedMemList').toggleClass('d-none');
})

$(document).on('click', '#exitRoom', () => {
    console.log('chatSocket [ ' + chatSocket.url + ' ] CLOSED');
})

$(document).on('click', '#clearHist', () => {
    // localStorage.clear();
    const currnetUserSession = JSON.parse(sessionStorage.getItem(messageData.sender));
    currnetUserSession[messageData.roomId] = [];
    sessionStorage.setItem(messageData.sender, JSON.stringify(currnetUserSession));
    $('#messageView').empty();
})

$(document).on('click', 'button[name="chatRoomBtn"]', function () {
    $('#rommId').val($(this).attr('id'));
})

$(document).on('click', '#messageSend', function () {
    sendMsgToServer();
    scrollToBottom();
})

$(document).on('keyup', 'input[name="sendMessage"]', function (e) {
    if (e.keyCode == 13) {
        sendMsgToServer();
        scrollToBottom();
    }
})


$('input[name="roomId"]').on('change', () => {
    const checkedRadio = $('input[name="roomId"]:checked');
    const accessId = checkedRadio.parents('div').children('input[name="roomAccessId"]').val();
    let currentLocation = location.pathname;
    let targetUrl = '/chat/room';
    if (currentLocation == '/admin/chat') targetUrl = '/admin/room';
    console.log(location.pathname)
    // console.log(chatSocket.readyState)
    // if (chatSocket.readyState != 1) chatSocket = new WebSocket("ws://localhost/ws/chat");
    
    $.ajax({
        url: targetUrl,
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
            
            if ($('#chatRoom > div').length == 3) {
                messageData.sender = $('input[name="sendName"]').val();
                messageData.roomId = checkedRadio.val();
                messageData.type = 'ENTER';
                chatSocket.send(JSON.stringify(messageData))
                getSavedChatHistory(messageData.sender, messageData.roomId);
            } else {
                $('#chatRoom > div').text('접근이 허용되지 않은 방입니다.');
            }
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
    messageData.message = $('input[name="sendMessage"]').val();
    if (messageData.message == '') return false;
        
    chatSocket.send(JSON.stringify(messageData));
    $('input[name="sendMessage"]').val('')
}

function appendMsg (getMessage, isMessage) {
    const messageOut = $('#messageView');
    const currentUserName = $('input[name="sendName"]').val();
    const sendName = getMessage.sender;
    const $div = $('<div></div>'); 
    
    if (getMessage.type == 'TALK') {
        const time = new Date();
        const am_pm = time.getHours() < 12 ? '오전 ' : '오후 ';
        const hour = time.getHours() < 13 ? time.getHours() : time.getHours() - 12;
        const minute = time.getMinutes() < 10 ? '0' + time.getMinutes() : time.getMinutes();
        const msgTime = $('<p>' + am_pm + hour + ':' + minute + '</p>').addClass('info');
        const msgText = $div.clone().text(getMessage.message);
        const msgTextTime = $div.clone().addClass('d-flex align-items-end');
        
        if (currentUserName != sendName) {
            const msgBox = $div.clone().addClass('mb-2');
            const msgUserName = $('<p>' + sendName + '</p>').addClass('sendName');
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
    let chatHistory = [];
    const roomId = msgData.roomId;
    const currentUser = $('input[name="sendName"]').val();
    let storageByUser = JSON.parse(sessionStorage.getItem(currentUser)) == null ? {} : JSON.parse(sessionStorage.getItem(currentUser));
    
    if (roomId == '') return false;
    if (storageByUser != null) {
        if (storageByUser[roomId] != null && storageByUser[roomId].length != 0) {
            chatHistory = storageByUser[roomId];
        }
    }
    if (chatHistory.length > 100) {
        chatHistory.slice(0, 1);
    }
    chatHistory.push(msgData)
    storageByUser[roomId] = chatHistory; 
    // localStorage.setItem(msgData.roomId, JSON.stringify(chatHistory));
    sessionStorage.setItem(currentUser, JSON.stringify(storageByUser));
}

function getSavedChatHistory (sender, roomId) {
    // let chatMsg = JSON.parse(localStorage.getItem(roomId));
    const allHistoryByUser = JSON.parse(sessionStorage.getItem(sender));
    if (allHistoryByUser == null) return false;
    const currentRoomHistory = allHistoryByUser[roomId];
    if (currentRoomHistory != null) {
        currentRoomHistory.forEach(msg => {
            appendMsg(msg, false)
        });
    }
}

