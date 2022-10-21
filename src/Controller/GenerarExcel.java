/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Conexion.Conexion;
import Constructors.UsuarioConst;
import java.sql.*;
import Query.ConsultaApp;
import Query.ConsultaUsuario;
import Views.Dashboard;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author camilo.soler
 */
public class GenerarExcel {
    
    ConsultaApp app = new ConsultaApp();
    ArrayList<String> listaapp = new ArrayList<>();
    Conexion con = new Conexion();
    Connection mysql = con.connecdb();
    ConsultaApp appCons = new ConsultaApp();
    DataFormatter formatter = new DataFormatter();
    PreparedStatement ps;
    ResultSet rs;
    
    public void CreateExcel(){
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("Reporte");
        sheet.setColumnWidth(1, 25 * 150);
        sheet.setColumnWidth(2, 25 * 256);
        sheet.setColumnWidth(3, 25 * 256);
        sheet.setColumnWidth(4, 25 * 256);
        sheet.setColumnWidth(5, 25 * 256);
        CellStyle style = book.createCellStyle();
        Row row = sheet.createRow(1);
        String titles[] = {"ID","Nombre","Usuario","Contraseña","Perfil"};
        row.setHeight((short)400);
        int iterador = 2;
        for(int i = 1; i <= titles.length; i++){
            HSSFCell cell = (HSSFCell) row.createCell((short)i);
            cell.setCellValue(titles[i-1]);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setBorderBottom(BorderStyle.MEDIUM);
            style.setBorderLeft(BorderStyle.MEDIUM);
            style.setBorderTop(BorderStyle.MEDIUM);
            style.setBorderRight(BorderStyle.MEDIUM);
            style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
        }
        listaapp = app.VerAplicaciones();
        for(int i = 6, j = 0 ;i < (listaapp.size() + 6); i++, j++){
            sheet.setColumnWidth(i, 20 * 256);
            HSSFCell cell = (HSSFCell) row.createCell((short)i);
            cell.setCellValue(listaapp.get(j));
            cell.setCellStyle(style);
        }
        CellStyle datosEstilo = book.createCellStyle();
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        datosEstilo.setBorderLeft(BorderStyle.THIN);
        datosEstilo.setBorderTop(BorderStyle.THIN);
        datosEstilo.setBorderRight(BorderStyle.THIN);
        try {
            ps = mysql.prepareStatement("Select usu_id,usu_nombre,usu_usuario,usu_password,"
                    + "Per_nombreperfil from usuarios inner join perfiles on (us_perfil=Per_id)");
            rs = ps.executeQuery();
            int numCol = rs.getMetaData().getColumnCount();
            while (rs.next()) {                
                Row data = sheet.createRow(iterador);
                for(int i=1 ; i <= numCol; i++ ){
                    HSSFCell CeldaDatos = (HSSFCell) data.createCell(i);
                    CeldaDatos.setCellStyle(datosEstilo);
                    if(i==1){
                        CeldaDatos.setCellValue(rs.getDouble(i));
                    }
                    else{
                        CeldaDatos.setCellValue(rs.getString(i));
                    }
                    if(i==5){
                        for(int j=1; j <= listaapp.size(); j++){
                            CeldaDatos = (HSSFCell) data.createCell(j + i);
                            CeldaDatos.setCellStyle(datosEstilo);
                            int IdApp = appCons.GetIdApp(listaapp.get(j -1));
                            if(appCons.consultaacceso(IdApp, rs.getString(5))){
                                CeldaDatos.setCellValue("SI");
                            }
                            else{
                                CeldaDatos.setCellValue("NO");
                            }
                        }
                    }
                }
                iterador++;
            }
        } catch (Exception e) {
        }
        try {
            FileOutputStream fileout = new FileOutputStream("C:\\Users\\"+System.getProperty("user.name")+"\\Downloads\\listado.xls");
            book.write(fileout);
            fileout.close();
            JOptionPane.showMessageDialog(null, "Excel generado con exito en la carpeta descargas");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GenerarExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GenerarExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public DefaultTableModel previous(){
        FileInputStream file;
        DefaultTableModel tabla = new DefaultTableModel();
        String data[] = new String[5];
        tabla.addColumn("ID");
        tabla.addColumn("Nombre");
        tabla.addColumn("Usuario");
        tabla.addColumn("Contraseña");
        tabla.addColumn("Perfil");
        try {
            file = new FileInputStream(new File(Dashboard.path));
            HSSFWorkbook book = new HSSFWorkbook(file);
            HSSFSheet sheet = book.getSheetAt(0);
            for(int i = 2; i <= sheet.getLastRowNum(); i++){
                HSSFRow fila = sheet.getRow(i);
                int id = (int) fila.getCell(1).getNumericCellValue();
                data[0] = id + "";
                data[1] = fila.getCell(2).getStringCellValue();
                data[2] = fila.getCell(3).getStringCellValue();
                data[3] = formatter.formatCellValue(fila.getCell(4));
                data[4] = fila.getCell(5).getStringCellValue();
                tabla.addRow(data);
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return tabla;
    }
    public void ReadExcel (String ruta){
        try {
            FileInputStream file = new FileInputStream(new File(ruta));
            HSSFWorkbook book = new HSSFWorkbook(file);
            HSSFSheet sheet = book.getSheetAt(0);
            for(int i = 2; i <= sheet.getLastRowNum(); i++){
                HSSFRow fila = sheet.getRow(i);
                int id = (int) fila.getCell(1).getNumericCellValue();
                UsuarioConst user = new UsuarioConst(id);
                ConsultaUsuario userConst = new ConsultaUsuario();
                if(userConst.validarexistencia(user)){
                    int id_usu = (int) fila.getCell(1).getNumericCellValue();
                    String nombre = fila.getCell(2).getStringCellValue();
                    String usuario = fila.getCell(3).getStringCellValue();
                    String clave = fila.getCell(4).getStringCellValue();
                    String perfil = fila.getCell(5).getStringCellValue();
                    UsuarioConst usuConst = new UsuarioConst(id_usu,nombre, usuario, clave, perfil);
                    ConsultaUsuario user2 = new ConsultaUsuario();
                    user2.actualizarusuario(usuConst);
                }
                else{
                    int id_usu = (int) fila.getCell(1).getNumericCellValue();
                    String nombre = fila.getCell(2).getStringCellValue();
                    String usuario = fila.getCell(3).getStringCellValue();
                    String clave = fila.getCell(4).getStringCellValue();
                    String perfil = fila.getCell(5).getStringCellValue();
                    UsuarioConst usuConst = new UsuarioConst(id_usu,nombre, usuario, clave, perfil);
                    ConsultaUsuario users = new ConsultaUsuario();
                    users.insertarusuario(usuConst);
                }
            }
            JOptionPane.showMessageDialog(null, "Cambios realizados con exito");
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(GenerarExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(GenerarExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
