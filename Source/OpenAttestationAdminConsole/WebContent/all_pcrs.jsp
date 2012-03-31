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
   <table class="main_div3">
     <tr><td class="nav_font">All PCRs</td></tr>
     <tr><td align="center">
	     <form name="form1" method="post" action="" >
	     <input type="hidden" id="return_page" name="return_page" value="GetAllPCRServlet">
	     <table border="1" style="margin:20px 20px 20px 25px">
	     <tr><td width="20px">&nbsp;</td>
	     <td width="40px" class="table_header_font" align="center">index</td>
	     <td width="90px" class="table_header_font" align="center">PCR Number</td>
		 <td width="200px" class="table_header_font" align="center">PCR Value</td>	     
	     <td width="200px" class="table_header_font" align="center">PCR Description</td>
	     <td width="110px" class="table_header_font" align="center">PCR Create Host</td>
	     <td width="110px" class="table_header_font" align="center">PCR Create Time</td>
	     <td width="150px" class="table_header_font" align="center">PCR Last Update Host</td>
	     <td width="150px" class="table_header_font" align="center">PCR Last Update Time</td></tr>
	     
	     <%
	        List<PCRManifest> pcrs = new ArrayList<PCRManifest>();
	        pcrs = (List<PCRManifest>)request.getAttribute("all_pcrs");
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
	     <td class="table_font">&nbsp;<%=pcr.getLastUpdateRequestHost()==null?"":pcr.getLastUpdateRequestHost()%></td>
	     <td class="table_font">&nbsp;<%=pcr.getLastUpdateTime()==null?"":pcr.getLastUpdateTime() %></td></tr>
	     <%} %>
	     </table>
	     </form>
	  </td></tr>
     <tr><td class="nav_font" align="center">
         <input type="button" value="Update" onclick="updatePCR()"> &nbsp;&nbsp;
         <input type="button" value="Delete" onclick="delPCR()">
          &nbsp;&nbsp;<font class="error_msg_font"><%=request.getAttribute("message")==null?"":request.getAttribute("message") %></font></td></tr>
     <tr><td class="nav_font" align="center">&nbsp;&nbsp;</td></tr>
   </table>
</td>
</tr>
</table>

</body>
</html>