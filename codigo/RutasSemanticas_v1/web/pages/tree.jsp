<%-- 
    Document   : tree
    Author     : David Moreno del Hoyo
--%>

<%@page import="model.data.Poi"%>
<%@page import="model.data.Position"%>
<%@page import="model.data.Track"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="aTracks" scope="session" class="ArrayList<Track>" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../WEB-INF/jspf/jscss.jspf" %>
        <title>Rutas seleccionadas</title>
    </head>
    <body>
       <nav class="navbar navbar-default navbar-right" role="navigation">
            <div class="container-fluid">
                <!-- El logotipo y el icono que despliega el menú se agrupan
                     para mostrarlos mejor en los dispositivos móviles -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                        <span class="sr-only">Desplegar navegación</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>

                <!-- Agrupar los enlaces de navegación, los formularios y cualquier
                     otro elemento que se pueda ocultar al minimizar la barra -->
                <div class="collapse navbar-collapse navbar-ex1-collapse">
                    <ul class="nav navbar-nav">
                        <li><a class="navbar-brand" href="index">Rutas Semánticas</a></li>
                        <li>
                            <a href="index">Portada</a>
                        </li>
                        <li>
                            <a href="upload">Ficheros</a>
                        </li>
                        <li class="active">
                            <a href="results">Resultados</a>
                        </li>
                        <li>
                            <a href="experiment">Pruebas</a>
                        </li>
                        <li>
                            <a href="manage">Gestión</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <div id="wrap">
            <div id="main" class="container">
                <div class="row">
                    <div class="col-md-6">
                        <h3 class="text-center">Rutas seleccionadas</h3>
                    </div>
                    <div class="col-md-6">
                        <h3 class="text-center">Mapa de las rutas seleccionadas</h3>
                    </div>
                </div>

                <div class="tracks">
                    <%  int pois = 0;
                        int stops = 0;
                        for(int i=0; i<aTracks.size(); i++){
                            pois = 0;
                            stops = 0;
                            Track track = aTracks.get(i);
                            ArrayList<Poi> aPoi = new ArrayList();
                            for(Position p : track.getListOfPositions()){
                                
                                if(p.isStop()){
                                    stops +=1;
                                }
                                aPoi.addAll(p.getListOfPois());
                            }
                    %>
                    <div class="row treerow">
                        <div class="col-md-6">
                            <input type="hidden" value="<%= track.getTrackId() %>">
                            
                            <div class="lista">
                                <ul>
                                    <li><strong>Fecha de inicio: </strong><%= track.getInitDate() %></li>                                    
                                    <li><strong>Fecha de fin: </strong><%= track.getEndDate() %></li> 
                                    <li><strong>Posiciones de la ruta: </strong><%= track.getListOfPositions().size() %></li> 
                                    <li><strong>Paradas en ruta: </strong><%= stops %></li>
                                    <% if(aPoi.size() == 0){ %>
                                    <li><strong>Puntos De Interés: </strong>"Sin Puntos de interés a mostrar."</li> 
                                    <li><strong>Secuencia de POIs: </strong>"No existe una secuencia que mostrar"</li>
                                    <%} else { %>
                                    <% String actualAmenity = aPoi.get(0).getAmenity();
                                        String newAmenity;
                                        String all = actualAmenity;
                                        pois ++;
                                       for(int j=1;j<aPoi.size();j++){
                                        newAmenity = aPoi.get(j).getAmenity();
                                        if(!actualAmenity.equals(newAmenity)){
                                            all += " --> " + newAmenity;
                                            pois ++;
                                            actualAmenity = newAmenity;
                                        }
                                        %>
                                    <% } %>
                                    <li><strong>Puntos De Interés: </strong><%= pois %></li> 
                                    <li><strong>Secuencia de POIs: </strong><%= all %></li> 
                                 <% } %>
                                </ul>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div class="loading" name="loading-<%= (int)track.getTrackId() %>" id="loading-<%= (int)track.getTrackId() %>"></div>
                            <div class="map" name="map-<%= (int)track.getTrackId() %>" id="map-<%= (int)track.getTrackId() %>"></div>
                        </div>
                    </div>
                        <hr>
                    <% } %>
                </div>
            </div>
            
        </div>
        
        
        <script src="jquery/rutassemanticas/treemap.js"></script>
        <%@include file="../WEB-INF/jspf/footer.jspf" %>
    </body>
</html>
