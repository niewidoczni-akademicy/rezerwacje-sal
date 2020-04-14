#!/bin/bash

ENV_FILE=".env"
ENV_EXAMPLE_FILE=".example.env"

if ! [[ -f ${ENV_FILE} ]]
then
    echo "Creating ${ENV_FILE} file"
    cp ${ENV_EXAMPLE_FILE} ${ENV_FILE}
else
	required=$(awk -F '=' '{if($2) print $1}' ${ENV_EXAMPLE_FILE} | xargs)
	current=$(awk -F '=' '{if($2) print $1}' ${ENV_FILE} | xargs)
	for variable in ${required}
	do
		if [[ ! " ${current[@]} " =~ " ${variable} " ]]; then
			echo >&2 "${variable} NOT FOUND IN ${ENV_FILE}, delete it to use ${ENV_EXAMPLE_FILE} or fix missing fields";
			exit 1;
		fi
	done
fi

docker-compose up --build "$@"
