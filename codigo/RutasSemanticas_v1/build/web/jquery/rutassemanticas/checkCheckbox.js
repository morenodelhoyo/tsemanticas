/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    (function($){
        
        if($("#findstops").prop("checked") === true){
            $(".stops :input").attr("disabled", false);
            $("#findpois").removeAttr("disabled");
        } else {
            $("#alertsearchpoi").removeClass("hidden");
            $(".stops :input").attr("disabled", true);
            $("#findpois").attr("disabled", true);
        }
        
        if($("#findpois").prop("checked") === true){
            $(".pois :input").attr("disabled", false);
        } else {
            $(".pois :input").attr("disabled", true);
        }
        
        $("#amenitiesalert").hide();
        $("#nuevaarea").hide();
        $("#existingareaselect").hide();
        
        
        $("#findstops").change(function(){
            if($(this).prop('checked')){
                $("#alertsearchpoi").addClass("hidden");
                $(".stops :input").attr("disabled", false);
                $("#findpois").removeAttr("disabled");
            }else{
                $("#alertsearchpoi").removeClass("hidden");
                $(".stops :input").attr("disabled", true);
                $("#findpois").bootstrapToggle("off");
                $("#findpois").attr("disabled", true);
            }

        });

        $("#findpois").change(function(){
            if($(this).prop('checked')){
                $(".pois :input").attr("disabled", false);
            }else{
                $(".pois :input").attr("disabled", true);
            }
        });
        
    })(jQuery);
});

function getValOfAreaSelect(sel){
    if(sel.value === '1'){
        $("#amenitiesalert").show();
        $("#nuevaarea").show();
        $("#existingareaselect").hide();
    } else if(sel.value === '2') {
        $("#amenitiesalert").hide();
        $("#nuevaarea").hide();
        $("#existingareaselect").show();
    } else {
        $("#amenitiesalert").hide();
        $("#nuevaarea").hide();
        $("#existingareaselect").hide();
    }
}