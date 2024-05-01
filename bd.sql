-- Tabla Postulante
CREATE TABLE postulante (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(25),
    apellido_paterno VARCHAR(25),
    apellido_materno VARCHAR(25),
    dni VARCHAR(20),
    genero VARCHAR(1),
    fecha_nac DATE,
    correo VARCHAR(50),
    telefono VARCHAR(11)
);

-- Tabla Red_social
CREATE TABLE red_social (
    id SERIAL PRIMARY KEY,
    id_postulante INT,
    FOREIGN KEY (id_postulante) REFERENCES postulante(id),
    link VARCHAR(50)
);

-- Tabla Empresa
CREATE TABLE empresa (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(20),
    sector VARCHAR(20),
    numero_contacto VARCHAR(11),
    descripcion VARCHAR(100)
);

-- Tabla reclutador//representante
CREATE TABLE reclutador (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(25),
    apellido_paterno VARCHAR(25),
    apellido_materno VARCHAR(25),
    genero VARCHAR(1),
    dni VARCHAR(20),
    cargo VARCHAR(20),
    departamento VARCHAR(10),
    fecha_nac DATE,
    id_empresa INT,
    FOREIGN KEY (id_empresa) REFERENCES empresa(id)
);

-- Tabla Tipo_vacante
CREATE TABLE tipo_vacante (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100)
);

-- Tabla Vacante_Laboral
CREATE TABLE vacante_laboral (
    id SERIAL PRIMARY KEY,
    fecha_publicacion DATE,
    fecha_final DATE,
    horario VARCHAR(10),
    estado_oferta VARCHAR(15),
    ubicacion VARCHAR(30),
    descripcion VARCHAR(100),
    id_reclutador INT,
    FOREIGN KEY (id_reclutador) REFERENCES reclutador(id),
    id_tipo_vacante INT,
    FOREIGN KEY (id_tipo_vacante) REFERENCES tipo_vacante(id)
);

-- Tabla Seguimiento_Entrevista (Tabla Intermedia)
CREATE TABLE seguimiento_entrevista (
    id SERIAL PRIMARY KEY,
    fecha DATE,
    estado VARCHAR(15),
    comentarios VARCHAR(50),
    id_postulante INT,
    FOREIGN KEY (id_postulante) REFERENCES postulante(id),
    id_vacante INT,
    FOREIGN KEY (id_vacante) REFERENCES vacante_laboral(id)
);

-- Tabla Educacion
CREATE TABLE educacion (
    id SERIAL PRIMARY KEY,
    Titulo VARCHAR(20),
    nombre_institucion VARCHAR(20),
    nivel_educativo VARCHAR(10),
    area_esp VARCHAR(20),
    prom_pond DECIMAL(4,2),
    fecha_inicio DATE,
    fecha_fin DATE,
    id_postulante INT,
    FOREIGN KEY (id_postulante) REFERENCES postulante(id)
);