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
})