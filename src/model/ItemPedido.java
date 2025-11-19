/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author mathe
 */
public class ItemPedido {
    private int idAlimento, quantidade;
    private String nomeAlimento;
    private double valorUnitario;

    public ItemPedido(int idAlimento, int quantidade, String nomeAlimento, double valorUnitario) {
        this.idAlimento = idAlimento;
        this.quantidade = quantidade;
        this.nomeAlimento = nomeAlimento;
        this.valorUnitario = valorUnitario;
    }

    public ItemPedido(int idAlimento, String nomeAlimento, int quantidade, double valorUnitario) {
        this.idAlimento = idAlimento;
        this.nomeAlimento = nomeAlimento;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;    
    }
    
    public double getValorTotalItem() {
        return this.quantidade * this.valorUnitario;
    }

    public ItemPedido() {
    }

    public int getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(int idAlimento) {
        this.idAlimento = idAlimento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getNomeAlimento() {
        return nomeAlimento;
    }

    public void setNomeAlimento(String nomeAlimento) {
        this.nomeAlimento = nomeAlimento;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }    
}
