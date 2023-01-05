<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head><title>a type menu</title>
<meta name='Author' content='Jacob Feldman'/>
</head>
<BODY  
 onLoad="status='change type for the object'"
BACKGROUND='/feldman-root/style/obj/bg.gif'>


<%request.setCharacterEncoding("windows-1251");
try{ss.x();
String line="obj";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String status="ntype tree";

String tn = request.getParameter("xid");
if(tn!=null)ss.initNTree(tn);
%>
<img src='<%=style%>type.gif'>
<br><%=ss.nTypeObj()%><hr>
<table cellpadding='0' cellspacing='0' border='0' bgcolor=white>
<%=ss.showTree("n")%>
</table>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>
