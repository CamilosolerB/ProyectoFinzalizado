/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Query;

import Conexion.Conexion;
import Constructors.LoginConst;
import Views.Dashboard;
import Views.Home;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author camilo.soler
 */
public class Init {
    Conexion con = new Conexion();
    Connection mysql = con.connecdb();
    ResultSet rs;
    Home home = new Home();
    
    public boolean iniciarsesion(LoginConst cons){
        boolean check = false;
            try {
                PreparedStatement ps = mysql.prepareStatement("Select * from usuarios "
                    + "Where usu_usuario = ? AND usu_password = ?");
                ps.setString(1, cons.getUser());
                ps.setString(2, cons.getPassword());
                rs = ps.executeQuery();
                if(rs.next()){
                    check = true;
                }
            } 
            catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        return check;
    }
}
