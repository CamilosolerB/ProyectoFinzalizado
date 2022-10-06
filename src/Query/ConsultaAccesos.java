/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Query;

import Conexion.Conexion;
import Constructors.Accesoconst;
import Constructors.LoginConst;
import Controller.Login;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author camilo.soler
 */
public class ConsultaAccesos {
    PreparedStatement ps;
    ResultSet rs;
    Conexion con = new Conexion();
    Connection mysql = con.connecdb();
    ConsultaApp app = new ConsultaApp();
    ConsultasPerfiles perfil = new ConsultasPerfiles();
    Init init = new Init();
    Login log;
    
    //elimina los accesos que se tenga a una aplicacion
    public void ElminarAcceso(String Nombre_app, String Nombre_profile){
        String user = JOptionPane.showInputDialog("Por favor indique su usuario");
        int idapp = app.GetIdApp(Nombre_app);
        if(validarpermiso(idapp, user)){
            int[] valores = new int[2];
            Accesoconst accs = new Accesoconst(Nombre_app, Nombre_profile);
            valores = GetIdprograms(accs);
            try {
                ps = mysql.prepareStatement("Delete from acceso Where acc_id_per=? AND acc_id_pro=?");
                ps.setInt(1, valores[0]);
                ps.setInt(2, valores[1]);
                if(ps.executeUpdate() > 0){
                    JOptionPane.showMessageDialog(null, "Acceso deshabilitado");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Este usuario no tiene acceso para modificacion de esta aplicacion, verifique por favor");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }    
        }
        else{
            JOptionPane.showMessageDialog(null, "No posees permisos sobre esta aplicacion para modificarla");
        }
        
    }
    //habilitar acceso a las aplicaciones
    public void PermitirAcceso(String Nombre_app, String Nombre_profile){
        String user = JOptionPane.showInputDialog("Por favor indique su usuario");
        int idapp = app.GetIdApp(Nombre_app);
        if(validarpermiso(idapp, user)){
            int[] valores = new int[2];
            Accesoconst accs = new Accesoconst(Nombre_app, Nombre_profile);
            valores = GetIdprograms(accs);
            if(valores[0] == 0){
                valores[0] = perfil.GetIdPerfil(Nombre_profile);
                valores[1] = app.GetIdApp(Nombre_app);
            }
            else{
                valores[0] = 0;
                valores[1] = 0;
            }
            try {
                ps = mysql.prepareStatement("Insert into acceso (acc_id_per,acc_id_pro) Values (?,?)");
                ps.setInt(1, valores[0]);
                ps.setInt(2, valores[1]);
                if(ps.executeUpdate() > 0){
                    JOptionPane.showMessageDialog(null, "Acceso habilitado");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Este perfil ya cuenta con acceso a la aplicacion");
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "No posees permisos sobre esta aplicacion para modificarla");
        }
    }
    //Validar si el usuario tiene acceso y traer sus ID para eliminar
    public int[] GetIdprograms(Accesoconst accs){
        int[] valores = new int[2];
        try {
            ps = mysql.prepareStatement("Select acc_id_per,acc_id_pro from acceso "
                    + "inner join perfiles on (acc_id_per=Per_id) "
                    + "inner join programas on (acc_id_pro=Pro_id) "
                    + "Where Pro_nombre_programa=? AND per_nombreperfil=? LIMIT 1");
            ps.setString(1, accs.getNombre_app());
            ps.setString(2, accs.getNombre_profile());
            rs = ps.executeQuery();
            if(rs.next()){
                valores[0] = rs.getInt(1);
                valores[1] = rs.getInt(2);
            }
            else{
                valores[0] = 0;
                valores[1] = 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e,"Error mysql",1);
        }
        return valores;
    }
    //validar acceso para modificacion
    public boolean validarpermiso(int app, String user){
        boolean check=false;
        try {
            ps = mysql.prepareStatement("Select * from acceso Where acc_id_per=(Select us_perfil from"
                    + " usuarios Where usu_usuario=?) AND acc_id_pro=?");
            ps.setString(1, user);
            ps.setInt(2, app);
            rs = ps.executeQuery();
            if(rs.next()){
                check = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaAccesos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }
}
