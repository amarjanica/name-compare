@echo off

echo Starting Lift, firing up Jetty ...
call "%~dp0\sbt.bat" --loop %* ~lift
