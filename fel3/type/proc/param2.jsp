<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head>
<meta name='Author' content='Jacob Feldman'/>
<title>e</title></head>


<%request.setCharacterEncoding("windows-1251");
String line="type";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
String status="params";
String tn = request.getParameter("id");
IGuiNode nn = ss.getGuiNode(tn);
int oid = nn.idOrigin();
    String code = nn.typeCode();
try{ss.x();
%>
  <BODY  <%=bg%>>
<%=nn.typeImgCode()%>
<table border=2>
<%=ss.procTypeDesc(oid,true)%>
</table>
<a href='frame.jsp?id=<%=tn%>' target=right><<<<<<<<<</a>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>
