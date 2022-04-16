#!/bin/bash


mvn clean package
DIR="target"
JAVA_FILE=$(find target/ -maxdepth 1 -type f -name "*.jar" -printf '%TY-%Tm-%Td %TH:%TM: %Tz %p\n'| sort -n | tail -n1 | awk -F "./" '{print $2}')

ssh root@164.92.150.184 "killall java;rm -rf /root/server/*.jar"
rsync -az $DIR/$JAVA_FILE root@164.92.150.184:/root/server/
ssh root@164.92.150.184 "java -jar /root/server/$JAVA_FILE"
