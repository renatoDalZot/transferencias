--V2__Create_Transferencia_Table
CREATE TABLE transferencia (
    id INT AUTO_INCREMENT PRIMARY KEY,
    conta_origem INT NOT NULL,
    conta_destino INT NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    data_transferencia TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (conta_origem) REFERENCES conta(numero),
    FOREIGN KEY (conta_destino) REFERENCES conta(numero)
);
