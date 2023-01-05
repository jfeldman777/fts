<%@page language="java" contentType="text/html; charset=windows-1251" %>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
try{ss.x();
String id = request.getParameter("id");
String status="tree pub";
String line="obj";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
%>
  <BODY  BACKGROUND='/feldman-root/style/obj/bg.gif'>
<a title='You are about to PUBLISH a giant TREE'>
<img src="/feldman-root/icon/attention.gif" 
        width="400" height="150" usemap="#m" border="0"> 
</a>
<map name="m"> 
  <area shape="rect" coords="3,105,86,145" 
    href="trteepub2.jsp?id=<%=id%>" 
    title="do it anyway">
  <area shape="rect" coords="318,103,368,137" 
        href="javascript:top.history.back()" title="no, my mistake">
</map>
</body>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>