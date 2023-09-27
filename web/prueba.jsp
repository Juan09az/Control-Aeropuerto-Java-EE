<%@page import="java.util.logging.Logger"%>
<%@page import="java.util.logging.Level"%>
<%@page import="java.util.*"%>
<%@page import="ORMJPA.ORMaero"%>
<%@page import="java.text.*"%>
               <% 
 
               if(request.getParameter("fechaNacimiento")!=null){
                   
                  
                  
                  SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                  String f="2020/12/12";
                  Date d=sdf.parse(f);
                  out.println(d);

               }

%>