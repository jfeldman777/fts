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
<script>
function f(ref,tab,col,b){
    document.forms[0].ref.value = ref;
    document.forms[0].tab.value = tab;
    document.forms[0].col.value = col;
    if(b)document.forms[0].on.value = "on";    
    document.forms[0].submit();
}

function fblind(tab,col,b){
    document.forms[1].tab.value = tab;
    document.forms[1].col.value = col;
    if(b)document.forms[1].on.value = "on";    
    document.forms[1].submit();
}

function fla(type,fx){
    document.forms[2].type.value = type;    
    if(fx)document.forms[2].on.value = "on";    
    document.forms[2].submit();
}
function fin(type,fx){
    document.forms[3].type.value = type;    
    if(fx)document.forms[3].on.value = "on";    
    document.forms[3].submit();
}
</script>
<form action=hh.jsp>
    <input id=ref type=hidden name=ref>    
    <input id=tab type=hidden name=tab>
    <input id=col type=hidden name=col>
    <input id=on type=hidden name=on>
</form>
<form action=hhblind.jsp>  
    <input id=tab type=hidden name=tab>
    <input id=col type=hidden name=col>
    <input id=on type=hidden name=on>
</form>

<form action=flash.jsp>  
    <input id=type type=hidden name=type>
    <input id=on type=hidden name=on>
</form>

<form action=fin.jsp>  
    <input id=type type=hidden name=type>
    <input id=on type=hidden name=on>
</form>
<%
String status="edit type form";
String tn= request.getParameter("id");
IGuiNode nn = ss.getGuiNode(tn);
int i = nn.idOrigin();


%>
<%=nn.typeImgCode()%>
<form action='edit.jsp' method=post>
<input type=hidden name=id value='<%=tn%>'>
<table border='2' cellpadding='2' cellspacing='0'>
<tr><td colspan=2 align='center'>
<input type=image src='<%=style%>edit.gif' title='edit names'>
</td><tr>
<%=nn.editForm()%>
<tr><td colspan=2 align='center'>
<input type=image src='<%=style%>edit.gif' title='edit names'>
</td><tr>
</table>
</form>
<%
//boolean ff = ss.getFlash(i);
//String check = ff?" CHECKED ":"";
//String xi = ""+i;
//String xb=ff?" false ":" true ";

boolean fin = ss.getFin(i);
String check_fin = fin?" CHECKED ":"";
String fb=fin?" false ":" true ";
%>
<!--form>
          <input type=checkbox title='flash' <%//=check%> 
          onClick='javascript:fla(<%//=xi%>,<%//=xb%>)'>
            </form-->
<form>
          <input type=checkbox title='fin' <%=check_fin%> 
          onClick='javascript:fin(<%=i%>,<%=fb%>)'>
            </form>            
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>