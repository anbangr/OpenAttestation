<?php
/*2012, U.S. Government, National Security Agency, National Information Assurance Research Laboratory

This is a work of the UNITED STATES GOVERNMENT and is not subject to copyright protection in the United States. Foreign copyrights may apply.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

鈥�Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

鈥�Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

鈥�Neither the name of the NATIONAL SECURITY AGENCY/NATIONAL INFORMATION ASSURANCE RESEARCH LABORATORY nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.*/

//CONNECT TO THE DATABASE
include("includes/dbconnect.php");

//CHECK INPUTS FOR PCR INDEX OR DEFAULT TO ALL INDEXES
if(isset($_GET["index"]) AND is_numeric($_GET["index"]) AND $_GET["index"] < 24 AND $_GET["index"] >= 0)
{
$index = $_GET["index"];
}
else
{
$index = -1;
}
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html lang="en-US" xml:lang="en-US" xmlns="http://www.w3.org/1999/xhtml">
<head>
<?php
//INCLUDE STYLESHEETS
include("includes/styles.php");
?>
<title>HIS</title>
</head>
<body>
<?php
//INCLUDE CLASSIFICATION BANNER
include("includes/classification.php");
?>
<div id="wrapper">
<?php
//INCLUDE SITE HEADER
include("includes/header.php");

//INCLUDE SITE NAV BAR
include("includes/navigation.php");
?>
<div id="content">
<div class="leftcol">
<ul>
<li><a href="pcrs.php">PCR Home</a></li>
</ul>
</div>
<div class="rightcol">
<h1>PCR Measurement Stats</h1>
<h2>Most Common Values Per Index</h2>
<?php
//WARN USER WHEN THEY'VE PICKED A PARTICULAR PCR INDEX
if($index != -1) {echo "<p>Displaying all unique values from index " . $index . ".\n";}
?>
<table>
<tr>
<th colspan="2">Index</th>
<th>Value</th>
<th>Count</th>
</tr>
<?php
//OUPUT ALL PCRS BECAUSE -1 MEANS NOTHING IS SELECTED
if($index == -1)
{
for($counter = 0; $counter < 24; $counter += 1)
{
$row = mysql_fetch_array(mysql_query("SELECT pcr" . $counter . ", count(pcr" . $counter . ") AS pcount FROM audit_log WHERE pcr" . $counter . " != '' GROUP BY pcr" . $counter . " ORDER BY pcount DESC"));
echo "<tr>
<td><a href=\"pcrs.php?index=" . $counter . "\"><img src=\"images/fatcow/16/chart_pie_alternative.png\" alt=\"chart icon\" /></a></td>
<td>" . $counter . "</td>
<td>" . $row["pcr" . $counter] . "</td>
<td>" . $row["pcount"] . "</td>
</tr>\n";
}
}
else
{
//OUTPUT ALL UNIQUE RESULTS FOR THE SELECTED PCR INDEX
$result = mysql_query("SELECT pcr" . $index . ", count(pcr" . $index . ") AS pcount FROM audit_log WHERE pcr" . $index . " != '' GROUP BY pcr" . $index . " ORDER BY pcount DESC");
while($row = mysql_fetch_array($result))
{
echo "<tr>
<td><!--<a href=\"reports.php?index=" . $index . "&pcr=" . $row["pcr" . $index] . "\"><img src=\"images/fatcow/16/zoom.png\" alt=\"report icon\" /></a>-->&nbsp;</td>
<td>" . $index . "</td>
<td>" . $row["pcr" . $index] . "</td>
<td>" . $row["pcount"] . "</td>
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
//DISCONNECT FROM THE DATABASE
include("includes/dbclose.php");
?>
