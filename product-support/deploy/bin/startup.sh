#!/bin/sh

error_exit ()
{
    echo "ERROR: $1 !!"
    exit 1
}
[ ! -e "$JAVA_HOME/bin/java" ] && JAVA_HOME=$HOME/jdk/java
[ ! -e "$JAVA_HOME/bin/java" ] && JAVA_HOME=/usr/java
[ ! -e "$JAVA_HOME/bin/java" ] && unset JAVA_HOME


if [ -z "$JAVA_HOME" ]; then
      error_exit "Please set the JAVA_HOME variable in your environment, We need java(x64)! jdk8 or later is better!"
fi


export SERVER="product-support"

export JAVA_HOME
export JAVA="$JAVA_HOME/bin/java"
export BASE_DIR=`cd $(dirname $0)/..; pwd`
export DEFAULT_SEARCH_LOCATIONS="classpath:/,classpath:/config/,file:./,file:./config/"
export CUSTOM_SEARCH_LOCATIONS=${DEFAULT_SEARCH_LOCATIONS},file:${BASE_DIR}/conf/

#===========================================================================================
# JVM Configuration
#===========================================================================================
JAVA_OPT="${JAVA_OPT} -Xms1024m -Xmx1024m -Xmn512m"
#JAVA_OPT="${JAVA_OPT} -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n"
JAVA_OPT="${JAVA_OPT} -Djava.ext.dirs=${JAVA_HOME}/jre/lib/ext:${JAVA_HOME}/lib/ext:${BASE_DIR}/plugins/cmdb:${BASE_DIR}/plugins/mysql"


JAVA_OPT="${JAVA_OPT} -Dsystem.basedir=${BASE_DIR}"
JAVA_OPT="${JAVA_OPT} -Dloader.path=${BASE_DIR}/plugins/health -jar ${BASE_DIR}/target/${SERVER}.jar"
JAVA_OPT="${JAVA_OPT} ${JAVA_OPT_EXT}"
JAVA_OPT="${JAVA_OPT} --spring.config.location=${CUSTOM_SEARCH_LOCATIONS}"
JAVA_OPT="${JAVA_OPT} --logging.config=${BASE_DIR}/conf/log4j2.xml"
JAVA_OPT="${JAVA_OPT} --server.max-http-header-size=524288"

if [ ! -d "${BASE_DIR}/logs" ]; then
  mkdir ${BASE_DIR}/logs
fi

echo "$JAVA ${JAVA_OPT}"


echo "${SERVER} is starting ..."



# check the start.out log output file
#if [ ! -f "${BASE_DIR}/logs/start.out" ]; then
#  touch "${BASE_DIR}/logs/start.out"
#fi
# start
#echo "$JAVA ${JAVA_OPT}" > ${BASE_DIR}/logs/default.log 2>&1 &
#nohup $JAVA ${JAVA_OPT} sun.demo >> ${BASE_DIR}/logs/start.out 2>&1 &
nohup $JAVA ${JAVA_OPT} ${SERVER} >> /dev/null 2>&1 &
echo "${SERVER} is starting，you can check the ${BASE_DIR}/logs/default.log"
