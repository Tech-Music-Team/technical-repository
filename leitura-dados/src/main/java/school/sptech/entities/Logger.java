package school.sptech.entities;

import school.sptech.enums.LogLevel;
import school.sptech.repository.LogRepository;
import school.sptech.repository.ConexaoBanco;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static final String aplicacao = "leitura-tech-music";
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static LogRepository logRepository;

    public static void conexaoBanco(ConexaoBanco conexaoBanco) {
        logRepository = new LogRepository(conexaoBanco);
    }

    public static void log(LogLevel nivel, String modulo, String classe, String mensagem) {
        String hora = LocalDateTime.now().format(fmt);
        String entrada = String.format("%s [%s] %s - %s - %s - %s", hora, nivel.name(), aplicacao, modulo, classe, mensagem);

        System.out.println(entrada);

        if (logRepository != null) {
            try {
                Log log = new Log(
                    LocalDateTime.now(),
                    nivel,
                    aplicacao,
                    modulo,
                    classe,
                    mensagem
                );
                logRepository.inserir(log);
            } catch (Exception e) {
                System.out.println("Erro ao gravar log no banco de dados: " + e.getMessage());
            }
        }
    }

    public static void debug(String modulo, String classe, String mensagem) {
        log(LogLevel.DEBUG, modulo, classe, mensagem);
    }

    public static void info(String modulo, String classe, String mensagem) {
        log(LogLevel.INFO, modulo, classe, mensagem);
    }

    public static void warn(String modulo, String classe, String mensagem) {
        log(LogLevel.WARN, modulo, classe, mensagem);
    }

    public static void error(String modulo, String classe, String mensagem) {
        log(LogLevel.ERROR, modulo, classe, mensagem);
    }
}
