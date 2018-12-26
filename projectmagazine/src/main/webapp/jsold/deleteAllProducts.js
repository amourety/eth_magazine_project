function deleteAllProduct() {
    $.ajax({
        type: 'POST',
        url: '/main',
        data: {
            action: 'deleteAll'
        }
    }).done(function () {
        var tableHtml = "";
        tableHtml += '<table>';
        tableHtml += '<p \n' +
            '    padding: 0 20px 0 20px;\n>' + 'Все было удалено! ' + '</p>';
        tableHtml += '<p \n' +
            '    padding: 0 20px 0 20px;\n>' + 'Добавьте что-нибудь в корзину!' + '</p>';
        tableHtml += '</table>';
        $("#amountOfCart").html('ВАША КОРЗИНА');
        $("#cart").html(tableHtml);
        return false;
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