let selectCity;
let cityId;

// Modal
let modalResult;
let modalResultSuccess;
let modalResultError;

let storeId;
let result;

$(document).ready(function() {

    selectCity = null;

    // Button
    let btnUpdate = $(":button[id^='btn_update_']");
    btnUpdate.on("click", function () {
        storeId = getStoreId($(this));
        updateStore(storeId);
        $(this).prop("disabled", true);
    });

    $(":input[id^='name_']").on("input paste", function (){
        checkAllInputs(getStoreId($(this)));
    });

    $(':input[id^="address_"]').on("input paste", function () {

        if($(this).attr("id").match("^address_zipCode")){
            $(this).val($(this).val().replace(/\D/g, ''));
        }

        let btn = $(this).next("div").find("button");
        if($(this).val() !== "") btn.attr("class", "btn btn-success");
        else btn.attr("class", "btn btn-danger");

        if(selectCity != null){
            if(selectCity.value === "0") btn.attr("class", "btn btn-danger");
        }

        checkAllInputs(getStoreId($(this)));

        selectCity = $("#address_cities_" + getStoreId($(this)));

        if($(this).attr("id") === "address_zipCode_" + getStoreId($(this))){
            if($(this).val().length === 5) loadCities($(this).val());
            else selectCity.prop("disabled", true);
        }
    });

    modalResult = $("#modal");
    modalResultSuccess = $("#modal_result_body_success");
    modalResultError = $("#modal_result_body_error");

    // On modal result close
    modalResult.on('hidden.bs.modal', function () {
        if(result){
            $(":button").prop("disabled", true);
            window.location.href = "/admin/stores/search";
        }
    });
});

function checkAllInputs(id) {

    console.log("check " + id);

    $(':input[id^="address_"]').each(function () {

        let btn = $(this).next("div").find("button");
        if($(this).val() !== "") btn.attr("class", "btn btn-success");
        else btn.attr("class", "btn btn-danger");
    });

    let disabled = false;
    $('[id^="check_"]').each(function(){
        if($(this).attr("id").match(id)){
            console.log("id = " + $(this).attr("id"));
            if($(this).attr("class") === "btn btn-danger") {
                disabled = true;
                return false;
            }
        }
    });

    if($("#name_" + id).val() === "") disabled = true;

    $("#btn_update_" + id).prop("disabled", disabled);
}

function loadCities(zipCode){

    console.log("load cities for " + zipCode);

    $.ajax({
        url: "/admin/auth/search/address/cities",
        type: "post",
        data: "zipCode=" + zipCode,
        success: function (data) {
            console.log("Success msg : " + data);
            displayCities(data);
        },
        error: function (data) {
            console.log("Error msg : " + data);
        }
    });
}

function displayCities(data) {

    console.log("display cities");

    selectCity.find("option").remove();
    selectCity.append("<option value='0' selected disabled>Sélectionner une ville</option>");

    $(data).each(function (index, value) {
        selectCity.append("<option value='" + value.id + "'>" + value.name + "</option>");
    });

    selectCity.prop("disabled", false);

    selectCity.on("change", function () {
        cityId = this.value;
        console.log("selected value = " + cityId);
    });
}

function updateStore(id) {

    let city = {};
    city.id = cityId;

    let address = {};
    address.streetNumber = $("#address_streetNumber_" + id).val();
    address.streetName = $("#address_streetName_" + id).val();
    address.complement = $("#address_complement_" + id).val();
    address.city = city;

    console.log("Json : " + JSON.stringify(address));

    let jsonObj = {};
    jsonObj.id = id;
    jsonObj.name = $("#name_" + id).val();
    jsonObj.address = address;

    console.log("Json : " + JSON.stringify(jsonObj));

    $.ajax({
        url: "/admin/stores/update",
        type: "put",
        data: JSON.stringify(jsonObj),
        contentType: "application/json",
        dataType : "json",
        success: function (data) {
            console.log("Success msg : " + JSON.stringify(data));
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

function getStoreId(obj) {

    let storeId = obj.attr("id");
    return storeId.substr(storeId.lastIndexOf("_") + 1);
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