/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    
    $("#steptwo").attr("hidden", "true");
    $("#stepthree").attr("hidden", "true");
    $("#stepfour").attr("hidden", "true");
    $("#stepfive").attr("hidden", "true");
    
    $("#steponecontinue").click(function(){
        var optionSelected = $( "#areaselect" ).val();
        
        if(optionSelected === "-1"){
            alert("Debe seleccionar una de las opciones disponibles.");
        }
        
        if(optionSelected === "1"){
            var textAreaName = $("#areaname").val().length;
            var textAreaDesc = $("#areadescription").val().length;
            var alertShow = true;
        }
        
        if((optionSelected === "1" && textAreaName > 0 && textAreaDesc > 0) || optionSelected === "2"){
            $("#stepone").attr("hidden", "true");
            $("#steptwo").removeAttr("hidden");
            $("#areastep").removeClass("current");
            $("#distancestep").addClass("current");
            $("#areastep").addClass("visited"); 
        }else{
            if(optionSelected === "1"){
                if(textAreaName === 0){
                alert("Se requiere un nombre de área.");
                alertShow = false;
                }
                if(alertShow){
                    if(textAreaDesc === 0){
                        alert("Se requiere una descripción para la nueva área.");
                    }
                }
            }
        }
        
    });
    
    $("#steptwoback").click(function(){
        $("#stepone").removeAttr("hidden");
        $("#steptwo").attr("hidden", "true");
        $("#distancestep").removeClass("current");
        $("#areastep").removeClass("visited");
        $("#areastep").addClass("current");
    });
    
    $("#steptwocontinue").click(function(){
        $("#steptwo").attr("hidden", "true");
        $("#stepthree").removeAttr("hidden");
        $("#distancestep").removeClass("current");
        $("#distancestep").addClass("visited");
        $("#stopstep").addClass("current");
    });
    
    $("#stepthreeback").click(function(){
        $("#steptwo").removeAttr("hidden");
        $("#stepthree").attr("hidden", "true");
        $("#stopstep").removeClass("current");
        $("#distancestep").removeClass("visited");
        $("#distancestep").addClass("current");
    });
    
    $("#stepthreecontinue").click(function(){        
        
        $("#stepthree").attr("hidden", "true");
        $("#stepfour").removeAttr("hidden");
        
        $("#poistep").addClass("current");
        $("#stopstep").removeClass("current");
        $("#stopstep").addClass("visited");
        
    });
    
    $("#stepfourback").click(function(){
        $("#stepthree").removeAttr("hidden");
        $("#stepfour").attr("hidden", "true");
        
        $("#poistep").removeClass("current");
        $("#stopstep").removeClass("visited");
        $("#stopstep").addClass("current");
        
    });
    
    $("#stepfourcontinue").click(function(){
        $("#stepfour").attr("hidden", "true");
        $("#stepfive").removeAttr("hidden");
        
        $("#storagestep").addClass("current");
        $("#poistep").removeClass("current");
        $("#poistep").addClass("visited");
    });
    
    $("#stepfiveback").click(function(){
        $("#stepfour").removeAttr("hidden");
        $("#stepfive").attr("hidden", "true");
        
        $("#poistep").addClass("current");
        $("#storagestep").removeClass("current");
        $("#poistep").removeClass("visited");
    });
    
});
