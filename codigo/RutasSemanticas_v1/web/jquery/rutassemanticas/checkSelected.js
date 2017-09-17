/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function() {
    (function($){
        
        $("#btngettracks").attr("disabled", true);
        $("#btnshowmoredetails").attr("disabled", true);
        
        $("#btnselectall").click(function(){
            $("#divbtnselectall").addClass("hidden");
            $("#divbtncancelselect").removeClass("hidden");
            $(".chkbox").bootstrapToggle("on"); 
        });
        
        $("#btncancelselect").click(function(){
            $("#divbtnselectall").removeClass("hidden");
            $("#divbtncancelselect").addClass("hidden");
            $(".chkbox").bootstrapToggle("off");
        });
        
        
        $("#btnselectalltracks").click(function(){
            $("#divselectalltracks").addClass("hidden");
            $("#divcancelselectalltracks").removeClass("hidden");
            $(".chkboxtrack").bootstrapToggle("on"); 
        });
        
        $("#btncancelselectalltracks").click(function(){
            $("#divselectalltracks").removeClass("hidden");
            $("#divcancelselectalltracks").addClass("hidden");
            $(".chkboxtrack").bootstrapToggle("off");
        });
        
        $(".chkbox").change(function(){
            
            var checked = false;
            var elements = $(".chkbox");
            for(i=0; i<elements.length; i++){
                if($(elements[i]).prop("checked") === true){
                    $("#btngettracks").removeAttr("disabled");
                    checked = true;
                }
            }
            
            if(!checked){
                $("#divbtnselectall").removeClass("hidden");
                $("#divbtncancelselect").addClass("hidden");
                $("#btngettracks").attr("disabled", true);
            }
            
        });
        
        $(".chkboxtrack").change(function(){
            
            var checked = false;
            var elements = $(".chkboxtrack");
            for(i=0; i<elements.length; i++){
                if($(elements[i]).prop("checked") === true){
                    $("#btnshowmoredetails").removeAttr("disabled");
                    checked = true;
                }
            }
            
            if(!checked){
                $("#divselectalltracks").removeClass("hidden");
                $("#divcancelselectalltracks").addClass("hidden");
                $("#btnshowmoredetails").attr("disabled", true);
            }
            
        });
        
        
    })(jQuery);
});