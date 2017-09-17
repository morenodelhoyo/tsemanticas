/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function() {
    (function($){
        
        var clicked = true;
        
        $("#area").click(function(){
            if(clicked){
                $("#area").addClass("area-clicked");
                $("#area").addClass("area-remove");
                $("#area").removeClass("area-unclicked");
                $("#area").removeClass("area");
                clicked = false;
            }else{
                $("#area").removeClass("area-clicked");
                $("#area").addClass("area-unclicked");
                $("#area").removeClass("area-remove");
                $("#area").addClass("area");
                clicked = true;
            }
        });
    })(jQuery);
});
