let socket = new WebSocket("ws://localhost/ws/chat");
let messageTextArea = $('#messageTextArea').val();
let messageData = {
        roomId: $('input[name="roomId"]').val(),
        sender: $('input[name="senderName"]').val(),
        type: 'TALK',
        message: '',
};

socket.onopen = () => {
    console.log('connect to : ' + socket.url);
    console.log(socket)
    messageData.type = 'ENTER';
    socket.send(JSON.stringify(messageData))
}

socket.onmessage = message => {
    let getMessage = JSON.parse(message.data)
    console.log(message)
    console.log(getMessage)
    messageTextArea += getMessage.message + "\n";
    $('#messageTextArea').val(messageTextArea);
}

socket.onclose = () => {
    console.log('connect closed')
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
    console.log(messageData)
    socket.send(JSON.stringify(messageData));
})

// $(window).on('unload', function () {
//     socket.close();
// })

// function checkSocketStatus() {
//     const connection = new WebSocket("ws://localhost/ws/chat");
//     if (connection.readyState === connection.OPEN) {
//         return connection;
//     } else {
//         checkSocketStatus();
//     }
// }