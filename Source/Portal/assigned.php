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

//PROCESS PAGE INPUT URL PARAMETER
include("includes/pagenumber.php");

//PROCESS NAME INPUT
if(isset($_GET["name"]) AND strlen($_GET["name"]) < 24)
{	//name is present and of acceptable length so escape it to protect against SQL injection
	$name = htmlspecialchars($_GET["name"]);
}
else
{	//name is invalid so assign a default
	$name = "nobody";
}

//PROCESS FILTER INPUT URL PARAMETER -- THESE MUST BE IN AGREEMENT WITH LEFT COLUMN
//filters use text fields so ESCAPE THEM because we don't want someone breaking the database -- this really should be done with USER IDs but I didn't build the database
switch($_GET["filter"])
{
case "closed":
	$where = " WHERE alerts.status = 'Closed' AND assignedTo = '" . mysql_real_escape_string($name) . "'";
	$filter = "filter=closed&name=" . $name;
	break;
case "cancelled":
	$where = " WHERE alerts.status = 'Cancelled' AND assignedTo = '" . mysql_real_escape_string($name) ."'";
	$filter = "filter=cancelled&name=" . $name;
	break;
case "inprogress":
	$where = " WHERE alerts.status = 'In Progress' AND assignedTo = '" . mysql_real_escape_string($name) ."'";
	$filter = "filter=inprogress&name=" . $name;
	break;
case "new":
	$where = " WHERE alerts.status = 'New' AND assignedTo = '" . mysql_real_escape_string($name) ."'";
	$filter = "filter=new&name=" . $name;
	break;
case "all":
default:
	$where = " WHERE assignedTo = '" . mysql_real_escape_string($name) . "'";
	$filter = "filter=all&name=" . $name;
}

//PROCESS SORT INPUT URL PARAMETER -- THESE MUST BE IN AGREEMENT WITH TABLE COLUMNS
//sorts define the ordering of content based on the table column being used for sorting. Make sure to properly form the ORDER BY clause for MYSQL and force ID DESC since we can't have multiple column sorts at the same time
switch($_GET["sort"])
{
case "reportasc":
	$order = " ORDER BY audit_log.id ASC, alerts.id DESC";
	$sort = "sort=reportasc";
	break;
case "reportdesc":
	$order = " ORDER BY audit_log.id DESC, alerts.id DESC";
	$sort = "sort=reportdesc";
	break;
case "timestampasc":
	$order = " ORDER BY audit_log.timestamp ASC";
	$sort = "sort=timestampasc";
	break;
case "timestampdesc":
	$order = " ORDER BY audit_log.timestamp DESC";
	$sort = "sort=timestampdesc";
	break;
case "assignedasc":
	$order = " ORDER BY alerts.assignedTo ASC, alerts.id DESC";
	$sort = "sort=assignedasc";
	break;
case "assigneddesc":
	$order = " ORDER BY alerts.assignedTo DESC, alerts.id DESC";
	$sort = "sort=assigneddesc";
	break;
case "userasc":
	$order = " ORDER BY audit_log.SID ASC, alerts.id DESC";
	$sort = "sort=userasc";
	break;
case "userdesc":
	$order = " ORDER BY audit_log.SID DESC, alerts.id DESC";
	$sort = "sort=userdesc";
	break;
case "machineasc":
	$order = " ORDER BY audit_log.machine_name ASC, alerts.id DESC";
	$sort = "sort=machineasc";
	break;
case "machinedesc":
	$order = " ORDER BY audit_log.machine_name DESC, alerts.id DESC";
	$sort = "sort=machinedesc";
	break;
case "statusasc":
	$order = " ORDER BY FIELD(status, 'Cancelled', 'Closed', 'In Progress', 'New'), alerts.id DESC";
	$sort = "sort=statusasc";
	break;
case "statusdesc":
	$order = " ORDER BY FIELD(status, 'New', 'In Progress', 'Closed', 'Cancelled'), alerts.id DESC";
	$sort = "sort=statusdesc";
	break;
case "idasc":
	$order = " ORDER BY alerts.id ASC";
	$sort = "sort=idasc";
	break;
case "iddesc":
default:
	$order = " ORDER BY alerts.id DESC";
	$sort = "sort=iddesc";
}
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html lang="en-US" xml:lang="en-US" xmlns="http://www.w3.org/1999/xhtml">
<head>
<?php
//INCLUDE STYLESHEETS
include("includes/styles.php");
?>
	<title>HIS Alerts</title>
</head>
<body>
<?php
//INCLUDE CLASSIFICATION MARKINGS
include("includes/classification.php");
?>
	<div id="wrapper">
<?php
//INCLUDE THE HEADER
include("includes/header.php");

//INCLUDE THE NAVIGATION BAR
include("includes/navigation.php");
?>
		<div id="content">
			<div class="leftcol">
<?php
//OUTPUT ALL FILTERS
echo "				<ul>
					<li><a href=\"assigned.php?name=" . $name . "&" . $sort . "&filter=all\">All</a></li>
					<li><a href=\"assigned.php?name=" . $name . "&" . $sort . "&filter=new\">New</a></li>
					<li><a href=\"assigned.php?name=" . $name . "&" . $sort . "&filter=inprogress\">In Progress</a></li>
					<li><a href=\"assigned.php?name=" . $name . "&" . $sort . "&filter=closed\">Closed</a></li>
					<li><a href=\"assigned.php?name=" . $name . "&" . $sort . "&filter=cancelled\">Cancelled</a></li>
				</ul>\n";
?>
</div>
<div class="rightcol">
<?php
echo "<h1>Alerts Assigned To " . $name . "</h1>\n";

//NEED A COUNT OF TOTAL RECORDS FOR THE PAGINATION SCRIPT
$count = mysql_fetch_row(mysql_query("SELECT COUNT(id) FROM alerts" . $where));

//NEED A FILE LINK FOR THE PAGINATION SCRIPT
$link = "assigned.php";

//INVOKE PAGINATION
include("includes/paginate.php");
?>
<table>
<?php
//DISPLAY TABLE HEADERS WITH SORT OPTIONS
echo "<tr>\n";
if($sort == "sort=iddesc") { echo "<th colspan=\"2\"><a href=\"assigned.php?" . $page . "&" . $filter . "&sort=idasc\">ID <img src=\"images/fatcow/16/bullet_arrow_down.png\" alt=\"ascending icon\" /></a></th>";}
else { if($sort == "sort=idasc") { echo "<th colspan=\"2\"><a href=\"assigned.php?" . $page . "&" . $filter . "&sort=iddesc\">ID <img src=\"images/fatcow/16/bullet_arrow_up.png\" alt=\"descending icon\" /></a></th>";}
else { echo "<th colspan=\"2\"><a href=\"assigned.php?" . $page . "&" . $filter . "&sort=iddesc\">ID</a></th>";}}
if($sort == "sort=statusdesc") { echo "<th><a href=\"assigned.php?" . $page . "&" . $filter . "&sort=statusasc\">Status <img src=\"images/fatcow/16/bullet_arrow_down.png\" alt=\"ascending icon\" /></a></th>";}
else { if($sort == "sort=statusasc") { echo "<th><a href=\"assigned.php?" . $page . "&" . $filter . "&sort=statusdesc\">Status <img src=\"images/fatcow/16/bullet_arrow_up.png\" alt=\"descending icon\" /></a></th>";}
else { echo "<th><a href=\"assigned.php?" . $page . "&" . $filter . "&sort=statusdesc\">Status</a></th>";}}
if($sort == "sort=assigneddesc") { echo "<th colspan=\"2\"><a href=\"assigned.php?" . $page . "&" . $filter . "&sort=assignedasc\">Assigned <img src=\"images/fatcow/16/bullet_arrow_down.png\" alt=\"ascending icon\" /></a></th>";}
else { if($sort == "sort=assignedasc") { echo "<th colspan=\"2\"><a href=\"assigned.php?" . $page . "&" . $filter . "&sort=assigneddesc\">Assigned <img src=\"images/fatcow/16/bullet_arrow_up.png\" alt=\"descending icon\" /></a></th>";}
else { echo "<th colspan=\"2\"><a href=\"assigned.php?" . $page . "&" . $filter . "&sort=assigneddesc\">Assigned</a></th>";}}
echo "<th>PCR</th>
<th>Sig</th>\n";
if($sort == "sort=reportdesc") { echo "<th colspan=\"2\"><a href=\"assigned.php?" . $page . "&" . $filter . "&sort=reportasc\">Report <img src=\"images/fatcow/16/bullet_arrow_down.png\" alt=\"ascending icon\" /></a></th>";}
else { if($sort == "sort=reportasc") { echo "<th colspan=\"2\"><a href=\"assigned.php?" . $page . "&" . $filter . "&sort=reportdesc\">Report <img src=\"images/fatcow/16/bullet_arrow_up.png\" alt=\"descending icon\" /></a></th>";}
else { echo "<th colspan=\"2\"><a href=\"assigned.php?" . $page . "&" . $filter . "&sort=reportdesc\">Report</a></th>";}}
if($sort == "sort=timestampdesc") { echo "<th><a href=\"assigned.php?" . $page . "&" . $filter . "&sort=timestampasc\">Timestamp <img src=\"images/fatcow/16/bullet_arrow_down.png\" alt=\"ascending icon\" /></a></th>";}
else { if($sort == "sort=timestampasc") { echo "<th><a href=\"assigned.php?" . $page . "&" . $filter . "&sort=timestampdesc\">Timestamp <img src=\"images/fatcow/16/bullet_arrow_up.png\" alt=\"descending icon\" /></a></th>";}
else { echo "<th><a href=\"assigned.php?" . $page . "&" . $filter . "&sort=timestampdesc\">Timestamp</a></th>";}}
if($sort == "sort=machinedesc") { echo "<th colspan=\"2\"><a href=\"assigned.php?" . $page . "&" . $filter . "&sort=machineasc\">Machine <img src=\"images/fatcow/16/bullet_arrow_down.png\" alt=\"ascending icon\" /></a></th>";}
else { if($sort == "sort=machineasc") { echo "<th colspan=\"2\"><a href=\"assigned.php?" . $page . "&" . $filter . "&sort=machinedesc\">Machine <img src=\"images/fatcow/16/bullet_arrow_up.png\" alt=\"descending icon\" /></a></th>";}
else { echo "<th colspan=\"2\"><a href=\"assigned.php?" . $page . "&" . $filter . "&sort=machinedesc\">Machine</a></th>";}}
if($sort == "sort=userdesc") { echo "<th colspan=\"2\"><a href=\"assigned.php?" . $page . "&" . $filter . "&sort=userasc\">User <img src=\"images/fatcow/16/bullet_arrow_down.png\" alt=\"ascending icon\" /></a></th>";}
else { if($sort == "sort=userasc") { echo "<th colspan=\"2\"><a href=\"assigned.php?" . $page . "&" . $filter . "&sort=userdesc\">User <img src=\"images/fatcow/16/bullet_arrow_up.png\" alt=\"descending icon\" /></a></th>";}
else { echo "<th colspan=\"2\"><a href=\"assigned.php?" . $page . "&" . $filter . "&sort=userdesc\">User</a></th>";}}
echo "</tr>\n";

//QUERY DATABASE FOR TABLE CONTENTS
$result = mysql_query("SELECT alerts.*, audit_log.timestamp, audit_log.SID, audit_log.machine_name, audit_log.previous_differences, audit_log.signature_verified FROM alerts JOIN audit_log ON alerts.audit_fk = audit_log.id" . $where . $order . " LIMIT " . (($limit - 1) * 100) . ",100");

//DISPLAY QUERY RESULTS IN TABLE
if(!mysql_num_rows($result))
{
echo "<tr><td colspan=\"14\">No Results</td></tr>";
}
else
{
while($row = mysql_fetch_array($result))
{
echo "<tr>
<td><a href=\"alert.php?id=" . $row["id"] . "\"><img src=\"images/fatcow/16/alarm_bell.png\" alt=\"alert icon\" /></a></td>
<td>" . $row["id"] . "</td>
<td>" . $row["status"] . "</td>
<td><a href=\"assigned.php?name=" . $row["assignedTo"] . "\"><img src=\"images/fatcow/16/user_suit.png\" /></a></td>
<td>" . $row["assignedTo"] . "</td>\n";
if($row["previous_differences"] == NULL) { echo "<td><img src=\"images/fatcow/16/accept.png\" alt=\"ok icon\" /></td>";}
else { echo "<td><img src=\"images/fatcow/16/exclamation.png\" alt=\"exclamation icon\" /></td>";}
if($row["signature_verified"] == "1") { echo "<td><img src=\"images/fatcow/16/accept.png\" alt=\"ok icon\" /></td>";}
else { echo "<td><img src=\"images/fatcow/16/exclamation.png\" alt=\"exclamation icon\" /></td>";}					
echo "<td><a href=\"report.php?id=" . $row["audit_fk"] . "\"><img src=\"images/fatcow/16/zoom.png\" alt=\"report icon\" /></a></td>
<td>" . $row["audit_fk"] . "</td>
<td>" . $row["timestamp"] . "</td>
<td><a href=\"machine.php?name=" . $row["machine_name"] . "\"><img src=\"images/fatcow/16/terminal.png\" /></a></td>
<td>" . $row["machine_name"] . "</td>
<td><a href=\"user.php?name=" . $row["SID"] . "\"><img src=\"images/fatcow/16/user.png\" /></a></td>
<td>" . $row["SID"] . "</td>
</tr>\n";
}
}
?>
</table>
</div>
</div>
<?php
//INCLUDE THE FOOTER
include("includes/footer.php");
?>
</div>
</body>
</html>
<?php
//CLOSE DATABASE CONNECTION
include("includes/dbclose.php");
?>
