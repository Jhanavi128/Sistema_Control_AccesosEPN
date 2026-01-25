#!/bin/bash
# Compilar por si acaso
# javac -d bin -cp "lib/sqlite-jdbc.jar:lib/slf4j-api.jar:lib/slf4j-simple.jar" src/Web/Server/*.java src/App.java

# Ejecutar Servidor Web
java -cp "bin:lib/sqlite-jdbc.jar:lib/slf4j-api.jar:lib/slf4j-simple.jar" App
