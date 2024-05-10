package br.com.samaragaldino.atividadefase2samaragaldino;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ListViewActivity extends AppCompatActivity {

    private ListView listView;
    private GerenciadorDB gerenciadorDB;
    private long idSelecionado = -1; // ID do item selecionado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        // Inicializa o GerenciadorDB
        gerenciadorDB = new GerenciadorDB(this);

        // Inicializa o ListView
        listView = findViewById(R.id.listView);

        // Atualiza o ListView com os dados do banco de dados
        atualizarListView();

        // Define um listener de clique para a ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Armazena o ID do item selecionado
                idSelecionado = id;
            }
        });
    }

    // Método para atualizar o ListView com os dados do banco de dados
    private void atualizarListView() {
        // Obter dados do banco de dados
        Cursor cursor = gerenciadorDB.obterProdutos();

        // Configurar o adapter para o ListView
        String[] from = {"nome", "email", "matricula", "categoria", "disponivel"};
        int[] to = {R.id.textViewNome, R.id.textViewEmail, R.id.textViewMatricula, R.id.textViewTitulo,R.id.textViewNome };
        SimpleCursorAdapter.ViewBinder viewBinder = new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(@NonNull View view, Cursor cursor, int columnIndex) {
                if (view.getId() == R.id.textViewEmail) {
                    String email = cursor.getString(columnIndex);
                    ((TextView) view).setText("Email: " + email);
                    return true;
                } else if (view.getId() == R.id.textViewMatricula) {
                    String matricula = cursor.getString(columnIndex);
                    ((TextView) view).setText("Matricula: " + matricula);
                    return true;
                } else if (view.getId() == R.id.textViewTitulo) {
                    String categoria = cursor.getString(columnIndex);
                    ((TextView) view).setText("Categoria: " + categoria);
                    return true;
                } else if (view.getId() == R.id.textViewNome) {
                    String disponivel = cursor.getString(columnIndex);
                    ((TextView) view).setText(" Inscrição Disponivel: " + disponivel);
                    return true;
                }

                return false;
            }
        };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.item_produto_layout, cursor, from, to, 0);
        adapter.setViewBinder(viewBinder);

        // Definir o adapter para o ListView
        listView.setAdapter(adapter);
    }



    // Método para apagar um item da ListView
    public void apagarItem(View view) {
        if (idSelecionado != -1) {
            // Remove o item do banco de dados
            boolean removido = gerenciadorDB.removerProduto(idSelecionado);
            if (removido) {
                // Atualiza a ListView após a remoção
                atualizarListView();
                Toast.makeText(this, "Item removido com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Falha ao remover o item", Toast.LENGTH_SHORT).show();
            }
            idSelecionado = -1; // Redefine o ID do item selecionado
        } else {
            Toast.makeText(this, "Nenhum item selecionado", Toast.LENGTH_SHORT).show();
        }
    }

    public void voltar(View view) {
        // Retorna à MainActivity
        finish();
    }
}