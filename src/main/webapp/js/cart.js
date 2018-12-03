var cartCount = $('.cartCount');
var cartTotal = $('.cartTotal');
var totalSum = $('.totalSum');
var totalCount = $('.totalCount');
var cartLabel = $('.pmd-badge');
var noProducts = $('.noProducts');
var productsTable = $('.productsTable');

$(".addToCart").click(function () {
    var id = $(this).attr('productId');
    var count = $(this).parents("div.project-detail").find('.productCount').val();
    var params = { 'productId': id, 'productCount': count };
    $.ajax({
        url: 'shoppingCart' + '?' + $.param(params),
        type: 'PUT',
        success: function (data) {
            setCountAndTotal(data)
        },
        error: function () {
            errorFunc()
        }
    });
})

$(".countInput").change(function () {
    var id = $(this).attr('productId');
    var subtotal = $(this).parents('tr').find('.subtotal');
    var count = $(this).val();
    var params = { 'productId': id, 'productCount': count };
    $.post('shoppingCart', $.param(params), function (data) {
        setCountAndTotal(data);
        subtotal.text('$' + data.subtotal);
    }).fail(function () { errorFunc() });
})

$(".deleteItem").click(function () {
    var id = $(this).attr("productId");
    var position = $(this).parents('tr');
    var params = { 'productId': id };
    $.ajax({
        url: 'shoppingCart' + '?' + $.param(params),
        type: 'DELETE',
        success: function (data) {
            position.remove();
            setCountAndTotal(data);
            if (data.count == 0) {
                emptyCount();
            }
        },
        error: function () {
            errorFunc()
        }
    });
})

$(".deleteAll").click(function () {
    var id = $(this).attr("productId");
    var params = { 'productId': id };
    $.ajax({
        url: 'shoppingCart' + '?' + $.param(params),
        type: 'DELETE',
        success: function (data) {
            setCountAndTotal(data);
            if (data.count == 0) {
                emptyCount();
            }
        },
        error: function () {
            errorFunc();
        }
    });
})

function setCountAndTotal(data) {
    cartCount.text(data.count);
    cartTotal.text('$' + data.total);
    cartLabel.attr("data-badge", data.count);
}

function emptyCount() {
    productsTable.remove();
    noProducts.toggleClass("d-none");
}

function errorFunc() {
    alert("Sorry but our weak server does not respond.");
}