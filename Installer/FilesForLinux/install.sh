#call UninstallUSW.bat
rpm -ivh NIARL_OAT_Standalone-2.0-1.x86_64.rpm
cp -f OAT.properties /OAT
cp -f TrustStore.jks /OAT
cp -f NIARL_TPM_Module /OAT
sh provisioner.sh
#cd "C:\Program Files\NIARL\HIS\service\"
#call "replaceUSW.bat"
