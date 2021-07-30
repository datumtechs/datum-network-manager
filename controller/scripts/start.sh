#!/bin/bash
PROFILE=$1
ps -ef|grep -v grep|grep rosettanet-admin|grep active=$PROFILE|awk '{print $2}'|xargs kill -9;
nohup java -jar rosettanet-admin-*.jar --spring.profiles.active=$PROFILE > /dev/null 2>&1 &
echo '进程列表:'
ps -elf|grep rosettanet-admin
