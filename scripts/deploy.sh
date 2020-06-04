#!/bin/bash 
# 通过sshpass免除scp命令输入密码的过程，通过StrictHostKeyChecking=no免除scp命令信任主机的过程
serverIp=$1
pwd=$2
# 上传jar
#sshpass -p $pwd scp violet-gateway/target/violet-gateway-0.0.1-SNAPSHOT.jar root@$serverIp:/root/violet/gateway
ls violet-app/violet-app-admin/target/
sshpass -p $pwd scp violet-app/violet-app-admin/target/violet-app-admin-0.0.1-SNAPSHOT.jar root@$serverIp:/root/violet/app/admin
sshpass -p $pwd scp -P 22 -o StrictHostKeyChecking=no violet-auth/target/violet-auth-0.0.1-SNAPSHOT.jar root@$serverIp:/root/violet/auth

# 执行脚本
sshpass -p $pwd ssh -tt -p 22 -o StrictHostKeyChecking=no root@$serverIp 2>&1 << eeooff
echo '开始在目标服务器执行脚本'
source /etc/profile ~/.profile ~/.bash_profile
cd /root/violet/auth
./stop.sh
./run.sh
cd /root/violet/app/admin
./stop.sh
./run.sh
exit 
eeooff
