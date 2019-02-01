let btnAddCoupon;
let modalResult;
let modalResultSuccess;
let modalResultError;
let result;

$(document).ready(function() {

    btnAddCoupon = $("#btnAddCoupon");
    btnAddCoupon.click(function () {
        addNewCoupon();
    });

    let btnUpdate = $(":button[type='button'][id^='btn_update_']");
    btnUpdate.click(function () {
        let couponId = $(this).attr("id");
        couponId = couponId.substr(couponId.lastIndexOf("_") + 1);
        updateDeleteCoupon(couponId, "put");
    });

    let btnDelete = $(":button[type='button'][id^='btn_delete_']");
    btnDelete.click(function () {
        let couponId = $(this).attr("id");
        couponId = couponId.substr(couponId.lastIndexOf("_") + 1);
        updateDeleteCoupon(couponId, "delete");
    });

    $(":input").on("input change past keyup", function () {

        if($(this).attr("id") === "coupon_amount"){
            this.value=this.value.replace(/[^0-9.]/g, '')
                .replace(/(\..*)\./g, '$1');
        }
        checkAllInputs();
    });

    // Checbox control
    $(":input[type='checkbox']").on("change", function () {
        console.log($(this).attr("id") + " : " + $(this).prop("checked"));
    });

    modalResult = $("#modal");
    modalResultSuccess = $("#modal_result_body_success");
    modalResultError = $("#modal_result_body_error");

    // On modal result close
    modalResult.on('hidden.bs.modal', function () {
        if(result){
            $(":button").prop("disabled", true);
            window.location.href = "/admin/orders/coupon";
        }
    });

    checkAllInputs();
});

function checkAllInputs() {

    let disabled = false;
    $(':input[id^="coupon_"]').each(function () {
        if($(this).val() === ""){
            disabled = true;
            return false;
        }
    });

    btnAddCoupon.prop("disabled", disabled);
}

function addNewCoupon() {

    let jsonObj = {};
    jsonObj.code = $("#coupon_code").val();
    jsonObj.amount = $("#coupon_amount").val();
    jsonObj.description = $("#coupon_desc").val();
    jsonObj.active = $("#coupon_active").prop("checked");

    console.log(JSON.stringify(jsonObj));

    $.ajax({
        url: "/admin/orders/coupon",
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

function updateDeleteCoupon(id, type) {

    console.log("id " + id);

    let jsonObj = {};
    jsonObj.id = id;
    jsonObj.code = "not modified";
    jsonObj.amount = $("#coupon_amount_" + id).val();
    jsonObj.description = $("#coupon_desc_" + id).val();
    jsonObj.active = $("#coupon_active_" + id).prop("checked");

    console.log(JSON.stringify(jsonObj));

    $.ajax({
        url: "/admin/orders/coupon",
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