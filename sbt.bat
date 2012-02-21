@echo off
setlocal
pushd
cd "%~dp0"

set JVM_PARAMS=-Xss2m -Xmx1g -XX:MaxPermSize=256m -XX:+CMSClassUnloadingEnabled

set LIFT_RUN_MODE=-Drun.mode=development
set TRY_JREBEL=true
set LOG_LEVEL=
set NO_PAUSE=false
set DO_LOOP=false

:PARSER_LOOP
if "%~1"=="" goto :PARSER_END

if "%~1"=="--prod" (
  set LIFT_RUN_MODE=-Drun.mode=production
  goto :PARSER_CONTINUE
)

if "%~1"=="--debug" (
  set LOG_LEVEL="set logLevel:=Level.Debug"
  goto :PARSER_CONTINUE
)

if "%~1"=="~lift" (
  set SBT_PARAMS=%SBT_PARAMS% container:start ~compile container:stop
  set JREBEL_PLUGINS=%JREBEL_PLUGINS% -Drebel.lift_plugin=true
  goto :PARSER_CONTINUE
)

if "%~1"=="--no-jrebel" (
  set TRY_JREBEL=false
  goto :PARSER_CONTINUE
)

if "%~1"=="--loop" (
  set DO_LOOP=true
  goto :PARSER_CONTINUE
)

if "%~1"=="--no-pause" (
  set NO_PAUSE=true
  goto :PARSER_CONTINUE
)

set SBT_PARAMS=%SBT_PARAMS% %1

:PARSER_CONTINUE
shift
goto :PARSER_LOOP
:PARSER_END

set JVM_PARAMS=%JVM_PARAMS% %LIFT_RUN_MODE%
if %TRY_JREBEL%.==true. (
  if exist "%JREBEL_HOME%\jrebel.jar" set JVM_PARAMS=%JVM_PARAMS% -noverify -javaagent:"%JREBEL_HOME%\jrebel.jar" %JREBEL_PLUGINS%
)

set GRUJ_PATH=project\strap\gruj_vs_sbt-launch-0.11.2.jar
set RUN_CMD=java %JVM_PARAMS% -jar %GRUJ_PATH% %LOG_LEVEL% %SBT_PARAMS%

:RUN_LOOP
%RUN_CMD%

if %DO_LOOP%.==true. (
  if %NO_PAUSE%.==false. (
    echo Press Enter to continue or Press CTRL+C to exit!
    pause
  )
  goto :RUN_LOOP
)

popd
endlocal
