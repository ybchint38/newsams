/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * StudentAttendanceSummary.java
 *
 * Created on Nov 25, 2013, 6:36:01 AM
 */

package panels;

import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import newattendancesystem.MainFrame;
import sql.AttendanceSql;
import sql.StudentSql;
import sql.SubjectSql;

/**
 *
 * @author apex predator
 */
public class StudentAttendanceSummary extends javax.swing.JPanel {

    private DefaultTableModel tm;
    private String eno;
    private boolean ready;
    private int bno;
    private String cls;
    private String section;
    private JPanel viewPanel;
    public int indexOfTab;
    
    /** Creates new form StudentAttendanceSummary */
    public StudentAttendanceSummary(String sid,JPanel par) {
        initComponents();
        viewPanel=par;
        tm=(DefaultTableModel)jTable1.getModel();
        eno=sid;
        setStudent();
    }

    public StudentAttendanceSummary(String sid,JPanel par,int sem) {
        initComponents();
        viewPanel=par;
        tm=(DefaultTableModel)jTable1.getModel();
        eno=sid;
        setStudent();
        ready=false;
        jComboBox1.removeAllItems();
        ready=true;
        jComboBox1.addItem(sem);
    }

    private void setStudent(){
        StudentSql sql=new StudentSql(eno);
        String data[]=sql.getData();
        sql.close();
        if(data!=null){
            jLabel4.setText(eno);
            jLabel5.setText(data[StudentSql.SNAME]);
            jLabel7.setText(data[StudentSql.CNO]);
            jLabel10.setText(data[StudentSql.BNO]);
            bno=Integer.parseInt(data[StudentSql.BNO]);
            section=data[StudentSql.SECTION];
            int n=Integer.parseInt(data[StudentSql.SEM]);
            cls=data[StudentSql.COURSE];
            ready=false;
            for(int i=1;i<=n;i++){
                jComboBox1.addItem(i);
            }
        }
        ready=true;
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Subject", "Total Lectures", "Present", "Absent", "Leave", "Attendance (in %)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getColumn(0).setMinWidth(170);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(170);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(170);
        jTable1.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(StudentAttendanceSummary.class, "StudentAttendanceSummary.jTable1.columnModel.title0")); // NOI18N
        jTable1.getColumnModel().getColumn(1).setMinWidth(90);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(90);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(90);
        jTable1.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(StudentAttendanceSummary.class, "StudentAttendanceSummary.jTable1.columnModel.title1")); // NOI18N
        jTable1.getColumnModel().getColumn(2).setMinWidth(65);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(65);
        jTable1.getColumnModel().getColumn(2).setMaxWidth(65);
        jTable1.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(StudentAttendanceSummary.class, "StudentAttendanceSummary.jTable1.columnModel.title2")); // NOI18N
        jTable1.getColumnModel().getColumn(3).setMinWidth(65);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(65);
        jTable1.getColumnModel().getColumn(3).setMaxWidth(65);
        jTable1.getColumnModel().getColumn(3).setHeaderValue(org.openide.util.NbBundle.getMessage(StudentAttendanceSummary.class, "StudentAttendanceSummary.jTable1.columnModel.title3")); // NOI18N
        jTable1.getColumnModel().getColumn(4).setMinWidth(60);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(60);
        jTable1.getColumnModel().getColumn(4).setMaxWidth(60);
        jTable1.getColumnModel().getColumn(4).setHeaderValue(org.openide.util.NbBundle.getMessage(StudentAttendanceSummary.class, "StudentAttendanceSummary.jTable1.columnModel.title4")); // NOI18N
        jTable1.getColumnModel().getColumn(5).setMinWidth(120);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(5).setMaxWidth(120);
        jTable1.getColumnModel().getColumn(5).setHeaderValue(org.openide.util.NbBundle.getMessage(StudentAttendanceSummary.class, "StudentAttendanceSummary.jTable1.columnModel.title5")); // NOI18N

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jButton1.setBackground(new java.awt.Color(204, 204, 204));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/close2ico.jpg"))); // NOI18N
        jButton1.setText(org.openide.util.NbBundle.getMessage(StudentAttendanceSummary.class, "StudentAttendanceSummary.jButton1.text")); // NOI18N
        jButton1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(org.openide.util.NbBundle.getMessage(StudentAttendanceSummary.class, "StudentAttendanceSummary.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(14, 14, 14)
                .addComponent(jButton1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText(org.openide.util.NbBundle.getMessage(StudentAttendanceSummary.class, "StudentAttendanceSummary.jLabel7.text")); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText(org.openide.util.NbBundle.getMessage(StudentAttendanceSummary.class, "StudentAttendanceSummary.jLabel8.text")); // NOI18N

        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText(org.openide.util.NbBundle.getMessage(StudentAttendanceSummary.class, "StudentAttendanceSummary.jLabel9.text")); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText(org.openide.util.NbBundle.getMessage(StudentAttendanceSummary.class, "StudentAttendanceSummary.jLabel2.text")); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText(org.openide.util.NbBundle.getMessage(StudentAttendanceSummary.class, "StudentAttendanceSummary.jLabel4.text")); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText(org.openide.util.NbBundle.getMessage(StudentAttendanceSummary.class, "StudentAttendanceSummary.jLabel10.text")); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText(org.openide.util.NbBundle.getMessage(StudentAttendanceSummary.class, "StudentAttendanceSummary.jLabel3.text")); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText(org.openide.util.NbBundle.getMessage(StudentAttendanceSummary.class, "StudentAttendanceSummary.jLabel6.text")); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText(org.openide.util.NbBundle.getMessage(StudentAttendanceSummary.class, "StudentAttendanceSummary.jLabel5.text")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
        if(ready && jComboBox1.getSelectedIndex()!=-1){
            for(int i=0;i<jTable1.getRowCount();i++){
                ((DefaultTableModel)jTable1.getModel()).removeRow(i);
            }
            int n=tm.getRowCount();
            for(int i=n-1;i>=0;i--){
                tm.removeRow(i);
            }
            int sem=Integer.parseInt(jComboBox1.getSelectedItem()+"");
            //Object subjects[]=getSubjects(sem);
            SubjectSql ssql=new SubjectSql();
            ArrayList<String> subjects=ssql.getSubjectCodes(sem);
            ssql.close();
            Vector temp;
            AttendanceSql sql=new AttendanceSql();
            //Date start;
            //Date end;
            int total;
            int present;
            int absent;
            int leave;
            Double per;
            int gTotal=0;
            int gPresent=0;
            int gAbsent=0;
            int gLeave=0;
            StudentSql sql2=new StudentSql(eno);
            for(String sub:subjects){
                //String sub=osub.toString();
                //System.out.println(sub);
                temp=new Vector();
                java.util.Date frst=sql.getFirstLecture(cls, sem,section, sub, bno);
                if(frst==null){
                    total=0;
                    present=0;
                    absent=0;
                    leave=0;
                    per=0.0;
                    //System.out.println(sub);
                }else{
                    //start=new Date(frst.getTime());
                    //end=new Date(sql.getLastLecture(cls, sem, sub, bno).getTime());
                    //total=sql.getTotalLectures(cls, sem, sub, bno);
                    present=sql2.getAttendanceCount(sub, sem, bno, AttendanceSql.PRESENT);
                    absent=sql2.getAttendanceCount(sub, sem, bno, AttendanceSql.ABSENT);
                    leave=sql2.getLeaveCount(sem);
                    total=present+absent+leave;
                    per=present*100.0/total;
                }
                temp.addElement(sub);
                temp.addElement(total);
                temp.addElement(present);
                temp.addElement(absent);
                temp.addElement(leave);
                temp.addElement(String.format("%.2f", per));
                tm.addRow(temp);
                gTotal+=total;
                gPresent+=present;
                gAbsent+=absent;
                gLeave+=leave;
            }
            sql.close();
            sql2.close();
            temp=new Vector();
            temp.addElement("");
            temp.addElement(gTotal);
            temp.addElement(gPresent);
            temp.addElement(gAbsent);
            temp.addElement(gLeave);
            per=gPresent*100.0/gTotal;
            temp.addElement(String.format("%.2f", per));
            tm.addRow(temp);
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        JScrollPane sc=(JScrollPane)MainFrame.mainFrame.formContainer.getComponentAt(indexOfTab);
        JPanel p=(JPanel)sc.getViewport().getComponent(0);
        p.removeAll();
        p.revalidate();
        p.add(viewPanel);
        p.revalidate();
        MainFrame.mainFrame.repaint();
    }//GEN-LAST:event_jButton1ActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    /*public static void main(String arg[]){
        javax.swing.JFrame frm=new javax.swing.JFrame();
        frm.setSize(800, 600);
        frm.add(new StudentAttendanceSummary());
        frm.setVisible(true);
    }*/
}
