let btnAdd;
let result;
let modalResult;
let modalResultSuccess;
let modalResultError;

$(document).ready(function() {

    btnAdd = $("#btnAdd");
    btnAdd.click(function () {
        btnAdd.prop("disabled", true);
        addNew();
    });

    let btnUpdate = $(":button[type='button'][id^='btn_update_']");
    btnUpdate.click(function () {
        let deliveryId = $(this).attr("id");
        deliveryId = deliveryId.substr(deliveryId.lastIndexOf("_") + 1);
        updateDeleteDelivery(deliveryId, "put");
    });

    let btnDelete = $(":button[type='button'][id^='btn_delete_']");
    btnDelete.click(function () {
        let deliveryId = $(this).attr("id");
        deliveryId = deliveryId.substr(deliveryId.lastIndexOf("_") + 1);
        updateDeleteDelivery(deliveryId, "delete");
    });

    modalResult = $("#modal");
    modalResultSuccess = $("#modal_result_body_success");
    modalResultError = $("#modal_result_body_error");

    $(":input").on("input change past keyup", function () {

        if($(this).attr("id").match("delivery_amount")){
            this.value=this.value.replace(/[^0-9.]/g, '')
                .replace(/(\..*)\./g, '$1');
        }
        checkAddInputs();

        let deliveryId = $(this).attr("id");
        deliveryId = deliveryId.substr(deliveryId.lastIndexOf("_") + 1);
        if($.isNumeric(deliveryId)) checkUpdateInputs(deliveryId);
    });

    // On modal result close
    modalResult.on('hidden.bs.modal', function () {
        if(result){
            $(":button").prop("disabled", true);
            window.location.href = "/admin/orders/delivery";
        }
    });
});

function checkAddInputs() {

    let disabled = false;
    if($("#delivery_method").val() === "" || $("#delivery_amount").val() === "")
        disabled = true;

    btnAdd.prop("disabled", disabled);
}

function checkUpdateInputs(id) {

    if($("#delivery_method_" + id).val() === ""
        || $("#delivery_amount_" + id).val() === "")
        $("#btn_update_" + id).prop("disabled", true);
    else $("#btn_update_" + id).prop("disabled", false);
}

function addNew(){

    let jsonObj = {};

    jsonObj.method = $("#delivery_method").val();
    jsonObj.amount = $("#delivery_amount").val()

    $.ajax({
        url: "/admin/orders/delivery",
        type: "post",
        data: JSON.stringify(jsonObj),
        contentType: "application/json",
        dataType : "json",
        success: function (data) {
            console.log("Success msg : " + data);
            result = true;
            showUpdateResult(result);
        },
        error: function (data) {
            console.log("Error msg : " + data);
            result = false;
            showUpdateResult(result);
        }
    });
}

function updateDeleteDelivery(id, type) {

    console.log("id " + id);

    let jsonObj = {};
    jsonObj.id = id;
    jsonObj.method = $("#delivery_method_" + id).val();
    jsonObj.amount = $("#delivery_amount_" + id).val();

    console.log(JSON.stringify(jsonObj));

    $.ajax({
        url: "/admin/orders/delivery",
        type: type,
        data: JSON.stringify(jsonObj),
        contentType: "application/json",
        dataType : "json",
        success: function (data) {
            console.log("Success msg : " + data);
            result = true;
            showUpdateResult(result);
        },
        error: function (data) {
            console.log("Error msg : " + data);
            result = false;
            showUpdateResult(result);
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

    modalResult.modal();
}