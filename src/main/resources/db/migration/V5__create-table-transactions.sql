-- Transactions Table
CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(45) NOT NULL,
    amount VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    description TEXT,
    askoins_amount FLOAT NOT NULL,
    question_id INT,
    askoin_rate INT,
    FOREIGN KEY (question_id) REFERENCES questions(question_id)
);
