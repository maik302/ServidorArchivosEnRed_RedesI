# compilacion_g50
#
# Archivo de compilacion y distribucion de los archivos necesarios para la
# ejecucion conjunta del sistema cliente-servidor de acceso a archivos remotos.
# @ Grupo 50
# @ Autores: 
#   Michael Woo 09-10912
#   Luis Esparragoza 08-10337
#!/bin/bash

javac *.java
rmic a_rmifs_implementacion
rmic s_rmifs_implementacion
cp *.class cliente/ && cp *.class servidor_archivos/ && cp *.class servidor_autenticacion/
javac cliente/*.java
javac servidor_archivos/*.java
javac servidor_autenticacion/*.java
