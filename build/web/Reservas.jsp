<%-- 
    Document   : Reservas
    Created on : 21/10/2020, 06:41:22 PM
    Author     : cesit
--%>

<%@page import="entities.*"%>
<%@page import="entities.Aerolinea"%>
<%@page import="java.util.List"%>
<%@page import="ORMJPA.ORMaero"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--Bootstrap-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
        <!--Estilos-->
        <link rel="stylesheet" type="text/css" href="styleIndex.css">
        <!--Google font-->
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&family=Rubik:wght@300&display=swap" rel="stylesheet">
        <!--Favicon-->
        <link rel="shortcut icon" type="image/png" href="favicon.png"/>
        <!--Titulo-->
        <title>Reservas</title>
    </head>
    <body>
        <!--<header><h1>AEROPUERTO VANGUARDIA</h1> </header>-->
        <%@ include file='Menu.jsp' %>
        
        <!--
        <nav class="navbar navbar-expand-lg bg-secondary text-uppercase fixed-top" id="mainNav">
            <label><a href="Index.jsp">Inicio</a></label>

            <a href="" id="etiLogin"><%="NOMBRE DEL USUARIO " %><image src="Recursos/usuario.png" alt="sesion" name="imgSesion"></a>            
        </nav>
        -->
        <section>
            
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
                        <%  
                            ORMaero objAero = new ORMaero();

                            List<Reserva> reservas = objAero.obtenerReserva();
                            List<Destino> destinos=objAero.obtenerdestino();
                                
                        %>
                        <%for (int i = 0; i < reservas.size(); i++) {%>                       
                        <tr>
                            <td><%=reservas.get(i).getVuelo().getOrigenList().get(i).getFecha() %></td>
                            <td><%=reservas.get(i).getVuelo().getOrigenList().get(i).getHora() %></td>
                            <td><%=reservas.get(i).getVuelo().getAvion().getAerolinea().getNombre() %></td>
                            <td><%=reservas.get(i).getVuelo().getCodVuelo() %></td>
                            <td><%
                                for(int j=0;j<destinos.size();j++){
                                if(destinos.get(j).getVuelo().getCodVuelo().equals(reservas.get(i).getVuelo().getCodVuelo())){
                                    destinos.get(j).getAeropuerto().getCiudad().getNombre();
                                }
                                        }%></td>
                            <td><a href="ModificarReserva.jsp?"<%="Parametros"%>><input type="submit" value="Modificar" class="btn-reservar"></a></td>
                        </tr>
                        <%}%>
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

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
