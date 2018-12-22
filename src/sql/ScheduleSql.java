/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import scheduleclass.ScheduleBase;

/**
 *
 * @author yash
 */
public class ScheduleSql extends SqlClass{
    
    public static int COURSE=1;
    public static int SEMESTER=2;
    public static int SECTION=3;
    public static int PERIODNO=4;
    public static int MON=5;
    public static int TUE=6;
    public static int WED=7;
    public static int THUS=8;
    public static int FRI=9;
    public static int SAT=10;
    
    public ScheduleSql() {
    }

    public ScheduleSql(JFrame frame) {
        super(frame);
    }
    
    public boolean insertSchedule(int sem,String section,Map<Integer,String> schedule){
        try (PreparedStatement ps = con.prepareStatement("insert into schedule values('MCA',"+sem+",'"+section+"',?,?,?,?,?,?,?)")) {
                int indx;
                for(int i=1;i<=ScheduleBase.DAILY_LEC;i++){
                    indx=i;
                    ps.setInt(1, indx);
                    for(int j=2;j<=7;j++){
                        ps.setString(j, schedule.get(indx));
                        indx=indx+7;
                    }
                    ps.addBatch();
                }
                ps.executeBatch();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(frm, ex.toString());
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public HashMap<Integer,String> getSchedule(int sem,String sec){
        HashMap<Integer,String> val=new HashMap();
        try{
            Statement s=con.createStatement();
            String qry="select * from schedule where semester="+sem+" and section='"+sec+"'";
            ResultSet rs=s.executeQuery(qry);
            if(!rs.next()){
                //int total=ScheduleBase.DAILY_LEC*6;
                for(int i=1;i<=ScheduleBase.S_LEC7;i++){
                    val.put(i, null);
                }
                return val;
            }
            int indx;
            do{
                indx=rs.getInt(PERIODNO);
                for(int i=MON;i<=SAT;i++){
                    val.put(indx, rs.getString(i));
                    indx=indx+7;
                }
            }while(rs.next());
        }catch(Exception ex){
            JOptionPane.showMessageDialog(frm, ex.toString());
            ex.printStackTrace();
        }
        return val;
    }

    public HashMap<Integer,String[]> getScheduleForReport(int sem,String section){
        HashMap<Integer,String[]> value=new HashMap<>();
        try(Statement s=con.createStatement()){
            String qry="select * from schedule where course='MCA' and semester="+ sem +" and section='"+section+"'";
            ResultSet rs=s.executeQuery(qry);
            String temp[];
            //int pno;
            while(rs.next()){
                temp=new String[7];
                //pno=rs.getInt(PERIODNO);
                temp[1]=rs.getString(MON);
                temp[2]=rs.getString(TUE);
                temp[3]=rs.getString(WED);
                temp[4]=rs.getString(THUS);
                temp[5]=rs.getString(FRI);
                temp[6]=rs.getString(SAT);
                value.put(rs.getInt(PERIODNO), temp);
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(frm, ex.toString());
            ex.printStackTrace();
        }
        return value;
    }
    
    public HashMap<Integer,String> getLabSchedule(String lab){
        HashMap<Integer,String> val=new HashMap();
        try{
            ArrayList<String[]> oneDaySch;
            for(int i=java.util.Calendar.MONDAY;i<=java.util.Calendar.SATURDAY;i++){
                oneDaySch=getLabScheduleForDay(lab, i);
                int n=oneDaySch.size();
                int pno;
                for(int j=0;j<n;j++){
                    pno=Integer.parseInt(oneDaySch.get(j)[3]);
                    val.put((i-java.util.Calendar.MONDAY)*7+pno, oneDaySch.get(j)[4]);
                }
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(frm, ex.toString());
            ex.printStackTrace();
        }
        return val;
    }

    public ArrayList<String[]> getLabScheduleForDay(String lab,int dy){
        ArrayList<String[]> val=new ArrayList(0);
        Statement s=null,s1=null;
        try{
            String day;
            switch(dy){
                case java.util.Calendar.MONDAY:
                    day="mon";
                    break;
                case java.util.Calendar.TUESDAY:
                    day="tue";
                    break;
                case java.util.Calendar.WEDNESDAY:
                    day="wed";
                    break;
                case java.util.Calendar.THURSDAY:
                    day="thus";
                    break;
                case java.util.Calendar.FRIDAY:
                    day="fri";
                    break;
                case java.util.Calendar.SATURDAY:
                    day="sat";
                    break;
                default:
                    throw new IllegalArgumentException("Invalid day");
            }
            s=con.createStatement();
            String qry;
            ResultSet rs;
            qry="select scode,semester,section from computerlab c inner join laballotment a on c.labid=a.labid where labname='"+lab+"'";
            //qry="select course,semester,section,periodno,"+day+" from schedule where "+day+" in (select scode from computerlab c inner join laballotment a on c.labid=a.labid where labname='"+lab+"')";
            rs=s.executeQuery(qry);
            String temp[];
            s1=con.createStatement();
            ResultSet rs1;
            while(rs.next()){
                qry="select course,semester,section,periodno,"+day+" from schedule where "+day+"='"+rs.getString("scode")+"' and semester="+rs.getInt("semester")+" and section='"+rs.getString("section")+"'";
                rs1=s1.executeQuery(qry);
                while(rs1.next()){
                    temp=new String[5];
                    temp[0]=rs1.getString("course");
                    temp[1]=rs1.getInt("semester")+"";
                    temp[2]=rs1.getString("section");
                    temp[3]=rs1.getInt("periodno")+"";
                    temp[4]=rs1.getString(day);
                    val.add(temp);
                }
            }
            s.close();
            s1.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
            try {
                s.close();
                s1.close();
            } catch (SQLException ex) {
                Logger.getLogger(ScheduleSql.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return val;
    }
    
    public boolean isScheduleExists(int sem,String sec){
        try{
            Statement st=con.createStatement();
            String qry="select count(periodno) from schedule where semester="+sem+" and section='"+sec+"'";
            ResultSet rs=st.executeQuery(qry);
            if(rs.next()){
                int n=rs.getInt(1);
                if(n==ScheduleBase.DAILY_LEC){
                    return true;
                }else{
                    deleteSchedule(sem, sec);
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return false;
    }
    
    public void deleteAllSchedule(){
        try(Statement s=con.createStatement()){
            s.executeUpdate("delete from schedule");
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
    }
    
    public void deleteSchedule(int sem){
        try(Statement s=con.createStatement()){
            s.executeUpdate("delete from schedule where semester="+sem);
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
    }
    
    private int deleteSchedule(int sem,String section){
        int n=-1;
        try(Statement s=con.createStatement()){
            String qry="delete from schedule where semester="+sem+" and section='"+section+"'";
            n=s.executeUpdate(qry);
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return n;
    }
    
    public boolean updateSchedule(int semester,String section,Map<Integer,String> schedule){
        deleteSchedule(semester, section);
        return insertSchedule(semester, section, schedule);
    }
    
    

}
/*
///////////////// sm metheods
    public ResultSet getSubject(String cls,int sem) throws SQLException
    {
       ResultSet rs=null;
       try{
       Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
       rs=st.executeQuery("select scode,sname from subject where class='"+cls+"' and semester="+sem);
       }catch(Exception e){
       JOptionPane.showMessageDialog(frm,e.toString());
       }
       return rs;
    }

    public ResultSet getForget(String id) throws SQLException
    {
       ResultSet rs=null;
       try{
       Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
       rs=st.executeQuery("select id,pwd,question,answer from at_login where id where id='"+id+"' ");
       }catch(Exception e){
       JOptionPane.showMessageDialog(frm,e.toString());
       }
       return rs;
    }

    public ResultSet getFaculty(String cls,int sem,char sec1,String scode) throws SQLException
    {
       ResultSet rs=null;
       try{
       Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
       String sec=sec1+"";
       rs=st.executeQuery("select fname from faculty where fid in (select fid from suballotment where class='"+cls+"' and semester="+sem+" and section='"+sec+"' and scode='"+scode+"')");
       }catch(Exception e){
       JOptionPane.showMessageDialog(frm,e.toString());
       }
       return rs;
    }

///////////////
  public ResultSet getSchedule(String cls,int sem,char sec){
      ResultSet rs=null;
      String section=sec+"";
      String qry= "select mon,tue,wed,thus,fri,sat,periodno from schedule where class='"+cls+"' and semester="+sem+" and section='"+section+"'";
      try{
      //String sub[]=new String[6];
      //for(int i=0;i<6;i++)
      //sub[i]=period.get(i).toString();
      Statement st=con.createStatement();
      rs=st.executeQuery(qry);
      }catch(Exception e){
      JOptionPane.showMessageDialog(frm, e.toString());
      }
    return rs;
  }

////////////////
    public int setSchedule(String cls,int sem,char sec,int period_no,Vector period)
    {
      int x=0;
      try{
       String sub[]=new String[6];
       for(int i=0;i<6;i++)
      sub[i]=period.get(i).toString();
      PreparedStatement ps=con.prepareStatement("insert into schedule values (?,?,?,?,?,?,?,?,?,?)");
      ps.setString(1,cls);
      ps.setInt(2,sem);
      ps.setString(3,sec+"");
      ps.setInt(4,period_no);
      ps.setString(5,sub[0]);
      ps.setString(6,sub[1]);
      ps.setString(7,sub[2]);
      ps.setString(8,sub[3]);
      ps.setString(9,sub[4]);
      ps.setString(10,sub[5]);

      x=ps.executeUpdate();
      }catch(Exception e){
      JOptionPane.showMessageDialog(frm, e.toString());
      }
    return x;
    }

///////////////
    public int updateSchedule(String cls,int sem,char sec,Vector period)
    {
      int x=0;
      try{
       String sub[]=new String[6];
       for(int i=0;i<6;i++)
      sub[i]=period.get(i).toString();
      PreparedStatement ps=con.prepareStatement("update schedule set mon=?, tue=?,wed=?,thus=?,fri=?, sat=? where class='"+cls+"' and semester="+sem+" and section='"+sec+"'");
      //ps.setInt(4,period_no);
      ps.setString(1,sub[0]);
      ps.setString(2,sub[1]);
      ps.setString(3,sub[2]);
      ps.setString(4,sub[3]);
      ps.setString(5,sub[4]);
      ps.setString(6,sub[5]);

      x=ps.executeUpdate();
      }catch(Exception e){
        JOptionPane.showMessageDialog(frm, e.toString());
      }
    return x;
    }
*/
