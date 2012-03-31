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
	function attest(){
		for(var i=0;i<document.asyncAttest.host.length;i++){
	           if(document.asyncAttest.host[i].checked==true){
	        	   document.asyncAttest.host[i].value = document.asyncAttest.host_name[i].value;
	           }
	       }
		document.asyncAttest.submit();
	}
	
	function selectAll(){
		if (document.asyncAttest.all_host.checked == false){
			for(var i=0;i<document.asyncAttest.host.length;i++){
				document.asyncAttest.host[i].checked = false;
			}
		}else{
			for(var i=0;i<document.asyncAttest.host.length;i++){
				document.asyncAttest.host[i].checked = true;
			}
		}
	}

</script>
<body bgcolor="RGB(238,240,242)">
<%@ include file="header_bar.jsp" %>

<table>
<tr><td valign="top">
	<%@ include file="attest_left_list.jsp" %>
</td>
<td valign="top">
   <form name="asyncAttest" method="post" action="AsyncAttestServlet">
   <table class="main_div">
     <tr><td class="nav_font">Attestation Attest - Asnc Mode</td></tr>
     
     <tr><td align="center">
	     <table border="1" style="margin:20px 20px 20px 25px">
	     <tr><td width="20px"><input type="checkbox" name="all_host" onClick="selectAll()"></td>
	     <td width="100px" class="table_header_font" align="center">Hosts</td></tr>
	     
	      <tr><td><input type="checkbox" name="host" value= "Server11"></td>
	     <td class="table_font">&nbsp;<input type="text" name="host_name" value="Server11"></td></tr>
	     
	     <tr><td><input type="checkbox" name="host" value= "Server11.sh.intel.com"></td>
	     <td class="table_font">&nbsp;<input type="text" name="host_name" value="Server11.sh.intel.com"></td></tr>
	     
	     <tr><td><input type="checkbox" name="host" value="Host3"></td>
	     <td class="table_font">&nbsp;<input type="text" name="host_name" value="Host3"></td></tr>

	     <tr><td><input type="checkbox" name="host" value="Host4"></td>
	     <td class="table_font">&nbsp;<input type="text" name="host_name" value="Host4"></td></tr>
	     
	     <tr><td><input type="checkbox" name="host" value="Host5"></td>
	     <td class="table_font">&nbsp;<input type="text" name="host_name" value="Host5"></td></tr>
	     
	     <tr><td><input type="checkbox" name="host" value="Host6"></td>
	     <td class="table_font">&nbsp;<input type="text" name="host_name" value="Host6"></td></tr>
	     
	     
	     
	     </table>
	     
	     <table>
	     <tr>
	     	<td width="200px" class="table_header_font" align="center">PCR Mask (6 digit HEX)</td>
	     	<td align="center"><input type="text" name="PCRmask"></td>
	     </tr>
	      <tr><td colspan="2" class="error_msg_font">&nbsp;<%=request.getAttribute("message")==null? "": request.getAttribute("message")%></td></tr>
	     </table>
	  </td></tr>
	  
      <tr>
      	<td class="nav_font" align="center"><input type="button" value="Attest" onClick="attest()">&nbsp;&nbsp;
     										<input type="reset" value="Reset"></td>
     </tr>
     <tr><td class="nav_font" align="center">&nbsp;&nbsp;</td></tr>
   </table>
   </form>
</td>
</tr>
  
</table>

</body>
</html>