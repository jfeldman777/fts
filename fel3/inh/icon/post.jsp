<%@page language="java" contentType="text/html; charset=windows-1251" %>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
try{ss.x();

            long L1 = System.currentTimeMillis();
int L=-1;
String status="upload icon:";
String line="inh";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";

    L=(int)ss.uploadIcon(request);
    status+=L+" bytes were uploaded ";
            long L2 = System.currentTimeMillis();
            status+=":"+(L2-L1)+" msec:";        
%>
<%@include  file="../../ok.jsp"%>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>