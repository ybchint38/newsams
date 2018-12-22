/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author yash
 */
public class LoginSql extends SqlClass{
    
    public String verifyLogin(String uid,String pwd){
        String fid=null;
        try(Statement s=con.createStatement()){
            String sql="select * from at_login where id='"+uid+"'";
            ResultSet rs=s.executeQuery(sql);
            while(rs.next()){
                if(rs.getString("id").equalsIgnoreCase(uid) && rs.getString("pwd").equals(pwd)){
                    //if(rs.getString("status"))
                    fid=rs.getString("fid");
                    return fid;
                }
            }
            JOptionPane.showMessageDialog(frm,"Invalid Username or Password.Please Try again.");
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm,e.toString());
            e.printStackTrace();
        }
        return fid;
    }
    
    public boolean changePassword(String fid,String oldpwd,String newpwd){
        try(Statement s=con.createStatement()){
            String sql="select * from at_login where fid='"+fid+"'";
            ResultSet rs=s.executeQuery(sql);
            while(rs.next()){
                if(rs.getString("pwd").equalsIgnoreCase(oldpwd)){
                    //if(rs.getString("status"))
                    //String uid=rs.getString("");
                    sql="update at_login set pwd='"+newpwd+"' where fid='"+fid+"'";
                    s.executeUpdate(sql);
                    JOptionPane.showMessageDialog(frm,"Password successfully changed.");
                    return true;
                }
            }
            JOptionPane.showMessageDialog(frm,"Current Password is incorrect.Please Try again.");
        }catch(Exception e){
            JOptionPane.showMessageDialog(frm,e.toString());
            e.printStackTrace();
        }
        return false;
    }
    
    
}
