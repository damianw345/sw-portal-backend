#!/usr/bin/env bash

JAVA_OPTIONS="-agentlib:jdwp=transport=dt_socket,address=*:5005,server=y,suspend=n -Djava.security.egd=file:/dev/./urandom"

until nc -z sw-portal-mongodb 27017; do
  echo "Waiting for MongoDB to get up..."
  sleep 1
done

java ${JAVA_OPTIONS} -jar "sw-portal-backend.jar"
