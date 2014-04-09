DROP TABLE IF EXISTS CR.productoseccion_temp;
CREATE TABLE CR.productoseccion_temp AS (
	SELECT * FROM CR.productoseccion
);
DROP TABLE IF EXISTS CR.productoseccion_temp;