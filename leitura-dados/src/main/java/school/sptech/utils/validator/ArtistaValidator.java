package school.sptech.utils.validator;

public class ArtistaValidator {

    public static boolean validar(String nome) {
        return nome != null && !nome.trim().isEmpty();
    }

    public static String validarComMensagem(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return "Nome do artista não pode estar vazio";
        }
        return "";
    }
}
