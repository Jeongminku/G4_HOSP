let socket = new WebSocket("ws://localhost/ws/chat");
let messageTextArea = $('#messageTextArea').val();
let messageData = {
        roomId: $('input[name="roomId"]').val(),
        sender: $('input[name="senderName"]').val(),
        type: 'TALK',
        message: '',
};

socket.onopen = () => {
    console.log('Socket [ ' + socket.url + ' ] CONNECTED');
    messageData.type = 'ENTER';
    socket.send(JSON.stringify(messageData))
}

socket.onmessage = message => {
    appendMsg(message);
    scrollDown();
}

$(document).on('click', 'button[name="chatRoomBtn"]', function () {
    $('#rommId').val($(this).attr('id'));
})

$('#messageSend').on('click', function () {
    $('#messageView').scrollTop($('#messageView').prop('scrollHeight'));
})
$('input[name="message"]').on('keyup', function (e) {
    if (e.keyCode == 13) sendMsgToServer();
})

function sendMsgToServer() {
    messageData.type = 'TALK'
    messageData.message = $('input[name="message"]').val();
    if (messageData.message == '') return false;
    socket.send(JSON.stringify(messageData));
    $('input[name="message"]').val('')
}

function appendMsg (message) {
    const getMessage = JSON.parse(message.data)
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
    }
}
