/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package report.handler;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import report.datasource.CustomDataSource;
import scheduleclass.ScheduleBase;
import scheduleclass.Subject;
import sql.ClassLabManagerSql;
import sql.FacultySql;
import sql.ScheduleSql;
import sql.SubjectSql;

/**
 *
 * @author yash
 */
public class Schedule {
    
    public void generateFacultySchedule(String fids[]){
        if(fids!=null){
            int n=fids.length;
            String tcol[]=new String[]{"time","mon","tue","wed","thus","fri","sat"};
            //String tdata[][];
        
            String column[]=new String[]{"fname","schedule_data"};
            Object data[][]=new Object[n][];
        
            FacultySql sql=new FacultySql(null);
            HashMap<Integer,String> temp;
            CustomDataSource fschedule;
        
            for(int i=0;i<n;i++){
                sql.setFID(fids[i]);
                temp=sql.getWeeklySchedule();
                fschedule=new CustomDataSource(tcol, getFacultyScheduleData(temp));
                data[i][0]=sql.getData()[FacultySql.FNAME];
                data[i][1]=fschedule;
            }
        
            CustomDataSource mainDS=new CustomDataSource(tcol, data);
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
    
    public void generateTimeTable(String cls,int sem,String section,Date wef){
        String tcolumn[]=new String[]{"time","mon","tue","wed","thus","fri","sat"};
        String tdata[][]=getScheduleData(sem, section);
        CustomDataSource tsource=new CustomDataSource(tcolumn, tdata);
        
        String acolumn[]=new String[]{"scode","sname","fname","remark"};
        String adata[][]=getAllocationData(sem, section);
        CustomDataSource asource=new CustomDataSource(acolumn, adata);
        
        Map param=new HashMap();
        ReportBase rb=new ReportBase();
        param.put("classcoordinator", "Class Coordinator: "+rb.getClassCoordinator(cls, sem, section));
        param.put("class", cls+" - "+sem+" Semester SEC-'"+section+"' (PG-"+rb.getRoomNo(cls, sem, section)+")");
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        param.put("wefdate", (wef==null)?"":sdf.format(wef));
        param.put("preparedby", rb.getTimeTableCoordinator(cls, sem, section));
        param.put("course_coordinator", rb.getCourseCoordinator());
        param.put("head", rb.getHOD());
        param.put("allocationdata", asource);
        param.put("scheduledata", tsource);
        
        try{
            JasperPrint jprint=(JasperPrint)JasperFillManager.fillReport(rb.getReportPath(ReportBase.TIMETABLE), param, new JREmptyDataSource());
            
            String output="report/attendance_sheet.pdf";
            JasperExportManager.exportReportToPdfFile(jprint, output);
            
            Desktop d=Desktop.getDesktop();
            d.open(new File(output));
            //System.out.println("Generated");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private String[][] getAllocationData(int sem,String section){
        SubjectSql ssql=new SubjectSql();
        ArrayList<String> subjects=ssql.getSubjectCodes(sem);
        String adata[][]=new String[subjects.size()][];
        ArrayList<String[]> faculty;
        int index=0;
        String temp[];
        for(String scode:subjects){
            temp=new String[4];
            temp[0]=scode;
            temp[1]=ssql.getSubjectName(scode);
            
            String fac="";
            faculty=ssql.getFacultiesFor(scode,sem,section);
            int n=faculty.size();
            //if(n>1){
                for(int i=0;i<n;i++){
                    if(i==0){
                        fac=faculty.get(i)[1];
                    }else{
                        fac=fac+"/ "+faculty.get(i)[1];
                    }
                }
            //}else if(n==1){
            //    fac=faculty[1].get(0);
            //}
            temp[2]=fac;
            int type=ssql.getSubjectType(scode);
            if(type==Subject.LAB1 || type==Subject.LAB2 || type==Subject.ADD_LAB){
                ClassLabManagerSql csql=new ClassLabManagerSql();
                temp[3]=csql.getLabName(scode, sem, section);
                csql.close();
            }
            adata[index]=temp;
            index++;
        }
        ssql.close();
        return adata;
    }
    
    private String[][] getScheduleData(int sem, String section){
        String tdata[][]=new String[8][];
        ScheduleSql sql=new ScheduleSql();
        HashMap<Integer,String[]> schedule=sql.getScheduleForReport(sem, section);
        sql.close();
        String temp[];
        int index=0;
        for(int i=1;i<=7;i++){
            if(index==3){
                temp=new String[]{ScheduleBase.LUNCH_TIME,"-----","-----","LUNCH","BREAK","-----","-----"};
                tdata[index]=temp;
                index++;
            }
            temp=schedule.get(i);
            if(temp==null){
                temp=new String[7];
            }
            temp[0]=getTiming(i);
            for(int j=1;j<7;j++){
                if(temp[j]==null){
                    temp[j]="---";
                }
            }
            tdata[index]=temp;
            index++;
        }
        return tdata;
    }
    
    private String[][] getFacultyScheduleData(HashMap<Integer,String> schedule){
        String tdata[][]=new String[7][];
        String row[];
        int lecIndex;
        int rowIndex=0;
        for(int j=ScheduleBase.LEC_NO1;j<=ScheduleBase.LEC_NO7;j++){
            /*if(rowIndex==3){
                row=new String[]{ScheduleBase.LUNCH_TIME,"-----","-----","LUNCH","BREAK","-----","-----"};
                tdata[rowIndex]=row;
                rowIndex++;
            }*/
            row=new String[7];
            row[0]=getTiming(j);
            lecIndex=j;
            for(int k=1;k<=6;k++){
                row[k]=schedule.get(lecIndex);
                lecIndex=lecIndex+7;
            }
            tdata[rowIndex]=row;
            rowIndex++;
        }
        return tdata;
    }
    
    private String getTiming(int pno){
        switch(pno){
            case ScheduleBase.LEC_NO1:
                return ScheduleBase.LEC1_TIME;
            case ScheduleBase.LEC_NO2:
                return ScheduleBase.LEC2_TIME;
            case ScheduleBase.LEC_NO3:
                return ScheduleBase.LEC3_TIME;
            case ScheduleBase.LEC_NO4:
                return ScheduleBase.LEC4_TIME;
            case ScheduleBase.LEC_NO5:
                return ScheduleBase.LEC5_TIME;
            case ScheduleBase.LEC_NO6:
                return ScheduleBase.LEC6_TIME;
            case ScheduleBase.LEC_NO7:
                return ScheduleBase.LEC7_TIME;
        }
        throw new IllegalArgumentException("Timing not found.");
    }
}
