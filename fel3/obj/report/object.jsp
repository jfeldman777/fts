<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<%request.setCharacterEncoding("windows-1251");
try{ss.x();
String line="obj";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
%>
<head><title>repot object</title></head>
  <BODY  <%=bg%>>

<%String status="ar field";

String sid = request.getParameter("id");

%>
<%=ss.pathAR(sid)%>
<form action='fields.jsp' method=post>
<input type='hidden' name='id' value='<%=sid%>'>
<table border=3>
<%=ss.getFieldsTab(sid)%>
</table>
<input type=image src='<%=style%>edit.gif'
title='write it down'
></td><tr>
</form>

</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>