#!/bin/bash
PROFILE=$1
ps -ef|grep -v grep|grep datum-admin|grep active=$PROFILE|awk '{print $2}'|xargs kill -9;
echo 'Process List:'
ps -elf|grep datum-admin