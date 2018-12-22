/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package panels;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import sql.ClassLabManagerSql;


/**
 *
 * @author yash
 */
public class SettingRoomLab extends javax.swing.JPanel {

    private String cls;
    private int semester;
    private String section;
    private int curRoom;
    Vector<Short> rooms;
    Vector<String> labs;
    
    public boolean isChange;
    
    //String[] items={"A","B","C","D"};

    public SettingRoomLab() {
        initComponents();
        //Object data[][]=new Object[][]{{"Subject1",new Value("")},{"Subject2",new Value("A")},{"Subject3",new Value("A")}};
        //MyTableModel tm=new MyTableModel(data);
        
        //jTable1.setModel(tm);
        //jTable1.setDefaultRenderer(Value.class, new ValueRenderer(new Vector()));
        //jTable1.setDefaultEditor(Value.class, new ValueEditor());
        
    }
    /**
     * Creates new form SettingRoomLab
     */
    
    
    public SettingRoomLab(int sem,String sec,int curRoom) {
        initComponents();
        initialize(sem, sec, curRoom);
    }

    public void initialize(int sem,String sec,int curRoom){
        cls="MCA";
        semester=sem;
        section=sec;
        jLabel2.setText("MCA "+semester+" \""+section+"\"");
        ClassLabManagerSql sql=new ClassLabManagerSql();
        //if(curRoom!=-1){
        //    this.curRoom=curRoom;
        //}
        if(curRoom==-1){
            curRoom=sql.getRoomNo("MCA", sem, sec);
        }
        this.curRoom=curRoom;
        rooms=sql.getAvailableRooms();
        if(curRoom!=-1){
            rooms.add(0,(short)curRoom);
        }
        jComboBox1.setModel(new DefaultComboBoxModel(rooms));
        jComboBox1.setSelectedItem(curRoom);
        
        labs=sql.getComputerLabs();
        String d[][]=sql.getSubjectLab("MCA", sem, sec);
        Object data[][]=new Object[d.length][2];
        for(int i=0;i<d.length;i++){
            data[i][0]=d[i][0];
            if(d[i][1]==null){
                data[i][1]=new Value(labs.firstElement());
            }else{
                data[i][1]=new Value(d[i][1]);
            }
        }
        sql.close();
        MyTableModel tm=new MyTableModel(data);
        
        jTable1.setModel(tm);
        jTable1.setDefaultRenderer(Value.class, new ValueRenderer(labs));
        jTable1.setDefaultEditor(Value.class, new ValueEditor());
    }
    
    private class Value{
        
        String lab;

        public Value(String v) {
            lab=v;
        }
    }
    
    private class MyTableModel extends AbstractTableModel{

        private Object[][] data;
        private String[] column=new String[]{"Subject","Computer Lab"};

        public MyTableModel(Object[][] data) {
            this.data = data;
        }
        
        
        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return column.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        @Override
        public void setValueAt(Object aValue, int row, int col) {
            //super.setValueAt(aValue, rowIndex, columnIndex);
            if(col==1){
                Value v=(Value)data[row][col];
                v.lab=aValue.toString();
            }
            this.fireTableCellUpdated(row, col);
        }

        
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch(columnIndex){
                case 0:
                    return String.class;
                case 1:
                    return Value.class;
            }
            return Object.class;
        }

        @Override
        public String getColumnName(int column) {
            return this.column[column];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex==1;
        }
    }
    
    private class ValueRenderer extends JComboBox implements TableCellRenderer{

        Vector data;
        public ValueRenderer(Vector d) {
            data=d;
            this.setModel(new DefaultComboBoxModel(data));
        }

        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
                        boolean isSelected, boolean hasFocus, int row, int column) {
            if(value instanceof Value){
                Value v=(Value)value;
                this.setSelectedItem(v.lab);
            }
            return this;
        }
    }
    
    private class ValueEditor extends AbstractCellEditor implements TableCellEditor, ItemListener{

        ValueRenderer vr=new ValueRenderer(labs);

        public ValueEditor() {
            vr.addItemListener(this);
        }
        
        @Override
        public Object getCellEditorValue() {
            return vr.getSelectedItem();
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table,
            Object value, boolean isSelected, int row, int col) {

            if(value instanceof Value){
                Value v = (Value) value;
                vr.setSelectedItem(v.lab);
            }/*else{
                vr.setSelected(false);
                vr.setText("Leave");
                vr.setEnabled(false);
            }*/
            return vr;
        }
        
        @Override
        public void itemStateChanged(ItemEvent e) {
            //JComboBox cmb=(JComboBox)e.getSource();
            this.fireEditingStopped();
            //System.out.println("Item Change");
            isChange=true;
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

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(SettingRoomLab.class, "SettingRoomLab.jLabel1.text")); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(SettingRoomLab.class, "SettingRoomLab.jLabel2.text")); // NOI18N

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(SettingRoomLab.class, "SettingRoomLab.jLabel5.text")); // NOI18N

        jComboBox1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setFillsViewportHeight(true);
        jTable1.setRowHeight(20);
        jScrollPane1.setViewportView(jTable1);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/SAVE.PNG"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(SettingRoomLab.class, "SettingRoomLab.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 170, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        jComboBox1.setEnabled(enabled);
        jTable1.setEnabled(enabled);
        jButton1.setEnabled(enabled);
    }

    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int room=Integer.parseInt(jComboBox1.getSelectedItem().toString());
        ClassLabManagerSql sql=new ClassLabManagerSql();
        if(curRoom!=room){
            if(curRoom!=-1){
                sql.removeRoom(curRoom);
            }
            sql.putRoom("MCA",room, semester, section);
        }
        if(isChange){
            int choice=JOptionPane.showConfirmDialog(this, "If you make this changes in between the design of the Schedule,\n"+
                                                "the whole schedule will be reset.Do you want to continue?","Confirmation" ,JOptionPane.YES_NO_OPTION);
            if(choice==JOptionPane.YES_OPTION){
                String labName,scode;
                for(int i=0;i<jTable1.getRowCount();i++){
                    scode=jTable1.getValueAt(i, 0).toString();
                    labName=((Value)jTable1.getValueAt(i, 1)).lab;
                    //System.out.println(labName +" at "+i );
                    sql.putLab(labName, scode, cls, semester, section);
                    //rs=rs+jTable1.getValueAt(i, 0)+":"+((Value)jTable1.getValueAt(i, 1)).lab;
                }
                
            }else{
                isChange=false;
            }
        }
        JOptionPane.showMessageDialog(this, "Changes has been saved successfully.");
        //sql.commit();
        sql.close();
        //isChange=true;
        
    }//GEN-LAST:event_jButton1ActionPerformed

    /*public static void main(String arg[]){
        JFrame frm=new JFrame();
        frm.setVisible(true);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.add(new SettingRoomLab());
        frm.pack();
    }*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
