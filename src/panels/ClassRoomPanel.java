/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package panels;

import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import newattendancesystem.MainFrame;
import sql.ClassLabManagerSql;
import sql.SubjectSql;

/**
 *
 * @author yash
 */
public class ClassRoomPanel extends javax.swing.JPanel {

    /**
     * Creates new form ClassRoomPanel
     */
    public ClassRoomPanel() {
        initComponents();
        setRooms();
    }
    
    private void setRooms(){
        ClassLabManagerSql sql=new ClassLabManagerSql();
        ArrayList<String[]> val=sql.getAllRooms();
        sql.close();
        DefaultTableModel tm=(DefaultTableModel)jTable1.getModel();
        int n=tm.getRowCount();
        for(int i=n-1;i>=0;i--){
            tm.removeRow(i);
        }
        for(String row[]:val){
            tm.addRow(row);
        }
    }

    private void setSemester(){
        SubjectSql sql=new SubjectSql();
        String sem[]=sql.getCurrentSemester("MCA");
        sql.close();
        jComboBox2.setModel(new DefaultComboBoxModel<>(sem));
        jComboBox2ActionPerformed(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        try{
            jFormattedTextField1 = new javax.swing.JFormattedTextField(new javax.swing.text.MaskFormatter("##"))
            ;
            jButton1 = new javax.swing.JButton();
            jButton2 = new javax.swing.JButton();
            jButton3 = new javax.swing.JButton();
            jButton4 = new javax.swing.JButton();
            jLabel2 = new javax.swing.JLabel();
            jComboBox1 = new javax.swing.JComboBox();
            jLabel3 = new javax.swing.JLabel();
            jComboBox2 = new javax.swing.JComboBox();
            jLabel4 = new javax.swing.JLabel();
            jComboBox3 = new javax.swing.JComboBox();
            jButton5 = new javax.swing.JButton();

            setBackground(new java.awt.Color(255, 255, 255));

            jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                    "Room No", "Class"
                }
            ) {
                Class[] types = new Class [] {
                    java.lang.String.class, java.lang.String.class
                };
                boolean[] canEdit = new boolean [] {
                    false, false
                };

                public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
                }

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            });
            jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            jScrollPane1.setViewportView(jTable1);
            jTable1.getColumnModel().getColumn(0).setMinWidth(60);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(200);
            jTable1.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(ClassRoomPanel.class, "ClassRoomPanel.jTable1.columnModel.title0")); // NOI18N
            jTable1.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(ClassRoomPanel.class, "ClassRoomPanel.jTable1.columnModel.title1")); // NOI18N

            org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ClassRoomPanel.class, "ClassRoomPanel.jLabel1.text")); // NOI18N

        }catch(Exception e){}
        jFormattedTextField1.setText(org.openide.util.NbBundle.getMessage(ClassRoomPanel.class, "ClassRoomPanel.jFormattedTextField1.text")); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/Button-Add-icon16.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(ClassRoomPanel.class, "ClassRoomPanel.jButton1.text")); // NOI18N
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/button_delete16.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(ClassRoomPanel.class, "ClassRoomPanel.jButton2.text")); // NOI18N
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/prints.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButton3, org.openide.util.NbBundle.getMessage(ClassRoomPanel.class, "ClassRoomPanel.jButton3.text")); // NOI18N
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton4, org.openide.util.NbBundle.getMessage(ClassRoomPanel.class, "ClassRoomPanel.jButton4.text")); // NOI18N
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(ClassRoomPanel.class, "ClassRoomPanel.jLabel2.text")); // NOI18N

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MCA" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(ClassRoomPanel.class, "ClassRoomPanel.jLabel3.text")); // NOI18N

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(ClassRoomPanel.class, "ClassRoomPanel.jLabel4.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton5, org.openide.util.NbBundle.getMessage(ClassRoomPanel.class, "ClassRoomPanel.jButton5.text")); // NOI18N
        jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jFormattedTextField1))
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        jFormattedTextField1.setEnabled(enabled);
        jButton1.setEnabled(enabled);
        jButton2.setEnabled(enabled);
        jButton3.setEnabled(enabled);
        jButton4.setEnabled(enabled);
        jButton5.setEnabled(enabled);
        jComboBox1.setEnabled(enabled);
        jComboBox2.setEnabled(enabled);
        jComboBox3.setEnabled(enabled);
        jTable1.setEnabled(enabled);
        if(enabled){
            setRooms();
        }
    }

    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int rno=Integer.parseInt(jFormattedTextField1.getText());
        int n=jTable1.getRowCount();
        for(int i=0;i<n;i++){
            if(rno==Integer.parseInt(jTable1.getValueAt(i, 0).toString())){
                JOptionPane.showMessageDialog(this, "Class room already exist.");
                return;
            }
        }
        ClassLabManagerSql sql=new ClassLabManagerSql();
        sql.addRoom(rno);
        sql.close();
        setRooms();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int index=jTable1.getSelectedRow();
        if(index==-1){
            JOptionPane.showMessageDialog(this, "Please select room.");
        }else{
            int rno=Integer.parseInt(jTable1.getValueAt(index, 0).toString());
            ClassLabManagerSql sql=new ClassLabManagerSql();
            String msg=sql.deleteRoom(rno);
            sql.close();
            JOptionPane.showMessageDialog(this, msg);
            setRooms();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
        int sem=Integer.parseInt(jComboBox2.getSelectedItem().toString());
        SubjectSql sql=new SubjectSql();
        sql.close();
        String section[]=sql.getCurrentSection("MCA", sem);
        jComboBox3.setModel(new DefaultComboBoxModel<>(section));
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        setSemester();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        int index=jTable1.getSelectedRow();
        if(index==-1){
            JOptionPane.showMessageDialog(this, "Please select room.");
        }else{
            String cls=jTable1.getValueAt(index, 0).toString();
            if(cls.equalsIgnoreCase("NA")){
                ClassLabManagerSql sql=new ClassLabManagerSql();
                int sem=Integer.parseInt(jComboBox2.getSelectedItem().toString());
                String section=jComboBox3.getSelectedItem().toString();
                int old=sql.getRoomNo("MCA", sem, section);
                int newrno=Integer.parseInt(jTable1.getValueAt(index, 0).toString());
                if(old!=-1){
                    int option=JOptionPane.showConfirmDialog(this, "This class is held in class room no." + old+".\n Do you want to change its class room to "+newrno+"?","Confirmation",JOptionPane.YES_NO_OPTION);
                    if(option==JOptionPane.NO_OPTION){
                        sql.close();
                        return;
                    }
                    sql.removeRoom(old);
                }
                sql.putRoom("MCA", newrno, sem, section);
                sql.close();
                JOptionPane.showMessageDialog(this, "Changes saved.");
                setRooms();
            }else{
                JOptionPane.showMessageDialog(this, "Class room is not free.");
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        int index=jTable1.getSelectedRow();
        if(index==-1){
            JOptionPane.showMessageDialog(this, "Please select room.");
        }else{
            String cls=jTable1.getValueAt(index, 1).toString();
            int rno=Integer.parseInt(jTable1.getValueAt(index, 0).toString());
            int option=JOptionPane.showConfirmDialog(this, "Do you want to de-allocate class room "+rno+" from class "+cls+"?","Confirmation",JOptionPane.YES_NO_OPTION);
            if(option==JOptionPane.YES_OPTION){
                ClassLabManagerSql sql=new ClassLabManagerSql();
                sql.removeRoom(rno);
                sql.close();
                JOptionPane.showMessageDialog(this, "Changes saved.");
                setRooms();
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            // TODO add your handling code here:
            jTable1.print();
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(MainFrame.mainFrame, "Unable to connect to the printer.\nPlease check printer connection and try again.");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}