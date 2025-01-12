-- Insert Mock Data into Users
INSERT INTO users (auth0_id, first_name, last_name, email, password, is_consultant, status, description, rating, specialties) VALUES
('auth001', 'Alice', 'Smith', 'alice.smith@example.com', 'password123', TRUE, TRUE, 'Expert in Mathematics', 4.8, 'Math, Science'),
('auth002', 'Bob', 'Johnson', 'bob.johnson@example.com', 'password123', FALSE, TRUE, 'Interested in general knowledge', NULL, NULL),
('auth003', 'Carol', 'Williams', 'carol.williams@example.com', 'password123', TRUE, TRUE, 'Expert in Physics', 4.5, 'Physics, Engineering'),
('auth004', 'David', 'Brown', 'david.brown@example.com', 'password123', TRUE, TRUE, 'Expert in Chemistry', 4.7, 'Chemistry, Biology'),
('auth005', 'Eve', 'Jones', 'eve.jones@example.com', 'password123', FALSE, TRUE, 'Casual learner', NULL, NULL),
('auth006', 'Frank', 'Garcia', 'frank.garcia@example.com', 'password123', TRUE, TRUE, 'Expert in Literature', 4.9, 'Literature, Writing'),
('auth007', 'Grace', 'Martinez', 'grace.martinez@example.com', 'password123', FALSE, TRUE, 'Science enthusiast', NULL, NULL),
('auth008', 'Hank', 'Davis', 'hank.davis@example.com', 'password123', TRUE, TRUE, 'Expert in History', 4.4, 'History, Archaeology'),
('auth009', 'Ivy', 'Miller', 'ivy.miller@example.com', 'password123', FALSE, TRUE, 'Interested in Arts', NULL, NULL),
('auth010', 'Jack', 'Wilson', 'jack.wilson@example.com', 'password123', TRUE, TRUE, 'Expert in Engineering', 4.6, 'Engineering, Mechanics'),
('auth011', 'Karen', 'Moore', 'karen.moore@example.com', 'password123', FALSE, TRUE, 'Mathematics student', NULL, NULL),
('auth012', 'Leo', 'Taylor', 'leo.taylor@example.com', 'password123', TRUE, TRUE, 'Expert in Astronomy', 4.3, 'Astronomy, Space Science'),
('auth013', 'Mia', 'Anderson', 'mia.anderson@example.com', 'password123', FALSE, TRUE, 'Physics enthusiast', NULL, NULL),
('auth014', 'Nick', 'Thomas', 'nick.thomas@example.com', 'password123', TRUE, TRUE, 'Expert in Biology', 4.5, 'Biology, Genetics'),
('auth015', 'Olivia', 'White', 'olivia.white@example.com', 'password123', FALSE, TRUE, 'Casual learner', NULL, NULL);
