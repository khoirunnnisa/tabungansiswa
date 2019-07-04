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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author khoirunnisa
 */
public class TutupBuku extends javax.swing.JFrame {
    DefaultTableModel model = new DefaultTableModel();
    Connection conn;
    Statement st;
    ResultSet rs;
    static String staticNoRekening, staticNama, staticSaldo;
    Date tanggal;

    /**
     * Creates new form TutupBuku
     */
    public TutupBuku(String[] data) {
        initComponents();
        setLocationRelativeTo(this);
        
        setTitle("Form Penarikan");
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
            System.out.println("Databse Connected");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to connect database :" + e,
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            System.out.println("Error :" + e);
        }
        tabel.setModel(model);
        model.addColumn("No");
        model.addColumn("Tgl Ditutup");
        model.addColumn("Id Tutup Buku");
        model.addColumn("No Rekening");
        model.addColumn("Nama");
        model.addColumn("Saldo");
        model.addColumn("Jumlah Diambil");
        model.addColumn("Alasan");

       showData();
       clear();
        btn_tambah.setEnabled(true);
        
        tgl_tutupbuku.setEnabled(false);
        id_tutupbuku.setEnabled(false);
        txt_norekening.setEnabled(false);
        txt_nmnasabah.setEnabled(false);
        txt_saldo.setEnabled(false);
        uangdiambil.setEnabled(false);
        txt_alasan.setEnabled(false);
        
        if (data != null) {
            staticNoRekening = data [0];
            staticNama = data [1];
            staticSaldo = data [2];
    }
}
    
     private void clear() {
        tgl_tutupbuku.setDate(new Date());
        id_tutupbuku.setText("");
        txt_norekening.setText("");
        txt_nmnasabah.setText("");
        txt_saldo.setText("0");
        uangdiambil.setText("0");
        txt_alasan.setText("");
    }
    
    private void on() {
        tgl_tutupbuku.setEnabled(false);
        id_tutupbuku.setEnabled(false);
        txt_norekening.setEnabled(false);
        txt_nmnasabah.setEnabled(false);
        txt_saldo.setEnabled(false);
        uangdiambil.setEnabled(true);
        txt_alasan.setEnabled(true);
    }
    
    private void showData() {
        model.fireTableDataChanged();
        model.getDataVector().removeAllElements();
        String query = "select * from tutup_buku order by id_tutupbuku ASC";
        try {
            st = (Statement) conn.createStatement();
            rs = st.executeQuery(query);
            int no = 1;
            while (rs.next()) {
                Object[] o = new Object[]{
                    no++,
                    rs.getDate("tgl_tutupbuku"),
                    rs.getString("id_tutupbuku"),
                    rs.getString("nmr_rekening"),
                    rs.getString("nama_siswa"),
                    rs.getInt("saldo"),
                    rs.getInt("jml_diambil"),
                    rs.getString("alasan"),};
                model.addRow(o);
            }
        } catch (Exception e) {
            System.out.println("Error Tampilkan :" + e);
        }
    }
   
    
   private void showing() {
      if (tabel.getSelectedRow() > -1) { 
                
        id_tutupbuku.setText(model.getValueAt(tabel.getSelectedRow(), 2).toString());
        txt_norekening.setText(model.getValueAt(tabel.getSelectedRow(), 3).toString());
        txt_nmnasabah.setText(model.getValueAt(tabel.getSelectedRow(), 4).toString());
        txt_saldo.setValue(Integer.parseInt(model.getValueAt(tabel.getSelectedRow(), 5).toString()));
        uangdiambil.setValue(Integer.parseInt(model.getValueAt(tabel.getSelectedRow(), 6).toString()));
        txt_alasan.setText(model.getValueAt(tabel.getSelectedRow(), 7).toString());
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateInString = model.getValueAt(tabel.getSelectedRow(), 1).toString();
            
            try {
              tgl_tutupbuku.setDate(formatter.parse(dateInString));
            } catch (ParseException ex) {
              System.out.println("Error Pengambilan Tanggal Pada Tabel karena: " + ex);
           }

            btn_tambah.setEnabled(true);
            //btn_edit.setEnabled(true);
            //btn_hapus.setEnabled(true);
            //btn_cari.setEnabled(true);
        }
        }

   public void KodePenarikan() {
        Date tanggal = new Date();
        String noSlip = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String notransaksi = "";
        Date tgl_tutupbuku = null;
       
        String urut = "";
        String query = "select tutup buku, id_tutupbuku from tutup_buku order by tutup_buku DESC LIMIT 0,1";
        try {
            st = (com.mysql.jdbc.Statement) conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                notransaksi = rs.getString("id_tutupbuku");
                tgl_tutupbuku = rs.getDate("tgl_tutupbuku"); 
            }
            if (tgl_tutupbuku != null && dateFormat.format(tanggal).equals(dateFormat.format(tgl_tutupbuku))) {
                int panjang = notransaksi.length();
                int subPanjang = panjang - 3;
                int no = Integer.valueOf(notransaksi.substring(subPanjang, panjang)) + 1;
                if (no < 10) {
                    urut = "00" + String.valueOf(no);
                } else if (no < 100) {
                    urut = "0" + String.valueOf(no);
                } else if (no < 1000) {
                    urut = String.valueOf(no);
                }
                noSlip = "TB" + dateFormat.format(tanggal) + "-" + urut;
            } else {
                noSlip = "TB" + dateFormat.format(tanggal) + "-001";
            }
            id_tutupbuku.setText(noSlip);
        } catch (Exception e) {
            System.out.println("Error Code Automation = " + e);
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        id_tutupbuku = new javax.swing.JTextField();
        btn_cari = new javax.swing.JButton();
        txt_norekening = new javax.swing.JTextField();
        uangdiambil = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        txt_saldo = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tgl_tutupbuku = new com.toedter.calendar.JDateChooser();
        txt_nmnasabah = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_alasan = new javax.swing.JTextArea();
        btn_tambah = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(23, 69, 23));

        jPanel3.setBackground(new java.awt.Color(25, 77, 25));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Form Tutup Buku");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(511, 511, 511)
                .addComponent(jLabel1)
                .addContainerGap(547, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 119, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1260, 180));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel2.setText("No Rekening");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 340, -1, -1));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel3.setText("Nama Nasabah");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 390, -1, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel4.setText("Saldo");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 440, -1, -1));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel5.setText("Alasan");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 550, -1, -1));

        id_tutupbuku.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        id_tutupbuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                id_tutupbukuActionPerformed(evt);
            }
        });
        jPanel1.add(id_tutupbuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 280, 240, 41));

        btn_cari.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btn_cariFocusGained(evt);
            }
        });
        btn_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cariActionPerformed(evt);
            }
        });
        jPanel1.add(btn_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 330, 30, 40));

        txt_norekening.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jPanel1.add(txt_norekening, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 330, 203, 41));
        jPanel1.add(uangdiambil, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 480, 240, 40));

        tabel.setModel(new javax.swing.table.DefaultTableModel(
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
        tabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 230, 670, 280));
        jPanel1.add(txt_saldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 430, 240, 40));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel6.setText("Uang yang dapat diambil");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 490, -1, -1));

        jLabel7.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel7.setText("Tanggal");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, -1, -1));

        jLabel8.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel8.setText("Id Tutup Buku");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, -1, -1));

        tgl_tutupbuku.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel1.add(tgl_tutupbuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 230, 240, 40));
        jPanel1.add(txt_nmnasabah, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 380, 240, 41));

        txt_alasan.setColumns(20);
        txt_alasan.setRows(5);
        jScrollPane2.setViewportView(txt_alasan);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 530, 240, 70));

        btn_tambah.setText("Tambah");
        jPanel1.add(btn_tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 530, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1261, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void id_tutupbukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_id_tutupbukuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_id_tutupbukuActionPerformed

    private void btn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cariActionPerformed
        // TODO add your handling code here:
         CariNasabah fcari = new CariNasabah();
         fcari.setVisible(true);
    }//GEN-LAST:event_btn_cariActionPerformed

    private void btn_cariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btn_cariFocusGained
        // TODO add your handling code here:
         if (staticNoRekening != null &&staticNama != null  && staticSaldo != null) {
            txt_norekening.setText(staticNoRekening);
            txt_nmnasabah.setText(staticNama);
            txt_saldo.setValue(Integer.valueOf(staticSaldo));
        }
    }//GEN-LAST:event_btn_cariFocusGained

    private void tabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelMouseClicked
        // TODO add your handling code here:
        showing();
    }//GEN-LAST:event_tabelMouseClicked

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
            java.util.logging.Logger.getLogger(TutupBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TutupBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TutupBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TutupBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TutupBuku(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cari;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JTextField id_tutupbuku;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabel;
    private com.toedter.calendar.JDateChooser tgl_tutupbuku;
    private javax.swing.JTextArea txt_alasan;
    private javax.swing.JTextField txt_nmnasabah;
    private javax.swing.JTextField txt_norekening;
    private javax.swing.JFormattedTextField txt_saldo;
    private javax.swing.JFormattedTextField uangdiambil;
    // End of variables declaration//GEN-END:variables
}
