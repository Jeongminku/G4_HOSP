let socket = new WebSocket("ws://localhost/ws/chat");
let messageTextArea = $('#messageTextArea').val();
let messageData = {
        roomId: $('input[name="roomId"]').val(),
        sender: $('input[name="senderName"]').val(),
        type: 'TALK',
        message: '',
};

socket.onopen = () => {
    messageData.type = 'ENTER';
    socket.send(JSON.stringify(messageData))
}

socket.onmessage = message => {
    let getMessage = JSON.parse(message.data)
    messageTextArea += getMessage.message + "\n";
    $('#messageTextArea').val(messageTextArea);
}

socket.onclose = () => {
    socket = new WebSocket("ws://localhost/ws/chat");
    messageData.type = 'LEAVE';
    socket.send(JSON.stringify(messageData));
}

$(document).on('click', 'button[name="chatRoomBtn"]', function () {
    $('#rommId').val($(this).attr('id'));
})

$('#send').on('submit', function (e) {
    e.preventDefault();
    messageData.type = 'TALK'
    messageData.message = $('input[name="message"]').val();
    socket.send(JSON.stringify(messageData));
})