var username = localStorage.getItem("username");
var role = localStorage.getItem("role");
var webSocket;

$(document).ready(function() {
    if (username == undefined) window.location = "/index.html";
    $("#subtitle").html($("#subtitle").html().replace("{username}", username));

    webSocket = new WebSocket("ws://" + location.host + "/buzzer/" + role + "/"+ username);

    webSocket.onopen = function () {
        console.log("Connected");
    };

    webSocket.onmessage = function (m) {
        buzzerReceived(m);
    };

    $("#buzzer").click(function() {
        webSocket.send("buzzer");
    });
});

function buzzerReceived(message) {
    alert(msgContent);
}
