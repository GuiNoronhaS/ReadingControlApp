package com.example.readingcontrolapp.entidade;

import java.io.Serializable;

public class Livro implements Serializable {
    private Integer livroID;
    private String titulo;
    private String autor;
    private String editora;
    private String totalPaginas;
    private String paginasLidas;
    private String estadoLeitura;

    public Livro() {
        this(0, "", "", "","0","0","");
    }

    public Livro(Integer livroID, String titulo, String autor, String editora, String totalPaginas, String paginasLidas, String estadoLeitura) {
        setLivroID(livroID);
        setTitulo(titulo);
        setAutor(autor);
        setEditora(editora);
        setTotalPaginas(totalPaginas);
        setPaginasLidas(paginasLidas);
        setEstadoLeitura(estadoLeitura);
    }

    public Integer getLivroID() {
        return livroID;
    }

    public void setLivroID(Integer livroID) {
        this.livroID = livroID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(String totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public String getPaginasLidas() {
        return paginasLidas;
    }

    public void setPaginasLidas(String paginasLidas) {
        this.paginasLidas = paginasLidas;
    }

    public String getEstadoLeitura() {
        return estadoLeitura;
    }

    public void setEstadoLeitura(String estadoLeitura) {
        this.estadoLeitura = estadoLeitura;
    }

    public String compartilharStatus() {
        String menssagem = " Meu Status com o Livro " + this.getTitulo() + " do Autor(a) " + this.getAutor()
        + " é " + this.getEstadoLeitura() + ". Paginas Lidas: " + this.getPaginasLidas() + " de " + this.getTotalPaginas();
        return menssagem;
    }
}
