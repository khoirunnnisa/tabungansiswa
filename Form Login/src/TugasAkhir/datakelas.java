/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TugasAkhir;


import javax.swing.JFrame;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author khoirunnisa
 */
public class datakelas extends javax.swing.JFrame {
    
    Connection conn;
    Statement st;
    ResultSet rs;
    
    /**
     * Creates new form dataguru
     */
    public datakelas() {
        initComponents();
    setLocationRelativeTo(this);
       
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
            JOptionPane.showMessageDialog(null, "Failed to Connect Database :" + e,
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            System.out.println("Error :" + e);
        }
        getcombobox();
    }
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
           txt_kelas1.setText(rs.getString("kelas"));
            }
        } catch (Exception e) {
            System.out.println("Error Tampilkan :" + e);
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbxkode = new javax.swing.JComboBox<>();
        txt_kelas1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(23, 69, 23));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setText("Kode ");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel2.setText("Kelas");

        cbxkode.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        cbxkode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Pilih Kode-" }));
        cbxkode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxkodeItemStateChanged(evt);
            }
        });

        txt_kelas1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_kelas1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxkode, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(93, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbxkode, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_kelas1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(86, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(datakelas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(datakelas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(datakelas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(datakelas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new datakelas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbxkode;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txt_kelas1;
    // End of variables declaration//GEN-END:variables
}
