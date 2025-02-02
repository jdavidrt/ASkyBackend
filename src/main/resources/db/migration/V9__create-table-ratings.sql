CREATE TABLE ratings (
    rating_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    expert_id INT NOT NULL,
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment TEXT DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    answer_detail_id INT DEFAULT NULL,

    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (expert_id) REFERENCES experts(user_id) ON DELETE CASCADE,
    FOREIGN KEY (answer_detail_id) REFERENCES answer_detail(answer_detail_id) ON DELETE SET NULL
);
