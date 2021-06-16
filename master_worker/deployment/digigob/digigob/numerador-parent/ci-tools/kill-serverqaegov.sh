#!/bin/bash

kill $( ps -fea | grep java | grep home=/data/qaegov/$1 | awk "{print \$2}" )