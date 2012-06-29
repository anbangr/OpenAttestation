#!/bin/sh
if test -e ClientInstallForLinuxOld.zip;then
rm -f ClientInstallForLinuxOld.zip
fi
mv ClientInstallForLinux.zip ClientInstallForLinuxOld.zip
if test -d ClientInstallForLinux;then
rm -rf ClientInstallForLinux
fi
mkdir ClientInstallForLinux

cp -rf linuxOatInstall/* ClientInstallForLinux

cp -r -f /usr/lib/apache-tomcat-6.0.29/webapps/HisPrivacyCAWebServices2/ClientFiles/endorsement.p12 ClientInstallForLinux/
#cp -r -f /usr/lib/apache-tomcat-6.0.29/webapps/HisPrivacyCAWebServices2/ClientFiles/lib ./ClientInstallForLinux/
#cp -r -f /usr/lib/apache-tomcat-6.0.29/webapps/HisPrivacyCAWebServices2/ClientFiles/TPMModule.properties ./ClientInstallForLinux/
#cp -r -f /usr/lib/apache-tomcat-6.0.29/webapps/HisPrivacyCAWebServices2/ClientFiles/exe ./ClientInstallForLinux/
cp -r -f /usr/lib/apache-tomcat-6.0.29/webapps/HisPrivacyCAWebServices2/ClientFiles/PrivacyCA.cer ClientInstallForLinux/
cp -r -f /usr/lib/apache-tomcat-6.0.29/webapps/HisPrivacyCAWebServices2/ClientFiles/TrustStore.jks ClientInstallForLinux/
cp -r -f /usr/lib/apache-tomcat-6.0.29/webapps/HisPrivacyCAWebServices2/ClientFiles/OATprovisioner.properties ClientInstallForLinux/
sed -i '/ClientPath/s/C:.*/\/OAT/' ClientInstallForLinux/OATprovisioner.properties
#DWC added two following lines for Chris
#cp -r -f /usr/lib/apache-tomcat-6.0.29/webapps/HisPrivacyCAWebServices2/ClientFiles/install.bat ./ClientInstallForLinux/
cp -r -f /usr/lib/apache-tomcat-6.0.29/webapps/HisPrivacyCAWebServices2/ClientFiles/OAT.properties ClientInstallForLinux/
sed -i 's/NIARL_TPM_Module\.exe/NIARL_TPM_Module/g' ./ClientInstallForLinux/OAT.properties

if test -e ClientInstallForLinux.zip;then
rm -rf ClientInstallForLinux.zip
fi

zip -9 -r ClientInstallForLinux.zip ClientInstallForLinux

#places the client installation folder up for tomcat6 to display
cp -f ClientInstallForLinux.zip /var/www/html/
