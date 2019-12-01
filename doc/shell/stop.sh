#!/bin/bash
echo "############################# Start ################################"
DIR_PATH=`pwd`

JAR_NAME=$1

echo "Wait Stop Jar Name : $JAR_NAME"

SERVICE_NAME=${JAR_NAME#*-}

SERVICE_NAME=${SERVICE_NAME%%-*}

echo "Wait Stop Service Name : $SERVICE_NAME"

if [ -z $SERVICE_NAME ]; then 
	echo "The Service Name Can Not Empty !"
	exit 1
fi

PIDS=`ps -ef | grep java | grep -v grep | grep " -DappName=$SERVICE_NAME "`
if [ -z "$PIDS" ]; then
	echo "The $SERVICE_NAME Does Not Started!"
	exit 1
fi

echo -e "Stopping The $SERVICE_NAME ...\c"

for PID in $PIDS ; do
    kill $PID > /dev/null 2>&1
done

COUNT=0
while [ $COUNT -lt 1 ]; do
    echo -e ".\c"
    sleep 1
	COUNT=`ps -ef | grep java | grep -v grep | grep " -DappName=$SERVICE_NAME " | wc -l`
	if [ $COUNT -lt 1 ]; then
		echo "\n"
		break
	fi
	COUNT=0
done

echo "\n"
echo "Stop The $SERVICE_NAME OK!"
echo "############################# End ################################"



