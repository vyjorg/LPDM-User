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


})

//get the id of the producer
function setProducerId() {
    producerId = $('#producer').val();
    console.log("producer id = " + producerId);
}

// id correspond à l'id de l'utilisateur
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

function displayUploadedPic(data){
    console.log("displayUploadedPic");
    currentPictureInput.val(data.url);
    currentPicture = currentPictureInput.parents('ul').next().find("img");
    console.log(inputPic.val())
    $('#image').attr("src", data.url);
    $('#check_photo').attr("class", "btn btn-success");
}
