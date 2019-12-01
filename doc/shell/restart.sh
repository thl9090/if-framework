#!/bin/bash
cd `dirname $0`

JAR_NAME=$1
TAIL_LOG=$2

if [ -z $JAR_NAME ]; then 
	echo "The Jar Name Can Not Empty !"
	exit 1
fi

./stop.sh $JAR_NAME
echo ""
./startup.sh $JAR_NAME $TAIL_LOG
