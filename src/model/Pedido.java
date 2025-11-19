/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author mathe
 */
public class Pedido {
    private int idPedido, idUsuario, avaliacao;
    private double valorTotal;
    private ArrayList<ItemPedido> itens;
    
    public Pedido(int idUsuario) {
        this.idUsuario = idUsuario;
        this.itens = new ArrayList<>();
        this.valorTotal = 0;
        this.avaliacao = 0; 
    }
    
    public void adicionarItem(ItemPedido item) {
        this.itens.add(item);
        this.recalcularValorTotal();
    }
    
    public void recalcularValorTotal() {
        this.valorTotal = 0;
        for (ItemPedido item : itens) {
            this.valorTotal += item.getValorTotalItem();
        }
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public ArrayList<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(ArrayList<ItemPedido> itens) {
        this.itens = itens;
    }
}
