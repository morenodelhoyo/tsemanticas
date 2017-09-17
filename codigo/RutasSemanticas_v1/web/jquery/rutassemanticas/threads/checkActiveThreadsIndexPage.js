/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    (function($){
        
        checkThread();
        var tid = setInterval(checkThread, 1000);
        var isPTT = false;
        var isPPT = false;

        function checkThread(){
            $.post('getactivethread', {
                cancelThread:"false",
                threads:"PTT,PPT"
            }, function(response) {
                for(i=0; i<response.length; i++){
                    if(i===0){
                        if(response[i] === true){
                            $("#alertthread").removeClass("hidden");
                            isPTT = true;
                            $("#experimentproc").removeClass("hidden");
                        }else{
                            isPTT = false;
                            $("#experimentproc").addClass("hidden");
                        }
                    }
                    if(i===1){
                        if(response[i] === true){
                            $("#alertthread").removeClass("hidden");
                            isPPT = true;
                            $("#amenitiesproc").removeClass("hidden");
                        }else{
                            isPPT = false;
                            $("#amenitiesproc").addClass("hidden");
                        }
                    }
                }
                if(!isPTT && !isPPT){
                    $("#alertthread").addClass("hidden");
                    abortTimer();
                }
            });
        }

        function abortTimer() { // to be called when you want to stop the timer
            clearInterval(tid);
        }
    
    
    })(jQuery);
});
