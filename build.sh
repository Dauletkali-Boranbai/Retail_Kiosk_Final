#!/bin/sh
# Requires JDK 17+. Compiles to ./out and runs Main.
mkdir -p out
javac --release 17 -d out $(find src -name '*.java') && java -cp out Main
