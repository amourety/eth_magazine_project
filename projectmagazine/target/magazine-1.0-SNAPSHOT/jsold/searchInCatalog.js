function search(query) {
    query = document.getElementById("q").value;
    $.ajax({
        type: 'GET',
        url: '/search',
        data: {
            q: query
        }
    }).done(function (data) {
        var tableHtml = "";
        tableHtml +='<ul>';
        var availableTags = [];

        for(var i = 0; i < data.length;i++){
            availableTags[i] = data[i].name;
            tableHtml +='<li>';
            tableHtml += '<div class="product" onclick="doBuying(' + data[i].id + ',' + data[i].price + ')" name="iphone 5">';
            tableHtml += '<div class="product-img">';
            tableHtml += '<a href="#"><img src="' + data[i].img + '" style="padding: 20px;"></a>';
            tableHtml += '</div>';
            tableHtml += '<p style="margin-top: 25px;" class="product-title">';
            tableHtml += '<a>' + data[i].name + '</a>';
            tableHtml += '</p>';
            tableHtml += '<p class="product-price" id="price">' + data[i].price + ' ₽</p>';
            tableHtml += '</div>';
            tableHtml += '</li>';
        }
        tableHtml +='</ul>';
        $("#left").html(tableHtml);
        $("#q").autocomplete({
            source: availableTags
        });
    }).fail(function () {
        alert('ALL BAD')
    });
}