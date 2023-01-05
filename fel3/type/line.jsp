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

String status="type line";
String tab = request.getParameter("tab");
String col = request.getParameter("col");
String tctype = request.getParameter("tctype");
String sid = request.getParameter("id");
int i = Integer.parseInt(sid.substring(1));
char c = (tctype==null?'c':tctype.charAt(0));
ITabCol tc=ss.getTabCol(tab,col,c);

String sx = (c=='t'?tc.value(i):tc.getRus());
String asx = Field.apo(sx);

String sx2 = tc.getRus();
String asx2 = Field.apo(sx2);
String tabcol = tab+"::"+col;
boolean oCode=tabcol.equals("object::code");
%>
<%if(!oCode){%>
<table border=0 width='100%'>

<form action='editline.jsp' method=post>
        <input type=hidden name=tab value='<%=tab%>'>
        <input type=hidden name=col value='<%=col%>'>
        <input type=hidden name=tctype value='<%=tctype%>'>
        <input type=hidden name=id value='<%=i%>'>
        <tr>
<td>
<%=tab%>::<%=col%>
<%if(c=='t'){%>
(<%=asx2%>)
<%}%>
</td>
</tr>
<tr>
<td>
<textarea cols='50' rows='5' name='val'>
<%=asx%>
</textarea>
</td>
</tr>
<tr>
<td>
<input type=image 
src='<%=style%>edit.gif' 
title='edit name'>
</td>
</tr>
</form>

</table>
<%}%>
<%if(tab.equals("type")){%>
<table border=0 width='100%'>
<form action='editline.jsp' method=post>
        <input type=hidden name=tab value='<%=tab%>'>
        <input type=hidden name=col value='<%=col%>'>
        <input type=hidden name=tctype value='c'>
        <input type=hidden name=id value='<%=i%>'>
        <tr>
<td>
<%=tab%>::<%=col%>
</td>
</tr>
<tr>
<td>
<textarea cols='50' rows='5' name='val'>
<%=asx2%>
</textarea>
</td>
</tr>
<tr>
<td>
<input type=image 
src='<%=style%>edit.gif' 
title='edit name'>
</td>
</tr>
</form>

</table>    
    
    
<%}%>
<%if(!tab.equals("type")){%>
<form action='do/shadow.jsp' method=post>
<input type=hidden name='col' value='<%=col%>'>
<input type=hidden name='tab' value='<%=tab%>'>
<input type=hidden name='tctype' value='<%=tctype%>'>
Yellow:
<input type=checkbox name=sh <%=ss.isShadow(tab,col)?"CHECKED":""%>>
<br><input type=image src='<%=style%>edit.gif' 
title='yes/no'>
</form>
<hr>
<%}%>
<%if(c=='c'&&!tab.equals("type")){%>
<form action='do/unique.jsp' method=post>
<input type=hidden name='col' value='<%=col%>'>
<input type=hidden name='tab' value='<%=tab%>'>

Unique:
<input type=checkbox name=uq <%=tc.getUnique()?"CHECKED":""%>>
<br><input type=image src='<%=style%>edit.gif' 
title='yes/no'>
</form>
<hr>
<%if(tc.keyCol())//(tc.nQuiz()>=0)
{%>
<form action=ulist.jsp method=post>
<input type=hidden name='tab' value='<%=tab%>'>
<input type=hidden name='col' value='<%=col%>'>
<%=tc.getUform()%>
<input type=image src='<%=style%>edit.gif' 
title='edit list of variants'>
</form>
<%}%>
<%}%>

</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../sorry.jsp"%>
<%}%>