<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head>
<meta name='Author' content='Jacob Feldman'/>
<title>e</title>

<%request.setCharacterEncoding("windows-1251");
try{ss.x();
String line="obj";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
String status="params";
String sid = request.getParameter("id");
int id = Integer.parseInt(sid);
String sk = request.getParameter("k");
if(sk==null)sk = ss.getKproc(id,false);
if(sk.equals("???")){
%>
<jsp:forward page="blank.html"/>
<%
}
int k = Integer.parseInt(sk);
%>
</head>
  <BODY  <%=bg%>>

<form action='exec.jsp'  method=post target=_parent id=f>

<table border=0>
<tr><td>
<input type=hidden name='id' value='<%=sid%>'>
<input type=hidden name='fun' value='<%=sk%>'>
<input type=text name=arg>
</td><td valign=baseline>
<input type=image src='<%=style%>execute.gif'
title='execute it!'
></td></tr></table>
</form>
<hr>
(<%=sk%>)<%=ss.parDesc(id,k)%>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>
