/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author apex predator
 */
public class StudentSql extends SqlClass{

    private String eNo;
    public static final int SID=0;
    public static final int SNAME=1;
    public static final int FNAME=2;
    public static final int CNO=3;
    public static final int EID=4;
    public static final int ADDRESS=5;
    public static final int COURSE=6;
    public static final int SEM=7;
    public static final int SECTION=8;
    public static final int BNO=9;
    public static final int SRNO=10;
    
    public StudentSql(String eno){
        super();
        eNo=eno;
    }

    public StudentSql(String eno,JFrame frm){
        super(frm);
        eNo=eno;
    }

    public void setStudentID(String str){
        eNo=str;
    }

    public boolean isExist(){
        try(Statement s=con.createStatement()){
            String qry="select sid from student where sid='"+eNo+"'";
            ResultSet rs=s.executeQuery(qry);
            return rs.next();
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return true;
    }
    
    public boolean insertStudent(String sid,String name,String cls,int sem,String section,int srno,String fname,String email,String cno,String address,int bno,boolean update){
        PreparedStatement ps=null;
        try{
            int classid=insertClass(cls, sem, section, bno);
            ps=con.prepareStatement("insert into student (SID,SName,fathername,emailid,contactno,address,classid,SrNo) values(?,?,?,?,?,?,?,?)");
            ps.setString(1, sid);
            ps.setString(2, name);
            ps.setString(3, fname);
            ps.setString(4, email);
            ps.setString(5, cno);
            ps.setString(6, address);
            ps.setInt(7, classid);
            ps.setInt(8, srno);
            int x=ps.executeUpdate();
            if(x==1){
                return true;
            }
        }catch(SQLException ex){
            if(update){
                return (updateStudent(name, cls, sem, section, srno, fname, email, cno, address, bno)==0)?false:true;
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

   /* public boolean insertStudent(String eno,String name,String cls,int sem,String section,int srno,String cno,int bno,boolean update){
        PreparedStatement ps=null;
        try{
            ps=con.prepareStatement("insert into student (SID,SName,Class,Semester,Section,SrNo,ContactNo,BatchNo) values(?,?,?,?,?,?,?,?)");
            ps.setString(1, eno);
            ps.setString(2, name);
            ps.setString(3, cls);
            ps.setInt(4, sem);
            ps.setString(5, section);
            ps.setInt(6, srno);
            ps.setString(7, cno);
            ps.setInt(8, bno);
            int x=ps.executeUpdate();
            if(x==1){
                return true;
            }
        }catch(SQLException ex){
            if(update){
                if(deleteStudent()){
                    return (insertStudent(name,cls,sem,section,srno,cno,bno,false));
                }
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

  public int insertStudent(String id,String name,String clas,int semestr,String sec,int srno,String cnt,int bno)
  {
      int x=0;
      PreparedStatement ps;
      try{
  ps=con.prepareStatement("insert into student values (?,?,?,?,?,?,?,?)");
  ps.setString(1,id);
  ps.setString(2,name);
  ps.setString(3,clas);
  ps.setInt(4,semestr);
  ps.setString(5,sec);
  ps.setInt(6,srno);
  ps.setString(7,cnt);
  ps.setInt(8,bno);
  x=ps.executeUpdate();
      }catch(Exception e)
      {
      JOptionPane.showMessageDialog(frm,e.toString());
      }
        return x;
  }*/

    public boolean deleteStudent(){
        try{
            PreparedStatement ps=con.prepareStatement("delete from student where SID=?");
            ps.setString(1, eNo);
            int x=ps.executeUpdate();
            if(x==1){
                return true;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm,e.toString());
            e.printStackTrace();
        }
        return false;
    }

  public int updateStudent(String name,String cls,int sem,String section,int srno,String fname,String email,String cno,String address,int bno)
  {
      int x=0;
      PreparedStatement ps;
      try{
          int classid=insertClass(cls, sem, section, bno);
          ps=con.prepareStatement("update student set sname=?,fathername=?,emailid=?,contactno=?,address=?,classid=?,srno=? where sid=?");
          ps.setString(1,name);
          ps.setString(2,fname);
          ps.setString(3,email);
          ps.setString(4,cno);
          ps.setString(5, address);
          ps.setInt(6,classid);
          ps.setInt(7,srno);
          ps.setString(8, eNo);
          x=ps.executeUpdate();
      }catch(Exception e){
          e.printStackTrace();
          JOptionPane.showMessageDialog(frm,e.toString());
      }
        return x;
  }
  ///////////

/*public ResultSet excuteSelect(String qry){
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
}*/
  
    /*public boolean updateAllStudent(String name,String cls,int sem,String section,int srno,String cno,int bno){
        try{
            if(deleteStudent()){
                return (insertStudent(name,cls,sem,section,srno,cno,bno,false));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
            return false;
        }
        return false;
    }*/

    public String[][] getAllStudentDataFromPreviousClass(String col[],Object value[]){
        String val[][]=null;
        val=getAllStudentData(col, value);
        if(val==null || val.length==0){
            try(Statement s=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY)){
                String qry;
                qry="select s.sid,sname,fathername,contactno,emailid,address,sc.classid,sc.srno from student s inner join studentclass sc on s.sid=sc.sid where sc.classid in (select tsc.classid from class c inner join studentclass tsc on c.classid=tsc.classid  ";
                String qry2="select classid,course,semester,section,batchno from class";
                if(col!=null && value!=null){
                    qry=qry + " where ";
                    qry2=qry2 + " where ";
                    for(int i=0;i<col.length-1;i++){
                        if(value[i] instanceof String){
                            qry=qry + col[i] + "='"+ value[i]+"' and ";
                            qry2=qry2 + col[i] + "='"+ value[i]+"' and ";
                        }else{
                            qry=qry + col[i]+ "="+value[i]+" and ";
                            qry2=qry2 + col[i]+ "="+value[i]+" and ";
                        }
                    }
                    if(value[col.length-1] instanceof String){
                        qry=qry + col[col.length-1] + "='"+ value[col.length-1]+"') order by sc.srno";
                        qry2=qry2 + col[col.length-1] + "='"+ value[col.length-1]+"')";
                    }else{
                        qry=qry + col[col.length-1]+ "="+value[col.length-1]+"  order by sc.srno";
                        qry2=qry2 + col[col.length-1]+ "="+value[col.length-1]+"";
                    }
                }
                Statement s2=con.createStatement();
                ResultSet rs2=s2.executeQuery(qry2);
                HashMap<Integer,String[]> classids=new HashMap<>();
                while(rs2.next()){
                    classids.put(rs2.getInt("classid"), new String[]{rs2.getString("course"),rs2.getInt("semester")+"",rs2.getString("section"),rs2.getInt("batchno")+""});
                }
                //System.out.println(qry);
                ResultSet rs=s.executeQuery(qry);
                if(rs.last()){
                    int n=rs.getRow();
                    val=new String[n][11];
                    rs.first();
                    int i=0;
                    do{
                        val[i][SID]=rs.getString(1);
                        val[i][SNAME]=rs.getString(2);
                        val[i][FNAME]=rs.getString(3);
                        val[i][CNO]=rs.getString(4)+"";
                        val[i][EID]=rs.getString(5);
                        val[i][ADDRESS]=rs.getString(6)+"";
                        val[i][SRNO]=rs.getInt(8)+"";
                        
                        String temp[]=classids.get(rs.getInt(7));
                        
                        val[i][COURSE]=temp[0];
                        val[i][SEM]=temp[1];
                        val[i][SECTION]=temp[2];
                        val[i][BNO]=temp[3];
                        
                    
                        i++;
                    }while(rs.next());
                    s.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return val;
    }
    
    public String[][] getAllStudentData(String col[],Object value[]){//from current batches students
        String val[][]=null;
        try{
            Statement s=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String qry;
            qry="select sid,sname,fathername,contactno,emailid,address,course,semester,section,batchno,srno from student s inner join class c on s.classid=c.classid";
            if(col!=null && value!=null){
                qry=qry + " where ";
            for(int i=0;i<col.length-1;i++){
                if(value[i] instanceof String){
                    qry=qry + col[i] + "='"+ value[i]+"' and ";
                }else{
                    qry=qry + col[i]+ "="+value[i]+" and ";
                }
            }
                if(value[col.length-1] instanceof String){
                    qry=qry + col[col.length-1] + "='"+ value[col.length-1]+"' order by srno";
                }else{
                    qry=qry + col[col.length-1]+ "="+value[col.length-1]+"  order by srno";
                }
            }

            //System.out.println(qry);
            ResultSet rs=s.executeQuery(qry);
            if(rs.last()){
                int n=rs.getRow();
                val=new String[n][11];
                rs.first();
                int i=0;
                do{
                    val[i][SID]=rs.getString(1);
                    val[i][SNAME]=rs.getString(2);
                    val[i][FNAME]=rs.getString(3);
                    val[i][CNO]=rs.getString(4)+"";
                    val[i][EID]=rs.getString(5);
                    val[i][ADDRESS]=rs.getString(6)+"";
                    val[i][COURSE]=rs.getString(7);
                    val[i][SEM]=rs.getInt(8)+"";
                    val[i][SECTION]=rs.getString(9)+"";
                    val[i][BNO]=rs.getInt(10)+"";
                    val[i][SRNO]=rs.getInt(11)+"";
                    
                    i++;
                }while(rs.next());
                s.close();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return val;
    }

    public String[] getData(String col[],Object value[]){
        String val[]=null;
        try{
            Statement s=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String qry=null;
            qry="select sid,sname,fathername,contactno,emailid,address,course,semester,section,batchno,srno from student s inner join class c on s.classid=c.classid where sid='"+ eNo +"'";
            ResultSet rs=s.executeQuery(qry);
            if(rs.next()){
                val=new String[11];
                    val[SID]=rs.getString(1);
                    val[SNAME]=rs.getString(2);
                    val[FNAME]=rs.getString(3);
                    val[CNO]=rs.getString(4);
                    val[EID]=rs.getString(5);
                    val[ADDRESS]=rs.getString(6);
                    val[COURSE]=rs.getString(7);
                    val[SEM]=rs.getInt(8)+"";
                    val[SECTION]=rs.getString(9);
                    val[BNO]=rs.getInt(10)+"";
                    val[SRNO]=rs.getInt(11)+"";
            }
            s.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm,e.toString());
            e.printStackTrace();
        }
        return val;
    }

    public String[] getData(){
        return getData(null,null);
    }

    /*public ResultSet getDataUpdatable(){
        ResultSet val=null;
        try{
            Statement s=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String qry=null;
            qry="select * from student where sid='"+ eNo +"'";


            val=s.executeQuery(qry);
            s.close();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm,e.toString());
            e.printStackTrace();
        }
        return val;
    }*/

    /*public boolean updateStudent(String name,String cls,int sem,String section,int srno,String cno,int bno){
        try{
            if(deleteStudent()){
                return (insertStudent(name,cls,sem,section,srno,cno,bno,false));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
            return false;
        }
        return false;
    }*/

    public String[] getCurrentBatches(String course){//current
        String val[]=null;
        Statement s;
        try{
            s=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String qry;
            //if(course.equalsIgnoreCase("all")){
            //    qry="select distinct BatchNo from student order by BatchNo";
            //}else{
                qry="select distinct BatchNo from class where course='" + course +"' and semester>0 and classid in (select distinct classid from student) order by BatchNo";
            //}
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
    }

    public String[] getCurrentSemester(String course,int bno){
        String val[]=null;
        try{
            Statement s=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String qry="select distinct semester from class where course='" + course +"' and BatchNo=" + bno +" and semester>0 and classid in (select distinct classid from student) order by semester";
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
    }

    public String[] getCurrentSection(String course,int bno,int sem){
        String val[]=null;
        try{
            Statement s=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String qry="select distinct Section from class where course='" + course +"' and BatchNo=" + bno + " and Semester=" + sem +" and classid in (select distinct classid from student) order by section";
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
    }
    
    public String[] getBatches(String course){//All
        String val[]=null;
        Statement s;
        try{
            s=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String qry;
            //if(course.equalsIgnoreCase("all")){
            //    qry="select distinct BatchNo from student order by BatchNo";
            //}else{
                qry="select distinct BatchNo from class where course='" + course +"' order by BatchNo";
            //}
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
    }

    public String[] getSemester(String course,int bno){
        String val[]=null;
        try{
            Statement s=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String qry="select distinct semester from class where course='" + course +"' and BatchNo=" + bno +" and semester>0 order by semester";
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
    }

    public String[] getSection(String course,int bno,int sem){
        String val[]=null;
        try{
            Statement s=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String qry="select distinct Section from class where course='" + course +"' and BatchNo=" + bno + " and Semester=" + sem +" order by section";
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
    }

    public Map<Long,Integer> getAttendance(Date from, Date to,String subject,int sem,int bno){
        Map<Long,Integer> value=new HashMap();
        try{
            PreparedStatement ps;
            if(from==null || to==null){
                String qry="select attendancemaster.id,status from attendancemaster inner join attendancedetail on attendancemaster.id=attendancedetail.id where sid=? and scode=? classid in (select classid from class where semester=? and batchno=?)";
                ps=con.prepareStatement(qry);
                ps.setString(1, eNo);
                ps.setString(2, subject);
                ps.setInt(3, sem);
                ps.setInt(4, bno);
            
            }else{
                String qry="select attendancemaster.id,status from attendancemaster inner join attendancedetail on attendancemaster.id=attendancedetail.id where sid=? and scode=? and adate>=? and adate<=? and classid in (select classid from class where semester=? and batchno=?)";
                ps=con.prepareStatement(qry);
                ps.setString(1, eNo);
                ps.setString(2, subject);
                ps.setDate(3, from);
                ps.setDate(4, to);
                ps.setInt(5, sem);
                ps.setInt(6, bno);
            }
            ResultSet rs=ps.executeQuery();
            int i=0;
            while(rs.next()){
                value.put((long)rs.getInt(1), rs.getInt(2));
                i++;
            }
            //System.out.println("i="+i);
        }catch(Exception e){
            e.printStackTrace();
        }
        return value;
    }

    public Map<Long,Integer> getAttendance(String subject,int sem,int bno){
        return getAttendance(null, null, subject, sem, bno);
    }
    
    public int getAttendanceCount(Date from, Date to,String subject,int sem,int bno,int countStatus){
        int value=0;
        try{
            PreparedStatement ps;
            if(from ==null || to==null){
                String qry="select status from attendancemaster am,attendancedetail ad where am.id=ad.id and sid=? and scode=? and classid in (select classid from class where semester=? and batchno=?)";
                ps=con.prepareStatement(qry);
                ps.setString(1, eNo);
                ps.setString(2, subject);
                ps.setInt(3, sem);
                ps.setInt(4, bno);
            }else{
                String qry="select status from attendancemaster am,attendancedetail ad where am.id=ad.id and sid=? and scode=? and adate>=? and adate<=? and classid in (select classid from class where semester=? and batchno=?)";
                ps=con.prepareStatement(qry);
                ps.setString(1, eNo);
                ps.setString(2, subject);
                ps.setDate(3, from);
                ps.setDate(4, to);
                ps.setInt(5, sem);
                ps.setInt(6, bno);
            }
            
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                if(rs.getInt(1)==countStatus){
                    value++;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return value;
    }
    
    public int getAttendanceCount(String subject,int sem,int bno,int countStatus){
        return getAttendanceCount(null,null,subject, sem, bno, countStatus);
    }
    
    public int getLeaveCount(int sem){
        int count=0;
        try(Statement s=con.createStatement()){
            String qry="select count(lid) from leavedetail l inner join class c on l.classid=c.classid where sid='"+eNo+"' and semester="+sem;
            ResultSet rs=s.executeQuery(qry);
            if(rs.next()){
                count=rs.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return count;
    }
    
    public Vector getLeaveDetail(){
        Vector val=new Vector();
        try{
            Statement s=con.createStatement();
            String qry="select approvaldate,lFrom,lTo,detail from leavedetail where sid='"+ eNo+"' order by approvaldate";
            ResultSet rs=s.executeQuery(qry);
            Vector temp;
            Date t;
            Calendar cal=Calendar.getInstance();
            int sno=1;
            while(rs.next()){
                temp=new Vector();
                temp.add(0,sno);
                
                t=rs.getDate(1);
                cal.setTimeInMillis(t.getTime());
                temp.add(1,cal.get(Calendar.DAY_OF_MONTH)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.YEAR));

                t=rs.getDate(2);
                cal.setTimeInMillis(t.getTime());
                temp.add(2,cal.get(Calendar.DAY_OF_MONTH)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.YEAR));

                t=rs.getDate(3);
                cal.setTimeInMillis(t.getTime());
                temp.add(3,cal.get(Calendar.DAY_OF_MONTH)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.YEAR));

                temp.add(4,rs.getString(4));
                val.add(temp);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return val;
    }

    public ArrayList<String[]> getLeaveDetail(int sem){
        ArrayList<String[]> val=new ArrayList<>();
        try(Statement s=con.createStatement()){
            String qry="select lfrom,lto,approvaldate,detail from leavedetail l inner join class c on l.classid=c.classid where sid='"+eNo+"' and semester="+sem+" order by approvaldate";
            ResultSet rs=s.executeQuery(qry);
            int sno=1;
            SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
            while(rs.next()){
                val.add(new String[]{sno+"",df.format(rs.getDate("approvaldate")),df.format(rs.getDate("lfrom")),df.format(rs.getDate("lto")),rs.getString("detail")});
                sno=sno+1;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return val;
    }
    
    public boolean setLeaveDetail(Date ad,Date from,Date to,String detail){
        boolean val=false;
        try{
            int classid;
            int lid=getLeaveID();
            Statement s=con.createStatement();
            ResultSet rs=s.executeQuery("select classid from student where sid='"+eNo+"'");
            if(rs.next()){
                classid=rs.getInt("classid");
            }else{
                throw new IllegalStateException("Class details not found");
            }
            PreparedStatement ps=con.prepareStatement("insert into leavedetail (lid,sid,classid,approvaldate,lfrom,lto,detail) values(?,?,?,?,?,?,?)");
            ps.setInt(1,lid);
            ps.setString(2, eNo);
            ps.setInt(3, classid);
            ps.setDate(4, ad);
            ps.setDate(5, from);
            ps.setDate(6, to);
            ps.setString(7, detail);
            if(ps.executeUpdate()==1){
                val=true;
            }
            ps.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(frm, ex.toString());
            ex.printStackTrace();
        }
        return val;
    }

    public Map<String,Integer> getStudentListOnLeave(Date date,int sem,String section){
        Map<String,Integer> val=new HashMap();
        try{
            Statement s=con.createStatement();
            ResultSet rs1=s.executeQuery("select max(batchno) from class where semester="+sem);
            if(rs1.next()){
                int bno=rs1.getInt(1);
                PreparedStatement ps=con.prepareStatement("select sid,lid from leavedetail where lfrom <= ? and lto >= ? and sid in(select sid from student where classid=(select classid from class where semester=? and section=? and batchno=?))");
                ps.setDate(1, date);
                ps.setDate(2, date);
                ps.setInt(3, sem);
                ps.setString(4, section);
                ps.setInt(5, bno);
                ResultSet rs=ps.executeQuery();
                while(rs.next()){
                    val.put(rs.getString("SID"), rs.getInt("LID"));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return val;
    }    
    
    public void migrateTo(String eno,String cls,int bno,int sem,String section,int srno){
        try(Statement s=con.createStatement()){
            String qry="select classid,srno from student where sid='"+eno+"'";
            ResultSet rs=s.executeQuery(qry);
            if(rs.next()){
                PreparedStatement ps=con.prepareStatement("insert into studentclass (sid,classid,srno) values(?,?,?)");
                ps.setString(1, eno);
                int oldClassid=rs.getInt("classid");
                ps.setInt(2, oldClassid);
                ps.setInt(3, rs.getInt("srno"));
                int classid=insertClass(cls, sem, section, bno);
                if(classid!=oldClassid){
                    ps.executeUpdate();
                }
                
                ps.executeUpdate("update student set classid="+classid+", srno="+srno+" where sid='"+eno+"'");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateSrNo(String eno,int srno){
        try(PreparedStatement ps=con.prepareStatement("update student set srno=? where sid=?")){
            ps.setInt(1,srno);
            ps.setString(2, eno);
            ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private int getLeaveID() throws Exception{
        int id=101;
        Statement s=con.createStatement();
        ResultSet rs=s.executeQuery("select max(lid) from leavedetail");
        if(rs.next()){
            id=rs.getInt(1)+1;
        }
        return id;
    }
    
    public boolean delete(){
        boolean val=false;
        commit();
        setAutoCommit(false);
        try(Statement s=con.createStatement()){
            String qry="delete from attendancedetail where sid='"+eNo+"'";
            int x=s.executeUpdate(qry);
            qry="delete from leavedetail where sid='"+eNo+"'";
            x=s.executeUpdate(qry);
            qry="delete from studentclass where sid='"+eNo+"'";
            x=s.executeUpdate(qry);
            qry="delete from student where sid='"+eNo+"'";
            x=s.executeUpdate(qry);
            val=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        commit();
        setAutoCommit(true);
        return val;
    }
}
