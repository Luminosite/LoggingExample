#!/bin/bash

. ./utils.sh
if [[ $# -lt 1 ]];
then
    echo "host missing"
    exit 1
else
    host=$1
fi
version=`getVersion`
echo "Version is $version"
suffix="_$version"
name=`getName`
echo "Name is $name"
user=`whoami`

chmod 755 shell/*.sh
echo "Creating directory on remote"
ssh ${host} "rm -r /x/home/$user/workspace/${name}$suffix/script/"
ssh ${host} "mkdir -p /x/home/$user/workspace/${name}$suffix/jars/ /x/home/$user/workspace/${name}$suffix/script/"
ssh ${host} "mkdir -p /x/home/$user/workspace/${name}$suffix/logs/"
rsync -azP shell/* ${host}:/x/home/${user}/workspace/${name}${suffix}/script/
