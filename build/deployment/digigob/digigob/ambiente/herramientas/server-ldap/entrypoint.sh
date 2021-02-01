#!/bin/bash

slappasswd -s secret  > /valPP.txt
paLdap=$(</valPP.txt)

sed -i "/olcRootDN/c olcRootDN: cn=Manager,dc=egoveris,dc=com" /etc/openldap/slapd.d/cn=config/olcDatabase={2}hdb.ldif
sed -i "/olcSuffix/c olcSuffix: dc=egoveris,dc=com" /etc/openldap/slapd.d/cn=config/olcDatabase={2}hdb.ldif
sed -i "/olcRootPW/c olcRootPW: $paLdap" /etc/openldap/slapd.d/cn=config/olcDatabase={2}hdb.ldif

