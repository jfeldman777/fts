<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head>
<meta name='Author' content='Jacob Feldman'/>
<title>e</title></head>
<%
String line="type";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";

%>
  <BODY  <%=bg%>>

<%request.setCharacterEncoding("windows-1251");
String status="params";
String sid = request.getParameter("id");
int id = Integer.parseInt(sid);
String sk = request.getParameter("k");
int k = Integer.parseInt(sk);
try{ss.x();
%>
(<%=sk%>)<%=ss.parDescType(id,k)%>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>
