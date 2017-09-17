<%-- 
    Document   : alldatadelete
    Author     : David Moreno del Hoyo
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../WEB-INF/jspf/jscss.jspf" %>
        <title>Eliminación completa de datos</title>
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
                <div class="col-md-12">
                    <h1 class="text-center">Eliminación de datos</h1>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-md-12 text-right">
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
            <div class="row">
                <div class="col-md-12">
                    <div class="alert alert-danger alert-dismissible" role="alert">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="text-center">
                                    <strong><h3>Eliminar Puntos De Interés</h3></strong>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-8">
                                Mediante el botón presente a la derecha podrá eliminar todos los datos 
                                referentes a los Puntos De Interés presentes en la Base de Datos.
                            </div>
                            <div class="col-md-2">
                                <button type="button" class="btn btn-danger btn-lg" data-toggle="modal" data-target="#modal_uno" name="deletepois" id="deletepois">Eliminar POIs</button>
                            </div>
                            <div class="col-md-2">
                                <div class="poisdeleted" id="poisdeleted" name="poisdeleted">
                                    Sin ejecutar
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>            
            <div class="row">
                <div class="col-md-12">
                    <div class="alert alert-danger alert-dismissible" role="alert">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="text-center">
                                    <strong><h3>Eliminar contenido de rutas y ficheros</h3></strong>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-8">
                                Mediante el botón presente a la derecha podrá eliminar todo el contenido
                                presente en la Base de Datos referente a las rutas (no se eliminarán los POIs) y 
                                reiniciar sus índices. Ademaś, se eliminarán todos los ficheros subidos al servidor.
                            </div>
                            <div class="col-md-2">
                                <button type="button" class="btn btn-danger btn-lg" data-toggle="modal" data-target="#modal_dos" mame="cleartables" id="cleartables">Limpiar tablas</button>
                            </div>
                            <div class="col-md-2">
                                <div class="tablescleared" name="tablescleared" id="tablescleared">
                                    Sin ejecutar
                                </div>
                            </div>
                        </div>
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
                        <h4 class="modal-title" id="modal_uno">Borrado de Puntos De Interés </h4>
                    </div>
                    <div class="modal-body">
                        
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <strong>Atención: </strong> si una prueba se encuentra en ejecución o la cargade nuevos Puntos De Interés
                            está en curso, dichos procesos serán anulados antes de eliminar los datos disponibles.
                        </div>
                        
                        Si desea eliminar todos los Puntos De Interés referentes al área seleccionada, clique sobre Aceptar.
                        <input type="hidden" id="regionid" name="regionid">
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
                        <h4 class="modal-title" id="modal_dos">Borrado de región </h4>
                    </div>
                    <div class="modal-body">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <strong>Atención: </strong> si una prueba se encuentra en ejecución o la cargade nuevos Puntos De Interés 
                            está en curso, dichos procesos serán anulados antes de eliminar los datos disponibles.
                        </div>
                        Si desea eliminar todos los Puntos De Interés disponibles en el sistema clique sobre Aceptar.
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                        <button type="button" class="btn btn-danger" id="modal_dos_accept">Eliminar</button>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- modal_tres -->
        <div class="modal fade" id="modal_tres" tabindex="-1" role="dialog" aria-labelledby="modal_tres">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="modal_tres">Ayuda (Borrado de datos) </h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-10 col-md-offset-1 text-justify">
                                Esta página permite eliminar los datos del sistema. Existen dos opciones:
                                <ul>
                                    <li><strong>Borrado de Puntos de Interés:</strong></li> esta opción permite eliminar de  la Base de Datos todos 
                                    los Puntos De Interés almacenados en la misma.
                                    <li><strong>Borrado de Rutas y ficheros:</strong></li> esta opción permite eliminar todos los datos 
                                    referentes a las rutas y, además, eliminará los ficheros subidos al servidor.
                                </ul>
                                <hr>
                                <div class="alert alert-danger alert-dismissible" role="alert">
                                    <strong>Atención: </strong> si una prueba se encuentra en ejecución o la cargade nuevos Puntos De Interés 
                                    está en curso, dichos procesos serán anulados antes de eliminar los datos disponibles.
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
                                    <div class="alert alert-danger alert-dismissible" role="alert">
                                        <strong>Atención: </strong><hr> Si la carga de Puntos De Interés se encuentra en ejecución 
                                        dicho proceso será anulado antes de eliminar los datos disponibles.
                                    </div>
                                </div>
                                <div id="experimentproc" name="experimentproc" class="hidden">
                                    <div class="alert alert-warning alert-dismissable">
                                        <div class="text-center">
                                            <strong>Información</strong>
                                            </div>
                                            <hr>
                                            Actualmente se está procesando una prueba. 
                                            Cuando el proceso termine, el aviso desaparecerá y se mostrará
                                            su información en la sección correspondiente.
                                        </div>
                                    </div>
                                    <div class="alert alert-danger alert-dismissible" role="alert">
                                        <div class="text-justify">                                        
                                            <div class="text-center"><strong>Atención</strong></div>
                                            <hr> Si una prueba se encuentra en ejecución dicho proceso 
                                            será anulado antes de eliminar los datos disponibles.
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
        
        <script src="jquery/rutassemanticas/threads/checkActiveThreadsAllDataDeletePage.js"></script>
        <script src="jquery/rutassemanticas/alldatadelete.js"></script>
        <%@include file="../WEB-INF/jspf/footer.jspf" %>
    </body>
</html>
