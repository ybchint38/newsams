/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package panels;

import component.LabSchedule;
import component.FacultySelectionOption;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import newattendancesystem.MainFrame;
import report.handler.Schedule;
import report.handler.StudentReport;
import scheduleclass.ScheduleBase;
import scheduleclass.Subject;
import sql.ClassLabManagerSql;
import sql.SqlClass;
import sql.SubjectSql;
import testpackage.FacultyToSubject;
import testpackage.TestFrame;

/**
 *
 * @author yash
 */
public class SchedulePanelDnD extends javax.swing.JPanel implements PanelInterface{

    private Border yesBorder;
    private Border noBorder;
    private Border nrmlBorder;
    
    private HashMap<JList,Integer> subContainer;
    private HashMap<JList,Boolean> canDrop;
    private HashMap<String,String[]> sub_fac;//<subject,fname>
    
    private ScheduleBase sbase;
    
    private JList curSelected;
    
    private boolean isNew;
    private int sem;
    private String sec;
    int roomno;
    //private String subjects[];
    
    /**
     * Creates new form SchedulePanelDnD
     */
    public SchedulePanelDnD(int s,String sc) {
        initComponents();
        try{
            initialize();
            sem=s;
            sec=sc;
            ClassLabManagerSql sql=new ClassLabManagerSql();
            roomno=sql.getRoomNo("MCA", sem, sec);
            sql.close();
            jLabel11.setText("MCA "+sem+" - \""+sec+"\", "+"Room No: "+((roomno==-1)?"NA":roomno));
            sbase=ScheduleBase.getInstance(sem, sec);
            
            load();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e.toString());
            e.printStackTrace();
        }
    }

    private void initialize() throws Exception{
        subContainer=new HashMap<>();
        canDrop=new HashMap<>();
        sub_fac=new HashMap<>();
        Font font=new Font("tahoma", Font.BOLD, 12);
        Color foreColor=new Color(51, 0, 153);
        yesBorder=new EtchedBorder(EtchedBorder.LOWERED, Color.GREEN, Color.GRAY);
        noBorder=new EtchedBorder(EtchedBorder.LOWERED, Color.RED, Color.GRAY);
        nrmlBorder=new EtchedBorder(EtchedBorder.LOWERED);
        
        subContainer.put(mLec1, ScheduleBase.M_LEC1);
        subContainer.put(mLec2, ScheduleBase.M_LEC2);
        subContainer.put(mLec3, ScheduleBase.M_LEC3);
        subContainer.put(mLec4, ScheduleBase.M_LEC4);
        subContainer.put(mLec5, ScheduleBase.M_LEC5);
        subContainer.put(mLec6, ScheduleBase.M_LEC6);
        subContainer.put(mLec7, ScheduleBase.M_LEC7);
        
        subContainer.put(tLec1, ScheduleBase.T_LEC1);
        subContainer.put(tLec2, ScheduleBase.T_LEC2);
        subContainer.put(tLec3, ScheduleBase.T_LEC3);
        subContainer.put(tLec4, ScheduleBase.T_LEC4);
        subContainer.put(tLec5, ScheduleBase.T_LEC5);
        subContainer.put(tLec6, ScheduleBase.T_LEC6);
        subContainer.put(tLec7, ScheduleBase.T_LEC7);
        
        subContainer.put(wLec1, ScheduleBase.W_LEC1);
        subContainer.put(wLec2, ScheduleBase.W_LEC2);
        subContainer.put(wLec3, ScheduleBase.W_LEC3);
        subContainer.put(wLec4, ScheduleBase.W_LEC4);
        subContainer.put(wLec5, ScheduleBase.W_LEC5);
        subContainer.put(wLec6, ScheduleBase.W_LEC6);
        subContainer.put(wLec7, ScheduleBase.W_LEC7);
        
        subContainer.put(thLec1, ScheduleBase.TH_LEC1);
        subContainer.put(thLec2, ScheduleBase.TH_LEC2);
        subContainer.put(thLec3, ScheduleBase.TH_LEC3);
        subContainer.put(thLec4, ScheduleBase.TH_LEC4);
        subContainer.put(thLec5, ScheduleBase.TH_LEC5);
        subContainer.put(thLec6, ScheduleBase.TH_LEC6);
        subContainer.put(thLec7, ScheduleBase.TH_LEC7);
        
        subContainer.put(fLec1, ScheduleBase.F_LEC1);
        subContainer.put(fLec2, ScheduleBase.F_LEC2);
        subContainer.put(fLec3, ScheduleBase.F_LEC3);
        subContainer.put(fLec4, ScheduleBase.F_LEC4);
        subContainer.put(fLec5, ScheduleBase.F_LEC5);
        subContainer.put(fLec6, ScheduleBase.F_LEC6);
        subContainer.put(fLec7, ScheduleBase.F_LEC7);

        subContainer.put(sLec1, ScheduleBase.S_LEC1);
        subContainer.put(sLec2, ScheduleBase.S_LEC2);
        subContainer.put(sLec3, ScheduleBase.S_LEC3);
        subContainer.put(sLec4, ScheduleBase.S_LEC4);
        subContainer.put(sLec5, ScheduleBase.S_LEC5);
        subContainer.put(sLec6, ScheduleBase.S_LEC6);
        subContainer.put(sLec7, ScheduleBase.S_LEC7);
        
        ListSelectionListener lstListen=new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                JList lst=(JList)e.getSource();
                if(curSelected!=null){
                    curSelected.clearSelection();
                }
                curSelected=lst;
            }
        };
        
        SubjectListTransferHamdler subjectListTransferHamdler=new SubjectListTransferHamdler();
        subjectList.setTransferHandler(subjectListTransferHamdler);
        
        SubjectContainerTransferHamdler subjectContainerTransferHamdler=new SubjectContainerTransferHamdler();
        
        Set<JList> listSet=subContainer.keySet();
        Iterator<JList> it=listSet.iterator();
        JList temp;
        while(it.hasNext()){
            temp=it.next();
            temp.addListSelectionListener(lstListen);
            temp.setTransferHandler(subjectContainerTransferHamdler);
            temp.setFont(font);
            temp.setForeground(foreColor);
        }
    }
    
    private void load() throws Exception{
        HashMap<Integer,String> sch=sbase.getSchedule();
            Set<JList> list=subContainer.keySet();
            //Iterator it=list.iterator();
            int lecNo;
            DefaultListModel mod;
            for(JList l:list){
                lecNo=subContainer.get(l);
                mod=(DefaultListModel)l.getModel();
                mod.removeAllElements();
                mod.add(0, sch.get(lecNo));
            }
            mod=(DefaultListModel)subjectList.getModel();
            mod.removeAllElements();
            ArrayList<String> subjects=sbase.getSubjects(); 
            int i=0;
            DefaultTableModel tm=(DefaultTableModel)jTable1.getModel();
            DefaultTableModel tm2=(DefaultTableModel)jTable2.getModel();
            int rcnt=tm.getRowCount();
            for(int in=rcnt-1;in>=0;in--){
                tm.removeRow(in);
                //System.out.println(in);
            }
            rcnt=tm2.getRowCount();
            for(int in=rcnt-1;in>=0;in--){
                tm2.removeRow(in);
            }
            SubjectSql sql=new SubjectSql();
            ArrayList<String[]> temp;
            int indxTable2=0;
            for(String s:subjects){
                mod.add(i, s);
                tm.addRow(new String[]{"",""});
                //System.out.println("table 1 row count: "+tm.getRowCount());
                tm2.addRow(new String[]{"","","","","","","","",""});
                //System.out.println("subject:"+s);
                tm.setValueAt(s, i, 0);
                tm2.setValueAt(s+":"+sql.getSubjectName(s), indxTable2, 0);
                temp=sql.getFacultiesFor(s, sem, sec);
                if(temp.isEmpty()){
                    indxTable2++;
                }
                int n=temp.size();
                String fnames[]=new String[n];
                for(int j=0;j<n;j++){
                    String f[]=temp.get(j);
                    if(j>0){
                        tm2.addRow(new String[]{"","","","","","","","",""});
                    }
                    tm2.setValueAt(f[1], indxTable2, 1);
                    fnames[j]=f[1];
                    indxTable2++;
                }
                sub_fac.put(s, fnames);
                i++;
            }
            setSubjectLectureCount();
            setFacultyLectureCount();
            jComboBox1.setModel(new DefaultComboBoxModel(sbase.getLabNames()));
            if(!sbase.validateSchedule()){
                this.setEnabled(false);
            }
    }
    
    
    private void setForDrop(String subject){
        
        //System.out.println("Set for drop start.");
        Set<JList> listSet=subContainer.keySet();
        Iterator<JList> it=listSet.iterator();
        JList temp;
        int i=0;
        while(it.hasNext()){
            temp=it.next();
            if(sbase.isVacant(subContainer.get(temp), subject)){
                canDrop.put(temp, true);
                temp.setBorder(yesBorder);
            }else{
                canDrop.put(temp, false);
                temp.setBorder(noBorder);
            }
            //System.out.println("Iteration "+(i++));
        }
        //System.out.println("Set for drop end.");
        /*Set<JList> listSet=subContainer.keySet();
        Iterator<JList> it=listSet.iterator();
        JList temp;
        while(it.hasNext()){
            temp=it.next();
            canDrop.put(temp, true);
            temp.setBorder(yesBorder);
        }
        canDrop.put(mLec1, false);
        mLec1.setBorder(noBorder);        
        canDrop.put(tLec2, false);
        tLec2.setBorder(noBorder);        
        canDrop.put(wLec3, false);
        wLec3.setBorder(noBorder);        
        canDrop.put(thLec4, false);
        thLec4.setBorder(noBorder);        
        canDrop.put(fLec5, false);
        fLec5.setBorder(noBorder);        
        canDrop.put(sLec7, false);
        sLec7.setBorder(noBorder);*/       
    }
    
    private void reset(){
        Set<JList> listSet=subContainer.keySet();
        Iterator<JList> it=listSet.iterator();
        JList temp;
        while(it.hasNext()){
            temp=it.next();
            //canDrop.put(temp, true);
            temp.setBorder(nrmlBorder);
        }
    }
    
    private void setSubjectLectureCount(){
        DefaultTableModel tm=(DefaultTableModel)jTable1.getModel();
        int cnt=tm.getRowCount();
        //System.out.println("subleccount called.");
        //System.out.println("row count "+cnt);
        String sub;
        for(int i=0;i<cnt;i++){
            sub=tm.getValueAt(i, 0).toString();
            //System.out.println(sub);
            tm.setValueAt(sbase.getNoOfLecture(sub), i, 1);
        }
    }
    
    private void setFacultyLectureCount(){
        DefaultTableModel tm=(DefaultTableModel)jTable2.getModel();
        //System.out.println("facleccount called.");
        int cnt=tm.getRowCount();
        String name;
        int lec[];
        for(int i=0;i<cnt;i++){
            name=(String)tm.getValueAt(i, 1);
            if(!name.equalsIgnoreCase("")){
                lec=sbase.getFacultyLecCount(name);
                for(int j=2;j<=8;j++){
                    tm.setValueAt(lec[j-2], i, j);
                    //System.out.print(lec[j-2]+" ");
                }
                //System.out.println();
            }
        }
    }
    
    private void highLightFaculties(String scode){
        String fnames[]=sbase.getActivatedFaculty(scode);
        int n=jTable2.getRowCount();
        ListSelectionModel lsm=jTable2.getSelectionModel();
        lsm.clearSelection();
        for(int i=0;i<n;i++){
            for(int j=0;j<fnames.length;j++){
                if(jTable2.getValueAt(i, 1).toString().equals(fnames[j])){
                    lsm.addSelectionInterval(i, i);
                    break;
                }
            }
        }
    }
    
    private void highLightSubject(String scode){
        Color old=new java.awt.Color(51, 0, 153);
        Color n=Color.MAGENTA;
        ArrayList<Integer> lecIds=sbase.getLectureID(scode);
        Set<JList> jList=subContainer.keySet();
        for(JList t:jList){
            if(lecIds.contains(subContainer.get(t))){
                t.setForeground(n);
            }else{
                t.setForeground(old);
            }
        }
    }

    @Override
    public boolean destroy() {
        sbase.destroy();
        return true;
    }
    
   
    private class SubjectListTransferHamdler extends TransferHandler{

        @Override
        public boolean canImport(TransferSupport support) {
            return true;
        }

        @Override
        public boolean importData(TransferSupport support) {
            return true;
        }

        @Override
        public int getSourceActions(JComponent c) {
            return TransferHandler.COPY;
        }

        @Override
        protected void exportDone(JComponent source, Transferable data, int action) {
            //Do nothing
            setSubjectLectureCount();
            setFacultyLectureCount();
            reset();
        }

        @Override
        protected Transferable createTransferable(JComponent c) {
            JList list=(JList)c;
            String subject=(String)list.getSelectedValue();
            if(subject!=null){
                setForDrop(subject);
                highLightFaculties(subject);
                highLightSubject(subject);
                return new StringSelection(subject);
            }
            return null;
        }
                
    }
    
    private class SubjectContainerTransferHamdler extends TransferHandler{

        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            int lecno=subContainer.get((JList)support.getComponent());
            try{
                String subject=(String)support.getTransferable().getTransferData(DataFlavor.stringFlavor);
                int n=jTable2.getRowCount();
                for(int i=0;i<n;i++){
                    jTable2.setValueAt("", i, 9);
                }
                int rows[]=jTable2.getSelectedRows();
                n=rows.length;
                for(int i=0;i<n;i++){
                    jTable2.setValueAt(sbase.isFacultyAvailable(jTable2.getValueAt(rows[i], 1).toString(), lecno, subject), rows[i], 9);
                }
            }catch(Exception e){
            }
            return canDrop.get((JList)support.getComponent());
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            JList lst=(JList)support.getComponent();
            String sub="";
            try{
                sub=(String)support.getTransferable().getTransferData(DataFlavor.stringFlavor);
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
            ((DefaultListModel)lst.getModel()).removeAllElements();
            ((DefaultListModel)lst.getModel()).add(0, sub);
            sbase.addLecture(subContainer.get(lst), sub);
            //System.out.println("Added at "+subContainer.get(lst)+" : "+sub);
            //setFacultyLectureCount();
            return true;
        }

        @Override
        public int getSourceActions(JComponent c) {
            return TransferHandler.MOVE;
        }

        @Override
        protected void exportDone(JComponent source, Transferable data, int action) {
            //Do nothing
            JList lst=(JList)source;
            if(action==MOVE){
                ((DefaultListModel)lst.getModel()).removeAllElements();
                try{
                    String subject=(String)data.getTransferData(DataFlavor.stringFlavor);
                    //sbase.addLecture(subContainer.get(lst), subject);
                    String cur[]=sbase.getActivatedFaculty(subject);
                    sbase.activateFaculty(subject, sub_fac.get(subject));
                    sbase.removeLecture(subContainer.get(lst));
                    sbase.activateFaculty(subject, cur);
                    //System.out.println("Removed from "+subContainer.get(lst));
                    //((DefaultListModel)lst.getModel()).add(0, subject);
                    setFacultyLectureCount();
                    setSubjectLectureCount();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            reset();
        }

        @Override
        protected Transferable createTransferable(JComponent c) {
            JList list=(JList)c;
            String subject=(String)list.getSelectedValue();
            if(subject!=null){
                //sbase.removeLecture(subContainer.get(list));
                //((DefaultListModel)list.getModel()).removeAllElements();
                setForDrop(subject);
                sbase.activateFaculty(subject, sub_fac.get(subject), subContainer.get(list));
                highLightFaculties(subject);
                highLightSubject(subject);
                return new StringSelection(subject);
            }
            return null;
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
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        mLec7 = new javax.swing.JList();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        mLec1 = new javax.swing.JList();
        tLec1 = new javax.swing.JList();
        wLec1 = new javax.swing.JList();
        thLec1 = new javax.swing.JList();
        fLec1 = new javax.swing.JList();
        sLec1 = new javax.swing.JList();
        mLec2 = new javax.swing.JList();
        tLec2 = new javax.swing.JList();
        wLec2 = new javax.swing.JList();
        thLec2 = new javax.swing.JList();
        fLec2 = new javax.swing.JList();
        sLec2 = new javax.swing.JList();
        mLec3 = new javax.swing.JList();
        tLec3 = new javax.swing.JList();
        wLec3 = new javax.swing.JList();
        thLec3 = new javax.swing.JList();
        fLec3 = new javax.swing.JList();
        sLec3 = new javax.swing.JList();
        mLec4 = new javax.swing.JList();
        tLec4 = new javax.swing.JList();
        wLec4 = new javax.swing.JList();
        thLec4 = new javax.swing.JList();
        fLec4 = new javax.swing.JList();
        sLec4 = new javax.swing.JList();
        mLec5 = new javax.swing.JList();
        tLec5 = new javax.swing.JList();
        wLec5 = new javax.swing.JList();
        thLec5 = new javax.swing.JList();
        fLec5 = new javax.swing.JList();
        sLec5 = new javax.swing.JList();
        mLec6 = new javax.swing.JList();
        tLec7 = new javax.swing.JList();
        wLec7 = new javax.swing.JList();
        thLec7 = new javax.swing.JList();
        fLec7 = new javax.swing.JList();
        sLec7 = new javax.swing.JList();
        jScrollPane1 = new javax.swing.JScrollPane();
        subjectList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        tLec6 = new javax.swing.JList();
        wLec6 = new javax.swing.JList();
        thLec6 = new javax.swing.JList();
        fLec6 = new javax.swing.JList();
        sLec6 = new javax.swing.JList();
        jPanel3 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setBackground(new java.awt.Color(153, 102, 0));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("jLabel11");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/SAVE.PNG"))); // NOI18N
        jButton3.setText("Save");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/reset-icon16.png"))); // NOI18N
        jButton2.setText("Last Saved");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/setting1.jpg"))); // NOI18N
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/pdf16.png"))); // NOI18N
        jButton6.setText("Save as Pdf");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel13)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3)
                        .addComponent(jButton2))
                    .addComponent(jButton6))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 204));
        jPanel7.setLayout(new java.awt.GridBagLayout());

        mLec7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mLec7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        mLec7.setModel(new DefaultListModel());
        mLec7.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        mLec7.setDragEnabled(true);
        mLec7.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(mLec7, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("testpackage/Bundle"); // NOI18N
        jLabel3.setText(bundle.getString("SchedulePanel.jLabel3.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 7;
        jPanel7.add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText(bundle.getString("SchedulePanel.jLabel4.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 24;
        jPanel7.add(jLabel4, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText(bundle.getString("SchedulePanel.jLabel5.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 26;
        jPanel7.add(jLabel5, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText(bundle.getString("SchedulePanel.jLabel6.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 5;
        jPanel7.add(jLabel6, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText(bundle.getString("SchedulePanel.jLabel7.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 17;
        jPanel7.add(jLabel7, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText(bundle.getString("SchedulePanel.jLabel8.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 36;
        jPanel7.add(jLabel8, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText(bundle.getString("SchedulePanel.jLabel9.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 26;
        jPanel7.add(jLabel9, gridBagConstraints);

        jLabel20.setText(bundle.getString("SchedulePanel.jLabel20.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(jLabel20, gridBagConstraints);

        jLabel21.setText(bundle.getString("SchedulePanel.jLabel21.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(jLabel21, gridBagConstraints);

        jLabel22.setText(bundle.getString("SchedulePanel.jLabel22.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(jLabel22, gridBagConstraints);

        jLabel23.setText(bundle.getString("SchedulePanel.jLabel23.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(jLabel23, gridBagConstraints);

        jLabel24.setText(bundle.getString("SchedulePanel.jLabel24.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(jLabel24, gridBagConstraints);

        jLabel25.setText(bundle.getString("SchedulePanel.jLabel25.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(jLabel25, gridBagConstraints);

        jLabel26.setText(bundle.getString("SchedulePanel.jLabel26.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(jLabel26, gridBagConstraints);

        jLabel2.setText(bundle.getString("SchedulePanel.jLabel2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 6;
        jPanel7.add(jLabel2, gridBagConstraints);

        mLec1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mLec1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        mLec1.setForeground(new java.awt.Color(51, 0, 153));
        mLec1.setModel(new DefaultListModel());
        mLec1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        mLec1.setAutoscrolls(false);
        mLec1.setDragEnabled(true);
        mLec1.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(mLec1, gridBagConstraints);

        tLec1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        tLec1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tLec1.setModel(new DefaultListModel());
        tLec1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tLec1.setDragEnabled(true);
        tLec1.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(tLec1, gridBagConstraints);

        wLec1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        wLec1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        wLec1.setModel(new DefaultListModel());
        wLec1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        wLec1.setDragEnabled(true);
        wLec1.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(wLec1, gridBagConstraints);

        thLec1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        thLec1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        thLec1.setModel(new DefaultListModel());
        thLec1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        thLec1.setDragEnabled(true);
        thLec1.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(thLec1, gridBagConstraints);

        fLec1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        fLec1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fLec1.setModel(new DefaultListModel());
        fLec1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        fLec1.setDragEnabled(true);
        fLec1.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(fLec1, gridBagConstraints);

        sLec1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        sLec1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sLec1.setModel(new DefaultListModel());
        sLec1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        sLec1.setDragEnabled(true);
        sLec1.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(sLec1, gridBagConstraints);

        mLec2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mLec2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        mLec2.setModel(new DefaultListModel());
        mLec2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        mLec2.setDragEnabled(true);
        mLec2.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(mLec2, gridBagConstraints);

        tLec2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tLec2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tLec2.setModel(new DefaultListModel());
        tLec2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tLec2.setAutoscrolls(false);
        tLec2.setDragEnabled(true);
        tLec2.setDropMode(javax.swing.DropMode.ON);
        tLec2.setFocusable(false);
        tLec2.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(tLec2, gridBagConstraints);

        wLec2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        wLec2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        wLec2.setModel(new DefaultListModel());
        wLec2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        wLec2.setDragEnabled(true);
        wLec2.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(wLec2, gridBagConstraints);

        thLec2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        thLec2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        thLec2.setModel(new DefaultListModel());
        thLec2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        thLec2.setDragEnabled(true);
        thLec2.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(thLec2, gridBagConstraints);

        fLec2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        fLec2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fLec2.setModel(new DefaultListModel());
        fLec2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        fLec2.setDragEnabled(true);
        fLec2.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(fLec2, gridBagConstraints);

        sLec2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sLec2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sLec2.setModel(new DefaultListModel());
        sLec2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        sLec2.setDragEnabled(true);
        sLec2.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(sLec2, gridBagConstraints);

        mLec3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mLec3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        mLec3.setModel(new DefaultListModel());
        mLec3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        mLec3.setDragEnabled(true);
        mLec3.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(mLec3, gridBagConstraints);

        tLec3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tLec3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tLec3.setModel(new DefaultListModel());
        tLec3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tLec3.setDragEnabled(true);
        tLec3.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(tLec3, gridBagConstraints);

        wLec3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        wLec3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        wLec3.setModel(new DefaultListModel());
        wLec3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        wLec3.setDragEnabled(true);
        wLec3.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(wLec3, gridBagConstraints);

        thLec3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        thLec3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        thLec3.setModel(new DefaultListModel());
        thLec3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        thLec3.setDragEnabled(true);
        thLec3.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(thLec3, gridBagConstraints);

        fLec3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        fLec3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fLec3.setModel(new DefaultListModel());
        fLec3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        fLec3.setAutoscrolls(false);
        fLec3.setDragEnabled(true);
        fLec3.setDropMode(javax.swing.DropMode.ON);
        fLec3.setFocusable(false);
        fLec3.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(fLec3, gridBagConstraints);

        sLec3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sLec3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sLec3.setModel(new DefaultListModel());
        sLec3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        sLec3.setDragEnabled(true);
        sLec3.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(sLec3, gridBagConstraints);

        mLec4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mLec4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        mLec4.setModel(new DefaultListModel());
        mLec4.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        mLec4.setAutoscrolls(false);
        mLec4.setDragEnabled(true);
        mLec4.setDropMode(javax.swing.DropMode.ON);
        mLec4.setFocusable(false);
        mLec4.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(mLec4, gridBagConstraints);

        tLec4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tLec4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tLec4.setModel(new DefaultListModel());
        tLec4.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tLec4.setDragEnabled(true);
        tLec4.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(tLec4, gridBagConstraints);

        wLec4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        wLec4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        wLec4.setModel(new DefaultListModel());
        wLec4.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        wLec4.setDragEnabled(true);
        wLec4.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(wLec4, gridBagConstraints);

        thLec4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        thLec4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        thLec4.setModel(new DefaultListModel());
        thLec4.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        thLec4.setDragEnabled(true);
        thLec4.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(thLec4, gridBagConstraints);

        fLec4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        fLec4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fLec4.setModel(new DefaultListModel());
        fLec4.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        fLec4.setDragEnabled(true);
        fLec4.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(fLec4, gridBagConstraints);

        sLec4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sLec4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sLec4.setModel(new DefaultListModel());
        sLec4.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        sLec4.setDragEnabled(true);
        sLec4.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(sLec4, gridBagConstraints);

        mLec5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mLec5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        mLec5.setModel(new DefaultListModel());
        mLec5.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        mLec5.setDragEnabled(true);
        mLec5.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(mLec5, gridBagConstraints);

        tLec5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tLec5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tLec5.setModel(new DefaultListModel());
        tLec5.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tLec5.setAutoscrolls(false);
        tLec5.setDragEnabled(true);
        tLec5.setDropMode(javax.swing.DropMode.ON);
        tLec5.setFocusable(false);
        tLec5.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(tLec5, gridBagConstraints);

        wLec5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        wLec5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        wLec5.setModel(new DefaultListModel());
        wLec5.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        wLec5.setDragEnabled(true);
        wLec5.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(wLec5, gridBagConstraints);

        thLec5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        thLec5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        thLec5.setModel(new DefaultListModel());
        thLec5.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        thLec5.setDragEnabled(true);
        thLec5.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(thLec5, gridBagConstraints);

        fLec5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        fLec5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fLec5.setModel(new DefaultListModel());
        fLec5.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        fLec5.setDragEnabled(true);
        fLec5.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(fLec5, gridBagConstraints);

        sLec5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sLec5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sLec5.setModel(new DefaultListModel());
        sLec5.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        sLec5.setDragEnabled(true);
        sLec5.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(sLec5, gridBagConstraints);

        mLec6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mLec6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        mLec6.setModel(new DefaultListModel());
        mLec6.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        mLec6.setDragEnabled(true);
        mLec6.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(mLec6, gridBagConstraints);

        tLec7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tLec7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tLec7.setModel(new DefaultListModel());
        tLec7.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tLec7.setDragEnabled(true);
        tLec7.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(tLec7, gridBagConstraints);

        wLec7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        wLec7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        wLec7.setModel(new DefaultListModel());
        wLec7.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        wLec7.setDragEnabled(true);
        wLec7.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(wLec7, gridBagConstraints);

        thLec7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        thLec7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        thLec7.setModel(new DefaultListModel());
        thLec7.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        thLec7.setDragEnabled(true);
        thLec7.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(thLec7, gridBagConstraints);

        fLec7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        fLec7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fLec7.setModel(new DefaultListModel());
        fLec7.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        fLec7.setAutoscrolls(false);
        fLec7.setDragEnabled(true);
        fLec7.setDropMode(javax.swing.DropMode.ON);
        fLec7.setFocusable(false);
        fLec7.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(fLec7, gridBagConstraints);

        sLec7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sLec7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sLec7.setModel(new DefaultListModel());
        sLec7.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        sLec7.setDragEnabled(true);
        sLec7.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(sLec7, gridBagConstraints);

        subjectList.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        subjectList.setForeground(new java.awt.Color(51, 0, 255));
        subjectList.setModel(new DefaultListModel<>());
        subjectList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        subjectList.setDragEnabled(true);
        subjectList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                subjectListMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(subjectList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel7.add(jScrollPane1, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Subjects");
        jPanel7.add(jLabel1, new java.awt.GridBagConstraints());

        jLabel10.setText("04:00-05:00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(jLabel10, gridBagConstraints);

        tLec6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tLec6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tLec6.setModel(new DefaultListModel());
        tLec6.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tLec6.setDragEnabled(true);
        tLec6.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(tLec6, gridBagConstraints);

        wLec6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        wLec6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        wLec6.setModel(new DefaultListModel());
        wLec6.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        wLec6.setDragEnabled(true);
        wLec6.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(wLec6, gridBagConstraints);

        thLec6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        thLec6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        thLec6.setModel(new DefaultListModel());
        thLec6.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        thLec6.setDragEnabled(true);
        thLec6.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(thLec6, gridBagConstraints);

        fLec6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        fLec6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fLec6.setModel(new DefaultListModel());
        fLec6.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        fLec6.setAutoscrolls(false);
        fLec6.setDragEnabled(true);
        fLec6.setDropMode(javax.swing.DropMode.ON);
        fLec6.setFocusable(false);
        fLec6.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(fLec6, gridBagConstraints);

        sLec6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sLec6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sLec6.setModel(new DefaultListModel());
        sLec6.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        sLec6.setDragEnabled(true);
        sLec6.setDropMode(javax.swing.DropMode.ON);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel7.add(sLec6, gridBagConstraints);

        jPanel3.setBackground(new java.awt.Color(153, 102, 0));

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText(bundle.getString("SchedulePanel.jLabel19.text")); // NOI18N

        jTable2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Subject", "Faculty", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Total", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setFillsViewportHeight(true);
        jTable2.setGridColor(new java.awt.Color(204, 204, 204));
        jTable2.setSelectionBackground(new java.awt.Color(51, 153, 0));
        jTable2.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(1).setMinWidth(100);
            jTable2.getColumnModel().getColumn(1).setPreferredWidth(150);
            jTable2.getColumnModel().getColumn(1).setMaxWidth(200);
            jTable2.getColumnModel().getColumn(2).setMinWidth(30);
            jTable2.getColumnModel().getColumn(2).setPreferredWidth(60);
            jTable2.getColumnModel().getColumn(2).setMaxWidth(100);
            jTable2.getColumnModel().getColumn(3).setMinWidth(30);
            jTable2.getColumnModel().getColumn(3).setPreferredWidth(60);
            jTable2.getColumnModel().getColumn(3).setMaxWidth(100);
            jTable2.getColumnModel().getColumn(4).setMinWidth(30);
            jTable2.getColumnModel().getColumn(4).setPreferredWidth(80);
            jTable2.getColumnModel().getColumn(4).setMaxWidth(100);
            jTable2.getColumnModel().getColumn(5).setMinWidth(30);
            jTable2.getColumnModel().getColumn(5).setPreferredWidth(70);
            jTable2.getColumnModel().getColumn(5).setMaxWidth(100);
            jTable2.getColumnModel().getColumn(6).setMinWidth(30);
            jTable2.getColumnModel().getColumn(6).setPreferredWidth(60);
            jTable2.getColumnModel().getColumn(6).setMaxWidth(100);
            jTable2.getColumnModel().getColumn(7).setMinWidth(30);
            jTable2.getColumnModel().getColumn(7).setPreferredWidth(70);
            jTable2.getColumnModel().getColumn(7).setMaxWidth(100);
            jTable2.getColumnModel().getColumn(8).setMinWidth(30);
            jTable2.getColumnModel().getColumn(8).setPreferredWidth(50);
            jTable2.getColumnModel().getColumn(8).setMaxWidth(70);
            jTable2.getColumnModel().getColumn(9).setMinWidth(50);
            jTable2.getColumnModel().getColumn(9).setPreferredWidth(100);
            jTable2.getColumnModel().getColumn(9).setMaxWidth(100);
        }
        jTable2.getColumnModel().getColumn(2).setMinWidth(20);
        jTable2.getColumnModel().getColumn(2).setPreferredWidth(60);
        jTable2.getColumnModel().getColumn(2).setMaxWidth(60);
        jTable2.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("SchedulePanel.jTable2.columnModel.title2")); // NOI18N
        jTable2.getColumnModel().getColumn(3).setMinWidth(20);
        jTable2.getColumnModel().getColumn(3).setPreferredWidth(60);
        jTable2.getColumnModel().getColumn(3).setMaxWidth(60);
        jTable2.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("SchedulePanel.jTable2.columnModel.title3")); // NOI18N
        jTable2.getColumnModel().getColumn(4).setMinWidth(20);
        jTable2.getColumnModel().getColumn(4).setPreferredWidth(70);
        jTable2.getColumnModel().getColumn(4).setMaxWidth(70);
        jTable2.getColumnModel().getColumn(4).setHeaderValue(bundle.getString("SchedulePanel.jTable2.columnModel.title4")); // NOI18N
        jTable2.getColumnModel().getColumn(5).setMinWidth(20);
        jTable2.getColumnModel().getColumn(5).setPreferredWidth(60);
        jTable2.getColumnModel().getColumn(5).setMaxWidth(60);
        jTable2.getColumnModel().getColumn(5).setHeaderValue(bundle.getString("SchedulePanel.jTable2.columnModel.title5")); // NOI18N
        jTable2.getColumnModel().getColumn(6).setMinWidth(20);
        jTable2.getColumnModel().getColumn(6).setPreferredWidth(60);
        jTable2.getColumnModel().getColumn(6).setMaxWidth(60);
        jTable2.getColumnModel().getColumn(6).setHeaderValue(bundle.getString("SchedulePanel.jTable2.columnModel.title6")); // NOI18N
        jTable2.getColumnModel().getColumn(7).setMinWidth(20);
        jTable2.getColumnModel().getColumn(7).setPreferredWidth(60);
        jTable2.getColumnModel().getColumn(7).setMaxWidth(60);
        jTable2.getColumnModel().getColumn(7).setHeaderValue(bundle.getString("SchedulePanel.jTable2.columnModel.title7")); // NOI18N

        jButton4.setText("View");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Lab Schedule:");

        jButton5.setText("View");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 987, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jButton4)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(153, 102, 0));

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText(bundle.getString("SchedulePanel.jLabel18.text")); // NOI18N

        jTable1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Subject", "No of Lecture"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setFillsViewportHeight(true);
        jTable1.setGridColor(new java.awt.Color(204, 204, 204));
        jTable1.setSelectionBackground(new java.awt.Color(51, 255, 51));
        jScrollPane3.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel18)
                .addGap(0, 154, Short.MAX_VALUE))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        Set<JList> list=subContainer.keySet();
        Iterator<JList> it=list.iterator();
        while(it.hasNext()){
            it.next().setEnabled(enabled);
        }
        subjectList.setEnabled(enabled);
        jButton2.setEnabled(enabled);
        jButton3.setEnabled(enabled);
        jButton4.setEnabled(enabled);
        jButton5.setEnabled(enabled);
        jButton6.setEnabled(enabled);
        jComboBox1.setEnabled(enabled);
    }
    
    /*private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        //JFrame frm=new JFrame();
        //frm.add(new FacultyToSubject(sem, sec, 2014));
        //frm.pack();
        //frm.setVisible(true);
        FacultyToSubject e=new FacultyToSubject(sem, sec);
        JOptionPane.showOptionDialog(this, e, "Subject Allotment", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, new ImageIcon("image/setting1.jpg"), new String[]{"Close"}, null);
        if(e.isChanged){
            try{
                sbase.reset();
                load();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, ex.toString(), "Exception", JOptionPane.ERROR_MESSAGE);
            }
        }
    }*/
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        try{
            sbase.reIntialize();
            load();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e.toString());
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        sbase.save();
        JOptionPane.showMessageDialog(this, "Schedule saved Successfully.");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        String lName=jComboBox1.getSelectedItem().toString();
        HashMap<Integer,String> tSch=sbase.getLabSchedule(lName);
        JOptionPane.showOptionDialog(this, new LabSchedule(lName,tSch), "Lab Schedule", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"Close"}, null);
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
        SettingRoomLab e=new SettingRoomLab(sem, sec, roomno);
        JOptionPane.showOptionDialog(this, e, "Settings", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, new ImageIcon("image/setting1.jpg"), new String[]{"Close"}, null);
        if(e.isChange){
            try{
                sbase.reset();
                load();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, ex.toString(), "Exception", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jLabel13MouseClicked

    private void subjectListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subjectListMousePressed
        // TODO add your handling code here:
        if(SwingUtilities.isRightMouseButton(evt)){
            int index=subjectList.locationToIndex(evt.getPoint());
            subjectList.setSelectedIndex(index);
            String subject=subjectList.getSelectedValue().toString();
            String avail[]=sub_fac.get(subject);
            String current[]=sbase.getActivatedFaculty(subject);
            FacultySelectionOption fso=new FacultySelectionOption(avail, current, Subject.isTheory(subject));
            int val=JOptionPane.showOptionDialog(this, fso, subject, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"OK"}, null);
            if(val==JOptionPane.OK_OPTION){
                current=fso.getSelected();
                if(current==null){
                    JOptionPane.showMessageDialog(this, "Atleast one faculty must be selected.");
                    return;
                }
                sbase.activateFaculty(subject, current);
            }
        }
    }//GEN-LAST:event_subjectListMousePressed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        int indx=jTable2.getSelectedRow();
        if(indx==-1){
            JOptionPane.showMessageDialog(this, "Please Select Faculty.");
            return ;
        }
        String fname=jTable2.getValueAt(indx, 1).toString();
        JOptionPane.showOptionDialog(this, new component.FacultySchedule(sbase.getFaculty(fname).getSchedule()), fname, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"OK"}, null);
         
        /*System.out.println(jTable2.getColumnModel().getColumn(0).getWidth());
        System.out.println(jTable2.getColumnModel().getColumn(1).getWidth());
        System.out.println(jTable2.getColumnModel().getColumn(2).getWidth());
        System.out.println(jTable2.getColumnModel().getColumn(3).getWidth());
        System.out.println(jTable2.getColumnModel().getColumn(4).getWidth());
        System.out.println(jTable2.getColumnModel().getColumn(5).getWidth());
        System.out.println(jTable2.getColumnModel().getColumn(6).getWidth());
        System.out.println(jTable2.getColumnModel().getColumn(7).getWidth());
        System.out.println(jTable2.getColumnModel().getColumn(8).getWidth());
        System.out.println(jTable2.getColumnModel().getColumn(9).getWidth());
        */
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        new Thread(){

            @Override
            public void run() {
                MainFrame.mainFrame.startProcessing("Generating");
                jButton6.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                Schedule sch=new Schedule();
                sch.generateTimeTable("MCA", sem, sec, new Date());
                jButton6.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                MainFrame.mainFrame.stopProcessing();
            }
            
        }.start();
    }//GEN-LAST:event_jButton6ActionPerformed

    public static void main(String arg[]){
        new TestFrame(new SchedulePanelDnD(4,"B"));
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList fLec1;
    private javax.swing.JList fLec2;
    private javax.swing.JList fLec3;
    private javax.swing.JList fLec4;
    private javax.swing.JList fLec5;
    private javax.swing.JList fLec6;
    private javax.swing.JList fLec7;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JList mLec1;
    private javax.swing.JList mLec2;
    private javax.swing.JList mLec3;
    private javax.swing.JList mLec4;
    private javax.swing.JList mLec5;
    private javax.swing.JList mLec6;
    private javax.swing.JList mLec7;
    private javax.swing.JList sLec1;
    private javax.swing.JList sLec2;
    private javax.swing.JList sLec3;
    private javax.swing.JList sLec4;
    private javax.swing.JList sLec5;
    private javax.swing.JList sLec6;
    private javax.swing.JList sLec7;
    private javax.swing.JList subjectList;
    private javax.swing.JList tLec1;
    private javax.swing.JList tLec2;
    private javax.swing.JList tLec3;
    private javax.swing.JList tLec4;
    private javax.swing.JList tLec5;
    private javax.swing.JList tLec6;
    private javax.swing.JList tLec7;
    private javax.swing.JList thLec1;
    private javax.swing.JList thLec2;
    private javax.swing.JList thLec3;
    private javax.swing.JList thLec4;
    private javax.swing.JList thLec5;
    private javax.swing.JList thLec6;
    private javax.swing.JList thLec7;
    private javax.swing.JList wLec1;
    private javax.swing.JList wLec2;
    private javax.swing.JList wLec3;
    private javax.swing.JList wLec4;
    private javax.swing.JList wLec5;
    private javax.swing.JList wLec6;
    private javax.swing.JList wLec7;
    // End of variables declaration//GEN-END:variables
}
