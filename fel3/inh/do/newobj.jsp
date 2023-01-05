<%@page language="java" contentType="text/html; charset=windows-1251" %>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
try{ss.x();

            long L1 = System.currentTimeMillis();
String status="new type";
String line="inh";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
    String ER = ss.ER();

    String tn = request.getParameter("parent");
    String code = request.getParameter("code");
    String name = request.getParameter("name");
    String file = request.getParameter("file");
    IGuiNode nn = ss.getGuiNode(tn);//tn
    nn.createType(code,name,"",true,file);
            long L2 = System.currentTimeMillis();
            status+=":"+(L2-L1)+" msec:";        
%>
<%@include  file="../../ok.jsp"%>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>
