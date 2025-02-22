CREATE TABLE questions (
    question_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(80) NOT NULL,
    body TEXT NOT NULL,
    price INT NOT NULL,
    active TINYINT(1) NOT NULL DEFAULT 1,
    status TINYINT(1) NOT NULL DEFAULT 0, -- 0 = pending, 1 = accepted, 2 = rejected, 3 = canceled
    topic_id INT NOT NULL,
    user_id INT NOT NULL,
    imageUrl VARCHAR(255) DEFAULT NULL,
    deadline TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (topic_id) REFERENCES topics(topic_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
