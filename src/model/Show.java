package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import dao.Conexao;

public class Show {

    public int id;
    public int codGenero;
    public int codLocal;
    public String nome;
    public String data;
    public String link;

    // Construtor vazio
    public Show() {
    }

    // Construtor com todos os argumentos
    public Show(int id, String nome, String data, int codGenero, int codLocal, String link) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.codGenero = codGenero;
        this.codLocal = codLocal;
        this.link = link;
    }

    // Método estático para obter todos os shows do banco de dados
    public static List<Show> getShows() {
        List<Show> lista = new ArrayList<>();
        String sql = "SELECT id, nome, data, codGenero, codLocal, link FROM shows ORDER BY nome";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String data = rs.getString("data");
                int codGenero = rs.getInt("codGenero");
                int codLocal = rs.getInt("codLocal");
                String link = rs.getString("link");

                Show show = new Show(id, nome, data, codGenero, codLocal, link);
                lista.add(show);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao obter shows: " + e.getMessage());
        }
        return lista;
    }

    // Método estático para cadastrar um novo show
    public static void cadastrarShow() {
        String nome = JOptionPane.showInputDialog("Digite o nome do show:");
        String data = JOptionPane.showInputDialog("Digite a data do show (DD/MM):");

        // Obtem o código do gênero
        String codGeneroStr = Genero.verificarOuCadastrar();
        if (codGeneroStr == null) {
            JOptionPane.showMessageDialog(null, "Operação cancelada.");
            return; // Retorna se o usuário cancelar a operação
        }
        int codGenero = Integer.parseInt(codGeneroStr);

        // Obtem o código do local
        String codLocalStr = Local.verificarOuCadastrar();
        if (codLocalStr == null) {
            JOptionPane.showMessageDialog(null, "Operação cancelada.");
            return; // Retorna se o usuário cancelar a operação
        }
        int codLocal = Integer.parseInt(codLocalStr);

        String link = JOptionPane.showInputDialog("Digite o link do show:");

        // Cria um novo objeto Show com os dados fornecidos
        Show show = new Show(0, nome, data, codGenero, codLocal, link);

        // Chama o método estático para cadastrar o show
        cadastrar(show);
    }

    // Método estático para inserir um show no banco de dados
    public static void cadastrar(Show show) {
        String sql = "INSERT INTO shows (nome, data, codGenero, codLocal, link) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, show.nome);
            ps.setString(2, show.data);
            ps.setInt(3, show.codGenero);
            ps.setInt(4, show.codLocal);
            ps.setString(5, show.link);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Show cadastrado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar show: " + e.getMessage());
        }
    }

    public static String montarStringShows() {
        List<Show> shows = getShows(); // Obtemos a lista de shows diretamente
        List<Genero> generos = Genero.getGeneros();
        List<Local> locais = Local.getLocais();
    
        StringBuilder sb = new StringBuilder();
        for (Show show : shows) {
            // Obter o nome do gênero e local pelo ID
            String nomeGenero = generos.stream()
                    .filter(g -> g.id == show.codGenero)
                    .map(g -> g.nome)
                    .findFirst()
                    .orElse("Desconhecido");
    
            String nomeLocal = locais.stream()
                    .filter(l -> l.id == show.codLocal)
                    .map(l -> l.nome)
                    .findFirst()
                    .orElse("Desconhecido");
    
            // Linha 1: Nome do show e local
            sb.append(show.nome).append(" - ").append(nomeLocal).append("\n");
    
            // Linha 2: Data do show e gênero
            sb.append(show.data).append(" - ").append(nomeGenero).append("\n");
    
            // Linha 3: Link completo
            sb.append(show.link).append("\n");
    
            // Linha em branco para separar os shows
            sb.append("\n");
        }
        return sb.toString();
    }

    public static String montarStringShowsPorGenero(int idGenero) {
        List<Show> shows = getShows(); // Obtemos a lista de shows diretamente
        List<Genero> generos = Genero.getGeneros();
        List<Local> locais = Local.getLocais();
    
        StringBuilder sb = new StringBuilder();
        for (Show show : shows) {
            if (show.codGenero == idGenero) {
                // Obter o nome do gênero e local pelo ID
                String nomeGenero = generos.stream()
                        .filter(g -> g.id == show.codGenero)
                        .map(g -> g.nome)
                        .findFirst()
                        .orElse("Desconhecido");
    
                String nomeLocal = locais.stream()
                        .filter(l -> l.id == show.codLocal)
                        .map(l -> l.nome)
                        .findFirst()
                        .orElse("Desconhecido");
    
                // Linha 1: Nome do show e local
                sb.append(show.nome).append(" - ").append(nomeLocal).append("\n");
    
                // Linha 2: Data do show e gênero
                sb.append(show.data).append(" - ").append(nomeGenero).append("\n");
    
                // Linha 3: Link completo
                sb.append(show.link).append("\n");
    
                // Linha em branco para separar os shows
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
