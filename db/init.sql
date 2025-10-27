CREATE TABLE IF NOT EXISTS Usuaris (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    cognoms VARCHAR(150) NOT NULL,
    telefon VARCHAR(20),
    email VARCHAR(150) UNIQUE,
    llibresFavorits TEXT,
    contrasenya VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Biblioteques (
    idBiblioteca INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(150) NOT NULL,
    adreca VARCHAR(255),
    telefon VARCHAR(20),
    email VARCHAR(150),
);

CREATE TABLE IF NOT EXISTS Agents (
    idAgent INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    cognoms VARCHAR(150) NOT NULL,
    telefon VARCHAR(20),
    email VARCHAR(150),
    tipus ENUM('bibliotecari', 'administrador') NOT NULL,
    contrasenya VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Llibres (
    isbn VARCHAR(20) PRIMARY KEY,
    titol VARCHAR(255) NOT NULL,
    autor VARCHAR(150) NOT NULL,
    editorial VARCHAR(150),
    categoria VARCHAR(100),
    sinopsis TEXT,
    imatgeUrl VARCHAR(255),
    exemplars INT NOT NULL DEFAULT 0,
    disponibles INT NOT NULL DEFAULT 0,
    idioma VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS Prestecs (
    idPrestec INT AUTO_INCREMENT PRIMARY KEY,
    idUsuari INT NOT NULL,
    isbn VARCHAR(20) NOT NULL,
    idBiblioteca INT NOT NULL,
    dataPrestec DATETIME NOT NULL,
    dataDevolucio DATETIME,
    idAgentPrestec INT,
    idAgentDevolucio INT,
    estat ENUM('actiu', 'retornat', 'retard', 'perdut') NOT NULL DEFAULT 'actiu',

    CONSTRAINT fk_prestecs_usuaris FOREIGN KEY (idUsuari) REFERENCES Usuaris(id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_prestecs_llibres FOREIGN KEY (isbn) REFERENCES Llibres(isbn)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_prestecs_biblioteca FOREIGN KEY (idBiblioteca) REFERENCES Biblioteques(idBiblioteca)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_prestecs_agent_prestec FOREIGN KEY (idAgentPrestec) REFERENCES Agents(idAgent)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_prestecs_agent_devolucio FOREIGN KEY (idAgentDevolucio) REFERENCES Agents(idAgent)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Reserves (
    idReserva INT AUTO_INCREMENT PRIMARY KEY,
    idUsuari INT NOT NULL,
    isbn VARCHAR(20) NOT NULL,
    dataReserva DATETIME NOT NULL,
    estat ENUM('pendent', 'disponible', 'caducada', 'cancelada') NOT NULL DEFAULT 'pendent',

    CONSTRAINT fk_reserves_usuaris FOREIGN KEY (idUsuari) REFERENCES Usuaris(id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_reserves_llibres FOREIGN KEY (isbn) REFERENCES Llibres(isbn)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS PropostesAdquisicio (
    idProposta INT AUTO_INCREMENT PRIMARY KEY,
    idUsuari INT NOT NULL,
    titol VARCHAR(255) NOT NULL,
    autor VARCHAR(150),
    isbn VARCHAR(20),
    editorial VARCHAR(150),
    motiu TEXT,
    dataProposta DATETIME NOT NULL,
    estat ENUM('pendent', 'acceptada', 'rebutjada', 'comprat') NOT NULL DEFAULT 'pendent',
    resposta TEXT,

    CONSTRAINT fk_propostes_usuaris FOREIGN KEY (idUsuari) REFERENCES Usuaris(id)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

-- DADES INICIALS PER A LA BASE DE DADES 

-- USUARIS
-- Generat amb BCrypt.hashpw("1234", BCrypt.gensalt(12))
-- El hash és: $2a$12$wovjHMsHrLKgh.95YUjxkuEXiF0fLTuQD3tDaBzyl1Lb4lgTcANnO
INSERT INTO Usuaris (nom, cognoms, telefon, email, llibresFavorits, contrasenya) VALUES
('Anna', 'Ribas Soler', '611223344', 'anna@totesbook.cat', 'Moby Dick, El Petit Príncep', '$2a$12$wovjHMsHrLKgh.95YUjxkuEXiF0fLTuQD3tDaBzyl1Lb4lgTcANnO'),
('Marc', 'Pérez Vila', '622334455', 'marc@totesbook.cat', '1984, El Nom de la Rosa', '$2a$12$wovjHMsHrLKgh.95YUjxkuEXiF0fLTuQD3tDaBzyl1Lb4lgTcANnO'),
('Júlia', 'Torra Puig', '633445566', 'julia@totesbook.cat', 'Orgull i Prejudici', '$2a$12$wovjHMsHrLKgh.95YUjxkuEXiF0fLTuQD3tDaBzyl1Lb4lgTcANnO'),
('Oriol', 'Grau Font', '644556677', 'oriol@totesbook.cat', NULL, '$2a$12$wovjHMsHrLKgh.95YUjxkuEXiF0fLTuQD3tDaBzyl1Lb4lgTcANnO'),
('Laura', 'Dalmau Roca', '655667788', 'laura@totesbook.cat', NULL, '$2a$12$wovjHMsHrLKgh.95YUjxkuEXiF0fLTuQD3tDaBzyl1Lb4lgTcANnO'),
('Pau', 'Casas Romeu', '666778899', 'pau@totesbook.cat', NULL, '$2a$12$wovjHMsHrLKgh.95YUjxkuEXiF0fLTuQD3tDaBzyl1Lb4lgTcANnO'),
('Carla', 'Serra Bosch', '677889900', 'carla@totesbook.cat', NULL, '$2a$12$wovjHMsHrLKgh.95YUjxkuEXiF0fLTuQD3tDaBzyl1Lb4lgTcANnO'),
('Nil', 'Costa Mora', '688990011', 'nil@totesbook.cat', NULL, '$2a$12$wovjHMsHrLKgh.95YUjxkuEXiF0fLTuQD3tDaBzyl1Lb4lgTcANnO'),
('Eva', 'Vidal Prat', '699100122', 'eva@totesbook.cat', NULL, '$2a$12$wovjHMsHrLKgh.95YUjxkuEXiF0fLTuQD3tDaBzyl1Lb4lgTcANnO'),
('Roger', 'Balcells Ruiz', '600111233', 'roger@totesbook.cat', NULL, '$2a$12$wovjHMsHrLKgh.95YUjxkuEXiF0fLTuQD3tDaBzyl1Lb4lgTcANnO');

-- BIBLIOTEQUES
INSERT INTO Biblioteques (nom, adreca, telefon, email) VALUES
('Biblioteca Central', 'Carrer Major 1', '934112233', 'central@totesbook.cat'),
('Biblioteca Nord', 'Avinguda del Parc 45', '934223344', 'nord@totesbook.cat'),
('Biblioteca Sud', 'Passeig del Mar 12', '934334455', 'sud@totesbook.cat');

-- AGENTS
INSERT INTO Agents (nom, cognoms, telefon, email, tipus, contrasenya) VALUES
('Marta', 'Vilaseca', '931001001', 'marta@totesbook.cat', 'bibliotecari', '$2a$12$wovjHMsHrLKgh.95YUjxkuEXiF0fLTuQD3tDaBzyl1Lb4lgTcANnO'),
('Joan', 'Arbós', '931002002', 'joan@totesbook.cat', 'bibliotecari', '$2a$12$wovjHMsHrLKgh.95YUjxkuEXiF0fLTuQD3tDaBzyl1Lb4lgTcANnO'),
('Laia', 'Roca', '931003003', 'laia@totesbook.cat', 'administrador', '$2a$12$wovjHMsHrLKgh.95YUjxkuEXiF0fLTuQD3tDaBzyl1Lb4lgTcANnO'),
('Albert', 'Ferrer', '931004004', 'albert@totesbook.cat', 'administrador', '$2a$12$wovjHMsHrLKgh.95YUjxkuEXiF0fLTuQD3tDaBzyl1Lb4lgTcANnO');

-- PRESTECS
INSERT INTO Prestecs (idUsuari, isbn, idBiblioteca, dataPrestec, dataDevolucio, idAgentPrestec, estat)
VALUES
(1, '9780439136365', 1, NOW(), NULL, 1, 'actiu'),
(2, '9780316769488', 1, NOW(), NULL, 2, 'actiu'),
(3, '9780307474278', 2, NOW(), NULL, 1, 'actiu');

-- RESERVES
INSERT INTO Reserves (idUsuari, isbn, dataReserva, estat)
VALUES
(4, '9780261103573', NOW(), 'pendent'),
(5, '9781260440232', NOW(), 'pendent');

-- PROPOSTES D’ADQUISICIÓ
INSERT INTO PropostesAdquisicio (idUsuari, titol, autor, isbn, editorial, motiu, dataProposta, estat)
VALUES
(6, 'Crim i càstig', 'Fiódor Dostoievski', NULL, 'Edicions 62', 'Molt demanat pels usuaris', NOW(), 'pendent'),
(7, 'El Senyor dels Anells', 'J.R.R. Tolkien', NULL, 'Minotauro', 'Per ampliar la secció de fantasia', NOW(), 'pendent');
