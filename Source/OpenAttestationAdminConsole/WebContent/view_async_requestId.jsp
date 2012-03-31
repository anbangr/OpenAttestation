<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<LINK rel=stylesheet type=text/css href="css/main.css">
<STYLE type=text/css>#heading .logo {
	TEXT-INDENT: -9999em; WIDTH: 531px; DISPLAY: block; BACKGROUND: url(/image/company_logo?img_id=10471&amp;t=1319605025673) no-repeat; HEIGHT: 76px; FONT-SIZE: 0px
}
</STYLE>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>OpenAttestation Admin Console</title>
</head>
<body bgcolor="RGB(238,240,242)">
<%@ include file="header_bar.jsp" %>

<table>
<tr><td valign="top">
	<%@ include file="attest_left_list.jsp" %>
</td>
<td valign="top">
   <table class="main_div">
     <tr><td class="nav_font">Attestation Result - Asnc Mode</td></tr>
     <tr><td>requestId =  <%=request.getAttribute("requestId") %></td></tr>
   </table>
</td>
</tr>
  
</table>

</body>
</html>