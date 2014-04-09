#!/bin/bash 

# en que directorio estamos?
CURRENT_DIR="/opt/SorteoTransaccionPremiada"
echo "El directorio Actual de Trabajo es: " $CURRENT_DIR

#directorio de aplicaciones
CR1_WORKING_DIR=$CURRENT_DIR
cd $CR1_WORKING_DIR
CR1_LIBS_DIR=$CR1_WORKING_DIR"/jars"
CLASSPATH="."

for x in `find $CR1_LIBS_DIR/*.jar` ; do
	echo "Agregando la libreria al CLASSPATH:  " $x
	CLASSPATH=$CLASSPATH:$x
done

java -classpath $CLASSPATH com.beco.cr.sorteotrx.controlador.PrincipalSorteo $1

