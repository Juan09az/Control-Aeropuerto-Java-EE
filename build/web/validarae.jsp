<%
    session = request.getSession();
    if (session.getAttribute("usuario") != null && session.getAttribute("clave") != null && session.getAttribute("tipo") != null) {
        String usuario = (String) session.getAttribute("usuario").toString();
        String clave = (String) session.getAttribute("clave").toString();
        String tipo = (String) session.getAttribute("tipo").toString();
        out.print("<a href='Login.jsp?cerrar=true'><h5>Cerrar Sesión" + usuario + "</h5></a>");
    } else {
        out.print("<script>location.replace('Login.jsp');</script>");
    }

%>
