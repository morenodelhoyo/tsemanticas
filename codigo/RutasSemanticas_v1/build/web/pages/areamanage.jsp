<%-- 
    Document   : areamanage
    Author     : David Moreno del Hoyo
--%>

<%@page import="java.text.NumberFormat"%>
<%@page import="model.data.Area"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="listOfAreas" scope="session" class="ArrayList<Area>" />
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
        <title>Gestión de áreas</title>
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
                    <h1>Áreas disponibles</h1>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-md-12 text-right">
                    <div id="helpbtn" name="helpbtn">
                        <button type="button" class="btn btn-default" data-toggle="modal" data-target="#modal_cuatro">Obtener ayuda</button>
                    </div>
                    <div id="helpandthreadbtn" name="helpandthreadbtn" class="hidden">
                        <button type="button" class="btn btn-default" data-toggle="modal" data-target="#modal_cuatro">Obtener ayuda</button>
                        <button data-toggle="modal" data-target="#modal_cinco" class="btn btn-warning">Ver eventos activos</button>
                    </div>
                </div>
            </div>
            <hr>
            
            <div class="row">
                <div class="col-md-12">
                    <% if(listOfAreas.size() > 0){ %>
                    <div class="tabla">
                    <table id="myTable" class="table">
                        <thead> 
                            <tr> 
                                <th class="col-md-1 text-center">Nombre</th> 
                                <th class="col-md-2 text-center">Descripción</th>
                                <th class="col-md-1 text-center">Latitud mínima</th>
                                <th class="col-md-1 text-center">Latitud máxima</th>
                                <th class="col-md-1 text-center">Longitud mínima</th>
                                <th class="col-md-1 text-center">Longitud máxima</th>
                                <th class="col-md-1 text-center">Posición central</th>
                                <th class="col-md-1 text-center">Fecha de creación</th>
                                <th class="col-md-1 text-center">Fecha de modificación</th>
                                <th class="col-md-1 text-center">Editar</th>
                                <th class="col-md-1 text-center">Eliminar</th>
                            </tr> 
                        </thead>
                        <%  for(int i=0; i<listOfAreas.size(); i++){
                            Area eachArea = new Area();
                            eachArea = (Area) listOfAreas.get(i);
                            double newLat = (eachArea.getMaxLat() + eachArea.getMinLat()) / 2;
                            double newLong = (eachArea.getMaxLong() + eachArea.getMinLong()) / 2;
                         %>   
                         <tr>
                             <td class="text-center">
                                <%= eachArea.getName() %>
                             </td>
                             <td class="text-center text-justify">
                                <%= eachArea.getDescription() %>
                             </td>
                             <td class="text-center">
                                 <%= nf.format(eachArea.getMinLat()) %>
                             </td>
                             <td class="text-center">
                                <%= nf.format(eachArea.getMaxLat()) %>
                             </td>
                             <td class="text-center">
                                <%= nf.format(eachArea.getMinLong()) %>
                             </td>
                             <td class="text-center">
                                <%= nf.format(eachArea.getMaxLong()) %>
                             </td>
                             <td class="text-center">
                                (<%= nf.format(newLat) %>; <%= nf.format(newLong) %>)
                             </td>
                             <td class="text-center">
                                <% if(eachArea.getFechaCreacion() == null){ %>
                                    - - - - 
                                <% } else { %>
                                    <%= eachArea.getFechaCreacion() %>
                                <% } %>
                             </td>
                             <td class="text-center">
                                <% if(eachArea.getFechaModificacion() == null){ %>
                                    - - - - 
                                <% } else { %>
                                    <%= eachArea.getFechaCreacion() %>
                                <% } %>
                             </td>
                             <td class="text-center">
                                <%  String disabled;
                                    if(eachArea.getId() == 1){
                                        disabled = "disabled";
                                    } else {
                                        disabled = "";
                                    } %>
                                
                                <button type="button" class="btn btn-default" data-toggle="modal" data-target="#modal_dos"
                                        name="btnedit"
                                        data-id="<%= eachArea.getId() %>"
                                        data-name-id="<%= eachArea.getName() %>"
                                        data-description="<%= eachArea.getDescription() %>"
                                        data-latmin="<%= eachArea.getMinLat() %>"
                                        data-latmax="<%= eachArea.getMaxLat() %>"
                                        data-longmin="<%= eachArea.getMinLong() %>"
                                        data-longmax="<%= eachArea.getMaxLong() %>"
                                        <%= disabled %>>
                                    Editar
                                </button>
                             </td>
                             <td class="text-center">
                                <button <%= disabled %> type="button" class="btn btn-danger" name="btndelete" data-toggle="modal" data-target="#modal_uno" data-name-id="<%= eachArea.getName() %>" data-id="<%= eachArea.getId() %>">Eliminar</button>
                             </td>
                         </tr>
                        <% } %>
                    </table>
                    </div>
                    <% } else{ %>
                    <div class="alert alert-danger alert-dismissible text-justify" role="alert">
                        <div class="row">
                            <div class="col-md-12">
                                <h4 class="text-center">Sin áreas</h4>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <span class="text-justify">Todavía no existen áreas en la Base de Datos. Esto puede deberse a varios motivos:
                                    <ul>
                                        <li>
                                            Todavía no se han creado áreas de forma manual mediante el formulario inferior.
                                        </li>
                                        <li>
                                            Todavía no se han procesado rutas con lo que las áreas no han sido creadas
                                            de forma automática.
                                        </li>
                                    </ul>
                                    Si desea crear una nueva área de forma manual solamente ha de clicar en el botón inferior "Crear un nuevo área" e introducir
                                    los datos requeridos.
                                </span>
                            </div>
                        </div>

                    </div>
                    <% } %>
                </div>    
            </div>
            
            <hr>

            <div class="row">
                <div class="col-md-12 text-center">
                    <button type="button" class="btn btn-default" data-toggle="modal" data-target="#modal_tres">Crear un nuevo área</button>
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
                        <h4 class="modal-title" id="modal_uno">Borrado de área </h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-10 col-md-offset-1">
                                <div class="alert alert-danger alert-dismissible text-justify" role="alert">
                                    <h4 class="text-center">Atención</h4>
                                    <span class="text-justify">Borrando este área:</span>
                                    <strong><div class="text-center" id="modal_uno_id" name="modal_uno_name"><!-- Name of area --></div></strong>
                                    <span>Todas sus rutas serán asignadas al área "Desconocida".</span>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-10 col-md-offset-1 text-center">
                                <label for="detener">Teclee la palabra "eliminar" para cancelar la prueba.<br>
                                <input class="form-control" placeholder="eliminar" type="text" name="eliminararea" id="eliminararea" autofocus>
                                <div name="palabranocorrecta" id="palabranocorrecta" hidden="true">
                                    <div class="alert alert-danger alert-dismissible text-justify" role="alert">
                                        No ha introducido la palabra correcta.
                                    </div>
                                </div>

                                <input type="hidden" name="areaidhidden" id="areaidhidden">

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
                        <h4 class="modal-title" id="modal_dos">Edición de área</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-10 col-md-offset-1">
                                <div class="alert alert-warning alert-dismissible text-justify" role="alert">
                                    <h4 class="text-center">Información</h4>
                                    <span class="text-justify">El contenido actual de los campos es el contenido del área antes de su edicicón.</span>
                                </div>
                            </div>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-10 col-md-offset-1">
                                <div class="nombre">
                                    <label for="nuevonombre">Nuevo nombre </label>
                                    <input type="text" class="form-control" id="nuevonombre" name="nuevonombre" placeholder="Nuevo nombre">
                                </div>
                            </div>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-10 col-md-offset-1">
                                <div class="descripcion">
                                    <label for="nuevadescripcion">Nueva descripción</label>
                                    <textarea type="text" class="form-control" id="nuevadescripcion" name="nuevadescripcion" placeholder="Nueva descripción"></textarea>
                                </div>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-md-4 col-md-offset-4">
                                <label for="nuevalatmax">Latitud máxima</label>
                                <input name="nuevalatmax" id="nuevalatmax" type="number" min="-90" max="90" step="0.000001" value="0" class="form-control">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4 col-md-offset-2">
                                <label for="nuevalongmin">Longitud mínima</label>
                                <input name="nuevalongmin" id="nuevalongmin" type="number" min="-180" max="180" step="0.000001" value="0" class="form-control">
                            </div>
                            <div class="col-md-4">
                                <label for="nuevalongmax">Longitud máxima</label>
                                <input name="nuevalongmax" id="nuevalongmax" type="number" min="-180" max="180" step="0.000001" value="0" class="form-control">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4 col-md-offset-4">
                                <label for="nuevalatmin">Latitud Mínima </label>
                                <input name="nuevalatmin" id="nuevalatmin" type="number" min="-90" max="90" step="0.000001" value="0" class="form-control">
                            </div>
                        </div>
                    </div>
                    <input type="hidden" name="areaid" id="areaid">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                        <button type="button" class="btn btn-success" id="modal_dos_accept">Actualizar</button>
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
                        <h4 class="modal-title" id="modal_tres">Nueva área</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-10 col-md-offset-1">
                                <div class="alert alert-warning alert-dismissible text-justify" role="alert">
                                    <h4 class="text-center">Información</h4>
                                    <div class="text-center">Mediante este formulario se creará una nueva área.</div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-6 col-md-offset-3">
                                <label for="areaname">Nombre</label>
                                <input name="areaname" id="areaname" type="text" class="form-control" placeholder="Nombre del nuevo área" required="true">
                                <br>
                                <label for="areadesc">Descripción</label><textarea name="areadesc" id="areadesc" type="text" class="form-control" placeholder="Descripción del área" required></textarea>
                                <br>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4 col-md-offset-4">

                                <label for="latmax">Latitud máxima</label>
                                <input name="latmax" id="latmax" type="number" min="-90" max="90" step="0.000001" value="0" class="form-control">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4 col-md-offset-2">
                                <label for="longmin">Longitud mínima</label>
                                <input name="longmin" id="longmin" type="number" min="-180" max="180" step="0.000001" value="0" class="form-control">
                            </div>
                            <div class="col-md-4">
                                <label for="longmax">Longitud máxima</label>
                                <input name="longmax" id="longmax" type="number" min="-180" max="180" step="0.000001" value="0" class="form-control">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4 col-md-offset-4">
                                <label for="latmin">Latitud Mínima </label>
                                <input name="latmin" id="latmin" type="number" min="-90" max="90" step="0.000001" value="0" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                        <button type="button" class="btn btn-success" id="createareabtn">Crear área</button>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- modal_cuatro -->
        <div class="modal fade" id="modal_cuatro" tabindex="-1" role="dialog" aria-labelledby="modal_cuatro">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title" id="modal_cuatro">Ayuda (gestión de áreas)</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-10 col-md-offset-1 text-justify">
                                Esta sección permite gestionar las áreas disponibles en la Base de Datos 
                                del sistema. Se permitirá:
                                <ul>
                                    <li><strong>Crear una nueva área:</strong> se podrá crear una nueva área de forma
                                    manual para poder ser asignada a una determinada serie de rutas. Se solicitará un 
                                    <strong>nombre</strong>, una <strong>descripción</strong> y cuatro <strong>coordenadas geográficas</strong>
                                    que la limiten.</li>
                                    <li><strong>Modificar un área existente:</strong> se podrán modificar los valores de una
                                    determinada área con el fin de adaptarla a las necesidades de las rutas actuales o futuras.</li>
                                    <li><strong>Borrar un área disponible:</strong> se permitirá eliminar cualquiera de las áreas
                                    disponibles (menos la llamada "Desconocida"). En dicho momento, las rutas asignadas a dicho
                                    área serán asiganadas al área "Desconocida". Este área siempre existirá pudiendo
                                    asignar rutas a dicho área en caso de no querer crear nuevas áreas.</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Aceptar</button>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- modal_cinco (hilos) -->
        <div class="modal fade" id="modal_cinco" tabindex="-1" role="dialog" aria-labelledby="modal_cinco">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="modal_cinco">Ayuda</h4>
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
                                            Actualmente se está procesando uan prueba. 
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
        
        <script src="jquery/rutassemanticas/threads/checkActiveThreadsAreaManagePage.js"></script>
        <script src="jquery/rutassemanticas/areamanage.js"></script>
        <%@include file="../WEB-INF/jspf/footer.jspf" %>
        
    </body>
</html>
