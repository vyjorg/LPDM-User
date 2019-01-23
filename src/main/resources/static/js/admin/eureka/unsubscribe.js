let modalEureka;
let modalResultSuccess;
let modalResultError;

$(document).ready(function() {

    modalEureka = $("#modal_eureka");
    modalResultSuccess = $("#modal_result_body_success");
    modalResultError = $("#modal_result_body_error");

    $(":button").on("click", function () {

        let instantId = $(this).parent().parent().find("input").val();
        let appId = $(this).parents("ul").parent().find("span").attr("id");

        unsubscribe(appId, instantId);
    });

    // On modal result close
    modalEureka.on('hidden.bs.modal', function () {
        $(":button").prop("disabled", true);
        window.location.href = "/admin/eureka/unsubscribe";
    });

});

function unsubscribe(appId, instanceId){

    let jsonObj = {};
    jsonObj.appId = appId;
    jsonObj.instanceId = instanceId;

    $.ajax({
        url: "/admin/eureka/unsubscribe",
        contentType: "application/json",
        data: JSON.stringify(jsonObj),
        type: "post",
        success: function (data) {
            console.log("Success msg : " + JSON.stringify(data));
            showUpdateResult(true);
        },
        error: function (data) {
            console.log("Error msg : " + JSON.stringify(data));
            showUpdateResult(false);
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

    modalEureka.modal();
}