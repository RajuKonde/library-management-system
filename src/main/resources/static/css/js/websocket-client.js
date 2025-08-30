'use strict';

var stompClient = null;
var currentUser = null;

// This is the default function that will be called when a private message is received.
// On the dashboard, it will show an alert. On the chat page, we will override it.
function onPrivateMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    alert("New private message from " + message.sender);
}

function connect(username) {
    currentUser = username;
    if (currentUser) {
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
}

function onConnected() {
    // Subscribe to the user's private channel.
    // All private messages and notifications will come here.
    stompClient.subscribe('/user/' + currentUser + '/queue/private', onPrivateMessageReceived);
}

function onError(error) {
    console.error('Could not connect to WebSocket server. Please refresh this page to try again!');
}