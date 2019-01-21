// Producer
let selectProducer;
let btnSearchProducer;
let inputSearchProducer;

// Product
let categories;
let inputName;
let inputLabel;
let inputPrice;
let inputTva;
let inputPicture;

let btnAdd;

$(document).ready(function() {

    // Producer
    selectProducer = $("#select_producer");
    btnSearchProducer = $("#btn_search_producer");
    inputSearchProducer = $("#input_search_producer");
    btnSearchProducer.click(function () {
        searchProducer();
    });

    // Add button
    btnAdd = $("#btnAdd");

    // Categories
    categories = $("#categories");
    checkInput(categories, $("#check_categories"));
    categories.on('input', function () {checkInput($(this), $("#check_categories"))});

    // Product name
    inputName = $("#name");
    checkInput(inputName, $("#check_name"));
    inputName.on('input', function () {checkInput($(this), $("#check_name"));});

    // Product label
    inputLabel = $("#label");
    checkInput(inputName, $("#check_label"));
    inputLabel.on('input', function () {checkInput($(this), $("#check_label"));});

    // Product price
    inputPrice = $("#price");
    checkInput(inputPrice, $("#check_price"));
    inputPrice.on('input', function () {checkInput($(this), $("#check_price"));});

    // Product tva
    inputTva = $("#tva");
    checkInput(inputPrice, $("#check_tva"));
    inputTva.on('input', function () {checkInput($(this), $("#check_tva"));});

    // Product picture
    inputPicture = $("#picture");
    checkInput(inputPicture, $("#check_picture"));
    inputPicture.change(function () {
        checkInput($(this), $("#check_picture"));
    });

});

function checkInput(obj, checkObj){

    if(obj.val() !== "") checkObj.attr("class", "btn btn-success");
    else checkObj.attr("class", "btn btn-danger");
    checkAllInputs();
}

function checkAllInputs() {
    $("button[type='button']").each(function () {
        if($(this).attr("class") === "btn btn-danger"){
            btnAdd.prop("disabled", true);
            return false;
        }
    });
    btnAdd.prop("disabled", false);
}

function searchProducer(){

    if(inputSearchProducer.val() === "") return false;

    alert("click search");
}