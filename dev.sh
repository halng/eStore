#!/bin/sh

docker compose -f docker-compose.yaml up -d kafka akhq  postgres redis swagger-ui
