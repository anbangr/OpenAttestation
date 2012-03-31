pushd ./exe 1>/dev/null
./NIARL_TPM_Module -mode 14 -owner_auth 1111111111111111111111111111111111111111 -cred_type EC
popd 1>/dev/null

#echo %LoadVersion% | find "x64" > nul
#IF %ERRORLEVEL%==0 (
#  set status64=\Wow6432Node\
#) ELSE (
# set status64=\
#)
#
#reg add "HKLM\SOFTWARE%status64%NTRU Cryptosystems\TSS" /v allowSOAPTCS /t REG_DWORD /d 0x00000001 /f
#net stop tcsd_win32.exe
#net start tcsd_win32.exe
#set status64=

export provclasspath=".:./lib/activation.jar:./lib/axis.jar:./lib/bcprov-jdk15-141.jar:./lib/commons-discovery-0.2.jar:./lib/commons-logging-1.0.4.jar:./lib/FastInfoset.jar:./lib/HisPrivacyCAWebServices-client.jar:./lib/HisPrivacyCAWebServices2-client.jar:./lib/HisWebServices-client.jar:./lib/http.jar:./lib/jaxb-api.jar:./lib/jaxb-impl.jar:./lib/jaxb-xjc.jar:./lib/jaxrpc.jar:./lib/jaxws-api.jar:./lib/jaxws-rt.jar:./lib/jaxws-tools.jar:./lib/jsr173_api.jar:./lib/jsr181-api.jar:./lib/jsr250-api.jar:./lib/mail.jar:./lib/mimepull.jar:./lib/PrivacyCA.jar:./lib/resolver.jar:./lib/saaj-api.jar:./lib/saaj-impl.jar:./lib/SALlib_hibernate3.jar:./lib/stax-ex.jar:./lib/streambuffer.jar:./lib/TSSCoreService.jar:./lib/woodstox.jar:./lib/wsdl4j-1.5.1.jar"

java -cp $provclasspath gov.niarl.his.privacyca.HisTpmProvisioner
ret=$?
if [ $ret == 0 ] ; then
  echo "Successfully initialized TPM" >> provisioning.log
else 
 echo "Failed to initialize the TPM, error $ret" >> provisioning.log
fi

java -cp $provclasspath gov.niarl.his.privacyca.HisIdentityProvisioner
ret=$?
if [ $ret == 0 ]; then
  echo "Successfully received AIC from Privacy CA" >> provisioning.log
else
 echo "Failed to receive AIC from Privacy CA, error $ret" >> provisioning.log
fi

java -cp $provclasspath gov.niarl.his.privacyca.HisRegisterIdentity
ret=$?
if [ $ret == 0 ]; then
  echo "Successfully registered identity with appraiser" >> provisioning.log
else
 echo "Failed to register identity with appraiser, error $ret" >> provisioning.log
fi
