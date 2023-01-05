<%@page language="java" contentType="text/html; charset=windows-1251" %>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
try{ss.x();

String er = request.getParameter("er");
if(er!=null) ss.setER(er);
String ER = ss.ER();
String status="point qq";
String dbconfig= request.getParameter("dbconfig");
String schema= request.getParameter("schema");
String root= request.getParameter("root");
String sid = request.getParameter("id");
String code= request.getParameter("pwd");

     ss.login2(dbconfig,schema,root,sid,code);

%>
<HTML>
<jsp:forward page="frame.jsp">
<jsp:param name="id" value="<%=sid%>"/>
</jsp:forward>
</HTML>

<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>

