/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Constructors.AppConst;
import Query.ConsultaApp;
import Views.InsertApp;
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
        AppConst appconst = new AppConst(nombre);
        ConsultaApp consultaapp = new ConsultaApp();
        if(consultaapp.InsertarAplicacion(appconst)){
            JOptionPane.showMessageDialog(null, "Aplicacion agragada con exito");
        }
    }
}
