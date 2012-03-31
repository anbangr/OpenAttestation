#!/bin/sh
# distribute Jar package

OAT_SOURCE=../Source
JAR_SOURCE=../JAR_SOURCE

ShowLogOK()
{
  echo -e "$1: --------------\033[32;49;5;1m [ OK ]\033[0m"
}
ShowLogFaild()
{
echo -e "$1:------------\033[31;49;5;1m [ Failed ]\033[0m"
exit 0
}

if test -e $JAR_SOURCE/activation.jar;then
  cp -nrf $JAR_SOURCE/activation.jar $OAT_SOURCE/HisClient/lib/activation.jar
  cp -nrf $JAR_SOURCE/activation.jar $OAT_SOURCE/HisPrivacyCAWebServices2/clientlib/activation.jar
  cp -nrf $JAR_SOURCE/activation.jar $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/activation.jar
  cp -nrf $JAR_SOURCE/activation.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/activation.jar
  cp -nrf $JAR_SOURCE/activation.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/activation.jar
  cp -nrf $JAR_SOURCE/activation.jar $OAT_SOURCE/HisAppraiser/lib/activation.jar
  cp -nrf $JAR_SOURCE/activation.jar $OAT_SOURCE/PrivacyCA/lib/activation.jar
  cp -nrf $JAR_SOURCE/activation.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/activation.jar
  cp -nrf $JAR_SOURCE/activation.jar $OAT_SOURCE/HisWebServices/clientlib/activation.jar
  cp -nrf $JAR_SOURCE/activation.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/activation.jar
  cp -nrf $JAR_SOURCE/activation.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/activation.jar
  if test -d $OAT_SOURCE/TSSCoreService;then
  	cp -nrf $JAR_SOURCE/activation.jar $OAT_SOURCE/TSSCoreService/lib/activation.jar
  fi
else
  ShowLogFaild "$JAR_SOURCE/activation.jar"
fi


if test -e $JAR_SOURCE/asm.jar;then
  cp -nrf $JAR_SOURCE/asm.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/asm.jar
  cp -nrf $JAR_SOURCE/asm.jar $OAT_SOURCE/HisAppraiser/lib/asm.jar
  cp -nrf $JAR_SOURCE/asm.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/asm.jar
  cp -nrf $JAR_SOURCE/asm.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/asm.jar
  cp -nrf $JAR_SOURCE/asm.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/asm.jar
  if test -d $OAT_SOURCE/TSSCoreService;then
  	cp -nrf $JAR_SOURCE/asm.jar $OAT_SOURCE/TSSCoreService/lib/asm.jar
  fi
else
  ShowLogFaild "$JAR_SOURCE/asm.jar"
fi


if test -e $JAR_SOURCE/asm-3.1.jar;then
  cp -nrf $JAR_SOURCE/asm-3.1.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/asm-3.1.jar
  cp -nrf $JAR_SOURCE/asm-3.1.jar $OAT_SOURCE/HisAppraiser/lib/asm-3.1.jar
  cp -nrf $JAR_SOURCE/asm-3.1.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/asm-3.1.jar
  cp -nrf $JAR_SOURCE/asm-3.1.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/asm-3.1.jar
else
  ShowLogFaild "$JAR_SOURCE/asm-3.1.jar"
fi


if test -e $JAR_SOURCE/bcprov-jdk15-141.jar;then
  cp -nrf $JAR_SOURCE/bcprov-jdk15-141.jar $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/bcprov-jdk15-141.jar
  cp -nrf $JAR_SOURCE/bcprov-jdk15-141.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/bcprov-jdk15-141.jar
  cp -nrf $JAR_SOURCE/bcprov-jdk15-141.jar $OAT_SOURCE/HisPrivacyCAWebServices2/clientlib/bcprov-jdk15-141.jar
  cp -nrf $JAR_SOURCE/bcprov-jdk15-141.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/bcprov-jdk15-141.jar
  cp -nrf $JAR_SOURCE/bcprov-jdk15-141.jar $OAT_SOURCE/HisAppraiser/lib/bcprov-jdk15-141.jar
  cp -nrf $JAR_SOURCE/bcprov-jdk15-141.jar $OAT_SOURCE/PrivacyCA/lib/bcprov-jdk15-141.jar
  cp -nrf $JAR_SOURCE/bcprov-jdk15-141.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/bcprov-jdk15-141.jar
  cp -nrf $JAR_SOURCE/bcprov-jdk15-141.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/bcprov-jdk15-141.jar
  cp -nrf $JAR_SOURCE/bcprov-jdk15-141.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/bcprov-jdk15-141.jar
else
  ShowLogFaild "$JAR_SOURCE/bcprov-jdk15-141.jar"
fi


if test -e $JAR_SOURCE/c3p0-0.9.0.jar;then
  cp -nrf $JAR_SOURCE/c3p0-0.9.0.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/c3p0-0.9.0.jar
  cp -nrf $JAR_SOURCE/c3p0-0.9.0.jar $OAT_SOURCE/HisAppraiser/lib/c3p0-0.9.0.jar
  cp -nrf $JAR_SOURCE/c3p0-0.9.0.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/c3p0-0.9.0.jar
  cp -nrf $JAR_SOURCE/c3p0-0.9.0.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/c3p0-0.9.0.jar
  cp -nrf $JAR_SOURCE/c3p0-0.9.0.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/c3p0-0.9.0.jar
else
  ShowLogFaild "$JAR_SOURCE/c3p0-0.9.0.jar"
fi


if test -e $JAR_SOURCE/cglib-2.1.3.jar;then
  cp -nrf $JAR_SOURCE/cglib-2.1.3.jar $OAT_SOURCE/HisAppraiser/lib/cglib-2.1.3.jar
  cp -nrf $JAR_SOURCE/cglib-2.1.3.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/cglib-2.1.3.jar
  if test -d $OAT_SOURCE/TSSCoreService;then
  	cp -nrf $JAR_SOURCE/cglib-2.1.3.jar $OAT_SOURCE/TSSCoreService/lib/cglib-2.1.3.jar
  fi

else
  ShowLogFaild "$JAR_SOURCE/cglib-2.1.3.jar"
fi


if test -e $JAR_SOURCE/cglib-2.2.jar;then
  cp -nrf $JAR_SOURCE/cglib-2.2.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/cglib-2.2.jar
  cp -nrf $JAR_SOURCE/cglib-2.2.jar $OAT_SOURCE/HisAppraiser/lib/cglib-2.2.jar
  cp -nrf $JAR_SOURCE/cglib-2.2.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/cglib-2.2.jar
  cp -nrf $JAR_SOURCE/cglib-2.2.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/cglib-2.2.jar
else
  ShowLogFaild "$JAR_SOURCE/cglib-2.2.jar"
fi


if test -e $JAR_SOURCE/commons-beanutils.jar;then
  cp -nrf $JAR_SOURCE/commons-beanutils.jar $OAT_SOURCE/HisClient/lib/commons-beanutils.jar
  cp -nrf $JAR_SOURCE/commons-beanutils.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/commons-beanutils.jar
  cp -nrf $JAR_SOURCE/commons-beanutils.jar $OAT_SOURCE/HisAppraiser/lib/commons-beanutils.jar
  cp -nrf $JAR_SOURCE/commons-beanutils.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/commons-beanutils.jar
  cp -nrf $JAR_SOURCE/commons-beanutils.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/commons-beanutils.jar
  cp -nrf $JAR_SOURCE/commons-beanutils.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/commons-beanutils.jar
else
  ShowLogFaild "$JAR_SOURCE/commons-beanutils.jar"
fi


if test -e $JAR_SOURCE/commons-cli-1.0.jar;then
  cp -nrf $JAR_SOURCE/commons-cli-1.0.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/commons-cli-1.0.jar
  cp -nrf $JAR_SOURCE/commons-cli-1.0.jar $OAT_SOURCE/HisAppraiser/lib/commons-cli-1.0.jar
  cp -nrf $JAR_SOURCE/commons-cli-1.0.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/commons-cli-1.0.jar
  cp -nrf $JAR_SOURCE/commons-cli-1.0.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/commons-cli-1.0.jar
  cp -nrf $JAR_SOURCE/commons-cli-1.0.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/commons-cli-1.0.jar
else
  ShowLogFaild "$JAR_SOURCE/commons-cli-1.0.jar"
fi


if test -e $JAR_SOURCE/commons-codec-1.3.jar;then
  cp -nrf $JAR_SOURCE/commons-codec-1.3.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/commons-codec-1.3.jar
  cp -nrf $JAR_SOURCE/commons-codec-1.3.jar $OAT_SOURCE/HisAppraiser/lib/commons-codec-1.3.jar
  cp -nrf $JAR_SOURCE/commons-codec-1.3.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/commons-codec-1.3.jar
  cp -nrf $JAR_SOURCE/commons-codec-1.3.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/commons-codec-1.3.jar
  cp -nrf $JAR_SOURCE/commons-codec-1.3.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/commons-codec-1.3.jar
else
  ShowLogFaild "$JAR_SOURCE/commons-codec-1.3.jar"
fi


if test -e $JAR_SOURCE/commons-codec-1.4.jar;then
  cp -nrf $JAR_SOURCE/commons-codec-1.4.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/commons-codec-1.4.jar
  cp -nrf $JAR_SOURCE/commons-codec-1.4.jar $OAT_SOURCE/HisAppraiser/lib/commons-codec-1.4.jar
  cp -nrf $JAR_SOURCE/commons-codec-1.4.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/commons-codec-1.4.jar
  cp -nrf $JAR_SOURCE/commons-codec-1.4.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/commons-codec-1.4.jar
else
  ShowLogFaild "$JAR_SOURCE/commons-codec-1.4.jar"
fi


if test -e $JAR_SOURCE/commons-collections-2.1.1.jar;then
  cp -nrf $JAR_SOURCE/commons-collections-2.1.1.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/commons-collections-2.1.1.jar
  cp -nrf $JAR_SOURCE/commons-collections-2.1.1.jar $OAT_SOURCE/HisAppraiser/lib/commons-collections-2.1.1.jar
  cp -nrf $JAR_SOURCE/commons-collections-2.1.1.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/commons-collections-2.1.1.jar
  cp -nrf $JAR_SOURCE/commons-collections-2.1.1.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/commons-collections-2.1.1.jar
  cp -nrf $JAR_SOURCE/commons-collections-2.1.1.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/commons-collections-2.1.1.jar
  if test -d $OAT_SOURCE/TSSCoreService;then
  	cp -nrf $JAR_SOURCE/commons-collections-2.1.1.jar $OAT_SOURCE/TSSCoreService/lib/commons-collections-2.1.1.jar
  fi
else
  ShowLogFaild "$JAR_SOURCE/commons-collections-2.1.1.jar"
fi


if test -e $JAR_SOURCE/commons-digester.jar;then
  cp -nrf $JAR_SOURCE/commons-digester.jar $OAT_SOURCE/HisClient/lib/commons-digester.jar
  cp -nrf $JAR_SOURCE/commons-digester.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/commons-digester.jar
  cp -nrf $JAR_SOURCE/commons-digester.jar $OAT_SOURCE/HisAppraiser/lib/commons-digester.jar
  cp -nrf $JAR_SOURCE/commons-digester.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/commons-digester.jar
  cp -nrf $JAR_SOURCE/commons-digester.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/commons-digester.jar
  cp -nrf $JAR_SOURCE/commons-digester.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/commons-digester.jar
else
  ShowLogFaild "$JAR_SOURCE/commons-digester.jar"
fi


if test -e $JAR_SOURCE/commons-httpclient-3.0.jar;then
  cp -nrf $JAR_SOURCE/commons-httpclient-3.0.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/commons-httpclient-3.0.jar
  cp -nrf $JAR_SOURCE/commons-httpclient-3.0.jar $OAT_SOURCE/HisAppraiser/lib/commons-httpclient-3.0.jar
  cp -nrf $JAR_SOURCE/commons-httpclient-3.0.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/commons-httpclient-3.0.jar
  cp -nrf $JAR_SOURCE/commons-httpclient-3.0.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/commons-httpclient-3.0.jar
  cp -nrf $JAR_SOURCE/commons-httpclient-3.0.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/commons-httpclient-3.0.jar
else
  ShowLogFaild "$JAR_SOURCE/commons-httpclient-3.0.jar"
fi


if test -e $JAR_SOURCE/commons-logging.jar;then
  cp -nrf $JAR_SOURCE/commons-logging.jar $OAT_SOURCE/HisClient/lib/commons-logging.jar
  cp -nrf $JAR_SOURCE/commons-logging.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/commons-logging.jar
  cp -nrf $JAR_SOURCE/commons-logging.jar $OAT_SOURCE/HisAppraiser/lib/commons-logging.jar
  cp -nrf $JAR_SOURCE/commons-logging.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/commons-logging.jar
  cp -nrf $JAR_SOURCE/commons-logging.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/commons-logging.jar
  cp -nrf $JAR_SOURCE/commons-logging.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/commons-logging.jar
  if test -d $OAT_SOURCE/TSSCoreService;then
  	cp -nrf $JAR_SOURCE/commons-logging.jar $OAT_SOURCE/TSSCoreService/lib/commons-logging.jar
  fi
else
  ShowLogFaild "$JAR_SOURCE/commons-logging.jar"
fi


if test -e $JAR_SOURCE/commons-logging-1.1.1.jar;then
  cp -nrf $JAR_SOURCE/commons-logging-1.1.1.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/commons-logging-1.1.1.jar
  cp -nrf $JAR_SOURCE/commons-logging-1.1.1.jar $OAT_SOURCE/HisAppraiser/lib/commons-logging-1.1.1.jar
  cp -nrf $JAR_SOURCE/commons-logging-1.1.1.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/commons-logging-1.1.1.jar
  cp -nrf $JAR_SOURCE/commons-logging-1.1.1.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/commons-logging-1.1.1.jar
else
  ShowLogFaild "$JAR_SOURCE/commons-logging-1.1.1.jar"
fi


if test -e $JAR_SOURCE/dom4j-1.6.1.jar;then
  cp -nrf $JAR_SOURCE/dom4j-1.6.1.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/dom4j-1.6.1.jar
  cp -nrf $JAR_SOURCE/dom4j-1.6.1.jar $OAT_SOURCE/HisAppraiser/lib/dom4j-1.6.1.jar
  cp -nrf $JAR_SOURCE/dom4j-1.6.1.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/dom4j-1.6.1.jar
  cp -nrf $JAR_SOURCE/dom4j-1.6.1.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/dom4j-1.6.1.jar
  cp -nrf $JAR_SOURCE/dom4j-1.6.1.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/dom4j-1.6.1.jar
  if test -d $OAT_SOURCE/TSSCoreService;then
  	cp -nrf $JAR_SOURCE/dom4j-1.6.1.jar $OAT_SOURCE/TSSCoreService/lib/dom4j-1.6.1.jar
  fi 
else
  ShowLogFaild "$JAR_SOURCE/dom4j-1.6.1.jar"
fi


if test -e $JAR_SOURCE/FastInfoset.jar;then
  cp -nrf $JAR_SOURCE/FastInfoset.jar $OAT_SOURCE/HisClient/lib/FastInfoset.jar
  cp -nrf $JAR_SOURCE/FastInfoset.jar $OAT_SOURCE/HisPrivacyCAWebServices2/clientlib/FastInfoset.jar
  cp -nrf $JAR_SOURCE/FastInfoset.jar $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/FastInfoset.jar
  cp -nrf $JAR_SOURCE/FastInfoset.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/FastInfoset.jar
  cp -nrf $JAR_SOURCE/FastInfoset.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/FastInfoset.jar
  cp -nrf $JAR_SOURCE/FastInfoset.jar $OAT_SOURCE/HisAppraiser/lib/FastInfoset.jar
  cp -nrf $JAR_SOURCE/FastInfoset.jar $OAT_SOURCE/PrivacyCA/lib/FastInfoset.jar
  cp -nrf $JAR_SOURCE/FastInfoset.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/FastInfoset.jar
  cp -nrf $JAR_SOURCE/FastInfoset.jar $OAT_SOURCE/HisWebServices/clientlib/FastInfoset.jar
  cp -nrf $JAR_SOURCE/FastInfoset.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/FastInfoset.jar
  cp -nrf $JAR_SOURCE/FastInfoset.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/FastInfoset.jar
else
  ShowLogFaild "$JAR_SOURCE/FastInfoset.jar"
fi


if test -e $JAR_SOURCE/hibernate3.jar;then
  cp -nrf $JAR_SOURCE/hibernate3.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/hibernate3.jar
  cp -nrf $JAR_SOURCE/hibernate3.jar $OAT_SOURCE/HisAppraiser/lib/hibernate3.jar
  cp -nrf $JAR_SOURCE/hibernate3.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/hibernate3.jar
  cp -nrf $JAR_SOURCE/hibernate3.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/hibernate3.jar
  cp -nrf $JAR_SOURCE/hibernate3.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/hibernate3.jar
else
  ShowLogFaild "$JAR_SOURCE/hibernate3.jar"
fi


if test -e $JAR_SOURCE/jaas.jar;then
  cp -nrf $JAR_SOURCE/jaas.jar $OAT_SOURCE/HisClient/lib/jaas.jar
  cp -nrf $JAR_SOURCE/jaas.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/jaas.jar
  cp -nrf $JAR_SOURCE/jaas.jar $OAT_SOURCE/HisAppraiser/lib/jaas.jar
  cp -nrf $JAR_SOURCE/jaas.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jaas.jar
  cp -nrf $JAR_SOURCE/jaas.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/jaas.jar
  cp -nrf $JAR_SOURCE/jaas.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/jaas.jar
else
  ShowLogFaild "$JAR_SOURCE/jaas.jar"
fi


if test -e $JAR_SOURCE/jackson-core-asl-1.8.3.jar;then
  cp -nrf $JAR_SOURCE/jackson-core-asl-1.8.3.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/jackson-core-asl-1.8.3.jar
  cp -nrf $JAR_SOURCE/jackson-core-asl-1.8.3.jar $OAT_SOURCE/HisAppraiser/lib/jackson-core-asl-1.8.3.jar
  cp -nrf $JAR_SOURCE/jackson-core-asl-1.8.3.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jackson-core-asl-1.8.3.jar
  cp -nrf $JAR_SOURCE/jackson-core-asl-1.8.3.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/jackson-core-asl-1.8.3.jar
else
  ShowLogFaild "$JAR_SOURCE/jackson-core-asl-1.8.3.jar"
fi


if test -e $JAR_SOURCE/jackson-jaxrs-1.8.3.jar;then
  cp -nrf $JAR_SOURCE/jackson-jaxrs-1.8.3.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/jackson-jaxrs-1.8.3.jar
  cp -nrf $JAR_SOURCE/jackson-jaxrs-1.8.3.jar $OAT_SOURCE/HisAppraiser/lib/jackson-jaxrs-1.8.3.jar
  cp -nrf $JAR_SOURCE/jackson-jaxrs-1.8.3.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jackson-jaxrs-1.8.3.jar
  cp -nrf $JAR_SOURCE/jackson-jaxrs-1.8.3.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/jackson-jaxrs-1.8.3.jar
else
  ShowLogFaild "$JAR_SOURCE/jackson-jaxrs-1.8.3.jar"
fi


if test -e $JAR_SOURCE/jackson-mapper-asl-1.8.3.jar;then
  cp -nrf $JAR_SOURCE/jackson-mapper-asl-1.8.3.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/jackson-mapper-asl-1.8.3.jar
  cp -nrf $JAR_SOURCE/jackson-mapper-asl-1.8.3.jar $OAT_SOURCE/HisAppraiser/lib/jackson-mapper-asl-1.8.3.jar
  cp -nrf $JAR_SOURCE/jackson-mapper-asl-1.8.3.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jackson-mapper-asl-1.8.3.jar
  cp -nrf $JAR_SOURCE/jackson-mapper-asl-1.8.3.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/jackson-mapper-asl-1.8.3.jar
else
  ShowLogFaild "$JAR_SOURCE/jackson-mapper-asl-1.8.3.jar"
fi


if test -e $JAR_SOURCE/jackson-xc-1.8.3.jar;then
  cp -nrf $JAR_SOURCE/jackson-xc-1.8.3.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/jackson-xc-1.8.3.jar
  cp -nrf $JAR_SOURCE/jackson-xc-1.8.3.jar $OAT_SOURCE/HisAppraiser/lib/jackson-xc-1.8.3.jar
  cp -nrf $JAR_SOURCE/jackson-xc-1.8.3.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jackson-xc-1.8.3.jar
  cp -nrf $JAR_SOURCE/jackson-xc-1.8.3.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/jackson-xc-1.8.3.jar
else
  ShowLogFaild "$JAR_SOURCE/jackson-xc-1.8.3.jar"
fi


if test -e $JAR_SOURCE/jaxb-api.jar;then
  cp -nrf $JAR_SOURCE/jaxb-api.jar $OAT_SOURCE/HisClient/lib/jaxb-api.jar
  cp -nrf $JAR_SOURCE/jaxb-api.jar $OAT_SOURCE/HisPrivacyCAWebServices2/clientlib/jaxb-api.jar
  cp -nrf $JAR_SOURCE/jaxb-api.jar $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/jaxb-api.jar
  cp -nrf $JAR_SOURCE/jaxb-api.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/jaxb-api.jar
  cp -nrf $JAR_SOURCE/jaxb-api.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/jaxb-api.jar
  cp -nrf $JAR_SOURCE/jaxb-api.jar $OAT_SOURCE/HisAppraiser/lib/jaxb-api.jar
  cp -nrf $JAR_SOURCE/jaxb-api.jar $OAT_SOURCE/PrivacyCA/lib/jaxb-api.jar
  cp -nrf $JAR_SOURCE/jaxb-api.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jaxb-api.jar
  cp -nrf $JAR_SOURCE/jaxb-api.jar $OAT_SOURCE/HisWebServices/clientlib/jaxb-api.jar
  cp -nrf $JAR_SOURCE/jaxb-api.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/jaxb-api.jar
  cp -nrf $JAR_SOURCE/jaxb-api.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/jaxb-api.jar
else
  ShowLogFaild "$JAR_SOURCE/jaxb-api.jar"
fi


if test -e $JAR_SOURCE/jaxb-impl.jar;then
  cp -nrf $JAR_SOURCE/jaxb-impl.jar $OAT_SOURCE/HisClient/lib/jaxb-impl.jar
  cp -nrf $JAR_SOURCE/jaxb-impl.jar $OAT_SOURCE/HisPrivacyCAWebServices2/clientlib/jaxb-impl.jar
  cp -nrf $JAR_SOURCE/jaxb-impl.jar $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/jaxb-impl.jar
  cp -nrf $JAR_SOURCE/jaxb-impl.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/jaxb-impl.jar
  cp -nrf $JAR_SOURCE/jaxb-impl.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/jaxb-impl.jar
  cp -nrf $JAR_SOURCE/jaxb-impl.jar $OAT_SOURCE/HisAppraiser/lib/jaxb-impl.jar
  cp -nrf $JAR_SOURCE/jaxb-impl.jar $OAT_SOURCE/PrivacyCA/lib/jaxb-impl.jar
  cp -nrf $JAR_SOURCE/jaxb-impl.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jaxb-impl.jar
  cp -nrf $JAR_SOURCE/jaxb-impl.jar $OAT_SOURCE/HisWebServices/clientlib/jaxb-impl.jar
  cp -nrf $JAR_SOURCE/jaxb-impl.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/jaxb-impl.jar
  cp -nrf $JAR_SOURCE/jaxb-impl.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/jaxb-impl.jar
else
  ShowLogFaild "$JAR_SOURCE/jaxb-impl.jar"
fi


if test -e $JAR_SOURCE/jaxb-xjc.jar;then
  cp -nrf $JAR_SOURCE/jaxb-xjc.jar $OAT_SOURCE/HisClient/lib/jaxb-xjc.jar
  cp -nrf $JAR_SOURCE/jaxb-xjc.jar $OAT_SOURCE/HisPrivacyCAWebServices2/clientlib/jaxb-xjc.jar
  cp -nrf $JAR_SOURCE/jaxb-xjc.jar $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/jaxb-xjc.jar
  cp -nrf $JAR_SOURCE/jaxb-xjc.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/jaxb-xjc.jar
  cp -nrf $JAR_SOURCE/jaxb-xjc.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/jaxb-xjc.jar
  cp -nrf $JAR_SOURCE/jaxb-xjc.jar $OAT_SOURCE/HisAppraiser/lib/jaxb-xjc.jar
  cp -nrf $JAR_SOURCE/jaxb-xjc.jar $OAT_SOURCE/PrivacyCA/lib/jaxb-xjc.jar
  cp -nrf $JAR_SOURCE/jaxb-xjc.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jaxb-xjc.jar
  cp -nrf $JAR_SOURCE/jaxb-xjc.jar $OAT_SOURCE/HisWebServices/clientlib/jaxb-xjc.jar
  cp -nrf $JAR_SOURCE/jaxb-xjc.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/jaxb-xjc.jar
  cp -nrf $JAR_SOURCE/jaxb-xjc.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/jaxb-xjc.jar
else
  ShowLogFaild "$JAR_SOURCE/jaxb-xjc.jar"
fi


if test -e $JAR_SOURCE/jax-qname.jar;then
  cp -nrf $JAR_SOURCE/jax-qname.jar $OAT_SOURCE/HisClient/lib/jax-qname.jar
  cp -nrf $JAR_SOURCE/jax-qname.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/jax-qname.jar
  cp -nrf $JAR_SOURCE/jax-qname.jar $OAT_SOURCE/HisAppraiser/lib/jax-qname.jar
  cp -nrf $JAR_SOURCE/jax-qname.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jax-qname.jar
  cp -nrf $JAR_SOURCE/jax-qname.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/jax-qname.jar
else
  ShowLogFaild "$JAR_SOURCE/jax-qname.jar"
fi


if test -e $JAR_SOURCE/jaxws-api.jar;then
  cp -nrf $JAR_SOURCE/jaxws-api.jar $OAT_SOURCE/HisClient/lib/jaxws-api.jar
  cp -nrf $JAR_SOURCE/jaxws-api.jar $OAT_SOURCE/HisPrivacyCAWebServices2/clientlib/jaxws-api.jar
  cp -nrf $JAR_SOURCE/jaxws-api.jar $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/jaxws-api.jar
  cp -nrf $JAR_SOURCE/jaxws-api.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/jaxws-api.jar
  cp -nrf $JAR_SOURCE/jaxws-api.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/jaxws-api.jar
  cp -nrf $JAR_SOURCE/jaxws-api.jar $OAT_SOURCE/HisAppraiser/lib/jaxws-api.jar
  cp -nrf $JAR_SOURCE/jaxws-api.jar $OAT_SOURCE/PrivacyCA/lib/jaxws-api.jar
  cp -nrf $JAR_SOURCE/jaxws-api.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jaxws-api.jar
  cp -nrf $JAR_SOURCE/jaxws-api.jar $OAT_SOURCE/HisWebServices/clientlib/jaxws-api.jar
  cp -nrf $JAR_SOURCE/jaxws-api.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/jaxws-api.jar
  cp -nrf $JAR_SOURCE/jaxws-api.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/jaxws-api.jar
else
  ShowLogFaild "$JAR_SOURCE/jaxws-api.jar"
fi


if test -e $JAR_SOURCE/jaxws-rt.jar;then
  cp -nrf $JAR_SOURCE/jaxws-rt.jar $OAT_SOURCE/HisClient/lib/jaxws-rt.jar
  cp -nrf $JAR_SOURCE/jaxws-rt.jar $OAT_SOURCE/HisPrivacyCAWebServices2/clientlib/jaxws-rt.jar
  cp -nrf $JAR_SOURCE/jaxws-rt.jar $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/jaxws-rt.jar
  cp -nrf $JAR_SOURCE/jaxws-rt.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/jaxws-rt.jar
  cp -nrf $JAR_SOURCE/jaxws-rt.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/jaxws-rt.jar
  cp -nrf $JAR_SOURCE/jaxws-rt.jar $OAT_SOURCE/HisAppraiser/lib/jaxws-rt.jar
  cp -nrf $JAR_SOURCE/jaxws-rt.jar $OAT_SOURCE/PrivacyCA/lib/jaxws-rt.jar
  cp -nrf $JAR_SOURCE/jaxws-rt.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jaxws-rt.jar
  cp -nrf $JAR_SOURCE/jaxws-rt.jar $OAT_SOURCE/HisWebServices/clientlib/jaxws-rt.jar
  cp -nrf $JAR_SOURCE/jaxws-rt.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/jaxws-rt.jar
  cp -nrf $JAR_SOURCE/jaxws-rt.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/jaxws-rt.jar
else
  ShowLogFaild "$JAR_SOURCE/jaxws-rt.jar"
fi


if test -e $JAR_SOURCE/jaxws-tools.jar;then
  cp -nrf $JAR_SOURCE/jaxws-tools.jar $OAT_SOURCE/HisClient/lib/jaxws-tools.jar
  cp -nrf $JAR_SOURCE/jaxws-tools.jar $OAT_SOURCE/HisPrivacyCAWebServices2/clientlib/jaxws-tools.jar
  cp -nrf $JAR_SOURCE/jaxws-tools.jar $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/jaxws-tools.jar
  cp -nrf $JAR_SOURCE/jaxws-tools.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/jaxws-tools.jar
  cp -nrf $JAR_SOURCE/jaxws-tools.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/jaxws-tools.jar
  cp -nrf $JAR_SOURCE/jaxws-tools.jar $OAT_SOURCE/HisAppraiser/lib/jaxws-tools.jar
  cp -nrf $JAR_SOURCE/jaxws-tools.jar $OAT_SOURCE/PrivacyCA/lib/jaxws-tools.jar
  cp -nrf $JAR_SOURCE/jaxws-tools.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jaxws-tools.jar
  cp -nrf $JAR_SOURCE/jaxws-tools.jar $OAT_SOURCE/HisWebServices/clientlib/jaxws-tools.jar
  cp -nrf $JAR_SOURCE/jaxws-tools.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/jaxws-tools.jar
  cp -nrf $JAR_SOURCE/jaxws-tools.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/jaxws-tools.jar
else
  ShowLogFaild "$JAR_SOURCE/jaxws-tools.jar"
fi


if test -e $JAR_SOURCE/jdbc2_0-stdext.jar;then
  cp -nrf $JAR_SOURCE/jdbc2_0-stdext.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/jdbc2_0-stdext.jar
  cp -nrf $JAR_SOURCE/jdbc2_0-stdext.jar $OAT_SOURCE/HisAppraiser/lib/jdbc2_0-stdext.jar
  cp -nrf $JAR_SOURCE/jdbc2_0-stdext.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jdbc2_0-stdext.jar
  cp -nrf $JAR_SOURCE/jdbc2_0-stdext.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/jdbc2_0-stdext.jar
  cp -nrf $JAR_SOURCE/jdbc2_0-stdext.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/jdbc2_0-stdext.jar
else
  ShowLogFaild "$JAR_SOURCE/jdbc2_0-stdext.jar"
fi


if test -e $JAR_SOURCE/jersey-bundle-1.9.1.jar;then
  cp -nrf $JAR_SOURCE/jersey-bundle-1.9.1.jar $OAT_SOURCE/HisAppraiser/lib/jersey-bundle-1.9.1.jar
  cp -nrf $JAR_SOURCE/jersey-bundle-1.9.1.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jersey-bundle-1.9.1.jar
  cp -nrf $JAR_SOURCE/jersey-bundle-1.9.1.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/jersey-bundle-1.9.1.jar
else
  ShowLogFaild "$JAR_SOURCE/jersey-bundle-1.9.1.jar"
fi


if test -e $JAR_SOURCE/jersey-client-1.9.1.jar;then
  cp -nrf $JAR_SOURCE/jersey-client-1.9.1.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/jersey-client-1.9.1.jar
  cp -nrf $JAR_SOURCE/jersey-client-1.9.1.jar $OAT_SOURCE/HisAppraiser/lib/jersey-client-1.9.1.jar
  cp -nrf $JAR_SOURCE/jersey-client-1.9.1.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jersey-client-1.9.1.jar
  cp -nrf $JAR_SOURCE/jersey-client-1.9.1.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/jersey-client-1.9.1.jar
  cp -nrf $JAR_SOURCE/jersey-client-1.9.1.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/jersey-client-1.9.1.jar
else
  ShowLogFaild "$JAR_SOURCE/jersey-client-1.9.1.jar"
fi


if test -e $JAR_SOURCE/jersey-core-1.9.1.jar;then
  cp -nrf $JAR_SOURCE/jersey-core-1.9.1.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/jersey-core-1.9.1.jar
  cp -nrf $JAR_SOURCE/jersey-core-1.9.1.jar $OAT_SOURCE/HisAppraiser/lib/jersey-core-1.9.1.jar
  cp -nrf $JAR_SOURCE/jersey-core-1.9.1.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jersey-core-1.9.1.jar
  cp -nrf $JAR_SOURCE/jersey-core-1.9.1.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/jersey-core-1.9.1.jar
  cp -nrf $JAR_SOURCE/jersey-core-1.9.1.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/jersey-core-1.9.1.jar
else
  ShowLogFaild "$JAR_SOURCE/jersey-core-1.9.1.jar"
fi


if test -e $JAR_SOURCE/jersey-json-1.9.1.jar;then
  cp -nrf $JAR_SOURCE/jersey-json-1.9.1.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/jersey-json-1.9.1.jar
  cp -nrf $JAR_SOURCE/jersey-json-1.9.1.jar $OAT_SOURCE/HisAppraiser/lib/jersey-json-1.9.1.jar
  cp -nrf $JAR_SOURCE/jersey-json-1.9.1.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jersey-json-1.9.1.jar
  cp -nrf $JAR_SOURCE/jersey-json-1.9.1.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/jersey-json-1.9.1.jar
  cp -nrf $JAR_SOURCE/jersey-json-1.9.1.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/jersey-json-1.9.1.jar
else
  ShowLogFaild "$JAR_SOURCE/jersey-json-1.9.1.jar"
fi


if test -e $JAR_SOURCE/jersey-server-1.9.1.jar;then
  cp -nrf $JAR_SOURCE/jersey-server-1.9.1.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/jersey-server-1.9.1.jar
  cp -nrf $JAR_SOURCE/jersey-server-1.9.1.jar $OAT_SOURCE/HisAppraiser/lib/jersey-server-1.9.1.jar
  cp -nrf $JAR_SOURCE/jersey-server-1.9.1.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jersey-server-1.9.1.jar
  cp -nrf $JAR_SOURCE/jersey-server-1.9.1.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/jersey-server-1.9.1.jar
else
  ShowLogFaild "$JAR_SOURCE/jersey-server-1.9.1.jar"
fi


if test -e $JAR_SOURCE/jettison-1.1.jar;then
  cp -nrf $JAR_SOURCE/jettison-1.1.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/jettison-1.1.jar
  cp -nrf $JAR_SOURCE/jettison-1.1.jar $OAT_SOURCE/HisAppraiser/lib/jettison-1.1.jar
  cp -nrf $JAR_SOURCE/jettison-1.1.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jettison-1.1.jar
  cp -nrf $JAR_SOURCE/jettison-1.1.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/jettison-1.1.jar
else
  ShowLogFaild "$JAR_SOURCE/jettison-1.1.jar"
fi


if test -e $JAR_SOURCE/jsr173_api.jar;then
  cp -nrf $JAR_SOURCE/jsr173_api.jar $OAT_SOURCE/HisClient/lib/jsr173_api.jar
  cp -nrf $JAR_SOURCE/jsr173_api.jar $OAT_SOURCE/HisPrivacyCAWebServices2/clientlib/jsr173_api.jar
  cp -nrf $JAR_SOURCE/jsr173_api.jar $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/jsr173_api.jar
  cp -nrf $JAR_SOURCE/jsr173_api.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/jsr173_api.jar
  cp -nrf $JAR_SOURCE/jsr173_api.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/jsr173_api.jar
  cp -nrf $JAR_SOURCE/jsr173_api.jar $OAT_SOURCE/HisAppraiser/lib/jsr173_api.jar
  cp -nrf $JAR_SOURCE/jsr173_api.jar $OAT_SOURCE/PrivacyCA/lib/jsr173_api.jar
  cp -nrf $JAR_SOURCE/jsr173_api.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jsr173_api.jar
  cp -nrf $JAR_SOURCE/jsr173_api.jar $OAT_SOURCE/HisWebServices/clientlib/jsr173_api.jar
  cp -nrf $JAR_SOURCE/jsr173_api.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/jsr173_api.jar
  cp -nrf $JAR_SOURCE/jsr173_api.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/jsr173_api.jar
else
  ShowLogFaild "$JAR_SOURCE/jsr173_api.jar"
fi


if test -e $JAR_SOURCE/jsr181-api.jar;then
  cp -nrf $JAR_SOURCE/jsr181-api.jar $OAT_SOURCE/HisClient/lib/jsr181-api.jar
  cp -nrf $JAR_SOURCE/jsr181-api.jar $OAT_SOURCE/HisPrivacyCAWebServices2/clientlib/jsr181-api.jar
  cp -nrf $JAR_SOURCE/jsr181-api.jar $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/jsr181-api.jar
  cp -nrf $JAR_SOURCE/jsr181-api.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/jsr181-api.jar
  cp -nrf $JAR_SOURCE/jsr181-api.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/jsr181-api.jar
  cp -nrf $JAR_SOURCE/jsr181-api.jar $OAT_SOURCE/HisAppraiser/lib/jsr181-api.jar
  cp -nrf $JAR_SOURCE/jsr181-api.jar $OAT_SOURCE/PrivacyCA/lib/jsr181-api.jar
  cp -nrf $JAR_SOURCE/jsr181-api.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jsr181-api.jar
  cp -nrf $JAR_SOURCE/jsr181-api.jar $OAT_SOURCE/HisWebServices/clientlib/jsr181-api.jar
  cp -nrf $JAR_SOURCE/jsr181-api.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/jsr181-api.jar
  cp -nrf $JAR_SOURCE/jsr181-api.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/jsr181-api.jar
else
  ShowLogFaild "$JAR_SOURCE/jsr181-api.jar"
fi


if test -e $JAR_SOURCE/jsr250-api.jar;then
  cp -nrf $JAR_SOURCE/jsr250-api.jar $OAT_SOURCE/HisClient/lib/jsr250-api.jar
  cp -nrf $JAR_SOURCE/jsr250-api.jar $OAT_SOURCE/HisPrivacyCAWebServices2/clientlib/jsr250-api.jar
  cp -nrf $JAR_SOURCE/jsr250-api.jar $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/jsr250-api.jar
  cp -nrf $JAR_SOURCE/jsr250-api.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/jsr250-api.jar
  cp -nrf $JAR_SOURCE/jsr250-api.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/jsr250-api.jar
  cp -nrf $JAR_SOURCE/jsr250-api.jar $OAT_SOURCE/HisAppraiser/lib/jsr250-api.jar
  cp -nrf $JAR_SOURCE/jsr250-api.jar $OAT_SOURCE/PrivacyCA/lib/jsr250-api.jar
  cp -nrf $JAR_SOURCE/jsr250-api.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jsr250-api.jar
  cp -nrf $JAR_SOURCE/jsr250-api.jar $OAT_SOURCE/HisWebServices/clientlib/jsr250-api.jar
  cp -nrf $JAR_SOURCE/jsr250-api.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/jsr250-api.jar
  cp -nrf $JAR_SOURCE/jsr250-api.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/jsr250-api.jar
else
  ShowLogFaild "$JAR_SOURCE/jsr250-api.jar"
fi


if test -e $JAR_SOURCE/jsr311-api-1.1.1.jar;then
  cp -nrf $JAR_SOURCE/jsr311-api-1.1.1.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/jsr311-api-1.1.1.jar
  cp -nrf $JAR_SOURCE/jsr311-api-1.1.1.jar $OAT_SOURCE/HisAppraiser/lib/jsr311-api-1.1.1.jar
  cp -nrf $JAR_SOURCE/jsr311-api-1.1.1.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jsr311-api-1.1.1.jar
  cp -nrf $JAR_SOURCE/jsr311-api-1.1.1.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/jsr311-api-1.1.1.jar
  cp -nrf $JAR_SOURCE/jsr311-api-1.1.1.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/jsr311-api-1.1.1.jar
else
  ShowLogFaild "$JAR_SOURCE/jsr311-api-1.1.1.jar"
fi


if test -e $JAR_SOURCE/jta.jar;then
  cp -nrf $JAR_SOURCE/jta.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/jta.jar
  cp -nrf $JAR_SOURCE/jta.jar $OAT_SOURCE/HisAppraiser/lib/jta.jar
  cp -nrf $JAR_SOURCE/jta.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jta.jar
  cp -nrf $JAR_SOURCE/jta.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/jta.jar
  cp -nrf $JAR_SOURCE/jta.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/jta.jar
else
  ShowLogFaild "$JAR_SOURCE/jta.jar"
fi


if test -e $JAR_SOURCE/jtds-1.2.jar;then
  cp -nrf $JAR_SOURCE/jtds-1.2.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/jtds-1.2.jar
  cp -nrf $JAR_SOURCE/jtds-1.2.jar $OAT_SOURCE/HisAppraiser/lib/jtds-1.2.jar
  cp -nrf $JAR_SOURCE/jtds-1.2.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jtds-1.2.jar
  cp -nrf $JAR_SOURCE/jtds-1.2.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/jtds-1.2.jar
  cp -nrf $JAR_SOURCE/jtds-1.2.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/jtds-1.2.jar
else
  ShowLogFaild "$JAR_SOURCE/jtds-1.2.jar"
fi


if test -e $JAR_SOURCE/log4j-1.2.8.jar;then
  cp -nrf $JAR_SOURCE/log4j-1.2.8.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/log4j-1.2.8.jar
  cp -nrf $JAR_SOURCE/log4j-1.2.8.jar $OAT_SOURCE/HisAppraiser/lib/log4j-1.2.8.jar
  cp -nrf $JAR_SOURCE/log4j-1.2.8.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/log4j-1.2.8.jar
  cp -nrf $JAR_SOURCE/log4j-1.2.8.jar $OAT_SOURCE/HisWebServices/clientlib/log4j-1.2.8.jar
  cp -nrf $JAR_SOURCE/log4j-1.2.8.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/log4j-1.2.8.jar
  cp -nrf $JAR_SOURCE/log4j-1.2.8.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/log4j-1.2.8.jar
else
  ShowLogFaild "$JAR_SOURCE/log4j-1.2.8.jar"
fi


if test -e $JAR_SOURCE/mail.jar;then
  cp -nrf $JAR_SOURCE/mail.jar $OAT_SOURCE/HisClient/lib/mail.jar
  cp -nrf $JAR_SOURCE/mail.jar $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/mail.jar
  cp -nrf $JAR_SOURCE/mail.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/mail.jar
  cp -nrf $JAR_SOURCE/mail.jar $OAT_SOURCE/HisAppraiser/lib/mail.jar
  cp -nrf $JAR_SOURCE/mail.jar $OAT_SOURCE/PrivacyCA/lib/mail.jar
  cp -nrf $JAR_SOURCE/mail.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/mail.jar
  cp -nrf $JAR_SOURCE/mail.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/mail.jar
  cp -nrf $JAR_SOURCE/mail.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/mail.jar
  if test -d $OAT_SOURCE/TSSCoreService;then
  	cp -nrf $JAR_SOURCE/mail.jar $OAT_SOURCE/TSSCoreService/lib/mail.jar
  fi
else
  ShowLogFaild "$JAR_SOURCE/mail.jar"
fi


if test -e $JAR_SOURCE/mimepull.jar;then
  cp -nrf $JAR_SOURCE/mimepull.jar $OAT_SOURCE/HisClient/lib/mimepull.jar
  cp -nrf $JAR_SOURCE/mimepull.jar $OAT_SOURCE/HisPrivacyCAWebServices2/clientlib/mimepull.jar
  cp -nrf $JAR_SOURCE/mimepull.jar $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/mimepull.jar
  cp -nrf $JAR_SOURCE/mimepull.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/mimepull.jar
  cp -nrf $JAR_SOURCE/mimepull.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/mimepull.jar
  cp -nrf $JAR_SOURCE/mimepull.jar $OAT_SOURCE/HisAppraiser/lib/mimepull.jar
  cp -nrf $JAR_SOURCE/mimepull.jar $OAT_SOURCE/PrivacyCA/lib/mimepull.jar
  cp -nrf $JAR_SOURCE/mimepull.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/mimepull.jar
  cp -nrf $JAR_SOURCE/mimepull.jar $OAT_SOURCE/HisWebServices/clientlib/mimepull.jar
  cp -nrf $JAR_SOURCE/mimepull.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/mimepull.jar
  cp -nrf $JAR_SOURCE/mimepull.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/mimepull.jar
else
  ShowLogFaild "$JAR_SOURCE/mimepull.jar"
fi


if test -e $JAR_SOURCE/mysql-connector-java-5.0.7-bin.jar;then
  cp -nrf $JAR_SOURCE/mysql-connector-java-5.0.7-bin.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/mysql-connector-java-5.0.7-bin.jar
  cp -nrf $JAR_SOURCE/mysql-connector-java-5.0.7-bin.jar $OAT_SOURCE/HisAppraiser/lib/mysql-connector-java-5.0.7-bin.jar
  cp -nrf $JAR_SOURCE/mysql-connector-java-5.0.7-bin.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/mysql-connector-java-5.0.7-bin.jar
  cp -nrf $JAR_SOURCE/mysql-connector-java-5.0.7-bin.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/mysql-connector-java-5.0.7-bin.jar
  cp -nrf $JAR_SOURCE/mysql-connector-java-5.0.7-bin.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/mysql-connector-java-5.0.7-bin.jar
else
  ShowLogFaild "$JAR_SOURCE/mysql-connector-java-5.0.7-bin.jar"
fi


if test -e $JAR_SOURCE/org.springframework.context.support-3.0.3.RELEASE.jar;then
  cp -nrf $JAR_SOURCE/org.springframework.context.support-3.0.3.RELEASE.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/org.springframework.context.support-3.0.3.RELEASE.jar
  cp -nrf $JAR_SOURCE/org.springframework.context.support-3.0.3.RELEASE.jar $OAT_SOURCE/HisAppraiser/lib/org.springframework.context.support-3.0.3.RELEASE.jar
  cp -nrf $JAR_SOURCE/org.springframework.context.support-3.0.3.RELEASE.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/org.springframework.context.support-3.0.3.RELEASE.jar
  cp -nrf $JAR_SOURCE/org.springframework.context.support-3.0.3.RELEASE.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/org.springframework.context.support-3.0.3.RELEASE.jar
else
  ShowLogFaild "$JAR_SOURCE/org.springframework.context.support-3.0.3.RELEASE.jar"
fi


if test -e $JAR_SOURCE/relaxngDatatype.jar;then
  cp -nrf $JAR_SOURCE/relaxngDatatype.jar $OAT_SOURCE/HisClient/lib/relaxngDatatype.jar
  cp -nrf $JAR_SOURCE/relaxngDatatype.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/relaxngDatatype.jar
  cp -nrf $JAR_SOURCE/relaxngDatatype.jar $OAT_SOURCE/HisAppraiser/lib/relaxngDatatype.jar
  cp -nrf $JAR_SOURCE/relaxngDatatype.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/relaxngDatatype.jar
  cp -nrf $JAR_SOURCE/relaxngDatatype.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/relaxngDatatype.jar
  cp -nrf $JAR_SOURCE/relaxngDatatype.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/relaxngDatatype.jar
else
  ShowLogFaild "$JAR_SOURCE/relaxngDatatype.jar"
fi


if test -e $JAR_SOURCE/resolver.jar;then
  cp -nrf $JAR_SOURCE/resolver.jar $OAT_SOURCE/HisClient/lib/resolver.jar
  cp -nrf $JAR_SOURCE/resolver.jar $OAT_SOURCE/HisPrivacyCAWebServices2/clientlib/resolver.jar
  cp -nrf $JAR_SOURCE/resolver.jar $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/resolver.jar
  cp -nrf $JAR_SOURCE/resolver.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/resolver.jar
  cp -nrf $JAR_SOURCE/resolver.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/resolver.jar
  cp -nrf $JAR_SOURCE/resolver.jar $OAT_SOURCE/HisAppraiser/lib/resolver.jar
  cp -nrf $JAR_SOURCE/resolver.jar $OAT_SOURCE/PrivacyCA/lib/resolver.jar
  cp -nrf $JAR_SOURCE/resolver.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/resolver.jar
  cp -nrf $JAR_SOURCE/resolver.jar $OAT_SOURCE/HisWebServices/clientlib/resolver.jar
  cp -nrf $JAR_SOURCE/resolver.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/resolver.jar
  cp -nrf $JAR_SOURCE/resolver.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/resolver.jar
else
  ShowLogFaild "$JAR_SOURCE/resolver.jar"
fi


if test -e $JAR_SOURCE/saaj-api.jar;then
  cp -nrf $JAR_SOURCE/saaj-api.jar $OAT_SOURCE/HisClient/lib/saaj-api.jar
  cp -nrf $JAR_SOURCE/saaj-api.jar $OAT_SOURCE/HisPrivacyCAWebServices2/clientlib/saaj-api.jar
  cp -nrf $JAR_SOURCE/saaj-api.jar $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/saaj-api.jar
  cp -nrf $JAR_SOURCE/saaj-api.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/saaj-api.jar
  cp -nrf $JAR_SOURCE/saaj-api.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/saaj-api.jar
  cp -nrf $JAR_SOURCE/saaj-api.jar $OAT_SOURCE/HisAppraiser/lib/saaj-api.jar
  cp -nrf $JAR_SOURCE/saaj-api.jar $OAT_SOURCE/PrivacyCA/lib/saaj-api.jar
  cp -nrf $JAR_SOURCE/saaj-api.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/saaj-api.jar
  cp -nrf $JAR_SOURCE/saaj-api.jar $OAT_SOURCE/HisWebServices/clientlib/saaj-api.jar
  cp -nrf $JAR_SOURCE/saaj-api.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/saaj-api.jar
  cp -nrf $JAR_SOURCE/saaj-api.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/saaj-api.jar
else
  ShowLogFaild "$JAR_SOURCE/saaj-api.jar"
fi


if test -e $JAR_SOURCE/saaj-impl.jar;then
  cp -nrf $JAR_SOURCE/saaj-impl.jar $OAT_SOURCE/HisClient/lib/saaj-impl.jar
  cp -nrf $JAR_SOURCE/saaj-impl.jar $OAT_SOURCE/HisPrivacyCAWebServices2/clientlib/saaj-impl.jar
  cp -nrf $JAR_SOURCE/saaj-impl.jar $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/saaj-impl.jar
  cp -nrf $JAR_SOURCE/saaj-impl.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/saaj-impl.jar
  cp -nrf $JAR_SOURCE/saaj-impl.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/saaj-impl.jar
  cp -nrf $JAR_SOURCE/saaj-impl.jar $OAT_SOURCE/HisAppraiser/lib/saaj-impl.jar
  cp -nrf $JAR_SOURCE/saaj-impl.jar $OAT_SOURCE/PrivacyCA/lib/saaj-impl.jar
  cp -nrf $JAR_SOURCE/saaj-impl.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/saaj-impl.jar
  cp -nrf $JAR_SOURCE/saaj-impl.jar $OAT_SOURCE/HisWebServices/clientlib/saaj-impl.jar
  cp -nrf $JAR_SOURCE/saaj-impl.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/saaj-impl.jar
  cp -nrf $JAR_SOURCE/saaj-impl.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/saaj-impl.jar
else
  ShowLogFaild "$JAR_SOURCE/saaj-impl.jar"
fi


if test -e $JAR_SOURCE/servlet.jar;then
  cp -nrf $JAR_SOURCE/servlet.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/servlet.jar
  cp -nrf $JAR_SOURCE/servlet.jar $OAT_SOURCE/HisAppraiser/lib/servlet.jar
  cp -nrf $JAR_SOURCE/servlet.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/servlet.jar
  cp -nrf $JAR_SOURCE/servlet.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/servlet.jar
else
  ShowLogFaild "$JAR_SOURCE/servlet.jar"
fi


if test -e $JAR_SOURCE/servlet-api.jar;then
  cp -nrf $JAR_SOURCE/servlet-api.jar $OAT_SOURCE/HisClient/lib/servlet-api.jar
  cp -nrf $JAR_SOURCE/servlet-api.jar $OAT_SOURCE/HisPrivacyCAWebServices2/clientlib/servlet-api.jar
  cp -nrf $JAR_SOURCE/servlet-api.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/jars-compile-only/servlet-api.jar
  cp -nrf $JAR_SOURCE/servlet-api.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/servlet-api.jar
  cp -nrf $JAR_SOURCE/servlet-api.jar $OAT_SOURCE/HisAppraiser/lib/servlet-api.jar
  cp -nrf $JAR_SOURCE/servlet-api.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/servlet-api.jar
  cp -nrf $JAR_SOURCE/servlet-api.jar $OAT_SOURCE/HisWebServices/clientlib/servlet-api.jar
  cp -nrf $JAR_SOURCE/servlet-api.jar $OAT_SOURCE/HisWebServices/WEB-INF/jars-compile-only/servlet-api.jar
  cp -nrf $JAR_SOURCE/servlet-api.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/servlet-api.jar
else
  ShowLogFaild "$JAR_SOURCE/servlet-api.jar"
fi


if test -e $JAR_SOURCE/stax-ex.jar;then
  cp -nrf $JAR_SOURCE/stax-ex.jar $OAT_SOURCE/HisClient/lib/stax-ex.jar
  cp -nrf $JAR_SOURCE/stax-ex.jar $OAT_SOURCE/HisPrivacyCAWebServices2/clientlib/stax-ex.jar
  cp -nrf $JAR_SOURCE/stax-ex.jar $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/stax-ex.jar
  cp -nrf $JAR_SOURCE/stax-ex.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/stax-ex.jar
  cp -nrf $JAR_SOURCE/stax-ex.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/stax-ex.jar
  cp -nrf $JAR_SOURCE/stax-ex.jar $OAT_SOURCE/HisAppraiser/lib/stax-ex.jar
  cp -nrf $JAR_SOURCE/stax-ex.jar $OAT_SOURCE/PrivacyCA/lib/stax-ex.jar
  cp -nrf $JAR_SOURCE/stax-ex.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/stax-ex.jar
  cp -nrf $JAR_SOURCE/stax-ex.jar $OAT_SOURCE/HisWebServices/clientlib/stax-ex.jar
  cp -nrf $JAR_SOURCE/stax-ex.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/stax-ex.jar
  cp -nrf $JAR_SOURCE/stax-ex.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/stax-ex.jar
else
  ShowLogFaild "$JAR_SOURCE/stax-ex.jar"
fi


if test -e $JAR_SOURCE/streambuffer.jar;then
  cp -nrf $JAR_SOURCE/streambuffer.jar $OAT_SOURCE/HisClient/lib/streambuffer.jar
  cp -nrf $JAR_SOURCE/streambuffer.jar $OAT_SOURCE/HisPrivacyCAWebServices2/clientlib/streambuffer.jar
  cp -nrf $JAR_SOURCE/streambuffer.jar $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/streambuffer.jar
  cp -nrf $JAR_SOURCE/streambuffer.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/streambuffer.jar
  cp -nrf $JAR_SOURCE/streambuffer.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/streambuffer.jar
  cp -nrf $JAR_SOURCE/streambuffer.jar $OAT_SOURCE/HisAppraiser/lib/streambuffer.jar
  cp -nrf $JAR_SOURCE/streambuffer.jar $OAT_SOURCE/PrivacyCA/lib/streambuffer.jar
  cp -nrf $JAR_SOURCE/streambuffer.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/streambuffer.jar
  cp -nrf $JAR_SOURCE/streambuffer.jar $OAT_SOURCE/HisWebServices/clientlib/streambuffer.jar
  cp -nrf $JAR_SOURCE/streambuffer.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/streambuffer.jar
  cp -nrf $JAR_SOURCE/streambuffer.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/streambuffer.jar
else
  ShowLogFaild "$JAR_SOURCE/streambuffer.jar"
fi


if test -e $JAR_SOURCE/woodstox.jar;then
  cp -nrf $JAR_SOURCE/woodstox.jar $OAT_SOURCE/HisClient/lib/woodstox.jar
  cp -nrf $JAR_SOURCE/woodstox.jar $OAT_SOURCE/HisPrivacyCAWebServices2/clientlib/woodstox.jar
  cp -nrf $JAR_SOURCE/woodstox.jar $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/woodstox.jar
  cp -nrf $JAR_SOURCE/woodstox.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/woodstox.jar
  cp -nrf $JAR_SOURCE/woodstox.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/woodstox.jar
  cp -nrf $JAR_SOURCE/woodstox.jar $OAT_SOURCE/HisAppraiser/lib/woodstox.jar
  cp -nrf $JAR_SOURCE/woodstox.jar $OAT_SOURCE/PrivacyCA/lib/woodstox.jar
  cp -nrf $JAR_SOURCE/woodstox.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/woodstox.jar
  cp -nrf $JAR_SOURCE/woodstox.jar $OAT_SOURCE/HisWebServices/clientlib/woodstox.jar
  cp -nrf $JAR_SOURCE/woodstox.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/woodstox.jar
  cp -nrf $JAR_SOURCE/woodstox.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/woodstox.jar
else
  ShowLogFaild "$JAR_SOURCE/woodstox.jar"
fi


if test -e $JAR_SOURCE/xsdlib.jar;then
  cp -nrf $JAR_SOURCE/xsdlib.jar $OAT_SOURCE/HisClient/lib/xsdlib.jar
  cp -nrf $JAR_SOURCE/xsdlib.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/xsdlib.jar
  cp -nrf $JAR_SOURCE/xsdlib.jar $OAT_SOURCE/HisAppraiser/lib/xsdlib.jar
  cp -nrf $JAR_SOURCE/xsdlib.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/xsdlib.jar
  cp -nrf $JAR_SOURCE/xsdlib.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/xsdlib.jar
  cp -nrf $JAR_SOURCE/xsdlib.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/xsdlib.jar
else
  ShowLogFaild "$JAR_SOURCE/xsdlib.jar"
fi


if test -e $JAR_SOURCE/commons-cli-1.0.jar;then
  cp -nrf $JAR_SOURCE/commons-cli-1.0.jar $OAT_SOURCE/OpenAttestationAdminConsole/WebContent/WEB-INF/lib/commons-cli-1.0.jar
  cp -nrf $JAR_SOURCE/commons-cli-1.0.jar $OAT_SOURCE/HisAppraiser/lib/commons-cli-1.0.jar
  cp -nrf $JAR_SOURCE/commons-cli-1.0.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/commons-cli-1.0.jar
  cp -nrf $JAR_SOURCE/commons-cli-1.0.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/commons-cli-1.0.jar
  cp -nrf $JAR_SOURCE/commons-cli-1.0.jar $OAT_SOURCE/OpenAttestationManifestWebServices/WebContent/WEB-INF/lib/commons-cli-1.0.jar
else
  ShowLogFaild "$JAR_SOURCE/commons-cli-1.0.jar"
fi


if test -e $JAR_SOURCE/jsp-api.jar;then
  cp -nrf $JAR_SOURCE/jsp-api.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/jars-compile-only/jsp-api.jar
  cp -nrf $JAR_SOURCE/jsp-api.jar $OAT_SOURCE/HisWebServices/WEB-INF/jars-compile-only/jsp-api.jar
else
  ShowLogFaild "$JAR_SOURCE/jsp-api.jar"
fi


if test -e $JAR_SOURCE/commons-discovery-0.2.jar;then
  cp -nrf $JAR_SOURCE/commons-discovery-0.2.jar $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/commons-discovery-0.2.jar
  cp -nrf $JAR_SOURCE/commons-discovery-0.2.jar $OAT_SOURCE/PrivacyCA/lib/commons-discovery-0.2.jar
  if test -d $OAT_SOURCE/TSSCoreService;then
  	cp -nrf $JAR_SOURCE/commons-discovery-0.2.jar $OAT_SOURCE/TSSCoreService/lib/commons-discovery-0.2.jar
  fi
else
  ShowLogFaild "$JAR_SOURCE/commons-discovery-0.2.jar"
fi


if test -e $JAR_SOURCE/wsdl4j-1.5.1.jar;then
  cp -nrf $JAR_SOURCE/wsdl4j-1.5.1.jar $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/wsdl4j-1.5.1.jar
  cp -nrf $JAR_SOURCE/wsdl4j-1.5.1.jar $OAT_SOURCE/PrivacyCA/lib/wsdl4j-1.5.1.jar
  if test -d $OAT_SOURCE/TSSCoreService;then
  	cp -nrf $JAR_SOURCE/wsdl4j-1.5.1.jar $OAT_SOURCE/TSSCoreService/lib/wsdl4j-1.5.1.jar
  fi
else
  ShowLogFaild "$JAR_SOURCE/wsdl4j-1.5.1.jar"
fi


if test -e $JAR_SOURCE/axis.jar;then
  cp -nrf $JAR_SOURCE/axis.jar $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/axis.jar
  cp -nrf $JAR_SOURCE/axis.jar $OAT_SOURCE/PrivacyCA/lib/axis.jar
  if test -d $OAT_SOURCE/TSSCoreService;then
  	cp -nrf $JAR_SOURCE/axis.jar $OAT_SOURCE/TSSCoreService/lib/axis.jar
  fi
else
  ShowLogFaild "$JAR_SOURCE/axis.jar"
fi


if test -e $JAR_SOURCE/jaxb-libs.jar;then
  cp -nrf $JAR_SOURCE/jaxb-libs.jar $OAT_SOURCE/HisClient/lib/jaxb-libs.jar
else
  ShowLogFaild "$JAR_SOURCE/jaxb-libs.jar"
fi


if test -e $JAR_SOURCE/jta-spec1_0_1.jar;then
  cp -nrf $JAR_SOURCE/jta-spec1_0_1.jar $OAT_SOURCE/HisClient/lib/jta-spec1_0_1.jar
else
  ShowLogFaild "$JAR_SOURCE/jta-spec1_0_1.jar"
fi


if test -e $JAR_SOURCE/log4j-1.2.14.jar;then
  cp -nrf $JAR_SOURCE/log4j-1.2.14.jar $OAT_SOURCE/HisClient/lib/log4j-1.2.14.jar
else
  ShowLogFaild "$JAR_SOURCE/log4j-1.2.14.jar"
fi


if test -e $JAR_SOURCE/uuid-3.2.jar;then
  cp -nrf $JAR_SOURCE/uuid-3.2.jar $OAT_SOURCE/HisClient/lib/uuid-3.2.jar
else
  ShowLogFaild "$JAR_SOURCE/uuid-3.2.jar"
fi


if test -e $JAR_SOURCE/jaxb-impl-2.1.12.jar;then
  cp -nrf $JAR_SOURCE/jaxb-impl-2.1.12.jar $OAT_SOURCE/OpenAttestationWebServices/WebContent/WEB-INF/lib/jaxb-impl-2.1.12.jar
else
  ShowLogFaild "$JAR_SOURCE/jaxb-impl-2.1.12.jar"
fi


if test -e $JAR_SOURCE/commons-logging-1.0.4.jar;then
  cp -nrf $JAR_SOURCE/commons-logging-1.0.4.jar $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/commons-logging-1.0.4.jar
  cp -nrf $JAR_SOURCE/commons-logging-1.0.4.jar $OAT_SOURCE/PrivacyCA/lib/commons-logging-1.0.4.jar
else
  ShowLogFaild "$JAR_SOURCE/commons-logging-1.0.4.jar"
fi


if test -e $JAR_SOURCE/commons-collections.jar;then
  cp -nrf $JAR_SOURCE/commons-collections.jar $OAT_SOURCE/HisClient/lib/commons-collections.jar
else
  ShowLogFaild "$JAR_SOURCE/commons-collections.jar"
fi


if test -e $JAR_SOURCE/bcprov-jdk15-129.jar;then
  cp -nrf $JAR_SOURCE/bcprov-jdk15-129.jar $OAT_SOURCE/HisWebServices/WEB-INF/lib/bcprov-jdk15-129.jar
else
  ShowLogFaild "$JAR_SOURCE/bcprov-jdk15-129.jar"
fi

if test -e $JAR_SOURCE/jaxrpc.jar;then
  cp -nrf $JAR_SOURCE/jaxrpc.jar $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/jaxrpc.jar
  cp -nrf $JAR_SOURCE/jaxrpc.jar $OAT_SOURCE/PrivacyCA/lib/jaxrpc.jar
  if test -d $OAT_SOURCE/TSSCoreService;then
  	cp -nrf $JAR_SOURCE/jaxrpc.jar $OAT_SOURCE/TSSCoreService/lib/jaxrpc.jar
  fi
else
  ShowLogFaild "$JAR_SOURCE/bcprov-jdk15-129.jar"
fi

if test -e $JAR_SOURCE/saaj.jar;then
  if test -d $OAT_SOURCE/TSSCoreService;then
  	cp -nrf $JAR_SOURCE/saaj.jar $OAT_SOURCE/TSSCoreService/lib/saaj.jar
  fi
else
  ShowLogFaild "$JAR_SOURCE/saaj.jar"
fi

if test -e $JAR_SOURCE/ant-antlr-1.6.5.jar;then
  if test -d $OAT_SOURCE/TSSCoreService;then
  	cp -nrf $JAR_SOURCE/ant-antlr-1.6.5.jar $OAT_SOURCE/TSSCoreService/lib/ant-antlr-1.6.5.jar
  fi
else
  ShowLogFaild "$JAR_SOURCE/ant-antlr-1.6.5.jar"
fi


####### SALlib #####
cp -nrf $JAR_SOURCE/hibernate3.jar  $OAT_SOURCE/HisPrivacyCAWebServices2/ClientFiles/lib/SALlib_hibernate3.jar
cp -nrf $JAR_SOURCE/log4j-1.2.8.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/SALlib_log4j-1.2.8.jar
cp -nrf $JAR_SOURCE/cglib-2.1.3.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/SALlib_cglib-2.1.3.jar
cp -nrf $JAR_SOURCE/commons-cli-1.0.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/SALlib_commons-cli-1.0.jar
cp -nrf $JAR_SOURCE/jtds-1.2.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/SALlib_jtds-1.2.jar
cp -nrf $JAR_SOURCE/jdbc2_0-stdext.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/SALlib_jdbc2_0-stdext.jar
cp -nrf $JAR_SOURCE/xsdlib.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/SALlib_xsdlib.jar
cp -nrf $JAR_SOURCE/jta.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/SALlib_jta.jar
cp -nrf $JAR_SOURCE/mail.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/SALlib_mail.jar
cp -nrf $JAR_SOURCE/relaxngDatatype.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/SALlib_relaxngDatatype.jar
cp -nrf $JAR_SOURCE/commons-httpclient-3.0.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/SALlib_commons-httpclient-3.0.jar
cp -nrf $JAR_SOURCE/hibernate3.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/SALlib_hibernate3.jar
cp -nrf $JAR_SOURCE/commons-logging.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/SALlib_commons-logging.jar
cp -nrf $JAR_SOURCE/dom4j-1.6.1.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/SALlib_dom4j-1.6.1.jar
cp -nrf $JAR_SOURCE/jaas.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/SALlib_jaas.jar
cp -nrf $JAR_SOURCE/c3p0-0.9.0.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/SALlib_c3p0-0.9.0.jar
cp -nrf $JAR_SOURCE/asm.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/SALlib_asm.jar
cp -nrf $JAR_SOURCE/commons-codec-1.3.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/SALlib_commons-codec-1.3.jar
cp -nrf $JAR_SOURCE/commons-beanutils.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/SALlib_commons-beanutils.jar
cp -nrf $JAR_SOURCE/commons-digester.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/SALlib_commons-digester.jar
cp -nrf $JAR_SOURCE/commons-collections-2.1.1.jar $OAT_SOURCE/HisPrivacyCAWebServices2/WEB-INF/lib/SALlib_commons-collections-2.1.1.jar
cp -nrf $JAR_SOURCE/hibernate3.jar $OAT_SOURCE/PrivacyCA/lib/SALlib_hibernate3.jar
