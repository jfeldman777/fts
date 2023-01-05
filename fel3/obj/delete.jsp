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
String status="before deleteing files/pictures ";
String path = request.getParameter("path");

%>
  <BODY  BACKGROUND='/feldman-root/style/obj/bg.gif'>
<a title='You are about to delete some file/picture from the server'>
<img src="/feldman-root/icon/attention.gif" 
        width="400" height="150" usemap="#m" border="0"> 
</a>
<map name="m"> 
  <area shape="rect" coords="3,105,86,145" 
    href="delete2.jsp?path=<%=path%>" 
    title="delete it anyway">
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