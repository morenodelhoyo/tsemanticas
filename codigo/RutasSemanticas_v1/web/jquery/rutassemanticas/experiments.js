/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function() {
    
    
    $("#modal_uno").on("show.bs.modal", function(e){
        
        var filesId = $(e.relatedTarget).data('experiment-id');
        var files = Array();

        $("#experiment"+filesId).find("input").each(function() {
            files.push($(this).val());
        });
        
        var text = "";
        for(i=0; i<files.length; i++){
            text += files[i] + "<br>";
        }
        
        text = jQuery.parseHTML(text);
        
        $("#modal_uno_id").html(text);
        
    });
    

    $("#eliminarexperimentosbtn").click(function(){
        $('#modal_cuatro').modal("show");
        $("#palabranocorrecta").attr("hidden", "true");
        
        $("#modal_cuatro_accept").click(function(){
           
            var text = $("#eliminartodasareas").val();
            if(text === "eliminar"){
                $.post('deleteexperiment', {
                    experimentid:-1
                }, function(response) {
                    location.reload();
                });
            } else {
                $("#palabranocorrecta").removeAttr("hidden");
            }
        });
    });

    
});