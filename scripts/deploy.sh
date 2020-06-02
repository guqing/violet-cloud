#!/bin/bash 
# 通过sshpass免除scp命令输入密码的过程，通过StrictHostKeyChecking=no免除scp命令信任主机的过程
serverIp=$1
pwd=$2
# 上传jar
sshpass -p $pwd scp -P 22 -o StrictHostKeyChecking=no violet-gateway/target/violet-gateway-0.0.1-SNAPSHOT.jar root@$serverIp:/root/violet/gateway
sshpass -p $pwd scp -P 22 -o StrictHostKeyChecking=no violet-auth/target/violet-auth-0.0.1-SNAPSHOT.jar root@$serverIp:/root/violet/auth
sshpass -p $pwd scp -P 22 -o StrictHostKeyChecking=no violet-app/violet-app-admin/target/violet-app-admin-0.0.1-SNAPSHOT.jar root@$serverIp:/root/violet/app/admin
# 执行脚本
sshpass -p $pwd ssh -tt -p 22 -o StrictHostKeyChecking=no root@$serverIp 2>&1 << eeooff
cd /root/violet/gateway
echo '开始执行服务器命令'
source /etc/profile ~/.profile ~/.bash_profile
./stop.sh
./run.sh
cd /root/violet/auth
./stop.sh
./run.sh
cd /root/violet/app/admin
./stop.sh
./run.sh
exit 
eeooff
