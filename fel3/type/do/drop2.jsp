<%@page language="java" contentType="text/html; charset=windows-1251" %>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
try{ss.x();
long L1 = System.currentTimeMillis();
String status="drop col";
String line="type";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";

    String tabcol = request.getParameter("tabcol");
    String tctype = request.getParameter("tctype");
    char c = tctype.charAt(0);
    ITabCol tc = ss.getTabCol(tabcol,c);
    tc.drop();
            long L2 = System.currentTimeMillis();
            status+=":"+(L2-L1)+" msec:";        
%>
<%@include  file="../../../ok.jsp"%>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../../sorry.jsp"%>
<%}%>