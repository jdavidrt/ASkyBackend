INSERT INTO notifications (user_id, message, type, read_status, question_id)
VALUES
(3, 'Your question has been answered!', 'Question Answered', 0, 1),
(5, 'Your answer received a rating of 5!', 'Answer Rated', 0, 2),
(7, 'New expert has joined the platform!', 'System Update', 1, NULL),
(9, 'You have a pending question to answer.', 'Reminder', 0, 4);

