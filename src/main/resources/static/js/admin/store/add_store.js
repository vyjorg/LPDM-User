
let selectCity;
let cityId;
let storeId;
let result;

// Modal
let modalResult;
let modalResultSuccess;
let modalResultError;

$(document).ready(function() {

    selectCity = null;

    $("#btn_add").click(function () {
        addNewStore();
    });

    $(':input[id^="store_"]').on("input paste", function () {

        if($(this).attr("id") === "store_address_zipCode"){
            $(this).val($(this).val().replace(/\D/g, ''));
        }

        let btn = $(this).next("div").find("button");
        if($(this).val() !== "") btn.attr("class", "btn btn-success");
        else btn.attr("class", "btn btn-danger");

        if(selectCity != null){
            if(selectCity.value === "0") btn.attr("class", "btn btn-danger");
        }

        checkAllInputs();

        selectCity = $("#store_address_cities");

        if($(this).attr("id") === "store_address_zipCode"){
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
            window.location.href = "/admin/stores/search/" + storeId;
        }
    });
});

function checkAllInputs(){

    let disabled = false;
    $('[id^="check_"]').each(function(){
        if($(this).attr("class") === "btn btn-danger") {
            disabled = true;
            return false;
        }
    });

    $("#btn_add").prop("disabled", disabled);
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

function addNewStore() {

    let city = {};
    city.id = cityId;

    let address = {};
    address.streetNumber = $("#store_address_streetNumber").val();
    address.streetName = $("#store_address_streetName").val();
    address.complement = $("#store_address_complement").val();
    address.city = city;

    console.log("Json : " + JSON.stringify(address));

    let jsonObj = {};
    jsonObj.name = $("#store_name").val();
    jsonObj.address = address;

    console.log("Json : " + JSON.stringify(jsonObj));

    $.ajax({
        url: "/admin/stores/add",
        type: "post",
        data: JSON.stringify(jsonObj),
        contentType: "application/json",
        dataType : "json",
        success: function (data) {
            console.log("Success msg : " + JSON.stringify(data));
            result = true;
            storeId = data.id;
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