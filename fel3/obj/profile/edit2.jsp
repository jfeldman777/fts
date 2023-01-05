<%@page language="java" contentType="text/html; charset=windows-1251" %>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<%@page import = "j5feldman.profile.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
try{ss.x();
            Profile pf = ss.profile;
            long L1 = System.currentTimeMillis();
String ER = ss.ER();
String sid;
String status="edit object";
String line="obj";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
    pf.saveBoxesAr(request);
            long L2 = System.currentTimeMillis();
            status+=":"+(L2-L1)+" msec:";        
%>
<%@include  file="../../ok.jsp"%> 
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>