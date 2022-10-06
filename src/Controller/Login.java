/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import Constructors.LoginConst;
import Query.Init;
import Views.Dashboard;
import Views.Home;
/**
 *
 * @author camilo.soler
 */
public class Login {
    
    Home log;
    
    public static String user;
    
    public Login(Home log){
        this.log = log;
    }
    
    public boolean iniciodesesion(){
        boolean check = false;
        user = log.getUser().getText();
        String password = log.getPassword().getText();
        LoginConst data = new LoginConst(user, password);
        Init in = new Init();
        check = in.iniciarsesion(data);
        return check;
    }
    

}

