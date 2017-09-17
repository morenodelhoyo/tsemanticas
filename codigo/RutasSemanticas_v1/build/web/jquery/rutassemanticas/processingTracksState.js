/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    
    getState();
    
    var tidTwo = setInterval(getState, 5000);

    function getState(){
        $.post('getprocessstate', { 
            thread:"PTT"
        }, function(response) {
            switch(response){
                case 1:
                    $("#readingfiles").addClass("current");
                break;
                case 2:
                    $("#readingfiles").removeClass("current");
                    $("#readingfiles").addClass("visited");
                    
                    $("#processingtracks").addClass("current");
                break;
                case 3:
                    $("#readingfiles").removeClass("current");
                    $("#readingfiles").addClass("visited");
                    $("#processingtracks").removeClass("current");
                    $("#processingtracks").addClass("visited");
                    
                    $("#processingstops").addClass("current");
                break;
                case 4:
                    $("#readingfiles").removeClass("current");
                    $("#readingfiles").addClass("visited");
                    $("#processingtracks").removeClass("current");
                    $("#processingtracks").addClass("visited");
                    $("#processingstops").removeClass("current");
                    $("#processingstops").addClass("visited");
                    
                    $("#processingpois").addClass("current");
                break;
                case 5:
                    $("#readingfiles").removeClass("current");
                    $("#readingfiles").addClass("visited");
                    $("#processingtracks").removeClass("current");
                    $("#processingtracks").addClass("visited");
                    $("#processingstops").removeClass("current");
                    $("#processingstops").addClass("visited");
                    $("#processingpois").removeClass("current");
                    $("#processingpois").addClass("visited");
                    
                    $("#dbsaving").addClass("current");
                break;
                case 6:
                    $("#readingfiles").removeClass("current");
                    $("#readingfiles").addClass("visited");
                    $("#processingtracks").removeClass("current");
                    $("#processingtracks").addClass("visited");
                    $("#processingstops").removeClass("current");
                    $("#processingstops").addClass("visited");
                    $("#processingpois").removeClass("current");
                    $("#processingpois").addClass("visited");
                    $("#dbsaving").removeClass("current");
                    $("#dbsaving").addClass("visited");
                    
                    $("#processended").addClass("current");
                    abortTimer();
                break;
            }
        });
    }

    function abortTimer() {
        clearInterval(tidTwo);
    }
    
});

