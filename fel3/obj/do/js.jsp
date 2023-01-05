<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
try{ss.x();
String line="obj";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";


String status="before edit";
String tn= request.getParameter("id");
IGuiNode nn = ss.getGuiNode(tn);
//String name = nn.name();//studentname
//String typecode = nn.typeCode();//oid
//String code = nn.code();
//String er = ss.ER();
String sid = ""+nn.id();
int type = nn.type();
String path = "/feldman-root/system/"+ss.getSchema()+"/js/"+type+".htm?id="+sid;
%>
<html>
<head>
<meta name='Author' content='Jacob Feldman'/>
    <META http-equiv="refresh"
    content="1;URL=<%=path%>">
<title>e132</title></head>
  <body  >
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>

