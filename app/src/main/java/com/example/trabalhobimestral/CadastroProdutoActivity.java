package com.example.trabalhobimestral;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabalhobimestral.model.Produto;

import java.util.ArrayList;

public class CadastroProdutoActivity extends AppCompatActivity {

    private EditText edCodigo;
    private EditText edDescricao;
    private EditText edValor;
    private Button btSalvar;
    private TextView tvListaProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);

        edCodigo = findViewById(R.id.edCodigo);
        edDescricao = findViewById(R.id.edDescricao);
        edValor = findViewById(R.id.edValor);

        btSalvar = findViewById(R.id.btSalvar);

        tvListaProduto = findViewById(R.id.tvListaProduto);

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarProduto();
            }
        });

        atualizarProduto();
    }

    private void atualizarProduto() {
        ArrayList<Produto> lista = Controller.getInstance().getListaProdutos();
        String texto = "";
        for (Produto prod : lista) {
            texto += "Código: " + prod.getCodigo() + "\n" +
                    "Descrição : " + prod.getDescricao() + "\n" +
                    "Valor : " + prod.getValor() + "\n";
        }
        tvListaProduto.setText(texto);
    }


    private void salvarProduto() {
        if (edCodigo.getText().toString().isEmpty()) {
            edCodigo.setError("Informe o Código do Produto !");
            edCodigo.requestFocus();
            return;
        }

        if (edDescricao.getText().toString().isEmpty()) {
            edDescricao.setError("Informe a Descrição do produto!");
            edDescricao.requestFocus();
            return;
        }

        if (edValor.getText().toString().isEmpty() ||
                Double.parseDouble(edValor.getText().toString()) <= 0.0) {
            edValor.setError("Informe o Valor do produto!");
            edValor.requestFocus();
            return;
        }

        Produto produto = new Produto();
        produto.setCodigo(edCodigo.getText().toString());
        produto.setDescricao(edDescricao.getText().toString());
        produto.setValor(Double.parseDouble(edValor.getText().toString()));

        Controller.getInstance().salvarProduto(produto);

        Toast.makeText(this, "Produto Salvo com sucesso!", Toast.LENGTH_LONG).show();

        this.finish();

    }
}