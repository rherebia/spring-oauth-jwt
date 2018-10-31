#!/bin/sh

mvn clean package -DskipTests

java -jar target/*.jar
