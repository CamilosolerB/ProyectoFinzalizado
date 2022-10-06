/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Constructors;

/**
 *
 * @author camilo.soler
 */
public class Accesoconst {
    private int Id_app;
    private int Id_profile;
    private String Nombre_app;
    private String Nombre_profile;

    public Accesoconst(int Id_app, int Id_profile) {
        this.Id_app = Id_app;
        this.Id_profile = Id_profile;
    }

    public Accesoconst(String Nombre_app, String Nombre_profile) {
        this.Nombre_app = Nombre_app;
        this.Nombre_profile = Nombre_profile;
    }

    public int getId_app() {
        return Id_app;
    }

    public void setId_app(int Id_app) {
        this.Id_app = Id_app;
    }

    public int getId_profile() {
        return Id_profile;
    }

    public void setId_profile(int Id_profile) {
        this.Id_profile = Id_profile;
    }

    public String getNombre_app() {
        return Nombre_app;
    }

    public void setNombre_app(String Nombre_app) {
        this.Nombre_app = Nombre_app;
    }

    public String getNombre_profile() {
        return Nombre_profile;
    }

    public void setNombre_profile(String Nombre_profile) {
        this.Nombre_profile = Nombre_profile;
    }
    
}
