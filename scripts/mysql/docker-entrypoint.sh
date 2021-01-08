#!/bin/bash
echo "Start executing initialization script..."
mysql -uroot -p$MYSQL_ROOT_PASSWORD <<EOF
source /data/initdb/violet_cloud.sql;
source /data/initdb/violet_nacos.sql;
EOF
echo "End of MySQL initialization file execution..."