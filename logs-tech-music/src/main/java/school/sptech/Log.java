package school.sptech;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    private static String timestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(new Date());
    }

    public static void info(String modulo, String acao, String mensagem) {
        System.out.println("[" + timestamp() + "] [INFO] [" + modulo + "] [" + acao + "] - " + mensagem);
    }

    public static void warn(String modulo, String acao, String mensagem) {
        System.out.println("[" + timestamp() + "] [WARN] [" + modulo + "] [" + acao + "] - " + mensagem);
    }

    public static void error(String modulo, String acao, String mensagem) {
        System.out.println("[" + timestamp() + "] [ERROR] [" + modulo + "] [" + acao + "] - " + mensagem);
    }

    public static void log(String nivel, String modulo, String acao, String mensagem) {
        System.out.printf("[%s] %-5s | %-12s | %-10s | %s%n",
                timestamp(),
                nivel,
                modulo,
                acao,
                mensagem);
    }

    public static void exception(String modulo, String acao, Exception e) {
        System.out.println("[" + timestamp() + "] [ERROR] [" + modulo + "] [" + acao + "] - Exceção: " + e.getMessage());
    }


}
