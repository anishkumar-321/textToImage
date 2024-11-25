const socket = new SockJS('/websocket');
const stompClient = Stomp.over(socket);

stompClient.connect({}, function () {
    console.log('Connected to WebSocket');
    document.getElementById('status').innerText = "Connected to WebSocket";
});

function sendMessage() {
    const messageInput = document.getElementById('messageInput');
    const message = messageInput.value;

    if (message.trim() === "") {
        alert("Message cannot be empty!");
        return;
    }

    stompClient.send('/app/sendMessage', {}, message);
    console.log(`Sent message to WebSocket: ${message}`);
    document.getElementById('status').innerText = "Message sent!";
    messageInput.value = "";
}
