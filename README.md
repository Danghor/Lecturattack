# ![Lecturattack](src/resources/images/logo.png)

**_by_** ☕ **_Coffee Productions (TINF13AIBC/MF)_**

## Contributors

- Tim Adamek
- Andreas Geis
- Laura Hillenbrand
- Stefanie Raschke
- Tobias Raschke
- Nick Steyer

## Gameplay

![Gameplay Screenshot](gameplay.png)

## Building and Running

The game is plain Java built on [Slick2D](http://slick.ninjacave.com/)/LWJGL2. There is no
Maven/Gradle file, so `setup.sh` fetches the required jars (Slick2D, LWJGL 2.9.3, JAXB) from
Maven Central and compiles the sources; `run.sh` then launches the game:

```sh
./setup.sh   # downloads dependencies into libs/ and compiles into out/
./run.sh     # starts the game
```

Both scripts expect an x86_64 JDK 11 (e.g. Temurin 11) at
`/Library/Java/JavaVirtualMachines/temurin-11.jdk`. This is required even on Apple Silicon
Macs, since LWJGL2's native libraries only exist for x86_64/i386 — the x86_64 JDK runs
transparently under Rosetta 2. On Intel Macs, Linux, or Windows a matching native JDK works
the same way; just adjust the `JAVA`/`JAVAC` path at the top of each script. Java 11 is used
(rather than 8) because it's more readily available for x86_64 on modern machines; the scripts
pull in JAXB (`javax.xml.bind`) separately since it was removed from the JDK in Java 11.

---

_The Android robot is reproduced or modified from work created and shared by Google and used according to terms described in the Creative Commons 3.0 Attribution License._