package com.example.readingcontrolapp.dao.livro;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import com.example.readingcontrolapp.dao.DAOFactory;
import com.example.readingcontrolapp.dao.SQLiteDatabaseHelper;
import com.example.readingcontrolapp.entidade.Livro;

public class SQLiteLivrosDAO extends DAOFactory implements LivrosDAO, SQLiteMetaDadosLivro {

    private SQLiteDatabaseHelper databaseHelper;

    public SQLiteLivrosDAO() {
        databaseHelper = new SQLiteDatabaseHelper((getContext()));
    }

    public List selectALL() {
        List<Livro> listaComOResultado = new LinkedList();
        SQLiteDatabase database = null;
        String[] colunasSelect = METADADOSSELECT;
        Cursor resultado = null;
        try {
            database = databaseHelper.getReadableDatabase();
            String sql = "SELECT " +
                    colunasSelect[0]+ ", " +
                    colunasSelect[1]+ ", " +
                    colunasSelect[2]+ ", " +
                    colunasSelect[3]+ ", " +
                    colunasSelect[4]+ ", " +
                    colunasSelect[5]+ ", " +
                    colunasSelect[6]+ " " +" FROM " + TABLE;
            resultado = database.rawQuery(sql,null);
            while(resultado.moveToNext()) {
                Livro livro = new Livro(
                        resultado.getInt(resultado.getColumnIndex(METADADOSCOLUNAS[0])),
                        resultado.getString(resultado.getColumnIndex(METADADOSCOLUNAS[1])),
                        resultado.getString(resultado.getColumnIndex(METADADOSCOLUNAS[2])),
                        resultado.getString(resultado.getColumnIndex(METADADOSCOLUNAS[3])),
                        resultado.getString(resultado.getColumnIndex(METADADOSCOLUNAS[4])),
                        resultado.getString(resultado.getColumnIndex(METADADOSCOLUNAS[5])),
                        resultado.getString(resultado.getColumnIndex(METADADOSCOLUNAS[6]))
                );
                listaComOResultado.add(livro);
            }
        } catch(Exception e) {
            System.out.println("Ocorreu um erro: " + e);
        } finally {
            if(resultado != null) {
                resultado.close();
            }
            if(database != null) {
                database.close();
            }
            resultado = null;
            database = null;
        }
        return listaComOResultado;
    }

    public boolean incluir(Object obj) {
        if(obj == null ) {
            return false;
        } else {
            Livro livro = (Livro) obj;
            SQLiteDatabase database = null;
            try {
                database = databaseHelper.getWritableDatabase();
                ContentValues livroValores = new ContentValues();
                livroValores.put(METADADOSCOLUNAS[1],livro.getTitulo());
                livroValores.put(METADADOSCOLUNAS[2],livro.getAutor());
                livroValores.put(METADADOSCOLUNAS[3],livro.getEditora());
                livroValores.put(METADADOSCOLUNAS[4],livro.getTotalPaginas());
                livroValores.put(METADADOSCOLUNAS[5],livro.getPaginasLidas());
                livroValores.put(METADADOSCOLUNAS[6],livro.getEstadoLeitura());
                database.insert(TABLE,null,livroValores);

            } catch(Exception e) {
                System.out.println("Ocorreu um erro: " + e);
                return false;
            } finally {
                if(database != null) {
                    database.close();
                }
                database=null;
            }
            return true;
        }
    }

    public boolean alterar(Object obj) {
        if(obj == null ) {
            return false;
        } else {
            Livro livro = (Livro) obj;
            int resultado;
            SQLiteDatabase database = null;
            try {
                database = databaseHelper.getWritableDatabase();
                ContentValues livroValores = new ContentValues();
                livroValores.put(METADADOSCOLUNAS[1],livro.getTitulo());
                livroValores.put(METADADOSCOLUNAS[2],livro.getAutor());
                livroValores.put(METADADOSCOLUNAS[3],livro.getEditora());
                livroValores.put(METADADOSCOLUNAS[4],livro.getTotalPaginas());
                livroValores.put(METADADOSCOLUNAS[5],livro.getPaginasLidas());
                livroValores.put(METADADOSCOLUNAS[6],livro.getEstadoLeitura());
                String alterarID = METADADOSCOLUNAS[0] + " = ?";
                String[] alterarIDArgument = {livro.getLivroID().toString()};
                resultado = database.update(TABLE,livroValores,alterarID, alterarIDArgument);
            } catch(Exception e) {
                System.out.println("Ocorreu um erro: " + e);
                return false;
            } finally {
                if(database != null) {
                    database.close();
                }
                database=null;
            }
            if(resultado > 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean excluir(int ID) {
            SQLiteDatabase database = null;
        int resposta;
            try {
                database = databaseHelper.getWritableDatabase();
                String deletarID = PrimaryKeys + " = " + ID;
                resposta = database.delete(TABLE,deletarID,null);
            } catch (Exception e) {
                System.out.println("Ocorreu um erro: " + e);
                return false;
            } finally {
                if(database != null) {
                    database.close();
                }
                database = null;
            }
            if(resposta > 0) {
                return true;
            } else {
                return false;
            }
        }
    }
