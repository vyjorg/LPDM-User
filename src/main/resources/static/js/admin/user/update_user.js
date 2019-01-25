$(document).ready(function() {

    $(":input[type='checkbox']").on("change", function () {

        console.log($(this).attr("id") + " : " + $(this).prop("checked"));
    });
});