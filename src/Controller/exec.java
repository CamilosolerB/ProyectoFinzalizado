/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Query.ConsultaApp;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author camilo.soler
 */
public class exec {
   
    public void abrirapps(String app){
        ConsultaApp application = new ConsultaApp();
        int value = application.TipoDeApp(app);
        switch(value){
            case 1:
             try {
                Desktop.getDesktop().browse(new URI(ConsultaApp.link));
            } catch (URISyntaxException ex) {
                Logger.getLogger(exec.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(exec.class.getName()).log(Level.SEVERE, null, ex);
            }   
            break;
            case 2:
                ejecutarprograma();
            break;
        }
    }
    
    public void ejecutarprograma(){
        try {
            Process process = Runtime.getRuntime().exec(ConsultaApp.link);
            process.waitFor();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "El archivo no se encuentra por favor notifique al administrador");
        } catch (InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "El archivo no se encuentra por favor notifique al administrador");
        }
    }
}
