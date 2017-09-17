/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    (function($){
        
        var coords = new Array();
        var eachCoord = new Array();
        var poiCoord = new Array();
        var pois = new Array();
        
        var selectedTracks = JSON.stringify({
        "tracks": $("input[type=hidden]").map(function() {
                        return $(this).val();
                    }).get()
        });
        
        $.post('gettracks', {
                tracks:selectedTracks
        }, function(response) {
            for(i=0; i<response.length; i++){
                coords = new Array();
                pois = new Array();
                var positions = response[i]['listOfPositions'];
                for(j=0; j<positions.length; j++){
                    eachCoord = new Array();
                    var latitude = positions[j]['latitude'];
                    var longitude = positions[j]['longitude'];
                    eachCoord.push(longitude);
                    eachCoord.push(latitude);
                    coords.push(eachCoord);
                    var eachPoiList = positions[j]['listOfPois'];
                    for(k=0; k<eachPoiList.length; k++){
                        if(eachPoiList[k]["id"] !== -1){                            
                            poiCoord = new Array();
                            latitude = eachPoiList[k]['latitude'];
                            longitude = eachPoiList[k]['longitude'];
                            poiCoord.push(longitude);
                            poiCoord.push(latitude);
                            pois.push(poiCoord);
                        }
                    }
                    
                }
                drawTracks(coords, pois, response[i]['trackId']);
            }
        });
    })(jQuery);
});

function drawTracks(coords, pois, id){
    
    $("#loading-"+id).removeClass("loading");
    
    var coordIni = coords[0];
    var vectorSourceIni = new ol.source.Vector({
      //create empty vector
    });

    var iconFeature = new ol.Feature({
        geometry: new ol.geom.Point(ol.proj.transform(coordIni, 'EPSG:4326',   'EPSG:3857')),
        name: 'Initial track point'
    });
    vectorSourceIni.addFeature(iconFeature);
    
    //create the style
    var iconStyleIni = new ol.style.Style({
      image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
        anchor: [0.5, 30],
        anchorXUnits: 'fraction',
        anchorYUnits: 'pixels',
        opacity: 0.75,
        src: 'images/icons/ini.png'
      }))
    });
    //add the feature vector to the layer vector, and apply a style to whole layer
    var vectorLayerIni = new ol.layer.Vector({
      source: vectorSourceIni,
      style: iconStyleIni
    });
    
    var coordEnd = coords[coords.length-1];
    var vectorSourceEnd = new ol.source.Vector({
      //create empty vector
    });
    
    var iconFeatureIni = new ol.Feature({
        geometry: new ol.geom.Point(ol.proj.transform(coordEnd, 'EPSG:4326',   'EPSG:3857')),
        name: 'End track point',
    });
    vectorSourceEnd.addFeature(iconFeatureIni);
    
    //create the style
    var iconStyleEnd = new ol.style.Style({
        image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
            anchor: [0.5, 30],
            anchorXUnits: 'fraction',
            anchorYUnits: 'pixels',
            opacity: 0.75,
            src: 'images/icons/end.png'
        }))
    });

    //add the feature vector to the layer vector, and apply a style to whole layer
    var vectorLayerEnd = new ol.layer.Vector({
        source: vectorSourceEnd,
        style: iconStyleEnd
    });
    
    ////////////////////7
    
    var vectorSourcePoi = new ol.source.Vector({
      //create empty vector
    });
    
    for (var i=0;i<pois.length;i++){
    
        var iconFeaturePoi = new ol.Feature({
            geometry: new ol.geom.Point(ol.proj.transform(pois[i], 'EPSG:4326',   'EPSG:3857')),
            name: 'A POI of the track',
        });
        vectorSourcePoi.addFeature(iconFeaturePoi);
    }
    
    //create the style
    var iconStylePoi = new ol.style.Style({
        image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
            anchor: [0.5, 30],
            anchorXUnits: 'fraction',
            anchorYUnits: 'pixels',
            opacity: 0.75,
            src: 'images/icons/poi.png'
        }))
    });

    //add the feature vector to the layer vector, and apply a style to whole layer
    var vectorLayerPoi = new ol.layer.Vector({
        source: vectorSourcePoi,
        style: iconStylePoi
    });
    
    ///////////////////

    var lineString = new ol.geom.LineString(coords);

    lineString.transform('EPSG:4326', 'EPSG:3857');

    var features = new ol.Feature({
        geometry:lineString,
        name: 'Line'
    });
    
    var lineStyle = new ol.style.Style({
        stroke: new ol.style.Stroke({
            color: 'yellow',
            width: 5
        })
    });

    var raster = new ol.layer.Tile({
        source: new ol.source.OSM()
    });

    var source = new ol.source.Vector({
        features: [features]
    });
    
    var vector = new ol.layer.Vector({
        source: source,
        style: [lineStyle]
    });

    new ol.Map({
        layers: [raster, vector, vectorLayerIni, vectorLayerEnd, vectorLayerPoi],
        target: 'map-'+id,
        view: new ol.View({
            center: ol.proj.transform(coords[0], 'EPSG:4326', 'EPSG:3857'),
            zoom: 10
        })
    });

}