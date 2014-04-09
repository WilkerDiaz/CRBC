# Deshabilitar opción F4 para Gtes.

UPDATE funcionperfil SET
	habilitado='N', actualizacion=current_timestamp
WHERE
	codperfil=3 AND
	((codfuncion=23 AND codmodulo=11) OR
	(codfuncion=35 AND codmodulo=46) OR
	(codfuncion=74 AND codmodulo=49) OR
	(codfuncion=28 AND codmodulo=11) OR
	(codfuncion=37 AND codmodulo=47) OR
	(codfuncion=36 AND codmodulo=47) OR
	(codfuncion=71 AND codmodulo=49) OR
	(codfuncion=72 AND codmodulo=49) OR
	(codfuncion=79 AND codmodulo=47) OR
	(codfuncion=31 AND codmodulo=45) OR
	(codfuncion=82 AND codmodulo=45));

# Agregar transición para anulación de ventas en Consulta de Lista modo titular
INSERT INTO maquinadeestado VALUES (22, 18, 22);

# Agregar funcionalidad de transferencia inmediata a la caja

insert into funcion values (92,'Transferencia Inmediata',22,3,'S','20100416133000','N');
insert into metodos values (104,'TRANSFERIRPRODUCTO');
insert into funcionmetodos values (22,92,104);
insert into funcionperfil values (4,92,'S','S',20100416041530,22);
insert into funcionperfil values (3,92,'S','S',20100416041530,22);
insert into funcionperfil values (2,92,'S','S',20100416041530,22);
insert into funcionperfil values (1,92,'S','S',20100416041530,22);