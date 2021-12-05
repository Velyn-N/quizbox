var username = localStorage.getItem("username");
var buzzerSocket;
var role = "player";

$(document).ready(function() {
    if (username == undefined) window.location = "/index.html";

    buzzerSocket = new WebSocket("ws://" + location.host + "/buzzer/" + role + "/"+ username);

    buzzerSocket.onopen = function () {
        console.log("Connected");
    };

    buzzerSocket.onmessage = function (m) {
        if (m.data.startsWith("[Buzzer]")) {
            buzzerReceived(m);
        }
    };
    
    $("#subtitle").html($("#subtitle").html().replace("{username}", username));
});

function buzzerReceived(message) {
    let msgContent = message.data.replace("[Buzzer]","");
    alert(msgContent);
}

$("#buzzer").click(function() {
    buzzerSocket.send("buzzer");
});
