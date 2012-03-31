#!/bin/sh

#download projects from : http://his.sh.intel.com/hg/OAT/file/d253082ba0d6. put OAT to the workspace 
#this script works at root directory of OAT. Just type sh build.sh to run it in a terminal console.
#It builds 6 projects:
# 1.TPMModule  2.HisClient 3.HisPrivacyCAWebServices2
# 4. HisWebServices 5.PrivacyCA 6.HisAppraiser

#1. build TPMModule
#type make after navigating to the folder in which the NIARL_TPM_Module 
make -C ./TPMModule/plain/linux/
#chmod +x NIARL_TPM_Module

#2. build HisAppraiser
ant -file ./HisAppraiser/build.xml
cp -rf ./HisAppraiser/HisAppraiser.jar ./OpenAttestationWebServices/WebContent/WEB-INF/lib/
cp -rf ./HisAppraiser/HisAppraiser.jar ./HisWebServices/clientlib/
cp -rf ./HisAppraiser/HisAppraiser.jar ./HisWebServices/WEB-INF/lib/HisAppraiser.jar
cp -rf ./HisAppraiser/HisAppraiser.jar ./OpenAttestationAdminConsole/WebContent/WEB-INF/lib/

#3. build HisWebServices
#cp -rf ./HisAppraiser/HisAppraiser.jar ./HisWebServices/WEB-INF/lib/
ant -file ./HisWebServices/build.xml server
cp ./HisWebServices/HisWebServices.war $1/webapps/
sh $1/bin/shutdown.sh
sh $1/bin/startup.sh
# This for loop makes the rpm wait until the .war file has unpacked before attempting to access the files that will be created
for((i = 1; i < 60; i++))
do

        rm -f ./warLog
         
        if test -e $1/webapps/HisWebServices;then
        echo the HisWebServices was unpacked!
        rm -f ./warLog
        sleep 5
        break
        fi

        sleep 1

        echo If this file is present after install then unpacking the HisWebServices.war file timed-out >> warLog

done

ant -file ./HisWebServices/build.xml client
cp -rf ./HisWebServices/clientlib/HisWebServices-client.jar ./PrivacyCA/lib/
cp -rf ./HisWebServices/clientlib/HisWebServices-client.jar ./HisClient/lib/
cp -rf ./HisWebServices/clientlib/HisWebServices-client.jar ./HisPrivacyCAWebServices2/ClientFiles/lib/
cp -rf ./HisWebServices/clientlib/HisWebServices-client.jar ./HisPrivacyCAWebServices2/WEB-INF/lib/

#4build TSSCoreService
if test -e ./TSSCoreService;then
   ant -file ./TSSCoreService/build.xml
   cp -rf ./TSSCoreService/TSSCoreService.jar ./PrivacyCA/lib/
   cp -rf ./TSSCoreService/TSSCoreService.jar ./HisPrivacyCAWebServices2/ClientFiles/lib/
fi

#5.build HisPrivacyCAWebServices2
#just run build.xml
#before run build.xml, you need install ant by typing yum install ant
for f in HisSetup.java   TpmIdentityRequest.java  TpmSymCaAttestation.java idResponse.java          TpmKeyParams.java        TpmSymmetricKey.java PrivacyCaException.java  TpmKeySubParams.java     TpmSymmetricKeyParams.java TpmAsymCaContents.java   TpmPubKey.java           TpmUtils.java TpmIdentityProof.java    TpmRsaKeyParams.java
do
  ln -s -f ../../../../../../PrivacyCA/src/gov/niarl/his/privacyca/$f HisPrivacyCAWebServices2/src/gov/niarl/his/privacyca/
done

ant -file ./HisPrivacyCAWebServices2/build.xml server
cp ./HisPrivacyCAWebServices2/HisPrivacyCAWebServices2.war $1/webapps/
#This is for loop makes the rpm wait until the .war file has unpacked before attempting to access the files that will be created
for((i = 1; i < 60; i++))
do

         if test -e $1/webapps/HisPrivacyCAWebServices2;then
        echo the Privacy CA was unpacked!
        rm -f ./warLog
        sleep 5
        break
        fi

        sleep 1

        echo If this file is present after install then unpacking the Privacy CA war file timed-out >> warLog

done
ant -file ./HisPrivacyCAWebServices2/build.xml client
cp -rf ./HisPrivacyCAWebServices2/clientlib/HisPrivacyCAWebServices2-client.jar ./HisPrivacyCAWebServices2/ClientFiles/lib/
cp -rf ./HisPrivacyCAWebServices2/clientlib/HisPrivacyCAWebServices2-client.jar ./PrivacyCA/lib/
sh $1/bin/shutdown.sh

#6. build PrivacyCA
ant -file ./PrivacyCA/build.xml
cp -rf ./PrivacyCA/PrivacyCA.jar ./HisPrivacyCAWebServices2/ClientFiles/lib/


#6. build HisClient
mkdir ./HisClient/jar
ant -file ./HisClient/build.xml
