var username = localStorage.getItem("username");
var webSocket;

$(document).ready(function() {
    if (username == undefined) window.location = "/index.html";

    webSocket = new WebSocket("ws://" + location.host + "/quiz/viewer/"+ username);

    webSocket.onopen = function () {
        console.log("Connected");
    };

    webSocket.onmessage = function (m) {
        console.log(m);
    };

    $(".buzzer").click(function() {
        webSocket.send("buzzer");
    });

    $("#subtitle").html("Watching as <b>" + username + "</b>.");
});