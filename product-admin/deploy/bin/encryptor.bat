@echo off

set JASYPT_PASSWORD="sunline"

::set/p password=�����������Կ��

:again

set/p input=������������ģ�

echo ������......

java -cp lib/jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input="%input%" password="%JASYPT_PASSWORD%"

goto again

pause