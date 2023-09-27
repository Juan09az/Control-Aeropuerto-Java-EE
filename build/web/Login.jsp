<%-- 
    Document   : Login
    Created on : 22/10/2020, 12:11:04 PM
    Author     : cesit
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dao.*"%>
<%@page import="entities.*"%>
<%@page import="ORMJPA.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="styleLogin.css">
        <link rel="shortcut icon" type="image/png" href="favicon.png"/>
        <title>Inicio de sesión</title>
    </head>

    <body>
        <%
            if (request.getParameter("txtusuario") != null && request.getParameter("txtclave") != null) {
                ORMaero ormaero = new ORMaero();
                String usuario = request.getParameter("txtusuario");
                String clave = request.getParameter("txtclave");
                String tipo;

                out.println(usuario);
                out.println(clave);
                if (ormaero.login(usuario, clave) == 0) {
                    response.sendRedirect("Login.jsp");
                }
                if (ormaero.login(usuario, clave) == 1) {
                    session = request.getSession();
                    session.setAttribute("usuario", usuario);
                    session.setAttribute("clave", clave);
                    response.sendRedirect("MapaDelSitio.html");
                }
                if (ormaero.login(usuario, clave) == 2) {
                    session = request.getSession();
                    session.setAttribute("usuario", usuario);
                    session.setAttribute("clave", clave);
                    tipo = "usuario";
                    session.setAttribute("tipo", tipo);
                    response.sendRedirect("Index.jsp");
                }
                if (ormaero.login(usuario, clave) == 3) {
                    session = request.getSession();
                    session.setAttribute("usuario", usuario);
                    session.setAttribute("clave", clave);
                    tipo = "adminae";
                    session.setAttribute("tipo", tipo);
                    response.sendRedirect("Administracion.jsp");
                }

            }

            if (request.getParameter("cerrar") != null) {
                session.invalidate();
                response.sendRedirect("Login.jsp");

            }
            
        %>
        
        <section id="container">            
            <form action="Login.jsp" method="get" class="login">
                <img src="https://image.flaticon.com/icons/png/512/1770/1770121.png" alt="Login" id="avatar_log">
                <h3> Iniciar Sesión</h3>
                <label>Usuario <input type="text" name="txtusuario" placeholder="Usuario" class="input_login" maxlength="50" required></label>
                <label>Contraseña <input type="password" name="txtclave" placeholder="Contraseña" class="input_login" maxlength="100" required=""></label>
                <input type="Submit" Value="Ingresar">
                <label>¿No tienes una cuenta? <a href="Registro.jsp">Registrate</a></label> 
                 <label> --- Volver a <a href="Index.jsp">Inicio</a></label> 
            </form>

        </section>
    </body>
</html>
