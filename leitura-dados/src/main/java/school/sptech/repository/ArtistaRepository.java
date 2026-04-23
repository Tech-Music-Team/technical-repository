package school.sptech.repository;

import school.sptech.entities.Artista;
import school.sptech.entities.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

public class ArtistaRepository {

    private final JdbcTemplate jdbcTemplate;

    public ArtistaRepository(ConexaoBanco conexaoBanco) {
        this.jdbcTemplate = conexaoBanco.getJdbcTemplate();
    }

    public void inserir(Artista artista) {
        Logger.debug(ArtistaRepository.class.getPackageName().toString(), ArtistaRepository.class.getName().toString(), "Inserindo artista: " + artista.getNome());
        
        jdbcTemplate.update(
            "INSERT INTO artista (nome, views, artist_popularity, likes, artist_genre, artist_followers) VALUES (?, ?, ?, ?, ?, ?)",
            artista.getNome(),
            artista.getViews(),
            artista.getArtistPopularity(),
            artista.getLikes(),
            artista.getArtistGenre(),
            artista.getArtistFollowers()
        );
        
        Logger.info(ArtistaRepository.class.getPackageName().toString(), ArtistaRepository.class.getName().toString(), "Artista inserido com sucesso: " + artista.getNome());
    }

    public List<Artista> buscarPorNome(String nome) {
        Logger.debug(ArtistaRepository.class.getPackageName().toString(), ArtistaRepository.class.getName().toString(), "Buscando artista: " + nome);
        
        List<Artista> resultado = jdbcTemplate.query(
            "SELECT id_artista AS idArtista, nome, views, artist_popularity AS artistPopularity, likes, artist_genre AS artistGenre, artist_followers AS artistFollowers FROM artista WHERE nome = ?",
            new BeanPropertyRowMapper<>(Artista.class),
            nome
        );
        
        if (resultado.isEmpty()) {
            Logger.warn(ArtistaRepository.class.getPackageName().toString(), ArtistaRepository.class.getName().toString(), "Artista não encontrado: " + nome);
        } else {
            Logger.info(ArtistaRepository.class.getPackageName().toString(), ArtistaRepository.class.getName().toString(), "Artista encontrado: " + nome);
        }
        
        return resultado;
    }
}