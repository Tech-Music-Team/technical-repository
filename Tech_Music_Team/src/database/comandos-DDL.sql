CREATE DATABASE tech_music;
USE tech_music;
-- DROP DATABASE tech_music;

-- TABELA ARTISTA
CREATE TABLE artista (
    id_artista INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    views INT DEFAULT 0,
    artist_popularity INT,
    likes INT DEFAULT 0,
    artist_genre VARCHAR(100)
);

-- TABELA MUSICA
CREATE TABLE musica (
    id_musica INT AUTO_INCREMENT PRIMARY KEY,

    id_track VARCHAR(100) UNIQUE,
    fk_artista INT NOT NULL,

    streams INT,
    title VARCHAR(100),      -- nome no YouTube
    track VARCHAR(100),      -- nome no Spotify
    views INT DEFAULT 0,
    likes INT DEFAULT 0,
    comments INT DEFAULT 0,

    -- FEATURES DE ÁUDIO (0 até 1)
    danceability DECIMAL(3,2),
    valence DECIMAL(3,2),
    energy DECIMAL(3,2),
    instrumentalness DECIMAL(3,2),
    speechiness DECIMAL(3,2),

    -- LOUDNESS (-60 até 0 dB)
    loudness DECIMAL(5,2),

    -- Popularidade (0 até 100)
    track_popularity INT,

    CONSTRAINT fk_musica_artista
        FOREIGN KEY (fk_artista)
        REFERENCES artista(id_artista),

    -- RESTRIÇÕES DE DOMÍNIO
    CONSTRAINT chk_danceability
        CHECK (danceability BETWEEN 0 AND 1),

    CONSTRAINT chk_valence
        CHECK (valence BETWEEN 0 AND 1),

    CONSTRAINT chk_energy
        CHECK (energy BETWEEN 0 AND 1),

    CONSTRAINT chk_instrumentalness
        CHECK (instrumentalness BETWEEN 0 AND 1),

    CONSTRAINT chk_speechiness
        CHECK (speechiness BETWEEN 0 AND 1),

    CONSTRAINT chk_loudness
        CHECK (loudness BETWEEN -60 AND 0),

    CONSTRAINT chk_track_popularity
        CHECK (track_popularity BETWEEN 0 AND 100)
);

-- TABELA DE ROLES
CREATE TABLE roles (
    id_role INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

-- TABELA USUARIO
CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    fk_role INT NOT NULL,

    CONSTRAINT fk_role_usuario
        FOREIGN KEY (fk_role)
        REFERENCES roles(id_role)
);

-- TABELA LOG
CREATE TABLE log (
    id_log INT AUTO_INCREMENT PRIMARY KEY,
    fk_usuario INT NULL,

    data_hora DATETIME NOT NULL,
    nivel VARCHAR(50),
    aplicacao VARCHAR(100),
    modulo VARCHAR(100),
    classe VARCHAR(100),
    mensagem VARCHAR(255),

    CONSTRAINT fk_log_usuario
        FOREIGN KEY (fk_usuario)
        REFERENCES usuario(id_usuario)
        ON DELETE SET NULL
);

-- TABELA PLAYLIST (FRACA)
CREATE TABLE playlist (
    id_playlist INT AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    fk_usuario INT NOT NULL,

    CONSTRAINT fk_playlist_usuario
        FOREIGN KEY (fk_usuario)
        REFERENCES usuario(id_usuario)
        ON DELETE CASCADE,
        
	CONSTRAINT pkComposta_playlist 
		PRIMARY KEY (id_playlist, fk_usuario)
);

-- TABELA ASSOCIATIVA
CREATE TABLE musica_playlist (
    fk_musica INT NOT NULL,
    fk_playlist INT NOT NULL,

    CONSTRAINT pkComposta_mp PRIMARY KEY (fk_musica, fk_playlist),

    CONSTRAINT fk_mp_musica
        FOREIGN KEY (fk_musica)
        REFERENCES musica(id_musica),

    CONSTRAINT fk_mp_playlist
        FOREIGN KEY (fk_playlist)
        REFERENCES playlist(id_playlist)
);


