<%-- 
    Document   : Index
    Created on : 20/10/2020, 11:48:04 PM
    Author     : cesit
--%>


<%@page import="java.sql.Time"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="ORMJPA.*"%>
<%@page import="entities.*"%>
<%@page import="java.util.*"%>
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

        <title>Aeropuerto Vanguardia</title>
        <link rel="shortcut icon" type="image/png" href="favicon.png"/>

    </head>
    <body>
        <%
            List<Destino> destino = null;
            String aero = "";
            ORMaero objOrm;
            
        %>
        <!--<header><h1>AEROPUERTO VANGUARDIA</h1> </header>-->
        <%@ include file='Menu.jsp' %>


        <section class="seccion">
            <a class="navbar-brand etiqueta">BUSCAR VUELO</a>
            <form name="filtros" method="post" action="Index.jsp">
                <div class="form-row">
                    <div class="form-group col-md-3">
                        <label>Aerolinea</label>
                        <div class="col-2">
                            <select name="Filtroaerolinea" value="AEROLINEA">
                                <%
                                    ORMaero ormaero = new ORMaero();
                                    List<Aerolinea> aerolinea = ormaero.obtenerAerolienas();
                                %>
                                <%for (int j = 0; j < aerolinea.size(); j++) {%> 
                                <tr>
                                <option value="<%=aerolinea.get(j).getNit()%>"><%=aerolinea.get(j).getNombre()%></option>
                                </tr>
                                <%}%>

                            </select>
                        </div>
                        <div class="col-2" >   
                            <input type="submit" name="filtAero" value="FILTRAR" style=" margin-top: 10px;">
                        </div>
                    </div>
                    <div class="form-group col-md-3">
                        <label>N° de pasajeros</label>
                        <select name="nPasajeros">
                            
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                        </select>
                    </div>

                    <div class="form-group col-md-3">
                        <label>Destino</label>
                        <select name="FiltroDestino" value="DESTINO">
                            <option value="">Seleccione una opcion</option>
                            <%

                                if (request.getParameter("filtAero") != null) {
                                    objOrm = new ORMaero();
                                    aero = request.getParameter("Filtroaerolinea");
                                    destino = objOrm.obtenerdestino();

                                    if (request.getParameter("filtAero") != null) {
                                        for (int i = 0; i < destino.size(); i++) {
                                            if (destino.get(i).getVuelo().getAvion().getAerolinea().getNit().equals(aero) && destino.get(i).getVuelo().getEstado().equals("en curso")) {

                            %>
                            <option value=<%=destino.get(i).getAeropuerto().getIata()%>> <%=destino.get(i).getAeropuerto().getCiudad().getNombre().toUpperCase()%> </option>
                            <%     }
                                        }
                                    }
                                }%>

                        </select>

                    </div>
                    <div class="form-group col-md-3">
                        <label>Clase</label>
                        <select name="clase">
                            <option value="economica">ECONOMICA</option>
                            <option value="primera">PRIMERA</option>
                            <option value="ejecutiva">EJECUTIVA</option>
                        </select>
                    </div>

                    <!--<div class="form-group col-md-2">
                        <label>Fecha</label>
                        <select name="FiltroFecha" value="FECHA">
                            <option value="">Seleccione una opcion</option>
                    <%
                        if (request.getParameter("filtAero") != null) {

                            for (int i = 0; i < destino.size(); i++) {
                                if (destino.get(i).getVuelo().getAvion().getAerolinea().getNit().equals(aero)) {

                    %>
                    <option value=<%=destino.get(i).getFecha()%>><%=destino.get(i).getFecha()%></option>
                    <%  }
                            }
                        }

                    %>
                </select>
            </div>-->

                </div>
                <div class="form-row">
                    <div class="col-12">
                        <input type="submit" name="buscarVuelos" value="BUSCAR">

                    </div>
                </div>

            </form>

        </section>
        <section>
            <div class="table-responsive-md">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Fecha</th>
                            <th>Hora programada</th>
                            <th>Aerolinea</th>
                            <th>Vuelo</th>
                            <th>Destino</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <%                            if (request.getParameter("buscarVuelos") != null) {
                                objOrm = new ORMaero();
                                List<Destino> destinosDis = objOrm.obtenerdestino();
                                List<Origen> origenv = objOrm.obtenerorigen();
                                 List<Vuelo> vuelov = objOrm.obtenerVuelosDisponibles();
                                String aeroli = request.getParameter("Filtroaerolinea");
                                String dest = request.getParameter("FiltroDestino");
                                String clase=request.getParameter("clase");
                                DateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
                                DateFormat dateFormat2 = new SimpleDateFormat("HH-mm-ss");
                        %>
                        <%for (int i = 0; i < vuelov.size(); i++) {
                                if (vuelov.get(i).getAvion().getAerolinea().getNit().equals(aeroli) && destinosDis.get(i).getAeropuerto().getIata().equals(dest)) {
                                    Date fechar=origenv.get(i).getFecha();
                                    Date horar=origenv.get(i).getHora();
                        %> 
                        <tr>
                            <td><%=formateador.format(fechar)%> </td>
                            <td><%=dateFormat2.format(horar)%></td>
                            <td><%=destinosDis.get(i).getVuelo().getAvion().getAerolinea().getNombre().toUpperCase()%></td>
                            <td><%=destinosDis.get(i).getVuelo().getCodVuelo()%></td>
                            <td><%=destinosDis.get(i).getAeropuerto().getCiudad().getNombre().toUpperCase()%></td>
                            <td><a href="PagoReserva.jsp?Cant=<%=request.getParameter("nPasajeros")%>&Clase=<%=clase%>&Vuelo=<%=destinosDis.get(i).getVuelo().getCodVuelo()%>"> Reservar</a></td>
                        </tr>
                        <% }
                                }
                            }%>
                    </tbody> 
                </table>
            </div>


        </section>

        <%@ include file='Footer.html' %>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
