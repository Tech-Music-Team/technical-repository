package school.sptech.utils.aggregator;

import school.sptech.entities.Musica;
import school.sptech.entities.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Deduplicador de Músicas
 * Gerencia deduplicação de músicas durante o processamento
 */
public class MusicaDeduplicator {

    private Map<String, Musica> musicasMap = new HashMap<>();

    public void adicionar(Musica musica) {
        if (musica == null || musica.getIdTrack() == null) {
            return;
        }

        String trackId = musica.getIdTrack();

        if (!musicasMap.containsKey(trackId)) {
            musicasMap.put(trackId, musica);
            Logger.debug(MusicaDeduplicator.class.getPackageName(), 
                    MusicaDeduplicator.class.getName(),
                    "Música adicionada: " + trackId);
        } else {
            Logger.debug(MusicaDeduplicator.class.getPackageName(), 
                    MusicaDeduplicator.class.getName(),
                    "Música já existe, duplicata ignorada: " + trackId);
        }
    }

    public boolean existe(String trackId) {
        return musicasMap.containsKey(trackId);
    }

    public List<Musica> obterTodas() {
        return new ArrayList<>(musicasMap.values());
    }

    public int obterQuantidade() {
        return musicasMap.size();
    }
}
