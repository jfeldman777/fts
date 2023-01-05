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
String line="obj";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
%>
  <BODY <%=bg%>>

<%

String er = request.getParameter("er");
if(er!=null) ss.setER(er);

String status="edit pointx";
String sid = request.getParameter("object::id");
String dbconfig= request.getParameter("dbconfig");
String schema= request.getParameter("schema");
String root= request.getParameter("root");
String code= request.getParameter("code");

    ss.editPointX(request);
%>

<form action='editform.jsp' method=post>
<input type=hidden name='dbconfig' value='<%=dbconfig%>'>
<input type=hidden name='schema' value='<%=schema%>'>
<input type=hidden name='root' value='<%=root%>'>
<input type=hidden name='code' value='<%=code%>'>
<input type=hidden name='object::id' value="<%=sid%>"/>
<input type=image src='<%=style%>edit.gif' title='edit'>
</form>
<img src='/feldman-root/style/<%=ss.ER()%>save.gif'>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>
