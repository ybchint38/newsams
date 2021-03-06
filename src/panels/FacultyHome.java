/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FacultyHome.java
 *
 * Created on Nov 11, 2013, 12:10:31 AM
 */

package panels;

import component.LecturePanel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import newattendancesystem.MainFrame;
import sql.FacultySql;

/**
 *
 * @author Hemant Borade
 */
public class FacultyHome extends javax.swing.JPanel implements PanelInterface{
    public int indexOfTab;  //using by Lecture Panel
    private ArrayList<LecturePanel> lectures;
    private int selectedIndex;
    
    /** Creates new form FacultyHome */
    public FacultyHome() {
        Date dt=new Date();
        Calendar cal=Calendar.getInstance();
        cal.setTime(dt);
        int day=cal.get(Calendar.DAY_OF_WEEK);
        if(day==1){
            JOptionPane.showMessageDialog(null, "Please visit within the working day.Its Sunday. Enjoy yourself.");
            //jTabbedPane1.setVisible(false);
            //jLabel1.setText("Sunday Holiday");
            jLabel1=new javax.swing.JLabel("Sunday Holiday");
            this.add(jLabel1);
            return;
        }
        initComponents();
        lectures=new ArrayList(0);
        setTabs();
    }

    /*private void setLecture(int d){
        FacultySql sql=new FacultySql(MainFrame.mainFrame.getUserID());
        ArrayList<String[]> temp=sql.getLecturesForDay(d);
        sql.close();
        for(String arr[]:temp){
            lectures.add(new LecturePanel(Integer.parseInt(arr[4]),arr[3],arr[0],Integer.parseInt(arr[1]),arr[2],this));
        }
    }*/

    private void setTabs(){
        Date dt=new Date();
        Calendar cal=Calendar.getInstance();
        cal.setTime(dt);
        int day=cal.get(Calendar.DAY_OF_WEEK);
        if(day!=1){
            int index=day-2;
            cal.add(Calendar.DATE, -index);
            FacultySql sql=new FacultySql(MainFrame.mainFrame.getUserID());
            ArrayList<String[]> temp;
            LecturePanel lec;
            JPanel panel;
            ArrayList<LecturePanel> tempLecPanel=new ArrayList<>();
            for(int i=Calendar.MONDAY;i<=Calendar.SATURDAY;i++){
                temp=sql.getLecturesForDay(i);
                //System.out.println(temp.size());
                panel=(JPanel)jTabbedPane1.getComponentAt(i-2);
                for(String arr[]:temp){
                    lec=new LecturePanel(Integer.parseInt(arr[4]),arr[3],arr[0],Integer.parseInt(arr[1]),arr[2],new java.sql.Date(cal.getTimeInMillis()),this);
                    lectures.add(lec);
                    tempLecPanel.add(lec);
                    //System.out.println(arr[0]+" "+arr[1]+" "+arr[2]+" "+arr[3]+" "+arr[4]+" ");
                }
                Collections.sort(tempLecPanel);
                int size=tempLecPanel.size();
                for(int j=0;j<size;j++){
                    panel.add(tempLecPanel.get(j));
                }
                tempLecPanel.clear();
            jTabbedPane1.setToolTipTextAt(i-2, cal.get(Calendar.DATE)+","+cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())+" "+cal.get(Calendar.YEAR));
            cal.add(Calendar.DATE, 1);
        }
        jTabbedPane1.setTitleAt(index, "Today's Lecture");
        jTabbedPane1.setSelectedIndex(index);
        sql.close();
        }else{
            JOptionPane.showMessageDialog(null, "Please visit within the working day.s");
        }
        //LecturePanel.ssql.close();
        //LecturePanel.asql.close();
        //LecturePanel.osql.close();
    }

    public void attendanceSubmitted(){
        LecturePanel lec=lectures.get(selectedIndex);
        lec.attendanceSubmitted();
    }

    public void setSelectedIndex(LecturePanel lec){
        selectedIndex=lectures.indexOf(lec);
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setText("Lectures");

        jTabbedPane1.setBackground(new java.awt.Color(204, 153, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));
        jTabbedPane1.addTab("Monday", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));
        jTabbedPane1.addTab("Tuesday", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.Y_AXIS));
        jTabbedPane1.addTab("Wednesday", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.Y_AXIS));
        jTabbedPane1.addTab("Thursday", jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.Y_AXIS));
        jTabbedPane1.addTab("Friday", jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.Y_AXIS));
        jTabbedPane1.addTab("Saturday", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(589, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addGap(12, 12, 12)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

    @Override
    public boolean destroy() {
        return true;
    }

}
