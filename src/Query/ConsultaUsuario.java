/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Query;

import Conexion.Conexion;
import Constructors.UsuarioConst;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author camilo.soler
 */
public class ConsultaUsuario {
    Conexion con = new Conexion();
    Connection mysql = con.connecdb();
    PreparedStatement ps;
    ResultSet rs;
    UsuarioConst usuarioconst;
    
    public boolean insertarusuario(UsuarioConst data){
        boolean result = false;
        try {
            ConsultasPerfiles consultaperfil = new ConsultasPerfiles();
            int resultado = consultaperfil.GetIdPerfil(data.getPerfil());
            if(data.getId() == 0){
                ps = mysql.prepareStatement("Insert into usuarios "
                + "(usu_nombre,usu_usuario,usu_password,us_perfil ) VALUES (?,?,?,?)");
                ps.setString(1, data.getNombre());
                ps.setString(2, data.getUsuario());
                ps.setString(3, data.getClave());
                ps.setInt(4, resultado);
            }
            else{
                ps = mysql.prepareStatement("Insert into usuarios "
                    + " VALUES (?,?,?,?,?)");
                ps.setInt(1, data.getId());
                ps.setString(2, data.getNombre());
                ps.setString(3, data.getUsuario());
                ps.setString(4, data.getClave());
                ps.setInt(5, resultado);
            }
            if(ps.executeUpdate()>0){
                result = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return result;
    }
    public boolean actualizarusuario(UsuarioConst data){
                boolean result = false;
        try {
            ConsultasPerfiles consultaperfil = new ConsultasPerfiles();
            int resultado = consultaperfil.GetIdPerfil(data.getPerfil());
            ps = mysql.prepareStatement("Update usuarios Set "
                    + "usu_usuario=?,usu_password=?,us_perfil=? Where usu_nombre=?");
            ps.setString(4, data.getNombre());
            ps.setString(2, data.getClave());
            ps.setInt(3, resultado);
            ps.setString(1, data.getUsuario());
            if(ps.executeUpdate()>0){
                result = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return result;
    }
    public boolean validarexistencia(UsuarioConst id){
        boolean check = false;
        try {
            ps = mysql.prepareStatement("Select * from usuarios Where usu_id=?");
            ps.setInt(1, id.getId());
            rs = ps.executeQuery();
            if(rs.next()){
                check = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }
}
