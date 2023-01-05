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
String tabcol = request.getParameter("tabcol");
String tctype = request.getParameter("tctype");
String status="drop col";

%>
  <BODY  BACKGROUND='/feldman-root/style/R/type/bg.gif'>
<a title='Some column is about to be deleted'>
<img src="/feldman-root/icon/attention.gif" 
        width="400" height="150" usemap="#m" border="0"> 
</a>
<map name="m"> 
  <area shape="rect" coords="3,105,86,145" 
    href="drop2.jsp?tabcol=<%=tabcol%>&tctype=<%=tctype%>" 
    title="delete it anyway">
  <area shape="rect" coords="318,103,368,137" 
        href="javascript:top.history.back()" 
        title="no, my mistake">
</map>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>