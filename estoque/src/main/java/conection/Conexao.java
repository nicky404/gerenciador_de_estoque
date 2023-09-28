/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package conection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Arthur
 */
public interface Conexao {
    public Connection obterConexao() throws SQLException;
}
