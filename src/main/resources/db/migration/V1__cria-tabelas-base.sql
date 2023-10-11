CREATE TABLE indicadores(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NULL,
    descricao VARCHAR(255) NULL,
    tipo VARCHAR(255) NULL
);

CREATE TABLE acompanhamentos(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome_representante VARCHAR(255) NULL,
    email_representante VARCHAR(255) NULL,
    telefone_representante VARCHAR(255) NULL,
    cargo_representante VARCHAR(255) NULL,
    informacoes_adicionais VARCHAR(255) NULL,
    acao_id INT NULL,
    organizacao_id INT NULL,
    usuario_id INT NULL,
    created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE indicadores_solicitados(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    valor VARCHAR(255) NULL,
    indicador_id INT UNSIGNED NULL,
    acompanhamento_id INT UNSIGNED NULL,
    FOREIGN KEY(indicador_id) REFERENCES indicadores(id),
    FOREIGN KEY(acompanhamento_id) REFERENCES acompanhamentos(id)
);

CREATE TABLE reunioes(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    link VARCHAR(255) NULL,
    acompanhamento_id INT UNSIGNED NULL,
    FOREIGN KEY(acompanhamento_id) REFERENCES acompanhamentos(id)
);

CREATE TABLE horarios(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    data_horario TIMESTAMP NULL,
    escolhido TINYINT(1) NULL DEFAULT '0',
    reuniao_id INT UNSIGNED NULL,
    FOREIGN KEY(reuniao_id) REFERENCES reunioes(id)
);

CREATE TABLE arquivos(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    caminho_s3 VARCHAR(255) NULL,
    nome_arquivo_original VARCHAR(255) NULL,
    tamanho LONG NULL,
    acompanhamento_id INT UNSIGNED NULL,
    created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY(acompanhamento_id) REFERENCES acompanhamentos(id)
);