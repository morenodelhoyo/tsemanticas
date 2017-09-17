/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    
    $('#modal_uno').on('show.bs.modal', function(e) {
        var nameId = $(e.relatedTarget).data('name-id');
        $(e.currentTarget).find('div[name="modal_uno_name"]').text(nameId);
    });
    
    $("#modal_uno_accept").click(function(){
        var fileName = $("#modal_uno_id").text();
        var filesToDelete = JSON.stringify({
            files: fileName
        });
        $.post('deletefiles', {
            files:filesToDelete
        }, function(response) {
            location.reload();
        });
    });
    
    $("#btndeletefiles").click(function(){
        
        var selected = [];
        $('input:checked').each(function() {
            selected.push($(this).attr('value'));
        });

        var text = "";
        $.each(selected, function(index, value){
            text += value +"<br>";
        });
        
        $("#modal_dos_id").append(text);
        $('#modal_dos').modal('show');
                
    });
    
    $("#modal_dos_accept").click(function(){
        var filesToDelete = JSON.stringify({
        "files": $("input:checked").map(function() {
                    return $(this).val();
                }).get()
        });
        
        $.post('deletefiles', {
            files:filesToDelete
        }, function(response) {
            location.reload();
        });
    });
    
});