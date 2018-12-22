/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sql;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import scheduleclass.ScheduleBase;

/**
 *
 * @author dominator
 */
public class FacultySql extends SqlClass{
    private String fid;
    
    
    public static final int FID=0;
    public static final int FNAME=1;
    public static final int CONTANCTNO=2;
    public static final int DEPT=3;
    public static final int DESIGNATION=4;
    public static final int TIMESLOT=5;
    
    
    public static final int ADMIN=2;
    public static final int FACULTY=3;
    public static final int CLASSCOORD=5;
    public static final int TIMETABLECOORD=7;
    


    public FacultySql(String fid){
        super();
        this.fid=fid;
    }

    public FacultySql(String fid,JFrame frm){
        super();
        this.fid=fid;
    }

    public void setFID(String fid){
        this.fid=fid;
    }
    
    public String getLoginID(){
        try{
            Statement st=con.createStatement();
            String qry="select ID from at_login where fid='"+fid+"'";
            ResultSet rs=st.executeQuery(qry);
            if(rs.next()){
                return rs.getString(1);
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(frm,e.toString());
        }
        return null;
    }

    public String resetPassword(){
        if(fid==null){
            return "Unexpected error occured.";
        }
        java.util.Date dt=new java.util.Date();
        String pwd=dt.getTime()+"";
        int len=pwd.length();
        pwd=pwd.substring(len-4, len-1);
        try(Statement s=con.createStatement()){
            s.executeUpdate("update at_login set pwd='"+pwd+"' where fid='"+fid+"'");
            
        }catch(Exception e){
            return "Unexpected error occured.";
        }
        return "New Password: "+pwd;
    }
/*//////////////////
  public int subjectAllotement(String cls,int sem,String sec,int bno,String fid,String scode){
  int x=0;
  PreparedStatement ps;
  try{
  ps=con.prepareStatement("insert into suballotment values (?,?,?,?,?,?)");
  ps.setString(1,cls);
  ps.setInt(2,sem);
  ps.setString(3,sec);
  ps.setString(4,scode);
  ps.setString(5,fid);
  ps.setInt(6,bno);
  x=ps.executeUpdate();
      }catch(Exception e)
      {
      JOptionPane.showMessageDialog(frm,e.toString());
      }
        return x;
  }*/

    public int getRoles(){
        int r=1;
        try{
            Statement s=con.createStatement();
            ResultSet rs=s.executeQuery("select distinct role from facultyrole where fid='"+fid+"'");
            int temp;
            while(rs.next()){
                temp=rs.getInt("Role");
                if(r%temp!=0){
                    r=r*temp;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return r;
    }
    
    public ArrayList<String[]> getAssignedRole(){
        ArrayList<String[]> val=new ArrayList<>();//
        try(Statement s=con.createStatement()){
            String qry="Select role,semester,section from facultyrole where fid='"+fid+"'";
            ResultSet rs=s.executeQuery(qry);
            String temp[];
            while(rs.next()){
                temp=new String[3];
                temp[0]=rs.getInt(1)+"";//Role
                temp[1]=rs.getInt(2)+"";//semester
                temp[2]=rs.getString(3);//section
                val.add(temp);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return val;
    }
    
    public String[][] getClasses(int role){
        String [][]val=null;//{{1,A,B,C},{2,A}}
        try{
            java.util.Date dt=new java.util.Date();
            int cy=dt.getYear()+1900;
            Statement s=con.createStatement();
            String qry;
            /*int lb,fb;
            if(session==Utility.PRESESSION){
                lb=cy+1;
                fb=cy+3;
            }else{
                lb=cy;
                fb=cy+2;
            }*/
            qry="select semester,section,role from facultyrole f where course='MCA' and fid='"+fid+"'";
            ResultSet rs=s.executeQuery(qry);
            HashMap<Integer,ArrayList<String>> temp=new HashMap();
            while(rs.next()){
                int r=rs.getInt(3);
                if(r%role==0){
                    int sem=rs.getInt(1);
                    if(temp.containsKey(sem)){
                        temp.get(sem).add(rs.getString(2));
                    }else{
                        ArrayList al=new ArrayList(0);
                        temp.put(sem, al);
                        al.add(rs.getString(2));
                    }
                }
            }
            val=new String[temp.size()][];
            Set<Integer> set=temp.keySet();
            ArrayList<String> sections;
            int i=0;
            for(int sem:set){
                sections=temp.get(sem);
                Collections.sort(sections);
                val[i]=new String[sections.size()+1];
                val[i][0]=sem+"";
                for(int j=1;j<val[i].length;j++){
                    val[i][j]=sections.get(j-1);
                }
                i++;
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(frm, e.toString());
        }
        return val;
    }

    /*public ArrayList<Integer> getAllSubject(String cls,int sem,String sec,int bno){
        ArrayList<Integer> val=new ArrayList(0);
        try{
            PreparedStatement s=con.prepareStatement("select  scode from suballotment where fid=? and class=? and semester=? and section=? and batchno=?");
            s.setString(1, fid);
            s.setString(2, cls);
            s.setInt(3, sem);
            s.setString(4, sec);
            s.setInt(5, bno);
            ResultSet rs=s.executeQuery();
            while(rs.next()){
                val.add(rs.getInt(1));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return val;
    }*/

    public Vector getAllFaculties(){
        Vector data=new Vector();
        try{
            Statement s=con.createStatement();
            ResultSet rs=s.executeQuery("select fid,fname,contactno,department from faculty");
            Vector temp;
            int sno=0;
            while(rs.next()){
                temp=new Vector();
                sno++;
                temp.addElement(sno);
                temp.addElement(rs.getString("fid"));
                temp.addElement(rs.getString("fname"));
                temp.addElement(rs.getString("contactno"));
                temp.addElement(rs.getString("department"));
                data.addElement(temp);
            }
            s.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return data;
    }

    public String[][] getAllFaculties(String name){
        String data[][]=null;
        try{
            Statement s=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=s.executeQuery("select fid,fname,contactno,department,designation,timeslot from faculty where fname like '%"+name+"%'");
            if(rs.last()){
                int n=rs.getRow();
                data=new String[n][6];
                rs.beforeFirst();
                int temp;
                for(int i=0;rs.next();i++){
                    data[i][FID]=rs.getString(1);
                    data[i][FNAME]=rs.getString(2);
                    data[i][CONTANCTNO]=rs.getString(3);
                    data[i][DEPT]=rs.getString(4);
                    data[i][DESIGNATION]=rs.getString(5);
                    temp=Integer.parseInt(rs.getString(6));
                    data[i][TIMESLOT]=ScheduleBase.SLOT_TIME[temp];
                }
            }
            s.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return data;
    }
    
    public Map<String,String> getAllFacultiesFor(String subject){
        Map<String,String> val=new HashMap<>();
        try(Statement s=con.createStatement()){
            String qry;
            if(subject==null){
                qry="select fid,fname from faculty";
            }else{
                qry="select fid,fname from faculty where fid in (select fid from facultysubject where scode='"+subject+"')";
            }
            ResultSet rs=s.executeQuery(qry);
            while(rs.next()){
                val.put(rs.getString("fname"),rs.getString("fid"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return val;
    }
    
    //insert faculty and return password
    public String insert(String name,String cno,String dept,File image,int timeslot,String designation,String uid,Vector val){
        if(insert(name,cno,dept,image,timeslot,designation)){
            if(this.insertSpecializedSubject(val)){
                try{
                    java.util.Date dt=new java.util.Date();
                    String pwd=dt.getTime()+"";
                    int len=pwd.length();
                    pwd=pwd.substring(len-4, len-1);
                    PreparedStatement ps=con.prepareStatement("insert into at_login (id,pwd,fid,status) values(?,?,?,?)");
                    ps.setString(1, uid);
                    ps.setString(2, pwd);
                    ps.setString(3, fid);
                    ps.setString(4, "FIRST");
                    if(ps.executeUpdate()==1){
                        return pwd;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private boolean insert(String name,String cno,String dept,File image,int timeslot,String designation){
        PreparedStatement ps=null;
        try{
            generateFacultyID();
            ps=con.prepareStatement("insert into faculty (fid,fname,contactno,department,image,timeslot,designation) values(?,?,?,?,?,?,?)");
            ps.setString(1, fid);
            ps.setString(2, name);
            ps.setString(3, cno);
            ps.setString(4, dept);
            if(image==null){
                ps.setBinaryStream(5, null, 0);
            }else{
                FileInputStream fs=new FileInputStream(image);
                //System.out.println(file.getAbsolutePath());
                ps.setBinaryStream(5, fs, fs.available());
            }
            ps.setInt(6, timeslot);
            ps.setString(7, designation);
            int x=ps.executeUpdate();
            if(x==1){
                return true;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
            return false;
        }finally{
            try{
                ps.close();
            }catch(Exception e){
                JOptionPane.showMessageDialog(frm,e.toString());
                e.printStackTrace();
            }
        }
        return false;
    }

    private void generateFacultyID(){
        try{
            Statement s=con.createStatement();
            ResultSet rs=s.executeQuery("select max(fid) from faculty");
            rs.next();
            fid=rs.getString(1);
            if(fid!=null){
                int id=Integer.parseInt(fid.substring(1));
                id++;
                fid="f"+id;
            }else{
                fid="f100";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /*private boolean delete(){
        PreparedStatement ps=null;
        try{
           ps=con.prepareStatement("delete from faculty where fid=?");
           ps.setString(1, fid);
           ps.executeUpdate();
           return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
            return false;
        }finally{
            try{
                ps.close();
            }catch(Exception e){
                JOptionPane.showMessageDialog(frm,e.toString());
                e.printStackTrace();
            }
        }
    }*/

    /*private boolean updateAll(String name,String cno,String dept,File image,int timeslot,String designation){
        if(delete()){
            return (insert(name,cno,dept,image,timeslot,designation));
        }
        return false;
    }*/

    public String[] getData(){
        String val[]=null;
        Statement s=null;
        try{
            s=con.createStatement();
            String qry="select * from faculty where fid='"+fid+"'";
            ResultSet rs=s.executeQuery(qry);
            if(rs.next()){
                val=new String[6];
                val[0]=fid;
                val[1]=rs.getString("Fname");
                val[2]=rs.getString("ContactNo");
                val[3]=rs.getString("Department");
                val[4]=rs.getString("designation");
                val[5]=rs.getString("timeslot");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm,e.toString());
            e.printStackTrace();
        }
        return val;
    }

    public ResultSet getDataUpdatable(){
        try{
            Statement s;
            s=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String qry="select fid,fname,contactno,department,image,timeslot,designation from faculty where fid='"+fid+"'";
            return(s.executeQuery(qry));
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm,e.toString());
            e.printStackTrace();
        }
        return null;
    }

    public Vector<String> getSpecializedSubject(){
        Vector<String> val=null;
        try{
            Statement s=con.createStatement();
            String sql="select scode,sname from subject where scode in (select scode from facultysubject where fid='"+fid+"')";
            ResultSet rs=s.executeQuery(sql);
            if(rs.next()){
                val=new Vector<String>();
              do{
                val.addElement(rs.getString(1)+":"+rs.getString(2));
              }while(rs.next());
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm,e.toString());
            e.printStackTrace();
        }
        return val;
    }

    public ArrayList<String> getSpecializedSubject(int sem){
        ArrayList<String> val=new ArrayList<>();
        try(Statement s=con.createStatement()){
            String qry="select scode,sname from subject where scode in (select scode from facultysubject where fid='"+fid+"') and semester="+sem;
            ResultSet rs=s.executeQuery(qry);
            while(rs.next()){
                val.add(rs.getString(1)+":"+rs.getString(2));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm,e.toString());
            e.printStackTrace();
        }
        return val;
    }
    
    public boolean insertSpecializedSubject(Vector val){
        PreparedStatement ps=null;
        try{
           ps=con.prepareStatement("insert into facultysubject (fid,scode) values(?,?)");
           Enumeration e=val.elements();
           String temp;
           while(e.hasMoreElements()){
               temp=e.nextElement().toString();
               int index=temp.indexOf(":");
               temp=temp.substring(0, index);
               ps.setString(1, fid);
               ps.setString(2, temp);
               ps.addBatch();
           }
           ps.executeBatch();
           return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
            return false;
        }finally{
            try{
                ps.close();
            }catch(Exception e){
                JOptionPane.showMessageDialog(frm,e.toString());
                e.printStackTrace();
            }
        }
    }

    public boolean deleteSpecializedSubject(){
        PreparedStatement ps=null;
        try{
           ps=con.prepareStatement("delete from facultysubject where FID='"+fid+"'");

           if(ps.executeUpdate()!=0){
                return true;
           }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
            return false;
        }finally{
            try{
                ps.close();
            }catch(Exception e){
                JOptionPane.showMessageDialog(frm,e.toString());
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean updateSpecializedSubject(Vector val){
        deleteSpecializedSubject();
        return insertSpecializedSubject(val);
        
    }

    public boolean isUserNameAvailable(String uname){
        try{
            Statement s=con.createStatement();
            ResultSet rs;
            /*ResultSet rs=s.executeQuery("select id from at_login where fid='"+fid+"'");
            if(rs.next()){
                if(rs.getString(1).equalsIgnoreCase(uname)){
                    return true;
                }
            }*/
            rs=s.executeQuery("select id from at_login");
            String temp;
            //System.out.println(uname);
            while(rs.next()){
                temp=rs.getString(1);
                //System.out.println(temp);
                if(uname.equalsIgnoreCase(temp)){
                    s.close();
                    return false;
                }
            }
            s.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm,e.toString());
            e.printStackTrace();
        }
        return true;
    }

    public boolean allotSubject(String cls,int sem,String section,String scode){
        boolean val=false;
        try{
            //int classid=insertClass(cls, sem, section, bno);
            PreparedStatement ps=con.prepareStatement("insert into suballotment (fid,scode,course,semester,section) values(?,?,?,?,?)");
            ps.setString(1, fid);
            ps.setString(2, scode);
            ps.setString(3, cls);
            ps.setInt(4, sem);
            ps.setString(5, section);
            
            //ps.setInt(3,classid);
            if(ps.executeUpdate()==1){
                val=true;
            }
                ps.close();

        }catch(Exception ex){
            ex.printStackTrace();
            val=false;
        }
        return val;
    }

    public boolean deAllotSubject(String cls,int sem,String section,String scode){
        boolean val=false;
        try{
            /*PreparedStatement ps=con.prepareStatement("select * from suballotment where semester=? and scode=? and fid=?",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ps.setInt(1, sem);
            ps.setString(2, scode);
            ps.setString(3, fid);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                if(rs.getString("course").equalsIgnoreCase(cls) && rs.getString("section").equalsIgnoreCase(section)){
                    rs.deleteRow();
                    
                    rs.close();
                    con.commit();
                    val=true;
                    break;
                }
            }
            ps.close();*/
            PreparedStatement ps=con.prepareStatement("delete from suballotment where semester=? and section=? and scode=? and fid=? and course=?",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ps.setInt(1, sem);
            ps.setString(2, section);
            ps.setString(3, scode);
            ps.setString(4, fid);
            ps.setString(5, cls);
            long x=ps.executeUpdate();
            if(x>=1){
                val=true;
            }
            ps.close();
        }catch(Exception ex){
            ex.printStackTrace();
            val=false;
        }
        return val;
    }

    public ArrayList<String[]> getAllotedSubject(){
        ArrayList<String[]> val=new ArrayList(0);
        try{
            /*java.util.Date dt=new java.util.Date();
            java.util.Calendar cal=java.util.Calendar.getInstance();
            cal.setTime(dt);
            int yf,yl;
            if(session==Utility.PRESESSION){
                yf=cal.get(java.util.Calendar.YEAR)+1;
                yl=cal.get(java.util.Calendar.YEAR)+3;
            }else{
                yf=cal.get(java.util.Calendar.YEAR);
                yl=cal.get(java.util.Calendar.YEAR)+2;
            }*/
            Statement s=con.createStatement();
            String qry="select * from suballotment where fid='"+fid+"'";
           // System.out.println(qry);
            String temp[];
            ResultSet rs=s.executeQuery(qry);
            while(rs.next()){
                temp=new String[5];
                temp[0]=rs.getString("course");
                temp[1]=rs.getInt("semester")+"";
                temp[2]=rs.getString("section");
                temp[3]=rs.getString("scode");
                //temp[4]=rs.getString("batchno");
                //System.out.println(temp[3]);
                val.add(temp);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return val;
    }

    /*public ArrayList<String[]> getLecturesForDay(int dy){
        ArrayList<String[]> val=new ArrayList(0);
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
            ArrayList<String[]> allotSub=this.getAllotedSubject();
            //System.out.println(allotSub.size());
            Statement s=con.createStatement();
            String qry;
            ResultSet rs;
            for(String temp[]:allotSub){
                qry="select periodno from schedule where course='"+ temp[0]+"'"
                        + " and semester="+temp[1]
                        +" and section='"+temp[2]+"' and "
                        +day+"='"+temp[3]+"'";
                //System.out.println(qry);
                rs=s.executeQuery(qry);
                while(rs.next()){
                    String arr[]=new String[5];
                    arr[0]=temp[0];             //Class
                    arr[1]=temp[1];             //semester
                    arr[2]=temp[2];             //section
                    arr[3]=temp[3];             //subject
                    arr[4]=rs.getInt(1)+"";     //period no
                    val.add(arr);
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return val;
    }*/

    public ArrayList<String[]> getLecturesForDay(int dy){
        ArrayList<String[]> val=new ArrayList(0);
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
            Statement s=con.createStatement();
            String qry;
            ResultSet rs;
            qry="select periodno,"+day+" from facultyschedule where fid='"+fid+"' and "+day+" is not null" ;
            rs=s.executeQuery(qry);
            String temp;
            while(rs.next()){
                temp=rs.getString(2)+":"+rs.getString(1);
                val.add(temp.split(":"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return val;
    }
    
    public HashMap<Integer,String> getWeeklySchedule(){
        HashMap<Integer,String> val=new HashMap<>();
        try{
            for(int i=1;i<=ScheduleBase.S_LEC7;i++){
                val.put(i, null);
            }
            if(getTimeSlot()==ScheduleBase.TIME_SLOT1){
                for(int i=ScheduleBase.M_LEC7;i<=ScheduleBase.S_LEC7;i=i+7){
                    val.put(i, "NA");
                }
            }else{
                for(int i=ScheduleBase.M_LEC1;i<=ScheduleBase.S_LEC1;i=i+7){
                    val.put(i, "NA");
                }
            }
            ArrayList<String[]> lec;
            int indx;
            for(int i=java.util.Calendar.MONDAY;i<=java.util.Calendar.SATURDAY;i++){
                lec=getLecturesForDay(i);
                indx=(i-2)*7;
                for(String[] temp:lec){
                    val.put(Integer.parseInt(temp[4])+indx, temp[0]+":"+temp[1]+":"+temp[2]+":"+temp[3]);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return val;
    }
    
    public void setSchedule(HashMap<Integer,String> schedule){
        try(Statement s=con.createStatement()){
            //HashMap<Integer,String> schedule=(HashMap<Integer,String>)sch.clone();
            /*for(int i=ScheduleBase.M_LEC1;i<=ScheduleBase.S_LEC7;i++){
                if(schedule.get(i).equalsIgnoreCase("NA")){
                    schedule.put(i,null);
                }
            }*/
            PreparedStatement ps=con.prepareStatement("insert into facultyschedule values(?,?,?,?,?,?,?,?)");
            int index;
            s.executeUpdate("delete from facultyschedule where fid='"+fid+"'");
            for(int i=1;i<=7;i++){
                index=i;
                ps.setString(1, fid);
                ps.setInt(2, i);
                ps.setString(3, schedule.get(index));
                index=index+7;
                ps.setString(4, schedule.get(index));
                index=index+7;
                ps.setString(5, schedule.get(index));
                index=index+7;
                ps.setString(6, schedule.get(index));
                index=index+7;
                ps.setString(7, schedule.get(index));
                index=index+7;
                ps.setString(8, schedule.get(index));
                ps.executeUpdate();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        /*for(int i=ScheduleBase.M_LEC1;i<=ScheduleBase.S_LEC7;i++){
            if(schedule.get(i)==null){
                    schedule.put(i,"NA");
            }
        }*/
    }
    
    public int getTimeSlot(){
        int val=1;
        try(Statement s=con.createStatement()){
            String qry="select timeslot from faculty where fid='"+fid+"'";
            ResultSet rs=s.executeQuery(qry);
            if(rs.next()){
                val=rs.getInt("timeslot");
            }else{
                qry="update faculty set timeslot="+val+" where fid='"+fid+"'";
                s.executeUpdate(qry);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return val;
    }
    
    public String getDesignation(){
        String val="NA";
        try(Statement s=con.createStatement()){
            String qry="select designation from faculty where fid='"+fid+"'";
            ResultSet rs=s.executeQuery(qry);
            if(rs.next()){
                val=rs.getString("designation");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return val;
    }
    
    public ArrayList<String> getFacultyName(String desg){
        ArrayList<String> val=new ArrayList<>();
        try(Statement s=con.createStatement()){
            String qry="select fname from faculty where designation='"+desg+"'";
            ResultSet rs=s.executeQuery(qry);
            while(rs.next()){
                val.add(rs.getString(1));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return val;
    } 
    
    public ArrayList<String> getFacultyName(int sem,String section,int role){
        ArrayList<String> val=new ArrayList<>();
        try(Statement s=con.createStatement()){
            String qry="select fname from faculty where fid in (select fid from facultyrole where course='MCA' and semester="+sem+" and section='"+section+"' and role="+role+")";
            ResultSet rs=s.executeQuery(qry);
            while(rs.next()){
                val.add(rs.getString(1));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return val;
    }
    
    public int assignRole(int role,String cls,int sem,String sec){
        int x=0;
        PreparedStatement ps;
        try{
            ps=con.prepareStatement("insert into facultyrole values (?,?,?,?,?)");
            ps.setString(1,fid);
            ps.setInt(2,role);
            ps.setString(3,cls);
            ps.setInt(4,sem);
            ps.setString(5,sec);
            x=ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(frm,e.toString());
        }
        return x;
    }
    
    public int removeRole(int role,int sem,String sec){
        int x=0;
        try(Statement s=con.createStatement()){
            String qry="delete from facultyrole where fid='"+fid+"' and role="+role+" and semester="+sem+" and section='"+sec+"'";
            x=s.executeUpdate(qry);
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(frm,e.toString());
        }
        return x;
    }
    
    public ArrayList<Integer[]> getLectureDaysFor(String scode,int sem,String section){
        ArrayList<Integer[]> val=new ArrayList<>();
        try(Statement s=con.createStatement()){
            String qry="select * from facultyschedule where fid='"+fid+"'";
            ResultSet rs=s.executeQuery(qry);
            String temp[];
            String t;
            int pno;
            while(rs.next()){
                t=rs.getString("mon");
                pno=rs.getInt("periodno");
                temp=(t==null)?null:t.split(":");
                if(temp!=null && temp[1].equalsIgnoreCase(sem+"") && temp[2].equalsIgnoreCase(section) && temp[3].equalsIgnoreCase(scode)){
                    val.add(new Integer[]{Calendar.MONDAY,pno});
                }
                
                t=rs.getString("tue");
                temp=(t==null)?null:t.split(":");
                if(temp!=null && temp[1].equalsIgnoreCase(sem+"") && temp[2].equalsIgnoreCase(section) && temp[3].equalsIgnoreCase(scode)){
                    val.add(new Integer[]{Calendar.TUESDAY,pno});
                }
                
                t=rs.getString("wed");
                temp=(t==null)?null:t.split(":");
                if(temp!=null && temp[1].equalsIgnoreCase(sem+"") && temp[2].equalsIgnoreCase(section) && temp[3].equalsIgnoreCase(scode)){
                    val.add(new Integer[]{Calendar.WEDNESDAY,pno});
                }
                
                t=rs.getString("thus");
                temp=(t==null)?null:t.split(":");
                if(temp!=null && temp[1].equalsIgnoreCase(sem+"") && temp[2].equalsIgnoreCase(section) && temp[3].equalsIgnoreCase(scode)){
                    val.add(new Integer[]{Calendar.THURSDAY,pno});
                }
                
                t=rs.getString("fri");
                temp=(t==null)?null:t.split(":");
                if(temp!=null && temp[1].equalsIgnoreCase(sem+"") && temp[2].equalsIgnoreCase(section) && temp[3].equalsIgnoreCase(scode)){
                    val.add(new Integer[]{Calendar.FRIDAY,pno});
                }
                
                t=rs.getString("sat");
                temp=(t==null)?null:t.split(":");
                if(temp!=null && temp[1].equalsIgnoreCase(sem+"") && temp[2].equalsIgnoreCase(section) && temp[3].equalsIgnoreCase(scode)){
                    val.add(new Integer[]{Calendar.SATURDAY,pno});
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(frm,e.toString());
        }
        return val;
    }
    
    public int getLecturesPerWeek(){
        int val=0;
        try(Statement s=con.createStatement()){
            String qry="select mon,tue,wed,thus,fri,sat from facultyschedule where fid='"+fid+"'";
            ResultSet rs=s.executeQuery(qry);
            while(rs.next()){
                for(int i=1;i<=6;i++){
                    if(rs.getString(i)!=null){
                        val++;
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(frm,e.toString());
        }
        return val;
    }
    
    public InputStream getImage(){
        InputStream is=null;
        try(Statement s=con.createStatement()){
            String qry="select image from faculty where fid='"+fid+"'";
            ResultSet rs=s.executeQuery(qry);
            if(rs.next()){
                Blob blob=rs.getBlob("image");
                return (blob==null)?null:blob.getBinaryStream();
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(frm,e.toString());
        }
        return is;
    }
    
    public boolean delete(){
        boolean val=false;
        commit();
        setAutoCommit(false);
        try(Statement s=con.createStatement()){
            String qry="select count(fid) from facultyrole where fid='"+fid+"'";
            ResultSet rs=s.executeQuery(qry);
            if(rs.next()){
                if(rs.getInt(1)>0){
                    setAutoCommit(true);
                    return false;
                }
            }else{
                throw new Exception("Unable to fetch record");
            }
            qry="select count(fid) from suballotment where fid='"+fid+"'";
            rs=s.executeQuery(qry);
            if(rs.next()){
                if(rs.getInt(1)>0){
                    setAutoCommit(true);
                    return false;
                }
            }else{
                throw new Exception("Unable to fetch record");
            }
            
            qry="delete from at_login where fid='"+fid+"'";
            int x=s.executeUpdate(qry);
            qry="delete from faculty where fid='"+fid+"'";
            x=s.executeUpdate(qry);
            qry="delete from facultyschedule where fid='"+fid+"'";
            x=s.executeUpdate(qry);
            qry="delete from facultysubject where fid='"+fid+"'";
            x=s.executeUpdate(qry);
            val=true;
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(frm,e.toString());
            rollback();
        }
        commit();
        setAutoCommit(true);
        return val;
    }
    /*public HashSet<Integer> getLectureDay(String scode){
        HashSet<Integer> val=new HashSet<>();
        try(Statement s=con.createStatement()){
            String qry="select * from facultySchedule where fid='"+fid+"'";
            ResultSet rs=s.executeQuery(qry);
            String temp;
            while(rs.next()){
                for(int i=3;i<=8;i++){
                    temp=rs.getString(i);
                    if(temp!=null && temp.contains(scode)){
                        //we know Calendar.MONDAY=2;
                        val.add(i-1);
                    }
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return val;
    }*/
    
    /*public ArrayList<Integer> getAllSemesters(){
        ArrayList<Integer> val=new ArrayList(0);
        try{
            Statement s=con.createStatement();
            ResultSet rs=s.executeQuery("select semester from facultyrole f where fid='"+fid+"' order by semester");
            while(rs.next()){
                val.add(rs.getInt(1));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return val;
    }

    public ArrayList<String> getAllSection(int sem){
        ArrayList<String> val=new ArrayList(0);
        try{
            Statement s=con.createStatement();
            ResultSet rs=s.executeQuery("select section from facultyrole f where fid='"+fid+"' and semester="+sem+" order by section");
            while(rs.next()){
                val.add(rs.getString(1));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return val;
    }

    public String[] getSubjects(int sem,String sec){
        String val[]=null;
        try{
            Statement s=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String qry="select distinct scode from suballotment where fid='"+fid+"' and course='MCA' and Semester=" + sem +" and section='"+sec+"') order by scode";
            ResultSet rs=s.executeQuery(qry);
            if(rs.last()){
                int n=rs.getRow();
                val=new String[n];
                rs.first();
                int i=0;
                do{
                    val[i]=rs.getString(1);
                    i++;
                }while(rs.next());
            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(frm,e.toString());
            e.printStackTrace();
        }
        return val;
    }*/

   /* public ResultSet selectFaculty(String qry){
ResultSet rs=null;
Statement st;
try
{
st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
rs=st.executeQuery(qry);
}catch(Exception e){
JOptionPane.showMessageDialog(frm,e.toString());
}
return rs;
}
/////////////
    public ResultSet selectStudent(String qry){
ResultSet rs=null;
Statement st;
try
{
st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
rs=st.executeQuery(qry);
}catch(Exception e){
JOptionPane.showMessageDialog(frm,e.toString());
}
return rs;
}
/////////////////

    public int insertRole(String fid,int role,String cls,int sem,String sec,int bno){
      int x=0;
      PreparedStatement ps;
      try{
  ps=con.prepareStatement("insert into facultyrole values (?,?,?,?,?,?)");
  ps.setString(1,fid);
  ps.setInt(2,role);
  ps.setString(3,cls);
  ps.setInt(4,sem);
  ps.setString(5,sec);
  ps.setInt(6,bno);
  x=ps.executeUpdate();
      }catch(Exception e)
      {
      JOptionPane.showMessageDialog(frm,e.toString());
      }
        return x;
  }*/

}
