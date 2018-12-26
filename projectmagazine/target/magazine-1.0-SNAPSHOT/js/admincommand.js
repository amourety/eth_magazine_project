function getUsers() {
    $.ajax({
        type: 'GET',
        url: '/allusers.json',
        data_type: 'json'
    }).done(function (data) {
        var tableHtml = "";
        tableHtml += '<h3> Users: ' + data.length +'</h3>';
        tableHtml += '<ul class="list-group">';
        for(var i = 0; i < data.length; i++){
            tableHtml += '<li class="list-group-item">'+ (i + 1) + '. ' + data[i].name + ' ' + data[i].role.name + '<img src="/images/delete.png" onclick="deleteProductFromList(' + data[i].id + '); return false;"/>' + '</li>';
        }
        tableHtml += '</ul>';
        $("#listUsers").html(tableHtml);
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
}


function replyMessage(id) {
    var text = document.getElementById('message' + id).value;
    $.ajax({
        type: 'POST',
        url: '/admin',
        data: {
            id_message: id,
            text: text,
            action: 'replyMessage'
        }
    }).done(function () {
        getMessages();
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
}


function getMessages() {
    $.ajax({
        type: 'GET',
        url: '/messages.json',
        data_type: 'json'
    }).done(function (data) {
        var tableHtml = "";
        tableHtml += '<h3> Messages: ' + data.length + '</h3>';
        tableHtml += "<ul class = \"list-group\">";
        for(var i = 0; i < data.length; i++) {
            tableHtml += '<li class=\"list-group-item\">';
            tableHtml += '<div class="alert alert-light" role="alert">';
            tableHtml += '<h5>' + (i + 1) + '. User [id ' + data[i].userid + '] Message [id ' + data[i].id + ']:</h5>';
            tableHtml += '<h5 id = "special">'  + data[i].letter + '</h5>';
            tableHtml += '<br>';
            tableHtml += '<h5> Answer: </h5>';
            tableHtml += '<textarea class="form-control" rows="2" id="message' + data[i].id + '"></textarea>';
            tableHtml += '<button type="button" class="btn btn-light"' + ' onclick = "replyMessage('+ data[i].id +')"> REPLY </button>';
            tableHtml += "</div>";
            tableHtml += "</li>"
        }
        tableHtml += "</ul>";
        $("#messagesCount").html(data.length);
        $("#answersList").html(tableHtml);
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
}



function getCatalog() {
    $.ajax({
        type: 'GET',
        url: '/catalog.json',
        data_type: 'json'
    }).done(function (data) {
        var tableHtml = "";
        tableHtml += '<h3> Count: ' + data.length +'</h3>';
        tableHtml += '<ul class="list-group">';
        for(var i = 0; i < data.length; i++){
            tableHtml += '<li class="list-group-item">'+ (i + 1) + '. ' + data[i].name + "  " + data[i].price + "$ " + '<img src="/images/delete.png" onclick="deleteProductFromList(' + data[i].id + '); return false;"/>' + '</li>';
        }
        tableHtml += '</ul>';
        $("#listOfProducts").html(tableHtml);
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
}
function deleteProductFromList(id) {
    $.ajax({
        type: 'POST',
        url: '/admin',
        data: {
            product_id: id,
            action: 'deleteProduct'
        }
    }).done(function () {
        getCatalog();
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
}

function addProductIntoList() {
    var name = document.getElementById('addProductName').value;
    var price = document.getElementById('addProductPrice').value;
    var img = document.getElementById('addProductImg').value;
    $.ajax({
        type: 'POST',
        url: '/admin',
        data: {
            name: name,
            price: price,
            img: img,
            action: 'addProduct'
        }
    }).done(function () {
        getCatalog();
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
}