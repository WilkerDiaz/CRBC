DROP TABLE IF EXISTS CR.detallepromocion_temp;

CREATE TABLE CR.detallepromocion_temp AS (
	SELECT * FROM CR.detallepromocion
);

DROP TABLE IF EXISTS CR.detallepromocion_temp;