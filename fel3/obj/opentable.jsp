<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
try{ss.x();

String status="open table";
String line="obj";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
String type = request.getParameter("type");
String sid = request.getParameter("id");
int id = Integer.parseInt(sid);

%>
<html>
<head>
<script>
 function set(xid,code){
    window.w.xid = xid;
    window.w.code = code;
    window.close();
 }

 function unload(){
 if(window.w.code == null){
        window.w.xid = 0;
        window.w.code = 'nodata';
    }
}
</script>
<meta name='Author' content='Jacob Feldman'/>
<title>e</title></head>
<body <%=bg%> onLoad='alert("select one item!");' onUnload='unload();'>
<form>
<table border='2'>
<%=ss.openTable(type,id)%>
</table>
</form>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../sorry.jsp"%>
<%}%>