<%@page language="java" contentType="text/html; charset=windows-1251" %>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
try{ss.x();
long L1 = System.currentTimeMillis();
String ref= request.getParameter("ref");
String tab= request.getParameter("tab");
String col= request.getParameter("col");
String on= request.getParameter("on");

String status="hh";
String line="type";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";

    ss.hh(ref,tab,col,on);
            long L2 = System.currentTimeMillis();
            status+=":"+(L2-L1)+" msec:";        
%>
<%@include  file="../../ok.jsp"%>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>
