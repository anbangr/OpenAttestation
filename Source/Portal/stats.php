<?php
/*2012, U.S. Government, National Security Agency, National Information Assurance Research Laboratory

This is a work of the UNITED STATES GOVERNMENT and is not subject to copyright protection in the United States. Foreign copyrights may apply.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

鈥�Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

鈥�Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

鈥�Neither the name of the NATIONAL SECURITY AGENCY/NATIONAL INFORMATION ASSURANCE RESEARCH LABORATORY nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.*/
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html lang="en-US" xml:lang="en-US" xmlns="http://www.w3.org/1999/xhtml">
<head>
<?php
//INCLUDE STYLESHEETS
include("includes/styles.php");
?>
<title>HIS Statistics</title>
</head>
<body>
<?php
//INCLUDE CLASSIFICATION MARKINGS
include("includes/classification.php");
?>
<div id="wrapper">
<?php
//OPEN A DATABASE CONNECTION
include("includes/dbconnect.php");

//INCLUDE THE HEADER
include("includes/header.php");

//INCLUDE THE NAVIGATION BAR
include("includes/navigation.php");
?>
<div id="content">
<div class="leftcol">
</div>
<div class="rightcol">
<h1>Statistics</h1>
<h2>Alerts</h2>
<table>
<th>Total Alerts</th>
<th>Unassigned</th>
<th>New</th>
<th>In Progress</th>
<th>Closed</th>
<th>Cancelled</th>
</tr>
<?php
//QUERY FOR EACH CELL IN THE ALERTS STATS TABLE
$total = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM alerts"));
$unassigned = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM alerts WHERE assignedTo = 'nobody'"));
$new = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM alerts WHERE status = 'New'"));
$inprogress = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM alerts WHERE status = 'In Progress'"));
$closed = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM alerts WHERE status = 'Closed'"));
$cancelled = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM alerts WHERE status = 'Cancelled'"));

//OUTPUT RESULTS IN APPROPRIATE LOCATIONS
echo "<tr>
<td>" . $total[0] . "</td>
<td>" . $unassigned[0] . "</td>
<td>" . $new[0] . "</td>
<td>" . $inprogress[0] . "</td>
<td>" . $closed[0] . "</td>
<td>" . $cancelled[0] . "</td>
</tr>\n";
?>
</table>
<h2>Reports</h2>
<table>
<tr>
<th>&nbsp;</th>
<th>All Time</th>
<th>Today</th>
<th>Yesterday</th>
<th>This Month</th>
<th>Last Month</th>
<th>This Year</th>
<th>Last Year</th>
</tr>
<tr>
<th>Total Reports</th>
<?php
//QUERIES TO FILL IN ALL CELLS OF THE TOTAL REPORTS ROW
$reportsalltime = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log"));
$reportstoday = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE DATE(timestamp) = DATE(NOW())"));
$reportsyesterday = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE DATE(timestamp) = DATE(DATE_SUB(NOW(), INTERVAL 1 DAY))"));
$reportsthismonth = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE MONTH(timestamp) = MONTH(NOW())"));
$reportslastmonth = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE MONTH(timestamp) = MONTH(DATE_SUB(NOW(), INTERVAL 1 MONTH))"));
$reportsthisyear = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE YEAR(timestamp) = YEAR(NOW())"));
$reportslastyear = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE YEAR(timestamp) = YEAR(DATE_SUB(NOW(), INTERVAL 1 YEAR))"));

//OUTPUT RESULTS IN APPROPRIATE LOCATIONS
echo "<td>" . $reportsalltime[0] . "</td>
<td>" . $reportstoday[0] . "</td>
<td>" . $reportsyesterday[0] . "</td>
<td>" . $reportsthismonth[0] . "</td>
<td>" . $reportslastmonth[0] . "</td>
<td>" . $reportsthisyear[0] . "</td>
<td>" . $reportslastyear[0] . "</td>\n";
?>
</tr>
<tr>
<th>PCR Errors</th>
<?php
//QUERIES TO FILL IN ALL CELLS OF THE PCR ERRORS ROW
$pcrreportsalltime = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE previous_differences != ''"));
$pcrreportstoday = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE DATE(timestamp) = DATE(NOW()) AND previous_differences != ''"));
$pcrreportsyesterday = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE DATE(timestamp) = DATE(DATE_SUB(NOW(), INTERVAL 1 DAY)) AND previous_differences != ''"));
$pcrreportsthismonth = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE MONTH(timestamp) = MONTH(NOW()) AND previous_differences != ''"));
$pcrreportslastmonth = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE MONTH(timestamp) = MONTH(DATE_SUB(NOW(), INTERVAL 1 MONTH)) AND previous_differences != ''"));
$pcrreportsthisyear = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE YEAR(timestamp) = YEAR(NOW()) AND previous_differences != ''"));
$pcrreportslastyear = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE YEAR(timestamp) = YEAR(DATE_SUB(NOW(), INTERVAL 1 YEAR)) AND previous_differences != ''"));

//OUTPUT RESULTS IN APPROPRIATE LOCATIONS
echo "<td>" . $pcrreportsalltime[0] . "</td>
<td>" . $pcrreportstoday[0] . "</td>
<td>" . $pcrreportsyesterday[0] . "</td>
<td>" . $pcrreportsthismonth[0] . "</td>
<td>" . $pcrreportslastmonth[0] . "</td>
<td>" . $pcrreportsthisyear[0] . "</td>
<td>" . $pcrreportslastyear[0] . "</td>\n";
?>
</tr>
<tr>
<th>Invalid Signatures</th>
<?php
//QUERIES TO FILL IN ALL CELLS OF THE INVALID SIGNATURES ROW
$sigreportsalltime = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE signature_verified = 0"));
$sigreportstoday = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE DATE(timestamp) = DATE(NOW()) AND signature_verified = 0"));
$sigreportsyesterday = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE DATE(timestamp) = DATE(DATE_SUB(NOW(), INTERVAL 1 DAY)) AND signature_verified = 0"));
$sigreportsthismonth = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE MONTH(timestamp) = MONTH(NOW()) AND signature_verified = 0"));
$sigreportslastmonth = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE MONTH(timestamp) = MONTH(DATE_SUB(NOW(), INTERVAL 1 MONTH)) AND signature_verified = 0"));
$sigreportsthisyear = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE YEAR(timestamp) = YEAR(NOW()) AND signature_verified = 0"));
$sigreportslastyear = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE YEAR(timestamp) = YEAR(DATE_SUB(NOW(), INTERVAL 1 YEAR)) AND signature_verified = 0"));

//OUTPUT RESULTS IN APPROPRIATE LOCATIONS
echo "<td>" . $sigreportsalltime[0] . "</td>
<td>" . $sigreportstoday[0] . "</td>
<td>" . $sigreportsyesterday[0] . "</td>
<td>" . $sigreportsthismonth[0] . "</td>
<td>" . $sigreportslastmonth[0] . "</td>
<td>" . $sigreportsthisyear[0] . "</td>
<td>" . $sigreportslastyear[0] . "</td>\n";
?>
</tr>
<tr>
<th>Error-Free</th>
<?php
//QUERIES TO FILL IN ALL CELLS OF THE ERROR-FREE ROW
$freereportsalltime = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE signature_verified = 1 AND previous_differences = ''"));
$freereportstoday = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE DATE(timestamp) = DATE(NOW()) AND signature_verified = 1 AND previous_differences = ''"));
$freereportsyesterday = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE DATE(timestamp) = DATE(DATE_SUB(NOW(), INTERVAL 1 DAY)) AND signature_verified = 1 AND previous_differences = ''"));
$freereportsthismonth = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE MONTH(timestamp) = MONTH(NOW()) AND signature_verified = 1 AND previous_differences = ''"));
$freereportslastmonth = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE MONTH(timestamp) = MONTH(DATE_SUB(NOW(), INTERVAL 1 MONTH)) AND signature_verified = 1 AND previous_differences = ''"));
$freereportsthisyear = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE YEAR(timestamp) = YEAR(NOW()) AND signature_verified = 1 AND previous_differences = ''"));
$freereportslastyear = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM audit_log WHERE YEAR(timestamp) = YEAR(DATE_SUB(NOW(), INTERVAL 1 YEAR)) AND signature_verified = 1 AND previous_differences = ''"));

//OUTPUT RESULTS IN APPROPRIATE LOCATIONS
echo "<td>" . $freereportsalltime[0] . "</td>
<td>" . $freereportstoday[0] . "</td>
<td>" . $freereportsyesterday[0] . "</td>
<td>" . $freereportsthismonth[0] . "</td>
<td>" . $freereportslastmonth[0] . "</td>
<td>" . $freereportsthisyear[0] . "</td>
<td>" . $freereportslastyear[0] . "</td>\n";
?>
</tr>
</table>
<h2>Machines</h2>
<table>
<tr>
<th>Enrollments</th>
<th>Active</th>
<th>Inactive</th>
</tr>
<?php
//QUERIES TO FILL IN ALL CELLS OF THE MACHINE TABLE
$enrollments = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM machine_cert"));
$active = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM machine_cert WHERE active = 1"));
$inactive = mysql_fetch_array(mysql_query("SELECT COUNT(id) FROM machine_cert WHERE active = 0"));

//OUTPUT RESULTS IN APPROPRIATE LOCATIONS
echo "<tr>
<td>" . $enrollments[0] . "</td>
<td>" . $active[0] . "</td>
<td>" . $inactive[0] . "</td>
</tr>\n";
?>
</table>
</div>
</div>
<?php
//INCLUDE THE FOOTER
include("includes/footer.php");

//CLOSE THE DATABASE CONNECTION
include("includes/dbclose.php");
?>
</div>
</body>
</html>
