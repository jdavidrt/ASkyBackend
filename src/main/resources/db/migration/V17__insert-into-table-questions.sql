INSERT INTO questions (title, body, price, active, status, topic_id, user_id, expert_id, image_url, deadline)
VALUES
('¿Cuál es el límite de x^2 cuando x tiende a infinito?', 'Necesito ayuda para entender los límites.', 10, 1, 0, 2, 3, 1, "https://physics.ucf.edu/~klemm/Problems%20Week%2013_Page_2.jpg", NOW() + INTERVAL 2 DAY),
('Explica la segunda ley de Newton.', '¿Qué significa F=ma en aplicaciones del mundo real?', 15, 1, 0, 3, 5, 2, "https://physics.ucf.edu/~klemm/Problems%20Week%2012_Page_1.jpg", NOW() + INTERVAL 3 DAY),
('¿Quiénes fueron las figuras clave del Renacimiento?', 'Necesito una lista de los principales personajes y sus contribuciones.', 12, 1, 0, 4, 7, 4, "https://physics.ucf.edu/~klemm/Problems%20Week%2010_Page_1.jpg", NOW() + INTERVAL 1 DAY),
('¿Cómo funciona una red neuronal?', 'Explica la propagación hacia adelante y la retropropagación.', 20, 1, 0, 5, 9, 6, "https://physics.ucf.edu/~klemm/Problems%20Week%2013_Page_1.jpg", NOW() + INTERVAL 5 DAY),
('¿Cuáles son las principales causas del cambio climático?', 'Busco una explicación detallada con ejemplos.', 18, 1, 0, 6, 3, 8, "https://physics.ucf.edu/~klemm/Problems%20Week%208_Page_1.jpg", NOW() + INTERVAL 4 DAY),
('¿Cómo balanceo una ecuación química?', 'Necesito una guía paso a paso.', 14, 1, 0, 7, 5, 10, "https://physics.ucf.edu/~klemm/Problems%20Week%208_Page_3.jpg", NOW() + INTERVAL 3 DAY),
('¿Para qué se usa el teorema de Pitágoras?', 'Explica con aplicaciones en la vida real.', 8, 1, 0, 8, 7, 1, "https://physics.ucf.edu/~klemm/Problems%20Week%209_Page_1.jpg", NOW() + INTERVAL 2 DAY),
('¿Cómo combate el sistema inmunológico a los virus?', 'Busco una explicación fácil de entender.', 22, 1, 0, 9, 9, 2, "https://physics.ucf.edu/~klemm/Problems%20Week%206_Page_5.jpg", NOW() + INTERVAL 6 DAY),
('¿Cuáles son los principios básicos de la oferta y la demanda?', 'Proporciona ejemplos relacionados con mercados reales.', 16, 1, 0, 10, 3, 8, "https://physics.ucf.edu/~klemm/Problems%20Week%206_Page_4.jpg", NOW() + INTERVAL 5 DAY),
('¿Cómo funciona el entrelazamiento cuántico?', 'Por favor, explícalo en términos sencillos.', 25, 1, 0, 10, 5, 10, "https://physics.ucf.edu/~klemm/Problems%20Week%206_Page_2.jpg", NOW() + INTERVAL 7 DAY),
('¿Qué es la secuencia de Fibonacci?', 'Explica su importancia en matemáticas y la naturaleza.', 13, 1, 0, 1, 12, 11, "https://physics.ucf.edu/~klemm/Problems%20Week%205_Page_3.jpg", NOW() + INTERVAL 4 DAY);

