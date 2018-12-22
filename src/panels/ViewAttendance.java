/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ViewAttendance.java
 *
 * Created on Nov 5, 2013, 12:32:31 AM
 */

package panels;

import java.awt.Cursor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Vector;
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
 * @author Hemant Borade
 */
public class ViewAttendance extends javax.swing.JPanel implements PanelInterface{

    public int indexOfTab;
    private Date startDate;
    //private String fid;
    private int role;
    private Vector column;
    private Vector data;
    private boolean ready;
    /** Creates new form ViewAttendance */

    public ViewAttendance(){
        this(null,FacultySql.ADMIN);
    }
    public ViewAttendance(String id,int r) {
        initComponents();
        //fid=id;
        role=r;
        Date dt=new Date();
        jDateChooser1.setDate(dt);
        jDateChooser2.setDate(dt);
        String batches[];
        if(role==FacultySql.ADMIN){
            StudentSql sql=new StudentSql(null);
            batches=sql.getBatches("MCA");
            sql.close();
        }else{
            /*FacultySql sql=new FacultySql(fid);
            batches=sql.getBatches();
            sql.close();*/
            batches=null;
            jComboBox2.setEnabled(false);
        }
        if(batches!=null){
        for(int i=0;i<batches.length;i++){
            jComboBox2.addItem(batches[i]);
        }
        }
        jComboBox2.setSelectedIndex(-1);
        ready=true;
    }

    public ViewAttendance(int sem,String sec,String scode){
        initComponents();
        //semester=sem;
        //section=sec;
        //subject=scode;
        jPanel5.setVisible(false);
    }
    
    private void generateReport(){
        try{
            int sem=Integer.parseInt(jComboBox3.getSelectedItem().toString());
            String sec=jComboBox4.getSelectedItem().toString();
            String cls="MCA "+sem+" ("+sec+")";
            SubjectSql sql=new SubjectSql();
            String subject=jComboBox5.getSelectedItem().toString();
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
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setText("Batch :");

        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel6.setText("Semester :");

        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jLabel7.setText("Section :");

        jComboBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox4ItemStateChanged(evt);
            }
        });

        jLabel8.setText("Subject :");

        jComboBox5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox5ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.Alignment.LEADING, 0, 75, Short.MAX_VALUE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox5, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setText("To:");

        jLabel2.setText("From:");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/information_icon16.png"))); // NOI18N
        jButton1.setText("View");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jDateChooser1.setOpaque(false);

        jDateChooser2.setOpaque(false);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/pdf16.png"))); // NOI18N
        jButton2.setText("Save as PDF");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 251, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addGap(0, 252, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 69, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addGap(0, 69, Short.MAX_VALUE)))
        );

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Attendance Details");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 696, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(2, 2, 2))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jTable1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.setEnabled(false);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        //System.out.println("jComboBox3statechange "+ready);
        
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
        if(ready){
            int sem=Integer.parseInt(jComboBox3.getSelectedItem()+"");
            int bno=Integer.parseInt(jComboBox2.getSelectedItem().toString());
        
            String section[]=null;
            if(role==FacultySql.ADMIN){
                StudentSql sql=new StudentSql(null);
                section=sql.getSection("MCA",bno,sem);
                sql.close();
            }/*else{
                FacultySql sql=new FacultySql(fid);
                /ArrayList<String> sectn=sql.getAllSection(sem);
                jComboBox4.removeAllItems();
                ready=false;
                for(String t:sectn){
                    jComboBox4.addItem(t);
                }
                ready=true;
                sql.close();
                return;
            }*/
            ready=false;
            jComboBox4.removeAllItems();
            if(section!=null){
                for(int i=0;i<section.length;i++){
                    jComboBox4.addItem(section[i]);
                }
            }
            ready=true;
            jComboBox4.setSelectedIndex(-1);
        }
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged
        if(ready){
        String sec=jComboBox4.getSelectedItem()+"";
        int sem=Integer.parseInt(jComboBox3.getSelectedItem()+"");
        int bno=Integer.parseInt(jComboBox2.getSelectedItem().toString());
        Object subject[]=null;
        if(role==FacultySql.ADMIN){
            SubjectSql sql=new SubjectSql();
            //Map map=sql.getSubjects(sem);
            subject=sql.getSubjects(sem).keySet().toArray();
            sql.close();
        }/*else{
            FacultySql sql=new FacultySql(fid);
            subject=sql.getSubjects(bno,sem,sec);
            sql.close();
        }*/
        ready=false;
        jComboBox5.removeAllItems();
        if(subject!=null){
        for(int i=0;i<subject.length;i++){
            jComboBox5.addItem(subject[i]);
        }
        }
        ready=true;
        jComboBox5.setSelectedIndex(-1);
        }
    }//GEN-LAST:event_jComboBox4ItemStateChanged

    private void jComboBox5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox5ItemStateChanged
        // TODO add your handling code here:
        /*String scode=jComboBox5.getSelectedItem()+"";
        Date dt=new Date();
        int bno=getBatch(semester);
        AttendanceSql sql=new AttendanceSql();
        startDate=sql.getFirstLecture("MCA", semester, section, scode, bno);
        if(startDate==null){
            startDate=dt;
        }
        jDateChooser1.setDate(startDate);
        jDateChooser1.setMinSelectableDate(startDate);
        jDateChooser2.setDate(dt);
        jDateChooser2.setMaxSelectableDate(dt);
        jLabel1.setText(scode);*/
    }//GEN-LAST:event_jComboBox5ItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Date from=jDateChooser1.getDate();
        Date to=jDateChooser2.getDate();
        int semester=Integer.parseInt(jComboBox3.getSelectedItem()+"");
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
        
        String scode=jComboBox5.getSelectedItem().toString();
        int bno=Integer.parseInt(jComboBox2.getSelectedItem().toString());
        StudentSql ssql=new StudentSql(null);
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
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
        if(ready){
            int bno=Integer.parseInt(jComboBox2.getSelectedItem().toString());
            String sem[]=null;
            if(role==FacultySql.ADMIN){
                StudentSql sql=new StudentSql(null);
                sem=sql.getSemester("MCA",bno);
                sql.close();
            }/*else{
                FacultySql sql=new FacultySql(fid);
                /ArrayList<Integer> semtr=sql.getAllSemesters();
                jComboBox3.removeAllItems();
                ready=false;
                for(Integer t:semtr){
                    jComboBox3.addItem(t);
                }
                ready=true;
                sql.close();
                return;
            }*/
            ready=false;
            //System.out.println("1");
            jComboBox3.removeAllItems();
            if(sem!=null){
                for(int i=0;i<sem.length;i++){
                    //System.out.println("2");
                    jComboBox3.addItem(sem[i]);
                }
                //System.out.println("3");
                jComboBox3.setSelectedIndex(-1);
            }
            ready=true;
        }
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jComboBox2ItemStateChanged

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

    private String[][] getStudentList(){
        StudentSql sql=new StudentSql(null);
        String section=jComboBox4.getSelectedItem()+"";
        int semester=Integer.parseInt(jComboBox3.getSelectedItem()+"");
        String tdata[][]=sql.getAllStudentDataFromPreviousClass(new String[]{"course","semester","section"}, new Object[]{"MCA",semester,section});
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
        String section=jComboBox4.getSelectedItem()+"";
        int semester=Integer.parseInt(jComboBox3.getSelectedItem()+"");
        int bno=Integer.parseInt(jComboBox2.getSelectedItem().toString());
        AttendanceSql sql=new AttendanceSql();
        ArrayList<Long[]> temp=sql.getAttendanceDates("MCA", semester,section, jComboBox5.getSelectedItem().toString(), bno, new java.sql.Date(from.getTime()), new java.sql.Date(to.getTime()));
        sql.close();
        return temp;
    }

    /*private int getBatch(int sem){
        Date dt=new Date();
        int cy=dt.getYear();
        switch(sem){
            case 5:
                if(dt.getMonth()>=7){
                    cy=cy+1;
                }
                break;
            case 4:
                if(dt.getMonth()>=11){
                    cy=cy+2;
                }else{
                    cy=cy+1;
                }
                break;
            case 3:
                if(dt.getMonth()>=7){
                    cy=cy+2;
                }else{
                    cy=cy+1;
                }
                break;
            case 2:
                if(dt.getMonth()>=11){
                    cy=cy+3;
                }else{
                    cy=cy+2;
                }
                break;
            case 1:
                if(dt.getMonth()>=7){
                    cy=cy+3;
                }else{
                    cy=cy+2;
                }
                break;
        }
        return cy+1900;
    }*/

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

    /*private void setSemester(){
        FacultySql sql=new FacultySql(fid);
        ArrayList<Integer> sem=sql.getAllSemesters();
        sql.close();
        jComboBox3.setModel(new DefaultComboBoxModel(sem.toArray()));
    }*/

    private void applyTableProperties(){
        jTable1.getColumn("SrNo").setPreferredWidth(40);
        jTable1.getColumn("Name").setPreferredWidth(210);
        
        int n=jTable1.getColumnCount();
        for(int i=2;i<n-1;i++){
            jTable1.getColumnModel().getColumn(i).setPreferredWidth(40);
        }
        jTable1.getColumnModel().getColumn(n-1).setPreferredWidth(60);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    public boolean destroy() {
        return true;
    }

}
