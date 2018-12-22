/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package report.handler;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import report.datasource.CustomDataSource;
import sql.StudentSql;

/**
 *
 * @author yash
 */
public class StudentReport {
    
    public void studentLeaveReport(String sid,String sname){
        String col[]=new String[]{"sno","a_date","f_date","t_date","reason"};
        String data[][];
        
        Object mainData[][];
        
        StudentSql sql=new StudentSql(sid);
        int bno=Integer.parseInt(sql.getData()[StudentSql.BNO]);
        String sem[]=sql.getSemester("MCA", bno);
        
        mainData=new Object[sem.length][2];
        ArrayList<String[]> temp;
        for(int i=0;i<sem.length;i++){
            temp=sql.getLeaveDetail(Integer.parseInt(sem[i]));
            data=new String[temp.size()][5];
            for(int j=0;j<data.length;j++){
                data[j]=temp.get(j);
            }
            
            mainData[i][0]="Semster "+sem[i];
            mainData[i][1]=new CustomDataSource(col, data);
        }
        
        CustomDataSource mainDS=new CustomDataSource(new String[]{"semester","leave_data"}, mainData);
        
        HashMap mainParam=new HashMap();
        mainParam.put("sname", sname);
        System.out.println(sname);
        mainParam.put("eno", sid);
        
        try{
            ReportBase rb=new ReportBase();
            JasperPrint jprint=(JasperPrint)JasperFillManager.fillReport(rb.getReportPath(ReportBase.STUDENT_LEAVE_REPORT), mainParam, mainDS);
            String output="report/student_leave_report.pdf";
            JasperExportManager.exportReportToPdfFile(jprint, output);
            
            Desktop d=Desktop.getDesktop();
            d.open(new File(output));
            //System.out.println("Generated");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void studentList(String cls,int sem,String section,String title){
        String col[]=new String[]{"sno","sname"};
        String data[][];
        
        StudentSql sql=new StudentSql(null);
        String allData[][]=sql.getAllStudentData(new String[]{"course","semester","section"}, new String[]{cls,sem+"",section});
        sql.close();
        
        data=new String[allData.length][2];
        
        for(int i=0;i<data.length;i++){
            data[i][0]=allData[i][StudentSql.SRNO];
            data[i][1]=allData[i][StudentSql.SNAME];
        }
        CustomDataSource ds=new CustomDataSource(col, data);
        
        HashMap param=new HashMap();
        param.put("title", title);
        param.put("class", cls+" "+sem+" '"+section+"'");
        
        try{
            ReportBase rb=new ReportBase();
            JasperPrint jprint=(JasperPrint)JasperFillManager.fillReport(rb.getReportPath(ReportBase.STUDENT_LIST), param, ds);
            String output="report/student_list.pdf";
            JasperExportManager.exportReportToPdfFile(jprint, output);
            
            Desktop d=Desktop.getDesktop();
            d.open(new File(output));
            //System.out.println("Generated");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void studentReport(String cls,int sem,String section){
        
        String heading=cls+" "+sem+" '"+section+"'";
        String col[]=new String[]{"sno","eno","sname","s_fname","cno","emailid","address"};
        String data[][];
        
        StudentSql sql=new StudentSql(null);
        String allData[][]=sql.getAllStudentData(new String[]{"course","semester","section"}, new String[]{cls,sem+"",section});
        sql.close();
                
        data=new String[allData.length][7];
        
        for(int i=0;i<data.length;i++){
            data[i][0]=allData[i][StudentSql.SRNO];
            data[i][1]=allData[i][StudentSql.SID];
            data[i][2]=allData[i][StudentSql.SNAME];
            data[i][3]=allData[i][StudentSql.FNAME];
            data[i][4]=allData[i][StudentSql.CNO];
            data[i][5]=allData[i][StudentSql.EID];
            data[i][6]=allData[i][StudentSql.ADDRESS];
        }
        CustomDataSource ds=new CustomDataSource(col, data);
        
        CustomDataSource mainDS=new CustomDataSource(new String[]{"heading","student_data"}, new Object[][]{{heading,ds}});
        
        try{
            ReportBase rb=new ReportBase();
            JasperPrint jprint=(JasperPrint)JasperFillManager.fillReport(rb.getReportPath(ReportBase.STUDENT_REPORT), null, mainDS);
            String output="report/student_report.pdf";
            JasperExportManager.exportReportToPdfFile(jprint, output);
            
            Desktop d=Desktop.getDesktop();
            d.open(new File(output));
            //System.out.println("Generated");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void studentReport(String cls,int bno,int sem,String section){
        
        String heading=cls+" "+sem+" '"+section+"'";
        String col[]=new String[]{"sno","eno","sname","s_fname","cno","emailid","address"};
        String data[][];
        
        StudentSql sql=new StudentSql(null);
        String allData[][]=sql.getAllStudentDataFromPreviousClass(new String[]{"course","batchno","semester","section"}, new String[]{cls,bno+"",sem+"",section});
        sql.close();
                
        data=new String[allData.length][7];
        
        for(int i=0;i<data.length;i++){
            data[i][0]=allData[i][StudentSql.SRNO];
            data[i][1]=allData[i][StudentSql.SID];
            data[i][2]=allData[i][StudentSql.SNAME];
            data[i][3]=allData[i][StudentSql.FNAME];
            data[i][4]=allData[i][StudentSql.CNO];
            data[i][5]=allData[i][StudentSql.EID];
            data[i][6]=allData[i][StudentSql.ADDRESS];
        }
        CustomDataSource ds=new CustomDataSource(col, data);
        
        CustomDataSource mainDS=new CustomDataSource(new String[]{"heading","student_data"}, new Object[][]{{heading,ds}});
        
        try{
            ReportBase rb=new ReportBase();
            JasperPrint jprint=(JasperPrint)JasperFillManager.fillReport(rb.getReportPath(ReportBase.STUDENT_REPORT), null, mainDS);
            String output="report/student_report.pdf";
            JasperExportManager.exportReportToPdfFile(jprint, output);
            
            Desktop d=Desktop.getDesktop();
            d.open(new File(output));
            //System.out.println("Generated");
        }catch(Exception e){
            e.printStackTrace();
        }
    }    
}
