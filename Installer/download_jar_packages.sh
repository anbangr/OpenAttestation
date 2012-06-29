#!/bin/bash

JAR_SOURCE_DIRCTORY=../JAR_SOURCE
DOWNLOAD_LOG=Download.log

if [ -d  $JAR_SOURCE_DIRCTORY ]
then
	rm -rf $JAR_SOURCE_DIRCTORY > /dev/null
	mkdir $JAR_SOURCE_DIRCTORY
else
	mkdir $JAR_SOURCE_DIRCTORY
fi

DOWNLOAD_CONTENT=`awk  '{print $1;}' download_jar_package_list.txt`

if [ -f $DOWNLOAD_LOG ]
then
	rm -f $DOWNLOAD_LOG > /dev/null
	touch $DOWNLOAD_LOG
else
	touch $DOWNLOAD_LOG
fi
for DOWNLOAD_FILE_NAME in $DOWNLOAD_CONTENT
do
	LOCAL_NAME=`echo "$DOWNLOAD_FILE_NAME" | awk -F "-----" '{print $1;}'`
	DOWNLOAD_PATH=`echo "$DOWNLOAD_FILE_NAME" | awk -F "-----" '{print $2;}'`
	echo "$LOCAL_NAME $DOWNLOAD_PATH"
	echo `wget -t 1 -O ../JAR_SOURCE/$LOCAL_NAME $DOWNLOAD_PATH`
	if [ -f ../JAR_SOURCE/$LOCAL_NAME ]
	then
		if [ 0 -eq `ls -al $JAR_SOURCE_DIRCTORY/$LOCAL_NAME | awk -F " " '{print $5;}'` ] 
		then
			echo "Download file from $DOWNLOAD_PATH failed!" >> $DOWNLOAD_LOG
			rm -f $JAR_SOURCE_DIRCTORY/$LOCAL_NAME
		else
			if [ "jar" = `ls $JAR_SOURCE_DIRCTORY/$LOCAL_NAME | awk -F "." '{print $NF}'` ]
			then
				ECH=`unzip -Z $JAR_SOURCE_DIRCTORY/$LOCAL_NAME | grep "uncompressed"`
				if [ ! "$ECH" ]
				then
					echo "Download file from $DOWNLOAD_PATH failed!" >> $DOWNLOAD_LOG
					rm -f $JAR_SOURCE_DIRCTORY/$LOCAL_NAME
				fi
			fi
		fi
	fi
done

grep "Download file from http://" $DOWNLOAD_LOG

cd $JAR_SOURCE_DIRCTORY

cp commons-collections-2.1.1.jar commons-collections.jar 
cp commons-logging-1.0.4.jar commons-logging.jar
cp jaxb-impl-2.1.12.jar jaxb-impl.jar
cp bcprov-jdk15-143.jar bcprov-jdk15-129.jar 
mv bcprov-jdk15-143.jar bcprov-jdk15-141.jar

