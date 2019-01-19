$(function() {
    $("#dateSelector1, #dateSelector2").datepicker({
        language: "fr",
        autoclose: true
    }).change(function () {
        let keyword = $("#inputDate1").val() + ":" + $("#inputDate2").val();
        $("#keyword_date").val(keyword);
    });
});