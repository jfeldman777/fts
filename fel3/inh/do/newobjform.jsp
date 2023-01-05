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
String line="inh";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
%>
  <BODY <%=bg%>>

<%
String status="new type form";

String tn = request.getParameter("id");
IGuiNode nn = ss.getGuiNode(tn);
%>
<%=nn.typeImgCode()%>
<hr>
<form action='newobj.jsp' method=post>
<input type=hidden name=parent value='<%=tn%>'>
<table>
<tr><td align=right>
            <%=ss.dict("code")%>  
    
</td>     
<td><input type=text name=code></td></tr>
<tr><td align=right>
            <%=ss.dict("name")%>  
    
</td>  
<td><input type=text name=name></td></tr>

<tr><td colspan=2 align=right>
<input type=image 
src='<%=style%>new.gif' title='create abstract type'>
</td><tr>
</table>
<%=nn.showIcons()%>
</form>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>