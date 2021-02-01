#!/bin/bash

kill $( ps -fea | grep java | grep home=/egoveris/$1 | awk "{print \$2}" )