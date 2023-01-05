<%@page language="java" contentType="text/html; charset=windows-1251" %>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
try{ss.x();
String status="root";
String tn = request.getParameter("id");
    if(Character.isDigit(tn.charAt(0))){tn = "T"+tn;}
    ss.setRoot(tn);
%>
<jsp:forward page="left.jsp"/>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../sorry.jsp"%>
<%}%>

