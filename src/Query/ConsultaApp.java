/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Query;

import Conexion.Conexion;
import Constructors.AppConst;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author camilo.soler
 */
public class ConsultaApp {
    Conexion con = new Conexion();
    Connection mysql = con.connecdb();
    PreparedStatement ps;
    ResultSet rs, rs2;
    ConsultasPerfiles consulta = new ConsultasPerfiles();
    
    public boolean InsertarAplicacion(AppConst app){
        boolean check = false;
        try {
            ps = mysql.prepareStatement("Insert into programas (Pro_nombre_programa)"
                    + " VALUES (?)");
            ps.setString(1, app.getNombre());
            if(ps.executeUpdate() > 0){
                check = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return check;
    }
    public DefaultTableModel verapps(){
        ArrayList<String> data = new ArrayList<>();
        DefaultTableModel tabla = new DefaultTableModel();
        tabla.addColumn("Identificador");
        tabla.addColumn("Nombre aplicacion");
        data = consulta.Verperfiles();
        for(int i = 0; i < data.size(); i++){
            tabla.addColumn(data.get(i));
        }
        String []valor = new String[2 + data.size()];
            try {
            ps = mysql.prepareStatement("Select * from programas");
            rs = ps.executeQuery();
            while(rs.next()){
                valor[0] = rs.getInt(1)+"";
                valor[1] = rs.getString(2);
                for(int i = 0; i < data.size(); i++){
                    if(consultaacceso(rs.getInt(1), data.get(i))){
                        valor[i + 2] = "SI";
                    }
                    else{
                        valor[i + 2] = "NO";
                    }
                }
                tabla.addRow(valor);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return tabla;
    }
    
    public boolean consultaacceso(int id, String nombre){
        boolean check = false;
        try {
            ps = mysql.prepareStatement("SELECT Pro_nombre_programa,Per_id FROM acceso "
                    + "INNER JOIN programas on (acc_id_pro=Pro_id) "
                    + " INNER JOIN perfiles on (acc_id_per=Per_id) "
                    + "Where Per_nombreperfil =? AND Pro_id=?");
            ps.setString(1, nombre);
            ps.setInt(2, id);
            rs2 = ps.executeQuery();
            if(rs2.next()){
                check = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return check;
    }
    public ArrayList<String> VerAplicaciones(){
        ArrayList<String> lista = new ArrayList<>();
        try {
            ps = mysql.prepareStatement("Select Pro_nombre_programa from programas");
            rs = ps.executeQuery();
            while (rs.next()) {                
                lista.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return lista;
    }
    public Integer GetIdApp(String App){
        int id=0;
        try {
            ps = mysql.prepareStatement("Select Pro_id from programas Where Pro_nombre_programa=?");
            ps.setString(1, App);
            rs = ps.executeQuery();
            if(rs.next()){
                id= rs.getInt(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return id;
    }
}
