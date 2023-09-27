package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import ORMJPA.*;
import entities.*;
import java.util.*;

public final class Index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(2);
    _jspx_dependants.add("/Menu.jsp");
    _jspx_dependants.add("/Footer.html");
  }

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
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("\n");
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
      out.write("\n");
      out.write("        <title>Aeropuerto Vanguardia</title>\n");
      out.write("        <link rel=\"shortcut icon\" type=\"image/png\" href=\"favicon.png\"/>\n");
      out.write("\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        ");

            List<Destino> destino = null;
            String aero = "";
            ORMaero objOrm;
            
        
      out.write("\n");
      out.write("        <!--<header><h1>AEROPUERTO VANGUARDIA</h1> </header>-->\n");
      out.write("        ");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("\n");
      out.write("<nav class=\"navbar navbar-expand-lg navbar\">\n");
      out.write("    <a class=\"navbar-brand\" href=\"Index.jsp\">INICIO</a>\n");
      out.write("    <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarNav\" aria-controls=\"navbarNav\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n");
      out.write("        <span class=\"navbar-toggler-icon\"></span>\n");
      out.write("    </button>\n");
      out.write("    <div class=\"collapse navbar-collapse\" id=\"navbarNav\">\n");
      out.write("        <ul class=\"navbar-nav\">\n");
      out.write("            <li class=\"nav-item active\">\n");
      out.write("\n");
      out.write("                <a class=\"nav-link\" href=\"Reservas.jsp\">RESERVAS<span class=\"sr-only\">(current)</span></a>\n");
      out.write("\n");
      out.write("            </li>\n");
      out.write("            <!--<li class=\"nav-item\">\n");
      out.write("                <a class=\"nav-link\" href=\"#\">Features</a>\n");
      out.write("            </li>-->\n");
      out.write("\n");
      out.write("        </ul>\n");
      out.write("        <div class=\"dropdown\">\n");
      out.write("            ");
 if (session.getAttribute("usuario")==null) { 
      out.write("\n");
      out.write("            <button type=\"button\" class=\"btn btn-secondary dropdown-toggle\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n");
      out.write("                MI CUENTA\n");
      out.write("            </button>\n");
      out.write("            \n");
      out.write("            <div class=\"dropdown-menu\" aria-labelledby=\"dropdownMenu2\">\n");
      out.write("                <button class=\"dropdown-item\" type=\"button\" ><a href=\"Login.jsp\">INICIAR SESION</a></button>\n");
      out.write("                <button class=\"dropdown-item\" type=\"button\"><a href=\"Registro.jsp\">REGISTRARSE</a></button>                   \n");
      out.write("            </div>\n");
      out.write("            ");
} else { 
      out.write("\n");
      out.write("             <button type=\"button\" class=\"btn btn-secondary dropdown-toggle\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n");
      out.write("                 ");
      out.print(session.getAttribute("usuario") );
      out.write("\n");
      out.write("            </button>\n");
      out.write("            <div class=\"dropdown-menu\" aria-labelledby=\"dropdownMenu2\">\n");
      out.write("                <button class=\"dropdown-item\" type=\"button\">ACTUALIZAR CONTRASEÑA</button>\n");
      out.write("                <button class=\"dropdown-item\" type=\"button\">ACTUALIZAR INFORMACIÓN</button>                   \n");
      out.write("            </div>\n");
      out.write("            ");
}
      out.write("\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("    </div>\n");
      out.write("</nav>\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("        <section class=\"seccion\">\n");
      out.write("            <a class=\"navbar-brand etiqueta\">BUSCAR VUELO</a>\n");
      out.write("            <form name=\"filtros\" method=\"get\" action=\"Index.jsp\">\n");
      out.write("                <div class=\"form-row\">\n");
      out.write("                    <div class=\"form-group col-md-3\">\n");
      out.write("                        <label>Aerolinea</label>\n");
      out.write("                        <div class=\"col-2\">\n");
      out.write("                            <select name=\"Filtroaerolinea\" value=\"AEROLINEA\">\n");
      out.write("                                ");

                                    ORMaero ormaero = new ORMaero();
                                    List<Aerolinea> aerolinea = ormaero.obtenerAerolienas();
                                
      out.write("\n");
      out.write("                                ");
for (int j = 0; j < aerolinea.size(); j++) {
      out.write(" \n");
      out.write("                                <tr>\n");
      out.write("                                <option value=\"");
      out.print(aerolinea.get(j).getNit());
      out.write('"');
      out.write('>');
      out.print(aerolinea.get(j).getNombre());
      out.write("</option>\n");
      out.write("                                </tr>\n");
      out.write("                                ");
}
      out.write("\n");
      out.write("\n");
      out.write("                            </select>\n");
      out.write("                        </div>\n");
      out.write("                        <div class=\"col-2\" >   \n");
      out.write("                            <input type=\"submit\" name=\"filtAero\" value=\"FILTRAR\" style=\" margin-top: 10px;\">\n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"form-group col-md-3\">\n");
      out.write("                        <label>N° de pasajeros</label>\n");
      out.write("                        <select name=\"nPasajeros\">\n");
      out.write("                            \n");
      out.write("                            <option value=\"1\">1</option>\n");
      out.write("                            <option value=\"2\">2</option>\n");
      out.write("                            <option value=\"3\">3</option>\n");
      out.write("                            <option value=\"4\">4</option>\n");
      out.write("                            <option value=\"5\">5</option>\n");
      out.write("                        </select>\n");
      out.write("                    </div>\n");
      out.write("\n");
      out.write("                    <div class=\"form-group col-md-3\">\n");
      out.write("                        <label>Destino</label>\n");
      out.write("                        <select name=\"FiltroDestino\" value=\"DESTINO\">\n");
      out.write("                            <option value=\"\">Seleccione una opcion</option>\n");
      out.write("                            ");


                                if (request.getParameter("filtAero") != null) {
                                    objOrm = new ORMaero();
                                    aero = request.getParameter("Filtroaerolinea");
                                    destino = objOrm.obtenerdestino();

                                    if (request.getParameter("filtAero") != null) {
                                        for (int i = 0; i < destino.size(); i++) {
                                            if (destino.get(i).getVuelo().getAvion().getAerolinea().getNit().equals(aero)) {

                            
      out.write("\n");
      out.write("                            <option value=");
      out.print(destino.get(i).getAeropuerto().getIata());
      out.write('>');
      out.write(' ');
      out.print(destino.get(i).getAeropuerto().getCiudad().getNombre().toUpperCase());
      out.write(" </option>\n");
      out.write("                            ");
     }
                                        }
                                    }
                                }
      out.write("\n");
      out.write("\n");
      out.write("                        </select>\n");
      out.write("\n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"form-group col-md-3\">\n");
      out.write("                        <label>Clase</label>\n");
      out.write("                        <select name=\"clase\">\n");
      out.write("                            <option value=\"economica\">ECONOMICA</option>\n");
      out.write("                            <option value=\"primera\">PRIMERA</option>\n");
      out.write("                            <option value=\"ejecutiva\">EJECUTIVA</option>\n");
      out.write("                        </select>\n");
      out.write("                    </div>\n");
      out.write("\n");
      out.write("                    <!--<div class=\"form-group col-md-2\">\n");
      out.write("                        <label>Fecha</label>\n");
      out.write("                        <select name=\"FiltroFecha\" value=\"FECHA\">\n");
      out.write("                            <option value=\"\">Seleccione una opcion</option>\n");
      out.write("                    ");

                        if (request.getParameter("filtAero") != null) {

                            for (int i = 0; i < destino.size(); i++) {
                                if (destino.get(i).getVuelo().getAvion().getAerolinea().getNit().equals(aero)) {

                    
      out.write("\n");
      out.write("                    <option value=");
      out.print(destino.get(i).getFecha());
      out.write('>');
      out.print(destino.get(i).getFecha());
      out.write("</option>\n");
      out.write("                    ");
  }
                            }
                        }

                    
      out.write("\n");
      out.write("                </select>\n");
      out.write("            </div>-->\n");
      out.write("\n");
      out.write("                </div>\n");
      out.write("                <div class=\"form-row\">\n");
      out.write("                    <div class=\"col-12\">\n");
      out.write("                        <input type=\"submit\" name=\"buscarVuelos\" value=\"BUSCAR\">\n");
      out.write("\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("\n");
      out.write("            </form>\n");
      out.write("\n");
      out.write("        </section>\n");
      out.write("        <section>\n");
      out.write("            <div class=\"table-responsive-md\">\n");
      out.write("                <table class=\"table\">\n");
      out.write("                    <thead>\n");
      out.write("                        <tr>\n");
      out.write("                            <th>Fecha</th>\n");
      out.write("                            <th>Hora programada</th>\n");
      out.write("                            <th>Aerolinea</th>\n");
      out.write("                            <th>Vuelo</th>\n");
      out.write("                            <th>Destino</th>\n");
      out.write("                            <th></th>\n");
      out.write("                        </tr>\n");
      out.write("                    </thead>\n");
      out.write("                    <tbody>\n");
      out.write("                        ");
                            if (request.getParameter("buscarVuelos") != null) {
                                objOrm = new ORMaero();
                                List<Destino> destinosDis = objOrm.obtenerdestino();
                                String aeroli = request.getParameter("Filtroaerolinea");
                                String dest = request.getParameter("FiltroDestino");
                                String clase=request.getParameter("clase");
                        
      out.write("\n");
      out.write("                        ");
for (int i = 0; i < destinosDis.size(); i++) {
                                if (destinosDis.get(i).getVuelo().getAvion().getAerolinea().getNit().equals(aeroli) && destinosDis.get(i).getAeropuerto().getIata().equals(dest)) {
                        
      out.write(" \n");
      out.write("                        <tr>\n");
      out.write("                            <td>  ");
      out.print(destinosDis.get(i).getFecha());
      out.write(" </td>\n");
      out.write("                            <td>");
      out.print(destinosDis.get(i).getHora());
      out.write("</td>\n");
      out.write("                            <td>");
      out.print(destinosDis.get(i).getVuelo().getAvion().getAerolinea().getNombre().toUpperCase());
      out.write("</td>\n");
      out.write("                            <td>");
      out.print(destinosDis.get(i).getVuelo().getCodVuelo());
      out.write("</td>\n");
      out.write("                            <td>");
      out.print(destinosDis.get(i).getAeropuerto().getCiudad().getNombre().toUpperCase());
      out.write("</td>\n");
      out.write("                            <td><a href=\"PagoReserva.jsp?Cant=");
      out.print(request.getParameter("nPasajeros"));
      out.write("&Clase=");
      out.print(clase);
      out.write("&Vuelo=");
      out.print(destinosDis.get(i).getVuelo().getCodVuelo());
      out.write("\"> Reservar</a></td>\n");
      out.write("                        </tr>\n");
      out.write("                        ");
 }
                                }
                            }
      out.write("\n");
      out.write("                    </tbody> \n");
      out.write("                </table>\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("\n");
      out.write("        </section>\n");
      out.write("\n");
      out.write("        ");
      out.write("<!DOCTYPE html>\n");
      out.write("<!--\n");
      out.write("To change this license header, choose License Headers in Project Properties.\n");
      out.write("To change this template file, choose Tools | Templates\n");
      out.write("and open the template in the editor.\n");
      out.write("-->\n");
      out.write("\n");
      out.write("<footer class=\"footer text-center\">\n");
      out.write("    <div>\n");
      out.write("        <label><a href=\"MapaDelSitio.html\">Mapa del sitio</a></label>\n");
      out.write("        <label><a href=\"Descripcion.html\">Descripción</a></label>\n");
      out.write("        <label>Politica y privacidad</label>\n");
      out.write("        <label>Terminos y condiciones</label>\n");
      out.write("    </div>\n");
      out.write("\n");
      out.write("    <div class=\"copyright\">\n");
      out.write("        <label>Copyright 2020</label>\n");
      out.write("        <label>Todos los derechos resevados</label>\n");
      out.write("    </div>\n");
      out.write("</footer>");
      out.write("\n");
      out.write("\n");
      out.write("        <script src=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>\n");
      out.write("        <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx\" crossorigin=\"anonymous\"></script>\n");
      out.write("        <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.bundle.min.js\"></script>\n");
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
