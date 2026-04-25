package school.sptech.repository;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import school.sptech.entities.Artista;
import school.sptech.entities.Logger;
import school.sptech.entities.Musica;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LeituraDados {
    public LeituraDados() {
    }

    public List<Musica> lerMusicas(String caminhoArquivo) {
        Map<String, Musica> musicasMap = new HashMap<>();
        Map<String, Artista> artistasMap = new HashMap<>();
        
        Logger.info(LeituraDados.class.getPackageName().toString(), LeituraDados.class.getName().toString(), "Iniciando leitura do arquivo: " + caminhoArquivo);

        try (InputStream caminho = new FileInputStream(caminhoArquivo);
             Workbook workbook = new XSSFWorkbook(caminho)) {

            Sheet baseUnificada = workbook.getSheetAt(0);
            int linhasProcessadas = 0;
            int linhasIgnoradas = 0;

            for (Row row : baseUnificada) {
                if (row.getRowNum() == 0) continue;

                // Leitura de dados da Música
                String trackId = getCellValueAsString(row.getCell(0));
                String trackName = getCellValueAsString(row.getCell(1));
                Integer trackPopularity = getCellValueAsInteger(row.getCell(2));
                BigDecimal danceability = getCellValueAsBigDecimal(row.getCell(7));
                BigDecimal energy = getCellValueAsBigDecimal(row.getCell(8));
                BigDecimal loudness = getCellValueAsBigDecimal(row.getCell(9));
                BigDecimal speechiness = getCellValueAsBigDecimal(row.getCell(10));
                BigDecimal instrumentalness = getCellValueAsBigDecimal(row.getCell(11));
                BigDecimal valence = getCellValueAsBigDecimal(row.getCell(12));
                String title = getCellValueAsString(row.getCell(13));
                Long views = getCellValueAsLong(row.getCell(14));
                Long likes = getCellValueAsLong(row.getCell(15));
                Long comments = getCellValueAsLong(row.getCell(16));
                Long stream = getCellValueAsLong(row.getCell(17));

                // Leitura de dados do Artista
                String artistName = getCellValueAsString(row.getCell(3));
                Integer artistPopularity = getCellValueAsInteger(row.getCell(4));
                Long artistFollowers = getCellValueAsLong(row.getCell(5));
                String artistGenres = getCellValueAsString(row.getCell(6));

                // Validar trackId
                trackId = trackId.trim();
                if (trackId.isEmpty()) {
                    Logger.debug(LeituraDados.class.getPackageName().toString(), LeituraDados.class.getName().toString(), "TrackId vazio ou inválido, linha " + row.getRowNum() + " ignorada");
                    linhasIgnoradas++;
                    continue;
                }

                // Verificar se a música já existe (O(1) lookup em HashMap)
                if (musicasMap.containsKey(trackId)) {
                    Logger.debug(LeituraDados.class.getPackageName().toString(), LeituraDados.class.getName().toString(), "Música duplicada detectada: " + trackId);
                    linhasIgnoradas++;
                    continue;
                }

                // Normalizar artistName (trim, mantendo case-sensitivity)
                artistName = artistName.trim();
                if (artistName.isEmpty()) {
                    Logger.debug(LeituraDados.class.getPackageName().toString(), LeituraDados.class.getName().toString(), "Nome do artista vazio, linha " + row.getRowNum() + " ignorada");
                    linhasIgnoradas++;
                    continue;
                }

                // Criar nova música
                Musica musica = new Musica(trackId, stream, title, trackName, views, likes, comments,
                        danceability, valence, energy, instrumentalness, speechiness,
                        loudness, trackPopularity, null);

                // Gerenciar artista (O(1) lookup em HashMap)
                Artista artista;
                if (artistasMap.containsKey(artistName)) {
                    // Artista já existe - reutilizar e contabilizar
                    artista = artistasMap.get(artistName);
                    artista.setLikes(artista.getLikes() + likes);
                    artista.setViews(artista.getViews() + views);
                    Logger.debug(LeituraDados.class.getPackageName().toString(), LeituraDados.class.getName().toString(), "Artista existente atualizado: " + artistName);
                } else {
                    // Novo artista - criar e adicionar ao mapa
                    artista = new Artista(artistName, artistPopularity, artistGenres,
                            artistFollowers, views, likes);
                    artistasMap.put(artistName, artista);
                    Logger.debug(LeituraDados.class.getPackageName().toString(), LeituraDados.class.getName().toString(), "Novo artista adicionado: " + artistName);
                }

                musica.setArtista(artista);
                musicasMap.put(trackId, musica);
                linhasProcessadas++;
            }
            
            Logger.info(LeituraDados.class.getPackageName().toString(), LeituraDados.class.getName().toString(), 
                    "Leitura concluída: " + linhasProcessadas + " linhas processadas, " + linhasIgnoradas + " duplicadas/inválidas ignoradas");

        } catch (IOException e) {
            Logger.error(LeituraDados.class.getPackageName().toString(), LeituraDados.class.getName().toString(), "Erro ao ler arquivo Excel: " + e.getMessage());
            return new ArrayList<>();
        }

        return new ArrayList<>(musicasMap.values());
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((int) cell.getNumericCellValue());
        }
        return "";
    }

    private BigDecimal getCellValueAsBigDecimal(Cell cell) {
        if (cell == null) {
            return BigDecimal.ZERO;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            return BigDecimal.valueOf(cell.getNumericCellValue());
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                return new BigDecimal(cell.getStringCellValue());
            } catch (NumberFormatException e) {
                return BigDecimal.ZERO;
            }
        }
        return BigDecimal.ZERO;
    }

    private Integer getCellValueAsInteger(Cell cell) {
        if (cell == null) {
            return 0;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                return Integer.parseInt(cell.getStringCellValue());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    private Long getCellValueAsLong(Cell cell) {
        if (cell == null) {
            return 0L;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            return (long) cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                return Long.parseLong(cell.getStringCellValue());
            } catch (NumberFormatException e) {
                return 0L;
            }
        }
        return 0L;
    }
}
