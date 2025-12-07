 package model;

public class Filme {

    private String titulo;

    private double nota;

    private String id;

    private StatusFilme status;

    private String review;

    private String linkImagem;

    public String getLinkImagem() { // <-- ADICIONE ESTE GETTER
        return linkImagem;
    }

    public void setLinkImagem(String linkImagem) { // <-- ADICIONE ESTE SETTER
        this.linkImagem = linkImagem;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public StatusFilme getStatus() {
        return status;
    }

    public void setStatus(StatusFilme status) {
        this.status = status;
    }

    //construtor
    public Filme(String titulo, String id, double nota, StatusFilme status, String linkImagem) {
        this.titulo = titulo;
        this.id = id;
        this.nota = nota;
        this.status = status;
        this.linkImagem = linkImagem;
    }
    // cons vazio para o json
    public Filme() {}

    @Override
    public String toString() {
        return titulo + " - " + nota + "★";
    }
}
