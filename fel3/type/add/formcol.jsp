<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<%@page import = "j5feldman.proc.basement.DbFormat"%>
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
String status="add col form";
String c = request.getParameter("typecode");

%>
<%=ss.typeImgCodeName(c)%><hr>
<form action='add.jsp' method=post>
<input type=hidden name=typecode value='<%=c%>'>
<input type=hidden name=tctype value='c'>
<table border=1 cellpadding='5' width='50%'>
<tr><td align=right>
   <img src='/feldman-root/icon/field.gif'>
   <%=ss.dict("FIELD")%>          
</td>
   <td><input type=image src='<%=style%>add.gif' 
title='add field'></td></tr>
<tr><td align=right>
    <%=ss.dict("code")%>  
</td>
   <td><input type=text name=code></td></tr>
<tr><td align=right>
    <%=ss.dict("name")%>      
</td>
   <td><input type=text name=name></td></tr>
<tr><td align=right>
    <%=ss.dict("datatype")%> 
</td>
    <td><select name=more><%=DbFormat.getList()%></select>
</td></tr>

</table>
</td><td>
</form>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>