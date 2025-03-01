INSERT INTO questions (title, body, price, active, status, topic_id, user_id, expert_id, image_url, deadline)
VALUES
('What is the limit of x^2 as x approaches infinity?', 'I need help understanding limits.', 10, 1, 0, 2, 3, 1, "https://physics.ucf.edu/~klemm/Problems%20Week%2013_Page_2.jpg", NOW() + INTERVAL 2 DAY),
('Explain Newton’s second law.', 'What does F=ma mean in real-world applications?', 15, 1, 0, 3, 5, 2, "https://physics.ucf.edu/~klemm/Problems%20Week%2012_Page_1.jpg", NOW() + INTERVAL 3 DAY),
('Who were the key figures in the Renaissance?', 'I need a list of main figures and their contributions.', 12, 1, 0, 4, 7, 4, "https://physics.ucf.edu/~klemm/Problems%20Week%2010_Page_1.jpg", NOW() + INTERVAL 1 DAY),
('How does a neural network work?', 'Explain forward propagation and backpropagation.', 20, 1, 0, 5, 9, 6, "https://physics.ucf.edu/~klemm/Problems%20Week%2013_Page_1.jpg", NOW() + INTERVAL 5 DAY),
('What are the main causes of climate change?', 'Looking for a detailed explanation with examples.', 18, 1, 0, 6, 3, 8, "https://physics.ucf.edu/~klemm/Problems%20Week%208_Page_1.jpg", NOW() + INTERVAL 4 DAY),
('How do I balance a chemical equation?', 'Need step-by-step guidance.', 14, 1, 0, 7, 5, 10, "https://physics.ucf.edu/~klemm/Problems%20Week%208_Page_3.jpg", NOW() + INTERVAL 3 DAY),
('What is the Pythagorean theorem used for?', 'Explain with real-life applications.', 8, 1, 0, 8, 7, 1, "https://physics.ucf.edu/~klemm/Problems%20Week%209_Page_1.jpg", NOW() + INTERVAL 2 DAY),
('How does the immune system fight viruses?', 'Looking for an easy-to-understand explanation.', 22, 1, 0, 9, 9, 2, "https://physics.ucf.edu/~klemm/Problems%20Week%206_Page_5.jpg", NOW() + INTERVAL 6 DAY),
('What are the basic principles of supply and demand?', 'Provide examples related to real markets.', 16, 1, 0, 10, 3, 8, "https://physics.ucf.edu/~klemm/Problems%20Week%206_Page_4.jpg", NOW() + INTERVAL 5 DAY),
('How does quantum entanglement work?', 'Please explain in simple terms.', 25, 1, 0, 10, 5, 10, "https://physics.ucf.edu/~klemm/Problems%20Week%206_Page_2.jpg", NOW() + INTERVAL 7 DAY),
('What is the Fibonacci sequence?', 'Explain its significance in mathematics and nature.', 13, 1, 0, 1, 12, 11, "https://physics.ucf.edu/~klemm/Problems%20Week%205_Page_3.jpg", NOW() + INTERVAL 4 DAY);

