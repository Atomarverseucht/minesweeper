#!/bin/sh

JDK_DIR="/usr/lib/jvm"
JDK21_PATH=""

echo "Searching Java 21 in $JDK_DIR..."

for i in "$JDK_DIR"/*; do
  if [ -x "$i/bin/java" ]; then
    JAVA_VERSION=$("$i/bin/java" -version 2>&1 | head -n 1 | grep -oE '[0-9]+' | head -n 1)

    if [ "$JAVA_VERSION" = "21" ]; then
      JDK21_PATH="$i"
      echo "$JDK21_PATH"
      break
    fi
  fi
done

RESET=$(printf '\e[0m')
RED=$(printf '\e[31m')
if [ -z "$JDK21_PATH" ]; then
  echo "${RED}ERROR:${RESET} No Java 21 installation found in $JDK_DIR!"
  exit 1
fi

echo "Java 21 found at: $JDK21_PATH"
#export JAVA_HOME="$JDK21_PATH"
#export PATH="$JAVA_HOME/bin:$PATH"

java -version

sbt clean coverage test coverageReport run

