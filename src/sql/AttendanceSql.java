/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import newattendancesystem.MainFrame;

/**
 *
 * @author apex predator
 */
public class AttendanceSql extends SqlClass{
    //private Date date;
    //private String clss;
    //private int sem;
    //private String section;
    //private int batch;
    //private int period;
    //private String subject;
    public static int PRESENT=0;
    public static int ABSENT=-1;

    private int ID;
            
    public AttendanceSql(){
        super();
    }

    public AttendanceSql(JFrame frm){
        super(frm);
    }
    
    public boolean insertAttendance(String sid,int sno,int status){
        try{
            PreparedStatement ps=con.prepareStatement("insert into attendancedeatil (id,sid,status,srno) values(?,?,?,?)");
            ps.setInt(1, ID);
            ps.setString(2,sid);
            ps.setInt(3,status);
            ps.setInt(4, sno);
            
            if(ps.executeUpdate()==1){
                return true;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean insertAttendance(String sid[],int sno[],int status[]){
        try{
            int n=sid.length;
            if(n!=sno.length || n!=status.length){
                JOptionPane.showMessageDialog(frm,"Number of entries do not match.");
                return false;
            }
            //this.setAutoCommit(false);
            PreparedStatement ps=con.prepareStatement("insert into attendancedetail (id,sid,status,srno) values(?,?,?,?)");
            for(int i=0;i<n;i++){
                ps.setInt(1, ID);
                ps.setString(2,sid[i]);
                ps.setInt(3,status[i]);
                ps.setInt(4, sno[i]);
                
                ps.addBatch();
            }
            for(int sts:ps.executeBatch()){
                if(sts==PreparedStatement.EXECUTE_FAILED){
                    throw new Exception("Error occured when updating.");
                }
            }
            //if(ps.executeBatch().length==n){
                //this.commit();
                //this.setAutoCommit(true);
                return true;
            //}
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
            //this.rollback();
            //this.setAutoCommit(true);
            return false;
        }
        //this.rollback();
        //this.setAutoCommit(true);
        //return false;
    }

    public int setDetailID(Date dt,String cls,int semester,String sec,String scode,int bno,int pno){
        int id=0;
        try{
            Statement s=con.createStatement();
            java.util.Date today=new java.util.Date();
            java.util.Date attendance=new java.util.Date(dt.getTime());
            if(today.compareTo(attendance)<0){
                JOptionPane.showMessageDialog(MainFrame.mainFrame ,"You can not insert attendance of future date.");
                return -1;
            }
            String qry="select max(ID) from attendancemaster";
            ResultSet rs=s.executeQuery(qry);
            if(rs.next()){
               id=rs.getInt(1);
               id++;
            }else{
                id=1;
            }
            int classid=insertClass(cls, semester, sec, bno);
            PreparedStatement ps=con.prepareStatement("insert into attendancemaster (id,adate,periodno,scode,classid) values(?,?,?,?,?)");
            ps.setInt(1,id);
            ps.setDate(2,dt);
            ps.setInt(3,pno);
            ps.setString(4,scode);
            ps.setInt(5,classid);
            ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
            id=-1;
        }
        ID=id;
        return ID;
   }
    
    public int getDetailID(){
        return ID;
    }

    public int findDetailID(Date dt,String cls,int semester,String sec,String sub,int bno,int pno){
        ID=-1;
        try{
            PreparedStatement s=con.prepareStatement("select ID from attendancemaster where adate=? and scode=? and periodno=? and classid=(select classid from class where course=? and semester=? and section=? and batchno=?)");
            s.setDate(1, dt);
            s.setString(2, sub);
            s.setInt(3, pno);
            s.setString(4, cls);
            s.setInt(5, semester);
            s.setString(6, sec);
            s.setInt(7, bno);
            ResultSet rs=s.executeQuery();
            if(rs.next()){
                ID=rs.getInt(1);
            }
            s.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
            ID=-1;
        }
        return ID;
    }

    public boolean updateAttendance(String sid[],int status[]){
        if(ID!=-1){
            try{
            int n=sid.length;
            if(n!=status.length){
                JOptionPane.showMessageDialog(frm,"Number of entries do not match.");
                return false;
            }

            //this.setAutoCommit(false);
            PreparedStatement ps=con.prepareStatement("update attendancedetail set status=? where id=? and sid=?");
            for(int i=0;i<n;i++){
                ps.setInt(1, status[i]);
                ps.setInt(2,ID);
                ps.setString(3, sid[i]);
                ps.addBatch();
            }
            for(int sts:ps.executeBatch()){
                if(sts==PreparedStatement.EXECUTE_FAILED){
                    throw new Exception("Error occured when updating.");
                }
            }
            //if(ps.executeBatch().length==n){
                //this.commit();
                //this.setAutoCommit(true);
                return true;
            //}
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
            //this.rollback();
            //this.setAutoCommit(true);
            return false;
        }
        }
        //this.rollback();
        //this.setAutoCommit(true);
        return false;
    }

    public Map<Integer,Integer> getAttendance(){
            Map<Integer,Integer> val=new HashMap();
            try{
                Statement s=con.createStatement();
                ResultSet rs=s.executeQuery("select srno,status from attendancedetail where id="+ID);
                while(rs.next()){
                    val.put(rs.getInt(1), rs.getInt(2));
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(frm, e.toString());
                e.printStackTrace();
            }
            return val;
        }

    public java.util.Date getFirstLecture(String cls,int sem,String sec,String subject,int bno){
        Date dt=null;
        try{
            //System.out.println(cls+"  "+sem+"  "+sec+"  "+subject+"  "+bno);
            PreparedStatement ps=con.prepareStatement("select min(adate) from attendancemaster where scode=? and classid=(select classid from class where course=? and semester=? and section=? and batchno=?)");
            ps.setString(1, subject);
            ps.setString(2, cls);
            ps.setInt(3, sem);
            ps.setString(4, sec);
            ps.setInt(5, bno);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                dt=rs.getDate(1);
                //System.out.println(dt);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        if(dt==null){
            return null;
        }
        return new java.util.Date(dt.getTime());
    }

    /*public java.util.Date getFirstLecture(String cls,int sem,String sec,String subject,int bno){
        Date dt=null;
        try{
            PreparedStatement ps=con.prepareStatement("select min(adate) from attendancemaster where class=? and semester=? and section=? and scode=? and batchno=?");
            ps.setString(1, cls);
            ps.setInt(2, sem);
            ps.setString(3, sec);
            ps.setString(4, subject);
            ps.setInt(5, bno);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                dt=rs.getDate(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        if(dt==null){
            return null;
        }
        return new java.util.Date(dt.getTime());
    }*/

    public java.util.Date getLastLecture(String cls,int sem,String sec,String subject,int bno){
        Date dt=null;
        try{
            PreparedStatement ps=con.prepareStatement("select max(adate) from attendancemaster where scode=? and classid=(select classid from class where course=? and semester=? and section=? and batchno=?)");
            ps.setString(1, subject);
            ps.setString(2, cls);
            ps.setInt(3, sem);
            ps.setString(4, sec);
            ps.setInt(5, bno);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                dt=rs.getDate(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        if(dt==null){
            return null;
        }
        return new java.util.Date(dt.getTime());
    }

    /*public java.util.Date getLastLecture(String cls,int sem,String subject,int bno){
        Date dt=null;
        try{
            PreparedStatement ps=con.prepareStatement("select max(adate) from attendancemaster where scode=? and classid in (select classid from class where course=? and semester=? and batchno=?)");
            ps.setString(1, subject);
            ps.setString(2, cls);
            ps.setInt(3, sem);
            ps.setInt(4, bno);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                dt=rs.getDate(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        if(dt==null){
            return null;
        }
        return new java.util.Date(dt.getTime());
    }*/

    /*public int getTotalLectures(String cls,int sem,String subject,int bno){
        int count=0;
        try{
            PreparedStatement ps=con.prepareStatement("select count(ID) from attendancemaster where scode=? and classid=(select classid from class where course=? and semester=? and batchno=?)");
            ps.setString(1, subject);
            ps.setString(2, cls);
            ps.setInt(3, sem);
            ps.setInt(4, bno);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                count=rs.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return count;
    }*/
    
    public ArrayList<Long[]> getAttendanceDates(String cls,int sem,String sec,String subject,int bno,Date from,Date to){
        ArrayList<Long[]> val=new ArrayList(0);//{date,aid}
        try{
            PreparedStatement ps=con.prepareStatement("select adate,id from attendancemaster where scode=? and adate>=? and adate<=? and classid=(select classid from class where course=? and semester=? and section=? and batchno=?) order by adate");
            ps.setString(1, subject);
            ps.setDate(2, from);
            ps.setDate(3, to);
            ps.setString(4, cls);
            ps.setInt(5, sem);
            ps.setString(6, sec);
            ps.setInt(7, bno);
            
            ResultSet rs=ps.executeQuery();
            //Calendar cal=Calendar.getInstance();
            while(rs.next()){
                //cal.setTimeInMillis(rs.getDate(1).getTime());
                val.add(new Long[]{rs.getDate(1).getTime(),(long)rs.getInt(2)});
                //System.out.println("Found");
            }
        }catch(Exception e){
                JOptionPane.showMessageDialog(frm, e.toString());
                e.printStackTrace();
        }
        return val;
    }

    public int getTotalLectures(String cls,int sem,String sec,String subject,int bno,Date from,Date to){
        int val=0;
        try{
            PreparedStatement ps;
            if(from==null || to==null){
                ps=con.prepareStatement("select count(id) from attendancemaster where scode=? and classid=(select classid from class where course=? and semester=? and section=? and batchno=?)");
                ps.setString(1, subject);
                ps.setString(2, cls);
                ps.setInt(3, sem);
                ps.setString(4, sec);
                ps.setInt(5, bno);
            }else{
                ps=con.prepareStatement("select count(id) from attendancemaster where scode=? and adate>=? and adate<=? and classid=(select classid from class where course=? and semester=? and section=? and batchno=?)");
                ps.setString(1, subject);
                ps.setDate(2, from);
                ps.setDate(3, to);
                ps.setString(4, cls);
                ps.setInt(5, sem);
                ps.setString(6, sec);
                ps.setInt(7, bno);
            }
            ResultSet rs=ps.executeQuery();
            //Calendar cal=Calendar.getInstance();
            if(rs.next()){
                val=rs.getInt(1);
            }
        }catch(Exception e){
                JOptionPane.showMessageDialog(frm, e.toString());
                e.printStackTrace();
        }
        return val;
    }

    public int getTotalLectures(String cls,int sem,String sec,String subject,int bno){
        return getTotalLectures(cls, sem, sec, subject, bno);
    }
    
    /*public ResultSet getStudentAttendance(String cls,int sem,String sec,String subject,int bno,Date from,Date to){
        String qry="select * from attendancedetail where id in (select id from attendancemaster where class=? and semester=? and section=? and scode=? and batchno=? and adate>=? and adate<=?)";
        try{
            PreparedStatement ps=con.prepareStatement(qry);
            ps.setString(1, cls);
            ps.setInt(2, sem);
            ps.setString(3, sec);
            ps.setString(4, subject);
            ps.setInt(5, bno);
            ps.setDate(6, from);
            ps.setDate(7, to);
            return ps.executeQuery(qry);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }*/

}
