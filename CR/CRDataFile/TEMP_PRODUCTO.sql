DROP TABLE IF EXISTS CR.producto_temp;

CREATE TABLE CR.producto_temp AS (
	SELECT * FROM CR.producto
);