/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author mathe
 */
public class Bebida extends Alimento implements ImpostoAlcool{
    private boolean alcoolica;
    private int idAlimento;

    public boolean getAlcoolica() {
        return alcoolica;
    }

    public void setAlcoolica(boolean alcoolica) {
        this.alcoolica = alcoolica;
    }

    public int getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(int idAlimento) {
        this.idAlimento = idAlimento;
    }

    @Override
    public double calcularImposto() {
        if (alcoolica){
            return this.valor * 0.2;
        } 
        return 0;
    }
    
}
