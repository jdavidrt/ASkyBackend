CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    auth0_id VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(60) NOT NULL,
    last_name VARCHAR(60) NOT NULL,
    email VARCHAR(45) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL, -- Aumentado el tamaño para soportar contraseñas hasheadas
    is_consultant BOOLEAN NOT NULL,
    status BOOLEAN NOT NULL DEFAULT TRUE,
    amount_askoins FLOAT DEFAULT 0.0, -- Valor predeterminado de 0
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

