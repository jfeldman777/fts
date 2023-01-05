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
String status="add key form";

%>
<%=ss.typeImgCodeName(c)%><hr>

<form action='add.jsp' method=post>
<input type=hidden name=typecode value='<%=c%>'>
<input type=hidden name=tctype value='c'>
<table border=1 cellpadding='5'>
<tr>
   <td align=right><img src='/feldman-root/icon/key.gif'>
           <%=ss.dict("KEY")%>  
       
   </td>
   <td><input type=image src='<%=style%>add.gif' title='add key'></td>
</tr>
<tr>
   <td align=right>
        <%=ss.dict("code")%>  
   </td>
   <td><input type=text name=code id=code></td>
</tr>
<tr>
   <td align=right>
       <%=ss.dict("name")%>  
   </td>
   <td><input type=text name=name id=name></td>
</tr>
<tr>
    <td align=right>
        <%=ss.dict("reference")%>          
    </td>
    <td><input name='more'  id=inh></td>
</tr>
</table>
</form>
<form>
<%=ss.showTypeIcons(true)%>
<form>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>