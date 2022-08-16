#!/bin/bash
PROFILE=$1
ps -ef|grep -v grep|grep /home/user1/admin/admin.jar |grep active=$PROFILE|awk '{print $2}'|xargs -r kill -9;
echo 'Process List:'
ps -elf|grep /home/user1/admin/admin.jar