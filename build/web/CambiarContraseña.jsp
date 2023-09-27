<%-- 
    Document   : CambiarContraseña
    Created on : 22/10/2020, 12:47:39 PM
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
        <title>CAMBIAR CONTRASEÑA</title>
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
                            <label><h5>CAMBIAR CONTRASEÑA</h5></label>  
                        </div>
                    </div>
                    <div class="form-group row">  
                        <div class="col-12">
                            <label class="col-3">Usuario</label>
                            <input class="col-6 input-text" type="text" name="user" id="user" maxlength="50" required>
                        </div>
                    </div>
                    <div class="form-group row">  
                        <div class="col-12">
                            <label class="col-3">Contrseña</label>
                            <input class="col-6 input-text" type="password" name="pass" id="pass" maxlength="100" required pattern="[A-Za-z0-9]">
                        </div>
                    </div>
                    <div class="form-group row">  
                        <div class="col-12">
                            <label class="col-3">Confirme su contrseña</label>    
                            <input class="col-6 input-text"type="password" name="pass" id="pass" maxlength="100" required pattern="[A-Za-z0-9]">
                        </div>
                    </div>
                     <div class="form-group row" style="text-align: center">
                            <div class="col-12">
                                <div class="g-recaptcha" data-sitekey="6Lf4FdoZAAAAACf-CD_W8V2g99H2f3FY_WOchrni"></div>
                            </div>
                        </div>
                    <div class="form-group row">
                        <div class="col-6" >
                            <a href="Index.jsp"> <input type="submit" value="Cancelar" href=""></a>
                        </div>
                        <div class="col-6" style="padding-left: 45px;">
                            <input type="submit" value="Guardar Cambios">
                        </div>
                    </div>
                </form>
                <form action="Reservas.jsp">

                </form>
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
        <!--<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>-->
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.bundle.min.js"></script>
        <script src='https://www.google.com/recaptcha/api.js'></script>
    </body>
</html>
