<%-- 
    Document   : Registro.jsp
    Created on : 22/10/2020, 01:05:14 PM
    Author     : cesit
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.text.*"%>
<%@page import="java.util.*"%>
<%@page import="dao.*"%>
<%@page import="entities.*"%>
<%@page import="ORMJPA.*"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!--Bootstrap-->

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
        <!--Estilos-->
        <link rel="stylesheet" type="text/css" href="styleIndex.css">
        <!--Google font-->
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&family=Rubik:wght@300&display=swap" rel="stylesheet">

        <link rel="shortcut icon" type="image/png" href="favicon.png"/>
    </head>
    <body>
        <%
            if (request.getParameter("accion") != null && request.getParameter("accion").equals("registrouser")) {
                String nombres = request.getParameter("nombres");
                String apellidos = request.getParameter("apellidos");
                String identificacion = request.getParameter("identificacion");
                String fn = request.getParameter("fechaNacimiento");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaNacimiento = sdf.parse(fn);
                String sexo = request.getParameter("sexo");
                String email = request.getParameter("email");
                Integer telefono = Integer.parseInt(request.getParameter("telefono"));
                String usuario = request.getParameter("user");
                String contraseña = request.getParameter("pass");
                String direccion = request.getParameter("direccion");
                /*out.println(nombres);
                out.println(apellidos);
                out.println(identificacion);
                out.println(sexo);
                out.println(email);
                out.println(telefono);
                out.println(usuario);
                out.println(contraseña);
                out.println(direccion);
                 */

                ORMaero ormaero = new ORMaero();
                ormaero.registrarusuario(nombres, apellidos, identificacion, fechaNacimiento, sexo, email, telefono, usuario, contraseña, direccion);

            }
        %>
        <nav>
            <label><a href="Index.jsp">Inicio</a></label>
        </nav>

        <section id="sectRegisto">
            <!--<h2>REGISTRO</h2>-->
            <div>
                <form action="Registro.jsp" method="post" id="formRegistro" autocomplete="off"> 
                    <div class="form-group row">  
                        <div class="col-12">
                            <label><h5>REGISTRO DE USUARIOS</h5></label>  
                        </div>
                    </div>

                    <div class="form-group row">  
                        <div class="col-12">
                            <label class="col-3">Nombres </label>
                            <input class="col-6 input-text" type="text" name="nombres" id="nombre" maxlength="50" required>
                        </div>

                    </div>
                    <div class="form-group row">
                        <div class="col-12">
                            <i class="fas fa-user icon"></i>
                            <label class="col-3">Apellidos</label>
                            <input class="col-6 input-text" type="text" name="apellidos" id="apellido" maxlength="50" required>
                        </div>

                    </div>
                    <div class="form-group row">
                        <div class="col-12">
                            <i class="fas fa-card icon"></i>
                            <label class="col-3">Identificacion</label>
                            <input class="col-6 input-text" type="text" name="identificacion" id="id" maxlength="50" required onkeypress='return event.charCode >= 48 && event.charCode <= 57'>
                        </div>
                    </div> 
                    <div class="form-group row">
                        <div class="col-12">
                            <i class="fas fa-date icon"></i>
                            <label class="col-3">Fecha de nacimiento</label>
                            <input class="col-6 input-text" type="Date" name="fechaNacimiento" id="fechaNac" required>
                        </div>

                    </div> 
                    <div class="form-group row">
                        <div class="col-12">
                            <label class="col-3">Sexo</label>
                            M<input class="col-1" type="radio" value="M" name="sexo" id="sexoM" >
                            F<input class="col-1" type="radio" value="F" name="sexo" id="sexoF" >
                        </div>
                    </div> 
                    <div class="form-group row">
                        <div class="col-12">
                            <i class="fas fa-user icon"></i>
                            <label class="col-3">Direccion</label>
                            <input class="col-6 input-text" type="text" name="direccion" id="direccion" maxlength="50" required>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-12">
                            <label class="col-1">Email</label>
                            <input class="col-8 input-text" type="email" name="email" id="email">
                        </div>
                    </div> 
                    <div class="form-group row">
                        <div class="col-12">
                            <label class="col-3">Telefono</label>
                            <input class="col-6 input-text" type="number" name="telefono" id="telefono">
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-12">
                            <label class="col-3">Usuario </label>
                            <input class="col-6 input-text" type="text" name="user" id="user" maxlength="50" required>
                        </div>

                    </div> 
                    <div class="form-group row">
                        <div class="col-12">
                            <label class="col-3">Contrseña</label>
                            <input class="col-6 input-text" type="password" name="pass" id="pass" maxlength="50" ">
                        </div>

                    </div> 

                    <div class="form-group row">
                        <div class="col-12">
                            <label class="col-3">Confirme contrseña </label>
                            <input class="col-6 input-text" type="password" name="pass" id="pass" maxlength="50" ">
                        </div>
                    </div>
                    <div class="form-group row" style="justify-content: center">
                        <div class="col-12">
                            <label class="col-6">Acepto los terminos y condiciones <input type="checkbox" name="Aceptarterminos" id="Aterminos" required></label>                          
                        </div>                       
                    </div>

                    <div class="form-group row" >
                        <div class="col-6" >
                            <a href="Index.jsp"><input type="button" value="CANCELAR" name="btnCancelar" ></a>
                        </div>
                        <div class="col-6" style="padding-left: 45px;">
                            <input id="accion" name="accion" type="hidden" value="registrouser">
                            <input type="submit" value="REGISTRAR" name="btnRegistrarse" >
                        </div>

                    </div>

                </form>
            </div>
        </section>
        <%@ include file='Footer.html' %>


        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
        <!--<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>-->
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
