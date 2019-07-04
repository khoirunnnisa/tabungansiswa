/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TugasAkhir;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author khoirunnisa
 */
public class Form_Petugas extends javax.swing.JFrame {
    
    String kode;
    DefaultTableModel model = new DefaultTableModel();
    Connection conn;
    Statement st;
    ResultSet rs;
    /**
     * Creates new form Form_Petugas
     */
    public Form_Petugas() {
        initComponents();
            setTitle("Petugas");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 6 - this.getSize().height / 6);

        conn = null;
        st = null;
        rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/tabungan";
            String user = "root";
            String pwd = "";

            conn = (Connection) DriverManager.getConnection(url, user, pwd);
            // System.out.println("Terkoneksi DB");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed connect to Database :" + e,
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            System.out.println("Error :" + e);

        }
        tabelPetugas.setModel(model);  
        model.addColumn("No");
        model.addColumn("ID Petugas");
        model.addColumn("Nama");
        model.addColumn("Alamat");
        model.addColumn("No Telp");
        model.addColumn("Jabatan");
        

        showData();
        clear();
        off();
        
        btn_tambah.setEnabled(true);
        btn_edit.setEnabled(false);
        btn_hapus.setEnabled(false);
        txt_idpetugas.setEnabled(false);
        
    
    }
    
    private void kode() {
        String query = "select id_petugas from pegawai order by id_petugas DESC";
        try {
            st = (Statement) conn.createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                String idPetugas = rs.getString("id_petugas");
                String i = idPetugas.substring(1, 2);
                int a = Integer.parseInt(i) + 1;
                //System.out.println("i =" + i);
                if (a < 10) {
                    kode = "P" + String.valueOf(a);                }
            } else {
                kode = "P1";
            }
            txt_idpetugas.setText(kode);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    
    private void showData() {
        model.fireTableDataChanged();
        model.getDataVector().removeAllElements();
        String query = "select * from pegawai order by id_petugas ASC";
        try {
            st = (Statement) conn.createStatement();
            rs = st.executeQuery(query);
            int no = 1;
            while (rs.next()) {
                Object[] o = new Object[]{
                    no++,
                    rs.getString("id_petugas"),
                    rs.getString("nm_petugas"),
                    rs.getString("alamat"),
                    rs.getString("no_tlp"),
                    rs.getString("jabatan")
                };
                model.addRow(o);
            }
        } catch (Exception e) {
            System.out.println("Error Tampil :" + e);
        }
    }
    
     private void clear() {
        txt_idpetugas.setText("");
        txt_nama1.setText("");
        txt_alamat.setText("");
        txt_tlp.setText("");
        cbxJabatan.setSelectedIndex(-1);
    }
     
     private void on() {
        txt_idpetugas.setEnabled(true);
        txt_nama1.setEnabled(true);
        txt_alamat.setEnabled(true);
        txt_tlp.setEnabled(true);
        cbxJabatan.setEnabled(true);
        
    }
     private void off() {
        txt_idpetugas.setEnabled(true);
        txt_nama1.setEnabled(false);
        txt_alamat.setEnabled(false);
        txt_tlp.setEnabled(false);
        cbxJabatan.setEnabled(false);
  
     }
   
     private void showing() {
        if (tabelPetugas.getSelectedRow() > -1) {
            txt_idpetugas.setText(model.getValueAt(tabelPetugas.getSelectedRow(), 1).toString());
            txt_nama1.setText(model.getValueAt(tabelPetugas.getSelectedRow(), 2).toString());
            txt_alamat.setText(model.getValueAt(tabelPetugas.getSelectedRow(), 3).toString());
            txt_tlp.setText(model.getValueAt(tabelPetugas.getSelectedRow(), 4).toString());
            cbxJabatan.setSelectedItem(model.getValueAt(tabelPetugas.getSelectedRow(), 5).toString());

            String id = model.getValueAt(tabelPetugas.getSelectedRow(), 1).toString();
            try {
                String query = "select password from pegawai where id_petugas='" + id + "'";
                st = (Statement) conn.createStatement();
                rs = st.executeQuery(query);
                while (rs.next()) {
                    Password.setText(rs.getString("password"));

                }
            } catch (Exception e) {
                System.out.println("Error " + e);
            }
            btn_tambah.setEnabled(true);
            btn_edit.setEnabled(true);
            btn_hapus.setEnabled(true);
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelPetugas = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_idpetugas = new javax.swing.JTextField();
        txt_alamat = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_tlp = new javax.swing.JTextField();
        txt_nama1 = new javax.swing.JTextField();
        Password = new javax.swing.JPasswordField();
        cbxJabatan = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        btn_tambah = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(23, 69, 23));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(25, 77, 25));

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Petugas");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(379, 379, 379)
                .addComponent(jLabel6)
                .addContainerGap(409, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addContainerGap())
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 880, 50));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_user_male_circle_70px.png"))); // NOI18N
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 50, 120, 70));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 880, 180));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBorder(null);

        tabelPetugas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tabelPetugas.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelPetugas.setGridColor(new java.awt.Color(255, 255, 255));
        tabelPetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelPetugasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelPetugas);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 690, 152));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel1.setText("ID Petugas");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel2.setText("Nama");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));
        jPanel4.add(txt_idpetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 204, 38));
        jPanel4.add(txt_alamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 204, 38));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel3.setText("Alamat");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel4.setText("Jabatan");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel5.setText("No. Tlp");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 40, -1, -1));
        jPanel4.add(txt_tlp, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 30, 204, 38));
        jPanel4.add(txt_nama1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 80, 204, 38));

        Password.setText("jPasswordField1");
        Password.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PasswordMouseClicked(evt);
            }
        });
        jPanel4.add(Password, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 130, 200, 40));

        cbxJabatan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Customer Service", "Teller", " " }));
        cbxJabatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxJabatanActionPerformed(evt);
            }
        });
        jPanel4.add(cbxJabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 80, 130, 40));

        jLabel8.setText("Password");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 140, -1, -1));

        btn_tambah.setText("Tambah");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });
        jPanel4.add(btn_tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 360, -1, -1));

        btn_edit.setText("Edit");
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });
        jPanel4.add(btn_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 360, -1, -1));

        btn_hapus.setText("Hapus");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        jPanel4.add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 360, -1, -1));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 880, 440));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 881, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 614, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbxJabatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxJabatanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxJabatanActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        if (btn_tambah.getText().equals("Tambah")) {
            btn_tambah.setText("Simpan");
            btn_tambah.setEnabled(true);
            btn_edit.setEnabled(false);
            btn_hapus.setEnabled(false);
            
            on();
            clear();
            kode();
            txt_nama1.requestFocus();

        } else if (btn_tambah.getText().equals("Simpan")) {
            if (!txt_idpetugas.getText().equals("")
                    && !txt_nama1.getText().equals("")
                    && !txt_alamat.getText().equals("")
                    && !txt_tlp.getText(). equals("")
                    && !cbxJabatan.getSelectedItem().equals("")
                    && !Password.getText().equals("")) {

                String query = "insert into pegawai set "
                        + " id_petugas='" + txt_idpetugas.getText() + "',"
                        + " nm_petugas='" + txt_nama1.getText() + "',"
                        + " alamat='" + txt_alamat.getText() + "',"
                        + " no_tlp='" + txt_tlp.getText() + "',"
                        + " jabatan='" + cbxJabatan.getSelectedItem() + "',"
                        + " password='" + Password.getText() + "'";
                try {
                    st = (Statement) conn.createStatement();
                    st.executeUpdate(query);
                    JOptionPane.showMessageDialog(null, "Berhasil Menyimpan Data User", "Sukses",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Gagal Menyimpan Data User", "Gagal",
                            JOptionPane.ERROR_MESSAGE);
                    System.out.println("Error Simpan =" + e);
                }

                // clear();
                showData();
                off();
                
                btn_tambah.setEnabled(true);
                btn_edit.setEnabled(false);
                btn_hapus.setEnabled(false);
                btn_tambah.requestFocus();
                btn_tambah.setText("Tambah");

            } else {
                JOptionPane.showMessageDialog(null, "Gagal Menyimpan Data Karena Data Belum Lengkap", "Gagal",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        // TODO add your handling code here:
        if (btn_edit.getText().equals("Edit")) {
            btn_edit.setText("Update");
            btn_tambah.setEnabled(false);
            btn_edit.setEnabled(true);
            btn_hapus.setEnabled(false);
            
            on();
            txt_nama1.requestFocus();

        } else if (btn_edit.getText().equals("Update")) {
            if (!txt_idpetugas.getText().equals("")
                    && !txt_nama1.getText().equals("")
                    && !txt_alamat.getText().equals("")
                    && !txt_tlp.getText().equals("")
                    && !Password.getText().equals("")) {

                        String query = "update pegawai set "
                        + " nm_petugas='" + txt_nama1.getText() + "',"
                        + " alamat='" + txt_alamat.getText() + "',"
                        + " no_tlp='" + txt_tlp.getText() + "',"
                        + " jabatan='" + cbxJabatan.getSelectedItem()+ "',"
                        + " password='" + Password.getText() + "' "
                        + "WHERE id_petugas = '"+txt_idpetugas.getText()+"'";

                try {
                    st = (Statement) conn.createStatement();
                    st.executeUpdate(query);
                    JOptionPane.showMessageDialog(null, "Berhasil Merubah Data User", "Sukses",
                            JOptionPane.INFORMATION_MESSAGE);
                    on();
                    showData();
                    off();
                    
                    btn_tambah.setEnabled(true);
                    btn_edit.setEnabled(true);
                    btn_hapus.setEnabled(false);
                    btn_tambah.requestFocus();
                    btn_edit.setText("Edit");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Gagal Merubah Data User", "Gagal",
                            JOptionPane.ERROR_MESSAGE);
                    System.out.println("Error Update =" + e);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Gagal Mengubah Data Karena Data Belum Lengkap", "Gagal",
                        JOptionPane.WARNING_MESSAGE);
            }
            }
        
    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
        int pesan = JOptionPane.showConfirmDialog(null, "Anda Yakin Akan Menghapus Data Ini ?", "Question", JOptionPane.OK_CANCEL_OPTION);
        if (pesan == 0) {

            String query = "delete from pegawai where id_petugas='" + txt_idpetugas.getText() + "'";
            try {
                st = (Statement) conn.createStatement();
                st.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Berhasil Menghapus Data", "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Gagal Menghapus Data", "Gagal",
                        JOptionPane.ERROR_MESSAGE);
            }
            clear();
            showData();
            off();
            btn_tambah.setEnabled(true);
            btn_edit.setEnabled(false);
            btn_hapus.setEnabled(false);
            btn_tambah.requestFocus();
    }   
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void tabelPetugasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPetugasMouseClicked
        // TODO add your handling code here:
        showing();
    }//GEN-LAST:event_tabelPetugasMouseClicked

    private void PasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PasswordMouseClicked
        // TODO add your handling code here:
        Password.setText("");
    }//GEN-LAST:event_PasswordMouseClicked

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
            java.util.logging.Logger.getLogger(Form_Petugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Petugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Petugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Petugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Petugas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField Password;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JComboBox<String> cbxJabatan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tabelPetugas;
    private javax.swing.JTextField txt_alamat;
    private javax.swing.JTextField txt_idpetugas;
    private javax.swing.JTextField txt_nama1;
    private javax.swing.JTextField txt_tlp;
    // End of variables declaration//GEN-END:variables
}
