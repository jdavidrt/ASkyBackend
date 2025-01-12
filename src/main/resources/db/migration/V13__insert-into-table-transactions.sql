-- Insert Mock Data into Transactions
INSERT INTO transactions (type, amount, description, askoins_amount, question_id, askoin_rate) VALUES
('Credit', '100.00', 'Deposit for question payment', 100.0, 1, 5),
('Debit', '50.00', 'Payment for answering question', 50.0, 2, 5),
('Credit', '200.00', 'User deposit for new question', 200.0, 3, 5),
('Debit', '150.00', 'Expert paid for answering question', 150.0, 4, 5),
('Credit', '300.00', 'Recharge for posting multiple questions', 300.0, 5, 5),
('Debit', '120.00', 'Payment for expert services', 120.0, 6, 5),
('Credit', '50.00', 'Small deposit for single question', 50.0, 7, 5),
('Debit', '80.00', 'Payment to expert for answer', 80.0, 8, 5),
('Credit', '400.00', 'Bulk deposit for platform use', 400.0, 9, 5),
('Debit', '60.00', 'Payout for providing details on topic', 60.0, 10, 5),
('Credit', '250.00', 'Top-up for advanced questions', 250.0, 11, 5),
('Debit', '90.00', 'Paid for well-received answer', 90.0, 12, 5),
('Credit', '150.00', 'Recharge to fund a question', 150.0, 13, 5),
('Debit', '70.00', 'Paid to expert for consulting services', 70.0, 14, 5),
('Credit', '500.00', 'High-value deposit for extensive questions', 500.0, 15, 5);