#!/bin/bash
chmod +x ./mvnw
./mvnw clean install -DskipTests -Dmaven.test.skip=true
