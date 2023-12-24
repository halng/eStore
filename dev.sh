#!/bin/sh

docker compose -f docker-compose.yaml up -d  postgres redis swagger-ui auth ui-home ui-auth media 
