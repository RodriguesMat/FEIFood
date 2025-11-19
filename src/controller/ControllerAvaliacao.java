package controller;

import dao.Conexao;
import dao.PedidoDAO;
import view.Avaliacao;
import view.PaginaInicial;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author mathe
 */
public class ControllerAvaliacao {
    
    private final Avaliacao view;

    public ControllerAvaliacao(Avaliacao view) {
        this.view = view;
    }
    
    public void salvarAvaliacao() {
        int idPedido = view.getIdPedidoParaAvaliar();
        int nota = -1; 
        
        if (view.getRbZero().isSelected()) {
            nota = 0;
        } else if (view.getRbUm().isSelected()) {
            nota = 1;
        } else if (view.getRbDois().isSelected()) {
            nota = 2;
        } else if (view.getRbTres().isSelected()) {
            nota = 3;
        } else if (view.getRbQuatro().isSelected()) {
            nota = 4;
        } else if (view.getRbCinco().isSelected()) {
            nota = 5;
        }

        if (nota == -1) {
            JOptionPane.showMessageDialog(view, 
                                        "Por favor, selecione uma nota de 0 a 5"
                                                + " estrelas.",
                                        "Aviso",
                                        JOptionPane.WARNING_MESSAGE);
            return;
        }

        Conexao conexao = new Conexao();
        try (Connection conn = conexao.GetConnection()) {
            PedidoDAO pedidoDAO = new PedidoDAO(conn);
            pedidoDAO.atualizarAvaliacao(idPedido, nota);
            
            JOptionPane.showMessageDialog(view, 
                                         "Obrigado por avaliar!", 
                                         "Sucesso", 
                                         JOptionPane.INFORMATION_MESSAGE);
            
            PaginaInicial home = new PaginaInicial();
            home.setVisible(true);
            view.dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, 
                                         "Erro ao salvar sua avaliação:\n" 
                                          + e.getMessage(), 
                                         "Erro de Banco de Dados", 
                                         JOptionPane.ERROR_MESSAGE);
        }
    }
}