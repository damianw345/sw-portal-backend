#!/usr/bin/env bash

JAVA_HOME=/usr/lib/jvm/java-11-oracle

./gradlew --info clean build &&
  cp build/libs/sw-portal-backend-1.0.0-SNAPSHOT.jar ./docker &&
  docker build -t sw-portal-backend:build ./docker
