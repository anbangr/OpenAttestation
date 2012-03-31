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
if(isset($_GET["name"]) AND strlen($_GET["name"]) < 48)
{
$name = htmlspecialchars($_GET["name"]);
}
else
{
$name = "nobody";
}

//PROCESS FILTER INPUT URL PARAMETER -- THESE MUST BE IN AGREEMENT WITH LEFT COLUMN
switch($_GET["filter"])
{
case "errorfree":
$where = " WHERE signature_verified = 1 AND (previous_differences = '' OR previous_differences = null) AND machine_name = '" . $name . "'";
$filter = "filter=errorfree&name=" . $name;
break;
case "pcrs":
$where = " WHERE (previous_differences != '' OR previous_differences != null) AND machine_name = '" . $name . "'";
$filter = "filter=pcrs&name=" . $name;
break;
case "sig":
$where = " WHERE signature_verified = 0 AND machine_name = '" . $name . "'";
$filter = "filter=sig&name=" . $name;
break;
case "all":
default:
$where = " WHERE machine_name = '" . $name . "'";
$filter = "filter=all&name=" . $name;
break;
}

//PROCESS SORT INPUT URL PARAMETER -- THESE MUST BE IN AGREEMENT WITH TABLE COLUMNS
switch($_GET["sort"])
{
case "timestampasc":
$order = " ORDER BY audit_log.timestamp ASC";
$sort = "sort=timestampasc";
break;
case "timestampdesc":
$order = " ORDER BY audit_log.timestamp DESC";
$sort = "sort=timestampdesc";
break;
case "userasc":
$order = " ORDER BY audit_log.SID ASC, audit_log.id DESC";
$sort = "sort=userasc";
break;
case "userdesc":
$order = " ORDER BY audit_log.SID DESC, audit_log.id DESC";
$sort = "sort=userdesc";
break;
case "machineasc":
$order = " ORDER BY audit_log.machine_name ASC, audit_log.id DESC";
$sort = "sort=machineasc";
break;
case "machinedesc":
$order = " ORDER BY audit_log.machine_name DESC, audit_log.id DESC";
$sort = "sort=machinedesc";
break;
case "reportasc":
$order = " ORDER BY audit_log.id ASC";
$sort = "sort=reportasc";
break;
case "reportdesc":
default:
$order = " ORDER BY audit_log.id DESC";
$sort = "sort=reportdesc";
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
<title>HIS Reports</title>
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
<li><a href=\"machine.php?name=" . $name . "&" . $sort . "&filter=all\">All</a></li>
<li><a href=\"machine.php?name=" . $name . "&" . $sort . "&filter=errorfree\">Error Free</a></li>
<li><a href=\"machine.php?name=" . $name . "&" . $sort . "&filter=pcrs\">PCR Errors</a></li>
<li><a href=\"machine.php?name=" . $name . "&" . $sort . "&filter=sig\">Signature Errors</a></li>
</ul>\n";
?>
</div>
<div class="rightcol">
<?php
echo "<h1>Integrity Reports From " . $name . "</h1>\n";
//NEED A COUNT OF TOTAL RECORDS FOR THE PAGINATION SCRIPT
$count = mysql_fetch_row(mysql_query("SELECT COUNT(id) FROM audit_log" . $where));

//NEED A FILE LINK FOR THE PAGINATION SCRIPT
$link = "machine.php";

//INVOKE PAGINATION
include("includes/paginate.php");
?>
<table>
<?php
//DISPLAY TABLE HEADERS WITH SORT OPTIONS
echo "<tr>\n";
if($sort == "sort=reportdesc") { echo "<th colspan=\"2\"><a href=\"machine.php?" . $page . "&" . $filter . "&sort=reportasc\">Report <img src=\"images/fatcow/16/bullet_arrow_down.png\" alt=\"ascending icon\" /></a></th>";}
else { if($sort == "sort=reportasc") { echo "<th colspan=\"2\"><a href=\"machine.php?" . $page . "&" . $filter . "&sort=reportdesc\">Report <img src=\"images/fatcow/16/bullet_arrow_up.png\" alt=\"descending icon\" /></a></th>";}
else { echo "<th colspan=\"2\"><a href=\"machine.php?" . $page . "&" . $filter . "&sort=reportdesc\">Report</a></th>";}}
echo "<th>PCR</th>
<th>Sig</th>\n";
if($sort == "sort=timestampdesc") { echo "<th><a href=\"machine.php?" . $page . "&" . $filter . "&sort=timestampasc\">Timestamp <img src=\"images/fatcow/16/bullet_arrow_down.png\" alt=\"ascending icon\" /></a></th>";}
else { if($sort == "sort=timestampasc") { echo "<th><a href=\"machine.php?" . $page . "&" . $filter . "&sort=timestampdesc\">Timestamp <img src=\"images/fatcow/16/bullet_arrow_up.png\" alt=\"descending icon\" /></a></th>";}
else { echo "<th><a href=\"machine.php?" . $page . "&" . $filter . "&sort=timestampdesc\">Timestamp</a></th>";}}
if($sort == "sort=machinedesc") { echo "<th colspan=\"2\"><a href=\"machine.php?" . $page . "&" . $filter . "&sort=machineasc\">Machine <img src=\"images/fatcow/16/bullet_arrow_down.png\" alt=\"ascending icon\" /></a></th>";}
else { if($sort == "sort=machineasc") { echo "<th colspan=\"2\"><a href=\"machine.php?" . $page . "&" . $filter . "&sort=machinedesc\">Machine <img src=\"images/fatcow/16/bullet_arrow_up.png\" alt=\"descending icon\" /></a></th>";}
else { echo "<th colspan=\"2\"><a href=\"machine.php?" . $page . "&" . $filter . "&sort=machinedesc\">Machine</a></th>";}}
if($sort == "sort=userdesc") { echo "<th colspan=\"2\"><a href=\"machine.php?" . $page . "&" . $filter . "&sort=userasc\">User <img src=\"images/fatcow/16/bullet_arrow_down.png\" alt=\"ascending icon\" /></a></th>";}
else { if($sort == "sort=userasc") { echo "<th colspan=\"2\"><a href=\"machine.php?" . $page . "&" . $filter . "&sort=userdesc\">User <img src=\"images/fatcow/16/bullet_arrow_up.png\" alt=\"descending icon\" /></a></th>";}
else { echo "<th colspan=\"2\"><a href=\"machine.php?" . $page . "&" . $filter . "&sort=userdesc\">User</a></th>";}}
echo "</tr>\n";

//QUERY DATABASE FOR TABLE CONTENTS
$result = mysql_query("SELECT id, previous_differences, signature_verified, machine_name, SID, timestamp FROM audit_log" . $where . $order . " LIMIT " . (($limit - 1) * 100) . ",100");

//DISPLAY QUERY RESULTS IN TABLE
while($row = mysql_fetch_array($result))
{
echo "<tr>
<td><a href=\"report.php?id=" . $row["id"] . "\"><img src=\"images/fatcow/16/zoom.png\" alt=\"report icon\" /></a></td>
<td>" . $row["id"] . "</td>\n";
if($row["previous_differences"] == NULL) { echo "<td><img src=\"images/fatcow/16/accept.png\" alt=\"ok icon\" /></td>";}
else { echo "<td><img src=\"images/fatcow/16/exclamation.png\" alt=\"exclamation icon\" /></td>";}
if($row["signature_verified"] == "1") { echo "<td><img src=\"images/fatcow/16/accept.png\" alt=\"ok icon\" /></td>";}
else { echo "<td><img src=\"images/fatcow/16/exclamation.png\" alt=\"exclamation icon\" /></td>";}					
echo "<td>" . $row["timestamp"] . "</td>
<td><a href=\"machine.php?name=" . $row["machine_name"] . "\"><img src=\"images/fatcow/16/terminal.png\" /></a></td>
<td>" . $row["machine_name"] . "</td>
<td><a href=\"user.php?name=" . $row["SID"] . "\"><img src=\"images/fatcow/16/user.png\" /></a></td>
<td>" . $row["SID"] . "</td>
</tr>\n";
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
