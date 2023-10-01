package com.example.trabalhobimestral;

import com.example.trabalhobimestral.model.Cliente;
import com.example.trabalhobimestral.model.Pedido;
import com.example.trabalhobimestral.model.Produto;

import java.util.ArrayList;

public class Controller {

    private static Controller instancia;
    private ArrayList<Cliente> listaClientes;
    private ArrayList<Produto> listaProdutos;
    private ArrayList<Pedido> listaPedidos;

    public static Controller getInstance() {
        if (instancia == null) {
            return instancia = new Controller();
        }
        return instancia;
    }

    private Controller() {
        listaClientes = new ArrayList<>();
        listaProdutos = new ArrayList<>();
        listaPedidos = new ArrayList<>();
    }

    public void salvarCliente(Cliente cliente) {
        listaClientes.add(cliente);
    }

    public ArrayList<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void salvarProduto(Produto produto) {
        listaProdutos.add(produto);
    }

    public ArrayList<Produto> getListaProdutos() {
        return listaProdutos;
    }

    public void salvarPedido(Pedido pedido) {
        listaPedidos.add(pedido);
    }

    public ArrayList<Pedido> getListaPedidos() {
        return listaPedidos;
    }
}
