INSERT INTO account(id, balance, account_iban) VALUES
    (1, 3000, 'ES9820385778983000760236'),
    (2, 1518, 'ES9820385778983000760238');

INSERT INTO transaction (reference, date, amount, fee, description, account_id) VALUES
    ('12345A', '2019-07-16T16:55:42.000Z', 193.38, 3.18, 'Restaurant Payment', 1),
    ('12345B', '2020-03-25T16:55:42.000Z', -100, 0, 'External transaction', 2),
    ('12345C', '2021-12-02T16:55:42.000Z', -289.90, 0, 'App Payment', 1);