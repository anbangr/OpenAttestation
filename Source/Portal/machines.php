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

//PROCESS FILTER INPUT URL PARAMETER -- THESE MUST BE IN AGREEMENT WITH LEFT COLUMN
switch($_GET["filter"])
{
case "all":
$where = "";
$filter = "filter=all";
break;
case "inactive":
$where = " WHERE machine_cert.active = 0";
$filter = "filter=inactive";
break;
case "active":
default:
$where = " WHERE machine_cert.active = 1";
$filter = "filter=active";
break;
}

//PROCESS SORT INPUT URL PARAMETER -- THESE MUST BE IN AGREEMENT WITH TABLE COLUMNS
switch($_GET["sort"])
{
case "timestampasc":
$order = " ORDER BY machine_cert.timestamp ASC";
$sort = "sort=timestampasc";
break;
case "timestampdesc":
$order = " ORDER BY machine_cert.timestamp DESC";
$sort = "sort=timestampdesc";
break;
case "lastrepasc":
$order = " ORDER BY lastrep ASC";
$sort = "sort=lastrepasc";
break;
case "lastrepdesc":
$order = " ORDER BY lastrep DESC";
$sort = "sort=lastrepdesc";
break;
case "reportdesc":
$order = " ORDER BY aid DESC";
$sort = "sort=reportdesc";
break;
case "reportasc":
$order = " ORDER BY aid ASC";
$sort = "sort=reportasc";
break;
case "machinedesc":
$order = " ORDER BY machine_cert.machine_name DESC";
$sort = "sort=machinedesc";
break;
case "machineasc":
default:
$order = " ORDER BY machine_cert.machine_name ASC";
$sort = "sort=machineasc";
break;
}
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html lang="en-US" xml:lang="en-US" xmlns="http://www.w3.org/1999/xhtml">
<head>
<?php
//INCLUDE STYLESHEETS
include("includes/styles.php");
?>
<title>HIS Machines</title>
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
echo "<ul>
<li><a href=\"machines.php?". $sort . "&filter=active\">Active</a></li>
<li><a href=\"machines.php?". $sort . "&filter=inactive\">Inactive</a></li>
<li><a href=\"machines.php?". $sort . "&filter=all\">All</a></li>
</ul>\n";
?>
</div>
<div class="rightcol">
<h1>Enrolled Machines</h1>
<p><strong>Note:</strong> Due to the complex nature of the last check in query this page may be slow to load and upate.</p>
<?php
//NEED A COUNT OF TOTAL RECORDS FOR THE PAGINATION SCRIPT
$count = mysql_fetch_row(mysql_query("SELECT COUNT(id) FROM machine_cert" . $where));

//NEED A FILE LINK FOR THE PAGINATION SCRIPT
$link = "machines.php";

//INVOKE PAGINATION
include("includes/paginate.php");
?>
<table>
<?php
//DISPLAY TABLE HEADERS WITH SORT OPTIONS
echo "<tr>\n";
if($sort == "sort=machinedesc") { echo "<th colspan=\"2\"><a href=\"machines.php?" . $page . "&" . $filter . "&sort=machineasc\">Machine <img src=\"images/fatcow/16/bullet_arrow_down.png\" alt=\"ascending icon\" /></a></th>";}
else { if($sort == "sort=machineasc") { echo "<th colspan=\"2\"><a href=\"machines.php?" . $page . "&" . $filter . "&sort=machinedesc\">Machine <img src=\"images/fatcow/16/bullet_arrow_up.png\" alt=\"descending icon\" /></a></th>";}
else { echo "<th colspan=\"2\"><a href=\"machines.php?" . $page . "&" . $filter . "&sort=machinedesc\">Machine</a></th>";}}
if($sort == "sort=timestampdesc") { echo "<th><a href=\"machines.php?" . $page . "&" . $filter . "&sort=timestampasc\">Enrolled <img src=\"images/fatcow/16/bullet_arrow_down.png\" alt=\"ascending icon\" /></a></th>";}
else { if($sort == "sort=timestampasc") { echo "<th><a href=\"machines.php?" . $page . "&" . $filter . "&sort=timestampdesc\">Enrolled <img src=\"images/fatcow/16/bullet_arrow_up.png\" alt=\"descending icon\" /></a></th>";}
else { echo "<th><a href=\"machines.php?" . $page . "&" . $filter . "&sort=timestampdesc\">Enrolled</a></th>";}}
echo "<th>Cert</th>
<th>Active</th>\n";
if($sort == "sort=lastrepdesc") { echo "<th><a href=\"machines.php?" . $page . "&" . $filter . "&sort=lastrepasc\">Last Check In <img src=\"images/fatcow/16/bullet_arrow_down.png\" alt=\"ascending icon\" /></a></th>";}
else { if($sort == "sort=lastrepasc") { echo "<th><a href=\"machines.php?" . $page . "&" . $filter . "&sort=lastrepdesc\">Last Check In <img src=\"images/fatcow/16/bullet_arrow_up.png\" alt=\"descending icon\" /></a></th>";}
else { echo "<th><a href=\"machines.php?" . $page . "&" . $filter . "&sort=lastrepdesc\">Last Check In</a></th>";}}
if($sort == "sort=reportdesc") { echo "<th colspan=\"2\"><a href=\"machines.php?" . $page . "&" . $filter . "&sort=reportasc\">Last Report <img src=\"images/fatcow/16/bullet_arrow_down.png\" alt=\"ascending icon\" /></a></th>";}
else { if($sort == "sort=reportasc") { echo "<th colspan=\"2\"><a href=\"machines.php?" . $page . "&" . $filter . "&sort=reportdesc\">Last Report <img src=\"images/fatcow/16/bullet_arrow_up.png\" alt=\"descending icon\" /></a></th>";}
else { echo "<th colspan=\"2\"><a href=\"machines.php?" . $page . "&" . $filter . "&sort=reportdesc\">Last Report</a></th>";}}
echo "</tr>\n";

//QUERY DATABASE FOR TABLE CONTENTS
$result = mysql_query("SELECT machine_cert.id AS mid, machine_cert.machine_name, machine_cert.timestamp, machine_cert.active, MAX(audit_log.timestamp) AS lastrep, MAX(audit_log.id) AS aid FROM machine_cert LEFT JOIN audit_log ON machine_cert.id = audit_log.machine_id" . $where . " GROUP BY machine_cert.id" . $order . " LIMIT " . (($limit - 1) * 100) . ",100");

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
<td><a href=\"machine.php?name=" . $row["machine_name"] . "\"><img src=\"images/fatcow/16/terminal.png\" /></a></td>
<td>" . $row["machine_name"] . "</td>
<td>" . $row["timestamp"] . "</td>
<td><a href=\"certificate.php?id=" . $row["mid"] . "\"><img src=\"images/fatcow/16/ssl_certificates.png\" alt=\"certificate icon\" /></a></td>\n";
if($row["active"] == 1) { echo "<td><img src=\"images/fatcow/16/accept.png\" alt=\"active icon\" /></td>\n";}
else { echo "<td><img src=\"images/fatcow/16/cancel.png\" alt=\"inactive icon\" /></td>\n";}
echo "<td>" . $row["lastrep"] . "</td>
<td><a href=\"report.php?id=" . $row["aid"] . "\"><img src=\"images/fatcow/16/zoom.png\" alt=\"report icon\" /></a></td>
<td>" . $row["aid"] . "</td>\n";
echo "</tr>\n";
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
