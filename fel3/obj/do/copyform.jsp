<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
try{ss.x();
String line="obj";
String ER = ss.ER();
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";


String status="before edit";
String parent= request.getParameter("parent");
String tn= request.getParameter("addr");

    IGuiNode nn = ss.getGuiNode(tn);
%>
<html>
<head>
<script>var id=<%=nn.id()%>; 
</script>
<script src="../opentable.js"></script>
<meta name='Author' content='Jacob Feldman'/>
<title>e</title></head>
  <BODY <%=bg%>>
<%=nn.faceName()%>         
<form action='newobj.jsp' method=post>
<input type=hidden name=parent value='<%=parent%>'>
<table border='2' cellpadding='2' cellspacing='0'>
    
<tr>
<td>
<%=nn.typeImgCode()%>
</td>
<td align='center'>
<input type=image src='<%=style%>edit.gif'>
</td>
</tr>
<%=nn.editForm()%>
<tr>
<td>
<%=nn.typeImgCode()%>
</td>
<td align='center'>
<input type=image src='<%=style%>edit.gif'>
</td>
</tr>
</table>
</form>
</BODY>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>