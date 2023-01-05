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
<body <%=bg%>>

<%
String typecode = request.getParameter("type");
String status="sql";

%>
<form action='../obj/findme.jsp' method=post>
<input type=image src='<%=style%>sql.gif' title='execute'>
<br>
<textarea name=crit cols="60" rows="20">
    <%=ss.mySql(typecode)%>
</textarea>
 <input type=hidden name=type value=<%=typecode%>>
<br>
</form>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../sorry.jsp"%>
<%}%>