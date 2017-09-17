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
                            window.location.href = 'process';
                        }else{
                            isPTT = false;
                        }
                    }
                    if(i===1){
                        if(response[i] === true){
                            isPPT = true;
                            $("#helpbtn").addClass("hidden");
                            $("#helpandthreadbtn").removeClass("hidden");
                            $("#amenitiesproc").removeClass("hidden");
                        }else{
                            isPPT = false;
                            $("#amenitiesproc").addClass("hidden");
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

