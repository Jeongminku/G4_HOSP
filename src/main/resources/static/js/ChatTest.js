const socket = new WebSocket("ws://localhost/ws/chat");
let messageTextArea = $('#messageTextArea').val();
socket.onopen = function () {
    console.log('connect to : ' + socket.url);   
}
socket.onmessage = function (message) {
    let getMessage = JSON.parse(message.data)
    console.log(message)
    console.log(getMessage)
    messageTextArea += getMessage.message + "\n";
    $('#messageTextArea').val(messageTextArea);
}
$(document).on('click', 'button[name="chatRoomBtn"]', function () {
    $('#rommId').val($(this).attr('id'));
})

$('#send').on('submit', function (e) {
    e.preventDefault();
    let roomId = $('#rommId').val();
    let sendUser = $('input[name="sender"]').val();
    let type = $('input[name="type"]').val();
    let message = $('input[name="message"]').val();
    
    let messageData = JSON.stringify({
        roomId: roomId,
        sender: sendUser,
        type: type,
        message: message,
    });
    socket.send(messageData);
})