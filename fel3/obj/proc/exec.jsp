<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head>
<meta name='Author' content='Jacob Feldman'/>
<title>e</title></head>
  <BODY  BACKGROUND='/feldman-root/style/obj/bg.gif'>

<%request.setCharacterEncoding("windows-1251");
try{ss.x();


String status="execute";
String sid= request.getParameter("id");
int id = Integer.parseInt(sid);
String arg= request.getParameter("arg");
String code= request.getParameter("code");
String fun = request.getParameter("fun");
int k = Integer.parseInt(fun);

%>

<%=ss.faceObj(id)%>
<!--hr-->
<%=ss.procResult(fun,id,arg)%>
<%if(id>0){%>
<a href='publish.jsp?id=<%=id%>&arg=<%=arg%>&fun=<%=fun%>'>publish it on server</a>
<%}%>
<hr>FUN:<%=fun%>=<%=ss.parDesc(id,k)%>; PARAMS:<%=arg%>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>