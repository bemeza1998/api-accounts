CREATE DATABASE banquito;

USE banquito;

CREATE TABLE `persona` (
  `edad` int NOT NULL,
  `genero` varchar(1) NOT NULL,
  `id_persona` bigint NOT NULL,
  `telefono` varchar(10) NOT NULL,
  `identificacion` varchar(13) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `direccion` varchar(200) NOT NULL,
  PRIMARY KEY (`id_persona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `cliente` (
  `id_persona` varchar(8) NOT NULL,
  `id_cliente` bigint NOT NULL,
  `edad` int NOT NULL,
  `identificacion` varchar(13) NOT NULL,
  `contrasena` varchar(30) NOT NULL,
  `direccion` varchar(200) NOT NULL,
  `estado` varchar(3) NOT NULL,
  `genero` varchar(1) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `telefono` varchar(10) NOT NULL,
  PRIMARY KEY (`id_persona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- banquito.cuenta definition

CREATE TABLE `cuenta` (
  `numero_cuenta` varchar(10) NOT NULL,
  `saldo` decimal(22,2) NOT NULL,
  `saldo_inicial` decimal(22,2) NOT NULL,
  `estado` varchar(8) NOT NULL,
  `tipo` varchar(12) NOT NULL,
  `id_persona` varchar(8) NOT NULL,
  PRIMARY KEY (`numero_cuenta`),
  FOREIGN KEY (`id_persona`)
        REFERENCES cliente(`id_persona`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- banquito.movimiento definition

CREATE TABLE `movimiento` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `saldo_post_movimiento` decimal(22,2) NOT NULL,
  `valor` decimal(22,2) NOT NULL,
  `fecha` datetime(6) NOT NULL,
  `tipo` varchar(8) NOT NULL,
  `numero_cuenta` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`numero_cuenta`)
        REFERENCES cuenta(`numero_cuenta`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO banquito.cliente (id_persona,id_cliente,edad,identificacion,contrasena,direccion,estado,genero,nombre,telefono) VALUES
	 ('CL-00001',201,30,'1756234569','passwd2','El valle','ACT','F','Maria Perez','0998541256'),
	 ('CL-00003',200,35,'1727432538','passwd','Quitumbe','ACT','M','Bryan Meza Navarrete','0989780185');
	

INSERT INTO banquito.cuenta (numero_cuenta,saldo,saldo_inicial,estado,tipo,id_persona) VALUES
	 ('12345',511.00,500.00,'ACT','Corriente','CL-00001'),
	 ('12346',296.00,100.00,'ACT','Ahorros','CL-00001'),
	 ('85665',5000.00,5000.00,'ACT','Corriente','CL-00003');
	
INSERT INTO banquito.movimiento (saldo_post_movimiento,valor,fecha,tipo,numero_cuenta) VALUES
	 (5050.00,50.00,'2024-10-15 20:45:51.749000','DEPOSITO','85665'),
	 (5100.00,50.00,'2024-10-15 20:45:54.344000','DEPOSITO','85665'),
	 (5150.00,50.00,'2024-10-15 20:45:55.077000','DEPOSITO','85665'),
	 (4584.00,-566.00,'2024-10-15 20:46:05.462000','RETIRO','85665'),
	 (495.00,-5.00,'2024-10-15 21:45:05.793000','RETIRO','12345'),
	 (511.00,16.00,'2024-10-15 21:45:11.418000','DEPOSITO','12345'),
	 (260.00,160.00,'2024-10-15 21:45:17.346000','DEPOSITO','12346'),
	 (161.00,-99.00,'2024-10-15 21:45:22.144000','RETIRO','12346'),
	 (146.00,-15.00,'2024-10-15 21:45:25.610000','RETIRO','12346'),
	 (296.00,150.00,'2024-10-15 21:45:29.344000','DEPOSITO','12346');
