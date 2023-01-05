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
    String dict = request.getParameter("dict");
    String file= request.getParameter("file");
    String tn= request.getParameter("id");
    String sparent= request.getParameter("parent");
    int parent = Integer.parseInt(sparent);
    
    if(dict!=null){
    %>
    <jsp:forward page="imp2.jsp">
<jsp:param name="file" value="<%=file%>"/>
<jsp:param name="id" value="<%=tn%>"/>
</jsp:forward>
            <%}
    IGuiNode nn = ss.getGuiNode(tn);
    DbfImport dbf = nn.getDbfImport(parent,file);
            
    int res = dbf.go(request);
            long L2 = System.currentTimeMillis();
            status+=":"+(L2-L1)+" msec:";        
%>
<b><%=res%></b>
<%@include  file="../../ok.jsp"%> 
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>