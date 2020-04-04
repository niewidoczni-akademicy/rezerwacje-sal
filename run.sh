#!/bin/bash

if ! [[ -f ".env" ]] 
then
    echo "Creating .env file"
    cp .example.env .env
fi

mkdir -p rezerwacje-db/data

export CURRENT_UID=$(id -u):$(id -g)
docker-compose up "$@"

