/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package panels;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import newattendancesystem.MainFrame;
import newattendancesystem.Utility;
import sql.StudentSql;

/**
 *
 * @author yash
 */
public class StudentPanel extends javax.swing.JPanel implements PanelInterface{
    
    //private String sid;
    public int indexOfTab;
    private JPanel parent;
    private String eno;
    private boolean isExist;
    
    /**
     * Creates new form StudentPanel
     */
    
    public StudentPanel() {
        initComponents();
        //setValidationPanel();
        jButton6.setText("Close");
        jButton6.setVisible(false);
    }
    
    public StudentPanel(JPanel par) {
        initComponents();
        //setValidationPanel();
        parent=par;
        //SqlClass sql=new SqlClass();
        jButton6.setText("Back");
        jLabel26.setVisible(false);
    }

    public StudentPanel(String sid,JPanel par) {
        initComponents();
        //setValidationPanel();
        eno=sid;
        parent=par;
        //this.sid=sid;
        jFormattedTextField2.setText(sid);
        jFormattedTextField2.setEditable(false);
        isExist=true;
        jButton6.setText("Back");
        setData();
    }
    
    /*private void setValidationPanel(){
        group=validationPanel1.getValidationGroup();
        group.add(jTextField10,Validators.REQUIRE_NON_EMPTY_STRING);
        group.add(jTextField9,Validators.REQUIRE_NON_EMPTY_STRING);
        group.add(jTextField11,Validators.REQUIRE_VALID_INTEGER,Validators.maxLength(11));
        group.add(jTextField12,Validators.EMAIL_ADDRESS);
        group.add(jTextField13,Validators.REQUIRE_VALID_INTEGER,Validators.maxLength(4));
        group.add(jTextField16,Validators.REQUIRE_VALID_INTEGER,Validators.maxLength(2));
        
        MyDocumentListener doc=new MyDocumentListener();
        jTextField10.getDocument().addDocumentListener(doc);
        jTextField9.getDocument().addDocumentListener(doc);
        jTextField11.getDocument().addDocumentListener(doc);
        jTextField12.getDocument().addDocumentListener(doc);
        jTextField13.getDocument().addDocumentListener(doc);
        jTextField16.getDocument().addDocumentListener(doc);
    }
    
    private class MyDocumentListener implements DocumentListener{

        @Override
        public void insertUpdate(DocumentEvent e) {
            checkValidations();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            checkValidations();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            checkValidations();
        }
        
        private void checkValidations(){
            Problem p=group.validateAll();
            if(p.isFatal()){
                jButton4.setEnabled(false);
            }else{
                jButton4.setEnabled(true);
            }
        }
    }*/
    
    private void setData(){
        StudentSql sql=new StudentSql(eno);
        String data[]=sql.getData();
        //System.out.println(data.length);
        if(data!=null){
            jTextField10.setText(data[StudentSql.SNAME]);
            jTextField9.setText(data[StudentSql.FNAME]);
            jTextField11.setText(data[StudentSql.CNO]);
            jTextField12.setText(data[StudentSql.EID]);
            jTextArea2.setText(data[StudentSql.ADDRESS]);
        
            jTextField14.setText(data[StudentSql.COURSE]);
            jTextField13.setText(data[StudentSql.BNO]);
            jTextField15.setText(data[StudentSql.SECTION]);
            jTextField16.setText(data[StudentSql.SRNO]);
            jComboBox3.setSelectedItem(data[StudentSql.SEM]);
        }
    }
    
    private boolean validatePanel(){
        boolean result;
        result=Utility.validateName(jTextField10);
        result=Utility.validateName(jTextField9) && result;
        result=Utility.validateOnlyLong(jTextField11) && result;
        result=Utility.validateEmailID(jTextField12) && result;
        result=Utility.validateOnlyInteger(jTextField13) && result;
        result=Utility.validateOnlyInteger(jTextField16) && result;
        if(result){
            if(jTextField13.getText().length()>4){
                JOptionPane.showMessageDialog(this, "Too long entry for the batch");
                return false;
            }
            if(jTextField16.getText().length()>2){
                JOptionPane.showMessageDialog(this, "Too long entry for the Srno");
                return false;
            }
            if(jTextField11.getText().length()>12){
                JOptionPane.showMessageDialog(this, "Too long entry for the Contact No");
                return false;
            }
        }else{
            JOptionPane.showMessageDialog(this, "Invalid enries.");
        }
        return result;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jTextField13 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jButton4 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        try{
            jFormattedTextField2 = new javax.swing.JFormattedTextField(new javax.swing.text.MaskFormatter("####UU######"));
            jButton6 = new javax.swing.JButton();

            setBackground(new java.awt.Color(255, 255, 255));

            jPanel3.setBackground(new java.awt.Color(0, 0, 0));
            jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

            jLabel14.setBackground(new java.awt.Color(0, 0, 0));
            jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
            jLabel14.setForeground(new java.awt.Color(255, 255, 255));
            org.openide.awt.Mnemonics.setLocalizedText(jLabel14, org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jLabel14.text")); // NOI18N

            jPanel4.setBackground(new java.awt.Color(255, 255, 255));
            jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jPanel4.border.title"), 0, 0, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

            jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
            org.openide.awt.Mnemonics.setLocalizedText(jLabel15, org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jLabel15.text")); // NOI18N

            jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
            org.openide.awt.Mnemonics.setLocalizedText(jLabel16, org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jLabel16.text")); // NOI18N

            jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
            org.openide.awt.Mnemonics.setLocalizedText(jLabel17, org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jLabel17.text")); // NOI18N

            jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
            org.openide.awt.Mnemonics.setLocalizedText(jLabel18, org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jLabel18.text")); // NOI18N

            jLabel19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
            org.openide.awt.Mnemonics.setLocalizedText(jLabel19, org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jLabel19.text")); // NOI18N

            jTextField9.setText(org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jTextField9.text")); // NOI18N

            jTextField10.setText(org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jTextField10.text")); // NOI18N
            jTextField10.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jTextField10ActionPerformed(evt);
                }
            });

            jTextField11.setColumns(13);
            jTextField11.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            jTextField11.setText(org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jTextField11.text")); // NOI18N
            jTextField11.addInputMethodListener(new java.awt.event.InputMethodListener() {
                public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                }
                public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                    jTextField11CaretPositionChanged(evt);
                }
            });
            jTextField11.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    jTextField11KeyTyped(evt);
                }
            });

            jTextField12.setText(org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jTextField12.text")); // NOI18N
            jTextField12.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jTextField12ActionPerformed(evt);
                }
            });

            jTextArea2.setColumns(20);
            jTextArea2.setRows(5);
            jScrollPane2.setViewportView(jTextArea2);

            javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
            jPanel4.setLayout(jPanel4Layout);
            jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel16)
                        .addComponent(jLabel15)
                        .addComponent(jLabel17)
                        .addComponent(jLabel18)
                        .addComponent(jLabel19))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField9)
                        .addComponent(jTextField10)
                        .addComponent(jTextField12)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(jScrollPane2))
                    .addContainerGap())
            );
            jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel19)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            );

            jPanel5.setBackground(new java.awt.Color(255, 255, 255));
            jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jPanel5.border.title"), 0, 0, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

            jLabel20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
            org.openide.awt.Mnemonics.setLocalizedText(jLabel20, org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jLabel20.text")); // NOI18N

            jLabel21.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
            org.openide.awt.Mnemonics.setLocalizedText(jLabel21, org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jLabel21.text")); // NOI18N

            jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
            org.openide.awt.Mnemonics.setLocalizedText(jLabel22, org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jLabel22.text")); // NOI18N

            jLabel23.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
            org.openide.awt.Mnemonics.setLocalizedText(jLabel23, org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jLabel23.text")); // NOI18N

            jLabel24.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
            org.openide.awt.Mnemonics.setLocalizedText(jLabel24, org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jLabel24.text")); // NOI18N

            jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6" }));
            jComboBox3.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jComboBox3ActionPerformed(evt);
                }
            });

            jTextField13.setText(org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jTextField13.text")); // NOI18N

            jTextField14.setText(org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jTextField14.text")); // NOI18N

            jTextField15.setColumns(1);
            jTextField15.setText(org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jTextField15.text")); // NOI18N
            jTextField15.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    jTextField15KeyPressed(evt);
                }
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    jTextField15KeyTyped(evt);
                }
            });

            jTextField16.setText(org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jTextField16.text")); // NOI18N

            javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
            jPanel5.setLayout(jPanel5Layout);
            jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel24)
                        .addComponent(jLabel20)
                        .addComponent(jLabel21)
                        .addComponent(jLabel22)
                        .addComponent(jLabel23))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jComboBox3, 0, 80, Short.MAX_VALUE)
                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                        .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                        .addComponent(jTextField15)
                        .addComponent(jTextField16))
                    .addGap(21, 21, 21))
            );
            jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel20)
                        .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel21)
                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel22)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel23)
                        .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel24)
                        .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(3, 3, 3))
            );

            jLabel25.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
            jLabel25.setForeground(new java.awt.Color(255, 255, 255));
            org.openide.awt.Mnemonics.setLocalizedText(jLabel25, org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jLabel25.text")); // NOI18N

            jButton4.setBackground(new java.awt.Color(255, 255, 255));
            jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
            jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/SAVE.PNG"))); // NOI18N
            org.openide.awt.Mnemonics.setLocalizedText(jButton4, org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jButton4.text")); // NOI18N
            jButton4.setMargin(new java.awt.Insets(2, 6, 2, 6));
            jButton4.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton4ActionPerformed(evt);
                }
            });

            jLabel26.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
            jLabel26.setForeground(new java.awt.Color(255, 255, 255));
            org.openide.awt.Mnemonics.setLocalizedText(jLabel26, org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jLabel26.text")); // NOI18N
            jLabel26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            jLabel26.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jLabel26MouseClicked(evt);
                }
            });

        }catch(java.text.ParseException e){

        }
        jFormattedTextField2.setText(org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jFormattedTextField2.text")); // NOI18N
        jFormattedTextField2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jFormattedTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jFormattedTextField2KeyTyped(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/back16.jpeg"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButton6, org.openide.util.NbBundle.getMessage(StudentPanel.class, "StudentPanel.jButton6.text")); // NOI18N
        jButton6.setMargin(new java.awt.Insets(2, 6, 2, 6));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel26)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jButton4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton6)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel25)
                    .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel26)
                        .addGap(17, 17, 17)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4)
                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10ActionPerformed

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jTextField15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField15KeyPressed
        // TODO add your handling code here:
        char keyChar=evt.getKeyChar();
        if(keyChar>='a' && keyChar<='z'){
            jTextField15.setText(((char)keyChar-32)+"");
        }else if(keyChar>='A' && keyChar<='Z'){
            
        }else{
            jTextField15.setText("");
        }
    }//GEN-LAST:event_jTextField15KeyPressed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if(eno==null){
            eno=jFormattedTextField2.getText();
            if(eno==null){
                JOptionPane.showMessageDialog(this, "Invalid Enrollment no");
                return;
            }
        }
        if(!validatePanel()){
            return;
        }
        String name=jTextField10.getText();
        String fname=jTextField9.getText();
        String cno=jTextField11.getText();
        String eid=jTextField12.getText();
        String address=jTextArea2.getText();
        String cls=jTextField14.getText();
        //System.out.println(jTextField13.getText());
        int bno=Integer.parseInt(jTextField13.getText());
        int sem=Integer.parseInt(jComboBox3.getSelectedItem().toString());
        String section=jTextField15.getText();
        int srno=Integer.parseInt(jTextField16.getText());
        StudentSql sql=new StudentSql(eno);
        boolean rslt=sql.insertStudent(eno, name, cls, sem, section, srno, fname, eid, cno, address, bno, isExist);    
        sql.close();
        if(rslt){
            JOptionPane.showMessageDialog(this, "Information saved.");
        }else{
            JOptionPane.showMessageDialog(this, "Unable to save information.");
        }
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel26MouseClicked

    private void jFormattedTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField2KeyTyped

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        JScrollPane sc=(JScrollPane)MainFrame.mainFrame.formContainer.getComponentAt(indexOfTab);
        JPanel p=(JPanel)sc.getViewport().getComponent(0);
        p.removeAll();
        p.revalidate();
        p.add(parent);
        ((AdminViewPanel)parent).refresh();
        MainFrame.mainFrame.repaint();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTextField11CaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTextField11CaretPositionChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTextField11CaretPositionChanged

    private void jTextField11KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField11KeyTyped
        // TODO add your handling code here:
        //Utility.validateOnlyInteger(jTextField11);
    }//GEN-LAST:event_jTextField11KeyTyped

    private void jTextField15KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField15KeyTyped
        // TODO add your handling code here:
        char keyChar=evt.getKeyChar();
        if(keyChar>='a' && keyChar<='z'){
            evt.consume();
            jTextField15.setText(((char)(keyChar-32))+"");
        }else if(keyChar>='A' && keyChar<='Z'){
            
        }else{
            evt.consume();
            jTextField15.setText("");
        }
    }//GEN-LAST:event_jTextField15KeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables

    @Override
    public boolean destroy() {
        return true;
    }
}
