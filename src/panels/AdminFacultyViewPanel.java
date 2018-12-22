/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package panels;

import component.CustomDialog;
import component.FacultySchedule;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import newattendancesystem.MainFrame;
import sql.FacultySql;

/**
 *
 * @author yash
 */
public class AdminFacultyViewPanel extends javax.swing.JPanel implements PanelInterface{

    private static String column[]=new String[]{"id","Name","Contact No","Department","Designation","Time Slot"};
    private String selectedID;
    public int indexOfTab;
    /**
     * Creates new form AdminFacultyViewPanel
     */
    public AdminFacultyViewPanel() {
        initComponents();
        findFaculties("");
        setPopupMenu();
    }

    private void setPopupMenu(){
        JMenuItem item;
        
        item=new JMenuItem("Edit");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editMenu();
            }
        });
        jPopupMenu1.add(item);
        
        item=new JMenuItem("Delete");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMenu();
            }
        });
        jPopupMenu1.add(item);
        
        item=new JMenuItem("Schedule");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scheduleMenu();
            }
        });
        jPopupMenu1.add(item);
        
        item=new JMenuItem("Roles");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rolesMenu();
            }
        });
        jPopupMenu1.add(item);
        
        item=new JMenuItem("Subject");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subjectMenu();
            }
        });
        jPopupMenu1.add(item);
        
        item=new JMenuItem("Reset Password");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x=JOptionPane.showConfirmDialog(AdminFacultyViewPanel.this, "Do you want to reset password for this account?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if(x==JOptionPane.YES_OPTION){
                    resetPasswordMenu();
                }
            }
        });
        jPopupMenu1.add(item);
        
    }
    
    private void editMenu(){
        //System.out.println("Edit Menu Selected.");
        if(selectedID!=null){
            FacultyPanel fp=new FacultyPanel(selectedID, this);
            fp.indexOfTab=indexOfTab;
            MainFrame.mainFrame.replacePanel(fp, indexOfTab, "Edit Information");
        }
    }
    
    private void deleteMenu(){
        //System.out.println("Edit Menu Selected.");
        if(selectedID!=null){
            int option=JOptionPane.showConfirmDialog(this, "Do you really want to remove faculty and all the information associated with this faculty?","Confirmation",JOptionPane.YES_NO_OPTION);
            if(option==JOptionPane.YES_OPTION){
                FacultySql sql=new FacultySql(selectedID);
                boolean result=sql.delete();
                if(result){
                    JOptionPane.showMessageDialog(this, "Successfully deleted.");
                    findFaculties(jTextField1.getText());
                }else{
                    JOptionPane.showMessageDialog(this, "Unable to delete this record, it may be involve in other activity.");
                }
                sql.close();
            }
        }
    }
    
    private void scheduleMenu(){
        //System.out.println("Schedule Menu Selected.");
        if(selectedID!=null){
            FacultySchedule fs=new FacultySchedule(selectedID);
            JOptionPane.showOptionDialog(this, fs, "", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"OK"}, null);
        }
    }
    
    private void rolesMenu(){
        //System.out.println("Roles Menu Selected.");
        if(selectedID!=null){
            AssignRolePanel fs=new AssignRolePanel(selectedID);
            new CustomDialog(MainFrame.mainFrame, true, fs,"Role Assignment").setVisible(true);
        }
    }
    
    private void subjectMenu(){
        //System.out.println("Subject Menu Selected.");
        if(selectedID!=null){
            FacultySubjectAllocation fsa=new FacultySubjectAllocation(selectedID);
            new CustomDialog(MainFrame.mainFrame, true, fsa,"Subject Allotment").setVisible(true);
        }
    }
    
    private void resetPasswordMenu(){
        //System.out.println("Subject Menu Selected.");
        if(selectedID!=null){
            FacultySql sql=new FacultySql(selectedID);
            JOptionPane.showMessageDialog(this, sql.resetPassword(), "Reset Password", JOptionPane.INFORMATION_MESSAGE);
            sql.close();
        }
    }
    public void refresh(){
        jButton3ActionPerformed(null);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jPopupMenu1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
                jPopupMenu1PopupMenuCanceled(evt);
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                jPopupMenu1PopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        setBackground(new java.awt.Color(102, 102, 102));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(AdminFacultyViewPanel.class, "AdminFacultyViewPanel.jLabel1.text")); // NOI18N

        jTextField1.setText(org.openide.util.NbBundle.getMessage(AdminFacultyViewPanel.class, "AdminFacultyViewPanel.jTextField1.text")); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/search16.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButton3, org.openide.util.NbBundle.getMessage(AdminFacultyViewPanel.class, "AdminFacultyViewPanel.jButton3.text")); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap(512, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addGap(6, 6, 6))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Name", "Contact No", "Department", "Designation", "Time Slot"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setFillsViewportHeight(true);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(AdminFacultyViewPanel.class, "AdminFacultyViewPanel.jTable1.columnModel.title0")); // NOI18N
        jTable1.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(AdminFacultyViewPanel.class, "AdminFacultyViewPanel.jTable1.columnModel.title1")); // NOI18N
        jTable1.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(AdminFacultyViewPanel.class, "AdminFacultyViewPanel.jTable1.columnModel.title2")); // NOI18N
        jTable1.getColumnModel().getColumn(3).setHeaderValue(org.openide.util.NbBundle.getMessage(AdminFacultyViewPanel.class, "AdminFacultyViewPanel.jTable1.columnModel.title3")); // NOI18N
        jTable1.getColumnModel().getColumn(4).setHeaderValue(org.openide.util.NbBundle.getMessage(AdminFacultyViewPanel.class, "AdminFacultyViewPanel.jTable1.columnModel.title4")); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/Button-Add-icon16.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(AdminFacultyViewPanel.class, "AdminFacultyViewPanel.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/prints.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(AdminFacultyViewPanel.class, "AdminFacultyViewPanel.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 847, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        // TODO add your handling code here:
        findFaculties(jTextField1.getText());
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        findFaculties(jTextField1.getText());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
        // TODO add your handling code here:
        if(SwingUtilities.isRightMouseButton(evt)){
            //int x=evt.getX();
            //int y=evt.getY();
            int rowIndex=jTable1.rowAtPoint(evt.getPoint());
            //TableColumnModel tcm=jTable1.getColumnModel();
            //int colIndex=tcm.getColumnIndex("id");
            selectedID=jTable1.getValueAt(rowIndex,0).toString();
            jTable1.setRowSelectionInterval(rowIndex, rowIndex);
            jPopupMenu1.show(jTable1, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jTable1MousePressed

    private void jPopupMenu1PopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jPopupMenu1PopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jPopupMenu1PopupMenuWillBecomeInvisible

    private void jPopupMenu1PopupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jPopupMenu1PopupMenuCanceled
        // TODO add your handling code here:
        selectedID=null;
    }//GEN-LAST:event_jPopupMenu1PopupMenuCanceled

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       // TODO add your handling code here:
        FacultyPanel fp=new FacultyPanel(this);
        fp.indexOfTab=indexOfTab;
        MainFrame.mainFrame.replacePanel(fp, indexOfTab, "Add New Faculty");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            // TODO add your handling code here:
            jTable1.print();
            
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(MainFrame.mainFrame, "Unable to connect to the printer.\nPlease check printer connection and try again.");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void findFaculties(String name){
        FacultySql sql=new FacultySql(null);
        String data[][]=sql.getAllFaculties(name);
        sql.close();
        jTable1.setModel(new DefaultTableModel(data, column){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        //jTable1.removeColumn(jTable1.getColumn("id"));
        TableColumn tc=jTable1.getColumn("id");
        tc.setMinWidth(0);
        tc.setMaxWidth(0);
        tc.setPreferredWidth(0);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    @Override
    public boolean destroy() {
        return true;
    }
}
