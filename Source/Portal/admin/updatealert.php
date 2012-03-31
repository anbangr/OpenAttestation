<?php
/*2012, U.S. Government, National Security Agency, National Information Assurance Research Laboratory

This is a work of the UNITED STATES GOVERNMENT and is not subject to copyright protection in the United States. Foreign copyrights may apply.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

鈥�Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

鈥�Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

鈥�Neither the name of the NATIONAL SECURITY AGENCY/NATIONAL INFORMATION ASSURANCE RESEARCH LABORATORY nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.*/

include("../includes/dbconnect.php");	//connect to the database
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html lang="en-US" xml:lang="en-US" xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>HIS Alert Update Page</title>
</head>
<body>
	<?php
	/* This page assumes that it will only be accessed after filling out the form on the alertdetails.php page. We must be able to handle a situation where that is not the case.
	 */

	//check all paramters expected from the form
	if(isset($_POST["id"]) AND isset($_POST["status"]) AND isset($_POST["assignedTo"]) AND isset($_POST["comments"]))
	{
		//VALIDATE THE INDIVIDUAL INPUT FIELDS
		//CHECK THE ALERT ID NUMBER
		if(is_numeric($_POST["id"]))
		{
			$id = $_POST["id"];
		}
		else
		{
			die("ID is invalid.");
		}

		//CHECK THE STATUS FIELD
		switch($_POST["status"])
		{
		case "new":
			$status = "New";
			break;
		case "inprogress":
			$status = "In Progress";
			break;
		case "closed":
			$status = "Closed";
			break;
		case "cancelled":
			$status = "Cancelled";
			break;
		default:
			die("Status is invalid.");
		}

		//CHECK THE ASSIGNED TO FIELD
		if(strlen($_POST["assignedTo"]) > 0 AND strlen($_POST["assignedTo"]) < 24)
		{
			$assignedTo = mysql_real_escape_string($_POST["assignedTo"]);		//combat SQL injection attacks with mysql_real_escape_string()
		}
		else
		{
			die("Assigned To is invalid.");
		}

		//CHECK THE COMMENTS FIELD
		$comments = mysql_real_escape_string($_POST["comments"]);			//really can't validate these because it's free-form text so just protect against SQL injection

		//UPDATE DATABASE
		$result = mysql_query('UPDATE alerts SET assignedTo = "' . $assignedTo . '", status = "' . $status . '", comments = "' . $comments . '" WHERE id = ' . $id);
		if(!$result)
		{
			die("Error updating database.");					//SQL query failed so stop execution
		}
		echo "<h1>Database updated successfully. Please wait to be redirected or use your browser's back button.</h1>";

		header("refresh: 2; ../alerts.php");						//automatically forward user to the main alerts page
	}
	else
	{
		//A FIELD IS MISSING SO REJECT ALL INPUTS
		echo "<h1>Form submission is invalid. Please use your browser's back button.</h1>";
	}
	?>
</body>
</html>
<?php
include("../includes/dbclose.php");	//close the database connection
?>
