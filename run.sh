#!/bin/bash

CLASSPATH=build/classes/java/main
CLASSPATH=$CLASSPATH:build/classes/java/test
CLASSPATH=$CLASSPATH:src/main/resources
for LIB in $( "ls" -1 build/deps ); do
    CLASSPATH=$CLASSPATH:build/deps/$LIB
done

if [ "$1" == "--print-classpath" ]; then
    echo $CLASSPATH
    exit
fi

export PROBE_USER=admin
export PROBE_PASSWORD=elvis
export PISAID=herei

java -cp $CLASSPATH \
    -Dorg.apache.commons.logging.Log=org.apache.commons.logging.impl.Jdk14Logger \
    se.arbetsformedlingen.venice.Venice
