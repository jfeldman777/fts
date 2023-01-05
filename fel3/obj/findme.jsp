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
String line="obj";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
%>
  <BODY  <%=bg%>>

<%
String status="find obj";
String crit = request.getParameter("crit");
String typecode = request.getParameter("type");
%>
<table border=2>
<%=ss.findMe(typecode,crit)%>
</table>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../sorry.jsp"%>
<%}%>
