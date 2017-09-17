<%-- 
    Document   : index
    Author     : David Moreno del Hoyo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="WEB-INF/jspf/jscss.jspf" %>
        <title>Rutas Semánticas</title>
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
                        <li class="active">
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
                        <li>
                            <a href="manage">Gestión</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        
        <div id="wrap" class="portada">
            <div id="main" class="container">
                <div class="row">
                    <div class="col-md-12">
                        <br><br><br>
                        <h1 class="text-center cabecera-portada">Rutas Semánticas</h1>
                        <h3 class="text-center cabecera-descripcion">Sistema para el registro y análisis de trayectorias semánticas</h3>
                    </div>
                </div>
                <div name="alertthread" id="alertthread" class="hidden">
                    <div class="row">
                        <div class="col-md-12">
                            <hr>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 text-right">
                            <button data-toggle="modal" data-target="#modal_tres" class="btn btn-warning">Ver eventos activos</button>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12">
                            <hr>
                        </div>
                    </div>
                </div>

                <br>
                <div class="row portada">
                    <div class="col-md-12 text-center acercade">
                        <button data-toggle="modal" data-target="#modal_dos" type="button" class="btn btn-lg btn-default">Acerca de</button>
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
        
        
        <!-- modal_dos -->
        <div class="modal fade" id="modal_dos" tabindex="-1" role="dialog" aria-labelledby="modal_dos">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="modal_dos">Acerca de</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-12 text-justify">
                                <p>El presente Trabajo de Fin de Máster aborda el complejo problema de la gestión de 
                                rutas basadas en coordenadas GPS. Cada ruta puede estar 
                                compuesta de un número realmente elevado de coordenadas 
                                lo que puede repercutir en tiempos de procesado elevados.</p>

                                <p>En este trabajo se desarrolla una plataforma web que permite 
                                realizar una limpìeza y etiquetado de las rutas llevadas a cabo 
                                por un usuario concreto, reconociendo las paradas que dicho usuario 
                                realiza durante el transcurso de la ruta y asignando semántica a 
                                dichas paradas. La citada semántica viene dada por la búsqueda de 
                                Puntos De Interés (PDI) cercanos a cada parada detectada.</p>
                                <hr>
                            </div>
                            
                            <div class="col-md-12 text-justify">
                                <strong>Alumno:</strong> David Moreno del Hoyo<br>
                                <strong>Tutores:</strong> Dr. Bruno Baruque Zanón y Dr. Santiago Porras Alfonso
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>
        
        <script src="jquery/rutassemanticas/threads/checkActiveThreadsIndexPage.js"></script>
        <%@include file="WEB-INF/jspf/footer.jspf" %>
    </body>
</html>
