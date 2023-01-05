<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head><title>erport menu</title>
<meta name='Author' content='Jacob Feldman'/>
</head>
<%request.setCharacterEncoding("windows-1251");
try{ss.x();
String line="obj";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
%>
  <BODY  onLoad="status='report generator'" <%=bg%>>

<%String status="ar tree...";

String norm = ss.arTreeQtxt();
int r = norm.length()/70 + 1;
%>
<%=ss.faceAR()%><hr>

<form action='delete.jsp' method=post>
<a href='normal.jsp' title='(1)set Q for bolds and cut'>
<img src='/feldman-root/style/obj/here.gif' border=0></a>
<a href='normal2.jsp' title='set Q for all'>
<img src='/feldman-root/style/obj/back.gif' border=0></a>

<input type=image src='<%=style%>delete.gif'
title='delete checked'
>
<table cellpadding='0' cellspacing='0' border='0' bgcolor=white>
<%=ss.showArTree()%>
</table>
</form>
<hr>
<form id=fx method=post action=sql.jsp target=sql>
<input type=image src='<%=style%>execute.gif' title='(2)apostroph once!'><br>
<TEXTAREA cols='70' rows='<%=r%>' name='q'>
<%=norm%>
</TEXTAREA><br>
</form>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>