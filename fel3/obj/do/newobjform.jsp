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
String line="obj";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";

String status="before new";
String list = request.getParameter("list");
String typecode = request.getParameter("type");
String addr = request.getParameter("addr");
if(list.equals("70"))typecode=ss.type2code(Integer.parseInt(addr));


String tn = request.getParameter("parent");
int i = Integer.parseInt(tn);
IGuiNode nn = ss.getGuiNode(i);



        long LL = System.currentTimeMillis();
        System.out.println("form:"+LL+":"+list);


if(list.equals("50")){
%>
<jsp:forward page="copyform.jsp">
<jsp:param name="addr" value="<%=addr%>"/>
<jsp:param name="parent" value="<%=nn.idOrigin()%>"/>
</jsp:forward>
<%
}else if(!list.equals("10")&&!list.equals("70")){
%>
<jsp:forward page="list.jsp">
<jsp:param name="type" value="<%=typecode%>"/>
<jsp:param name="addr" value="<%=addr%>"/>
<jsp:param name="parent" value="<%=tn%>"/>
<jsp:param name="list" value="<%=list%>"/>
</jsp:forward>
<%}%>
<%
if(typecode.equals("-1"))throw new ExTypeId();
%>
  <BODY <%=bg%>>
<%=nn.faceName()%>      
<script>var id=<%=i%>;</script>
<script src="../opentable.js"></script>

<form action='newobj.jsp' method=post>
<table border='2'>
<tr><td align='center'>
<input type=image src='<%=style%>new.gif'></td><td>
<%=ss.typeImgCodeName(typecode)%>
</td></tr>
<%=nn.addForm(typecode)%>
<tr><td align='center'>
<input type=image src='<%=style%>new.gif'>
</td><td>
<%=ss.typeImgCodeName(typecode)%>
</td></tr>
</table>
</form>

</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>