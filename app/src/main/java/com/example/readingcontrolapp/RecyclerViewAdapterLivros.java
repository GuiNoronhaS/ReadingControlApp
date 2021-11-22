package com.example.readingcontrolapp;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.readingcontrolapp.dao.livro.SQLiteLivrosDAO;
import com.example.readingcontrolapp.entidade.Livro;

import java.util.List;

public class RecyclerViewAdapterLivros  extends RecyclerView.Adapter<RecyclerViewAdapterLivros.ViewHolderLivro> {

    private List<Livro> listaLivros;
    private Context context;
    private LivroClickListener clickListener;
    private LayoutInflater inflater;

    RecyclerViewAdapterLivros(Context context, List<Livro> listaLivros) {
        this.inflater = LayoutInflater.from(context);
        this.listaLivros = listaLivros;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderLivro onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_recycler_view_adapter_livros,parent,false);
        return new ViewHolderLivro(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderLivro livroHolder, final int posicao) {
        Livro livro = listaLivros.get(posicao);
        livroHolder.livroTituloView.setText("Titulo: " + livro.getTitulo()+"  ");
        livroHolder.livroAutorView.setText("Autor: " + livro.getAutor()+"  ");
        livroHolder.editoraLivroView.setText("Editora: " + livro.getEditora()+"  ");
        livroHolder.contadorPaginasLivroView.setText(livro.getPaginasLidas() +" / "+livro.getTotalPaginas());
        livroHolder.estadoLeituraView.setText("Estado: " + livro.getEstadoLeitura()+ "  ");
        livroHolder.leituraLivroProgressBar.setProgress(
                (Integer.parseInt(livro.getPaginasLidas()) * 100 /Integer.parseInt(livro.getTotalPaginas())));

        livroHolder.botaoEditar.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                alteraLivro(posicao);
            }
        });
        livroHolder.botaoCompartilhar.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                compartilhaLivro(posicao);
            }
        });
        livroHolder.boatoExcluir.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeLivro(posicao);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaLivros.size();
    }

    public void atualizarLista(List<Livro> novaListaDeLivro) {
        listaLivros = novaListaDeLivro;
        notifyDataSetChanged();
    }

    public void removeLivro(int posicao) {
        SQLiteLivrosDAO database = new SQLiteLivrosDAO();
        boolean retorno = database.excluir(getLivro(posicao).getLivroID());
        if(retorno) {
            listaLivros = database.selectALL();
            atualizarLista(listaLivros);
        }
    }

    public void alteraLivro(int posicao) {
        Intent intent = new Intent(context, CadastroEdicaoLivros.class);
        intent.putExtra("livro", getLivro(posicao));
        intent.putExtra("editar", "atualizar");
        ((Activity) context).startActivityForResult(intent, 0);
    }

    public void compartilhaLivro(int posicao) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, getLivro(posicao).compartilharStatus());
        shareIntent.setType("text/plain");
        ((Activity) context).startActivity(Intent.createChooser(shareIntent, "Compartilhar estado de Leitura"));
    }

    public class ViewHolderLivro extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageButton botaoEditar;
        ImageButton botaoCompartilhar;
        ImageButton boatoExcluir;
        TextView livroTituloView;
        TextView livroAutorView;
        TextView editoraLivroView;
        TextView contadorPaginasLivroView;
        TextView estadoLeituraView;
        ProgressBar leituraLivroProgressBar;

        ViewHolderLivro(View livroView) {
            super(livroView);
            livroTituloView = livroView.findViewById(R.id.tituloLivroItemView);
            livroAutorView = livroView.findViewById(R.id.autorLivroItemView);
            editoraLivroView = livroView.findViewById(R.id.editoraLivroItemView);
            contadorPaginasLivroView  = livroView.findViewById(R.id.contadorPaginasLivroItem);
            leituraLivroProgressBar = livroView.findViewById(R.id.progressoLeituraBar);
            estadoLeituraView = livroView.findViewById(R.id.estadoLeituraView);
            botaoEditar = livroView.findViewById(R.id.editItemButton);
            botaoCompartilhar = livroView.findViewById(R.id.shareItemButton);
            boatoExcluir = livroView.findViewById(R.id.deleteItemButton);
        }

        @Override
        public void onClick(View view) {
            if(clickListener != null) clickListener.onItemClick(view,getAdapterPosition());
        }
    }

    Livro getLivro(int i) {
        return listaLivros.get(i);
    }

    void setClickListener(LivroClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface LivroClickListener {
        void onItemClick(View view, int position);
    }

}