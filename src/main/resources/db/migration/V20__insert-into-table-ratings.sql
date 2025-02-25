INSERT INTO ratings (user_id, expert_id, rating, comment, answer_id, created_at)
VALUES
-- ✅ Ratings for Accepted Answers with proper expert assignments
(3, 1, 5, 'Great explanation on limits!', 1, NOW()),  -- Expert ID 1
(5, 2, 4, 'Good physics explanation.', 2, NOW()),    -- Expert ID 2
(7, 4, 5, 'Very insightful history lesson.', 3, NOW()), -- Expert ID 4
(9, 6, 5, 'AI topic explained very well!', 4, NOW()),  -- Expert ID 6
(3, 8, 3, 'Answer was correct but needed more details.', 5, NOW()),  -- Expert ID 8
(5, 10, 4, 'Good effort, but could be clearer.', 6, NOW()), -- Expert ID 10
(7, 4, 5, 'Great answer, helped a lot!', 7, NOW()), -- Expert ID 4
(9, 6, 4, 'Interesting explanation about AI.', 8, NOW()), -- Expert ID 6
(3, 4, 5, 'Well-structured response.', 9, NOW()), -- Expert ID 4
(5, 2, 5, 'Excellent and well-supported answer!', 10, NOW()); -- Expert ID 2
