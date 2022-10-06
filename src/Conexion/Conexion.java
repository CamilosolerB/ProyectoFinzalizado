/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author camilo.soler
 */
public class Conexion {
    Connection mysql;
    
    public Connection connecdb(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try {
                mysql = DriverManager.getConnection("jdbc:mysql://127.0.0.1/control_aplicaciones","root","");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    return mysql;
    }
    
    public static void main(String[] args) {
        Conexion con = new Conexion();
        con.connecdb();
    }
}
