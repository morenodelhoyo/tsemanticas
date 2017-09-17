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
        
        var tid = setInterval(checkThread, 10000);

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
                            $("#alertArea").removeAttr("hidden");
                            $("#myTable").find("button").attr("disabled", "disabled");
                        }else{
                            isPTT = false;
                            $("#experimentproc").addClass("hidden");
                            $("#myTable").find("button").removeAttr("disabled");
                            var buttons = $("#myTable").find("button");
                            for(j=0;j<buttons.length; j++){
                                var btn = $(buttons[j]);
                                if(btn.attr("data-id") === "1"){
                                    btn.attr("disabled", "disabled");
                                }
                            }
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
