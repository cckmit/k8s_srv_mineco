#!/bin/bash

#USE: build egoveris 1.0.0

docker build -t solr-$1:$2 .
