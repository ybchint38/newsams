/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package report.handler;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import report.datasource.CustomDataSource;
import scheduleclass.ScheduleBase;
import sql.FacultySql;

/**
 *
 * @author yash
 */
public class FacultyReport {
    
    public void generateFacultyReport(){
        String col[]=new String[]{"FNAME","CONTACTNO","TIMESLOT","DESIGNATION","LEC_PER_WEEK"};
        
        String data[][];
        
        FacultySql sql=new FacultySql(null);
        String temp[][]=sql.getAllFaculties("");
        
        data=new String[temp.length][5];
        for(int i=0;i<data.length;i++){
            data[i][0]=temp[i][FacultySql.FNAME];
            data[i][1]=temp[i][FacultySql.CONTANCTNO];
            data[i][2]=temp[i][FacultySql.TIMESLOT];
            data[i][3]=temp[i][FacultySql.DESIGNATION];
            sql.setFID(temp[i][FacultySql.FID]);
            data[i][4]=sql.getLecturesPerWeek()+"";
        }
        sql.close();
        
        CustomDataSource mainDS=new CustomDataSource(col, data);
        try{
            ReportBase rb=new ReportBase();
            JasperPrint jprint=(JasperPrint)JasperFillManager.fillReport(rb.getReportPath(ReportBase.FACULTY), null, mainDS);
            String output="report/facultyreport.pdf";
            JasperExportManager.exportReportToPdfFile(jprint, output);
            
            Desktop d=Desktop.getDesktop();
            d.open(new File(output));
            //System.out.println("Generated");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void generateFacultyTimeTable(){
        String tcol[]=new String[]{"time","mon","tue","wed","thus","fri","sat"};
        
        String data[][];
        
        FacultySql sql=new FacultySql(null);
        String temp[][]=sql.getAllFaculties("");
        
        Object mainData[][]=new Object[temp.length][2];
        
        for(int n=0;n<temp.length;n++){
            sql.setFID(temp[n][FacultySql.FID]);
            data=new String[7][7];
            data[0][0]="09:15 - 10:15";
            data[1][0]="10:15 - 11:15";
            data[2][0]="11:15 - 12:15";
            data[3][0]="01:00 - 02:00";
            data[4][0]="02:00 - 03:00";
            data[5][0]="03:00 - 04:00";
            data[6][0]="04:00 - 05:00";
            ArrayList<String[]> lectures;
            for(int i=2;i<=7;i++){
                lectures=sql.getLecturesForDay(i);
                int pno;
                for(int j=0;j<7;j++){
                    data[j][i-1]="N.A.";
                }
                for(String []t:lectures){
                    pno=Integer.parseInt(t[4])-1;
                    data[pno][i-1]=t[3]+" "+t[1]+"("+t[2]+")";
                }
            }
            mainData[n][0]=temp[n][FacultySql.FNAME];
            mainData[n][1]=new CustomDataSource(tcol, data);
        }
        sql.close();
        
        CustomDataSource mainDS=new CustomDataSource(new String[]{"fname","schedule_data"}, mainData);
        try{
            ReportBase rb=new ReportBase();
            JasperPrint jprint=(JasperPrint)JasperFillManager.fillReport(rb.getReportPath(ReportBase.FACULTY_TIME_TABLE), null, mainDS);
            String output="report/FacultyTimeTable.pdf";
            JasperExportManager.exportReportToPdfFile(jprint, output);
            
            Desktop d=Desktop.getDesktop();
            d.open(new File(output));
            //System.out.println("Generated");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
