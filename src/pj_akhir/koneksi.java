/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pj_akhir;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class koneksi {
    
    Connection koneksi = null;
    public static Connection koneksiDb(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection koneksi = DriverManager.getConnection("jdbc:mysql://localhost/db_perpus", "root", "");
            return koneksi;
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "koneksi gagal "+e.getMessage());
            return null;
        }
    }
}
