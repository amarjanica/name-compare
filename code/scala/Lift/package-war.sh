#!/bin/bash

echo Packaging the WAR file ...
`dirname $0`/sbt.sh --no-jrebel "$@" package-war
