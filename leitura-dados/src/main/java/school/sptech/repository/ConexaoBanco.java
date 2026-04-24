package school.sptech.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import school.sptech.entities.Logger;


public class ConexaoBanco {

    private final JdbcTemplate jdbcTemplate;

    public ConexaoBanco() {
        Logger.info(ConexaoBanco.class.getPackageName().toString(), ConexaoBanco.class.getName().toString(), "Iniciando conexão com banco de dados");

        try {
            String url = "jdbc:mysql://localhost:3307/tech_music";
            String username = "root";
            String password = "p0o9i8u7";

            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setUrl(url);
            basicDataSource.setUsername(username);
            basicDataSource.setPassword(password);

            this.jdbcTemplate = new JdbcTemplate(basicDataSource);
            Logger.info(ConexaoBanco.class.getPackageName().toString(), ConexaoBanco.class.getName().toString(), "Conexão criado em: " + url);
        } catch (Exception e) {
            Logger.error(ConexaoBanco.class.getPackageName().toString(), ConexaoBanco.class.getName().toString(), "Falha ao conectar: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}