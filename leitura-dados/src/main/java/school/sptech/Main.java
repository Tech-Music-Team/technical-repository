package school.sptech;

import school.sptech.client.S3Provider;
import school.sptech.entities.Musica;
import school.sptech.entities.Artista;
import school.sptech.entities.Logger;
import school.sptech.repository.LeituraDados;
import school.sptech.repository.ConexaoBanco;
import school.sptech.repository.ArtistaRepository;
import school.sptech.repository.MusicaRepository;
import school.sptech.service.S3Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        S3Service s3Service = new S3Service(new S3Provider());
        String bucketName = "tech-music-raw";
        Boolean isDev = true;

        try {
            ConexaoBanco conexaoBanco = new ConexaoBanco();

            Logger.conexaoBanco(conexaoBanco);

            Logger.info(Main.class.getPackageName(), Main.class.getName(), 
                    "========================================");
            Logger.info(Main.class.getPackageName(), Main.class.getName(), 
                    "Iniciando aplicação TECH MUSIC ETL");
            Logger.info(Main.class.getPackageName(), Main.class.getName(), 
                    "Conexão com banco de dados estabelecida");
            Logger.info(Main.class.getPackageName(), Main.class.getName(), 
                    "========================================");

            if (!isDev) {
                // ===== MODO PRODUÇÃO: S3 =====
                try {
                    Logger.info(Main.class.getPackageName(), Main.class.getName(), 
                            "===== MODO PRODUÇÃO: Integração S3 =====");
                    
                    s3Service.listarBuckets();
                    s3Service.listarObjetos(bucketName);
                    s3Service.baixarArquivo(bucketName);

                    Logger.info(Main.class.getPackageName(), Main.class.getName(), 
                            "Arquivo baixado com sucesso do S3");
                    
                    executarFluxoETL("data_base.xlsx", conexaoBanco);
                    
                } catch (Exception e) {
                    Logger.error(Main.class.getPackageName(), Main.class.getName(), 
                            "Erro no fluxo de produção (S3): " + e.getMessage());
                    throw e;
                }
            } else {
                // ===== MODO DESENVOLVIMENTO: Arquivo Local =====
                try {
                    Logger.info(Main.class.getPackageName(), Main.class.getName(), 
                            "===== MODO DESENVOLVIMENTO: Arquivo Local =====");
                    
                    String caminhoLocal = "data/data_base.xlsx";
                    
                    // Validar existência do arquivo
                    File arquivo = new File(caminhoLocal);
                    if (!arquivo.exists()) {
                        throw new FileNotFoundException(
                            "Arquivo não encontrado em: " + arquivo.getAbsolutePath() + 
                            "\nCertifique-se de que o arquivo 'data_base.xlsx' existe no diretório 'data'"
                        );
                    }
                    
                    if (!arquivo.canRead()) {
                        throw new IOException(
                            "Permissão negada: Não é possível ler o arquivo em " + arquivo.getAbsolutePath()
                        );
                    }

                    Logger.info(Main.class.getPackageName(), Main.class.getName(), 
                            "Arquivo local validado: " + arquivo.getAbsolutePath());
                    
                    executarFluxoETL(caminhoLocal, conexaoBanco);
                    
                } catch (FileNotFoundException e) {
                    Logger.error(Main.class.getPackageName(), Main.class.getName(), 
                            "ERRO: Arquivo de dados não encontrado - " + e.getMessage());
                    System.err.println("[DESENVOLVIMENTO] Verifique se 'data/data_base.xlsx' existe");
                    System.exit(1);
                    
                } catch (IOException e) {
                    Logger.error(Main.class.getPackageName(), Main.class.getName(), 
                            "ERRO: Problema ao acessar arquivo local - " + e.getMessage());
                    System.err.println("[DESENVOLVIMENTO] Verifique as permissões do arquivo");
                    System.exit(1);
                    
                } catch (Exception e) {
                    Logger.error(Main.class.getPackageName(), Main.class.getName(), 
                            "ERRO no fluxo de desenvolvimento: " + e.getMessage());
                    throw e;
                }
            }

            Logger.info(Main.class.getPackageName(), Main.class.getName(), 
                    "========================================");
            Logger.info(Main.class.getPackageName(), Main.class.getName(), 
                    "Aplicação finalizada com sucesso");
            Logger.info(Main.class.getPackageName(), Main.class.getName(), 
                    "========================================");

        } catch (Exception e) {
            Logger.error(Main.class.getPackageName(), Main.class.getName(), 
                    "ERRO CRÍTICO na aplicação: " + e.getMessage());
            System.err.println("[ERRO] Aplicação encerrada devido a erro fatal");
            System.exit(1);
        }
    }

    /**
     * Executa o fluxo ETL comum (Extract, Transform, Load)
     * Processa artistas e músicas, persist no banco de dados
     * 
     * @param caminhoArquivo caminho para o arquivo Excel (local ou S3)
     * @param conexaoBanco instância da conexão com banco de dados
     * @throws Exception em caso de erro no processamento
     */
    private static void executarFluxoETL(String caminhoArquivo, ConexaoBanco conexaoBanco) 
            throws Exception {
        
        try {
            ArtistaRepository artistaRepository = new ArtistaRepository(conexaoBanco);
            MusicaRepository musicaRepository = new MusicaRepository(conexaoBanco);

            LeituraDados leituraDados = new LeituraDados();
            List<Musica> musicas = leituraDados.lerMusicas(caminhoArquivo);

            if (musicas.isEmpty()) {
                Logger.warn(Main.class.getPackageName(), Main.class.getName(), 
                        "Nenhuma música foi carregada do arquivo");
                return;
            }

            Logger.info(Main.class.getPackageName(), Main.class.getName(), 
                    "Processando " + musicas.size() + " músicas...");

            // Processar artistas (deduplicação e inserção)
            for (Musica m : musicas) {
                Artista artista = m.getArtista();
                if (artista != null) {
                    List<Artista> artistasExistentes = artistaRepository.buscarPorNome(artista.getNome());

                    if (artistasExistentes.isEmpty()) {
                        artistaRepository.inserir(artista);
                        List<Artista> artistaSalvo = artistaRepository.buscarPorNome(artista.getNome());
                        artista.setIdArtista(artistaSalvo.getFirst().getIdArtista());
                    } else {
                        artista.setIdArtista(artistasExistentes.getFirst().getIdArtista());
                    }
                }
            }

            Logger.info(Main.class.getPackageName(), Main.class.getName(), "Inserindo músicas...");
            
            // Inserir músicas com referência ao artista
            for (Musica m : musicas) {
                if (m.getArtista() != null && m.getArtista().getIdArtista() != null) {
                    musicaRepository.inserir(m);
                }
            }

            Logger.info(Main.class.getPackageName(), Main.class.getName(), 
                    "Processamento concluído com sucesso! " + musicas.size() + " músicas inseridas.");
            
        } catch (Exception e) {
            Logger.error(Main.class.getPackageName(), Main.class.getName(), 
                    "Erro ao processar dados: " + e.getMessage());
            throw e;
        }
    }
}
