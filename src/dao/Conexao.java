package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Conexao {
    private static final String url = "jdbc:mysql://localhost:3306/loja";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection conn;

    public static Connection getConexao(){
        try {
            if( conn == null){
          //      Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(url, USER, PASSWORD);
                if(conn != null){
                    return conn;
                }else{
                    return null;
                }
            }else{
                return conn;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return null;
        }
    }

    public static void fecharConn( Connection conn){
        if( conn != null){
            try {
                conn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString());
            }
        }
    }

}
