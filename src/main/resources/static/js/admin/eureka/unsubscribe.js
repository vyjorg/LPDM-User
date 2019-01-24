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

    let xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if(xmlHttp.readyState === 4 && xmlHttp.status === 200){
            try{
                let data = xmlHttp.responseText;
            }
            catch (e) {
                console.log(e.message + " in " + xmlHttp.responseText);
                return;
            }

        }
    };

    xmlHttp.open("GET", "https://eureka.lpdm.kybox.fr/eureka/apps/" + appId + "/" + instanceId, true);
    xmlHttp.send();

    /*

    $.ajax({
        url: "https://eureka.lpdm.kybox.fr/eureka/apps/" + appId + "/" + instanceId,
        type: "DELETE",
        crossDomain: true,
        dataType: "jsonp, xml",
        jsonpCallback: 'processJSONPResponse',

        headers: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "text/plain; charset=utf-8"
        },

        success: function (data) {
            console.log("Success msg : " + JSON.stringify(data));
            showUpdateResult(true);
        },
        error: function (xhr, status, error) {
            console.log("Result: " + status + " " + error + " " + xhr.status + " " + xhr.statusText);
            //console.log("Error msg : " + JSON.stringify(data));
            //showUpdateResult(false);
        }
    });

    */

    /*
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
    */
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

function f(data) {
    alert(data);
}