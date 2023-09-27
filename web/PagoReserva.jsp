<%-- Document : PagoReserva Created on : 21/10/2020, 06:06:21 PM Author : cesit --%><%@page import="entities.Clase"%> <%@page import="java.util.List"%> <%@page import="ORMJPA.ORMaero"%> <%@page contentType="text/html" pageEncoding="UTF-8"%> <!DOCTYPE html><html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8"><link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous"><link rel="stylesheet" type="text/css" href="styleIndex.css"><link rel="icon" type="image/x-icon" href="assets/img/Favicon01.ico" /><title>Pago de reserva</title></head><body> <% double tarifa = 0;
    if (request.getSession().getAttribute("usuario") == null) {
        response.sendRedirect("Login.jsp?accion=pagar");
    }
    String codVuelo = "";
    String clase = "";
    if (request.getParameter("Vuelo") != null) {
        codVuelo = request.getParameter("Vuelo");
        String usuario = request.getSession().getAttribute("usuario").toString();
        clase = request.getParameter("Clase");
        ORMaero objOrm = new ORMaero();
        List<Clase> clases = objOrm.obtenerClases();
        for (int i = 0; i < clases.size(); i++) {
            if (clases.get(i).getNombre().equals(clase)) {
                tarifa = clases.get(i).getTarifa();
            }
        }
        if (request.getParameter("pago") != null) {
            String titular = request.getParameter("NombreTitular");
            String numeroT = request.getParameter("numeroTar");
            String fechaV = request.getParameter("fechaVenci");
            String cvv = request.getParameter("cvv");
        }
    }%><nav class="navbar navbar-expand-lg navbar"> <a class="navbar-brand" href="#">INICIO</a> <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation"> <span class="navbar-toggler-icon"></span> </button><div class="collapse navbar-collapse" id="navbarNav"><ul class="navbar-nav"><li class="nav-item active"><a class="nav-link" href="#">RESERVAS<span class="sr-only">(current)</span></a></li></ul><div class="dropdown"> <% if (session.getAttribute("usuario") == null) { %> <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> MI CUENTA </button><div class="dropdown-menu" aria-labelledby="dropdownMenu2"> <button class="dropdown-item" type="button" ><a href="#">INICIAR SESION</a></button> <button class="dropdown-item" type="button"><a href="#">REGISTRARSE</a></button></div> <%} else {%> <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <%=session.getAttribute("usuario")%> </button><div class="dropdown-menu" aria-labelledby="dropdownMenu2"> <button class="dropdown-item" type="button" href="#">ACTUALIZAR CONTRASEÑA</button> <button class="dropdown-item" type="button" href="#">ACTUALIZAR INFORMACIÓN</button></div> <%}%></div></div> </nav> <section id="sectRegisto"><div><form action="pago.jsp" method="post" id="formPago"><div><div class="form-group row"><div class="col-12"> <label><h5>PAGAR RESERVA</h5></label></div></div><div class="form-group row"><div class="col-12"> <label class="col-4">NOMBRE DEL TITULAR</label> <input class="input-text" type="text" name="NombreTitular" required autocomplete="false"></div></div><div class="form-group row"><div class="col-12"> <label class="col-4">NUMERO</label> <input class="input-text" type="text" name="numero" required></div></div><div class="form-group row"><div class="col-12"> <label class="col-4">FECHA DE VENCIMIENTO</label> <input class="input-text" type="date" name="fechaVenci" required></div></div><div class="form-group row"><div class="col-12"> <label class="col-4">DIGITO DE VERIFICACIÓN</label> <input class="input-text" type="text" name="cvv" required ></div></div><div class="form-group row"><div class="col-12"> <label class="col-4">VALOR A PAGAR</label> <input class="input-text" type="text" name="valor" readonly value="<%=tarifa%>"></div></div><div class="form-group row" style="text-align: center"><div class="col-12"><div class="g-recaptcha" data-sitekey="6Lf4FdoZAAAAACf-CD_W8V2g99H2f3FY_WOchrni"></div></div></div> <input type="hidden" name="cant" value=<%=request.getParameter("Cant")%>> <input type="hidden" name="codVuelo" value=<%=codVuelo%>> <input type="hidden" name="clase" value=<%=clase%>><div class="form-group row" id="div-botones"> <a href="Index.jsp"><input type="button" value="VOLVER"></a> <a href="Pago.jsp"><input type="submit" value="PAGAR" name="pago"></a></div></div></form></div></section></body> <footer class="footer text-center"><div> <label><a href="MapaDelSitio.html">Mapa del sitio</a></label> <label><a href="Descripcion.html">Descripción</a></label> <label>Politica y privacidad</label> <label>Terminos y condiciones</label></div><div class="copyright"> <label>Copyright 2020</label> <label>Todos los derechos resevados</label></div> </footer> <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.bundle.min.js"></script> <script src='https://www.google.com/recaptcha/api.js'></script> <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" /><link href="https://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic" rel="stylesheet" type="text/css" /></html>

<!--VALIDAR RECAPTCHA
$recaptcha = $_POST["g-recaptcha-response"];

    $url = 'https://www.google.com/recaptcha/api/siteverify';
    $data = array(
        'secret' => 'API-SECRET',
        'response' => $recaptcha
    );

    $options = array(
        'http' => array (
            'method' => 'POST',
            'content' => http_build_query($data)
        )
    );

    $context  = stream_context_create($options);
    $verify = file_get_contents($url, false, $context);
    $captcha_success = json_decode($verify);
    
    if ($captcha_success->success) {
        echo 'Se envía el formulario';
    } else {
        echo 'No se envía el formulario';
    }
-->