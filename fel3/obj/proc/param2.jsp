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
  <BODY  <%=bg%>>

<%
String status="params";
String tn = request.getParameter("id");
IGuiNode nn = ss.getGuiNode(tn);

boolean shadow = nn.isShadow();
boolean pseudo = nn.isPseudo(); 
int oid = nn.idOrigin();
String code = nn.typeCode();
%>
<img src='<%=style%>sql.gif'><p>
<%=ss.faceObj(oid)%>
<hr>
<table border=2>
<%=ss.procDesc(oid,true)%>
</table>
<a href='frame.jsp?id=<%=tn%>' target=right><<<<<<<<<<</a>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>
