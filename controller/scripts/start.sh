#!/bin/bash
PROFILE=$1
ps -ef|grep -v grep|grep metis-admin-0.3.0-SNAPSHOT|grep active=$PROFILE|awk '{print $2}'|xargs kill -9;
# nohup java JAVA_OPTS=-Xmx1024m -Xms512m -Xmn512m -Xss256k -XX:PermSize=256M -XX:MaxPermSize=256m -XX:MaxTenuringThreshold=0 -XX:+UseParallelGC -XX:ParallelGCThreads=20 -XX:+UseParallelOldGC -jar metis-admin-0.3.0-SNAPSHOT.jar --spring.profiles.active=$PROFILE > /dev/null 2>&1 &

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
-jar metis-admin-0.3.0-SNAPSHOT.jar --spring.profiles.active=$PROFILE > /dev/null 2>&1 &


echo 'Agent Process List:'
ps -elf|grep metis-admin-0.3.0-SNAPSHOT