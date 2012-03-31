################ Build installation package process#############
.	run "sh remove_jar_packages.sh" to remove all unnecessary jar packages
.	run "sh download_jar_packages.sh" to download new third party jar packages
.	run "sh distribute_jar_packages.sh" to distribute downloaded third party jar packages to corresponding source folders
.	run "sh rpm.sh -s /xxx/Source -t /xxx/apache-tomcat-x.x.x" to build installation rpm package
