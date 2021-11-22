package com.example.readingcontrolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.readingcontrolapp.dao.livro.SQLiteLivrosDAO;
import com.example.readingcontrolapp.entidade.Livro;

public class CadastroEdicaoLivros extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView tituloLivroEditView;
    private TextView autorLivroEditView;
    private TextView editoraLivroEditView;
    private TextView paginasLidasLivroEditView;
    private TextView totalPaginasLivrosEditView;
    private Spinner estadoLivroSpinner;

    private Button salvarButton;
    private Button voltarButton;

    private String metodoParaChamar;
    private int livroID;
    private String[] estados = {"Adquirir","Na fila","Lendo","Parado","Abandonado","Completo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_edicao_livros);

        tituloLivroEditView = findViewById(R.id.editNomeLivroView);
        autorLivroEditView = findViewById(R.id.editNomeAutorView);
        editoraLivroEditView = findViewById(R.id.editEditoraView);
        paginasLidasLivroEditView = findViewById(R.id.editTextNumberPaginasLidas);
        totalPaginasLivrosEditView = findViewById(R.id.editTextNumberPaginasTotal);
        estadoLivroSpinner = findViewById(R.id.spinnerEstadoLeitura);

        salvarButton = findViewById(R.id.salvarButton);
        voltarButton = findViewById(R.id.voltarButton);


        estadoLivroSpinner.setOnItemSelectedListener(this);
        ArrayAdapter adaptadorArray = new ArrayAdapter(this,android.R.layout.simple_spinner_item, estados);
        adaptadorArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        estadoLivroSpinner.setAdapter(adaptadorArray);

        if (getIntent().getExtras() != null  && getIntent().hasExtra("livro")) {
            Livro livro = (Livro)getIntent().getExtras().get("livro");
            livroID = livro.getLivroID();
            metodoParaChamar = getIntent().getStringExtra("editar");
            tituloLivroEditView.setText(livro.getTitulo());
            autorLivroEditView.setText(livro.getAutor());
            editoraLivroEditView.setText(livro.getEditora());
            paginasLidasLivroEditView.setText(livro.getPaginasLidas());
            totalPaginasLivrosEditView.setText(livro.getTotalPaginas());
            estadoLivroSpinner.setSelection(adaptadorArray.getPosition(livro.getEstadoLeitura()));
        } else {
            metodoParaChamar = "incluir";
            tituloLivroEditView.setText("");
            autorLivroEditView.setText("");
            editoraLivroEditView.setText("");
            paginasLidasLivroEditView.setText("");
            totalPaginasLivrosEditView.setText("");
            estadoLivroSpinner.setSelection(adaptadorArray.getPosition("Adquirir"));
        }
    }

    public void onClickVoltar(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onClickSalvar(View v) {
        String tituloLivro = tituloLivroEditView.getText().toString();
        String autorLivro = autorLivroEditView.getText().toString();
        String editoraLivro = editoraLivroEditView.getText().toString();
        String paginasLidas = paginasLidasLivroEditView.getText().toString();
        String paginasTotal = totalPaginasLivrosEditView.getText().toString();
        if(validaCadastro(tituloLivro,autorLivro,editoraLivro,paginasLidas,paginasTotal)) {
            setResult(RESULT_CANCELED);
        } else {
            if(metodoParaChamar.equals("atualizar")){
                SQLiteLivrosDAO livrosDAO = new SQLiteLivrosDAO();
                Livro livroEditar = new Livro(
                        livroID,
                        tituloLivro,
                        autorLivro,
                        editoraLivro,
                        paginasTotal,
                        paginasLidas,
                        estados[estadoLivroSpinner.getSelectedItemPosition()]
                );
                livrosDAO.alterar(livroEditar);
                setResult(RESULT_OK);
            } else if(metodoParaChamar.equals("incluir")){
                SQLiteLivrosDAO livrosDAO = new SQLiteLivrosDAO();
                Livro livroIncluir = new Livro(
                        0,
                        tituloLivro,
                        autorLivro,
                        editoraLivro,
                        paginasTotal,
                        paginasLidas,
                        estados[estadoLivroSpinner.getSelectedItemPosition()]
                );
                livrosDAO.incluir(livroIncluir);
                setResult(RESULT_OK);
            } else {
                setResult(RESULT_CANCELED);
            }
        }
        finish();
    }

    private boolean validaCadastro(String titulo,String autor, String editora, String paginasLidas, String paginasTotal) {
        if(titulo.equals("") ||
                autor.equals("") ||
                editora.equals("") ||
                paginasLidas.equals("") ||
                paginasTotal.equals("") ||
                conferePaginasLidasEstaDeAcordoComTotal(paginasLidas,paginasTotal)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean conferePaginasLidasEstaDeAcordoComTotal(String paginasLidas, String paginasTotal) {
        if(Integer.parseInt(paginasLidas) > Integer.parseInt(paginasTotal)){
            return true;
        } else {
            return false;
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int posicao, long id) {
   }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}