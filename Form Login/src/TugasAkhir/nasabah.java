/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TugasAkhir;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author khoirunnisa
 */
public class nasabah extends javax.swing.JFrame {
    DefaultTableModel model = new DefaultTableModel();
    Connection conn; 
    Statement st; 
    ResultSet rs; 
    Date tanggal;

    /**
     * Creates new form nasabah
     */
    public nasabah() {
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2
                - this.getSize().width / 2, dim.height / 6
                - this.getSize().height / 6);

        conn = null;
        st = null;
        rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/tabungan";
            String user = "root";
            String pwd = "";

            conn = (com.mysql.jdbc.Connection) DriverManager.getConnection(url, user, pwd);
            // System.out.println("Terkoneksi DB");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed connect to Database :" + e,
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            //  System.out.println("Error Connection Karena :" + e);
        }
        
        tabel.setModel(model);
        tabel1.setModel(model);
        model.addColumn("No");
        model.addColumn("Tanggal Dibuka");
        model.addColumn("No Rekening");
        model.addColumn("NIS");
        model.addColumn("Nama");
        model.addColumn("Jenjang");
        model.addColumn("Kelas");
        model.addColumn("Telepon");
        model.addColumn("Saldo");
        
        tampil();
        clear();
        
        //hidupNavigation();
        btn_tambah.setEnabled(true);
        btn_edit.setEnabled(true);
        btn_hapus.setEnabled(true);
        tgl_dibuka.setEnabled(false);
        txt_norekening.setEnabled(false);
        txt_nis.setEnabled(false);  
        txt_nama.setEnabled(false);
        cbx_kelas.setEnabled(false);
        cbx_jenjang.setEnabled(false);
        txt_tlp.setEnabled(false);
        txt_saldo.setEnabled(false);
        
    }
    
        private void clear() {
        tgl_dibuka.setDate(new Date());
        txt_norekening.setText("");
        txt_nis.setText("");
        txt_nama.setText("");
        cbx_jenjang.setSelectedIndex(0);
        cbx_kelas.setSelectedIndex(0);
        txt_tlp.setText("");
        txt_saldo.setText("0");

    }
    
  
    
    private void tampil() {
        model.fireTableDataChanged();
        model.getDataVector().removeAllElements();
        String query = "select * from tb_nasabah order by nis_siswa ASC";
        try {
            st = (com.mysql.jdbc.Statement) conn.createStatement();
            rs = st.executeQuery(query);
            int no = 1;
            while (rs.next()) {
                Object[] o = new Object[]{
                    no++,
                    rs.getDate("tanggal_dibuka"),
                    rs.getString("nmr_rekening"),
                    rs.getString("nis_siswa"),
                    rs.getString("nama_siswa"),
                    rs.getString("jenjang"),
                    rs.getString("kelas"),
                    rs.getString("no_telp"),
                    rs.getInt("saldo")
                };
                model.addRow(o);
            }
        } catch (Exception e) {
            System.out.println("Error Tampil :" + e);
        }
    }
    

    private void on() {
        txt_nis.setEnabled(true);
        txt_nama.setEnabled(true);
        cbx_jenjang.setEnabled(true);
        cbx_kelas.setEnabled(true);
        txt_tlp.setEnabled(true);
        txt_saldo.setEnabled(true);
    }

    private void matikan() {
        tgl_dibuka.setEnabled(false);
        txt_nis.setEnabled(false);
        txt_nama.setEnabled(false);
        txt_tlp.setEnabled(false);
        txt_saldo.setEnabled(false);
        
        
    }
    
    
    private void showing() {
        if (tabel.getSelectedRow() > -1) { 
            if (tabel1.getSelectedRow() > -1)
                
            txt_norekening.setText(model.getValueAt(tabel.getSelectedRow(), 2).toString());
            txt_nis.setText(model.getValueAt(tabel.getSelectedRow(), 3).toString());
            txt_nama.setText(model.getValueAt(tabel.getSelectedRow(), 4).toString());
            cbx_jenjang.setSelectedItem(model.getValueAt(tabel.getSelectedRow(), 5).toString());
            cbx_kelas.setSelectedItem(model.getValueAt(tabel.getSelectedRow(), 6).toString());
            txt_tlp.setText(model.getValueAt(tabel.getSelectedRow(),7).toString());
            txt_saldo.setValue(Integer.parseInt(model.getValueAt(tabel.getSelectedRow(), 8).toString()));
            
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateInString = model.getValueAt(tabel.getSelectedRow(), 1).toString();
            
            try {
                tgl_dibuka.setDate(formatter.parse(dateInString));
            } catch (ParseException ex) {
                System.out.println("Error Pengambilan Tanggal Pada Tabel karena: " + ex);
            }

            btn_tambah.setEnabled(true);
            btn_edit.setEnabled(true);
            btn_hapus.setEnabled(true);
            btn_cari.setEnabled(true);
        }
        }
    
    public void NomorRekening() {
        Date tanggal = new Date();
        String noSlip = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String notransaksi = "";
        Date tanggal_dibuka = null;
       
        String urut = "";
        String query = "select tanggal_dibuka, nmr_rekening from tb_nasabah order by nis_siswa DESC LIMIT 0,1";
        try {
            st = (com.mysql.jdbc.Statement) conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                notransaksi = rs.getString("nmr_rekening");
                tanggal_dibuka = rs.getDate("tanggal_dibuka"); //disini yang diubah
            }
            if (tanggal_dibuka != null && dateFormat.format(tanggal).equals(dateFormat.format(tanggal_dibuka))) {
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
                noSlip = "RS" + dateFormat.format(tanggal) + "-" + urut;
            } else {
                noSlip = "RS" + dateFormat.format(tanggal) + "-001";
            }
            txt_norekening.setText(noSlip);
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
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        mainpanel = new javax.swing.JPanel();
        tabel_nasabah = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel1 = new javax.swing.JTable();
        tbh_nasabah = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tgl_dibuka = new com.toedter.calendar.JDateChooser();
        txt_tlp = new javax.swing.JTextField();
        txt_norekening = new javax.swing.JTextField();
        txt_nis = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cbx_jenjang = new javax.swing.JComboBox<>();
        cbx_kelas = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_nama = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_saldo = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        btn_tambah = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_cari = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bodypanel.setBackground(new java.awt.Color(255, 51, 255));
        bodypanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(23, 69, 23));

        jPanel2.setBackground(new java.awt.Color(25, 77, 25));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Tambah Siswa");

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_add_25px.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel9))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(25, 77, 25));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Lihat Data Siswa");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(jLabel10)
                .addContainerGap(91, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 674, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(109, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        bodypanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1250, -1));

        mainpanel.setBackground(new java.awt.Color(204, 255, 0));
        mainpanel.setLayout(new java.awt.CardLayout());

        tabel_nasabah.setBackground(new java.awt.Color(255, 255, 255));

        tabel1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
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

        javax.swing.GroupLayout tabel_nasabahLayout = new javax.swing.GroupLayout(tabel_nasabah);
        tabel_nasabah.setLayout(tabel_nasabahLayout);
        tabel_nasabahLayout.setHorizontalGroup(
            tabel_nasabahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabel_nasabahLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        tabel_nasabahLayout.setVerticalGroup(
            tabel_nasabahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabel_nasabahLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(115, Short.MAX_VALUE))
        );

        mainpanel.add(tabel_nasabah, "card3");

        tbh_nasabah.setBackground(new java.awt.Color(255, 255, 255));
        tbh_nasabah.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setText("/");
        tbh_nasabah.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 240, -1, 20));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel2.setText("Tanggal Dibuka");
        tbh_nasabah.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 32, -1, -1));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel3.setText("No Rekening");
        tbh_nasabah.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel5.setText("NIS");
        tbh_nasabah.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, -1, -1));

        tgl_dibuka.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbh_nasabah.add(tgl_dibuka, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, 240, 40));

        txt_tlp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_tlp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tlpActionPerformed(evt);
            }
        });
        tbh_nasabah.add(txt_tlp, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 280, 240, 40));

        txt_norekening.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_norekening.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_norekeningActionPerformed(evt);
            }
        });
        tbh_nasabah.add(txt_norekening, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, 240, 40));

        txt_nis.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_nis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nisActionPerformed(evt);
            }
        });
        tbh_nasabah.add(txt_nis, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 240, 40));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel4.setText("Nama");
        tbh_nasabah.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, -1, -1));

        cbx_jenjang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbx_jenjang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Jenjang", "SD", "SMP", "SMA" }));
        cbx_jenjang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_jenjangActionPerformed(evt);
            }
        });
        tbh_nasabah.add(cbx_jenjang, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 230, 100, 40));

        cbx_kelas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbx_kelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Kelas", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII" }));
        tbh_nasabah.add(cbx_kelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 230, 90, 40));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel6.setText("Saldo");
        tbh_nasabah.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, -1, -1));

        jLabel7.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel7.setText("Jenjang/Kelas");
        tbh_nasabah.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, -1, -1));

        txt_nama.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaActionPerformed(evt);
            }
        });
        tbh_nasabah.add(txt_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 180, 240, 40));

        jLabel8.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel8.setText("No. Telp");
        tbh_nasabah.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 290, -1, -1));

        txt_saldo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_saldo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_saldoFocusGained(evt);
            }
        });
        txt_saldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_saldoActionPerformed(evt);
            }
        });
        tbh_nasabah.add(txt_saldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 330, 240, 40));

        tabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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

        tbh_nasabah.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 30, 730, 340));

        btn_tambah.setText("Tambah");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });
        tbh_nasabah.add(btn_tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 400, -1, -1));

        btn_edit.setText("Edit");
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });
        tbh_nasabah.add(btn_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 400, -1, -1));

        btn_hapus.setText("HAPUS");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        tbh_nasabah.add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 400, -1, -1));

        btn_cari.setText("Cari");
        btn_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cariActionPerformed(evt);
            }
        });
        tbh_nasabah.add(btn_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 400, -1, -1));

        jButton1.setText("refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        tbh_nasabah.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 400, -1, -1));

        jLabel12.setText("pertama");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });
        tbh_nasabah.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 400, -1, -1));

        jLabel13.setText("sebelumnya");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        tbh_nasabah.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 430, -1, -1));

        jLabel14.setText("selanjutnya");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        tbh_nasabah.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 400, -1, -1));

        jLabel15.setText("terakhir");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });
        tbh_nasabah.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 430, -1, -1));

        mainpanel.add(tbh_nasabah, "card2");

        bodypanel.add(mainpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 1250, 500));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bodypanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bodypanel, javax.swing.GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_tlpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tlpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tlpActionPerformed

    private void txt_norekeningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_norekeningActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_norekeningActionPerformed

    private void txt_nisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nisActionPerformed

    private void txt_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaActionPerformed

    private void cbx_jenjangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_jenjangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbx_jenjangActionPerformed

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        // TODO add your handling code here:
        //remove panel
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();
        
        //add panel
        mainpanel.add(tbh_nasabah);
        mainpanel.repaint();
        mainpanel.revalidate();
    }//GEN-LAST:event_jPanel2MouseClicked

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
        //remove panel
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();
        
        //add panel
        mainpanel.add(tabel_nasabah);
        mainpanel.repaint();
        mainpanel.revalidate();
    }//GEN-LAST:event_jPanel3MouseClicked

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
            if (btn_tambah.getText().equals("Tambah")) {
            btn_tambah.setText("Simpan");
            btn_tambah.setEnabled(true);

            on();
            clear();
            //navigationOFF();
            NomorRekening();
            
            
        } else if (btn_tambah.getText().equals("Simpan")) {
            if (tgl_dibuka.getDate() != null 
                    
                    && !txt_norekening.getText().equals("")
                    && !txt_nis.getText().equals("")
                    && !txt_nama.getText().equals("")
                    && !cbx_jenjang.getSelectedItem().equals("")
                    && !cbx_kelas.getSelectedItem().equals("")
                    && !txt_tlp.getText().equals("")
                    && !txt_saldo.getText().equals("")){
                    
               tanggal = tgl_dibuka.getDate();
               java.sql.Date tanggalSql = new java.sql.Date(tanggal.getTime());
            
            
                   String query = "insert into tb_nasabah (tanggal_dibuka,nmr_rekening,nis_siswa,nama_siswa,jenjang, kelas, no_telp, saldo)"  
                        + " values('" 
                        + tanggalSql + "','"
                        + txt_norekening.getText() + "','"
                        + txt_nis.getText() + "','"
                        + txt_nama.getText() + "','"
                        + cbx_jenjang.getSelectedItem()+ "','"
                        + cbx_kelas.getSelectedItem()+ "','"
                        + txt_tlp.getText() + "','"
                        + txt_saldo.getText() + "')";
                
                try {
                    st = (com.mysql.jdbc.Statement) conn.createStatement();
                    st.executeUpdate(query);
                    JOptionPane.showMessageDialog(null, "Berhasil Menyimpan Data User", "Sukses",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Gagal Menyimpan Data User", "Gagal",
                            JOptionPane.ERROR_MESSAGE);
                    System.out.println("Error Simpan =" + e);
                }
                
                clear();
                tampil();

                //navigationON();
                btn_tambah.setEnabled(true);
                btn_tambah.requestFocus();
                btn_tambah.setText("Tambah");
            } else {
                JOptionPane.showMessageDialog(null, "Gagal Menyimpan Data Karena Data Belum Lengkap", "Gagal",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void txt_saldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_saldoActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txt_saldoActionPerformed

    private void txt_saldoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_saldoFocusGained
        // TODO add your handling code here:
        txt_saldo.setText("");
    }//GEN-LAST:event_txt_saldoFocusGained

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
        int pesan = JOptionPane.showConfirmDialog(null, "Anda Yakin Akan Menghapus Data Ini ?", "Question", JOptionPane.OK_CANCEL_OPTION);
        if (pesan == 0) {

            String query = "delete from tb_nasabah where nis_siswa='" + txt_nis.getText() + "'";
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
            tampil();
            matikan();
            //hidupNavigation();
            btn_tambah.setEnabled(true);
            btn_edit.setEnabled(true);
            btn_hapus.setEnabled(true);
            //btn_cari.setEnabled(true);
            
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        // TODO add your handling code here:
        if (btn_edit.getText().equals("Edit")) {
            btn_edit.setText("Update");
            btn_tambah.setEnabled(true);
            btn_edit.setEnabled(true);
            btn_hapus.setEnabled(true);
            //btn_cari.setEnabled(true);
            
            on();
            //hidupNavigation();
            txt_nis.requestFocus();

        } else if (btn_edit.getText().equals("Update")) {
            if (tgl_dibuka.getDate() != null
                    
                    && !txt_nis.getText().equals("") 
                    && !txt_nama.getText().equals("")
                    && !cbx_jenjang.getSelectedItem().equals("") 
                    && !cbx_kelas.getSelectedItem().equals("")  
                    && !txt_tlp.getText().equals("")
                    && !txt_saldo.getValue().equals(0)) {

                java.util.Date tglDibuka = tgl_dibuka.getDate();
                java.sql.Date tanggalSql = new java.sql.Date(tglDibuka.getTime());

                
                int saldo = Integer.valueOf(txt_saldo.getText().replaceAll("\\D+", ""));
                String query = "update tb_nasabah set "
                       
                        
                        + " nama_siswa='" + txt_nama.getText() + "',"
                        + " jenjang='" + cbx_jenjang.getSelectedItem()+ "',"
                        + " kelas='" + cbx_kelas.getSelectedItem()+ "',"
                        + " no_telp='" + txt_tlp.getText() + "',"
                        + " saldo='" + saldo + "'"
                        
                        + "WHERE nis_siswa = '"+txt_nis.getText()+"'";
                        

                try {
                    st = (com.mysql.jdbc.Statement) conn.createStatement();
                    st.executeUpdate(query);
                    JOptionPane.showMessageDialog(null, "Berhasil Merubah Data User", "Sukses",
                            JOptionPane.INFORMATION_MESSAGE);
                    clear();
                    tampil();
                    matikan();
                    //hidupNavigation();
                    btn_tambah.setEnabled(true);
                    btn_edit.setEnabled(true);
                    btn_hapus.setEnabled(true);
                    btn_cari.setEnabled(true);
                    btn_tambah.requestFocus();
                    btn_edit.setText("Edit");
                    
                
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e, "Gagal",
                            JOptionPane.ERROR_MESSAGE);
                    System.out.println("Error Update =" + e);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Gagal Mengubah Data Karena Data Belum Lengkap", "Gagal",
                        JOptionPane.WARNING_MESSAGE);
                
            }                    
        }
    }//GEN-LAST:event_btn_editActionPerformed

    private void tabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelMouseClicked
        // TODO add your handling code here:
        showing();
    }//GEN-LAST:event_tabelMouseClicked

    private void tabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel1MouseClicked
        // TODO add your handling code here:
        showing();
    }//GEN-LAST:event_tabel1MouseClicked

    private void btn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cariActionPerformed
        // TODO add your handling code here:
        String cari = JOptionPane.showInputDialog("Pencarian Berdasarkan "
                + "NIS dan Nama :");
        if (cari != null) {
            String query = "select * from tb_nasabah where nis_siswa like '%" + cari.replace("'", "\\'") + "%' or nama_siswa like '%" + cari.replace("'", "\\'") + "%' order by nis_siswa ASC";
            try {
                model.fireTableDataChanged();
                model.getDataVector().removeAllElements();
                st = (com.mysql.jdbc.Statement) conn.createStatement();
                rs = st.executeQuery(query);
                int no = 1;
                while (rs.next()) {
                    Object[] o = new Object[]{
                        no++,
                        rs.getDate("tanggal_dibuka"),
                        rs.getString("nmr_rekening"),
                        rs.getString("nis_siswa"),
                        rs.getString("nama_siswa"),
                        rs.getString("jenjang"),
                        rs.getString("kelas"),
                        rs.getString("no_telp"),
                        rs.getInt("saldo")
                        

                    };
                    model.addRow(o);
                }
            } catch (Exception e) {
                System.out.println("Error Pencarian :" + e);
            }
           
        }
    }//GEN-LAST:event_btn_cariActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        tampil();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        // TODO add your handling code here:
        int jumlah = model.getRowCount();
        if (jumlah > 0) {
            ListSelectionModel selectionModel = tabel.getSelectionModel();
            selectionModel.setSelectionInterval(0, 0);
            showing();
        }
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
        ListSelectionModel selectionModel = tabel.getSelectionModel();
        if (tabel.getSelectedRow() >= 0) {
            selectionModel.setSelectionInterval(tabel.getSelectedRow() - 1,
                    tabel.getSelectedRow() - 1);
            showing();

        }
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        // TODO add your handling code here:
        int jumlah = model.getRowCount();
        ListSelectionModel selectionModel = tabel.getSelectionModel();
        if (tabel.getSelectedRow() <= jumlah - 2) {
            selectionModel.setSelectionInterval(tabel.getSelectedRow() + 1, tabel.getSelectedRow() + 1);
            showing();

        }
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        // TODO add your handling code here:
        int jumlah = model.getRowCount();
        if (jumlah > 0) {
            ListSelectionModel selectionModel = tabel.getSelectionModel();
            selectionModel.setSelectionInterval(jumlah - 1, jumlah - 1);
            showing();
        }
    }//GEN-LAST:event_jLabel15MouseClicked

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
            java.util.logging.Logger.getLogger(nasabah.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(nasabah.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(nasabah.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(nasabah.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new nasabah().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bodypanel;
    private javax.swing.JButton btn_cari;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JComboBox<String> cbx_jenjang;
    private javax.swing.JComboBox<String> cbx_kelas;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JTable tabel;
    private javax.swing.JTable tabel1;
    private javax.swing.JPanel tabel_nasabah;
    private javax.swing.JPanel tbh_nasabah;
    private com.toedter.calendar.JDateChooser tgl_dibuka;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_nis;
    private javax.swing.JTextField txt_norekening;
    private javax.swing.JFormattedTextField txt_saldo;
    private javax.swing.JTextField txt_tlp;
    // End of variables declaration//GEN-END:variables
}
