/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mathe
 */
public class AlimentoDAO {
    private final Connection conexao;

    public AlimentoDAO(Connection conexao){
        this.conexao = conexao;
    }

    public ResultSet buscarPorNome(String termoBusca) throws SQLException {
        String sql = "SELECT * FROM tb_alimentos WHERE \"Nome\" ILIKE ?";
        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setString(1, "%" + termoBusca + "%");
        statement.execute();
        return statement.getResultSet();
    }

    public ResultSet buscarPorNomeECategoria(String termoBusca, String categoria) throws SQLException {
        String sql = "SELECT * FROM tb_alimentos WHERE \"Nome\" ILIKE ? AND \"Categoria\" = ?";
        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setString(1, "%" + termoBusca + "%");
        statement.setString(2, categoria);
        statement.execute();
        return statement.getResultSet();
    }
    
    
    public ResultSet buscarCategoria(String termoBusca) throws SQLException {
        String sql = "SELECT * FROM tb_alimentos WHERE \"Categoria\" ILIKE ?";
        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setString(1, "%" + termoBusca + "%");
        statement.execute();
        return statement.getResultSet();
    }
}
