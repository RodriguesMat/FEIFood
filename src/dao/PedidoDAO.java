/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.ItemPedido;
import model.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * @author mathe
 */
public class PedidoDAO {
    private final Connection conexao;

    public PedidoDAO(Connection conexao) {
        this.conexao = conexao;
    }
    
    public int finalizarPedido(Pedido pedido) throws SQLException {
        
        String sqlPedido = "INSERT INTO tb_pedidos (\"ID_Cliente\", \"Valor_Total\", "
                + "\"Avaliacao\") VALUES (?, ?, ?) RETURNING \"ID_Pedido\"";
        String sqlItens = "INSERT INTO tb_itens (\"ID_Pedido\", \"ID_Alimento\", "
                + "\"Quantidade\", \"Valor_Unitario\", \"Valor_Total\") VALUES (?, ?, ?, ?, ?)";
        
        PreparedStatement stmPedido = null;
        PreparedStatement stmItens = null;
        ResultSet chavesGeradas = null;
        int novoIdPedido = -1;        
        int idCliente = pedido.getIdUsuario();
        double valorTotalPedido = pedido.getValorTotal(); 
        ArrayList<ItemPedido> itens = pedido.getItens();
        
        try {
            conexao.setAutoCommit(false);
            stmPedido = conexao.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            stmPedido.setInt(1, idCliente);
            String valorTotalString = String.format(Locale.US, "%.2f", valorTotalPedido);
            stmPedido.setString(2, valorTotalString);
            stmPedido.setInt(3, 0);
            stmPedido.executeUpdate();
            chavesGeradas = stmPedido.getGeneratedKeys();
            
            if (chavesGeradas.next()) {
                novoIdPedido = chavesGeradas.getInt(1);
            } else {
                throw new SQLException("Falha ao obter o ID do pedido gerado.");
            }
            
            stmItens = conexao.prepareStatement(sqlItens);
            for (ItemPedido item : itens) {
                stmItens.setInt(1, novoIdPedido); 
                stmItens.setInt(2, item.getIdAlimento());
                stmItens.setInt(3, item.getQuantidade());
                String valorUnitarioStr = String.format(Locale.US, "%.2f", item.getValorUnitario());
                String valorTotalItemStr = String.format(Locale.US, "%.2f", item.getValorTotalItem());
                stmItens.setString(4, valorUnitarioStr);
                stmItens.setString(5, valorTotalItemStr);             
                stmItens.addBatch();
            }
            
            stmItens.executeBatch();
            conexao.commit();
            return novoIdPedido; 
        } catch (SQLException e) {
            try {
                if (conexao != null) {
                    System.err.println("Transação falhou. Executando rollback...");
                    conexao.rollback();
                }
            } catch (SQLException eRollback) {
                System.err.println("Erro crítico ao tentar executar o rollback: " 
                        + eRollback.getMessage());
            }
            throw new SQLException("Erro ao finalizar pedido: " + e.getMessage(), e);
        } finally {
            if (chavesGeradas != null) chavesGeradas.close();
            if (stmPedido != null) stmPedido.close();
            if (stmItens != null) stmItens.close();
            if (conexao != null) {
                conexao.setAutoCommit(true);
            }
        }
    }
    public void atualizarAvaliacao(int idPedido, int nota) throws SQLException {
        String sql = "UPDATE tb_pedidos SET \"Avaliacao\" = ? WHERE \"ID_Pedido\" = ?";
        
        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, nota);
            stm.setInt(2, idPedido);
            stm.executeUpdate();
        }
    }
}