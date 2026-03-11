package school.sptech;


class Main {

    public static void main(String[] args) {

        Log log = new Log();

        log.info("TechMusicTeam", "AuthService", "Usuário autenticado");
        log.warn("TechMusicTeam", "AnalyticsService", "Armazenamento próximo do limite");
        log.error("TechMusicTeam", "DatabaseRepository", "Falha ao conectar ao banco");

    }

}