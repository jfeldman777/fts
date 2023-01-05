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
String line="inh";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
%>
  <BODY <%=bg%>>

<%
String tn = request.getParameter("id");
IGuiNode nn = ss.getGuiNode(tn);
String status="show icon";



    
    if(ss.notDemo()&&tn.equalsIgnoreCase("h0")){
%>
<jsp:forward page="showicon0.jsp"/>
<%
    }
%>
<%if(nn.isAbstr()){%><a href='../proc/frame.jsp?id=<%=nn.id()%>'><%}%>
    <%=nn.typeImgCode()%>
<%if(nn.isAbstr()){%></a><%}%>
<hr>
<form action='seticon.jsp' method=post>
<input type=hidden name=id value='<%=tn%>'>
<%=nn.showIcons()%>
<%if(ss.notDemo()){%>
<input type=image 
src='<%=style%>edit.gif' title='set icon for the type'>
<%}%>
</form>

</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>
