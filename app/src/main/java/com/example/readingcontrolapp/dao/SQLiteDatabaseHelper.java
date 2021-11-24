package com.example.readingcontrolapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.readingcontrolapp.dao.livro.SQLiteMetaDadosLivro;


public class SQLiteDatabaseHelper extends SQLiteOpenHelper implements SQLiteDatabaseDados,SQLiteMetaDadosLivro {

    public SQLiteDatabaseHelper(Context context) {
        super(context, SQLiteDatabaseDados.BANCODEDADOS, null, SQLiteDatabaseDados.VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQLiteMetaDadosLivro.METADADOSCREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,int versaoanterior, int versaonova) {
        if(versaoanterior < versaonova) {
            //Atualizar Banco
        }
    }
}
