/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ViewStudentPanel.java
 *
 * Created on Nov 9, 2013, 10:58:29 AM
 */

package panels;

import newattendancesystem.MainFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dominator
 */
public class ViewStudentPanel extends javax.swing.JPanel {

    public int indexOfTab;
    private Vector column;
    private Vector data;

    /** Creates new form ViewStudentPanel */
    public ViewStudentPanel(String head,String action,ActionListener listener,Vector c,Vector d) {
        initComponents();
        headlbl.setText(head);
        actionbtn.setText(action);
        actionbtn.addActionListener(listener);
        column=c;
        data=d;
        setTableData();
        actionbtn.setEnabled(false);
    }

    public ViewStudentPanel(String head,String action,ActionListener listener) {
        this(head,action,listener,null,null);
        initComponents();
    }

    public ViewStudentPanel(){
        initComponents();
        actionbtn.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                LeaveDetail pan=new LeaveDetail(ViewStudentPanel.this);
                pan.indexOfTab=indexOfTab;
               JScrollPane sc=(JScrollPane)MainFrame.mainFrame.formContainer.getComponentAt(indexOfTab);
               JPanel p=(JPanel)sc.getViewport().getComponent(0);
               p.removeAll();
               p.add(pan);
               p.revalidate();
               MainFrame.mainFrame.formContainer.setTitleAt(indexOfTab, "Leave Details");
            }
        });
    }

    public void setTableData(){
        if(column!=null && data!=null){
            DefaultTableModel tm=new DefaultTableModel(data,column);
            jTable1.setModel(tm);
        }
    }

    public void setListener(ActionListener listener){
        actionbtn.addActionListener(listener);
    }

    public void setData(Vector d){
        data=d;
    }

    public void setColumn(Vector c){
        column=c;
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headlbl = new javax.swing.JLabel();
        actionbtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        headlbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        headlbl.setText("Heading");

        actionbtn.setText("Action");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(actionbtn, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(actionbtn)
                    .addComponent(headlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton actionbtn;
    private javax.swing.JLabel headlbl;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

}
