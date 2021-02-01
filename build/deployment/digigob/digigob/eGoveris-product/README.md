Initial version of eGoveris Product Maven Project

* This repository contains only 2 files:

** pom-master-config.xml This file contains all the dependency versions
used in the different modules with the purpose of unifying the version
when possible. It will always be possible to overwrite the version on the
child pom.xml of the corresponding subproject.

** pom-modules.xml This file barely contains the list of modules that
conforms the eGoveris product.

This 2 files allows us to manage the whole project in an easy and scallable way.

We are able to update all the modules versions with a single maven goal:

mvn release:clean release:update-version -U -f pom-modules.xml -DautoVersionSubmodules

or we can force a specific version for all the modules:

mvn release:clean release:update-versions -U -f pom-modules.xml -DdevelopmentVersion=X.X.X-SNAPSHOT

It is important to note that we need to specify the node:
<scm>
	<connection>scm:git:git@7.214.8.38/egoveris/repository-to-connect.git</connection>
	<developerConnection>scm:git:git@7.214.8.38/egoveris/repository-to-connect.git</developerConnection>
	<tag>HEAD</tag>
</scm>
for all the projects involved, otherwise we will found and error. 

We can even create branches or releases with the maven release goals.