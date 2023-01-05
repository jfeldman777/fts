<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head><title>help</title>
<meta name='Author' content='Jacob Feldman'/>
</head>


<%request.setCharacterEncoding("windows-1251");
try{ss.x();

String status="start ar",line="obj";
String tn = request.getParameter("id");
IGuiNode nn = ss.getGuiNode(tn);
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";

%>
  <body  <%=bg%>>

<%=nn.face()%><hr>
Root type of new query:
<form action=init.jsp method=post>
<table border=0><tr><td>
<input type=hidden name='id' value='<%=tn%>'>
<input name='type' value='<%=ss.inhRootCode()%>'>
</td><td valign=baseline>
<input type=image src='/feldman-root/style/obj/here.gif'
title='start from this type'
>
</td></tr></table>
</form>

</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>
