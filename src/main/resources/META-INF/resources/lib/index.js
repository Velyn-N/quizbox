function participate(link) {
    let val = document.querySelector("#nameInput").value;
    if (val.length > 0) {
        $.ajax({
            type: "GET",
            url: "/rest/quiz/usernameexists/" + val,
            success: redirect
        });
    } else {
        alert("Please enter a Name first!");
    }

    function redirect(data) {
        if (!data) {
            localStorage.setItem("username", val);
            window.location = link;
        } else {
            alert("This Username is already in use!");
        }
    }
}

$("#joinAsGmBtn").click(function() {
    localStorage.setItem("role", "gamemaster");
    participate("/gamemaster.html");
});

$("#joinAsPlayerBtn").click(function() {
    localStorage.setItem("role", "player");
    participate("/player.html");
});

$("#joinAsViewerBtn").click(function() {
    localStorage.setItem("role", "viewer");
    participate("/viewer.html");
});
