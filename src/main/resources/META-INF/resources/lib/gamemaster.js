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

    // Overrides pointSocket.onmessage in pointDisplayScript.js
    pointSocket.onmessage = function (m) {
        renderPoints(m.data);
        registerPointButtons();
    };
});

function buzzerReceived(message) {
    alert(message); // Function called to Display received Message
}

function registerPointButtons() {
    $(".minusButton").click(function() {
        decrementPoints(this);
    })

    $(".plusButton").click(function() {
        incrementPoints(this);
    })
}

function decrementPoints(button) {
    let currPoints = $(button).parent().find(".pointField").text();
    let newPoints = +currPoints - 1;
    
    changePoints(button, newPoints);
}

function incrementPoints(button) {
    let currPoints = $(button).parent().find(".pointField").text();
    let newPoints = +currPoints + 1;

    changePoints(button, newPoints);
}

function changePoints(button, points) {
    let name = $(button).parent().parent().find(".nameField").text();

    if (points >= 0) {
        $.ajax({
            type: "POST",
            url: "/rest/points/" + name + "/" + points
        });
    }
}