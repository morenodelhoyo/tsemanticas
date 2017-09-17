/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function() {
    
    $('#modal_uno').on('show.bs.modal', function(e) {
        var areaName = $(e.relatedTarget).data('name-id');
        var areaId = $(e.relatedTarget).data('id');
        $(e.currentTarget).find('div[name="modal_uno_name"]').text(areaName);
        $(e.currentTarget).find('input[name="areaidhidden"]').val(areaId);
        $("#palabranocorrecta").attr("hidden", "true");
    });
    
    
    $("#modal_uno_accept").click(function(){
        var areaId = $("input#areaidhidden").val();
        var text = $("#eliminararea").val();
        if(text === "eliminar"){
            $.post('deletearea', {
                areaid:areaId
            }, function(response) {
                location.reload();
            });
        } else {
            $("#palabranocorrecta").removeAttr("hidden");
        }
    });
    
    $('#modal_dos').on('show.bs.modal', function(e) {
        
        var areaName = $(e.relatedTarget).data('name-id');
        var areaDescription = $(e.relatedTarget).data('description');
        var latMin = $(e.relatedTarget).data('latmin');
        var latMax = $(e.relatedTarget).data('latmax');
        var longMin = $(e.relatedTarget).data('longmin');
        var longMax = $(e.relatedTarget).data('longmax');
        var areaId = $(e.relatedTarget).data('id');
        $(e.currentTarget).find('input[name="areaid"]').val(areaId);
        $(e.currentTarget).find('div[name="modal_uno_name"]').text(areaName);
        $(e.currentTarget).find('input[name="nuevonombre"]').val(areaName);
        $(e.currentTarget).find('textarea[name="nuevadescripcion"]').val(areaDescription);
        $(e.currentTarget).find('input[name="nuevalatmax"]').val(latMax);
        $(e.currentTarget).find('input[name="nuevalatmin"]').val(latMin);
        $(e.currentTarget).find('input[name="nuevalongmax"]').val(longMax);
        $(e.currentTarget).find('input[name="nuevalongmin"]').val(longMin);
    });
    
    
    $("#modal_dos_accept").click(function(e){
        
        var areaName = $("#nuevonombre").val();
        var areaDescription = $("#nuevadescripcion").val();
        var latMin = $("#nuevalatmin").val();
        var latMax = $("#nuevalatmax").val();
        var longMin = $("#nuevalongmin").val();
        var longMax = $("#nuevalongmax").val();
        var areaId = $("#areaid").val();
        
        
        $.post('updatearea', {
            areaid:areaId,
            areaname:areaName,
            areadescription:areaDescription,
            latmin:latMin,
            latmax:latMax,
            longmin:longMin,
            longmax:longMax
        }, function(response) {
            location.reload();
        });
    });
    
    
    $("#createareabtn").click(function(){
        
        var areaName = $("#areaname").val();
        var areaDescription = $("#areadesc").val();
        var latMin = $("#latmax").val();
        var latMax = $("#latmin").val();
        var longMin = $("#longmin").val();
        var longMax = $("#longmax").val();
       
        $.post('createarea', {
            areaname:areaName,
            areadescription:areaDescription,
            latmin:latMin,
            latmax:latMax,
            longmin:longMin,
            longmax:longMax
        }, function(response) {
            location.reload();
        });
       
        
    });
    
        
});