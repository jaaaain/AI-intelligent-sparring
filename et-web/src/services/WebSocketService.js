// import SockJS from "sockjs-client";
// import Stomp from "stompjs";


// let stompClient = null;

// export function connect(onMessageReceived, onConnected) {
//   const socket = new SockJS("/ws");
//   stompClient = Stomp.over(socket);
//   stompClient.connect({}, onConnected, onError);

  // function onError(error) {
  //   console.log("WebSocket connection error: " + error);
  // }
// }



// export function sendMessage(message) {
//   websocket.send(message);
  // if (stompClient) {
  //   stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(message));
  // }
// }

// export function subscribePublic(onMessageReceived) {
//   if (stompClient) {
//     stompClient.subscribe("/topic/public", function (message) {
//       onMessageReceived(JSON.parse(message.body));
//     });
//   }
// }
