/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package panels;

import java.awt.Cursor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import newattendancesystem.MainFrame;
import report.handler.AttendanceReport;
import sql.AttendanceSql;
import sql.FacultySql;
import sql.StudentSql;
import sql.SubjectSql;

/**
 *
 * @author yash borade
 */
public class FacultyAttendanceView extends javax.swing.JPanel implements PanelInterface{

    public int indexOfTab;
    private String fid;
    private ArrayList<String[]> allotmentData; 
    private Vector column;
    private Vector data;
    /**
     * Creates new form FacultyAttendanceView
     */
    public FacultyAttendanceView(String fid) {
        initComponents();
        this.fid=fid;
        FacultySql sql=new FacultySql(fid);
        allotmentData=sql.getAllotedSubject();
        sql.close();
        setSemester();
        Date today=new Date();
        jDateChooser1.setDate(today);
        jDateChooser2.setDate(today);
    }

    private void setSemester(){
        TreeSet semester=new TreeSet();
        for(String temp[]:allotmentData){
            semester.add(Integer.parseInt(temp[1]));
        }
        jComboBox1.setModel(new DefaultComboBoxModel(semester.toArray()));
        jComboBox1ActionPerformed(null);
    }
    
    private void setSection(String sem){
        TreeSet section=new TreeSet();
        for(String temp[]:allotmentData){
            if(temp[1].equalsIgnoreCase(sem)){
                section.add(temp[2]);
            }
        }
        jComboBox2.setModel(new DefaultComboBoxModel(section.toArray()));
        jComboBox2ActionPerformed(null);
    }
    
    private void setSubject(String sem,String section){
        TreeSet subject=new TreeSet();
        for(String temp[]:allotmentData){
            if(temp[1].equalsIgnoreCase(sem) && temp[2].equalsIgnoreCase(section)){
                subject.add(temp[3]);
            }
        }
        jComboBox3.setModel(new DefaultComboBoxModel(subject.toArray()));
    }

    private void generateReport(){
        try{
            int sem=Integer.parseInt(jComboBox1.getSelectedItem().toString());
            String sec=jComboBox2.getSelectedItem().toString();
            String cls="MCA "+sem+" ("+sec+")";
            SubjectSql sql=new SubjectSql();
            String subject=jComboBox3.getSelectedItem().toString();
            ArrayList<String[]> fac=sql.getFacultiesFor(subject, sem, sec);
            subject=subject+" - "+sql.getSubjectName(subject);
            sql.close();
            int n=fac.size();
            System.out.println(n);
            String faculty=null;
            for(int i=0;i<n;i++){
                if(i==0){
                    faculty=fac.get(i)[1];
                }else{
                    faculty=faculty+" / "+fac.get(i)[1];
                }
            }
            
        
        String duration;
        SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
        duration="Period: "+df.format(jDateChooser1.getDate())+"-"+df.format(jDateChooser2.getDate());
        
        int rows=jTable1.getRowCount();
        if(rows<2){
            JOptionPane.showMessageDialog(this, "No Data Found.");
            return;
        }
        String names[][]=new String[rows][2];
        for(int i=0;i<names.length;i++){
            names[i][0]=jTable1.getValueAt(i, 0).toString();
            names[i][1]=jTable1.getValueAt(i, 1).toString();
        }
        
        int columns=jTable1.getColumnCount();
        String dates[]=new String[columns-2];
        for(int i=0;i<dates.length;i++){
            dates[i]=jTable1.getColumnName(i+2);
        }
        
        String data[][]=new String[rows][dates.length];
        for(int i=0;i<rows;i++){
            for(int j=0;j<dates.length;j++){
                data[i][j]=jTable1.getValueAt(i, j+2).toString();
            }
        }
        
        AttendanceReport report=new AttendanceReport();
        report.dailyAttendance(cls, subject, duration, faculty, dates, names, data);
        }catch(Exception e){
            
        }
    }
    
    private void viewAttendance(){
        Date from=jDateChooser1.getDate();
        Date to=jDateChooser2.getDate();
        int semester=Integer.parseInt(jComboBox1.getSelectedItem()+"");
        if(from.compareTo(to)>0){
            JOptionPane.showMessageDialog(MainFrame.mainFrame, "Invalid Date range.");
            return;
        }
        this.setEnabled(false);
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        
        String slist[][]=getStudentList();
        ArrayList<Long[]> adates=this.getAttendanceDates(from, to);
        column=new Vector();
        column.addElement("SrNo");
        column.addElement("Name");
        Calendar cal=Calendar.getInstance();
        for(Long date[]:adates){
            cal.setTimeInMillis(date[0]);
            column.addElement(cal.get(Calendar.DATE)+"/"+(cal.get(Calendar.MONTH)+1));
        }
        column.addElement("Total");
        column.addElement("in %");
        data=new Vector();
        Vector temp;
        
        int n=adates.size();
        temp=new Vector();
        temp.addElement("");//sr no
        temp.addElement("");//name
        for(int i=0;i<n;i++){
            temp.addElement("");
        }
        temp.addElement(n); //total class held
        temp.addElement(100.00); //total percentage
        data.addElement(temp);
        
        String scode=jComboBox3.getSelectedItem().toString();
        StudentSql ssql=new StudentSql(null);
        int bno=ssql.getBatch("MCA", semester);
        for(int i=0;i<slist.length;i++){
            temp=new Vector();
            //int indx=Integer.parseInt(slist[i][0])-1;
            //System.out.println(indx);
            temp.add(0, slist[i][0]);
            temp.add(1, slist[i][1]);
            ssql.setStudentID(slist[i][2]);
            Map<Long,Integer> sattnd=ssql.getAttendance(new java.sql.Date(from.getTime()), new java.sql.Date(to.getTime()), scode, semester, bno);
            //System.out.println(sattnd.size());
            int cnt=2;
            int attndCnt=0;
            for(int j=0;j<n;j++){
                //System.out.println(adates.get(cnt-2)[1]);
                Integer sts=sattnd.get(adates.get(cnt-2)[1]);
                if(sts==null){
                    temp.add(cnt, "NA");
                }else{
                    if(sts==AttendanceSql.PRESENT){
                        temp.add(cnt, "P");
                        attndCnt++;
                    }else if(sts==AttendanceSql.ABSENT){
                        temp.add(cnt, "A");
                    }else{
                        temp.add(cnt, "L");
                    }
                }
                cnt++;
            }
            temp.addElement(attndCnt);//total classes attended
            temp.addElement(Double.valueOf(String.format("%.2f",attndCnt*100.00/n)));//Attendance in percentage
            data.add(temp);
        }
        ssql.close();
        setData();
        applyTableProperties();

        this.setEnabled(true);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        
    }
    
    private String[][] getStudentList(){
        StudentSql sql=new StudentSql(null);
        String section=jComboBox2.getSelectedItem()+"";
        int semester=Integer.parseInt(jComboBox1.getSelectedItem()+"");
        String tdata[][]=sql.getAllStudentData(new String[]{"course","semester","section"}, new Object[]{"MCA",semester,section});
        sql.close();
        if(tdata!=null){
            String list[][]=new String[tdata.length][3];
                for(int i=0;i<list.length;i++){
                    list[i][0]=tdata[i][StudentSql.SRNO];
                    list[i][1]=tdata[i][StudentSql.SNAME];
                    list[i][2]=tdata[i][StudentSql.SID];
            }
        return list;
        }
        return null;
    }

    private ArrayList<Long[]> getAttendanceDates(Date from,Date to){
        String section=jComboBox2.getSelectedItem()+"";
        int semester=Integer.parseInt(jComboBox1.getSelectedItem()+"");
        AttendanceSql sql=new AttendanceSql();
        int bno=sql.getBatch("MCA", semester);
        ArrayList<Long[]> temp=sql.getAttendanceDates("MCA", semester,section, jComboBox3.getSelectedItem().toString(), bno, new java.sql.Date(from.getTime()), new java.sql.Date(to.getTime()));
        sql.close();
        return temp;
    }
    
    private void setData(){
        DefaultTableModel tm=new DefaultTableModel(data,column){

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                int lastCol=this.getColumnCount()-1;
                if(columnIndex==0 || columnIndex==lastCol-1 ){
                    //System.out.println("Integer");
                    return Integer.class;
                }else if(columnIndex==lastCol){
                    //System.out.println("Float");
                    return Float.class;
                }else{
                    //System.out.println("String");
                    return String.class;
                }
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable1.setModel(tm);
    }

    private void applyTableProperties(){
        jTable1.getColumn("SrNo").setPreferredWidth(40);
        jTable1.getColumn("Name").setPreferredWidth(210);
        
        int n=jTable1.getColumnCount();
        for(int i=2;i<n-1;i++){
            jTable1.getColumnModel().getColumn(i).setPreferredWidth(40);
        }
        jTable1.getColumnModel().getColumn(n-1).setPreferredWidth(60);
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
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(FacultyAttendanceView.class, "FacultyAttendanceView.jLabel5.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(FacultyAttendanceView.class, "FacultyAttendanceView.jLabel1.text")); // NOI18N

        jComboBox1.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(FacultyAttendanceView.class, "FacultyAttendanceView.jLabel2.text")); // NOI18N

        jComboBox2.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(FacultyAttendanceView.class, "FacultyAttendanceView.jLabel3.text")); // NOI18N

        jComboBox3.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N

        jDateChooser1.setBackground(new java.awt.Color(255, 255, 255));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(FacultyAttendanceView.class, "FacultyAttendanceView.jLabel4.text")); // NOI18N

        jDateChooser2.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/information_icon16.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(FacultyAttendanceView.class, "FacultyAttendanceView.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/pdf16.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(FacultyAttendanceView.class, "FacultyAttendanceView.jButton2.text")); // NOI18N
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
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox3)
                    .addComponent(jComboBox2)
                    .addComponent(jComboBox1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setOpaque(false);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.setFillsViewportHeight(true);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        try{
            String sem=jComboBox1.getSelectedItem().toString();
            setSection(sem);
        }catch(Exception e){}
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
        try{
            String sem=jComboBox1.getSelectedItem().toString();
            String section=jComboBox2.getSelectedItem().toString();
            setSubject(sem, section);
        }catch(Exception e){}
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        viewAttendance();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        new Thread(){

            @Override
            public void run() {
                MainFrame.mainFrame.startProcessing("Generating");
                jButton2.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                generateReport();
                jButton2.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                MainFrame.mainFrame.stopProcessing();
            }
            
        }.start();
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    @Override
    public boolean destroy() {
        return true;
    }
}
