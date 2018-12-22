/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package report.handler;

import java.io.InputStream;
import java.util.ArrayList;
import newattendancesystem.Utility;
import sql.ClassLabManagerSql;
import sql.FacultySql;

/**
 *
 * @author yash
 */
public class ReportBase {
    
    public static final String FACULTY="faculty.jasper";
    public static final String TIMETABLE="TimeTable.jasper";
    public static final String ATTNDNC_SHEET="attendance_sheet.jasper";
    public static final String ATTNDNC_SHEET_MLTPL="attendance_sheet_multiple.jasper";
    public static final String ATTNDNC_CMPL_2="attndnc_compile_2.jasper";
    public static final String ATTNDNC_CMPL_3="attndnc_compile_3.jasper";
    public static final String FACULTY_TIME_TABLE="faculty_time_table.jasper";
    public static final String STUDENT_LEAVE_REPORT="student_leave_report.jasper";
    public static final String ATTNDNC_DAILY="attendance_daily.jasper";
    public static final String STUDENT_LIST ="student_list.jasper";
    public static final String STUDENT_REPORT ="student.jasper";
    
    public String getClassCoordinator(String cls,int sem,String section){
        String val="";
        FacultySql sql=new FacultySql(null);
        ArrayList<String> fname=sql.getFacultyName(sem, section, FacultySql.CLASSCOORD);
        int n=fname.size();
        for(int i=0;i<n;i++){
            if(i==0){
                val= fname.get(i);
            }else{
                val="/ "+fname.get(i);
            }
        }
        sql.close();
        return val;
    }
    
    public String getCourseCoordinator(){
        String val="";
        FacultySql sql=new FacultySql(null);
        ArrayList<String> fname=sql.getFacultyName(Utility.DESG_CRS_COORD);
        sql.close();
        if(fname.size()>=1){
            val=fname.get(0);
        }
        return val;
    }
    
    public String getTimeTableCoordinator(String cls,int sem,String section){
        String val="";
        FacultySql sql=new FacultySql(null);
        ArrayList<String> fname=sql.getFacultyName(sem, section, FacultySql.TIMETABLECOORD);
        int n=fname.size();
        for(int i=0;i<n;i++){
            if(i==0){
                val= fname.get(i);
            }else{
                val=val+ "/ "+fname.get(i);
            }
        }
        sql.close();
        return val;
    }
    
    public String getHOD(){
        String val="";
        FacultySql sql=new FacultySql(null);
        ArrayList<String> fname=sql.getFacultyName(Utility.DESG_HOD);
        sql.close();
        if(fname.size()>=1){
            val=fname.get(0);
        }
        return val;
    }
    
    public String getRoomNo(String cls,int sem,String section){
        String val="";
        ClassLabManagerSql sql=new ClassLabManagerSql();
        val=sql.getRoomNo(cls, sem, section)+"";
        sql.close();
        return val;
    }
    
    public String getReportPath(String rpt){
        String path="report/source/"+rpt;
        //path=getClass().getResource(path).getPath();
        return path;
        //InputStream is=getClass().getResourceAsStream(path);
        //return is;
    }
    
    
}
