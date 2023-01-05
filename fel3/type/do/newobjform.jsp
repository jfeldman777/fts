<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head>
<meta name='Author' content='Jacob Feldman'/>
<title>new type</title></head>
<%
String line="type";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
%>
  <BODY <%=bg%>>

<%

request.setCharacterEncoding("windows-1251");
String status="new type form";
try{ss.x();
String tn = request.getParameter("id");
IGuiNode nn = ss.getGuiNode(tn);
%>
<%=nn.typeImgCode()%>
<hr>
<form action='newobj.jsp' method=post>
<input type=hidden name=parent value='<%=tn%>'>
<table><tr><td>
    <table border=0>
<tr><td align=right>
    <%=ss.dict("code")%> 
    
</td>     
<td><input type=text name=code id=c></td></tr>
<tr><td align=right>
    <%=ss.dict("name")%> 
        
</td>  
<td>
    <input type=text name=name>
</td>
</tr>
<tr>
    <td align=right  width=200>
        <%=ss.dict("inherited from")%> 
        
    </td> 
<td>
    <input type=text name=inh value=object>
</tr>

        <tr>
    <td align=right><%=ss.dict("final")%> 
               
                </td><td align="right">
 <input type=CHECKBOX CHECKED name=fin>                    
<input type=image 
src='<%=style%>new.gif' title='create new type'>
</td>
</tr>
    </table>
    </td><td>
    <table border=0>
        <tr valign="top"><td><div align="center">
            <%=ss.dict("inherited from")%>:
        </div><hr>
        
        <%=ss.showTypeIcons(false)%><hr>
        </td></tr>
    </table>
    </td></tr>

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