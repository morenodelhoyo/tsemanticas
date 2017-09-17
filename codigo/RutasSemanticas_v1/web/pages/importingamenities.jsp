<%-- 
    Document   : importingamenities
    Author     : David Moreno del Hoyo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../WEB-INF/jspf/jscss.jspf" %>
        <link rel="stylesheet" type="text/css" href="css/steps/style.css"/>
        <script type="text/javascript" src="jquery/steps/modernizr.js"></script>
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
                <div name="importing" id="importing">
                    <div class="row">
                        <div class="col-md-12">
                            <h1 class="text-center">Importando Puntos de Interés</h1>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert alert-info alert-dismissible text-justify" role="alert">
                                <h3 class="text-center">Información</h3> 
                                <h4>Dependiendo del contenido del fichero (y su tamaño), el proceso de importación
                                    de Puntos de Interés puede alargarse en el tiempo. En cuanto el proceso termine,
                                    será invitado a visualizar la página resumen con la información sobre los datos
                                    importados.
                                </h4>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <hr>
                            <section>
                                <nav>
                                    <ol class="cd-multi-steps text-top">
                                        <li name="readingfile" id="readingfile" class="current"><span>Leyendo ficheros</span></li>
                                        <li name="processingnodes" id="processingnodes"><span>Procesando nodos</span></li>
                                        <li name="processingwayss" id="processingways"><span>Procesando caminos</span></li>
                                        <li name="processended" id="processended"><span>Proceso finalizado</span></li>
                                    </ol>
                                </nav>
                            </section>
                            <hr>
                        </div>
                    </div>
                </div>
                <div class="hidden" name="importingamenitiesended" id="importingamenitiesended">
                    <div class="row">
                        <div class="col-md-12">
                            <h1 class="text-center">Proceso finalizado</h1>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert alert-success alert-dismissible text-justify" role="alert">
                                <h3 class="text-center">Información</h3> 
                                <h4>El proceso de importación de Puntos De Interés ha finalizado con éxito. Puede ver los resultados clicando 
                                    en el siguiente enlace.
                                <hr>
                                <div class="text-center">
                                    <a href="amenitiesmanage" class="btn btn-lg btn-success">Ver Regiones</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                </div>
            </div>
        </div>
        
        
        <script src="jquery/rutassemanticas/importingamenities.js"></script>
        <%@include file="../WEB-INF/jspf/footer.jspf" %>
    </body>
</html>
