CREATE TABLE notifications (
    notification_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    message TEXT NOT NULL,
    type VARCHAR(50) NOT NULL,
    read_status TINYINT(1) NOT NULL DEFAULT 0, -- 0 = unread, 1 = read
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    question_id INT DEFAULT NULL,

    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES questions(question_id) ON DELETE SET NULL
);

