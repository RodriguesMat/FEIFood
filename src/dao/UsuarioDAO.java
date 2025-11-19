/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author mathe
 */
public class UsuarioDAO {
    private final Connection conexao;
    
    public UsuarioDAO(Connection conexao){
        this.conexao = conexao;
    }
    
    public ResultSet consultarLogin (Usuario usuario) throws SQLException {
        String sql = "SELECT * FROM tb_usuario WHERE email = ? AND senha = ?";
        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setString(1, usuario.getEmail());
        statement.setString(2, usuario.getSenha());
        statement.execute();
        ResultSet resultado = statement.getResultSet();
        return resultado;
    }
    
    public void cadastrarUsuario(Usuario usuario) throws SQLException{
        String sql = "INSERT INTO tb_usuario (nome, email, endereco, telefone, "
                    + "senha) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setString(1, usuario.getNome());
        statement.setString(2, usuario.getEmail());
        statement.setString(3, usuario.getEndereco());
        statement.setString(4, usuario.getTelefone());
        statement.setString(5, usuario.getSenha());
        statement.executeUpdate();
    }
    
}
