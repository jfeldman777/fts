<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head><title>form</title>
<meta name='Author' content='Jacob Feldman'/>
</head>



<%request.setCharacterEncoding("windows-1251");
try{ss.x();

String status="upload icon form";

String name = request.getParameter("name");
String line= "inh";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
%>
  <body <%=bg%>>
icon for name:<b><%=name%></b>:
<FORM ACTION="post.jsp" METHOD=post ENCTYPE="multipart/form-data">
<input type=hidden value='<%=name+".gif"%>' name='path'>
  <INPUT TYPE=FILE NAME=file>
  <INPUT TYPE=SUBMIT value='Upload?'>  
</FORM>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>
