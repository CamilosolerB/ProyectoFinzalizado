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
public class PerfilConst {
    private int id;
    private String nombre_perfil;

    public PerfilConst(int id, String nombre_perfil) {
        this.id = id;
        this.nombre_perfil = nombre_perfil;
    }

    public PerfilConst(String nombre_perfil) {
        this.nombre_perfil = nombre_perfil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_perfil() {
        return nombre_perfil;
    }

    public void setNombre_perfil(String nombre_perfil) {
        this.nombre_perfil = nombre_perfil;
    }
    
}
