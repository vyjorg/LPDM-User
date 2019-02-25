let producerId;
$(document).ready(function() {
    /*
    * Check of form
    */
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

    getUploadForm(producerId);



})

//get the id of the producer
function setProducerId() {
    producerId = $('#producer').val();
    console.log("producer id = " + producerId);
}

// id correspond à l'id de l'utilisateur
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
