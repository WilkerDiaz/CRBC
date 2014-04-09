# Se agregan los modulos y las funciones necesarias para el módulo de PDA

use CR;

insert into modulos values (51,'PDA','S','20100128113000');
insert into funcion values (87,'Iniciar venta',51,3,'S','20100128113000','N');
insert into funcion values (88,'Iniciar indicadores',51,3,'S','20100128113000','N');
insert into funcion values (89,'Iniciar transferencia inmediata',51,3,'S','20100128113000','N');
insert into funcion values (90,'Iniciar generación de carteles',51,3,'S','20100128113000','N');
insert into funcion values (91,'Iniciar cotizacion',51,3,'S','20100128113000','N');
INSERT INTO funcion VALUES (92, 'Transferencia Inmediata', 22, '3', 'S', '20100422162126', 'N');
insert into metodos values (99,'VENTAS');
insert into metodos values (100,'INDICADORES');
insert into metodos values (101,'SOLICITUDDECODIGO');
insert into metodos values (102,'CARTELES');
insert into metodos values (103,'COTIZACIONES');
insert into metodos values (104,'TRANSFERIRPRODUCTO');
insert into funcionmetodos values (51,87,99);
insert into funcionmetodos values (51,88,100);
insert into funcionmetodos values (51,89,101);
insert into funcionmetodos values (51,90,102);
insert into funcionmetodos values (51,91,103);
INSERT INTO funcionmetodos VALUES (22, 92, 104);

#Se agregan las funciones al perfil 4 y 3

insert into funcionperfil values (3,87,'S','S',20100129041530,51);
insert into funcionperfil values (3,88,'S','S',20100129041530,51);
insert into funcionperfil values (3,89,'S','S',20100129041530,51);
insert into funcionperfil values (3,90,'S','S',20100129041530,51);
insert into funcionperfil values (3,91,'S','S',20100129041530,51);
insert into funcionperfil values (4,87,'S','S',20100129041530,51);
insert into funcionperfil values (4,88,'S','S',20100129041530,51);
insert into funcionperfil values (4,89,'S','S',20100129041530,51);
insert into funcionperfil values (4,90,'S','S',20100129041530,51);
insert into funcionperfil values (4,91,'S','S',20100129041530,51);
INSERT INTO funcionperfil VALUES (3,92,'S','S','20100416041530', 22);
INSERT INTO funcionperfil VALUES (4,92,'S','S','20100416041530', 22);

# Habilita a los usuarios de perfil 3 y 4 a acceder a las funciones de cotizaciones desde caja

UPDATE funcionperfil SET habilitado='S', autorizado='S' WHERE codperfil = 4 AND codmodulo = 48;
UPDATE funcionperfil SET habilitado='S', autorizado='S' WHERE codperfil = 3 AND codmodulo = 48;

# Evita la solicitud de autorizacion para la recuperacion de cotizaciones

update funcion set reqautorizacion='N' where codfuncion = 56;

#Creación de tabla para auditoría PDA

drop table CR.auditoriapda;
create table CR.auditoriapda(
    id INT not null AUTO_INCREMENT,
	nombremodulo varchar(100) not null,
	nombreoperacion varchar(100) not null,
	fecha timestamp not null,
	usuario varchar(100),
	primary key(id),
	KEY idx_id(id)
)TYPE=InnoDB COMMENT='';