<%@page language="java" contentType="text/html; charset=windows-1251" %>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
try{ss.x();
            
            long L1 = System.currentTimeMillis();
String ER = ss.ER();
String status="move object";
String line="obj";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
String var= request.getParameter("var");
int iv = Integer.parseInt(var);
String id= request.getParameter("id");
int i=Integer.parseInt(id);
    
    ss.lock(i,iv);
            long L2 = System.currentTimeMillis();
            status+=":"+(L2-L1)+" msec:";    
%>
<%@include  file="../../ok.jsp"%>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>