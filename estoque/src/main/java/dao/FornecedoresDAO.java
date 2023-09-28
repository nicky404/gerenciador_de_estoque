/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conection.Conexao;
import conection.ConexaoMysql;
import forms.Estoque;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Arthur
 */
public class FornecedoresDAO {
    private Conexao conexao;
    private DefaultTableModel originalTableModel; // Adicione esta variável de classe
    Estoque visualizar = new Estoque();
    
    public FornecedoresDAO() {
        this.conexao = new ConexaoMysql();
    }
    
    public String atualizarTabelaFornecedores(DefaultTableModel model) {
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

            String sqlAtualizacao = "UPDATE fornecedores SET nome = ?, contato = ? WHERE id = ?";
            String sqlInsercao = "INSERT INTO fornecedores (nome, contato) VALUES (?, ?)";
            PreparedStatement preparedStatementAtualizacao = conexao.obterConexao().prepareStatement(sqlAtualizacao);
            PreparedStatement preparedStatementInsercao = conexao.obterConexao().prepareStatement(sqlInsercao);

            int rowCount = model.getRowCount();

            if (rowCount == 0) {
                visualizar.chamarPreencherTabela();
                return "Dados recarregados com sucesso!";
            }

            for (int i = 0; i < rowCount; i++) {
                String nome = (String) model.getValueAt(i, 1); // Coluna "nome"

                String contato = (String) model.getValueAt(i, 2); // Coluna "contato"

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
                    preparedStatementInsercao.setString(2, contato);

                    int result = preparedStatementInsercao.executeUpdate();

                    if (result != 1) {
                        JOptionPane.showMessageDialog(null, "Erro ao inserir novo registro.");
                        return "Erro ao inserir novo registro.";
                    }
                } else {
                    // Atualização
                    int idRegistro = Integer.parseInt(idRegistroString);

                    preparedStatementAtualizacao.setString(1, nome);
                    preparedStatementAtualizacao.setString(2, contato);
                    preparedStatementAtualizacao.setInt(0, idRegistro);

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
            String sql = "DELETE FROM fornecedores WHERE id = ?";
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