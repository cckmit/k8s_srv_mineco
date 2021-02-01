#!/bin/bash

#USE: build egoveris 0.1.0

docker build -t apache-$1:$2 .
