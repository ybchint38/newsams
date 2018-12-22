/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package report.handler;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import report.datasource.CustomDataSource;

/**
 *
 * @author yash
 */
public class AttendanceReport {
    
    public void dailyAttendance(String cls,String subject,String duration,String faculty,String dates[],String names[][],String data[][]){
        int noOfSubReport=data[0].length/16+1;
        String subReportParamNames[]=new String[]{"date_1","date_2","date_3","date_4","date_5","date_6","date_7","date_8","date_9","date_10","date_11","date_12","date_13","date_14","date_15"};
        String subReportCol[]=new String[]{"sno","sname","date_1_val","date_2_val","date_3_val","date_4_val","date_5_val","date_6_val","date_7_val","date_8_val","date_9_val","date_10_val","date_11_val","date_12_val","date_13_val","date_14_val","date_15_val"};
        
        Object mainData[][]=new Object[noOfSubReport][2];
        
        HashMap mainParam=new HashMap<>();
        mainParam.put("class", cls);
        mainParam.put("subject", subject);
        mainParam.put("duration", duration);
        mainParam.put("faculty", faculty);
        
        CustomDataSource subDataSource;
        String subData[][];
        HashMap subParam;
        for(int i=0;i<noOfSubReport;i++){
            
            subData=new String[names.length][17]; //forming subreportdata i.e attendance
            for(int j=0;j<subData.length;j++){
                subData[j][0]=names[j][0];
                subData[j][1]=names[j][1];
                for(int k=2;k<17;k++){
                    subData[j][k]=((i*15)+k-2<data[0].length)?data[j][(i*15)+k-2]:null;
                }
            }
            subDataSource=new CustomDataSource(subReportCol, subData);
            
            subParam=new HashMap();//forming dates
            for(int m=0;m<subReportParamNames.length;m++){
                subParam.put(subReportParamNames[m], ((i*15)+m<dates.length)?dates[(i*15)+m]:null);
            }
            
            mainData[i][0]=subParam;//hashmap
            mainData[i][1]=subDataSource;//2-d array
        }
        
        CustomDataSource mainDataSource=new CustomDataSource(new String[]{"subreport_parameter","subreport_data"}, mainData);
        
        try{
            ReportBase rb=new ReportBase();
            JasperPrint jprint=(JasperPrint)JasperFillManager.fillReport(rb.getReportPath(ReportBase.ATTNDNC_DAILY), mainParam, mainDataSource);
            String output="report/attendance_daily.pdf";
            JasperExportManager.exportReportToPdfFile(jprint, output);
            
            Desktop d=Desktop.getDesktop();
            d.open(new File(output));
            //System.out.println("Generated");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void attendanceSheet(String session,String cls,String faculty,String date[],String subject,String time[],String data[][]){
        String col[]=new String[]{"sno","sname"};
        
        CustomDataSource ds=new CustomDataSource(col, data);
        
        Object mainData[][]=new Object[date.length][2];
        HashMap param;
        
        for(int i=0;i<date.length;i++){
            param=new HashMap();
            param.put("session", session);
            param.put("class", cls);
            param.put("faculty", faculty);
            param.put("date", "Date: "+date[i]);
            param.put("subject", subject);
            param.put("time", time[i]);
            mainData[i][0]=param;
            mainData[i][1]=new CustomDataSource(col, data);//ds;
        }
        
        CustomDataSource mainDS=new CustomDataSource(new String[]{"subreport_parameter","subreport_data"}, mainData);
        try{
            ReportBase rb=new ReportBase();
            JasperPrint jprint=(JasperPrint)JasperFillManager.fillReport(rb.getReportPath(ReportBase.ATTNDNC_SHEET_MLTPL), null, mainDS);
            String output="report/attendance_sheet.pdf";
            JasperExportManager.exportReportToPdfFile(jprint, output);
            
            Desktop d=Desktop.getDesktop();
            d.open(new File(output));
            //System.out.println("Generated");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void attendanceCompile2(String cls,int sem,String section,String duration,ArrayList<String> theoSubject,ArrayList<String> pracSubject,HashMap<String,Integer> theoTotal,HashMap<String,Integer> pracTotal,String data[][]){
        String col[]=new String[]{"sno","sname","s1","s2","s3","s4","s5","theo_total","theo_per","s6","s7","prac_total","prac_per","total_per"};
        
        Map param=new HashMap();
        
        ReportBase rb=new ReportBase();
        param.put("head", rb.getHOD());
        param.put("class_coordinator", rb.getClassCoordinator(cls, sem, section));
        param.put("class", cls+" - "+sem+" '"+section+"'");
        param.put("duration", duration);
        param.put("course_coordinator",rb.getCourseCoordinator());
        
        param.put("s1_name", theoSubject.get(0));
        param.put("s2_name", theoSubject.get(1));
        param.put("s3_name", theoSubject.get(2));
        param.put("s4_name", theoSubject.get(3));
        param.put("s5_name", theoSubject.get(4));
        
        param.put("s6_name", pracSubject.get(0));
        param.put("s7_name", pracSubject.get(1));
        
        param.put("s1_total", theoTotal.get(theoSubject.get(0))+"");
        param.put("s2_total", theoTotal.get(theoSubject.get(1))+"");
        param.put("s3_total", theoTotal.get(theoSubject.get(2))+"");
        param.put("s4_total", theoTotal.get(theoSubject.get(3))+"");
        param.put("s5_total", theoTotal.get(theoSubject.get(4))+"");
        param.put("theo_total", theoTotal.get("total")+"");
        
        param.put("s6_total", pracTotal.get(pracSubject.get(0))+"");
        param.put("s7_total", pracTotal.get(pracSubject.get(1))+"");
        param.put("prac_total", pracTotal.get("total")+"");
        
        CustomDataSource ds=new CustomDataSource(col, data);
        
        try{
            JasperPrint jprint=(JasperPrint)JasperFillManager.fillReport(rb.getReportPath(ReportBase.ATTNDNC_CMPL_2), param, ds);
            String output="report/attndnc_compile_2.pdf";
            JasperExportManager.exportReportToPdfFile(jprint, output);
            
            Desktop d=Desktop.getDesktop();
            d.open(new File(output));
            //System.out.println("Generated");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
