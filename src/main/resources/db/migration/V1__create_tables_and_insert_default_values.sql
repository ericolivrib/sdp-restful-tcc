CREATE TABLE usuarios
(
    id INTEGER PRIMARY KEY NOT NULL
);

CREATE TABLE eixos_tecnologicos
(
    id INTEGER PRIMARY KEY NOT NULL
);

CREATE TABLE projetos
(
    id                  BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nome                VARCHAR(70)        NOT NULL,
    numero              VARCHAR(6)         NOT NULL,
    modalidade          VARCHAR(8)         NOT NULL,
    justificativa       TEXT               NOT NULL,
    impactos_ambientais TEXT               NOT NULL,
    ano                 INTEGER            DEFAULT YEAR(CURRENT_DATE()),
    status              VARCHAR(19)        DEFAULT 'NAO_FINALIZADO',
    data_criacao        DATE               DEFAULT CURRENT_DATE(),
    data_finalizacao    DATE,
    usuario_id          BIGINT             NOT NULL REFERENCES usuarios (id),
    eixo_tecnologico_id BIGINT             NOT NULL REFERENCES eixos_tecnologicos (id)
);

CREATE TABLE datas_limite
(
    id          INTEGER PRIMARY KEY NOT NULL,
    nome        VARCHAR(30) UNIQUE  NOT NULL,
    data_inicio DATE                NOT NULL,
    data_fim    DATE                NOT NULL
);

INSERT INTO usuarios (id)
VALUES (1);
INSERT INTO eixos_tecnologicos (id)
VALUES (1);
INSERT INTO eixos_tecnologicos (id)
VALUES (2);
INSERT INTO eixos_tecnologicos (id)
VALUES (3);
INSERT INTO eixos_tecnologicos (id)
VALUES (4);
INSERT INTO eixos_tecnologicos (id)
VALUES (5);
INSERT INTO eixos_tecnologicos (id)
VALUES (6);
INSERT INTO eixos_tecnologicos (id)
VALUES (7);

INSERT INTO datas_limite (id, nome, data_inicio, data_fim)
VALUES (1, 'SUBMISSÃO DE PROJETOS', CURRENT_DATE(), '2024-10-31');
INSERT INTO datas_limite (id, nome, data_inicio, data_fim)
VALUES (2, 'ANÁLISE DE PROJETOS', CURRENT_DATE(), CURRENT_DATE());
INSERT INTO datas_limite (id, nome, data_inicio, data_fim)
VALUES (3, 'AJUSTE DE PROJETOS', CURRENT_DATE(), CURRENT_DATE());
INSERT INTO datas_limite (id, nome, data_inicio, data_fim)
VALUES (4, 'ANÁLISE DE DESCRIÇÃO', CURRENT_DATE(), CURRENT_DATE());
INSERT INTO datas_limite (id, nome, data_inicio, data_fim)
VALUES (5, 'ANÁLISE DE AJUSTES', CURRENT_DATE(), CURRENT_DATE());
