function renderCatalogFromDb() {
    $.ajax({
        type: 'GET',
        url: '/catalog.json',
        data_type: 'json'
    }).done(function (data) {
        var tableHtml = "";
        tableHtml += '<div class = "row">';
        for(var i = 0; i < data.length;i++){
            tableHtml +='<div class="col-md-4 col-sm-6 col-lg-4">';
            tableHtml +='<div class="item">';
            tableHtml +='<img class="img-responsive" src="' + data[i].img + '">';
            tableHtml += '<div class="item-dtls">';
            tableHtml += '<h4><a style="font-size: 14px; color: #000000; font-weight: bold;">' + data[i].name + ' </a></h4>';
            tableHtml += '<span class="price lblue" id = "special">' + data[i].price + '$</span>';
            tableHtml += '</div>';
            tableHtml += '<div class="ecom bg-black">';
            tableHtml +='<a class="btn" href="" onclick="doBuying(' + data[i].id + ',' + data[i].price + '); return false;"> Add to cart</a>';
            tableHtml += '</div>';
            tableHtml += '</div>';
            tableHtml += '</div>';
        }
        tableHtml += '</div>';

        $("#catalog").html(tableHtml);

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