#!/bin/sh

if [[ !("$1" != *"fix:"*) || !("$1" != *"init:"*) || !("$1" == *"feat:"*) || !("$1" != *"enhance:"*) ]];
then 
    echo "Commit message have to include fix, feat, init, enhance!"
    exit 1
fi