<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />

<%request.setCharacterEncoding("windows-1251");
try{ss.x();
  
String status="type";
String line="inh";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";

String tn= request.getParameter("id");
IGuiNode nn = ss.getGuiNode(tn);
int i = nn.id();
int io= nn.idOrigin();
String typecode = nn.typeCode();
ss.setMillis();
int ifolder = i==2?0:i;
    %>
<html>
<head>
    

<meta name='Author' content='Jacob Feldman'/>
<title>Inheritance</title></head>
<body <%=bg%>>

<table bgcolor='white'><tr><td>
<table border=0 width='100%' <%=bg%>>
<tr><td>
<%=nn.typeImgCode()%>
</td>
<td align=right>
                <a href='root.jsp?id=H<%=i%>' target=left title='set tree root here/корень здесь'>
                <img src='/feldman-root/style/inh/here.gif' border=0></a>

                <a href='oldroot.jsp' target=left title='back to root/восстановить корень'>
                <img src='/feldman-root/style/inh/back.gif' border=0></a>



                <a href='root.jsp?id=H<%=io%>' target=left title='go to origin/к оригиналу'>
                <img src='/feldman-root/style/inh/left.gif' border=0></a>
                


                <a href='rootup.jsp?id=<%=i%>' target=left title='lift tree root up/вверх по дереву'>
                <img src='/feldman-root/style/inh/up.gif' border=0></a>


                
                <a href='path.jsp?id=<%=i%>' target=left title='show path/проследить путь'>
                <img src='/feldman-root/style/inh/down.gif' border=0></a>

                <a href='../type/root.jsp?id=T<%=ifolder%>' target=left title='type tree/к синему дереву'>
                <img src='/feldman-root/style/inh/right.gif' border=0></a>

</td>
</tr>

</table>
<%if(!nn.isAbstr()){%>
    <table border=3 width='100%'><%=nn.boxView()%></table>
<%}%>
<%if(ss.notDemo()){%>
<table border=0 width='100%' <%=bg%>>
<%
if(tn.equals("h0")){
%>

<tr>

<form action=find.jsp method=post>
<td valign='baseline'>
<input type=image src='<%=style%>find.gif'
 title='put criteria on the right' ></td><td>
<input type=text name=criteria>
<input type=checkbox name=sh title='look for shortcuts: o.code'>
</td>
</form>
</tr>
<%}
if(ss.isZ()){if(!nn.isAbstr()){
%>
<%if(i==0){%>
<tr>
<form action=file2area.jsp method=post>
<td valign='baseline'>
<input type=image src='<%=style%>script.gif'
 title='execute from file or open area for script/sql' ></td>
<td>
<input type=file name=path>
<input type=hidden name=utf value=ON>
</td>
</form>
</tr>

<%}else{%>
<tr>
<form action=mysql.jsp method=post>
<td valign='baseline'>
<input type=hidden name=id value=<%=i%>>
<input type=image src='<%=style%>sql.gif'
 title='select for the type' >
 <input type=hidden name=type value=<%=typecode%>>
 </td>
</form>
</tr>
<tr>
<form action=db2area.jsp method=post>
<td valign='baseline'>
<input type=image src='<%=style%>export.gif'
 title='export the type' ></td>
<td>
<input type=hidden name=id value=<%=i%>>
</td>
</form>
</tr>
<%}%>
    <tr>
            
    <form action='do/abc.jsp'  method=post>
            <td valign=baseline>
                <input type=hidden name='id' value='<%=tn%>'>
                <input type=image src='<%=style%>abc.gif'
                    title='set abc field order'>
            </td>
            <td><input type=checkbox name=abc <%=nn.isAbc()?"CHECKED":""%>>
   </td>          
   </form>
            
    </tr>
<%}
if(i>0){%>
<tr valign=baseline>
<form action='do/move.jsp' method=post>
<td width=103>
<input type=hidden name=id value='<%=tn%>'>
<input  type=image src='<%=style%>move.gif'
 title='inherit it from this address'>
</td><td>
<input type=text name=parent>
</td>
</form>
</tr>
<%if(!nn.isAbstr()){%>
<tr>
<form action='do/shortcut.jsp' method=post>
<td width=103 valign=baseline>
<input type=hidden name=id value='<%=tn%>'>
<input type=image src='<%=style%>shortcut.gif' 
title='make shortcut under this address'>
</td>
<td>
<input type=text name=parent>
</td>
</form>
</tr>
<%}%>
<%if(nn.isShadow()||nn.isAbstr()){%>
<tr>
<td>
<a href='do/delete.jsp?id=<%=tn%>' title='drop the type'>
<img src='<%=style%>delete.gif' border=0>
</a>
</td>
</tr>
<%}

if(!nn.isShadow()){%>
<tr>

<form action='do/name.jsp' method=post>
    <td valign=baseline>
        <input type=image src='<%=style%>name.gif' title='the name of the type'>
</td>
<td>
    <input type=text name=name value='<%=nn.name()%>'>
        <input type=hidden name=id value='<%=tn%>'>
  </td>    
  </form>
    
</tr>
<%}}
if(i!=2){
%>
<tr>
<td>
    <a href='do/newobjform.jsp?id=<%=tn%>' title='create abstract type here'>
    <img src='<%=style%>new.gif' border=0></a>
</td></tr>
<%

    }}%>
</table>
<%}%>
</td></tr></table>
&nbsp;&nbsp;msec = <%=ss.getMillis()%>
&nbsp;&nbsp;user = <%=ss.getUser()%>
&nbsp;&nbsp;schema = <%=ss.getSchema()%>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../sorry.jsp"%>
<%}%>