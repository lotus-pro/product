#!/bin/sh

cd `dirname $0`/../target
target_dir=`pwd`
server_name=product-support

pid=`ps ax | grep -i ${server_name} | grep ${target_dir} | grep java | grep -v grep | awk '{print $1}'`
if [ -z "$pid" ] ; then
        echo "No ${server_name} running..."
        exit 0;
fi

echo "The ${server_name}(${pid}) is running..."

kill ${pid}

echo "Send shutdown request to ${server_name}(${pid}) OK"
