var username = localStorage.getItem("username");
var webSocket;

$(document).ready(function() {
    if (username == undefined) window.location = "/index.html";

    webSocket = new WebSocket("ws://" + location.host + "/quiz/player/"+ username);

    webSocket.onopen = function () {
        console.log("Connected");
    };

    webSocket.onmessage = function (m) {
        if (m.data.startsWith("[Buzzer]")) {
            buzzerReceived(m);
        }
    };
    
    $("#subtitle").html("Playing as <b>" + username + "</b>.");
});

function buzzerReceived(message) {
    let msgContent = message.data.replace("[Buzzer]","");
    alert(msgContent);
}

$("#buzzer").click(function() {
    webSocket.send("buzzer");
});
