var username = localStorage.getItem("username");
var role = "viewer";
var buzzerSocket;

$(document).ready(function() {
    if (username == undefined) window.location = "/index.html";

    buzzerSocket = new WebSocket("ws://" + location.host + "/buzzer/" + role + "/"+ username);

    buzzerSocket.onopen = function () {
        console.log("Connected");
    };

    buzzerSocket.onmessage = function (m) {
        console.log(m);
    };

    $(".buzzer").click(function() {
        buzzerSocket.send("buzzer");
    });

    $("#subtitle").html($("#subtitle").html().replace("{username}", username));
});