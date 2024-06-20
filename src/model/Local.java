package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import dao.Conexao;

public class Local {

    public int id;
    public String nome;

    //Construtos que nao recebe nenhum parametro
    public Local(){}

    //Metodo Construtor que recebe dois parametros
    public Local(int id,String nome){
        this.id = id;
        this.nome = nome;
    }

    public static void cadastrar(String nome) {
        String sql = "INSERT INTO local (nome) VALUES (?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Local cadastrado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar local: " + e.getMessage());
        }
    }

    public static List<Local> getLocal(){
        List<Local> lista = new ArrayList<Local>();
        String sql = "SELECT id, nome FROM local ORDER BY nome";
        PreparedStatement ps = null;
        try {
            Connection conn = Conexao.getConexao();
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if( rs != null){
                while ( rs.next()) {
                    Local local = new Local();
                    local.id = rs.getInt(1);
                    local.nome = rs.getString(2);
                    lista.add(local);
                }
            }
            //Conexao.fecharConn( conn );
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return lista;
    }
    public static String verificarOuCadastrar() {
        List<String> locais = obterNomesLocais();
    
        if (locais.isEmpty()) {
            int opcaoCadastro = JOptionPane.showConfirmDialog(null,
                    "Não há locais cadastrados. Deseja cadastrar um novo local?",
                    "Cadastro de Local", JOptionPane.YES_NO_OPTION);
    
            if (opcaoCadastro == JOptionPane.YES_OPTION) {
                String nomeLocal = JOptionPane.showInputDialog("Digite o nome do novo local:");
                if (nomeLocal != null && !nomeLocal.isEmpty()) {
                    cadastrar(nomeLocal); // Cadastra o novo local
                    return nomeLocal; // Retorna o nome do local cadastrado
                } else {
                    JOptionPane.showMessageDialog(null, "Nome do local não pode ser vazio.");
                    return null; // Retorna null se o nome do local for vazio
                }
            } else {
                JOptionPane.showMessageDialog(null, "Operação cancelada.");
                return null; // Retorna null se o usuário não deseja cadastrar um novo local
            }
        } else {
            // Usuário escolhe o local existente
            return escolherLocal();
        }
    }

    public static List<String> obterNomesLocais() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT nome FROM local ORDER BY nome";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(rs.getString("nome"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao obter locais: " + e.getMessage());
        }
        return lista;
    }

    private static String escolherLocal() {
        List<String> locais = obterNomesLocais();
    
        if (locais.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há locais cadastrados.");
            return null; // Retorna null se não houver locais cadastrados
        }
    
        String[] opcoes = locais.toArray(new String[0]);
        
        String escolha = (String) JOptionPane.showInputDialog(null,
                "Escolha um local:",
                "Escolha de Local",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]);
    
        return escolha;
    }
}
