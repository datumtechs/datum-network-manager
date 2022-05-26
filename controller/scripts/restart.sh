#!/bin/bash

PROFILE=$1
# stop
ps -ef|grep -v grep|grep datum-admin|grep active=$PROFILE|awk '{print $2}'|xargs kill -9;
echo 'stop success!'

rm -rf nohup.out

#start
#nohup java -jar datum-admin*.jar --spring.profiles.active=$PROFILE > /dev/null 2>&1 &

nohup java \
-server \
-Xms2G \
-Xmx2G \
-Xmn256m \
-XX:MetaspaceSize=512M \
-XX:MaxMetaspaceSize=512M \
-Xss256k \
-XX:+UseConcMarkSweepGC \
-XX:+UseParNewGC \
-XX:+CMSClassUnloadingEnabled \
-XX:+HeapDumpOnOutOfMemoryError \
-verbose:gc \
-XX:+PrintGCDetails \
-XX:+PrintGCTimeStamps \
-XX:+PrintGCDateStamps \
-Xloggc:.logs/gc.log \
-XX:CMSInitiatingOccupancyFraction=75 \
-XX:+UseCMSInitiatingOccupancyOnly \
-jar datum-admin*.jar --spring.profiles.active=$PROFILE > /dev/null 2>&1 &

echo 'start success!'
ps -elf|grep datum-admin