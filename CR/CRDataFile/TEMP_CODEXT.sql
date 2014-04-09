DROP TABLE IF EXISTS CR.prodcodigoexterno_temp;

CREATE TABLE CR.prodcodigoexterno_temp AS (
	SELECT * FROM CR.prodcodigoexterno
);