# DBTools DBMYSQL - MySQL Database Dump
#

CREATE DATABASE `CR`;
USE `CR`;

SET FOREIGN_KEY_CHECKS=0;

# Dumping Table Structure for afiliado

#
CREATE TABLE `afiliado` (
  `codafiliado` varchar(12) binary NOT NULL default '',
  `tipoafiliado` char(1) NOT NULL default '',
  `nombre` varchar(100) NOT NULL default '',
  `apellido` varchar(100) default NULL,
  `numtienda` smallint(6) NOT NULL default '0',
  `numficha` varchar(8) default NULL,
  `coddepartamento` char(2) default NULL,
  `codcargo` varchar(4) default NULL,
  `nitcliente` varchar(12) default NULL,
  `direccion` varchar(250) NOT NULL default '',
  `direccionfiscal` varchar(250) default NULL,
  `email` varchar(50) default NULL,
  `codarea` varchar(9) default NULL,
  `numtelefono` varchar(9) default NULL,
  `codarea1` varchar(9) default NULL,
  `numtelefono1` varchar(9) default NULL,
  `fechaafiliacion` date NOT NULL default '0000-00-00',
  `exentoimpuesto` char(1) NOT NULL default 'N',
  `registrado` char(1) NOT NULL default 'N',
  `contactar` char(1) NOT NULL default 'N',
  `codregion` char(3) NOT NULL default '000',
  `estadoafiliado` char(1) NOT NULL default 'A',
  `estadocolaborador` char(1) NOT NULL default 'A',
  `actualizacion` timestamp(14) NOT NULL,
  `genero` char(1) NOT NULL default '',
  `estadocivil` char(1) NOT NULL default '',
  `fechanacimiento` date default '0000-00-00',
  PRIMARY KEY  (`codafiliado`),
  KEY `idx_afiliado` (`numtienda`,`codafiliado`,`tipoafiliado`),
  KEY `idx_colaborador` (`numtienda`,`numficha`,`tipoafiliado`),
  KEY `idx_actualizacion` (`actualizacion`),
  KEY `idx_cargo` (`codcargo`),
  KEY `idx_departamento` (`coddepartamento`),
  KEY `idx_ficha` (`numficha`),
  KEY `idx_nombre` (`nombre`)
) TYPE=InnoDB COMMENT='Maestro de afiliados';
#
# Dumping Table Structure for anulaciondeabonos

#
CREATE TABLE `anulaciondeabonos` (
  `numtienda` smallint(6) NOT NULL default '0',
  `numcaja` smallint(3) NOT NULL default '0',
  `numabono` int(11) NOT NULL default '0',
  `fechaabono` date NOT NULL default '0000-00-00',
  `numabonoanulado` int(11) NOT NULL default '0',
  `fechaabonoanulado` date NOT NULL default '0000-00-00',
  `numservicio` int(11) NOT NULL default '0',
  `regactualizado` char(1) NOT NULL default 'N',
  PRIMARY KEY  (`numtienda`,`numcaja`,`numabono`,`fechaabono`,`numabonoanulado`,`fechaabonoanulado`,`numservicio`),
  KEY `idx_abonoanulado` (`numabonoanulado`,`fechaabonoanulado`,`numservicio`),
  KEY `idx_vigentes` (`regactualizado`),
  KEY `idx_transaccionabono` (`numtienda`,`numcaja`,`fechaabono`,`numabono`,`numservicio`),
  CONSTRAINT `0_7347` FOREIGN KEY (`numtienda`, `numcaja`, `fechaabono`, `numabono`) REFERENCES `transaccionabono` (`numtienda`, `numcaja`, `fecha`, `numabono`) ON DELETE CASCADE
) TYPE=InnoDB COMMENT='Tabla referencial de abonos anulados';
#
# Dumping Table Structure for atcm23

#
CREATE TABLE `atcm23` (
  `codedo` smallint(3) NOT NULL default '0',
  `desedo` varchar(50) NOT NULL default '',
  `staeli` char(1) default '',
  `usreli` varchar(10) default '',
  `actualizacion` timestamp(14) NOT NULL,
  PRIMARY KEY  (`codedo`)
) TYPE=InnoDB COMMENT='Maestro de Estados';
#
# Dumping Table Structure for atcm24

#
CREATE TABLE `atcm24` (
  `codedo` smallint(3) NOT NULL default '0',
  `codciu` smallint(3) NOT NULL default '0',
  `desciu` varchar(50) NOT NULL default '',
  `codarea1` smallint(5) NOT NULL default '0',
  `staeli` char(1) default '',
  `usreli` varchar(10) default '',
  `actualizacion` timestamp(14) NOT NULL,
  PRIMARY KEY  (`codedo`,`codciu`)
) TYPE=InnoDB COMMENT='Maestro de ciudades';
#
# Dumping Table Structure for atcm25

#
CREATE TABLE `atcm25` (
  `codedo` smallint(3) NOT NULL default '0',
  `codciu` smallint(3) NOT NULL default '0',
  `codurb` smallint(6) NOT NULL default '0',
  `desurb` varchar(50) NOT NULL default '',
  `zonpos` varchar(6) NOT NULL default '',
  `staeli` char(1) default '',
  `usreli` varchar(10) default '',
  `actualizacion` timestamp(14) NOT NULL,
  PRIMARY KEY  (`codedo`,`codciu`,`codurb`)
) TYPE=InnoDB COMMENT='Maestro de urbanizaciones';
#
# Dumping Table Structure for auditoria

#
CREATE TABLE `auditoria` (
  `idauditoria` bigint(20) unsigned NOT NULL auto_increment,
  `numtienda` smallint(6) NOT NULL default '0',
  `numcaja` smallint(3) NOT NULL default '0',
  `codusuario` varchar(8) NOT NULL default '',
  `tiporegistro` char(1) NOT NULL default '',
  `fecha` timestamp(14) NOT NULL,
  `codmodulo` smallint(6) NOT NULL default '0',
  `codfuncion` smallint(6) NOT NULL default '0',
  `mensaje` varchar(100) NOT NULL default '',
  `nivelauditoria` char(1) NOT NULL default '1',
  `regactualizado` char(1) NOT NULL default 'N',
  `numtransaccion` int(10) unsigned default NULL,
  PRIMARY KEY  (`idauditoria`,`numtienda`,`numcaja`),
  KEY `idx_idauditoria` (`idauditoria`),
  KEY `idx_usuariotienda` (`numtienda`,`numcaja`,`codusuario`,`codfuncion`,`fecha`),
  KEY `idx_tipoRegistro` (`tiporegistro`),
  KEY `idx_actualizados` (`regactualizado`),
  KEY `idx_caja` (`numtienda`,`numcaja`),
  KEY `idx_usuario` (`numtienda`,`codusuario`),
  KEY `idx_funcion` (`codfuncion`,`codmodulo`),
  CONSTRAINT `auditoria_ibfk_1` FOREIGN KEY (`numtienda`, `numcaja`) REFERENCES `caja` (`numtienda`, `numcaja`),
  CONSTRAINT `auditoria_ibfk_2` FOREIGN KEY (`numtienda`, `codusuario`) REFERENCES `usuario` (`numtienda`, `numficha`),
  CONSTRAINT `auditoria_ibfk_3` FOREIGN KEY (`codfuncion`, `codmodulo`) REFERENCES `funcion` (`codfuncion`, `codmodulo`)
) TYPE=InnoDB COMMENT='Maestro de auditorías registradas por acciones del sistema';
#
# Dumping Table Structure for banco

#
CREATE TABLE `banco` (
  `codbanco` char(3) NOT NULL default '',
  `codexterno` varchar(4) default NULL,
  `nombre` varchar(40) NOT NULL default '',
  `regvigente` char(1) NOT NULL default 'S',
  PRIMARY KEY  (`codbanco`),
  KEY `idx_codigoexterno` (`codexterno`),
  KEY `idx_vigentes` (`regvigente`)
) TYPE=InnoDB COMMENT='Maestro de entidades bancarias';
#
# Dumping Table Structure for caja

#
CREATE TABLE `caja` (
  `numtienda` smallint(6) NOT NULL default '0',
  `numcaja` smallint(3) NOT NULL default '0',
  `idestadocaja` char(2) NOT NULL default '',
  `codusuario` varchar(8) default NULL,
  `numtransaccion` int(11) NOT NULL default '0',
  `numnofiscal` int(11) NOT NULL default '0',
  `numregistro` int(11) NOT NULL default '0',
  `numseqmerchant` int(11) NOT NULL default '0',
  `nivelauditoria` char(1) NOT NULL default '',
  `fechareportez` date NOT NULL default '0000-00-00',
  `versionsistema` varchar(5) NOT NULL default '',
  `modelo` varchar(20) default NULL,
  `numserial` varchar(20) default 'S/N',
  `montorecaudado` decimal(13,2) NOT NULL default '0.00',
  `cierrecajero` char(1) default 'N',
  `ipcaja` varchar(15) default NULL,
  `fechaipcaja` timestamp(14) NOT NULL,
  PRIMARY KEY  (`numtienda`,`numcaja`),
  KEY `idx_estadocaja` (`idestadocaja`),
  KEY `idx_usuario` (`codusuario`),
  CONSTRAINT `0_7354` FOREIGN KEY (`numtienda`) REFERENCES `tienda` (`numtienda`) ON DELETE CASCADE,
  CONSTRAINT `0_7355` FOREIGN KEY (`idestadocaja`) REFERENCES `estadodecaja` (`idestado`)
) TYPE=InnoDB COMMENT='Maestro de cajas registradas en la tienda';
#
# Dumping Table Structure for cambiodeldia

#
CREATE TABLE `cambiodeldia` (
  `tipoformadepago` smallint(6) NOT NULL default '0',
  `cambiodeldia` double NOT NULL default '0',
  `actualizacion` timestamp(14) NOT NULL,
  KEY `idx_cambiodeldia` (`tipoformadepago`,`actualizacion`)
) TYPE=InnoDB;
#
# Dumping Table Structure for cargo

#
CREATE TABLE `cargo` (
  `codcargo` varchar(4) NOT NULL default '0',
  `nombre` varchar(40) NOT NULL default '0',
  PRIMARY KEY  (`codcargo`)
) TYPE=InnoDB COMMENT='Maestro de cargos de la organización';
#
# Dumping Table Structure for catdep

#
CREATE TABLE `catdep` (
  `codcat` char(2) NOT NULL default '',
  `codDep` char(2) NOT NULL default '',
  PRIMARY KEY  (`codcat`,`codDep`)
) TYPE=InnoDB;
#
# Dumping Table Structure for condicionpromocion

#
CREATE TABLE `condicionpromocion` (
  `codigoPromocion` int(11) NOT NULL default '0',
  `orden` int(11) NOT NULL default '0',
  `lineaCondicion` varchar(40) NOT NULL default '',
  `regactualizado` char(1) default NULL,
  PRIMARY KEY  (`codigoPromocion`,`orden`),
  KEY `IDX_condicionPromocion_2` (`codigoPromocion`),
  CONSTRAINT `FK_condicionPromocion_1` FOREIGN KEY (`codigoPromocion`) REFERENCES `promocion` (`codpromocion`) ON DELETE CASCADE
) TYPE=InnoDB;
#
# Dumping Table Structure for condicionventa

#
CREATE TABLE `condicionventa` (
  `codcondicionventa` char(2) NOT NULL default '',
  `descripcion` varchar(30) default NULL,
  PRIMARY KEY  (`codcondicionventa`)
) TYPE=InnoDB COMMENT='Maestro de condiciones de venta para productos';
#
# Dumping Table Structure for departamento

#
CREATE TABLE `departamento` (
  `coddepartamento` char(2) NOT NULL default '',
  `nombre` varchar(30) NOT NULL default '',
  PRIMARY KEY  (`coddepartamento`)
) TYPE=InnoDB COMMENT='Maestro de departamentos';
#
# Dumping Table Structure for detalleafiliado

#
CREATE TABLE `detalleafiliado` (
  `numtienda` smallint(6) NOT NULL default '0',
  `codafiliado` varchar(12) binary NOT NULL default '',
  `mensaje` varchar(200) NOT NULL default '',
  `monto` decimal(13,2) default '0.00',
  `proceso` varchar(7) default '',
  `notificado` char(1) NOT NULL default 'N',
  `actualizacion` timestamp(14) NOT NULL,
  PRIMARY KEY  (`numtienda`,`codafiliado`,`mensaje`),
  KEY `idx_afiliado` (`codafiliado`,`notificado`),
  KEY `idx_monto` (`monto`),
  CONSTRAINT `detalleafiliado_ibfk_2` FOREIGN KEY (`codafiliado`) REFERENCES `afiliado` (`codafiliado`) ON UPDATE CASCADE
) TYPE=InnoDB COMMENT='Tabla con mensajes para los afiliados';
#
# Dumping Table Structure for detallepromocion

#
CREATE TABLE `detallepromocion` (
  `codpromocion` int(11) NOT NULL default '0',
  `numdetalle` int(11) NOT NULL default '0',
  `numcupon` int(11) default NULL,
  `coddepartamento` char(2) default NULL,
  `codlineaseccion` char(2) default NULL,
  `codproducto` varchar(12) default NULL,
  `porcentajedescuento` decimal(5,2) NOT NULL default '0.00',
  `preciofinal` decimal(13,2) NOT NULL default '0.00',
  `estadoregistro` char(1) NOT NULL default 'A',
  PRIMARY KEY  (`codpromocion`,`numdetalle`),
  KEY `idx_cupon` (`numcupon`),
  KEY `idx_departamento` (`coddepartamento`),
  KEY `idx_lineaseccion` (`codlineaseccion`),
  KEY `idx_producto` (`codproducto`),
  CONSTRAINT `0_7362` FOREIGN KEY (`codproducto`) REFERENCES `producto` (`codproducto`),
  CONSTRAINT `0_7363` FOREIGN KEY (`codpromocion`) REFERENCES `promocion` (`codpromocion`) ON DELETE CASCADE
) TYPE=InnoDB COMMENT='Tabla con detalles de las promociones activas';
#
# Dumping Table Structure for detallepromocionext

#
CREATE TABLE `detallepromocionext` (
  `codPromocion` int(11) NOT NULL default '0',
  `numDetalle` int(11) NOT NULL default '0',
  `porcentajeDescuento` decimal(5,2) default '0.00',
  `categoria` char(2) default '',
  `departamento` char(2) default '',
  `marca` varchar(30) default '',
  `linea` char(2) default '',
  `refProveedor` varchar(13) default '',
  `montoMinimo` double(13,5) default '0.00000',
  `cantMinima` int(11) default '0',
  `cantRegalada` int(11) default '0',
  `codProducto` varchar(12) default '',
  `bsDescuento` double(13,5) default '0.00000',
  `estadoRegistro` char(1) default NULL,
  `nombrePromocion` varchar(60) default NULL,
  `prodSinConsecutivo` varchar(12) default NULL,
  `codSeccion` int(2) default NULL,
  PRIMARY KEY  (`codPromocion`,`numDetalle`),
  KEY `IDX_detallepromocionext_2` (`codPromocion`),
  KEY `IDX_detallepromocionext_3` (`codProducto`),
  KEY `IDX_detallepromocionext_4` (`codSeccion`),
  CONSTRAINT `FK_detallepromocionext_1` FOREIGN KEY (`codPromocion`) REFERENCES `promocion` (`codpromocion`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_detallepromocionext_2` FOREIGN KEY (`codProducto`) REFERENCES `producto` (`codproducto`) ON DELETE CASCADE ON UPDATE CASCADE
) TYPE=InnoDB;
#
# Dumping Table Structure for detalleservicio

#
CREATE TABLE `detalleservicio` (
  `numtienda` smallint(6) NOT NULL default '0',
  `codtiposervicio` char(2) NOT NULL default '',
  `numservicio` int(11) NOT NULL default '0',
  `fecha` date NOT NULL default '0000-00-00',
  `codproducto` varchar(12) NOT NULL default '',
  `codcondicionventa` char(2) NOT NULL default '',
  `correlativoitem` smallint(6) NOT NULL default '0',
  `cantidad` decimal(13,2) NOT NULL default '0.00',
  `precioregular` decimal(13,2) NOT NULL default '0.00',
  `preciofinal` decimal(13,2) NOT NULL default '0.00',
  `montoimpuesto` decimal(16,5) NOT NULL default '0.00000',
  `codtipocaptura` char(2) NOT NULL default '',
  `codpromocion` int(11) default NULL,
  `estadoregistro` char(1) NOT NULL default 'V',
  `regactualizado` char(1) NOT NULL default 'N',
  PRIMARY KEY  (`numtienda`,`codtiposervicio`,`numservicio`,`fecha`,`codproducto`,`codcondicionventa`,`correlativoitem`),
  KEY `idx_servicio` (`numtienda`,`codtiposervicio`,`numservicio`,`fecha`),
  KEY `idx_promocion` (`codpromocion`),
  KEY `idx_producto` (`codproducto`),
  KEY `idx_tiposervicio` (`codtiposervicio`),
  KEY `idx_condicionventa` (`codcondicionventa`),
  KEY `idx_tipocaptura` (`codtipocaptura`),
  CONSTRAINT `0_7365` FOREIGN KEY (`codtipocaptura`) REFERENCES `tipocaptura` (`codtipocaptura`),
  CONSTRAINT `0_7366` FOREIGN KEY (`numtienda`, `codtiposervicio`, `numservicio`, `fecha`) REFERENCES `servicio` (`numtienda`, `codtiposervicio`, `numservicio`, `fecha`) ON DELETE CASCADE,
  CONSTRAINT `0_7367` FOREIGN KEY (`codproducto`) REFERENCES `producto` (`codproducto`),
  CONSTRAINT `0_7368` FOREIGN KEY (`codpromocion`) REFERENCES `promocion` (`codpromocion`),
  CONSTRAINT `0_7370` FOREIGN KEY (`codcondicionventa`) REFERENCES `condicionventa` (`codcondicionventa`)
) TYPE=InnoDB COMMENT='Tabla con detalles de los servicios vigentes';
#
# Dumping Table Structure for detalleserviciocondicion

#
CREATE TABLE `detalleserviciocondicion` (
  `numtienda` smallint(6) NOT NULL default '0',
  `codtiposervicio` char(2) NOT NULL default '',
  `numservicio` int(11) NOT NULL default '0',
  `fecha` date NOT NULL default '0000-00-00',
  `codproducto` varchar(12) NOT NULL default '',
  `condicionventa` char(2) NOT NULL default '',
  `correlativoitem` smallint(6) NOT NULL default '0',
  `codcondicionventa` char(2) NOT NULL default '',
  `nombrePromocion` varchar(60) NOT NULL default '',
  `porcentajeDescuento` decimal(5,2) NOT NULL default '0.00',
  `codpromocion` int(11) NOT NULL default '0',
  `montoDctoCombo` decimal(13,2) default NULL,
  `regactualizado` char(1) NOT NULL default 'N',
  `prioridadPromocion` int(11) NOT NULL default '0',
  PRIMARY KEY  (`numtienda`,`codtiposervicio`,`numservicio`,`fecha`,`codproducto`,`condicionventa`,`correlativoitem`,`codcondicionventa`),
  KEY `IDX_detalleserviciocondicion_2` (`codcondicionventa`),
  KEY `IDX_detalleserviciocondicion_3` (`codpromocion`),
  CONSTRAINT `FK_detalleserviciocondicion_1` FOREIGN KEY (`codcondicionventa`) REFERENCES `condicionventa` (`codcondicionventa`) ON UPDATE CASCADE,
  CONSTRAINT `FK_detalleserviciocondicion_2` FOREIGN KEY (`numtienda`, `codtiposervicio`, `numservicio`, `fecha`, `codproducto`, `condicionventa`, `correlativoitem`) REFERENCES `detalleservicio` (`numtienda`, `codtiposervicio`, `numservicio`, `fecha`, `codproducto`, `codcondicionventa`, `correlativoitem`) ON UPDATE CASCADE
) TYPE=InnoDB;
#
# Dumping Table Structure for detalleserviciotempcondicion

#
CREATE TABLE `detalleserviciotempcondicion` (
  `numtienda` smallint(6) NOT NULL default '0',
  `codtiposervicio` char(2) NOT NULL default '',
  `numservicio` int(11) NOT NULL default '0',
  `fecha` date NOT NULL default '0000-00-00',
  `codproducto` varchar(12) NOT NULL default '',
  `condicionventa` char(2) NOT NULL default '',
  `correlativoitem` smallint(6) NOT NULL default '0',
  `codcondicionventa` char(2) NOT NULL default '',
  `nombrePromocion` varchar(60) NOT NULL default '',
  `porcentajeDescuento` decimal(5,2) NOT NULL default '0.00',
  `codpromocion` int(11) NOT NULL default '0',
  `montoDctoCombo` decimal(13,2) default NULL,
  `prioridadPromocion` int(11) NOT NULL default '0',
  `regactualizado` char(1) NOT NULL default 'N',
  PRIMARY KEY  (`numtienda`,`codtiposervicio`,`numservicio`,`fecha`,`codproducto`,`condicionventa`,`correlativoitem`,`codcondicionventa`),
  KEY `IDX_detalleserviciotempcondicion_2` (`codcondicionventa`),
  CONSTRAINT `FK_detalleserviciotempcondicion_1` FOREIGN KEY (`codcondicionventa`) REFERENCES `condicionventa` (`codcondicionventa`) ON UPDATE CASCADE,
  CONSTRAINT `FK_detalleserviciotempcondicion_2` FOREIGN KEY (`numtienda`, `codtiposervicio`, `numservicio`, `fecha`, `codproducto`, `condicionventa`, `correlativoitem`) REFERENCES `detalleserviciotemp` (`numtienda`, `codtiposervicio`, `numservicio`, `fecha`, `codproducto`, `codcondicionventa`, `correlativoitem`) ON UPDATE CASCADE
) TYPE=InnoDB;
#
# Dumping Table Structure for detalletransaccion

#
CREATE TABLE `detalletransaccion` (
  `numtienda` smallint(6) NOT NULL default '0',
  `fecha` date NOT NULL default '0000-00-00',
  `numcajainicia` smallint(3) NOT NULL default '0',
  `numregcajainicia` int(11) NOT NULL default '0',
  `codproducto` varchar(12) NOT NULL default '',
  `codcondicionventa` char(2) NOT NULL default '',
  `correlativoitem` smallint(6) NOT NULL default '0',
  `numcajafinaliza` smallint(3) default NULL,
  `numtransaccion` int(11) default NULL,
  `cantidad` decimal(8,2) NOT NULL default '0.00',
  `codvendedor` varchar(8) default NULL,
  `precioregular` decimal(13,2) NOT NULL default '0.00',
  `codsupervisor` varchar(8) default NULL,
  `preciofinal` decimal(13,2) NOT NULL default '0.00',
  `montoimpuesto` decimal(16,5) NOT NULL default '0.00000',
  `desctoempleado` decimal(13,2) NOT NULL default '0.00',
  `despacharproducto` char(1) NOT NULL default '',
  `codpromocion` int(11) default NULL,
  `codtipocaptura` char(2) NOT NULL default '',
  `regactualizado` char(1) NOT NULL default 'N',
  PRIMARY KEY  (`numtienda`,`fecha`,`numcajainicia`,`numregcajainicia`,`codproducto`,`codcondicionventa`,`correlativoitem`),
  KEY `idx_transacciontemporal` (`numtienda`,`fecha`,`numcajainicia`,`numregcajainicia`),
  KEY `idx_transaccionfinal` (`numtienda`,`fecha`,`numcajafinaliza`,`numtransaccion`),
  KEY `idx_detalleproducto` (`codproducto`,`codcondicionventa`,`correlativoitem`,`preciofinal`),
  KEY `idx_despachados` (`despacharproducto`),
  KEY `idx_actualizados` (`regactualizado`),
  KEY `idx_producto` (`codproducto`),
  KEY `idx_supervisor` (`numtienda`,`codsupervisor`),
  KEY `idx_condicionventa` (`codcondicionventa`),
  KEY `idx_tipocaptura` (`codtipocaptura`),
  KEY `idx_vendedor` (`numtienda`,`codvendedor`),
  CONSTRAINT `0_7372` FOREIGN KEY (`codtipocaptura`) REFERENCES `tipocaptura` (`codtipocaptura`),
  CONSTRAINT `0_7373` FOREIGN KEY (`codproducto`) REFERENCES `producto` (`codproducto`),
  CONSTRAINT `0_7375` FOREIGN KEY (`numtienda`, `fecha`, `numcajafinaliza`, `numtransaccion`) REFERENCES `transaccion` (`numtienda`, `fecha`, `numcajafinaliza`, `numtransaccion`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `0_7376` FOREIGN KEY (`codcondicionventa`) REFERENCES `condicionventa` (`codcondicionventa`),
  CONSTRAINT `detalletransaccion_ibfk_1` FOREIGN KEY (`numtienda`, `codvendedor`) REFERENCES `usuario` (`numtienda`, `numficha`)
) TYPE=InnoDB COMMENT='Tabla con los detalles de las transacciones efectuadas';
#
# Dumping Table Structure for detalletransaccioncondicion

#
CREATE TABLE `detalletransaccioncondicion` (
  `numtienda` smallint(6) NOT NULL default '0',
  `fecha` date NOT NULL default '0000-00-00',
  `numcajainicia` smallint(3) NOT NULL default '0',
  `numregcajainicia` int(11) NOT NULL default '0',
  `codproducto` varchar(12) NOT NULL default '',
  `condicionventa` char(2) NOT NULL default '',
  `correlativoitem` smallint(6) NOT NULL default '0',
  `codcondicionventa` char(2) NOT NULL default '',
  `nombrePromocion` varchar(60) NOT NULL default '',
  `porcentajeDescuento` decimal(5,2) NOT NULL default '0.00',
  `regactualizado` char(1) NOT NULL default 'N',
  `codpromocion` int(11) NOT NULL default '0',
  `montoDctoCombo` decimal(13,2) default NULL,
  `numtransaccion` int(11) NOT NULL default '0',
  `prioridadPromocion` int(11) NOT NULL default '0',
  PRIMARY KEY  (`numtienda`,`fecha`,`numcajainicia`,`numregcajainicia`,`codproducto`,`condicionventa`,`correlativoitem`,`codcondicionventa`),
  KEY `IDX_detalletransaccioncondicion_3` (`codcondicionventa`),
  KEY `IDX_detalletransaccioncondicion_4` (`numtienda`,`fecha`,`numcajainicia`,`numregcajainicia`,`codproducto`,`condicionventa`,`correlativoitem`),
  CONSTRAINT `FK_detalletransaccioncondicion_1` FOREIGN KEY (`codcondicionventa`) REFERENCES `condicionventa` (`codcondicionventa`) ON UPDATE CASCADE,
  CONSTRAINT `FK_detalletransaccioncondicion_3` FOREIGN KEY (`numtienda`, `fecha`, `numcajainicia`, `numregcajainicia`, `codproducto`, `condicionventa`, `correlativoitem`) REFERENCES `detalletransaccion` (`numtienda`, `fecha`, `numcajainicia`, `numregcajainicia`, `codproducto`, `codcondicionventa`, `correlativoitem`) ON UPDATE CASCADE
) TYPE=InnoDB;
#
# Dumping Table Structure for devolucionventa

#
CREATE TABLE `devolucionventa` (
  `numtiendadevolucion` smallint(6) NOT NULL default '0',
  `fechadevolucion` date NOT NULL default '0000-00-00',
  `numcajadevolucion` smallint(3) NOT NULL default '0',
  `numtransacciondev` int(11) NOT NULL default '0',
  `numtiendaventa` smallint(6) NOT NULL default '0',
  `fechaventa` date NOT NULL default '0000-00-00',
  `numcajaventa` smallint(3) NOT NULL default '0',
  `numtransaccionvta` int(11) NOT NULL default '0',
  `tipotransaccion` char(1) NOT NULL default '',
  `regactualizado` char(1) NOT NULL default 'N',
  PRIMARY KEY  (`numtiendadevolucion`,`fechadevolucion`,`numcajadevolucion`,`numtransacciondev`,`numtiendaventa`,`fechaventa`,`numcajaventa`,`numtransaccionvta`),
  KEY `idx_tiendaventa` (`numtiendaventa`,`fechaventa`,`numcajaventa`,`numtransaccionvta`),
  KEY `idx_tiendadevolucion` (`numtiendadevolucion`,`fechadevolucion`,`numcajadevolucion`,`numtransacciondev`),
  KEY `idx_tipotransaccion` (`tipotransaccion`),
  KEY `idx_actualizados` (`regactualizado`),
  CONSTRAINT `devolucionventa_ibfk_1` FOREIGN KEY (`numtiendadevolucion`, `fechadevolucion`, `numcajadevolucion`, `numtransacciondev`) REFERENCES `transaccion` (`numtienda`, `fecha`, `numcajafinaliza`, `numtransaccion`)
) TYPE=InnoDB COMMENT='Tabla referencial con información de las notas de crédito';
#
# Dumping Table Structure for donacion

#
CREATE TABLE `donacion` (
  `codDonacion` int(11) NOT NULL default '0',
  `codBarraProdDonacion` int(11) NOT NULL default '0',
  `fecIniDonacion` timestamp(14) NOT NULL,
  `fecFinDonacion` timestamp(14) NOT NULL default '00000000000000',
  `nomDonacion` varchar(50) NOT NULL default '',
  `descDonacion` varchar(50) NOT NULL default '',
  `tipoDonacion` int(11) NOT NULL default '0',
  `estadoDonacion` int(11) NOT NULL default '0',
  `cantPropDonacion` double(13,5) NOT NULL default '0.00000',
  `regactualizado` char(1) default 'N',
  `mostrarAlTotalizar` smallint(6) default NULL,
  PRIMARY KEY  (`codDonacion`)
) TYPE=InnoDB;
#
# Dumping Table Structure for donacionesregistradas

#
CREATE TABLE `donacionesregistradas` (
  `montoDonado` double(13,5) NOT NULL default '0.00000',
  `fechaDonacion` date NOT NULL default '0000-00-00',
  `numTransaccion` int(11) NOT NULL default '0',
  `numCaja` smallint(6) NOT NULL default '0',
  `numTienda` smallint(6) NOT NULL default '0',
  `codigoDonacion` int(11) NOT NULL default '0',
  `codigo` int(11) NOT NULL default '0',
  `regactualizado` char(1) default 'N',
  PRIMARY KEY  (`fechaDonacion`,`numTransaccion`,`numCaja`,`numTienda`,`codigoDonacion`,`codigo`),
  KEY `IDX_donacionesRegistradas_2` (`numTienda`,`fechaDonacion`,`numCaja`,`numTransaccion`),
  KEY `IDX_donacionesRegistradas_3` (`codigoDonacion`),
  KEY `IXPK_donacionesregistradas` (`fechaDonacion`,`numTransaccion`,`numCaja`,`numTienda`,`codigoDonacion`,`codigo`),
  CONSTRAINT `FK_donacionesRegistradas_1` FOREIGN KEY (`codigoDonacion`) REFERENCES `donacion` (`codDonacion`),
  CONSTRAINT `FK_donacionesregistradas_2` FOREIGN KEY (`numTienda`, `fechaDonacion`, `numCaja`, `numTransaccion`) REFERENCES `transaccion` (`numtienda`, `fecha`, `numcajafinaliza`, `numtransaccion`) ON DELETE CASCADE ON UPDATE CASCADE
) TYPE=InnoDB;
#
# Dumping Table Structure for estadodecaja

#
CREATE TABLE `estadodecaja` (
  `idestado` char(2) NOT NULL default '',
  `descripcion` varchar(30) NOT NULL default '',
  PRIMARY KEY  (`idestado`)
) TYPE=InnoDB COMMENT='Maestro de estados válidos para la caja';
#
# Dumping Table Structure for formadepago

#
CREATE TABLE `formadepago` (
  `codformadepago` varchar(10) NOT NULL default '',
  `tipoformadepago` smallint(2) NOT NULL default '0',
  `nombre` varchar(30) NOT NULL default '',
  `codbanco` char(3) default NULL,
  `indicarbanco` char(1) NOT NULL default 'N',
  `indicarnumdocumento` char(1) NOT NULL default 'N',
  `indicarnumcuenta` char(1) NOT NULL default 'N',
  `indicarnumconforma` char(1) NOT NULL default 'N',
  `indicarnumreferencia` char(1) NOT NULL default 'N',
  `indicarcedulatitular` char(1) NOT NULL default 'N',
  `validarsaldocliente` char(1) NOT NULL default 'N',
  `montominimo` decimal(13,2) default NULL,
  `montomaximo` decimal(13,2) default NULL,
  `montocomision` decimal(13,2) default NULL,
  `permitevuelto` char(1) NOT NULL default 'N',
  `entregaparcial` char(1) NOT NULL default 'N',
  `requiereautorizacion` char(1) NOT NULL default 'N',
  `prioridad` smallint(6) NOT NULL default '0',
  `regvigente` char(1) NOT NULL default 'S',
  PRIMARY KEY  (`codformadepago`),
  KEY `idx_tipoformadepago` (`tipoformadepago`),
  KEY `idx_banco` (`codbanco`),
  KEY `idx_vigentes` (`regvigente`),
  CONSTRAINT `0_7382` FOREIGN KEY (`codbanco`) REFERENCES `banco` (`codbanco`)
) TYPE=InnoDB COMMENT='Maestro de formas de pago disponibles';
#
# Dumping Table Structure for funcion

#
CREATE TABLE `funcion` (
  `codfuncion` smallint(6) NOT NULL default '0',
  `descripcion` varchar(60) NOT NULL default '',
  `codmodulo` smallint(6) NOT NULL default '0',
  `nivelauditoria` char(1) NOT NULL default '1',
  `regvigente` char(1) NOT NULL default 'S',
  `actualizacion` timestamp(14) NOT NULL,
  `reqautorizacion` char(1) NOT NULL default 'S',
  PRIMARY KEY  (`codfuncion`,`codmodulo`),
  KEY `idx_modulo` (`codmodulo`),
  KEY `idx_vigentes` (`regvigente`),
  KEY `idx_actualizacion` (`actualizacion`),
  CONSTRAINT `0_7384` FOREIGN KEY (`codmodulo`) REFERENCES `modulos` (`codmodulo`)
) TYPE=InnoDB COMMENT='Maestro de funciones del sistema disponibles';
#
# Dumping Table Structure for funcionmetodos

#
CREATE TABLE `funcionmetodos` (
  `codmodulo` smallint(6) NOT NULL default '0',
  `codfuncion` smallint(6) NOT NULL default '0',
  `codmetodo` smallint(6) NOT NULL default '0',
  PRIMARY KEY  (`codmodulo`,`codfuncion`,`codmetodo`),
  KEY `idx_metodo` (`codmetodo`),
  KEY `idx_funcion` (`codfuncion`),
  CONSTRAINT `0_7386` FOREIGN KEY (`codmetodo`) REFERENCES `metodos` (`codmetodo`) ON DELETE CASCADE,
  CONSTRAINT `0_7387` FOREIGN KEY (`codmodulo`, `codfuncion`) REFERENCES `funcion` (`codmodulo`, `codfuncion`) ON DELETE CASCADE
) TYPE=InnoDB COMMENT='Tabla de enlaze de funciones con métodos del sistema';
#
# Dumping Table Structure for funcionperfil

#
CREATE TABLE `funcionperfil` (
  `codperfil` char(3) NOT NULL default '',
  `codfuncion` smallint(6) NOT NULL default '0',
  `habilitado` char(1) NOT NULL default 'S',
  `autorizado` char(1) NOT NULL default 'N',
  `actualizacion` timestamp(14) NOT NULL,
  `codmodulo` smallint(6) NOT NULL default '0',
  PRIMARY KEY  (`codperfil`,`codfuncion`,`codmodulo`),
  KEY `idx_actualizacion` (`actualizacion`),
  KEY `idx_funcion` (`codfuncion`),
  KEY `idx_modulo` (`codmodulo`),
  KEY `idx_perfil` (`codperfil`),
  CONSTRAINT `0_7389` FOREIGN KEY (`codperfil`) REFERENCES `perfil` (`codperfil`) ON DELETE CASCADE,
  CONSTRAINT `0_7390` FOREIGN KEY (`codfuncion`) REFERENCES `funcion` (`codfuncion`) ON DELETE CASCADE
) TYPE=InnoDB COMMENT='Tabla referencial de la configuración de los perfiles';
#
# Dumping Table Structure for impuestoregion

#
CREATE TABLE `impuestoregion` (
  `codimpuesto` char(3) NOT NULL default '',
  `codregion` char(3) NOT NULL default '000',
  `fechaemision` date NOT NULL default '0000-00-00',
  `descripcion` varchar(20) default NULL,
  `porcentaje` decimal(5,2) NOT NULL default '0.00',
  PRIMARY KEY  (`codimpuesto`,`codregion`,`fechaemision`),
  KEY `idx_region` (`codregion`),
  KEY `idx_impuesto` (`codimpuesto`),
  CONSTRAINT `0_7393` FOREIGN KEY (`codregion`) REFERENCES `region` (`codregion`)
) TYPE=InnoDB COMMENT='Tabla referencial de la información de las regiones de venta';
#
# Dumping Table Structure for lineaseccion

#
CREATE TABLE `lineaseccion` (
  `codseccion` char(2) NOT NULL default '',
  `coddepartamento` char(2) NOT NULL default '',
  `nombre` varchar(30) NOT NULL default '',
  PRIMARY KEY  (`codseccion`,`coddepartamento`),
  KEY `idx_departamento` (`coddepartamento`),
  KEY `idx_nombre` (`nombre`),
  CONSTRAINT `0_7395` FOREIGN KEY (`coddepartamento`) REFERENCES `departamento` (`coddepartamento`) ON DELETE CASCADE
) TYPE=InnoDB COMMENT='Maestro de línea/sección de la organización';
#
# Dumping Table Structure for maquinadeestado

#
CREATE TABLE `maquinadeestado` (
  `edoinicial` char(2) NOT NULL default '0',
  `codmetodo` smallint(6) NOT NULL default '0',
  `edofinal` char(2) NOT NULL default '0',
  PRIMARY KEY  (`edoinicial`,`codmetodo`),
  KEY `idx_metodo` (`codmetodo`),
  KEY `idx_edofinal` (`edofinal`),
  CONSTRAINT `0_7397` FOREIGN KEY (`edoinicial`) REFERENCES `estadodecaja` (`idestado`),
  CONSTRAINT `0_7398` FOREIGN KEY (`edofinal`) REFERENCES `estadodecaja` (`idestado`),
  CONSTRAINT `0_7399` FOREIGN KEY (`codmetodo`) REFERENCES `metodos` (`codmetodo`) ON DELETE CASCADE
) TYPE=InnoDB COMMENT='Maestro de métodos disponibles para la máquina de estado';
#
# Dumping Table Structure for metodos

#
CREATE TABLE `metodos` (
  `codmetodo` smallint(6) NOT NULL default '0',
  `descripcion` varchar(50) NOT NULL default '0',
  PRIMARY KEY  (`codmetodo`)
) TYPE=InnoDB COMMENT='Maestro de metodos del sistema en java';
#
# Dumping Table Structure for modulos

#
CREATE TABLE `modulos` (
  `codmodulo` smallint(6) NOT NULL default '0',
  `descripcion` varchar(30) NOT NULL default '0',
  `regvigente` char(1) NOT NULL default 'S',
  `actualizacion` timestamp(14) NOT NULL,
  PRIMARY KEY  (`codmodulo`)
) TYPE=InnoDB COMMENT='Maestro de modulos contenedores de las funciones del sistema';
#
# Dumping Table Structure for pagodeabonos

#
CREATE TABLE `pagodeabonos` (
  `numtienda` smallint(6) NOT NULL default '0',
  `numcaja` smallint(3) NOT NULL default '0',
  `numabono` int(11) NOT NULL default '0',
  `fecha` date NOT NULL default '0000-00-00',
  `numservicio` int(11) NOT NULL default '0',
  `codformadepago` varchar(10) NOT NULL default '',
  `correlativoitem` smallint(2) NOT NULL default '0',
  `codbanco` char(3) default NULL,
  `monto` decimal(13,2) NOT NULL default '0.00',
  `numdocumento` varchar(10) default NULL,
  `numcuenta` varchar(20) default NULL,
  `numconformacion` int(11) default NULL,
  `numreferencia` int(11) default NULL,
  `cedtitular` varchar(12) default NULL,
  `regactualizado` char(1) NOT NULL default 'N',
  PRIMARY KEY  (`numtienda`,`numcaja`,`numabono`,`fecha`,`numservicio`,`codformadepago`,`correlativoitem`),
  KEY `idx_actualizados` (`regactualizado`),
  KEY `idx_formadepago` (`codformadepago`),
  KEY `idx_banco` (`codbanco`),
  KEY `idx_transaccionabono` (`numtienda`,`numcaja`,`fecha`,`numabono`),
  CONSTRAINT `0_7403` FOREIGN KEY (`codformadepago`) REFERENCES `formadepago` (`codformadepago`),
  CONSTRAINT `0_7405` FOREIGN KEY (`numtienda`, `numcaja`, `fecha`, `numabono`, `numservicio`) REFERENCES `transaccionabono` (`numtienda`, `numcaja`, `fecha`, `numabono`, `numservicio`) ON DELETE CASCADE
) TYPE=InnoDB COMMENT='Detalle de pagos efectuados por concepto de abono';
#
# Dumping Table Structure for pagodetransaccion

#
CREATE TABLE `pagodetransaccion` (
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
  KEY `idx_actualizados` (`regactualizado`),
  KEY `idx_formadepago` (`codformadepago`,`fecha`),
  KEY `idx_transaccion` (`numtienda`,`fecha`,`numcaja`,`numtransaccion`),
  KEY `idx_banco` (`codbanco`,`fecha`),
  CONSTRAINT `0_7407` FOREIGN KEY (`numtienda`, `fecha`, `numcaja`, `numtransaccion`) REFERENCES `transaccion` (`numtienda`, `fecha`, `numcajafinaliza`, `numtransaccion`) ON DELETE CASCADE,
  CONSTRAINT `0_7408` FOREIGN KEY (`codformadepago`) REFERENCES `formadepago` (`codformadepago`)
) TYPE=InnoDB COMMENT='Detalle de pagos efectuados por concepto de ventas';
#
# Dumping Table Structure for pagodonacion

#
CREATE TABLE `pagodonacion` (
  `fechaDonacion` date NOT NULL default '0000-00-00',
  `numTransaccion` int(11) NOT NULL default '0',
  `numCaja` smallint(6) NOT NULL default '0',
  `numTienda` smallint(6) NOT NULL default '0',
  `codigoDonacion` int(11) NOT NULL default '0',
  `codigoDonacionRegistrada` int(11) NOT NULL default '0',
  `codigoPago` int(11) NOT NULL default '0',
  `codigoFormaPago` varchar(10) NOT NULL default '',
  `monto` double(13,5) NOT NULL default '0.00000',
  `regactualizado` char(1) default 'N',
  PRIMARY KEY  (`codigoPago`,`fechaDonacion`,`numTransaccion`,`numCaja`,`numTienda`,`codigoDonacion`,`codigoDonacionRegistrada`),
  KEY `formapago` (`codigoFormaPago`),
  KEY `donacionesregistradas` (`fechaDonacion`,`numTransaccion`,`numCaja`,`numTienda`,`codigoDonacion`,`codigoDonacionRegistrada`),
  CONSTRAINT `FK_donacionregistrada` FOREIGN KEY (`fechaDonacion`, `numTransaccion`, `numCaja`, `numTienda`, `codigoDonacion`, `codigoDonacionRegistrada`) REFERENCES `donacionesregistradas` (`fechaDonacion`, `numTransaccion`, `numCaja`, `numTienda`, `codigoDonacion`, `codigo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_pagodonacion_2` FOREIGN KEY (`codigoFormaPago`) REFERENCES `formadepago` (`codformadepago`)
) TYPE=InnoDB;
#
# Dumping Table Structure for perfil

#
CREATE TABLE `perfil` (
  `codperfil` char(3) NOT NULL default '',
  `descripcion` varchar(20) NOT NULL default '',
  `nivelauditoria` char(1) NOT NULL default '1',
  `regvigente` char(1) NOT NULL default 'S',
  `actualizacion` timestamp(14) NOT NULL,
  PRIMARY KEY  (`codperfil`),
  KEY `idx_vigentes` (`regvigente`),
  KEY `idx_actualizacion` (`actualizacion`)
) TYPE=InnoDB COMMENT='Maestro de perfiles de usuario disponibles';
#
# Dumping Table Structure for planificador

#
CREATE TABLE `planificador` (
  `numtienda` smallint(6) NOT NULL default '0',
  `numcaja` smallint(3) NOT NULL default '0',
  `entidad` varchar(30) NOT NULL default '',
  `actualizacion` timestamp(14) NOT NULL,
  `fallasincronizador` char(1) NOT NULL default 'S',
  `destino` char(1) NOT NULL default 'C',
  PRIMARY KEY  (`numtienda`,`numcaja`,`entidad`,`destino`),
  KEY `idx_actualizacion` (`actualizacion`)
) TYPE=InnoDB COMMENT='Entidad referencial del funcionamiento del sincronizador';
#
# Dumping Table Structure for prodcodigoexterno

#
CREATE TABLE `prodcodigoexterno` (
  `codproducto` varchar(12) NOT NULL default '',
  `codexterno` varchar(13) NOT NULL default '',
  `actualizacion` timestamp(14) NOT NULL,
  PRIMARY KEY  (`codexterno`),
  KEY `idx_producto` (`codproducto`)
) TYPE=InnoDB COMMENT='Referencia entre códigos interno y externo de productos';
#
# Dumping Table Structure for prodseccion

#
CREATE TABLE `prodseccion` (
  `codProducto` varchar(12) NOT NULL default '',
  `codDepartamento` char(2) NOT NULL default '',
  `codLinea` char(2) NOT NULL default '',
  `codSeccion` int(2) NOT NULL default '0',
  PRIMARY KEY  (`codProducto`),
  KEY `IDX_productoseccion_linea` (`codProducto`,`codDepartamento`,`codLinea`),
  CONSTRAINT `FK_productoseccion_1` FOREIGN KEY (`codProducto`, `codDepartamento`, `codLinea`) REFERENCES `producto` (`codproducto`, `coddepartamento`, `codlineaseccion`) ON DELETE CASCADE
) TYPE=InnoDB;
#
# Dumping Table Structure for producto

#
CREATE TABLE `producto` (
  `codproducto` varchar(12) NOT NULL default '',
  `descripcioncorta` varchar(20) NOT NULL default '',
  `descripcionlarga` varchar(100) default NULL,
  `codunidadventa` int(11) NOT NULL default '0',
  `referenciaproveedor` varchar(13) default NULL,
  `marca` varchar(30) default NULL,
  `modelo` varchar(30) default NULL,
  `coddepartamento` char(2) NOT NULL default '',
  `codlineaseccion` char(2) NOT NULL default '',
  `costolista` decimal(13,2) NOT NULL default '0.00',
  `precioregular` decimal(13,2) NOT NULL default '0.00',
  `codimpuesto` char(3) NOT NULL default '',
  `cantidadventaempaque` int(11) NOT NULL default '0',
  `desctoventaempaque` decimal(5,2) default NULL,
  `indicadesctoempleado` char(1) NOT NULL default 'N',
  `indicadespachar` char(1) default 'N',
  `estadoproducto` char(1) NOT NULL default 'A',
  `actualizacion` timestamp(14) NOT NULL,
  PRIMARY KEY  (`codproducto`),
  KEY `idx_actualizacion` (`actualizacion`),
  KEY `idx_proveedor` (`referenciaproveedor`),
  KEY `idx_unidadventa` (`codunidadventa`),
  KEY `idx_departamento` (`coddepartamento`),
  KEY `idx_lineaSeccion` (`codlineaseccion`,`coddepartamento`),
  KEY `idx_desclarga` (`descripcionlarga`),
  KEY `idx_desccorta` (`descripcioncorta`),
  KEY `idx_marca` (`marca`),
  KEY `idx_modelo` (`modelo`),
  KEY `IDX_producto_linea` (`codproducto`,`coddepartamento`,`codlineaseccion`),
  CONSTRAINT `0_7414` FOREIGN KEY (`codunidadventa`) REFERENCES `unidaddeventa` (`codunidadventa`),
  CONSTRAINT `0_7416` FOREIGN KEY (`codlineaseccion`, `coddepartamento`) REFERENCES `lineaseccion` (`codseccion`, `coddepartamento`)
) TYPE=InnoDB COMMENT='Maestro de productos disponibles para la venta';
#
# Dumping Table Structure for promocion

#
CREATE TABLE `promocion` (
  `codpromocion` int(11) NOT NULL default '0',
  `tipopromocion` char(1) NOT NULL default '',
  `fechainicio` date NOT NULL default '0000-00-00',
  `horainicio` time NOT NULL default '00:00:00',
  `fechafinaliza` date NOT NULL default '0000-00-00',
  `horafinaliza` time NOT NULL default '00:00:00',
  `prioridad` char(1) NOT NULL default '',
  PRIMARY KEY  (`codpromocion`),
  KEY `idx_iniciopromocion` (`fechainicio`,`horainicio`),
  KEY `idx_finpromocion` (`fechafinaliza`,`horafinaliza`),
  KEY `idx_tipopromocion` (`tipopromocion`)
) TYPE=InnoDB COMMENT='Cabecera de promociones activas';
#
# Dumping Table Structure for promocionregistrada

#
CREATE TABLE `promocionregistrada` (
  `codPromocion` int(11) NOT NULL default '0',
  `codigoProducto` varchar(12) NOT NULL default '',
  `codigo` int(11) NOT NULL default '0',
  `numTienda` smallint(6) NOT NULL default '0',
  `numCaja` smallint(3) NOT NULL default '0',
  `fecha` date NOT NULL default '0000-00-00',
  `numtransacion` int(11) NOT NULL default '0',
  `regactualizado` char(1) default 'N',
  `cantproducto` int(3) default '1',
  PRIMARY KEY  (`codPromocion`,`codigoProducto`,`codigo`,`numTienda`,`numCaja`,`fecha`,`numtransacion`),
  KEY `prod` (`codPromocion`),
  KEY `trans` (`numTienda`,`fecha`,`numCaja`,`numtransacion`),
  KEY `detPromo` (`codPromocion`)
) TYPE=InnoDB;
#
# Dumping Table Structure for puntoAgilCuentasEspeciales

#
CREATE TABLE `puntoAgilCuentasEspeciales` (
  `idPuntoAgilCuentasEspeciales` int(10) unsigned NOT NULL auto_increment,
  `cuentaEspecial` char(2) NOT NULL default '',
  `descripcion` varchar(45) NOT NULL default '',
  `tipo` char(1) NOT NULL default 'T',
  `regvigente` char(1) NOT NULL default 'S',
  `fechaActualizacion` timestamp(14) NOT NULL,
  `codbanco` char(3) default NULL,
  PRIMARY KEY  (`idPuntoAgilCuentasEspeciales`),
  KEY `idx_pactaesp_banco` (`codbanco`),
  CONSTRAINT `FK_puntoAgilCuentasEspeciales_banco` FOREIGN KEY (`codbanco`) REFERENCES `banco` (`codbanco`) ON DELETE CASCADE ON UPDATE CASCADE
) TYPE=InnoDB COMMENT='Parametros posibles de cuentas especiales de transacciones p';
#
# Dumping Table Structure for puntoAgilFormadePago

#
CREATE TABLE `puntoAgilFormadePago` (
  `codformadepago` varchar(10) NOT NULL default '',
  `permitePuntoAgil` char(1) NOT NULL default 'N',
  `requiereLecturaBanda` char(1) NOT NULL default 'N',
  `imprimirVoucher` char(1) NOT NULL default 'N',
  `indicarPlan` char(1) NOT NULL default 'N',
  `indicarCuentaEspecial` char(1) NOT NULL default 'N',
  `indicarTipoCuenta` char(1) NOT NULL default 'N',
  `tipoTarjetaPuntoAgil` char(1) default NULL,
  `longitudCodigoSeguridad` smallint(6) default NULL,
  `fechaActualizacion` timestamp(14) NOT NULL,
  PRIMARY KEY  (`codformadepago`),
  CONSTRAINT `refFormaPago` FOREIGN KEY (`codformadepago`) REFERENCES `formadepago` (`codformadepago`)
) TYPE=InnoDB COMMENT='Complementario de formas de pago en Punto Agil';
#
# Dumping Table Structure for puntoAgilOperacion

#
CREATE TABLE `puntoAgilOperacion` (
  `numtienda` smallint(6) NOT NULL default '0',
  `numcaja` smallint(3) unsigned NOT NULL default '0',
  `vtId` varchar(10) NOT NULL default '',
  `numSeq` int(10) unsigned NOT NULL default '0',
  `tipoProceso` decimal(2,0) unsigned NOT NULL default '0',
  `codformadepago` varchar(10) default NULL,
  `numproceso` int(11) default NULL,
  `numservicio` int(11) default NULL,
  `correlativoPagoProceso` smallint(2) default NULL,
  `codCajero` varchar(8) NOT NULL default '',
  `fecha` date NOT NULL default '0000-00-00',
  `horaInicia` time NOT NULL default '00:00:00',
  `horaFinaliza` time NOT NULL default '00:00:00',
  `de_cedulaCliente` varchar(8) default NULL,
  `de_monto` decimal(13,2) default NULL,
  `de_tipoCuenta` char(2) default NULL,
  `de_ctaEspecial` char(2) default NULL,
  `de_nroCuenta` decimal(20,0) default NULL,
  `de_nroCheque` decimal(10,0) default NULL,
  `de_numSeqAnular` int(10) unsigned default NULL,
  `de_planCreditoEPA` char(2) default NULL,
  `de_porcentajeProvimillas` decimal(3,0) default NULL,
  `dt_numeroTarjeta` varchar(20) default NULL,
  `dt_nombreCliente` varchar(45) default NULL,
  `dt_TipoTarjeta` char(1) default NULL,
  `do_codRespuesta` varchar(4) default NULL,
  `do_mensajeRespuesta` varchar(255) default NULL,
  `do_mensajeError` varchar(255) default NULL,
  `do_nombreAutorizador` varchar(255) default NULL,
  `do_numeroAutorizacion` varchar(10) default NULL,
  `do_nombreVoucher` varchar(255) default NULL,
  `status` char(1) NOT NULL default 'V',
  `tipoOperacion` decimal(1,0) NOT NULL default '0',
  `regActualizado` char(1) NOT NULL default 'C',
  PRIMARY KEY  (`numtienda`,`numcaja`,`vtId`,`numSeq`),
  UNIQUE KEY `idx_pa_operacion` (`vtId`,`numSeq`)
) TYPE=InnoDB PACK_KEYS=1 ROW_FORMAT=DYNAMIC COMMENT='Operaciones (Transacciones) de Punto Agil';
#
# Dumping Table Structure for puntoAgilPlanCreditoEPA

#
CREATE TABLE `puntoAgilPlanCreditoEPA` (
  `idPuntoAgilPlanCreditoEPA` int(10) unsigned NOT NULL auto_increment,
  `planCredito` char(2) NOT NULL default '',
  `descripcion` varchar(45) NOT NULL default '',
  `requiereDatoAdicional` char(1) NOT NULL default 'N',
  `abrPlanCredito` char(1) NOT NULL default '',
  `regvigente` char(1) NOT NULL default 'S',
  `fechaActualizacion` timestamp(14) NOT NULL,
  PRIMARY KEY  (`idPuntoAgilPlanCreditoEPA`)
) TYPE=InnoDB COMMENT='Planes para Credito EPA';
#
# Dumping Table Structure for puntoAgilProcesoEstadoCaja

#
CREATE TABLE `puntoAgilProcesoEstadoCaja` (
  `idestado` char(2) NOT NULL default '',
  `tipoProceso` decimal(2,0) unsigned NOT NULL default '0',
  PRIMARY KEY  (`idestado`),
  CONSTRAINT `FK_puntoAgilEdoCaja` FOREIGN KEY (`idestado`) REFERENCES `estadodecaja` (`idestado`)
) TYPE=InnoDB COMMENT='Estado de Caja para determinar tipo proceso (venta, abono, p';
#
# Dumping Table Structure for puntoAgilTipoCuenta

#
CREATE TABLE `puntoAgilTipoCuenta` (
  `idPuntoAgilTipoCuenta` int(10) unsigned NOT NULL auto_increment,
  `tipoCuentaPuntoAgil` char(1) NOT NULL default '',
  `descripcion` varchar(45) NOT NULL default '',
  `abrTipoCuenta` char(1) NOT NULL default '',
  `regvigente` char(1) NOT NULL default 'S',
  `fechaActualizacion` timestamp(14) NOT NULL,
  PRIMARY KEY  (`idPuntoAgilTipoCuenta`)
) TYPE=InnoDB COMMENT='Tipos de Cuenta para Operaciones de Debito';
#
# Dumping Table Structure for puntoagilcaja

#
CREATE TABLE `puntoagilcaja` (
  `numtienda` smallint(6) NOT NULL default '0',
  `numcaja` smallint(3) NOT NULL default '0',
  `fechaCierreMerchant` timestamp(14) NOT NULL,
  PRIMARY KEY  (`numtienda`,`numcaja`),
  CONSTRAINT `FK_puntoAgilCaja_1` FOREIGN KEY (`numtienda`, `numcaja`) REFERENCES `caja` (`numtienda`, `numcaja`)
) TYPE=InnoDB COMMENT='Informacion de la caja con respecto al punto agil';
#
# Dumping Table Structure for reciboemitido

#
CREATE TABLE `reciboemitido` (
  `numtienda` smallint(6) NOT NULL default '0',
  `numcaja` smallint(3) NOT NULL default '0',
  `numrecibo` int(11) NOT NULL default '0',
  `fechaemision` date NOT NULL default '0000-00-00',
  `codcajero` varchar(8) NOT NULL default '',
  `montorecibo` decimal(13,2) NOT NULL default '0.00',
  `motivo` varchar(50) NOT NULL default '',
  `numtransaccionventa` int(11) default NULL,
  `regactualizado` char(1) NOT NULL default 'N',
  PRIMARY KEY  (`numtienda`,`numcaja`,`numrecibo`,`fechaemision`),
  KEY `idx_actualizados` (`regactualizado`),
  KEY `idx_cajero` (`numtienda`,`codcajero`),
  CONSTRAINT `0_7420` FOREIGN KEY (`numtienda`, `numcaja`) REFERENCES `caja` (`numtienda`, `numcaja`),
  CONSTRAINT `0_7421` FOREIGN KEY (`numtienda`, `codcajero`) REFERENCES `usuario` (`numtienda`, `numficha`)
) TYPE=InnoDB COMMENT='Tabla referencial de los recibos emitidos en caja';
#
# Dumping Table Structure for regalosregistrados

#
CREATE TABLE `regalosregistrados` (
  `cantidad` double(13,5) NOT NULL default '0.00000',
  `numTransaccion` int(11) NOT NULL default '0',
  `numCaja` smallint(3) NOT NULL default '0',
  `numTienda` smallint(6) NOT NULL default '0',
  `fechaTransaccion` date NOT NULL default '0000-00-00',
  `codPromocion` int(11) NOT NULL default '0',
  `numDetalle` int(11) NOT NULL default '0',
  `monto` double(13,5) NOT NULL default '0.00000',
  `regactualizado` char(1) default 'N',
  PRIMARY KEY  (`numTransaccion`,`numCaja`,`numTienda`,`fechaTransaccion`,`codPromocion`,`numDetalle`),
  KEY `DetPromo` (`codPromocion`,`numDetalle`),
  KEY `trans` (`numTienda`,`fechaTransaccion`,`numCaja`,`numTransaccion`),
  KEY `IXFK_regalosregistrados` (`numTienda`,`fechaTransaccion`,`numCaja`,`numTransaccion`),
  CONSTRAINT `FK_RegalosRegistrados_1` FOREIGN KEY (`codPromocion`, `numDetalle`) REFERENCES `detallepromocionext` (`codPromocion`, `numDetalle`),
  CONSTRAINT `FK_regalosregistrados_2` FOREIGN KEY (`numTienda`, `fechaTransaccion`, `numCaja`, `numTransaccion`) REFERENCES `transaccion` (`numtienda`, `fecha`, `numcajafinaliza`, `numtransaccion`) ON DELETE CASCADE ON UPDATE CASCADE
) TYPE=InnoDB;
#
# Dumping Table Structure for region

#
CREATE TABLE `region` (
  `codregion` char(3) NOT NULL default '',
  `descripcion` varchar(30) NOT NULL default '',
  PRIMARY KEY  (`codregion`)
) TYPE=InnoDB COMMENT='Maestro de regiones de venta';
#
# Dumping Table Structure for saldocliente

#
CREATE TABLE `saldocliente` (
  `codcliente` varchar(12) binary NOT NULL default '',
  `saldo` decimal(13,2) NOT NULL default '0.00',
  `saldobloqueado` char(1) NOT NULL default 'N',
  `actualizacion` timestamp(14) NOT NULL,
  `codcajero` varchar(8) NOT NULL default '',
  PRIMARY KEY  (`codcliente`),
  KEY `idx_actualizacion` (`actualizacion`),
  KEY `idx_cajero` (`codcajero`),
  CONSTRAINT `saldocliente_ibfk_2` FOREIGN KEY (`codcajero`) REFERENCES `usuario` (`numficha`),
  CONSTRAINT `saldocliente_ibfk_3` FOREIGN KEY (`codcliente`) REFERENCES `afiliado` (`codafiliado`) ON UPDATE CASCADE
) TYPE=InnoDB COMMENT='Entidad referencial del saldo por condicionales/acreencias (';
#
# Dumping Table Structure for servicio

#
CREATE TABLE `servicio` (
  `numtienda` smallint(6) NOT NULL default '0',
  `codtiposervicio` char(2) NOT NULL default '',
  `numservicio` int(11) NOT NULL default '0',
  `fecha` date NOT NULL default '0000-00-00',
  `codcliente` varchar(12) default NULL,
  `montobase` decimal(13,2) NOT NULL default '0.00',
  `montoimpuesto` decimal(13,2) NOT NULL default '0.00',
  `lineasfacturacion` smallint(6) NOT NULL default '0',
  `condicionabono` char(1) default NULL,
  `codcajero` varchar(8) default '',
  `numtransaccionventa` int(11) default NULL,
  `fechatransaccionvnta` date default '0000-00-00',
  `numcajaventa` smallint(3) default NULL,
  `direcciondespacho` varchar(100) default NULL,
  `cambiadireccion` char(1) default 'N',
  `horainicia` time NOT NULL default '00:00:00',
  `horafinaliza` time default '00:00:00',
  `estadoservicio` char(1) NOT NULL default 'V',
  `numcajaanula` smallint(3) default NULL,
  `codusuarioanula` varchar(8) default NULL,
  `regactualizado` char(1) NOT NULL default 'N',
  `tipoapartado` smallint(6) default '0',
  `fechavence` date default NULL,
  PRIMARY KEY  (`numtienda`,`codtiposervicio`,`numservicio`,`fecha`),
  KEY `idx_estadoservicio` (`estadoservicio`),
  KEY `idx_cliente` (`codcliente`),
  KEY `idx_cajero` (`numtienda`,`codcajero`),
  KEY `idx_codtiposervicio` (`codtiposervicio`),
  CONSTRAINT `0_7424` FOREIGN KEY (`codtiposervicio`) REFERENCES `tiposervicio` (`codtiposervicio`) ON DELETE CASCADE,
  CONSTRAINT `0_7426` FOREIGN KEY (`numtienda`) REFERENCES `tienda` (`numtienda`)
) TYPE=InnoDB COMMENT='Maestro de servicios disponibles';
#
# Dumping Table Structure for servidortienda

#
CREATE TABLE `servidortienda` (
  `numtienda` smallint(6) NOT NULL default '0',
  `dbclase` varchar(50) default NULL,
  `dburlservidor` varchar(50) NOT NULL default '',
  `dbusuario` varchar(30) default NULL,
  `dbclave` varchar(30) default NULL,
  `actualizacion` timestamp(14) NOT NULL,
  PRIMARY KEY  (`numtienda`)
) TYPE=InnoDB;
#
# Dumping Table Structure for solicitudcliente

#
CREATE TABLE `solicitudcliente` (
  `numtienda` smallint(6) NOT NULL default '0',
  `fechainicio` date NOT NULL default '1900-01-01',
  `fechafinal` date NOT NULL default '1900-01-01',
  `actualizacion` timestamp(14) NOT NULL,
  PRIMARY KEY  (`numtienda`,`actualizacion`)
) TYPE=InnoDB;
#
# Dumping Table Structure for tienda

#
CREATE TABLE `tienda` (
  `numtienda` smallint(6) NOT NULL default '0',
  `nombresucursal` varchar(30) NOT NULL default '',
  `razonsocial` varchar(30) NOT NULL default '',
  `rif` varchar(12) NOT NULL default '',
  `nit` varchar(12) NOT NULL default '',
  `direccion` varchar(100) NOT NULL default '',
  `codarea` varchar(4) default NULL,
  `numtelefono` varchar(9) default NULL,
  `codareafax` varchar(4) default NULL,
  `numfax` varchar(9) default NULL,
  `direccionfiscal` varchar(100) NOT NULL default '',
  `codareafiscal` varchar(4) default NULL,
  `numtelefonofiscal` varchar(9) default NULL,
  `codareafaxfiscal` varchar(4) default NULL,
  `numfaxfiscal` varchar(9) default NULL,
  `monedabase` char(3) default NULL,
  `codregion` char(3) NOT NULL default '000',
  `limiteentregacaja` decimal(13,2) NOT NULL default '0.00',
  `tipoimpuestoaplicar` char(1) NOT NULL default '',
  `indicadesctoempleado` char(1) NOT NULL default '',
  `desctoventaempleado` decimal(5,2) NOT NULL default '0.00',
  `cambioprecioencaja` char(1) NOT NULL default '',
  `fechatienda` date NOT NULL default '0000-00-00',
  `utilizarvendedor` char(1) NOT NULL default 'N',
  `desctosacumulativos` char(1) NOT NULL default 'N',
  `dbclase` varchar(100) default NULL,
  `dburlservidor` varchar(100) default NULL,
  `dbusuario` varchar(30) default NULL,
  `dbclave` varchar(30) default NULL,
  PRIMARY KEY  (`numtienda`),
  KEY `idx_region` (`codregion`),
  KEY `idx_tienda` (`numtienda`),
  CONSTRAINT `0_7429` FOREIGN KEY (`codregion`) REFERENCES `region` (`codregion`)
) TYPE=InnoDB COMMENT='Maestro de tienda';
#
# Dumping Table Structure for tiendapublicidad

#
CREATE TABLE `tiendapublicidad` (
  `numtienda` smallint(6) NOT NULL default '0',
  `publicidadlinea` varchar(40) NOT NULL default '0',
  `orden` smallint(6) NOT NULL default '0',
  PRIMARY KEY  (`numtienda`,`orden`),
  CONSTRAINT `0_7431` FOREIGN KEY (`numtienda`) REFERENCES `tienda` (`numtienda`)
) TYPE=InnoDB COMMENT='Tabla con líneas publicitarias para la factura';
#
# Dumping Table Structure for tipoapartado

#
CREATE TABLE `tipoapartado` (
  `codigo` smallint(6) NOT NULL default '0',
  `descripcion` varchar(50) NOT NULL default '',
  `fechadesde` date default '0000-00-00',
  `fechahasta` date default '0000-00-00',
  `fechavence` date default '0000-00-00',
  PRIMARY KEY  (`codigo`)
) TYPE=InnoDB;
#
# Dumping Table Structure for tipocaptura

#
CREATE TABLE `tipocaptura` (
  `codtipocaptura` char(2) NOT NULL default '',
  `descripcion` varchar(50) default NULL,
  PRIMARY KEY  (`codtipocaptura`)
) TYPE=InnoDB COMMENT='Tabla referencial para tipos de captura de los códigos';
#
# Dumping Table Structure for tipoeventolistaregalos

#
CREATE TABLE `tipoeventolistaregalos` (
  `codtipoevento` char(2) NOT NULL default '',
  `descripcion` varchar(30) NOT NULL default '',
  `actualizacion` timestamp(14) NOT NULL,
  PRIMARY KEY  (`codtipoevento`)
) TYPE=InnoDB;
#
# Dumping Table Structure for tiposervicio

#
CREATE TABLE `tiposervicio` (
  `codtiposervicio` char(2) NOT NULL default '',
  `descripcion` varchar(30) default NULL,
  `correlativo` int(11) NOT NULL default '0',
  PRIMARY KEY  (`codtiposervicio`)
) TYPE=InnoDB COMMENT='Tabla referencial de los tipos de servicio disponibles';
#
# Dumping Table Structure for transaccion

#
CREATE TABLE `transaccion` (
  `numtienda` smallint(6) NOT NULL default '0',
  `fecha` date NOT NULL default '0000-00-00',
  `numcajainicia` smallint(3) NOT NULL default '0',
  `numregcajainicia` int(11) NOT NULL default '0',
  `numcajafinaliza` smallint(3) default NULL,
  `numtransaccion` int(11) default NULL,
  `tipotransaccion` char(1) NOT NULL default '',
  `numcomprobantefiscal` int(8) default NULL,
  `horainicia` time NOT NULL default '00:00:00',
  `horafinaliza` time NOT NULL default '00:00:00',
  `codcliente` varchar(12) default NULL,
  `codcajero` varchar(8) NOT NULL default '',
  `codigofacturaespera` varchar(8) default NULL,
  `montobase` decimal(13,2) NOT NULL default '0.00',
  `montoimpuesto` decimal(13,2) NOT NULL default '0.00',
  `vueltocliente` decimal(13,2) NOT NULL default '0.00',
  `montoremanente` decimal(13,2) NOT NULL default '0.00',
  `lineasfacturacion` smallint(6) NOT NULL default '0',
  `cajaenlinea` char(1) NOT NULL default 'S',
  `serialcaja` varchar(20) default 'S/N',
  `duracionventa` smallint(6) NOT NULL default '0',
  `duracionpago` smallint(6) NOT NULL default '0',
  `checksum` int(11) default NULL,
  `estadotransaccion` char(1) NOT NULL default '',
  `regactualizado` char(1) NOT NULL default 'N',
  `codautorizante` varchar(8) default NULL,
  `numimpnotadespacho` int(10) unsigned NOT NULL default '0',
  `numimpnotaentrega` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`numtienda`,`fecha`,`numcajainicia`,`numregcajainicia`),
  UNIQUE KEY `idx_transaccion` (`numtienda`,`fecha`,`numcajafinaliza`,`numtransaccion`),
  KEY `idx_cliente` (`codcliente`),
  KEY `idx_tienda` (`numtienda`,`tipotransaccion`),
  KEY `idx_actualizados` (`regactualizado`),
  KEY `idx_estadotransaccion` (`estadotransaccion`),
  KEY `idx_cajainicia` (`numtienda`,`numcajainicia`),
  KEY `idx_cajafinaliza` (`numtienda`,`numcajafinaliza`),
  KEY `idx_facturaespera` (`codigofacturaespera`),
  KEY `idx_cajero` (`numtienda`,`codcajero`)
) TYPE=InnoDB COMMENT='Cabecera de transaciones efectuadas';
#
# Dumping Table Structure for transaccionabono

#
CREATE TABLE `transaccionabono` (
  `numtienda` smallint(6) NOT NULL default '0',
  `numcaja` smallint(3) NOT NULL default '0',
  `fecha` date NOT NULL default '0000-00-00',
  `numabono` int(11) NOT NULL default '0',
  `numservicio` int(11) NOT NULL default '0',
  `tipotransaccionabono` char(1) NOT NULL default '',
  `codtiposervicio` char(2) NOT NULL default '',
  `fechaservicio` date NOT NULL default '0000-00-00',
  `codcajero` varchar(8) NOT NULL default '',
  `horainicia` time NOT NULL default '00:00:00',
  `horafinaliza` time default NULL,
  `monto` decimal(13,2) NOT NULL default '0.00',
  `vueltocliente` decimal(13,2) NOT NULL default '0.00',
  `montoremanente` decimal(13,2) NOT NULL default '0.00',
  `cajaenlinea` char(1) NOT NULL default 'N',
  `serialcaja` varchar(20) default 'S/N',
  `estadoabono` char(1) NOT NULL default '',
  `regactualizado` char(1) NOT NULL default 'N',
  PRIMARY KEY  (`numtienda`,`numcaja`,`fecha`,`numabono`,`numservicio`),
  KEY `idx_transaccionabonocaja` (`tipotransaccionabono`,`numcaja`,`fecha`),
  KEY `idx_servicio` (`numtienda`,`codtiposervicio`,`numservicio`,`fechaservicio`),
  KEY `idx_cajero` (`numtienda`,`codcajero`),
  KEY `idx_estadoabono` (`estadoabono`),
  KEY `idx_actualizados` (`regactualizado`),
  KEY `idx_caja` (`numtienda`,`numcaja`),
  KEY `idx_tiposervicio` (`codtiposervicio`),
  CONSTRAINT `0_7438` FOREIGN KEY (`numtienda`, `numcaja`) REFERENCES `caja` (`numtienda`, `numcaja`),
  CONSTRAINT `0_7439` FOREIGN KEY (`numtienda`, `codcajero`) REFERENCES `usuario` (`numtienda`, `numficha`),
  CONSTRAINT `0_7440` FOREIGN KEY (`numtienda`, `codtiposervicio`, `numservicio`, `fechaservicio`) REFERENCES `servicio` (`numtienda`, `codtiposervicio`, `numservicio`, `fecha`) ON DELETE CASCADE
) TYPE=InnoDB COMMENT='Cabecera de transacciones de abono efectuadas';
#
# Dumping Table Structure for transaccionabonolr

#
CREATE TABLE `transaccionabonolr` (
  `numtienda` smallint(6) NOT NULL default '0',
  `numcaja` smallint(3) NOT NULL default '0',
  `fecha` date NOT NULL default '0000-00-00',
  `numabono` int(11) NOT NULL default '0',
  `numservicio` int(11) NOT NULL default '0',
  `tipotransaccionabono` char(1) NOT NULL default '',
  `codtiposervicio` char(2) NOT NULL default '',
  `fechaservicio` date NOT NULL default '0000-00-00',
  `codcajero` varchar(8) NOT NULL default '',
  `horainicia` time NOT NULL default '00:00:00',
  `horafinaliza` time default NULL,
  `monto` decimal(13,2) NOT NULL default '0.00',
  `vueltocliente` decimal(13,2) NOT NULL default '0.00',
  `montoremanente` decimal(13,2) NOT NULL default '0.00',
  `cajaenlinea` char(1) NOT NULL default 'N',
  `serialcaja` varchar(20) default 'S/N',
  `estadoabono` char(1) NOT NULL default '',
  `regactualizado` char(1) NOT NULL default 'N',
  PRIMARY KEY  (`numtienda`,`numcaja`,`fecha`,`numabono`,`numservicio`),
  KEY `idx_transaccionabonocajalr` (`tipotransaccionabono`,`numcaja`,`fecha`),
  KEY `idx_serviciolr` (`numtienda`,`codtiposervicio`,`numservicio`,`fechaservicio`),
  KEY `idx_cajerolr` (`numtienda`,`codcajero`),
  KEY `idx_estadoabonolr` (`estadoabono`),
  KEY `idx_actualizadoslr` (`regactualizado`),
  KEY `idx_cajalr` (`numtienda`,`numcaja`),
  KEY `idx_tiposerviciolr` (`codtiposervicio`),
  CONSTRAINT `0_7438lr` FOREIGN KEY (`numtienda`, `numcaja`) REFERENCES `caja` (`numtienda`, `numcaja`),
  CONSTRAINT `0_7439lr` FOREIGN KEY (`numtienda`, `codcajero`) REFERENCES `usuario` (`numtienda`, `numficha`)
) TYPE=InnoDB COMMENT='Cabecera de transacciones de abono efectuadas a listas de re';
#
# Dumping Table Structure for transaccionafiliadocrm

#
CREATE TABLE `transaccionafiliadocrm` (
  `numtienda` smallint(6) NOT NULL default '0',
  `fechatransaccion` date NOT NULL default '0000-00-00',
  `numcajafinaliza` smallint(3) NOT NULL default '0',
  `numtransaccion` int(11) NOT NULL default '0',
  `codafiliado` varchar(12) binary NOT NULL default '',
  `contribuyente` char(1) NOT NULL default 'N',
  `regactualizado` char(1) NOT NULL default 'N',
  `horainiciacrm` time default NULL,
  `horafinalizacrm` time default NULL,
  PRIMARY KEY  (`numtienda`,`fechatransaccion`,`numcajafinaliza`,`numtransaccion`,`codafiliado`),
  KEY `IDX_transaccionafiliadocrm_1` (`numtienda`,`fechatransaccion`,`numcajafinaliza`,`numtransaccion`),
  KEY `IDX_transaccionafiliadocrm_2` (`codafiliado`),
  CONSTRAINT `FK_transaccionafiliadocrm_1` FOREIGN KEY (`numtienda`, `fechatransaccion`, `numcajafinaliza`, `numtransaccion`) REFERENCES `transaccion` (`numtienda`, `fecha`, `numcajafinaliza`, `numtransaccion`)
) TYPE=InnoDB COMMENT='Está tabla tendrá los afiliados de CRM';
#

# Dumping Table Structure for transaccionpremiada

#
CREATE TABLE `transaccionpremiada` (
  `horaGanador` timestamp(14) NOT NULL,
  `premioPorEntregar` int(11) NOT NULL default '0',
  `numTienda` smallint(6) NOT NULL default '0',
  `fecha` date default NULL,
  `numCaja` smallint(3) default NULL,
  `numTransaccion` int(11) default NULL,
  `codPromocion` int(11) NOT NULL default '0',
  `numDetalle` int(11) NOT NULL default '0',
  `regactualizado` char(1) default 'N',
  PRIMARY KEY  (`horaGanador`,`numTienda`),
  UNIQUE KEY `IDX_transaccionpremiada_3` (`numTienda`,`fecha`,`numCaja`,`numTransaccion`),
  KEY `IDX_transaccionpremiada_2` (`codPromocion`,`numDetalle`),
  KEY `IXFK_transaccionpremiada` (`numTienda`,`fecha`,`numCaja`,`numTransaccion`),
  CONSTRAINT `FK_transaccionpremiada_2` FOREIGN KEY (`numTienda`, `fecha`, `numCaja`, `numTransaccion`) REFERENCES `transaccion` (`numtienda`, `fecha`, `numcajafinaliza`, `numtransaccion`) ON DELETE CASCADE ON UPDATE CASCADE
) TYPE=InnoDB;
#
# Dumping Table Structure for unidaddeventa

#
CREATE TABLE `unidaddeventa` (
  `codunidadventa` int(11) NOT NULL default '0',
  `descripcion` varchar(20) NOT NULL default '',
  `abreviado` char(3) NOT NULL default '',
  `indicafraccion` char(1) NOT NULL default 'N',
  PRIMARY KEY  (`codunidadventa`)
) TYPE=InnoDB COMMENT='Maestro de unidades de venta disponibles';
#
# Dumping Table Structure for usuario

#
CREATE TABLE `usuario` (
  `numtienda` smallint(6) NOT NULL default '0',
  `numficha` varchar(8) NOT NULL default '',
  `codigobarra` varchar(32) NOT NULL default '',
  `codperfil` char(3) NOT NULL default '',
  `clave` varchar(32) NOT NULL default '',
  `nivelauditoria` char(1) NOT NULL default '',
  `nombre` varchar(50) default NULL,
  `puedecambiarclave` char(1) NOT NULL default '',
  `indicacambiarclave` char(1) NOT NULL default '',
  `fechacreacion` date NOT NULL default '0000-00-00',
  `ultimocambioclave` timestamp(14) NOT NULL,
  `tiempovigenciaclave` smallint(6) NOT NULL default '90',
  `regvigente` char(1) NOT NULL default 'S',
  `actualizacion` timestamp(14) NOT NULL default '00000000000000',
  PRIMARY KEY  (`numtienda`,`numficha`),
  UNIQUE KEY `idx_codigobarra` (`codigobarra`),
  UNIQUE KEY `idx_numficha` (`numficha`),
  KEY `idx_tienda` (`numtienda`),
  KEY `idx_perfil` (`codperfil`),
  KEY `idx_actualizacion` (`actualizacion`),
  KEY `idx_vigentes` (`regvigente`),
  CONSTRAINT `0_7444` FOREIGN KEY (`codperfil`) REFERENCES `perfil` (`codperfil`),
  CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`numtienda`) REFERENCES `tienda` (`numtienda`)
) TYPE=InnoDB COMMENT='Maestro de usuarios del sistema de caja registradora';
#
SET FOREIGN_KEY_CHECKS=1

