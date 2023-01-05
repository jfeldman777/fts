<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />


<%request.setCharacterEncoding("windows-1251");
try{ss.x();

String status="object";
ss.setMillis();

String tn= request.getParameter("id");
IGuiNode nn = ss.getGuiNode(tn);
int i=nn.id();
int io = nn.idOrigin();
boolean pseudo=nn.isPseudo();
boolean shadow=nn.isShadow();
String c = nn.typeCode();
String line="obj";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
%>
<html>
<head>
<meta name='Author' content='Jacob Feldman'/>
<title>Objects</title>
</head>
<body <%=bg%> >
<table bgcolor=white>
<tr><td>
    <table border=0 width='100%' <%=bg%>>
        <tr><td><%=nn.typeImgCode()%></td>
         <%if(ss.isZ()){%>
                <td align="right"><a href='root.jsp?id=A<%=io%>' target=left title='to origin'>
                <img src='/feldman-root/style/obj/left.gif' border=0></a>
                </td>
                <%}%> 
            <!--td align=right><a href='root.jsp?id=A<%=i%>' target=left title='set tree root here'>
                <img src='/feldman-root/style/obj/here.gif' border=0></a>
            </td-->
        </tr>
    </table>
<table border='2' width='100%'> 
<%=nn.boxView(true)%>
</table>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../sorry.jsp"%>
<%}%>