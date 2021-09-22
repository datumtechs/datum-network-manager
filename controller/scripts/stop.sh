#!/bin/bash
PROFILE=$1
ps -ef|grep -v grep|grep metis-admin|grep active=$PROFILE|awk '{print $2}'|xargs kill -9;
echo '进程列表:'
ps -elf|grep metis-admin
