var username = localStorage.getItem("username");
var role = localStorage.getItem("role");
var pointSocket;

$(document).ready(function() {
    pointSocket = new WebSocket("ws://" + location.host + "/points/" + role + "/"+ username);

    pointSocket.onopen = function () {
        console.log("PointSocket connected");
    };

    pointSocket.onmessage = function (m) {
        renderPoints(m.data);
    };
});

function renderPoints(message) {
    let points = JSON.parse(message);
    $("#pointTarget").html("");

    points.forEach(element => {
        let template = $("#pointTemplate").clone().attr("id", "pointDisplay-" + username);
        
        template.find(".nameField").html(element.name);
        template.find(".pointField").html(element.points);
    
        template.appendTo("#pointTarget");
    });
}
