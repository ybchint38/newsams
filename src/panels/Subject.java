/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Subject.java
 *
 * Created on Nov 4, 2013, 11:04:29 AM
 */

package panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import newattendancesystem.MainFrame;
import sql.SubjectSql;


/**
 *
 * @author apex predator
 */
public class Subject extends javax.swing.JPanel implements PanelInterface{

    public int indexOfTab;
    /** Creates new form Subject */
    private final int x=0;
    private int y=0;
    private final int w=700;
    private final int h=30;
    private int selected=-1;
    private ArrayList<SubjectPanel> subjects;
    private ArrayList<String> removeList;

    /** Creates new form Subject */
    public Subject(String cls,int s) {
        initComponents();
        jTextField1.setText(cls);
        jTextField2.setText(s+"");

        heading();
        SubjectSql sql=new SubjectSql();
        Map<String,String[]> val=sql.getSubjects(s);
        subjects=new ArrayList<>(0);
        removeList=new ArrayList<>(0);
        if(val!=null){
            Set<Entry<String,String[]>> set=val.entrySet();
            int indx=0;
            SubjectPanel sc;
            String temp[];
            for(Entry<String,String[]> t:set){
                temp=t.getValue();
                sc=new SubjectPanel(t.getKey(),temp[0],temp[1],Integer.parseInt(temp[2]),indx);
                subjects.add(sc);
                //sc.setBounds(x, y, w, h);
                //jPanel4.add(sc);
                //y=y+30;
            indx++;
            }
        }
        sql.close();
        arrangeSubjectPanel();
    }

    private void arrangeSubjectPanel(){
        jPanel4.removeAll();
        y=0;
        Collections.sort(subjects);
        int index=0;
        for(SubjectPanel sp:subjects){
            sp.setBounds(x, y, w, h);
            sp.index=index;
            jPanel4.add(sp);
            y=y+30;
            index++;
        }
        jPanel4.revalidate();
    }
    
    private void heading(){
        JPanel p=new JPanel();
        p.setLayout(null);

        JLabel code=new JLabel("Subject Code");
        JLabel name=new JLabel("Subject Name");
        //code.setHorizontalAlignment(JLabel.CENTER);
        //name.setHorizontalAlignment(JLabel.CENTER);

        code.setBounds(10, 5, 100, 20);
        name.setBounds(110, 5, w-100, 20);

        //code.setBackground(Color.GRAY);
        //name.setBackground(Color.GRAY);

        p.add(code);
        p.add(name);

        p.setBounds(x, y, w, h);
        jPanel4.add(p);
        jPanel4.revalidate();

        //this.repaint();
        y=y+40;
    }

    private void deselectOther(){
        for(SubjectPanel sp:subjects){
            //if(sp.index!=selected){
                sp.select(false);
            //}
        }
    }

        private void refreshjPanel4(){
        y=0;
        jPanel4.removeAll();
        heading();
        for(SubjectPanel sc:subjects){
            sc.setBounds(x, y, w, h);
            jPanel4.add(sc);
            y=y+30;
        }
        jPanel4.revalidate();
        jPanel4.repaint();
    }


    private class SubjectPanel extends JPanel implements Comparable<SubjectPanel> {
        JLabel code;
        JLabel name;
        String file;
        boolean modify;
        int index;
        int subjectType;
        final Color select=Color.BLUE;
        final Color deselect=Color.BLACK;


        SubjectPanel(String cd,String nm,String fl,int type,int indx){
            code=new JLabel(cd);
            name=new JLabel(nm);
            subjectType=type;
            index=indx;
            file=fl;
            initialize();
            setListener();
        }

        private void initialize(){
            this.setBackground(Color.WHITE);
            this.setLayout(null);
            code.setBounds(10, 5, 100, 20);
            name.setBounds(110, 5, w-100, 20);
            code.setBackground(Color.BLUE);
            name.setBackground(Color.BLUE);
            code.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            name.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            this.add(code);
            this.add(name);
            this.revalidate();
        }

        private void setListener(){
            code.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e){
                    select(true);
                    jButton2.setEnabled(true);
                    jButton3.setEnabled(true);
                    selected=index;
                }
                
            });

            name.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e){
                    Subject.this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    SubjectSql sql=new SubjectSql();
                    File f=sql.downloadSyllabus(code.getText());
                    if(f==null){
                        JOptionPane.showMessageDialog(null, "Syllabus not found. or\nThis subject may be project/practical.");
                        Subject.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        return;
                    }
                    JOptionPane.showMessageDialog(null, "Download complete at \n"+f.getAbsolutePath());
                    try{
                        Desktop.getDesktop().open(f);
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(null, "Unable to open file. It may be damaged or corrupted.");
                    }
                    Subject.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }

                @Override
                public void mouseEntered(MouseEvent e){
                    name.setForeground(Color.red);
                    //Subject.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e){
                    name.setForeground(Color.BLACK);
                    //Subject.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            });
        }

        void select(boolean val){
            if(val){
                //selected=index;
                //name.setForeground(select);
                deselectOther();
                code.setForeground(select);
            }else{
                code.setForeground(deselect);
                //name.setForeground(deselect);
            }
        }

        @Override
        public int compareTo(SubjectPanel o) {
            return this.code.getText().toString().compareTo(o.code.getText().toString());
        }
    }

    private class AddNewSubject extends javax.swing.JDialog {

    //private JFrame parent;
    private SubjectPanel sdetail;
    /** Creates new form AddNewSubject */

    /*public AddNewSubject(JFrame p) {
        initComponents();
        parent=p;
        parent.setEnabled(false);
        this.setLocation(200, 100);
    }*/

    public AddNewSubject(SubjectPanel sp) {
        super(MainFrame.mainFrame, "Edit Subject Details");
        initComponents();
        //parent=MainFrame.mainFrame;
        sdetail=sp;
        //parent.setEnabled(false);

        /*int y=parent.getHeight()/2;
        int x=parent.getWidth()/2;
        y=y-(this.getHeight()/2);
        x=x-(this.getWidth()/2);

        this.setLocation(x, y);*/
        this.setLocationByPlatform(true);
        this.setLocationRelativeTo(MainFrame.mainFrame);
        jTextField1.setText(sp.code.getText());
        jTextField2.setText(sp.name.getText());
        jTextField3.setText(sp.file);
        jComboBox1.setSelectedIndex(sp.subjectType);
    }

    public AddNewSubject() {
        super(MainFrame.mainFrame, "Add New Subject");
        initComponents();
        //parent=MainFrame.mainFrame;
        sdetail=null;
        //parent.setEnabled(false);
        /*int y=parent.getHeight()/2;
        int x=parent.getWidth()/2;
        y=y-(this.getHeight()/2);
        x=x-(this.getWidth()/2);

        this.setLocation(x, y);*/
        this.setLocationByPlatform(true);
        this.setLocationRelativeTo(MainFrame.mainFrame);
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Subject Code:");
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        //org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(AddNewSubject.class, "AddNewSubject.jLabel1.text")); // NOI18N

        jLabel2.setText("Subject Name:");
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        //org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(AddNewSubject.class, "AddNewSubject.jLabel2.text")); // NOI18N

        jLabel3.setText("Syllabus:");
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        //org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(AddNewSubject.class, "AddNewSubject.jLabel3.text")); // NOI18N

        
        
        //org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(AddNewSubject.class, "AddNewSubject.jButton1.text")); // NOI18N

        jButton1.setText("Browse");
        
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setText("Save");
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/SAVE.PNG")));
        //org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(AddNewSubject.class, "AddNewSubject.jButton2.text")); // NOI18N

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setText("Cancel");
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/exit_Hover.png")));
        //org.openide.awt.Mnemonics.setLocalizedText(jButton3, org.openide.util.NbBundle.getMessage(AddNewSubject.class, "AddNewSubject.jButton3.text")); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Subject Type:");
        //org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(AddNewSubject.class, "AddNewSubject.jLabel4.text")); // NOI18N

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Theory", "Lab 1", "Lab 2", "Additonal Theory", "Additional Lab" }));

        jTextField1.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent evt){
                int c=evt.getKeyChar();
                if(c>=97 && c<=122){
                    evt.consume();
                    String txt=jTextField1.getText();
                    c=c-32;
                    txt=txt+(char)c;
                    jTextField1.setText(txt);
                }
            }
        });

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               jButton1ActionPerformed(evt);
            }
        });

        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox1, 0, 128, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        //parent.setEnabled(true);
        dispose();
    }

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        if(jTextField1.getText().equalsIgnoreCase("")){
                JOptionPane.showMessageDialog(this, "Please enter Subject code");
                return;
        }
        if(jTextField2.getText().equalsIgnoreCase("")){
                JOptionPane.showMessageDialog(this, "Please enter Subject name");
                return;
        }
        if(jTextField1.getText().length()>7){
            JOptionPane.showMessageDialog(this, "Subject code is too long.");
            return;
        }
        if(jTextField2.getText().length()>60){
            JOptionPane.showMessageDialog(this, "Subject name is too long.");
            return;
        }
        boolean isExists=true;
        try{
                File file=new File(jTextField3.getText());
                FileReader fr=new FileReader(file);
                fr.read();
                if(!file.isFile()){
                    JOptionPane.showMessageDialog(this, "Please select only file");
                    return;
                }
        }catch(Exception e){
                int x=JOptionPane.showConfirmDialog(this,"File not found at specified location.\n Do you want to continue?","Confirm Box",JOptionPane.YES_NO_OPTION);
                //System.out.println(x);
                if(x!=JOptionPane.YES_OPTION){
                    return;
                }
                isExists=false;
        }
        if(sdetail!=null){
            sdetail.code.setText(jTextField1.getText());
            sdetail.name.setText(jTextField2.getText());
            int type=jComboBox1.getSelectedIndex();
            sdetail.subjectType=type;
            if(isExists){
                sdetail.file=jTextField3.getText();
            }
            sdetail.modify=true;
            sdetail.revalidate();
            //parent.setEnabled(true);
            Subject.this.repaint();
            JOptionPane.showMessageDialog(null,"Subject Information Modified.");
        }else{
            int i=subjects.size()-1;
            i++;
            String filename="";
            if(isExists){
                filename=jTextField3.getText();
            }
            int type=jComboBox1.getSelectedIndex();
            SubjectPanel sp=new SubjectPanel(jTextField1.getText(),jTextField2.getText(),filename,type,i);
            sp.modify=true;
            subjects.add(sp);
            //sp.setBounds(x, y, w, h);
            //jPanel4.add(sp);
            //jPanel4.revalidate();
            arrangeSubjectPanel();
            //parent.setEnabled(true);
            Subject.this.repaint();
            y=y+30;
            JOptionPane.showMessageDialog(null,"Subject Added.");
        }
        dispose();
    }

    private void formWindowClosed(java.awt.event.WindowEvent evt) {
        // TODO add your handling code here:
        //JOptionPane.showMessageDialog(null,"Closed");
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        JFileChooser fc=new JFileChooser();
        //fc.setAcceptAllFileFilterUsed(false);
        //fc.addChoosableFileFilter(new FileNameExtensionFilter("Adobe Reader File(*.pdf)",new String[]{"pdf","doc","docx"}));
        fc.showOpenDialog(this);
        if(fc.getSelectedFile()!=null){
            jTextField3.setText(fc.getSelectedFile().getAbsolutePath());
            //System.out.println(fc.getSelectedFile().getAbsolutePath());
            //OtherSql sql=new SubjectSql();
            //sql.insertSubject(jTextField1.getText(), jTextField1.getText(), fc.getSelectedFile());
            //sql.close();
        }
    }

    /**
    * @param args the command line arguments
    */
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("SUBJECT DETAILS");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel1.setText("Class:");

        jLabel2.setText("Semester:");

        jTextField1.setEditable(false);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.setEditable(false);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/Button-Add-icon16.png"))); // NOI18N
        jButton1.setText("Add ");
        jButton1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/edit16.png"))); // NOI18N
        jButton2.setText("Edit");
        jButton2.setEnabled(false);
        jButton2.setMargin(new java.awt.Insets(2, 10, 2, 10));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/button_delete16.png"))); // NOI18N
        jButton3.setText("Remove");
        jButton3.setEnabled(false);
        jButton3.setMargin(new java.awt.Insets(2, 10, 2, 10));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/SAVE.PNG"))); // NOI18N
        jButton4.setText("Save Changes");
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
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(null);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new AddNewSubject().setVisible(true);
}//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        SubjectPanel sp=subjects.get(selected);
        new AddNewSubject(sp).setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        SubjectSql sql=new SubjectSql();
        for(String scode:removeList){
            if(sql.deleteSubject(scode)){
                //System.out.println("Deleted: "+scode);
            }else{
                //System.out.println("Unable to Delete: "+scode);
            }
        }
        int n=subjects.size();
        String sc;
        SubjectPanel sp;
        String cls=jTextField1.getText();
        int sem=Integer.parseInt(jTextField2.getText());
        for(int i=0;i<n;i++){
            sp=subjects.get(i);
            if(sp.modify){
                sc=sp.code.getText();
                if(sql.deleteSubject(sc)){
                    //System.out.println("Subject deleted "+ sc);
                }
                File file=null;
                if(!sp.file.equalsIgnoreCase("") && !sp.file.equalsIgnoreCase("na")){
                    //System.out.println(sp.file);
                    file=new File(sp.file);
                    //System.out.println(file.getAbsolutePath());
                }
                if(sql.insertSubject(sc, sp.name.getText(),file,sp.subjectType,cls,sem)){
                        //System.out.println("Subject Inserted "+ sc);
                }
                
            }
        }
        JOptionPane.showMessageDialog(this,"All Changes successfully completed." );
        sql.close();
}//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if(selected!=-1){
            String scode=subjects.get(selected).code.getText();
            removeList.add(scode);
            subjects.remove(selected);
            refreshjPanel4();
            //System.out.println("Removed at "+selected);
            selected=-1;
            jButton2.setEnabled(false);
            jButton3.setEnabled(false);
        }
    }//GEN-LAST:event_jButton3ActionPerformed


    public boolean destroy(){
        return true;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables

}
