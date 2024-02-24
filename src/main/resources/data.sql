-- Clean DB
DELETE FROM teams;
DELETE FROM players;

-- Initialize dummy data
INSERT INTO teams (id, name) VALUES
(1, 'South Side Second Serves');

INSERT INTO players (id, name, rating) VALUES
(1, 'Novak Djokovic', 5.0),
(2, 'Carlos Alcaraz', 5.0),
(3, 'Kei Nishikori', 4.5),
(4, 'Roger Federer', 5.0),
(5, 'Rafael Nadal', 5.0),
(6, 'Nick Kyrgios', 4.5);