#!/bin/bash

echo Will cross-publish project to the maven server
`dirname $0`/sbt.sh --no-jrebel "$@" clean +publish
