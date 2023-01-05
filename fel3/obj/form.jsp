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
String line="obj";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/obj/bg.gif' ";
%>
  <BODY <%=bg%>>


<%
String status="before upload";
String tn= request.getParameter("id");
String tab = request.getParameter("tab");
String col = request.getParameter("col");
String tctype = request.getParameter("tctype");
IGuiNode nn = ss.getGuiNode(tn);
//ss.markUpdate(nn.id());
boolean img = tctype.charAt(0)=='p';
String fpath = nn.fpath(tab,col,tctype.charAt(0));
ITabCol tc = ss.getTabCol(tab,col,tctype.charAt(0));

%>
<%=nn.face()%>::<%=tc.getRus()%>
<hr>
<a href='delete.jsp?path=<%=fpath%>' 
    title='delete <%=img?"picture":"file"%> from server'>
<img src='<%=style%>delete.gif' border=0>
</a>
<hr>
<img src='<%=style%>find.gif' title='find'><img src='/feldman-root/style/obj/up.gif' title='upload'>
<FORM ACTION="post.jsp" METHOD=POST ENCTYPE="multipart/form-data">
<input type=hidden value='<%=fpath%>' name='path'>
  <INPUT TYPE=FILE NAME=file>

  <INPUT TYPE=SUBMIT value='Upload?'>  
</FORM>

</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../sorry.jsp"%>
<%}%>