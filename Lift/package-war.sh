#!/bin/bash

echo Packaging war file ...
`dirname $0`/sbt.sh --no-jrebel "$@" package-war
