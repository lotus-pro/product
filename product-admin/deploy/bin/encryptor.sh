#!/bin/sh

export JASYPT_PASSWORD="sunline"

#echo "请输入加密密钥："
#read password

while true

do
echo "请输入加密明文："
read input
java -cp lib/jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input="$input" password="$JASYPT_PASSWORD"
done
