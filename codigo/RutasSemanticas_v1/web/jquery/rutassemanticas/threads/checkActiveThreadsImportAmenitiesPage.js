/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    (function($){
        
        var isPTT = false;
        var isPPT = false;
        
        checkThread();
        
        var tid = setInterval(checkThread, 5000);

        function checkThread(){
            $.post('getactivethread', {
                cancelThread:"false",
                threads:"PTT,PPT"
            }, function(response) {
                for(i=0; i<response.length; i++){
                    if(i===0){
                        if(response[i] === true){
                            isPTT = true;
                            $("#helpbtn").addClass("hidden");
                            $("#helpandthreadbtn").removeClass("hidden");
                            $("#experimentproc").removeClass("hidden");
                            $("#myTable").find("button").attr("disabled", "true");
                            $("#deleteallregions").attr("disabled", "true");
                        }else{
                            isPTT = false;
                            $("#helpbtn").removeClass("hidden");
                            $("#helpandthreadbtn").addClass("hidden");
                            $("#experimentproc").addClass("hidden");
                            $("#myTable").find("button").removeAttr("disabled");
                            $("#deleteallregions").removeAttr("disabled");
                        }
                    }
                    if(i===1){
                        if(response[i] === true){
                            isPPT = true;
                            window.location.href = 'importingamenities';
                        }else{
                            isPPT = false;
                        }
                    }
                }
                if(!isPTT && !isPPT){
                    $("#helpbtn").removeClass("hidden");
                    $("#helpandthreadbtn").addClass("hidden");
                    abortTimer();
                }
            });
        }

        function abortTimer() { // to be called when you want to stop the timer
            clearInterval(tid);
        }
    
    
    })(jQuery);
});

