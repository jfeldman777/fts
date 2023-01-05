<%@page language="java" contentType="text/html; charset=windows-1251" %>
<%@page import = "java.util.*"%>
<%@page import = "java.net.*" %>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
try{ss.x();

            long L1 = System.currentTimeMillis();
String ER = ss.ER();
String line="obj";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
String status="edit line";


    
    String tn= request.getParameter("id");
    IGuiNode nn = ss.getGuiNode(tn);
    String tabcol = request.getParameter("tabcol");
    String val = request.getParameter(tabcol);
    String sdtype=request.getParameter("dtype");
    int dtype = Integer.parseInt(sdtype);
    if(dtype==16&&val==null)val="";

    nn.doUpdateLine(tabcol,val,dtype);
            long L2 = System.currentTimeMillis();
            status+=":"+(L2-L1)+" msec:";      
%>
<%@include  file="../ok.jsp"%>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../sorry.jsp"%>
<%}%>
