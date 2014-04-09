#!/bin/bash 
clear
rm -rf /var/lock/LCK..ttyS*

export LANG=es_VE

# en que directorio estamos?
CURRENT_DIR="/opt/CR"
echo "El directorio Actual de Trabajo es: " $CURRENT_DIR
#directorio de aplicaciones


CR1_WORKING_DIR=$CURRENT_DIR
cd $CR1_WORKING_DIR
CR1_LIBS_DIR=$CR1_WORKING_DIR"/jars"
CLASSPATH="."


echo "La CR está instalada en: " $CR1_WORKING_DIR
echo "El directorio que contiene todas las librerias es: " $CR1_LIBS_DIR
echo "El directorio que contiene la CR es: " $CR1_BIN_DIR
echo "Java está instalada en: " $JAVA_HOME

for x in `find $CR1_LIBS_DIR/*.jar` ; do
	echo "Agregando la libreria al CLASSPATH:  " $x
	CLASSPATH=$CLASSPATH:$x
done

java  -classpath $CLASSPATH com.visualizador.Auditoria



