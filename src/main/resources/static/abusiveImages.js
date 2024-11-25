document.addEventListener('DOMContentLoaded', () => {
    const socket = new SockJS('/websocket');
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, (frame) => {
        console.log('Connected:', frame);

        stompClient.subscribe('/topic/abusiveImages', (message) => {
            console.log('Image received:', message.body);


            const imageContainer = document.getElementById('image-container');
            const img = document.createElement('img');
            img.src = `data:image/jpeg;base64,${message.body}`;
            img.alt = "Real-Time Image";
            img.style.maxWidth = "100%";
            imageContainer.appendChild(img);
        });
    }, (error) => {
        console.error('WebSocket connection error:', error);
    });
});
