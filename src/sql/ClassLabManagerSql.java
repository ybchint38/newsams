/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import scheduleclass.Subject;

/**
 *
 * @author yash
 */
public class ClassLabManagerSql extends SqlClass{

    public ClassLabManagerSql() {
    }

    public ClassLabManagerSql(JFrame frame) {
        super(frame);
    }
    
    public int getRoomNo(String cls,int sem,String sec){
        int val=-1;
        try(Statement s=con.createStatement()){
            String qry="select roomno from classroom where course='"+cls+"' and semester="+sem+" and section='"+sec+"'";
            ResultSet rs=s.executeQuery(qry);
            if(rs.next()){
                val=rs.getInt("roomno");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return val;
    }
    
    public Vector<Short> getAvailableRooms(){
        Vector<Short> val=new Vector<>();
        try(Statement s=con.createStatement()){
            String qry="select roomno from classroom where semester is null and section is null";
            ResultSet rs=s.executeQuery(qry);
            while(rs.next()){
                val.add(rs.getShort("roomno"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return val;
    }
    
    public ArrayList<String[]> getAllRooms(){
        ArrayList<String[]> val=new ArrayList<>();
        try(Statement s=con.createStatement()){
            String qry="select * from classroom order by roomno";
            ResultSet rs=s.executeQuery(qry);
            while(rs.next()){
                if(rs.getString("course")!=null){
                    val.add(new String[]{rs.getShort("roomno")+"","MCA "+rs.getInt("semester")+"("+rs.getString("section")+")"});
                }else{
                    val.add(new String[]{rs.getShort("roomno")+"","NA"});
                }
           }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return val;
    }
    
    public Vector<String> getComputerLabs(){
        Vector<String> val=new Vector<>();
        try(Statement s=con.createStatement()){
            String qry="select labname from computerlab";
            ResultSet rs=s.executeQuery(qry);
            while(rs.next()){
                val.add(rs.getString("labname"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return val;
    }
    
    public ArrayList<String>[] getComputerLabNameID(){
        ArrayList<String>[] val=new ArrayList[]{new ArrayList<>(),new ArrayList<>()};
        try(Statement s=con.createStatement()){
            String qry="select * from computerlab";
            ResultSet rs=s.executeQuery(qry);
            while(rs.next()){
                val[0].add(rs.getString("labid"));
                val[1].add(rs.getString("labname"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return val;
    }
    
    public int addRoom(int roomno){
        try(PreparedStatement ps=con.prepareStatement("insert into classroom (roomno) values(?)")){
            ps.setShort(1, (short)roomno);
            //ps.setString(2,"MCA");
            int x=ps.executeUpdate();
            return x;
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return -1;
    }
    
    public String deleteRoom(int roomno){
        String val=null;
        try(Statement s=con.createStatement()){
            String qry="select course,semester,section from classroom where semester is not null and section is not null and roomno="+roomno;
            ResultSet rs=s.executeQuery(qry);
            if(rs.next()){
                val="Can not delete this class room, It is been alloted to "+rs.getString("course")+" "+rs.getInt("semester")+" \""+rs.getString("section")+"\"";
                return val;
            }
            qry="delete from classroom where roomno="+roomno;
            int x=s.executeUpdate(qry);
            if(x>=1){
                val="This class room has been removed successfully.";
            }else{
                val="Class room not found";
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return val;
    }
    
    public int putRoom(String cls,int roomno,int sem,String sec){
        try(Statement s=con.createStatement()){
            String qry="update classroom set course='"+cls+"', semester="+sem+", section='"+sec+"' where roomno="+roomno;
            int x=s.executeUpdate(qry);
            return x;
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return -1;
    }
    
    public void removeRoom(int roomno){
        try(Statement s=con.createStatement()){
            String qry="update classroom set course=null, semester=null, section=null where roomno="+roomno;
            int x=s.executeUpdate(qry);
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
    }

    public int putLab(String labname,String scode,String cls,int sem,String sec){
        try(PreparedStatement ps=con.prepareStatement("insert into laballotment (labid,course,semester,section,scode) values(?,?,?,?,?)")){
            Statement s=con.createStatement();
            //System.out.println(labname);
            String qry="delete from laballotment where course='"+cls+"' and semester="+sem+" and section='"+sec+"' and scode='"+scode+"'";
            s.executeUpdate(qry);
            qry="select labid from computerlab where labname='"+labname+"'";
            ResultSet rs=s.executeQuery(qry);
            int id=0;
            if(rs.next()){
                id=rs.getInt("labid");
                ps.setInt(1, id);
                ps.setString(2, cls);
                ps.setInt(3, sem);
                ps.setString(4, sec);
                ps.setString(5, scode);
                ps.executeUpdate();
            }else{
                throw new Exception("Lab id not found for lab "+labname);
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return -1;
    }
    
    public void removeLab(String labname,String scode,String cls,int sem,String sec){
        try(Statement s=con.createStatement()){
            String qry="select labid from computerlab where labname='"+labname+"'";
            ResultSet rs=s.executeQuery(qry);
            int id=0;
            if(rs.next()){
                id=rs.getInt("labid");
            }
            qry="delete from laballotment where labid="+id+" and scode='"+scode+"' and course='"+cls+"' and semester="+sem+" and section='"+sec+"'";
            s.executeUpdate(qry);
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
    }
    
    public String[][] getSubjectLab(String cls,int sem,String sec){
        String[][] val=null;
        try(Statement s=con.createStatement()){
            SubjectSql sql=new SubjectSql();
            Map<String,Integer> map=sql.getSubjectTypes(sem);
            Set<String> tSub=map.keySet();
            int cnt=0;
            for(String sub:tSub){
                int type=map.get(sub);
                switch(type){
                    case Subject.LAB1:
                    case Subject.LAB2:
                    case Subject.ADD_LAB:
                        cnt++;
                }
            }
            sql.close();
            val=new String[cnt][2];
            String qry;
            ResultSet rs;
            int i=0;
            for(String sub:tSub){
                int type=map.get(sub);
                switch(type){
                    case Subject.LAB1:
                    case Subject.LAB2:
                    case Subject.ADD_LAB:
                        qry="select labname from computerlab c inner join laballotment a on c.labid=a.labid where course='"+cls+"' and semester="+sem+" and section='"+sec+"' and scode='"+sub+"'";
                        rs=s.executeQuery(qry);
                        if(rs.next()){
                            val[i][0]=sub;
                            val[i][1]=rs.getString("labname");
                        }else{
                            val[i][0]=sub;
                            val[i][1]=null;
                        }
                        i++;
                }
            }
            
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(frm, e.toString());
        }
        return val;
    }

    public int addLab(String labName){
        try(PreparedStatement ps=con.prepareStatement("insert into computerlab (labname) values(?)")){
            ps.setString(2,labName);
            int x=ps.executeUpdate();
            return x;
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return -1;
    }
    
    public String deleteLab(String labName){
        String val=null;
        try(Statement s=con.createStatement()){
            String qry="select labid from computerlab where labname='"+labName+"'";
            ResultSet rs=s.executeQuery(qry);
            int labid=0;
            if(rs.next()){
                labid=rs.getInt("labid");
            }else{
                return "Computer Lab not found";
            }
            qry="select count(labid) from laballotment where labid="+labid;
            rs=s.executeQuery(qry);
            if(rs.next()){
                if(rs.getInt(1)>=1){
                    val="Can not remove this lab. This is been alloted to class";
                    return val;
                }
            }
            qry="delete from computerlab where labid="+labid;
            int x=s.executeUpdate(qry);
            if(x>=1){
                val="This Computer Lab has been removed successfully.";
            }else{
                val="Computer Lab not found";
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return val;
    }
    
    public String getLabName(String scode,int sem,String sec){
        String lab=null;
        try(Statement s=con.createStatement()){
            String qry="select labname from computerlab c inner join laballotment a on c.labid=a.labid where scode='"+scode+"' and semester="+sem+" and section='"+sec+"'";
            ResultSet rs=s.executeQuery(qry);
            if(rs.next()){
                lab=rs.getString("labname");
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(frm, e.toString());
        }
        return lab;
    }    
    
    public ArrayList<String> getClassesAlloted(String lname){
        ArrayList<String> val=new ArrayList<>();
        try(Statement s=con.createStatement()){
            String qry="select * from laballotment where labid=(select labid from computerlab where labname='"+lname+"')";
            ResultSet rs=s.executeQuery(qry);
            while(rs.next()){
                val.add(rs.getString("course") + " "+rs.getInt("semester")+"("+rs.getString("section")+") for "+rs.getString("scode"));
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(frm, e.toString());
        }
        return val;
    }
}
