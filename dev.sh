#!/bin/sh

docker compose -f docker-compose.yaml up -d redis postgres auth ui-home ui-auth media api 
