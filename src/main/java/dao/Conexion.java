/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author deivy
 */
public class Conexion {
    private Connection cn;
    
    public void conectar() throws SQLException, ClassNotFoundException{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SERVICIO","deivy", "deivy");
            System.out.println("Conectado");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: " + e);
        }
    }
    
    public void cerrar() throws SQLException{
        try {
            if (cn != null) {
                if (cn.isClosed() == false) {
                    cn.isClosed();
                }
            }
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException{
        try {
            Conexion conx = new Conexion();
        conx.conectar();
        } catch (ClassNotFoundException | SQLException e) {
            throw e;
        }
            }
}
