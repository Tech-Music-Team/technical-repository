package school.sptech.entities;

import school.sptech.enums.LogLevel;
import java.time.LocalDateTime;

public class Log {
    private Integer idLog;
    private LocalDateTime dataHora;
    private LogLevel nivel;
    private String aplicacao;
    private String modulo;
    private String classe;
    private String mensagem;

    // Construtor vazio
    public Log() {}

    // Construtor completo
    public Log(LocalDateTime dataHora, LogLevel nivel, String aplicacao,
               String modulo, String classe, String mensagem) {
        this.dataHora = dataHora;
        this.nivel = nivel;
        this.aplicacao = aplicacao;
        this.modulo = modulo;
        this.classe = classe;
        this.mensagem = mensagem;
    }

    // Getters e Setters
    public Integer getIdLog() { 
        return idLog; 
    }
    public void setIdLog(Integer idLog) { 
        this.idLog = idLog; 
    }

    public LocalDateTime getDataHora() { 
        return dataHora; 
    }
    public void setDataHora(LocalDateTime dataHora) { 
        this.dataHora = dataHora; 
    }

    public LogLevel getNivel() { 
        return nivel; 
    }
    public void setNivel(LogLevel nivel) { 
        this.nivel = nivel; 
    }

    public String getAplicacao() { 
        return aplicacao; 
    }
    public void setAplicacao(String aplicacao) { 
        this.aplicacao = aplicacao; 
    }

    public String getModulo() { 
        return modulo; 
    }
    public void setModulo(String modulo) { 
        this.modulo = modulo; 
    }

    public String getClasse() { 
        return classe; 
    }
    public void setClasse(String classe) { 
        this.classe = classe; 
    }

    public String getMensagem() { 
        return mensagem; 
    }
    public void setMensagem(String mensagem) { 
        this.mensagem = mensagem; 
    }
}
