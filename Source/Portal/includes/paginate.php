<?php
/*2012, U.S. Government, National Security Agency, National Information Assurance Research Laboratory

This is a work of the UNITED STATES GOVERNMENT and is not subject to copyright protection in the United States. Foreign copyrights may apply.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

鈥�Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

鈥�Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

鈥�Neither the name of the NATIONAL SECURITY AGENCY/NATIONAL INFORMATION ASSURANCE RESEARCH LABORATORY nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.*/
?>
				<div id="pageselector">
<?php
/* pageselector automatically generates a list of pages based on the maximum number of results returned
 * for a given query view. Pageselector also handles the navigation between various pages of content.
 *
 * DEPENDENCY: $count is the maximum number of results for a given query
 */
$last = (ceil($count[0] / 100));	//figure out the last page of results by rounding up to a full number

if($last > 0)				//if there are multiple pages to display then we can procede
{
	echo "					<ul>\n";			//begin the unordered list
	//DISPLAY FIRST PAGE LINK
	if($limit > 1) { echo "						<li><a href=\"" . $link . "?" . $sort . "&" . $filter . "&page=1\"><img src=\"images/fatcow/32/resultset_first.png\" alt=\"first page\" /></a></li>\n";}
	//DISPLAY LEFT BUTTON
	if(($limit - 1) > 1) { echo "						<li><a href=\"" . $link . "?" . $sort . "&" . $filter . "&page=" . ($limit - 1) . "\"><img src=\"images/fatcow/32/resultset_previous.png\" alt=\"previous page\" /></a></li>\n";}

	//DISPLAY LEFT BUFFER PAGES
	if(($limit - 2) >= 1) { echo "						<li><a href=\"" . $link . "?" . $sort . "&" . $filter . "&page=" . ($limit - 2) . "\">" . ($limit - 2) . "</a></li>\n";}
	if(($limit - 1) >= 1) { echo "						<li><a href=\"" . $link . "?" . $sort . "&" . $filter . "&page=" . ($limit - 1) . "\">" . ($limit - 1) . "</a></li>\n";}

	//DISPLAY CURRENT PAGE
	echo "						<li class=\"currentpage\">" . $limit . "</li>\n";

	//DISPLAY RIGHT BUFFER PAGES
	if(($limit + 1) <= $last) { echo "						<li><a href=\"" . $link . "?" . $sort . "&" . $filter . "&page=" . ($limit + 1) . "\">" . ($limit + 1) . "</a></li>\n";}
	if(($limit + 2) <= $last) { echo "						<li><a href=\"" . $link . "?" . $sort . "&" . $filter . "&page=" . ($limit + 2) . "\">" . ($limit + 2) . "</a></li>\n";}

	//DISPLAY RIGHT BUTTON
	if(($limit + 1) < $last) { echo "						<li><a href=\"" . $link . "?" . $sort . "&" . $filter . "&page=" . ($limit + 1) . "\"><img src=\"images/fatcow/32/resultset_next.png\" alt=\"next page\" /></a></li>\n";}

	//DISPLAY LAST PAGE LINK
	if($limit < $last) { echo "						<li><a href=\"" . $link . "?" . $sort . "&" . $filter . "&page=" . $last . "\"><img src=\"images/fatcow/32/resultset_last.png\" alt=\"last page\" /></a></li>\n";}
	echo "					</ul>\n";			//end unordered list
	}
?>
				</div>
