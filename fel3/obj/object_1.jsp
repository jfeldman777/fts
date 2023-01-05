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
var start=1;
function f(fx){
    if(start==1){
            start=0;
        alert("click and wait");     
        document.fx.submit();
    }
}


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
boolean flash = ss.hasFlash(i);
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
    function oo(){Layer1.style.visibility="hidden"};
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
    <%=pf.toolbar()%> +++ <%=pf.toolbarAr()%>
    <hr>
<table bgcolor=white>
<tr><td>
    <table border=0 width='100%' <%=bg%>>
        <tr><td><%=nn.typeImgCode()%></td>
        <td align=right>

                <a href='root.jsp?id=A<%=i%>' target=left title='set tree root here/корень здесь'>
                <img src='/feldman-root/style/obj/here.gif' border=0></a>

                <img src='/feldman-root/style/obj/back.gif' border=0  title='reload/обновить'
                onclick='location.reload()'
                >
            <%if(!lock){%>
             <img src='/feldman-root/style/obj/_edit.gif' border=0 title='edit/изменить'
            onclick='document.getElementById("LayerEdit1").style.visibility="visible";
            '>

             <img src='/feldman-root/style/obj/_delete.gif' border=0 title='delete/удалить'
            onclick='document.getElementById("LayerDelete1").style.visibility="visible";'>
            
             <img src='/feldman-root/style/obj/_move.gif' border=0 title='move/сдвинуть'
        onclick='
document.getElementById("LayerMove1").style.visibility="visible";
document.getElementById("LayerMove2").style.visibility="visible";
'>            
            <%}%>
             <img src='/feldman-root/style/obj/_shortcut.gif' border=0 title='shortcut/создать ярлык'
        onclick='
document.getElementById("LayerShortcut1").style.visibility="visible";
document.getElementById("LayerShortcut2").style.visibility="visible";
'>
             

             
             <img src='/feldman-root/style/obj/_send.gif' border=0 title='send/разослать по списку'
        onclick='
document.getElementById("LayerSend1").style.visibility="visible";
document.getElementById("LayerSend2").style.visibility="visible";
'>
        
        <%if(!pseudo&&!shadow){%>    
            <img src='/feldman-root/style/obj/_new.gif' border=0 title='new/создать'
            onclick='
document.getElementById("LayerNew1").style.visibility="visible";
document.getElementById("LayerNew2").style.visibility="visible";
'>   
            <%if(!lock){%>
             <img src='/feldman-root/style/obj/_type.gif' border=0 title='type/уточнить тип'
            onclick='document.getElementById("LayerType1").style.visibility="visible";'>      
            <%}%>
             <img src='/feldman-root/style/obj/_find.gif' border=0 title='find/найти'
        onclick='
document.getElementById("LayerFind1").style.visibility="visible";
document.getElementById("LayerFind2").style.visibility="visible";
'>
        <%if(ss.isZ()){%>
             <img src='/feldman-root/style/obj/_sql.gif' border=0 title='query/запрос'
        onclick='
document.getElementById("LayerSql1").style.visibility="visible";
document.getElementById("LayerSql2").style.visibility="visible";
'>
        
             <img src='/feldman-root/style/obj/_export.gif' border=0 title='export/экспорт'
        onclick='document.getElementById("LayerExport1").style.visibility="visible";'>       
  
             <img src='/feldman-root/style/obj/_lock.gif' border=0 title='lock/замок'
        onclick='document.getElementById("LayerLock1").style.visibility="visible";
document.getElementById("LayerLock2").style.visibility="visible";
'>   
               <%}%> 
                <a href='../inh/root.jsp?id=H<%=type%>' target=left title='to inh tree/к зеленому дереву'>
                <img src='/feldman-root/style/obj/left.gif' border=0></a>        
        

                
                <a href='rootup.jsp?id=<%=i%>' target=left title='lift tree root up/вверх по дереву'>
                <img src='/feldman-root/style/obj/up.gif' border=0></a>


             
                <!--a href='oldroot.jsp' target=left title='back to root/к главному корню'>
                <img src='/feldman-root/style/obj/back.gif' border=0></a-->
                
                <a href='path.jsp?id=<%=i%>' target=left title='show path/проследить путь'>
                <img src='/feldman-root/style/obj/down.gif' border=0></a>
                
                <a href='../type/root.jsp?id=T<%=type%>' target=left title='to type tree/к синему дереву'>
                <img src='/feldman-root/style/obj/right.gif' border=0></a>
                <%}else if(ss.isZ()){%>
                <a href='root.jsp?id=A<%=io%>' target=left title='to origin/к оригиналу'>
                <img src='/feldman-root/style/obj/left.gif' border=0></a>

                <%}%> 
             </td>
        </tr>
    </table>
<table border='2' width='100%'> 
<%=nn.boxView(lock)%>
</table>


<%if(ss.notDemo()){%>
<%if(ss.ver==0//!shadow&&!pseudo
        ){%>
<table border=0 width='100%'   <%=bg%> >
 <tr align=center <%=bg%>> 
<%if(!pseudo && !shadow){%>

<!--td>
    <div id=LayerEdit2></div>
<div id="LayerEdit1" style="visibility:hidden">
<a href='do/editform.jsp?id=<%=tn%>' title='edit data elements'>
    <img src='<%=style%>edit.gif' border=0>
    </a>

</div>
</td-->

<%if(ss.hasFlash(i)){%>
<td>
    <!--div id=LayerFlash2></div>
<div id="LayerFlash1" style="visibility:hidden">
<a href='do/flash.jsp?id=<%=tn%>' title='edit data elements'
><img src='/feldman-root/style/obj/flash.gif' border=0></a-->

<%}
    }else{%>
    <div id=LayerEdit2></div>
    <div id="LayerEdit1" style="visibility:hidden">
    <a href='do/editformshadow.jsp?id=<%=tn%>' title='edit shadow fields'>
    <img src='<%=style%>edit.gif' border=0></a>    
    </div>
</td>
<%}%>

<%if(i>0 && !shadow && !pseudo){%>

<!--td> 
    <div id=LayerType2></div>
    <div id="LayerType1" style="visibility:hidden">
<a href='ntype/menu.jsp?xid=<%=tn%>' title='type-center,type-set'
onClick="alert('click and wait')"
><img src='<%=style%>type.gif' border=0>
</a>
</div>

</td-->
<%}%>


<%if(!tn.equals("a0")&& !pseudo){%>
<!--td>
    <div id=LayerDelete2></div>
    <div id="LayerDelete1" style="visibility:hidden">
<a href='do/delete.jsp?id=<%=tn%>' title='delete the object'>
    <img src='<%=style%>delete.gif' border=0>
</a>
</div>
</td-->
<%}%>
<%if(ss.isZ()){%>

<!--form id=treepub action='do/treepub.jsp' method=post>
<td width=103>
    <div id=LayerExport2></div>
    <div id="LayerExport1" style="visibility:hidden">
<input type=image src='<%=style%>export.gif' title='tree publish' onClick="f('treepub')" >
            <td colspan=2><input type=hidden name=id value=<%=i%>></td>
</div>

</form-->
<%}%>
</tr>


</table>
<table border='0' width='100%'  <%=bg%> > 



<%if(!tn.equals("a0")){%>

<!--tr><td>
<form id=word action='do/word.jsp' method=post> 
<input type=image src='/feldman-root/style/obj/word.gif' title='word doc'
onClick="javascript:f('word')"><input type=hidden name=id value=<%=tn%>>
</td>
</form>
</tr-->
<%}%>
<%if(i==0||!shadow && !pseudo //&& !c.equals("object")
){%>



            <!--tr valign=baseline>
<form id=fnew action='do/newobjform.jsp' method=post>
<td>
<input type=hidden name=parent value='<%=tn%>'>

<div id="LayerNew1" style="visibility:hidden">
<input type=image src='<%=style%>new.gif'
 title='create new object(s) of the type(s) under this object'
 onClick="javascript:f('fnew')"
>
</div>
</td><td>
<div id="LayerNew2" style="visibility:hidden">
<input type=text name=addr title=addr>
<select name=type >
    <option value=-1>-------</option>
<%=nn.innerLBtypes()%>

</select>

<br><select name=list>
        <option value=-1>-------</option>
<%=ss.optionListA()%>
<%if(ss.isZ()){%>
<%=ss.optionListB()%>
<%}%>
</select>

</div></td>
</form>
</tr-->




<%}%>
<%{%>

<!--tr valign=baseline>

<form action=find.jsp method=post>
<td>
    <div id="LayerFind1" style="visibility:hidden">
<input type=image src='<%=style%>find.gif'  title='put criteria on the right'>
 </div>
</td><td>
<div id="LayerFind2" style="visibility:hidden">
<input type=text name=criteria>
<input type=checkbox name=sh title='look for shortcuts: o.code'>
</div>
</td>
</form>
</tr-->

<%}%>
<%if(i>0){%>

<!--tr valign=baseline>
<form id=fh action='do/shortcut.jsp' method=post>
<td width=103>
    <div id="LayerShortcut1" style="visibility:hidden">      
<input type=image src='<%=style%>shortcut.gif' title='put shortcut under the address' onClick="f('fh')">
</div>
</td>

<td>
 <div id="LayerShortcut2" style="visibility:hidden"> 
<input type=hidden name=id value='<%=tn%>'>
<input type=text name=parent> 
<%if(ss.isZ()){%>
<input type=checkbox name=blind title='blind node/shortcut' <%=nn.isBlind()?"CHECKED":""%>>
<%}else if(nn.isBlind()){%>
<input type=hidden name=blind value='on'>
<%}%>
</div>
</td>
</form>
</tr-->


<!--tr valign=baseline>
<form id=send action='do/send.jsp' method=post>
<td width=103>
    <div id="LayerSend1" style="visibility:hidden">
<input type=image src='<%=style%>send.gif' 
title='put shortcuts according the list'
onClick="f('send')"
>
</div>
</td>
<td>
<div id="LayerSend2" style="visibility:hidden">
<input type=hidden name=id value='<%=io%>'>
<input type=text name=parent> 
<//div>
</td>
</form>
</tr-->

<%}%>
</td></tr>




<%if(ss.isZ()){%>
<!--tr>
<form id=lock action='do/lock.jsp' method=post>
<td width=103>
    <div id="LayerLock1" style="visibility:hidden">
<input type=image src='<%=style%>lock.gif' 
title='lock' onClick="javascript:f('lock')" >
</div>
            <td colspan=2>

 <div id="LayerLock2" style="visibility:hidden">
            <input type=hidden name=id value=<%=i%>>
            <select name=var>
                <option value="0">---</option>
                <option value="1">Lock/Закрыть</option>
                <option value="2">Unlock/Открыть</option>
                <option value="3">Lock the tree/Закрыть дерево</option>
                <option value="4">Unlock the tree/Открыть дерево</option>                
            </select>
</div>
        </td>
</form>
</tr-->

<%}%>
<%if(ss.isZ()){%>

<!--tr align=left <%=bg%>>
<form action=sql2.jsp method=post>
<td> 
    <div id="LayerSql1" style="visibility:hidden">
<input type=image src='<%=style%>sql.gif' title='quick report'>
</div>
</td><td>
   <div id="LayerSql2" style="visibility:hidden">
<textarea name=sql cols="50" rows="3">
</textarea>
</div>
</td>
</form>
</tr-->

<!--tr align=left <%=bg%>>
<td> 
<a href='shutdown.jsp' title='shutdown data base'
><img src='<%=style%>base.gif' border=0></a>
</td></tr-->
<%}%>
<%if(!pseudo && !tn.equals("a0")){%>

<!--tr valign=baseline>
<form id=fmove action='do/move.jsp' method=post>
<td>
    <div id="LayerMove1" style="visibility:hidden">
<input type=image src='<%=style%>move.gif'
 title='move it under this address' onClick="javascript:f('fmove')">
</div>
</td><td>
    <div id="LayerMove2" style="visibility:hidden">
<input type=text name=parent>
<input type=hidden name=id value='<%=tn%>'>
</div>
</td>
</form>
</tr-->

<%}%>
</table>
</td></tr></table>

<%}%>
</div>
<table border=2>
    <tr>
    <%=pf.tab1(i,shadow,pseudo,admin,flash,root,z,lock)%>
    </tr>
</table>

<table border=2>
    <%=pf.tab2(i,shadow,pseudo,admin,flash,root,z,lock,blind,nn)%>
</table>
&nbsp;&nbsp;msec = <%=ss.getMillis()%>
<%}%>
<%=ss.myHs(io)%>
</body>
</html>
<%}catch(ExFTS e2){%>
<hr><%=e2.fts()%><hr>
<%}catch(Exception e){%>
<%@include  file="../sorry.jsp"%>
<%}%>