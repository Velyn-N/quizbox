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
    participate("/gamemaster.html");
});

$("#joinAsPlayerBtn").click(function() {
    participate("/player.html");
});

$("#joinAsViewerBtn").click(function() {
    participate("/viewer.html");
});
