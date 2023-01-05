<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head>
<meta name='Author' content='Jacob Feldman'/>
<title>123</title>
<%request.setCharacterEncoding("windows-1251");
try{ss.x();
String ER = ss.ER();
String line="obj";
String style="/feldman-root/style/"+ER+line+"/";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";
%>
<script>

</script>
</head>
  <BODY <%=bg%>>
<%

String er = request.getParameter("er");
if(er!=null) ss.setER(er);

String status="edit pointfla form";
String sid= request.getParameter("id");
int id = Integer.parseInt(sid);

String dbconfig= request.getParameter("dbconfig");
String schema= request.getParameter("schema");
String root= request.getParameter("root");
String code= request.getParameter("pwd");

ss.xConnect(request);
String name = ss.id2name(id);//"myname";
String typecode = ss.id2typecode(id);//"v3dfi";

String flashvars = "id="+sid+"&student_name="+name+"&with_fts=1";
    flashvars+="&dbconfig="+dbconfig+"&root="+root+"&schema="+schema+"&code="+code+"&er="+er;
%>

<!--a href='/feldman-root/system/<%//=schema%>/flash/help/<%//=typecode%>' 
target='help'>HELP</a-->
<hr>
<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" 
codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0" 
width="780" height="600" id="<%=typecode%>" align="middle">
<param name="allowScriptAccess" value="sameDomain" />
<PARAM NAME=FlashVars VALUE="<%=flashvars%>"/>
<param name="movie" value="/feldman-root/system/<%=schema%>/flash/<%=typecode%>.swf"/>
<param name="quality" value="high" />
<param name="bgcolor" value="#ffffff" />
</object>

</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>