<%@page language="java" contentType="text/html; charset=windows-1251" %>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
try{ss.x();
            long L1 = System.currentTimeMillis();
String status="sql:";
String line="inh";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";

    String qq = request.getParameter("area");
    String dbg = request.getParameter("dbg");
    ss.area2db(qq,dbg!=null);
            long L2 = System.currentTimeMillis();
            status+=":"+(L2-L1)+" msec:";        
%>
<%@include  file="../ok.jsp"%>
<%}catch(ExScriptLine e2){
%>
Bad script line:<br><b>
<%=e2.line%></b>
<%}catch(ExFTS e3){%>
<hr><%=e3.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../sorry.jsp"%>
<%}%>