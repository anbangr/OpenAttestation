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
<script language="javascript">
   function delPCR(){
	   var index = form1.selectedIndex.value;

	   if (confirm("Delete index " + index + "?")){
    	   form1.submit();
	   }
   }
</script>
<body bgcolor="RGB(238,240,242)">
<%@ include file="header_bar.jsp" %>
<table>
<tr><td valign="top">
   <%@ include file="left_list.jsp" %>
</td>
<td valign="top">
   <form name="form1" method="post" action="DelPCRServlet">
   <input type="hidden" name="return_page" value="del_pcr.jsp">
   <table class="main_div3">
     <tr><td class="nav_font">Delete a PCR</td></tr>
     <tr><td>
	     <table border="0" style="margin:20px 20px 20px 25px">
	     <tr><td width="100px" class="table_header_font">&nbsp;Input an index:</td>
	     <td width="300px"><input type="text" name="selectedIndex" value=""></tr>
	     </table>
	  </td></tr>
     <tr><td class="nav_font" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     <input type="button" value="Delete" onclick="delPCR()">&nbsp;&nbsp;<font class="error_msg_font"><%=request.getAttribute("message")==null?"":request.getAttribute("message") %></font></td></tr>
     <tr><td class="nav_font" align="center">&nbsp;&nbsp;</td></tr>
   </table>
   </form>
</td>
</tr>
</table>

</body>
</html>