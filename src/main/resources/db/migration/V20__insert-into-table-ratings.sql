INSERT INTO ratings (user_id, expert_id, rating, comment, answer_detail_id)
VALUES
(3, 1, 5, 'Great explanation on limits!', 1),  -- Matemáticas (ID correcto)
(5, 2, 4, 'Good physics explanation.', 2),    -- Física (ID correcto)
(7, 4, 5, 'Very insightful history lesson.', 3), -- Historia (ID corregido de 3 a 4)
(9, 6, 5, 'AI topic explained very well!', 4);  -- IA (ID corregido de 4 a 6)
