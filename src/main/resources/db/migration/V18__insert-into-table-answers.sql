-- Poblar la tabla answers (UNA respuesta por cada pregunta)
INSERT INTO answers (answer_id, type, body, question_id, created_at)
VALUES
-- ✅ Respuestas aceptadas (type = 1)
(1, 1, 'The limit of x^2 as x approaches infinity is infinity.', 1, NOW()),
(2, 1, 'F=ma means that force is the product of mass and acceleration.', 2, NOW()),
(3, 1, 'Leonardo da Vinci and Michelangelo were key figures.', 3, NOW()),
(4, 1, 'A neural network is a system of nodes mimicking brain neurons.', 4, NOW()),

-- ❌ Respuestas rechazadas (type = 0) con justificación
(5, 0, 'This question lacks clarity and is too vague.', 5, NOW()),
(6, 0, 'The requested information is outside the expert’s scope.', 6, NOW()),
(7, 0, 'This question violates platform guidelines.', 7, NOW()),
(8, 0, 'Duplicate question. Please refer to existing answers.', 8, NOW()),
(9, 0, 'This question requires a longer and more detailed answer, not suitable for this format.', 9, NOW()),
(10, 0, 'Question lacks essential details to provide an answer.', 10, NOW());
