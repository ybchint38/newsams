/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FacultySchedule.java
 *
 * Created on Nov 12, 2013, 12:29:18 AM
 */

package component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import newattendancesystem.MainFrame;
import panels.PanelInterface;
import scheduleclass.ScheduleBase;
import sql.FacultySql;

/**
 *
 * @author Hemant Borade
 */
public class FacultySchedule extends javax.swing.JPanel implements Printable,PanelInterface{
    public int indexOfTab;

    /** Creates new form FacultySchedule */
    public FacultySchedule(String fid) {
        initComponents();
        DefaultTableModel tm=(DefaultTableModel)jTable1.getModel();

        /*String column[]=new String[8];
        column[0]="Period No";
        column[1]="Time";
        column[2]="Monday";
        column[3]="Tuesday";
        column[4]="Wednesday";
        column[5]="Thursday";
        column[6]="Friday";
        column[7]="Saturday";*/
        String data[][]=new String[7][8];
        data[0][0]="1";
        data[1][0]="2";
        data[2][0]="3";
        data[3][0]="4";
        data[4][0]="5";
        data[5][0]="6";
        data[0][1]="09:15 - 10:15";
        data[1][1]="10:15 - 11:15";
        data[2][1]="11:15 - 12:15";
        data[3][1]="01:00 - 02:00";
        data[4][1]="02:00 - 03:00";
        data[5][1]="03:00 - 04:00";
        data[6][1]="04:00 - 05:00";
        ArrayList<String[]> lectures;
        FacultySql sql=new FacultySql(fid);
        for(int i=2;i<=7;i++){
            lectures=sql.getLecturesForDay(i);
            int pno;
            for(int j=0;j<6;j++){
                data[j][i]="N.A.";
            }
            for(String []temp:lectures){
                pno=Integer.parseInt(temp[4])-1;
                data[pno][i]=temp[3]+" "+temp[1]+"("+temp[2]+")";
            }
        }
        for(int i=0;i<6;i++){
            tm.addRow(data[i]);
        }
        sql.close();
    }
    
    public FacultySchedule(HashMap<Integer,String> schedule){
        initComponents();
        String row[];
        String time[]=new String[]{"09:15 - 10:15","10:15 - 11:15","11:15 - 12:15","01:00 - 02:00","02:00 - 03:00","03:00 - 04:00","04:00 - 05:00"};
        String pno[]=new String[]{"1","2","3","4","5","6","7"};
        DefaultTableModel tm=(DefaultTableModel)jTable1.getModel();
        for(int i=ScheduleBase.M_LEC1;i<=ScheduleBase.M_LEC7;i++){
            row=new String[8];
            row[0]=pno[i-1];
            row[1]=time[i-1];
            row[2]=schedule.get(i);
            row[3]=schedule.get(i+7);
            row[4]=schedule.get(i+14);
            row[5]=schedule.get(i+21);
            row[6]=schedule.get(i+28);
            row[7]=schedule.get(i+35);
            tm.addRow(row);
        }
        jButton1.setVisible(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Weekly Schedule");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Period No", "Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setFillsViewportHeight(true);
        jTable1.setOpaque(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getColumn(0).setMinWidth(70);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(70);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(70);
        jTable1.getColumnModel().getColumn(1).setMinWidth(85);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(85);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(85);
        jTable1.getColumnModel().getColumn(2).setMinWidth(85);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(85);
        jTable1.getColumnModel().getColumn(2).setMaxWidth(85);
        jTable1.getColumnModel().getColumn(3).setMinWidth(85);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(85);
        jTable1.getColumnModel().getColumn(3).setMaxWidth(85);
        jTable1.getColumnModel().getColumn(4).setMinWidth(85);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(85);
        jTable1.getColumnModel().getColumn(4).setMaxWidth(85);
        jTable1.getColumnModel().getColumn(5).setMinWidth(85);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(85);
        jTable1.getColumnModel().getColumn(5).setMaxWidth(85);
        jTable1.getColumnModel().getColumn(6).setMinWidth(85);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(85);
        jTable1.getColumnModel().getColumn(6).setMaxWidth(85);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buttonimage/prints.png"))); // NOI18N
        jButton1.setText("Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(607, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        /*PrinterJob job=PrinterJob.getPrinterJob();
        job.setPrintable(this);
        Color old=this.getBackground();
        this.setBackground(Color.WHITE);
        jButton1.setVisible(false);
        try{
            job.print();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Printer not found.");
        }
        this.setBackground(old);
        jButton1.setVisible(true);*/
        
        try {
            // TODO add your handling code here:
            jTable1.print();
        } catch (PrinterException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(MainFrame.mainFrame, "Unable to connect to the printer.\nPlease check printer connection and try again.");
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
                int x=0;
        if (page > 0) {
         return NO_SUCH_PAGE;
    }

    // User (0,0) is typically outside the
    // imageable area, so we must translate
    // by the X and Y values in the PageFormat
    // to avoid clipping.
    Graphics2D g2d = (Graphics2D)g;
    g2d.translate(pf.getImageableX(), pf.getImageableY()+50);

    // Now we perform our rendering
    //g.drawString("Hello world!", 100, 100);
    this.printAll(g);
    //this.printAll(g);
    // tell the caller that this page is part
    // of the printed document
    return PAGE_EXISTS;
    }

    public boolean destroy() {
        return true;
    }

}
