<%-- 
    Document   : ActualizarUsuario
    Created on : 21/10/2020, 09:13:22 PM
    Author     : cesit
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!--Bootstrap-->
        <title>ACTUALIZAR USUARIO</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
        <!--Estilos-->
        <link rel="stylesheet" type="text/css" href="styleIndex.css">
        <!--Google font-->
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&family=Rubik:wght@300&display=swap" rel="stylesheet">

        <link rel="shortcut icon" type="image/png" href="favicon.png"/>
    </head>
    <body>
        <nav>
            <label><a href="Index.jsp">Inicio</a></label>
        </nav>

        <section id="sectRegisto">
            <div>
                <form action="" method="post" id="formRegistro">
                    <div class="form-group row">  
                        <div class="col-12">
                            <label><h5>ACTUALIZAR USUARIO</h5></label>  
                        </div>
                    </div>
                    <div class="form-group row">  
                        <div class="col-12">
                            <label class="col-3">Nombres</label>
                            <input class="col-6 input-text" type="text" name="nombres" id="nombre" maxlength="25" required>
                        </div>
                    </div>
                    <div class="form-group row">  
                        <div class="col-12">
                            <label class="col-3">Apellidos</label>
                            <input class="col-6 input-text" type="text" name="apellidos" id="apellido" maxlength="25" required>
                        </div>
                    </div>
                    <div class="form-group row">  
                        <div class="col-12">
                            <label class="col-3">Identificacion </label>
                            <input class="col-6 input-text" type="text" name="identificacion" id="id" maxlength="20" required="">
                        </div>
                    </div>
                    <div class="form-group row">  
                        <div class="col-12">
                            <label class="col-3">Fecha de nacimiento</label> 
                            <input class="col-6 input-text" type="Date" name="fechaNacimiento" id="fechaNac" required>
                        </div>
                    </div>
                    <div class="form-group row">  
                        <div class="col-12">
                            <label class="col-3">Sexo</label>
                            M<input class="col-3" type="radio" value="M" name="sexo" id="sexoM" >
                            F<input class="col-3" type="radio" value="F" name="sexo" id="sexoF" >
                        </div>
                    </div> <div class="form-group row">  
                        <div class="col-12">
                            <label class="col-3">Email</label>
                            <input class="col-6 input-text" type="email" name="email" id="email">
                        </div>
                    </div>
                    <div class="form-group row">  
                        <div class="col-12">
                            <label class="col-3">Telefono</label>
                            <input class="col-6 input-text" type="text" name="telefono" id="telefono">
                        </div>
                    </div>

                    <div class="form-group row" >  
                            <div class="col-6" >
                                <a href="Index.jsp"><input type="button" value="CANCELAR" onclick="" name="btnRegistrarse"></a>
                            </div>
                            <div class="col-6" style="padding-left: 45px;">
                                <input type="submit" value="ACTUALIZAR" onclick="" name="btnRegistrarse">
                            </div>
                    </div>
                </form>
            </div>
        </section>

        <%@ include file='Footer.html' %>
    </body>
</html>
