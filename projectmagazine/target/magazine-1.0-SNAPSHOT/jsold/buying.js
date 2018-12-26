function doBuying(id, price) {
    $.ajax({
        type: 'POST',
        url: '/main',
        data: {
            action: 'buy',
            product_id: id,
            price: price
        }
    }).done(function (data) {
        if(document.getElementById("hiddenCart").children[0].hidden){
            $('#hideCart').html('Показать корзину');
        } else {
            $('#hideCart').html('Спрятать корзину');
        }
        var tableHtml = "";
        tableHtml += '<table>';
        if (data.length === 0) {
            tableHtml += '<p \n' +
                '    padding: 0 20px 0 20px;\n>' + 'Ваша корзина пуста.' + '</p>';
            $("#amountOfCart").html('ВАША КОРЗИНА');
        } else {
            var sum = 0;
            var amount = 0;
            for (var i = 0; i < data.length; i++) {
                sum = sum + +data[i].price * +data[i].amount;
                amount = amount + +data[i].amount;
            }

            for (var i = 0; i < data.length; i++) {
                tableHtml += '<tr>' +
                    '<td class = "product-title" style="color: #d51e08;">' + data[i].name + '</td>' +
                    '<td class = "product-title"> | </td>' +
                    '<td class = "product-title">' + data[i].price + '</td>' +
                    '<td class = "product-title"> | </td>' +
                    '<td class = "product-title">' + data[i].amount + '</td>' +
                    '<td class = "product-title"> | </td>' +
                    '<td>' + '<img src="/images/delete.png" onclick="deleteProduct(' + data[i].id + ')" id=' + data[i].id + '/>' + '</td>' +
                    '</tr>';
            }

        }
        tableHtml += '</table>';
        if (data.length === 0) {
        } else {
            $("#amountOfCart").html('В вашей корзине ' + amount + ' эл.');
            tableHtml += '<p class="sum">' + 'ИТОГО: ' + sum + '</p>';
        }
        $("#cart").html(tableHtml);
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