INSERT INTO questions (title, body, price, active, status, topic_id, user_id, attachment, deadline)
VALUES
('What is the limit of x^2 as x approaches infinity?', 'I need help understanding limits.', 10, 1, 0, 2, 3, NULL, NOW() + INTERVAL 2 DAY),
('Explain Newton’s second law.', 'What does F=ma mean in real-world applications?', 15, 1, 0, 3, 5, NULL, NOW() + INTERVAL 3 DAY),
('Who were the key figures in the Renaissance?', 'I need a list of main figures and their contributions.', 12, 1, 0, 4, 7, NULL, NOW() + INTERVAL 1 DAY),
('How does a neural network work?', 'Explain forward propagation and backpropagation.', 20, 1, 0, 5, 9, NULL, NOW() + INTERVAL 5 DAY);

