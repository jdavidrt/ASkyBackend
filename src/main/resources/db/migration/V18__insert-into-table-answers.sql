-- Poblar la tabla answers (UNA respuesta por cada pregunta)
INSERT INTO answers (answer_id, type, body, question_id, created_at)
VALUES
-- ✅ Respuestas aceptadas (tipo = 1)
(1, 1, 'El límite de x^2 cuando x tiende a infinito es infinito.', 1, NOW()),
(2, 1, 'F=ma significa que la fuerza es el producto de la masa y la aceleración.', 2, NOW()),
(3, 1, 'Leonardo da Vinci y Miguel Ángel fueron figuras clave.', 3, NOW()),
(4, 1, 'Una red neuronal es un sistema de nodos que imita las neuronas del cerebro.', 4, NOW()),

-- ❌ Respuestas rechazadas (tipo = 0) con justificación
(5, 0, 'Esta pregunta carece de claridad y es demasiado vaga.', 5, NOW()),
(6, 0, 'La información solicitada está fuera del alcance del experto.', 6, NOW()),
(7, 0, 'Esta pregunta viola las normas de la plataforma.', 7, NOW()),
(8, 0, 'Pregunta duplicada. Por favor, consulta las respuestas existentes.', 8, NOW()),
(9, 0, 'Esta pregunta requiere una respuesta más extensa y detallada, no adecuada para este formato.', 9, NOW()),
(10, 0, 'La pregunta carece de detalles esenciales para proporcionar una respuesta.', 10, NOW());