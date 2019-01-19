$( document ).ready(function() {

    $("#li_" + selectedTab).addClass("active");
    $("#" + selectedTab).addClass("active in");

    let orderId;
    let currentOrder;
    let btnDropdown = $("#btnMenuOrders");

    $(".dropdown-menu li a").click(function(){

        if(orderId != null) currentOrder.fadeOut(500);

        btnDropdown.text($(this).text());
        btnDropdown.val($(this).text());
        btnDropdown.append("<span class='caret' style='margin-left: 10px;'></span>");

        let currentId = $.trim($(this).attr("id"));
        orderId = currentId.substr(currentId.indexOf("_") + 1, currentId.length);
        currentOrder = $("#order_" + orderId);
        currentOrder.delay(500).fadeIn();
    });
});