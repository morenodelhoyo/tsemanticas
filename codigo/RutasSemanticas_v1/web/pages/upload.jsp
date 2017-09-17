<%-- 
    Document   : upload
    Author     : David Moreno del Hoyo
--%>

<%@page import="java.util.Map"%>
<%@page import="model.data.UploadedFile"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="serverFolderFiles" scope="session" class="File[]" />
<jsp:useBean id="numberOfCsvFiles" scope="session" class="Integer" />
<jsp:useBean id="numberOfNotCsvFiles" scope="session" class="Integer" />
<jsp:useBean id="error" scope="session" class="Boolean" />
<jsp:useBean id="mUF" scope="session" class="Map<String, UploadedFile>" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../WEB-INF/jspf/jscss.jspf" %>
        <link href="css/fileupload/fileUpload.css" rel="stylesheet">
        <link href="css/bootstrap/bootstrap-toggle.min.css" rel="stylesheet">
        <script src="jquery/bootstrap/bootstrap-toggle.min.js"></script>
        <script src="jquery/rutassemanticas/tablesorter.js"></script>        
        <title>Subida de ficheros</title>
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
                    <h1>Gestión de ficheros</h1>
                </div>
            </div>
            <hr>
            
            <% if(numberOfCsvFiles != 0){ %>
                <div class="row">
                    <div class="col-md-12">
                        <div class="alert alert-success alert-dismissable">
                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                            <strong>Información.</strong><br> <strong><%= numberOfCsvFiles %></strong> <% if(numberOfCsvFiles == 1){ %> fichero subido con éxito. <% } else { %> ficheros subidos con éxito. <% } %>
                        </div>
                    </div>
                </div>
            <% } %>
            <% if(error == true){ %>
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
            
            <% if(numberOfNotCsvFiles != 0){ %>
                <div class="row">
                    <div class="col-md-12">
                        <div class="alert alert-danger alert-dismissable">
                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                            <strong>Error.</strong> <%= numberOfNotCsvFiles %> fichero/s no era/n csv.
                        </div>
                    </div>
                </div>
            <% } %>
            
            <div class="row">   
                <div class="col-md-8 uploadFile">
                    <form action="upload"
                          method="POST"
                          enctype="multipart/form-data">
                        <div class="col-md-4 fileUpload btn btn-default uno">
                            <span>Seleccionar fichero</span>
                            <input type="file" class="upload" id="uploadBtn" name="file" accept=".csv, .zip"/>
                        </div>
                        <div class="col-md-4 dos">
                            <input id="uploadFile" title="Se admiten ficheros zip y xml." placeholder="zip, csv" class="form-control" disabled="disabled" />
                        </div>
                        <div class="col-md-4 tres">
                            <button type="submit" class="btn btn-default" id="btnsubmit" disabled>Enviar</button>
                        </div>
                    </form>
                </div>
                
                <div class="col-md-4 text-right">
                    <div id="helpbtn" name="helpbtn">
                        <button type="button" class="btn btn-default" data-toggle="modal" data-target="#modal_tres">Obtener ayuda</button>
                    </div>
                    <div id="helpandthreadbtn" name="helpandthreadbtn" class="hidden">
                        <button type="button" class="btn btn-default" data-toggle="modal" data-target="#modal_tres">Obtener ayuda</button>
                        <button data-toggle="modal" data-target="#modal_cuatro" class="btn btn-warning">Ver eventos activos</button>
                    </div>
                </div>
                
            </div>
            <hr>
            <% if(serverFolderFiles == null || serverFolderFiles.length == 0){ %>
            <div class="alert alert-danger alert-dismissible" role="alert">
                <div class="row">
                    <div class="col-md-12">
                        <div class="text-center">
                            <strong><h3>Sin ficheros</h3></strong>
                            Actualmente no existen ficheros para ser procesados. Puede subir uno mediante el formulario superior.
                        </div>
                    </div>
                </div>
            </div>
            <%  } else { %>                 
            <div class="row btntablaficheros">
                <div class="col-md-12">
                    <form action="dataprocessingoptions"
                          method="POST">
                <div class="tabla">
                    <table class="table">
                        <thead> 
                            <tr>
                                <th class="col-md-1 text-center">Seleccionar</th> 
                                <th class="col-md-2 text-center">Nombre</th> 
                                <th class="col-md-2 text-center">Tamaño (Bytes)</th> 
                                <th class="col-md-2 text-center">Fecha de subida</th> 
                                <th class="col-md-2 text-center">¿Procesado?</th>
                                <th class="col-md-2 text-center">Fecha de procesado</th>
                                <th class="col-md-1 text-center">Eliminar</th> 
                            </tr> 
                        </thead>
                        <div id="files">
                            <tbody>
                                <% for(int i=0; i<serverFolderFiles.length; i++){ %>
                                    <tr>
                                        <td class="text-center margin-chkbox">
                                            <input data-on="Sí" data-off="No" data-onstyle="success" data-toggle="toggle" id="chkbox-<%= i %>" name="chkbox" class="chkbox" type="checkbox" onchange="javascript:check();" value="<%= serverFolderFiles[i].getName()  %>">
                                        </td>
                                        <td class="text-center margin-name">
                                            <%= serverFolderFiles[i].getName() %>
                                        </td>
                                        <td class="text-center margin-length">
                                            <%= serverFolderFiles[i].length() %>
                                        </td>
                                        <td class="text-center margin-upload">
                                            <% if(mUF.get(serverFolderFiles[i].getName()) != null){ %>
                                                <%= mUF.get(serverFolderFiles[i].getName()).getUploadedDate()%>
                                            <% } %>
                                        </td>
                                        <td class="text-center margin-isprocessed">
                                            <% if(mUF.get(serverFolderFiles[i].getName()) != null){ %>
                                                <% if(mUF.get(serverFolderFiles[i].getName()).isIsProcessed() == false){ %>
                                                    No
                                                <% } else { %>
                                                    Sí 
                                                <% } %>
                                            <% } %>
                                        </td>
                                        <td class="text-center margin-processed">
                                            <% if(mUF.get(serverFolderFiles[i].getName()) != null){ %>
                                                <% if(mUF.get(serverFolderFiles[i].getName()).isIsProcessed()){ %>
                                                    <%= mUF.get(serverFolderFiles[i].getName()).getProcessedDate() %>
                                                <% } else { %> - - - - <% } %>
                                            <% } else { %>
                                                - - - -
                                            <% } %>
                                        </td>
                                        <td class="text-center">
                                            <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#modal_uno" data-name-id="<%= serverFolderFiles[i].getName() %>">
                                                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                            </button>
                                        </td>
                                    </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>
                        <hr>
                        <div class="row btntablas">
                            <% if(serverFolderFiles.length < 2){ %>
                                <div class="col-md-4 col-md-offset-4">
                                    <button name="btnprocessfiles" id="btnprocessfiles" type="submit" class="btn btn-default">Procesar el fichero seleccionado</button>
                                </div>
                            <% } else { %>
                                <div class="col-md-4 text-center" name="divbtnselectall" id="divbtnselectall">
                                    <input name="btnselectall" id="btnselectall" type="button" class="btn btn-success" value="Seleccionar todos">
                                </div>
                                <div class="col-md-4 text-center hidden" name="divbtnselectcancel" id="divbtnselectcancel">
                                    <input name="btnselectcancel" id="btnselectcancel" type="button" class="btn btn-warning" value="Cancelar selección">
                                </div>
                                <div class="col-md-4">
                                    <input name="btndeletefiles" id="btndeletefiles" type="button" class="btn btn-danger"value="Eliminar los ficheros seleccionados">
                                </div>
                                <div class="col-md-4 text-center" name="divbtnprocess" id="divbtnprocess">
                                    <button name="btnprocessfiles" id="btnprocessfiles" type="submit" class="btn btn-default">Procesar los ficheros seleccionados</button>
                                </div>
                                <div class="col-md-4 text-center" name="divbtnexpencurso" id="divbtnexpencurso" hidden="true">
                                    <button title="Un prueba se encuentra en curso, espere para poder lanzar uno nuevo." name="btnexpencurso" id="btnexpencurso" type="submit" class="btn btn-warning" disabled="true">Experimento en curso</button>
                                </div>
                            <% } %>
                        </div>
                    </div>
                    <input type="hidden" name="exprepeat" value="false">
                    </form>
                    <% } %>
                </div>               
                
            <!-- modal_tres (ayuda) -->
            <div class="modal fade" id="modal_tres" tabindex="-1" role="dialog" aria-labelledby="modal_tres">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="modal_tres">Ayuda</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-10 col-md-offset-1 text-center">
                                    <strong>Subida, eliminación y procesado de ficheros</strong>
                                    <hr>
                                    <div class="text-justify">
                                        Este es el primer paso para ejecutar el algoritmo de Rutas Semánticas. El primer paso que ha de realizar es 
                                        subir al servidor los ficheros de datos que desee analizar. Los ficheros admitidos son los siguientes:
                                        <ul>
                                            <li><b>CSV</b>: ficheros con extensión csv.</li>
                                            <li><b>ZIP</b>: ficheros comprimidos de extensión ZIP, en cuyo interior se encuentren ficheros CSV.
                                        </ul>
                                        Cualquier otro formato no será admitido en la plataforma web y no será subido al servidor.
                                        <hr>
                                        Una vez subidos los ficheros deseados, puede ver el estado de los mismos en la tabla superior. Dicha tabla cuenta con las 
                                        siguientes columnas:
                                        <ul>
                                            <li><b>Selección</b>: checkbox que permite seleccionar el fichero para ser procesado.</li>
                                            <li><b>Nombre</b>: nombre* con el que se ha subido el fichero a la plataforma.</li>
                                            <li><b>Tamaño (Bytes)</b>: tamaño que ocupa el fichero medido en Bytes.</li>
                                            <li><b>Fecha de subida</b>: fecha en la que el fichero ha sido subido a la plataforma.</li>
                                            <li><b>¿Procesado?</b>: indica si el fichero ha sido procesado. En caso de que haya sido procesado 
                                                varias veces, se mostrará la última fecha.</li>
                                            <li><b>Fecha de procesado</b>: esta columna muestra la fecha en la que el fichero ha sido procesado por
                                                última vez.</li>
                                            <li><b>Eliminar</b>: en esta columnba se puede hacer uso de botón de papelera para eliminar el fichero
                                                deseado. En caso de querer realizar un borrado múltiple* ha de seleccionar los ficheros deseados
                                                y seguidamente clicar sobre el botón inferior rojo con leyenda "Eliminar los ficheros seleccionados".</li>
                                        </ul>
                                        <b>*nombre</b>: no se aceptarán dos ficheros con mismo nombre. El fichero anterior será reemplazado por el nuevo.<br>
                                        <b>*borrado múltiple</b>: en caso de que existan pocos ficheros en el servidor, puede que el borrado múltiple esté deshabilitado.
                                        <hr>
                                        Para continuar con el procesado de los ficheros debe seleccionar los deseados y clicar sobre el botón blanco inferior
                                        con leyenda "Procesar los ficheros seleccionados".
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
        
            <!-- modal_dos -->
            <div class="modal fade" id="modal_dos" tabindex="-1" role="dialog" aria-labelledby="modal_dos">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="modal_dos">Borrado de fichero/s </h4>
                        </div>
                        <div class="modal-body">
                            Si desea elimiar el/los fichero/s seleccionado/s: <b><div id="modal_dos_id" name="modal_dos_name"><!-- Name of file --></div></b>
                            clique en eliminar. En caso contrario, cierre esta ventana.
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                            <button type="button" class="btn btn-danger" id="modal_dos_accept">Eliminar</button>
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
                            <h4 class="modal-title" id="modal_uno">Borrado de fichero </h4>
                        </div>
                        <div class="modal-body">
                            Si desea elimiar el fichero seleccionado: <b><div id="modal_uno_id" name="modal_uno_name"><!-- Name of file --></div></b>
                            clique en eliminar. En caso contrario, cierre esta ventana.
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                            <button type="button" class="btn btn-danger" id="modal_uno_accept">Eliminar</button>
                        </div>
                    </div>
                </div>
            </div>
        
                
        <!-- modal_cuatro (hilos) -->
        <div class="modal fade" id="modal_cuatro" tabindex="-1" role="dialog" aria-labelledby="modal_cuatro">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="modal_cuatro">Ayuda</h4>
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
                                            Actualmente se está procesando un fichero de Puntos de Interés. Cuando el 
                                            proceso termine, el aviso desaparecerá y se mostrará
                                            su información en la sección correspondiente.
                                        </div>
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
            
        </div>

        </div>

        <%@include file="../WEB-INF/jspf/footer.jspf" %>
        <script src="jquery/fileupload/fileUpload.js"></script>
        <script src="jquery/rutassemanticas/checkall.js"></script>
        <script src="jquery/rutassemanticas/delete.js"></script>
        <script src="jquery/rutassemanticas/threads/checkActiveThreadsUploadPage.js"></script>
    </body>
</html>
