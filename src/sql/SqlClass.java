/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sql;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author apex predator
 */
public class SqlClass {
    private static String host="localhost";
    private static String uname="root";
    private static String pwd="root";
    private static String port="3306";
    private static String database="sams";
    
    private static PrintWriter printWriter;
    protected static Connection con;
    protected JFrame frm;
    protected static int conCnt=0;
    
    public SqlClass(JFrame frame){
        try{
            if(con==null || con.isClosed()){
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database, uname, pwd);
            frm=frame;
            }
            conCnt++;
        }catch(Exception e){
            PrintWriter pw=getLogWriter();
            pw.println(new Date());
            e.printStackTrace(pw);
        }
    }

    public SqlClass(){
        try{
            if(con==null || con.isClosed()){
                Class.forName("com.mysql.jdbc.Driver");
                con=DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database, uname, pwd);
                frm=null;
            }
            conCnt++;
        }catch(Exception e){
            PrintWriter pw=getLogWriter();             pw.println(new Date());             e.printStackTrace(pw);
        }
    }


    public void close(){
        try{
            //if(con!=null)
            conCnt--;
            if(conCnt==0){
                closeAll();
            }
            //if(ps!=null)
            //    ps.close();
            //if(st!=null)
            //    st.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.toString());
            PrintWriter pw=getLogWriter();             pw.println(new Date());             e.printStackTrace(pw);

        }
    }

    public static void closeAll(){
        try{
            conCnt=0;
            con.close();
            if(printWriter!=null){
                printWriter.flush();
                printWriter.close();
                FileWriter fw=new FileWriter("Log.log", true);
                FileReader fr=new FileReader("temp.txt");
                int n;
                while((n=fr.read())!=-1){
                    fw.write(n);
                }
                fw.close();
                fr.close();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.toString());
            PrintWriter pw=getLogWriter();             
            pw.println(new Date());             
            e.printStackTrace(pw);
        }
    }
    
    public void setAutoCommit(boolean bool){
        try{
        con.setAutoCommit(bool);
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm,e.toString());
            PrintWriter pw=getLogWriter();             pw.println(new Date());             e.printStackTrace(pw);
        }
    }

    public void rollback(){
        try{
        con.rollback();
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm,e.toString());
            PrintWriter pw=getLogWriter();             pw.println(new Date());             e.printStackTrace(pw);
        }
    }

    public void commit(){
        try{
            if(!con.getAutoCommit()){
                con.commit();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm,e.toString());
            PrintWriter pw=getLogWriter();             pw.println(new Date());             e.printStackTrace(pw);
        }
    }

    public Connection getConnection(){
        return con;
    }

    public int insertClass(String cls,int sem,String sec,int bno){
        int id=-1;
        try(PreparedStatement ps=con.prepareStatement("insert into class values(?,?,?,?,?)")){
            id=getClassID(cls,sem,sec,bno);
            if(id==-1){
                id=getNextClassID();
                ps.setInt(1, id);
                ps.setString(2, cls);
                ps.setInt(3, sem);
                ps.setString(4, sec);
                ps.setInt(5, bno);
                ps.executeUpdate();
            }
        }catch(Exception e){
            PrintWriter pw=getLogWriter();             pw.println(new Date());             e.printStackTrace(pw);
            JOptionPane.showMessageDialog(frm, e.toString());
            return -1;
        }
        return id;
    }

    public int getClassID(String cls,int sem,String sec,int bno){
        int id=-1;
        try(Statement s=con.createStatement()){
            String qry="select classid from class where course='"+cls+"' and semester="+sem+" and section='"+sec+"' and batchno="+bno;
            ResultSet rs=s.executeQuery(qry);
            if(rs.next()){
                return rs.getInt("classid");
            }
        }catch(Exception e){
            PrintWriter pw=getLogWriter();             pw.println(new Date());             e.printStackTrace(pw);
            JOptionPane.showMessageDialog(frm, e.toString());
            return -1;
        }
        return id;
    }
    
    private int getNextClassID() throws SQLException{
        int id=1;
        Statement s=con.createStatement();
        String qry="select max(classid) from class";
        ResultSet rs=s.executeQuery(qry);
        if(rs.next()){
            return rs.getInt(1)+1;
        }
        return id;
    }
    
    public int getBatch(String cls,int sem){
        int bno=-1;
        try(Statement s=con.createStatement()){
            String qry="select max(batchno) from class where course='"+cls+"' and semester="+sem;
            ResultSet rs=s.executeQuery(qry);
            if(rs.next()){
                return rs.getInt(1);
            }
        }catch(Exception e){
            PrintWriter pw=getLogWriter();             pw.println(new Date());             e.printStackTrace(pw);
            JOptionPane.showMessageDialog(frm, e.toString());
            return -1;
        }
        return bno;
    }
    
    protected static PrintWriter getLogWriter(){
        if(printWriter==null){
            try{
                printWriter=new PrintWriter("temp.txt");
            }catch(FileNotFoundException e){
                
            }
        }
        return printWriter;
    }
}
