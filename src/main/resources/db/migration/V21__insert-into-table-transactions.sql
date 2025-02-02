INSERT INTO transactions (type, amount, askoin_amount, money_amount, method, description, user_id, question_id)
VALUES
('ASKoin Purchase', 50.0, 50, NULL, 'PayPal', 'User bought ASKoins', 3, NULL),
('Question Payment', 10.0, NULL, 10, 'ASKoin', 'Paid for question #1', 3, 1),
('Expert Payout', 8.0, NULL, 8, 'Bank Transfer', 'Expert received payment for question #1', 1, 1),
('ASKoin Purchase', 100.0, 100, NULL, 'Stripe', 'User bought ASKoins', 5, NULL);

