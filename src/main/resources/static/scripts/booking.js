window.addEventListener("load", function() {
    var checkStd = $("#Standard");
    var checkEnh = $("#Enhanced");
    var checkVIP = $("#VIP");
    var price = $("#price");

    const setPrice = () => {
        let subtotal = checkEnh.is(":checked") ? 29.99 : checkVIP.is(":checked") ? 49.99 : 12.99;
        price.val(subtotal.toFixed(2));
    }

    checkStd.on("click", setPrice);
    checkEnh.on("click", setPrice);
    checkVIP.on("click", setPrice);
});