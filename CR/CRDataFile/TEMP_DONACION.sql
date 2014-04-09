DROP TABLE IF EXISTS CR.donacion_temp;
CREATE TABLE CR.donacion_temp AS (
	SELECT * FROM CR.donacion
);
DROP TABLE IF EXISTS CR.donacion_temp;