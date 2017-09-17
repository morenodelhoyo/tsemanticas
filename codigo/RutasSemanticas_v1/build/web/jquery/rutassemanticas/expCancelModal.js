/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function() {
    (function($){
        
        checkThread();
        
        var stopped = "false";
        
        var tid = setInterval(checkThread, 5000);

        function checkThread(){
            if(stopped === "false"){
                $.post('getactivethread', {
                    cancelThread:"false",
                    threads:"PTT"
                }, function(response) {
                    console.log(response);
                    if(stopped === "false"){
                        for(i=0; i<response.length; i++){
                            if(response[i] === false){
                                abortTimer();
                                $("#expencurso").addClass("hidden");
                                $("#expfinalizado").removeClass("hidden");
                                console.log("Experiment finished.");
                                document.title = 'Experimento finalizado';
                            }
                        }
                    }
                });
            }else{
                abortTimer();
            }
        }

        $('#modal_uno').on('show.bs.modal', function(e) {
            $("#palabranocorrecta").attr("hidden", "true");
        });
        
        $("#modal_uno_accept").click(function(){
            var text = $("#detener").val();
            if(text === "detener"){
                $("#expencurso").addClass("hidden");
                $("#expdetenido").removeClass("hidden");
                $('#modal_uno').modal('toggle');
                $("#steps").addClass("hidden");
                document.title = 'Experimento detenido';
                stopped = "true";
                $.post('getactivethread', {
                    cancelThread:"true",
                    threads:"PTT"
                }, function(response) {
                    abortTimer();
                    console.log("Experiment cancelled.");
                    dataDelete();
                });
            } else {
                $("#palabranocorrecta").removeAttr("hidden");
            }
        });
        
        function dataDelete(){
            $.post('deletenewdata', {
            }, function(response) {
                console.log("All new data has been deleted.");
            });
        }
        
        function abortTimer() {
            clearInterval(tid);
        }
        
        
    })(jQuery);
});
