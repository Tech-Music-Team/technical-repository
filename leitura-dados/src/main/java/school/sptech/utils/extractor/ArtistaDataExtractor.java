package school.sptech.utils.extractor;

import org.apache.poi.ss.usermodel.Row;
import school.sptech.utils.excel.ExcelCellConverter;

import java.util.HashMap;
import java.util.Map;

public class ArtistaDataExtractor {

    // Índices das colunas do arquivo Excel para dados de artista
    private static final int COL_ARTIST_NAME = 3;
    private static final int COL_ARTIST_POPULARITY = 4;
    private static final int COL_ARTIST_FOLLOWERS = 5;
    private static final int COL_ARTIST_GENRES = 6;
    private static final int COL_VIEWS = 14;
    private static final int COL_LIKES = 15;

    public static Map<String, Object> extrairDados(Row row) {
        Map<String, Object> dados = new HashMap<>();

        // Extração de dados do artista
        String nome = ExcelCellConverter.toStringValue(row.getCell(COL_ARTIST_NAME));
        Integer popularity = ExcelCellConverter.toIntegerValue(row.getCell(COL_ARTIST_POPULARITY));
        Long followers = ExcelCellConverter.toLongValue(row.getCell(COL_ARTIST_FOLLOWERS));
        String genres = ExcelCellConverter.toStringValue(row.getCell(COL_ARTIST_GENRES));
        Long views = ExcelCellConverter.toLongValue(row.getCell(COL_VIEWS));
        Long likes = ExcelCellConverter.toLongValue(row.getCell(COL_LIKES));

        // Populando o mapa com os dados
        dados.put("nome", nome);
        dados.put("popularity", popularity);
        dados.put("followers", followers);
        dados.put("genres", genres);
        dados.put("views", views);
        dados.put("likes", likes);

        return dados;
    }
}
