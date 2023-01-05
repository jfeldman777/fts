<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<%@page import = "j5feldman.profile.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />

<%request.setCharacterEncoding("windows-1251");
try{ss.x();
String line="obj";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";

String status="before edit profile";
Profile pf = ss.profile;

%>
<html>
<meta name='Author' content='Jacob Feldman'/>
<title>e</title></head>
  <BODY <%=bg%>>
      <table border=0>
  <tr><td>    
<form action='edit.jsp' method=post>
<%=pf.boxes()%>
<input type=image src='<%=style%>edit.gif'>
</form>
</td><td>
<form action='edit2.jsp' method=post>
<%=pf.boxesAr()%>
<input type=image src='<%=style%>edit.gif'>
</form>
</td>
</tr>
</table>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>