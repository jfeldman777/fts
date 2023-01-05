<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
try{ss.x();

String status="edit line form";
String line="obj";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/obj/bg.gif' ";
String tn= request.getParameter("id");
String tab = request.getParameter("tab");
String col = request.getParameter("col");
String tctype = request.getParameter("tctype");
char c = tctype.charAt(0);
if(c!='c'){
%>
<jsp:forward page="form.jsp">
<jsp:param name="tab" value="<%=tab%>"/>
<jsp:param name="col" value="<%=col%>"/>
<jsp:param name="id" value="<%=tn%>"/>
<jsp:param name="tctype" value="<%=tctype%>"/>
</jsp:forward>
<%
}else{

IGuiNode nn = ss.getGuiNode(tn);
ITabCol tc = ss.getTabCol(tab,col,tctype.charAt(0));
%>
<html>
<head>

<script>var id=<%=nn.id()%>;</script>
<script src="opentable.js"></script>

<meta name='Author' content='Jacob Feldman'/>
<title>123</title></head>
<body <%=bg%>>
<%=ss.faceObj(nn.id())%>
<hr>
<form action='editline.jsp' method=post>
<table border='0' cellpadding='2' cellspacing='0'>
<%=ss.lineForm(tn,tab,col)%>
<tr><td colspan=2 align='center'>
<input type=image src='<%=style%>edit.gif'></td><tr>
</table>
</form>
</body>
</html>
<%}}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../sorry.jsp"%>
<%}%>