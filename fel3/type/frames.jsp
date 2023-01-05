<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head>
<meta name='Author' content='Jacob Feldman'/>
<title>Types</title></head>


<%request.setCharacterEncoding("windows-1251");
try{ss.x();
String line="type";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
String status="type frames";

%>
<frameset cols='250,*'>

<frameset rows='30,*'  border=0>
<frame name=left3 src='../lang/<%=ss.ER()%>frame3.html'  scrolling=no></frame>
<frame name=left src='left.jsp'></frame>
</frameset>

<frame name=right src='blank.html'></frame>
</frameset>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../sorry.jsp"%>
<%}%>