package com.example.trabalhobimestral;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btCadastroCliente;
    private Button btCadastroProduto;
    private Button btCadastroPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btCadastroCliente = findViewById(R.id.btCadastroCliente);
        btCadastroProduto = findViewById(R.id.btCadastroProduto);
        btCadastroPedido = findViewById(R.id.btCadastroPedido);

        btCadastroCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { abrirCadastroCliente(); }
        });
        btCadastroProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { abrirCadastroProduto(); }
        });
        btCadastroPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { abrirCadastroPedido(); }
        });
    }

    private void abrirCadastroCliente() {
        Intent intent = new Intent(this, CadastroClienteActivity.class);
        startActivity(intent);
    }

    private void abrirCadastroProduto() {
        Intent intent = new Intent(this, CadastroProdutoActivity.class);
        startActivity(intent);
    }

    private void abrirCadastroPedido() {
        Intent intent = new Intent(this, CadastroPedidoActivity.class);
        startActivity(intent);
    }
}