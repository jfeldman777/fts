<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head>
<meta name='Author' content='Jacob Feldman'/>
<title>Objects</title></head>


<%request.setCharacterEncoding("windows-1251");
try{ss.x();


String line="obj";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
String status="obj tree";

%>
<body <%=bg%>  onLoad="status='objects'"
 text="#553100" link="#009966" alink="#FE491D" vlink="#009966"
>
<table border='0' cellpadding='0' cellspacing='0' bgcolor='white'>
<%=ss.showTree()%>
</table>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>