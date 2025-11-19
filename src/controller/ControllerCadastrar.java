/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.Conexao;
import dao.UsuarioDAO;
import model.Usuario;
import view.Cadastrar;
import view.Login;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author mathe
 */
public class ControllerCadastrar {
    private final Cadastrar telaCadastrar;
    
    public ControllerCadastrar(Cadastrar telaCadastrar){
        this.telaCadastrar = telaCadastrar;  
    }
    
    public void cadastrarNovousuario() {
        String nome = telaCadastrar.getTxtNome().getText();
        String email = telaCadastrar.getTxtEmail().getText();
        String endereco = telaCadastrar.getTxtEndereco().getText();
        String telefone = telaCadastrar.getTxtTelefone().getText();
        String senha = telaCadastrar.getTxtSenha().getText();
        
        if (nome.isEmpty() || email.isEmpty() || endereco.isEmpty() ||
            telefone.isEmpty()  ||  senha.isEmpty()) {
             JOptionPane.showMessageDialog(telaCadastrar, 
                                             "Todos espaços são obrigatórios.",
                                             "Erro",
                                              JOptionPane.ERROR_MESSAGE);
             return;
        }
        
        Usuario novoUsuario = new Usuario(nome, email, endereco, telefone, senha);  
        Conexao conexao = new Conexao();
        try{
            Connection conn = conexao.GetConnection();
            UsuarioDAO dao = new UsuarioDAO(conn);
            dao.cadastrarUsuario(novoUsuario);
         
            JOptionPane.showMessageDialog(telaCadastrar, 
                                         "Usuário cadastrado com sucesso!",
                                         "Sucesso",
                                         JOptionPane.INFORMATION_MESSAGE);
            
            Login telaLogin = new Login();
            telaLogin.setVisible(true);
            this.telaCadastrar.dispose();
        } catch(SQLException e){
            JOptionPane.showMessageDialog(telaCadastrar, 
                                             "Erro ao cadastrar no banco de dados: \n" + e.getMessage(),
                                             "Erro",
                                              JOptionPane.ERROR_MESSAGE);
        }
        
    }
}
