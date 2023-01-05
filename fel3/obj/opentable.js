function openTable(ctrl,ref){
    window.xid = null;
    window.code = null;
    if(ctrl.options[ctrl.selectedIndex].value!=-1)return;
    var newwindow = window.open('/fel3/obj/opentable.jsp?type='+ref+
    '&id='+id,'opentable','width=200,height=200,resizable,scrollbars');
    newwindow.w = window;
    var j = ctrl.options.length;
    while(window.code==null){
       alert('I wait for your selection');
    }
    ctrl.options[j] = new Option(window.code);
    ctrl.options[j].value = window.xid;
}
