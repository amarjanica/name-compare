@echo off
setlocal

if %1.==. set DEFAULT=shell
call "%~dp0..\sbt.bat" "project Name-Compare" %DEFAULT% %*
