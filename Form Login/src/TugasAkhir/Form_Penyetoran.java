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
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author khoirunnisa
 */
public class Form_Penyetoran extends javax.swing.JFrame {
    DefaultTableModel model = new DefaultTableModel();
    Connection conn;
    Statement st;
    ResultSet rs;
    static String staticNis, staticNoRekening, staticNama, staticSaldo;
    Date tanggal;
    private String query;
    /**
     * Creates new form Form_Penarikan
     */
    public Form_Penyetoran(String[] data) {
        initComponents();
        setTitle("Form Simpanan");
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
        model.addColumn("Tanggal Setor");
        model.addColumn("No Setor");
        model.addColumn("Walikelas");
        model.addColumn("NIS");
        model.addColumn("No Rekening");
        model.addColumn("Nama");
        model.addColumn("Kelas");
        model.addColumn("Saldo");
        model.addColumn("Kredit");
        model.addColumn("Saldo Akhir");
        
        showData();
        clear();
        btn_tambah.setEnabled(true);
        tgl_penyetoran.setEnabled(false);
        txt_nopen.setEnabled(false);
        txt_nis.setEnabled(false);
        txt_norek.setEnabled(false);
        txt_nama.setEnabled(false);
        txt_saldoawal.setEnabled(false);
        txt_kredit.setEnabled(false);
        txt_saldoakhir.setEnabled(false);
        
        
        if (data != null) {
            staticNis = data[0];
            staticNoRekening = data [1];
            staticNama = data [2];
            staticSaldo = data[3];
    
    }}

    public void getcombobox(){
    String query = "select * from kodekelas order by kode ASC";
        try {
            st = (com.mysql.jdbc.Statement) conn.createStatement();
            rs = st.executeQuery(query);
            int no = 1;
            while (rs.next()) {
             cbxkode.addItem(rs.getString("kode"));
                
            }
        } catch (Exception e) {
            System.out.println("Error Tampilkan :" + e);
        }
    }
    public void getvalue(){
        String kelas = cbxkode.getSelectedItem().toString();
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
        tgl_penyetoran.setDate(new Date());
        txt_nopen.setText("");
        cbx_guru.setSelectedIndex(0);
        cbxkode.setSelectedIndex(0);
        txt_nis.setText("");
        txt_norek.setText("");
        txt_nama.setText("");
        txt_saldoawal.setText("0");
        txt_kredit.setText("0");
        txt_saldoakhir.setText("0");
    }
    
    private void on() {
        tgl_penyetoran.setEnabled(false);
        txt_nis.setEnabled(true);
        txt_norek.setEnabled(true);
        txt_nama.setEnabled(true);
        txt_saldoawal.setEnabled(true);
        txt_kredit.setEnabled(true);
        txt_saldoakhir.setEnabled(true);
        
    }
    
    private void off() {
       
        tgl_penyetoran.setEnabled(false);
        txt_nis.setEnabled(false);
        txt_saldoakhir.setEnabled(false);
        txt_norek.setEnabled(false);
        txt_nama.setEnabled(false);
        txt_saldoawal.setEnabled(false);
        txt_kredit.setEnabled(false);
        txt_saldoakhir.setEnabled(false);
        
    }
    
    private void showData() {
        model.fireTableDataChanged();
        model.getDataVector().removeAllElements();
        String query = "select * from tb_penyetoran order by no_setor ASC";
        try {
            st = (Statement) conn.createStatement();
            rs = st.executeQuery(query);
            int no = 1;
            while (rs.next()) {
                Object[] o = new Object[]{
                    no++,
                    rs.getDate("tgl_setor"),
                    rs.getString("no_setor"),
                    rs.getString("nm_guru"),
                    rs.getString("nis_siswa"),
                    rs.getString("nmr_rekening"),
                    rs.getString("nama_siswa"), 
                    rs.getString("kelas"),
                    rs.getInt("saldo_awal"),
                    rs.getInt("kredit"),
                    rs.getInt("saldo"),};
                model.addRow(o);
            }
        } catch (Exception e) {
            System.out.println("Error Tampilkan :" + e);
        }
    }
    
    
    
    private void showing() {
        if (tabel.getSelectedRow() > -1) { 
                
            txt_nopen.setText(model.getValueAt(tabel.getSelectedRow(), 2).toString());
            cbx_guru.setSelectedItem(model.getValueAt(tabel.getSelectedRow(), 3).toString());
            txt_nis.setText(model.getValueAt(tabel.getSelectedRow(), 4).toString());
            txt_norek.setText(model.getValueAt(tabel.getSelectedRow(), 5).toString());
            txt_nama.setText(model.getValueAt(tabel.getSelectedRow(), 6).toString());
            txt_kelas.setText(model.getValueAt(tabel.getSelectedRow(), 7).toString());
            txt_saldoawal.setValue(Integer.parseInt(model.getValueAt(tabel.getSelectedRow(), 8).toString()));
            txt_kredit.setValue(Integer.parseInt(model.getValueAt(tabel.getSelectedRow(), 9).toString()));
            txt_saldoakhir.setValue(Integer.parseInt(model.getValueAt(tabel.getSelectedRow(), 10).toString()));
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateInString = model.getValueAt(tabel.getSelectedRow(), 1).toString();
            
            try {
                tgl_penyetoran.setDate(formatter.parse(dateInString));
            } catch (ParseException ex) {
                System.out.println("Error Pengambilan Tanggal Pada Tabel karena: " + ex);
            }

            btn_tambah.setEnabled(true);
            //btn_edit.setEnabled(true);
            //btn_hapus.setEnabled(true);
            //btn_cari.setEnabled(true);
        }
        }
    
    public void KodePenyetoran() {
        Date tanggal = new Date();
        String noSlip = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String notransaksi = "";
        Date tgl_setor = null;
       
        String urut = "";
        String query = "select tgl_setor, no_setor from tb_penyetoran order by no_setor DESC LIMIT 0,1";
        try {
            st = (com.mysql.jdbc.Statement) conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                notransaksi = rs.getString("no_setor");
                tgl_setor = rs.getDate("tgl_setor"); //disini yang diubah
            }
            if (tgl_setor != null && dateFormat.format(tanggal).equals(dateFormat.format(tgl_setor))) {
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
                noSlip = "ST" + dateFormat.format(tanggal) + "-" + urut;
            } else {
                noSlip = "ST" + dateFormat.format(tanggal) + "-001";
            }
            txt_nopen.setText(noSlip);
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
        menupanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tgl_penyetoran = new com.toedter.calendar.JDateChooser();
        txt_nis = new javax.swing.JTextField();
        txt_kredit = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_norek = new javax.swing.JTextField();
        cari = new javax.swing.JButton();
        txt_kelas = new javax.swing.JTextField();
        txt_saldoawal = new javax.swing.JFormattedTextField();
        txt_saldoakhir = new javax.swing.JFormattedTextField();
        btn_tambah = new javax.swing.JButton();
        txt_nopen = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        btn_hapus = new javax.swing.JButton();
        btn_cari = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cbxkode = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cbx_guru = new javax.swing.JComboBox<>();
        txt_nama = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bodypanel.setBackground(new java.awt.Color(255, 255, 255));
        bodypanel.setPreferredSize(new java.awt.Dimension(881, 614));
        bodypanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menupanel.setBackground(new java.awt.Color(23, 69, 23));
        menupanel.setPreferredSize(new java.awt.Dimension(881, 614));

        jPanel3.setBackground(new java.awt.Color(25, 77, 25));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Form Penyetoran");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(480, 480, 480)
                .addComponent(jLabel3)
                .addContainerGap(618, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
        );

        javax.swing.GroupLayout menupanelLayout = new javax.swing.GroupLayout(menupanel);
        menupanel.setLayout(menupanelLayout);
        menupanelLayout.setHorizontalGroup(
            menupanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        menupanelLayout.setVerticalGroup(
            menupanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menupanelLayout.createSequentialGroup()
                .addGap(0, 84, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        bodypanel.add(menupanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1250, 150));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setText("Tanggal");
        bodypanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, -1, -1));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel2.setText("No. Penyetoran");
        bodypanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, -1, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel4.setText("Jumlah");
        bodypanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 230, -1, -1));

        tgl_penyetoran.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        bodypanel.add(tgl_penyetoran, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 170, 230, 40));

        txt_nis.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_nis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nisActionPerformed(evt);
            }
        });
        bodypanel.add(txt_nis, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 170, 200, 40));

        txt_kredit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_kredit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_kreditFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_kreditFocusLost(evt);
            }
        });
        bodypanel.add(txt_kredit, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 220, 230, 40));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel5.setText("Walikelas");
        bodypanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, -1, -1));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel6.setText("Kode Tabungan");
        bodypanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, -1, -1));

        jLabel8.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel8.setText("Saldo Awal");
        bodypanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 180, -1, -1));

        jLabel7.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel7.setText("Saldo Akhir");
        bodypanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 280, -1, -1));

        txt_norek.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_norek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_norekActionPerformed(evt);
            }
        });
        bodypanel.add(txt_norek, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 220, 230, 40));

        cari.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cariFocusGained(evt);
            }
        });
        cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariActionPerformed(evt);
            }
        });
        bodypanel.add(cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 170, 30, 40));

        txt_kelas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_kelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kelasActionPerformed(evt);
            }
        });
        bodypanel.add(txt_kelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 320, 230, 40));

        txt_saldoawal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        bodypanel.add(txt_saldoawal, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 170, 230, 40));

        txt_saldoakhir.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        bodypanel.add(txt_saldoakhir, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 270, 230, 40));

        btn_tambah.setText("Tambah");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });
        bodypanel.add(btn_tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 610, -1, -1));

        txt_nopen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        bodypanel.add(txt_nopen, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 220, 230, 40));

        jLabel9.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel9.setText("No. Rekening");
        bodypanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 230, -1, -1));

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

        bodypanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 1170, 210));

        btn_hapus.setText("Hapus");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        bodypanel.add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 610, -1, -1));

        btn_cari.setText("Cari");
        btn_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cariActionPerformed(evt);
            }
        });
        bodypanel.add(btn_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 610, -1, -1));

        jLabel10.setText("pertama");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        bodypanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 340, -1, -1));

        jLabel11.setText("sebelumnya");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        bodypanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 340, -1, -1));

        jLabel12.setText("selanjutnya");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });
        bodypanel.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 340, -1, -1));

        jLabel13.setText("terakhir");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        bodypanel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 340, -1, -1));

        cbxkode.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        cbxkode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Pilih Kode-" }));
        cbxkode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxkodeItemStateChanged(evt);
            }
        });
        bodypanel.add(cbxkode, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 320, 230, 40));

        jLabel14.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel14.setText("NIS");
        bodypanel.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 180, -1, -1));

        jLabel15.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel15.setText("Kelas");
        bodypanel.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 330, -1, -1));

        cbx_guru.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        cbx_guru.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Pilih Guru-", "Khoirunnisa", "Risma Nur", "Chalikul Hafidz" }));
        bodypanel.add(cbx_guru, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 230, 40));

        txt_nama.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaActionPerformed(evt);
            }
        });
        bodypanel.add(txt_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 270, 230, 40));

        jLabel16.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel16.setText("Nama");
        bodypanel.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 280, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bodypanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1250, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bodypanel, javax.swing.GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
  
    }//GEN-LAST:event_jPanel3MouseClicked

    private void txt_nisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nisActionPerformed

    private void txt_norekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_norekActionPerformed
     
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_norekActionPerformed

    private void txt_kelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kelasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kelasActionPerformed

    private void cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariActionPerformed
        // TODO add your handling code here:
        CariNasabah fcari = new CariNasabah();
        fcari.setVisible(true);
    }//GEN-LAST:event_cariActionPerformed

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
            KodePenyetoran();
            
            staticNis = null;
            staticNoRekening = null;
            staticNama = null;
            staticSaldo = null;
            getcombobox();
        } else if (btn_tambah.getText().equals("Simpan")) {
            tanggal = tgl_penyetoran.getDate();
            java.sql.Date tanggalSql = new java.sql.Date(tanggal.getTime());
            
            int saldo_awal = Integer.valueOf(txt_saldoawal.getText().replaceAll("\\D+", ""));
            int kredit = Integer.valueOf(txt_kredit.getText().replaceAll("\\D+", ""));
            int saldo = Integer.valueOf(txt_saldoakhir.getText().replaceAll("\\D+", ""));
 
            if (tgl_penyetoran.getDate() != null
                    && !txt_nopen.getText().equals("") 
                    && !cbx_guru.getSelectedItem().equals("")
                    && !txt_nis.getText().equals("") 
                    && !txt_norek.getText().equals("")
                    && !txt_nama.getText().equals("")
                    && !txt_kelas.getText().equals("") 
                    && !txt_saldoawal.getText().equals(0) 
                    && !txt_kredit.getText().equals(0)
                    && !txt_saldoakhir.getText().equals(0)) { //disini yang diubah
               
                 try {
                    st = (Statement) conn.createStatement();
                    
                    JOptionPane.showMessageDialog(null, "Saldo tersimpan", "Sukses",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Gagal Merubah saldo anggota", "Gagal",
                            JOptionPane.ERROR_MESSAGE);
                    System.out.println("Error Update anggota =" + e);
                }
                 
                String query = "insert into tb_penyetoran (tgl_setor, no_setor, nm_guru, nis_siswa, nmr_rekening, nama_siswa, kelas, saldo_awal, kredit, saldo)"  //disini yang diubah
                        + " values('" 
                        + tanggalSql  
                        + "','" + txt_nopen.getText() 
                        + "','" + cbx_guru.getSelectedItem()
                        + "','" + txt_norek.getText() 
                        + "','" + txt_nis.getText()
                        + "','" + txt_nama.getText()
                        + "','" + txt_kelas.getText()
                        + "','" + saldo_awal + "','" + kredit + "','" + saldo + "')"; //disini yang diubah
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
                String updatesaldo = "UPDATE tb_nasabah set saldo = '"+(saldo_awal+kredit)+"' where nis_siswa= '"+txt_nis.getText()+"'";
                try {
                    st = (Statement) conn.createStatement();
                    st.executeUpdate(updatesaldo);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Berhasil mengubah saldo nasabah", "Gagal",
                            JOptionPane.ERROR_MESSAGE);
                    System.out.println("Error Simpan =" + e);
                }
                
                
                clear();
                showData();
                off();
                //navigationON();
                btn_tambah.setEnabled(true);
                //btCari.setEnabled(true);
                //btbatal.setEnabled(false);
                //btKeluar.setEnabled(true);
                btn_tambah.requestFocus();
                btn_tambah.setText("Tambah");
            } else {
                JOptionPane.showMessageDialog(null, "Gagal Menyimpan Data Karena Data Belum Lengkap", "Gagal",
                        JOptionPane.WARNING_MESSAGE);
            }
        }

    }//GEN-LAST:event_btn_tambahActionPerformed

    private void cariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cariFocusGained
        // TODO add your handling code here:
        if (staticNis != null  &&staticNoRekening != null &&staticNama != null  && staticSaldo != null) {
            txt_nis.setText(staticNis);
            txt_norek.setText(staticNoRekening);
            txt_nama.setText(staticNama);
            txt_saldoawal.setText(staticSaldo);
        }
    }//GEN-LAST:event_cariFocusGained

    private void tabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelMouseClicked
        // TODO add your handling code here:
        showing();
    }//GEN-LAST:event_tabelMouseClicked

    private void txt_kreditFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_kreditFocusLost
        // TODO add your handling code here:
        int saldo = Integer.valueOf(txt_saldoawal.getText().replaceAll("\\D+", ""));
        int kredit =Integer.valueOf(txt_kredit.getText().replaceAll("\\D+", ""));
        
        txt_saldoakhir.setValue(saldo+kredit);
    }//GEN-LAST:event_txt_kreditFocusLost

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
        int pesan = JOptionPane.showConfirmDialog(null, "Anda Yakin Akan Menghapus Data Ini ?", "Question", JOptionPane.OK_CANCEL_OPTION);
        if (pesan == 0) {

            String query = "delete from tb_penyetoran where no_setor='" + txt_nopen.getText() + "'";
            try {
                st = (com.mysql.jdbc.Statement) conn.createStatement();
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
            //hidupNavigation();
            btn_tambah.setEnabled(true);
            
            btn_hapus.setEnabled(true);
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cariActionPerformed
        // TODO add your handling code here:
        String cari = JOptionPane.showInputDialog("Pencarian Berdasarkan "
                + "Nomor Penyetoran dan Nama :");
        if (cari != null) {
            String query = "select * from tb_penyetoran where no_setor like '%" + cari.replace("'", "\\'") + "%' or nama_siswa like '%" + cari.replace("'", "\\'") + "%' order by no_setor ASC";
            try {
                model.fireTableDataChanged();
                model.getDataVector().removeAllElements();
                st = (com.mysql.jdbc.Statement) conn.createStatement();
                rs = st.executeQuery(query);
                int no = 1;
                while (rs.next()) {
                    Object[] o = new Object[]{
                        no++,
                        rs.getDate("tgl_setor"),
                        rs.getString("no_setor"),
                        rs.getString("nm_guru"),
                        rs.getString("nis_siswa"),
                        rs.getString("nmr_rekening"),
                        rs.getString("nama_siswa"),
                        rs.getString("kelas"),
                        rs.getInt("saldo"),
                        rs.getInt("kredit"),
                        rs.getInt("saldo_akhir")
                        

                    };
                    model.addRow(o);
                }
            } catch (Exception e) {
                System.out.println("Error Pencarian :" + e);
            }
           
        }
    }//GEN-LAST:event_btn_cariActionPerformed

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        // TODO add your handling code here:
        int jumlah = model.getRowCount();
        if (jumlah > 0) {
            ListSelectionModel selectionModel = tabel.getSelectionModel();
            selectionModel.setSelectionInterval(0, 0);
            showing();
        }
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // TODO add your handling code here:
        ListSelectionModel selectionModel = tabel.getSelectionModel();
        if (tabel.getSelectedRow() >= 0) {
            selectionModel.setSelectionInterval(tabel.getSelectedRow() - 1,
                    tabel.getSelectedRow() - 1);
            showing();

        }
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        // TODO add your handling code here:
        int jumlah = model.getRowCount();
        ListSelectionModel selectionModel = tabel.getSelectionModel();
        if (tabel.getSelectedRow() <= jumlah - 2) {
            selectionModel.setSelectionInterval(tabel.getSelectedRow() + 1, tabel.getSelectedRow() + 1);
            showing();

        }
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
        int jumlah = model.getRowCount();
        if (jumlah > 0) {
            ListSelectionModel selectionModel = tabel.getSelectionModel();
            selectionModel.setSelectionInterval(jumlah - 1, jumlah - 1);
            showing();
        }

    }//GEN-LAST:event_jLabel13MouseClicked

    private void txt_kreditFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_kreditFocusGained
        // TODO add your handling code here:
        txt_kredit.setText("");
    }//GEN-LAST:event_txt_kreditFocusGained

    private void txt_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaActionPerformed

    private void cbxkodeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxkodeItemStateChanged
        // TODO add your handling code here:
        getvalue();
    }//GEN-LAST:event_cbxkodeItemStateChanged

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
            java.util.logging.Logger.getLogger(Form_Penyetoran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Penyetoran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Penyetoran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Penyetoran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Penyetoran(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bodypanel;
    private javax.swing.JButton btn_cari;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton cari;
    private javax.swing.JComboBox<String> cbx_guru;
    private javax.swing.JComboBox<String> cbxkode;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel menupanel;
    private javax.swing.JTable tabel;
    private com.toedter.calendar.JDateChooser tgl_penyetoran;
    private javax.swing.JTextField txt_kelas;
    private javax.swing.JFormattedTextField txt_kredit;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_nis;
    private javax.swing.JTextField txt_nopen;
    private javax.swing.JTextField txt_norek;
    private javax.swing.JFormattedTextField txt_saldoakhir;
    private javax.swing.JFormattedTextField txt_saldoawal;
    // End of variables declaration//GEN-END:variables
}
