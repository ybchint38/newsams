/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FacultyPanel.java
 *
 * Created on Nov 9, 2013, 7:11:02 PM
 */

package panels;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import newattendancesystem.MainFrame;
import newattendancesystem.Utility;
import sql.FacultySql;
import sql.SqlClass;
import sql.SubjectSql;

/**
 *
 * @author apex predator
 */
public class FacultyPanel extends javax.swing.JPanel implements PanelInterface{

    private static int IMG_HEIGHT=130;
    private static int IMG_WIDTH=120;
    public int indexOfTab;
    private JPanel viewPanel;
    private Vector<String> allSubject;
    private Vector<String> fSubject;
    private boolean isExists;
    private boolean isSubjectChanged;
    private ResultSet resultSet;
    private String ID;
    private FacultySql sql;
    private boolean isImageChanged;
    
    /*public FacultyPanel() {
        initComponents();
        allSubject=new Vector();
        fSubject=new Vector();
        //setListDnD();
        //setList1();
        jList1.setEnabled(false);
        jList2.setEnabled(false);
        jButton7.setText("Clear");
        jComboBox2.setModel(new DefaultComboBoxModel(FacultySql.DESG));
    }*/

    public FacultyPanel(JPanel p) {
        initComponents();
        allSubject=new Vector();
        fSubject=new Vector();
        //setListDnD();
        //setList1();
        viewPanel=p;
        jList1.setEnabled(false);
        jList2.setEnabled(false);
        jButton7.setText("Back");
        sql=new FacultySql(null);
        jComboBox2.setModel(new DefaultComboBoxModel(Utility.DESG));
    }
    
    public FacultyPanel(String fid,JPanel p){
        initComponents();
        viewPanel=p;
        ID=fid;
        isExists=true;
        jLabel6.setText("Available");
        isSubjectChanged=false;
        setListDnD();
        sql=new FacultySql(fid);
        sql.setAutoCommit(false);
        setFaculty();
        setList2();
        setList1();
        if(p==null){
            jButton7.setText("Clear");
            jComboBox1.setEnabled(false);
            jComboBox2.setEnabled(false);
            jComboBox3.setEnabled(false);
        }else{
            jButton7.setText("Back");
            jList1.setEnabled(false);
            jList2.setEnabled(false);
            jTextField3.setEnabled(false);
            jButton1.setEnabled(false);
        }
        jComboBox2.setModel(new DefaultComboBoxModel(Utility.DESG));
    }
    
    

    private void setListDnD(){
        jList1.setDragEnabled(true);
        jList1.setDropMode(DropMode.INSERT);
        jList1.setDropTarget(new DropTarget(jList2,new DropTargetAdapter(){
        public void drop(DropTargetDropEvent dtde) {
            jButton5ActionPerformed(null);
        }
        }));

        jList2.setDragEnabled(true);
        jList2.setDropTarget(new DropTarget(null,new DropTargetAdapter(){
        public void drop(DropTargetDropEvent dtde) {
            jButton3ActionPerformed(null);
        }
        }));
        jList2.setDropMode(DropMode.INSERT);
    }

    private void setList1(){
        SubjectSql sql=new SubjectSql();
        allSubject=sql.getAllSubject();
        if(fSubject!=null){
            Enumeration e=fSubject.elements();
            while(e.hasMoreElements()){
                allSubject.remove(e.nextElement().toString());
            }
        }
        if(allSubject!=null){
            jList1.setListData(allSubject);
        }
        sql.close();
    }

    private void setList2(){
        fSubject=sql.getSpecializedSubject();
        if(fSubject!=null){
            jList2.setListData(fSubject);
        }else{
            fSubject=new Vector();
        }
    }

    private void setFaculty(){
        resultSet=sql.getDataUpdatable();

        try{
            if(resultSet.next()){
                jTextField1.setText(resultSet.getString("fname"));
                jTextField2.setText(resultSet.getString("contactNo"));
                
                //String dprt=resultSet.getString("department");
                //if(dprt!=null){
                    //jComboBox1.removeAllItems();
                //    jComboBox1.addItem(dprt);
                //}
                String desg=resultSet.getString("designation");
                jComboBox2.setSelectedItem(desg);
                int ts=resultSet.getInt("timeslot");
                jComboBox3.setSelectedIndex(ts);
                Image scaledImage=null;
                try{
                    InputStream is=resultSet.getBlob("image").getBinaryStream();
                    //Image scaledImage;
                    //if(is==null)
                        File file=new File("temp/"+ID+".jpg");
                        FileOutputStream fos=new FileOutputStream(file);
                        int n;
                        while((n=is.read())!=-1){
                            fos.write(n);
                        }
                        fos.close();
                        Toolkit toolkit= this.getToolkit();
                        Image image=toolkit.getImage(file.getAbsolutePath());
                        scaledImage=image.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, Image.SCALE_DEFAULT);
                        imageTxt.setText(file.getName());
                    /*}else{
                        File file=new File("image/default.jpg");
                        Toolkit toolkit= this.getToolkit();
                        Image image=toolkit.getImage(file.getAbsolutePath());
                        scaledImage=image.getScaledInstance(imageLbl.getWidth(), imageLbl.getHeight(), Image.SCALE_DEFAULT);
                    }*/
                    //imageLbl.setIcon(new ImageIcon(scaledImage));
                    is.close();
                }catch(NullPointerException e){
                    //File file=new File(getClass().getResource("/image/default.jpg").getPath().replaceAll("%20", " "));
                    File file=new File("temp/default.jpg");
                    if(!file.exists()){
                        
                        try{
                            InputStream is=getClass().getResourceAsStream("/image/default.jpg");
                            FileOutputStream fos=new FileOutputStream(file);
                            int n;
                            while((n=is.read())!=-1){
                                fos.write(n);
                            }
                        }catch(Exception ex){
                            JOptionPane.showMessageDialog(this, "File not found");
                        }
                    }
                    Toolkit toolkit= this.getToolkit();
                    Image image=toolkit.getImage(file.getAbsolutePath());
                    scaledImage=image.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, Image.SCALE_DEFAULT);
                }catch(Exception e){
                    JOptionPane.showMessageDialog(this, e.toString());
                    e.printStackTrace();
                }
                imageLbl.setIcon(new ImageIcon(scaledImage));
                SqlClass bsql=new SqlClass();
                Connection con=bsql.getConnection();
                Statement s=con.createStatement();
                ResultSet rs=s.executeQuery("select id from at_login where fid='"+ID+"'");
                if(rs.next()){
                    jTextField3.setText(rs.getString(1));
                }
                bsql.close();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e.toString());
            e.printStackTrace();
        }
        jLabel6.setVisible(false);
        //jLabel7.setVisible(false);
    }

    private boolean validatePanel(){
        boolean result;
        result=Utility.validateName(jTextField1);
        result=Utility.validateOnlyLong(jTextField2) && result;
        if(result){
            if(jTextField2.getText().length()>12){
                JOptionPane.showMessageDialog(this, "Too long entry for the Contact No");
                return false;
            }
        }else{
            JOptionPane.showMessageDialog(this, "Invalid enries.");
            return false;
        }
        
        jButton1ActionPerformed(null);
        if(!jLabel6.getText().equalsIgnoreCase("Available") && jTextField3.isEnabled()){
            JOptionPane.showMessageDialog(MainFrame.mainFrame, "This username is not Available.");
            return false;
        }
        File f=new File(imageTxt.getText());
        if(isImageChanged && !f.exists()){
            JOptionPane.showMessageDialog(MainFrame.mainFrame, "Image not Found.");
            return false;
        }
        if(f.length()>=16777215){
            JOptionPane.showMessageDialog(MainFrame.mainFrame, "Image is too big.");
            return false;
        }
        return true;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        imageLbl = new javax.swing.JLabel();
        imageTxt = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel5 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jSeparator1 = new javax.swing.JSeparator();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Basic Details"));

        jLabel1.setText("Name:");

        jLabel2.setText("Contact No:");

        jLabel3.setText("Department:");

        jTextField2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Department of Computer Science and Applications" }));

        jLabel8.setText("Designation:");

        jLabel9.setText("Time slot:");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "09:00 - 04:00", "10:00 - 05:00" }));

        imageLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/default.jpg"))); // NOI18N
        imageLbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        imageLbl.setOpaque(true);

        imageTxt.setBackground(new java.awt.Color(255, 255, 255));
        imageTxt.setEditable(false);
        imageTxt.setText("default.jpg");
        imageTxt.setMaximumSize(new java.awt.Dimension(200, 20));
        imageTxt.setPreferredSize(new java.awt.Dimension(200, 20));
        imageTxt.setVisible(false);

        jButton8.setText("Browse");
        jButton8.setMargin(new java.awt.Insets(2, 8, 2, 8));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(imageTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton8))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jTextField1))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBox3, 0, 118, Short.MAX_VALUE)
                                    .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, 0, 1, Short.MAX_VALUE)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(imageLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(imageLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(imageTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8))
                .addGap(12, 12, 12))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Login Credentials"));

        jLabel4.setText("User Name:");

        jTextField3.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextField3CaretUpdate(evt);
            }
        });
        jTextField3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField3FocusLost(evt);
            }
        });

        jButton1.setText("Check Availability");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("");
        jLabel6.setVisible(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jList1MousePressed(evt);
            }
        });
        jList1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jList1MouseDragged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jLabel5.setText("Specialized in subjects:");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/SAVE.PNG"))); // NOI18N
        jButton2.setText("Save");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jList2MousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(jList2);

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setText(">");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setText(">>");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton5.setText("<");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton6.setText("<<");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Close");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(jLabel5))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5)
                        .addGap(18, 18, 18)
                        .addComponent(jButton6)
                        .addGap(95, 95, 95))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton7))
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jList1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MousePressed
        // TODO add your handling code here:
        jList2.clearSelection();
}//GEN-LAST:event_jList1MousePressed

    private void jList1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseDragged
        // TODO add your handling code here:
        //JOptionPane.showMessageDialog(null,"Dragged");
}//GEN-LAST:event_jList1MouseDragged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if(validatePanel()){
            if(isExists){ //update existing
                try{
                    resultSet.updateString("fname", jTextField1.getText());
                    resultSet.updateString("ContactNo", jTextField2.getText());
                    resultSet.updateString("Department", jComboBox1.getSelectedItem().toString());
                    resultSet.updateString("Designation", jComboBox2.getSelectedItem().toString());
                    resultSet.updateInt("timeslot", jComboBox3.getSelectedIndex());
                    
                    if(isImageChanged){
                        File file=new File(imageTxt.getText());
                        FileInputStream fis=new FileInputStream(file);
                        //resultSet.updateBlob("image", fis);
                        resultSet.updateBinaryStream("image", fis, fis.available());
                    }
                    if(isSubjectChanged){
                        sql.updateSpecializedSubject(fSubject);
                    }
                    ///
                    resultSet.updateRow();
                    JOptionPane.showMessageDialog(MainFrame.mainFrame, "Data updated...");
                    if(jTextField3.isEnabled()){
                        String newuid=jTextField3.getText();
                        SqlClass bsql=new SqlClass();
                        Connection con=bsql.getConnection();
                        Statement s=con.createStatement();
                        int x=s.executeUpdate("update at_login set id='"+ newuid +"' where fid='"+ID+"'");
                        if(x!=1){
                            throw new Exception("Error occured during changing your User Name.");
                        }
                    }
                    sql.commit();
                    if(isImageChanged){
                        MainFrame.mainFrame.updateProfilePic(ID);
                    }
                }catch(Exception e){
                    JOptionPane.showMessageDialog(this, e.toString());
                    //e.printStackTrace();
                    sql.rollback();
                }
                
            }else{        //Add New
                sql=new FacultySql(null);
                sql.setAutoCommit(false);
                File f;
                if(!isImageChanged){
                    f=new File("temp/default.jpg");
                }else{
                    f=new File(imageTxt.getText());
                }
                int ts=jComboBox3.getSelectedIndex();
                String pwd=sql.insert(jTextField1.getText(), jTextField2.getText(), jComboBox1.getSelectedItem().toString(),f,ts,jComboBox2.getSelectedItem().toString(),jTextField3.getText(), fSubject);
                if(pwd!=null){
                    sql.commit();
                    JOptionPane.showMessageDialog(MainFrame.mainFrame, "Default password is: "+pwd);
                }else{
                    sql.rollback();
                }
                sql.close();
            }
        }
}//GEN-LAST:event_jButton2ActionPerformed

    private void jList2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList2MousePressed
        // TODO add your handling code here:
        jList1.clearSelection();
}//GEN-LAST:event_jList2MousePressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        Object val[]=jList1.getSelectedValues();
        for(Object temp:val){
            fSubject.addElement((String)temp);
            allSubject.removeElement((String)temp);
        }
        jList1.setListData(allSubject);
        jList2.setListData(fSubject);
        isSubjectChanged=true;
}//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        for(String temp:allSubject){
            fSubject.addElement(temp);
        }
        allSubject.removeAllElements();
        jList1.setListData(allSubject);
        jList2.setListData(fSubject);
        isSubjectChanged=true;
}//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        Object val[]=jList2.getSelectedValues();
        for(Object temp:val){
            fSubject.removeElement((String)temp);
            allSubject.addElement((String)temp);
        }
        jList1.setListData(allSubject);
        jList2.setListData(fSubject);
        isSubjectChanged=true;
}//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        for(String temp:fSubject){
            allSubject.addElement(temp);
        }
        fSubject.removeAllElements();
        jList1.setListData(allSubject);
        jList2.setListData(fSubject);
        isSubjectChanged=true;
}//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        if(viewPanel!=null){
            sql.close();
            ((AdminFacultyViewPanel)viewPanel).refresh();
            MainFrame.mainFrame.replacePanel(viewPanel, indexOfTab, "Faculty Details");
        }else{
            //MainFrame.mainFrame.formContainer.remove(indexOfTab);
            //MainFrame.mainFrame.removeTab(indexOfTab);
            jTextField1.setText(null);
            jTextField2.setText(null);
            jComboBox1.setSelectedIndex(0);
            jButton6ActionPerformed(null);
        }
}//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        JFileChooser fc=new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", new String[]{"jpg","jpeg"}));
        int val=fc.showOpenDialog(MainFrame.mainFrame);
        if(val==JFileChooser.APPROVE_OPTION){
            File file=fc.getSelectedFile();
            if(file!=null && file.isFile()){
                isImageChanged=true;
                Toolkit toolkit= this.getToolkit();
                Image image=toolkit.getImage(file.getAbsolutePath());
                Image scaledimage=image.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, Image.SCALE_DEFAULT);
                imageLbl.setIcon(new ImageIcon(scaledimage));
                imageTxt.setText(file.getAbsolutePath());
            }
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        FacultySql s=new FacultySql(MainFrame.mainFrame.getUserID());
        String newUsername=jTextField3.getText();
        String logid=s.getLoginID();
        //System.out.println(logid);
        if(newUsername.equalsIgnoreCase(logid)){
            jLabel6.setText("Available");
            jLabel6.setForeground(Color.GREEN);
            //jLabel7.setIcon(new ImageIcon(getClass().getResource("/image/correct.jpeg")));
            s.close();
            //System.out.println("same");
            return;
        }
        if(s.isUserNameAvailable(newUsername)){
            jLabel6.setText("Available");
            jLabel6.setForeground(Color.GREEN);
            //jLabel7.setIcon(new ImageIcon(getClass().getResource("/image/correct.jpeg")));
        }else{
            jLabel6.setText("Not Available");
            jLabel6.setForeground(Color.RED);
            //jLabel7.setIcon(new ImageIcon(getClass().getResource("/image/close2ico.jpg")));
        }
        s.close();
        //System.out.println(jLabel6.getText());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField3FocusLost
        // TODO add your handling code here:
        jButton1ActionPerformed(null);
    }//GEN-LAST:event_jTextField3FocusLost

    private void jTextField3CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextField3CaretUpdate
        // TODO add your handling code here:
        jLabel6.setVisible(true);
        //jLabel7.setVisible(true);
        jButton1ActionPerformed(null);
    }//GEN-LAST:event_jTextField3CaretUpdate

    /*public static void main(String arg[]){
        JFrame frm=new JFrame();
        frm.add(new FacultyPanel("f101",null));
        //frm.add(new FacultyPanel());
        frm.setVisible(true);
        frm.pack();
    }*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel imageLbl;
    private javax.swing.JTextField imageTxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables

    public boolean destroy() {
        int i=JOptionPane.showConfirmDialog(null, "Do you want to close?");
        if(JOptionPane.YES_OPTION==i){
            return true;
        }
        return false;
    }

}
