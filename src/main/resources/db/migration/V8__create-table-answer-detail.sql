CREATE TABLE answer_detail (
    answer_detail_id INT AUTO_INCREMENT PRIMARY KEY,
    answer_id INT NOT NULL,
    question_id INT NOT NULL,
    expert_id INT NOT NULL,
    user_id INT NOT NULL,
    is_right BOOLEAN NOT NULL,

    FOREIGN KEY (answer_id) REFERENCES answers(answer_id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES questions(question_id) ON DELETE CASCADE,
    FOREIGN KEY (expert_id) REFERENCES experts(user_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

