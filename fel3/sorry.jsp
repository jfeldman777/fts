<%@page import="java.io.*"%>
<%e.getMessage();%>
<a href="javascript:top.history.back()">
<img src='/feldman-root/icon/sorry.gif' border=0></a>
<%
PrintWriter p = new PrintWriter(out);
e.printStackTrace(p);
%>

