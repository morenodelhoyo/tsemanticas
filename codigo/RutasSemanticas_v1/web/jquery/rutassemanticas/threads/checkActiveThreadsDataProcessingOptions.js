/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    (function($){
        
        checkThread();
        
        var tid = setInterval(checkThread, 1000);

        function checkThread(){
            $.post('getactivethread', {
                cancelThread:"false",
                threads:"PTT,PPT"
            }, function(response) {
                for(i=0; i<response.length; i++){
                    if(i===0){
                        if(response[i] === true){
                            window.location.href = 'upload';
                        }
                    }
                    if(i===1){
                        if(response[i] === true){
                            $("#alertpoi").removeAttr("hidden");
                        }else{
                            $("#alertpoi").attr("hidden", "true");
                            abortTimer();
                        }
                    }
                }
            });
        }

        function abortTimer() { // to be called when you want to stop the timer
            clearInterval(tid);
        }
    
    })(jQuery);
});
