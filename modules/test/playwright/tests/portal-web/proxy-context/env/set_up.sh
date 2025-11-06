#!/bin/bash

source $(dirname ${BASH_SOURCE[0]})/../../../../env/common.sh

default_set_up down

(
cd ${LIFERAY_HOME}/tomcat-*

mv webapps/ROOT webapps/dxp
mv work/Catalina/localhost/ROOT work/Catalina/localhost/dxp
mv conf/Catalina/localhost/ROOT.xml conf/Catalina/localhost/dxp.xml
sed -i 's/<JarScanner[^>]*>//g' conf/Catalina/localhost/dxp.xml
)

start_app_server ${LIFERAY_HOME} ${LIFERAY_PORTAL_URL}/dxp