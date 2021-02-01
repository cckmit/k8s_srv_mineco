#!/bin/bash

kill $( ps -fea | grep java | grep home=/data/qatrade/$1 | awk "{print \$2}" )