/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Query;

import Conexion.Conexion;
import Constructors.PerfilConst;
import java.util.ArrayList;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author camilo.soler
 */
public class ConsultasPerfiles {
    Conexion con = new Conexion();
    Connection mysql = con.connecdb();
    ResultSet rs;
    PreparedStatement ps;
    
    public ArrayList<String> Verperfiles(){
        ArrayList<String> lista = new ArrayList<>();
        try {
            ps = mysql.prepareStatement("Select Per_nombreperfil from perfiles");
            rs = ps.executeQuery();
            while(rs.next()){
                lista.add(rs.getString("Per_nombreperfil"));
         }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return lista;
    }
    public Integer GetIdPerfil(String perfil){
        int id=0;
        try {
            ps = mysql.prepareStatement("Select Per_id from perfiles Where Per_nombreperfil=?");
            ps.setString(1, (perfil));
            rs = ps.executeQuery();
            if(rs.next()){
                id= rs.getInt(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return id;
    }
    public boolean InsertarPerfil(PerfilConst perfil){
        boolean check = false;
            try {
            String nombre = perfil.getNombre_perfil();
            String name = nombre.toUpperCase().charAt(0) + nombre.substring(1, nombre.length()).toLowerCase();
            ps = mysql.prepareStatement("Insert into perfiles (Per_nombreperfil) VALUES (?)");
            ps.setString(1, name);
            if(ps.executeUpdate() > 0){
                check = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return check;
    }
    public DefaultTableModel VerPerfiles(){
        DefaultTableModel table = new DefaultTableModel();
        table.addColumn("Id");
        table.addColumn("Nombre");
        String []data = new String[2];
        try {
            ps = mysql.prepareStatement("Select * from perfiles");
            rs = ps.executeQuery();
            while(rs.next()){
                data[0] = rs.getInt(1) + "";
                data[1] = rs.getString(2);
                table.addRow(data);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return table;
    }
}
