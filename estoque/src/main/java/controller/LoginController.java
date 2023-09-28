/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import forms.Login;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Arthur
 */
public class LoginController implements ActionListener {
    
    private final Login login;
    
    public LoginController(forms.Login login) {
        this.login = login;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String acao = e.getActionCommand().toLowerCase();
        
        switch (acao) {
            case "login": login(); break;
            case "sair": sair(); break;
        }
    }

    private void login() {
        String usuario = login.getUserLabel().getText();
        String senha = login.getPassLabel().getText();
        
        if (usuario.equals("") || senha.equals("")) {
            //JOptionPane.showMessageDialog(null, "Teste");
            System.out.println("Teste");
            return;
        }
    }

    private void sair() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
