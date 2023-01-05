<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head>
<meta name='Author' content='Jacob Feldman'/>
<title>login</title>

</head>
<BODY bgcolor="#CCCCCC">

<% request.setCharacterEncoding("windows-1251");
try{

String dbconfig= request.getParameter("dbconfig");
String schema= request.getParameter("schema");
String root= request.getParameter("root");
String user= request.getParameter("user");
String pwd= request.getParameter("pwd");
String er = request.getParameter("er");
if(er!=null) ss.setER(er);
String ER = ss.ER();
long L1 = System.currentTimeMillis();
String hello = ss.login(dbconfig,schema,root,user,pwd);
boolean guest = hello.equals("guest");
String ac = ss.adminOnly?"checked":"";
long L2 = System.currentTimeMillis();
String hello2 = er!=null?"Hello":"Здравствуйте";
    String LL = ""+(L2-L1)+" msec ";
%>
<div id="Layer1" style="position:absolute; width:798px; height:598px; z-index:1; background-image: 
 url(/feldman-root/face2.jpg); layer-background-image: 
 url(/feldman-root/face2.jpg); border: 1px none #000000; left: 0px; top: 0px"> 
  <div id="Layer2" style="position:absolute; 
  width:279px; height:71px; z-index:1; left: 224px; top: 257px"> 
  
      
    <p align="center"><b><font face="Courier New, Courier, mono" size="+3">
 <%=hello2%>, <%=hello%>!
    </font>
    </b></p>
    
<%=guest?"<p align=center><b>The password is wrong, try again:</b></p>":""%>
    <p align="center">
<%if(guest){%>
<a href='javascript:top.history.back();' title='wrong password - try again, please!'>
<img src="/feldman-root/icon/try_again.jpg" width="80" height="30" border="0">
</a> 
<%}else{

    %>
<form action='obj/frames.jsp'>
<input type=image
 src="/feldman-root/icon/enter2.jpg" width="80" height="30" border="0" 
 title='<%=LL%>'
 ><br>
<%if(ss.isZ()){%>
<input type=checkbox name=a <%=ac%> title="admin only">
<%}%>

</form> 
<%}%>
    
</p>
  </div>
</div>
  </BODY>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="sorry.jsp"%>
<%}%>