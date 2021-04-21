@echo off

if not exist "%JAVA_HOME%\bin\java.exe" echo Please set the JAVA_HOME variable in your environment, We need java(x64)! jdk8 or later is better! & EXIT /B 1
set "JAVA=%JAVA_HOME%\bin\java.exe"

setlocal enabledelayedexpansion

set BASE_DIR=%~dp0
rem added double quotation marks to avoid the issue caused by the folder names containing spaces.
rem removed the last 5 chars(which means \bin\) to get the base DIR.
set BASE_DIR="%BASE_DIR:~0,-5%"

set DEFAULT_SEARCH_LOCATIONS="classpath:/,classpath:/config/,file:./,file:./config/"
set CUSTOM_SEARCH_LOCATIONS=%DEFAULT_SEARCH_LOCATIONS%,file:%BASE_DIR%/conf/


set SERVER=product-core


set "JAVA_OPT=%JAVA_OPT% -Xms512m -Xmx512m -Xmn512m"


set "JAVA_OPT=%JAVA_OPT% -Dsystem.basedir=%BASE_DIR%"
set "JAVA_OPT=%JAVA_OPT% -Dloader.path=%BASE_DIR%/plugins/health -jar %BASE_DIR%\target\%SERVER%.jar"
set "JAVA_OPT=%JAVA_OPT% --spring.config.location=%CUSTOM_SEARCH_LOCATIONS%"
set "JAVA_OPT=%JAVA_OPT% --logging.config=%BASE_DIR%/conf/log4j2.xml"

call "%JAVA%" %JAVA_OPT% %SERVER% %*

pause
