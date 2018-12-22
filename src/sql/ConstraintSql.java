/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author yash
 */
public class ConstraintSql extends SqlClass{

    public ConstraintSql() {
        super();
    }

    public ConstraintSql(JFrame frame) {
        super(frame);
    }
    
    /*
    public int[] getConstraints(){
        int val[]=new int[5];
        val[0]=12;
        val[1]=16;
        val[2]=18;
        val[3]=4;
        val[4]=2;
        return val;
    } 
    */
    
    public int[] getConstraints(){
        int val[]=new int[5];
        try(Statement s=con.createStatement()){
            String qry="select * from constraint_common";
            ResultSet rs=s.executeQuery(qry);
            int cnt=0;
            while(rs.next()){
                val[rs.getInt("cid")-1]=rs.getInt("value");
                cnt++;
            }
            if(cnt!=5){
                JOptionPane.showMessageDialog(frm, "All Constraints are not defined.");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return val;
    }
    
    /*
    public int[] getConstraints(int sem){
        int val[]=new int[5];
        val[0]=4;
        val[1]=8;
        val[2]=2;
        val[3]=1;
        val[4]=1;
        return val;
    } 
    */
    
    public int[] getConstraints(int sem){
        int val[]=new int[5];
        try(Statement s=con.createStatement()){
            String qry="select cid,value from constraints where semester="+sem;
            ResultSet rs=s.executeQuery(qry);
            int cnt=0;
            while(rs.next()){
                val[rs.getInt("cid")-1]=rs.getInt("value");
                cnt++;
            }
            if(cnt<5){
                JOptionPane.showMessageDialog(frm, "All Constraints are not defined.");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
        return val;
    }
    
    public void put(int cnst,int value){
        try(Statement s=con.createStatement()){
            String qry="UPDATE constraint_common SET value="+value+" where cid="+cnst;
            int n=s.executeUpdate(qry);
            if(n==0){
                qry="INSERT INTO constraint_common values("+cnst+","+value+")";
                s.executeUpdate(qry);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
    }
    
    public void put(int sem,int cnst,int value){
        try(Statement s=con.createStatement()){
            String qry="UPDATE constraints SET value="+value+" where cid="+cnst+" and semester="+sem;
            int n=s.executeUpdate(qry);
            if(n==0){
                qry="INSERT INTO constraints values("+sem+","+cnst+","+value+")";
                s.executeUpdate(qry);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm, e.toString());
            e.printStackTrace();
        }
    }
    
}
