<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head>
<meta name='Author' content='Jacob Feldman'/>
<title>e</title></head>

<%
String line="type";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
%>
  <BODY <%=bg%>>

<%request.setCharacterEncoding("windows-1251");
try{ss.x();
String status="edit base form";

String tn= request.getParameter("id");
IGuiNode nn = ss.getGuiNode(tn);
%>
<%=nn.typeImgCode()%><hr>
<form action='edit.jsp' method=post>
<input type=image src='<%=style%>edit.gif'
 title='edit base value'>
<table border='0' cellpadding='2' cellspacing='0'>
<%=nn.baseForm()%>
</table>
<input type=image src='<%=style%>edit.gif'
 title='edit base value'>
</form>


</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>