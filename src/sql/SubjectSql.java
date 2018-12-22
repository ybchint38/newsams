/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sql;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Hemant Borade
 */
public class SubjectSql extends SqlClass{
    

    public SubjectSql(){
        super();
    }

    public SubjectSql(JFrame frm){
        super(frm);
    }

    public String[] getCourse(){
        String val[]=null;
        Statement s=null;
        try{
            s=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String qry="select distinct course from class order by course";
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
    
    public String[] getCurrentSemester(String course){//current semesters
        String val[]=null;
        Statement s=null;
        try{
            s=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String qry="select distinct semester from class where course='"+ course+ "' and semester>0 and classid in (select distinct classid from student )order by semester";
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

    public String[] getCurrentSection(String course,int sem){//current semesters
        String val[]=null;
        Statement s=null;
        try{
            s=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String qry="select distinct section from class where course='"+ course+ "' and semester=" + sem +" and classid in (select distinct classid from student ) order by section";
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
    
    public boolean insertSubject(String scode,String name,File file,int type,String cls,int sem){
        try{
            String filename;
            if(file==null){
                filename="NA";
            }else{
                filename=file.getName();
                //filename=file.getAbsolutePath();
                
                //System.out.println(filename);
                //return false;
                filename=scode+filename.substring(filename.lastIndexOf("."));
            }
            PreparedStatement ps=con.prepareStatement("insert into subject (scode,sname,filename,syllabus,type,course,semester) values(?,?,?,?,?,?,?)");
            ps.setString(1, scode);
            ps.setString(2, name);
            ps.setString(3, filename);
            if(file==null){
                ps.setBinaryStream(4, null, 0);
            }else{
                FileInputStream fs=new FileInputStream(file);
                //System.out.println(file.getAbsolutePath());
                ps.setBinaryStream(4, fs, fs.available());
            }
            ps.setInt(5,type);
            ps.setString(6,cls);
            ps.setInt(7, sem);
            ps.executeUpdate();
            //if(x==1){
                return true;
            //}
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm,e.toString());
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteSubject(String scode){
        try{
            PreparedStatement ps=con.prepareStatement("delete from subject where scode='"+scode+"'");
            int x=ps.executeUpdate();
            if(x==1){
                return true;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm,"deletesubject "+e.toString());
            e.printStackTrace();
        }
        return false;
    }

    public Map<String,String[]> getSubjects(int sem){
        Map<String,String[]> val=new HashMap<String,String[]>();
        try{
            Statement s=con.createStatement();
            ResultSet rs=s.executeQuery("select scode,sname,filename,type from subject where course='MCA' and semester="+sem +" order by scode");
            while(rs.next()){
                val.put(rs.getString(1), new String[]{rs.getString(2),rs.getString(3),rs.getInt(4)+""});
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return val;
    }
    
    public ArrayList<String> getSubjectCodes(int sem){
        ArrayList<String> val=new ArrayList<>();
        try{
            Statement s=con.createStatement();
            ResultSet rs=s.executeQuery("select scode from subject where course='MCA' and semester="+sem+" order by scode");
            while(rs.next()){
                val.add(rs.getString(1));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return val;
    }

    public HashMap<String,Integer> getSubjectTypes(int sem){
        HashMap<String,Integer> val=new HashMap<>();
        try(Statement s=con.createStatement()){
            String qry="select scode,type from subject where course='MCA' and semester="+sem;
            ResultSet rs=s.executeQuery(qry);
            while(rs.next()){
                val.put(rs.getString("scode"), rs.getInt("type"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return val;
    }
    
    public int getSubjectType(String scode){
        int val=-1;
        try(Statement s=con.createStatement()){
            String qry="select type from subject where scode='"+scode+"'";
            ResultSet rs=s.executeQuery(qry);
            while(rs.next()){
                return rs.getInt("type");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return val;
    }
    
    public Vector<String> getAllSubject(){
        Vector<String> val=null;
        try{
            Statement s=con.createStatement();
            String sql="select scode,sname from subject";
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

    public File downloadSyllabus(String scode){
        File f=null;
        try{
            Statement s=con.createStatement();
            ResultSet rs=s.executeQuery("select filename,syllabus from subject where scode='"+scode+"'");
            if(rs.next()){
                String filename=rs.getString(1);
                
                if(filename==null || filename.equalsIgnoreCase("NA")){
                    return null;
                }
                f=new File(filename);
                FileOutputStream fos=new FileOutputStream(f);
                InputStream fis=rs.getBlob(2).getBinaryStream();
                //byte []b;
                //if(fis.available()>=256){
                //    b=new byte[256];
                //}else{
                //    b=new byte[fis.available()];
                //}
                //int n=fis.read(b);
                //while((n=fis.read(b))!=-1){
                //    fos.write(b,0,n);
                //}
                int n;
                while((n=fis.read())!=-1){
                    fos.write(n);
                }
                fos.flush();
                fos.close();
                fis.close();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm,e.toString());
            e.printStackTrace();
            return null;
        }
        return f;
    }

    public ArrayList<String>[] getFacultiesFor(String scode){
        ArrayList<String>[] val=new ArrayList[2];
        try{
            Statement s=con.createStatement();
            String qry="select fid,fname from faculty where fid in(select fid from facultysubject where scode='"+scode+"')";
            ResultSet rs=s.executeQuery(qry);
            val[0]=new ArrayList(0);
            val[1]=new ArrayList(0);
            for(int i=0;rs.next();i++){
                val[0].add(i, rs.getString(1));//fid
                val[1].add(i, rs.getString(2));//fname
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e);
            e.printStackTrace();
        }
        return val;
    }

    public ArrayList<String[]> getSubjectAllotments(int sem,String section){
        ArrayList<String[]> val=new ArrayList<>();
        try(Statement s=con.createStatement()){
            String qry="select f.fid,f.fname,s.scode from faculty f inner join suballotment s on f.fid=s.fid where s.semester="+sem+" and s.section='"+section+"'";
            ResultSet rs=s.executeQuery(qry);
            while(rs.next()){
                val.add(new String[]{rs.getString(1),rs.getString(2),rs.getString(3)});
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e);
            e.printStackTrace();
        }
        return val;
    }
    
    public ArrayList<String[]> getFacultiesFor(String scode,int sem,String sec){
        ArrayList<String[]> val=new ArrayList<>();
        try{
            Statement s=con.createStatement();
            String qry="select fid,fname from faculty where fid in (select fid from suballotment where scode='"+scode+"' and semester="+sem+" and section='"+sec+"')";
            ResultSet rs=s.executeQuery(qry);
            while(rs.next()){
                val.add(new String[]{rs.getString("fid"),rs.getString("fname")});
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e);
            e.printStackTrace();
        }
        return val;
    }

    public String getSubjectName(String scode){
        try{
            Statement s=con.createStatement();
            ResultSet rs=s.executeQuery("select sname from subject where scode='"+scode+"'");
            if(rs.next()){
                return rs.getString(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /*public int getCurrentBatchNo(int sem){
        try(Statement s=con.createStatement()){
            String qry="select max(batchno) from class where course='MCA' and semester="+sem;
            ResultSet rs=s.executeQuery(qry);
            if(rs.next()){
                return rs.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }*/
}
