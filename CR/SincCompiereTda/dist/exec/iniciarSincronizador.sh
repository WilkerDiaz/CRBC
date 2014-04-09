#directorio de aplicaciones
CURRENT_DIR="/opt/SincCompiereServTienda"
CR1_WORKING_DIR=$CURRENT_DIR
cd $CR1_WORKING_DIR
CR1_LIBS_DIR=$CR1_WORKING_DIR"/jars"
CLASSPATH="."

for x in `find $CR1_LIBS_DIR/*.jar` ; do
	echo "Agregando la libreria al CLASSPATH:  " $x
	CLASSPATH=$CLASSPATH:$x
done

java -classpath $CLASSPATH com.beco.sinccompieretda.controlador.PrincipalSinc

