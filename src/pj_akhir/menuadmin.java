/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pj_akhir;

import com.mysql.cj.jdbc.Blob;
import java.awt.Color;
import java.awt.Image;
import java.sql.PreparedStatement;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


public class menuadmin extends javax.swing.JFrame {

    /**
     * Creates new form menuadmin
     */
    public menuadmin() {
        initComponents();
        tabdashboard.setVisible(true);
        dsh.setBackground(new Color(51,51,51));
        tabdatasiswa.setVisible(false);
        tambahdatasiswa.setVisible(false);
        editdatasiswa.setVisible(false);
        tabdatabuku.setVisible(false);
        tambahdatabuku.setVisible(false);
        editdatabuku.setVisible(false);
        laporanpeminjam.setVisible(false);
        laporanpengembalian.setVisible(false);
        dendapeminjam.setVisible(false);
        pembayaran.setVisible(false);
        autoidsiswa();
        loadsiswa();
        autoidbuku();
        autoiddenda();
        loadbuku();
        combojb();
        comborb();
        loadlprpj();
        loadlprkmb();
        tb();
        copg();
        loaddenda();
    }
    String imgpath = null;
    public String x = "";

    //id generator siswa--------------------------------------------------------
    private void autoidsiswa(){
        try{
            Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String sql_id = "SELECT * FROM tbl_datasiswa order by id_siswa desc";
            ResultSet rs = st.executeQuery(sql_id);
            if(rs.next()){
                String id_siswa = rs.getString("id_siswa").substring(1);
                String AN = ""+(Integer.parseInt(id_siswa)+1);
                String Nol = "";
                if (AN.length() == 1){ //klo A00001
                    Nol = "0000";
                } 
                else if (AN.length() == 2){ //klo A00010
                    Nol = "000";
                } 
                else if (AN.length() == 3){ //klo A00100
                    Nol = "00";
                } 
                idsiswa.setText("A" + Nol + AN);
            }
            else {
                idsiswa.setText("A00001");
            }
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //registrasi----------------------------------------------------------------
    private void registrasi(){
        Connection kon = koneksi.koneksiDb();
        try{
           Statement st = kon.createStatement();
           String pasw = pass.getText();
            String cpasw = konpass.getText();
             if(pasw.equals(cpasw)){
                 String sql = "INSERT INTO tbl_loginsiswa VALUES ('" + user.getText()+"','"+pass.getText()+"')";
                 st.execute(sql);
                    
                   
             }
             else{
                 JOptionPane.showMessageDialog(null, "Pasword Yang Anda Tulis Tidak Sama");
                  pass.setText("");
                  konpass.setText("");
             }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }    
    
    //munculin data siswa------------------------------------------------------- 
    private void loadsiswa(){
        Connection kon = koneksi.koneksiDb();
        Object header[] = {"ID ANGGOTA","NAMA","NIS","JK","TINGKAT","JURUSAN","KELAS","NO HP", "ALAMAT", "TTL"};
        DefaultTableModel data = new DefaultTableModel(null,header);
        tbldatasiswa.setModel(data);
        String sql_data = "SELECT id_siswa, nama_siswa, nis_siswa, jk_siswa, tingkat_siswa, jurusan_siswa, kelas_siswa, nope_siswa, alamat, TTL FROM tbl_datasiswa";
        try{
            Statement st = kon.createStatement();
            ResultSet rs = st.executeQuery(sql_data);
            while (rs.next()){
                String d1 = rs.getString(1);
                String d2 = rs.getString(2);
                String d3 = rs.getString(3);
                String d4 = rs.getString(4);
                String d5 = rs.getString(5);
                String d6 = rs.getString(6);
                String d7 = rs.getString(7);
                String d8 = rs.getString(8);
                String d9 = rs.getString(9);
                String d10 = rs.getString(10);
                
                String d[] ={d1,d2,d3,d4,d5,d6,d7,d8,d9,d10};
                data.addRow(d);
                
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //input data siswa----------------------------------------------------------
    private void inputsiswa(){
        try {
        Connection kon = koneksi.koneksiDb(); // pastikan method koneksiDb() mengembalikan objek Connection
        String sql = "INSERT INTO tbl_datasiswa "
                + "(id_siswa, nama_siswa, nis_siswa, jk_siswa, tingkat_siswa, jurusan_siswa, "
                + "kelas_siswa, nope_siswa, stok_siswa, alamat, TTL) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pst = kon.prepareStatement(sql);
        pst.setString(1, idsiswa.getText());
        pst.setString(2, namasiswa.getText());
        pst.setString(3, nissiswa.getText());

        // Jenis Kelamin
        String jk = jkl.isSelected() ? jkl.getText() : jkp.getText();
        pst.setString(4, jk);

        pst.setString(5, ting.getSelectedItem().toString());
        pst.setString(6, jur.getSelectedItem().toString());
        pst.setString(7, kel.getSelectedItem().toString());
        pst.setString(8, nope.getText());

        // Misalnya stok_siswa diambil dari combo box atau field text
        pst.setString(9, "2"); // pastikan ada field stok (misalnya JTextField stok;)

        pst.setString(10, alamat.getText());
        pst.setString(11, ttl.getText());

        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Data siswa berhasil disimpan.");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
    }
}
    
    //edit registrasi-----------------------------------------------------------
    private void editregistrasi(){
        try{
            Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String pasw = editpass.getText();
            String cpasw = editkonpass.getText();
            if(pasw.equals(cpasw)){
                 String sql = "UPDATE tbl_loginsiswa SET login_user='"+edituser.getText()
                    +"',password_user='"+editpass.getText()
                    +"' WHERE id_siswa='"+editidsiswa.getText()+"'";
                 st.execute(sql);
                    
                    
             }
             else{
                 JOptionPane.showMessageDialog(null, "Pasword Yang Anda Tulis Tidak Sama");
                  pass.setText("");
                  konpass.setText("");
             }
        }catch (Exception e){
            
        }
    }
    
    //edit data siswa-----------------------------------------------------------
    private void editsiswa(){
        try{
            Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String jk = "";
            if (editjkl.isSelected()){
                jk = editjkl.getText();
            }
            else {
                jk = editjkp.getText();
            }
            
            String sql_up = "UPDATE tbl_datasiswa SET nama_siswa='"+editnamasiswa.getText()
                    +"',nis_siswa='"+editnissiswa.getText()
                    +"',jk_siswa='"+jk
                    +"',tingkat_siswa='"+editting.getSelectedItem()
                    +"',jurusan_siswa='"+editjur.getSelectedItem()
                    +"',kelas_siswa='"+editkel.getSelectedItem()
                    +"',nope_siswa='"+editnope.getText()
                    +"',alamat='"+editalamat.getText()
                    +"',TTL='"+editttl.getText()
                    +"' WHERE id_siswa='"+editidsiswa.getText()+"'";
            st.execute(sql_up);
            JOptionPane.showMessageDialog(null, "Data Berhasil di Update");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //delete data siswa---------------------------------------------------------
    private void delsiswa(){
        try{
            int bar = tbldatasiswa.getSelectedRow();
            String a = tbldatasiswa.getValueAt(bar, 0).toString();
            Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String sql_del = "DELETE from tbl_datasiswa WHERE id_siswa='"+a+"'";
            st.execute(sql_del);
            JOptionPane.showMessageDialog(null, "Data berhasil di Hapus");
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //clear data siswa----------------------------------------------------------
    private void clearsiswa(){
        nissiswa.setText(null);
        namasiswa.setText(null);
        jkl.setSelected(rootPaneCheckingEnabled);
        ting.setSelectedItem("Default");
        jur.setSelectedItem("Default");
        kel.setSelectedItem("Default");
        nope.setText(null);
    }
    
    //munculin data buku--------------------------------------------------------
    private void loadbuku(){
        java.sql.Connection kon = koneksi.koneksiDb();
        Object header[] = {"ID Buku","Judul Buku","Jenis Buku","Pengarang","Penerbit","Rak","Stok"};
        DefaultTableModel data = new DefaultTableModel(null,header);
        tbldatabuku.setModel(data);
        String sql_data = "SELECT * FROM tbl_databuku";
        try{
            Statement st = kon.createStatement();
            ResultSet rs = st.executeQuery(sql_data);
            while (rs.next()){
                String d1 = rs.getString(1);
                String d2 = rs.getString(2);
                String d3 = rs.getString(3);
                String d4 = rs.getString(4);
                String d5 = rs.getString(5);
                String d6 = rs.getString(6);
                String d7 = rs.getString(7);
              
                
                String d[] ={d1,d2,d3,d4,d5,d6,d7};
                data.addRow(d);
                
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //id generator buku---------------------------------------------------------
    private void autoidbuku(){
        try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String sql_id = "SELECT * FROM tbl_databuku order by id_buku desc";
            ResultSet rs = st.executeQuery(sql_id);
            if(rs.next()){
                String id_buku = rs.getString("id_buku").substring(1);
                String AN = ""+(Integer.parseInt(id_buku)+1);
                String Nol = "";
                if (AN.length() == 1){ //klo A00001
                    Nol = "0000";
                } 
                else if (AN.length() == 2){ //klo A00010
                    Nol = "000";
                } 
                else if (AN.length() == 3){ //klo A00100
                    Nol = "00";
                } 
                idbuku.setText("B" + Nol + AN);
            }
            else {
                idbuku.setText("B00001");
            }
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //combo box jenis buku-----------------------------------------------------
    private void combojb(){
        try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String sql_tingkat = "SELECT * FROM tbl_jenisbuku";
            ResultSet rs = st.executeQuery(sql_tingkat);
            while (rs.next()){
                jenisbuku.addItem(rs.getString("jenis_buku"));
                editjenisbuku.addItem(rs.getString("jenis_buku"));
            }
            rs.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //combo box rak buku--------------------------------------------------------
    private void comborb(){
        try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String sql_tingkat = "SELECT * FROM tbl_rakbuku";
            ResultSet rs = st.executeQuery(sql_tingkat);
            while (rs.next()){
                rakbuku.addItem(rs.getString("rak_buku"));
                editrakbuku.addItem(rs.getString("rak_buku"));
            }
            rs.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //resize images-------------------------------------------------------------
    public ImageIcon ResizeImages(String ImagePath, byte[] pic){
        ImageIcon MyImage = null;
        if(ImagePath != null){
            MyImage = new ImageIcon(ImagePath);
        }else{
            MyImage = new ImageIcon(pic);
        }
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(gambar.getWidth(), gambar.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }
    
    //input data buku-----------------------------------------------------------
    private void inputbuku(){
        if(imgpath != null)
        try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            
            PreparedStatement prs = kon.prepareStatement("INSERT INTO tbl_databuku(id_buku,judul_buku,jenis_buku,pengarang_buku,penerbit_buku,rak_buku,stok_buku,photo) VALUES(?,?,?,?,?,?,?,?)"); 
            InputStream img = new FileInputStream(new File(imgpath));
            prs.setString(1, idbuku.getText());
            prs.setString(2, judulbuku.getText());
            prs.setString(3, (String)jenisbuku.getSelectedItem());
            prs.setString(4, pengbuku.getText());
            prs.setString(5, penbuku.getText());
            prs.setString(6, (String)rakbuku.getSelectedItem());
            prs.setInt(7, Integer.parseInt(stokbuku.getText()));
            prs.setBlob(8, img);
            prs.execute();
            //st.execute();
            JOptionPane.showMessageDialog(null, "Data Berhasil Di Masukkan ");
        }
        catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
    //edit data buku------------------------------------------------------------
    private void editbuku(){
        try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            
            if(imgpath != null){
                PreparedStatement prs = kon.prepareStatement("UPDATE tbl_databuku SET judul_buku = ?, jenis_buku = ?, pengarang_buku = ?, penerbit_buku = ?, rak_buku = ?, stok_buku = ?, photo = ? WHERE id_buku = ?");
                InputStream img = new FileInputStream(new File(imgpath));
                prs.setString(1, editjudulbuku.getText());
                prs.setString(2, (String)editjenisbuku.getSelectedItem());
                prs.setString(3, editpengbuku.getText());
                prs.setString(4, editpenbuku.getText());
                prs.setString(5, (String)editrakbuku.getSelectedItem());
                prs.setInt(6, Integer.parseInt(editstokbuku.getText()));
                prs.setBlob(7, img);
                prs.setString(8, editidbuku.getText());
                prs.executeUpdate();
            }else{
                PreparedStatement prs = kon.prepareStatement("UPDATE tbl_databuku SET judul_buku = ?, jenis_buku = ?, pengarang_buku = ?, penerbit_buku = ?, rak_buku = ?, stok_buku = ? WHERE id_buku = ?");
                prs.setString(1, editjudulbuku.getText());
                prs.setString(2, (String)editjenisbuku.getSelectedItem());
                prs.setString(3, editpengbuku.getText());
                prs.setString(4, editpenbuku.getText());
                prs.setString(5, (String)editrakbuku.getSelectedItem());
                prs.setInt(6, Integer.parseInt(editstokbuku.getText()));
                prs.setString(7, editidbuku.getText());
                prs.executeUpdate();
            }

            JOptionPane.showMessageDialog(null, "Data Berhasil di Update");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //delete data buku----------------------------------------------------------
     private void delbuku(){
        try{
            int bar = tbldatabuku.getSelectedRow();
            String a = tbldatabuku.getValueAt(bar, 0).toString();
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String sql_del = "DELETE from tbl_databuku WHERE id_buku='"+a+"'";
            st.execute(sql_del);
            JOptionPane.showMessageDialog(null, "Data berhasil di Hapus");
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
     
     //clear data buku----------------------------------------------------------
      private void clearbuku(){
        judulbuku.setText(null);
        jenisbuku.setSelectedItem("Default");
        pengbuku.setText(null);
        penbuku.setText(null);
        rakbuku.setSelectedItem("Default");
        stokbuku.setText(null);
        gambar.setIcon(null);
    }
      
     //munculin laporan peminjam------------------------------------------------
      private void loadlprpj(){
        java.sql.Connection kon = koneksi.koneksiDb();
        Object header[] = {"No. Peminjam","Tanggal Pinjam","Id Siswa","NIS","Nama Siswa","Id Buku","Judul Buku","Jenis Buku"};
        DefaultTableModel data = new DefaultTableModel(null,header);
        tbllaporpinjam.setModel(data);
        String sql_data = "SELECT * FROM tbl_peminjam";
        try{
            Statement st = kon.createStatement();
            ResultSet rs = st.executeQuery(sql_data);
            while (rs.next()){
                String d1 = rs.getString(1);
                String d2 = rs.getString(2);
                String d3 = rs.getString(3);
                String d4 = rs.getString(4);
                String d5 = rs.getString(5);
                String d6 = rs.getString(6);
                String d7 = rs.getString(7);
                String d8 = rs.getString(8);
                
                String d[] ={d1,d2,d3,d4,d5,d6,d7,d8};
                data.addRow(d);
                
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
      
      //delete laporan peminjam-------------------------------------------------
     private void delpeminjam(){
         try{
             int bar = tbllaporpinjam.getSelectedRow();
             String a = tbllaporpinjam.getValueAt(bar, 0).toString();
             java.sql.Connection kon = koneksi.koneksiDb();
             Statement st = kon.createStatement();
             String sql_del = "DELETE from tbl_peminjam WHERE no_peminjam='"+a+"'";
             st.execute(sql_del);
             JOptionPane.showMessageDialog(null, "Data Berhasil Di Hapus");
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
     }
     
     //munculin laporan peminjam------------------------------------------------
      private void loadlprkmb(){
        java.sql.Connection kon = koneksi.koneksiDb();
        Object header[] = {"No. Pemgembalian","Tanggal kembali","Id Siswa","NIS","Nama Siswa","Id Buku","Judul Buku","Jenis Buku"};
        DefaultTableModel data = new DefaultTableModel(null,header);
        tbllaporkembali.setModel(data);
        String sql_data = "SELECT * FROM tbl_kembali";
        try{
            Statement st = kon.createStatement();
            ResultSet rs = st.executeQuery(sql_data);
            while (rs.next()){
                String d1 = rs.getString(1);
                String d2 = rs.getString(2);
                String d3 = rs.getString(3);
                String d4 = rs.getString(4);
                String d5 = rs.getString(5);
                String d6 = rs.getString(6);
                String d7 = rs.getString(7);
                String d8 = rs.getString(8);
                
                String d[] ={d1,d2,d3,d4,d5,d6,d7,d8};
                data.addRow(d);
                
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
      
      //delete laporan peminjam-------------------------------------------------
     private void delkembali(){
         try{
             int bar = tbllaporkembali.getSelectedRow();
             String a = tbllaporkembali.getValueAt(bar, 0).toString();
             java.sql.Connection kon = koneksi.koneksiDb();
             Statement st = kon.createStatement();
             String sql_del = "DELETE from tbl_kembali WHERE no_kembali='"+a+"'";
             String sqlup = "UPDATE tbl_peminjam SET ket_pinjam='false' WHERE no_peminjam=";
             st.execute(sql_del);
             st.execute(sqlup);
             JOptionPane.showMessageDialog(null, "Data Berhasil Di Hapus");
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
     }
     
     //menambah stok buku-------------------------------------------------------
     public void stokp(){
         try{
             java.sql.Connection kon = koneksi.koneksiDb();
             Statement st = kon.createStatement();
             String ank = "SELECT stok_buku FROM tbl_databuku WHERE id_buku='" + x + "'";
             ResultSet rs = st.executeQuery(ank);
             String c = "";
             while(rs.next()){
                 String a  = rs.getString("stok_buku");
                 int b = Integer.valueOf(a);
                 b++;
                 c = String.valueOf(b);
             }
             String up = "UPDATE tbl_databuku SET stok_buku='" +  c + "' WHERE id_buku='" + x + "'";
             st.execute(up);
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
     }
     
     //menambah stok siswa------------------------------------------------------
     public void sts(){
         try{
             java.sql.Connection kon = koneksi.koneksiDb();
             Statement st = kon.createStatement();
             String ank = "SELECT stok_siswa FROM tbl_datasiswa WHERE id_siswa='" + x + "'";
             ResultSet rs = st.executeQuery(ank);
             String c = "";
             while(rs.next()){
                 String a  = rs.getString("stok_siswa");
                 int b = Integer.valueOf(a);
                 b++;
                 c = String.valueOf(b);
             }
             String up = "UPDATE tbl_datasiswa SET stok_siswa='" +  c + "' WHERE id_siswa='" + x + "'";
             st.execute(up);
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
     }
     
     //tampilan gambar----------------------------------------------------------
     public ImageIcon sampul(){
         ImageIcon tampilkan = null;
         try{
             int row = tbldatabuku.getSelectedRow();
             String id = (String) tbldatabuku.getValueAt(row, 0);
             Connection kon = (Connection) koneksi.koneksiDb();
             Statement st = (Statement) kon.createStatement();
             String tampil = "SELECT * FROM tbl_databuku WHERE id_buku='" + id + "';";
             ResultSet rs = st.executeQuery(tampil);
             while (rs.next()) {
                 Blob gambar = (Blob) rs.getBlob("Photo");
                 int ukuran = (int) (gambar.length());
                 tampilkan = new ImageIcon(gambar.getBytes(1, ukuran));
//                 editgambar.setIcon(tampilkan);
             }
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
         return tampilkan;
     }
     
     //akun---------------------------------------------------------------------
     public void akun(){
         try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            int bar = tbldatasiswa.getSelectedRow();
            String id = tbldatasiswa.getValueAt(bar, 0).toString();
            String sql_data = "SELECT * FROM tbl_loginsiswa WHERE id_siswa='" + id + "'";
            ResultSet rs = st.executeQuery(sql_data);
            if(rs.next()){
                edituser.setText(rs.getString("login_user"));
                editpass.setText(rs.getString("password_user"));
                editkonpass.setText(rs.getString("password_user"));
            }
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
     }
         
     public void delregis(){
         try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            int bar = tbldatasiswa.getSelectedRow();
            String id = tbldatasiswa.getValueAt(bar, 0).toString();
            String sql_data = "DELETE FROM tbl_loginsiswa WHERE id_siswa='" + id + "'";
            st.execute(sql_data);
           
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
     }
     
     //mengurangi stok buku-----------------------------------------------------
    private void stok(){
        try{
             java.sql.Connection kon = koneksi.koneksiDb();
             Statement st = kon.createStatement();
             String ank = "SELECT stok_buku FROM tbl_databuku WHERE id_buku='" + x + "'";
             ResultSet rs = st.executeQuery(ank);
             String c = "";
             while(rs.next()){
                 String a  = rs.getString("stok_buku");
                 int b = Integer.valueOf(a);
                 b--;
                 c = String.valueOf(b);
             }
             String up = "UPDATE tbl_databuku SET stok_buku='" +  c + "' WHERE id_buku='" + x + "'";
             st.execute(up);
         }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //id generator buku---------------------------------------------------------
    private void autoiddenda(){
        try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String sql_id = "SELECT * FROM tbl_denda order by id_denda desc";
            ResultSet rs = st.executeQuery(sql_id);
            if(rs.next()){
                String id_buku = rs.getString("id_denda").substring(1);
                String AN = ""+(Integer.parseInt(id_buku)+1);
                String Nol = "";
                if (AN.length() == 1){ //klo A00001
                    Nol = "0000";
                } 
                else if (AN.length() == 2){ //klo A00010
                    Nol = "000";
                } 
                else if (AN.length() == 3){ //klo A00100
                    Nol = "00";
                } 
                iddenda.setText("D" + Nol + AN);
            }
            else {
                iddenda.setText("D00001");
            }
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //tanggal kebali------------------------------------------------------------
    public void tb(){
        Date tgp = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        tglby.setText(f.format(tgp));     
    }
    
     //no pengembalian----------------------------------------------------------
    private void copg(){
        try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String sql_buku = "SELECT * FROM tbl_kembali WHERE ket_kembali='false'";
            ResultSet rs = st.executeQuery(sql_buku);
            while(rs.next()){
                String dd = rs.getString("denda");
                if(dd.equals("0")){
                    
                }else{
                    nopeng.addItem(rs.getString("no_kembali"));
                }
            }
            rs.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //nama siswa----------------------------------------------------------------
    private void nsw(){
        try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String jlb = "SELECT nama_kembali FROM tbl_kembali WHERE no_kembali='"+nopeng.getSelectedItem()+"'";
            ResultSet rs = st.executeQuery(jlb);
            while(rs.next()){
                nsis.setText(rs.getString("nama_kembali"));
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //nis siswa-----------------------------------------------------------------
    private void nisw(){
        try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String jlb = "SELECT nis_kembali FROM tbl_kembali WHERE no_kembali='"+nopeng.getSelectedItem()+"'";
            ResultSet rs = st.executeQuery(jlb);
            while(rs.next()){
                nissis.setText(rs.getString("nis_kembali"));
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //judul buku----------------------------------------------------------------
    private void jjd(){
        try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String jlb = "SELECT judul_bkkembali FROM tbl_kembali WHERE no_kembali='"+nopeng.getSelectedItem()+"'";
            ResultSet rs = st.executeQuery(jlb);
            while(rs.next()){
                jdbk.setText(rs.getString("judul_bkkembali"));
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //jenis buku----------------------------------------------------------------
    private void jjn(){
        try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String jlb = "SELECT jenis_bkkembali FROM tbl_kembali WHERE no_kembali='"+nopeng.getSelectedItem()+"'";
            ResultSet rs = st.executeQuery(jlb);
            while(rs.next()){
                jnbk.setText(rs.getString("jenis_bkkembali"));
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //hapus bayar---------------------------------------------------------------
    private void clearby(){
       nsis.setText(null);
       nissis.setText(null);
       jdbk.setText(null);
        jnbk.setText(null);
        bayar.setText(null);
       nopeng.removeItem(nopeng.getSelectedItem());
      nopeng.setSelectedItem("Default");
    }
    
    //hitung denda--------------------------------------------------------------
    private void hiden(){
        try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String jlb = "SELECT denda FROM tbl_kembali WHERE no_kembali='"+nopeng.getSelectedItem()+"'";
            ResultSet rs = st.executeQuery(jlb);
            if(rs.next()){
                int sel = Integer.valueOf(rs.getString("denda"));
                int bia = 5000 * sel;
                bayar.setText(String.valueOf(bia));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //input data denda----------------------------------------------------------
    private void inputdenda(){
        try{
            Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            
            
            String sql = "INSERT INTO tbl_denda values('" + iddenda.getText() + "','"
                    + tglby.getText() + "','" + nopeng.getSelectedItem() + "','" + nsis.getText() + "','"
                    + nissis.getText() + "','" + jdbk.getText() + "','"
                    + jnbk.getText() + "','" + bayar.getText() + "')";
            st.execute(sql);
            
        }
        catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //munculin data siswa------------------------------------------------------- 
    private void loaddenda(){
        Connection kon = koneksi.koneksiDb();
        Object header[] = {"Id Denda","Tanggal Bayar","No Pengembalian","Nama","Nis","Judul Buku","Jenis","Total Denda"};
        DefaultTableModel data = new DefaultTableModel(null,header);
        tbldenda.setModel(data);
        String sql_data = "SELECT * FROM tbl_denda";
        try{
            Statement st = kon.createStatement();
            ResultSet rs = st.executeQuery(sql_data);
            while (rs.next()){
                String d1 = rs.getString(1);
                String d2 = rs.getString(2);
                String d3 = rs.getString(3);
                String d4 = rs.getString(4);
                String d5 = rs.getString(5);
                String d6 = rs.getString(6);
                String d7 = rs.getString(7);
                String d8 = rs.getString(8);
                
                String d[] ={d1,d2,d3,d4,d5,d6,d7,d8};
                data.addRow(d);
                
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //update keterangan denda---------------------------------------------------
    
    public void tru(){
        try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String up = "UPDATE tbl_kembali SET ket_kembali='true' WHERE no_kembali='" + nopeng.getSelectedItem() + "'";
            st.execute(up);
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
    }
    
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jk = new javax.swing.ButtonGroup();
        sidebar = new javax.swing.JPanel();
        dts = new javax.swing.JPanel();
        datasiswa = new javax.swing.JLabel();
        dsh = new javax.swing.JPanel();
        dashboard = new javax.swing.JLabel();
        admin = new javax.swing.JLabel();
        logout = new javax.swing.JPanel();
        lgo = new javax.swing.JLabel();
        dtb = new javax.swing.JPanel();
        databuku = new javax.swing.JLabel();
        lpp = new javax.swing.JPanel();
        lprpeminjam = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lppg = new javax.swing.JPanel();
        lprpengembalian = new javax.swing.JLabel();
        dd = new javax.swing.JPanel();
        denda = new javax.swing.JLabel();
        footer = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tabdashboard = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        tabdatasiswa = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbldatasiswa = new javax.swing.JTable();
        tambahdtsis = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        editsis = new javax.swing.JButton();
        delsis = new javax.swing.JButton();
        tambahdatasiswa = new javax.swing.JPanel();
        save = new javax.swing.JButton();
        backsiswa = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        user = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        pass = new javax.swing.JPasswordField();
        cekpass = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        konpass = new javax.swing.JPasswordField();
        cekkon = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        idsiswa = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        namasiswa = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        nissiswa = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jkl = new javax.swing.JRadioButton();
        jkp = new javax.swing.JRadioButton();
        ting = new javax.swing.JComboBox<>();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jur = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        kel = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        nope = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        alamat = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        ttl = new javax.swing.JTextField();
        editdatasiswa = new javax.swing.JPanel();
        backeditsiswa = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        editsiswa = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        edituser = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        editpass = new javax.swing.JPasswordField();
        editcekpass = new javax.swing.JCheckBox();
        jLabel54 = new javax.swing.JLabel();
        editkonpass = new javax.swing.JPasswordField();
        editcekkon = new javax.swing.JCheckBox();
        jPanel12 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        editidsiswa = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        editnamasiswa = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        editnissiswa = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        editjkl = new javax.swing.JRadioButton();
        editjkp = new javax.swing.JRadioButton();
        editting = new javax.swing.JComboBox<>();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        editjur = new javax.swing.JComboBox<>();
        jLabel62 = new javax.swing.JLabel();
        editkel = new javax.swing.JComboBox<>();
        jLabel63 = new javax.swing.JLabel();
        editnope = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        editalamat = new javax.swing.JTextField();
        editttl = new javax.swing.JTextField();
        tabdatabuku = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbldatabuku = new javax.swing.JTable();
        tambahdtbk = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        editbk = new javax.swing.JButton();
        delbk = new javax.swing.JButton();
        tambahdatabuku = new javax.swing.JPanel();
        backbuku = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        idbuku = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        judulbuku = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        rakbuku = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        jenisbuku = new javax.swing.JComboBox<>();
        jLabel34 = new javax.swing.JLabel();
        stokbuku = new javax.swing.JTextField();
        pengbuku = new javax.swing.JTextField();
        penbuku = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        gambar = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        savebuku = new javax.swing.JButton();
        chooser = new javax.swing.JButton();
        editdatabuku = new javax.swing.JPanel();
        backeditbuku = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        editbuku = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        editidbuku = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        editjudulbuku = new javax.swing.JTextField();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        editrakbuku = new javax.swing.JComboBox<>();
        jLabel69 = new javax.swing.JLabel();
        editjenisbuku = new javax.swing.JComboBox<>();
        jLabel70 = new javax.swing.JLabel();
        editstokbuku = new javax.swing.JTextField();
        editpengbuku = new javax.swing.JTextField();
        editpenbuku = new javax.swing.JTextField();
        jLabel71 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        editgambar = new javax.swing.JLabel();
        editchooser = new javax.swing.JButton();
        laporanpeminjam = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbllaporpinjam = new javax.swing.JTable();
        delpin = new javax.swing.JButton();
        laporanpengembalian = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbllaporkembali = new javax.swing.JTable();
        delkem = new javax.swing.JButton();
        dendapeminjam = new javax.swing.JPanel();
        jLabel73 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbldenda = new javax.swing.JTable();
        editden = new javax.swing.JButton();
        pembayaran = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        bayar = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        tglby = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        nopeng = new javax.swing.JComboBox<>();
        jLabel43 = new javax.swing.JLabel();
        jdbk = new javax.swing.JTextField();
        nsis = new javax.swing.JTextField();
        nissis = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        jnbk = new javax.swing.JTextField();
        iddenda = new javax.swing.JTextField();
        backdenda = new javax.swing.JButton();
        savedenda = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sidebar.setBackground(new java.awt.Color(102, 102, 102));

        dts.setBackground(new java.awt.Color(102, 102, 102));

        datasiswa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        datasiswa.setForeground(new java.awt.Color(255, 255, 255));
        datasiswa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_students_40px.png"))); // NOI18N
        datasiswa.setText("Data Siswa");
        datasiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                datasiswaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout dtsLayout = new javax.swing.GroupLayout(dts);
        dts.setLayout(dtsLayout);
        dtsLayout.setHorizontalGroup(
            dtsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dtsLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(datasiswa)
                .addContainerGap(116, Short.MAX_VALUE))
        );
        dtsLayout.setVerticalGroup(
            dtsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(datasiswa, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
        );

        dsh.setBackground(new java.awt.Color(102, 102, 102));

        dashboard.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dashboard.setForeground(new java.awt.Color(255, 255, 255));
        dashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_content_30px.png"))); // NOI18N
        dashboard.setText("Dashbord");
        dashboard.setMaximumSize(new java.awt.Dimension(158, 50));
        dashboard.setMinimumSize(new java.awt.Dimension(158, 50));
        dashboard.setPreferredSize(new java.awt.Dimension(158, 50));
        dashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashboardMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout dshLayout = new javax.swing.GroupLayout(dsh);
        dsh.setLayout(dshLayout);
        dshLayout.setHorizontalGroup(
            dshLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dshLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dshLayout.setVerticalGroup(
            dshLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dashboard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        admin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_Admin_Settings_Male_150px.png"))); // NOI18N

        logout.setBackground(new java.awt.Color(102, 102, 102));
        logout.setPreferredSize(new java.awt.Dimension(201, 50));

        lgo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lgo.setForeground(new java.awt.Color(255, 255, 255));
        lgo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_exit_40px.png"))); // NOI18N
        lgo.setText("Log Out");
        lgo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lgoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout logoutLayout = new javax.swing.GroupLayout(logout);
        logout.setLayout(logoutLayout);
        logoutLayout.setHorizontalGroup(
            logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logoutLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(lgo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        logoutLayout.setVerticalGroup(
            logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logoutLayout.createSequentialGroup()
                .addComponent(lgo, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addContainerGap())
        );

        dtb.setBackground(new java.awt.Color(102, 102, 102));

        databuku.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        databuku.setForeground(new java.awt.Color(255, 255, 255));
        databuku.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_book_shelf_40px.png"))); // NOI18N
        databuku.setText("Data Buku");
        databuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                databukuMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout dtbLayout = new javax.swing.GroupLayout(dtb);
        dtb.setLayout(dtbLayout);
        dtbLayout.setHorizontalGroup(
            dtbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dtbLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(databuku)
                .addContainerGap(122, Short.MAX_VALUE))
        );
        dtbLayout.setVerticalGroup(
            dtbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dtbLayout.createSequentialGroup()
                .addComponent(databuku, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                .addContainerGap())
        );

        lpp.setBackground(new java.awt.Color(102, 102, 102));

        lprpeminjam.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lprpeminjam.setForeground(new java.awt.Color(255, 255, 255));
        lprpeminjam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_view_file_40px.png"))); // NOI18N
        lprpeminjam.setText("Laporan Peminjam");
        lprpeminjam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lprpeminjamMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout lppLayout = new javax.swing.GroupLayout(lpp);
        lpp.setLayout(lppLayout);
        lppLayout.setHorizontalGroup(
            lppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lppLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lprpeminjam)
                .addGap(61, 61, 61))
        );
        lppLayout.setVerticalGroup(
            lppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lprpeminjam, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
        );

        lppg.setBackground(new java.awt.Color(102, 102, 102));

        lprpengembalian.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lprpengembalian.setForeground(new java.awt.Color(255, 255, 255));
        lprpengembalian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_graph_report_40px_4.png"))); // NOI18N
        lprpengembalian.setText("Laporan Pengembalian");
        lprpengembalian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lprpengembalianMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout lppgLayout = new javax.swing.GroupLayout(lppg);
        lppg.setLayout(lppgLayout);
        lppgLayout.setHorizontalGroup(
            lppgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lppgLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(lprpengembalian)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        lppgLayout.setVerticalGroup(
            lppgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lprpengembalian, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
        );

        dd.setBackground(new java.awt.Color(102, 102, 102));

        denda.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        denda.setForeground(new java.awt.Color(255, 255, 255));
        denda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_fine_print_40px.png"))); // NOI18N
        denda.setText("Denda");
        denda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dendaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout ddLayout = new javax.swing.GroupLayout(dd);
        dd.setLayout(ddLayout);
        ddLayout.setHorizontalGroup(
            ddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ddLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(denda)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ddLayout.setVerticalGroup(
            ddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(denda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout sidebarLayout = new javax.swing.GroupLayout(sidebar);
        sidebar.setLayout(sidebarLayout);
        sidebarLayout.setHorizontalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(admin)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(sidebarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sidebarLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(dd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(logout, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addComponent(dtb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dsh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addComponent(lpp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lppg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        sidebarLayout.setVerticalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(admin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dsh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dtb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lpp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lppg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(logout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(231, Short.MAX_VALUE))
        );

        getContentPane().add(sidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 868));

        footer.setBackground(new java.awt.Color(51, 51, 255));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Copyright");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_copyright_20px.png"))); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("2025 SMK Nusa bangsa");

        javax.swing.GroupLayout footerLayout = new javax.swing.GroupLayout(footer);
        footer.setLayout(footerLayout);
        footerLayout.setHorizontalGroup(
            footerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(footerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addContainerGap(1010, Short.MAX_VALUE))
        );
        footerLayout.setVerticalGroup(
            footerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(footerLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(footerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(footerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(jLabel12))
                    .addComponent(jLabel13))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        getContentPane().add(footer, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 790, 1280, 90));

        header.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SMK Nusa Bangsa");

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(306, 306, 306)
                .addComponent(jLabel1)
                .addContainerGap(543, Short.MAX_VALUE))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        getContentPane().add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 1280, 100));

        tabdashboard.setBackground(new java.awt.Color(153, 255, 255));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        jLabel2.setText("Selamat Datang di Perpustakaan");

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        jLabel19.setText("SMK Nusa Bangsa!");

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Desain tanpa judul icon.png"))); // NOI18N

        javax.swing.GroupLayout tabdashboardLayout = new javax.swing.GroupLayout(tabdashboard);
        tabdashboard.setLayout(tabdashboardLayout);
        tabdashboardLayout.setHorizontalGroup(
            tabdashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabdashboardLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabdashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel19))
                .addContainerGap(598, Short.MAX_VALUE))
        );
        tabdashboardLayout.setVerticalGroup(
            tabdashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabdashboardLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(tabdashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addGroup(tabdashboardLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)))
                .addContainerGap(456, Short.MAX_VALUE))
        );

        getContentPane().add(tabdashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 1280, 690));

        tabdatasiswa.setBackground(new java.awt.Color(153, 255, 255));
        tabdatasiswa.setPreferredSize(new java.awt.Dimension(1282, 670));

        tbldatasiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tbldatasiswa);

        tambahdtsis.setBackground(new java.awt.Color(51, 51, 255));
        tambahdtsis.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tambahdtsis.setForeground(new java.awt.Color(255, 255, 255));
        tambahdtsis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_plus_math_30px.png"))); // NOI18N
        tambahdtsis.setText("Tambah Data");
        tambahdtsis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahdtsisActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setText("Data Siswa");

        editsis.setBackground(new java.awt.Color(255, 153, 0));
        editsis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_edit_40px.png"))); // NOI18N
        editsis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editsisActionPerformed(evt);
            }
        });

        delsis.setBackground(new java.awt.Color(255, 51, 51));
        delsis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_delete_40px.png"))); // NOI18N
        delsis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delsisActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabdatasiswaLayout = new javax.swing.GroupLayout(tabdatasiswa);
        tabdatasiswa.setLayout(tabdatasiswaLayout);
        tabdatasiswaLayout.setHorizontalGroup(
            tabdatasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabdatasiswaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabdatasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabdatasiswaLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 687, Short.MAX_VALUE)
                        .addComponent(tambahdtsis))
                    .addGroup(tabdatasiswaLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 955, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabdatasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(editsis, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(delsis, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(269, 269, 269))
        );
        tabdatasiswaLayout.setVerticalGroup(
            tabdatasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabdatasiswaLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(tabdatasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tambahdtsis))
                .addGap(18, 18, 18)
                .addGroup(tabdatasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabdatasiswaLayout.createSequentialGroup()
                        .addComponent(editsis, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(delsis))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(145, Short.MAX_VALUE))
        );

        getContentPane().add(tabdatasiswa, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 1280, 690));

        tambahdatasiswa.setBackground(new java.awt.Color(153, 255, 255));

        save.setBackground(new java.awt.Color(204, 204, 204));
        save.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        save.setForeground(new java.awt.Color(51, 51, 51));
        save.setText("Simpan");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        backsiswa.setBackground(new java.awt.Color(204, 204, 204));
        backsiswa.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        backsiswa.setForeground(new java.awt.Color(51, 51, 51));
        backsiswa.setText("Kembali");
        backsiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backsiswaActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel5.setText("Tambah Data Siswa");

        jPanel4.setBackground(new java.awt.Color(51, 51, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(230, 230, 230));

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel45.setText("Sign Up");

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel46.setText("Username:");

        user.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel47.setText("Password:");

        pass.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        cekpass.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cekpass.setText("Show Password");
        cekpass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cekpassActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setText("Konfirmasi Password:");

        konpass.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        cekkon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cekkon.setText("Show Password");
        cekkon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cekkonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6)
                            .addComponent(cekkon)
                            .addComponent(jLabel46)
                            .addComponent(jLabel47)
                            .addComponent(cekpass)
                            .addComponent(user)
                            .addComponent(pass)
                            .addComponent(konpass, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(176, 176, 176)
                        .addComponent(jLabel45)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45)
                .addGap(20, 20, 20)
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cekpass)
                .addGap(40, 40, 40)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(konpass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cekkon)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(230, 230, 230));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel7.setText("Registrasi");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setText("Id Siswa");

        idsiswa.setEditable(false);
        idsiswa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel48.setText("Nama");

        namasiswa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setText("NIS");

        nissiswa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setText("Jenis Kelamin");

        jk.add(jkl);
        jkl.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jkl.setText("L");

        jk.add(jkp);
        jkp.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jkp.setText("P");

        ting.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ting.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Default", "X", "XI", "XII" }));

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel49.setText("Tingkat");

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel50.setText("Jurusan");

        jur.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jur.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Default", "RPL", "DKV", "LP", "AK", "MP", "BD" }));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel14.setText("Rombel");

        kel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        kel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Default", "1", "2", "3", "4", "5", "6", "7" }));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel15.setText("No HP");

        nope.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel17.setText("Alamat");

        alamat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        alamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alamatActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel18.setText("TTL");

        ttl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel48)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(idsiswa, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                                    .addComponent(namasiswa)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel18)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(ttl, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(alamat, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(nope, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(kel, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel50)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                                        .addComponent(jur, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel11)
                                                .addGap(18, 18, 18)
                                                .addComponent(jkl)
                                                .addGap(59, 59, 59)
                                                .addComponent(jkp))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel49)
                                                    .addComponent(jLabel10))
                                                .addGap(85, 85, 85)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(nissiswa)
                                                    .addComponent(ting, 0, 256, Short.MAX_VALUE))))
                                        .addGap(0, 0, Short.MAX_VALUE))))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(232, 232, 232)
                        .addComponent(jLabel7)))
                .addGap(51, 119, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(idsiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(namasiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(nissiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jkl)
                    .addComponent(jkp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(jur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(kel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(nope, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(alamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(ttl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout tambahdatasiswaLayout = new javax.swing.GroupLayout(tambahdatasiswa);
        tambahdatasiswa.setLayout(tambahdatasiswaLayout);
        tambahdatasiswaLayout.setHorizontalGroup(
            tambahdatasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tambahdatasiswaLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(tambahdatasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tambahdatasiswaLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(tambahdatasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tambahdatasiswaLayout.createSequentialGroup()
                                .addComponent(backsiswa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(save))
                            .addGroup(tambahdatasiswaLayout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel5)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tambahdatasiswaLayout.setVerticalGroup(
            tambahdatasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tambahdatasiswaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tambahdatasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tambahdatasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backsiswa)
                    .addComponent(save))
                .addGap(0, 260, Short.MAX_VALUE))
        );

        getContentPane().add(tambahdatasiswa, new org.netbeans.lib.awtextra.AbsoluteConstraints(254, 100, 1290, 770));

        editdatasiswa.setBackground(new java.awt.Color(153, 255, 255));

        backeditsiswa.setBackground(new java.awt.Color(204, 204, 204));
        backeditsiswa.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        backeditsiswa.setForeground(new java.awt.Color(51, 51, 51));
        backeditsiswa.setText("Kembali");
        backeditsiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backeditsiswaActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel16.setText("Edit Data Siswa");

        jPanel7.setBackground(new java.awt.Color(51, 51, 255));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1100, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        editsiswa.setBackground(new java.awt.Color(204, 204, 204));
        editsiswa.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        editsiswa.setForeground(new java.awt.Color(51, 51, 51));
        editsiswa.setText("Edit");
        editsiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editsiswaActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(230, 230, 230));

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel51.setText("Sign Up");

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel52.setText("Username:");

        edituser.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel53.setText("Password:");

        editpass.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        editcekpass.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        editcekpass.setText("Show Password");
        editcekpass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editcekpassActionPerformed(evt);
            }
        });

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel54.setText("Konfirmasi Password:");

        editkonpass.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        editcekkon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        editcekkon.setText("Show Password");
        editcekkon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editcekkonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel54)
                            .addComponent(editcekkon)
                            .addComponent(jLabel52)
                            .addComponent(jLabel53)
                            .addComponent(editcekpass)
                            .addComponent(edituser)
                            .addComponent(editpass)
                            .addComponent(editkonpass, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(176, 176, 176)
                        .addComponent(jLabel51)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel51)
                .addGap(20, 20, 20)
                .addComponent(jLabel52)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edituser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jLabel53)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editpass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editcekpass)
                .addGap(40, 40, 40)
                .addComponent(jLabel54)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editkonpass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editcekkon)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(230, 230, 230));

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel55.setText("Registrasi");

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel56.setText("Id Siswa");

        editidsiswa.setEditable(false);
        editidsiswa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel57.setText("Nama");

        editnamasiswa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel58.setText("NIS");

        editnissiswa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel59.setText("Jenis Kelamin");

        jk.add(editjkl);
        editjkl.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        editjkl.setText("L");

        jk.add(editjkp);
        editjkp.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        editjkp.setText("P");

        editting.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        editting.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Default", "X", "XI", "XII" }));

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel60.setText("Tingkat");

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel61.setText("Jurusan");

        editjur.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        editjur.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Default", "RPL", "DKV", "LP", "AK", "MP", "BD" }));

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel62.setText("Kelas");

        editkel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        editkel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Default", "1", "2", "3", "4", "5", "6", "7" }));

        jLabel63.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel63.setText("No HP");

        editnope.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel75.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel75.setText("Alamat");

        jLabel76.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel76.setText("TTL");

        editalamat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        editttl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel57)
                                    .addComponent(jLabel56))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(editidsiswa, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                                    .addComponent(editnamasiswa)))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addComponent(jLabel76)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(editttl, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addComponent(jLabel75)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(editalamat, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addComponent(jLabel63)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(editnope, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addComponent(jLabel62)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(editkel, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addComponent(jLabel61)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                                        .addComponent(editjur, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel12Layout.createSequentialGroup()
                                                .addComponent(jLabel59)
                                                .addGap(18, 18, 18)
                                                .addComponent(editjkl)
                                                .addGap(59, 59, 59)
                                                .addComponent(editjkp))
                                            .addGroup(jPanel12Layout.createSequentialGroup()
                                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel60)
                                                    .addComponent(jLabel58))
                                                .addGap(85, 85, 85)
                                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(editnissiswa)
                                                    .addComponent(editting, 0, 256, Short.MAX_VALUE))))
                                        .addGap(0, 0, Short.MAX_VALUE))))))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(232, 232, 232)
                        .addComponent(jLabel55)))
                .addGap(51, 92, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel55)
                .addGap(20, 20, 20)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(editidsiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(editnamasiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(editnissiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59)
                    .addComponent(editjkl)
                    .addComponent(editjkp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel60))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61)
                    .addComponent(editjur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel62)
                    .addComponent(editkel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel63)
                    .addComponent(editnope, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel75)
                    .addComponent(editalamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel76)
                    .addComponent(editttl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout editdatasiswaLayout = new javax.swing.GroupLayout(editdatasiswa);
        editdatasiswa.setLayout(editdatasiswaLayout);
        editdatasiswaLayout.setHorizontalGroup(
            editdatasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editdatasiswaLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(editdatasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editdatasiswaLayout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel16)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(editdatasiswaLayout.createSequentialGroup()
                        .addComponent(backeditsiswa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editsiswa)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        editdatasiswaLayout.setVerticalGroup(
            editdatasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editdatasiswaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editdatasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editdatasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editsiswa)
                    .addComponent(backeditsiswa))
                .addContainerGap(106, Short.MAX_VALUE))
        );

        getContentPane().add(editdatasiswa, new org.netbeans.lib.awtextra.AbsoluteConstraints(258, 98, 1280, 770));

        tabdatabuku.setBackground(new java.awt.Color(153, 255, 255));

        tbldatabuku.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tbldatabuku);

        tambahdtbk.setBackground(new java.awt.Color(51, 51, 255));
        tambahdtbk.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tambahdtbk.setForeground(new java.awt.Color(255, 255, 255));
        tambahdtbk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_plus_math_30px.png"))); // NOI18N
        tambahdtbk.setText("Tambah Data");
        tambahdtbk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahdtbkActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel25.setText("Data Buku");

        editbk.setBackground(new java.awt.Color(255, 153, 0));
        editbk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_edit_40px.png"))); // NOI18N
        editbk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editbkActionPerformed(evt);
            }
        });

        delbk.setBackground(new java.awt.Color(255, 51, 51));
        delbk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_delete_40px.png"))); // NOI18N
        delbk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delbkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabdatabukuLayout = new javax.swing.GroupLayout(tabdatabuku);
        tabdatabuku.setLayout(tabdatabukuLayout);
        tabdatabukuLayout.setHorizontalGroup(
            tabdatabukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabdatabukuLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(tabdatabukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabdatabukuLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 915, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(tabdatabukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(editbk, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(delbk, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(tabdatabukuLayout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tambahdtbk)))
                .addGap(270, 270, 270))
        );
        tabdatabukuLayout.setVerticalGroup(
            tabdatabukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabdatabukuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabdatabukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(tambahdtbk))
                .addGap(18, 18, 18)
                .addGroup(tabdatabukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabdatabukuLayout.createSequentialGroup()
                        .addComponent(editbk, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(delbk))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(161, Short.MAX_VALUE))
        );

        getContentPane().add(tabdatabuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 1280, 690));

        tambahdatabuku.setBackground(new java.awt.Color(153, 255, 255));

        backbuku.setBackground(new java.awt.Color(204, 204, 204));
        backbuku.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        backbuku.setForeground(new java.awt.Color(51, 51, 51));
        backbuku.setText("Kembali");
        backbuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backbukuActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel26.setText("Tambah Buku Siswa");

        jPanel8.setBackground(new java.awt.Color(245, 245, 245));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel27.setText("Id Buku");

        idbuku.setEditable(false);
        idbuku.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        idbuku.setMaximumSize(new java.awt.Dimension(200, 256));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel28.setText("Judul Buku");

        judulbuku.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel29.setText("Jenis Buku");

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel30.setText("Rak");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel32.setText("Pengarang");

        rakbuku.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        rakbuku.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Default" }));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel33.setText("Penerbit");

        jenisbuku.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jenisbuku.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Default" }));

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel34.setText("Stok");

        stokbuku.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        pengbuku.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        penbuku.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel31.setText("Sampul Buku");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gambar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gambar, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(jLabel29)
                            .addComponent(jLabel32)
                            .addComponent(jLabel28)
                            .addComponent(jLabel33)
                            .addComponent(jLabel34))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pengbuku, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jenisbuku, 0, 256, Short.MAX_VALUE)
                                .addComponent(idbuku, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(judulbuku))
                            .addComponent(penbuku, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(stokbuku, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rakbuku, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(87, 87, 87))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel31)
                        .addGap(173, 173, 173))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(idbuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(judulbuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(jenisbuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(pengbuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(penbuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(stokbuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(rakbuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(51, 51, 255));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        savebuku.setBackground(new java.awt.Color(204, 204, 204));
        savebuku.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        savebuku.setForeground(new java.awt.Color(51, 51, 51));
        savebuku.setText("Simpan");
        savebuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savebukuActionPerformed(evt);
            }
        });

        chooser.setBackground(new java.awt.Color(204, 204, 204));
        chooser.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        chooser.setForeground(new java.awt.Color(51, 51, 51));
        chooser.setText("Chooser");
        chooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tambahdatabukuLayout = new javax.swing.GroupLayout(tambahdatabuku);
        tambahdatabuku.setLayout(tambahdatabukuLayout);
        tambahdatabukuLayout.setHorizontalGroup(
            tambahdatabukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tambahdatabukuLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(tambahdatabukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel26)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(tambahdatabukuLayout.createSequentialGroup()
                        .addComponent(backbuku)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(savebuku)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chooser)
                        .addGap(211, 211, 211)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tambahdatabukuLayout.setVerticalGroup(
            tambahdatabukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tambahdatabukuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tambahdatabukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(savebuku)
                    .addComponent(backbuku)
                    .addComponent(chooser))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        getContentPane().add(tambahdatabuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 1280, 770));

        editdatabuku.setBackground(new java.awt.Color(153, 255, 255));

        backeditbuku.setBackground(new java.awt.Color(204, 204, 204));
        backeditbuku.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        backeditbuku.setForeground(new java.awt.Color(51, 51, 51));
        backeditbuku.setText("Kembali");
        backeditbuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backeditbukuActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel35.setText("Edit Data Buku");

        jPanel10.setBackground(new java.awt.Color(51, 51, 255));
        jPanel10.setPreferredSize(new java.awt.Dimension(0, 39));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        editbuku.setBackground(new java.awt.Color(204, 204, 204));
        editbuku.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        editbuku.setForeground(new java.awt.Color(51, 51, 51));
        editbuku.setText("Edit");
        editbuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editbukuActionPerformed(evt);
            }
        });

        jPanel13.setBackground(new java.awt.Color(245, 245, 245));

        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel64.setText("Id Buku");

        editidbuku.setEditable(false);
        editidbuku.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        editidbuku.setMaximumSize(new java.awt.Dimension(200, 256));

        jLabel65.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel65.setText("Judul Buku");

        editjudulbuku.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        editjudulbuku.setMaximumSize(new java.awt.Dimension(200, 256));

        jLabel66.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel66.setText("Jenis Buku");

        jLabel67.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel67.setText("Rak");

        jLabel68.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel68.setText("Pengarang");

        editrakbuku.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        editrakbuku.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Default" }));

        jLabel69.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel69.setText("Penerbit");

        editjenisbuku.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        editjenisbuku.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Default" }));

        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel70.setText("Stok");

        editstokbuku.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        editpengbuku.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        editpenbuku.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel71.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel71.setText("Sampul Buku");

        jPanel6.setPreferredSize(new java.awt.Dimension(350, 402));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(editgambar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(editgambar, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel64)
                            .addComponent(jLabel66)
                            .addComponent(jLabel68)
                            .addComponent(jLabel65)
                            .addComponent(jLabel69)
                            .addComponent(jLabel70))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(editpengbuku, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(editjudulbuku, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(editjenisbuku, 0, 256, Short.MAX_VALUE)
                                .addComponent(editidbuku, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(editpenbuku, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(editstokbuku, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel67)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(editrakbuku, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel71)
                        .addGap(173, 173, 173))))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(editidbuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel71))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(editjudulbuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel65))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel66)
                            .addComponent(editjenisbuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel68)
                            .addComponent(editpengbuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel69)
                            .addComponent(editpenbuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel70)
                            .addComponent(editstokbuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel67)
                            .addComponent(editrakbuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editchooser.setBackground(new java.awt.Color(204, 204, 204));
        editchooser.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        editchooser.setForeground(new java.awt.Color(51, 51, 51));
        editchooser.setText("Chooser");
        editchooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editchooserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout editdatabukuLayout = new javax.swing.GroupLayout(editdatabuku);
        editdatabuku.setLayout(editdatabukuLayout);
        editdatabukuLayout.setHorizontalGroup(
            editdatabukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editdatabukuLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(editdatabukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addGroup(editdatabukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(editdatabukuLayout.createSequentialGroup()
                            .addComponent(backeditbuku)
                            .addGap(18, 18, 18)
                            .addComponent(editbuku)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(editchooser)
                            .addGap(227, 227, 227))
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 1149, Short.MAX_VALUE)
                        .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        editdatabukuLayout.setVerticalGroup(
            editdatabukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editdatabukuLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editdatabukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backeditbuku)
                    .addComponent(editbuku)
                    .addComponent(editchooser))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(editdatabuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 1280, 770));

        laporanpeminjam.setBackground(new java.awt.Color(153, 255, 255));

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel44.setText("Laporan Peminjam");

        tbllaporpinjam.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tbllaporpinjam);

        delpin.setBackground(new java.awt.Color(255, 51, 51));
        delpin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_delete_40px.png"))); // NOI18N
        delpin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delpinActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout laporanpeminjamLayout = new javax.swing.GroupLayout(laporanpeminjam);
        laporanpeminjam.setLayout(laporanpeminjamLayout);
        laporanpeminjamLayout.setHorizontalGroup(
            laporanpeminjamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(laporanpeminjamLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(laporanpeminjamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel44)
                    .addGroup(laporanpeminjamLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 930, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(delpin)))
                .addContainerGap(87, Short.MAX_VALUE))
        );
        laporanpeminjamLayout.setVerticalGroup(
            laporanpeminjamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(laporanpeminjamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel44)
                .addGap(18, 18, 18)
                .addGroup(laporanpeminjamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delpin))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(laporanpeminjam, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 1280, 690));

        laporanpengembalian.setBackground(new java.awt.Color(153, 255, 255));

        jLabel72.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel72.setText("Laporan Pengembalian");

        tbllaporkembali.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tbllaporkembali);

        delkem.setBackground(new java.awt.Color(255, 51, 51));
        delkem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_delete_40px.png"))); // NOI18N
        delkem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delkemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout laporanpengembalianLayout = new javax.swing.GroupLayout(laporanpengembalian);
        laporanpengembalian.setLayout(laporanpengembalianLayout);
        laporanpengembalianLayout.setHorizontalGroup(
            laporanpengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(laporanpengembalianLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(laporanpengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel72)
                    .addGroup(laporanpengembalianLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 930, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(delkem)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        laporanpengembalianLayout.setVerticalGroup(
            laporanpengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(laporanpengembalianLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel72)
                .addGap(18, 18, 18)
                .addGroup(laporanpengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delkem))
                .addContainerGap(174, Short.MAX_VALUE))
        );

        getContentPane().add(laporanpengembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 1280, 690));

        dendapeminjam.setBackground(new java.awt.Color(153, 255, 255));

        jLabel73.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel73.setText("Laporan Denda");

        tbldenda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tbldenda);

        editden.setBackground(new java.awt.Color(255, 153, 0));
        editden.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_edit_40px.png"))); // NOI18N
        editden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editdenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dendapeminjamLayout = new javax.swing.GroupLayout(dendapeminjam);
        dendapeminjam.setLayout(dendapeminjamLayout);
        dendapeminjamLayout.setHorizontalGroup(
            dendapeminjamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dendapeminjamLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(dendapeminjamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel73)
                    .addGroup(dendapeminjamLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 930, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editden)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dendapeminjamLayout.setVerticalGroup(
            dendapeminjamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dendapeminjamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel73)
                .addGap(18, 18, 18)
                .addGroup(dendapeminjamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editden, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(209, Short.MAX_VALUE))
        );

        getContentPane().add(dendapeminjam, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 1280, 690));

        pembayaran.setBackground(new java.awt.Color(153, 255, 255));

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel36.setText("Pembayaran");

        jPanel11.setBackground(new java.awt.Color(51, 51, 255));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1149, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        jPanel14.setBackground(new java.awt.Color(245, 245, 245));

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel37.setText("Id Denda");

        bayar.setEditable(false);
        bayar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        bayar.setMaximumSize(new java.awt.Dimension(200, 256));

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel38.setText("Tanggal Bayar");

        tglby.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel39.setText("No Pengembalian");

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel40.setText("Jenis Buku");

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel41.setText("Nama");

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel42.setText("Nis");

        nopeng.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        nopeng.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Default" }));
        nopeng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nopengActionPerformed(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel43.setText("Judul Buku");

        jdbk.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        nsis.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        nissis.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel74.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel74.setText("Total denda");

        jnbk.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        iddenda.setEditable(false);
        iddenda.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        iddenda.setMaximumSize(new java.awt.Dimension(200, 256));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel37)
                            .addComponent(jLabel39)
                            .addComponent(jLabel41)
                            .addComponent(jLabel38)
                            .addComponent(jLabel42)
                            .addComponent(jLabel43))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nsis, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(nopeng, 0, 256, Short.MAX_VALUE)
                                .addComponent(tglby)
                                .addComponent(iddenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(nissis, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jdbk, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jnbk, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38)
                .addComponent(jLabel74)
                .addGap(18, 18, 18)
                .addComponent(bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(jLabel74)
                    .addComponent(bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iddenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tglby, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(nopeng, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(nsis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(nissis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(jdbk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(jnbk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        backdenda.setBackground(new java.awt.Color(204, 204, 204));
        backdenda.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        backdenda.setForeground(new java.awt.Color(51, 51, 51));
        backdenda.setText("Kembali");
        backdenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backdendaActionPerformed(evt);
            }
        });

        savedenda.setBackground(new java.awt.Color(204, 204, 204));
        savedenda.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        savedenda.setForeground(new java.awt.Color(51, 51, 51));
        savedenda.setText("Simpan");
        savedenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savedendaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pembayaranLayout = new javax.swing.GroupLayout(pembayaran);
        pembayaran.setLayout(pembayaranLayout);
        pembayaranLayout.setHorizontalGroup(
            pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pembayaranLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pembayaranLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(backdenda)
                        .addGap(18, 18, 18)
                        .addComponent(savedenda))
                    .addComponent(jLabel36)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pembayaranLayout.setVerticalGroup(
            pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pembayaranLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backdenda)
                    .addComponent(savedenda))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        getContentPane().add(pembayaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 1280, 770));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void datasiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datasiswaMouseClicked
        // TODO add your handling code here:
         tabdatasiswa.setVisible(true);
         tabdashboard.setVisible(false);
         tambahdatasiswa.setVisible(false);
         tabdatabuku.setVisible(false);
         tambahdatabuku.setVisible(false);
         editdatasiswa.setVisible(false);
         editdatabuku.setVisible(false);
         laporanpeminjam.setVisible(false);
         laporanpengembalian.setVisible(false);
         dendapeminjam.setVisible(false);
         pembayaran.setVisible(false);
         footer.setVisible(true);
         dts.setBackground(new Color(51,51,51));
         dsh.setBackground(new Color(102,102,102));
         dtb.setBackground(new Color(102,102,102));
         lpp.setBackground(new Color(102,102,102));
         lppg.setBackground(new Color(102,102,102));
          dd.setBackground(new Color(102,102,102));
        
    }//GEN-LAST:event_datasiswaMouseClicked

    private void dashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardMouseClicked
        // TODO add your handling code here:
         tabdashboard.setVisible(true);
         tabdatasiswa.setVisible(false);
         tabdatabuku.setVisible(false);
         tambahdatasiswa.setVisible(false);
         tambahdatabuku.setVisible(false);
         editdatasiswa.setVisible(false);
         editdatabuku.setVisible(false);
         laporanpeminjam.setVisible(false);
         laporanpengembalian.setVisible(false);
         dendapeminjam.setVisible(false);
         pembayaran.setVisible(false);
         footer.setVisible(true);
         dsh.setBackground(new Color(51,51,51));
         dts.setBackground(new Color(102,102,102));
         dtb.setBackground(new Color(102,102,102));
         lpp.setBackground(new Color(102,102,102));
         lppg.setBackground(new Color(102,102,102));
          dd.setBackground(new Color(102,102,102));
    }//GEN-LAST:event_dashboardMouseClicked

    private void tambahdtsisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahdtsisActionPerformed
        // TODO add your handling code here:
        tambahdatasiswa.setVisible(true);
        tabdatasiswa.setVisible(false);
        footer.setVisible(false);
        editdatasiswa.setVisible(false);
    }//GEN-LAST:event_tambahdtsisActionPerformed

    private void backsiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backsiswaActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Data yang telah terisi tidak akan disimpan");
        clearsiswa();
        tabdatasiswa.setVisible(true);
        tambahdatasiswa.setVisible(false);
        footer.setVisible(true);
    }//GEN-LAST:event_backsiswaActionPerformed

    private void lgoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lgoMouseClicked
        // TODO add your handling code here:
        new loginadmin().setVisible(true);
        dispose();
    }//GEN-LAST:event_lgoMouseClicked

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        // TODO add your handling code here:
        int simp = JOptionPane.showOptionDialog(this,"Apakah Data Sudah Benar?","Simpan Data",
                JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
         if(user.getText().equals("") || pass.getText().equals("") || konpass.getText().equals("") || idsiswa.getText().equals("") ||
                 namasiswa.getText().equals("") || nissiswa.getText().equals("") || jk.getSelection().equals("") || ting.getSelectedItem().equals("Default") || 
                 jur.getSelectedItem().equals("Default") || kel.getSelectedItem().equals("Default") || nope.getText().equals("") || 
                 alamat.getText().equals("") || ttl.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Masih Ada Data Yang Kosong");
        }else if(simp == JOptionPane.YES_OPTION){
            JOptionPane.showMessageDialog(null, "Data Berhasil Di Masukan");
            registrasi();
            inputsiswa();
            loadsiswa();
            clearsiswa();
            autoidsiswa();
            tambahdatasiswa.setVisible(false);
            tabdatasiswa.setVisible(true);
            footer.setVisible(true);
        }

    }//GEN-LAST:event_saveActionPerformed

    private void editsisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editsisActionPerformed
        // TODO add your handling code here:
        akun();
        int bar = tbldatasiswa.getSelectedRow();
        String a = tbldatasiswa.getValueAt(bar, 0).toString();
        String b = tbldatasiswa.getValueAt(bar, 1).toString();
        String c = tbldatasiswa.getValueAt(bar, 2).toString();
        String d = tbldatasiswa.getValueAt(bar, 3).toString();
        String e = tbldatasiswa.getValueAt(bar, 4).toString();
        String f = tbldatasiswa.getValueAt(bar, 5).toString();
        String g = tbldatasiswa.getValueAt(bar, 6).toString();
        String h = tbldatasiswa.getValueAt(bar, 7).toString();
        String i = tbldatasiswa.getValueAt(bar, 8).toString();
        String j = tbldatasiswa.getValueAt(bar, 9).toString();
        
        editidsiswa.setText(a); //id
        editnamasiswa.setText(b); //nis
        editnissiswa.setText(c); //nama
        if("L".equals(d)){ //kelamin
            editjkl.setSelected(true);
        }
        else{
            editjkp.setSelected(true);
        }
        editting.setSelectedItem(e); //combo tingkat
        editjur.setSelectedItem(f); //combo jurusan
        editkel.setSelectedItem(g); //combo kelas
        editnope.setText(h); //no hp
        editalamat.setText(i);
        editttl.setText(j);
        //sta.setSelectedItem(i); //status
        
        backeditsiswa.setEnabled(true);
        editsiswa.setEnabled(true);
        //del.setEnabled(true);
        
        
        editdatasiswa.setVisible(true);
        tabdatasiswa.setVisible(false);
        footer.setVisible(false);
    }//GEN-LAST:event_editsisActionPerformed

    private void backeditsiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backeditsiswaActionPerformed
        // TODO add your handling code here:
        tabdatasiswa.setVisible(true);
        editdatasiswa.setVisible(false);
        footer.setVisible(true);
    }//GEN-LAST:event_backeditsiswaActionPerformed

    private void editsiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editsiswaActionPerformed
        // TODO add your handling code here:
        int simp = JOptionPane.showOptionDialog(this,"Apakah Yakin Ingin Mengubah Data Tersebut?","Simpan Data",
                JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
        if(simp == JOptionPane.YES_OPTION){
            editregistrasi();
            editsiswa();
            loadsiswa();
            clearsiswa();
            autoidsiswa();
            editdatasiswa.setVisible(false);
            tabdatasiswa.setVisible(true);
            footer.setVisible(true);
        }
    }//GEN-LAST:event_editsiswaActionPerformed

    private void delsisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delsisActionPerformed
        // TODO add your handling code here:
        int bar = tbldatasiswa.getSelectedRow();
        String a = tbldatasiswa.getValueAt(bar, 0).toString();
        if( a.equals("")){
            JOptionPane.showMessageDialog(this, "adakdas");
        }
        else{
            int simp = JOptionPane.showOptionDialog(this,"Apakah Yakin Ingin Menghapus Data Tersebut?","Hapus Data",
                JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
        if(simp == JOptionPane.YES_OPTION){
            delsiswa();
            delregis();
            loadsiswa();
            clearsiswa();
            autoidsiswa();
        }
        }
        
    }//GEN-LAST:event_delsisActionPerformed

    private void databukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_databukuMouseClicked
        // TODO add your handling code here:
        tabdatabuku.setVisible(true);
        tabdashboard.setVisible(false);
        tabdatasiswa.setVisible(false);
        tambahdatasiswa.setVisible(false);
        editdatasiswa.setVisible(false);
        editdatabuku.setVisible(false);
        laporanpeminjam.setVisible(false);
        laporanpengembalian.setVisible(false);
        dendapeminjam.setVisible(false);
        pembayaran.setVisible(false);
        footer.setVisible(true);
        dtb.setBackground(new Color(51,51,51));
        dsh.setBackground(new Color(102,102,102));
        dts.setBackground(new Color(102,102,102));
        lpp.setBackground(new Color(102,102,102));
        lppg.setBackground(new Color(102,102,102));
         dd.setBackground(new Color(102,102,102));
        
    }//GEN-LAST:event_databukuMouseClicked

    private void tambahdtbkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahdtbkActionPerformed
        // TODO add your handling code here:
        tambahdatabuku.setVisible(true);
        tabdatabuku.setVisible(false);
        editdatasiswa.setVisible(false);
        footer.setVisible(false);
    }//GEN-LAST:event_tambahdtbkActionPerformed

    private void editbkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editbkActionPerformed
        // TODO add your handling code here:
        int bar = tbldatabuku.getSelectedRow();
        String a = tbldatabuku.getValueAt(bar, 0).toString();
        String b = tbldatabuku.getValueAt(bar, 1).toString();
        String c = tbldatabuku.getValueAt(bar, 2).toString();
        String d = tbldatabuku.getValueAt(bar, 3).toString();
        String e = tbldatabuku.getValueAt(bar, 4).toString();
        String f = tbldatabuku.getValueAt(bar, 5).toString();
        String g = tbldatabuku.getValueAt(bar, 6).toString();
        //String h = tbldatabuku.getValueAt(bar, 7).toString();
       

        editidbuku.setText(a); //id
        editjudulbuku.setText(b); //judul buku
        
        editjenisbuku.setSelectedItem(c); //combo jenis buku
       // editketbuku.setSelectedItem(d); //combo rak
        editpengbuku.setText(d); //keterangan
        
        editpenbuku.setText(e); //pengarang
        editrakbuku.setSelectedItem(f); //penerbit
        editstokbuku.setText(g); //stok
        ImageIcon sam = sampul();
        Image img = sam.getImage();
        Image newImg = img.getScaledInstance(editgambar.getWidth(), editgambar.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        editgambar.setIcon(image);
//        editsampul();
        
//        editgambar.setIcon(ResizeImages(null, getValueAt(tbldatabuku)));
//        editgambar.setIcon(h);

        editdatabuku.setVisible(true);
        tabdatabuku.setVisible(false);
        footer.setVisible(false);
        //input.setEnabled(false);
        editbuku.setEnabled(true);
        //del.setEnabled(true);

    }//GEN-LAST:event_editbkActionPerformed

    private void delbkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delbkActionPerformed
        // TODO add your handling code here:
        int bar = tbldatabuku.getSelectedRow();
        String a = tbldatabuku.getValueAt(bar, 0).toString();
        if( a.equals("")){
            JOptionPane.showMessageDialog(this, "adakdas");
        }
        else{
            int simp = JOptionPane.showOptionDialog(this,"Apakah Yakin Ingin Menghapus Data Tersebut?","Hapus Data",
                JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
        if(simp == JOptionPane.YES_OPTION){
            delbuku();
            loadbuku();
            clearbuku();
            autoidbuku();
        }
        }
    }//GEN-LAST:event_delbkActionPerformed

    private void backbukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backbukuActionPerformed
        // TODO add your handling code here:
        clearbuku();
        tabdatabuku.setVisible(true);
        tambahdatabuku.setVisible(false);
        editdatasiswa.setVisible(false);
        footer.setVisible(true);
    }//GEN-LAST:event_backbukuActionPerformed

    private void savebukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebukuActionPerformed
        // TODO add your handling code here:
        int simp = JOptionPane.showOptionDialog(this,"Apakah Data Sudah Benar?","Simpan Data",
                JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
        if(simp == JOptionPane.YES_OPTION){
            inputbuku();
            loadbuku();
            clearbuku();
            autoidbuku();
            tambahdatabuku.setVisible(false);
            tabdatabuku.setVisible(true);
            footer.setVisible(true);
        }
    }//GEN-LAST:event_savebukuActionPerformed

    private void backeditbukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backeditbukuActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Data yang telah diubah tidak akan terupdate");
        tabdatabuku.setVisible(true);
        footer.setVisible(true);
        editdatabuku.setVisible(false);
    }//GEN-LAST:event_backeditbukuActionPerformed

    private void editbukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editbukuActionPerformed
        // TODO add your handling code here:
         int simp = JOptionPane.showOptionDialog(this,"Apakah Yakin Ingin Mengubah Data Tersebut?","Simpan Data",
                JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
        if(simp == JOptionPane.YES_OPTION){
            editbuku();
            loadbuku();
            clearbuku();
            autoidbuku();
            editdatabuku.setVisible(false);
            tabdatabuku.setVisible(true);
            footer.setVisible(true);
        }
    }//GEN-LAST:event_editbukuActionPerformed

    private void lprpeminjamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lprpeminjamMouseClicked
        // TODO add your handling code here:
         laporanpeminjam.setVisible(true);
         tabdatasiswa.setVisible(false);
         tabdashboard.setVisible(false);
         tambahdatasiswa.setVisible(false);
         tabdatabuku.setVisible(false);
         tambahdatabuku.setVisible(false);
         editdatasiswa.setVisible(false);
         editdatabuku.setVisible(false);
         laporanpengembalian.setVisible(false);
         dendapeminjam.setVisible(false);
         pembayaran.setVisible(false);
         footer.setVisible(true);
         lpp.setBackground(new Color(51,51,51));
         dsh.setBackground(new Color(102,102,102));
         dtb.setBackground(new Color(102,102,102));
         dts.setBackground(new Color(102,102,102));
         lppg.setBackground(new Color(102,102,102));
          dd.setBackground(new Color(102,102,102));
    }//GEN-LAST:event_lprpeminjamMouseClicked

    private void lprpengembalianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lprpengembalianMouseClicked
        // TODO add your handling code here:
         laporanpengembalian.setVisible(true);
         laporanpeminjam.setVisible(false);
         tabdatasiswa.setVisible(false);
         tabdashboard.setVisible(false);
         tambahdatasiswa.setVisible(false);
         tabdatabuku.setVisible(false);
         tambahdatabuku.setVisible(false);
         editdatasiswa.setVisible(false);
         editdatabuku.setVisible(false);
         dendapeminjam.setVisible(false);
         pembayaran.setVisible(false);
         footer.setVisible(true);
         lppg.setBackground(new Color(51,51,51));
         lpp.setBackground(new Color(102,102,102));
         dsh.setBackground(new Color(102,102,102));
         dtb.setBackground(new Color(102,102,102));
         dts.setBackground(new Color(102,102,102));
         dd.setBackground(new Color(102,102,102));
    }//GEN-LAST:event_lprpengembalianMouseClicked

    private void dendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dendaMouseClicked
        // TODO add your handling code here:
         dendapeminjam.setVisible(true);
         laporanpengembalian.setVisible(false);
         laporanpeminjam.setVisible(false);
         tabdatasiswa.setVisible(false);
         tabdashboard.setVisible(false);
         tambahdatasiswa.setVisible(false);
         tabdatabuku.setVisible(false);
         tambahdatabuku.setVisible(false);
         editdatasiswa.setVisible(false);
         editdatabuku.setVisible(false);
         pembayaran.setVisible(false);
         footer.setVisible(true);
         dd.setBackground(new Color(51,51,51));
         lppg.setBackground(new Color(102,102,102));
         lpp.setBackground(new Color(102,102,102));
         dsh.setBackground(new Color(102,102,102));
         dtb.setBackground(new Color(102,102,102));
         dts.setBackground(new Color(102,102,102));
    }//GEN-LAST:event_dendaMouseClicked

    private void delpinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delpinActionPerformed
        // TODO add your handling code here:
        int bar = tbllaporpinjam.getSelectedRow();
        String a = tbllaporpinjam.getValueAt(bar,5).toString();
        x = a;
        sts();
        stokp();
        
        delpeminjam();
        loadlprpj();
    }//GEN-LAST:event_delpinActionPerformed

    private void cekpassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cekpassActionPerformed
        // TODO add your handling code here:
        if (cekpass.getText().equals("Show Password")){
            cekpass.setText("Hide Password");
            pass.setEchoChar((char)0);
        }
        else {
            cekpass.setText("Show Password");
            pass.setEchoChar('*');
        }
    }//GEN-LAST:event_cekpassActionPerformed

    private void cekkonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cekkonActionPerformed
        // TODO add your handling code here:
        if (cekkon.getText().equals("Show Password")){
            cekkon.setText("Hide Password");
            konpass.setEchoChar((char)0);
        }
        else {
            cekkon.setText("Show Password");
            konpass.setEchoChar('*');
        }
    }//GEN-LAST:event_cekkonActionPerformed

    private void editcekpassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editcekpassActionPerformed
        // TODO add your handling code here:
        if (editcekpass.getText().equals("Show Password")){
            editcekpass.setText("Hide Password");
            editpass.setEchoChar((char)0);
        }
        else {
            editcekpass.setText("Show Password");
            editpass.setEchoChar('*');
        }

    }//GEN-LAST:event_editcekpassActionPerformed

    private void editcekkonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editcekkonActionPerformed
        // TODO add your handling code here:
        if (editcekkon.getText().equals("Show Password")){
            editcekkon.setText("Hide Password");
            editkonpass.setEchoChar((char)0);
        }
        else {
            editcekkon.setText("Show Password");
            editkonpass.setEchoChar('*');
        }
    }//GEN-LAST:event_editcekkonActionPerformed

    private void chooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooserActionPerformed
        // TODO add your handling code here:
        
        //browse image
        JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.home")));
        //filter the files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images","jpg","gif","png");
        file.addChoosableFileFilter(filter);
        int result = file.showSaveDialog(null);
        //if the user click on save in Jfilechooser
        if(result == JFileChooser.APPROVE_OPTION){
            File selectedFile = file.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
            gambar.setIcon(ResizeImages(path,null));
            
            imgpath = path;
        }
        //if the user click save in Jfilechooser
        else if(result == JFileChooser.CANCEL_OPTION){
            System.out.println("No File Select");
        }
    }//GEN-LAST:event_chooserActionPerformed

    private void editchooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editchooserActionPerformed
        // TODO add your handling code here:
        //browse image
        JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.home")));
        //filter the files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images","jpg","gif","png");
        file.addChoosableFileFilter(filter);
        int result = file.showSaveDialog(null);
        //if the user click on save in Jfilechooser
        if(result == JFileChooser.APPROVE_OPTION){
            File selectedFile = file.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
            editgambar.setIcon(ResizeImages(path,null));
            
            imgpath = path;
        }
        //if the user click save in Jfilechooser
        else if(result == JFileChooser.CANCEL_OPTION){
            System.out.println("No File Select");
        }
    }//GEN-LAST:event_editchooserActionPerformed

    private void delkemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delkemActionPerformed
        // TODO add your handling code here:
        int bar = tbllaporkembali.getSelectedRow();
        String a = tbllaporkembali.getValueAt(bar,5).toString();
        x = a;
        stok();
        delkembali();
        loadlprkmb();
    }//GEN-LAST:event_delkemActionPerformed

    private void editdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editdenActionPerformed
        // TODO add your handling code here:
        pembayaran.setVisible(true);
        dendapeminjam.setVisible(false);
       
        footer.setVisible(false);
    }//GEN-LAST:event_editdenActionPerformed

    private void backdendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backdendaActionPerformed
        // TODO add your handling code here:
        clearby();
        dendapeminjam.setVisible(true);
        pembayaran.setVisible(false);
        footer.setVisible(true);
    }//GEN-LAST:event_backdendaActionPerformed

    private void savedendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savedendaActionPerformed
        // TODO add your handling code here:
        int simp = JOptionPane.showOptionDialog(this,"Apakah Data Sudah Benar?","Simpan Data",
                JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
        if(simp == JOptionPane.YES_OPTION){
            inputdenda();
            tru();
            loaddenda();
            clearby();
            autoiddenda();
            pembayaran.setVisible(false);
            dendapeminjam.setVisible(true);
            footer.setVisible(true);
        }
        
    }//GEN-LAST:event_savedendaActionPerformed

    private void nopengActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nopengActionPerformed
        // TODO add your handling code here:
        if(nopeng.getSelectedItem().equals("Default")){
            nsis.setText(null);
            nissis.setText(null);
            jdbk.setText(null);
            jnbk.setText(null);
         }
        else{
            nsw();
            nisw();
            jjd();
            jjn();
            hiden();
         }
    }//GEN-LAST:event_nopengActionPerformed

    private void alamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_alamatActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(menuadmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menuadmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menuadmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menuadmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new menuadmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel admin;
    private javax.swing.JTextField alamat;
    private javax.swing.JButton backbuku;
    private javax.swing.JButton backdenda;
    private javax.swing.JButton backeditbuku;
    private javax.swing.JButton backeditsiswa;
    private javax.swing.JButton backsiswa;
    private javax.swing.JTextField bayar;
    private javax.swing.JCheckBox cekkon;
    private javax.swing.JCheckBox cekpass;
    private javax.swing.JButton chooser;
    private javax.swing.JLabel dashboard;
    private javax.swing.JLabel databuku;
    private javax.swing.JLabel datasiswa;
    private javax.swing.JPanel dd;
    private javax.swing.JButton delbk;
    private javax.swing.JButton delkem;
    private javax.swing.JButton delpin;
    private javax.swing.JButton delsis;
    private javax.swing.JLabel denda;
    private javax.swing.JPanel dendapeminjam;
    private javax.swing.JPanel dsh;
    private javax.swing.JPanel dtb;
    private javax.swing.JPanel dts;
    private javax.swing.JTextField editalamat;
    private javax.swing.JButton editbk;
    private javax.swing.JButton editbuku;
    private javax.swing.JCheckBox editcekkon;
    private javax.swing.JCheckBox editcekpass;
    private javax.swing.JButton editchooser;
    private javax.swing.JPanel editdatabuku;
    private javax.swing.JPanel editdatasiswa;
    private javax.swing.JButton editden;
    private javax.swing.JLabel editgambar;
    private javax.swing.JTextField editidbuku;
    private javax.swing.JTextField editidsiswa;
    private javax.swing.JComboBox<String> editjenisbuku;
    private javax.swing.JRadioButton editjkl;
    private javax.swing.JRadioButton editjkp;
    private javax.swing.JTextField editjudulbuku;
    private javax.swing.JComboBox<String> editjur;
    private javax.swing.JComboBox<String> editkel;
    private javax.swing.JPasswordField editkonpass;
    private javax.swing.JTextField editnamasiswa;
    private javax.swing.JTextField editnissiswa;
    private javax.swing.JTextField editnope;
    private javax.swing.JPasswordField editpass;
    private javax.swing.JTextField editpenbuku;
    private javax.swing.JTextField editpengbuku;
    private javax.swing.JComboBox<String> editrakbuku;
    private javax.swing.JButton editsis;
    private javax.swing.JButton editsiswa;
    private javax.swing.JTextField editstokbuku;
    private javax.swing.JComboBox<String> editting;
    private javax.swing.JTextField editttl;
    private javax.swing.JTextField edituser;
    private javax.swing.JPanel footer;
    private javax.swing.JLabel gambar;
    private javax.swing.JPanel header;
    private javax.swing.JTextField idbuku;
    private javax.swing.JTextField iddenda;
    private javax.swing.JTextField idsiswa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jdbk;
    private javax.swing.JComboBox<String> jenisbuku;
    private javax.swing.ButtonGroup jk;
    private javax.swing.JRadioButton jkl;
    private javax.swing.JRadioButton jkp;
    private javax.swing.JTextField jnbk;
    private javax.swing.JTextField judulbuku;
    private javax.swing.JComboBox<String> jur;
    private javax.swing.JComboBox<String> kel;
    private javax.swing.JPasswordField konpass;
    private javax.swing.JPanel laporanpeminjam;
    private javax.swing.JPanel laporanpengembalian;
    private javax.swing.JLabel lgo;
    private javax.swing.JPanel logout;
    private javax.swing.JPanel lpp;
    private javax.swing.JPanel lppg;
    private javax.swing.JLabel lprpeminjam;
    private javax.swing.JLabel lprpengembalian;
    private javax.swing.JTextField namasiswa;
    private javax.swing.JTextField nissis;
    private javax.swing.JTextField nissiswa;
    private javax.swing.JTextField nope;
    private javax.swing.JComboBox<String> nopeng;
    private javax.swing.JTextField nsis;
    private javax.swing.JPasswordField pass;
    private javax.swing.JPanel pembayaran;
    private javax.swing.JTextField penbuku;
    private javax.swing.JTextField pengbuku;
    private javax.swing.JComboBox<String> rakbuku;
    private javax.swing.JButton save;
    private javax.swing.JButton savebuku;
    private javax.swing.JButton savedenda;
    private javax.swing.JPanel sidebar;
    private javax.swing.JTextField stokbuku;
    private javax.swing.JPanel tabdashboard;
    private javax.swing.JPanel tabdatabuku;
    private javax.swing.JPanel tabdatasiswa;
    private javax.swing.JPanel tambahdatabuku;
    private javax.swing.JPanel tambahdatasiswa;
    private javax.swing.JButton tambahdtbk;
    private javax.swing.JButton tambahdtsis;
    private javax.swing.JTable tbldatabuku;
    private javax.swing.JTable tbldatasiswa;
    private javax.swing.JTable tbldenda;
    private javax.swing.JTable tbllaporkembali;
    private javax.swing.JTable tbllaporpinjam;
    private javax.swing.JTextField tglby;
    private javax.swing.JComboBox<String> ting;
    private javax.swing.JTextField ttl;
    private javax.swing.JTextField user;
    // End of variables declaration//GEN-END:variables

}

