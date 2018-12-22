/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package component;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 *
 * @author dominator
 */
public class CreateOptionPanel {
    public static Font titleFont;

    public CreateOptionPanel(){
        titleFont=new Font(Font.SANS_SERIF,Font.BOLD,12);
    }
    
    public static JPanel getAdminPanel(ActionListener action){
        JPanel panel=new JPanel();
        TitledBorder tb=new TitledBorder("Admin");
        tb.setTitleColor(Color.WHITE);
        //tb.setBorder(new LineBorder(Color.WHITE));
        tb.setTitleFont(titleFont);
        panel.setBorder(tb);
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        OptionButton []leaf=new OptionButton[22];
        leaf[0]=new OptionButton("Import Database",11,action);
        leaf[1]=new OptionButton("Add New",12,action);
        leaf[2]=new OptionButton("View Details",13,action);
        leaf[3]=new OptionButton("Detailed",141,action);
        leaf[19]=new OptionButton("Compiled",142,action);
        leaf[4]=new OptionButton("Add New",21,action);
        leaf[5]=new OptionButton("View Details",22,action);
        leaf[6]=new OptionButton("Manage Faculty Role",23,action);
        leaf[17]=new OptionButton("Subject Allotment",24,action);
        leaf[7]=new OptionButton("View",32,action);
        leaf[8]=new OptionButton("Semester 1",411,action);
        leaf[9]=new OptionButton("Semester 2",412,action);
        leaf[10]=new OptionButton("Semester 3",413,action);
        leaf[11]=new OptionButton("Semester 4",414,action);
        leaf[12]=new OptionButton("Semester 5",415,action);
        leaf[13]=new OptionButton("Semester 6",416,action);
        //leaf[14]=new OptionButton("Student",51,action);
        //leaf[15]=new OptionButton("Faculty",52,action);
        //leaf[16]=new OptionButton("Attendance",53,action);
        leaf[18]=new OptionButton("Setting",6,action);
        leaf[20]=new OptionButton("Migration",7, action);
        leaf[21]=new OptionButton("Start New Session",8, action);
        
        OptionButton []arrAttndance=new OptionButton[2];
            arrAttndance[0]=leaf[3];
            arrAttndance[1]=leaf[19];
        
        OptionButton []arr1=new OptionButton[5];
            arr1[0]=leaf[0];
            arr1[1]=leaf[1];
            arr1[2]=leaf[2];
            arr1[3]=leaf[20];
            arr1[4]=new OptionButton("View Attendance", arrAttndance);
            
        OptionButton student=new OptionButton("Manage Student",arr1);

        OptionButton []arr2=new OptionButton[4];
            arr2[0]=leaf[4];
            arr2[1]=leaf[5];
            arr2[2]=leaf[6];
            arr2[3]=leaf[17];
        OptionButton faculty=new OptionButton("Manage Faculty",arr2);

        //OptionButton []arr3=new OptionButton[2];
        //    arr3[0]=leaf[6];
        //    arr3[1]=leaf[7];
        //OptionButton roles=new OptionButton("Manage Roles",arr3);

        OptionButton []arr4=new OptionButton[6];
            arr4[0]=leaf[8];
            arr4[1]=leaf[9];
            arr4[2]=leaf[10];
            arr4[3]=leaf[11];
            arr4[4]=leaf[12];
            arr4[5]=leaf[13];
        OptionButton mca=new OptionButton("MCA",arr4);
        OptionButton subject=new OptionButton("Subject",new OptionButton[]{mca});

       // OptionButton []arr5=new OptionButton[3];
       //     arr5[0]=leaf[14];
       //     arr5[1]=leaf[15];
       //     arr5[2]=leaf[16];
        OptionButton report=new OptionButton("Report",5,action);

        panel.add(student);
        panel.add(faculty);
        panel.add(subject);
        panel.add(report);
        panel.add(leaf[18]);
        panel.add(leaf[21]);

        return panel;
    }

    public static JPanel getClassCoordinatorPanel(String values[][],ActionListener listener){
        JPanel panel=new JPanel();
        TitledBorder tb=new TitledBorder("Class Coordinator");
        tb.setTitleColor(Color.WHITE);
        tb.setTitleFont(titleFont);
        panel.setBorder(tb);
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        OptionButton semester[]=new OptionButton[values.length];
        OptionButton leaf[]=new OptionButton[5];
        OptionButton section[];
        int hcode;
        for(int i=0;i<values.length;i++){
            section=new OptionButton[values[i].length-1];
            for(int j=1;j<values[i].length;j++){
                int temp=1;
                hcode=(i+1)*100+(j)*10;
                leaf[0]=new OptionButton("View Student List",hcode+temp,listener);
                //System.out.println(hcode);
                leaf[1]=new OptionButton("View Attendance",hcode+(++temp),listener);
                //System.out.println(hcode);
                leaf[2]=new OptionButton("Leave Details",hcode+(++temp),listener);
                //System.out.println(hcode);
                hcode=(i+1)*1000+(j)*100+(++temp)*10+1;
                leaf[3]=new OptionButton("Student List",hcode,listener);
                
                hcode=(i+1)*1000+(j)*100+(temp)*10+2;
                leaf[4]=new OptionButton("Student Details",hcode,listener);
                
                //System.out.println(hcode);

                OptionButton report=new OptionButton("Report",new OptionButton[]{leaf[3],leaf[4]});
                
                section[j-1]=new OptionButton("Section "+values[i][j],new OptionButton[]{leaf[0],leaf[1],leaf[2],report});
            }
            semester[i]=new OptionButton("Semester "+values[i][0],section);
            panel.add(semester[i]);
        }
        OptionButton cmplAttnd=new OptionButton("Compiled Attendance", 5, listener);
        panel.add(cmplAttnd);
        return panel;
    }

    public static JPanel getFacultyPanel(String values[][],ActionListener action){
        JPanel panel=new JPanel();
        TitledBorder tb=new TitledBorder("Faculty Function");
        tb.setTitleColor(Color.WHITE);
        tb.setTitleFont(titleFont);
        panel.setBorder(tb);
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        //panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        /*OptionButton semester[]=new OptionButton[values.length];   //Different Semesters
        int hcode=0;
        for(int i=0;i<semester.length;i++){
            OptionButton section[]=new OptionButton[values[i].length-1];    //Different Sections in semester
            for(int j=0;j<section.length;j++){
                hcode=(i+1)*100+(j+1)*10;
                hcode+=1;
                OptionButton insert=new OptionButton("Insert Attendance",hcode,action);
                hcode+=1;
                OptionButton view=new OptionButton("View Attendance",hcode,action);
                section[j]=new OptionButton(values[i][j+1],new OptionButton[]{insert,view});
            }
            semester[i]=new OptionButton(values[i][0],section);
            panel.add(semester[i]);
        }*/
        panel.add(new OptionButton("View Attendance",1,action));
        panel.add(new OptionButton("View Schedule",2,action));
        panel.add(new OptionButton("Attendance Sheet",3,action));
        return panel;
    }

    public static JPanel getTimeTableCoordinatorPanel(String values[][],ActionListener action){
        JPanel panel=new JPanel();
        TitledBorder tb=new TitledBorder("Time-Table Coordinator");
        tb.setTitleColor(Color.WHITE);
        tb.setTitleFont(titleFont);
        panel.setBorder(tb);
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        OptionButton semester[]=new OptionButton[values.length];   //Different Semesters
        int hcode=0;
        for(int i=0;i<semester.length;i++){
            OptionButton section[]=new OptionButton[values[i].length-1];    //Different Sections in semester
            hcode=100+(i+1)*10+1;
            for(int j=0;j<section.length;j++){
                section[j]=new OptionButton(values[i][j+1],hcode,action);
                hcode++;
            }
            semester[i]=new OptionButton(values[i][0],section);
        }
        panel.add(new OptionButton("Schedule",semester));
        return panel;
    }

    public static JPanel getProfileButton(ActionListener action){
        JPanel panel=new JPanel();
        TitledBorder tb=new TitledBorder(" ");
        tb.setTitleColor(Color.WHITE);
        tb.setTitleFont(titleFont);
        //tb.setBorder(new LineBorder(Color.yellow));
        panel.setBorder(tb);
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        OptionButton btn=new OptionButton("My Profile",1,action);
        panel.add(btn);
        return (panel);
    }
}
