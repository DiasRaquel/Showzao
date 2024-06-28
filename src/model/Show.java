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
    public String nomeArtista;
    public String classificacaoIdade;
    public String categoriaIngresso;

    public Show(
        int id,
        String nome,
        String data, 
        int codGenero,
        int codLocal,
        String link,
        String nomeArtista,
        String classificacaoIdade,
        String categoriaIngresso
    ) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.codGenero = codGenero;
        this.codLocal = codLocal;
        this.link = link;
        this.nomeArtista = nomeArtista;
        this.classificacaoIdade = classificacaoIdade;
        this.categoriaIngresso = categoriaIngresso;
    }

    public static List<Show> getShows() {
        List<Show> lista = new ArrayList<>();
        String sql = "SELECT id, nome, data, codGenero, codLocal, link, nomeArtista, classificacaoIdade, categoriaIngresso FROM shows ORDER BY nome";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String data = rs.getString("data");
                String link = rs.getString("link");
                int codLocal = rs.getInt("codLocal");
                int codGenero = rs.getInt("codGenero");
                String nomeArtista = rs.getString("nomeArtista");
                String classificacaoIdade = rs.getString("classificacaoIdade");
                String categoriaIngresso = rs.getString("categoriaIngresso");
                
                Show show = new Show(
                    id,
                    nome,
                    data,
                    codGenero,
                    codLocal,
                    link,
                    nomeArtista,
                    classificacaoIdade,
                    categoriaIngresso
                );

                lista.add(show);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao obter shows: " + e.getMessage());
        }
        return lista;
    }

    public static void cadastrarShow() {
        String nome = JOptionPane.showInputDialog("Digite o nome do show:");
        String data = JOptionPane.showInputDialog("Digite a data do show (DD/MM):");

        String codGeneroStr = Genero.verificarOuCadastrar();
        if (codGeneroStr == null) {
            JOptionPane.showMessageDialog(null, "Operação cancelada.");
            return; 
        }

        int codGenero = Integer.parseInt(codGeneroStr);


        String codLocalStr = Local.verificarOuCadastrar();
        if (codLocalStr == null) {
            JOptionPane.showMessageDialog(null, "Operação cancelada.");
            return;
        }

        int codLocal = Integer.parseInt(codLocalStr);

        String link = JOptionPane.showInputDialog("Digite o link do show:");

        String nomeArtista = JOptionPane.showInputDialog("Digite o nome do artista:");

        String classificacaoIdade = JOptionPane.showInputDialog("Digite a idade:");

        String categoriaIngresso = CategoriaIngresso.verificarOuCadastrar();
        if (categoriaIngresso == null) {
            JOptionPane.showMessageDialog(null, "Operação cancelada.");
            return;
        }

        Show show = new Show(
            0,
            nome,
            data,
            codGenero,
            codLocal,
            link,
            nomeArtista,
            classificacaoIdade,
            categoriaIngresso
        );

        cadastrar(show);
    }

    public static void cadastrar(Show show) {
        String sql = "INSERT INTO shows (nome, data, codGenero, codLocal, link, nomeArtista, classificacaoIdade, categoriaIngresso) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, show.nome);
            ps.setString(2, show.data);
            ps.setInt(3, show.codGenero);
            ps.setInt(4, show.codLocal);
            ps.setString(5, show.link);
            ps.setString(6, show.nomeArtista);
            ps.setString(7, show.classificacaoIdade);
            ps.setString(8, show.categoriaIngresso);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Show cadastrado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar show: " + e.getMessage());
        }
    }

    public static String montarStringShows() {
        List<Show> shows = getShows(); 
        List<Genero> generos = Genero.getGeneros();
        List<Local> locais = Local.getLocais();
    
        StringBuilder sb = new StringBuilder();
        for (Show show : shows) {
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
    
            sb.append(show.nome).append(" - ").append(nomeLocal).append("\n");
    
            sb.append(show.data).append(" - ").append(nomeGenero).append("\n");
    
            sb.append(show.link).append("\n");

            sb.append(show.nomeArtista).append("\n");

            sb.append(show.classificacaoIdade).append("\n");

            sb.append(show.categoriaIngresso).append("\n");
    
            sb.append("\n");
        }
        return sb.toString();
    }


    public static String montarStringShowsPorGenero(int idGenero) {
        List<Show> shows = getShows(); 
        List<Genero> generos = Genero.getGeneros();
        List<Local> locais = Local.getLocais();
    
        
        StringBuilder sb = new StringBuilder();
        for (Show show : shows) {
            if (show.codGenero == idGenero) { 
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
    

                sb.append(show.nome).append(" - ").append(nomeLocal).append("\n");
    
                sb.append(show.data).append(" - ").append(nomeGenero).append("\n");
    
                sb.append(show.link).append("\n");

                sb.append(show.nomeArtista).append("\n");

                sb.append(show.classificacaoIdade).append("\n");

                sb.append(show.categoriaIngresso).append("\n");

                sb.append("\n");
            }
        }
        return sb.toString();
    }
}

