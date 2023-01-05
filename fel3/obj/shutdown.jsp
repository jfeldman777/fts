<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head><title>delete</title>
<meta name='Author' content='Jacob Feldman'/>
</head>

<%request.setCharacterEncoding("windows-1251");
try{ss.x();

String status="before shutdown";
String path = request.getParameter("path");

%>
  <BODY  BACKGROUND='/feldman-root/style/obj/bg.gif'>
<a title='You are about to shutdown the database'>
<img src="/feldman-root/icon/attention.gif" 
        width="400" height="150" usemap="#m" border="0"> 
</a>
<map name="m"> 
  <area shape="rect" coords="3,105,86,145" 
    href="do/shutdown.jsp" 
    title="do it now!">
  <area shape="rect" coords="318,103,368,137" 
        href="javascript:top.history.back()" title="no, my mistake">
</map>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../sorry.jsp"%>
<%}%>