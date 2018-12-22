/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package report.datasource;

import java.awt.Desktop;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author yash
 */
public class TestReport {
    
    TestReport(){
    
    }
    
    void testHindiReport(){
        String col[]={"names"};
        
        String data[][]={{"\u092F\u0936"},{"\u0928\u0930\u0947\u0929\u0926\u0931"}};
        
        CustomDataSource ds=new CustomDataSource(col, data);
        try{
            
            //"E:\Projects\VI sem\NewAttendanceSystem\build\classes\report\source\attendance_sheet.jasper"
//            /getClass().getResource("/report/source/attendance_sheet.jasper").getPath()
            //String file=getClass().getResource("/report/source/attendance_sheet.jasper").getPath();
            //file=file.replaceAll("%20", " ");
            JasperPrint jprint=(JasperPrint)JasperFillManager.fillReport("src/report/source/hindi_report.jasper", null, ds);
            String output="report/hindi.pdf";
            //JasperExportManager.exportReportToPdfFile(jprint, output);
            JasperExportManager.exportReportToHtmlFile(jprint, "report/hindi.html");
                    
            /*File destFile = new File("report/hindi_rpt.docx");
            
            JRDocxExporter exporter = new JRDocxExporter();
		
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jprint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
		
            exporter.exportReport();*/
            
            Desktop d=Desktop.getDesktop();
            //d.open(new File(output));
            
            System.out.println("Generated");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    void testStudentAttendanceSheet(){
        String col[]=new String[]{"sno","sname"};
        
        String data[][]=new String[][]{
            {"1.","Amit"},
            {"2.","Amit"},
            {"3.","Amit"},
            {"4.","Amit"},
            {"5.","Amit"},
            {"6.","Amit"},
            {"7.","Amit"},
            {"8.","Amit"},
            {"9.","Amit"},
            {"10.","Amit"},
            {"11.","Amit"},
            {"12.","Amit"},
            {"13.","Amit"},
            {"14.","Amit"},
            {"15.","Amit"},
            {"16.","Amit"},
            {"17.","Amit"},
            {"18.","Amit"},
            {"19.","Amit"},
            {"20.","Amit"},
            {"21.","Amit"},
            {"22.","Amit"},
            {"23.","Amit"},
            {"24.","Amit"},
            {"25.","Amit"},
            {"26.","Amit"},
            {"27.","Amit"},
            {"28.","Amit"},
            {"29.","Amit"},
            {"30.","Amit"},
            {"31.","Amit"},
            {"32.","Amit"},
            {"33.","Amit"},
            {"34.","Amit"},
            {"35.","Amit"},
            {"36.","Amit"},
            {"37.","Amit"},
            {"38.","Amit"}
        };
        
        CustomDataSource ds=new CustomDataSource(col, data);
        
        HashMap param=new HashMap();
        param.put("session", "Session: Jan-June 2014");
        param.put("class", "MCA IV 'C'");
        param.put("faculty", "Mrs Ekta Agrawal");
        param.put("date", "Date: 24/03/2014");
        param.put("subject", "MCA 402");
        param.put("time", "09:15-10:00");
        
        try{
            
            //"E:\Projects\VI sem\NewAttendanceSystem\build\classes\report\source\attendance_sheet.jasper"
//            /getClass().getResource("/report/source/attendance_sheet.jasper").getPath()
            String file=getClass().getResource("/report/source/attendance_sheet.jasper").getPath();
            file=file.replaceAll("%20", " ");
            JasperPrint jprint=(JasperPrint)JasperFillManager.fillReport(file, param, ds);
            String output="report/attendance_sheet.pdf";
            JasperExportManager.exportReportToPdfFile(jprint, output);
            
            Desktop d=Desktop.getDesktop();
            d.open(new File(output));
            System.out.println("Generated");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    void testStudentLeaveReport(){
        String col[]=new String[]{"sno","a_date","f_date","t_date","reason"};
        
        String data1[][]=new String[][]{{"1.","01/02/2013","02/02/2013","05/02/2013","ihgufdkjhg kjfdhg dfkjhg \nfkhg fdkjh fdkjhg hdg kjfdg kjdfg kjdfhg dfkjhg dfhgfd kjdfhg kjfg"}};
        
        String data2[][]=new String[][]{{"1.","01/02/2013","02/02/2013","05/02/2013","ihgufdkjhg kjfdhg dfkjhg \nfkhg fdkjh fdkjhg hdg kjfdg kjdfg kjdfhg dfkjhg dfhgfd kjdfhg kjfg"}};
        
        CustomDataSource ds1=new CustomDataSource(col, data1);
        CustomDataSource ds2=new CustomDataSource(col, data2);
        
        CustomDataSource main=new CustomDataSource(new String[]{"semester","leave_data"}, new Object[][]{{"Semester I",ds1},{"Semester II",ds2}});
        
        HashMap mainParam=new HashMap();
        mainParam.put("sname", "Yash Borade");
        mainParam.put("eno", "0807CA111088");
        
        try{
            JasperPrint jprint=(JasperPrint)JasperFillManager.fillReport("report/source/student_leave_report.jasper", mainParam, main);
            String output="report/student_leave_report.pdf";
            JasperExportManager.exportReportToPdfFile(jprint, output);
            
            Desktop d=Desktop.getDesktop();
            d.open(new File(output));
            System.out.println("Generated");
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    
    void testAttendance_Dailly(){
        String data[][]=new String[][]{
            {"1.","Abhishek","P","A","L","3","99.99",null,null,null,null,null,null,null,null,null,null}
        };
        
        String col[]=new String[]{"sno","sname","date_1_val","date_2_val","date_3_val","date_4_val","date_5_val","date_6_val","date_7_val","date_8_val","date_9_val","date_10_val","date_11_val","date_12_val","date_13_val","date_14_val","date_15_val"};
        
        CustomDataSource sub=new CustomDataSource(col, data);
        
        HashMap mainParam=new HashMap();
        mainParam.put("class", "MCA IV 'A'");
        mainParam.put("subject", "MCA 402");
        mainParam.put("duration", "16/04/2014-20/04/2014");
        mainParam.put("faculty", "Mrs Ekta Agrawal / Mr S R Gupta / Mr Kamlesh Malpani");
        
        HashMap subParam=new HashMap();
        subParam.put("date_1", "01/ 03");
        subParam.put("date_2", "02/ 03");
        subParam.put("date_3", "03/ 03");
        
        CustomDataSource main=new CustomDataSource(new String[]{"subreport_parameter","subreport_data"}, new Object[][]{{subParam,sub}});
        
        try{
            JasperPrint jprint=(JasperPrint)JasperFillManager.fillReport("src/report/source/attendance_daily.jasper", mainParam, main);
            String output="report/attendance_daily.pdf";
            JasperExportManager.exportReportToPdfFile(jprint, output);
            
            //File destFile = new File("attendance_daily.docx");
		
            /*JRXlsxExporter exporter = new JRXlsxExporter();
		
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jprint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
            exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		
            exporter.exportReport();*/
            
            /*JRDocxExporter exporter = new JRDocxExporter();
		
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jprint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
		
            exporter.exportReport();*/

            
            Desktop d=Desktop.getDesktop();
            d.open(new File(output));
            System.out.println("Generated");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    void testAttendance_Compile3(){
        
        String data[][]=new String[][]{
            {"1","Abhishek Jagtap","22","12","17","19","17","87","71.31","11","13","4","28","42.42","56.87"},
            {"2","Abhishek Jagtap","22","12","17","19","17","87","71.31","11","13","4","28","42.42","56.87"},
            {"3","Abhishek Jagtap","22","12","17","19","17","87","71.31","11","13","4","28","42.42","56.87"},
            {"4","Abhishek Jagtap","22","12","17","19","17","87","71.31","11","13","4","28","42.42","56.87"}
        };
        
        String col[]=new String[]{"sno","sname","s1","s2","s3","s4","s5","theo_total","theo_per","s6","s7","s8","prac_total","prac_per","total_per"};
        
        CustomDataSource ds=new CustomDataSource(col, data);
        
        Map param=new HashMap();
        
        param.put("head", "Dr Kshama Paithankar");
        param.put("class_coordinator", "Ms Shweta Pawar");
        param.put("class", "MCA - IV 'A'");
        param.put("duration", "January,14 2013 to February,28 2013");
        param.put("s1_name", "MCA-401");
        param.put("s2_name", "MCA-402");
        param.put("s3_name", "MCA-403");
        param.put("s4_name", "MCA-404");
        param.put("s5_name", "MCA-405");
        param.put("s6_name", "405 Lab");
        param.put("s7_name", "406 Lab");
        param.put("s8_name", "407 Lab");
        
        param.put("s1_total", "26");
        param.put("s2_total", "26");
        param.put("s3_total", "21");
        param.put("s4_total", "28");
        param.put("s5_total", "21");
        param.put("theo_total", "123");
        param.put("s6_total", "15");
        param.put("s7_total", "42");
        param.put("s8_total", "9");
        param.put("prac_total", "66");
        
        try{
        JasperPrint jprint=(JasperPrint)JasperFillManager.fillReport("report/source/attndnc_compile_3.jasper", param, ds);
        String output="report/attndnc_compile_3.pdf";
        JasperExportManager.exportReportToPdfFile(jprint, output);
            
        Desktop d=Desktop.getDesktop();
        d.open(new File(output));
        System.out.println("Generated");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    void testAttendance_Compile2(){
        
        String data[][]=new String[][]{
            {"1","Abhishek Jagtap","22","12","17","19","17","87","71.31","11","13","28","42.42","56.87"},
            {"2","Abhishek Jagtap","22","12","17","19","17","87","71.31","11","13","28","42.42","56.87"},
            {"3","Abhishek Jagtap","22","12","17","19","17","87","71.31","11","13","28","42.42","56.87"},
            {"4","Abhishek Jagtap","22","12","17","19","17","87","71.31","11","13","28","42.42","56.87"}
        };
        
        String col[]=new String[]{"sno","sname","s1","s2","s3","s4","s5","theo_total","theo_per","s6","s7","prac_total","prac_per","total_per"};
        
        CustomDataSource ds=new CustomDataSource(col, data);
        
        Map param=new HashMap();
        
        param.put("head", "Dr Kshama Paithankar");
        param.put("class_coordinator", "Ms Shweta Pawar");
        param.put("class", "MCA - IV 'A'");
        param.put("duration", "January,14 2013 to February,28 2013");
        param.put("s1_name", "MCA-401");
        param.put("s2_name", "MCA-402");
        param.put("s3_name", "MCA-403");
        param.put("s4_name", "MCA-404");
        param.put("s5_name", "MCA-405");
        param.put("s6_name", "405 Lab");
        param.put("s7_name", "406 Lab");
        
        param.put("s1_total", "26");
        param.put("s2_total", "26");
        param.put("s3_total", "21");
        param.put("s4_total", "28");
        param.put("s5_total", "21");
        param.put("theo_total", "123");
        param.put("s6_total", "15");
        param.put("s7_total", "42");
        param.put("prac_total", "66");
        
        try{
        JasperPrint jprint=(JasperPrint)JasperFillManager.fillReport("report/source/attndnc_compile_2.jasper", param, ds);
        String output="report/attndnc_compile_2.pdf";
        JasperExportManager.exportReportToPdfFile(jprint, output);
            
        Desktop d=Desktop.getDesktop();
        d.open(new File(output));
        System.out.println("Generated");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    void testTimeTable(){
        
        String tdata[][]=new String[][]{
            {"09:15-10:15","MCA-203","MCA-203","MCA-205","MCA-203","MCA-205","Technical Grooming Class"},
            {"10:15-11:15","MCA-203","MCA-203","MCA-205","MCA-203","MCA-205","Technical Grooming Class"},
            {"11:15-12:15","MCA-203","MCA-203","MCA-205","MCA-203","Technical Grooming Class","MCA-205"},
            {"12:15-01:00","-----","-----","LUNCH","BREAK","-----","-----"},
            {"01:00-02:00","MCA-203","MCA-203","MCA-205","Technical Grooming Class","MCA-205","MCA-205"},
            {"02:00-03:00","MCA-203","MCA-203","Technical Grooming Class","MCA-203","MCA-205","MCA-205"},
            {"03:00-04:00","MCA-203","Technical Grooming Class","MCA-205","MCA-203","MCA-205","MCA-205"},
            {"04:00-05:00","Technical Grooming Class","MCA-203","MCA-205","MCA-203","MCA-205","MCA-205"}
        };
        
        String tcol[]=new String[]{"time","mon","tue","wed","thus","fri","sat"};
        
        CustomDataSource tsource=new CustomDataSource(tcol, tdata);
        
        String adata[][]=new String[][]{
            {"MCA-201","Operating Systems","Dr Kshama Paithankar"},
            {"MCA-202","Database Management System","Mr Manish Sharma"},
            {"MCA-203","Data Structure","Mrs Bhuvaneshwari Chouhan"},
            {"MCA-204","Computer Oriented Numerical and Statistical Methods","Dr Jayesh Tiwari"},
            {"MCA-205","Accounting and Management Control","Ms Ruchi Gupta"},
            {"MCA-206Lab","Programming Lab in RDBMS","Mr Manish Sharma/ Mr Someshwar Joshi/ Mr Gopal Phoolkar"},
            {"MCA-207Lab","Data Structure Lab","Mrs Bhuvaneshwari Chouhan/ Mr Kamlesh Malpani"},
            {"","Personality Development Class(PD Class)","Ms Vanita Chouhan"},
            {"","Technical Grooming Class Additional","Mrs Shweta Pawar/ Mrs Geetanjali Gupta"},
        };
        
        String acol[]=new String[]{"scode","sname","fname","remark"};
        
        CustomDataSource asource=new CustomDataSource(acol, adata);
        
        Map param=new HashMap();
        
        param.put("classcoordinator", "Class Coordinator: Mr Abhishek Khare");
        param.put("class", "MCA - II Semster SEC-'A' (PG-09)");
        param.put("wefdate", "18/02/2013");
        param.put("preparedby", "Mrs Ekta Agrawal");
        param.put("course_coordinator", "Mr Abhishek Khare");
        param.put("head", "Dr Kshama Paithankar");
        param.put("allocationdata", asource);
        param.put("scheduledata", tsource);
        
        try{
        JasperPrint jprint=(JasperPrint)JasperFillManager.fillReport("report/source/TimeTable.jasper", param, new JREmptyDataSource());
        String output="report/TimeTable.pdf";
        JasperExportManager.exportReportToPdfFile(jprint, output);
            
        Desktop d=Desktop.getDesktop();
        d.open(new File(output));
        System.out.println("Generated");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    void testFacultyTimeTable(){
        
        String tdata[][]=new String[][]{
            {"09:15-10:15","MCA-203","MCA-203","MCA-205","MCA-203","MCA-205",""},
            {"10:15-11:15","MCA-203","MCA-203","MCA-205","MCA-203","MCA-205",""},
            {"11:15-12:15","MCA-203","MCA-203","MCA-205","MCA-203","","MCA-205"},
            {"12:15-01:00","-----","-----","LUNCH","BREAK","-----","-----"},
            {"01:00-02:00","MCA-203","MCA-203","MCA-205","","MCA-205","MCA-205"},
            {"02:00-03:00","MCA-203","MCA-203","","MCA-203","MCA-205","MCA-205"},
            {"03:00-04:00","MCA-203","","MCA-205","MCA-203","MCA-205","MCA-205"},
            {"04:00-05:00","","MCA-203","MCA-205","MCA-203","MCA-205","MCA-205"}
        };
        
        String tcol[]=new String[]{"time","mon","tue","wed","thus","fri","sat"};
        
        CustomDataSource tsource1=new CustomDataSource(tcol, tdata);
        
        String tdata2[][]=new String[][]{
            {"09:15-10:15","MCA-203","MCA-203","MCA-205","MCA-203","MCA-205","Technical Grooming Class"},
            {"10:15-11:15","MCA-203","MCA-203","MCA-205","MCA-203","MCA-205","Technical Grooming Class"},
            {"11:15-12:15","MCA-203","MCA-203","MCA-205","MCA-203","Technical Grooming Class","MCA-205"},
            {"12:15-01:00","-----","-----","LUNCH","BREAK","-----","-----"},
            {"01:00-02:00","MCA-203","MCA-203","MCA-205","Technical Grooming Class","MCA-205","MCA-205"},
            {"02:00-03:00","MCA-203","MCA-203","Technical Grooming Class","MCA-203","MCA-205","MCA-205"},
            {"03:00-04:00","MCA-203","Technical Grooming Class","MCA-205","MCA-203","MCA-205","MCA-205"},
            {"04:00-05:00","Technical Grooming Class","MCA-203","MCA-205","MCA-203","MCA-205","MCA-205"}
        };
        
        String tcol2[]=new String[]{"time","mon","tue","wed","thus","fri","sat"};
        
        CustomDataSource tsource2=new CustomDataSource(tcol, tdata);
                
        String tdata3[][]=new String[][]{
            {"09:15-10:15","MCA-203","MCA-203","MCA-205","MCA-203","MCA-205","Technical Grooming Class"},
            {"10:15-11:15","MCA-203","MCA-203","MCA-205","MCA-203","MCA-205","Technical Grooming Class"},
            {"11:15-12:15","MCA-203","MCA-203","MCA-205","MCA-203","Technical Grooming Class","MCA-205"},
            {"12:15-01:00","-----","-----","LUNCH","BREAK","-----","-----"},
            {"01:00-02:00","MCA-203","MCA-203","MCA-205","Technical Grooming Class","MCA-205","MCA-205"},
            {"02:00-03:00","MCA-203","MCA-203","Technical Grooming Class","MCA-203","MCA-205","MCA-205"},
            {"03:00-04:00","MCA-203","Technical Grooming Class","MCA-205","MCA-203","MCA-205","MCA-205"},
            {"04:00-05:00","Technical Grooming Class","MCA-203","MCA-205","MCA-203","MCA-205","MCA-205"}
        };
        
        String tcol3[]=new String[]{"time","mon","tue","wed","thus","fri","sat"};
        
        CustomDataSource tsource3=new CustomDataSource(tcol, tdata);
        
        CustomDataSource main=new CustomDataSource(new String[]{"fname","schedule_data"}, new Object[][]{{"Mrs Ekta Agrawal",tsource1},{"Mrs Shweta Pawar",tsource2},{"Mr Kamlesh Malpani",tsource3} });
        
        try{
        JasperPrint jprint=(JasperPrint)JasperFillManager.fillReport("report/source/faculty_time_table.jasper", null, main);
        String output="report/FacultyTimeTable.pdf";
        JasperExportManager.exportReportToPdfFile(jprint, output);
            
        Desktop d=Desktop.getDesktop();
        d.open(new File(output));
        System.out.println("Generated");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String arg[]){
        new TestReport().testAttendance_Dailly();
    }
    
}
