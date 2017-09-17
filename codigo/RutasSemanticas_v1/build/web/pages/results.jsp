<%-- 
    Document   : results
    Author     : David Moreno del Hoyo
--%>

<%@page import="java.util.Date"%>
<%@page import="model.data.Position"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="model.data.Area"%>
<%@page import="model.data.Track"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:useBean id="aTracks" scope="session" class="ArrayList<Track>" />
<jsp:useBean id="aAreas" scope="session" class="ArrayList<Area>" />
<jsp:useBean id="minlat" scope="session" class="Double" />
<jsp:useBean id="minlong" scope="session" class="Double" />
<jsp:useBean id="maxlat" scope="session" class="Double" />
<jsp:useBean id="maxlong" scope="session" class="Double" />

<%
    NumberFormat nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(3);
    nf.setMinimumFractionDigits(3);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../WEB-INF/jspf/jscss.jspf" %>
        <link href="css/bootstrap/bootstrap-toggle.min.css" rel="stylesheet">
        <script src="jquery/bootstrap/bootstrap-toggle.min.js"></script>
        <title>Resultados de los análisis</title>
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
                <div class="col-md-12 text-center">
                    <h1 class="text-center">Datos disponibles</h1>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-md-12 text-right">
                    <div id="helpbtn" name="helpbtn">
                        <button type="button" class="btn btn-default" data-toggle="modal" data-target="#modal_uno">Obtener ayuda</button>
                    </div>
                    <div id="helpandthreadbtn" name="helpandthreadbtn" class="hidden">
                        <button type="button" class="btn btn-default" data-toggle="modal" data-target="#modal_uno">Obtener ayuda</button>
                        <button data-toggle="modal" data-target="#modal_dos" class="btn btn-warning">Ver eventos activos</button>
                    </div>
                </div>
                
            </div>
            <hr>
            <div class="row">
                <div class="col-md-5">
                    <div class='area-header'>
                        <div class='row'>
                          <div class='text-center'><h4>Información sobre cada área</h4></div>
                        </div>
                    </div>
                    <% if(aAreas.size() == 0){ %>
                    <div class="alert alert-danger alert-dismissable fade in">
                        <h4 class="text-center">Sin áreas</h4>
                        <strong>En este momento no existen áreas con rutas asignadas.</strong> Debe procesar un nuevo fichero para continuar.
                    </div>
                    <% } else { %>
                    <form method="POST"
                          action="">
                      <div class="areas">

                          <%  for(int i=0; i<aAreas.size(); i++){
                                  Area eachArea = new Area();
                                  eachArea = (Area) aAreas.get(i);
                                  double newLat = (eachArea.getMaxLat() + eachArea.getMinLat()) / 2;
                                  double newLong = (eachArea.getMaxLong() + eachArea.getMinLong()) / 2;
                          %>
                                <div name="area" id="area" class='area'>
                                    <div class='row'>
                                        <div class="col-md-3">
                                            <input data-on="Sí" data-off="No" data-onstyle="success" data-toggle="toggle" class="chkbox" name="chkbox" id="chkbox" type="checkbox" value="<%= eachArea.getId() %>">
                                        </div>
                                        <div class='col-md-9'>
                                            <div id='nombre' class='nombre'>Nombre: <span class='nombre-span'> <%= eachArea.getName() %>  </span></div>
                                            <div id='descripcion' class='descripcion'>Descripción: <span class='descripcion-span'><%= eachArea.getDescription() %> </span></div>
                                            <div id='zona' class='zona'>Limitación geográfica:<br>
                                                <span class='zona-span'>
                                                    <div class="row text-center">
                                                        <%= nf.format(eachArea.getMaxLat()) %>
                                                    </div>
                                                    <div class="row text-center">
                                                        <%= nf.format(eachArea.getMinLong()) %> - <%= nf.format(eachArea.getMaxLong()) %>
                                                    </div>
                                                    <div class="row text-center">
                                                        <%= nf.format(eachArea.getMinLat()) %>
                                                    </div>
                                                </span>
                                            </div>
                                            <div id="num-rutas" class="num-rutas">Número de rutas: <span class="num-rutas-span"><%= eachArea.getNumTracks() %></span></div>
                                        </div>
                                    </div>
                                </div>
                          <% } %>
                        </div>
                        <div class="row">
                            <div class="col-md-5 text-left" name="divbtnselectall" id="divbtnselectall">
                                <button name="btnselectall" id="btnselectall" type="button" class="btn btn-success">Seleccionar todas</button>
                            </div>
                            <div class="col-md-5 text-left hidden" name="divbtncancelselect" id="divbtncancelselect">
                                <button name="btncancelselect" id="btncancelselect" type="button" class="btn btn-warning">Cancelar selección</button>
                            </div>
                            <div class="col-md-5 text-right">
                                <button name="btngettracks" id="btngettracks" type="submit" class="btn btn-default">Obtener rutas de las áreas marcadas</button>
                            </div>
                        </div>    
                        
                    </form>
                    <hr>
                    <div class="row">
                        <div class="col-md-12">
                        <div class="mapofareas">
                            <h3 class="text-center">Selecciona un área en el mapa</h3>
                            <div id="map" class="map"></div>
                        </div>
                        </div>
                    </div>
                    <div class="row botonesresultados">
                        <div class="col-md-12">
                        <form method="post">
                            <div class="row">
                                <div class="col-md-4  col-md-offset-4">
                                    <label for="maxlat">Latitud máxima</label>
                                    <input name="maxlat" id="maxlat" class="form-control" type="number" min="-90" max="90" value="<%= maxlat %>" step="0.000001">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-4">
                                    <label for="minlong">Longitud mínima</label>
                                    <input name="minlong" id="minlong" class="form-control" type="number" min="-180" max="180" value="<%= minlong %>" step="0.000001">
                                </div>
                                <div class="col-md-4 col-md-offset-4">
                                    <label for="maxlong">Longitud máxima</label>
                                <input name="maxlong" id="maxlong" class="form-control" type="number" min="-180" max="180" value="<%= maxlong %>" step="0.000001">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-4 col-md-offset-4">
                                    <label for="minlat">Latitud mínima</label>
                                    <input name="minlat" id="minlat" class="form-control" type="number" min="-90" max="90" value="<%= minlat %>" step="0.000001">
                                </div>
                            </div>
                            <br>
                            <div class="text-center">
                                <button type="submit" class="btn btn-default">Buscar rutas en el área seleccionada</button>
                            </div>
                        </form>
                    </div>
                    </div>                        
                    <% } %>
                </div>
                
                <div class="col-md-7">
                    <div class="area-header">
                        <h4 class="text-center">Últimas rutas disponibles en la Base de Datos</h4>
                    </div>
                    
                    <% if(minlat != 0 && aTracks.size() > 0 && aAreas.size() != 0){ %>
                        <div class="emptytracks">
                            <div class="alert alert-warning alert-dismissable fade in">
                                <strong>Atención. </strong> La región seleccionada puede contener rutas de diferentes áreas.
                            </div>
                        </div>
                    <% } %>
                    <% if(aTracks.size() == 0 && aAreas.size() != 0) { %>
                    <div class="emptytracks">
                        <div class="alert alert-danger alert-dismissable fade in">
                            <strong>No existen rutas que cumplan con los criterios seleccionados.</strong>Puede volver a seleccionar un área en la parte derecha de la ventana.
                        </div>
                    </div>
                    <%} else if(aAreas.size() == 0){ %>
                    <div class="emptytracks">
                        <div class="alert alert-danger alert-dismissable fade in">
                            <h4 class="text-center">Sin rutas</h4>
                            <strong>todavía no existen rutas.</strong> Para obtener rutas analizables, debe procesar un nuevo fichero en la sección
                            correspondiente.
                        </div>
                    </div>
                    <% } else { %>
                    <div class="track">
                        <div class="row">
                            <form method="POST"
                                  action="tree">
                            <div class="tablapruebas">
                            <table class="table">
                                <thead> 
                                    <tr>
                                        <th class="col-md-2">
                                            Seleccionar
                                        </th>
                                        <th class="col-md-1">
                                            ID
                                        </th>
                                        <th class="col-md-2">
                                            Área
                                        </th>
                                        <th class="col-md-1">
                                            Nº posiciones
                                        </th>
                                        <th class="col-md-2">
                                            Fecha de inicio
                                        </th>
                                        <th class="col-md-2">
                                            Fecha de fin
                                        </th>
                                        <th class="col-md-2">
                                            Fecha de creación
                                        </th>
                                    </tr>
                                </thead>    
                        </div>
                    </div>
                    <div class="tracks">
                                <tbody>
                                <%
                                for(int i=0;i<aTracks.size();i++){
                                    Track track = aTracks.get(i); %>
                                    <tr>        
                                        <td class="text-center">
                                            <input data-on="Sí" data-off="No" data-onstyle="success" data-toggle="toggle" class="chkboxtrack" name="chkboxtrack" id="chkboxtrack-<%= track.getTrackId() %>" type="checkbox" value="<%= track.getTrackId() %>">
                                        </td>
                                        <td class="text-center">
                                            <%= track.getTrackId() %>
                                        </td>
                                        <td class="text-center">
                                            <%= track.getArea().getName() %>
                                        </td>
                                        <td class="text-center">
                                            <%= track.getnPositions() %>
                                        </td>
                                        <td class="text-center">                                      
                                            <%= track.getInitDate() %>
                                        </td>
                                        <td>
                                            <%= track.getEndDate() %>
                                        </td>
                                        <td class="text-center">
                                            <%= track.getFechaCreacion() %>
                                        </td>
                                    </tr>
                                <% } %>
                                </tbody>
                            </table>
                            </div>
                            <div class="col-md-5 text-left" name="divselectalltracks" id="divselectalltracks">
                                <button name="btnselectalltracks" id="btnselectalltracks" type="button" class="btn btn-success">Seleccionar todas</button>
                            </div>
                            <div class="col-md-5 text-left hidden" name="divcancelselectalltracks" id="divcancelselectalltracks">
                                <button name="btncancelselectalltracks" id="btncancelselectalltracks" type="button" class="btn btn-warning">Cancelar selección</button>
                            </div>
                            <div class="col-md-5 text-right" name="divshowmoredetails" name="divshowmoredetails">
                                <button name="btnshowmoredetails" id="btnshowmoredetails" type="submit" class="btn btn-default">Ver más detalles</button>
                            </div>
                        </form>
                    </div>
                    <% } %>
                </div>
            </div>
        </div>                
        </div>
</div>
            
<!-- modal_uno -->
            <div class="modal fade" id="modal_uno" tabindex="-1" role="dialog" aria-labelledby="modal_uno">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="modal_uno">Ayuda (resultados)</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-10 col-md-offset-1">
                                    <div class="header text-center">
                                        <h3>Selección de área y de rutas</h3>
                                    </div>
                                    <div class="text-justify">
                                        En la parte izquierda de esta página puede seleccionar un área de tres formas diferentes. A continuación
                                        se explica cómo hacerlo:
                                        <ul>
                                            <li><strong>A partir de las áreas existentes</strong>: seleccionando el área de la 
                                            lista de áreas mostradas.</li>
                                            <li><strong>Seleccionando un área en el mapa</strong>: seleccionando un área en el mapa </li>
                                            <li><strong>Indicando las coordenadas geográficas</strong>: también puede indicar las
                                            coordenadas geográficas de forma manual en el formulario.</li>
                                        </ul>
                                        Una vez seleccionada un área de cualquiera de las tres maneras posibles, se deberá clicar sobre
                                        el botón correspondiente a cada formulario y, en breves momentos, aparecerá, en la parte izquierda de la
                                        página, un listado con las rutas llevadas a cabo en dicha área.
                                        Seleccionando las rutas que se desee analizar se podrá continuar a la siguiente ventana. En dicha ventana
                                        se permitirá realizar un filtro por fechas antes de ejecutar el algoritmo.
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                        </div>
                    </div>
                </div>
            </div>  

        <!-- modal_dos (hilos) -->
        <div class="modal fade" id="modal_dos" tabindex="-1" role="dialog" aria-labelledby="modal_dos">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="modal_dos">Ayuda</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-12 text-center">
                                <div id="amenitiesproc" name="amenitiesproc" class="hidden">
                                    <div class="alert alert-warning alert-dismissable">
                                        <div class="text-justify">
                                            <div class="text-center">
                                                <strong>Información</strong>
                                            </div>
                                            <hr>
                                            Actualmente se está procesando un fichero
                                            de Puntos de Interés. Cuando el proceso termine, el aviso desaparecerá y se mostrará
                                            su información en la sección correspondiente.</div>
                                    </div>
                                </div>
                                <div id="experimentproc" name="experimentproc" class="hidden">
                                    <div class="alert alert-warning alert-dismissable">
                                        <div class="text-justify">
                                            <div class="text-center">
                                                <strong>Información</strong>
                                            </div>
                                            <hr>
                                            Actualmente se está procesando una prueba. 
                                            Cuando el proceso termine, el aviso desaparecerá y se mostrará
                                            su información en la sección correspondiente.
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>
        
        <script src="jquery/rutassemanticas/threads/checkResultsPage.js"></script>
        <script src="jquery/rutassemanticas/checkSelected.js"></script>
        <script src="jquery/rutassemanticas/selectmap.js"></script>
        <script src="jquery/rutassemanticas/tablesorter.js"></script>
        <%@include file="../WEB-INF/jspf/footer.jspf" %>
    </body>
</html>
