package com.example.readingcontrolapp.dao;

import android.content.Context;

import com.example.readingcontrolapp.dao.livro.LivrosDAO;
import com.example.readingcontrolapp.dao.livro.SQLiteLivrosDAO;


public abstract class DAOFactory {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        DAOFactory.context = context;
    }

    public static DAOFactory getDAOFactory() {
        return new SQLiteLivrosDAO();
    }

    public abstract LivrosDAO getLivrosDAO();
}
