function hide() {
    var target = document.getElementById("hiddenCart");
    var countChildren = target.children.length;
    for (var i = 0; i < countChildren; i++ ){
        target.children[i].hidden = !target.children[i].hidden;
    }
    if(document.getElementById("hiddenCart").children[0].hidden){
        $('#hideCart').html('Показать корзину');
    } else {
        $('#hideCart').html('Спрятать корзину');
    }


}