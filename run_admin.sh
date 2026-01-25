#!/bin/bash
# Compilar por si acaso (opcional, pero util durante desarrollo)
# javac -d bin -cp "lib/sqlite-jdbc.jar:lib/slf4j-api.jar:lib/slf4j-simple.jar" src/Data/*.java src/Desktop/*.java

# Ejecutar Panel Administrativo
java -cp "bin:lib/sqlite-jdbc.jar:lib/slf4j-api.jar:lib/slf4j-simple.jar" Desktop.AdminPanel
