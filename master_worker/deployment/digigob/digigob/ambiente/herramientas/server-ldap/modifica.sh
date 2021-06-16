#!/bin/bash


	docker exec -ti ldap-$1-$2 /entrypoint.sh
	docker restart ldap-$1-$2
	sleep 10s
	docker exec ldap-$1-$2 /bin/bash -c "/addUser.sh"
