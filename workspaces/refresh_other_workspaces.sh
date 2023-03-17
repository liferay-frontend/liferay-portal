#!/bin/bash

function main {
	for dir in "./"*
	do
		if [ ${dir} = "./liferay-sample-workspace" ] ||
		   [ -f ${dir} ]
		then
			continue
		fi

		rsync -a --delete --exclude "client-extensions" --exclude "modules" --exclude "themes" liferay-sample-workspace/ ${dir}
	done
}

main "${@}"