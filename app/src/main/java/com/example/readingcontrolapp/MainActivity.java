package com.example.readingcontrolapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.readingcontrolapp.dao.DAOFactory;
import com.example.readingcontrolapp.dao.livro.SQLiteLivrosDAO;
import com.example.readingcontrolapp.entidade.Livro;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapterLivros.LivroClickListener {

    private Button botaoVaiParaCadastro;
    private Button botaoFecharApp;
    private RecyclerView viewListaLivros;
    private RecyclerViewAdapterLivros adaptadorLivros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoVaiParaCadastro = findViewById(R.id.cadastrarButton);
        botaoFecharApp = findViewById(R.id.fecharButton);
        viewListaLivros = findViewById(R.id.livrosRecyclerView);
        viewListaLivros.setLayoutManager(new LinearLayoutManager(this));
        DAOFactory.setContext(getApplicationContext());
        SQLiteLivrosDAO livrosDAO = new SQLiteLivrosDAO();
        List<Livro> listaLivros = livrosDAO.selectALL();
        adaptadorLivros = new RecyclerViewAdapterLivros(this, listaLivros);
        adaptadorLivros.setClickListener(this);
        viewListaLivros.setAdapter(adaptadorLivros);

    }

    @Override
    public void onItemClick(View view, int posicao) {
        Livro livro = adaptadorLivros.getLivro(posicao);
        Toast.makeText(this, "Clique no nome: " + livro.getTitulo() + " linha número: " + posicao, Toast.LENGTH_SHORT).show();
    }

    public void onclickFechar(View v){
        System.exit(0);
    }

    public void onClickBotaoAdicionar(View v){
        Intent intent = new Intent(this, CadastroEdicaoLivros.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            SQLiteLivrosDAO livrosDAO = new SQLiteLivrosDAO();
            List listaLivros = livrosDAO.selectALL();
            adaptadorLivros.atualizarLista(listaLivros);
        }
    }
}