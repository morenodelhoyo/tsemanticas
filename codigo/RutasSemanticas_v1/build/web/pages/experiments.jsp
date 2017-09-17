<%-- 
    Document   : experiments
    Author     : David Moreno del Hoyo
--%>
<%@page import="model.data.UploadedFile"%>
<%@page import="model.data.Experiment"%>
<%@page import="java.util.ArrayList"%>
<jsp:useBean id="experiments" scope="session" class="ArrayList<Experiment>" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../WEB-INF/jspf/jscss.jspf" %>
        <title>Pruebas realizadas</title>
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
                        <li>
                            <a href="results">Resultados</a>
                        </li>
                        <li class="active">
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
                <div class="col-md-12">
                    <h1 class="text-center">Pruebas realizadas</h1>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-md-12 text-right">
                    <div id="helpbtn" name="helpbtn">
                        <button type="button" class="btn btn-default" data-toggle="modal" data-target="#modal_cinco">Obtener ayuda</button>
                    </div>
                    <div id="helpandthreadbtn" name="helpandthreadbtn" class="hidden">
                        <button type="button" class="btn btn-default" data-toggle="modal" data-target="#modal_cinco">Obtener ayuda</button>
                        <button data-toggle="modal" data-target="#modal_tres" class="btn btn-warning">Ver eventos activos</button>
                    </div>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-md-12">
                <% if(experiments == null || experiments.size() == 0){ %>
                    <div class="alert alert-danger alert-dismissable">
                        <h3 class="text-center">Sin pruebas</h3>
                        <strong>No existen pruebas almacenados en la Base de Datos.</strong>
                        Puede realizar uno desde la sección correspondiente.
                    </div>
                    <% } else { %>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="tabla">
                            <table class="table">
                                <thead> 
                                    <tr> 
                                        <th class="col-md-1 text-center">Ficheros</th>
                                        <th class="col-md-1 text-center">Área</th>
                                        <th class="col-md-1 text-center">Distancia (rutas)</th>
                                        <th class="col-md-1 text-center">¿Búsqueda de paradas?</th>
                                        <th class="col-md-1 text-center">Distancia</th>
                                        <th class="col-md-1 text-center">Variación de Mediana</th>
                                        <th class="col-md-1 text-center">¿Búsqueda de POIs?</th>
                                        <th class="col-md-1 text-center">Radio máximo</th>
                                        <th class="col-md-1 text-center">Multiplicador de radio</th>
                                        <th class="col-md-1 text-center">Almacenado en BD</th>
                                        <th class="col-md-1 text-center">Fecha de al prueba</th>
                                        <th class="col-md-1 text-center">Repetir / Eliminar</th>
                                    </tr> 
                                </thead>
                                <tbody>
                                <% for(int i=0; i<experiments.size(); i++){
                                    Experiment exp = experiments.get(i);
                                %>
                                    <form method="POST"
                                          action="dataprocessingoptions">
                                        <tr>
                                            <td class="text-center">
                                                <% if(exp.getAnalyzedFiles().size() == 0){ %>
                                                     ----
                                                <% } else if(exp.getAnalyzedFiles().size() == 1){ %>
                                                    <%= exp.getAnalyzedFiles().get(0).getName() %>
                                                <% } else { %>
                                                    <button type="button" class="btn btn-default" name="btnfiles" data-toggle="modal" data-target="#modal_uno" data-experiment-id="<%= (int) exp.getIdExperiment() %>">
                                                        Ver todos (<%= exp.getAnalyzedFiles().size() %>)
                                                    </button>
                                                <% } %>
                                                <div id="experiment<%= (int)exp.getIdExperiment() %>" class="experiment">
                                                    <%
                                                    ArrayList<UploadedFile> aux = exp.getAnalyzedFiles();
                                                    for(int j=0; j<aux.size(); j++ ){ %>
                                                    <input type="hidden" name="files[]" value="<%= aux.get(j).getName() %>">
                                                    <% } %>
                                                </div>
                                            </td>
                                            <td class="text-center">
                                                <%= exp.getAreaName() %>
                                            </td>
                                            <td class="text-center">
                                                <%= exp.getTrackDistance() %>
                                                <input type="hidden" name="trackdistance" value="<%= exp.getTrackDistance()%>">
                                            </td>
                                            <td class="text-center">
                                                <% if(exp.isFindStops()){ %>
                                                    Sí
                                                <% } else { %>
                                                    No
                                                <% } %>
                                                <input type="hidden" name="findstops" value="<%= exp.isFindStops() %>">
                                            </td>
                                            <td class="text-center">
                                                <% if(exp.isFindStops()){ %>
                                                    <%= exp.getStopDistance() %>
                                                <% } else { %>
                                                    - - - -
                                                <% } %>
                                                <input type="hidden" name="stopdistance" value="<%= exp.getStopDistance() %>">
                                            </td>
                                            <td class="text-center">
                                                <% if(exp.isFindStops()){ %>
                                                    <%= exp.getMedianVariation() %>
                                                <% } else { %>
                                                    - - - -
                                                <% } %>
                                                <input type="hidden" name="medianvariation" value="<%= exp.getMedianVariation() %>">
                                            </td>
                                            <td class="text-center">
                                                <% if(exp.isFindPois()){ %>
                                                    Sí
                                                <% } else { %>
                                                    No
                                                <% } %>
                                                <input type="hidden" name="findpois" value="<%= exp.isFindPois()%>">
                                            </td>
                                            <td class="text-center">
                                                <% if(exp.isFindPois()){ %>
                                                    <%= exp.getMaxRadius() %>
                                                <% } else { %>
                                                    - - - -
                                                <% } %>
                                                <input type="hidden" name="maxradius" value="<%= exp.getMaxRadius()%>">
                                            </td>
                                            <td class="text-center">
                                                <% if(exp.isFindPois()){ %>
                                                    <%= exp.getRadiusMultiplier() %>
                                                <% } else { %>
                                                    - - - -
                                                <% } %>
                                                <input type="hidden" name="radiusmultiplier" value="<%= exp.getRadiusMultiplier()%>">
                                            </td>
                                            <td class="text-center">
                                                <% if(exp.isStoreInDB()) { %>
                                                    Sí
                                                <% } else { %>
                                                    No
                                                <% } %>
                                                <input type="hidden" name="storeindb" value="<%= exp.isStoreInDB()%>">
                                            </td>
                                            <td class="text-center">
                                                <%= exp.getExperimentDate() %>
                                            </td>
                                            <td class="text-center">
                                                <button type="submit" class="btn btn-success">
                                                    <span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
                                                </button>
                                                <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#modal_dos" data-name-id="<%= exp.getIdExperiment() %>">
                                                    <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                                </button>
                                            </td>
                                            
                                        </tr>
                                        <input type="hidden" name="exprepeat" value="true">
                                    </form>
                                <% } %>
                                </tbody>
                            </table>
                        </div>
                        </div>
                    </div>
                    <div class="row btnborrarpruebas">
                        <div class="col-md-12 text-center">
                            <button name="eliminarexperimentosbtn" id="eliminarexperimentosbtn" type="button" class="btn btn-lg btn-danger">Eliminar todas las pruebas realizadas</button>
                        </div>
                    </div>
                    <% } %>
                </div> 
            </div>
        </div>
    </div>
        
        <!-- modal_cuatro (borrado de experimentos) -->
        <div class="modal fade" id="modal_cuatro" tabindex="-1" role="dialog" aria-labelledby="modal_cuatro">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="modal_cuatro">Borrado de pruebas</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-12">
                                Clicando sobre eliminar, se borrarán todas las pruebas llevados a cabo, incluyendo las rutas generadas.
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <label for="detener">Teclee la palabra "eliminar" para borrar las pruebas.<br>
                                <input class="form-control" placeholder="eliminar" type="text" name="eliminartodasareas" id="eliminartodasareas" autofocus>
                                <div name="palabranocorrecta" id="palabranocorrecta" hidden="true">
                                    <hr>
                                    <div class="alert alert-danger alert-dismissible text-justify" role="alert">
                                        No ha introducido la palabra correcta.
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                        <button type="button" class="btn btn-danger" id="modal_cuatro_accept">Eliminar</button>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- modal_dos (borrado de experimento) -->
        <div class="modal fade" id="modal_dos" tabindex="-1" role="dialog" aria-labelledby="modal_dos">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="modal_dos">Borrado de prueba </h4>
                    </div>
                    <div class="modal-body">
                        Si desea elimiar la prueba eleccionada <b><div id="modal_dos_id" name="modal_dos_name"><!-- Name of file --></div></b>
                        clique en eliminar. En caso contrario, cierre esta ventana.
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                        <button type="button" class="btn btn-danger" id="modal_dos_accept">Eliminar</button>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- modal_uno (ficheros procesados) -->
        <div class="modal fade" id="modal_uno" tabindex="-1" role="dialog" aria-labelledby="modal_uno">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title" id="modal_uno">Ficheros procesados en la prueba </h4>
                    </div>
                    <div class="modal-body">
                        Lista de ficheros procesados en la prueba seleccinada son los siguientes:<br>
                        <b>
                            <div id="modal_uno_id" name="modal_uno_name"><!-- Name of file --></div>
                        </b>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Aceptar</button>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- modal_cinco -->
        <div class="modal fade" id="modal_cinco" tabindex="-1" role="dialog" aria-labelledby="modal_cinco">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="modal_cinco">Ayuda (pruebas)</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-10 col-md-offset-1">
                                <div class="text-justify">
                                    <div class="header text-center">
                                        <h3>Tabla de pruebas</h3>
                                    </div>
                                    En la tabla inferior se muestran las pruebas 
                                    llevados a cabo por un usuario cualquiera del sistema. Como se aprecia en la tabla, 
                                    se pueden ver los distintos valores que fueron usados a la hora de llevar a cabo 
                                    la ejecución de la prueba.
                                    <hr>
                                    <ul>
                                        <li><strong>Ficheros analizados:</strong> indica los ficheros que han sido analizados.</li>
                                        <li><strong>Área:</strong> nombre del área asignada a las rutas analizadas.</li>
                                        <li><strong>Distancia (entre rutas):</strong> es la distancia máxima para dividir una ruta en dos partes.</li>
                                        <li><strong>Variación de meidiana:</strong> valor que multiplica la mediana calculada.</li>
                                        <li><strong>¿Búsqueda de paradas?:</strong> indica si el usuario ha realizado la búsquda de paradas.</li>
                                        <li><strong>Distancia entre paradas:</strong> es el valor que indica al algoritmo una distancia para considerar
                                        que dos puntos forman parte de una parada en ruta.</li>
                                        <li><strong>¿Búsqueda de POIs?:</strong> indica si el usuario ha realizado la búsqueda de POIs.</li>
                                        <li><strong>Radio máximo:</strong> valor que limita la búsqueda de Puntos De Interés en una parada.</li>
                                        <li><strong>Multiplicador de radio:</strong> valor que multiplica al radio medido entre los puntos de una parada.</li>
                                        <li><strong>Almacenado en BD:</strong> indica si los resultados han sido almacenado en la Base de Datos.</li>
                                        <li><strong>Fecha de la prueba:</strong> fecha en la que se ha llevado a cabo la prueba.</li>
                                        <li><strong>Repetir:</strong> botón que permite repetir la prueba.</li>
                                        <li><strong>Eliminar:</strong> botón que permite eliminar la prueba.</li>
                                    </ul>
                                    <hr>
                                    Si se desean eliminar todas las pruebas, se puede hacer desde la parte inferior de la tabla.
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
        
        
        <!-- modal_tres (hilos) -->
        <div class="modal fade" id="modal_tres" tabindex="-1" role="dialog" aria-labelledby="modal_tres">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="modal_tres">Ayuda</h4>
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
                                            su información en la sección correspondiente.
                                        </div>
                                    </div>
                                </div>
                                <div id="experimentproc" name="experimentproc" class="hidden">
                                    <div class="alert alert-warning alert-dismissable">
                                        <div class="text-justify">
                                            <div class="text-center"><strong>Información</strong></div>
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
        
        <%@include file="../WEB-INF/jspf/footer.jspf" %>
        
        <script src="jquery/rutassemanticas/threads/checkActiveThreadsExperimentPage.js"></script>
        <script src="jquery/rutassemanticas/experiments.js"></script>
    </body>
</html>
