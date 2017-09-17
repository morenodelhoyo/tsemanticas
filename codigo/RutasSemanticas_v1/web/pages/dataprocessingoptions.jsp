<%-- 
    Document   : fileprocessing
    Author     : David Moreno del Hoyo
--%>
<%@page import="model.data.Area"%>
<%@page import="java.util.ArrayList"%>
<jsp:useBean id="selectedFiles" scope="session" class="String[]" />
<jsp:useBean id="areas" scope="session" class="ArrayList<Area>" />
<jsp:useBean id="trackdistance" scope="session" class="Double" />
<jsp:useBean id="findstops" scope="session" class="Boolean" />
<jsp:useBean id="stopdistance" scope="session" class="Double" />
<jsp:useBean id="medianvariation" scope="session" class="Double" />
<jsp:useBean id="findpois" scope="session" class="Boolean" />
<jsp:useBean id="maxradius" scope="session" class="Double" />
<jsp:useBean id="radiusmultiplier" scope="session" class="Double" />
<jsp:useBean id="storeindb" scope="session" class="Boolean" />

<jsp:useBean id="notallfiles" scope="session" class="Boolean" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../WEB-INF/jspf/jscss.jspf" %>
        <link rel="stylesheet" type="text/css" href="css/steps/style.css"/>
        <link rel="stylesheet" type="text/css" href="css/bootstrap/bootstrap-toggle.min.css">
        <script type="text/javascript" src="jquery/steps/modernizr.js"></script>
        <script src="jquery/bootstrap/bootstrap-toggle.min.js"></script>
        <title>Opciones de procesado de los ficheros seleccionados</title>
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
                        <li class="active">
                            <a href="upload">Ficheros</a>
                        </li>
                        <li>
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
                    <h1>Procesado de ficheros</h1>
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-12">
                    <section>
                            <nav>
                                <ol class="cd-multi-steps text-top">
                                    <li name="areastep" id="areastep" class="current"><span>Área</span></li>
                                    <li name="distancestep" id="distancestep"><span>Distancia</span></li>
                                    <li name="stopstep" id="stopstep"><span>Paradas</span></li>
                                    <li name="poistep" id="poistep"><span>POIs</span></li>
                                    <li name="storagestep" id="storagestep"><span>Almacenado</span></li>
                                </ol>
                            </nav>
                        </section>
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-3">
                    <% if(selectedFiles != null && selectedFiles.length != 0){ %>
                    <fieldset>
                        <legend>Ficheros seleccionados</legend>
                    </fieldset>
                    <table id="myTable" class="tablesorter table">
                        <thead>
                            <tr>
                                <th>
                                    Nombre
                                </th>
                            </tr>
                        </thead>
                        <tbody>

                            <% for(int i=0; i<selectedFiles.length; i++){ %>
                            <tr>
                                <td>
                                    <%= selectedFiles[i] %>
                                </td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                    <% } %>
                    <div class="alert alert-warning alert-dismissable text-justify" name="alertpoi" id="alertpoi" hidden="true">
                        <strong>Se está ejecutando la carga de nuevos Puntos De Interés sobre la Base de Datos del sistema.
                        Es posible que si ejecuta una nueva prueba en este momento se produzcan resultados adversos
                        y/o el rendimiento no sea óptimo.</strong>
                    </div>
                    
                    <% if(!notallfiles){ %>
                    <div class="alert alert-danger alert-dismissable text-justify" name="alertnotfiles" id="alertnotfiles">
                        <strong>El servidor no cuenta con todos los ficheros seleccionados.</strong>
                    </div>
                    <% }%>
                    
                    
                </div>
                    
                <div class="col-md-7">
                    <div class="form-group">
                        
                        <form 
                            action="process"
                            method="POST">
                            <div name="stepone" id="stepone">
                                <fieldset>
                                    <legend>Opciones de Área</legend>
                                </fieldset>
                                <b>Creación de nueva área: </b>todas las rutas serán asignadas a una nueva área.
                                Los límites serán tomados a partir de las rutas analizadas.<br>
                                <b>Asignación manual: </b> se podrá elegir un área entre las existentes.

                                <select name="areaselect" id="areaselect" class="form-control" onchange="getValOfAreaSelect(this);" required>
                                    <option value="-1"> - - - - </option>
                                    <option value="1">Creación de nueva área</option>
                                    <% if(areas != null && !areas.isEmpty()) { %>
                                    <option value="2">Asignación manual</option>
                                    <% } %>
                                </select>
                                <div id="amenitiesalert" name="amenitiesalert" class="amenitiesalert">
                                    <br>
                                    <div class="alert alert-danger alert-dismissable">
                                        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                        Es posible que no existan Puntos De Interés para este área. Si lo desea, puede
                                        importar PDIs desde un fichero xml <a href="amenitiesmanage" class="alert-link">en la sección correspondiente.</a> 
                                    </div>
                                </div>
                                <div id="nuevaarea" name="nuevaarea" class="nuevaarea">
                                    <input name="areaname" id="areaname" type="text" class="form-control" placeholder="Nombre del nuevo área">
                                    <br>
                                    <textarea name="areadescription" id="areadescription" type="textarea" class="form-control" placeholder="Indica una breve descripción para este área"></textarea>
                                </div>


                                <% if(areas != null && !areas.isEmpty()) { %>
                                    <div id="existingareaselect" name="existingareaselect" class="existingareaselect">
                                        <br>
                                        <select name="existingareas" id="existingareas" class="form-control" onchange="">
                                            <% for(int i = 0; i < areas.size(); i++){ %>
                                                <option value="<%= areas.get(i).getId() %>"><%= areas.get(i).getName() %></option>
                                            <% } %>
                                        </select>
                                    </div>
                                <% } %>

                                <div class="text-center">
                                    <br>
                                    <button name="steponecontinue" id="steponecontinue" type="button" class="btn btn-default">Siguiente</button>
                                </div>
                            </div>

                            <div name="steptwo" id="steptwo">
                                <fieldset>
                                    <legend>Opciones de Distancia</legend>
                                </fieldset>
                                <b>Distancia entre rutas: </b>el valor inferior indica al algoritmo la distancia mínima 
                                a la que separar dos rutas que están distanciadas en el tiempo.<br>
                                <b>Variación de mediana: </b> valor para la variación de la mediana temporal.<br>
                                <input class="form-control" type="number" name="maxdistancevalue" id="maxdistancevalue" min="0.1" max="1000" step="0.1" value="<%= trackdistance == 0 ? 200 : trackdistance %>">
                                <br>
                                <label for="varmedian">Variación de la mediana (x)</label>
                                <input name="varmedian" id="varmedian" class="form-control" type="number" min="0.01" max="10" step="0.01" value="<%= medianvariation == 0 ? 1 : medianvariation %>">
                                <div class="text-center">
                                    <br>
                                    <button name="steptwoback" id="steptwoback" type="button" class="btn btn-default">Anterior</button>
                                    <button name="steptwocontinue" id="steptwocontinue" type="button" class="btn btn-default">Siguiente</button>
                                </div>
                            </div>
                                
                                    
                            <div name="stepthree" id="stepthree">
                                <fieldset>
                                    <legend>Opciones de Paradas</legend>
                                </fieldset>
                                <b>Checkbox: </b>activando el checkbox se buscarán paradas en la ruta.<br>
                                <b>Distancia </b> se trata de la distancia entre puntos de la ruta que indican que se trate de una parada.

                                    <input data-on="Buscar" data-off="No buscar" data-onstyle="success" data-toggle="toggle" type="checkbox" name="findstops" id="findstops" <%= findstops ? "checked" : "" %>>

                                <br>
                                <div class="stops">
                                    <label for="maxstopdistance">Distancia máxima para considerar como parada (metros)</label>
                                    <input name="maxstopdistance" id="maxstopdistance" class="form-control" type="number" min="1" max="2000" step="1" value="<%= findstops ? stopdistance : 100 %>">
                                </div>
                                <div class="text-center">
                                    <br>
                                    <button name="stepthreeback" id="stepthreeback" type="button" class="btn btn-default">Anterior</button>
                                    <button name="stepthreecontinue" id="stepthreecontinue" type="button" class="btn btn-default">Siguiente</button>
                                </div>
                            </div>

                            <div name="stepfour" id="stepfour">
                                <fieldset>
                                    <legend>Opciones de POIs</legend>
                                </fieldset>
                                
                                <div class="hidden alert alert-warning alert-dismissable text-justify" name="alertsearchpoi" id="alertsearchpoi">
                                    <strong>Debe activar la búsqueda de paradas para poder encontrar Puntos De Interés asociados a las mismas.</strong>
                                </div>
                                
                                <input data-on="Buscar POIs" data-off="No buscar POIs" data-onstyle="success" data-toggle="toggle"  type="checkbox" name="findpois" id="findpois"  <%= findpois ? "checked" : "" %>>
                                <br>
                                <div class="pois">
                                    
                                    <label for="radius">Radio máximo de búsqueda de POIs</label>
                                    <input name="radius" id="radio" class="form-control" type="number" min="10" max="500" step="0.5" value="<%= findpois ? maxradius : 50 %>">
                                    <br>
                                    <label for="radiusmultiplier">Multiplicador de radio de punto medio.</label>
                                    <input name="radiusmultiplier" id="radiusmultiplier" class="form-control" type="number" min="1" max="100" step="0.01" value="<%= findpois ? radiusmultiplier : 1 %>">
                                </div>

                                <div class="text-center">
                                    <br>
                                    <button name="stepfourback" id="stepfourback" type="button" class="btn btn-default">Anterior</button>
                                    <button name="stepfourcontinue" id="stepfourcontinue" type="button" class="btn btn-default">Siguiente</button>
                                </div>
                            </div>
                                    
                            <div name="stepfive" id="stepfive">
                                <fieldset>
                                    <legend>Opciones de Almacenamiento</legend>
                                </fieldset>
                                <b>Guardar en BD: </b> Permite guardar de forma permanente los resultados en la Base de Datos. En caso de no seleccionar esta
                                opción, los datos serán perdidos al cerrar el navegador.<br>
                                <input data-on="Guardar" data-off="No guardar" data-onstyle="success" data-toggle="toggle" type="checkbox" name="storedata" id="storedata" <%= storeindb ? "checked" : "" %>>
                                <br>
                                <div class="text-center">
                                    <br>
                                    <button name="stepfiveback" id="stepfiveback" type="button" class="btn btn-default">Anterior</button>
                                    <button class="btn btn-default" name="btnprocessfiles" id="btnprocessfiles" type="submit" class="btn btn-default">Ejecutar</button>
                                </div>
                                <% for(int i=0; i<selectedFiles.length; i++){ %>
                                <input name="files" id="files" type="hidden" value="<%= selectedFiles[i] %>">
                                <% } %>
                                <input name="exprepeat" id="exprepeat" type="hidden" value="false">
                            </div>
                        </form> 
                    </div>
                </div>
            </div>
        </div>
    </div>

        <script src="jquery/rutassemanticas/threads/checkActiveThreadsDataProcessingOptions.js"></script>
        <script src="jquery/rutassemanticas/checkCheckbox.js"></script>
        <script src="jquery/rutassemanticas/manageoptionsform.js"></script>
        <%@include file="../WEB-INF/jspf/footer.jspf" %>
    </body>
</html>
