INSERT INTO transactions (type, askoin_amount, money_amount, method, description, status, user_id, question_id)
VALUES
-- Recharge transactions (users buying ASKoins)
('Recharge', 50, 50000, 'PayPal', 'El usuario recargó 50 ASKoins a través de PayPal', 'Completed', 3, 3),
('Recharge', 100, 100000, 'Stripe', 'El usuario recargó 100 ASKoins a través de Stripe', 'Completed', 5, 2),

-- Withdrawal transactions (users cashing out ASKoins)
('Withdrawal', 20, 20000, 'Bank Transfer', 'El usuario retiró 20 ASKoins mediante transferencia bancaria', 'Pending', 4, 1),
('Withdrawal', 30, 30000, 'PayPal', 'El usuario retiró 30 ASKoins a través de PayPal', 'Completed', 2, 3);

