/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package panels.report;

import java.awt.Cursor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.TreeSet;
import javax.swing.DefaultComboBoxModel;
import newattendancesystem.MainFrame;
import panels.PanelInterface;
import scheduleclass.ScheduleBase;
import sql.FacultySql;
import sql.StudentSql;
import sql.SubjectSql;

/**
 *
 * @author yash
 */
public class AttendanceSheetPanel extends javax.swing.JPanel implements PanelInterface{

    private String fid;
    private ArrayList<String[]> allotmentData;
    private ArrayList<String> time;
    private ArrayList<String> date;
    /**
     * Creates new form AttendanceSheetPanel
     */
    public AttendanceSheetPanel(String fid) {
        initComponents();
        //jProgressBar1.setVisible(false);
        this.fid =fid;
        FacultySql sql=new FacultySql(fid);
        allotmentData=sql.getAllotedSubject();
        sql.close();
        SubjectSql ssql=new SubjectSql();
        for(String temp[]:allotmentData){
            temp[3]=temp[3]+" - "+ssql.getSubjectName(temp[3]);
        }
        ssql.close();
        jButton1.setEnabled(false);
        setSemester();
        
    }

    private void setSemester(){
        TreeSet semester=new TreeSet();
        for(String temp[]:allotmentData){
            semester.add(Integer.parseInt(temp[1]));
        }
        jComboBox1.setModel(new DefaultComboBoxModel(semester.toArray()));
        int indx=jComboBox1.getSelectedIndex();
        if(indx!=-1){
            jComboBox1ActionPerformed(null);
        }
    }
    
    private void setSection(String sem){
        TreeSet section=new TreeSet();
        for(String temp[]:allotmentData){
            if(temp[1].equalsIgnoreCase(sem)){
                section.add(temp[2]);
            }
        }
        jComboBox2.setModel(new DefaultComboBoxModel(section.toArray()));
        int indx=jComboBox2.getSelectedIndex();
        if(indx!=-1){
            jComboBox2ActionPerformed(null);
        }
    }
    
    private void setSubject(String sem,String section){
        TreeSet subject=new TreeSet();
        for(String temp[]:allotmentData){
            if(temp[1].equalsIgnoreCase(sem) && temp[2].equalsIgnoreCase(section)){
                subject.add(temp[3]);
            }
        }
        jComboBox3.setModel(new DefaultComboBoxModel(subject.toArray()));
        int indx=jComboBox3.getSelectedIndex();
        if(indx!=-1){
            jComboBox3ActionPerformed(null);
        }
    }
    
    private void setDates(String sem,String section,String scode){
        FacultySql sql=new FacultySql(fid);
        ArrayList<Integer[]> val=sql.getLectureDaysFor(scode, Integer.parseInt(sem), section);
        sql.close();
        
        int n=val.size();
        date=new ArrayList<>();
        time=new ArrayList<>();
        ArrayList<String> list=new ArrayList<>();
        Date today=new Date();
        Calendar cal=Calendar.getInstance();
        cal.setTime(today);
        int todayDay=cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DAY_OF_MONTH, -(todayDay-Calendar.MONDAY));//no it contains date on monday
        Date mondayDate=cal.getTime();
        
        SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
        
        for(int i=0;i<n;i++){
            Integer temp[]=val.get(i);
            cal.add(Calendar.DAY_OF_MONTH, temp[0]-Calendar.MONDAY);
            list.add(df.format(cal.getTime()));
            date.add(i,df.format(cal.getTime()));
            time.add(i,ScheduleBase.LEC_TIME[temp[1].intValue()]);
            cal.setTime(mondayDate);
        }
        Collections.sort(list);
        
        list.add(0, "All");
        jComboBox4.setModel(new DefaultComboBoxModel(list.toArray()));
        jButton1.setEnabled(true);
    }
    
    private void generateReport(){
        String date=jComboBox4.getSelectedItem().toString();
        int sem=Integer.parseInt(jComboBox1.getSelectedItem().toString());
        String section=jComboBox2.getSelectedItem().toString();
        String subject=jComboBox3.getSelectedItem().toString();
        String scode=subject.split("-")[0].trim();
        if(date.equals("All")){
            int n=time.size();
            String tdate[]=new String[n];
            String ttime[]=new String[n];
            String temp;
            for(int i=0;i<n;i++){
                //temp=jComboBox4.getItemAt(i).toString();
                tdate[i]=this.date.get(i);
                ttime[i]=time.get(i);
            }
            report.handler.AttendanceReport ar=new report.handler.AttendanceReport();
            ar.attendanceSheet("", "MCA "+sem+" \""+section+"\"", getFacultyName(sem, section, scode), tdate, subject, ttime, getStudentList(sem, section));
        }else{
            int index=this.date.indexOf(date);
            report.handler.AttendanceReport ar=new report.handler.AttendanceReport();
            ar.attendanceSheet("", "MCA "+sem+" \""+section+"\"", getFacultyName(sem, section, scode), new String[]{this.date.get(index)}, subject, new String[]{this.time.get(index)}, getStudentList(sem, section));
        }
        
    }
    
    private String[][] getStudentList(int sem,String section){
        StudentSql sql=new StudentSql(null);
        String tdata[][]=sql.getAllStudentData(new String[]{"course","semester","section"}, new Object[]{"MCA",sem,section});
        sql.close();
        if(tdata!=null){
            String list[][]=new String[tdata.length][3];
                for(int i=0;i<list.length;i++){
                    list[i][0]=tdata[i][StudentSql.SRNO];
                    list[i][1]=tdata[i][StudentSql.SNAME];
            }
        return list;
        }
        return null;        
    }
    
    private String getFacultyName(int sem,String section,String scode){
        SubjectSql sql=new SubjectSql();
        ArrayList<String[]> val=sql.getFacultiesFor(scode,sem, section);
        int n=val.size();
        String faculty="";
        for(int i=0;i<n;i++){
            if(i==0){
                faculty=val.get(i)[1];
            }else{
                faculty=faculty +" \\ "+val.get(i)[1];
            }
        }
        return faculty;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(AttendanceSheetPanel.class, "AttendanceSheetPanel.jLabel1.text")); // NOI18N

        jComboBox1.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(AttendanceSheetPanel.class, "AttendanceSheetPanel.jLabel2.text")); // NOI18N

        jComboBox2.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(AttendanceSheetPanel.class, "AttendanceSheetPanel.jLabel3.text")); // NOI18N

        jComboBox3.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(AttendanceSheetPanel.class, "AttendanceSheetPanel.jLabel4.text")); // NOI18N

        jComboBox4.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/pdf16.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(AttendanceSheetPanel.class, "AttendanceSheetPanel.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox2, 0, 75, Short.MAX_VALUE))
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        setSection(jComboBox1.getSelectedItem().toString());
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
        setSubject(jComboBox1.getSelectedItem().toString(), jComboBox2.getSelectedItem().toString());
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
        String sem=jComboBox1.getSelectedItem().toString();
        String section=jComboBox2.getSelectedItem().toString();
        String scode=jComboBox3.getSelectedItem().toString().split("-")[0].trim();
        setDates(sem, section, scode);
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        //jProgressBar1.setVisible(true);
        new Thread(){

            @Override
            public void run() {
                MainFrame.mainFrame.startProcessing("Generating");
                jButton1.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                generateReport();
                jButton1.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                MainFrame.mainFrame.stopProcessing();
            }
            
        }.start();
        //jButton1.setEnabled(false);
        
        //jProgressBar1.setVisible(false);
        //jButton1.setEnabled(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables

    @Override
    public boolean destroy() {
        return true;
    }
}
