#!/bin/sh
mv ClientInstall.zip ClientInstallOld.zip
if test -d ClientInstall;then
rm -rf ClientInstall
fi
mkdir ClientInstall
cp -rf installers ClientInstall

cp -r -f /usr/lib/apache-tomcat-6.0.29/webapps/HisPrivacyCAWebServices2/ClientFiles/endorsement.p12 ClientInstall/installers/hisInstall/
cp -r -f /usr/lib/apache-tomcat-6.0.29/webapps/HisPrivacyCAWebServices2/ClientFiles/lib ClientInstall/installers/hisInstall/
cp -r -f /usr/lib/apache-tomcat-6.0.29/webapps/HisPrivacyCAWebServices2/ClientFiles/TPMModule.properties ClientInstall/installers/hisInstall/
cp -r -f /usr/lib/apache-tomcat-6.0.29/webapps/HisPrivacyCAWebServices2/ClientFiles/exe ClientInstall/installers/hisInstall/
cp -r -f /usr/lib/apache-tomcat-6.0.29/webapps/HisPrivacyCAWebServices2/ClientFiles/PrivacyCA.cer ClientInstall/installers/hisInstall/
cp -r -f /usr/lib/apache-tomcat-6.0.29/webapps/HisPrivacyCAWebServices2/ClientFiles/TrustStore.jks ClientInstall/installers/hisInstall/
cp -r -f /usr/lib/apache-tomcat-6.0.29/webapps/HisPrivacyCAWebServices2/ClientFiles/OATprovisioner.properties ClientInstall/installers/hisInstall/
cp -r -f /usr/lib/apache-tomcat-6.0.29/webapps/HisPrivacyCAWebServices2/ClientFiles/install.bat ClientInstall/installers/hisInstall/
cp -r -f /usr/lib/apache-tomcat-6.0.29/webapps/HisPrivacyCAWebServices2/ClientFiles/OAT.properties ClientInstall/installers/hisInstall/

if test -e ClientInstall.zip;then
rm -rf ClientInstall.zip
fi 
zip -9 -r ClientInstall.zip ClientInstall

sleep 5

cp -f ClientInstall.zip /var/www/html/

