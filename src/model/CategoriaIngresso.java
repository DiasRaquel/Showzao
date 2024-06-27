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

    // Construtor vazio
    public CategoriaIngresso() {}

    // Construtor com todos os argumentos
    public CategoriaIngresso(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Método para cadastrar um local e retornar o ID gerado
    private static int cadastrar(String nome) {
        String sql = "INSERT INTO categoriaIngresso (categoria) VALUES (?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nome);
            ps.executeUpdate();

            // Obtém o ID gerado automaticamente
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Retorna o ID do local cadastrado
            }
            JOptionPane.showMessageDialog(null, "Categoria cadastrada com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar a categoria: " + e.getMessage());
        }
        return -1; // Retorna -1 se ocorrer algum erro
    }

    // Método para obter a lista de locais
    public static List<CategoriaIngresso> getCategorias() {
        List<CategoriaIngresso> lista = new ArrayList<>();
        String sql = "SELECT categoria FROM categoriaIngresso";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CategoriaIngresso categorias = new CategoriaIngresso();
                categorias.categoria = rs.getString("categoria");
                lista.add(categorias);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao obter categorias: " + e.getMessage());
        }
        return lista;
    }

    
    //Verifica se há locais cadastrados. Se não houver, oferece a opção de cadastrar um novo local.
    //Se já houver locais cadastrados, permite ao usuário escolher entre um local existente ou cadastrar um novo.
    //Retorna o ID do local escolhido ou cadastrado, ou null se a operação for cancelada.
    public static String verificarOuCadastrar() {
        List<CategoriaIngresso> categorias = getCategorias();
    
        if (categorias.isEmpty()) {
            int opcaoCadastro = JOptionPane.showConfirmDialog(null,
                    "Não há categorias cadastradas. Deseja cadastrar uma nova categoria?",
                    "Cadastro de Categoria", JOptionPane.YES_NO_OPTION);
    
            if (opcaoCadastro == JOptionPane.YES_OPTION) {
                String nomeCategoria = JOptionPane.showInputDialog("Digite o nome da nova categoria:");
                if (nomeCategoria != null && !nomeCategoria.isEmpty()) {
                    int idCategoria = cadastrar(nomeCategoria); // Cadastra o novo local e retorna o ID
                    return String.valueOf(idCategoria); // Retorna o ID do local cadastrado como String
                } else {
                    JOptionPane.showMessageDialog(null, "Nome da categoria não pode ser vazio.");
                    return null; // Retorna null se o nome do local for vazio
                }
            } else {
                JOptionPane.showMessageDialog(null, "Operação cancelada.");
                return null; // Retorna null se o usuário não deseja cadastrar um novo local
            }
        } else {
            // Oferece ao usuário a escolha entre cadastrar um novo local ou escolher um existente
            String[] opcoes = { "Escolher Categoria Existente", "Cadastrar Nova Categoria" };
            int escolha = JOptionPane.showOptionDialog(null,
                    "Escolha uma opção:",
                    "Opções de categorias",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcoes,
                    opcoes[0]);
    
            if (escolha == 0) { // Escolher Local Existente
                CategoriaIngresso escolhido = escolherCategoria(categorias);
                if (escolhido != null) {
                    return String.valueOf(escolhido.id); // Retorna o ID do local escolhido como String
                } else {
                    return null; // Retorna null se o usuário cancelar a escolha
                }
            } else if (escolha == 1) { // Cadastrar Novo Local
                String nomeCategoria = JOptionPane.showInputDialog("Digite o nome da nova categoria:");
                if (nomeCategoria != null && !nomeCategoria.isEmpty()) {
                    int idLocal = cadastrar(nomeCategoria); // Cadastra o novo local e retorna o ID
                    return String.valueOf(idLocal); // Retorna o ID do local cadastrado como String
                } else {
                    JOptionPane.showMessageDialog(null, "Nome do local não pode ser vazio.");
                    return null; // Retorna null se o nome do local for vazio
                }
            } else {
                JOptionPane.showMessageDialog(null, "Operação cancelada.");
                return null; // Retorna null se o usuário cancelar a operação
            }
        }
    }

    //Permite ao usuário escolher um local existente a partir da lista de locais cadastrados,
    //obtida através do método verificarOuCadastrar.
    private static CategoriaIngresso escolherCategoria(List<CategoriaIngresso> categorias) {
        //Verifica se há locais cadastrados
        if (categorias.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há categorias cadastrados.");
            return null; // Retorna null se não houver locais cadastrados
        }
        //Converte a lista Locais em um array com os nomes dos locais
        String[] opcoes = categorias.stream()//Cria uma stream a partir da lista Local
                .map(l -> l.nome)//Usa map para transformar cada objeto em seu nome
                .toArray(String[]::new);//Converte a stream em um array de Strings

        //Mostra as opçoes de escolha de local
        String escolha = (String) JOptionPane.showInputDialog(null,
                "Escolha um local:",//Mensagem exibida no dialogo
                "Escolha de Local",//Titulo da janela de dialogo
                JOptionPane.QUESTION_MESSAGE,//Icone de interrogação
                null,
                opcoes,//Opções obtidas como array de nomes dos locais
                opcoes[0]);//Valor padrão, primeiro elemento do array

        //Percorre a lista de locais para achar a escolha do usuario
        for (CategoriaIngresso categoriaIngresso : categorias) {
            if (categoriaIngresso.nome.equalsIgnoreCase(escolha)) { //Esse metodo equalsIgnoreCase permite ignorar Maiuscula/minuscula
                return categoriaIngresso; // Retorna o objeto Local escolhido
            }
        }

        return null; // Retorna null se o usuário cancelar a escolha
    }

}
