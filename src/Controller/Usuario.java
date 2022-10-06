/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Constructors.UsuarioConst;
import Query.ConsultaUsuario;
import Views.InsertUsers;
import javax.swing.JOptionPane;

/**
 *
 * @author camilo.soler
 */
public class Usuario {
    
    ConsultaUsuario consultausuario = new ConsultaUsuario();
    InsertUsers users;
    
    public Usuario(InsertUsers users){
        this.users = users;
    }
    
    public void crearusuario(){
        String nombre = users.getNombre().getText();
        String usuario = users.getUsuario().getText();
        String password = users.getPassword().getText();
        String perfil = users.getPerfil();
        if(isNumeric(password)){
            JOptionPane.showMessageDialog(null, "Contraseña no valida, debe tener una letra y/o simbolo");
        }
        else{
            UsuarioConst userconst = new UsuarioConst(nombre, usuario, password, perfil);
            if(consultausuario.insertarusuario(userconst)){
                JOptionPane.showMessageDialog(null, "Insertado correctamente");
            }
        }
    }
    
    public static boolean isNumeric(String s){
        if (s == null || s.equals("")) {
            return false;
        }
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

}
