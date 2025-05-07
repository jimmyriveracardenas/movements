CREATE DATABASE "Bank"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LOCALE_PROVIDER = 'libc'
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;
	
	

BEGIN;

-- 1. Tabla Persona (clase base)
CREATE TABLE persona (
    persona_id     SERIAL PRIMARY KEY,
    nombre         VARCHAR(100)  NOT NULL,
    genero         CHAR(1)       
      CHECK (genero IN ('M','F','O')),
    edad           INTEGER       CHECK (edad >= 0),
    identificacion VARCHAR(50)   NOT NULL UNIQUE,
    direccion      TEXT,
    telefono       VARCHAR(20)
);

-- 2. Tabla Cliente (hereda de Persona por FK uno-a-uno)
CREATE TABLE cliente (
    cliente_id   SERIAL PRIMARY KEY,
    persona_id   INTEGER NOT NULL UNIQUE
      REFERENCES persona(persona_id) ON DELETE CASCADE,
    contrasena   VARCHAR(255) NOT NULL,
    estado       BOOLEAN      NOT NULL DEFAULT TRUE
);

-- 3. Tabla Cuenta
CREATE TABLE cuenta (
    numero_cuenta   VARCHAR(50)     PRIMARY KEY,
    tipo_cuenta     VARCHAR(50)     NOT NULL,          -- ej. 'Ahorro', 'Corriente'
    saldo_inicial   NUMERIC(12,2)   NOT NULL DEFAULT 0,
    estado          BOOLEAN         NOT NULL DEFAULT TRUE,
    cliente_id      INTEGER         NOT NULL
      REFERENCES cliente(cliente_id) ON DELETE RESTRICT
);

-- 4. Tabla Movimientos
CREATE TABLE movimiento (
    movimiento_id    SERIAL PRIMARY KEY,
    fecha            TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    tipo_movimiento  VARCHAR(50)     NOT NULL,         -- ej. 'Depósito', 'Retiro'
    valor            NUMERIC(12,2)   NOT NULL,
    saldo            NUMERIC(12,2)   NOT NULL,         
    numero_cuenta    VARCHAR(50)     NOT NULL
      REFERENCES cuenta(numero_cuenta) ON DELETE CASCADE
);

COMMIT;



-- 1. Insertar en persona
INSERT INTO persona (nombre, genero, edad, identificacion, direccion, telefono) VALUES
  ('Juan Ponce',    'M', 35, '1712131415', 'Av. Eloy, Quito',    '0998765432'),
  ('Ana Loaiza','F', 28, '1703456545', 'Calle Eucaliptos, Guayaquil',       '0987654321');

-- 2. Insertar en cliente (cada cliente referencia a una persona existente)
INSERT INTO cliente (persona_id, contrasena, estado) VALUES
  (1, 'secret123', TRUE),
  (2, 'pass456',   TRUE);

-- 3. Insertar en cuenta (asociadas a los clientes)
INSERT INTO cuenta (numero_cuenta, tipo_cuenta, saldo_inicial, estado, cliente_id) VALUES
  ('C0010001', 'Ahorro',    1000.00, TRUE,  1),
  ('C0010002', 'Corriente',  500.00, TRUE,  1),
  ('C0020001', 'Ahorro',    2000.00, TRUE, 2);



