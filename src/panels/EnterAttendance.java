/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EnterAttendance.java
 *
 * Created on Nov 4, 2013, 8:01:11 PM
 */

package panels;

import newattendancesystem.MainFrame;
import component.CustomTable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import sql.AttendanceSql;
import sql.SubjectSql;
import sql.StudentSql;


/**
 *
 * @author apex predator
 */
public class EnterAttendance extends javax.swing.JPanel implements PanelInterface{

    public int indexOfTab;
    private final int recOnPage=18;
    private int total,present,absent,leave;
    private String clss,section,fac;
    private int sem,pNo,bNo;
    private String scode;
    private Date date;
    private Map<String,Integer> onLeave;//<SID,LID>
    private String[][] data;   ///srno,name,sid
    private FacultyHome parent;
    private boolean isExists;
    
    /** Creates new form EnterAttendance */
    
    public EnterAttendance(String c,int s,String sec,String subject,Date atdate,String faculty,int period,FacultyHome par,boolean update) {
        initComponents();
        //System.out.println("Enter Attendance");
        parent=par;
        if(parent==null){
            throw new IllegalArgumentException("Invoking frame can not ne NULL");
        }
        jTextArea1.setLineWrap(true);
        jTextArea1.setWrapStyleWord(true);
        clss=c;
        sem=s;
        section=sec;
        scode=subject;
        date=atdate;
        fac=faculty;
        pNo=period;
        isExists=update;
        getStudentList();
        if(isExists){
            getExistingAttendance();
        }
        fillForm();
        
    }

    public EnterAttendance(String c,int s,String sec,String subject,Date atdate,String faculty,int period,FacultyHome par){
        this(c,s,sec,subject,atdate,faculty,period,par,false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        totalLbl = new javax.swing.JLabel();
        presentLbl = new javax.swing.JLabel();
        absentLbl = new javax.swing.JLabel();
        leavelbl = new javax.swing.JLabel();
        attendPerLbl = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        attendanceTable = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 0, 0));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("jLabel1");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("jLabel2");

        jButton5.setText("Close");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 733, Short.MAX_VALUE)
                .addComponent(jButton5))
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton5))
                .addGap(0, 0, 0)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setText("Enter Class Roll No. (Seprated by commas (,) )");

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextArea1.setRows(3);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setMargin(new java.awt.Insets(2, 10, 2, 2));
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("Record");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Mark All");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Unmark All ");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.GridLayout(1, 0, 0, 2));

        totalLbl.setText("Total: ");
        jPanel7.add(totalLbl);

        presentLbl.setText("Present: ");
        jPanel7.add(presentLbl);

        absentLbl.setText("Absent: ");
        jPanel7.add(absentLbl);

        leavelbl.setText("Leave:");
        jPanel7.add(leavelbl);

        attendPerLbl.setText("Attendance : ");
        jPanel7.add(attendPerLbl);

        jPanel2.add(jPanel7);

        attendanceTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        attendanceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Sr No.", "Name", "Attendance"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        attendanceTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        attendanceTable.setFillsViewportHeight(true);
        attendanceTable.setOpaque(false);
        attendanceTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(attendanceTable);
        attendanceTable.getColumnModel().getColumn(0).setMinWidth(20);
        attendanceTable.getColumnModel().getColumn(0).setPreferredWidth(65);
        attendanceTable.getColumnModel().getColumn(1).setPreferredWidth(462);
        attendanceTable.getColumnModel().getColumn(2).setPreferredWidth(160);

        jPanel2.add(jScrollPane2);

        jPanel5.setBackground(new java.awt.Color(102, 102, 102));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/SAVE.PNG"))); // NOI18N
        jButton2.setText("Submit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(729, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton2))
        );

        jPanel2.add(jPanel5);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        recordAttendance();
}//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        selectAll(true);
}//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        selectAll(false);
}//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if(data!=null){
            if(isExists){
                updateExisting();
            }else{
                insertNewAttendance();
            }
/*        if(parent!=null){
                JScrollPane sc=(JScrollPane)MainFrame.mainFrame.formContainer.getComponentAt(indexOfTab);
                JPanel p=(JPanel)sc.getViewport().getComponent(0);
                p.removeAll();
                p.add(parent);
                p.revalidate();
            }
*/        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
//        if(parent!=null){
                JScrollPane sc=(JScrollPane)MainFrame.mainFrame.formContainer.getComponentAt(indexOfTab);
                JPanel p=(JPanel)sc.getViewport().getComponent(0);
                p.removeAll();
                p.add(parent);
                p.revalidate();
                MainFrame.mainFrame.repaint();
//        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void insertNewAttendance(){
        AttendanceSql sql=new AttendanceSql();
            sql.setAutoCommit(false);
            int id=sql.setDetailID(date,clss,sem,section,scode,bNo,pNo);
            if(id==-1){
                 sql.setAutoCommit(true);
                 sql.close();
                 JOptionPane.showMessageDialog(null, "Error Occured...");
                 return;
            }
            CustomTable.Value val;
            String sid[]=new String[data.length];
            int srno[]=new int[data.length];
            int status[]=new int[data.length];
            int page=attendanceTable.getColumnCount()/3;
            try{
            for(int j=0;j<page;j++){
                for(int i=0;i<recOnPage ;i++){
                    val=(CustomTable.Value)attendanceTable.getValueAt(i, (j*3)+2);
                    if(val==null){
                        status[(j*recOnPage)+i]=onLeave.get(data[(j*recOnPage)+i][2]);
                    }else{
                        status[(j*recOnPage)+i]=val.getStatus();
                    }
                    sid[(j*recOnPage)+i]=data[(j*recOnPage)+i][2];
                    srno[(j*recOnPage)+i]=Integer.parseInt(data[(j*recOnPage)+i][0]);
                }
            }
            }catch(  ArrayIndexOutOfBoundsException | NullPointerException e){
                //System.out.println(e.toString());
                //e.printStackTrace();
            }
            if(sql.insertAttendance(sid, srno, status)){
                JOptionPane.showMessageDialog(null, "Attendance submitted successfully.");
                sql.commit();
                sql.setAutoCommit(true);
                sql.close();
                parent.attendanceSubmitted();
                jButton5ActionPerformed(null);
            }else{
                JOptionPane.showMessageDialog(null, "Error Occured...");
                sql.rollback();
                sql.commit();
                sql.close();
            }
    }

    private void updateExisting(){
        AttendanceSql sql=new AttendanceSql();
            sql.setAutoCommit(false);
            int id=sql.findDetailID(date,clss,sem,section,scode,bNo,pNo);
            if(id==-1){
                 sql.setAutoCommit(true);
                 sql.close();
                 JOptionPane.showMessageDialog(null, "Previous record not found.");
                 return;
            }
            CustomTable.Value val;
            String sid[]=new String[data.length];
            int status[]=new int[data.length];
            int page=attendanceTable.getColumnCount()/3;
            try{
            for(int j=0;j<page;j++){
                for(int i=0;i<recOnPage;i++){
                    val=(CustomTable.Value)attendanceTable.getValueAt(i, (j*3)+2);
                    if(val==null){
                        status[(j*recOnPage)+i]=onLeave.get(data[(j*recOnPage)+i][2]);
                    }else{
                        status[(j*recOnPage)+i]=val.getStatus();
                    }
                    sid[(j*recOnPage)+i]=data[(j*recOnPage)+i][2];
                }
            }
            }catch(  ArrayIndexOutOfBoundsException | NullPointerException e){
                //System.out.println(e.toString());
                //e.printStackTrace();
            }
            if(sql.updateAttendance(sid, status)){
                JOptionPane.showMessageDialog(null, "Attendance updated successfully.");
                jButton5ActionPerformed(null);
            }else{
                JOptionPane.showMessageDialog(null, "Error Occured...");
                sql.rollback();
                sql.commit();
                sql.close();
                return;
            }
            sql.commit();
            sql.setAutoCommit(true);
            sql.close();
    }
    
    private void fillForm(){
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        jLabel1.setText(sdf.format(date));
        jLabel2.setText(scode+":");
        absent=total-present-leave;
        totalLbl.setText("Total: "+total);
        absentLbl.setText("Absent: "+absent);
        leavelbl.setText("Leave: "+leave);
        presentLbl.setText("Present: "+present);
        double per=present*100.0/total;
        attendPerLbl.setText(String.format("Attendance : %.2f", per));
        
    }

    public void updateAttendance(boolean prsnt,int rno){
        if(prsnt){
            present=present+1;
            absent=absent-1;
        }else{
            present=present-1;
            absent=absent+1;
        }
        presentLbl.setText("Present : " +present);
        absentLbl.setText("Absent : "+absent);
        double per=present*100.0/total;
        //double per=0.0;
        attendPerLbl.setText(String.format("Attendance : %.2f", per));
        /*if(jTextArea1.getText().equals("")){
            jTextArea1.setText(rno+"");
        }else{
            if(prsnt){
                jTextArea1.setText(jTextArea1.getText()+","+rno);
            }else{
                jTextArea1.setText(jTextArea1.getText().replaceAll(rno+"", ""));
                jTextArea1.setText(jTextArea1.getText().replaceAll(",,", ","));
                if(jTextArea1.getText().startsWith(",")){
                    jTextArea1.setText(jTextArea1.getText().replaceFirst(",", ""));
                }

            }
        }*/
    }

    private void getStudentList(){
        StudentSql sql=new StudentSql("");
        String temp[][]=sql.getAllStudentData(new String[]{"course","Semester","Section"}, new Object[]{clss,sem,section});
        sql.close();
        if(temp!=null){
        bNo=Integer.parseInt(temp[0][StudentSql.BNO]);
        data=new String[temp.length][3];
        for(int i=0;i<temp.length;i++){
            data[i][0]=temp[i][StudentSql.SRNO];
            data[i][1]=temp[i][StudentSql.SNAME];
            data[i][2]=temp[i][StudentSql.SID];
        }

        //StudentSql sql2 =new StudentSql();
        onLeave=sql.getStudentListOnLeave(date,sem,section);
        //sql2.close();

        total=temp.length;
        leave=onLeave.size();
        fillTable();
        }
        sql.close();
    }

    private void fillTable(){
        
        int n=data.length;
        
        int page=n/recOnPage+1;
        //System.out.println(page);
        String col[]=new String[page*3];
        for(int i=0;i<page;i++){
            int cIndex=i*3;
            col[cIndex]="Sr No";
            col[cIndex+1]="Name";
            col[cIndex+2]="Attendance";
        }
        //System.out.println(col.size());
        /*Vector vdata=new Vector();
        for(int i=0;i<data.length;i++){
            Vector temp=new Vector();
            temp.addElement(data[i][0]);
            temp.addElement(data[i][1]);
            temp.addElement(new Boolean(false));
            vdata.addElement(temp);
        }*/
        int nCol=page*3;
        Object vdata[][]=new Object[recOnPage][nCol];
        for(int i=0;i<recOnPage;i++){
            //vdata[i][2]=new CustomTable.Value("Absent",false);
            for(int j=0;j<page;j++){
                int cIndx=j*3;
                if(i+(j*recOnPage)<n){
                    vdata[i][cIndx]=data[i+(j*recOnPage)][0];
                    vdata[i][cIndx+1]=data[i+(j*recOnPage)][1];
                    if(onLeave.containsKey(data[i+(j*recOnPage)][2])){
                        vdata[i][cIndx+2]=null;
                    }else{  
                        vdata[i][cIndx+2]=new CustomTable.Value("Absent", false);
                    }
                }else{
                    vdata[i][cIndx]=null;
                    vdata[i][cIndx+1]=null;
                    vdata[i][cIndx+2]=null;
                    
                }
            }
        }

        //vdata[3][2]=null;
        CustomTable ct=new CustomTable(this);
        CustomTable.DataModel tm=new CustomTable.DataModel(col,vdata);
        
        //DefaultTableModel tm=new DefaultTableModel(vdata,col);
        attendanceTable.setModel(tm);
        attendanceTable.setDefaultRenderer(CustomTable.Value.class, new CustomTable.ValueRenderer());
        attendanceTable.setDefaultEditor(CustomTable.Value.class, new CustomTable.ValueEditor());

        //attendanceTable.getColumnModel().getColumn(0).setPreferredWidth(65);
        //attendanceTable.getColumnModel().getColumn(1).setPreferredWidth(462);
        //attendanceTable.getColumnModel().getColumn(2).setPreferredWidth(160);

        applyTableSetting();
    }

    private void getExistingAttendance(){
        AttendanceSql sql=new AttendanceSql();
        //System.out.println(date.toString()+" "+clss+" "+sem+" "+section+" "+scode+" "+bNo);
        int id=sql.findDetailID(date,clss,sem,section,scode,bNo,pNo);
        
        if(id<=0){
            JOptionPane.showMessageDialog(MainFrame.mainFrame, "Previous Attendance record not found.");
        }else{
            Map<Integer,Integer> atnd=sql.getAttendance();
            int status;
            int cnt=0;
            int page=attendanceTable.getColumnCount()/3;
            for(int i=0;i<recOnPage;i++){
                for(int j=0;j<page;j++){
                    if(attendanceTable.getValueAt(i, (j*3))!=null){
                        try{
                            status=atnd.get(Integer.parseInt(attendanceTable.getValueAt(i, (j*3))+""));
                            if(status==AttendanceSql.PRESENT){
                                attendanceTable.setValueAt(true, i, (j*3)+2);
                                cnt++;
                            }else if(status==AttendanceSql.ABSENT){
                                attendanceTable.setValueAt(false, i, (j*3)+2);
                            }
                        }catch(NumberFormatException e){
                            //JOptionPane.showMessageDialog(MainFrame.mainFrame, "Previous Attendance record not found for "+attendanceTable.getValueAt(0, 0));
                            System.out.println("getExistingAttendance"+e.toString());
                            //e.printStackTrace();
                        }
                    }
               }
            }
            present=cnt;
        }
        sql.close();
    }

    private void applyTableSetting(){
        int page=attendanceTable.getColumnCount()/3;
        TableColumnModel tcm=attendanceTable.getColumnModel();
        for(int i=0;i<page;i++){
            int cIndex=i*3;
            tcm.getColumn(cIndex).setPreferredWidth(50);
            tcm.getColumn(cIndex+1).setPreferredWidth(200);
            tcm.getColumn(cIndex+2).setPreferredWidth(100);
        }

    }

    private void recordAttendance(){
        try{
            TableModel tm=attendanceTable.getModel();
            int n=recOnPage;
            int page=attendanceTable.getColumnCount()/3;
            String rollNos[]=jTextArea1.getText().split(",");
            for(int i=0;i<n;i++){
                //
                for(int j=0;j<page;j++){
                    if( tm.getValueAt(i,(j*3)+2)!=null && ((CustomTable.Value)tm.getValueAt(i, (j*3)+2)).present){
                        tm.setValueAt(false, i, 2+(j*3));
                    }
                }
            }
            if(!jTextArea1.getText().equals("")){
                int rno;
                ArrayList<Integer> presentRollNo=new ArrayList<Integer>();
                for(int i=0;i<rollNos.length;i++){
                    presentRollNo.add(Integer.parseInt(rollNos[i]));
                }
                for(int i=0;i<recOnPage;i++){
                    for(int j=0;j<page;j++){
                        if(tm.getValueAt(i, (j*3)+2)!=null){
                            rno=Integer.parseInt(tm.getValueAt(i, (j*3)).toString());
                            if( presentRollNo.contains(rno)){
                                tm.setValueAt(true, i, (j*3)+2);
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
            //JOptionPane.showMessageDialog(this,e.toString());
            e.printStackTrace();
        }
    }

    private void selectAll(boolean select){
        TableModel tm=attendanceTable.getModel();
            int page=attendanceTable.getColumnCount()/3;
            if(select){
             for(int i=0;i<recOnPage;i++){
                for(int j=0;j<page;j++){
                    if( tm.getValueAt(i,(j*3)+2)!=null && ((CustomTable.Value)tm.getValueAt(i, (j*3)+2)).present==false){
                        tm.setValueAt(true, i, 2+(j*3));
                    }
                }
                /*if( tm.getValueAt(i,2)!=null && ((CustomTable.Value)tm.getValueAt(i, 2)).present==false){
                    tm.setValueAt(true, i, 2);
                }*/
             }
            }else{
             for(int i=0;i<recOnPage;i++){
                 for(int j=0;j<page;j++){
                    if( tm.getValueAt(i,(j*3)+2)!=null && ((CustomTable.Value)tm.getValueAt(i, (j*3)+2)).present==true){
                        tm.setValueAt(false, i, 2+(j*3));
                    }
                }
                /*if( tm.getValueAt(i,2)!=null && ((CustomTable.Value)tm.getValueAt(i, 2)).present==true){
                    tm.setValueAt(false, i, 2);
                }*/
            }
            }
    }

    /*public static void main(String arg[]){
        JFrame frm=new JFrame();
        java.util.Date dt=new java.util.Date(2013-1900, 10, 6);
        frm.add(new EnterAttendance("MCA",5,"A","MCA 501",new Date(dt.getTime()),"F101",1,null,true));
        frm.setVisible(true);
        frm.setSize(600, 200);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel absentLbl;
    private javax.swing.JLabel attendPerLbl;
    private javax.swing.JTable attendanceTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel leavelbl;
    private javax.swing.JLabel presentLbl;
    private javax.swing.JLabel totalLbl;
    // End of variables declaration//GEN-END:variables

    public boolean destroy() {
        return true;
    }

}
