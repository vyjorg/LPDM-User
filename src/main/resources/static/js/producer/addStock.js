let productId;
let check;

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

    // Check inputs quantity and unity
    $(":input").on("input change when keyup", function () {
        let input = $(this).attr("id");
        if(input.match("quantity") || input.match("unity")){
            this.value=this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
        }

        let button = $(this).next("div").find("button");
        if($(this).val() !== "") button.attr("class", "btn btn-success");
        else button.attr("class", "btn btn-danger");
    });

    // Check inputs packaging and description
    $(":input").on("input change when keyup", function () {
        let input = $(this).attr("id");
        if(input.match("packaging") || input.match("description")){
            this.value=this.value.replace(!/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
        }

        let button = $(this).next("div").find("button");
        if($(this).val() !== "") button.attr("class", "btn btn-success");
        else button.attr("class", "btn btn-danger");
    });

    if($("#expiration").val() !== "") $("#check_expiration").attr("class", "btn btn-success");
    else $("#check_expiration").attr("class", "btn btn-danger");

    //récupère l'id du product
    setProductId();

    check = setInterval(checkAllInputs,1000);
})

//get the id of the producer
function setProductId() {
    productId = $('#product').val();
    console.log("product id = " + productId);
}



//check if all input are ok
function checkAllInputs() {

    let checkup = true;
    $('[id^="check_"]').each(function(){
        if($(this).attr("class") === "btn btn-danger") {
            checkup = false;
            return false;
        }
    });

    if(checkup) $("#btnAdd").prop("disabled", false);
    else $("#btnAdd").prop("disabled", true);
}

function addStock() {

    clearInterval(check);

    let jsonObj = {};
    jsonObj.quantity = $("#quantity").val();
    jsonObj.expireDate = $("#expiration").val();
    jsonObj.packaging = $("#packaging").val();
    jsonObj.unitByPackage = $("#unity").val();
    jsonObj.productId = $("#product").val();
    jsonObj.description=$("#description").val();

    console.log(jsonObj);

    $.ajax({
        url: "/producer/stock/add",
        type: "post",
        data: JSON.stringify(jsonObj),
        contentType: "application/json",
        dataType : "json"
    });

    $('#modalAdd').show();
}