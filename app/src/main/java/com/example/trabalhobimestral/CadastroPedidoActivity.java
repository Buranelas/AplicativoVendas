package com.example.trabalhobimestral;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabalhobimestral.model.Cliente;
import com.example.trabalhobimestral.model.Pedido;
import com.example.trabalhobimestral.model.Produto;

import java.util.ArrayList;

public class CadastroPedidoActivity extends AppCompatActivity {

    private ArrayList<Cliente> listaClientes;
    private ArrayList<Produto> listaProdutos;
    private ArrayList<Produto> listaProdutosSelecionados;
    private AutoCompleteTextView acClientes;
    private Spinner spProduto;
    private ImageButton btAdicionar;
    private TextView tvListaProdutos, tvErro, tvTotal, tvTotalValor,
            tvValorTotal, tvRadioGroupError, tvListaPedidos;
    private EditText edQuantidade, edValor, edQuantidadeParcelas;
    private int posicaoSelecionada = 0;
    private String texto = "";
    private int produtosSelecionados = 0;
    private double totalProdutosSelecionados = 0;
    private double totalProdutosSelecionadosAux = 0;
    private RadioGroup rgPagamento;
    private RadioButton rbAPrazo;
    private Button btSalvar;
    private Cliente clienteSelecionado;
    private int numParcelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lancamento_pedido);

        listaProdutosSelecionados = new ArrayList<>();

        acClientes = findViewById(R.id.acClientes);
        spProduto = findViewById(R.id.spProduto);
        btAdicionar = findViewById(R.id.btAdicionar);
        tvListaProdutos = findViewById(R.id.tvListaProdutos);
        edQuantidade = findViewById(R.id.edQuantidade);
        edValor = findViewById(R.id.edValor);
        edQuantidadeParcelas = findViewById(R.id.edQuantidadeParcelas);
        tvErro = findViewById(R.id.tvErro);
        tvTotal = findViewById(R.id.tvTotal);
        tvTotalValor = findViewById(R.id.tvTotalValor);
        rgPagamento = findViewById(R.id.rgPagamento);
        tvValorTotal = findViewById(R.id.tvValorTotal);
        btSalvar = findViewById(R.id.btSalvar);
        rbAPrazo  = findViewById(R.id.rbAPrazo);
        tvRadioGroupError = findViewById(R.id.tvRadioGroupError);
        tvListaPedidos = findViewById(R.id.tvListaPedidos);
        getClientes();
        getProdutos();
        atualizarListaProduto();

        acClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clienteSelecionado = listaClientes.get(position);
            }
        });

        btAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { adicionarProduto(); }
        });

        spProduto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i > 0){
                    posicaoSelecionada = i;
                    tvErro.setVisibility(View.GONE);
                }
                if (l >  0 && listaProdutos.size() > 0) {
                    edValor.setText(Double.toString(listaProdutos.get(i - 1).getValor()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rgPagamento.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rbAVista) {
                    calcularAVista();
                    edQuantidadeParcelas.setVisibility(View.INVISIBLE);
                } else if (i == R.id.rbAPrazo) {
                    tvValorTotal.setText("");
                    edQuantidadeParcelas.setVisibility(View.VISIBLE);
                }
            }
        });

        edQuantidadeParcelas.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    calcularAPrazo();
                }
            }
        });

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { salvarPedido(); }
        });
    }

    private void getClientes() {
        listaClientes = Controller.getInstance().getListaClientes();
        String[]vetClientes = new String[listaClientes.size() + 1];
        vetClientes[0] = "Selecione o cliente";
        for (int i = 0; i < listaClientes.size(); i++) {
            Cliente cliente = listaClientes.get(i);
            vetClientes[i+1] = cliente.getNome();
        }
        ArrayAdapter adapter = new ArrayAdapter(
                CadastroPedidoActivity.this,
                android.R.layout.simple_dropdown_item_1line,
                vetClientes);

        acClientes.setAdapter(adapter);
    }

    private void getProdutos() {
        listaProdutos = Controller.getInstance().getListaProdutos();
        String[]vetProdutos = new String[listaProdutos.size() + 1];
        vetProdutos[0] = "Selecione o produto";
        for (int i = 0; i < listaProdutos.size(); i++) {
            Produto prod = listaProdutos.get(i);
            vetProdutos[i+1] = prod.getDescricao();
        }
        ArrayAdapter adapter = new ArrayAdapter(
                CadastroPedidoActivity.this,
                android.R.layout.simple_dropdown_item_1line,
                vetProdutos);

        spProduto.setAdapter(adapter);
    }

    private void adicionarProduto() {

        if (posicaoSelecionada <=0) {
            tvErro.setVisibility(View.VISIBLE);
            return;
        }

        if (edQuantidade.getText().toString().isEmpty() ||
                Integer.parseInt(edQuantidade.getText().toString()) <= 0) {
            edQuantidade.setError("Quantidade do produto deve ser informada!");
            edQuantidade.requestFocus();
            return;
        }

        if (edValor.getText().toString().isEmpty() ||
                Double.parseDouble(edValor.getText().toString()) <= 0) {
            edValor.setError("Valor do produto deve ser informado!");
            edValor.requestFocus();
            return;
        }

        Produto produto = listaProdutos.get(posicaoSelecionada-1);
        listaProdutosSelecionados.add(produto);
        texto += "Produto: " + produto.getDescricao() + "\n" +
                "Valor UN: R$ " + edValor.getText().toString() + "\n" +
                "Quantidade: " + edQuantidade.getText().toString() + "\n" +
                "_____________________________________\n";

        tvListaProdutos.setText(texto);
        produtosSelecionados += Integer.parseInt(edQuantidade.getText().toString());
        totalProdutosSelecionados += Double.parseDouble(edValor.getText().toString())
                * Integer.parseInt(edQuantidade.getText().toString());
        totalProdutosSelecionadosAux = totalProdutosSelecionados;
        tvTotal.setText("Qtd Produtos: " + produtosSelecionados);
        tvTotalValor.setText("Total: R$ " + totalProdutosSelecionados);
        limparCampos();
    }

    private void calcularAVista() {
        totalProdutosSelecionados = totalProdutosSelecionadosAux;
        Double desconto = (double)totalProdutosSelecionados * 0.05;
        totalProdutosSelecionados = totalProdutosSelecionados - desconto;
        tvValorTotal.setText("Valor total: " + totalProdutosSelecionados + "\n" +
                "Desconto: " + desconto);
    }
    private void calcularAPrazo() {
        if (edQuantidadeParcelas.getText().toString().isEmpty() ||
                Integer.parseInt(edQuantidadeParcelas.getText().toString()) <= 0) {
            edQuantidadeParcelas.setError("Informe a quantidade de parcelas");
            edQuantidadeParcelas.requestFocus();
            return;
        }
        numParcelas = Integer.parseInt(edQuantidadeParcelas.getText().toString());
        totalProdutosSelecionados = totalProdutosSelecionadosAux;
        Double acrescimo = 5.0 / 100.0 * totalProdutosSelecionados;
        totalProdutosSelecionados = totalProdutosSelecionados + acrescimo;
        Double totalParcela = totalProdutosSelecionados
                / Integer.parseInt(edQuantidadeParcelas.getText().toString());
        tvValorTotal.setText("Valor total: " + totalProdutosSelecionados + "\n" +
                "Valor parcela: " + totalParcela + "\n" +
                "Acréscimo: " + acrescimo + "\n" +
                "Qtd parcelas: " + edQuantidadeParcelas.getText().toString());
        edQuantidadeParcelas.setText("");
    }

    private void salvarPedido() {
        if (clienteSelecionado == null) {
            acClientes.setError("Selecione um cliente!");
            acClientes.requestFocus();
            return;
        }

        if (listaProdutosSelecionados.isEmpty()) {
            tvErro.setVisibility(View.VISIBLE);
            return;
        }

        if (rgPagamento.getCheckedRadioButtonId() == -1) {
            tvRadioGroupError.setVisibility(View.VISIBLE);
            return;
        }

        if (rbAPrazo.isChecked()) {
            if (numParcelas <= 0) {
                edQuantidadeParcelas.setError("Informe a quantidade de parcelas!");
                edQuantidadeParcelas.requestFocus();
                return;
            }
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(clienteSelecionado);
        pedido.setListaProdutos(listaProdutosSelecionados);
        pedido.setValorTotal(totalProdutosSelecionados);
        pedido.setTotalProdutos(produtosSelecionados);

        Controller.getInstance().salvarPedido(pedido);

        Toast.makeText(this, "Pedido salvo com sucesso!", Toast.LENGTH_LONG).show();

        this.finish();
    }


    private void atualizarListaProduto() {
        ArrayList<Pedido> lista = Controller.getInstance().getListaPedidos();
        String texto = "";
        for (Pedido pedido : lista) {
            texto += "Cliente: " + pedido.getCliente().getNome() + "\n" +
                    "CPF: " + pedido.getCliente().getCpf() + "\n" +
                    "Valor Total: " + pedido.getValorTotal() + "\n" +
                    "Total Produtos: " + pedido.getTotalProdutos() + "\n" +
                    "Lista Produtos" + pedido.getListaProdutos().toString() + "\n";
        }
        tvListaPedidos.setText(texto);
    }

    private void limparCampos() {
        spProduto.setSelection(0);
        posicaoSelecionada = 0;
        edQuantidade.setText("");
        edValor.setText("");
    }

}