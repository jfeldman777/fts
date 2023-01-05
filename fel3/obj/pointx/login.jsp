<%@page language="java" contentType="text/html; charset=windows-1251" %>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
try{ss.x();
String er = request.getParameter("er");
if(er!=null) ss.setER(er);
String status="edit pointx login";
String dbconfig= request.getParameter("dbconfig");
String schema= request.getParameter("schema");
String root= request.getParameter("root");
String sid = request.getParameter("id");
String pwd= request.getParameter("pwd");


%>
<jsp:forward page='editform.jsp'>
<jsp:param name='object::id' value="<%=sid%>"/>
<jsp:param name='dbconfig' value="<%=dbconfig%>"/>
<jsp:param name='root' value="<%=root%>"/>
<jsp:param name='schema' value="<%=schema%>"/>
<jsp:param name='code' value="<%=pwd%>"/>
</jsp:forward>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>

