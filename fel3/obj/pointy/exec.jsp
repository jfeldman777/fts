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
String er = request.getParameter("er");
if(er!=null) ss.setER(er);
String ER = ss.ER();
String status="exec pointy login";
String dbconfig= request.getParameter("dbconfig");
String schema= request.getParameter("schema");
String root= request.getParameter("root");
String sid = request.getParameter("id");
int id = Integer.parseInt(sid);
String arg= request.getParameter("arg");
String code= request.getParameter("code");
String fun = request.getParameter("fun");
ss.login2(dbconfig,schema,root,sid,code);

%>
<%=ss.faceObj(id)%>
<hr>
<%=ss.procResult(fun,id,arg)%>
<hr>PARAMS:<%=arg%>
</body>
</html>

<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>