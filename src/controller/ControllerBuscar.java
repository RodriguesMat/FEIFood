/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.AlimentoDAO;
import dao.Conexao;
import model.ItemPedido;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.util.Locale;
import java.awt.BorderLayout;

/**
 *
 * @author mathe
 */
public class ControllerBuscar {

    public ControllerBuscar() {
    }

    public void realizarBusca(JTextField campoBusca, JPanel painelParaResultados, String categoria) {
        String termo = campoBusca.getText();
        painelParaResultados.removeAll();
        painelParaResultados.setLayout(new BoxLayout(painelParaResultados, BoxLayout.Y_AXIS));

        Conexao conexao = new Conexao();
        try (Connection conn = conexao.GetConnection()) {
            AlimentoDAO alimentoDAO = new AlimentoDAO(conn);
            ResultSet rs; 

            if (categoria == null || categoria.isEmpty()) {
               rs = alimentoDAO.buscarPorNome(termo);
            } else {
               if (termo.isEmpty()) {
                   rs = alimentoDAO.buscarCategoria(categoria);
                } else {
                    rs = alimentoDAO.buscarPorNomeECategoria(termo, categoria);
                }
            }

            while (rs.next()) {
                final int idAlimentoDoBanco = rs.getInt("ID"); 
                final String nomeDoBanco = rs.getString("Nome");
                final double precoDoBanco = rs.getDouble("Valor");
                final String infoDoBanco = rs.getString("Informacoes");

                JPanel cardItem = new JPanel(new BorderLayout(10, 0));
                cardItem.setBorder(new EmptyBorder(0, 10, 0, 0));
                String textoItem = String.format(Locale.US, 
                    "%s R$ %.2f", 
                    nomeDoBanco, 
                    precoDoBanco
                );
                JLabel lblItem = new JLabel(textoItem);
                cardItem.add(lblItem, BorderLayout.CENTER); 
                JPanel painelBotoes = new JPanel();
                
                JButton btAdicionar = new JButton("Adicionar");
                btAdicionar.addActionListener(e -> {
                    
                    int quantidade = 1; 
                    ItemPedido novoItem = new ItemPedido(
                        idAlimentoDoBanco, 
                        nomeDoBanco, 
                        quantidade, 
                        precoDoBanco
                    );
                    ControllerPedido.adicionarItem(novoItem);
                    
                    JOptionPane.showMessageDialog(painelParaResultados,
                            nomeDoBanco + " adicionado ao carrinho!");
                });
                painelBotoes.add(btAdicionar);
                
                JButton btInfo = new JButton("i");
                btInfo.addActionListener(e -> {

                JOptionPane.showMessageDialog(
                    painelParaResultados,      
                    infoDoBanco,              
                    "Informações - " + nomeDoBanco, 
                JOptionPane.INFORMATION_MESSAGE
                );
            });
                painelBotoes.add(btInfo);
                cardItem.add(painelBotoes, BorderLayout.EAST);
                painelParaResultados.add(cardItem);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(painelParaResultados.getTopLevelAncestor(), 
                                          "Erro ao buscar no banco de dados: \n" + e.getMessage(),
                                          "Erro de Busca",
                                          JOptionPane.ERROR_MESSAGE);
        }
        
        painelParaResultados.revalidate();
        painelParaResultados.repaint();
    }
    
    public void iniciarBuscaGlobal(javax.swing.JFrame viewPaginaInicial, String termoBusca) {
        view.Busca telaBusca = new view.Busca();
        JTextField campoBuscaDestino = telaBusca.getTxtBuscar();
        JPanel painelDestino = telaBusca.getPanelResultados();
        campoBuscaDestino.setText(termoBusca);      
        realizarBusca(campoBuscaDestino, painelDestino, null);

        telaBusca.setVisible(true);
        viewPaginaInicial.dispose();
    }
    
    public void iniciarBuscaCategoria(javax.swing.JFrame viewPaginaInicial, String categoria) {
        view.Busca telaBusca = new view.Busca();
        JTextField campoBuscaDestino = telaBusca.getTxtBuscar();
        JPanel painelDestino = telaBusca.getPanelResultados();
        realizarBusca(campoBuscaDestino, painelDestino, categoria); 

        telaBusca.setVisible(true);
        viewPaginaInicial.dispose();
    }
}
