<%@page language="java" contentType="text/html; charset=windows-1251" %>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
            long L1 = System.currentTimeMillis();
String status="edit col name";
String line="type";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
try{ss.x();
    String tab = request.getParameter("tab");
    String col = request.getParameter("col");
    String val = request.getParameter("val");
    String sid = request.getParameter("id");
    int i = Integer.parseInt(sid);
String tctype = request.getParameter("tctype");
char c = (tctype==null?'c':tctype.charAt(0));
ITabCol tc=ss.getTabCol(tab,col,c);
    if(c!='t')tc.setRus(val);
    else tc.setValue(i,val);
            long L2 = System.currentTimeMillis();
            status+=":"+(L2-L1)+" msec:";    
%>
<%@include  file="../ok.jsp"%>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../sorry.jsp"%>
<%}%>
