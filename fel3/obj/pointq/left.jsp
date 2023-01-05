<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head>
<meta name='Author' content='Jacob Feldman'/>
<title>e</title></head>


<%request.setCharacterEncoding("windows-1251");
try{ss.x();

String ER = ss.ER();
String status="point q tab";
String dbconfig= request.getParameter("dbconfig");
String schema= request.getParameter("schema");
String root= request.getParameter("root");
String sid = request.getParameter("id");
String pwd= request.getParameter("pwd");

%>
<body BACKGROUND='/feldman-root/style/obj/bg.gif'>
<table border='0' cellpadding='2' cellspacing='0'>
<%=ss.pointQtab(dbconfig,schema,root,sid,pwd//,pars
)%>
</table>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>