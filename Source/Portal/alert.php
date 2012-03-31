<?php
/*2012, U.S. Government, National Security Agency, National Information Assurance Research Laboratory

This is a work of the UNITED STATES GOVERNMENT and is not subject to copyright protection in the United States. Foreign copyrights may apply.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

鈥�Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

鈥�Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

鈥�Neither the name of the NATIONAL SECURITY AGENCY/NATIONAL INFORMATION ASSURANCE RESEARCH LABORATORY nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.*/

//CONNECT TO DATABASE
include("includes/dbconnect.php");

//CHECK ALERT ID
if(isset($_GET["id"]) AND is_numeric($_GET["id"]) AND $_GET["id"] > 0 AND $_GET["id"] < 4294967295)
{
	//alert id has validated so set up the variable
	$id = $_GET["id"];
}
else
{
	//don't set a default if the alert id is bad. Just stop execution and show an error
	die("Invalid ID number.");
}

//query the alerts and reports table for the data relating to this alert id
$row = mysql_fetch_array(mysql_query("SELECT alerts.id AS aid, alerts.*, audit_log.* FROM alerts JOIN audit_log ON alerts.audit_fk = audit_log.id WHERE alerts.id = " . $id));

//now go query for the details of the report submitted before the id number of the one above so we can get the previous pcr values
$previous = mysql_fetch_array(mysql_query("SELECT * FROM audit_log WHERE machine_name = '" . $row["machine_name"] . "' AND id < " . $row["audit_fk"] . " ORDER BY id DESC LIMIT 0,1"));
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html lang="en-US" xml:lang="en-US" xmlns="http://www.w3.org/1999/xhtml">
<head>
<?php
//INCLUDE STYLESHEETS
include("includes/styles.php");
?>
	<title>HIS Alert Details</title>
</head>
<body>
<?php
//INCLUDE CLASSIFICATION BANNER
include("includes/classification.php");
?>
	<div id="wrapper">
<?php
//INCLUDE HEADER
include("includes/header.php");

//INCLUDE NAV BAR
include("includes/navigation.php");
?>
		<div id="content">
			<div class="leftcol">
			</div>
			<div class="rightcol">
				<h1>Alert Details</h1>
				<form action="admin/updatealert.php" method="POST">
				<table>
				<tr>
					<th>ID</th>
					<th>Status</th>
					<th colspan="1">Assigned To</th>
				</tr>
				<tr>
					<td><?php echo $row["aid"]; ?><input type="hidden" name="id" value="<?php echo $row["aid"]; ?>" /></td>
					<td><select name="status">
						<option value="new" <?php if($row["status"] == "New") { echo "selected=\"selected\""; } ?>>New</option>
						<option value="inprogress" <?php if($row["status"] == "In Progress") { echo "selected=\"selected\""; } ?>>In Progress</option>
						<option value="closed" <?php if($row["status"] == "Closed") { echo "selected=\"selected\""; } ?>>Closed</option>
						<option value="cancelled" <?php if($row["status"] == "Cancelled") { echo "selected=\"selected\""; } ?>>Cancelled</option>
					</select></td>
					<td><input type="text" size="24" maxlength="24" name="assignedTo" value="<?php echo $row["assignedTo"]; ?>" /></td>
				</tr>
				<tr>
					<th colspan="3">Comments</th>
				</tr>
				<tr>
					<td colspan="4"><textarea name="comments" rows="8"><?php echo $row["comments"]; ?></textarea></td>
				</tr>
				</table>
				<div class="controlbuttons">
					<button name="Submit" type="Submit"><img src="images/fatcow/16/accept.png" alt="Submit" /> Submit Update</button>
					<!--<button name="Cancel" type="Cancel" onClick="javascript:history.back();"><img src="images/fatcow/16/delete.png" alt="Cancel" /> Cancel</button>-->
					<button name="Reset" type="Reset"><img src="images/fatcow/16/transform_rotate.png" alt="Reset" /> Reset Values</button>
				</div>
				</form>

				<h2>Report Details</h2>
				<table>
				<tr>
					<th colspan="2">Report</th>
					<th>Timestamp</th>
					<th colspan="2">Machine</th>
					<th colspan="2">User</th>
				</tr>
				<tr>
					<td><a href="report.php?id=<?php echo $row["audit_fk"]; ?>"><img src="images/fatcow/16/zoom.png" alt="report icon" /></a></td>
					<td><?php echo $row["audit_fk"]; ?></td>
					<td><?php echo $row["timestamp"]; ?></td>
					<td><a href="machine.php?name=<?php echo $row["machine_name"]; ?>"><img src="images/fatcow/16/computer.png" alt="machine icon" /></a></td>
					<td><?php echo $row["machine_name"]; ?></td>
					<td><a href="user.php?name=<?php echo $row["SID"]; ?>"><img src="images/fatcow/16/user.png" alt="user icon" /></a></td>
					<td><?php echo $row["SID"]; ?></td>
				</tr>
<?php
//DISPLAY INFORMATION ABOUT PCR DIFFERENCES
echo "				<tr>
					<th colspan=\"7\">PCR Analysis</th>
				</tr>\n";

//DISPLAY PCR ANALYSIS SECTION IFF THERE ARE PREVIOUS DIFFERENCES
if($row["previous_differences"] != NULL)
{
	echo "				<tr>
						<td>PCR</td>
						<td colspan=\"3\"><a href=\"report.php?id=" . $row["audit_fk"] . "\">Current Value</a></td>
						<td colspan=\"3\"><a href=\"report.php?id=" . $previous["id"] . "\">Previous Value</a></td>
				</tr>\n";

	//DISPLAY PCR ANALYSIS SECTION SPECIFIC CHANGES
	$token = strtok($row["previous_differences"], "|");		//use a token to parse through the list of previous differences. This is in the format |n| for each difference where n is the PCR index
	while ($token !== false)
	{
		echo "				<tr>\n";

		//DISPLAY PCR INDEX WITH HELPFUL TOOLTIP
		switch($token)
		{
		case 0:
		case 1:
			echo "					<td title=\"BIOS\">" . $token . "</td>\n";
			break;
		case 2:
		case 3:
			echo "					<td title=\"Expansion Devices\">" . $token . "</td>\n";
			break;
		case 4:
			echo "					<td title=\"Boot Media\">" . $token . "</td>\n";
			break;
		case 5:
			echo "					<td title=\"Boot Loader\">" . $token . "</td>\n";
			break;
		case 8:
		case 9:
			echo "					<td title=\"NTFS Boot Loader or GRUB\">" . $token . "</td>\n";
			break;
		case 10:
			echo "					<td title=\"Intel IMA\">" . $token . "</td>\n";
			break;
		case 11:
			echo "					<td title=\"Bit Locker\">" . $token . "</td>\n";
			break;
		case 12:
			echo "					<td title=\"Boot Loader Parameters\">" . $token . "</td>\n";
			break;
		case 13:
			echo "					<td title=\"Checkfile List\">" . $token . "</td>\n";
			break;
		case 14:
			echo "					<td title=\"Kernel, Initrd, Modules\">" . $token . "</td>\n";
			break;
		case 17:
			echo "					<td title=\"Trusted Boot\">" . $token . "</td>\n";
			break;
		case 18:
			echo "					<td title =\"MLE Hash\">" . $token . "</td>\n";
			break;
		default:
			echo "					<td>" . $token . "</td>\n";
		}

	//display the current PCR value followed by the previous value
		echo "					<td colspan=\"3\">" . $row["pcr" . $token] . "</td>
						<td colspan=\"3\">" . $previous["pcr" . $token] . "</td>
				</tr>\n";
	$token = strtok("|");		//move to the next token in previous_differences iff it exists
		}
}
else
{
	echo "				<tr>
					<td colspan=\"7\">No PCR Changes</td>
				</tr>\n";
}

//DISPLAY SIGNATURE INFORMATION
echo "				<tr>
					<th colspan=\"7\">Signature Analysis</th>
				</tr>\n";

//display the correct message based on if the signature validated or not
if(!$row["signature_verified"])
{	//signature did not validate
	echo "				<tr>
					<td colspan=\"7\">The signature was not validated. This means the identity and measurement information that triggered this alert is not trustworthy. Signature errors can be a result of several things. Benign explanations are machine re-enrollment, service calls requiring motherboard replacement, changes in the Trusted Software Stack (TSS like NTru or Trousers), or network errors. Malicious explanations are identity key manipulation, firmware tampering, or hardware tampering. A signature error requires investigation to determine if the change is benign or malicious.</td>
				</tr>\n";
}
else
{	//signature is valid
	echo "				<tr>
					<td colspan=\"7\">Signature Validated</td>
				</tr>\n";
}
?>
				</table>
			</div>
		</div>
<?php
//INCLUDE FOOTER
include("includes/footer.php");
?>
	</div>
</body>
</html>
<?php
//CLOSE DATABASE
include("includes/dbclose.php");
?>
