// Product
let currentProduct;
let currentProductId;

// Form
let inputQuantity;
let inputDate;
let inputUnit;
let inputPackaging;
let inputDescription;

// Modal
let modalStock;
let modalResultSuccess;
let modalResultError;

// Button
let btnAdd;

let productId;

$(document).ready(function() {

    productId = $('[id^=product_]').attr("id");
    productId = productId.substr(productId.indexOf("_") + 1, productId.length);
    console.log("Id = " + productId);

    // Modal
    modalStock = $("#modal_stock");
    modalResultSuccess = $("#modal_result_body_success");
    modalResultError = $("#modal_result_body_error");

    // Button
    btnAdd = $("#btn_add");
    btnAdd.on("click", function () {
        addNewStock();
    });

    // Inputs
    inputQuantity = $("#stock_quantity");
    inputDate = $("#stock_expiredate");
    inputUnit = $("#stock_unitbypackage");
    inputPackaging = $("#stock_packaging");
    inputDescription = $("#stock_description");

    $(":input").on("input change past keyup", function () {

        if($(this).attr("id") === inputQuantity.attr("id") || $(this).attr("id") === inputUnit.attr("id"))
            this.value = this.value.replace(/[^0-9]/g, '');

        let btn = $(this).next("div").find("button");
        if($(this).val() !== "") btn.attr("class", "btn btn-success");
        else btn.attr("class", "btn btn-danger");

        checkAllInputs();
    });

    // Dropdown
    let btnDropdown = $("#btnMenuProducts");

    $(".dropdown-menu li a").click(function(){

        if(currentProductId != null) currentProduct.fadeOut(500);

        btnDropdown.text($(this).text());
        btnDropdown.val($(this).text());
        btnDropdown.append("<span class='caret' style='margin-left:10px;'></span>");

        let currentId = $.trim($(this).attr("id"));
        currentProductId = currentId.substr(currentId.indexOf("_") + 1, currentId.length);
        currentProduct = $("#product_" + currentProductId);
        currentProduct.delay(500).fadeIn();
    });

    // Date picker
    let datePickerInput = $(":input[data-provide='datepicker']");

    datePickerInput.datepicker({
        language: "fr",
        autoclose: true
    });

    $.fn.inputFilter = function(inputFilter) {
        return this.on("input keydown keyup mousedown mouseup select contextmenu drop", function() {
            if (inputFilter(this.value)) {
                this.oldValue = this.value;
                this.oldSelectionStart = this.selectionStart;
                this.oldSelectionEnd = this.selectionEnd;
            } else if (this.hasOwnProperty("oldValue")) {
                this.value = this.oldValue;
                this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
            }
        });
    };

    // On modal result close
    modalStock.on('hidden.bs.modal', function () {
        $(":button").prop("disabled", true);
        window.location.href = "/admin/stocks/add";
    });

});

function checkAllInputs() {

    let checkup = true;
    $('[id^="check_"]').each(function(){
        if($(this).attr("class") === "btn btn-danger") {
            checkup = false;
            return false;
        }
    });

    if(checkup) btnAdd.prop("disabled", false);
    else btnAdd.prop("disabled", true);
}

function addNewStock() {

    let jsonObj = {};
    jsonObj.quantity = inputQuantity.val();
    jsonObj.expireDate = moment(inputDate.val(), "DD/MM/YYYY").format("YYYY-MM-DD");
    jsonObj.unitByPackage = inputUnit.val();
    jsonObj.packaging = inputPackaging.val();
    jsonObj.description = inputDescription.val();
    jsonObj.productId = productId;

    console.log(jsonObj);

    $.ajax({
        url: "/admin/stocks/add",
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