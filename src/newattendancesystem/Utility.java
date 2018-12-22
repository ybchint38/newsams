/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newattendancesystem;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;

/**
 *
 * @author yash
 */
public class Utility {
 
    //public static final int PRESESSION=0;
    //public static final int POSTSESSION=1;

    public static final String[] DESG=new String[]{"Head of Department","Course Coordinator","Professor","Asso Professor","Asst Professor"};
    public static final String DESG_HOD="Head of Department";
    public static final String DESG_CRS_COORD="Course Coordinator";
    public static final String DESG_ASSO_PROF="Asso Professor";
    public static final String DESG_ASST_PROF="Asst Professor";
    public static final String DESG_PROF="Professor";
    
    public static final int ADMIN=2;
    public static final int FACULTY=3;
    public static final int CLASSCOORD=5;
    public static final int TIMETABLECOORD=7;
    
    //public static final LineBorder errorBorder=new LineBorder(Color.RED);
    //public static LineBorder okBorder;
    
    public static String getRoleName(int role){
        switch(role){
            case ADMIN: 
                return "Admin";
            case FACULTY:
                return "Faculty";
            case CLASSCOORD:
                return "Class Coordinator";
            case TIMETABLECOORD:
                return "Time Table Coordinator";
            default:
                return "Unknown";
        }
    } 
    
    public static int getRoleID(String rolename){
        if(rolename.equalsIgnoreCase("Class Coordinator")){
            return CLASSCOORD;
        }else if(rolename.equalsIgnoreCase("Time Table Coordinator")){
            return TIMETABLECOORD;
        }else if(rolename.equalsIgnoreCase("Admin")){
            return ADMIN;
        }else{
            return FACULTY;
        } 
    }
    /*public static int getBatch(int sem){
        Date dt=new Date();
        Calendar cal=Calendar.getInstance();
        cal.setTime(dt);
        int cy=cal.get(Calendar.YEAR);
        int mnth=cal.get(Calendar.MONTH);
        if(mnth>=Calendar.JULY && mnth<=Calendar.DECEMBER){
            switch(sem){
                case 1:
                    return cy+3;
                case 2:
                case 3:
                    return cy+2;
                case 4:
                case 5:
                    return cy+1;
                case 6:
                    return cy;
                default:
                    throw new IllegalArgumentException("Invalid semester.");
            }
        }else{
            switch(sem){
                case 1:
                case 2:
                    return cy+2;
                case 3:
                case 4:
                    return cy+1;
                case 5:
                case 6:
                    return cy;
                default:
                    throw new IllegalArgumentException("Invalid semester.");
            }
        }
    }*/

    public static String toMyCase(String str){
        int len=str.length();
        StringBuilder mystr=new StringBuilder(len);
        char ch;
        boolean upper=true;
        for(int i=0;i<len;i++){
            ch=str.charAt(i);
            if(ch!=' '){
                if(Character.isLetter(ch)){
                    if(upper){
                        //mystr.append(Character.toUpperCase(ch));
                        ch=Character.toUpperCase(ch);
                    }else{
                        //mystr.append(Character.toLowerCase(ch));
                        ch=Character.toLowerCase(ch);
                    }
                }
                upper=false;
            }else{
                upper=true;
            }
            mystr.append(ch);
        }
        return mystr.toString();
    }
    
    public static boolean validateOnlyInteger(JTextField tf){
        String val=tf.getText();
        try{
            Integer.parseInt(val);
        }catch(NumberFormatException ex){
            tf.setForeground(Color.red);
            //System.out.println(ex);
            return false;
        }
        tf.setForeground(Color.BLACK);
        return true;
    }
    
    public static boolean validateOnlyLong(JTextField tf){
        String val=tf.getText();
        try{
            Long.parseLong(val);
        }catch(NumberFormatException ex){
            tf.setForeground(Color.red);
            //System.out.println(ex);
            return false;
        }
        tf.setForeground(Color.BLACK);
        return true;
    }
    
    public static boolean validateOnlyNumber(JTextField tf){
        String val=tf.getText();
        try{
            Double.parseDouble(val);
        }catch(NumberFormatException ex){
            tf.setForeground(Color.red);
            return false;
        }
        tf.setForeground(Color.BLACK);
        return true;
    }
    
    public static boolean exceptOnlyAlpha(int keyChar){
        if((keyChar>='A' && keyChar<='Z') || (keyChar>='a' && keyChar<='z')){
            return true;
        }
        return false;
    }
    
    public static boolean validateName(JTextField tf){
        String emailRegx=".*[0-9].*";
        String eid=tf.getText();
        if(eid.matches(emailRegx)){
            tf.setForeground(Color.RED);
            return false;
        }else{
            tf.setForeground(Color.BLACK);
            return true;
        }
        //return true;
    }
    
    public static boolean validateEmailID(JTextField tf){
        String emailRegx="[a-zA-Z0-9_.]+@[a-zA-Z]+.[a-zA-Z]+";
        String eid=tf.getText();
        if(eid.matches(emailRegx)){
            tf.setForeground(Color.BLACK);
            return true;
        }else{
            tf.setForeground(Color.RED);
            return false;
        }
        //return true;
    }
   
    public static void setNimbusLAF(){
        try {
            javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(MainFrame.mainFrame);
            MainFrame.mainFrame.pack();
        } catch (ClassNotFoundException ex) {
            //java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            //java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            //java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            //java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public static void setMetalLAF(){
        try {
            // Set cross-platform Java L&F (also called "Metal")
        UIManager.setLookAndFeel(
            UIManager.getCrossPlatformLookAndFeelClassName());
        SwingUtilities.updateComponentTreeUI(MainFrame.mainFrame);
            MainFrame.mainFrame.pack();
    } 
    catch (UnsupportedLookAndFeelException e) {
       // handle exception
    }
    catch (ClassNotFoundException e) {
       // handle exception
    }
    catch (InstantiationException e) {
       // handle exception
    }
    catch (IllegalAccessException e) {
       // handle exception
    }
    }
    
    public static void setDefault(){
        try {
            // Set System L&F
        UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.updateComponentTreeUI(MainFrame.mainFrame);
            MainFrame.mainFrame.pack();
    } 
    catch (UnsupportedLookAndFeelException e) {
       // handle exception
    }
    catch (ClassNotFoundException e) {
       // handle exception
    }
    catch (InstantiationException e) {
       // handle exception
    }
    catch (IllegalAccessException e) {
       // handle exception
    }

    

    }
    
    public static void setWindowsLAF(){
        try {
            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(MainFrame.mainFrame);
            MainFrame.mainFrame.pack();
        } catch (ClassNotFoundException ex) {
            //java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            //java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            //java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            //java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
    public static void setWindowsClassicLAF(){
        try {
            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
            SwingUtilities.updateComponentTreeUI(MainFrame.mainFrame);
            MainFrame.mainFrame.pack();
        } catch (ClassNotFoundException ex) {
            //java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            //java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            //java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            //java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
    public static void setMotifLAF(){
        try {
            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            SwingUtilities.updateComponentTreeUI(MainFrame.mainFrame);
            MainFrame.mainFrame.pack();
        } catch (ClassNotFoundException ex) {
            //java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            //java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            //java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            //java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String arg[]){
        //System.out.println(validateEmailID("1@udfhgdf.c"));
    }
}
