INSERT INTO transactions (type, askoin_amount, money_amount, method, description, status, user_id, question_id)
VALUES
-- Recharge transactions (users buying ASKoins)
('Recharge', 50, 50000, 'PayPal', 'User recharged 50 ASKoins via PayPal', 'Completed', 3, 3),
('Recharge', 100, 100000, 'Stripe', 'User recharged 100 ASKoins via Stripe', 'Completed', 5, 2),

-- Withdrawal transactions (users cashing out ASKoins)
('Withdrawal', 20, 20000, 'Bank Transfer', 'User withdrew 20 ASKoins via bank transfer', 'Pending', 4, 1),
('Withdrawal', 30, 30000, 'PayPal', 'User withdrew 30 ASKoins via PayPal', 'Completed', 2, 3);

