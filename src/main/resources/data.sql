-- Insert users
INSERT INTO users (id, username, password, email, balance, user_type)
VALUES
    (1, 'john_doe', 'password123', 'john@example.com', 10000.00, 'GUEST'),
    (2, 'jane_smith', 'securepass', 'jane@example.com', 15000.00, 'ADMIN'),
    (3, 'alice_jones', 'alicepass', 'alice@example.com', 20000.00, 'BASIC_USER'),
    (4, 'bob_brown', 'bobbypass', 'bob@example.com', 5000.00, 'ADVANCED_USER'),
    (5, 'carol_white', 'carolpass', 'carol@example.com', 8000.00, 'GUEST');

-- Insert portfolios
INSERT INTO portfolio (id, name, total_value, user_id)
VALUES
    (1, 'Retirement Fund', 5000.00, 1),
    (2, 'Growth Portfolio', 10000.00, 1),
    (3, 'High Risk Fund', 7000.00, 1),
    (4, 'Savings Account', 3000.00, 2),
    (5, 'Investment Trust', 6000.00, 2),
    (6, 'Balanced Portfolio', 8000.00, 2),
    (7, 'Retirement Fund', 4000.00, 3),
    (8, 'Growth Portfolio', 9000.00, 3),
    (9, 'High Risk Fund', 7000.00, 3),
    (10, 'Savings Account', 3500.00, 4),
    (11, 'Investment Trust', 7000.00, 4),
    (12, 'Balanced Portfolio', 7500.00, 4),
    (13, 'Retirement Fund', 5000.00, 5),
    (14, 'Growth Portfolio', 10000.00, 5),
    (15, 'High Risk Fund', 7000.00, 5);

-- Insert market data
INSERT INTO market_data (id, investment_type, symbol, current_price, last_update_date, low_price, high_price, volume)
VALUES
    (1, 'STOCKS', 'AAPL', 150.00, '2024-07-20 08:00:00', 145.00, 155.00, 1000000),
    (2, 'BONDS', 'US10Y', 100.00, '2024-07-20 09:00:00', 98.00, 102.00, 500000),
    (3, 'COMMODITIES', 'GOOGL', 2800.00, '2024-07-20 10:00:00', 2750.00, 2850.00, 800000),
    (4, 'CRYPTOCURRENCY', 'BTC', 35000.00, '2024-07-20 11:00:00', 34000.00, 36000.00, 300000),
    (5, 'REAL_ESTATE', 'AMZN', 3400.00, '2024-07-20 12:00:00', 3300.00, 3500.00, 600000);

-- Insert assets
INSERT INTO asset (id, quantity, portfolio_id, market_data_id)
VALUES
    (1, 50, 1, 1),
    (2, 100, 1, 2),
    (3, 20, 1, 3),
    (4, 15, 2, 1),
    (5, 30, 2, 2),
    (6, 10, 2, 3),
    (7, 5, 3, 1),
    (8, 20, 3, 2),
    (9, 15, 3, 3),
    (10, 50, 4, 1),
    (11, 100, 4, 2),
    (12, 20, 4, 3),
    (13, 15, 5, 1),
    (14, 30, 5, 2),
    (15, 10, 5, 3),
    (16, 5, 6, 1),
    (17, 20, 6, 2),
    (18, 15, 6, 3),
    (19, 50, 7, 1),
    (20, 100, 7, 2),
    (21, 20, 7, 3),
    (22, 15, 8, 1),
    (23, 30, 8, 2),
    (24, 10, 8, 3),
    (25, 5, 9, 1),
    (26, 20, 9, 2),
    (27, 15, 9, 3),
    (28, 50, 10, 1),
    (29, 100, 10, 2),
    (30, 20, 10, 3),
    (31, 15, 11, 1),
    (32, 30, 11, 2),
    (33, 10, 11, 3),
    (34, 5, 12, 1),
    (35, 20, 12, 2),
    (36, 15, 12, 3),
    (37, 50, 13, 1),
    (38, 100, 13, 2),
    (39, 20, 13, 3),
    (40, 15, 14, 1),
    (41, 30, 14, 2),
    (42, 10, 14, 3),
    (43, 5, 15, 1),
    (44, 20, 15, 2),
    (45, 15, 15, 3);

-- Insert orders
INSERT INTO orders (id, transaction_type, order_status, quantity, price, date_created_order, user_id, market_data_id, asset_id)
VALUES
    (1, 'BUY', 'OPEN', 10, 150.00, '2024-07-20 08:15:00', 1, 1, 1),
    (2, 'SELL', 'EXECUTED', 5, 100.00, '2024-07-20 09:30:00', 2, 2, 2),
    (3, 'BUY', 'CANCELLED', 3, 2800.00, '2024-07-20 10:45:00', 3, 3, 3),
    (4, 'SELL', 'OPEN', 1, 35000.00, '2024-07-20 11:30:00', 4, 4, 4),
    (5, 'BUY', 'EXECUTED', 2, 3400.00, '2024-07-20 12:15:00', 5, 5, 5),
    (6, 'BUY', 'CANCELLED', 5, 150.00, '2024-07-20 08:30:00', 1, 1, 6),
    (7, 'SELL', 'OPEN', 10, 150.00, '2024-07-20 09:45:00', 2, 1, 7),
    (8, 'BUY', 'CANCELLED', 1, 3400.00, '2024-07-20 10:00:00', 3, 5, 8);