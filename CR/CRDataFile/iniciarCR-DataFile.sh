#!/bin/bash 
clear
rm -rf /var/lock/LCK*
export LANG=es_VE

# en que directorio estamos?
CURRENT_DIR=`pwd`
echo "El directorio Actual de Trabajo es: " $CURRENT_DIR
#directorio de aplicaciones


CR1_WORKING_DIR=$CR1_HOME
cd $CR1_WORKING_DIR
CR1_LIBS_DIR=$CR1_WORKING_DIR"/jars"
#CR1_BIN_DIR=$CR1_WORKING_DIR"/jars"
CLASSPATH="."


echo "La CR está instalada en: " $CR1_WORKING_DIR
echo "El directorio que contiene todas las librerias es: " $CR1_LIBS_DIR
echo "El directorio que contiene la CR es: " $CR1_BIN_DIR
echo "Java está instalada en: " $JAVA_HOME

for x in `find $CR1_LIBS_DIR/*.jar` ; do
	echo "Agregando la libreria al CLASSPATH:  " $x
	CLASSPATH=$CLASSPATH:$x
done

#for y in `find $CR1_BIN_DIR/*.jar` ; do
#	echo "Agregando la libreria al CLASSPATH:  " $y
#        CLASSPATH=$CLASSPATH:$y
#done

java  -classpath $CLASSPATH com.epa.sincronizador.Principal client CR.prodcodigoexterno.txt.gz

rm -rf /var/lock/LCK*