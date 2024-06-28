package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import dao.Conexao;

public class CategoriaIngresso {

    public int id;
    public String nome;

    public CategoriaIngresso() {}

    public CategoriaIngresso(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    private static int cadastrar(String nome) {
        String sql = "INSERT INTO categoriaIngresso (categoria) VALUES (?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nome);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); 
            }
            JOptionPane.showMessageDialog(null, "Categoria cadastrada com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar a categoria: " + e.getMessage());
        }
        return -1; 
    }

    public static List<CategoriaIngresso> getCategorias() {
        List<CategoriaIngresso> lista = new ArrayList<>();
        String sql = "SELECT categoria FROM categoriaIngresso";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CategoriaIngresso categorias = new CategoriaIngresso();
                categorias.nome = rs.getString("categoria");
                lista.add(categorias);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao obter categorias: " + e.getMessage());
        }
        return lista;
    }

    
    public static String verificarOuCadastrar() {
        List<CategoriaIngresso> categorias = getCategorias();
    
        if (categorias.isEmpty()) {
            int opcaoCadastro = JOptionPane.showConfirmDialog(null,
                    "Não há categorias cadastradas. Deseja cadastrar uma nova categoria?",
                    "Cadastro de Categoria", JOptionPane.YES_NO_OPTION);
    
            if (opcaoCadastro == JOptionPane.YES_OPTION) {
                String nomeCategoria = JOptionPane.showInputDialog("Digite o nome da nova categoria:");
                if (nomeCategoria != null && !nomeCategoria.isEmpty()) {
                    int idCategoria = cadastrar(nomeCategoria); 
                    return String.valueOf(idCategoria); 
                } else {
                    JOptionPane.showMessageDialog(null, "Nome da categoria não pode ser vazio.");
                    return null; 
                }
            } else {
                JOptionPane.showMessageDialog(null, "Operação cancelada.");
                return null; 
            }
        } else {
            String[] opcoes = { "Escolher Categoria Existente", "Cadastrar Nova Categoria" };
            int escolha = JOptionPane.showOptionDialog(null,
                    "Escolha uma opção:",
                    "Opções de categorias",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcoes,
                    opcoes[0]);
    
            if (escolha == 0) { 
                CategoriaIngresso escolhido = escolherCategoria(categorias);
                if (escolhido != null) {
                    return String.valueOf(escolhido.id); 
                } else {
                    return null; 
                }
            } else if (escolha == 1) { 
                String nomeCategoria = JOptionPane.showInputDialog("Digite o nome da nova categoria:");
                if (nomeCategoria != null && !nomeCategoria.isEmpty()) {
                    int idLocal = cadastrar(nomeCategoria); 
                    return String.valueOf(idLocal); 
                } else {
                    JOptionPane.showMessageDialog(null, "Nome do local não pode ser vazio.");
                    return null; 
                }
            } else {
                JOptionPane.showMessageDialog(null, "Operação cancelada.");
                return null; 
            }
        }
    }

    private static CategoriaIngresso escolherCategoria(List<CategoriaIngresso> categorias) {
        if (categorias.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há categorias cadastrados.");
            return null; 
        }
        String[] opcoes = categorias.stream()
                .map(l -> l.nome)
                .toArray(String[]::new);

        String escolha = (String) JOptionPane.showInputDialog(null,
                "Escolha um local:",
                "Escolha de Local",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]);

        for (CategoriaIngresso categoriaIngresso : categorias) {
            if (categoriaIngresso.nome.equalsIgnoreCase(escolha)) {
                return categoriaIngresso; 
            }
        }

        return null; 
    }

}
