package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.Time;
import java.text.*;
import java.util.*;
import entities.*;
import ORMJPA.*;

public final class Administracion_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("\n");
      out.write("        <!--<script src=\"Estilos/Plantilla.jsp\"></script>-->\n");
      out.write("        <!--Bootstrap-->\n");
      out.write("\n");
      out.write("        <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">\n");
      out.write("        <!--Estilos-->\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"styleIndex.css\">\n");
      out.write("        <!--Google font-->\n");
      out.write("        <link href=\"https://fonts.googleapis.com/css2?family=Roboto:wght@300&family=Rubik:wght@300&display=swap\" rel=\"stylesheet\">\n");
      out.write("        <!--Favicon-->\n");
      out.write("        <link rel=\"shortcut icon\" type=\"image/png\" href=\"favicon.png\"/>\n");
      out.write("\n");
      out.write("        <title>Administrar aerolinea</title>\n");
      out.write("    </head>\n");
      out.write("\n");
      out.write("    <body>\n");
      out.write("        ");

                session = request.getSession();
                request.getSession().setMaxInactiveInterval(5*60);
    if (session.getAttribute("usuario") != null && session.getAttribute("clave") != null && session.getAttribute("tipo") != null) {
        String usuario = (String) session.getAttribute("usuario").toString();
        String clave = (String) session.getAttribute("clave").toString();
        String tipo = (String) session.getAttribute("tipo").toString();
        out.print("<a href='Login.jsp?cerrar=true'><h5>Cerrar Sesión" +" "+ usuario + "</h5></a>");
    
            
            if (request.getParameter("accion") != null && request.getParameter("accion").equals("agregaravion")) {
                String matricula = request.getParameter("matricula");
                String modelo = request.getParameter("modelo");
                Integer capacidad = Integer.parseInt(request.getParameter("capacidad"));
                String nombreae = (String) session.getAttribute("usuario").toString();
                /* out.println(matricula);
                out.println(modelo);
                out.println(capacidad);
                out.println(nombreae); */
                ORMaero ormaero = new ORMaero();
                ormaero.agregaravion(matricula, modelo, capacidad, nombreae);
                response.sendRedirect("Administracion.jsp");
            }

            if (request.getParameter("accion") != null && request.getParameter("accion").equals("eliminaravion")) {
                String matricula = request.getParameter("matricula");
                /* out.println(matricula);
                out.println(modelo);
                out.println(capacidad);
                out.println(nombreae); */
                ORMaero ormaero = new ORMaero();
                ormaero.eliminaravion(matricula);
                response.sendRedirect("Administracion.jsp");
            }
        
      out.write("\n");
      out.write("        <!--<header><h1>AEROPUERTO VANGUARDIA</h1> </header>-->\n");
      out.write("\n");
      out.write("        <nav class=\"navbar navbar-expand-lg navbar\">\n");
      out.write("            <a class=\"navbar-brand\" href=\"Administracion.jsp\">INICIO</a>\n");
      out.write("            <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarNav\" aria-controls=\"navbarNav\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n");
      out.write("                <span class=\"navbar-toggler-icon\"></span>\n");
      out.write("            </button>\n");
      out.write("            <div class=\"collapse navbar-collapse\" id=\"navbarNav\">\n");
      out.write("                <ul class=\"navbar-nav\">\n");
      out.write("                    <li class=\"nav-item active\">\n");
      out.write("                        <a class=\"nav-link\" href=\"#\">AVIONES<span class=\"sr-only\">(current)</span></a>\n");
      out.write("                    </li>\n");
      out.write("                    <li class=\"nav-item active\">\n");
      out.write("                        <a class=\"nav-link\" href=\"#\">VUELOS<span class=\"sr-only\">(current)</span></a>\n");
      out.write("                    </li>\n");
      out.write("                </ul>\n");
      out.write("                <div class=\"dropdown\">\n");
      out.write("                    <button type=\"button\" class=\"btn btn-secondary dropdown-toggle\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n");
      out.write("                        ADMINISTRACIÓN ");
 
      out.write("\n");
      out.write("                    </button>\n");
      out.write("                    ");
 if (true) { 
      out.write("\n");
      out.write("                    <div class=\"dropdown-menu\" aria-labelledby=\"dropdownMenu2\">\n");
      out.write("                        <button class=\"dropdown-item\" type=\"button\" ><a href=\"ActualizarUsuario.jsp\">ACTUALIZAR DATOS</a></button>\n");
      out.write("                        <button class=\"dropdown-item\" type=\"button\"><a href=\"CambiarContraseña.jsp\">CAMBIAR CONTRASEÑA</a></button>\n");
      out.write("                       <a href=\"Index.jsp\"> <button class=\"dropdown-item\" type=\"button\">SALIR</button></a>\n");
      out.write("                    </div>\n");
      out.write("                    ");
} else { 
      out.write("\n");
      out.write("                    <div class=\"dropdown-menu\" aria-labelledby=\"dropdownMenu2\">\n");
      out.write("                        <button class=\"dropdown-item\" type=\"button\">ACTUALIZAR CONTRASEÑA</button>\n");
      out.write("                        <button class=\"dropdown-item\" type=\"button\">ACTUALIZAR INFORMACIÓN</button>                   \n");
      out.write("                    </div>\n");
      out.write("                    ");
}
      out.write("\n");
      out.write("                </div>\n");
      out.write("\n");
      out.write("            </div>\n");
      out.write("        </nav>\n");
      out.write("\n");
      out.write("        <section id=\"sectAviones sectRegisto\" >\n");
      out.write("            <div>\n");
      out.write("                <form action=\"Administracion.jsp\" method=\"GET\" id=\"formRegistro\" autocomplete=\"off\">\n");
      out.write("\n");
      out.write("                    <div class=\"form-group row\">  \n");
      out.write("                        <div class=\"col-12\">\n");
      out.write("                            <label class=\"col-3\">Matricula</label>\n");
      out.write("                            <input class=\"col-6 input-text\" type=\"text\" name=\"matricula\" maxlength=\"50\" required>\n");
      out.write("\n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"form-group row\">\n");
      out.write("                        <div class=\"col-12\">\n");
      out.write("                            <label class=\"col-3\">Model</label>\n");
      out.write("                            <input class=\"col-6 input-text\" type=\"text\" name=\"modelo\" maxlength=\"70\" required>                       \n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"form-group row\">\n");
      out.write("                        <div class=\"col-12\">\n");
      out.write("                            <label class=\"col-3\">Capacidad</label>\n");
      out.write("                            <input class=\"col-6 input-text\" type=\"number\" name=\"capacidad\" required>\n");
      out.write("\n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"form-group row\"  style=\"justify-content: center; padding-left: 72%\">\n");
      out.write("                        <div class=\"col-12\">\n");
      out.write("                            <input id=\"accion\" name=\"accion\" type=\"hidden\" value=\"agregaravion\">\n");
      out.write("                            <input type=\"submit\" value=\"Agregar\">\n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("\n");
      out.write("                </form>\n");
      out.write("            </div>\n");
      out.write("            <div class=\"table-responsive-md\">\n");
      out.write("                <table  class=\"table\">\n");
      out.write("                    <thead>\n");
      out.write("                        <tr>\n");
      out.write("                            <th>Matricula</th>\n");
      out.write("                            <th>Modelo</th>\n");
      out.write("                            <th>Capacidad</th>\n");
      out.write("                            <th>Retirar</th>\n");
      out.write("                        </tr>\n");
      out.write("                    </thead>\n");
      out.write("                    <tbody>\n");
      out.write("                        ");

                            ORMaero ormaero = new ORMaero();
                            List<Avion> avion = ormaero.obteneraviones();
                            //String usuario = (String) session.getAttribute("usuario").toString();
                            String nitaero = ormaero.compararav(usuario);

                            for (int j = 0; j < avion.size(); j++) {
                                //out.println(avion.get(j).getAerolinea().getNit());
                                if (avion.get(j).getAerolinea().getNit().equals(nitaero)) {
                        
      out.write(" \n");
      out.write("\n");
      out.write("                        <tr>\n");
      out.write("                            <td>");
      out.print(avion.get(j).getMatricula());
      out.write("</td>\n");
      out.write("                            <td>");
      out.print(avion.get(j).getModelo());
      out.write("</td>\n");
      out.write("                            <td>");
      out.print(avion.get(j).getCapacidad());
      out.write("</td>\n");
      out.write("                            <td><a href=\"Administracion.jsp?accion=eliminaravion&matricula=");
      out.print(avion.get(j).getMatricula());
      out.write("\"> <img src=\"https://image.flaticon.com/icons/png/512/61/61848.png\" width=\"23\"> </a></td>\n");
      out.write("                        </tr>\n");
      out.write("                        ");

                                }
                            }
      out.write("\n");
      out.write("                    </tbody>\n");
      out.write("                </table>\n");
      out.write("            </div>\n");
      out.write("        </section>\n");
      out.write("\n");
      out.write("\n");
      out.write("        <section id=\"sectVuelos sectRegisto\">\n");
      out.write("            <div>\n");
      out.write("                ");

                    Date ahora = new Date();
                    DateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
                    DateFormat formateador2 = new SimpleDateFormat("MMdd");
                    //out.println(formateador.format(ahora));
                    //String fechas=formateador2.format(ahora);
                    DateFormat dateFormat = new SimpleDateFormat("HHmm");
                    DateFormat dateFormat2 = new SimpleDateFormat("HH-mm-ss");
                    Date hora = new Date();
                    //String horas=dateFormat.format(hora);
                    //out.println(dateFormat.format(hora));
                    // out.println(fechas+horas);
                    if (request.getParameter("accion") != null && request.getParameter("accion").equals("agregarvuelo")) {
                    if (request.getParameter("fechaVuelo") != null && request.getParameter("horaVuelo") != null && request.getParameter("puerta") != null && request.getParameter("destino") != null && request.getParameter("avionparavuelo") != null) {
                        ormaero = new ORMaero();
                        String fechaVuelo = request.getParameter("fechaVuelo");
                        String horaVuelo = request.getParameter("horaVuelo") + ":00";
                        String fechas = formateador2.format(ahora);
                        String horas = dateFormat.format(hora);
                        String codVuelo = fechas + horas;
                        String iatadestino = request.getParameter("destino");
                        String matriculaAvion = request.getParameter("avionparavuelo");
                        String puerta = String.valueOf(request.getParameter("puerta"));

                        Date fechaVuelo2 = formateador.parse(fechaVuelo);
                        Time horaVuelo2 = Time.valueOf(request.getParameter("horaVuelo") + ":00");
                        ormaero.crearvuelo(fechaVuelo2, horaVuelo2, codVuelo, iatadestino, matriculaAvion, puerta);
                    response.sendRedirect("Administracion.jsp");
                    }
                    }
                    
                if (request.getParameter("accion") != null && request.getParameter("accion").equals("cancelarvuelo")) {
                    String codvueloed=request.getParameter("codvuelo");
                    String matravion=request.getParameter("avion");
                    String puertaed=request.getParameter("puerta");
                    out.println(codvueloed);
                    out.println(matravion);
                    out.println(puertaed);
                    ormaero = new ORMaero();
                    ormaero.cancelarvuelo(codvueloed, matravion, puertaed);
                    response.sendRedirect("Administracion.jsp");
                }
                
      out.write("\n");
      out.write("                <form action=\"\" method=\"post\" id=\"formRegistro\" autocomplete=\"off\">\n");
      out.write("                    <div class=\"form-group row\">  \n");
      out.write("                        <div class=\"col-12\">\n");
      out.write("                            <label class=\"col-3\">Fecha</label>\n");
      out.write("                            <input class=\"col-6 input-text\" type=\"date\" name=\"fechaVuelo\" min=\"");
      out.print(formateador.format(ahora));
      out.write("\" id=\"fechaVuelo\">\n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"form-group row\">  \n");
      out.write("                        <div class=\"col-12\">\n");
      out.write("                            <label class=\"col-3\">Hora</label>\n");
      out.write("                            <input class=\"col-6 input-text\" type=\"time\" name=\"horaVuelo\" id=\"horaVuelo\">\n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("\n");
      out.write("\n");
      out.write("                    <div class=\"form-group row\">  \n");
      out.write("                        <div class=\"col-12\">\n");
      out.write("                            <label class=\"col-3\">Destino</label>\n");
      out.write("                            <select name=\"destino\" class=\"col-6 input-text\">\n");
      out.write("                                ");

                                    ormaero = new ORMaero();
                                    List<Aeropuerto> aeropuerto = ormaero.obteneraeropuertos();
                                    String usuario3 = (String) session.getAttribute("usuario").toString();
                                    String nitaero3 = ormaero.compararav(usuario3);

                                    if (usuario3.equals("avianca")) {
                                        for (int e = 0; e < 1; e++) {

                                
      out.write("         \n");
      out.write("                                <tr>\n");
      out.write("                                <option name=\"destino\" value=\"");
      out.print(aeropuerto.get(e).getIata());
      out.write('"');
      out.write('>');
      out.print(aeropuerto.get(e).getNombre().toUpperCase());
      out.write("</option>\n");
      out.write("                                </tr>\n");
      out.write("                                ");

                                        }
                                    } 
                                
      out.write("\n");
      out.write("                                ");

                                    if (usuario3.equals("easyfly")) {
                                        for (int e1 = 1; e1 < 4; e1++) {

                                
      out.write("         \n");
      out.write("                                <tr>\n");
      out.write("                                <option value=\"");
      out.print(aeropuerto.get(e1).getIata());
      out.write('"');
      out.write('>');
      out.print(aeropuerto.get(e1).getNombre().toUpperCase());
      out.write("</option>\n");
      out.write("                                </tr>\n");
      out.write("                                ");

                                        }
                                    } 
                                
      out.write("\n");
      out.write("                                ");

                                    if (usuario3.equals("satena")) {
                                        for (int e2 = 4; e2 < 8; e2++) {

                                
      out.write("         \n");
      out.write("                                <tr>\n");
      out.write("                                <option value=\"");
      out.print(aeropuerto.get(e2).getIata());
      out.write('"');
      out.write('>');
      out.print(aeropuerto.get(e2).getNombre().toUpperCase());
      out.write("</option>\n");
      out.write("                                </tr>\n");
      out.write("                                ");

                                        }
                                    } 
                                
      out.write("\n");
      out.write("                            </select> \n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"form-group row\">  \n");
      out.write("                        <div class=\"col-12\">\n");
      out.write("                            <label class=\"col-3\">Puerta de Embarque</label>\n");
      out.write("                            <input class=\"col-6 input-text\" type=\"number\" name=\"puerta\" id=\"fechaVuelo\">\n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"form-group row\">\n");
      out.write("                        <div class=\"col-12\">                                \n");
      out.write("                            <label class=\"col-3\">Avion</label>\n");
      out.write("                            <select name=\"avionparavuelo\" class=\"col-6 input-text\">  \n");
      out.write("                                ");

                                    ormaero = new ORMaero();
                                    List<Avion> avion2 = ormaero.obteneraviones();
                                    String usuario2 = (String) session.getAttribute("usuario").toString();
                                    String nitaero2 = ormaero.compararav(usuario2);

                                    for (int k = 0; k < avion2.size(); k++) {
                                        out.println(avion2.get(k).getAerolinea());
                                        if (avion2.get(k).getAerolinea().getNit().equals(nitaero2)) {
                                
      out.write("       \n");
      out.write("                                <tr>\n");
      out.write("                                <option name=\"avionparavuelo\" value=\"");
      out.print(avion2.get(k).getMatricula());
      out.write('"');
      out.write('>');
      out.print(avion2.get(k).getMatricula());
      out.write("</option>\n");
      out.write("                                </tr>\n");
      out.write("\n");
      out.write("                                ");

                                        }
                                    }
                                
      out.write("\n");
      out.write("                            </select> \n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("\n");
      out.write("                    <div class=\"form-group row\" style=\"justify-content: center; padding-left: 72%\">\n");
      out.write("                        <div class=\"col-12\">\n");
      out.write("                            <input id=\"accion\" name=\"accion\" type=\"hidden\" value=\"agregarvuelo\">\n");
      out.write("                            <input type=\"submit\" value=\"Agendar\">\n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("\n");
      out.write("                </form>\n");
      out.write("            </div>        \n");
      out.write("           \n");
      out.write("               <div class=\"table-responsive-md\">\n");
      out.write("                <table class=\"table\">\n");
      out.write("                    <thead>\n");
      out.write("                        <tr>\n");
      out.write("                            <th>Fecha</th>\n");
      out.write("                            <th>Hora programada</th>\n");
      out.write("                            <th>Aerolinea</th>\n");
      out.write("                            <th>Vuelo</th>\n");
      out.write("                            <th>Avion</th>\n");
      out.write("                            <th>Origen</th>\n");
      out.write("                            <th>Destino</th>\n");
      out.write("                            <th>Estado</th>\n");
      out.write("                            <th>Cancelar</th>\n");
      out.write("                        </tr>\n");
      out.write("                    </thead>\n");
      out.write("                    <tbody>\n");
      out.write("                        ");
 
                        ormaero = new ORMaero();
                        List<Avion>avion4 = ormaero.obteneraviones();
                        List<Vuelo>vuelo4 = ormaero.obtenerVuelosDisponibles();
                        List<Destino>destino4 = ormaero.obtenerdestino();
                        List<Origen>origen4 = ormaero.obtenerorigen();
                        usuario3 = (String) session.getAttribute("usuario").toString();
                        String nitaero4 = ormaero.compararav(usuario3);
                        /*out.println(nitaero4);
                        out.println();
                        out.println();*/
                            for (int t = 0; t < vuelo4.size(); t++) {
                                /*out.println(avion4.get(t).getMatricula());
                                out.println("------");
                                out.println(vuelo4.get(t).getAvion().getMatricula());*/
                                //out.println(avion4.get(t).getMatricula().equals(vuelo4.get(t).getAvion()));
                                //out.println(avion.get(j).getAerolinea().getNit());vuelo4.get(t).getAvion()
                                if (avion4.get(t).getAerolinea().getNit().equals(nitaero4) && vuelo4.get(t).getEstado().equals("en curso") ) {

                        
      out.write("\n");
      out.write("                        <tr>\n");
      out.write("                            <td>");
      out.print(origen4.get(t).getFecha());
      out.write("</td>\n");
      out.write("                            <td>");
      out.print(origen4.get(t).getHora());
      out.write("</td>\n");
      out.write("                            <td>");
      out.print(vuelo4.get(t).getAvion().getAerolinea().getNombre());
      out.write("</td>\n");
      out.write("                            <td>");
      out.print(vuelo4.get(t).getCodVuelo());
      out.write("</td>\n");
      out.write("                            <td>");
      out.print(vuelo4.get(t).getAvion().getMatricula());
      out.write("</td>\n");
      out.write("                            <td>");
      out.print(origen4.get(t).getAeropuerto().getNombre().toUpperCase());
      out.write("</td>\n");
      out.write("                            <td>");
      out.print(destino4.get(t).getAeropuerto().getNombre().toUpperCase());
      out.write("</td>\n");
      out.write("                            <td>");
      out.print(vuelo4.get(t).getEstado().toUpperCase());
      out.write("</td>\n");
      out.write("                            <td><a href=\"Administracion.jsp?accion=cancelarvuelo&codvuelo=");
      out.print(vuelo4.get(t).getCodVuelo());
      out.write("&avion=");
      out.print(vuelo4.get(t).getAvion().getMatricula());
      out.write("&puerta=");
      out.print(vuelo4.get(t).getPuertaEmbarque());
      out.write("\">CANCELAR</a></td>\n");
      out.write("                            <!--<td><a href=\"RegistroPasajeros.jsp?\"><image src=\"\" alt=\"reservar\" width=\"\"></a></td>        -->                    \n");
      out.write("                        </tr>\n");
      out.write("                        ");

                            }
                          }
                        
      out.write("\n");
      out.write("                    </tbody>\n");
      out.write("                </table>\n");
      out.write("            </div>\n");
      out.write("    </section>    \n");
      out.write("        <footer class=\"footer text-center\">\n");
      out.write("            <div>\n");
      out.write("                <label><a href=\"MapaDelSitio.html\">Mapa del sitio</a></label>\n");
      out.write("                <label><a href=\"Descripcion.html\">Descripción</a></label>\n");
      out.write("                <label>Politica y privacidad</label>\n");
      out.write("                <label>Terminos y condiciones</label>\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("            <div class=\"copyright\">\n");
      out.write("                <label>Copyright 2020</label>\n");
      out.write("                <label>Todos los derechos resevados</label>\n");
      out.write("            </div>\n");
      out.write("        </footer>\n");
      out.write("\n");
 
} else {
        out.print("<script>location.replace('Login.jsp');</script>");
    }

      out.write("\n");
      out.write("\n");
      out.write("        <script src=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>\n");
      out.write("        <!--<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx\" crossorigin=\"anonymous\"></script>-->\n");
      out.write("        <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.bundle.min.js\"></script>\n");
      out.write("        <script src=\"Estilos/js/Script.js\"></script>\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
