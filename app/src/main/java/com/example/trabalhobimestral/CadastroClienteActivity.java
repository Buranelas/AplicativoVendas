package com.example.trabalhobimestral;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabalhobimestral.model.Cliente;

import java.util.ArrayList;

public class CadastroClienteActivity extends AppCompatActivity {

    private EditText edNomeCliente;
    private EditText edCpf;
    private Button btSalvar;
    private TextView tvListaClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        edNomeCliente = findViewById(R.id.edNomeCliente);
        edCpf = findViewById(R.id.edCpf);
        btSalvar = findViewById(R.id.btSalvar);
        tvListaClientes = findViewById(R.id.tvListaClientes);

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarCliente();
            }
        });
        
            atualizarCliente();
    }

    private void atualizarCliente() {
        ArrayList<Cliente> lista = Controller.getInstance().getListaClientes();
        String texto = "";
        for (Cliente cli : lista) {
            texto += "Nome: " + cli.getNome() + "\n" +
                    "CPF: " + cli.getCpf() + "\n";
        }
        tvListaClientes.setText(texto);
    }

    private void salvarCliente() {
        if (edNomeCliente.getText().toString().isEmpty()) {
            edNomeCliente.setError("Informe o nome do cliente!");
            edNomeCliente.requestFocus();
            return;
        }

        if (edCpf.getText().toString().isEmpty()) {
            edCpf.setError("Informe o CPF do cliente!");
            edCpf.requestFocus();
            return;
        }

        Cliente cliente = new Cliente();
        cliente.setNome(edNomeCliente.getText().toString());
        cliente.setCpf(edCpf.getText().toString());

        Controller.getInstance().salvarCliente(cliente);

        Toast.makeText(this, "Cliente salvo com sucesso!", Toast.LENGTH_LONG).show();

        this.finish();
    }
}