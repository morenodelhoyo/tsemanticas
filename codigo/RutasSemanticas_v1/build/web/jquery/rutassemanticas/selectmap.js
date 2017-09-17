/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    (function($){
        
        var raster = new ol.layer.Tile({
          source: new ol.source.OSM()
        });

        var source = new ol.source.Vector({wrapX: false});

        var vector = new ol.layer.Vector({
          source: source,
          style: new ol.style.Style({
            fill: new ol.style.Fill({
              color: 'rgba(255, 255, 255, 0.2)'
            }),
            stroke: new ol.style.Stroke({
              color: '#ffcc33',
              width: 2
            }),
            image: new ol.style.Circle({
              radius: 7,
              fill: new ol.style.Fill({
                color: '#ffcc33'
              })
            })
          })
        });

        var map = new ol.Map({
          layers: [raster, vector],
          target: 'map',
          view: new ol.View({
            center: ol.proj.transform([101.505921, 34.809734], 'EPSG:4326', 'EPSG:3857'),
            zoom: 4
          })
        });


        var draw; // global so we can remove it later
        function addInteraction() {
          var value = 'Box';
          if (value !== 'None') {
            var geometryFunction, maxPoints;
            if (value === 'Box') {
              value = 'LineString';
              maxPoints = 2;
              geometryFunction = function(coordinates, geometry) {
                if (!geometry) {
                  geometry = new ol.geom.Polygon(null);
                }
                var start = coordinates[0];
                var end = coordinates[1];
                geometry.setCoordinates([
                  [start, [start[0], end[1]], end, [end[0], start[1]], start]
                ]);
                return geometry;
              };
            }
            draw = new ol.interaction.Draw({
              source: source,
              type: /** @type {ol.geom.GeometryType} */ (value),
              geometryFunction: geometryFunction,
              maxPoints: maxPoints
            });
            draw.on('drawend',function(e){
                var coordinates = e.feature.getGeometry()["A"];
                var w = ol.proj.transform([coordinates[0],coordinates[1]], 'EPSG:3857','EPSG:4326');
                var x = ol.proj.transform([coordinates[2],coordinates[3]], 'EPSG:3857','EPSG:4326');
                var y = ol.proj.transform([coordinates[4],coordinates[5]], 'EPSG:3857','EPSG:4326');
                var z = ol.proj.transform([coordinates[6],coordinates[7]], 'EPSG:3857','EPSG:4326');
                var minlat = x[1];
                var minlong = w[0];
                var maxlat = w[1];
                var maxlong = y[0];
                
                if(minlat > maxlat){
                    var aux = minlat;
                    minlat = maxlat;
                    maxlat = aux;
                }
                
                if(minlong > maxlong){
                    var aux = minlong;
                    minlong = maxlong;
                    maxlong = aux;
                }
                              
                $("#maxlat").val(maxlat.toFixed(6));
                $("#minlat").val(minlat.toFixed(6));
                $("#maxlong").val(maxlong.toFixed(6));
                $("#minlong").val(minlong.toFixed(6));
                
                
            })
            map.addInteraction(draw);
          }
        }


        addInteraction();

        
        $("#searchbycoord").click(function(){
           alert("aqui"); 
        });
        
        
        
    })(jQuery);
});
