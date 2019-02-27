let producerId;
let modalFormDiv;
let modalUpload;
let modalResult;
let modalResultSuccess;
let modalResultError;
let currentPictureInput;
let inputPic;


$(document).ready(function() {
    /*
    * Check of form
    */
    inputPic = $(":input[type=text][readonly='readonly']");
    modalUpload = $("#modal_upload");
    modalFormDiv = $("#uploadForm");
    modalResult = $("#modal_result");
    modalResultSuccess = $("#modal_result_body_success");
    modalResultError = $("#modal_result_body_error");


    $("#btnAdd").on("click", function () {
        addNewProduct();
    });

    $("#close").on("click", function () {
        $('#modalAdd').hide();
    });

    $('#modalAdd').on('shown.bs.modal', function() {
        $(this).find('[autofocus]').focus();
    });

    $('#modalAdd').hide();



    //check the category
    $('#category').change(function(){
        $('#check_categories').attr("class", "btn btn-success");
    })

    //check the status
    $('#status').change(function(){
        $('#check_status').attr("class", "btn btn-success");
    })

    // Check inputs price and tax
    $(":input").on("input change when keyup", function () {
        let input = $(this).attr("id");
        if(input.match("price") || input.match("tax")){
            this.value=this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
        }

        let button = $(this).next("div").find("button");
        if($(this).val() !== "") button.attr("class", "btn btn-success");
        else button.attr("class", "btn btn-danger");
    });

    // Check inputs name and label
    $(":input").on("input change when keyup", function () {
        let input = $(this).attr("id");
        if(input.match("name") || input.match("label")){
            this.value=this.value.replace(!/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
        }

        let button = $(this).next("div").find("button");
        if($(this).val() !== "") button.attr("class", "btn btn-success");
        else button.attr("class", "btn btn-danger");
    });

    //récupère l'id du producteur
    setProducerId();

    // FocusIN picture URL input
    inputPic.focusin(function (e) {
        e.preventDefault();
        $(this).focusout();
        currentPictureInput = $(this);
        getUploadForm(producerId);
    });

    // On modal upload picture close
    modalUpload.on('hidden.bs.modal', function () {
        modalFormDiv.empty();
        getLatestPicture();
    });


    checkAllInputs()
})

//get the id of the producer
function setProducerId() {
    producerId = $('#producer').val();
    console.log("producer id = " + producerId);
}

// get the form for upload
function getUploadForm(id) {
    console.log("entrée upload form + id ="+id);
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
    console.log("insertuplaodform");
    let content = $.parseHTML(uploadForm, document, true);
    modalFormDiv.append(content[17]);
}

//get the latest picture
function getLatestPicture(){
    console.log("getlatestpicture");
    $.ajax({
        url: "/admin/products/picture/owner/" + producerId,
        type: "get",
        success: function (data) {
            console.log("success");
            displayUploadedPic(data);
        },
        error: function (error) {
            alert("ERROR : " + error);
        },
    });
}

//show the latest picture
function displayUploadedPic(data){
    console.log("displayUploadedPic");
    currentPictureInput.val(data.url);
    currentPicture = currentPictureInput.parents('ul').next().find("img");
    console.log(inputPic.val())
    $('#image').attr("src", data.url);
    $('#check_photo').attr("class", "btn btn-success");
    checkAllInputs()
}

//check if all input are ok
function checkAllInputs() {

    let checkup = true;
    $('[id^="check_"]').each(function(){
        if($(this).attr("class") === "btn btn-danger") {
            checkup = false;
            return false;
        }
    });

    if(checkup) $("#btnAdd").prop("disabled", false);
    else $("#btnAdd").prop("disabled", true);
}

function addNewProduct() {

    let deactivate = $("#status").val() !== "1";

    let producer = {};
    producer.id = parseInt(producerId);

    let category = {};
    category.id = $("#category").val();

    console.log($("#tva").val())

    let jsonObj = {};
    jsonObj.name = $("#name").val();
    jsonObj.category = category;
    jsonObj.label = $("#label").val();
    jsonObj.price = $("#price").val();
    jsonObj.tax = $("#tva").val();
    jsonObj.deactivate = deactivate;
    jsonObj.picture = $("#picture").val();
    jsonObj.producer = producer;

    console.log(jsonObj);

    $.ajax({
        url: "/producer/product/add",
        type: "post",
        data: JSON.stringify(jsonObj),
        contentType: "application/json",
        dataType : "json"
    });

    $('#modalAdd').show();
}