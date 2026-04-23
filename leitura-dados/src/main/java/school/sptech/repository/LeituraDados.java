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
import java.util.List;


public class LeituraDados {
    public LeituraDados() {
    }

    public List<Musica> lerMusicas(String caminhoArquivo) {
        List<Musica> musicas = new ArrayList<>();
        List<Artista> artistas = new ArrayList<>();
        
        Logger.info(LeituraDados.class.getPackageName().toString(), LeituraDados.class.getName().toString(), "Iniciando leitura do arquivo: " + caminhoArquivo);

        try (InputStream caminho = new FileInputStream(caminhoArquivo);
             Workbook workbook = new XSSFWorkbook(caminho)) {

            Sheet baseUnificada = workbook.getSheetAt(0);
            int linhasProcessadas = 0;

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

                // Verifica se a música já existe na lista
                Musica musicaExistente = null;
                for (Musica m : musicas) {
                    if (m.getIdTrack().equals(trackId)) {
                        musicaExistente = m;
                        break;
                    }
                }

                if (musicaExistente != null) {
                    Logger.debug(LeituraDados.class.getPackageName().toString(), LeituraDados.class.getName().toString(), "Música duplicada detectada: " + trackId);
                    continue;
                }

                // Cria nova música
                Musica musica = new Musica(trackId, stream, title, trackName, views, likes, comments,
                        danceability, valence, energy, instrumentalness, speechiness,
                        loudness, trackPopularity, null);

                // Verifica se o artista já existe na lista
                Artista artistaExistente = null;
                for (Artista a : artistas) {
                    if (a.getNome().equals(artistName)) {
                        artistaExistente = a;
                        break;
                    }
                }

                if (artistaExistente == null) {
                    Artista novoArtista = new Artista(artistName, artistPopularity, artistGenres,
                            artistFollowers, views, likes);
                    artistas.add(novoArtista);
                    musica.setArtista(novoArtista);
                } else {
                    artistaExistente.setLikes(artistaExistente.getLikes() + likes);
                    artistaExistente.setViews(artistaExistente.getViews() + views);
                    musica.setArtista(artistaExistente);
                }

                musicas.add(musica);
                linhasProcessadas++;
            }
            
            Logger.info(LeituraDados.class.getPackageName().toString(), LeituraDados.class.getName().toString(), "Leitura concluída: " + linhasProcessadas + " linhas processadas");

        } catch (IOException e) {
            Logger.error(LeituraDados.class.getPackageName().toString(), LeituraDados.class.getName().toString(), "Erro ao ler arquivo Excel: " + e.getMessage());
            return musicas;
        }

        return musicas;
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
