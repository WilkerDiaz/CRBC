CREATE TABLE `br_transaccion` (
  `numtienda` smallint(6) NOT NULL default '0',
  `fecha` date NOT NULL default '0000-00-00',
  `numcaja` smallint(3) NOT NULL default '0',
  `numtransaccion` int(11) NOT NULL default '0',
  `tipotransaccion` char(1) NOT NULL default '',
  `horainicia` time NOT NULL default '00:00:00',
  `horafinaliza` time NOT NULL default '00:00:00',
  `codcliente` varchar(12) default NULL,
  `codcajero` varchar(8) NOT NULL default '',
  `montobase` decimal(13,2) NOT NULL default '0.00',
  `montoimpuesto` decimal(13,2) NOT NULL default '0.00',
  `vueltocliente` decimal(13,2) NOT NULL default '0.00',
  `montoremanente` decimal(13,2) NOT NULL default '0.00',
  `lineasfacturacion` smallint(6) NOT NULL default '0',
  `cajaenlinea` char(1) NOT NULL default 'S',
  `duracionventa` smallint(6) NOT NULL default '0',
  `duracionpago` smallint(6) NOT NULL default '0',
  `estadotransaccion` char(1) NOT NULL default '',
  `regactualizado` char(1) NOT NULL default 'N',
  `codautorizante` varchar(8) default NULL,
  `codvendedor` varchar(8) default NULL,
  `fechaanulacion` date default '0000-00-00',
  PRIMARY KEY  (`numtienda`,`fecha`,`numcaja`,`numtransaccion`),
  UNIQUE KEY `idx_transaccion_br` (`numtienda`,`fecha`,`numcaja`,`numtransaccion`),
  KEY `idx_cliente_br` (`codcliente`),
  KEY `idx_actualizados_br` (`regactualizado`),
  KEY `idx_estadotransaccion_br` (`estadotransaccion`),
  KEY `idx_cajero_br` (`numtienda`,`codcajero`)
) TYPE=InnoDB COMMENT='Cabecera de transaciones de bonos regalo efectuadas';

CREATE TABLE `br_pagodetransaccion` (
  `numtienda` smallint(6) NOT NULL default '0',
  `fecha` date NOT NULL default '0000-00-00',
  `numcaja` smallint(3) NOT NULL default '0',
  `numtransaccion` int(11) NOT NULL default '0',
  `codformadepago` varchar(10) NOT NULL default '',
  `correlativoitem` smallint(2) NOT NULL default '0',
  `monto` decimal(13,2) NOT NULL default '0.00',
  `numdocumento` varchar(10) default NULL,
  `numcuenta` varchar(20) default NULL,
  `numconformacion` varchar(11) default NULL,
  `numreferencia` int(11) default NULL,
  `cedtitular` varchar(12) default NULL,
  `codbanco` char(3) default NULL,
  `regactualizado` char(1) NOT NULL default 'N',
  `codautorizante` varchar(8) default NULL,
  PRIMARY KEY  (`numtienda`,`fecha`,`numcaja`,`numtransaccion`,`codformadepago`,`correlativoitem`),
  KEY `idx_actualizados_br` (`regactualizado`),
  KEY `idx_formadepago_br` (`codformadepago`,`fecha`),
  KEY `idx_transaccion_br` (`numtienda`,`fecha`,`numcaja`,`numtransaccion`),
  KEY `idx_banco_br` (`codbanco`,`fecha`),
  CONSTRAINT `fk_pagodetransaccion_transaccion` FOREIGN KEY (`numtienda`, `fecha`, `numcaja`, `numtransaccion`) REFERENCES `br_transaccion` (`numtienda`, `fecha`, `numcaja`, `numtransaccion`) ON DELETE CASCADE,
  CONSTRAINT `fk_pagodetransaccion_formadepago` FOREIGN KEY (`codformadepago`) REFERENCES `formadepago` (`codformadepago`)
) TYPE=InnoDB COMMENT='Detalle de pagos efectuados por concepto de ventas de bonos ';

CREATE TABLE `br_detalletransaccion` (
  `numtienda` smallint(6) NOT NULL default '0',
  `fecha` date NOT NULL default '0000-00-00',
  `numcaja` smallint(3) NOT NULL default '0',
  `codtarjeta` varchar(20) default NULL,
  `correlativoitem` smallint(6) NOT NULL default '0',
  `numtransaccion` int(11) NOT NULL default '0',
  `montobase` decimal(13,2) NOT NULL default '0.00',
  `montoimpuesto` decimal(16,5) NOT NULL default '0.00000',
  `regactualizado` char(1) NOT NULL default 'N',
  `estadoregistro` char(1) NOT NULL default 'A',
  `numseq` int(11) NOT NULL default '0',
  PRIMARY KEY  (`numtienda`,`fecha`,`numcaja`,`correlativoitem`,`numtransaccion`),
  KEY `idx_transaccionfinal_br` (`numtienda`,`fecha`,`numcaja`,`numtransaccion`),
  KEY `idx_actualizados_br` (`regactualizado`),
  KEY `idx_producto_br` (`codtarjeta`),
  CONSTRAINT `fk_detalletransaccion_transaccion` FOREIGN KEY (`numtienda`, `fecha`, `numcaja`, `numtransaccion`) REFERENCES `br_transaccion` (`numtienda`, `fecha`, `numcaja`, `numtransaccion`) ON DELETE CASCADE ON UPDATE CASCADE
) TYPE=InnoDB COMMENT='Tabla con los detalles de las transacciones de bonos regalo ';

CREATE TABLE `br_comprobantefiscal` (
  `numtienda` smallint(6) NOT NULL default '0',
  `fecha` date NOT NULL default '0000-00-00',
  `numcaja` smallint(3) NOT NULL default '0',
  `numtransaccion` int(11) NOT NULL default '0',
  `numcomprobantefiscal` int(8) NOT NULL default '0',
  `fechaemision` date NOT NULL default '0000-00-00',
  `tipocomprobante` char(1) NOT NULL default '',
  `serialcaja` varchar(20) NOT NULL default '',
  `regactualizado` char(1) NOT NULL default 'N',
  PRIMARY KEY  (`numtienda`,`fecha`,`numcaja`,`numtransaccion`,`numcomprobantefiscal`,`tipocomprobante`),
  CONSTRAINT `fk_comprobantefiscal_transaccion` FOREIGN KEY (`numtienda`, `fecha`, `numcaja`, `numtransaccion`) REFERENCES `br_transaccion` (`numtienda`, `fecha`, `numcaja`, `numtransaccion`) ON DELETE CASCADE ON UPDATE CASCADE
) TYPE=InnoDB COMMENT='Tabla con los datos de los comprobantes fiscales emitidos po';

CREATE TABLE `br_condiciones` (
  `orden` smallint(6) NOT NULL default '0',
  `lineacondicion` varchar(40) NOT NULL default '',
  `habilitado` char(1) NOT NULL default 'S',
  PRIMARY KEY  (`orden`)
) TYPE=InnoDB COMMENT='Tabla con líneas de las condiciones del servicio de bonos re';

CREATE TABLE `br_opcionhabilitada` (
  `codmetodo` smallint(6) NOT NULL default '0',
  `nombreopcion` varchar(30) NOT NULL default '',
  `rutaimagen` varchar(255) NOT NULL default '',
  `tecla` char(1) NOT NULL default '',
  `orden` smallint(6) NOT NULL default '0',
  `habilitado` char(1) NOT NULL default 'S',
  PRIMARY KEY  (`codmetodo`,`nombreopcion`),
  CONSTRAINT `fk_opcionhabilitada_metodo` FOREIGN KEY (`codmetodo`) REFERENCES `metodos` (`codmetodo`) ON DELETE CASCADE ON UPDATE CASCADE
) TYPE=InnoDB COMMENT='Opciones disponlibles para cada modulo de vpos universal';

alter table caja add column numtransaccionbr integer(11) not null default 0;

CREATE TABLE `br_transaccionbloqueada` (
  `numtienda` smallint(6) NOT NULL default '0',
  `fecha` date NOT NULL default '0000-00-00',
  `numcaja` smallint(3) NOT NULL default '0',
  `numtransaccion` int(11) NOT NULL default '0',
  `numcajabloquea` smallint(3) NOT NULL default '0',
  PRIMARY KEY  (`numtienda`,`fecha`,`numcaja`,`numtransaccion`)
) TYPE=InnoDB COMMENT='Tabla de transacciones bloqueadas de BR';