package school.sptech.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import school.sptech.entities.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConexaoBanco {

    private final JdbcTemplate jdbcTemplate;

    private Properties carregarCredenciais() {
        Properties propriedades = new Properties();
        try (InputStream entrada = new FileInputStream("src/main/resources/application.properties")) {
            propriedades.load(entrada);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar arquivo de configuração: " + e.getMessage(), e);
        }
        return propriedades;
    }

    public ConexaoBanco() {
        Logger.info(ConexaoBanco.class.getPackageName().toString(), ConexaoBanco.class.getName().toString(), "Inicializando conexão com banco de dados");
        
        try {
            Properties propriedades = carregarCredenciais();
            String url = propriedades.getProperty("db.url");

            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setUrl(url);
            basicDataSource.setUsername(propriedades.getProperty("db.username"));
            basicDataSource.setPassword(propriedades.getProperty("db.password"));

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