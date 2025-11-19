/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.ItemPedido;
import model.Pedido;

import java.util.ArrayList;

/**
 *
 * @author mathe
 */
public class ControllerPedido {
    private static Pedido pedidoAtual = null;
    
    public static void iniciarNovoPedido(int idUsuario) {
        pedidoAtual = new Pedido(idUsuario);
    }
    
    public static Pedido getPedidoAtual() {
        if (pedidoAtual == null) {
            if (ControllerUsuario.estaLogado()) {
                iniciarNovoPedido(ControllerUsuario.getIdUsuarioLogado());
            } else {
                return new Pedido(-1); 
            }
        }
        return pedidoAtual;
    }
    
    public static void adicionarItem(ItemPedido item) {
        getPedidoAtual().adicionarItem(item);
    }
    
    public static ArrayList<ItemPedido> getItensDoPedido() {
        return getPedidoAtual().getItens();
    }
    
    public static void limparPedidoAtual() {
        if (ControllerUsuario.estaLogado()) {
            iniciarNovoPedido(ControllerUsuario.getIdUsuarioLogado());
        } else {
            pedidoAtual = null;
        }
    }
    
    public static void removerItem(ItemPedido itemParaRemover) {
        Pedido pedido = getPedidoAtual();
        pedido.getItens().remove(itemParaRemover);
        pedido.recalcularValorTotal();
    }
    
    public static void aumentarQuantidade(ItemPedido item) {
        item.setQuantidade(item.getQuantidade() + 1);
        getPedidoAtual().recalcularValorTotal();
    }
    
    public static void diminuirQuantidade(ItemPedido item) {
        int novaQtd = item.getQuantidade() - 1;
        if (novaQtd <= 0) {
            removerItem(item);
        } else {
            item.setQuantidade(novaQtd);
            getPedidoAtual().recalcularValorTotal();
        }
    }
    
    public static double getValorTotalDoPedido() {
        return getPedidoAtual().getValorTotal();
    }
}
