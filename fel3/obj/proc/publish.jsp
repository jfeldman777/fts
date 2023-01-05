<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head><title>publish</title>
<meta name='Author' content='Jacob Feldman'/>
</head>
  <BODY  BACKGROUND='/feldman-root/style/obj/bg.gif'>


<%request.setCharacterEncoding("windows-1251");
try{ss.x();

String status="publish";

String sid = request.getParameter("id");
String arg = request.getParameter("arg");
int id = Integer.parseInt(sid);
String fun = request.getParameter("fun");
%>
Open your mini-site 
<a href='<%=ss.procPublish(fun,id,arg)%>' target='wf' 
title='Static page with your report'>>>here>> </a>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>