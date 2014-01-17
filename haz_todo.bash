#!/bin/bash
javac *.java
rmic a_rmifs_implementacion
rmic s_rmifs_implementacion
./mover_archivos.bash
javac cliente/*.java
javac servidor_archivos/*.java
javac servidor_autenticacion/*.java
