package br.com.samaragaldino.atividadefase2samaragaldino;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GerenciadorDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "projetoatv.db";
    private static final int DB_VERSION = 3;

    public GerenciadorDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Método chamado ao criar o banco de dados pela primeira vez
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Cria a tabela Produto com colunas _id, nome, codigo, estoque, categoria e disponivel
        db.execSQL("CREATE TABLE Produto " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT, " +
                "codigo TEXT, " +
                "estoque TEXT, " +
                "categoria TEXT, " +
                "disponivel TEXT)"); // Adiciona a coluna disponivel
    }


    // Método chamado quando há uma atualização do esquema do banco de dados
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Remove a tabela Produto se existir e chama onCreate para recriá-la
        db.execSQL("DROP TABLE IF EXISTS Produto");
        onCreate(db);
    }

    // Método para inserir um novo produto no banco de dados
    public boolean inserirProduto(String nome, String codigo, String estoque, String categoria, String disponivel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", nome);
        contentValues.put("codigo", codigo);
        contentValues.put("estoque", estoque);
        contentValues.put("categoria", categoria);
        contentValues.put("disponivel", disponivel);
        long result = db.insert("Produto", null, contentValues);
        return result != -1; // Retorna verdadeiro se a inserção foi bem-sucedida
    }


    // Método para obter todos os produtos do banco de dados
    public Cursor obterProdutos() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM Produto", null);
    }

    // Método para remover um produto do banco de dados com base no ID
    public boolean removerProduto(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("Produto", "_id=?", new String[]{String.valueOf(id)});
        return result > 0; // Retorna verdadeiro se a remoção foi bem-sucedida
    }
}