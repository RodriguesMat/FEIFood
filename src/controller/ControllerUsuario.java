/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author mathe
 */
public class ControllerUsuario {
    private static int idUsuarioLogado = -1;
    
    public static int getIdUsuarioLogado() {
        return idUsuarioLogado;
    }

    public static void setIdUsuarioLogado(int idUsuario) {
        ControllerUsuario.idUsuarioLogado = idUsuario;
    }
    
    public static boolean estaLogado() {
        return idUsuarioLogado != -1;
    }
}
