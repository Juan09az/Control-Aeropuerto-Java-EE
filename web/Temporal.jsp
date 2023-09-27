<%-- 
    Document   : Temporal
    Created on : 22/10/2020, 12:55:12 PM
    Author     : cesit
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%if(request.getParameter("usuario").equals("admin")){
         response.sendRedirect("Administracion.jsp");
        }else{
            response.sendRedirect("Index.jsp");
        }
        %>
    </body>
</html>
