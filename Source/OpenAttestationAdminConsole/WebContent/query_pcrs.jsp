<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List, java.util.ArrayList,com.intel.openAttestation.manifest.hibernate.domain.*"%>
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
function updatePCR(){
    document.form1.action="GetPCRServlet";
    document.form1.submit();
}
function delPCR(){
	   var index = "";

    for(var i=0;i<document.form1.selectedIndex.length;i++){
        if(document.form1.selectedIndex[i].checked==true){
           index = document.form1.selectedIndex[i].value;
           break;
        }
    }
	   if (confirm("Delete index " + index + "?")){
		   document.form1.action="DelPCRServlet";
		   document.form1.submit();
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
   <form name="form1" method="post" action="QueryPCRServlet">
   <input type="hidden" name="return_page" value="QueryPCRServlet">
   <table class="main_div3">
     <tr><td class="nav_font">Query PCRs</td></tr>
     <tr><td>
	     <table border="0" style="margin:20px 20px 20px 25px">
	     <tr><td width="100px" class="table_header_font">&nbsp;Index:</td>
	     <td width="300px"><input type="text" name="pcrIndex" value="<%=request.getAttribute("pcrIndex")==null?"":request.getAttribute("pcrIndex")%>"></tr>
	     <tr><td width="100px" class="table_header_font">&nbsp;PCR Number:</td>
	     <td width="300px"><input type="text" name="pcrNumber" value="<%=request.getAttribute("pcrNumber")==null?"":request.getAttribute("pcrNumber")%>"></tr>
	     <tr><td width="100px" class="table_header_font">&nbsp;PCR Description:</td>
	     <td width="300px"><input type="text" name="pcrDesc" value="<%=request.getAttribute("pcrDesc")==null?"":request.getAttribute("pcrDesc")%>"></tr>
	     <tr><td class="nav_font" align="center" colspan="2">&nbsp;&nbsp;</td></tr>
	     <tr><td class="nav_font" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           <input type="submit" value="Query">&nbsp;&nbsp;<font class="error_msg_font"><%=request.getAttribute("query_message")==null?"":request.getAttribute("query_message") %></font></td></tr>
	     </table>
	 </td></tr>
     <% if (request.getAttribute("pcrs") != null){
     %>
     <tr><td>
	     <table border="1" style="margin:20px 20px 20px 25px">
		     <tr><td width="20px">&nbsp;</td>
		     <td width="40px" class="table_header_font" align="center">index</td>
		     <td width="90px" class="table_header_font" align="center">PCR Number</td>
			 <td width="200px" class="table_header_font" align="center">PCR Value</td>	     
		     <td width="200px" class="table_header_font" align="center">PCR Description</td>
		     <td width="110px" class="table_header_font" align="center">PCR Create User</td>
		     <td width="110px" class="table_header_font" align="center">PCR Create Time</td>
		     <td width="150px" class="table_header_font" align="center">PCR Last Update User</td>
		     <td width="150px" class="table_header_font" align="center">PCR Last Update Time</td></tr>
		     <% List<PCRManifest> pcrs = (List<PCRManifest>)request.getAttribute("pcrs");
				for(PCRManifest pcr: pcrs)
				{
             %>
		     <tr><td><input type="radio" name="selectedIndex" value="<%=pcr.getIndex() %>"></td>
	         <td class="table_font">&nbsp;<%=pcr.getIndex() %></td>
	         <td class="table_font">&nbsp;<%=pcr.getPCRNumber() %></td>
	         <td class="table_font">&nbsp;<%=pcr.getPCRValue() %></td>
	         <td class="table_font">&nbsp;<%=pcr.getPCRDesc()==null?"":pcr.getPCRDesc() %></td>
	         <td class="table_font">&nbsp;<%=pcr.getCreateRequestHost()==null?"":pcr.getCreateRequestHost() %></td>
	         <td class="table_font">&nbsp;<%=pcr.getCreateTime()==null?"":pcr.getCreateTime() %></td>
	         <td class="table_font">&nbsp;<%=pcr.getLastUpdateRequestHost()==null?"":pcr.getLastUpdateRequestHost() %></td>
	         <td class="table_font">&nbsp;<%=pcr.getLastUpdateTime()==null?"":pcr.getLastUpdateTime() %></td></tr>
	         <%} %>
	     </table>
	 </td></tr>
     <tr><td class="nav_font" align="center"><input type="button" value="Update" onclick="updatePCR()">&nbsp;&nbsp;
     <input type="button" value="Delete" onclick="delPCR()">
     &nbsp;&nbsp;<font class="error_msg_font"><%=request.getAttribute("message")==null?"":request.getAttribute("message") %></font></td></tr>
     <tr><td class="nav_font" align="center">&nbsp;&nbsp;</td></tr>
     <%} %>
   </table>
   </form>
   
</td>
</tr>
</table>

</body>
</html>