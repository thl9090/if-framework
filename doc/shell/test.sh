#!/bin/bash
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

PID=`ps -ef -ww | grep java | grep " -DappName=$SERVICE_NAME "`
if [ -z "$PID" ]; then
	echo "ERROR: The $SERVICE_NAME Does Not Started!"
	exit 1
fi

#echo "------------------ $PID ---------------------------"

for STID in $PID ; do
    echo "$STID"
done

