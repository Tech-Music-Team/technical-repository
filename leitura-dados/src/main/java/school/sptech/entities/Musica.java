package school.sptech.entities;

import java.math.BigDecimal;

public class Musica {
    private Integer idMusica;
    private String idTrack;
    private Long streams;
    private String title;
    private String track;
    private Long views;
    private Long likes;
    private Long comments;
    private BigDecimal danceability;
    private BigDecimal valence;
    private BigDecimal energy;
    private BigDecimal instrumentalness;
    private BigDecimal speechiness;
    private BigDecimal loudness;
    private Integer trackPopularity;
    private Artista artista;

    public Musica() {
    }

    public Musica(String idTrack, Long streams,
                  String title, String track, Long views, Long likes, Long comments,
                  BigDecimal danceability, BigDecimal valence, BigDecimal energy, BigDecimal instrumentalness,
                  BigDecimal speechiness, BigDecimal loudness, Integer trackPopularity, Artista artista) {
        this.idTrack = idTrack;
        this.streams = streams;
        this.title = title;
        this.track = track;
        this.views = views;
        this.likes = likes;
        this.comments = comments;
        this.danceability = danceability;
        this.valence = valence;
        this.energy = energy;
        this.instrumentalness = instrumentalness;
        this.speechiness = speechiness;
        this.loudness = loudness;
        this.trackPopularity = trackPopularity;
        this.artista = artista;
    }

    public Integer getIdMusica() {
        return idMusica;
    }

    public void setIdMusica(Integer idMusica) {
        this.idMusica = idMusica;
    }

    public String getIdTrack() {
        return idTrack;
    }

    public void setIdTrack(String idTrack) {
        this.idTrack = idTrack;
    }

    public Long getStreams() {
        return streams;
    }

    public void setStreams(Long streams) {
        this.streams = streams;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getComments() {
        return comments;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }

    public BigDecimal getDanceability() {
        return danceability;
    }

    public void setDanceability(BigDecimal danceability) {
        this.danceability = danceability;
    }

    public BigDecimal getValence() {
        return valence;
    }

    public void setValence(BigDecimal valence) {
        this.valence = valence;
    }

    public BigDecimal getEnergy() {
        return energy;
    }

    public void setEnergy(BigDecimal energy) {
        this.energy = energy;
    }

    public BigDecimal getInstrumentalness() {
        return instrumentalness;
    }

    public void setInstrumentalness(BigDecimal instrumentalness) {
        this.instrumentalness = instrumentalness;
    }

    public BigDecimal getSpeechiness() {
        return speechiness;
    }

    public void setSpeechiness(BigDecimal speechiness) {
        this.speechiness = speechiness;
    }

    public BigDecimal getLoudness() {
        return loudness;
    }

    public void setLoudness(BigDecimal loudness) {
        this.loudness = loudness;
    }

    public Integer getTrackPopularity() {
        return trackPopularity;
    }

    public void setTrackPopularity(Integer trackPopularity) {
        this.trackPopularity = trackPopularity;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }
}