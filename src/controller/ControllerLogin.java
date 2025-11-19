/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.Conexao;
import dao.UsuarioDAO;
import model.Usuario;
import view.Login;
import view.PaginaInicial;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author mathe
 */
public class ControllerLogin {
    private final Login telaLogin;
    
    public ControllerLogin(Login telaLogin){
        this.telaLogin = telaLogin;
    }
    
    public void loginUsuario() throws SQLException{
        String email = telaLogin.getTxtEmail().getText();
        String senha = telaLogin.getTxtSenha().getText();
        Usuario usuarioLogin = new Usuario(email, senha);
        Conexao conexao = new Conexao();
        
        try{
            Connection conn = conexao.GetConnection();
            UsuarioDAO dao = new UsuarioDAO(conn);
            ResultSet res = dao.consultarLogin(usuarioLogin);
            
            if(res.next()){
                int idUsuario = res.getInt("ID"); 
                ControllerUsuario.setIdUsuarioLogado(idUsuario);
                ControllerPedido.iniciarNovoPedido(idUsuario);
                
                JOptionPane.showMessageDialog(telaLogin, 
                                              "Login efetuado com sucesso", 
                                              "Aviso", 
                                              JOptionPane.INFORMATION_MESSAGE);
                
                PaginaInicial telaInicial = new PaginaInicial();
                telaInicial.setVisible(true);
                this.telaLogin.dispose();
            }else{
                JOptionPane.showMessageDialog(telaLogin, 
                                             "Login ou senha inválidos",
                                             "Erro",
                                              JOptionPane.ERROR_MESSAGE);
            }            
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(telaLogin, 
                                             "Erro de conexão com o banco de dados: \n" 
                                                     + e.getMessage(),
                                             "Erro",
                                             JOptionPane.ERROR_MESSAGE);
        }
    }
}
