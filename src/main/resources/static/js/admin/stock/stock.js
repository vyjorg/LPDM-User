// Product
let currentProduct;
let currentProductId;

// Stock
let currentStockId;

// Buttons
let btnUpdate;
let btnDelete;
let btnAll;

// Modal
let modalStock;
let modalResultSuccess;
let modalResultError;

// Date picker
let datePickerInput;

$(document).ready(function() {

    // Buttons
    btnUpdate = $(":button.btn-success[type=button]");
    btnDelete = $(":button.btn-danger[type=button]");
    btnAll = $(":button");

    // Modal
    modalStock = $("#modal_stock");
    modalResultSuccess = $("#modal_result_body_success");
    modalResultError = $("#modal_result_body_error");

    // Dropdown
    let btnDropdown = $("#btnMenuProducts");

    datePickerInput = $(":input[data-provide='datepicker']");

    $(".dropdown-menu li a").click(function(){

        if(currentProductId != null) currentProduct.fadeOut(500);

        btnDropdown.text($(this).text());
        btnDropdown.val($(this).text());
        btnDropdown.append("<span class='caret' style='margin-left: 10px;'></span>");

        let currentId = $.trim($(this).attr("id"));
        currentProductId = currentId.substr(currentId.indexOf("_") + 1, currentId.length);
        currentProduct = $("#product_" + currentProductId);
        currentProduct.delay(500).fadeIn();
    });

    btnDelete.on("click", function () {
        let btnId = $(this).attr("id");
        currentStockId = btnId.substr(btnId.lastIndexOf("_") + 1, btnId.length);
        currentProductId = $(this).parents(".row").closest('[id^=product]').attr("id");
        currentProductId = currentProductId.substr(currentProductId.indexOf("_") + 1, currentProductId.length);
        console.log("product id = " + currentProductId + " / stock id = " + currentStockId);
        deleteStock(currentStockId);
    });

    btnUpdate.on("click", function () {
        let btnId = $(this).attr("id");
        currentStockId = btnId.substr(btnId.lastIndexOf("_") + 1, btnId.length);
        currentProductId = $(this).parents(".row").closest('[id^=product]').attr("id");
        currentProductId = currentProductId.substr(currentProductId.indexOf("_") + 1, currentProductId.length);
        console.log("product id = " + currentProductId + " / stock id = " + currentStockId);
        updateStock(currentStockId);
    });

    datePickerInput.datepicker({
        language: "fr",
        autoclose: true
    }).change(function () {
        // nothing for the moment
    });

    // On modal result close
    modalStock.on('hidden.bs.modal', function () {
        btnAll.prop("disabled", true);
        window.location.href = "/admin/stocks/search/product/" + currentProductId;
    });
});

function deleteStock(id){

    $.ajax({
        url: "/admin/stocks/delete",
        type: "post",
        data: "stockId=" + id,
        success: function () {
            showUpdateResult(true);
        },
        error: function () {
            showUpdateResult(false);
        }
    });
}

function updateStock(id){

    let quantity = $("#quantity_" + id).val();
    let expireDate = $("#expiredate_" + id).val();
    let unitByPackage = $("#unitbypackage_" + id).val();
    let packaging = $("#packaging_" + id).val();
    let description = $("#description_" + id).val();

    let jsonObj = {};
    jsonObj.id = id;
    jsonObj.productId = currentProductId;
    jsonObj.quantity = quantity;
    jsonObj.expireDate = moment(expireDate, "DD/MM/YYYY").format("YYYY-MM-DD");
    jsonObj.unitByPackage = unitByPackage;
    jsonObj.packaging = packaging;
    jsonObj.description = description;

    console.log(jsonObj);

    $.ajax({
        url: "/admin/stocks/update",
        type: "post",
        data: JSON.stringify(jsonObj),
        contentType: "application/json",
        dataType : "json",
        success: function (data) {
            console.log("Success msg : " + data);
            showUpdateResult(true);
        },
        error: function (data) {
            console.log("Error msg : " + data);
            showUpdateResult(false);
        }
    });
}

function showUpdateResult(success){

    if(success){
        modalResultSuccess.show();
        modalResultError.hide();
    }
    else{
        modalResultSuccess.hide();
        modalResultError.show();
    }

    modalStock.modal();
}

