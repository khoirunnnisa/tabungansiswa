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
public class Form_Penarikan extends javax.swing.JFrame {
 DefaultTableModel model = new DefaultTableModel();
    Connection conn;
    Statement st;
    ResultSet rs;
    static String staticNis, staticNoRekening, staticNama, staticSaldo;
    Date tanggal;
    
    /**
     * Creates new form Form_Penarikan
     */
    public Form_Penarikan(String[] data) {
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
            System.out.println("Database Connected");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to connect database :" + e,
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            System.out.println("Error :" + e);
        }
       tabel1.setModel(model);
        model.addColumn("No");
        model.addColumn("Tgl Penarikan");
        model.addColumn("No Penarikan");
        model.addColumn("Walikelas");
        model.addColumn("NIS");
        model.addColumn("No Rekening");
        model.addColumn("Nama");
        model.addColumn("Kelas");
        model.addColumn("Saldo");
        model.addColumn("Debet");
        model.addColumn("Saldo Akhir");
        
        showData();
        clear();
        btn_tambah.setEnabled(true);
        
        tgl_penarikan.setEnabled(false);
        txt_nopenarikan1.setEnabled(false);
        txt_nis.setEnabled(false);
        txt_norekening.setEnabled(false);
        txt_nama.setEnabled(false);
        txt_saldoawal.setEnabled(false);
        txt_debet.setEnabled(false);
        txt_saldo.setEnabled(false);
        
        if (data != null) {
            staticNis = data[0];
            staticNoRekening = data [1];
            staticNama = data [2];
            staticSaldo = data [3];
    }
    
    }
    
    public void getcombobox(){
    String query = "select * from kodekelas order by kode ASC";
        try {
            st = (com.mysql.jdbc.Statement) conn.createStatement();
            rs = st.executeQuery(query);
            int no = 1;
            while (rs.next()) {
             cbx_kode.addItem(rs.getString("kode"));
                
            }
        } catch (Exception e) {
            System.out.println("Error Tampilkan :" + e);
        }
    }
    public void getvalue(){
        String kelas = cbx_kode.getSelectedItem().toString();
        System.out.println(kelas);
        String query = "select kelas from kodekelas where kode = '"+kelas+"'";
        try {
            st = (com.mysql.jdbc.Statement) conn.createStatement();
            rs = st.executeQuery(query);
            int no = 1;
            while (rs.next()) {
           txt_kelas.setText(rs.getString("kelas"));
            }
        } catch (Exception e) {
            System.out.println("Error Tampilkan :" + e);
        }

    }
   
    
    private void clear() {
        tgl_penarikan.setDate(new Date());
        txt_nopenarikan1.setText("");
        cbx_guru.setSelectedIndex(0);
        cbx_kode.setSelectedIndex(0);
        txt_nis.setText("");
        txt_norekening.setText("");
        txt_nama.setText("");
        txt_kelas.setText("");
        txt_saldoawal.setText("0");
        txt_debet.setText("0");
        txt_saldo.setText("0");
    }
    
    private void on() {
        tgl_penarikan.setEnabled(false);
        txt_nis.setEnabled(true);
        txt_norekening.setEnabled(true);
        txt_nama.setEnabled(true);
        txt_saldoawal.setEnabled(true);
        txt_debet.setEnabled(true);
        txt_saldo.setEnabled(true);
    }
    
    private void off() {
        tgl_penarikan.setEnabled(false);
        txt_nis.setEnabled(false);
        txt_norekening.setEnabled(false);
        txt_nama.setEnabled(false);
        txt_saldoawal.setEnabled(false);
        txt_debet.setEnabled(false);
        txt_saldo.setEnabled(false);
    }
    
    private void showData() {
        model.fireTableDataChanged();
        model.getDataVector().removeAllElements();
        String query = "select * from penarikan order by no_penarikan ASC";
        try {
            st = (Statement) conn.createStatement();
            rs = st.executeQuery(query);
            int no = 1;
            while (rs.next()) {
                Object[] o = new Object[]{
                    no++,
                    rs.getDate("tgl_tarik"),
                    rs.getString("no_penarikan"),
                    rs.getString("nm_guru"),
                    rs.getString("nis_siswa"),
                    rs.getString("nmr_rekening"),
                    rs.getString("nama_siswa"),
                    rs.getString("kelas"),
                    rs.getInt("saldo_awal"),
                    rs.getInt("debet"),
                    rs.getInt("saldo"),};
                model.addRow(o);
            }
        } catch (Exception e) {
            System.out.println("Error Tampilkan :" + e);
        }
    }
   
    
   private void showing() {
      if (tabel1.getSelectedRow() > -1) { 
                
        txt_nopenarikan1.setText(model.getValueAt(tabel1.getSelectedRow(), 2).toString());
        cbx_guru.setSelectedItem(model.getValueAt(tabel1.getSelectedRow(), 3).toString());
        txt_nis.setText(model.getValueAt(tabel1.getSelectedRow(), 4).toString());
        txt_norekening.setText(model.getValueAt(tabel1.getSelectedRow(), 5).toString());
        txt_nama.setText(model.getValueAt(tabel1.getSelectedRow(), 6).toString());
        txt_kelas.setText(model.getValueAt(tabel1.getSelectedRow(), 7).toString());
        txt_saldoawal.setValue(Integer.parseInt(model.getValueAt(tabel1.getSelectedRow(), 8).toString()));
        txt_debet.setValue(Integer.parseInt(model.getValueAt(tabel1.getSelectedRow(), 9).toString()));
        txt_saldo.setValue(Integer.parseInt(model.getValueAt(tabel1.getSelectedRow(), 10).toString()));
            
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateInString = model.getValueAt(tabel1.getSelectedRow(), 1).toString();
            
            try {
              tgl_penarikan.setDate(formatter.parse(dateInString));
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
        Date tgl_tarik = null;
       
       
        String urut = "";
        String query = "select tgl_tarik, no_penarikan from penarikan order by no_penarikan DESC LIMIT 0,1";
        try {
            st = (com.mysql.jdbc.Statement) conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                notransaksi = rs.getString("no_penarikan");
                tgl_tarik = rs.getDate("tgl_tarik"); 
            }
            if (tgl_tarik != null && dateFormat.format(tanggal).equals(dateFormat.format(tgl_tarik))) {
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
                noSlip = "TR" + dateFormat.format(tanggal) + "-" + urut;
            } else {
                noSlip = "TR" + dateFormat.format(tanggal) + "-001";
            }
            txt_nopenarikan1.setText(noSlip);
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

        bodypanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        mainpanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tgl_penarikan = new com.toedter.calendar.JDateChooser();
        txt_nis = new javax.swing.JTextField();
        btn_cari = new javax.swing.JButton();
        txt_norekening = new javax.swing.JTextField();
        txt_nama = new javax.swing.JTextField();
        txt_saldo = new javax.swing.JFormattedTextField();
        txt_saldoawal = new javax.swing.JFormattedTextField();
        txt_debet = new javax.swing.JFormattedTextField();
        txt_nopenarikan1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cbx_guru = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        btn_cari1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel1 = new javax.swing.JTable();
        btn_tambah = new javax.swing.JButton();
        cbx_kode = new javax.swing.JComboBox<>();
        txt_kelas = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bodypanel.setBackground(new java.awt.Color(255, 255, 255));
        bodypanel.setPreferredSize(new java.awt.Dimension(881, 614));
        bodypanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(23, 69, 23));
        jPanel1.setPreferredSize(new java.awt.Dimension(881, 614));

        jPanel4.setBackground(new java.awt.Color(25, 77, 25));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Penarikan");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(547, 547, 547)
                .addComponent(jLabel1)
                .addContainerGap(599, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 116, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        bodypanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1260, 170));

        mainpanel.setLayout(new java.awt.CardLayout());
        bodypanel.add(mainpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel2.setText("Saldo Akhir");
        bodypanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 610, -1, -1));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel3.setText("Tanggal ");
        bodypanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel4.setText("No Penarikan");
        bodypanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, -1, -1));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel5.setText("Nama Guru");
        bodypanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, -1, -1));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel6.setText("Kode Tabungan");
        bodypanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 460, -1, -1));

        jLabel7.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel7.setText("Saldo Awal");
        bodypanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 510, -1, -1));

        jLabel8.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel8.setText("Jumlah Penarikan");
        bodypanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 560, -1, -1));

        tgl_penarikan.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        bodypanel.add(tgl_penarikan, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 200, 270, 40));

        txt_nis.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        bodypanel.add(txt_nis, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 350, 100, 40));

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
        bodypanel.add(btn_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 350, 30, 40));

        txt_norekening.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        bodypanel.add(txt_norekening, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 350, 160, 40));

        txt_nama.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        bodypanel.add(txt_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 400, 270, 40));

        txt_saldo.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txt_saldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_saldoActionPerformed(evt);
            }
        });
        bodypanel.add(txt_saldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 600, 270, 40));

        txt_saldoawal.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txt_saldoawal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_saldoawalFocusGained(evt);
            }
        });
        bodypanel.add(txt_saldoawal, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 500, 270, 40));

        txt_debet.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txt_debet.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_debetFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_debetFocusLost(evt);
            }
        });
        bodypanel.add(txt_debet, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 550, 270, 40));

        txt_nopenarikan1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        bodypanel.add(txt_nopenarikan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 250, 270, 40));

        jLabel9.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel9.setText("Nama");
        bodypanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, -1, -1));

        cbx_guru.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        cbx_guru.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Pilih Guru-", "Khoirunnisa", "Risma Nur", "Chalikul Hafidz" }));
        bodypanel.add(cbx_guru, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 270, 40));

        jLabel10.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel10.setText("NIS & No. Rek");
        bodypanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, -1, -1));

        btn_cari1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btn_cari1FocusGained(evt);
            }
        });
        btn_cari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cari1ActionPerformed(evt);
            }
        });
        bodypanel.add(btn_cari1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 350, 30, 40));

        tabel1.setModel(new javax.swing.table.DefaultTableModel(
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
        tabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabel1);

        bodypanel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 240, 700, 330));

        btn_tambah.setText("Tambah");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });
        bodypanel.add(btn_tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 590, 90, 30));

        cbx_kode.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        cbx_kode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Pilih Kode-" }));
        cbx_kode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbx_kodeItemStateChanged(evt);
            }
        });
        bodypanel.add(cbx_kode, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 450, 130, 40));

        txt_kelas.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txt_kelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kelasActionPerformed(evt);
            }
        });
        bodypanel.add(txt_kelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 450, 120, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bodypanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1260, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bodypanel, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cariActionPerformed
        // TODO add your handling code here:
        CariNasabah fcari = new CariNasabah();
        fcari.setVisible(true);
    }//GEN-LAST:event_btn_cariActionPerformed

    private void txt_saldoawalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_saldoawalFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_saldoawalFocusGained

    private void txt_debetFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_debetFocusGained
        // TODO add your handling code here:
        txt_debet.setText("");
    }//GEN-LAST:event_txt_debetFocusGained

    private void txt_debetFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_debetFocusLost
        // TODO add your handling code here:
        int saldo_awal  = Integer.valueOf(txt_saldoawal.getText().replaceAll("\\D+", ""));
        int debet =Integer.valueOf(txt_debet.getText().replaceAll("\\D+", ""));

        txt_saldo.setValue(saldo_awal-debet);
    }//GEN-LAST:event_txt_debetFocusLost

    private void btn_cariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btn_cariFocusGained
        // TODO add your handling code here:
        if (staticNis != null  &&staticNoRekening != null &&staticNama != null  && staticSaldo != null) {
            txt_nis.setText(staticNis);
            txt_norekening.setText(staticNoRekening);
            txt_nama.setText(staticNama);
            txt_saldoawal.setValue(Integer.valueOf(staticSaldo));
     }
    }//GEN-LAST:event_btn_cariFocusGained

    private void txt_saldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_saldoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_saldoActionPerformed

    private void btn_cari1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btn_cari1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_cari1FocusGained

    private void btn_cari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cari1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_cari1ActionPerformed

    private void tabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabel1MouseClicked

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
            if (btn_tambah.getText().equals("Tambah")) {
            btn_tambah.setText("Simpan");
            btn_tambah.setEnabled(true);
            //btCari.setEnabled(false);
            //btbatal.setEnabled(true);
            //btKeluar.setEnabled(false);
            on();
            clear();
            //navigationOFF();
            KodePenarikan();
            getcombobox();
            
            staticNis = null;
            staticNoRekening = null;
            staticNama = null;
            staticSaldo = null;
            
        } else if (btn_tambah.getText().equals("Simpan")) {
            tanggal = tgl_penarikan.getDate();
            java.sql.Date tanggalSql = new java.sql.Date(tanggal.getTime());
            
            int saldo_awal = Integer.valueOf(txt_saldoawal.getText().replaceAll("\\D+", ""));
            int debet = Integer.valueOf(txt_debet.getText().replaceAll("\\D+", ""));
            int saldo = Integer.valueOf(txt_saldo.getText().replaceAll("\\D+", ""));
            
            if (tgl_penarikan.getDate() != null 
                    && !txt_nopenarikan1.getText().equals("")
                    && !cbx_guru.getSelectedItem().equals("")
                    && !txt_nis.getText().equals("") 
                    && !txt_norekening.getText().equals("") 
                    && !txt_nama.getText().equals("") 
                    && !txt_kelas.getText().equals("")
                    && !txt_saldoawal.getValue().equals(0)
                    && !txt_debet.getText().equals("0") 
                    && !txt_saldo.getText().equals("0")) {
                
                 String query = "insert into penarikan (tgl_tarik, no_penarikan, nm_guru, nis_siswa, nmr_rekening, nama_siswa, kelas, saldo_awal, debet, saldo)" 
                        + " values('" 
                        + tanggalSql  
                        + "','" + txt_nopenarikan1.getText() 
                        + "','" + cbx_guru.getSelectedItem()
                        + "','" + txt_nis.getText() 
                        + "','" + txt_norekening.getText()
                        + "','" + txt_nama.getText()
                        + "','" + txt_kelas.getText()
                        + "','" + saldo_awal + "','" + debet + "','" + saldo + "')"; //disini yang diubah
                System.out.println(query);
                try {
                    st = (Statement) conn.createStatement();
                    st.executeUpdate(query);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Gagal Menyimpan Data", "Gagal",
                            JOptionPane.ERROR_MESSAGE);
                    System.out.println("Error Simpan =" + e);
                }
                
                //fungsi untuk mengupdate saldo tabungan
                String updatesaldo = "UPDATE tb_nasabah set saldo = '"+(saldo_awal-debet)+"' where nis_siswa= '"+txt_nis.getText()+"'";
                try {
                    st = (Statement) conn.createStatement();
                    st.executeUpdate(updatesaldo);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Berhasil mengubah saldo nasabah", "Gagal",
                            JOptionPane.ERROR_MESSAGE);
                    System.out.println("Error Simpan =" + e);
                }
              
            }}

    }//GEN-LAST:event_btn_tambahActionPerformed

    private void txt_kelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kelasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kelasActionPerformed

    private void cbx_kodeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_kodeItemStateChanged
        // TODO add your handling code here:
        getvalue();
    }//GEN-LAST:event_cbx_kodeItemStateChanged

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
            java.util.logging.Logger.getLogger(Form_Penarikan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Penarikan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Penarikan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Penarikan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Penarikan(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bodypanel;
    private javax.swing.JButton btn_cari;
    private javax.swing.JButton btn_cari1;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JComboBox<String> cbx_guru;
    private javax.swing.JComboBox<String> cbx_kode;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JTable tabel1;
    private com.toedter.calendar.JDateChooser tgl_penarikan;
    private javax.swing.JFormattedTextField txt_debet;
    private javax.swing.JTextField txt_kelas;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_nis;
    private javax.swing.JTextField txt_nopenarikan1;
    private javax.swing.JTextField txt_norekening;
    private javax.swing.JFormattedTextField txt_saldo;
    private javax.swing.JFormattedTextField txt_saldoawal;
    // End of variables declaration//GEN-END:variables
}
