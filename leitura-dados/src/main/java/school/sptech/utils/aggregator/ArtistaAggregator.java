package school.sptech.utils.aggregator;

import school.sptech.entities.Artista;
import school.sptech.entities.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArtistaAggregator {

    private Map<String, Artista> artistasMap = new HashMap<>();

    public void adicionarOuAtualizar(Artista artista, Long views, Long likes) {
        if (artista == null || artista.getNome() == null) {
            return;
        }

        String nome = artista.getNome();

        if (artistasMap.containsKey(nome)) {
            // Artista já existe, agregamos os valores
            Artista artistaExistente = artistasMap.get(nome);
            artistaExistente.setViews(artistaExistente.getViews() + views);
            artistaExistente.setLikes(artistaExistente.getLikes() + likes);
            
            Logger.debug(ArtistaAggregator.class.getPackageName(), 
                    ArtistaAggregator.class.getName(),
                    "Artista existente atualizado: " + nome);
        } else {
            // Novo artista
            artistasMap.put(nome, artista);
            
            Logger.debug(ArtistaAggregator.class.getPackageName(), 
                    ArtistaAggregator.class.getName(),
                    "Novo artista adicionado: " + nome);
        }
    }

    public Artista obter(String nome) {
        return artistasMap.get(nome);
    }

    public boolean existe(String nome) {
        return artistasMap.containsKey(nome);
    }

    public List<Artista> obterTodos() {
        return new ArrayList<>(artistasMap.values());
    }

    public int obterQuantidade() {
        return artistasMap.size();
    }
}
