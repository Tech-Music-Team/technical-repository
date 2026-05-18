package school.sptech.utils.validator;

/**
 * Validador de dados de Música
 * Centraliza regras de validação para dados de música
 */
public class MusicaValidator {

    /**
     * Valida se o trackId é válido
     * @param trackId identificador da faixa de música
     * @return true se válido, false caso contrário
     */
    public static boolean validar(String trackId) {
        return trackId != null && !trackId.trim().isEmpty();
    }

    /**
     * Valida trackId e retorna mensagem descritiva
     * @param trackId identificador da faixa de música
     * @return mensagem de validação ou vazio se válido
     */
    public static String validarComMensagem(String trackId) {
        if (trackId == null || trackId.trim().isEmpty()) {
            return "TrackId não pode estar vazio";
        }
        return "";
    }
}
