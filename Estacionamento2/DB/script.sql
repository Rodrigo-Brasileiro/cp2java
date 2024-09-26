DROP DATABASE IF EXISTS SCRIPT;
CREATE DATABASE SCRIPT;
USE SCRIPT;

CREATE TABLE CARRO (
  ID INT AUTO_INCREMENT NOT NULL,
  MARCA VARCHAR(15) NOT NULL,
  MODELO VARCHAR(15) NOT NULL,
  PLACA VARCHAR(7) NOT NULL,
  HORARIO_ENTRADA TIME NOT NULL,
  PRIMARY KEY (ID)
);

INSERT INTO CARRO (MARCA, MODELO, PLACA, HORARIO_ENTRADA) 
VALUES ('Toyota', 'Corolla', 'ABC1234', '14:30:00');

INSERT INTO CARRO (MARCA, MODELO, PLACA, HORARIO_ENTRADA) 
VALUES ('Nissan', 'March', 'FNR2ABC', '15:30:00');

SELECT * FROM CARRO;
