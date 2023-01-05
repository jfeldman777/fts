<%@page language="java" contentType="text/html; charset=windows-1251" %>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
            long L1 = System.currentTimeMillis();
String status="upload:";
String line="obj";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
int L=-1;
try{
    L = (int)ss.upload(request);
    status+=""+L+" bytes were uploaded";
            long L2 = System.currentTimeMillis();
            status+=":"+(L2-L1)+" msec:";        
%>
<%@include  file="../ok.jsp"%>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../sorry.jsp"%>
<%}%>

