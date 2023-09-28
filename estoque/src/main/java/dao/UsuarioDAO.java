/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import classes.Usuario;
import conection.Conexao;
import conection.ConexaoMysql;
import forms.Estoque;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Arthur
 */
public class UsuarioDAO {
    private final Conexao conexao;
    Estoque visualizar = new Estoque();
    private DefaultTableModel originalTableModel;

    public UsuarioDAO() {
        this.conexao = new ConexaoMysql();
        
    }
    
    public String salvar(Usuario usuario) {
        return usuario.getId() == 0 ? adicionar(usuario) : editar (usuario);
    }
    
    private String adicionar (Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, usuario, senha) values (?, ?, ?)";
        Usuario usuarioTempo = buscarUsuarioPeloUsuario (usuario.getUsuario());
        if (usuarioTempo != null) {
            return String.format ("Error: usuario %s ja existe no banco de dados.", usuario.getUsuario());
        }
        
        try {
            PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql);
            preencherValores (preparedStatement, usuario);
            int result = preparedStatement.executeUpdate();
            return result == 1 ? "Usuario adicionado com sucesso!" : "Não foi possivel adicionar.";
        } catch (SQLException e){
            return "Error" + e.getMessage();
        }
    }
    
    private String editar (Usuario usuario) {
        String sql = "UPDATE usuario SET nome = ?, usuario = ?, senha = ? WHERE id = ? ";
        try {
            PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql);
            preencherValores (preparedStatement, usuario);
            int result = preparedStatement.executeUpdate();
            return result == 1 ? "Usuario editado com sucesso!" : "Não foi possivel editar.";
        } catch (SQLException e){
            return String.format ("Error: %s", e.getMessage());
        }
    }

    private void preencherValores(PreparedStatement preparedStatement, Usuario usuario) throws SQLException {
       preparedStatement.setString(1, usuario.getNome());
       preparedStatement.setString(2, usuario.getUsuario());
       preparedStatement.setString(3, usuario.getSenha());
       
       if (usuario.getId() != 0) {
           preparedStatement.setInt(4, usuario.getId());
       }
    }
    
    public List <Usuario> buscarUsuarios () {
        String sql = "SELECT * FROM usuario";
        List <Usuario> usuarios = new ArrayList<>();
        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            while (result.next()) {
                usuarios.add((getUsuario(result)));
            }
        } catch (SQLException e){
            System.out.println(String.format("Error: ", e.getMessage()));
        }
      return usuarios;
    }
    
    private Usuario getUsuario (ResultSet result) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(result.getInt ("id"));
        usuario.setNome(result.getString("nome"));
        usuario.setUsuario (result.getString ("usuario"));
        usuario.setSenha(result.getString("senha"));
        return usuario;
    }

    public Usuario buscarUsuarioPeloId (int id) {
        String sql = String.format ("SELECT * FROM usuario WHERE id = %d", id);

        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            if (result.next()) {
                return getUsuario(result);
            }
        } catch (SQLException e){
            System.out.println(String.format("Error: ", e.getMessage()));
        }
      return null;
    }
    
    public Usuario buscarUsuarioPeloUsuario (String usuario) {
        String sql = String.format ("SELECT * FROM usuario WHERE usuario = '%s'", usuario);

        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            if (result.next()) {
                return getUsuario(result);
            }
        } catch (SQLException e){
            System.out.println(String.format("Error: ", e.getMessage()));
        }
      return null;
    }
    
    public String atualizarTabelaUsuario(DefaultTableModel model) {
        try {
            // Verifique se há alguma linha em branco no modelo
            for (int row = 0; row < model.getRowCount(); row++) {
                for (int col = 1; col < model.getColumnCount(); col++) {
                    Object cellValue = model.getValueAt(row, col);
                    if (cellValue == null || cellValue.toString().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Não é possível atualizar: há linhas em branco.");
                        return "Não é possível atualizar: há linhas em branco.";
                    }
                }
            }

            String sqlAtualizacao = "UPDATE usuario SET nome = ?, usuario = ?, senha = ? WHERE id = ?";
            String sqlInsercao = "INSERT INTO usuario (nome, usuario, senha) VALUES (?, ?, ?)";
            PreparedStatement preparedStatementAtualizacao = conexao.obterConexao().prepareStatement(sqlAtualizacao);
            PreparedStatement preparedStatementInsercao = conexao.obterConexao().prepareStatement(sqlInsercao);

            int rowCount = model.getRowCount();

            if (rowCount == 0) {
                visualizar.chamarPreencherTabela();
                return "Dados recarregados com sucesso!";
            }

            for (int i = 0; i < rowCount; i++) {
                String nome = (String) model.getValueAt(i, 1); // Coluna "nome"

                String usuario = (String) model.getValueAt(i, 2); // Coluna "usuario"

                String senha = (String) model.getValueAt(i, 3); // Coluna "senha"

                Object idRegistroObject = model.getValueAt(i, 0); // Coluna "id"
                String idRegistroString = "";

                if (idRegistroObject != null) {
                    if (idRegistroObject instanceof String) {
                        idRegistroString = (String) idRegistroObject;
                    } else if (idRegistroObject instanceof Integer) {
                        idRegistroString = String.valueOf(idRegistroObject);
                    }
                }

                // Verifique se o ID é uma nova inserção ou uma atualização existente
                if (idRegistroString == null || idRegistroString.isEmpty()) {
                    // Nova inserção
                    preparedStatementInsercao.setString(1, nome);
                    preparedStatementInsercao.setString(2, usuario);
                    preparedStatementInsercao.setString(3, senha);

                    int result = preparedStatementInsercao.executeUpdate();

                    if (result != 1) {
                        JOptionPane.showMessageDialog(null, "Erro ao inserir novo registro.");
                        return "Erro ao inserir novo registro.";
                    }
                } else {
                    // Atualização
                    int idRegistro = Integer.parseInt(idRegistroString);

                    preparedStatementAtualizacao.setString(1, nome);
                    preparedStatementAtualizacao.setString(2, usuario);
                    preparedStatementAtualizacao.setString(3, senha);
                    preparedStatementAtualizacao.setInt(4, idRegistro);

                    int result = preparedStatementAtualizacao.executeUpdate();

                    if (result != 1) {
                        JOptionPane.showMessageDialog(null, "Erro ao editar registro.");
                        return "Erro ao editar registro.";
                    }
                }
            }

            JOptionPane.showMessageDialog(null, "Todos os registros foram atualizados com sucesso!");
            return "Todos os registros foram atualizados com sucesso!";
        } catch (SQLException e) {
            return String.format("Erro: %s", e.getMessage());
        }
    }

// Adicione este método para copiar os dados originais da tabela
    public void copiarDadosOriginais(DefaultTableModel model) {
        DefaultTableModel originalTableModel = new DefaultTableModel();

        // Copie os dados do modelo atual para o modelo original
        for (int row = 0; row < model.getRowCount(); row++) {
            Vector<Object> rowData = new Vector<>();
            for (int col = 0; col < model.getColumnCount(); col++) {
                rowData.add(model.getValueAt(row, col));
            }
            originalTableModel.addRow(rowData);
        }
    }

// Adicione este método para restaurar os dados originais da tabela
    public void restaurarDadosOriginais(DefaultTableModel model) {
        model.setRowCount(0);
        for (int row = 0; row < originalTableModel.getRowCount(); row++) {
            Vector<Object> rowData = new Vector<>();
            for (int col = 0; col < originalTableModel.getColumnCount(); col++) {
                rowData.add(originalTableModel.getValueAt(row, col));
            }
            model.addRow(rowData);
        }
    }
    
    public boolean excluirRegistroNoBanco(int idRegistro) {
        try {
            String sql = "DELETE FROM usuario WHERE id = ?";
            PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql);
            preparedStatement.setInt(1, idRegistro);
            int result = preparedStatement.executeUpdate();

            return result == 1; // Retorna verdadeiro se uma linha foi excluída com sucesso
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retorna falso em caso de erro
        }
    }   
}