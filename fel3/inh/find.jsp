<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head>
<meta name='Author' content='Jacob Feldman'/>
<title>e</title></head>

<%
String line="inh";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
%>
<body <%=bg%>>

<%request.setCharacterEncoding("windows-1251");
try{ss.x();

String status="find type";
String crit = "";
boolean sh=false;

    crit = request.getParameter("criteria");
    sh = request.getParameter("sh")!=null;
%>
<table border=2>
<%=ss.find("h",crit,sh)%>
</table>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../sorry.jsp"%>
<%}%>