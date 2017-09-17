/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function() {
    
    $('#modal_uno').on('show.bs.modal', function(e) {
        var id = $(e.relatedTarget).data('name-id');
        var name = $(e.relatedTarget).data('name');
        $(e.currentTarget).find('div[name="modal_uno_name"]').text(name);
        $(e.currentTarget).find('input[name="regionid"]').val(id);
    });
    
    
    $("#modal_uno_accept").click(function(){
        var id = $("#regionid").val();
        console.log(id);
        $.post('deleteregions', {
            all:"false",
            regionid:id
        }, function(response) {
            location.reload();
        });
    });
    
    
    $("#deleteallregions").click(function(){
        
        $("#palabranocorrecta").attr("hidden", "true");
        
        $("#modal_cuatro_accept").click(function(){
            
            var text = $("#deleteallpois").val();
            if(text === "eliminar"){
                $.post('deleteregions', {
                    all:"true"
                }, function(response) {
                    location.reload();
                });
            }else{
                $("#palabranocorrecta").removeAttr("hidden");
            }
        });
        
    });
    
    
    
    
});
