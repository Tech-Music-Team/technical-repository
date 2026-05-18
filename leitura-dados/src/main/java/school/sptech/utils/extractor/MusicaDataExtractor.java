package school.sptech.utils.extractor;

import org.apache.poi.ss.usermodel.Row;
import school.sptech.utils.excel.ExcelCellConverter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class MusicaDataExtractor {

    // Índices das colunas do arquivo Excel para dados de música
    private static final int COL_TRACK_ID = 0;
    private static final int COL_TRACK_NAME = 1;
    private static final int COL_TRACK_POPULARITY = 2;
    private static final int COL_DANCEABILITY = 7;
    private static final int COL_ENERGY = 8;
    private static final int COL_LOUDNESS = 9;
    private static final int COL_SPEECHINESS = 10;
    private static final int COL_INSTRUMENTALNESS = 11;
    private static final int COL_VALENCE = 12;
    private static final int COL_TITLE = 13;
    private static final int COL_VIEWS = 14;
    private static final int COL_LIKES = 15;
    private static final int COL_COMMENTS = 16;
    private static final int COL_STREAMS = 17;

    /**
     * Extrai dados de música de uma linha do Excel
     * @param row linha do Excel
     * @return Map com dados da música (trackId, streams, title, features, etc)
     */
    public static Map<String, Object> extrairDados(Row row) {
        Map<String, Object> dados = new HashMap<>();

        // Extração de dados numéricos e textuais
        String trackId = ExcelCellConverter.toStringValue(row.getCell(COL_TRACK_ID));
        String trackName = ExcelCellConverter.toStringValue(row.getCell(COL_TRACK_NAME));
        Integer trackPopularity = ExcelCellConverter.toIntegerValue(row.getCell(COL_TRACK_POPULARITY));
        
        BigDecimal danceability = ExcelCellConverter.toBigDecimalValue(row.getCell(COL_DANCEABILITY));
        BigDecimal energy = ExcelCellConverter.toBigDecimalValue(row.getCell(COL_ENERGY));
        BigDecimal loudness = ExcelCellConverter.toBigDecimalValue(row.getCell(COL_LOUDNESS));
        BigDecimal speechiness = ExcelCellConverter.toBigDecimalValue(row.getCell(COL_SPEECHINESS));
        BigDecimal instrumentalness = ExcelCellConverter.toBigDecimalValue(row.getCell(COL_INSTRUMENTALNESS));
        BigDecimal valence = ExcelCellConverter.toBigDecimalValue(row.getCell(COL_VALENCE));
        
        String title = ExcelCellConverter.toStringValue(row.getCell(COL_TITLE));
        Long views = ExcelCellConverter.toLongValue(row.getCell(COL_VIEWS));
        Long likes = ExcelCellConverter.toLongValue(row.getCell(COL_LIKES));
        Long comments = ExcelCellConverter.toLongValue(row.getCell(COL_COMMENTS));
        Long streams = ExcelCellConverter.toLongValue(row.getCell(COL_STREAMS));

        // Populando o mapa com os dados
        dados.put("trackId", trackId);
        dados.put("trackName", trackName);
        dados.put("trackPopularity", trackPopularity);
        dados.put("danceability", danceability);
        dados.put("energy", energy);
        dados.put("loudness", loudness);
        dados.put("speechiness", speechiness);
        dados.put("instrumentalness", instrumentalness);
        dados.put("valence", valence);
        dados.put("title", title);
        dados.put("views", views);
        dados.put("likes", likes);
        dados.put("comments", comments);
        dados.put("streams", streams);

        return dados;
    }
}
