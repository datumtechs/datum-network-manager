#!/bin/bash

PROFILE=$1
# stop
ps -ef|grep -v grep|grep rosettanet-admin|grep active=$PROFILE|awk '{print $2}'|xargs kill -9;
echo 'stop success!'
#remove log file
rm -rf nohup.out
#start
nohup java -jar rosettanet-admin-*.jar --spring.profiles.active=$PROFILE > /dev/null 2>&1 &
echo 'start success!'
ps -elf|grep rosettanet-admin
