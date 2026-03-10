package school.sptech;

public class Main {
    public static void main(String[] args) {
        try {
            int x = 10 / 0;
        } catch (Exception e) {
            Log.exception("Calculadora", "DIVISAO", e);
        }
    }
}