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

import java.util.List;

public class Main {
    public static void main(String[] args) {
        S3Service s3Service = new S3Service(new S3Provider());
        String bucketName = "tech-music-raw";

        try {
            ConexaoBanco conexaoBanco = new ConexaoBanco();

            Logger.conexaoBanco(conexaoBanco);

            Logger.info(Main.class.getPackageName().toString(), Main.class.getName().toString(), "Iniciando aplicação");
            Logger.info(Main.class.getPackageName().toString(), Main.class.getName().toString(), "Conexão estabelecida com sucesso");

            s3Service.listarBuckets();
            s3Service.listarObjetos(bucketName);
            s3Service.baixarArquivo(bucketName);

            ArtistaRepository artistaRepository = new ArtistaRepository(conexaoBanco);
            MusicaRepository musicaRepository = new MusicaRepository(conexaoBanco);

            LeituraDados leituraDados = new LeituraDados();
            List<Musica> musicas = leituraDados.lerMusicas("data_base.xlsx");

            Logger.info(Main.class.getPackageName().toString(), Main.class.getName().toString(), "Processando artistas...");

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

            Logger.info(Main.class.getPackageName().toString(), Main.class.getName().toString(), "Inserindo músicas...");
            for (Musica m : musicas) {
                if (m.getArtista() != null && m.getArtista().getIdArtista() != null) {
                    musicaRepository.inserir(m);
                }
            }

            Logger.info(Main.class.getPackageName().toString(), Main.class.getName().toString(), 
                    "Processamento concluído com sucesso! " + musicas.size() + " músicas inseridas.");
        } catch (Exception e) {
            Logger.error(Main.class.getPackageName().toString(), Main.class.getName().toString(), "Erro na programa: " + e.getMessage());
        }
    }
}
