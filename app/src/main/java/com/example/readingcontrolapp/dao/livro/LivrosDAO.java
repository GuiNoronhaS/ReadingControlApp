package com.example.readingcontrolapp.dao.livro;

import java.util.List;

public interface LivrosDAO {

    public boolean incluir(Object obj);

    public boolean alterar(Object obj);

    public boolean excluir(int ID);

    public List selectALL();

}
