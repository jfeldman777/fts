<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head>
<meta name='Author' content='Jacob Feldman'/>
<title>Objects</title></head>


<%request.setCharacterEncoding("windows-1251");
try{ss.x();


String line="obj";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
String status="obj tree";

%>
<body <%=bg%>  onLoad="status='objects'"
 text="#553100" link="#009966" alink="#FE491D" vlink="#009966"
>
<table border='0' cellpadding='0' cellspacing='0' bgcolor='white'>
<%=ss.showTree("a")%>
</table>
<hr>
&nbsp;&nbsp;msec = <%=ss.getMillis()%><hr>

                <a href='oldroot.jsp' title='back to root/к главному корню'>
                <img src='/feldman-root/style/obj/back.gif' border=0></a>
                
                <a href='profile/editform.jsp' title='edit profile/настройки' target=right>
                <img src='/feldman-root/style/obj/right.gif' border=0></a>
</body>
<script>
        document.body.style.scrollbarBaseColor = '#F6E4D7';
</script>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../sorry.jsp"%>
<%}%>