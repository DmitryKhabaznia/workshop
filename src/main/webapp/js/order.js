$("input[name='paymentType']").change(function() {
    if($("input[value='card']").attr("checked") != 'checked') { 
        $("#cartInput").toggleClass( "d-none" );
    }
});

$("input[name='deliveryType']").change(function() {
    if($("input[value='delivery']").attr("checked") != 'checked') { 
        $("#deliveryInput").toggleClass( "d-none" );
    }
});