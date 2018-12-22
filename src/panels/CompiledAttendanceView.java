/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package panels;

import java.awt.Cursor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import newattendancesystem.MainFrame;
import report.handler.AttendanceReport;
import scheduleclass.Subject;
import sql.AttendanceSql;
import sql.StudentSql;
import sql.SubjectSql;

/**
 *
 * @author yash
 */
public class CompiledAttendanceView extends javax.swing.JPanel implements PanelInterface{

    public int indexOfTab;
    private ArrayList<String> TheorySubjects;
    private ArrayList<String> PracSubjects;
    private Vector column;
    private Vector data;
    /**
     * Creates new form CompiledAttendanceView
     */
    public CompiledAttendanceView() {
        initComponents();
        jDateChooser1.setDate(new Date());
        jDateChooser2.setDate(new Date());
        setBatches();
    }

    private void setBatches(){
        StudentSql sql=new StudentSql(null);
        String []batches=sql.getBatches("MCA");
        sql.close();
        jComboBox2.setModel(new DefaultComboBoxModel(batches));
        jComboBox2ActionPerformed(null);
    }
    
    private void setSemester(){
        int bno=Integer.parseInt(jComboBox2.getSelectedItem().toString());
            String sem[]=null;
            StudentSql sql=new StudentSql(null);
            sem=sql.getSemester("MCA",bno);
            sql.close();
            jComboBox3.setModel(new DefaultComboBoxModel(sem));
            jComboBox3ActionPerformed(null);
    }
    
    private void setSection(){
        int sem=Integer.parseInt(jComboBox3.getSelectedItem()+"");
        int bno=Integer.parseInt(jComboBox2.getSelectedItem().toString());
        String section[]=null;
        StudentSql sql=new StudentSql(null);
        section=sql.getSection("MCA",bno,sem);
        sql.close();
        jComboBox4.setModel(new DefaultComboBoxModel(section));
    }
    
    private void setSubjects(){
        int sem=Integer.parseInt(jComboBox3.getSelectedItem()+"");
        SubjectSql sql=new SubjectSql();
        ArrayList<String> all=sql.getSubjectCodes(sem);
        HashMap<String,Integer> types=sql.getSubjectTypes(sem);
        sql.close();
        TheorySubjects=new ArrayList<>();
        PracSubjects=new ArrayList<>();
        int t;
        for(String scode:all){
            t=types.get(scode);
            if(t==Subject.THEORY){
                TheorySubjects.add(scode);
            }else if(t==Subject.LAB1 || t==Subject.LAB2){
                PracSubjects.add(scode);
            }
        }
    }
    
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
    
    private void viewAttendance(){
        Date from=jDateChooser1.getDate();
        Date to=jDateChooser2.getDate();
        double criteria=Double.parseDouble(jSpinner1.getValue().toString());
        if(from.compareTo(to)>0){
            JOptionPane.showMessageDialog(MainFrame.mainFrame, "Invalid Date range.");
            return;
        }
        java.sql.Date fromSql=new java.sql.Date(from.getTime());
        java.sql.Date toSql=new java.sql.Date(to.getTime());
        int semester=Integer.parseInt(jComboBox3.getSelectedItem()+"");
        String section=jComboBox4.getSelectedItem().toString();
        int bno=Integer.parseInt(jComboBox2.getSelectedItem()+"");
        
        this.setEnabled(false);
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));

        column=new Vector();
        data=new Vector();
        column.add("SrNo");
        column.add("Name");
        
        
        String slist[][]=getStudentList();
        
        AttendanceSql asql=new AttendanceSql();
        
        
        Vector row=new Vector();
        row.addElement("");
        row.addElement("");
        int lCnt;
        int theoTotalCnt=0;
        for(String scode:TheorySubjects){
            column.add(scode);
            lCnt=asql.getTotalLectures("MCA", semester, section, scode, bno, fromSql, toSql);
            row.addElement(lCnt);
            theoTotalCnt=theoTotalCnt+lCnt;
        }
        column.addElement("Total");
        row.addElement(theoTotalCnt);
        column.addElement("in %");
        row.addElement("");
        
        int pracTotalCnt=0;
        for(String scode:PracSubjects){
            column.add(scode);
            lCnt=asql.getTotalLectures("MCA", semester, section, scode, bno, fromSql, toSql);
            row.addElement(lCnt);
            pracTotalCnt=pracTotalCnt+lCnt;
        }
        column.addElement("Total");
        row.addElement(pracTotalCnt);
        column.addElement("in %");
        row.addElement("");
        
        column.addElement("Total (%)");
        data.add(0,row);  //first row count initialize
        
        StudentSql ssql=new StudentSql(null);
        String sid;
        int totalTheo=0,totalPrac=0;
        int cnt;
        int srno=1;
        int colIndex;
        int rowIndex=1;
        for(int i=0;i<slist.length;i++){
            sid=slist[i][2];
            row=new Vector();
            colIndex=0;
            //row.addElement(srno);
            row.add(colIndex++, srno);
            row.add(colIndex++,slist[i][1]);
            ssql.setStudentID(sid);
            totalTheo=0;
            for(String scode:TheorySubjects){
                cnt=ssql.getAttendanceCount(fromSql, toSql, scode, semester, bno, AttendanceSql.PRESENT);
                row.add(colIndex++,cnt);
                totalTheo=totalTheo+cnt;
            }
            row.add(colIndex++,totalTheo);
            row.add(colIndex++,String.format("%.2f", (theoTotalCnt==0)?0:(totalTheo*100.0/theoTotalCnt)));
            
            totalPrac=0;
            for(String scode:PracSubjects){
                cnt=ssql.getAttendanceCount(fromSql, toSql, scode, semester, bno, AttendanceSql.PRESENT);
                row.add(colIndex++,cnt);
                totalPrac=totalPrac+cnt;
            }
            row.add(colIndex++,totalPrac);
            row.add(colIndex++,String.format("%.2f", (pracTotalCnt==0)?0:(totalPrac*100.0/pracTotalCnt)));
            
            Double grandTotalPer=((pracTotalCnt+theoTotalCnt)==0)?0:((totalPrac+totalTheo)*100.0/(pracTotalCnt+theoTotalCnt));
            row.add(colIndex++,String.format("%.2f", grandTotalPer));
            
            if(grandTotalPer>=criteria){
                data.add(rowIndex++,row);
                srno++;
            }
        }
        if(jRadioButton1.isSelected()){
            sort(true);
        }else{
            sort(false);
        }
        
        
        setData();
        this.setEnabled(true);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    
    private void sort(boolean byName){
        int nrows=data.size();
        Vector temp;
        boolean swap=false;
        for(int i=1;i<nrows;i++){
            for(int j=i+1;j<nrows;j++){
                if(byName){
                    swap=myCompareName((Vector)data.get(i), (Vector)data.get(j));
                }else{
                    swap=myCompareAttnd((Vector)data.get(i), (Vector)data.get(j));
                }
                if(!swap){
                    temp=(Vector)data.get(i);
                    data.remove(i);
                    data.add(i, data.get(j-1));
                    data.remove(j);
                    data.add(j, temp);
                }
            }
            temp=(Vector)data.get(i);
            temp.remove(0);
            temp.add(0, i);
        }
    }
    
    private boolean myCompareAttnd(Vector v1, Vector v2){
        //return true if on correct place
        double v1a=Double.parseDouble(v1.get(8).toString());
        double v2a=Double.parseDouble(v2.get(8).toString());
        if(v1a==v2a){
            return myCompareName(v1, v2);
        }
        return(v1a>v2a);
    }
    
    private boolean myCompareName(Vector v1, Vector v2){
        String v1n=v1.get(1).toString();
        String v2n=v2.get(1).toString();
        int rslt=v1n.compareToIgnoreCase(v2n);
        return (rslt<=0);
    }
    
    private void setData(){
        DefaultTableModel tm=new DefaultTableModel(data,column){

            /*@Override
            public Class<?> getColumnClass(int columnIndex) {
                int lastCol=this.getColumnCount()-1;
                if(columnIndex==1 ){
                    //System.out.println("Integer");
                    return String.class;
                }else if(columnIndex==lastCol){
                    //System.out.println("Float");
                    return Float.class;
                }else{
                    //System.out.println("String");
                    return String.class;
                }
            }*/

           
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable1.setModel(tm);
        applyTableProperties();
    }

    /*private void setSemester(){
        FacultySql sql=new FacultySql(fid);
        ArrayList<Integer> sem=sql.getAllSemesters();
        sql.close();
        jComboBox3.setModel(new DefaultComboBoxModel(sem.toArray()));
    }*/

    private void applyTableProperties(){
        jTable1.getColumn("SrNo").setPreferredWidth(40);
        jTable1.getColumn("Name").setPreferredWidth(200);
        
        int n=jTable1.getColumnCount();
        for(int i=2;i<n;i++){
            jTable1.getColumnModel().getColumn(i).setPreferredWidth(60);
        }
        //jTable1.getColumnModel().getColumn(n-1).setPreferredWidth(60);
    }
    
    private void generateReport(){
        int n=jTable1.getRowCount();
        if(n>1){
            String cls="MCA";
            int sem=Integer.parseInt(jComboBox3.getSelectedItem().toString());
            String section=jComboBox4.getSelectedItem().toString();
            SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
            
            String duration=df.format(jDateChooser1.getDate())+" - "+df.format(jDateChooser2.getDate());
            int col=14;
            String data[][]=new String[n-1][col];
            
            for(int i=0;i<n-1;i++){
                for(int j=0;j<col;j++){
                    data[i][j]=jTable1.getValueAt(i+1, j).toString();
                }
            }
            
            HashMap<String,Integer> theoTotal=new HashMap<>();
            DefaultTableColumnModel tcm=(DefaultTableColumnModel) jTable1.getColumnModel();
            int cnt;
            int total=0;
            for(String scode:TheorySubjects){
                cnt=Integer.parseInt(jTable1.getValueAt(0, tcm.getColumnIndex(scode)).toString());
                theoTotal.put(scode, cnt);
                total=total+cnt;
            }
            theoTotal.put("total",total);
            
            HashMap<String,Integer> pracTotal=new HashMap<>();
            total=0;
            for(String scode:PracSubjects){
                cnt=Integer.parseInt(jTable1.getValueAt(0, tcm.getColumnIndex(scode)).toString());
                pracTotal.put(scode, cnt);
                total=total+cnt;
            }
            pracTotal.put("total",total);
            
            AttendanceReport ar=new AttendanceReport();
            ar.attendanceCompile2(cls, sem, section, duration, TheorySubjects, PracSubjects, theoTotal, pracTotal, data);
        }
    }
    
    private boolean isValidate(){
        java.util.Date from=jDateChooser1.getDate();
        java.util.Date to=jDateChooser2.getDate();
        if(from.compareTo(to)>0){
            JOptionPane.showMessageDialog(this, "Invalid Date range.");
            return false;
        }
        return true;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(CompiledAttendanceView.class, "CompiledAttendanceView.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(CompiledAttendanceView.class, "CompiledAttendanceView.jLabel5.text")); // NOI18N

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

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(CompiledAttendanceView.class, "CompiledAttendanceView.jLabel6.text")); // NOI18N

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

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(CompiledAttendanceView.class, "CompiledAttendanceView.jLabel7.text")); // NOI18N

        jComboBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox4ItemStateChanged(evt);
            }
        });
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
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
                    .addComponent(jLabel7))
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.Alignment.LEADING, 0, 75, Short.MAX_VALUE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(CompiledAttendanceView.class, "CompiledAttendanceView.jLabel4.text")); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(CompiledAttendanceView.class, "CompiledAttendanceView.jLabel2.text")); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/information_icon16.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(CompiledAttendanceView.class, "CompiledAttendanceView.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jDateChooser1.setOpaque(false);

        jDateChooser2.setOpaque(false);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/pdf16.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(CompiledAttendanceView.class, "CompiledAttendanceView.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(CompiledAttendanceView.class, "CompiledAttendanceView.jLabel8.text")); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel9, org.openide.util.NbBundle.getMessage(CompiledAttendanceView.class, "CompiledAttendanceView.jLabel9.text")); // NOI18N

        jRadioButton1.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton1, org.openide.util.NbBundle.getMessage(CompiledAttendanceView.class, "CompiledAttendanceView.jRadioButton1.text")); // NOI18N

        jRadioButton2.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton2);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton2, org.openide.util.NbBundle.getMessage(CompiledAttendanceView.class, "CompiledAttendanceView.jRadioButton2.text")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButton2)))
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
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jSpinner1)
                        .addComponent(jLabel8))
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(jRadioButton1)
                        .addComponent(jRadioButton2)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(24, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 69, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addGap(0, 69, Short.MAX_VALUE)))
        );

        Date dt=new Date();
        dt.setDate(1);
        jDateChooser1.setDate(dt);
        jDateChooser1.setDate(new Date());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
        setSemester();
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        //System.out.println("jComboBox3statechange "+ready);

    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
        setSection();
        setSubjects();
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged
    }//GEN-LAST:event_jComboBox4ItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(isValidate()){
            viewAttendance();
        }

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
        /*int n=jTable1.getColumnCount();
        for(int i=0;i<n;i++){
            System.out.println(jTable1.getColumnModel().getColumn(i).getPreferredWidth());
        }*/
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jComboBox4ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    @Override
    public boolean destroy() {
        return true;
    }
}
