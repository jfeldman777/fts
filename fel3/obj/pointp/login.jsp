<%@page language="java" contentType="text/html; charset=windows-1251" %>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
try{ss.x();


String er = request.getParameter("er");
if(er!=null) ss.setER(er);
String status="point p";
String dbconfig= request.getParameter("dbconfig");
String schema= request.getParameter("schema");
String root= request.getParameter("root");
String sid = request.getParameter("id");
String code= request.getParameter("pwd");
String par="?id="+sid+
    "&root="+root+
    "&schema="+schema+
    "&dbconfig="+dbconfig+"&code="+code;

%>
<HTML>
  <HEAD>
    <TITLE>package</TITLE>
  </HEAD> 
<frameset cols='30%,*'>
<frame name=left src='left.jsp<%=par%>'></frame>
<frame name=right src='../blank.html'></frame>
</frameset>
</HTML>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>

