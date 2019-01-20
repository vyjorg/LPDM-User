let modalUpload;
let modalFormDiv;
let inputId;
let searchForm;
let searchInput;
let searchSelect;
let currentPictureInput;
let currentPicture;
let currentProductId;

$( document ).ready(function() {

    let btnUpdate = $(".btn-success");
    let btnDeactivate = $(".btn-danger");
    let inputPic = $(":input[type=text][readonly='readonly']");
    modalUpload = $("#modal_upload");
    modalFormDiv = $("#uploadForm");
    searchForm = $("#mainSearchForm");
    searchInput = $("#mainSearchInput");
    searchSelect = $("#select");

    inputPic.focusin(function (e) {
        e.preventDefault();
        $(this).focusout();
        currentPictureInput = $(this);
        inputId = $(this).attr("id");
        inputId = inputId.substr(inputId.indexOf("_") + 1, inputId.length);
        getUploadForm(inputId);
    });

    modalUpload.on('hidden.bs.modal', function () {
        modalFormDiv.empty();
        getLatestPicture();
        $("#mainSearchBtn").prop("disabled", false);
    });

    btnUpdate.on("click", function () {
        let btnId = $(this).attr("id");
        currentProductId = btnId.substr(btnId.indexOf("_") + 1, btnId.length);
        updateProduct(currentProductId);
    });

    btnDeactivate.on("click", function () {
        alert("btn id : " + $(this).attr("id"));
    });
});

function getUploadForm(id) {

    $.ajax({
        url: "/admin/products/upload",
        type: "post",
        data: "id=" + id,
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
        url: "/admin/products/picture/owner/" + inputId,
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
    currentPicture = currentPictureInput.parents('ul').next().find("img");
    currentPicture.attr("src", data.url);
}

function updateProduct(id){

    let name = $("#name_" + id).val();
    let label = $("#label_" + id).val();
    let price = $("#price_" + id).val();
    let tva = $("#tva_" + id).val();
    let picture;
    if(currentPictureInput == null) picture = null;
    else picture = currentPictureInput.val();

    /*
    let jsonObj = "{'id': " + id + ", 'name': " + name + ", 'label': "
                    + label + ", 'price': " + price + ", 'tva': "
                    + tva + ", 'picture': " + picture + " }";
                    */
    let jsonObj = {};
    jsonObj.id = id;
    jsonObj.name = name;
    jsonObj.label = label;
    jsonObj.tva = tva;
    jsonObj.price = price;
    jsonObj.picture = picture;

    $.ajax({
        url: "/admin/products/update",
        type: "post",
        data: JSON.stringify(jsonObj),
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            alert("response : " + data)
        },
        error: function (error) {
            alert("ERROR : " + error);
        }
    });
}

