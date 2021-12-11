var username = localStorage.getItem("username");
var role = localStorage.getItem("role");
var buzzerSocket;

$(document).ready(function() {
    if (username == undefined) window.location = "/index.html";
    $("#subtitle").html($("#subtitle").html().replace("{username}", username));

    buzzerSocket = new WebSocket("ws://" + location.host + "/buzzer/" + role + "/"+ username);

    buzzerSocket.onopen = function () {
        console.log("BuzzerSocket connected");
    };

    buzzerSocket.onmessage = function (m) {
        buzzerReceived(m.data);
    };

    $("#buzzer").click(function() {
        buzzerSocket.send("buzzer");
    });
});

function buzzerReceived(message) {
    alert(message);
}
