INSERT INTO ratings (user_id, expert_id, rating, comment, answer_id, created_at)
VALUES
-- ✅ Calificaciones para respuestas aceptadas con la asignación correcta de expertos
(3, 1, 5, '¡Gran explicación sobre límites!', 1, NOW()),  -- Experto ID 1
(5, 2, 4, 'Buena explicación de física.', 2, NOW()),    -- Experto ID 2
(7, 4, 5, 'Lección de historia muy perspicaz.', 3, NOW()), -- Experto ID 4
(9, 6, 5, '¡El tema de IA fue explicado muy bien!', 4, NOW()),  -- Experto ID 6
(3, 8, 3, 'La respuesta fue correcta pero necesitaba más detalles.', 5, NOW()),  -- Experto ID 8
(5, 10, 4, 'Buen esfuerzo, pero podría ser más clara.', 6, NOW()), -- Experto ID 10
(7, 4, 5, '¡Gran respuesta, me ayudó mucho!', 7, NOW()), -- Experto ID 4
(9, 6, 4, 'Explicación interesante sobre IA.', 8, NOW()), -- Experto ID 6
(3, 4, 5, 'Respuesta bien estructurada.', 9, NOW()), -- Experto ID 4
(5, 2, 5, '¡Excelente y bien fundamentada respuesta!', 10, NOW()); -- Experto ID 2
