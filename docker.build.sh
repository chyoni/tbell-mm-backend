#!/bin/bash

git pull origin master

docker compose -f ./docker-compose.yml down

./gradlew bootJar

docker compose -f ./docker-compose.yml -p tbell-mm up --build -d