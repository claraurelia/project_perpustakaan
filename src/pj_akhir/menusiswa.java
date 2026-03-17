/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pj_akhir;

import com.mysql.cj.jdbc.Blob;
import java.awt.Color;
import java.awt.Image;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
import javax.swing.JOptionPane;
import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
//import java.text.DateFormat;
//import javax.swing.DefaultListModel;
//import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
//import java.util.Calendar;
import java.util.Date;
//import javax.swing.Icon;
import javax.swing.ImageIcon;
//import java.util.Calendar;



public class menusiswa extends javax.swing.JFrame {

    /**
     * Creates new form menusiswa
     */
    public menusiswa() {
        initComponents();
        tabdashboard.setVisible(true);
        dsh.setBackground(new Color(51,51,51));
        tablistbook.setVisible(false);
        tabpeminjamanbuku.setVisible(false);
        tabpengembalianbuku.setVisible(false);
        list();
        combojb();
//        autonopinjam();
        tglp();
//        coni();
       comb();
        //gambar();
        tglk();
        comtr();
    }
    
    public String kelamin = null;
    
    public menusiswa(String nis, String ids, String sis, String us, String kel) {
        initComponents();  
        tabdashboard.setVisible(true);
        dsh.setBackground(new Color(51,51,51));
        tablistbook.setVisible(false);
        tabpeminjamanbuku.setVisible(false);
        tabpengembalianbuku.setVisible(false);
        list();
        autonopinjam();
        autonokembali();
        tglp();
        comb();
        combojb();
        kelamin = kel;
        gambar();
        tglk();
        textNis.setText(nis);
        textIds.setText(ids);
        textSis.setText(sis);
        textUs.setText(us);
        comtr();
        try{           
            Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String sql_tingkat = "SELECT * FROM tbl_datasiswa WHERE id_siswa='" + textIds.getText() + "'";
            ResultSet rs = st.executeQuery(sql_tingkat);
            while (rs.next()){                   
                napro.setText(rs.getString("nama_siswa"));
                idpro.setText(rs.getString("id_siswa"));
                nispro.setText(rs.getString("nis_siswa"));
                jepro.setText(rs.getString("jk_siswa"));
                tipro.setText(rs.getString("tingkat_siswa"));
                jupro.setText(rs.getString("jurusan_siswa"));
                kepro.setText(rs.getString("kelas_siswa"));
                nopro.setText(rs.getString("nope_siswa"));
                apro.setText(rs.getString("alamat"));
                tpro.setText(rs.getString("TTL"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    String imgpath = null;
    public String x = "";
    public String dead = null;
    public long sel = 0;
    public int dend = 0;
    
    
    //informasi buku------------------------------------------------------------
    public void infobuku(){
        try{

            Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String sql_tingkat = "SELECT * FROM tbl_databuku WHERE judul_buku='"+dtbuku.getSelectedItem()+"'";
            ResultSet rs = st.executeQuery(sql_tingkat);
            while (rs.next()){
                if(imgpath != null);        
                idbu.setText(rs.getString("id_buku"));
                jdbu.setText(rs.getString("judul_buku"));
                jebu.setText(rs.getString("jenis_buku"));
                pebu.setText(rs.getString("pengarang_buku"));
                penbu.setText(rs.getString("penerbit_buku"));
                rabu.setText(rs.getString("rak_buku"));
                stbu.setText(rs.getString("stok_buku"));
                ImageIcon sam = sampul();
                Image img = sam.getImage();
                Image newImg = img.getScaledInstance(gambar.getWidth(), gambar.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon image = new ImageIcon(newImg);
                gambar.setIcon(image);

            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e);
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
                jenis.addItem(rs.getString("jenis_buku"));
                
            }
            rs.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
//tampilan gambar----------------------------------------------------------
     public ImageIcon sampul(){
         ImageIcon tampilkan = null;
         try{
             Connection kon = (Connection) koneksi.koneksiDb();
             Statement st = (Statement) kon.createStatement();
             String tampil = "SELECT * FROM tbl_databuku WHERE judul_buku='" + dtbuku.getSelectedItem() + "';";
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

    //clear informasi buku------------------------------------------------------
    public void clearinfo(){
        try{
            idbu.setText("Default");
            jdbu.setText("Default");
            jebu.setText("Default");
            pebu.setText("Default");
            penbu.setText("Default");
            rabu.setText("Default");
            stbu.setText("Default");
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    //no.pinjam-----------------------------------------------------------------
    private void autonopinjam(){
        try{
            Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String sql_id = "SELECT * FROM tbl_peminjam order by no_peminjam desc";
            ResultSet rs = st.executeQuery(sql_id);
            if(rs.next()){
                String id_siswa = rs.getString("no_peminjam").substring(1);
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
                nopin.setText("P" + Nol + AN);
            }
            else {
                nopin.setText("P00001");
            }
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //no.kembali-----------------------------------------------------------------
    private void autonokembali(){
        try{
            Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String sql_id = "SELECT * FROM tbl_kembali order by no_kembali desc";
            ResultSet rs = st.executeQuery(sql_id);
            if(rs.next()){
                String id_siswa = rs.getString("no_kembali").substring(1);
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
                nokb.setText("K" + Nol + AN);
            }
            else {
                nokb.setText("K00001");
            }
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void gambar(){
        switch (kelamin){
                case "P":
                    usp.setVisible(true);
                    usl.setVisible(false);
                    break;
                case "L":
                    usp.setVisible(false);
                    usl.setVisible(true);
                    break;
                default:
                    JOptionPane.showMessageDialog(this,"ADA YANG ANEH");
        }
    }
    
    //tanggal pinjam------------------------------------------------------------
    public void tglp(){
        Date tgp = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        tglp.setText(f.format(tgp));     
    }
    
    //tanggal kebali------------------------------------------------------------
    public void tglk(){
        Date tgp = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        tglk.setText(f.format(tgp));     
    }
    
    //combo box judul buku------------------------------------------------------
    private void comb(){
        try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String sql_buku = "SELECT * FROM tbl_databuku";
            ResultSet rs = st.executeQuery(sql_buku);
            while(rs.next()){
                jdb.addItem(rs.getString("judul_buku"));
            }
            rs.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //id buku-------------------------------------------------------------------
    private void nmb(){
        try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String jlb = "SELECT id_buku FROM tbl_databuku WHERE judul_buku='"+jdb.getSelectedItem()+"'";
            ResultSet rs = st.executeQuery(jlb);
            while(rs.next()){
                idb.setText(rs.getString("id_buku"));
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //jenis buku----------------------------------------------------------------
    private void nmj(){
        try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String jnb = "SELECT jenis_buku FROM tbl_databuku WHERE judul_buku='"+jdb.getSelectedItem()+"'";
            ResultSet rs = st.executeQuery(jnb);
            while(rs.next()){
                jeb.setText(rs.getString("jenis_buku"));
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
     //mengurangi stok buku-----------------------------------------------------
    private void stok(){
        try{
             java.sql.Connection kon = koneksi.koneksiDb();
             Statement st = kon.createStatement();
             String ank = "SELECT stok_buku FROM tbl_databuku WHERE id_buku='" + idb.getText() + "'";
             ResultSet rs = st.executeQuery(ank);
             String c = "";
             while(rs.next()){
                 String a  = rs.getString("stok_buku");
                 int b = Integer.valueOf(a);
                 b--;
                 c = String.valueOf(b);
             }
             String up = "UPDATE tbl_databuku SET stok_buku='" +  c + "' WHERE id_buku='" + idb.getText() + "'";
             st.execute(up);
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
             String ank = "SELECT stok_buku FROM tbl_databuku WHERE id_buku='" + textidb.getText() + "'";
             ResultSet rs = st.executeQuery(ank);
             String c = "";
             while(rs.next()){
                 String a  = rs.getString("stok_buku");
                 int b = Integer.valueOf(a);
                 b++;
                 c = String.valueOf(b);
             }
             String up = "UPDATE tbl_databuku SET stok_buku='" +  c + "' WHERE id_buku='" + textidb.getText() + "'";
             st.execute(up);
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
     }
    //input data pinjam---------------------------------------------------------
    private void inp(){
        try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            
            String sql = "INSERT INTO tbl_peminjam values ('" + nopin.getText() + "','" + tglp.getText() + "','"
                     + textIds.getText() + "','" + textNis.getText() + "','" + textSis.getText() + "','"
                     + idb.getText() + "','" + jdb.getSelectedItem() + "','" + jeb.getText() + "','false')";
             st.execute(sql);
             JOptionPane.showMessageDialog(null, "Data Berhasil Di Masukan");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //input data kembali---------------------------------------------------------
    private void ink(){
        try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            
            String sql = "INSERT INTO tbl_kembali values ('" + nokb.getText() + "','" + tglk.getText() + "','"
                     + textids.getText() + "','" + textnis.getText() + "','" + textsis.getText() + "','"
                     + textidb.getText() + "','" + jd.getSelectedItem() + "','" + textjnb.getText() + "','" + sel + "','false')";
             st.execute(sql);
             JOptionPane.showMessageDialog(null, "Data Berhasil Di Masukan");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //cek stok------------------------------------------------------------------
     public void cesto(){
         try{
             
             java.sql.Connection kon = koneksi.koneksiDb();
             Statement st = kon.createStatement();
             String ank = "SELECT stok_buku FROM tbl_databuku WHERE id_buku='" + idb.getText() + "'";
             ResultSet rs = st.executeQuery(ank);
             while (rs.next()){
                String a = rs.getString("stok_buku");
                switch(a){
                    case"0":
                        JOptionPane.showMessageDialog(this, "Maaf Stok Buku ini habis!!!");
                        break;
                    default:
                        int simp = JOptionPane.showOptionDialog(this,"Apakah Data Sudah Benar?","Simpan Data",
                        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
                        if(simp == JOptionPane.YES_OPTION){
                        ss();        
                        //load();
                        clear();
                        autonopinjam();
                        break;
                    }
                }
             }
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
     }
     
     //cek stok------------------------------------------------------------------
     public void cestk(){
         try{
             java.sql.Connection kon = koneksi.koneksiDb();
             Statement st = kon.createStatement();
             String ank = "SELECT stok_buku FROM tbl_databuku WHERE id_buku='" + textidb.getText() + "'";
             ResultSet rs = st.executeQuery(ank);
             while (rs.next()){
                String a = rs.getString("stok_buku");
                switch(a){
                    case"0":
                        JOptionPane.showMessageDialog(this, "tes!!!");
                        break;
                    default:
                        int simp = JOptionPane.showOptionDialog(this,"Apakah Data Sudah Benar?","Simpan Data",
                        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
                        if(simp == JOptionPane.YES_OPTION){
                        ink();
                        stokp();
                        //load();
                        autonokembali();
                        clear2();
                    }
                }
             }
         }
         catch(Exception e){
//             JOptionPane.showMessageDialog(null, e);
         }
     }
     
     //clear--------------------------------------------------------------------
     public void clear(){
         jdb.setSelectedItem("<Default>");
     }
     
     //clear2-------------------------------------------------------------------
     public void clear2(){
//         int i = 1;
        
         jd.removeItem(jd.getSelectedItem());
//         int a = nopj.getItemCount()-1;
//         while(i<=a){
//             nopj.removeItemAt(i);
//             i++;
//         }  
         
         
         jd.setSelectedItem("<Default>");
//                tst.setText("ddsdsf");
//         comtr();
     }
     
     public void tpbook(){
         try{
             if(jenis.getSelectedItem().equals("Default")){
                dtbuku.setEnabled(false);
                dtbuku.removeAllItems();
                dtbuku.addItem("Default");
             }else{
                java.sql.Connection kon = koneksi.koneksiDb();
                Statement st = kon.createStatement();
                String ank = "SELECT * FROM tbl_databuku WHERE jenis_buku='" + jenis.getSelectedItem() + "'";
                ResultSet rs = st.executeQuery(ank);
                dtbuku.setEnabled(false);
                dtbuku.removeAllItems();
                dtbuku.addItem("Default");
                while(rs.next()){
//                    dtbuku.removeAllItems();
//                    dtbuku.addItem("Default");
                    dtbuku.addItem(rs.getString("judul_buku"));
                    dtbuku.setEnabled(true);
                }
             }
         } catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
     }
     
     //combo box id kembali-----------------------------------------------------
    private void comtr(){
        try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String sql_buku = "SELECT * FROM tbl_peminjam WHERE nis_pinjam ='" + textNis.getText() + "' and ket_pinjam='false'";
            ResultSet rs = st.executeQuery(sql_buku);
//            nopj.addItem("<Default>");
            while(rs.next()){
                jd.addItem(rs.getString("judulbk_pinjam"));
            }
            rs.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //judul buku----------------------------------------------------------------
    private void nis(){
        try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String jlb = "SELECT * FROM tbl_peminjam WHERE judulbk_pinjam='"+jd.getSelectedItem()+"' and nama_pinjam='" + textSis.getText() + "'";
            ResultSet rs = st.executeQuery(jlb);
            while(rs.next()){
                textnis.setText(rs.getString("nis_pinjam"));
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
     private void ids (){
        try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String jlb = "SELECT id_sispinjam FROM tbl_peminjam WHERE judulbk_pinjam='"+jd.getSelectedItem()+"'and nama_pinjam='" + textSis.getText() + "'";
            ResultSet rs = st.executeQuery(jlb);
            while(rs.next()){
                textids.setText(rs.getString("id_sispinjam"));
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
     
     private void nms (){
        try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String jlb = "SELECT nama_pinjam FROM tbl_peminjam WHERE judulbk_pinjam='"+jd.getSelectedItem()+"'and nama_pinjam='" + textSis.getText() + "'";
            ResultSet rs = st.executeQuery(jlb);
            while(rs.next()){
                textsis.setText(rs.getString("nama_pinjam"));
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
     
     private void idb(){
        try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String jlb = "SELECT id_bkpinjam FROM tbl_peminjam WHERE judulbk_pinjam='"+jd.getSelectedItem()+"'";
            ResultSet rs = st.executeQuery(jlb);
            while(rs.next()){
                textidb.setText(rs.getString("id_bkpinjam"));
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
     
     private void jdb(){
        try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String jlb = "SELECT no_peminjam FROM tbl_peminjam WHERE judulbk_pinjam='"+jd.getSelectedItem()+"'";
            ResultSet rs = st.executeQuery(jlb);
            while(rs.next()){
                nopj.setText(rs.getString("no_peminjam"));
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
     
     private void jnb(){
        try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String jlb = "SELECT jenisbk_pinjam FROM tbl_peminjam WHERE judulbk_pinjam='"+jd.getSelectedItem()+"'";
            ResultSet rs = st.executeQuery(jlb);
            while(rs.next()){
                textjnb.setText(rs.getString("jenisbk_pinjam"));
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
     
     //rubah ket_pinjam ke true-------------------------------------------------
     
     public void tru(){
         try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String up = "UPDATE tbl_peminjam SET ket_pinjam='true' WHERE judulbk_pinjam='" + jd.getSelectedItem() + "'";
            st.execute(up);
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
     }
     
     //nambah deadline----------------------------------------------------------
     
     public void dead(){
         try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String up = "SELECT tgl_pinjam FROM tbl_peminjam WHERE no_peminjam='" + nopj.getText() + "'";
            ResultSet rs = st.executeQuery(up);
            if(rs.next()){
                DateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
                Date dtb = fr.parse(rs.getString("tgl_pinjam"));
                Calendar cal = Calendar.getInstance();
                cal.setTime(dtb);
                cal.add(Calendar.DAY_OF_MONTH, 7);
                dead  = fr.format(cal.getTime());
            }
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
     }
     
     //ambil selisih tanggal----------------------------------------------------
     
     public void sel(){
         try{
            java.sql.Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String up = "SELECT tgl_pinjam FROM tbl_peminjam WHERE judulbk_pinjam='" + jd.getSelectedItem() + "'";
            ResultSet rs = st.executeQuery(up);
            if(rs.next()){
                SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
                String tbl = fr.format(new Date());
                Date tgl1 = fr.parse(tbl);
                Date tgl2 = fr.parse(dead);

                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.setTime(tgl1);
                cal2.setTime(tgl2);
                
                if(cal1.before(cal2)){
                    Calendar tgl = (Calendar) cal1.clone();
                    while(tgl.before(cal2)){
                        tgl.add(Calendar.DAY_OF_MONTH, 1);
//                        sel++;
                    }
                }else{
                    Calendar tgl = (Calendar) cal2.clone();
                    while(tgl.before(cal1)){
                        tgl.add(Calendar.DAY_OF_MONTH, 1);
                        sel++;
                    }
                }
            }
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
     }
     
     //hitung denda-------------------------------------------------------------
     
     public void denda(){
         int temp = Integer.parseInt(String.valueOf(sel)) * 1000;
         dend = temp;
     }
     
     //mengurangi stok siswa----------------------------------------------------
     public void ss(){
         try{
             java.sql.Connection kon = koneksi.koneksiDb();
             Statement st = kon.createStatement();
             String ank = "SELECT stok_siswa FROM tbl_datasiswa WHERE id_siswa='" + textIds.getText() + "'";
             ResultSet rs = st.executeQuery(ank);
             String c = "";
             if(rs.next()){
                 String a  = rs.getString("stok_siswa");
                 if(a.equals("0")){
                     JOptionPane.showMessageDialog(this, "maaf sesi anda sudah berakhir");
                 }else{
                    int b = Integer.valueOf(a);
                    b--;
                    c = String.valueOf(b);
                    String up = "UPDATE tbl_datasiswa SET stok_siswa='" +  c + "' WHERE id_siswa='" + textIds.getText() + "'";
                    st.execute(up);
                    inp();
                    stok();
                 }
             }
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
     }
     
     //menambah stok siswa------------------------------------------------------
     public void sts(){
         try{
             java.sql.Connection kon = koneksi.koneksiDb();
             Statement st = kon.createStatement();
             String ank = "SELECT stok_siswa FROM tbl_datasiswa WHERE id_siswa='" + textIds.getText() + "'";
             ResultSet rs = st.executeQuery(ank);
             String c = "";
             while(rs.next()){
                 String a  = rs.getString("stok_siswa");
                 int b = Integer.valueOf(a);
                 b++;
                 c = String.valueOf(b);
             }
             String up = "UPDATE tbl_datasiswa SET stok_siswa='" +  c + "' WHERE id_siswa='" + textIds.getText() + "'";
             st.execute(up);
         }
         catch(Exception e){
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

        sidebar = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        usp = new javax.swing.JPanel();
        per = new javax.swing.JLabel();
        usl = new javax.swing.JPanel();
        laki = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        dsh = new javax.swing.JPanel();
        dashboard = new javax.swing.JLabel();
        list = new javax.swing.JPanel();
        listbook = new javax.swing.JLabel();
        logout = new javax.swing.JPanel();
        lgo = new javax.swing.JLabel();
        pmj = new javax.swing.JPanel();
        peminjaman = new javax.swing.JLabel();
        pgm = new javax.swing.JPanel();
        pengembalian = new javax.swing.JLabel();
        textUs = new javax.swing.JLabel();
        header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        footer = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        tabdashboard = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        napro = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        idpro = new javax.swing.JLabel();
        kepro = new javax.swing.JLabel();
        nispro = new javax.swing.JLabel();
        jepro = new javax.swing.JLabel();
        tipro = new javax.swing.JLabel();
        jupro = new javax.swing.JLabel();
        apro = new javax.swing.JLabel();
        nopro = new javax.swing.JLabel();
        tpro = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        tablistbook = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        show = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jdbu = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jebu = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        pebu = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        penbu = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        stbu = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        rabu = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        idbu = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        gambar = new javax.swing.JLabel();
        jenis = new javax.swing.JComboBox<>();
        dtbuku = new javax.swing.JComboBox<>();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        tabpeminjamanbuku = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        nopin = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tglp = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jdb = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jeb = new javax.swing.JTextField();
        textNis = new javax.swing.JTextField();
        textIds = new javax.swing.JTextField();
        textSis = new javax.swing.JTextField();
        idb = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        pinjam = new javax.swing.JButton();
        tabpengembalianbuku = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        textidb = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        tglk = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        textjnb = new javax.swing.JTextField();
        textnis = new javax.swing.JTextField();
        textids = new javax.swing.JTextField();
        textsis = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        nokb = new javax.swing.JTextField();
        jd = new javax.swing.JComboBox<>();
        nopj = new javax.swing.JTextField();
        kembali = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sidebar.setBackground(new java.awt.Color(102, 102, 102));

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));

        usp.setBackground(new java.awt.Color(102, 102, 102));
        usp.setMaximumSize(new java.awt.Dimension(160, 160));
        usp.setMinimumSize(new java.awt.Dimension(160, 160));
        usp.setPreferredSize(new java.awt.Dimension(160, 160));

        per.setBackground(new java.awt.Color(102, 102, 102));
        per.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_Female_User_150px.png"))); // NOI18N

        javax.swing.GroupLayout uspLayout = new javax.swing.GroupLayout(usp);
        usp.setLayout(uspLayout);
        uspLayout.setHorizontalGroup(
            uspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uspLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(per)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        uspLayout.setVerticalGroup(
            uspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uspLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(per)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        usl.setBackground(new java.awt.Color(102, 102, 102));
        usl.setMaximumSize(new java.awt.Dimension(160, 160));
        usl.setMinimumSize(new java.awt.Dimension(160, 160));
        usl.setPreferredSize(new java.awt.Dimension(160, 160));

        laki.setBackground(new java.awt.Color(102, 102, 102));
        laki.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_user_150px.png"))); // NOI18N

        javax.swing.GroupLayout uslLayout = new javax.swing.GroupLayout(usl);
        usl.setLayout(uslLayout);
        uslLayout.setHorizontalGroup(
            uslLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uslLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(laki, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        uslLayout.setVerticalGroup(
            uslLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, uslLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(laki)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(usl, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usp, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 23, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        dsh.setBackground(new java.awt.Color(102, 102, 102));
        dsh.setPreferredSize(new java.awt.Dimension(248, 50));

        dashboard.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dashboard.setForeground(new java.awt.Color(255, 255, 255));
        dashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_content_40px.png"))); // NOI18N
        dashboard.setText(" Profile");
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
                .addGap(12, 12, 12)
                .addComponent(dashboard)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dshLayout.setVerticalGroup(
            dshLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dshLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        list.setBackground(new java.awt.Color(102, 102, 102));
        list.setPreferredSize(new java.awt.Dimension(248, 50));

        listbook.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        listbook.setForeground(new java.awt.Color(255, 255, 255));
        listbook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_book_shelf_50px.png"))); // NOI18N
        listbook.setText("List Book");
        listbook.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listbookMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout listLayout = new javax.swing.GroupLayout(list);
        list.setLayout(listLayout);
        listLayout.setHorizontalGroup(
            listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(listbook)
                .addContainerGap(102, Short.MAX_VALUE))
        );
        listLayout.setVerticalGroup(
            listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(listbook, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        logout.setBackground(new java.awt.Color(102, 102, 102));
        logout.setPreferredSize(new java.awt.Dimension(248, 50));

        lgo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lgo.setForeground(new java.awt.Color(255, 255, 255));
        lgo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_exit_50px_5.png"))); // NOI18N
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
                .addGap(8, 8, 8)
                .addComponent(lgo)
                .addContainerGap(115, Short.MAX_VALUE))
        );
        logoutLayout.setVerticalGroup(
            logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lgo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pmj.setBackground(new java.awt.Color(102, 102, 102));
        pmj.setPreferredSize(new java.awt.Dimension(248, 50));

        peminjaman.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        peminjaman.setForeground(new java.awt.Color(255, 255, 255));
        peminjaman.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_borrow_book_50px.png"))); // NOI18N
        peminjaman.setText("Peminjaman");
        peminjaman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                peminjamanMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pmjLayout = new javax.swing.GroupLayout(pmj);
        pmj.setLayout(pmjLayout);
        pmjLayout.setHorizontalGroup(
            pmjLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pmjLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(peminjaman)
                .addContainerGap(75, Short.MAX_VALUE))
        );
        pmjLayout.setVerticalGroup(
            pmjLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(peminjaman, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pgm.setBackground(new java.awt.Color(102, 102, 102));
        pgm.setPreferredSize(new java.awt.Dimension(248, 50));

        pengembalian.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        pengembalian.setForeground(new java.awt.Color(255, 255, 255));
        pengembalian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_return_book_50px.png"))); // NOI18N
        pengembalian.setText("Pengembalian");
        pengembalian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pengembalianMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pgmLayout = new javax.swing.GroupLayout(pgm);
        pgm.setLayout(pgmLayout);
        pgmLayout.setHorizontalGroup(
            pgmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pgmLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pengembalian)
                .addGap(77, 77, 77))
        );
        pgmLayout.setVerticalGroup(
            pgmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pengembalian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        textUs.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        textUs.setForeground(new java.awt.Color(255, 255, 255));
        textUs.setText("Default");

        javax.swing.GroupLayout sidebarLayout = new javax.swing.GroupLayout(sidebar);
        sidebar.setLayout(sidebarLayout);
        sidebarLayout.setHorizontalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarLayout.createSequentialGroup()
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sidebarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dsh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                            .addComponent(jSeparator1)
                            .addGroup(sidebarLayout.createSequentialGroup()
                                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pmj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(logout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pgm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(sidebarLayout.createSequentialGroup()
                        .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(sidebarLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(list, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(sidebarLayout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addComponent(textUs)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        sidebarLayout.setVerticalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textUs, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(dsh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(list, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pmj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pgm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(logout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(314, Short.MAX_VALUE))
        );

        getContentPane().add(sidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 870));

        header.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SMK Nusa Bangsa");

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(265, 265, 265)
                .addComponent(jLabel1)
                .addContainerGap(584, Short.MAX_VALUE))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        getContentPane().add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 1280, 100));

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

        getContentPane().add(footer, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 780, 1280, 90));

        tabdashboard.setBackground(new java.awt.Color(153, 255, 255));

        jPanel8.setBackground(new java.awt.Color(51, 51, 255));

        napro.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        napro.setForeground(new java.awt.Color(255, 255, 255));
        napro.setText("Default");

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel50.setForeground(java.awt.Color.white);
        jLabel50.setText("Id Siswa");

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel51.setForeground(java.awt.Color.white);
        jLabel51.setText("NIS ");

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel52.setForeground(java.awt.Color.white);
        jLabel52.setText("Jenis Kelamin");

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel53.setForeground(java.awt.Color.white);
        jLabel53.setText("Tingkat");

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel54.setForeground(java.awt.Color.white);
        jLabel54.setText("No HP");

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel55.setForeground(java.awt.Color.white);
        jLabel55.setText("Jurusan");

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel56.setForeground(java.awt.Color.white);
        jLabel56.setText("Kelas");

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel57.setForeground(java.awt.Color.white);
        jLabel57.setText("Alamat");

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel58.setForeground(java.awt.Color.white);
        jLabel58.setText("TTL");

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Nama");

        idpro.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        idpro.setForeground(java.awt.Color.white);
        idpro.setText("Default");

        kepro.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        kepro.setForeground(java.awt.Color.white);
        kepro.setText("Default");

        nispro.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        nispro.setForeground(java.awt.Color.white);
        nispro.setText("Default");

        jepro.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jepro.setForeground(java.awt.Color.white);
        jepro.setText("Default");

        tipro.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tipro.setForeground(java.awt.Color.white);
        tipro.setText("Default");

        jupro.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jupro.setForeground(java.awt.Color.white);
        jupro.setText("Default");

        apro.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        apro.setForeground(java.awt.Color.white);
        apro.setText("Default");

        nopro.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        nopro.setForeground(java.awt.Color.white);
        nopro.setText("Default");

        tpro.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tpro.setForeground(java.awt.Color.white);
        tpro.setText("Default");

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Biodata Siswa");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(napro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(idpro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nispro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jepro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tipro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jupro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(kepro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nopro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(apro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tpro, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(113, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addGap(31, 31, 31)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(napro)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(idpro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(nispro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(jepro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(tipro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(jupro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(kepro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(nopro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(apro))
                .addGap(12, 12, 12)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(tpro))
                .addContainerGap(72, Short.MAX_VALUE))
        );

        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Desain tanpa judul icon.png"))); // NOI18N

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Desain tanpa judul icon.png"))); // NOI18N

        javax.swing.GroupLayout tabdashboardLayout = new javax.swing.GroupLayout(tabdashboard);
        tabdashboard.setLayout(tabdashboardLayout);
        tabdashboardLayout.setHorizontalGroup(
            tabdashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabdashboardLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel41)
                .addGap(70, 70, 70)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86)
                .addComponent(jLabel40)
                .addContainerGap(151, Short.MAX_VALUE))
        );
        tabdashboardLayout.setVerticalGroup(
            tabdashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabdashboardLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(tabdashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addContainerGap(147, Short.MAX_VALUE))
        );

        getContentPane().add(tabdashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 1280, 680));

        tablistbook.setBackground(new java.awt.Color(153, 255, 255));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setText("List Book");

        show.setBackground(new java.awt.Color(204, 51, 255));
        show.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        show.setForeground(new java.awt.Color(255, 255, 255));
        show.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_Show_Property_30px_2.png"))); // NOI18N
        show.setText("Show");
        show.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 204, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Judul Buku:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, -1, -1));

        jdbu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jdbu.setText("Default");
        jPanel1.add(jdbu, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("Jenis Buku :");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, -1, -1));

        jebu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jebu.setText("Default");
        jPanel1.add(jebu, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("Pengarang :");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, -1, -1));

        pebu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        pebu.setText("Default");
        jPanel1.add(pebu, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 240, -1, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setText("Penerbit :");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 290, -1, -1));

        penbu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        penbu.setText("Default");
        jPanel1.add(penbu, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 290, -1, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel16.setText("Stok :");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 390, -1, -1));

        stbu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        stbu.setText("Default");
        jPanel1.add(stbu, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 390, -1, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel18.setText("Rak :");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, -1, -1));

        rabu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        rabu.setText("Default");
        jPanel1.add(rabu, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 340, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setText("Informasi Buku");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 191, -1));

        idbu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        idbu.setText("Default");
        jPanel1.add(idbu, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, -1, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel20.setText("Judul Buku:");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gambar, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gambar, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .addContainerGap())
        );

        jenis.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jenis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Default" }));
        jenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jenisActionPerformed(evt);
            }
        });

        dtbuku.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dtbuku.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Default" }));
        dtbuku.setEnabled(false);
        dtbuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dtbukuActionPerformed(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel38.setText("Jenis");

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel39.setText("Judul Buku");

        javax.swing.GroupLayout tablistbookLayout = new javax.swing.GroupLayout(tablistbook);
        tablistbook.setLayout(tablistbookLayout);
        tablistbookLayout.setHorizontalGroup(
            tablistbookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablistbookLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tablistbookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(tablistbookLayout.createSequentialGroup()
                        .addGroup(tablistbookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(show, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jenis, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dtbuku, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38)
                            .addComponent(jLabel39))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(155, Short.MAX_VALUE))
        );
        tablistbookLayout.setVerticalGroup(
            tablistbookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablistbookLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tablistbookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tablistbookLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dtbuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(144, 144, 144)
                        .addComponent(show, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(155, Short.MAX_VALUE))
        );

        getContentPane().add(tablistbook, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 1280, 680));

        tabpeminjamanbuku.setBackground(new java.awt.Color(153, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("Peminjaman Buku");

        jPanel2.setBackground(new java.awt.Color(51, 51, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(0, 39));

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_attention_40px.png"))); // NOI18N

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Tolong, diperhatikan dalam pengisian data peminjaman, terima kasih");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25)
                .addContainerGap(380, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addGap(18, 18, 18))
        );

        jPanel3.setBackground(new java.awt.Color(245, 245, 245));
        jPanel3.setPreferredSize(new java.awt.Dimension(1097, 401));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("No. Peminjam");

        nopin.setEditable(false);
        nopin.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setText("Tanggal Peminjam");

        tglp.setEditable(false);
        tglp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setText("Siswa");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setText("NIS");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel19.setText("Id Siswa");

        jdb.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jdb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Default>" }));
        jdb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jdbActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel22.setText("Judul Buku");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel23.setText("Jenis Buku");

        jeb.setEditable(false);
        jeb.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        textNis.setEditable(false);
        textNis.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        textIds.setEditable(false);
        textIds.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        textSis.setEditable(false);
        textSis.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        idb.setEditable(false);
        idb.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel21.setText("Id Buku");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel8)
                        .addGap(62, 62, 62)
                        .addComponent(nopin, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addGap(51, 51, 51)
                        .addComponent(tglp, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(117, 117, 117))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(textNis, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(92, 92, 92))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jdb, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel21)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textIds, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(idb, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(66, 66, 66)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jeb, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 19, Short.MAX_VALUE)
                                .addComponent(jLabel15)
                                .addGap(53, 53, 53)
                                .addComponent(textSis, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(23, 23, 23))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(tglp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(nopin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel19)
                            .addComponent(jLabel15)
                            .addComponent(textNis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textIds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textSis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23)
                    .addComponent(jeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jdb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addGap(60, 60, 60))
        );

        pinjam.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        pinjam.setText("Pinjam");
        pinjam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pinjamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabpeminjamanbukuLayout = new javax.swing.GroupLayout(tabpeminjamanbuku);
        tabpeminjamanbuku.setLayout(tabpeminjamanbukuLayout);
        tabpeminjamanbukuLayout.setHorizontalGroup(
            tabpeminjamanbukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabpeminjamanbukuLayout.createSequentialGroup()
                .addGroup(tabpeminjamanbukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 991, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(tabpeminjamanbukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(tabpeminjamanbukuLayout.createSequentialGroup()
                            .addGap(9, 9, 9)
                            .addGroup(tabpeminjamanbukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(pinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)))
                        .addGroup(tabpeminjamanbukuLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 994, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        tabpeminjamanbukuLayout.setVerticalGroup(
            tabpeminjamanbukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabpeminjamanbukuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(215, Short.MAX_VALUE))
        );

        getContentPane().add(tabpeminjamanbuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 1280, 680));

        tabpengembalianbuku.setBackground(new java.awt.Color(153, 255, 255));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel26.setText("Pengembalian Buku");

        jPanel6.setBackground(new java.awt.Color(51, 51, 255));
        jPanel6.setPreferredSize(new java.awt.Dimension(0, 39));

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_attention_40px.png"))); // NOI18N

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Tolong, diperhatikan dalam pengisian data peminjaman, terima kasih");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel28)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel28))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel27)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(245, 245, 245));
        jPanel7.setPreferredSize(new java.awt.Dimension(1097, 401));

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel29.setText("No. Kembali");

        textidb.setEditable(false);
        textidb.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel30.setText("Tanggal Kembali");

        tglk.setEditable(false);
        tglk.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel31.setText("Siswa");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel32.setText("NIS");

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel33.setText("Id Siswa");

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel34.setText("Id Buku");

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel35.setText("Judul Buku");

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel36.setText("Jenis Buku");

        textjnb.setEditable(false);
        textjnb.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        textnis.setEditable(false);
        textnis.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        textids.setEditable(false);
        textids.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        textsis.setEditable(false);
        textsis.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel37.setText("No. Peminjam");

        nokb.setEditable(false);
        nokb.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jd.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jd.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Default>" }));
        jd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jdActionPerformed(evt);
            }
        });

        nopj.setEditable(false);
        nopj.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel29)
                        .addGap(18, 18, 18)
                        .addComponent(nokb, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel37))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel32)
                                .addGap(93, 93, 93))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel34)
                                .addGap(59, 59, 59)))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textidb, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textnis, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(jLabel35))))
                .addGap(18, 24, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nopj, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jd, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textids, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36)
                            .addComponent(jLabel31))
                        .addGap(54, 54, 54))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textjnb, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textsis, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tglk, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jLabel31))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addGap(77, 77, 77)
                                    .addComponent(textsis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tglk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(49, 49, 49)
                            .addComponent(textjnb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel29)
                                .addComponent(jLabel37)
                                .addComponent(nokb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(nopj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(49, 49, 49)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel32)
                                .addComponent(jLabel33)
                                .addComponent(textnis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(textids, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(49, 49, 49)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel35)
                                .addComponent(jLabel34)
                                .addComponent(textidb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel36)))))
                .addContainerGap(72, Short.MAX_VALUE))
        );

        kembali.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        kembali.setText("Kembalikan");
        kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kembaliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabpengembalianbukuLayout = new javax.swing.GroupLayout(tabpengembalianbuku);
        tabpengembalianbuku.setLayout(tabpengembalianbukuLayout);
        tabpengembalianbukuLayout.setHorizontalGroup(
            tabpengembalianbukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabpengembalianbukuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabpengembalianbukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kembali)
                    .addComponent(jLabel26)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 959, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 959, Short.MAX_VALUE))
                .addContainerGap(315, Short.MAX_VALUE))
        );
        tabpengembalianbukuLayout.setVerticalGroup(
            tabpengembalianbukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabpengembalianbukuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(tabpengembalianbuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 1280, 680));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardMouseClicked
        // TODO add your handling code here:
        tabdashboard.setVisible(true);
        tablistbook.setVisible(false);
        tabpeminjamanbuku.setVisible(false);
        tabpengembalianbuku.setVisible(false);
        dsh.setBackground(new Color(51,51,51));
        list.setBackground(new Color(102,102,102));
        pmj.setBackground(new Color(102,102,102));
        pgm.setBackground(new Color(102,102,102));
        clearinfo();
    }//GEN-LAST:event_dashboardMouseClicked

    private void listbookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listbookMouseClicked
        // TODO add your handling code here:
        tablistbook.setVisible(true);
        tabdashboard.setVisible(false);
        tabpeminjamanbuku.setVisible(false);
        tabpengembalianbuku.setVisible(false);
        list.setBackground(new Color(51,51,51));
        dsh.setBackground(new Color(102,102,102));
        pmj.setBackground(new Color(102,102,102));
        pgm.setBackground(new Color(102,102,102));
    }//GEN-LAST:event_listbookMouseClicked

    private void lgoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lgoMouseClicked
        // TODO add your handling code here:
        new loginsiswa().setVisible(true);
        dispose();
    }//GEN-LAST:event_lgoMouseClicked

    private void showActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showActionPerformed
        // TODO add your handling code here:
        infobuku();
    }//GEN-LAST:event_showActionPerformed

    private void peminjamanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_peminjamanMouseClicked
        // TODO add your handling code here:
        tabpeminjamanbuku.setVisible(true);
        tabdashboard.setVisible(false);
        tablistbook.setVisible(false);
        tabpengembalianbuku.setVisible(false);
        pmj.setBackground(new Color(51,51,51));
        dsh.setBackground(new Color(102,102,102));
        list.setBackground(new Color(102,102,102));
        pgm.setBackground(new Color(102,102,102));
        clearinfo();
    }//GEN-LAST:event_peminjamanMouseClicked

    private void jdbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jdbActionPerformed
        // TODO add your handling code here:
        if(jdb.getSelectedItem().equals("<Default>")){
            idb.setText(null);
            jeb.setText(null);
         }
        else{
        nmb();
         nmj();
         }
    }//GEN-LAST:event_jdbActionPerformed

    private void pinjamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pinjamActionPerformed
        // TODO add your handling code here:
        if(jdb.getSelectedItem().equals("<Default>")){
            JOptionPane.showMessageDialog(this, "Anda Belum Memilih Data Yang Valid");
         }
       else{
           cesto();
       }
    }//GEN-LAST:event_pinjamActionPerformed

    private void pengembalianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pengembalianMouseClicked
        // TODO add your handling code here:
        tabpengembalianbuku.setVisible(true);
        tabpeminjamanbuku.setVisible(false);
        tabdashboard.setVisible(false);
        tablistbook.setVisible(false);
        pgm.setBackground(new Color(51,51,51));
        pmj.setBackground(new Color(102,102,102));
        dsh.setBackground(new Color(102,102,102));
        list.setBackground(new Color(102,102,102));
        clearinfo();
    }//GEN-LAST:event_pengembalianMouseClicked

    private void jenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jenisActionPerformed
        // TODO add your handling code here:
        tpbook();
    }//GEN-LAST:event_jenisActionPerformed

    private void dtbukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dtbukuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dtbukuActionPerformed

    private void kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kembaliActionPerformed
        // TODO add your handling code here:
        if(jd.getSelectedItem().equals("<Default>")){
            JOptionPane.showMessageDialog(this, "Anda Belum Memilih Data Yang Valid");
         }
       else{
            dead();
            sel();
            denda();
            if(sel == 0){
                tru();  
                cestk();
            }else{
                tru();  
                cestk();
                JOptionPane.showMessageDialog(this, "Maaf waktu pengembalian sudah melebihi batas tanggal");
            }
       }
    }//GEN-LAST:event_kembaliActionPerformed

    private void jdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jdActionPerformed
        // TODO add your handling code here:
        if(jd.getSelectedItem().equals("<Default>")){
            textnis.setText(null);
            textids.setText(null);
            textsis.setText(null);
            textidb.setText(null);
            nopj.setText(null);
            textjnb.setText(null);
         }
        else{
        nis();
        ids();
        nms();
        idb();
        jdb();
        jnb();
         }
    }//GEN-LAST:event_jdActionPerformed

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
            java.util.logging.Logger.getLogger(menusiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menusiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menusiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menusiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new menusiswa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel apro;
    private javax.swing.JLabel dashboard;
    private javax.swing.JPanel dsh;
    private javax.swing.JComboBox<String> dtbuku;
    private javax.swing.JPanel footer;
    private javax.swing.JLabel gambar;
    private javax.swing.JPanel header;
    private javax.swing.JTextField idb;
    private javax.swing.JLabel idbu;
    private javax.swing.JLabel idpro;
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
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JComboBox<String> jd;
    private javax.swing.JComboBox<String> jdb;
    private javax.swing.JLabel jdbu;
    private javax.swing.JTextField jeb;
    private javax.swing.JLabel jebu;
    private javax.swing.JComboBox<String> jenis;
    private javax.swing.JLabel jepro;
    private javax.swing.JLabel jupro;
    private javax.swing.JButton kembali;
    private javax.swing.JLabel kepro;
    private javax.swing.JLabel laki;
    private javax.swing.JLabel lgo;
    private javax.swing.JPanel list;
    private javax.swing.JLabel listbook;
    private javax.swing.JPanel logout;
    private javax.swing.JLabel napro;
    private javax.swing.JLabel nispro;
    private javax.swing.JTextField nokb;
    private javax.swing.JTextField nopin;
    private javax.swing.JTextField nopj;
    private javax.swing.JLabel nopro;
    private javax.swing.JLabel pebu;
    private javax.swing.JLabel peminjaman;
    private javax.swing.JLabel penbu;
    private javax.swing.JLabel pengembalian;
    private javax.swing.JLabel per;
    private javax.swing.JPanel pgm;
    private javax.swing.JButton pinjam;
    private javax.swing.JPanel pmj;
    private javax.swing.JLabel rabu;
    private javax.swing.JButton show;
    private javax.swing.JPanel sidebar;
    private javax.swing.JLabel stbu;
    private javax.swing.JPanel tabdashboard;
    private javax.swing.JPanel tablistbook;
    private javax.swing.JPanel tabpeminjamanbuku;
    private javax.swing.JPanel tabpengembalianbuku;
    private javax.swing.JTextField textIds;
    private javax.swing.JTextField textNis;
    private javax.swing.JTextField textSis;
    private javax.swing.JLabel textUs;
    private javax.swing.JTextField textidb;
    private javax.swing.JTextField textids;
    private javax.swing.JTextField textjnb;
    private javax.swing.JTextField textnis;
    private javax.swing.JTextField textsis;
    private javax.swing.JTextField tglk;
    private javax.swing.JTextField tglp;
    private javax.swing.JLabel tipro;
    private javax.swing.JLabel tpro;
    private javax.swing.JPanel usl;
    private javax.swing.JPanel usp;
    // End of variables declaration//GEN-END:variables
}
