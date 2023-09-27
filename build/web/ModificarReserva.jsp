<%-- 
    Document   : ModificarReserva
    Created on : 21/10/2020, 07:22:21 PM
    Author     : cesit
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
        <!--Estilos-->
        <link rel="stylesheet" type="text/css" href="styleIndex.css">
        <!--Google font-->
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&family=Rubik:wght@300&display=swap" rel="stylesheet">

        <title>Aeropuerto Vanguardia</title>
        <link rel="shortcut icon" type="image/png" href="favicon.png"/>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar">
            <a class="navbar-brand" href="Reservas.jsp" >CANCELAR</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">

                <div class="dropdown">
                    <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <% session.getAttribute("usuario"); %>
                    </button>
                    <% if (false) { %>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                        <button class="dropdown-item" type="button" ><a href="Login.jsp">INICIAR SESION</a></button>
                        <button class="dropdown-item" type="button"><a href="Registro.jsp">REGISTRARSE</a></button>                   
                    </div>
                    <%} else { %>
                    <!--<div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                        <button class="dropdown-item" type="button">ACTUALIZAR CONTRASEÑA</button>
                        <button class="dropdown-item" type="button">ACTUALIZAR INFORMACIÓN</button>                   
                    </div>-->
                    <%}%>
                </div>

            </div>
        </nav>
        <!--<header><h1>AEROPUERTO VANGUARDIA</h1> </header>-->


        <section id="sectModificarRe">
            <form name="#" action="" method="post" id="formRegistro" style="padding-top: 20px;">
                <div>
                    <div class="form-group row">  
                        <div class="col-12">
                            <label><h5>MODIFICAR RESERVA</h5></label>  
                        </div>
                    </div>
                    <div class="form-group row">  
                        <div class="col-12">
                            <label class="col-3">Fecha</label>
                            <select class="col-6">
                                <%for (int i = 0; i <= 3; i++) {%>
                                <option value=""><%="Fechas disponibles"%></option>
                                <%}%>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row">  
                        <div class="col-12">
                            <label class="col-3">Hora</label>
                            <select class="col-6">
                                <%for (int i = 0; i <= 3; i++) {%>
                                <option value=""><%="Fechas disponibles"%></option>
                                <%}%>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row">  
                        <div class="col-12">
                            <label class="col-3">Precio de la modificación</label>
                            <input class="col-6" type="text" name="precio" readonly>
                        </div>
                    </div>
                    <div class="form-group row" style="margin-left: 40%;"> 
                        <div class="col-6">
                            <a href="Reservas.jsp"><input type="button" value="CANCELAR" class=""></a>
                        </div>
                        <div class="col-6">
                            <input type="button" value="PAGAR" class="">
                        </div>

                    </div>
                </div>
            </form>
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
        <!--<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>-->
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
