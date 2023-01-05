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
String line="type";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
%>
  <BODY <%=bg%>>

<%
String status="edit base";
String tn= request.getParameter("id");
IGuiNode nn = ss.getGuiNode(tn);

%>
<%=nn.typeImgCode()%> <a href='export.jsp?id=<%=tn%>' title='export of base values'>
                <img src='<%=style%>export.gif' border=0></a><hr>
<table border=0 cellspacing='2'><tr><td align=center>
<form action='editform.jsp'  method=post>
<input type=image src='<%=style%>edit.gif' border=0
  title='edit base value'>
</td></tr>
<tr><td>
<table border=1 cellpadding='5' bgcolor=white>
<tr><td>column</td><td colspan=2>base value</td></tr>
<%=nn.baseView()%>
</table>
</td></tr>
<tr><td align=center>
<input type=image src='<%=style%>edit.gif' border=0 
title='edit base value'>
<input type=hidden name=id value='<%=tn%>'>
</form>
</td></tr>
</table>


</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>