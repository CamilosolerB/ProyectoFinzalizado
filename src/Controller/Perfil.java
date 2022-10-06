/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Constructors.PerfilConst;
import Query.ConsultasPerfiles;
import Views.InsertProfiles;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author camilo.soler
 */
public class Perfil {
    
    InsertProfiles insertprofiles;
    public Perfil(InsertProfiles insertprofiles){
        this.insertprofiles = insertprofiles;
    }
    
    public void CrearPerfil(){
        String perfil = insertprofiles.getPerfil().getText();
        PerfilConst perfilconst = new PerfilConst(perfil);
        ConsultasPerfiles consultaperfiles = new ConsultasPerfiles();
        if(consultaperfiles.InsertarPerfil(perfilconst)){
            JOptionPane.showMessageDialog(null, "Perfil generado con exito");
        }
    }
}
