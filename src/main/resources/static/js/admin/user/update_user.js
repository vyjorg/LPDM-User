// Button
let btnUpdate;

// User id
let userId;

// Modal
let modalResult;
let modalResultSuccess;
let modalResultError;

// Result
let result;

$(document).ready(function() {


    // Button
    btnUpdate = $("#btnUpdate");
    btnUpdate.on("click", function (e) {
        updateUser();
    });

    checkInputsOnLoad();

    // Checbox control
    $(":input[type='checkbox']").on("change", function () {
        if(!checkAllCheckBoxes()) $(this).prop("checked", true);
        console.log($(this).attr("id") + " : " + $(this).prop("checked"));
    });

    // Inputs control
    $('[id^="user_"]').on("change paste input keyup focus", function () {
        let btn = $(this).next("div").find("button");
        if($(this).val() !== "") btn.attr("class", "btn btn-success");
        else btn.attr("class", "btn btn-danger");
        userId = $(this).attr("id");
        userId = userId.substr(userId.lastIndexOf("_") + 1);

        checkAllInputs();
    });

    // Datepicker
    let datePickerInput = $(":input[data-provide='datepicker']");
    datePickerInput.datepicker({
        language: "fr",
        autoclose: true
    });

    modalResult = $("#modal");
    modalResultSuccess = $("#modal_result_body_success");
    modalResultError = $("#modal_result_body_error");

    // On modal result close
    modalResult.on('hidden.bs.modal', function () {
        if(result){
            $(":button").prop("disabled", true);
            window.location.href = "/admin/auth/update";
        }
    });
});

function checkInputsOnLoad(){

    $('[id^="user_"]').each(function () {
        if(!$(this).attr("id").match("^user_registrationDate")){
            let btn = $(this).next("div").find("button");
            if($(this).val() !== "") btn.attr("class", "btn btn-success");
            else btn.attr("class", "btn btn-danger");
        }
    });

    if(!checkAllInputs()) btnUpdate.prop("disabled", true);
}

function checkAllInputs() {

    let checkup = true;
    $('[id^="check_"]').each(function(){
        if($(this).attr("class") === "btn btn-danger") {
            checkup = false;
            return false;
        }
    });

    if(checkup) btnUpdate.prop("disabled", false);
    else btnUpdate.prop("disabled", true);
}

function checkAllCheckBoxes(){

    let checkup = false;
    $(":input[type='checkbox']").each(function () {
        if($(this).prop("checked") === true) {
            checkup = true;
            return true;
        }
    });

    return checkup;
}

function updateUser(){

    let roles = [];
    $(":input[type='checkbox']").each(function () {
        if($(this).prop("checked") === true){
            let userRole = {};
            userRole.id = $(this).val();
            roles.push(userRole);
        }
    });

    let active = $("#user_status_" + userId).val() === "1";
    console.log($("#user_status_" + userId).val());

    let jsonObj = {};
    jsonObj.id = userId;
    jsonObj.email = $("#user_email_" + userId).val();
    jsonObj.appRole = roles;
    jsonObj.name = $("#user_name_" + userId).val();
    jsonObj.firstName = $("#user_firstname_" + userId).val();
    jsonObj.tel = $("#user_tel_" + userId).val();
    jsonObj.birthday = moment($("#user_birthday_" + userId).val(), "DD/MM/YYYY")
        .format("YYYY-MM-DD");
    jsonObj.registrationDate = $("#user_registrationDate_" + userId).val();
    jsonObj.addressId = $("#user_address_" + userId).val();
    jsonObj.active = active;

    console.log("AppUser : " + JSON.stringify(jsonObj));

    $.ajax({
        url: "/admin/auth/update/user",
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