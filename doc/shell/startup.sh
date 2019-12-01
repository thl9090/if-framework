#!/bin/bash
echo "############################# Start ################################"
DIR_PATH=`pwd`
LOG_PATH=$DIR_PATH

JAR_NAME=$1
TAIL_LOG=$2

echo "Wait Jar Name : $JAR_NAME"
echo "Tail Out File : $TAIL_LOG"

SERVICE_NAME=${JAR_NAME#*-}

SERVICE_NAME=${SERVICE_NAME%%-*}

echo "Wait Start Service Name : $SERVICE_NAME"

if [ -z $SERVICE_NAME ]; then 
	echo "The Service Name Can Not Empty !"
	exit 1
fi

PIDS=`ps -ef | grep java | grep -v grep | grep " -DappName=$SERVICE_NAME "`
if [ -n "$PIDS" ]; then
	echo "The $SERVICE_NAME Is Running !"
	echo "PIDS: $PIDS"
	exit 1
fi

LOG_FILE=$LOG_PATH/$SERVICE_NAME.out

JAVA_OPTS=" -DappName=$SERVICE_NAME -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -Dproject.run.path=$DIR_PATH -Ddubbo.shutdown.hook=true"

BITS=`java -version 2>&1 | grep -i 64-bit`
#echo "JVM is 64-bit? $BITS"

JAVA_MEM_SIZE_OPTS="-Xmx512m -Xms512m -Xmn256m -XX:PermSize=128m -Xss256k"

if [ -n "$BITS" ]; then
    JAVA_MEM_OPTS=" -server $JAVA_MEM_SIZE_OPTS -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
else
    JAVA_MEM_OPTS=" -server $JAVA_MEM_SIZE_OPTS -XX:SurvivorRatio=2 -XX:+UseParallelGC "
fi

echo -e "Starting the $SERVICE_NAME ...\c"
nohup java -jar $JAVA_OPTS $JAVA_MEM_OPTS $JAR_NAME > $LOG_FILE 2>&1 &

COUNT=0
while [ $COUNT -lt 1 ]; do
    echo -e ".\c"
    sleep 1
    COUNT=`ps -ef | grep java | grep -v grep | grep " -DappName=$SERVICE_NAME " | wc -l`
    if [ $COUNT -gt 0 ]; then
		echo "\n"
        break
    fi
done

PIDS=`ps -ef | grep java | grep -v grep | grep " -DappName=$SERVICE_NAME "`
echo "Start $SERVICE_NAME success!"
echo "PID: $PIDS"
echo "Out File Dir: $LOG_FILE"
echo "############################# End ################################"
if [ -n "$TAIL_LOG" ]; then
	tail -f $LOG_FILE
fi



