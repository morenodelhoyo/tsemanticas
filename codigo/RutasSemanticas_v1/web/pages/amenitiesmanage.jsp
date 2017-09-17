<%-- 
    Document   : amenitiesmanage
    Author     : David Moreno del Hoyo
--%>

<%@page import="model.data.Region"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.math.BigDecimal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="regions" scope="session" class="ArrayList<Region>" />
<jsp:useBean id="error" scope="session" class="Boolean" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../WEB-INF/jspf/jscss.jspf" %>
        <link href="css/fileupload/fileUpload.css" rel="stylesheet">
        <link href="css/bootstrap/bootstrap-toggle.min.css" rel="stylesheet">
        <title>Gestión de Puntos de Interés</title>
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
                        <li>
                            <a href="experiment">Pruebas</a>
                        </li>
                        <li class="active">
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
                    <h1>Gestión de Puntos De Interés</h1>
                </div>
            </div>
            <hr>
            
            
            <% if(error){ %>
            <div class="row">
                <div class="col-md-12">
                    <div class="alert alert-danger alert-dismissable">
                        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                        <strong>Error al subir ficheros.</strong><br> Puede que no se hayan recibido los ficheros indicados de forma correcta.
                        Inténtalo de nuevo.
                    </div>
                </div>
            </div>
            <% } %>
            
            <div class="row">   
                <div class="col-md-8 uploadFile">
                    <form action="importamenities"
                          method="POST"
                          enctype="multipart/form-data">
                        
                        <div class="col-md-3 fileUpload btn btn-default uno">
                            <span>Seleccionar fichero</span>
                            <input type="file" class="upload" id="uploadBtn" name="file" id="file" accept=".xml"/>
                        </div>
                        <div class="col-md-3 dos">
                            <input title="Se admiten ficheros de tipo xml" id="uploadFile" placeholder="xml" class="form-control" disabled="disabled" />
                        </div>
                        <div class="col-md-3 dos">
                            <input title="Nombre de la región a la que pertenecen los POIs" placeholder="Región" required type="text" placeholder="Nombre de la región" name="regionname" id="regionname" class="form-control" required>
                        </div>
                        <div class="col-md-2 tres">
                            <button type="submit" class="btn btn-default" id="btnsubmit" disabled>Enviar</button>
                        </div>
                    </form>
                </div>
                
                
                <div class="col-md-4 text-right">
                    <div id="helpbtn" name="helpbtn">
                        <button type="button" class="btn btn-default" data-toggle="modal" data-target="#modal_dos">Obtener ayuda</button>
                    </div>
                    <div id="helpandthreadbtn" name="helpandthreadbtn" class="hidden">
                        <button type="button" class="btn btn-default" data-toggle="modal" data-target="#modal_dos">Obtener ayuda</button>
                        <button data-toggle="modal" data-target="#modal_tres" class="btn btn-warning">Ver eventos activos</button>
                    </div>
                </div>
                
            </div>
            <hr>
            
            <% if(regions.size() == 0){ %>
            
            <div class="alert alert-danger alert-dismissible text-justify" role="alert">
                <div class="row">
                    <div class="col-md-12">
                        <h4 class="text-center">Sin Puntos De Interés</h4>
                        <div class="text-justify">
                            No existen regiones en la Base de Datos. Es posible crear una nueva región mediante el formulario superior. Debe indicar
                            un nombre para dicha región y un fichero con una extensión correcta.
                        </div>
                    </div> 
                </div>
            </div>
            
            <% } else { %>
            <div class="row">
                <div class="col-md-12">
                    <h3 class="text-center">Regiones existentes en la Base de Datos</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="table">
                    <table id="myTable " class="table">
                        <thead> 
                            <tr>
                                <th class="col-md-3 text-center">Región</th> 
                                <th class="col-md-3 text-center">Nº de nodos</th> 
                                <th class="col-md-3 text-center">Nº de caminos</th> 
                                <th class="col-md-3 text-center">Eliminar</th> 
                            </tr> 
                        </thead>
                        <tbody>
                        <% for(Region r : regions){ %>
                            <tr>
                                <td class="col-md-3 text-center">
                                    <%= r.getName() %>
                                </td>
                                <td class="col-md-3 text-center">
                                    <%= r.getNumberOfNodes() %>
                                </td>
                                <td class="col-md-3 text-center">
                                    <%= r.getNumberOfWays() %>
                                </td>
                                <td class="col-md-3 text-center tablaamenitiesbottom">
                                    <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#modal_uno" data-name-id="<%= r.getRegionId() %>" data-name="<%= r.getName() %>">
                                        <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                    </button> 
                                </td>
                            </tr>
                        <% } %>
                        </tbody>
                    </table>
                    </div>
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-6 col-md-offset-4 btnborrarregiones">
                    <button type="button" data-toggle="modal" data-target="#modal_cuatro" class="btn btn-danger" name="deleteallregions" id="deleteallregions">Eliminar todas las regiones</button>
                </div>
            </div>
                        
            <% } %>
        </div>
    </div>
                         
        <!-- modal_uno -->
        <div class="modal fade" id="modal_uno" tabindex="-1" role="dialog" aria-labelledby="modal_uno">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="modal_uno">Borrado de región </h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-10 col-md-offset-1 text-justify">
                                <div class="alert alert-danger alert-dismissible text-justify" role="alert">
                                    <div class="text-justify">
                                        Tenga en cuenta que la carga de nuevos Puntos De Interés puede resultar en un proceso 
                                        extenso en el tiempo.
                                    </div>
                                </div>
                                <hr>
                                Si desea elimiar la región seleccionada: <b><div id="modal_uno_id" name="modal_uno_name"><!-- Name of file --></div></b>
                                clique en eliminar. En caso contrario, cierre esta ventana.
                                <input type="hidden" id="regionid" name="regionid">
                            </div>
                        </div>
                       
                        
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                        <button type="button" class="btn btn-danger" id="modal_uno_accept">Eliminar</button>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- modal_dos -->
        <div class="modal fade" id="modal_dos" tabindex="-1" role="dialog" aria-labelledby="modal_dos">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="modal_dos">Ayuda (Puntos De Interés) </h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-12 text-justify">
                                Esta sección le permite importar nuevos Puntos De Intereś al sistema. Para ello
                                ha de seleccionar un fichero con extensión <strong>xml</strong> (habiendo sido extraído previamente de un
                                fichero osm). Consulta el siguiente punto de la ayuda para aprender a obtener ficheros xml de Puntos De Interés.
                                <hr>
                                <h4 class="text-center">Obtención de POIs desde ficheros osm y osm.pbf</h4>
                                Dependiendo del punto inicial en el que se encuentre, deberá usar una o dos herramientas.<br>
                                
                                <ul>
                                    <li>
                                        <strong>osm.pbf:</strong> en caso de contar con un fichero de extensión <strong>osm.pbf</strong> se deberá hacer 
                                        uso de la herramienta <strong>osmconvert</strong> para obtener un fichero de extensión <strong>osm</strong>.
                                    </li>
                                    <li>
                                        <strong>osm:</strong> en caso de contar con un fichero de extensión <strong>osm</strong> de forma inicial u 
                                        obtenido nediante el proceso anterior, debe usar la herramienta osmfilter para obtener un fichero <strong>xml</strong>
                                        admitido por el sistema de Trayectorias Semánticas.
                                    </li>
                                </ul>
                                Con el fin de facilitar este proceso, se facilitan scripts con una ayuda básica de funcionamiento.
                                <hr>
                                <div class="alert alert-warning alert-dismissible text-justify" role="alert">
                                    <strong>Importante:</strong> Al extraer un fichero de extensión <strong>osm.pbf</strong> puede generarse un fichero osm
                                    de gran tamaño. Aségurese de contar con suficiente espacio en disco. Este proceso puede consumir una elevada
                                    cantidad de tiempo.
                                </div>
                                <hr>
                                En cuanto obtenga un fichero xml, podrá usarlo en el sistema y una vez finalizado el proceso, 
                                los Puntos De Interés serán usados para ser asignados a las paradas detectadas en las rutas analizadas.
                                <hr>
                                <div class="alert alert-warning alert-dismissible text-justify" role="alert">
                                    <strong>Importante:</strong> durante el transcurso de una prueba, no se permitirá la eliminación
                                    de Puntos De Interés ni regiones del sistema.
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
                                        <div class="text-center">
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
        
        <!-- modal_cuatro -->
        <div class="modal fade" id="modal_cuatro" tabindex="-1" role="dialog" aria-labelledby="modal_cuatro">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="modal_cuatro">Borrado de regiones </h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-10 col-md-offset-1 text-justify">
                                <div class="alert alert-danger alert-dismissible text-justify" role="alert">
                                    <div class="text-justify">
                                        Tenga en cuenta que la carga de nuevos Puntos De Interés puede resultar en un proceso 
                                        extenso en el tiempo.
                                    </div>
                                </div>
                                <hr>
                                Si desea elimiar todas las regiones del sistema teclee el texto "<strong>eliminar</strong>" en la entrada
                                inferior y clique en eliminar. En caso contrario, cierre esta ventana.
                                <hr>
                                <label for="deleteallpois">Eliminar todos los POIs</label>
                                <input type="text" class="form-control" name="deleteallpois" id="deleteallpois" placeholder="eliminar">
                                
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
        
        <script src="jquery/fileupload/fileUpload.js"></script>
        <script src="jquery/rutassemanticas/threads/checkActiveThreadsImportAmenitiesPage.js"></script>
        <script src="jquery/rutassemanticas/deleteregion.js"></script>
        <%@include file="../WEB-INF/jspf/footer.jspf" %>
    </body>
</html>
