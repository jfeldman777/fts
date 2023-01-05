<%@page language="java" contentType="text/html; charset=windows-1251" %>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%
request.setCharacterEncoding("windows-1251");
try{ss.x();
long L1 = System.currentTimeMillis();
String status="set name";
String line="type";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";

    String tn = request.getParameter("id");
    String path = request.getParameter("path");

    IGuiNode nn = ss.getGuiNode(tn);
    nn.setWTpath(path);
            long L2 = System.currentTimeMillis();
            status+=":"+(L2-L1)+" msec:";        
%>
<%@include  file="../../ok.jsp"%>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>
