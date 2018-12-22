/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LecturePanel.java
 *
 * Created on Nov 11, 2013, 12:22:16 AM
 */

package component;

import newattendancesystem.MainFrame;
import java.awt.Color;
import java.sql.Date;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import panels.EnterAttendance;
import panels.FacultyHome;
import sql.AttendanceSql;
import sql.SubjectSql;
import sql.StudentSql;

/**
 *
 * @author Hemant Borade
 */
public class LecturePanel extends javax.swing.JPanel implements Comparable<LecturePanel>{
    private int semester;
    private int periodno;
    private String section;
    private String scode;
    private String sname;
    private String clss;
    private Date date;
    private FacultyHome parent;
    public static StudentSql ssql=new StudentSql(null);
    public static AttendanceSql asql=new AttendanceSql();
    public static SubjectSql osql=new SubjectSql();


    /** Creates new form LecturePanel */
    public LecturePanel(int pno,String sc,String cls,int sem,String sec,Date dt,FacultyHome par) {
        initComponents();
        String time;
        periodno=pno;
        switch(pno){
            case 1:
                time="9:15 - 10:15";
                break;
            case 2:
                time="10:15 - 11:15";
                break;
            case 3:
                time="11:15 - 12:15";
                break;
            case 4:
                time="01:00 - 02:00";
                break;
            case 5:
                time="02:00 - 03:00";
                break;
            case 6:
                time="03:00 - 04:00";
                break;
            case 7:
                time="04:00 - 05:00";
                break;
            default:
                throw new IllegalArgumentException("Invalid Period Number");
        }
        semester=sem;
        section=sec;
        clss=cls;
        scode=sc;
        jLabel1.setText(time);
        jLabel1.setName(pno+"");
        jLabel2.setText(cls+" "+semester+" - \""+ section+"\"");
        if(par==null){
            throw new IllegalArgumentException("Parent Panel can not be Null.");
        }
        date=dt;
        parent=par;
        //OtherSql sql=new SubjectSql();
        sname=osql.getSubjectName(scode);
        //sql.close();
        jLabel3.setText(scode+" : "+sname);
        try{
            //StudentSql ssql=new StudentSql(null);
            //column not found;
            int bno=asql.getBatch(cls, sem);
            if(bno==-1){
                JOptionPane.showMessageDialog(parent, "Batch not found.");
                return;
            }
            //ssql.close();
            //AttendanceSql sql2=new AttendanceSql();
            int id=asql.findDetailID(dt, cls, semester, sec, scode,bno, pno);
            if(id==-1){
                jLabel4.setText("Feed Attendance");
            }else{
                jLabel4.setText("Edit Attendance");
            }
            //sql2.close();
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }


    public void attendanceSubmitted(){
        jLabel4.setText("Edit Attendance");
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14));
        jLabel1.setText("9:15 - 10:15");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14));
        jLabel2.setText("MCA 5 - \"B\"");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14));
        jLabel3.setText("MCA103");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14));
        jLabel4.setText("Feed Attendance");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel4MouseExited(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseEntered
        // TODO add your handling code here:
        jLabel4.setForeground(Color.BLUE);
        
    }//GEN-LAST:event_jLabel4MouseEntered

    private void jLabel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseExited
        // TODO add your handling code here:
        jLabel4.setForeground(Color.BLACK);
    }//GEN-LAST:event_jLabel4MouseExited

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        java.util.Date today=new java.util.Date();
        java.util.Date attendance=new java.util.Date(date.getTime());
        if(today.compareTo(attendance)<0){
            JOptionPane.showMessageDialog(MainFrame.mainFrame ,"You can not insert attendance of future date.");
            return;
        }

        if(jLabel4.getText().equalsIgnoreCase("Feed Attendance")){
            parent.setSelectedIndex(this);
            EnterAttendance panel=new EnterAttendance(clss,semester,section,scode,date,MainFrame.mainFrame.getUserID(),Integer.parseInt(jLabel1.getName()),parent);
            panel.indexOfTab=parent.indexOfTab;
            JScrollPane sc=(JScrollPane)MainFrame.mainFrame.formContainer.getComponentAt(parent.indexOfTab);
            JPanel p=(JPanel)sc.getViewport().getComponent(0);
            p.removeAll();
            p.add(panel);
            p.revalidate();
        }else{
            EnterAttendance panel=new EnterAttendance(clss,semester,section,scode,date,MainFrame.mainFrame.getUserID(),Integer.parseInt(jLabel1.getName()),parent,true);
            panel.indexOfTab=parent.indexOfTab;
            JScrollPane sc=(JScrollPane)MainFrame.mainFrame.formContainer.getComponentAt(parent.indexOfTab);
            JPanel p=(JPanel)sc.getViewport().getComponent(0);
            p.removeAll();
            p.add(panel);
            p.revalidate();
        }
        MainFrame.mainFrame.repaint();
    }//GEN-LAST:event_jLabel4MouseClicked

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables

    @Override
    public int compareTo(LecturePanel o) {
        return this.periodno-o.periodno;
    }

}
