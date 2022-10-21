/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Query;

import Conexion.Conexion;
import Constructors.AppConst;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author camilo.soler
 */
public class ConsultaApp {
    Conexion con = new Conexion();
    Connection mysql = con.connecdb();
    PreparedStatement ps, ps2;
    ResultSet rs, rs2;
    ConsultasPerfiles consulta = new ConsultasPerfiles();
    FileInputStream file;
    public static String link;
    
    public boolean InsertarAplicacion(AppConst app){
        boolean check = false;
        try {
            if(!"".equals(app.getUrl())){
                ps = mysql.prepareStatement("Insert into programas (Pro_nombre_programa,Pro_tipo,Pro_link)"
                        + " VALUES (?,?,?)");
                ps.setString(1, app.getNombre());
                ps.setInt(2, app.getType());
                ps.setString(3, app.getUrl());
                if(ps.executeUpdate() > 0){
                    int id = last_id();
                    ps2 = mysql.prepareStatement("Insert into acceso (acc_id_per,acc_id_pro ) VALUES (2,?)");
                    ps2.setInt(1, id);
                    if(ps2.executeUpdate() > 0){
                        check = true;
                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "No se encuentra la ruta o el directorio seleccionado, por favor validar");
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
    public ArrayList<String> GetPersonalApps(){
        ArrayList<String> lista = new ArrayList<>();
        try {
            ps = mysql.prepareStatement("Select Pro_nombre_programa from programas "
                    + "inner join acceso on (Pro_id=acc_id_pro) Where acc_id_per=?");
            ps.setInt(1, Init.id_perfil);
            rs = ps.executeQuery();
            while(rs.next()){
                lista.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
    public Integer last_id(){
        int valor = 0;
        try {
            ps = mysql.prepareStatement("Select max(Pro_id) as id_p from programas");
            rs2 = ps.executeQuery();
            if(rs2.next()){
                valor = rs2.getInt("id_p");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    }
    
    public Integer TipoDeApp(String nombre){
        int valor = 0;
        try {
            ps = mysql.prepareStatement("Select Pro_tipo,Pro_link from programas Where Pro_nombre_programa=?");
            ps.setString(1, nombre);
            rs2 = ps.executeQuery();
            if(rs2.next()){
                valor = rs2.getInt("Pro_tipo");
                link = rs2.getString("Pro_link");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    }
}
