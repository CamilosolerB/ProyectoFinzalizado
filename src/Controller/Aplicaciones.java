/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Constructors.AppConst;
import Query.ConsultaApp;
import Views.InsertApp;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author camilo.soler
 */
public class Aplicaciones {
    InsertApp insertapp;

    public Aplicaciones(InsertApp insertapp) {
        this.insertapp = insertapp;
    }
    
    public void agregaraplicacion(){
        String nombre = insertapp.getAplicacion().getText();
        String url = insertapp.getUrl().getText();
        String type = insertapp.getTypo().getSelectedItem().toString();
        int vtype = (type.equals("Web")) ? 1 : 2;
        if(url.equals("")){
            JFileChooser file = insertapp.getSetup();
            url = file.getSelectedFile().getPath();
        }
        AppConst appconst = new AppConst(nombre, vtype, url);
        ConsultaApp consultaapp = new ConsultaApp();
        if(consultaapp.InsertarAplicacion(appconst)){
            JOptionPane.showMessageDialog(null, "Aplicacion agragada con exito");
        }
    }
}
