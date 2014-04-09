/* Especifique una o varias sentencias de SQL separadas por signos de punto y coma */
DROP TABLE IF EXISTS CR.afiliado_temp1;
CREATE TABLE CR.afiliado_temp1 AS (
	SELECT * FROM CR.afiliado
);
DROP TABLE IF EXISTS CR.afiliado_temp1;
