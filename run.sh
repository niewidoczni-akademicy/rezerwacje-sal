#!/bin/bash

mkdir -p rezerwacje-db/data

export CURRENT_UID=$(id -u):$(id -g)
docker-compose up "$@"

