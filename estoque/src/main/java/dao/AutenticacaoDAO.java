/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import classes.Usuario;
import model.LoginDTO;

/**
 *
 * @author Arthur
 */
public class AutenticacaoDAO {
    private final UsuarioDAO usuarioDao;

    public AutenticacaoDAO() {
        this.usuarioDao = new UsuarioDAO();
    }
        
    public Usuario login (LoginDTO login) {
        Usuario usuario = usuarioDao.buscarUsuarioPeloUsuario(login.getUsuario());
        if (usuario == null)
            return null;
        
        if (validarSenha(usuario.getSenha(), login.getSenha())) {
            return usuario;
        }
        return null;
    }

    private boolean validarSenha(String senhaUsuario, String senhaLogin) {
        return senhaUsuario.equals(senhaLogin);
    }   
}