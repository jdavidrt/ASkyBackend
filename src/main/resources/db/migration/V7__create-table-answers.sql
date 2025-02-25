CREATE TABLE answers (
    answer_id INT AUTO_INCREMENT PRIMARY KEY,
    type TINYINT(1) NOT NULL DEFAULT 0, -- 0 = Rejected, 1 = Accepted
    body TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    question_id INT NOT NULL,
    FOREIGN KEY (question_id) REFERENCES questions(question_id) ON DELETE CASCADE
);
