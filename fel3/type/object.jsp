<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head>

<%request.setCharacterEncoding("windows-1251");//("UTF-8");//
try{ss.x();
String status="type";
String line="type";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
String tn= request.getParameter("id");

IGuiNode nn = ss.getGuiNode(tn);
int i = nn.id();
int io = nn.idOrigin();
ss.setMillis();
%>
<meta name='Author' content='Jacob Feldman'/>
<title>Types</title></head>
<body <%=bg%>>
<table bgcolor=white><tr><td>
    <table border=0 width='100%' <%=bg%>>
        <tr><td><%=nn.typeImgCode()%></td>

            <td align=right>
                <a href='root.jsp?id=T<%=i%>' target=left title='set tree root here/корень здесь'>
                <img src='/feldman-root/style/type/here.gif' border=0></a>

                <a href='oldroot.jsp' target=left title='back to root/восстановить корень'>
                <img src='/feldman-root/style/type/back.gif' border=0></a>

<a href='root.jsp?id=T<%=io%>' target=left title='go to origin/к оригиналу'>
                <img src='/feldman-root/style/type/left.gif' border=0></a>        
                

                
                <a href='rootup.jsp?id=<%=i%>' target=left title='lift tree root up/вверх по дереву'>
                <img src='/feldman-root/style/type/up.gif' border=0></a>
                

                
                <a href='path.jsp?id=<%=i%>' target=left title='show path/проследить путь'>
                <img src='/feldman-root/style/type/down.gif' border=0></a>
                
                
                <a href='../inh/root.jsp?id=H<%=i%>' target=left title='to inh tree/к зеленому дереву'>
                <img src='/feldman-root/style/type/right.gif' border=0></a>
            </td>
        </tr>
    </table>

    <table border=2 width='100%'><%=nn.boxView()%></table>
<%if(ss.notDemo()){%>
    <%if(ss.isZ()){%>
    <table border=0 width='100%' <%=bg%>>


        <tr align=center>
            <td><a href='do/newobjform.jsp?id=<%=tn%>' title='create new type here'>
                <img src='<%=style%>new.gif' border=0></a>
            </td>            
            <td><a href='do/editform.jsp?id=<%=tn%>' title='edit col names, drop columns'>
                <img src='<%=style%>edit.gif' border=0></a>
            </td>
            <td><a href='export.jsp?id=<%=tn%>' >
                <img src='<%=style%>export.gif' border=0 title='objects export'></a>


            </td>
        </tr>
        <%if(i>0){%>
        <tr  align=center>
            <td><a href='do/delete.jsp?id=<%=tn%>' title='drop the type'>
                <img src='<%=style%>delete.gif' border=0></a>
            </td>
            <td><a href='add/addform.jsp?id=<%=tn%>' title='add fields, keys, files, photos'>
                <img src='<%=style%>add.gif' border=0></a>
            </td>
            <td><a href='base/object.jsp?id=<%=tn%>' title='about base values'>
                <img src='<%=style%>base.gif' border=0></a>
            </td>
        </tr>
        <%}%>
    </table>
    <table border=2 width='100%' <%=bg%>>
           
        <%if(i>0){%>
        <tr>

            <form action='do/move.jsp'  method=post>
            <td width=103 valign=baseline>
                <input type=hidden name=id value='<%=tn%>'>
                <input type=image src='<%=style%>move.gif' 
                    title='move the type under this address'>
            </td>
            <td>
                <input type=text name=parent>
            </td>                
        </form>

        </tr>

        <tr>
            <td width=103 valign=baseline>
                <form action='do/shortcut.jsp'  method=post>
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
        <%if(tn.equals("t0")){%>
        <tr>
            <form action='do/max.jsp' method=post>
                <td valign=baseline>        
                    <input type=image src='<%=style%>list.gif' 
                title='set pages'>
            </td>
            <td><input type=text name=mm value='<%=ss.getMax()%>' 
                    title='page;page2'>
            </td>
            </form>
        </tr>

        <tr>
        <form action='do/admpwd.jsp'  method=post>
            <td valign=baseline>
                <input  type=image src='<%=style%>password.gif' title='set admin password'>
            </td>
            <td><input type=password name=pwd>
            </td>
        </form>

        </tr>
        <tr valign=baseline>
        <form action=find.jsp method=post>
            <td>
               <input type=image src='<%=style%>find.gif'
                   title='put criteria on the right'>
            </td>
            <td><input type=text name=criteria>
                <input type=checkbox name=sh title='look for shortcuts: o.code'>
            </td>
         </form>
        </tr>
        <%}%>
    </table>
    <%}%>
    <table border=2 width='100%'><%=nn.boxTypeView()%></table>
</td></tr>
</table>
<%}%>
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