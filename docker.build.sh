#!/bin/bash

git pull origin master

docker stop tbell-mm-backend

docker compose -f ./docker-compose.yml down

./gradlew clean bootJar

docker compose -f ./docker-compose.yml -p tbell-mm up --build -d