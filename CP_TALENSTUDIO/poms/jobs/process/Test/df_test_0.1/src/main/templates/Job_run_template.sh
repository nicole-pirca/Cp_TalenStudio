#!/bin/sh
cd `dirname $0`
ROOT_PATH=`pwd`
java -Dtalend.component.manager.m2.repository=$ROOT_PATH/../lib -Xms256M -Xmx1024M -Dfile.encoding=UTF-8 -cp .:$ROOT_PATH:$ROOT_PATH/../lib/routines.jar:$ROOT_PATH/../lib/log4j-slf4j-impl-2.17.1.jar:$ROOT_PATH/../lib/log4j-api-2.17.1.jar:$ROOT_PATH/../lib/log4j-core-2.17.1.jar:$ROOT_PATH/../lib/log4j-layout-template-json-2.17.1.jar:$ROOT_PATH/../lib/talend_file_enhanced-1.3.1.jar:$ROOT_PATH/../lib/jboss-marshalling-2.0.12.Final.jar:$ROOT_PATH/../lib/json-smart-2.4.11.jar:$ROOT_PATH/../lib/javassist-3.30.2-GA.jar:$ROOT_PATH/../lib/dom4j-2.1.3.jar:$ROOT_PATH/../lib/slf4j-api-1.7.34.jar:$ROOT_PATH/../lib/job-audit-1.5.jar:$ROOT_PATH/../lib/system-routines-dq.jar:$ROOT_PATH/../lib/accessors-smart-2.4.11.jar:$ROOT_PATH/../lib/antlr-runtime-3.5.2.jar:$ROOT_PATH/../lib/talendagent-1.0.2.jar:$ROOT_PATH/../lib/audit-log4j2-1.16.1.jar:$ROOT_PATH/../lib/logging-event-layout-1.16.1.jar:$ROOT_PATH/../lib/org.talend.dataquality.parser.jar:$ROOT_PATH/../lib/commons-lang3-3.10.jar:$ROOT_PATH/../lib/talendboot-1.0.9.jar:$ROOT_PATH/../lib/asm-9.5.jar:$ROOT_PATH/../lib/system-routines.jar:$ROOT_PATH/../lib/crypto-utils-7.1.20.jar:$ROOT_PATH/../lib/talendCBP-1.1.5.jar:$ROOT_PATH/../lib/audit-common-1.16.1.jar:$ROOT_PATH/df_test_0_1.jar: cp_talenstudio.df_test_0_1.DF_TEST "$@"
