package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import dao.Conexao;

public class Genero {

    public int id;
    public String nome;

    //Contrutor que nao precisa de nenhum parametro
    public Genero(){}

    //Metodo Construtor que recebe dois parametros
    public Genero(int id,String nome){
        this.id = id;
        this.nome = nome;
    }

    public static void cadastrar(String nome){
        String sql = "INSERT INTO genero (nome) VALUES ( ? )";
        PreparedStatement ps = null;

        try {
            Connection conn = Conexao.getConexao();
            ps = conn.prepareStatement(sql);
            ps.setString(1, nome);
            ps.execute();
            //Conexao.fecharConn( conn );
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
                }
        }

    public static void editar(Genero genero){
        String sql = "UPDATE genero SET nome = ( ? ) WHERE id = ?";
        PreparedStatement ps = null;

        try {
            Connection conn = Conexao.getConexao();
            ps = conn.prepareStatement(sql);
            ps.setInt(2, genero.id);
            ps.execute();
            //Conexao.fecharConn( conn );
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    public static void excluir(int idGenero){
        String sql = "DELETE FROM genero WHERE id = ?";
        PreparedStatement ps = null;

        try {
            Connection conn = Conexao.getConexao();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idGenero);
            ps.execute();
            //Conexao.fecharConn( conn );
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    public static List<Genero> getGenero(){
        List<Genero> lista = new ArrayList<Genero>();
        String sql = "SELECT id, nome FROM genero ORDER BY nome";
        PreparedStatement ps = null;
        try {
            Connection conn = Conexao.getConexao();
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if( rs != null){
                while ( rs.next()) {
                    Genero gen = new Genero();
                    gen.id = rs.getInt(1);
                    gen.nome = rs.getString(2);
                    lista.add(gen);
                }
            }
            //Conexao.fecharConn( conn );
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return lista;
    }
    
}

