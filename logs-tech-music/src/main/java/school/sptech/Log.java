package school.sptech;

import java.text.SimpleDateFormat;
import java.util.Date;

class Log {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    String data() {
        return sdf.format(new Date());
    }

    void log(String nivel, String service, String classe, String mensagem) {
        System.out.printf("%s %-5s [%s] [%s] - %s%n", data(), nivel, service, classe, mensagem);
    }

    void info(String service, String classe, String mensagem) {
        log("INFO", service, classe, mensagem);
    }

    void warn(String service, String classe, String mensagem) {
        log("WARN", service, classe, mensagem);
    }

    void error(String service, String classe, String mensagem) {
        log("ERROR", service, classe, mensagem);
    }
}