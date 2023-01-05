<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head>
<meta name='Author' content='Jacob Feldman'/>
<title>e</title></head>
  <BODY  BACKGROUND='/feldman-root/style/type/bg.gif'>

<%request.setCharacterEncoding("windows-1251");
try{ss.x();
String c= request.getParameter("typecode");
String status="add top";

%>
<table border=0 width='100%' bgcolor=white>
<tr>
<td>
<a href='formcol.jsp?typecode=<%=c%>' target=bottom
title='add field form'
>
    <img src='/feldman-root/icon/field.gif' border=0>
            <%=ss.dict("FIELD")%>  
    
</a>
</td>
<td>
<a href='formkey.jsp?typecode=<%=c%>'  target=bottom
title='add key form'><img src='/feldman-root/icon/key.gif' border=0> 
        <%=ss.dict("KEY")%>  
    
</a>
</td>
<td>
<a href='formpic.jsp?typecode=<%=c%>'  target=bottom
title='add picture form'>
    <img src='/feldman-root/icon/picture.gif' border=0> 
            <%=ss.dict("PICTURE")%>  
    
</a></td>
<td>
<a href='formdoc.jsp?typecode=<%=c%>'  target=bottom
title='add document form'>
    <img src='/feldman-root/icon/bag.gif' border=0> 
            <%=ss.dict("DOCUMENT")%>  
    
</a></td>
</tr></table>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>