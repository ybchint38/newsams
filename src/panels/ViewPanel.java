/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ViewPanel.java
 *
 * Created on Nov 9, 2013, 10:58:29 AM
 */

package panels;

import newattendancesystem.MainFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import sql.AttendanceSql;
import sql.FacultySql;
import sql.SqlClass;
import sql.SubjectSql;
import sql.StudentSql;

/**
 *
 * @author dominator
 */
public class ViewPanel extends javax.swing.JPanel implements PanelInterface{

    public int indexOfTab;
    private Vector column;
    private Vector data;
    public static final int FACULTYDETAIL_UPDATE=0;
    public static final int STUDENTLEAVE_ADD=1;
    public static final int STUDENTATTENDANCE=2;
    public static final int STUDENTLISTVIEW=3;


    /** Creates new form ViewPanel */
    /*public ViewPanel(String head,String action,ActionListener listener,Vector c,Vector d) {
        initComponents();
        headlbl.setText(head);
        actionbtn.setText(action);
        actionbtn.addActionListener(listener);
        column=c;
        data=d;
        setTableData();
        actionbtn.setEnabled(false);
    }

    public ViewPanel(String head,String action,ActionListener listener) {
        this(head,action,listener,null,null);
        initComponents();
    }*/

    public ViewPanel(int ch){
        initComponents();
        switch(ch){
            case FACULTYDETAIL_UPDATE:
                setForFacultyUpdate();
                break;
            default:
                throw new UnsupportedOperationException("Invalid Choice");
        }
    }

    public ViewPanel(int sem,String section,int ch){
        initComponents();
        switch(ch){
            case STUDENTLEAVE_ADD:
                setForStudentLeave_Add(sem,section);
                break;
            case  STUDENTATTENDANCE:
                setForStudentAttendance(sem,section);
                break;
            case STUDENTLISTVIEW:
                setForStudentListView(sem,section);
                break;
            default:
        }
    }
    public ViewPanel(){
        initComponents();
        actionbtn.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                LeaveDetail pan=new LeaveDetail(ViewPanel.this);
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
            DefaultTableModel tm=new DefaultTableModel(data,column){
                public boolean isCellEditable(int row,int col){
                    return false;
                }
            };
            jTable1.setModel(tm);
        }
    }


    private void setForFacultyUpdate(){
        headlbl.setText("Faculty Details");
        actionbtn.setText("Edit");
        FacultySql sql=new FacultySql(null);
        data=sql.getAllFaculties();
        sql.close();
        column=new Vector();
        column.addElement("Sno");
        column.addElement("FID");
        column.addElement("Name");
        column.addElement("Contact No");
        column.addElement("Department");
        setTableData();
        jTable1.getColumn("FID").setMinWidth(0);
        jTable1.getColumn("FID").setPreferredWidth(0);
        jTable1.getColumn("FID").setMaxWidth(0);

        jTable1.getColumn("Sno").setPreferredWidth(40);
        jTable1.getColumn("Name").setPreferredWidth(304);
        jTable1.getColumn("Contact No").setPreferredWidth(120);
        jTable1.getColumn("Department").setPreferredWidth(110);

        jTable1.getColumn("Sno").setResizable(false);
        jTable1.getColumn("Name").setResizable(false);
        jTable1.getColumn("Contact No").setResizable(false);
        jTable1.getColumn("Department").setResizable(false);

        jTable1.getTableHeader().setReorderingAllowed(false);
        actionbtn.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                int index=jTable1.getSelectedRow();
                if(index>=0){
                    String id=jTable1.getValueAt(index, 1).toString();
                    FacultyPanel pan=new FacultyPanel(id,ViewPanel.this);
                    pan.indexOfTab=indexOfTab;
                    JScrollPane sc=(JScrollPane)MainFrame.mainFrame.formContainer.getComponentAt(indexOfTab);
                    JPanel p=(JPanel)sc.getViewport().getComponent(0);
                    p.removeAll();
                    p.add(pan);
                    p.revalidate();
                    MainFrame.mainFrame.formContainer.setTitleAt(indexOfTab, "Faculty Details");
                }
            }
        });
    }

    private void setForStudentLeave_Add(int sem,String section){
        headlbl.setText("Student List");
        actionbtn.setText("Add Leave");
        StudentSql sql=new StudentSql(null);
        String temp[][]=sql.getAllStudentData(new String[]{"semester","section"},new Object[]{sem,section});
        sql.close();

        data=new Vector();
        Vector tmp;
        for(String t[]:temp){
            tmp=new Vector();
            tmp.addElement(t[StudentSql.SRNO]);
            tmp.addElement(t[StudentSql.SID]);
            tmp.addElement(t[StudentSql.SNAME]);
            tmp.addElement(t[StudentSql.CNO]);
            tmp.addElement(t[StudentSql.EID]);
            tmp.addElement(t[StudentSql.ADDRESS]);
            data.addElement(tmp);
        }

        column=new Vector();
        column.addElement("SrNo");
        column.addElement("Enrollment No");
        column.addElement("Name");
        column.addElement("Contact No");
        column.addElement("Email ID");
        column.addElement("Address");
        setTableData();
        
        jTable1.getColumn("SrNo").setPreferredWidth(45);
        jTable1.getColumn("Enrollment No").setPreferredWidth(130);
        jTable1.getColumn("Name").setPreferredWidth(162);
        jTable1.getColumn("Contact No").setPreferredWidth(137);

        //jTable1.getColumn("SrNo").setResizable(false);
        //jTable1.getColumn("Enrollment No").setResizable(false);
        //jTable1.getColumn("Name").setResizable(false);
        //jTable1.getColumn("Contact No").setResizable(false);

        jTable1.getTableHeader().setReorderingAllowed(false);
        actionbtn.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                int index=jTable1.getSelectedRow();
                if(index>=0){
                    String id=jTable1.getValueAt(index, 1).toString();
                    LeaveDetail pan=new LeaveDetail(id,ViewPanel.this);
                    pan.indexOfTab=indexOfTab;
                    JScrollPane sc=(JScrollPane)MainFrame.mainFrame.formContainer.getComponentAt(indexOfTab);
                    JPanel p=(JPanel)sc.getViewport().getComponent(0);
                    p.removeAll();
                    p.add(pan);
                    p.revalidate();
                    MainFrame.mainFrame.repaint();
                    MainFrame.mainFrame.formContainer.setTitleAt(indexOfTab, "Leave Details");
                }
                /*System.out.println(jTable1.getColumn("SrNo").getWidth()+"  "+
                        jTable1.getColumn("Enrollment No").getWidth()+"  "+
                        jTable1.getColumn("Name").getWidth()+"  "+
                        jTable1.getColumn("Contact No").getWidth());*/
            }
        });
    }

    private void setForStudentAttendance(int sem,String section){
        actionbtn.setVisible(false);
        headlbl.setText("Attendance List - "+sem+" \""+section+"\"");
        
        column=new Vector();
        column.addElement("SrNo");
        column.addElement("Name");
        
        SubjectSql osql=new SubjectSql();
        Set<String> sub=osql.getSubjects(sem).keySet();
        osql.close();
        for(String scodes:sub){
            column.addElement(scodes);
        }
        column.addElement("Total");
        column.addElement("Attendance(%)");
        
        data=new Vector();
        Vector temp=new Vector();
        temp.addElement("");
        temp.addElement("");
        int total;
        int bno=getBatch(sem);
        Date from;
        Date to=new Date();
        int sum=0;
        AttendanceSql asql=new AttendanceSql();
        ArrayList<Date> startdate=new ArrayList(0);
        for(String scode:sub){
            from=asql.getFirstLecture("MCA", sem, section, scode, bno);
            if(from==null){
                total=0;
            }else{
                total=asql.getTotalLectures("MCA", sem, section, scode, bno, new java.sql.Date(from.getTime()), new java.sql.Date(to.getTime()));
            }temp.addElement(total);
            sum=sum+total;
            startdate.add(from);
        }
        asql.close();
        temp.addElement(sum);
        temp.addElement("");
        data.addElement(temp);
        
        String [][]slist=this.getStudentList(sem,section);//srno,name,sid

        StudentSql ssql=new StudentSql(null);
        for(int i=0;i<slist.length;i++){
            temp=new Vector();
            temp.addElement(slist[i][0]);
            temp.addElement(slist[i][1]);

            ssql.setStudentID(slist[i][2]);
            int indx=0;
            sum=0;
            for(String scode:sub){
                from=startdate.get(indx);
                if(from==null){
                    total=0;
                }else{
                    total=ssql.getAttendanceCount(new java.sql.Date(from.getTime()), new java.sql.Date(to.getTime()), scode, sem, bno,AttendanceSql.PRESENT);
                }
                temp.addElement(total);
                sum=sum+total;
                indx++;
            }
            temp.addElement(sum);
            temp.addElement("");

            data.addElement(temp);
        }
        ssql.close();
        setTableData();
        calculateAttendance();
        jTable1.getColumn("SrNo").setPreferredWidth(40);
        jTable1.getColumn("Name").setPreferredWidth(210);
        for(int i=2;i<jTable1.getColumnCount()-2;i++){
            jTable1.getColumnModel().getColumn(i).setPreferredWidth(50);
        }
        jTable1.getColumn("Total").setPreferredWidth(100);
        jTable1.getColumn("Attendance(%)").setPreferredWidth(120);
        //jTable1.setEnabled(false);
        //jTable1.setAutoCreateRowSorter(true);
    }

    private void calculateAttendance(){
        int ncol=jTable1.getColumnCount()-2;
        int nrow=jTable1.getRowCount();
        int total=Integer.parseInt(jTable1.getValueAt(0, ncol).toString());
        int ob;
        double per;
        for(int i=1;i<nrow;i++){
            ob=Integer.parseInt(jTable1.getValueAt(i, ncol).toString());
            per=(ob*100.0)/total;
            jTable1.setValueAt(String.format("%.2f %%", per),i,ncol+1);
        }
    }

    private String[][] getStudentList(int semester,String section){
        StudentSql sql=new StudentSql(null);
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

    private int getBatch(int sem){
        /*Date dt=new Date();
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
        return cy+1900;*/
        SqlClass sql=new SqlClass();
        int bno=sql.getBatch("MCA", sem);
        sql.close();
        return bno;
    }

    private void setForStudentListView(final int sem, String section) {
        headlbl.setText("Student List");
        actionbtn.setText("View Attendance");
        StudentSql sql=new StudentSql(null);
        String temp[][]=sql.getAllStudentData(new String[]{"semester","section"},new Object[]{sem,section});
        sql.close();

        data=new Vector();
        Vector tmp;
        for(String t[]:temp){
            tmp=new Vector();
            tmp.addElement(t[StudentSql.SRNO]);
            tmp.addElement(t[StudentSql.SID]);
            tmp.addElement(t[StudentSql.SNAME]);
            tmp.addElement(t[StudentSql.FNAME]);
            tmp.addElement(t[StudentSql.CNO]);
            tmp.addElement(t[StudentSql.EID]);
            tmp.addElement(t[StudentSql.ADDRESS]);
            data.addElement(tmp);
        }

        column=new Vector();
        column.addElement("SrNo");
        column.addElement("Enrollment No");
        column.addElement("Name");
        column.addElement("Father's Name");
        column.addElement("Contact No");
        column.addElement("Email ID");
        column.addElement("Address");
        setTableData();

        jTable1.getColumn("SrNo").setPreferredWidth(45);
        jTable1.getColumn("Enrollment No").setPreferredWidth(130);
        jTable1.getColumn("Name").setPreferredWidth(162);
        jTable1.getColumn("Contact No").setPreferredWidth(137);

        //jTable1.getColumn("SrNo").setResizable(false);
        //jTable1.getColumn("Enrollment No").setResizable(false);
        //jTable1.getColumn("Name").setResizable(false);
        //jTable1.getColumn("Contact No").setResizable(false);

        jTable1.getTableHeader().setReorderingAllowed(false);

        actionbtn.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                //try{
                int row=jTable1.getSelectedRow();
                if(row!=-1){
                    String eno=jTable1.getValueAt(row, 1).toString();
                    StudentAttendanceSummary aSum=new StudentAttendanceSummary(eno,ViewPanel.this,sem);
                    aSum.indexOfTab=ViewPanel.this.indexOfTab;
                    JScrollPane sc=(JScrollPane)MainFrame.mainFrame.formContainer.getComponentAt(indexOfTab);
                    JPanel p=(JPanel)sc.getViewport().getComponent(0);
                    p.removeAll();
                    p.revalidate();
                    p.add(aSum);
                    p.revalidate();
                }
                //}catch(PrinterException ex){
                //    JOptionPane.showMessageDialog(MainFrame.mainFrame, ex.toString());
                //}

            }
        });
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

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getColumn(1).setResizable(false);
        jTable1.getColumnModel().getColumn(2).setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(actionbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 947, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(actionbtn)
                    .addComponent(headlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton actionbtn;
    private javax.swing.JLabel headlbl;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    public boolean destroy() {
        return true;
    }



}
