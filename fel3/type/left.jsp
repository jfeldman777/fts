<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head>

<%request.setCharacterEncoding("windows-1251");
try{ss.x();
String status="type tree";
String s= request.getParameter("s");
String line="type";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";

%>
<meta name='Author' content='Jacob Feldman'/>
<title>Types</title></head>
<body <%=bg%>  onLoad="status='types'"   alink="red" link=blue vlink=blue>
<table border='0' cellpadding='0' cellspacing='0' bgcolor='white'>
<%=ss.showTree("t")%>
</table>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../sorry.jsp"%>
<%}%>