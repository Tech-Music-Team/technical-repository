package school.sptech.entities;

public class Artista {
    private Integer idArtista;
    private String nome;
    private Long views;
    private Integer artistPopularity;
    private Long likes;
    private String artistGenre;
    private Long artistFollowers;

    public Artista() {
    }

    public Artista(String nome, Integer artistPopularity, String artistGenre, Long artistFollowers, Long views, Long likes) {
        this.nome = nome;
        this.artistPopularity = artistPopularity;
        this.artistGenre = artistGenre;
        this.artistFollowers = artistFollowers;
        this.views = views;
        this.likes = likes;
    }

    public Integer getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(Integer idArtista) {
        this.idArtista = idArtista;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Integer getArtistPopularity() {
        return artistPopularity;
    }

    public void setArtistPopularity(Integer artistPopularity) {
        this.artistPopularity = artistPopularity;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public String getArtistGenre() {
        return artistGenre;
    }

    public void setArtistGenre(String artistGenre) {
        this.artistGenre = artistGenre;
    }

    public Long getArtistFollowers() {
        return artistFollowers;
    }

    public void setArtistFollowers(Long artistFollowers) {
        this.artistFollowers = artistFollowers;
    }
}