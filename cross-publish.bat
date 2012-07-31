@echo off

echo Will cross-publish project to the maven server
call "%~dp0sbt.bat" %* +clean +publish
