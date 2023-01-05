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

String line="obj";
String status="edit point p";
String style="/feldman-root/style/"+ss.ER()+line+"/";

    ss.editPointX(request);
%>
<img src='/feldman-root/style/<%=ss.ER()%>save.gif'>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>
