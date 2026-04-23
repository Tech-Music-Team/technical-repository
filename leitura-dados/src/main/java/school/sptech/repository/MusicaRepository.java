package school.sptech.repository;

import school.sptech.entities.Musica;
import school.sptech.entities.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

public class MusicaRepository {

    private final JdbcTemplate jdbcTemplate;

    public MusicaRepository(ConexaoBanco conexaoBanco) {
        this.jdbcTemplate = conexaoBanco.getJdbcTemplate();
    }

    public void inserir(Musica musica) {
        Logger.debug(MusicaRepository.class.getPackageName().toString(), MusicaRepository.class.getName().toString(), "Inserindo música: " + musica.getTitle());
        
        jdbcTemplate.update(
            "INSERT INTO musica (id_track, fk_artista, streams, title, track, views, likes, comments, danceability, valence, energy, instrumentalness, speechiness, loudness, track_popularity) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            musica.getIdTrack(),
            musica.getArtista().getIdArtista(),
            musica.getStreams(),
            musica.getTitle(),
            musica.getTrack(),
            musica.getViews(),
            musica.getLikes(),
            musica.getComments(),
            musica.getDanceability(),
            musica.getValence(),
            musica.getEnergy(),
            musica.getInstrumentalness(),
            musica.getSpeechiness(),
            musica.getLoudness(),
            musica.getTrackPopularity()
        );
        
        Logger.info(MusicaRepository.class.getPackageName().toString(), MusicaRepository.class.getName().toString(), "Música inserida: " + musica.getTitle());
    }

}