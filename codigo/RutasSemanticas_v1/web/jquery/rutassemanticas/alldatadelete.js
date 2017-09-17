/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    
    $("#modal_uno_accept").click(function(){
        
        $('#modal_uno').modal('toggle');
        $("#poisdeleted").text("Ejecutando");
        $.post('alldatadelete', {
                type:"pois"
        }, function(response) {
            $("#deletepois").attr("disabled", true);
            $("#poisdeleted").text("Ejecutado");
        });
    });
    
    
    $("#modal_dos_accept").click(function(){
        
        $('#modal_dos').modal('toggle');
        $("#tracksdeleted").text("Ejecutando");
        $.post('alldatadelete', {
                type:"all"
        }, function(response) {
            $("#cleartables").attr("disabled", true);
            $("#deletetracks").attr("disabled", true);
            $("#tablescleared").text("Ejecutado");
        });
    });
    
});

