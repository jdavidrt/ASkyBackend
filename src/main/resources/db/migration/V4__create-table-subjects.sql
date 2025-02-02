CREATE TABLE subjects (
    subject_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    description TEXT NOT NULL,
    topic_id INT NOT NULL,

    FOREIGN KEY (topic_id) REFERENCES topics(topic_id) ON DELETE CASCADE
);
