<%@page language="java" contentType="text/html; charset=windows-1251" %>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
try{ss.x();

int L=-1;
String status="sql:";
String line="obj";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";


    String qq = request.getParameter("q");
    ss.recordAR(Field.apo(qq));
%>
<html>
<head>
<meta name='Author' content='Jacob Feldman'/>
<title>e</title></head>
<body <%=bg%>>
<table border=2>
<%=ss.quickReport(qq)%>
</table>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>