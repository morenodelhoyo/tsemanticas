<%-- 
    Document   : manage
    Author     : David Moreno del Hoyo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../WEB-INF/jspf/jscss.jspf" %>
        <title>Gestión del sistema</title>
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
                    <h1>Área de gestión de Rutas Semánticas</h1>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-md-6">
                    <div class="alert alert-info alert-dismissable text-justify">
                        Clicando en el botón inferior accederá a las opciones de gestión
                        de áreas. Estas opciones incluyen la creación, edición y eliminación de un área.
                        <hr>
                        <div class="btn-area text-center">
                            <a href="areamanage" class="btn btn-default">Gestión de áreas</a>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-6">
                    <div class="alert alert-info alert-dismissable text-justify">
                        Esta sección le permitirá gestionar los Puntos De Interés existentes. Podrá importar
                        nuevos puntos así como eliminar las existentes.
                        <hr>
                        <div class="btn-area text-center">
                            <a href="amenitiesmanage" class="btn btn-default">Gestión de Puntos De Interés</a>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-12">
                    <div class="alert alert-danger alert-dismissable text-justify">
                        Clicando sobre el botón inferior podrá eliminar los datos disponibles en la Base de Datos. Le permitirá
                        eliminar rutas, áreas o los distintos experimentos que hayan sido llevados a cabo.
                        <hr>
                        <div class="btn-area text-center">
                            <a href="alldatadelete" class="btn btn-danger">Borrado de datos</a>
                        </div>
                    </div>
                </div>    
            </div>
        </div>
        </div>
        <%@include file="../WEB-INF/jspf/footer.jspf" %>
    </body>
</html>
