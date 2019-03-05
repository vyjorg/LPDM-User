let productId;
let stockId;

$(document).ready(function() {
    /*
    * Check of form
    */

    $("#btnAdd").on("click", function () {
        addStock();
    });

    $("#close").on("click", function () {
        $('#modalAdd').hide();
    });

    $('#modalAdd').on('shown.bs.modal', function() {
        $(this).find('[autofocus]').focus();
    });

    $('#modalAdd').hide();

    setProductId();
    setStockId();


})

//get the id of the product
function setProductId() {
    productId = $('#product').val();
    console.log("product id = " + productId);
}

//get the id of the stock
function setStockId() {
    stockId = $('#stock').val();
    console.log("product id = " + stockId);
}


function addStock() {

    let jsonObj = {};
    jsonObj.id = stockId;
    jsonObj.quantity = $("#quantity").val();
    jsonObj.expireDate = $("#expiration").val();
    jsonObj.packaging = $("#packaging").val();
    jsonObj.unitByPackage = $("#unity").val();
    jsonObj.productId = productId;
    jsonObj.description=$("#description").val();

    console.log(jsonObj);

    $.ajax({
        url: "/producer/stock/modif",
        type: "put",
        data: JSON.stringify(jsonObj),
        contentType: "application/json",
        dataType : "json"
    });

    $('#modalAdd').show();
}