<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head>
<meta name='Author' content='Jacob Feldman'/>
<title>e</title></head>
<%
request.setCharacterEncoding("windows-1251");
String line="obj";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
String er = request.getParameter("er");
if(er!=null) ss.setER(er);

String status="edit pointfla";
//ss.xConnect(request);
try{
    ss.editPointFla(request);
%>
  <BODY <%=bg%>>




<img src='/feldman-root/style/<%=ss.ER()%>save.gif'>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}finally{
//ss.xDisconnect();
}%>
