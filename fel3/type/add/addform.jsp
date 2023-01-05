<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
try{ss.x();


String status="frameset";
String line="type";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
String tn= request.getParameter("id");
IGuiNode nn = ss.getGuiNode(tn);

%>

<html><head></head>
<frameset rows='55,*'>
<frame name=top1 src='top.jsp?typecode=<%=nn.typeCode()%>'></frame>
<frame name=bottom src='formcol.jsp?typecode=<%=nn.typeCode()%>'></frame>
</frameset>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>