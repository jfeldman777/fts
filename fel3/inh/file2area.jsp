<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head>
<meta name='Author' content='Jacob Feldman'/>
<title>e</title></head>

<%request.setCharacterEncoding("windows-1251");
try{ss.x();
String line="inh";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
            long L1 = System.currentTimeMillis();
String path = request.getParameter("path"); 
String utf = request.getParameter("utf"); 
boolean btf = utf!=null;
if(path==null || path.equals("")){
%>
<jsp:forward page="area.jsp">
<jsp:param name="path" value="<%=path%>"/>
</jsp:forward>
<%};
String status="sql";

    ss.file2db(path,btf);
            long L2 = System.currentTimeMillis();
            status+=":"+(L2-L1)+" msec:";   
%>
<%@include  file="../ok.jsp"%>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../sorry.jsp"%>
<%}%>