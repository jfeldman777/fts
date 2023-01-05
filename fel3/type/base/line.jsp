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
String status="edit base form";
String tab = request.getParameter("tab");
String col = request.getParameter("col");
String dtype = request.getParameter("dtype");
ITabCol tc = ss.getTabCol(tab,col,'c');
tc.setKey();

%>
<%=ss.typeImgCode(tab)%><hr>
<form action='edit.jsp' method=post>
<table border='0'>
<tr><td>column</td><td>base value</td></tr>
<%=tc.baseForm(dtype.equals("16"))%>
<tr><td colspan=2 align=center>
<input type=image src='<%=style%>edit.gif'
 title='edit base value'>
</td><tr>
</table>
</form>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>
