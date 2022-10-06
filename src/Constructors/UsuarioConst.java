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
public class UsuarioConst {
    private int id;
    private String nombre;
    private String usuario;
    private String clave;
    private String perfil;

    public UsuarioConst(int id, String nombre, String usuario, String clave, String perfil) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.clave = clave;
        this.perfil = perfil;
    }

    public UsuarioConst(String nombre, String usuario, String clave, String perfil) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.clave = clave;
        this.perfil = perfil;
    }

    public UsuarioConst(String nombre, String usuario, String clave) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.clave = clave;
    }

    public UsuarioConst(int id) {
        this.id = id;
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
    
    
}
