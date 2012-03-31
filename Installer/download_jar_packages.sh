#!/bin/sh

JAR_SOURCE_DIRCTORY=../JAR_SOURCE
DOWNLOAD_LOG=Download.log
i=0


if [ -d  $JAR_SOURCE_DIRCTORY ]
then
	rm -rf $JAR_SOURCE_DIRCTORY > /dev/null
else
	mkdir $JAR_SOURCE_DIRCTORY
fi

DOWNLOAD_LOCAL_PATH=`awk -F "://" '{print $2;}' download_jar_package_list.txt`

if [ -f $DOWNLOAD_LOG ]
then
	rm -f $DOWNLOAD_LOG > /dev/null
	touch $DOWNLOAD_LOG
else
	touch $DOWNLOAD_LOG
fi

for DOWNLOAD_FILE_NAME in $DOWNLOAD_LOCAL_PATH
do
	echo `wget -t 1 -P ../JAR_SOURCE http://$DOWNLOAD_FILE_NAME`
	j=`find $JAR_SOURCE_DIRCTORY -type f | wc -l`
	let "i+=1"
	if [ $i -gt $j ]
	then
		echo "Download file from http://$DOWNLOAD_FILE_NAME failed!" >> $DOWNLOAD_LOG
		let "i-=1"
	fi
done
grep "Download file from http://" $DOWNLOAD_LOG

cd $JAR_SOURCE_DIRCTORY

mv activation-1.0.2.jar activation.jar
mv asm-1.5.3.jar asm.jar
cp commons-collections-2.1.1.jar commons-collections.jar 
mv commons-digester-1.6.jar commons-digester.jar
cp commons-logging-1.0.4.jar commons-logging.jar
mv FastInfoset-1.2.4.jar FastInfoset.jar
mv hibernate-3.1.3.jar hibernate3.jar
mv jaxb-api-2.1.jar jaxb-api.jar
cp jaxb-impl-2.1.12.jar jaxb-impl.jar
mv jaxb-xjc-2.1.11.jar jaxb-xjc.jar
mv jaxws-api-2.1.jar jaxws-api.jar
mv jaxws-rt-2.1.7.jar jaxws-rt.jar
mv jaxws-tools-2.1.7.jar jaxws-tools.jar
mv mail-1.3.1.jar mail.jar
mv mimepull-1.2.jar mimepull.jar
mv remotecontent?filepath=mysql%2Fmysql-connector-java%2F5.0.7%2Fmysql-connector-java-5.0.7.jar mysql-connector-java-5.0.7-bin.jar 
mv relaxngDatatype-1.5.jar relaxngDatatype.jar
mv resolver-20050927.jar resolver.jar
mv saaj-api-1.3.jar saaj-api.jar
mv saaj-impl-1.3.jar saaj-impl.jar
mv servlet-4.1.36.jar servlet.jar
mv stax-ex-1.2.jar stax-ex.jar
mv streambuffer-0.8.jar streambuffer.jar
mv servlet-api-2.4.jar servlet-api.jar
mv commons-beanutils-1.6.jar commons-beanutils.jar
mv org.apache.servicemix.bundles.woodstox-3.2.7_1.jar woodstox.jar 
mv jaxb-libs-1.0.5.jar jaxb-libs.jar
mv remotecontent?filepath=org%2Fmod4j%2Forg%2Feclipse%2Fxtext%2Flog4j%2F1.2.15%2Flog4j-1.2.15.jar log4j-1.2.14.jar
mv remotecontent?filepath=com%2Feaio%2Fuuid%2Fuuid%2F3.2%2Fuuid-3.2.jar uuid-3.2.jar
mv jsp-api-2.0.jar jsp-api.jar
mv axis-1.2.1.jar axis.jar
cp bcprov-jdk15-143.jar bcprov-jdk15-129.jar 
mv bcprov-jdk15-143.jar bcprov-jdk15-141.jar 
mv cglib-2.1_3.jar cglib-2.1.3.jar 
