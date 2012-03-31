<?php
/*2012, U.S. Government, National Security Agency, National Information Assurance Research Laboratory

This is a work of the UNITED STATES GOVERNMENT and is not subject to copyright protection in the United States. Foreign copyrights may apply.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

鈥�Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

鈥�Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

鈥�Neither the name of the NATIONAL SECURITY AGENCY/NATIONAL INFORMATION ASSURANCE RESEARCH LABORATORY nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.*/

/* pagenumber is responsible for checking the pagenumber input from the url parameter. We must make sure it is a number and within bounds. If it is missing then we should set it manually.
 */
if(isset($_GET["page"]) && is_numeric($_GET["page"]) && $_GET["page"] >= 1 && $_GET["page"] <= 4294967295)
{
	//page number has been validated so set it based on the url parameter
	$limit = $_GET["page"];			//limit variable is for the MYSQL query
	$page = "page=" . $_GET["page"];	//url parameter for links
}
else
{
	//page number has failed validation so set it to defaults
	$limit = 1;
	$page = "page=1";
}
?>
