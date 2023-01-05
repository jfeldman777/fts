<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head>
<meta name='Author' content='Jacob Feldman'/>
<title>login</title>
</head>
<BODY bgcolor=#CCFFFF>



<%request.setCharacterEncoding("windows-1251");
try{ss.x();
%>

  <div id="Layer3" style="position:absolute; width:150px; height:25px; z-index:2; left: 30px; top: 80px">
<font face="Courier New, Courier, mono">
    <a href='http://jfeldman.narod.ru' target=jfeldman title=my-home-page>&copy;
        <%=ss.dict("Jacob Feldman")%>   
    
</a><p>
            <%=ss.dict("FTS-build")%>:<br> 
2010-08-15 17:27:15.267
<p>

        <%=ss.dict("Last visit")%>:<br><%=ss.lastVisit()%><p>
        <%=ss.dict("Current start")%>:<br><%=ss.getVisit()%><p>
        <%=ss.dict("user")%>: <%=ss.getUser()%><br>
        <%=ss.dict("schema")%>: <%=ss.getSchema()%><p>
<a href='mailto:jfeldman777@gmail.com?subject=fts-support' title="email me">
        <%=ss.dict("Support")%>1</a>  
</font></div>
</div>
  </BODY>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="sorry.jsp"%>
<%}%>