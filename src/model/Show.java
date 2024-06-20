package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import dao.Conexao;
import model.Genero;
import model.Local;


public class Show {

    public int id;
    public String nome,data, genero, local, link;

    //Metodo Construtor que nao precisa de argumentos
    public Show(){}
    
    //Metodo Construtor que precisa de todos os argumentos
    public Show(int id, String nome, String data, String genero, String local, String link){
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.genero = genero;
        this.local = local;
        this.link = link;
    }

    public static List<Show> getShows() {
    List<Show> lista = new ArrayList<>();
    String sql = "SELECT id, nome, data, genero, local, link FROM show ORDER BY nome";
    try (Connection conn = Conexao.getConexao();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            String data = rs.getString("data");
            String genero = rs.getString("genero");
            String local = rs.getString("local");
            String link = rs.getString("link");

            Show show = new Show(id, nome, data, genero, local, link);
            lista.add(show);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao obter shows: " + e.getMessage());
    }
    return lista;
}

    
    //Esse metodo adquire as informaçoes para montar o show
    public static void cadastrarShow() {
        String nome = JOptionPane.showInputDialog("Digite o nome do show:");
        String data = JOptionPane.showInputDialog("Digite a data do show (DD/MM):");
        String genero = JOptionPane.showInputDialog("Digite o gênero do show:");
        
        String nomeLocal = Local.verificarOuCadastrar();
    
    if (nomeLocal == null) {
        JOptionPane.showMessageDialog(null, "Operação cancelada.");
        return; // Encerra o método se o usuário cancelar a escolha ou não cadastrar um novo local
    }
        
        String link = JOptionPane.showInputDialog("Digite o link do show:");

        // Cria um novo objeto Show com os dados fornecidos
        Show show = new Show(0, nome, data, genero, nomeLocal, link);

        // Chama o método estático para cadastrar o show
        cadastrar(show);
    }

    public static void cadastrar(Show show){

        String sql = "INSERT INTO show (nome,data,genero, local, link) VALUES ( ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;

        try {
            Connection conn = Conexao.getConexao();
            ps = conn.prepareStatement(sql);
            ps.setString(1, show.nome);
            ps.setString(2, show.data);
            ps.setString(3, show.genero);
            ps.setString(4, show.local);
            ps.setString(5, show.link);
            ps.executeUpdate();

        JOptionPane.showMessageDialog(null, "Show cadastrado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar show: " + e.getMessage());
        } 
    }


    
}
