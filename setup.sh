#!/bin/bash
# Downloads the Slick2D/LWJGL2 dependencies (not committed to git, see .gitignore)
# and compiles the game into out/. Run this once (or again after pulling source changes).
set -e
cd "$(dirname "$0")"

JAVAC=/Library/Java/JavaVirtualMachines/temurin-11.jdk/Contents/Home/bin/javac
if [ ! -x "$JAVAC" ]; then
  echo "Expected an x86_64 JDK 11 (Temurin) at $JAVAC — see README notes / ask for setup help." >&2
  exit 1
fi

mkdir -p libs libs/natives out

BASE=https://repo1.maven.org/maven2
declare -a jars=(
  "org/slick2d/slick2d-core/1.0.2/slick2d-core-1.0.2.jar"
  "org/lwjgl/lwjgl/lwjgl/2.9.3/lwjgl-2.9.3.jar"
  "org/lwjgl/lwjgl/lwjgl_util/2.9.3/lwjgl_util-2.9.3.jar"
  "org/lwjgl/lwjgl/lwjgl-platform/2.9.3/lwjgl-platform-2.9.3-natives-osx.jar"
  "org/jcraft/jorbis/0.0.17/jorbis-0.0.17.jar"
  "jakarta/xml/bind/jakarta.xml.bind-api/2.3.3/jakarta.xml.bind-api-2.3.3.jar"
  "org/glassfish/jaxb/jaxb-runtime/2.3.3/jaxb-runtime-2.3.3.jar"
  "org/glassfish/jaxb/txw2/2.3.3/txw2-2.3.3.jar"
  "jakarta/activation/jakarta.activation-api/1.2.2/jakarta.activation-api-1.2.2.jar"
  "com/sun/istack/istack-commons-runtime/3.0.11/istack-commons-runtime-3.0.11.jar"
  "com/sun/xml/fastinfoset/FastInfoset/1.2.16/FastInfoset-1.2.16.jar"
)

for j in "${jars[@]}"; do
  fname=$(basename "$j")
  if [ ! -f "libs/$fname" ]; then
    echo "Downloading $fname"
    curl -sf -o "libs/$fname" "$BASE/$j"
  fi
done

if [ ! -f libs/natives/liblwjgl.dylib ]; then
  (cd libs/natives && jar xf ../lwjgl-platform-2.9.3-natives-osx.jar)
fi

echo "Compiling..."
find src/Lecturattack -name "*.java" ! -path "*LecturattackTest*" > /tmp/lecturattack_sources.txt
"$JAVAC" -encoding UTF-8 -cp "libs/*" -d out @/tmp/lecturattack_sources.txt

mkdir -p out/resources
cp -R src/resources/* out/resources/

echo "Build complete. Run ./run.sh to start the game."
