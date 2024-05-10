package br.com.samaragaldino.atividadefase2samaragaldino;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private GerenciadorDB gerenciadorDB;
    private EditText nomeEditText, emailEditText, matriculaEditText;
    private Spinner spinnerCategorias;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialização do banco de dados e dos campos de entrada
        gerenciadorDB = new GerenciadorDB(this);
        nomeEditText = findViewById(R.id.nome);
        emailEditText = findViewById(R.id.email);
        matriculaEditText = findViewById(R.id.matricula);
        spinnerCategorias = findViewById(R.id.categorias);
    }

    // Método chamado quando o botão de inserção de produto é clicado
    public void inserirProduto(View view) {
        String nome = nomeEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String cpf = matriculaEditText.getText().toString();
        String categoria = spinnerCategorias.getSelectedItem().toString();

        // Encontra o RadioGroup e obtém o ID do botão de opção selecionado
        RadioGroup radioGroup = findViewById(R.id.radioGroupDisponivel);
        int radioButtonID = radioGroup.getCheckedRadioButtonId();

        // Verifica se algum botão de opção está selecionado
        if (radioButtonID != -1) {
            // Encontra o botão de opção selecionado pelo ID
            RadioButton radioButtonSelecionado = findViewById(radioButtonID);

            // Obtém o texto do botão de opção selecionado
            String disponivel = radioButtonSelecionado.getText().toString();


            // Verificação se algum campo está vazio
            String matricula = null;
            if (nome.trim().isEmpty() || email.trim().isEmpty() || matricula.trim().isEmpty() || categoria.trim().isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Inserção do produto no banco de dados
            boolean inserido = gerenciadorDB.inserirProduto(nome, email, cpf, categoria, disponivel);

            // Exibição de mensagem de sucesso ou falha na inserção
            if (inserido) {
                limparCampos();
                Toast.makeText(this, "Inscrição realizada com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Falha ao realizar o inscrição", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Nenhum botão de opção selecionado
            Toast.makeText(this, "Por favor, selecione a disponibilidade", Toast.LENGTH_SHORT).show();
        }
    }

    // Método chamado quando o botão de consulta de produtos é clicado
    public void consultarProdutos(View view) {
        Intent intent = new Intent(this, ListViewActivity.class);
        startActivity(intent);
    }

    // Método para limpar os campos de entrada
    private void limparCampos() {
        nomeEditText.setText("");
        emailEditText.setText("");
        matriculaEditText.setText("");
    }

    // Método chamado quando o botão de voltar é clicado
    public void voltar(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
