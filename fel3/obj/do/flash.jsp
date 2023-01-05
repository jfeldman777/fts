<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<%request.setCharacterEncoding("windows-1251");
try{ss.x();
String line="obj";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";


String status="before edit";
String tn= request.getParameter("id");
IGuiNode nn = ss.getGuiNode(tn);
String name = nn.name();//studentname
String typecode = nn.typeCode();//oid
String code = nn.code();
String er = ss.ER();
String sid = ""+nn.id();
String flashvars = "id="+sid+"&student_name="+name+"&with_fts=1";
    //flashvars+="&dbconfig="+dbconfig+"&root="+root+"&schema="+schema;
    flashvars+="&code="+code+"&er="+er;

%>
<html>
<head>
<script>
</script>

<meta name='Author' content='Jacob Feldman'/>
<title>e132</title></head>
  <BODY <%=bg%>>
<table border='2' cellpadding='2' cellspacing='0'>
<tr>
<td>
<%=nn.faceName()%>
<!--a href='/feldman-root/system/<%//=ss.getSchema()%>/flash/help/<%//=typecode%>'  
target='help'>HELP</a-->
<hr>
</td>
</tr>
<tr><td>
    
<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" 
codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0" 
width="780" height="600" id="<%=typecode%>" align="middle">
<param name="allowScriptAccess" value="sameDomain" />
<PARAM NAME=FlashVars VALUE="<%=flashvars%>"/>
<param name="movie" value="/feldman-root/system/<%=ss.getSchema()%>/flash/<%=typecode%>.swf"/>
<param name="quality" value="high" />
<param name="bgcolor" value="#ffffff" />
</object>

</td></tr>
<tr>
<td>
<%=nn.faceName()%>
</td>
</tr>
</table>

</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../../sorry.jsp"%>
<%}%>