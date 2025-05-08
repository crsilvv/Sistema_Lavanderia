CREATE DATABASE IF NOT EXISTS lavanderia;
USE lavanderia;

CREATE TABLE Cliente (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100),
    telefone VARCHAR(20),
    email VARCHAR(100)
);

CREATE TABLE Servico (
    id INT PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(50),
    descricao TEXT,
    preco DECIMAL(10,2)
);

CREATE TABLE OrdemServico (
    id INT PRIMARY KEY AUTO_INCREMENT,
    cliente_id INT,
    data_entrada DATE,
    data_saida DATE,
    valor_total DECIMAL(10,2),
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id)
);

CREATE TABLE OrdemItem (
    id INT PRIMARY KEY AUTO_INCREMENT,
    ordem_id INT,
    servico_id INT,
    quantidade INT,
    FOREIGN KEY (ordem_id) REFERENCES OrdemServico(id),
    FOREIGN KEY (servico_id) REFERENCES Servico(id)
);