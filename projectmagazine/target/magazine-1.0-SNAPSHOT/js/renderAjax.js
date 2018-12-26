function renderAjax(){
    getData();
    getUserOrders();
}
function renderAnswers() {
    $.ajax({
        type: 'GET',
        url: '/answers.json',
        data_type: 'json'
    }).done(function (data) {
        var tableHtml = "";
        tableHtml += '<h3> Messages: ' + data.length + '</h3>';
        tableHtml += "<ul class = \"list-group\">";
        for(var i = 0; i < data.length; i++) {
            tableHtml += '<li class=\"list-group-item\">';
            tableHtml += '<div class="alert alert-light" role="alert">';
            tableHtml += '<h5>' + (i + 1) + '. Your message: ' + data[i].letter + '</h5>';
            tableHtml += '<h5 id = "special"> Answer: ' + data[i].answer + '</h5>';
            tableHtml += '<button type="button" class="btn btn-danger"' + ' onclick = "deleteAnswer(' + data[i].id + '); return false;"> Delete </button>';
            tableHtml += "</div>";
            tableHtml += "</li>"
        }
        tableHtml += "</ul>";
        $("#answers").html(tableHtml);
    }).fail(function (jqXHR, exception) {
        var msg = '';
        if (jqXHR.status === 0) {
            msg = 'Not connect.\n Verify Network.';
        } else if (jqXHR.status == 404) {
            msg = 'Requested page not found. [404]';
        } else if (jqXHR.status == 500) {
            msg = 'Internal Server Error [500].';
        } else if (exception === 'parsererror') {
            msg = 'Requested JSON parse failed.';
        } else if (exception === 'timeout') {
            msg = 'Time out error.';
        } else if (exception === 'abort') {
            msg = 'Ajax request aborted.';
        } else {
            msg = 'Uncaught Error.\n' + jqXHR.responseText;
        }
        alert(msg)
    });
    return false;
}