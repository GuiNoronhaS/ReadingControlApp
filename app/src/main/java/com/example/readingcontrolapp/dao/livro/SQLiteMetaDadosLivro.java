package com.example.readingcontrolapp.dao.livro;

public interface SQLiteMetaDadosLivro {

    public static final String TABLE = "LIVRO";
    public static final String PrimaryKeys = "LIVROID";
    public static final String[] METADADOSCOLUNAS = {"LIVROID","TITULO","AUTOR","EDITORA","TOTALPAGINAS","PAGINASLIDAS","ESTADOLEITURA"};
    public static String[] METADADOSSELECT = {TABLE+".LIVROID", TABLE+".TITULO",TABLE+".AUTOR",
            TABLE+".EDITORA",TABLE+".TOTALPAGINAS",TABLE+".PAGINASLIDAS",TABLE+".ESTADOLEITURA"};
    public static String METADADOSCREATE
            = "CREATE TABLE IF NOT EXISTS " + TABLE + " " +
            "(" + PrimaryKeys + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "TITULO varchar(255) NOT NULL, " +
            "AUTOR varchar(255) NOT NULL, " +
            "EDITORA varchar(255), " +
            "TOTALPAGINAS varchar(255), " +
            "PAGINASLIDAS varchar(255), " +
            "ESTADOLEITURA varchar(255))";
}
