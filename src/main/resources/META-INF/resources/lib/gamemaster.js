var username = localStorage.getItem("username");
var role = "gamemaster";
var buzzerSocket;

$(document).ready(function() {
    if (username == undefined) window.location = "/index.html";
    $("#subtitle").html($("#subtitle").html().replace("{username}", username));

    buzzerSocket = new WebSocket("ws://" + location.host + "/buzzer/" + role + "/"+ username);

    buzzerSocket.onopen = function () {
        console.log("Connected");
    };

    buzzerSocket.onmessage = function (m) {
        if (m.data.startsWith("[Buzzer]")) {
            buzzerReceived(m);
        }
    };
});

function buzzerReceived(message) {
    let msgContent = message.data.replace("[Buzzer]","");
    alert(msgContent);
}