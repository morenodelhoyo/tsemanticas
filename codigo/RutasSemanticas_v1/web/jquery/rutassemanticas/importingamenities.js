/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    
    checkThread();
    getState();
    
    var tid = setInterval(checkThread, 5000);

    function checkThread(){
        $.post('getactivethread', { 
            cancelThread:"false",
            threads:"PPT"
        }, function(response) {
            for(i=0; i<response.length; i++){
                if(i===0){
                    if(response[i] === false){
                        abortTimer();
                        $("#importing").addClass("hidden");
                        $("#importingamenitiesended").removeClass("hidden"); 
                    }
                }
            }
        });
    }

    function abortTimer() {
        clearInterval(tid);
    }
    
    
    var tidTwo = setInterval(getState, 10000);

    function getState(){
        $.post('getprocessstate', { 
            thread:"PPT"
        }, function(response) {
            switch(response){
                case 1:
                    $("#readingfile").addClass("current");
                break;
                case 2:
                    $("#readingfile").removeClass("current");
                    $("#readingfile").addClass("visited");
                    $("#processingnodes").addClass("current");
                break;
                case 3:
                    $("#readingfile").removeClass("current");
                    $("#readingfile").addClass("visited");
                    $("#processingnodes").removeClass("current");
                    $("#processingnodes").addClass("visited");
                    $("#processingways").addClass("current");
                break;
                case 4:
                    $("#readingfile").addClass("visited");
                    $("#processingnodes").removeClass("current");
                    $("#processingnodes").addClass("visited");
                    $("#processingways").removeClass("current");
                    $("#processingways").addClass("visited");
                    $("#processended").addClass("current");
                    abortTimerTwo();
                break;
            }
        });
    }

    function abortTimerTwo() {
        clearInterval(tidTwo);
    }
    
    
    
});

