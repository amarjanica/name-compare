#!/bin/bash
cd `dirname $0`

## START JVM PARAMS
JVM_PARAMS="-Xss2m -Xmx712m -XX:MaxPermSize=256m -XX:+CMSClassUnloadingEnabled"

LIFT_RUN_MODE="-Drun.mode=development"
TRY_JREBEL=true
LOG_LEVEL=
NO_PAUSE=false
DO_LOOP=false

while [ -n "$*" ]
do
  case "$1" in
    "--debug")
      echo "Setting debug mode"
      LOG_LEVEL="\"set logLevel:=Level.Debug\""
      ;;
    "--prod")
      echo "Set Lift mode to Production"
      LIFT_RUN_MODE="-Drun.mode=production"
      ;;
    "~lift")
      echo "Firing up Jetty ..."
      SBT_PARAMS="$SBT_PARAMS container:start ~compile container:stop"
      JREBEL_PLUGINS="$JREBEL_PLUGINS -Drebel.lift_plugin=true"
      ;;
    "--no-jrebel")
      echo "Disabling JRebel for faster compilation"
      TRY_JREBEL=false
      ;;
    "--loop")
      echo "Will run SBT in loop mode"
      DO_LOOP=true
      ;;
    "--no-pause")
      echo "Will not pause in loop mode"
      NO_PAUSE=true
      ;;
    *)
      SBT_PARAMS="$SBT_PARAMS \"$1\""
      ;;
  esac
  shift

done

JVM_PARAMS="$JVM_PARAMS $LIFT_RUN_MODE"
if $TRY_JREBEL && [ -n "$JREBEL_HOME" ] && [ -f $JREBEL_HOME/jrebel.jar ]; then
  JVM_PARAMS="$JVM_PARAMS -noverify -javaagent:$JREBEL_HOME/jrebel.jar $JREBEL_PLUGINS"
fi

GRUJ_PATH="project/strap/gruj_vs_sbt-launch-0.11.2.jar"
RUN_CMD="java $JVM_PARAMS -jar $GRUJ_PATH $LOG_LEVEL $SBT_PARAMS"

LOOPING=true
while $LOOPING
do
  eval "$RUN_CMD"

  if ! $DO_LOOP ; then
    LOOPING=false
  else
    if ! $NO_PAUSE ; then
      echo "Press Enter to continue or Press CTRL+C to exit!"
      read
    fi
  fi
done
