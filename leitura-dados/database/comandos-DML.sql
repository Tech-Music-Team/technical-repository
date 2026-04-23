USE tech_music;

INSERT INTO roles (nome) VALUES
('ADMIN'),
('USER');

INSERT INTO usuario (email, nome, senha, fk_role) VALUES
('alexandre@email.com', 'Alexandre', '123456', 1),
('maria@email.com', 'Maria', '123456', 2);

INSERT INTO artista (nome, views, artist_popularity, likes, artist_genre) VALUES
('The Weeknd', 8500000000, 98, 12000000, 'Pop'),
('Taylor Swift', 9200000000, 100, 15000000, 'Pop'),
('Drake', 7800000000, 96, 11000000, 'Hip-Hop'),
('Bad Bunny', 7000000000, 97, 10000000, 'Reggaeton'),
('Dua Lipa', 6400000000, 95, 9000000, 'Pop');

INSERT INTO musica (
id_track, fk_artista, streams, title, track,
views, likes, comments,
danceability, valence, energy,
loudness, instrumentalness, speechiness,
track_popularity
) VALUES

('track001', 1, 3200000000, 'Blinding Lights (Official Video)', 'Blinding Lights',
3500000000, 21000000, 1500000,
0.51, 0.38, 0.73,
-5.93, 0.00, 0.06,
98),

('track002', 2, 2800000000, 'Anti-Hero (Official Video)', 'Anti-Hero',
2000000000, 18000000, 980000,
0.64, 0.51, 0.62,
-6.10, 0.00, 0.07,
95),

('track003', 3, 2500000000, 'Gods Plan (Official Video)', 'Gods Plan',
2200000000, 17000000, 870000,
0.75, 0.45, 0.59,
-8.20, 0.00, 0.22,
94),

('track004', 4, 3000000000, 'Tití Me Preguntó', 'Tití Me Preguntó',
2400000000, 16000000, 920000,
0.78, 0.69, 0.81,
-4.50, 0.01, 0.12,
96),

('track005', 5, 2100000000, 'Levitating (Official Video)', 'Levitating',
2300000000, 15000000, 760000,
0.80, 0.91, 0.72,
-3.80, 0.00, 0.05,
93);

INSERT INTO playlist (nome, fk_usuario) VALUES
('Pop Hits', 1),
('Treino', 1),
('Favoritas Maria', 2);

INSERT INTO musica_playlist (fk_musica, fk_playlist) VALUES
(1,1),
(2,1),
(5,1),
(3,2),
(4,2),
(2,3),
(5,3);

INSERT INTO log (fk_usuario, data_hora, nivel, aplicacao, modulo, classe, mensagem) VALUES
(1, NOW(), 'INFO', 'TechMusic', 'AUTH', 'LoginService', 'Usuário realizou login'),
(1, NOW(), 'INFO', 'TechMusic', 'PLAYLIST', 'PlaylistService', 'Playlist criada'),
(2, NOW(), 'WARN', 'TechMusic', 'MUSICA', 'MusicService', 'Tentativa de acesso inválido'),
(NULL, NOW(), 'ERROR', 'TechMusic', 'API', 'GlobalHandler', 'Erro inesperado');
