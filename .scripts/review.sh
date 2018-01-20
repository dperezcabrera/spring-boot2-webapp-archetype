#! /bin/bash

TASK=review
PROJECT=test

mvn clean install

if [ ! -d ".temp" ]; then
   mkdir ".temp"
fi

cd ".temp"

if [ ! -d "$TASK" ]; then
   mkdir "$TASK"
fi

cd "$TASK"

# Script
if [ -d "$PROJECT" ]; then
    rm -rf "$PROJECT"
fi

mvn -DarchetypeGroupId=com.github.dperezcabrera -DarchetypeArtifactId=spring-boot2-webapp-archetype -DarchetypeVersion=1.0.0-SNAPSHOT -DarchetypeRepository=local -DgroupId=com.github.dperezcabrera -DartifactId=$PROJECT -Dversion=1.0.0-SNAPSHOT -Dpackage=com.github.dperezcabrera.test -Dbasedir=/home/dperezcabrera/NetBeansProjects/spring-boot-webapp-archetype/.Temp -Darchetype.interactive=false --batch-mode --update-snapshots archetype:generate

cd "$PROJECT"

git init
git add -A
git commit -m "First commit"
