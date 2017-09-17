<%-- 
    Document   : processing
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
        <title>Experimento en proceso</title>
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
                <div name="expencurso" id="expencurso">
                    <div class="row">
                        <div class="col-md-12">
                            <h1 class="text-center">Prueba en ejecución</h1>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert alert-info alert-dismissible text-justify" role="alert">
                                <h3 class="text-center">Información</h3> 
                                <h4>Su prueba se encuentra en ejecución en estos momentos. Mientras el algoritmo se encuentre
                                    activo no podrán ser lanzados nuevos pruebas.
                                </h4>
                                <hr>
                                <h4>Dependiendo del tamaño y cantidad de ficheros de rutas, la prueba puede prolongarse
                                    durante un espacio de tiempo elevado. Aunque abandone esta página, la prueba no será detenido.
                                    En caso de permanecer en la presente página, será redirigido a la página de pruebas una vez 
                                    haya finalizado el mismo.
                                </h4>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 col-md-offset-3">
                            <div class="alert alert-danger alert-dismissible text-justify" role="alert">
                                <h3 class="text-center">Atención</h3> 
                                <h4>En caso de desear detener la prueba en ejecución, clique sobre el botón inferior. Será
                                requerida confirmación.</h4>
                                <hr>
                                <h4 class="text-center">
                                    <strong>Atención, todo el progreso será perdido.</strong>
                                </h4>
                            </div>
                            <div class="text-center">
                                <button type="button" class="btn btn-lg btn-danger" data-toggle="modal" data-target="#modal_uno">Cancelar prueba en curso</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="hidden" name="expdetenido" id="expdetenido">
                    <div class="row">
                        <div class="col-md-12">
                            <h1 class="text-center">Experimento detenido</h1>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert alert-warning alert-dismissible text-justify" role="alert">
                                <h3 class="text-center">Atención</h3> 
                                <h4>La prueba ha sido detenida y se procederá a eliminar los datos insertados en la Base de Datos.</h4>
                                <hr>
                                <h4 class="text-center">
                                    <strong>Todo el progreso ha sido perdido.</strong>
                                </h4>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="hidden" name="expfinalizado" id="expfinalizado">
                    <div class="row">
                        <div class="col-md-12">
                            <h1 class="text-center">Prueba finalizada</h1>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert alert-success alert-dismissible text-justify" role="alert">
                                <h3 class="text-center">Información</h3> 
                                <h4 class="text-center">La prueba ha finalizado de forma correcta. Los resultados se encuentran disponibles
                                en el área correspondiente.</h4>
                                <hr>
                                <div class="text-center">
                                    <a href="experiment" class="btn btn-lg btn-success">Ver pruebas realizadas</a>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>

                <div name="steps" id="steps">
                    <div class="row">
                        <div class="col-md-12">
                            <hr>
                            <section>
                                <nav>
                                    <ol class="cd-multi-steps text-top">
                                        <li name="readingfiles" id="readingfiles" class="current"><span>Leyendo ficheros</span></li>
                                        <li name="processingtracks" id="processingtracks"><span>Procesando rutas</span></li>
                                        <li name="processingstops" id="processingstops"><span>Procesando paradas</span></li>
                                        <li name="processingpois" id="processingpois"><span>Procesando POIs</span></li>
                                        <li name="dbsaving" id="dbsaving"><span>Guardando en la BD</span></li>
                                        <li name="processended" id="processended"><span>Proceso finalizado</span></li>
                                    </ol>
                                </nav>
                            </section>
                            <hr>
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
                        <h4 class="modal-title" id="modal_uno">Detención manual de la prueba </h4>
                    </div>
                    <div class="modal-body">
                        En caso de desear detener la prueba teclee en la caja de texto inferior la palabra solicitada y clique
                        sobre "Detener prueba".<br>
                        <label for="detener">Teclee la palabra "detener" para cancelar el prueba.<br>
                        <input class="form-control" placeholder="detener" type="text" name="detener" id="detener" autofocus>
                        <div name="palabranocorrecta" id="palabranocorrecta" hidden="true">
                            <div class="alert alert-danger alert-dismissible text-justify" role="alert">
                                No ha introducido la palabra correcta.
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                        <button type="button" class="btn btn-danger" name="modal_uno_accept" id="modal_uno_accept">Detener prueba</button>
                    </div>
                </div>
            </div>
        </div>
        
        <script src="jquery/rutassemanticas/expCancelModal.js"></script>
        <script src="jquery/rutassemanticas/processingTracksState.js"></script>
        <%@include file="../WEB-INF/jspf/footer.jspf" %>
    </body>
    
</html>
