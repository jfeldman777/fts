<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
try{ss.x();

String status="publish";

String sid = request.getParameter("id");
    IGuiNode nn = ss.getGuiNode(sid);
    int ID = nn.id();
%>
<html>
<head>
<title>main</title>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
</head>

<frameset rows='50%,*'>
<frame name=1 src=param2.jsp?id=<%=sid%>></frame>
<frame name=2 src='bottom2.jsp?id=<%=ID%>'></frame>
</framest>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>