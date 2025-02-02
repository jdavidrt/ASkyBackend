CREATE TABLE experts_topics (
    id INT AUTO_INCREMENT PRIMARY KEY,
    topic_id INT NOT NULL,
    expert_id INT NOT NULL,

    FOREIGN KEY (topic_id) REFERENCES topics(topic_id) ON DELETE CASCADE,
    FOREIGN KEY (expert_id) REFERENCES experts(user_id) ON DELETE CASCADE
);
