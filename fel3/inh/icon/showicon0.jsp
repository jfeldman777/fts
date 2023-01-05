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
String status="control icon";

%>
<a href='showicon2.jsp'><img src='<%=style%>sql.gif' title='set final' border=0></a>
<hr>
<form action='deleteicon.jsp' method=post>
<%=ss.showFreeIcons()%>
<input type=image 
src='<%=style%>delete.gif' title='delete icon'>
</form>
<hr>
<form ACTION=form.jsp method=get>
upload for name:<input type=text name=name>
<input type=image 
src='<%=style%>new.gif' title='upload new icon'  align=absbottom>
</form>
<hr>
<form ACTION=rename.jsp method=post>
rename icon:<input type=text name=name> to <input type=text name=name2>
<input type=image 
src='<%=style%>edit.gif' title='rename icon' align=absbottom>
</form>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>
