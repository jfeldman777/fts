<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head><title>delete a type</title>
<meta name='Author' content='Jacob Feldman'/>
</head>
  <BODY  BACKGROUND='/feldman-root/style/obj/bg.gif'>


<%request.setCharacterEncoding("windows-1251");
try{ss.x();

String status="move type set";
String sid = request.getParameter("id");

%><a title='Type-set is about to be changed'>
<img src="/feldman-root/icon/attention.gif" 
        width="400" height="150" usemap="#m" border="0"> 
</a>
<map name="m"> 
  <area shape="rect" coords="3,105,86,145" 
    href="delete2.jsp?id=<%=sid%>" 
    title="change it anyway">
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
