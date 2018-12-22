/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AdminViewPanel.java
 *
 * Created on Nov 10, 2013, 12:07:10 AM
 */

package panels;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import newattendancesystem.MainFrame;
import sql.StudentSql;

/**
 *
 * @author Hemant Borade
 */
public class AdminViewPanel extends javax.swing.JPanel implements PanelInterface{

    public int indexOfTab;
    String []column=new String[]{"Enrollment No","Name","Father's Name","Contact No","Email ID","Address","Class","Semester","Section"};
    private ArrayList<String> sids;
    //boolean []isColVisible=new boolean[]{true,true,true,true,true,true,true,true,true};
    HashMap<String,TableColumn> removed=new HashMap<>();
    private String course[]=new String[]{"MCA"};
    //private String sem[][];
    //private String section[][];
    private String FID;
    //private JFormattedTextField enoText;
    //private JFormattedTextField nameText;

    /** Creates new form AdminViewPanel */
    public AdminViewPanel() {
        initComponents();
        /*enoText=new JFormattedTextField();
        nameText=new JFormattedTextField();
        SubjectSql sql=new SubjectSql(null);
        setCourse(sql.getCourse());
        sql.close();*/
        FID="admin";
        ItemListener listener=new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                JCheckBox t=(JCheckBox)e.getSource();
                //isColVisible[Integer.parseInt(t.getName())]=t.isSelected();     
                if(e.getStateChange()==ItemEvent.SELECTED){
                    String name=t.getText();
                    TableColumn col=removed.get(name);
                    if(col!=null){
                        jTable1.addColumn(col);
                        removed.put(name, null);
                    }
                    //System.out.println("Selected");
                }else{
                    String name=t.getText();
                    TableColumn col=jTable1.getColumn(name);
                    jTable1.removeColumn(col);
                    removed.put(name, col);
                    //System.out.println("deSelected");
                }
                //System.out.println(e.getStateChange());
            }
        };
        JCheckBox temp;
        for(int i=0;i<column.length;i++){
            temp=new JCheckBox(column[i]);
            //temp.setName(i+"");
            temp.addItemListener(listener);
            jPopupMenu1.add(temp);
        }
        setCourse(course);
        /*JMenuItem item;
        item=new JCheckBoxMenuItem("Item 1");
        jPopupMenu1.add(item);
        item=new JCheckBoxMenuItem("Item 2");
        jPopupMenu1.add(item);
        item=new JCheckBoxMenuItem("Item 3");
        jPopupMenu1.add(item);*/
        
        
    }

    /*public AdminViewPanel(String c[],String s[][],String sec[][],boolean add) {
        initComponents();
        sem=s;
        section=sec;
        addNewBtn.setVisible(add);
        for(String x:c){
            jComboBox1.addItem(x);
        }
    }*/

    public AdminViewPanel(String fid){
        initComponents();
        FID=fid;
    }

    /*private void initialize(int role){
        if(role==SystemUsers.ADMIN){
            
        }else if(role==SystemUsers.CLASSCOORD){
            
        }
    }*/
    
    private void setCourse(String val[]){
        jComboBox1.removeAllItems();
        for(String x:val){
            jComboBox1.addItem(x);
        }
    }

    /*private void setSemester(int index){
        jComboBox2.removeAllItems();
        for(String x:sem[index]){
            jComboBox2.addItem(x);
        }
    }

    private void setSection(int index){
        jComboBox3.removeAllItems();
        for(String x:section[index]){
            jComboBox3.addItem(x);
        }
    }*/

    private void setBatch(String val[]){
        jComboBox4.removeAllItems();
        if(val!=null){

        for(String x:val){
            jComboBox4.addItem(x);
        }
        }
    }

    private void setSemester(String val[]){
        jComboBox2.removeAllItems();;
        if(val!=null){
        for(String x:val){
            jComboBox2.addItem(x);
        }
        }
    }

    private void setSection(String val[]){
        jComboBox3.removeAllItems();
        if(val!=null){
            jComboBox3.addItem("All");
            for(String x:val){
                jComboBox3.addItem(x);
            }
        }
    }

    private void setData(String val[][],String col[]){
        //String column[]=col;
        /*if(column==null){
            column=new String[]{"EnrollmentNo","Name","Father's Name","Contact No","Email ID","Address","Class","Semester","Section"};
        }*/
        DefaultTableModel tm=new DefaultTableModel(val,column){
            //private HashMap<String,TableColumn> removed;
            
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
            
            /*public void removeColumn(String name,TableColumn col){
                removed.put(name, col);
            }
            
            public TableColumn getColumn(String name){
                return removed.get(name);
            }*/
        };
        jTable1.setModel(tm);
        jLabel7.setText(val.length+"");
        
        //jTable1.getColumnModel().getColumn("Enrollment No").setPreferredWidth(40);
        jTable1.getColumn("Enrollment No").setPreferredWidth(110);
        jTable1.getColumn("Name").setPreferredWidth(200);
        jTable1.getColumn("Father's Name").setPreferredWidth(230);
        jTable1.getColumn("Contact No").setPreferredWidth(100);
        jTable1.getColumn("Email ID").setPreferredWidth(140);
        jTable1.getColumn("Address").setPreferredWidth(350);
        jTable1.getColumn("Class").setPreferredWidth(60);
        jTable1.getColumn("Semester").setPreferredWidth(70);
        jTable1.getColumn("Section").setPreferredWidth(60);
        //JCheckBox temp;
        for(Component chkbox:jPopupMenu1.getComponents()){
            //if(chkbox instanceof JCheckBox){
                try{
                    ((JCheckBox)chkbox).setSelected(true);
                }catch(ClassCastException e){
                    System.out.println(e);
                }
            //}
        }
    }

    public void refresh(){
        jComboBox3ActionPerformed(null);
    }
    
    private String[][] getData(String col[],Object val[]){
        StudentSql sql=new StudentSql(null);
        /*String column[]=col;
        if(column==null){
            column=new String[]{"EnrollmentNo","Name","Father's Name","Contact No","Email ID","Address","Class","Semester","Section"};
        }*/
        String data[][]=sql.getAllStudentDataFromPreviousClass(col, val);
        sql.close();
        return data;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        addNewBtn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jPopupMenu1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
                jPopupMenu1PopupMenuCanceled(evt);
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setText("Class: ");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MCA" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setText(" Semester:");

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

        jLabel3.setText("Section:");

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

        jLabel5.setText("Batch No:");

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBackground(new java.awt.Color(153, 102, 0));

        jTable1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SNo", "Enrollment No", "Name", "Contact No", "Batch No"
            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        addNewBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/Button-Add-icon16.png"))); // NOI18N
        addNewBtn.setText("Add");
        addNewBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewBtnActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/edit16.png"))); // NOI18N
        jButton1.setText("Edit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/information_icon16.png"))); // NOI18N
        jButton2.setText("View Attendance");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/prints.png"))); // NOI18N
        jButton3.setText("Print");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/button_delete16.png"))); // NOI18N
        jButton4.setText("Delete");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(addNewBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap(488, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addComponent(jButton4))
                    .addComponent(addNewBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setText("Total : ");

        jLabel7.setText("100");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/setting1.jpg"))); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel6)
                .addGap(5, 5, 5)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
        
}//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        // TODO add your handling code here:
        
}//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        // TODO add your handling code here:
        
}//GEN-LAST:event_jComboBox3ItemStateChanged

    public void reload(){
        jComboBox3ItemStateChanged(null);
    }

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged
        // TODO add your handling code here:
        
}//GEN-LAST:event_jComboBox4ItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int row=jTable1.getSelectedRow();
        if(row==-1){
            JOptionPane.showMessageDialog(MainFrame.mainFrame, "Please select record.");
            return;
        }
        StudentPanel pan=new StudentPanel(sids.get(row),this);
        pan.indexOfTab=indexOfTab;
        JScrollPane sc=(JScrollPane)MainFrame.mainFrame.formContainer.getComponentAt(indexOfTab);
        JPanel p=(JPanel)sc.getViewport().getComponent(0);
        p.removeAll();
        p.add(pan);
        p.revalidate();
        MainFrame.mainFrame.formContainer.setTitleAt(indexOfTab, "Edit Student Details");

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int row=jTable1.getSelectedRow();
        if(row!=-1){
            String eno=sids.get(row);
            StudentAttendanceSummary aSum=new StudentAttendanceSummary(eno,this);
            aSum.indexOfTab=this.indexOfTab;
            JScrollPane sc=(JScrollPane)MainFrame.mainFrame.formContainer.getComponentAt(indexOfTab);
            JPanel p=(JPanel)sc.getViewport().getComponent(0);
            p.removeAll();
            //p.revalidate();
            p.add(aSum);
            p.revalidate();
            MainFrame.mainFrame.repaint();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void addNewBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewBtnActionPerformed
        // TODO add your handling code here:
        StudentPanel pan=new StudentPanel(this);
        pan.indexOfTab=indexOfTab;
        JScrollPane sc=(JScrollPane)MainFrame.mainFrame.formContainer.getComponentAt(indexOfTab);
        JPanel p=(JPanel)sc.getViewport().getComponent(0);
        p.removeAll();
        p.add(pan);
        p.revalidate();
        MainFrame.mainFrame.formContainer.setTitleAt(indexOfTab, "Add New Student");

    }//GEN-LAST:event_addNewBtnActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
        try{
            String cls=jComboBox1.getSelectedItem().toString();
            int bno=Integer.parseInt(jComboBox4.getSelectedItem().toString());
            int sem=Integer.parseInt(jComboBox2.getSelectedItem().toString());
            String sec=jComboBox3.getSelectedItem().toString();

            StudentSql sql=new StudentSql(null);

            String col[];
            Object values[];
            if(sec.equalsIgnoreCase("all")){
                col=new String[]{"course","BatchNo","Semester"};
                values=new Object[]{cls,bno,sem};
            }else{
                col=new String[]{"course","BatchNo","Semester","Section"};
                values=new Object[]{cls,bno,sem,sec};
            }
            String data[][]=sql.getAllStudentDataFromPreviousClass(col, values);
            sql.close();
            if(data!=null){
                sids=new ArrayList<>();
                for(int i=0;i<data.length;i++){
                    sids.add(data[i][StudentSql.SID]);
                }
                setData(data,null);
            }

        }catch(NullPointerException e){
            //JOptionPane.showMessageDialog(this,e.toString());
            //e.printStackTrace();
        }
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
        StudentSql sql=new StudentSql(null);
        try{
            int bno=Integer.parseInt(jComboBox4.getSelectedItem().toString());
            setSemester(sql.getSemester(jComboBox1.getSelectedItem().toString(),bno));
        }catch(NullPointerException e){
            setSemester(null);
        }
        sql.close();
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
        //int index=jComboBox2.getSelectedIndex();
        //setSection(index);
        StudentSql sql=new StudentSql(null);
        try{
            int bno=Integer.parseInt(jComboBox4.getSelectedItem().toString());
            int sem=Integer.parseInt(jComboBox2.getSelectedItem().toString());
            setSection(sql.getSection(jComboBox1.getSelectedItem().toString(),bno,sem));
        }catch(NullPointerException e){
            //JOptionPane.showMessageDialog(null,e.toString());
            setSection(null);
        }
        sql.close();
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        //int index=jComboBox1.getSelectedIndex();
        //setSemester(index);
        StudentSql sql=new StudentSql(null);
        try{
            setBatch(sql.getBatches(jComboBox1.getSelectedItem().toString()));
        }catch(NullPointerException e){
            setBatch(null);
        }
        sql.close();
        
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        jPopupMenu1.show(jLabel4, 0, 0);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jPopupMenu1PopupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jPopupMenu1PopupMenuCanceled
        // TODO add your handling code here:
        //System.out.println("Cancelled");
        /*TableColumnModel tcm=jTable1.getColumnModel();
        TableColumn col;
        for(int i=0;i<isColVisible.length;i++){
            col=tcm.getColumn(tcm.getColumnIndex(column[i]));
            if(isColVisible[i]){
                col.setMaxWidth(300);
                col.setMinWidth(40);
                col.setPreferredWidth(50);
            }else{
                col.setMaxWidth(0);
                col.setMinWidth(0); 
                col.setPreferredWidth(0);
            }
        }*/
    }//GEN-LAST:event_jPopupMenu1PopupMenuCanceled

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            // TODO add your handling code here:
            jTable1.print();
        }catch (PrinterException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(MainFrame.mainFrame, "Unable to connect to the printer.\nPlease check printer connection and try again.");
        }
        /*int n=jTable1.getColumnCount();
        for(int i=0;i<n;i++){
            System.out.println(jTable1.getColumnModel().getColumn(i).getPreferredWidth());
        }*/
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        int row=jTable1.getSelectedRow();
        if(row==-1){
            JOptionPane.showMessageDialog(MainFrame.mainFrame, "Please select record.");
            return;
        }
        int option=JOptionPane.showConfirmDialog(this, "All the attendance record and other information associated with this student will be deleted.\nDo you want to continue?","Confirmation",JOptionPane.YES_NO_OPTION);
        if(option==JOptionPane.YES_OPTION){
            StudentSql sql=new StudentSql(sids.get(row));
            boolean result=sql.delete();
            if(result){
                JOptionPane.showMessageDialog(this, "Successfully deleted.");
            }else{
                JOptionPane.showMessageDialog(this, "Unable to delete.");
            }
            sql.close();
        }
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addNewBtn;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    public boolean destroy() {
        return true;
    }

}
