<%@page language="java" contentType="text/html; charset=windows-1251" %>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<%@page import = "j5feldman.proc.basement.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
try{ss.x();
            long L1 = System.currentTimeMillis();
String ER = ss.ER();
String sid;
String status="edit object";
String line="obj";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
    String file= request.getParameter("file");
    String tn= request.getParameter("id");
    IGuiNode nn = ss.getGuiNode(tn);
    DbfImport dbf = nn.getDbfImport(file);

    dbf.go(request);
            long L2 = System.currentTimeMillis();
            status+=":"+(L2-L1)+" msec:";        
%>
IMP 2
<%@include  file="../../ok.jsp"%> 
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>