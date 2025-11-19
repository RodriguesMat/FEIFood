/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.Conexao;
import dao.PedidoDAO;
import model.ItemPedido;
import model.Pedido;
import view.Carrinho;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;

/**
 *
 * @author mathe
 */
public class ControllerCarrinho {
    private final Carrinho view;
    private final JPanel painelItens;
    private final JTextField txtTotal;

    public ControllerCarrinho(Carrinho view) {
        this.view = view;
        this.painelItens = view.getPanelResultados(); 
        this.txtTotal = view.getTxtTotal();
    }
    
    public void carregarItensVisuais() {
        painelItens.removeAll();
        painelItens.setLayout(new BoxLayout(painelItens, BoxLayout.Y_AXIS));
        ArrayList<ItemPedido> itens = ControllerPedido.getItensDoPedido();
        
        if (itens.isEmpty()) {
            painelItens.add(new JLabel("  Seu carrinho está vazio."));
        } else {
            for (ItemPedido item : itens) {
                JPanel cardItem = new JPanel(new BorderLayout(10, 0));
                JButton btRemover = new JButton("X");
                btRemover.addActionListener(e -> {
                    removerItem(item);
                });
                cardItem.add(btRemover, BorderLayout.WEST);

                String textoItem = String.format(Locale.US, 
                    "%s (R$ %.2f cada)", 
                    item.getNomeAlimento(), 
                    item.getValorUnitario()
                );
                
                JLabel lblItem = new JLabel(textoItem);
                cardItem.add(lblItem, BorderLayout.CENTER);

                JPanel painelDireita = new JPanel();
                painelDireita.setLayout(new BoxLayout(painelDireita, BoxLayout.X_AXIS));

                JPanel painelQuantidade = new JPanel(); 
                JButton btMenos = new JButton("-");
                
                btMenos.addActionListener(e -> {
                    diminuirItem(item);
                });
                
                JLabel lblQuantidade = new JLabel(String.valueOf(item.getQuantidade()));
                JButton btMais = new JButton("+");
                btMais.addActionListener(e -> {
                    aumentarItem(item);
                });
                
                painelQuantidade.add(btMenos);
                painelQuantidade.add(lblQuantidade);
                painelQuantidade.add(btMais);
                
                double subtotalItem = item.getValorTotalItem();
                JLabel lblSubtotal = new JLabel(String.format(Locale.US, "R$ %.2f", subtotalItem));

                painelDireita.add(painelQuantidade);
                painelDireita.add(Box.createHorizontalStrut(10));
                painelDireita.add(lblSubtotal);
                painelDireita.add(Box.createHorizontalStrut(5));
                
                cardItem.add(painelDireita, BorderLayout.EAST);
                painelItens.add(cardItem);
            }
        }
        double valorTotal = ControllerPedido.getValorTotalDoPedido();
        txtTotal.setText(String.format(Locale.US, "R$ %.2f", valorTotal));
        
        painelItens.revalidate();
        painelItens.repaint();
    }
    
    private void diminuirItem(ItemPedido item) {
        ControllerPedido.diminuirQuantidade(item);
        carregarItensVisuais();
    }

    private void aumentarItem(ItemPedido item) {
        ControllerPedido.aumentarQuantidade(item);
        carregarItensVisuais();
    }
    
    private void removerItem(ItemPedido item) {
        int resposta = JOptionPane.showConfirmDialog(
            view, 
            "Remover '" + item.getNomeAlimento() + "' do carrinho?",
            "Confirmar Remoção",
            JOptionPane.YES_NO_OPTION
        );
        
        if (resposta == JOptionPane.YES_OPTION) {
            ControllerPedido.removerItem(item);
            carregarItensVisuais();
        }
    }
    
    public void finalizarPedido() {
        if (!ControllerUsuario.estaLogado()) {
            JOptionPane.showMessageDialog(view, "Erro: Nenhum usuário está logado.", 
                                          "Erro", 
                                          JOptionPane.ERROR_MESSAGE);
            return;
        }
       
        Pedido pedidoParaSalvar = ControllerPedido.getPedidoAtual();
        
        if (pedidoParaSalvar.getItens().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Seu carrinho está vazio.", 
                                         "Aviso", 
                                         JOptionPane.WARNING_MESSAGE);
            return;
        }

        Conexao conexao = new Conexao();
        try (Connection conn = conexao.GetConnection()) {
            PedidoDAO pedidoDAO = new PedidoDAO(conn);
            int novoIdPedido = pedidoDAO.finalizarPedido(pedidoParaSalvar);
            
            if (novoIdPedido == -1) {
                 JOptionPane.showMessageDialog(view, 
                                               "Erro: Não foi possível obter o "
                                                       + "ID do pedido salvo.", 
                                               "Erro Crítico", 
                                               JOptionPane.ERROR_MESSAGE);
                 return;
            }
            
            JOptionPane.showMessageDialog(view, 
                                          "Pedido finalizado! "
                                                  + "Agora, avalie seu pedido.",
                                          "Sucesso", 
                                          JOptionPane.INFORMATION_MESSAGE);
            ControllerPedido.limparPedidoAtual(); 
            
            view.Avaliacao telaAvaliacao = new view.Avaliacao(novoIdPedido);
            telaAvaliacao.setVisible(true);
            view.dispose(); 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, 
                                          "Erro de banco de dados ao finalizar o pedido:\n" 
                                          + e.getMessage(), 
                                          "Erro Crítico", 
                                          JOptionPane.ERROR_MESSAGE);
        }
    }
}
