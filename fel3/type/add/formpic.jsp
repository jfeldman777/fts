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
String c = request.getParameter("typecode");
String status="add pic form";

%>
<%=ss.typeImgCodeName(c)%><hr>

<form action='add.jsp' method=post>
<input type=hidden name=typecode value='<%=c%>'>
<input type=hidden name=tctype value='p'>
<table border=1 cellpadding='5' width='100%'>
<tr><td align=right>
<img src='/feldman-root/icon/picture.gif'>
            <%=ss.dict("PICTURE")%>  
    
</td>
   <td><input type=image src='<%=style%>add.gif' 
title='add picture'></td></tr>
<tr><td align=right>
            <%=ss.dict("code")%>  
    
</td>
   <td><input type=text name=code></td></tr>
<tr><td align=right>
            <%=ss.dict("name")%>  
    
</td>
   <td><input type=text name=name></td></tr>
</td></tr>
</table>
</form>

</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>