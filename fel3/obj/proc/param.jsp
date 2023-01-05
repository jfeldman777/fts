<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head>
<meta name='Author' content='Jacob Feldman'/>
<title>777</title></head>

<%request.setCharacterEncoding("windows-1251");
try{ss.x();
String line="obj";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
String tn = request.getParameter("id");
    IGuiNode nn = ss.getGuiNode(tn);
    boolean shadow = nn.isShadow();
    boolean pseudo = nn.isPseudo(); 
    int oid = nn.idOrigin();
    String code = nn.typeCode();
    String sTab="",sTab2="";
    
    if(code.equals("qfolder"))sTab2=ss.descQfolder(oid);
    //else 
        sTab=ss.procDesc(oid,false);
%>
  <BODY  <%=bg%>>
<img src='<%=style%>sql.gif'><p>
<%=ss.faceObj(oid)%>
<hr>
<table border=2>
<%=sTab%>
<%=sTab2%>
</table>

<%if(code.equals("query")&&!shadow&&!pseudo&&ss.isZ()){%>
<hr>
<a href='../report/start.jsp?id=<%=tn%>' title='report generator' target=_parent>
<img src='<%=style%>edit.gif' border=0>
</a>
<%}%>
<%if(ss.notDemo()&&!code.equals("query")&&ss.hasForce(oid)){%>
<a href='frame2.jsp?id=<%=tn%>' target=right>>>>>>>></a>
<%}%>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>
