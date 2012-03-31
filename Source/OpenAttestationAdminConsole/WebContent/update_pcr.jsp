<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.text.SimpleDateFormat,com.intel.openAttestation.manifest.util.*,com.intel.openAttestation.manifest.hibernate.domain.*"%>
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
   <%
   	PCRManifest pcr = (PCRManifest)request.getAttribute("update_pcr");
        SimpleDateFormat sdf = new SimpleDateFormat(AttestUtil.getDateTimePattern());
   %>
   <form name="form1" method="post" action="UpdatePCRServlet"> 
   <input type="hidden" name="index" value="<%=pcr.getIndex()%>">
   <input type="hidden" name="pcrCreateRequestHost" value="<%=pcr.getCreateRequestHost()==null?"":pcr.getCreateRequestHost()%>">
   <input type="hidden" name="pcrCreateTime" value="<%=pcr.getCreateTime()==null?"":sdf.format(pcr.getCreateTime())%>">
   <table class="main_div3">
     <tr><td class="nav_font">Update a PCR</td></tr>
     <tr><td>
	     <table border="0" style="margin:20px 20px 20px 25px">
	     <tr><td width="100px" class="table_header_font">&nbsp;Index:</td>
	     <td width="300px">&nbsp;<%=pcr.getIndex() %></tr>
	     <tr><td width="100px" class="table_header_font">&nbsp;PCR Number:</td>
	     <td width="300px"><input type="text" name="pcrNumber" value="<%=pcr.getPCRNumber()==null?"": pcr.getPCRNumber() %>" size="42"></tr>
	     <tr><td width="100px" class="table_header_font">&nbsp;PCR Value:</td>
	     <td width="300px"><input type="text" name="pcrValue" value="<%=pcr.getPCRValue()==null?"": pcr.getPCRValue() %>" size="42"></tr>
	     <tr><td width="100px" class="table_header_font">&nbsp;PCR Description:</td>
	     <td width="300px"><input type="text" name="pcrDesc" value="<%=pcr.getPCRDesc()==null?"": pcr.getPCRDesc()%>" size="42"></tr>
	     </table>
	  </td></tr>
     <tr><td class="nav_font">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     <input type="submit" value="Update"> &nbsp;&nbsp;<font class="error_msg_font"><%=request.getAttribute("message")==null?"":request.getAttribute("message") %></font></td></tr>
     <tr><td class="nav_font" align="center">&nbsp;&nbsp;</td></tr>
     
   </table>
   </form>
</td>
</tr>
</table>

</body>
</html>