<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head>
<meta name='Author' content='Jacob Feldman'/>
<title>hello!!</title>
<%request.setCharacterEncoding("windows-1251");
try{ss.x();
String line="obj";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
%>
<script>
function ran(){
    x = Math.random();
    return x
}
var start=1;
function f(fx){ 
    if(start==1){
        var N = 1000*ran();
        for(n=0;n < N;n++){}
        alert("clck and wait");
        for(n=0;n < N;n++){}
        document.fx.submit();
    }
    start=0;
}
</script>
</head>
  <BODY <%=bg%> onLoad="document.forms[0].reset();" >
<%

String status="edit point p form";
String sid= request.getParameter("id");
int id = Integer.parseInt(sid);
String dbconfig= request.getParameter("dbconfig");
String schema= request.getParameter("schema");
String root= request.getParameter("root");
String code= request.getParameter("code");

ss.xConnect(request);
%>
<form form id=ff  onSubmit="javascript:f('ff')"
action='edit.jsp' method=post AUTOCOMPLETE="OFF">
<input type=image src='<%=style%>edit.gif' title='edit'></td><tr>
<input type=hidden name='dbconfig' value='<%=dbconfig%>'>
<input type=hidden name='schema' value='<%=schema%>'>
<input type=hidden name='root' value='<%=root%>'>
<input type=hidden name='code' value='<%=code%>'>
<input type=hidden name='er' value='<%=ss.ER()%>'>
<%=ss.getName(id)%>
<table border='3' cellpadding='2' cellspacing='0'>
<%=ss.pointXEditForm(id)%>
</table>
<input type=image src='<%=style%>edit.gif' title='edit'></td><tr>
</form>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}finally{
}%>