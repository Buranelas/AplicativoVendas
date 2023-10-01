package com.example.trabalhobimestral.model;

public class Produto {
    private String codigo;
    private String descricao;
    private double valor;

    public Produto() {
    }

    public Produto(String codigo, String descricao, double valor) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.valor = valor;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codProduto) {
        this.codigo = codProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString(){
        return "\n Codigo: " + codigo + "\n" +
                "Produto: " + descricao + "\n" +
                "Valor Produto: " + valor + "\n";
    }
}
