/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainFrame.java
 *
 * Created on Oct 21, 2013, 10:58:02 PM
 */

package newattendancesystem;

import testpackage.*;
import newattendancesystem.*;
import component.CustomDialog;
import component.ProcessDialog;
import component.SystemUsers;
import component.VerticalLabelUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import panels.ChangePasswordPanel;
import panels.LoginPanel;
import panels.PanelInterface;
import panels.design.TopPanel;
import sql.FacultySql;
import testpackage.TestFrame;

/**
 *
 * @author Yash Borade
 */

public class MainFrame extends javax.swing.JFrame {

    private ProcessDialog processDialog;
    public static MainFrame mainFrame;
    //public static final int SESSION=Utility.PRESESSION;
    private SystemUsers currentUser;
    private static final String upArrow="/image/navigator/1uparrow.PNG";
    private static final String downArrow="/image/navigator/1downarrow.PNG";
    private static final String leftArrow="/image/navigator/1leftarrow.png";
    private static final String rightArrow="/image/navigator/1rightarrow.png";

    public final ImageIcon upArrowIcon=new ImageIcon(getClass().getResource(upArrow));
    public final ImageIcon downArrowIcon=new ImageIcon(getClass().getResource(downArrow));
    public final ImageIcon leftArrowIcon=new ImageIcon(getClass().getResource(leftArrow));
    public final ImageIcon rightArrowIcon=new ImageIcon(getClass().getResource(rightArrow));
    /** Creates new form MainFrame */
    
    public final static GlassPane glassPane=new GlassPane();
    
    public MainFrame() {
        initComponents();
        
        this.setGlassPane(glassPane);
        
        topPanelControl.setIcon(upArrowIcon);
        leftPanelControl.setIcon(leftArrowIcon);

        //Toolkit toolkit= this.getToolkit();
        try {
            //this.setIconImage(toolkit.getImage("src/image/svimi.jpg"));
            
            //this.setIconImage(toolkit.getImage(getClass().getResource("/image/svimi.jpg").getPath().replaceAll("%20", " ")));
            this.setIconImage(ImageIO.read(getClass().getResource("/image/svimi.jpg")));
        } catch (IOException ex) {
            //Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        topContainer.add(new TopPanel(this));
        topContainer.revalidate();
        
        jPanel2.setVisible(false);
        jPanel5.setVisible(false);
        jLabel2.setVisible(false);
        //jLabel6.setVisible(false);
        this.repaint();
        mainFrame=this;

        //setPopupMenu();
        LoginPanel vp=new LoginPanel();
        JScrollPane sc=new JScrollPane();
        JPanel pan=new JPanel();
        pan.setLayout(new GridBagLayout());
        pan.setBackground(Color.BLACK);
        pan.add(vp);
        sc.setViewportView(pan);
        formContainer.addTab("Login", sc);
        //vp.indexOfTab=formContainer.indexOfComponent(sc);

    }

    public void initializePopupMenu(Component comp,HashMap<String,Object> data,ActionListener al){
        Set keySet=data.keySet();
        Iterator<String> it=keySet.iterator();
        String temp;
        jPopupMenu1.removeAll();
        while(it.hasNext()){
            temp=it.next();
            //System.out.println(temp);
            Object menu=findItem(data, temp,al);
            if(menu instanceof JMenu){
                jPopupMenu1.add((JMenu)menu);
            }else{
                jPopupMenu1.add((JMenuItem)menu);
            }
        }
        jPopupMenu1.show(comp, comp.getWidth()-2, 0);
    }
    
    private Object findItem(HashMap<String,Object> data, String key,ActionListener al){
        Object val=data.get(key);
        if(val instanceof String){
            JMenuItem item=new JMenuItem(val.toString());
            item.setName(key);
            item.addActionListener(al);
            return item;
        }else{
            HashMap map=(HashMap)val;
            JMenu menu=new JMenu(key);
            Set keySet=map.keySet();
            Iterator<String> it=keySet.iterator();
            String temp;
            while(it.hasNext()){
                temp=it.next();
                menu.add((JMenuItem)findItem(map, temp,al));
            }
            return menu;
        }
    }
    
    public void showProcessDialog(){
        if(processDialog==null){
            processDialog=new ProcessDialog(this,true);
        }
        processDialog.start();
    }
    
    public void hideProcessDialog(){
        processDialog.stop();
    }
    
    public void startProcessing(String title){
        jLabel4.setVisible(true);
        jLabel4.setText(title);
        jProgressBar1.setVisible(true);
    }
    
    public void changeState(String title){
        jLabel4.setText(title);
    }
    
    public void stopProcessing(){
        jLabel4.setVisible(false);
        jProgressBar1.setVisible(false);
    }
    /*private void setPopupMenu(){
        Date dt=new Date();
        Calendar cal=Calendar.getInstance();
        int day=cal.get(Calendar.DAY_OF_WEEK);
        int index=day-2;
        System.out.println(day+" "+index);
        JMenuItem temp;
        cal.add(Calendar.DATE, -index);
        temp=new JMenuItem("Monday");
        temp.setEnabled(false);
        temp.setToolTipText(cal.get(Calendar.DATE)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR));
        jPopupMenu1.add(temp);
        temp=new JMenuItem("Tuesday");
        temp.setEnabled(false);
        cal.add(Calendar.DATE, 1);
        temp.setToolTipText(cal.get(Calendar.DATE)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR));
        jPopupMenu1.add(temp);

        temp=new JMenuItem("Wednesday");
        temp.setEnabled(false);
        cal.add(Calendar.DATE, 1);
        temp.setToolTipText(cal.get(Calendar.DATE)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR));
        jPopupMenu1.add(temp);

        temp=new JMenuItem("Thursday");
        temp.setEnabled(false);
        cal.add(Calendar.DATE, 1);
        temp.setToolTipText(cal.get(Calendar.DATE)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR));
        jPopupMenu1.add(temp);

        temp=new JMenuItem("Friday");
        temp.setEnabled(false);
        cal.add(Calendar.DATE, 1);
        temp.setToolTipText(cal.get(Calendar.DATE)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR));
        jPopupMenu1.add(temp);

        temp=new JMenuItem("Saturday");
        temp.setEnabled(false);
        cal.add(Calendar.DATE, 1);
        temp.setToolTipText(cal.get(Calendar.DATE)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR));
        jPopupMenu1.add(temp);

        switch(day){
            case Calendar.SATURDAY:
                jPopupMenu1.getComponent(5).setEnabled(true);
            case Calendar.FRIDAY:
                jPopupMenu1.getComponent(4).setEnabled(true);
            case Calendar.THURSDAY:
                jPopupMenu1.getComponent(3).setEnabled(true);
            case Calendar.WEDNESDAY:
                jPopupMenu1.getComponent(2).setEnabled(true);
            case Calendar.TUESDAY:
                jPopupMenu1.getComponent(1).setEnabled(true);
            case Calendar.MONDAY:
                jPopupMenu1.getComponent(0).setEnabled(true);
        }
    }*/
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        topContainer = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        topPanelControl = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        leftContainer = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        leftPanelControl = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        formContainer = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel4 = new javax.swing.JLabel();

        jMenuItem1.setText("Windows");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(jMenuItem1);

        jMenuItem2.setText("Windows Classic");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(jMenuItem2);

        jMenuItem3.setText("Nimbus");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(jMenuItem3);

        jMenuItem4.setText("Metal");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(jMenuItem4);

        jMenuItem5.setText("Motif");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(jMenuItem5);

        jMenuItem6.setText("System Default");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(jMenuItem6);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Student Attendance Management System");

        topContainer.setBackground(new java.awt.Color(204, 204, 255));
        topContainer.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(0, 0, 0));

        topPanelControl.setBackground(new java.awt.Color(0, 0, 0));
        topPanelControl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/navigator/1uparrow.PNG"))); // NOI18N
        topPanelControl.setBorder(null);
        topPanelControl.setBorderPainted(false);
        topPanelControl.setFocusPainted(false);
        topPanelControl.setFocusable(false);
        topPanelControl.setMargin(new java.awt.Insets(0, 0, 0, 0));
        topPanelControl.setOpaque(false);
        topPanelControl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                topPanelControlActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText(" Welcome to Student Attendance Management System");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Change Password");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel2MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(topPanelControl, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(topPanelControl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2))
        );

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(null);

        leftContainer.setBackground(new java.awt.Color(0, 0, 0));
        leftContainer.setLayout(new javax.swing.BoxLayout(leftContainer, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(leftContainer);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));

        leftPanelControl.setBackground(new java.awt.Color(0, 0, 0));
        leftPanelControl.setFocusPainted(false);
        leftPanelControl.setFocusable(false);
        leftPanelControl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leftPanelControlActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Operations");
        jLabel3.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jLabel3.setUI(new VerticalLabelUI());
        jLabel3.setVisible(false);
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(leftPanelControl, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(leftPanelControl, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));

        formContainer.setBackground(new java.awt.Color(0, 0, 0));
        formContainer.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        formContainer.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        formContainer.setOpaque(true);
        formContainer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formContainerMouseClicked(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(0, 0, 0));

        jProgressBar1.setIndeterminate(true);

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("jLabel4");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jProgressBar1.setVisible(false);
        jLabel4.setVisible(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(formContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(formContainer)
                .addGap(0, 0, 0)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(topContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(topContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void topPanelControlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_topPanelControlActionPerformed
        // TODO add your handling code here:
        if(topContainer.isVisible()){
            topContainer.setVisible(false);
            topPanelControl.setIcon(downArrowIcon);
        }else{
            topContainer.setVisible(true);
            topPanelControl.setIcon(upArrowIcon);
        }
    }//GEN-LAST:event_topPanelControlActionPerformed

    private void leftPanelControlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leftPanelControlActionPerformed
        // TODO add your handling code here:
        if(jPanel2.isVisible()){
           jPanel2.setVisible(false);
           leftPanelControl.setIcon(rightArrowIcon);
           jLabel3.setVisible(true);
        }else{
            jPanel2.setVisible(true);
            leftPanelControl.setIcon(leftArrowIcon);
            jLabel3.setVisible(false);
        }
    }//GEN-LAST:event_leftPanelControlActionPerformed

    private void formContainerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formContainerMouseClicked
        // TODO add your handling code here:
        //jPopupMenu1.show(this, evt.getXOnScreen(), evt.getYOnScreen());
    }//GEN-LAST:event_formContainerMouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        new CustomDialog(this,true, new ChangePasswordPanel(currentUser.getID()), "Change Password").setVisible(true);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseEntered
        // TODO add your handling code here:
        jLabel2.setForeground(Color.GREEN);
    }//GEN-LAST:event_jLabel2MouseEntered

    private void jLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseExited
        // TODO add your handling code here:
        jLabel2.setForeground(Color.WHITE);
    }//GEN-LAST:event_jLabel2MouseExited

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        leftPanelControlActionPerformed(null);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        Utility.setWindowsLAF();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        Utility.setWindowsClassicLAF();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        Utility.setNimbusLAF();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        Utility.setMetalLAF();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        Utility.setMotifLAF();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        Utility.setDefault();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    public void setSystemUser(final String id,final String fname){
        
        //new Thread(){

          //  @Override
           // public void run() {
                
                //MainFrame.this.showProcessDialog();
                formContainer.removeAll();
                currentUser=new SystemUsers(id,formContainer);
                leftContainer.removeAll();
                currentUser.setOperationPanel(leftContainer);
                leftContainer.revalidate();
                jLabel1.setText("Welcome, "+fname);
                TopPanel tp=(TopPanel)topContainer.getComponent(0);
                tp.loggedIn();
                jPanel2.setVisible(true);
                jPanel5.setVisible(true);
                jLabel2.setVisible(true);
                //jLabel6.setVisible(true);
                FacultySql sql=new FacultySql(id);
                Image image=null;
                try{
                    InputStream is=sql.getImage();
                    //Image scaledImage;
                    //if(is==null)
                    File dir=new File("temp");
                    if(!dir.exists()){
                        dir.mkdir();
                    }
                    File file=new File("temp/"+id+".jpg");
                    FileOutputStream fos=new FileOutputStream(file);
                    int n;
                    while((n=is.read())!=-1){
                        fos.write(n);
                    }
                    fos.close();
                    Toolkit toolkit= MainFrame.this.getToolkit();
                    image=toolkit.getImage(file.getAbsolutePath());
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
                            JOptionPane.showMessageDialog(MainFrame.this, "File not found");
                        }
                    }
                    Toolkit toolkit= MainFrame.this.getToolkit();
                    image=toolkit.getImage(file.getAbsolutePath());
                }catch(Exception e){
                    JOptionPane.showMessageDialog(MainFrame.this, e.toString());
                    e.printStackTrace();
                }
                sql.close();
                tp.setProfileImage(image);
                //MainFrame.this.hideProcessDialog();
            //}
            
        //}.start();
        
    }

    public void updateProfilePic(String fid){
        if(currentUser.getID().equals(fid)){
            TopPanel tp=(TopPanel)topContainer.getComponent(0);
            FacultySql sql=new FacultySql(fid);
                Image image=null;
                try{
                    InputStream is=sql.getImage();
                    //Image scaledImage;
                    //if(is==null)
                    File dir=new File("temp");
                    if(!dir.exists()){
                        dir.mkdir();
                    }
                    File file=new File("temp/"+fid+".jpg");
                    FileOutputStream fos=new FileOutputStream(file);
                    int n;
                    while((n=is.read())!=-1){
                        fos.write(n);
                    }
                    fos.close();
                    Toolkit toolkit= MainFrame.this.getToolkit();
                    image=toolkit.getImage(file.getAbsolutePath());
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
                            JOptionPane.showMessageDialog(MainFrame.this, "File not found");
                        }
                    }
                    Toolkit toolkit= MainFrame.this.getToolkit();
                    image=toolkit.getImage(file.getAbsolutePath());
                }catch(Exception e){
                    JOptionPane.showMessageDialog(MainFrame.this, e.toString());
                    //e.printStackTrace();
                }
                sql.close();
                tp.setProfileImage(image);
        }
    }
    
    public boolean resetSystemUser(){
        formContainer.removeAll();
        currentUser=null;
        jLabel1.setText("Welcome to Student Attendance Management System");
        jPanel2.setVisible(false);
        jPanel5.setVisible(false);
        jLabel2.setVisible(false);
        //jLabel6.setVisible(false);
        LoginPanel vp=new LoginPanel();
        JScrollPane sc=new JScrollPane();
        JPanel pan=new JPanel();
        pan.setBackground(Color.BLACK);
        pan.add(vp);
        sc.setViewportView(pan);
        formContainer.addTab("Login", sc);
        return true;
    }

    public void removeTab(int indx){
        JScrollPane sc=(JScrollPane)this.formContainer.getComponentAt(indx);
        String hc=sc.getName();
        currentUser.activeTabs.remove(hc);
        this.formContainer.remove(indx);
    }

    public void removeTab(JScrollPane sc){
        JPanel panel=(JPanel)sc.getViewport().getComponent(0);
        PanelInterface pi=(PanelInterface)panel.getComponent(0);
        if(pi.destroy()){
            String hc=sc.getName();
            currentUser.activeTabs.remove(hc);
            this.formContainer.remove(sc);
        }
    }

    public int addTab(String title,String hc,PanelInterface form){
        JPanel panel=new JPanel();
        panel.setBackground(Color.WHITE);
        JScrollPane scrollPane=new JScrollPane();
        scrollPane.setName(hc);
        //panel.setBackground(Color.BLACK);
        panel.setLayout(new GridBagLayout());
        panel.add((JPanel)form);
        scrollPane.setViewportView(panel);
        panel.revalidate();
        formContainer.addTab(title, scrollPane);
        currentUser.activeTabs.put(hc, scrollPane);
        int index=formContainer.indexOfComponent(scrollPane);
        formContainer.setSelectedIndex(index);
        return index;
    }

    public void replacePanel(JPanel replaceWith,int indexOfTab,String title){
        JScrollPane sc=(JScrollPane)this.formContainer.getComponentAt(indexOfTab);
        JPanel p=(JPanel)sc.getViewport().getComponent(0);
        p.removeAll();
        p.add(replaceWith);
        p.revalidate();
        this.formContainer.setTitleAt(indexOfTab, title);
    }
    
    /**
    * @param args the command line arguments
    */

    public String getUserID(){
        return currentUser.getID();
    }

    /*public int getSession(){
        return currentUser.session;
    }*/

    public static void main(String args[]) {
        
        /* Set the Nimbus look and feel */
        
        
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TestFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TestFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TestFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TestFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
         
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
                /*Object o=new HashMap();
                if(o instanceof Set){
                    System.out.println("String");
                }*/
            }
        });
    }

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTabbedPane formContainer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel leftContainer;
    private javax.swing.JButton leftPanelControl;
    private javax.swing.JPanel topContainer;
    private javax.swing.JButton topPanelControl;
    // End of variables declaration//GEN-END:variables

}
