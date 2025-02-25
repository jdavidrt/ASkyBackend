INSERT INTO answers (type, body, question_id)
VALUES
-- ✅ Accepted Answers (type = 1)
(1, 'The limit of x^2 as x approaches infinity is infinity.', 1),
(1, 'F=ma means that force is the product of mass and acceleration.', 2),
(1, 'Leonardo da Vinci and Michelangelo were key figures.', 3),
(1, 'A neural network is a system of nodes mimicking brain neurons.', 4),
(1, 'Another example of an answer', 1),
(1, 'Yet another answer', 2),
(1, 'This is a repeated answer for testing.', 3),
(1, 'More AI-related content', 4),
(1, 'Answer on history topic', 1),
(1, 'Physics question solved', 2),

-- ❌ Rejected Answers (type = 0) with rejection justifications
(0, 'This question lacks clarity and is too vague.', 5),  -- Rejected for unclear question
(0, 'The requested information is outside the expert’s scope.', 6),  -- Rejected due to expertise mismatch
(0, 'This question violates platform guidelines.', 7),  -- Rejected for policy violation
(0, 'Duplicate question. Please refer to existing answers.', 8),  -- Rejected for being a duplicate
(0, 'This question requires a longer and more detailed answer, not suitable for this format.', 9),  -- Rejected due to format constraints
(0, 'Question lacks essential details to provide an answer.', 10);  -- Rejected due to missing details



