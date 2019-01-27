let btnAdd;
let producerId;
let currentPictureInput;

// Modals
let modalUpload;
let modalFormDiv;
let modalResult;
let modalResultSuccess;
let modalResultError;

// Result
let result;
let redirectId;

$(document).ready(function() {

    // Add button
    btnAdd = $("#btnAdd");
    btnAdd.on("click", function () {
        addNewProduct();
    });

    // Modals
    modalUpload = $("#modal_upload");
    modalFormDiv = $("#uploadForm");

    // On modal upload picture close
    modalUpload.on('hidden.bs.modal', function () {
        modalFormDiv.empty();
        getLatestPicture();
        $("#mainSearchBtn").prop("disabled", false);
    });

    // Picture URL input
    let inputPic = $(":input[type=text][readonly='readonly']");

    // Check inputs change
    $(":input").on("input change past keyup", function () {

        let inputId = $(this).attr("id");
        if(inputId.match("^product_tax") || inputId.match("^product_price")){
            this.value=this.value.replace(/[^0-9.]/g, '')
                .replace(/(\..*)\./g, '$1');
        }
        let btn = $(this).next("div").find("button");
        if($(this).val() !== "") btn.attr("class", "btn btn-success");
        else btn.attr("class", "btn btn-danger");
        setProducerId($(this));
        checkAllInputs();
    });

    // FocusIN picture URL input
    inputPic.focusin(function (e) {
        e.preventDefault();
        $(this).focusout();
        currentPictureInput = $(this);

        if(producerId == null) setProducerId($(this));
        getUploadForm(producerId);
    });

    modalResult = $("#modal");
    modalResultSuccess = $("#modal_result_body_success");
    modalResultError = $("#modal_result_body_error");

    // On modal result close
    modalResult.on('hidden.bs.modal', function () {
        if(result){
            $(":button").prop("disabled", true);
            window.location.href = "/admin/products/search/" + redirectId;
        }
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

function setProducerId(input) {
    producerId = input.attr("id");
    producerId = producerId.substr(producerId.lastIndexOf("_") + 1);
    console.log("producer id = " + producerId);
}

function getUploadForm(id) {

    $.ajax({
        url: "/admin/products/upload",
        type: "post",
        data: "id=" + id + "&restricted=true",
        success: function (data) {
            insertUploadForm(data);
        },
        error: function (error) {
            modalUpload.focus();
        },
        complete: function () {
            modalUpload.modal();
            modalUpload.focus();
        }
    });
}

function insertUploadForm(uploadForm) {

    let content = $.parseHTML(uploadForm, document, true);
    modalFormDiv.append(content[17]);
}

function getLatestPicture(){

    $.ajax({
        url: "/admin/products/picture/owner/" + producerId,
        type: "get",
        success: function (data) {
            displayUploadedPic(data);
        },
        error: function (error) {
            alert("ERROR : " + error);
        },
    });
}

function displayUploadedPic(data){
    currentPictureInput.val(data.url);
    currentPictureInput.change();
    currentPicture = currentPictureInput.parents('ul').next().find("img");
    currentPicture.attr("src", data.url);
}

function addNewProduct() {

    let deactivate = $("#product_status_" + producerId).val() !== "1";

    let producer = {};
    producer.id = parseInt(producerId);

    let category = {};
    category.id = $("#product_categories_" + producerId).val();

    let jsonObj = {};
    jsonObj.name = $("#product_name_" + producerId).val();
    jsonObj.category = category;
    jsonObj.label = $("#product_label_" + producerId).val();
    jsonObj.price = $("#product_price_" + producerId).val();
    jsonObj.tva = $("#product_tax_" + producerId).val();
    jsonObj.deactivate = deactivate;
    jsonObj.picture = $("#product_picture_" + producerId).val();
    jsonObj.producer = producer;

    $.ajax({
        url: "/admin/products/add",
        type: "post",
        data: JSON.stringify(jsonObj),
        contentType: "application/json",
        dataType : "json",
        success: function (data) {
            console.log("Success msg : " + data);
            result = true;
            redirectId = data.id;
            console.log("redirect id = " + redirectId);
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