@echo off

echo Packaging the WAR file ...
call "%~dp0\sbt.bat" --no-jrebel %* package-war
