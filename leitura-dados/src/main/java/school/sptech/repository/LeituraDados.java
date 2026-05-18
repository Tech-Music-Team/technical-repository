package school.sptech.repository;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import school.sptech.entities.Artista;
import school.sptech.entities.Logger;
import school.sptech.entities.Musica;
import school.sptech.utils.aggregator.ArtistaAggregator;
import school.sptech.utils.aggregator.MusicaDeduplicator;
import school.sptech.utils.excel.ExcelFileReader;
import school.sptech.utils.extractor.ArtistaDataExtractor;
import school.sptech.utils.extractor.MusicaDataExtractor;
import school.sptech.utils.validator.ArtistaValidator;
import school.sptech.utils.validator.MusicaValidator;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LeituraDados {

    public LeituraDados() {
    }

    public List<Musica> lerMusicas(String caminhoArquivo) {
        Logger.info(LeituraDados.class.getPackageName(), LeituraDados.class.getName(), 
                "Iniciando leitura do arquivo: " + caminhoArquivo);

        try {
            // Ler o arquivo Excel
            Sheet sheet = ExcelFileReader.lerSheet(caminhoArquivo);

            // Inicializar agregadores
            ArtistaAggregator artistaAggregator = new ArtistaAggregator();
            MusicaDeduplicator musicaDedup = new MusicaDeduplicator();

            int linhasProcessadas = 0;
            int linhasIgnoradas = 0;

            // Processar cada linha do Excel
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Pular header

                if (processarLinha(row, artistaAggregator, musicaDedup)) {
                    linhasProcessadas++;
                } else {
                    linhasIgnoradas++;
                }
            }

            Logger.info(LeituraDados.class.getPackageName(), LeituraDados.class.getName(),
                    "Leitura concluída: " + linhasProcessadas + " linhas processadas, " 
                    + linhasIgnoradas + " duplicadas/inválidas ignoradas");

            return musicaDedup.obterTodas();

        } catch (IOException e) {
            Logger.error(LeituraDados.class.getPackageName(), LeituraDados.class.getName(), 
                    "Erro ao ler arquivo Excel: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private boolean processarLinha(Row row, ArtistaAggregator artistaAggregator, 
                                   MusicaDeduplicator musicaDedup) {
        
        // Extrair dados de música e artista
        Map<String, Object> dadosMusica = MusicaDataExtractor.extrairDados(row);
        Map<String, Object> dadosArtista = ArtistaDataExtractor.extrairDados(row);

        String trackId = (String) dadosMusica.get("trackId");
        String nomeArtista = (String) dadosArtista.get("nome");

        // ===== VALIDAÇÕES =====

        // 1. Validar música
        if (!MusicaValidator.validar(trackId)) {
            Logger.debug(LeituraDados.class.getPackageName(), LeituraDados.class.getName(), 
                    "TrackId vazio ou inválido, linha " + row.getRowNum() + " ignorada");
            return false;
        }

        // 2. Validar duplicata de música
        if (musicaDedup.existe(trackId)) {
            Logger.debug(LeituraDados.class.getPackageName(), LeituraDados.class.getName(), 
                    "Música duplicada detectada: " + trackId);
            return false;
        }

        // 3. Validar artista
        if (!ArtistaValidator.validar(nomeArtista)) {
            Logger.debug(LeituraDados.class.getPackageName(), LeituraDados.class.getName(), 
                    "Nome do artista vazio, linha " + row.getRowNum() + " ignorada");
            return false;
        }

        // ===== PROCESSAMENTO =====

        // 1. Obter ou criar artista
        Artista artista = obterOuCriarArtista(nomeArtista, dadosArtista, artistaAggregator);

        // 2. Criar música
        Musica musica = criarMusica(dadosMusica, artista);

        // 3. Adicionar música
        musicaDedup.adicionar(musica);

        return true;
    }

    private Artista obterOuCriarArtista(String nomeArtista, Map<String, Object> dados,
                                        ArtistaAggregator aggregator) {
        
        if (aggregator.existe(nomeArtista)) {
            // Artista já existe, agregamos os valores
            Artista artista = aggregator.obter(nomeArtista);
            artista.setLikes(artista.getLikes() + (Long) dados.get("likes"));
            artista.setViews(artista.getViews() + (Long) dados.get("views"));
            return artista;
        } else {
            // Novo artista
            Artista artista = new Artista(
                nomeArtista,
                (Integer) dados.get("popularity"),
                (String) dados.get("genres"),
                (Long) dados.get("followers"),
                (Long) dados.get("views"),
                (Long) dados.get("likes")
            );
            aggregator.adicionarOuAtualizar(artista, (Long) dados.get("views"), 
                                           (Long) dados.get("likes"));
            return artista;
        }
    }

    /**
     * Cria uma objeto Musica a partir dos dados extraídos
     * @param dados mapa com dados da música
     * @param artista artista associado à música
     * @return objeto Musica criado
     */
    private Musica criarMusica(Map<String, Object> dados, Artista artista) {
        return new Musica(
            (String) dados.get("trackId"),
            (Long) dados.get("streams"),
            (String) dados.get("title"),
            (String) dados.get("trackName"),
            (Long) dados.get("views"),
            (Long) dados.get("likes"),
            (Long) dados.get("comments"),
            (BigDecimal) dados.get("danceability"),
            (BigDecimal) dados.get("valence"),
            (BigDecimal) dados.get("energy"),
            (BigDecimal) dados.get("instrumentalness"),
            (BigDecimal) dados.get("speechiness"),
            (BigDecimal) dados.get("loudness"),
            (Integer) dados.get("trackPopularity"),
            artista
        );
    }
}
