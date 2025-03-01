INSERT INTO notifications (user_id, message, type, read_status, question_id)
VALUES
(3, '¡Tu pregunta ha sido respondida!', 'Question Answered', 0, 1),
(5, '¡Tu respuesta recibió una calificación de 5!', 'Answer Rated', 0, 2),
(7, '¡Un nuevo experto se ha unido a la plataforma!', 'System Update', 1, NULL),
(9, 'Tienes una pregunta pendiente por responder..', 'Reminder', 0, 4);

