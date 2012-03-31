<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, java.util.ArrayList,com.intel.openAttestation.bean.*, java.text.DateFormat"%>
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
	<!-- <form name="asyncResult" method="post" action="ListAsyncRequest"> -->
   
   
   <table class="main_div">
     <tr><td class="nav_font">Check Status for Async Request</td></tr>
     <tr><td>
	     <table border="1" style="margin:20px 20px 20px 25px">
	     <tr><td width="200px" class="table_header_font" align="center">Requests</td>
	     <td width="200px" class="table_header_font" align="center">Hosts</td> 
	     <td width="100px" class="table_header_font" align="center">PCRMask</td>
	     <td width="200px" class="table_header_font" align="center">RequestTime</td>
	      <td width="100px" class="table_header_font" align="center">Count</td>
	     <td width="200px" class="table_header_font" align="center">Status</td></tr> 
	     
	     <%
	     List<ReqAsyncStatusBean> reqs = new ArrayList<ReqAsyncStatusBean>();
    		 reqs = (List<ReqAsyncStatusBean>)request.getAttribute("all_requests");
	    	if(reqs != null && reqs.size()>0)
	    	{
	    		for (ReqAsyncStatusBean req:reqs)
		    	{
	     %>
			      <tr><td><a href="request_result.jsp?requestId=<%= req.getRequestId() %>"><%=req.getRequestId() %></a></td>
			      <td align="center"><%=req.getHosts() %></td>
		 		  <td align="center"><%=req.getPCRMask() %></td>
		          <td align="center"><%= req.getRequestTime()%> </td>
		          <td align="center"><%=req.getCount() %></td>
			      <td align="center"><%=req.getStatus() %></td></tr>
	           <%} %>
	         <%} %>
	     </table>
	  </td></tr>
	  <tr><td colspan="2" class="error_msg_font">&nbsp;<%=request.getAttribute("message")==null? "": request.getAttribute("message")%></td></tr>
     <tr><td class="nav_font" align="center"><input onclick="window.location.reload()" type="button" value="Refresh">&nbsp;&nbsp;
     </td></tr>
     <tr><td class="nav_font" align="center">&nbsp;&nbsp;</td></tr>
   </table>
   
</td>
</tr>
</table>

</body>
</html>