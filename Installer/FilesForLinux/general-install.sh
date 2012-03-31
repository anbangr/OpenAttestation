#!/bin/bash

# default
dist="fedora"


# check distrition
if [ -f /etc/issue ]; then
if [ -n "`grep -i 'ubuntu' /etc/issue`" ]; then
    echo "Linux distribution is Ubuntu"
    dist="ubuntu"
fi

if [ -n "`grep -i 'suse' /etc/issue`" ]; then
    echo "Linux distribution is SUSE"
    dist="suse"
fi

if [ -n "`grep -i 'fedora' /etc/issue`" ]; then
    echo "Linux distribution is Fedora"
    dist="fedora"
fi
fi


# unpack OAT client files
rm -rf /OAT

tar vfxz NIARL_OAT_Standalone.tar.gz -C /
cp shells/$dist.sh /OAT/OAT.sh
chmod +x /OAT/OAT.sh
cp -f /OAT/OAT.sh /etc/init.d/OAT.sh

cp -f OAT.properties /OAT
cp -f TrustStore.jks /OAT
cp -f NIARL_TPM_Module /OAT

rm -f /OAT/uninstallOAT.sh
echo "/etc/init.d/OAT.sh stop" >> /OAT/uninstallOAT.sh
echo "rm -rf /OAT" >> /OAT/uninstallOAT.sh
echo "rm -f /etc/init.d/OAT.sh" >> /OAT/uninstallOAT.sh

# let it run at startup
if [ "$dist" = "fedora" ]; then
    ln -fs /etc/init.d/OAT.sh /etc/rc5.d/S99OAT
    echo "rm -f /etc/rc5.d/S99OAT" >> /OAT/uninstallOAT.sh
fi

if [ "$dist" = "suse" ]; then
    ln -fs /etc/init.d/OAT.sh /etc/init.d/rc5.d/S99OAT
    echo "rm -f /etc/init.d/rc5.d/S99OAT" >> /OAT/uninstallOAT.sh
fi

if [ "$dist" = "ubuntu" ]; then
    ln -fs /etc/init.d/OAT.sh /etc/rc5.d/S99OAT
    echo "rm -f /etc/rc5.d/S99OAT" >> /OAT/uninstallOAT.sh
fi

# OAT provisioning
bash provisioner.sh
