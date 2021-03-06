/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testpackage;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import panels.CompiledAttendanceView;
import panels.FacultyAttendanceView;
import panels.SchedulePanelDnD;
import panels.SettingPanel;
import panels.StudentMigrationPanel;
import panels.report.AdminReportPanel;
import panels.report.AttendanceSheetPanel;

/**
 *
 * @author yash
 */
public class TestFrame extends javax.swing.JFrame {

    
    /**
     * Creates new form TestFrame
     */
    public TestFrame(JPanel p) {
        initComponents();
        setVisible(true);
        jPanel1.add(p,BorderLayout.CENTER);
        //JOptionPane.showMessageDialog(this, "Press ok to continue");
        //System.out.println("Ok pressed");
        //this.repaint();
        this.pack();
    }

    public TestFrame(){
        initComponents();
        setVisible(true);
        //jLabel1.setText("\u092F\u0936");
        
    }
    
    private void initializePopupMenu(HashMap<String,Object> data,ActionListener al){
        Set keySet=data.keySet();
        Iterator<String> it=keySet.iterator();
        String temp;
        while(it.hasNext()){
            temp=it.next();
            System.out.println(temp);
            Object menu=findItem(data, temp,al);
            if(menu instanceof JMenu){
                jPopupMenu1.add((JMenu)menu);
            }else{
                jPopupMenu1.add((JMenuItem)menu);
            }
        }
    }
    
    private Object findItem(HashMap<String,Object> data, String key,ActionListener al){
        Object val=data.get(key);
        if(val instanceof String){
            JMenuItem item=new JMenuItem(val.toString());
            item.setName(key);
            item.addActionListener(al);
            return item;
        }else{
            HashMap map=(HashMap)val;
            JMenu menu=new JMenu(key);
            Set keySet=map.keySet();
            Iterator<String> it=keySet.iterator();
            String temp;
            while(it.hasNext()){
                temp=it.next();
                menu.add((JMenuItem)findItem(map, temp,al));
            }
            return menu;
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });
        jPanel1.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        // TODO add your handling code here:
        ActionListener listener=new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem item=(JMenuItem)e.getSource();
                System.out.println(item.getName());
            }
        };
        
        HashMap<String,Object> data=new HashMap<>();
        HashMap<String,Object> M1=new HashMap<>();
        
        HashMap<String,Object> M11=new HashMap<>();
        //M11.put("1.1.1", "1.1.1");
        //M11.put("1.1.2", "1.1.2");
        
        M1.put("1.1", "1.1");
        M1.put("1.2", M11);
        
        /*HashMap<String,Object> M2=new HashMap<>();
        M1.put("A", "A");
        M1.put("B", "B");*/
        data.put("1", M1);
        data.put("2", "Menu 2");
        data.put("3", "Menu 3");
        jPopupMenu1.removeAll();
        initializePopupMenu(data,null);
        jPopupMenu1.show(jPanel1, evt.getX(), evt.getY());
    }//GEN-LAST:event_jPanel1MouseClicked

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
            java.util.logging.Logger.getLogger(TestFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TestFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TestFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TestFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new TestFrame(new ImportStudentPanel()).setVisible(true);
                //new TestFrame(new AdminViewPanel()).setVisible(true);
                //new TestFrame(new StudentPanel()).setVisible(true);
                //new TestFrame(new AdminFacultyViewPanel()).setVisible(true);
                //new TestFrame(new SettingPanel()).setVisible(true);
                //new TestFrame(new SchedulePanelDnD(4,"A")).setVisible(true);
                //new TestFrame(new FacultyAttendanceView("f101")).setVisible(true);
                //new TestFrame(new AttendanceSheetPanel("f101")).setVisible(true);
                //new TestFrame(new CompiledAttendanceView()).setVisible(true);
                //new TestFrame(new AdminReportPanel()).setVisible(true);
                new TestFrame(new StudentMigrationPanel()).setVisible(true);
                //new TestFrame();
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    // End of variables declaration//GEN-END:variables
}
