INSERT INTO answer_detail (answer_id, question_id, expert_id, user_id, is_right)
VALUES
(1, 1, 1, 3, TRUE),  -- Expert 1 respondió la pregunta 1 correctamente para el usuario 3
(2, 2, 2, 5, TRUE),  -- Expert 2 respondió la pregunta 2 correctamente para el usuario 5
(3, 3, 4, 7, TRUE),  -- Expert 4 respondió la pregunta 3 correctamente para el usuario 7
(4, 4, 6, 9, TRUE),  -- Expert 6 respondió la pregunta 4 correctamente para el usuario 9
(5, 1, 1, 3, TRUE),  -- Repetimos para pruebas con más datos
(6, 2, 2, 5, TRUE),
(7, 3, 4, 7, FALSE), -- Simulando una respuesta incorrecta
(8, 4, 6, 9, TRUE),
(9, 1, 1, 3, TRUE),
(10, 2, 2, 5, FALSE);
