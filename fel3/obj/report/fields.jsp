<%@page language="java" contentType="text/html; charset=windows-1251" %>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
try{ss.x();

String status="set field";

    String sid = request.getParameter("id");
    String sPos = request.getParameter("pos");
    int pos = Integer.parseInt(sPos);
    String sOrd = request.getParameter("ord");
    int ord = Integer.parseInt(sOrd);
    String col = request.getParameter("col");

    ss.setFieldsTab(sid,pos,ord,col);
%>
<jsp:forward page="menu.jsp"/>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>