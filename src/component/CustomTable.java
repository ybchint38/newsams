/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package component;

import panels.EnterAttendance;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import sql.AttendanceSql;
import newattendancesystem.Utility;
/**
 *
 * @author apex predator
 */
public class CustomTable {

    private static EnterAttendance frm;

    public CustomTable(EnterAttendance ref){
        frm=ref;
    }


    public static class Value{
        public boolean present;
        public String text;

        public Value(String s,boolean b){
            present=b;
            text=s;
        }

        public int getStatus(){
            if(present){
                return AttendanceSql.PRESENT;
            }else{
                return AttendanceSql.ABSENT;
            }
        }
    }

    public static class DataModel extends AbstractTableModel {

        //private static final int MAX = 8;
        private String[] columns;
        //private List<Value> values = new ArrayList<Value>();
        private Object[][] data;

        public DataModel(String col[],Object [][]d) {
            columns=col;
            data=d;
            for(int i=0;i<data.length;i++){
                if(data[i][1]!=null){
                    data[i][1]=Utility.toMyCase(data[i][1].toString());
                }
            }
        }

        @Override
        public int getRowCount() {
            //return MAX;
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public Object getValueAt(int row, int col) {
            /*if (col == 0) {
                return data[row][0];
            } else if (col == 1) {
                return data[row][1];
            } else if(col==2){
                //if(data[row][2]==null){
                //   return null;
                //}else{
                    return data[row][2];
                //}
            }else{
                //System.out.println("returning null");
                return null;
            }*/
            return data[row][col];
        }

        @Override
        public void setValueAt(Object aValue, int row, int col) {
            if ((col%3) == 2) {
                if(data[row][col]!=null){
                    ((Value)data[row][col]).present = (Boolean) aValue;
                    if((Boolean) aValue){
                        ((Value)data[row][col]).text = "Present";
                    }else{
                        ((Value)data[row][col]).text = "Absent";
                    }
                    frm.updateAttendance((Boolean) aValue,row+1);
                }
                this.fireTableCellUpdated(row, col);
                
            }
        }

        @Override
        public Class<?> getColumnClass(int col) {
            if ((col%3) == 0) {
                return String.class;
            } else if ((col%3) == 1) {
                return String.class;
            } else if((col%3)==2){
                return Value.class;
            }else{
                return null;
            }
        }

        @Override
        public String getColumnName(int col) {
            return columns[col];
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return (col%3) == 2;
        }
    }


    public static class ValueRenderer extends JCheckBox
        implements TableCellRenderer {

        private static final Color hilite = new Color(0xE8E8E8);

        public ValueRenderer() {
            this.setOpaque(true);
            //this.setFont(new Font(Font.SERIF,Font.BOLD,14));
        }

        @Override
        public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int col) {
            if(value instanceof Value){
                Value v = (Value) value;
                this.setSelected(v.present);
                this.setText(v.text);
                if (isSelected) {
                    this.setBackground(hilite);
                } else {
                    this.setBackground(Color.white);
            }
                if(v.present){
                    this.setBackground(Color.GREEN);
                }else{
                    this.setBackground(Color.RED);
                }
                this.setEnabled(true);
            }else{
                this.setSelected(false);
                this.setText("Leave");
                this.setEnabled(false);
                this.setBackground(Color.ORANGE);
            }
            return this;
        }
    }


    public static class ValueEditor extends AbstractCellEditor
        implements TableCellEditor, ItemListener {

        private ValueRenderer vr = new ValueRenderer();

        public ValueEditor() {
            vr.addItemListener(this);
        }

        @Override
        public Object getCellEditorValue() {
            return vr.isSelected();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table,
            Object value, boolean isSelected, int row, int col) {

            if(value instanceof Value){
                Value v = (Value) value;
                vr.setSelected(v.present);
                vr.setText(v.text);
            }/*else{
                vr.setSelected(false);
                vr.setText("Leave");
                vr.setEnabled(false);
            }*/
            return vr;
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            this.fireEditingStopped();
        }
    }

}


