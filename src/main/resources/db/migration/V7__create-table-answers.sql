CREATE TABLE answers (
    answer_id INT AUTO_INCREMENT PRIMARY KEY,
    body TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    question_id INT NOT NULL,
    user_id INT NOT NULL,
    rating INT DEFAULT NULL,
    comment TEXT DEFAULT NULL,
    rated_at TIMESTAMP DEFAULT NULL,

    FOREIGN KEY (question_id) REFERENCES questions(question_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
