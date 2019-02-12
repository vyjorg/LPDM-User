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
})