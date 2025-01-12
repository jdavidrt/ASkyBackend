-- Payments Table
CREATE TABLE payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    amount VARCHAR(45) NOT NULL,
    method VARCHAR(45) NOT NULL,
    status VARCHAR(10) NOT NULL,
    type VARCHAR(45) NOT NULL,
    transaction_id INT,
    FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id)
);
