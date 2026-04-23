package school.sptech.repository;

import school.sptech.entities.Log;
import org.springframework.jdbc.core.JdbcTemplate;

public class LogRepository {

    private final JdbcTemplate jdbcTemplate;

    public LogRepository(ConexaoBanco conexaoBanco) {
        this.jdbcTemplate = conexaoBanco.getJdbcTemplate();
    }

    public void inserir(Log logEntry) {
        jdbcTemplate.update(
            "INSERT INTO log (fk_usuario, data_hora, nivel, aplicacao, modulo, classe, mensagem) VALUES (?, ?, ?, ?, ?, ?, ?)",
            null,  // Depois insiro o id do Java no banco
            logEntry.getDataHora(),
            logEntry.getNivel().name(),
            logEntry.getAplicacao(),
            logEntry.getModulo(),
            logEntry.getClasse(),
            logEntry.getMensagem()
        );
    }
}
