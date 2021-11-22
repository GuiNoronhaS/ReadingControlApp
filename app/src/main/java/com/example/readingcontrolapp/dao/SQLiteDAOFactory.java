package com.example.readingcontrolapp.dao;

import com.example.readingcontrolapp.dao.livro.LivrosDAO;
import com.example.readingcontrolapp.dao.livro.SQLiteLivrosDAO;

public class SQLiteDAOFactory extends DAOFactory implements SQLiteDatabaseDados {
    @Override
    public LivrosDAO getLivrosDAO() {
        return new SQLiteLivrosDAO();
    }
}
