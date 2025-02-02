CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(45) NOT NULL,
    amount FLOAT NOT NULL,
    askoin_amount FLOAT DEFAULT NULL,
    money_amount FLOAT DEFAULT NULL,
    method VARCHAR(45) DEFAULT NULL,
    description TEXT DEFAULT NULL,
    user_id INT NOT NULL,
    question_id INT DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES questions(question_id) ON DELETE SET NULL
);

