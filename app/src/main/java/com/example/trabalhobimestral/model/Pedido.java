package com.example.trabalhobimestral.model;

import java.util.ArrayList;

public class Pedido {

    private Cliente cliente;
    private ArrayList<Produto> listaProdutos;
    private double valorTotal;
    private int totalProdutos;
    private int Produtos;
    private int qntdParcelas;

    public Pedido() {
    }

    public Pedido(Cliente cliente, ArrayList<Produto> listaProdutos, double valorTotal, int totalProdutos, int produtos, int qntdParcelas) {
        this.cliente = cliente;
        this.listaProdutos = listaProdutos;
        this.valorTotal = valorTotal;
        this.totalProdutos = totalProdutos;
        Produtos = produtos;
        this.qntdParcelas = qntdParcelas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<Produto> getListaProdutos() {
        return listaProdutos;
    }

    public void setListaProdutos(ArrayList<Produto> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getTotalProdutos() {
        return totalProdutos;
    }

    public void setTotalProdutos(int totalProdutos) {
        this.totalProdutos = totalProdutos;
    }

    public int getProdutos() {
        return Produtos;
    }

    public void setProdutos(int produtos) {
        Produtos = produtos;
    }

    public int getQntdParcelas() {
        return qntdParcelas;
    }

    public void setQntdParcelas(int qntdParcelas) {
        this.qntdParcelas = qntdParcelas;
    }
}
