#!/bin/bash
#切换到脚本所在目录
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
SERVER_NAME=`basename $DEPLOY_DIR`
CONF_DIR=$DEPLOY_DIR/conf

PID=`ps -ef | grep java | grep "$CONF_DIR" |awk '{print $2}'`
if [ -n "$PID" ]; then
    echo "ERROR: already started!"
    echo "PID: $PID"
    exit 1
fi

LIB_DIR=$DEPLOY_DIR/lib
LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`

JAVA_OPTS="-Djava.awt.headless=true -Djava.net.preferIPv4Stack=true"
JAVA_MEM_OPTS=""
BITS=`java -version 2>&1 | grep -i 64-bit`
if [ -n "$BITS" ]; then
    JAVA_MEM_OPTS=" -server -Xmx4g -Xms4g -Xmn1g -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
else
    JAVA_MEM_OPTS=" -server -Xms4g -Xmx4g -XX:SurvivorRatio=2 -XX:+UseParallelGC "
fi

CLASSPATH="$CLASSPATH:$CONF_DIR:$LIB_JARS"
echo -e "Starting the $SERVER_NAME ...\c"
java $JAVA_OPTS $JAVA_MEM_OPTS -classpath $CLASSPATH com.iecas.kds.tools.kafka.main.KafkaCon
