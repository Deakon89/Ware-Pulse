#!/bin/bash
chmod +x ./mvnw
./mvnw clean install -DskipTests -Dmaven.javadoc.skip=true -Dmaven.source.skip=true
