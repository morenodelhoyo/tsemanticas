/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    (function($){        
        
        $("#myTable").tablesorter(); 
        var chkbox = document.getElementsByClassName("chkbox");
        for(i=0; i<chkbox.length; i++){
            if(chkbox[i].checked === false){
                $('#btnprocessfiles').prop('disabled', true);
                $('#btndeletefiles').prop('disabled', true);
            }
        }
        
        $("#btnselectall").click(function(){
            $(".chkbox").bootstrapToggle('on');
            $("#divbtnselectall").addClass("hidden");
            $("#divbtnselectcancel").removeClass("hidden");
        });
        
        $("#btnselectcancel").click(function(){
            $(".chkbox").bootstrapToggle('off');
            $("#divbtnselectcancel").addClass("hidden");
            $("#divbtnselectall").removeClass("hidden");
        });
        
    })(jQuery);
});

function check(){
    
    var flag = false;
    var check = document.getElementsByClassName("chkbox");
    for(i=0; i<check.length; i++){
        if(check[i].checked === true){
            flag = true;
            document.getElementById("btnprocessfiles").disabled = false;
            document.getElementById("btndeletefiles").disabled = false;
        }
    }
    
    if(!flag){
        document.getElementById("btnprocessfiles").disabled = true;
        document.getElementById("btndeletefiles").disabled = true;
    }
    
}
