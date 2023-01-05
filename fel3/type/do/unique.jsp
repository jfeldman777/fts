<%@page language="java" contentType="text/html; charset=windows-1251" %>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
            long L1 = System.currentTimeMillis();
String status="set unique";
String line="type";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
try{ss.x();
    String tab = request.getParameter("tab");
    String col = request.getParameter("col");
    String val = request.getParameter("uq");
    boolean v = val!=null;
    ITabCol tc=ss.getTabCol(tab,col,'c');
    tc.setUnique(v);
            long L2 = System.currentTimeMillis();
            status+=":"+(L2-L1)+" msec:";        
%>
<%@include  file="../../ok.jsp"%>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>
