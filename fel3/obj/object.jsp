<%@page contentType="text/html; charset=Windows-1251"%>
<%@page import = "j5feldman.*"%>
<%@page import = "j5feldman.ex.*"%>
<%@page import = "j5feldman.profile.*"%>
<jsp:useBean id="ss" scope="session" class="j5feldman.Session" />
<html>
<head>
<meta name='Author' content='Jacob Feldman'/>
<title>Objects</title>
<script>
    var x2go=0;
var start=1;
//function f(fx){    if(start==1){            start=0;        alert("click and wait");             document.fx.submit();    }}


</script>

</head>

<%request.setCharacterEncoding("windows-1251");
try{ss.x();

String status="object";
ss.setMillis();

String tn= request.getParameter("id");
IGuiNode nn = ss.getGuiNode(tn);
int i=nn.id();
int type = nn.type();
int io = nn.idOrigin();
boolean pseudo=nn.isPseudo();
boolean shadow=nn.isShadow();
boolean admin = ss.isZ();
//boolean flash = ss.hasFlash(i);
boolean root = tn.equals("a0");
boolean z = i==0;
boolean blind = nn.isBlind();
String c = nn.typeCode();
String line="obj";
String style="/feldman-root/style/"+ss.ER()+line+"/";
String bg=" BACKGROUND='/feldman-root/style/"+line+"/bg.gif' ";

boolean lock = ss.hasLock(i);

Profile pf = ss.profile;

%>
<script>
  function oo(){
    Layer1.style.visibility="hidden";
  };
</script>
<script>
    function up(kw){
              var bord = document.images['Button' + kw].border; 
              if(bord==0){
                document.getElementById('Layer' +kw+ '1').style.visibility='visible';
                document.getElementById('Layer' +kw+ '2').style.visibility='visible';
                document.images['Button' + kw].border=1;
             }else{
                document.getElementById('Layer' +kw+ '1').style.visibility='hidden';
                document.getElementById('Layer' +kw+ '2').style.visibility='hidden';  
               document.images['Button' + kw].border=0;                
             }
           };
    
</script>

<body <%=bg%> onload="javascript:oo();">


<table bgcolor=white>
<tr><td>
    <table border=0 width='100%' <%=bg%>>
        <tr><td><%=nn.typeImgCode()%></td>
        <td align=right>

                <%=pf.toolbar(shadow,pseudo,admin,root,z,lock)%>
                <%=pf.toolbarAr(i,type,io)%>
               

             </td>
        </tr>
    </table>
<table border='2' width='100%'> 
<%=nn.boxView(lock)%>
</table>


<%if(ss.notDemo()){%>
<%if(ss.ver==0){%>
<table border=2 width='100%'   <%=bg%> >
 <tr align=center <%=bg%>> 
    <%=pf.tab1(i,shadow,pseudo,admin,root,z,lock)%>
    </tr>
    </table>
<table border=2 width='100%'   <%=bg%> >  
    <%if(!shadow && !pseudo){%>
        <%=pf.tab0(admin,nn)%>
    <%}%>
    <%=pf.tab2(i,shadow,pseudo,admin,root,z,lock,blind)%>
</table>
&nbsp;&nbsp;msec = <%=ss.getMillis()%>
<%}}%>
<%=ss.myHs(io)%>

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