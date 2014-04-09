DROP TABLE IF EXISTS CR.detallepromocionext_temp;
CREATE TABLE CR.detallepromocionext_temp AS (
	SELECT * FROM CR.detallepromocionext
);
DROP TABLE IF EXISTS CR.detallepromocionext_temp;