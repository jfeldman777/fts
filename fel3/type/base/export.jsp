<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head><title>form</title>
<meta name='Author' content='Jacob Feldman'/>
</head>

<%request.setCharacterEncoding("windows-1251");
try{ss.x();
String line="type";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String status="export base form";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";

String tn = request.getParameter("id");
IGuiNode n = ss.getGuiNode(tn);
%>
<body <%=bg%>>
<FORM>
<textarea name='txt' cols="80" rows="20"><%=n.exportBaseVal()%>
</textarea>

</FORM>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>
