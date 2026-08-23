#!/bin/bash
# Launches the game. Must run under an x86_64 JVM (via Rosetta) because the
# LWJGL2 native libraries used by Slick2D have no Apple Silicon build.
set -e
cd "$(dirname "$0")"

JAVA=/Library/Java/JavaVirtualMachines/temurin-11.jdk/Contents/Home/bin/java
if [ ! -x "$JAVA" ]; then
  echo "Expected an x86_64 JDK 11 (Temurin) at $JAVA — see README notes / ask for setup help." >&2
  exit 1
fi
if [ ! -d out/Lecturattack ]; then
  echo "Not built yet — run ./setup.sh first." >&2
  exit 1
fi

exec "$JAVA" -Djava.library.path=libs/natives -cp "out:libs/*" Lecturattack.statemachine.Lecturattack
