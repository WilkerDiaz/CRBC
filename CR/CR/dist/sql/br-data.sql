-- Queries a ejecutar en AS400, nuevos datos para Caja Principal
INSERT INTO ICTFILE.CAPM02 VALUES ('IFA', 7, 'VENTAS BONOS REGALO ELECTRONICO', '','');
INSERT INTO ICTFILE.CAPM02 VALUES ('EFA', 5, 'ANULACION DE BONOS REGALO ELECTRONICO', '','');

-- Datos para Caja Registradora
INSERT INTO cr.formadepago (codformadepago, tipoformadepago, nombre, codbanco, indicarbanco, indicarnumdocumento, indicarnumcuenta, indicarnumconforma, indicarnumreferencia, indicarcedulatitular, validarsaldocliente, montominimo, montomaximo, montocomision, permitevuelto, entregaparcial, requiereautorizacion, prioridad, regvigente) VALUES ('22', 6, 'Bono Regalo Electrónico', NULL, 'N', 'S', 'N', 'N', 'N', 'N', 'N', 0.00, 99999999999.00, 0.00, 'N', 'N', 'N', 5, 'S');
INSERT INTO cr.puntoagilformadepago (codformadepago, permitePuntoAgil, requiereLecturaBanda, imprimirVoucher, indicarPlan, indicarCuentaEspecial, indicarTipoCuenta, tipoTarjetaPuntoAgil, longitudCodigoSeguridad, fechaActualizacion) VALUES ('22', 'S', 'S', 'S', 'N', 'S', 'N', 'B', NULL, '20100914162037');

INSERT INTO cr.modulos (codmodulo, descripcion, regvigente) VALUES (52, 'Bono Regalo', 'S');

INSERT INTO cr.metodos VALUES (105,'OBTENERPAGOOPERACION');
INSERT INTO cr.metodos (codmetodo,descripcion) VALUES (106,'CONSULTARSALDOBR');
INSERT INTO cr.metodos (codmetodo,descripcion) VALUES (107,'CARGARECARGASALDOBR');
INSERT INTO cr.metodos (codmetodo,descripcion) VALUES (108,'CREARVENTABR');
INSERT INTO cr.metodos (codmetodo,descripcion) VALUES (109,'RECUPERARVENTABR');
INSERT INTO cr.metodos (codmetodo,descripcion) VALUES (110,'DESBLOQUEARTRANSACCIONBR');
INSERT INTO cr.metodos (codmetodo,descripcion) VALUES (111,'ANULARVENTABR');

INSERT INTO cr.br_opcionhabilitada (codmetodo, nombreopcion, rutaimagen, tecla, orden) VALUES (105, 'CompraCards', 'C:/ImagenesVpos/CardsCompra.JPG', '0', 0);
INSERT INTO cr.br_opcionhabilitada (codmetodo, nombreopcion, rutaimagen, tecla, orden) VALUES (107, 'CargaRecargaSaldoBR', 'C:/ImagenesVpos/CardsRecarga.JPG', '0', 0);
INSERT INTO cr.br_opcionhabilitada (codmetodo, nombreopcion, rutaimagen, tecla, orden) VALUES (106, 'ConsultaCards', 'C:/ImagenesVpos/CardsConsulta.JPG', '0',0);

INSERT INTO cr.funcion (codfuncion, descripcion, codmodulo, nivelauditoria, regvigente, reqautorizacion) VALUES (93,'Consultar saldo Bono Regalo', 52, 3, 'S', 'N');
INSERT INTO cr.funcion (codfuncion, descripcion, codmodulo, nivelauditoria, regvigente, reqautorizacion) VALUES (94,'Carga/Recarga de saldo Bono Regalo', 52, 3, 'S', 'N');
INSERT INTO cr.funcion (codfuncion, descripcion, codmodulo, nivelauditoria, regvigente, reqautorizacion) VALUES (95,'Consultar transaccion de Bono Regalo', 52,3,'S','N');
INSERT INTO cr.funcion (codfuncion, descripcion, codmodulo, nivelauditoria, regvigente, reqautorizacion) VALUES (96,'Anular transacción de Bono Regalo', 52,3,'S','S');

INSERT INTO cr.funcionmetodos (codmodulo, codfuncion, codmetodo) VALUES (12, 21, 105); -- funcion 21 es efectuar pago, modulo 12 es pagos
INSERT INTO cr.funcionmetodos (codmodulo, codfuncion, codmetodo) VALUES (52,93,106);
INSERT INTO cr.funcionmetodos (codmodulo, codfuncion, codmetodo) VALUES (52,94,107);
INSERT INTO cr.funcionmetodos (codmodulo, codfuncion, codmetodo) VALUES (52,94,108);
INSERT INTO cr.funcionmetodos (codmodulo, codfuncion, codmetodo) VALUES (52,95,109);
INSERT INTO cr.funcionmetodos (codmodulo, codfuncion, codmetodo) VALUES (52,95,110);
INSERT INTO cr.funcionmetodos (codmodulo, codfuncion, codmetodo) VALUES (52,96,111);

INSERT INTO cr.perfil (codperfil, descripcion, nivelauditoria) VALUES(5,'TESORERIA', 5);

INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (1,93,'S', 'S', 52);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (2,93,'S', 'S', 52);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (3,93,'S', 'S', 52);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (4,93,'S', 'S', 52);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (5,93,'S', 'S', 52);

INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (1,94,'S', 'N', 52);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (2,94,'S', 'S', 52);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (3,94,'S', 'N', 52);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (4,94,'S', 'S', 52);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (5,94,'S', 'S', 52);

INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (1,95,'S', 'N', 52);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (2,95,'S', 'S', 52);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (3,95,'S', 'N', 52);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (4,95,'S', 'S', 52);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (5,95,'S', 'S', 52);

INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (1,96,'S', 'N', 52);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (2,96,'S', 'N', 52);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (3,96,'S', 'S', 52);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (4,96,'S', 'N', 52);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (5,96,'S', 'N', 52);

INSERT INTO cr.estadodecaja(idestado,descripcion) VALUES (24,'Facturando Bonos Regalo');

INSERT INTO cr.maquinadeestado(edoinicial,codmetodo,edofinal) VALUES (24,107,6);
INSERT INTO cr.maquinadeestado(edoinicial,codmetodo,edofinal) VALUES (6,108,24);
INSERT INTO cr.maquinadeestado(edoinicial,codmetodo,edofinal) VALUES (6,109,24);
INSERT INTO cr.maquinadeestado(edoinicial,codmetodo,edofinal) VALUES (24,110,6);
INSERT INTO cr.maquinadeestado(edoinicial,codmetodo,edofinal) VALUES (24,111,24);
INSERT INTO cr.maquinadeestado(edoinicial,codmetodo,edofinal) VALUES (24,10004,24);


-- Autorizacion de usuarios con perfil 5 (tesoreria)
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (5,41,'S', 'S', 22);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (5,6,'S', 'S', 1);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (5,20,'S', 'S', 1);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (5,70,'S', 'S', 22);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (5,42,'S', 'S', 22);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (5,52,'S', 'S', 22);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (5,21,'S', 'S', 12);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (5,53,'S', 'S', 22);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (5,26,'S', 'S', 11);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (5,2,'S', 'S', 1);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (5,2,'S', 'S', 11);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (5,19,'S', 'S', 18);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (5,49,'S', 'S', 11);
INSERT INTO cr.funcionperfil(codperfil, codfuncion, habilitado, autorizado, codmodulo) VALUES (5,54,'S', 'S', 22);

insert into cr.br_condiciones (1,'- Valida al portador solo para la com-', 'S');
insert into cr.br_condiciones (2,'  pra de productos en las Tiendas', 'S');
insert into cr.br_condiciones (3,'  BECO', 'S');
insert into cr.br_condiciones (4,'- En ningun caso se cambiara la canti-', 'S');
insert into cr.br_condiciones (5,'  dad acreditada en la tarjeta por', 'S');
insert into cr.br_condiciones (6,'  dinero efectivo', 'S');
insert into cr.br_condiciones (7,'- El emisor no es responsable por', 'S');
insert into cr.br_condiciones (8,'  robo, hurto, perdida o daño de la', 'S');
insert into cr.br_condiciones (9,'  tarjeta', 'S');
insert into cr.br_condiciones (10,'- Su adquisicion implica el conoci-', 'S');
insert into cr.br_condiciones (11,'  miento y aceptacion de las condicio-', 'S');
insert into cr.br_condiciones (11,'  nes establecidas para su uso', 'S');


-- este query debe correrse solo en cada caja (no en los servidores)
--INSERT INTO puntoAgilProcesoEstadoCaja (idestado,tipoproceso) values(24,1);
