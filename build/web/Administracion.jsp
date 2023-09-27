<%-- 
    Document   : Aerolinea
    Created on : 21/10/2020, 07:56:27 PM
    Author     : cesit
--%>
<%@page import="java.sql.Time"%>
<%@page import="java.text.*"%>
<%@page import="java.util.*"%>
<%@page import="entities.*"%>
<%@page import="ORMJPA.*"%>
<%@page session="true"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--<script src="Estilos/Plantilla.jsp"></script>-->
        <!--Bootstrap-->

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
        <!--Estilos-->
        <link rel="stylesheet" type="text/css" href="styleIndex.css">
        <!--Google font-->
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&family=Rubik:wght@300&display=swap" rel="stylesheet">
        <!--Favicon-->
        <link rel="shortcut icon" type="image/png" href="favicon.png"/>

        <title>Administrar aerolinea</title>
    </head>

    <body>
        <%
                session = request.getSession();
                request.getSession().setMaxInactiveInterval(5*60);
    if (session.getAttribute("usuario") != null && session.getAttribute("clave") != null && session.getAttribute("tipo") != null) {
        String usuario = (String) session.getAttribute("usuario").toString();
        String clave = (String) session.getAttribute("clave").toString();
        String tipo = (String) session.getAttribute("tipo").toString();
        out.print("<a href='Login.jsp?cerrar=true'><h5>Cerrar Sesión" +" "+ usuario.toUpperCase() + "</h5></a>");
    
            
            if (request.getParameter("accion") != null && request.getParameter("accion").equals("agregaravion")) {
                String matricula = request.getParameter("matricula");
                String modelo = request.getParameter("modelo");
                Integer capacidad = Integer.parseInt(request.getParameter("capacidad"));
                String nombreae = (String) session.getAttribute("usuario").toString();
                /* out.println(matricula);
                out.println(modelo);
                out.println(capacidad);
                out.println(nombreae); */
                ORMaero ormaero = new ORMaero();
                ormaero.agregaravion(matricula, modelo, capacidad, nombreae);
                response.sendRedirect("Administracion.jsp");
            }

            if (request.getParameter("accion") != null && request.getParameter("accion").equals("eliminaravion")) {
                String matricula = request.getParameter("matricula");
                /* out.println(matricula);
                out.println(modelo);
                out.println(capacidad);
                out.println(nombreae); */
                ORMaero ormaero = new ORMaero();
                ormaero.eliminaravion(matricula);
                response.sendRedirect("Administracion.jsp");
            }
        %>
        <!--<header><h1>AEROPUERTO VANGUARDIA</h1> </header>-->

        <nav class="navbar navbar-expand-lg navbar">
            <a class="navbar-brand" href="Administracion.jsp">INICIO</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item active">
                        <a class="nav-link" href="#">AVIONES<span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="#">VUELOS<span class="sr-only">(current)</span></a>
                    </li>
                </ul>
                <div class="dropdown">
                    <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        ADMINISTRACIÓN <% %>
                    </button>
                    <% if (true) { %>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                        <button class="dropdown-item" type="button" ><a href="ActualizarUsuario.jsp">ACTUALIZAR DATOS</a></button>
                        <button class="dropdown-item" type="button"><a href="CambiarContraseña.jsp">CAMBIAR CONTRASEÑA</a></button>
                       <a href="Index.jsp"> <button class="dropdown-item" type="button">SALIR</button></a>
                    </div>
                    <%} else { %>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                        <button class="dropdown-item" type="button">ACTUALIZAR CONTRASEÑA</button>
                        <button class="dropdown-item" type="button">ACTUALIZAR INFORMACIÓN</button>                   
                    </div>
                    <%}%>
                </div>

            </div>
        </nav>

        <section id="sectAviones sectRegisto" >
            <div>
                <form action="Administracion.jsp" method="GET" id="formRegistro" autocomplete="off">

                    <div class="form-group row">  
                        <div class="col-12">
                            <label class="col-3">Matricula</label>
                            <input class="col-6 input-text" type="text" name="matricula" maxlength="50" required>

                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-12">
                            <label class="col-3">Model</label>
                            <input class="col-6 input-text" type="text" name="modelo" maxlength="70" required>                       
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-12">
                            <label class="col-3">Capacidad</label>
                            <input class="col-6 input-text" type="number" name="capacidad" required>

                        </div>
                    </div>
                    <div class="form-group row"  style="justify-content: center; padding-left: 72%">
                        <div class="col-12">
                            <input id="accion" name="accion" type="hidden" value="agregaravion">
                            <input type="submit" value="Agregar">
                        </div>
                    </div>

                </form>
            </div>
            <div class="table-responsive-md">
                <table  class="table">
                    <thead>
                        <tr>
                            <th>Matricula</th>
                            <th>Modelo</th>
                            <th>Capacidad</th>
                            <th>Retirar</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            ORMaero ormaero = new ORMaero();
                            List<Avion> avion = ormaero.obteneraviones();
                            //String usuario = (String) session.getAttribute("usuario").toString();
                            String nitaero = ormaero.compararav(usuario);

                            for (int j = 0; j < avion.size(); j++) {
                                //out.println(avion.get(j).getAerolinea().getNit());
                                if (avion.get(j).getAerolinea().getNit().equals(nitaero)) {
                        %> 

                        <tr>
                            <td><%=avion.get(j).getMatricula()%></td>
                            <td><%=avion.get(j).getModelo()%></td>
                            <td><%=avion.get(j).getCapacidad()%></td>
                            <td><a href="Administracion.jsp?accion=eliminaravion&matricula=<%=avion.get(j).getMatricula()%>"> <img src="https://image.flaticon.com/icons/png/512/61/61848.png" width="23"> </a></td>
                        </tr>
                        <%
                                }
                            }%>
                    </tbody>
                </table>
            </div>
        </section>


        <section id="sectVuelos sectRegisto">
            <div>
                <%
                    Date ahora = new Date();
                    DateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
                    DateFormat formateador2 = new SimpleDateFormat("MMdd");
                    //out.println(formateador.format(ahora));
                    //String fechas=formateador2.format(ahora);
                    DateFormat dateFormat = new SimpleDateFormat("HHmm");
                    DateFormat dateFormat2 = new SimpleDateFormat("HH-mm-ss");
                    Date hora = new Date();
                    //String horas=dateFormat.format(hora);
                    //out.println(dateFormat.format(hora));
                    // out.println(fechas+horas);
                    if (request.getParameter("accion") != null && request.getParameter("accion").equals("agregarvuelo")) {
                    if (request.getParameter("fechaVuelo") != null && request.getParameter("horaVuelo") != null && request.getParameter("puerta") != null && request.getParameter("destino") != null && request.getParameter("avionparavuelo") != null) {
                        ormaero = new ORMaero();
                        String fechaVuelo = request.getParameter("fechaVuelo");
                        String horaVuelo = request.getParameter("horaVuelo") + ":00";
                        String fechas = formateador2.format(ahora);
                        String horas = dateFormat.format(hora);
                        String codVuelo = fechas + horas;
                        String iatadestino = request.getParameter("destino");
                        String matriculaAvion = request.getParameter("avionparavuelo");
                        String puerta = String.valueOf(request.getParameter("puerta"));

                        Date fechaVuelo2 = formateador.parse(fechaVuelo);
                        Time horaVuelo2 = Time.valueOf(request.getParameter("horaVuelo") + ":00");
                        ormaero.crearvuelo(fechaVuelo2, horaVuelo2, codVuelo, iatadestino, matriculaAvion, puerta);
                    response.sendRedirect("Administracion.jsp");
                    }
                    }
                    
                if (request.getParameter("accion") != null && request.getParameter("accion").equals("cancelarvuelo")) {
                    String codvueloed=request.getParameter("codvuelo");
                    String matravion=request.getParameter("avion");
                    String puertaed=request.getParameter("puerta");
                    out.println(codvueloed);
                    out.println(matravion);
                    out.println(puertaed);
                    ormaero = new ORMaero();
                    ormaero.cancelarvuelo(codvueloed, matravion, puertaed);
                    response.sendRedirect("Administracion.jsp");
                }
                %>
                <form action="" method="post" id="formRegistro" autocomplete="off">
                    <div class="form-group row">  
                        <div class="col-12">
                            <label class="col-3">Fecha</label>
                            <input class="col-6 input-text" type="date" name="fechaVuelo" min="<%=formateador.format(ahora)%>" id="fechaVuelo">
                        </div>
                    </div>
                    <div class="form-group row">  
                        <div class="col-12">
                            <label class="col-3">Hora</label>
                            <input class="col-6 input-text" type="time" name="horaVuelo" id="horaVuelo">
                        </div>
                    </div>


                    <div class="form-group row">  
                        <div class="col-12">
                            <label class="col-3">Destino</label>
                            <select name="destino" class="col-6 input-text">
                                <%
                                    ormaero = new ORMaero();
                                    List<Aeropuerto> aeropuerto = ormaero.obteneraeropuertos();
                                    String usuario3 = (String) session.getAttribute("usuario").toString();
                                    String nitaero3 = ormaero.compararav(usuario3);

                                    if (usuario3.equals("avianca")) {
                                        for (int e = 0; e < 1; e++) {

                                %>         
                                <tr>
                                <option name="destino" value="<%=aeropuerto.get(e).getIata()%>"><%=aeropuerto.get(e).getNombre().toUpperCase()%></option>
                                </tr>
                                <%
                                        }
                                    } 
                                %>
                                <%
                                    if (usuario3.equals("easyfly")) {
                                        for (int e1 = 1; e1 < 4; e1++) {

                                %>         
                                <tr>
                                <option value="<%=aeropuerto.get(e1).getIata()%>"><%=aeropuerto.get(e1).getNombre().toUpperCase()%></option>
                                </tr>
                                <%
                                        }
                                    } 
                                %>
                                <%
                                    if (usuario3.equals("satena")) {
                                        for (int e2 = 4; e2 < 8; e2++) {

                                %>         
                                <tr>
                                <option value="<%=aeropuerto.get(e2).getIata()%>"><%=aeropuerto.get(e2).getNombre().toUpperCase()%></option>
                                </tr>
                                <%
                                        }
                                    } 
                                %>
                            </select> 
                        </div>
                    </div>
                    <div class="form-group row">  
                        <div class="col-12">
                            <label class="col-3">Puerta de Embarque</label>
                            <input class="col-6 input-text" type="number" name="puerta" id="fechaVuelo">
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-12">                                
                            <label class="col-3">Avion</label>
                            <select name="avionparavuelo" class="col-6 input-text">  
                                <%
                                    ormaero = new ORMaero();
                                    List<Avion> avion2 = ormaero.obteneraviones();
                                    String usuario2 = (String) session.getAttribute("usuario").toString();
                                    String nitaero2 = ormaero.compararav(usuario2);

                                    for (int k = 0; k < avion2.size(); k++) {
                                        out.println(avion2.get(k).getAerolinea());
                                        if (avion2.get(k).getAerolinea().getNit().equals(nitaero2)) {
                                %>       
                                <tr>
                                <option name="avionparavuelo" value="<%=avion2.get(k).getMatricula()%>"><%=avion2.get(k).getMatricula()%></option>
                                </tr>

                                <%
                                        }
                                    }
                                %>
                            </select> 
                        </div>
                    </div>

                    <div class="form-group row" style="justify-content: center; padding-left: 72%">
                        <div class="col-12">
                            <input id="accion" name="accion" type="hidden" value="agregarvuelo">
                            <input type="submit" value="Agendar">
                        </div>
                    </div>

                </form>
            </div>        
           
               <div class="table-responsive-md">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Fecha</th>
                            <th>Hora programada</th>
                            <th>Aerolinea</th>
                            <th>Vuelo</th>
                            <th>Avion</th>
                            <th>Origen</th>
                            <th>Destino</th>
                            <th>Estado</th>
                            <th>Cancelar</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                        ormaero = new ORMaero();
                        List<Avion>avion4 = ormaero.obteneraviones();
                        List<Vuelo>vuelo4 = ormaero.obtenerVuelosDisponibles();
                        List<Destino>destino4 = ormaero.obtenerdestino();
                        List<Origen>origen4 = ormaero.obtenerorigen();
                        usuario3 = (String) session.getAttribute("usuario").toString();
                        String nitaero4 = ormaero.compararav(usuario3);
                        DateFormat formateador3 = new SimpleDateFormat("yyyy-MM-dd");
                        DateFormat dateFormat3 = new SimpleDateFormat("HH-mm-ss");
                        
                        //out.println(nitaero4);
                        //out.println();
                        //out.println();
                            for (int t = 0; t < vuelo4.size(); t++) {
                                //out.println(vuelo4.get(t).getAvion().getAerolinea().getNombre());
                                /*out.println(avion4.get(t).getMatricula());
                                out.println("------");
                                out.println(vuelo4.get(t).getAvion().getMatricula());*/
                                //out.println(avion4.get(t).getMatricula().equals(vuelo4.get(t).getAvion()));
                                //out.println(avion.get(j).getAerolinea().getNit());vuelo4.get(t).getAvion()
                                if (vuelo4.get(t).getAvion().getAerolinea().getNit().equals(nitaero4) && vuelo4.get(t).getEstado().equals("en curso") ) {
                                     Date fechar=origen4.get(t).getFecha();
                                    Date horar=origen4.get(t).getHora();

                        %>
                        <tr>
                            <td><%=formateador3.format(fechar)%></td>
                            <td><%=dateFormat3.format(horar)%></td>
                            <td><%=vuelo4.get(t).getAvion().getAerolinea().getNombre()%></td>
                            <td><%=vuelo4.get(t).getCodVuelo()%></td>
                            <td><%=vuelo4.get(t).getAvion().getMatricula()%></td>
                            <td><%=origen4.get(t).getAeropuerto().getNombre().toUpperCase()%></td>
                            <td><%=destino4.get(t).getAeropuerto().getNombre().toUpperCase()%></td>
                            <td><%=vuelo4.get(t).getEstado().toUpperCase()%></td>
                            <td><a href="Administracion.jsp?accion=cancelarvuelo&codvuelo=<%=vuelo4.get(t).getCodVuelo()%>&avion=<%=vuelo4.get(t).getAvion().getMatricula()%>&puerta=<%=vuelo4.get(t).getPuertaEmbarque()%>">CANCELAR</a></td>
                            <!--<td><a href="RegistroPasajeros.jsp?"><image src="" alt="reservar" width=""></a></td>        -->                    
                        </tr>
                        <%
                            }
                          }
                        %>
                    </tbody>
                </table>
            </div>
    </section>    
        <footer class="footer text-center">
            <div>
                <label><a href="MapaDelSitio.html">Mapa del sitio</a></label>
                <label><a href="Descripcion.html">Descripción</a></label>
                <label>Politica y privacidad</label>
                <label>Terminos y condiciones</label>
            </div>

            <div class="copyright">
                <label>Copyright 2020</label>
                <label>Todos los derechos resevados</label>
            </div>
        </footer>

<% 
} else {
        out.print("<script>location.replace('Login.jsp');</script>");
    }
%>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <!--<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>-->
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.bundle.min.js"></script>
        <script src="Estilos/js/Script.js"></script>
    </body>
</html>
