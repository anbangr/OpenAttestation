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
   <%@ include file="left_list.jsp" %>
</td>
<td valign="top">
   <%String returnPage = request.getParameter("returnPage") == null? "": request.getParameter("returnPage"); %>
   <form name="form1" method="post" action="LoginServlet" >
   <input type="hidden" name="return_page" value="<%=returnPage%>">
   <table class="main_div">
   
     <tr><td class="nav_font">Login</td></tr>
     <tr><td>
	     <table border="0" style="margin:20px 20px 20px 25px">
	     <tr><td width="70px" class="table_header_font">&nbsp;User:</td>
	     <td width="300px"><input type="text" name="username" value="admin" size="20"></tr>
	     <tr><td width="70px" class="table_header_font">&nbsp;Password:</td>
	     <td width="300px"><input type="password" name="password" value="123" size="21"></tr>
         <tr><td colspan="2" class="error_msg_font">&nbsp;<%=request.getAttribute("message")==null? "": request.getAttribute("message")%></td></tr>
         </table>
	  </td></tr>
     <tr><td class="nav_font">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     <input type="submit" value="Login"></td></tr>
     <tr><td class="nav_font" align="center">&nbsp;&nbsp;</td></tr>
   </table>
   </form>
</td>
</tr>
  
</table>

</body>
</html>