<%@page language="java" contentType="text/html; charset=windows-1251" %>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
try{ss.x();


String ER = ss.ER();
String status="add col";
String line="type";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";

    String tab = request.getParameter("typecode");
    String code = request.getParameter("code");
    String name = request.getParameter("name");
    String more = request.getParameter("more");
    String tctype = request.getParameter("tctype");
    char c = tctype.charAt(0);
    ITabCol tc = ss.getTabCol(tab,code,c);
    tc.addTC(name,more);
%>
<%@include  file="../../ok2.jsp"%>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>
