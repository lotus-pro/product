@echo off

set JASYPT_PASSWORD="sunline"

::set/p password=请输入加密密钥：

:again

set/p input=请输入加密明文：

echo 加密中......

java -cp lib/jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input="%input%" password="%JASYPT_PASSWORD%"

goto again

pause