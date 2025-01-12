-- Askoins Table
CREATE TABLE askoins (
    askoin_id INT AUTO_INCREMENT PRIMARY KEY,
    amount FLOAT NOT NULL,
    user_id INT NOT NULL,
    transaction_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id)
);
