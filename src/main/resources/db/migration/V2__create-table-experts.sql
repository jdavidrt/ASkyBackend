CREATE TABLE experts (
    user_id INT PRIMARY KEY, -- Mismo ID que en `users`
    biography TEXT DEFAULT NULL,
    average_rating FLOAT NOT NULL DEFAULT 0.0,
    base_price FLOAT NOT NULL,
    availability BOOLEAN NOT NULL DEFAULT TRUE,
    response_rate FLOAT NOT NULL DEFAULT 0.0,
    total_responses INT DEFAULT 0,

    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);