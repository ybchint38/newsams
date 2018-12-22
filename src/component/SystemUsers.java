/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package component;

import component.OptionButton.YToggleButton;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import newattendancesystem.MainFrame;
import newattendancesystem.Utility;
import panels.AdminFacultyViewPanel;
import panels.AdminViewPanel;
import panels.AssignRolePanel;
import panels.CCCompiledAttendanceView;
import panels.CompiledAttendanceView;
import panels.FacultyAttendanceView;
import panels.FacultyHome;
import panels.FacultyPanel;
import panels.FacultyToSubject;
import panels.ImportStudentPanel;
import panels.PanelInterface;
import panels.SchedulePanelDnD;
import panels.SettingPanel;
import panels.StudentMigrationPanel;
import panels.StudentPanel;
import panels.Subject;
import panels.SystemResetPanel;
import panels.ViewAttendance;
import panels.ViewPanel;
import panels.report.AdminReportPanel;
import panels.report.AttendanceSheetPanel;
import report.handler.StudentReport;
import sql.FacultySql;
import sql.SubjectSql;

/**
 *
 * @author Hemant Borade
 */
public class SystemUsers {
    
    public static final int ADMIN=2;
    public static final int FACULTY=3;
    public static final int CLASSCOORD=5;
    public static final int TIMETABLECOORD=7;
    
    private String fid;
    private int roles;
    private int bno;
    private JTabbedPane tabPanel;
    private String[][] CC;
    private String[][] TTC;
    //private String[][] faculty;
    //public int session;
    
    public Map<String,JScrollPane> activeTabs;
    
    public SystemUsers(String id,JTabbedPane t){
        fid=id;
        tabPanel=t;
        activeTabs=new HashMap();
        //bno=no;
        FacultySql sql=new FacultySql(fid);
        roles=sql.getRoles();
        sql.close();
        //Date dt=new Date();
        //int month=dt.getMonth();
        /*if(month>=7){
            session=Utility.PRESESSION;
        }else{
            session=Utility.POSTSESSION;
        }*/
    }

    public SystemUsers(String id,JTabbedPane t,int sess){
        fid=id;
        tabPanel=t;
        activeTabs=new HashMap();
        //bno=no;
        FacultySql sql=new FacultySql(fid);
        roles=sql.getRoles();
        sql.close();
        //session=sess;
    }

    public SystemUsers(){
    }

    public int getSystemUsers(){
        return roles;
    }

    public String getID(){
        return fid;
    }

    public void setOperationPanel(JPanel left){
        boolean norole=true;
        //JPanel p=new JPanel();
        //p.setBackground(Color.BLACK);
        //p.setLayout(new BorderLayout());
        //p.add(this.getProfileButton(),BorderLayout.NORTH);
        left.add(this.getProfileButton());
        
        FacultySql sql=new FacultySql(fid);
        ArrayList<String[]> sub=sql.getAllotedSubject();
        String desg=sql.getDesignation();
        sql.close();
        if(!sub.isEmpty()){
            left.add(this.getFacultyPanel());
            norole=false;
        }
        //if(roles%FacultySql.FACULTY==0){
        //    left.add(this.getFacultyPanel());
        //    norole=false;
        //}
        
        if(desg!=null && (desg.equalsIgnoreCase(Utility.DESG_HOD) || desg.equalsIgnoreCase(Utility.DESG_CRS_COORD))){
            left.add(this.getAdminPanel());
            norole=false;
        }
        
        if(roles%FacultySql.CLASSCOORD==0){
            left.add(this.getClassCoordinatorPanel());
            norole=false;
        }
        if(roles%FacultySql.TIMETABLECOORD==0){
            left.add(this.getTimeTableCoordinatorPanel());
            norole=false;
        }
        if(norole){
            JOptionPane.showMessageDialog(MainFrame.mainFrame, "No Role have been assigned to you yet.");
        }
        
    }

    private JPanel getAdminPanel(){
        ActionListener a=new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                adminFunctions(e);
            }
        };
        return CreateOptionPanel.getAdminPanel(a);
    }

    private void adminFunctions(ActionEvent e){
        YToggleButton btn=(YToggleButton)e.getSource();
        int hc=Integer.parseInt(btn.getName());
        JScrollPane scrollPane;
        if((scrollPane=activeTabs.get("A"+hc))==null){
            btn.Select();
            switch(hc){
            case 11:
            {
                ImportStudentPanel panel=new ImportStudentPanel();
                panel.indexOfTab=MainFrame.mainFrame.addTab("Import Student Database","A"+hc, panel );
            }    
            break;
            case 12:
            {
                StudentPanel panel=new StudentPanel();
                panel.indexOfTab=MainFrame.mainFrame.addTab("New Student", "A"+hc, panel );
            }
                break;
            case 13:
            {
                AdminViewPanel panel=new AdminViewPanel();
                panel.indexOfTab=MainFrame.mainFrame.addTab("Student Details","A"+hc, panel );
            }
                break;
            case 141:
            {
                ViewAttendance panel=new ViewAttendance(fid,FacultySql.ADMIN);
                panel.indexOfTab=MainFrame.mainFrame.addTab("Attendance", "A"+hc, panel );
            }
                break;
            case 142:
            {
                CompiledAttendanceView panel=new CompiledAttendanceView();
                panel.indexOfTab=MainFrame.mainFrame.addTab("Attendance", "A"+hc, panel );
            }
                break;    
            case 21:
            {
                FacultyPanel panel=new FacultyPanel(null);
                panel.indexOfTab=MainFrame.mainFrame.addTab("New Faculty", "A"+hc, panel );
            }
                break;
            case 22:
            {
                AdminFacultyViewPanel panel=new AdminFacultyViewPanel();
                panel.indexOfTab=MainFrame.mainFrame.addTab("Faculty Details", "A"+hc, panel );
            }
                break;
            case 23:
            {
                AssignRolePanel panel=new AssignRolePanel();
                //panel.indexOfTab=MainFrame.mainFrame.addTab("Assign Role", "A"+hc, panel );
                new CustomDialog(MainFrame.mainFrame, true, panel,"Faculty Role Assignment").setVisible(true);
                btn.deSelect();
            }
                break;
            case 24:
            {
                SubjectSql sql=new SubjectSql();
                String sem[]=sql.getCurrentSemester("MCA");
                if(sem!=null){
                    HashMap<String,Object> data=new HashMap<>();
                    for(int i=0;i<sem.length;i++){
                        String sec[]=sql.getCurrentSection("MCA", Integer.parseInt(sem[i]));
                        if(sec!=null){
                            HashMap<String,String> t11=new HashMap<>();
                            for(int j=0;j<sec.length;j++){
                                t11.put(sem[i]+":"+sec[j], "Section "+sec[j]);
                            }
                            data.put("Semester "+sem[i], t11);
                        }else{
                            HashMap<String,String> t11=new HashMap<>();
                            t11.put("na", "No Section Found");
                            data.put("Semester "+sem[i], t11);
                        }
                    }
                    
                    ActionListener al=new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JMenuItem item=(JMenuItem)e.getSource();
                            String val[]=item.getName().split(":");
                            if(val.length==2){
                                int sem=Integer.parseInt(val[0]);
                                new CustomDialog(MainFrame.mainFrame, true, new FacultyToSubject(sem, val[1]),"Subject Allotment").setVisible(true);
                            }
                        }
                    };
                    MainFrame.mainFrame.initializePopupMenu(btn, data, al);
                    btn.deSelect();
                }else{
                    HashMap<String,Object> data=new HashMap<>();
                    data.put("na", "No Semester Found");
                    MainFrame.mainFrame.initializePopupMenu(btn, data, null);
                    btn.deSelect();
                }
            }
                break;
            case 411:
            {
                Subject panel=new Subject("MCA",1);
                panel.indexOfTab=MainFrame.mainFrame.addTab("Semester 1", "A"+hc, panel );
            }
                break;
            case 412:
            {
                Subject panel=new Subject("MCA",2);
                panel.indexOfTab=MainFrame.mainFrame.addTab("Semester 2", "A"+hc, panel );
            }
                break;
            case 413:
            {
                Subject panel=new Subject("MCA",3);
                panel.indexOfTab=MainFrame.mainFrame.addTab("Semester 3", "A"+hc, panel );
            }
                break;
            case 414:
            {
                Subject panel=new Subject("MCA",4);
                panel.indexOfTab=MainFrame.mainFrame.addTab("Semester 4", "A"+hc, panel );
            }
                break;
            case 415:
            {
                Subject panel=new Subject("MCA",5);
                panel.indexOfTab=MainFrame.mainFrame.addTab("Semester 5", "A"+hc, panel );
            }
                break;
            case 416:
            {
                Subject panel=new Subject("MCA",6);
                panel.indexOfTab=MainFrame.mainFrame.addTab("Semester 6", "A"+hc, panel );
            }
                break;
            case 5:
            {
                //new GenerateReport(null,btn,GenerateReport.STUDENTREPORT).setVisible(true);
                //panel.indexOfTab=MainFrame.mainFrame.addTab("Student Report", "A"+hc, panel );
                MainFrame.glassPane.addPanel(new AdminReportPanel());
                btn.deSelect();
            }
                break;
            case 52:
            {
                //new GenerateReport(null,btn,GenerateReport.FACULTYREPORT).setVisible(true);
                //UnderConstruction panel=new UnderConstruction();
                //panel.indexOfTab=MainFrame.mainFrame.addTab("Faculty Report", "A"+hc, panel );
            }
                break;
            case 53:
            {
                //new GenerateReport(null,btn,GenerateReport.ATTENDANCEREPORT).setVisible(true);
                //UnderConstruction panel=new UnderConstruction();
                //panel.indexOfTab=MainFrame.mainFrame.addTab("Attendance Report", "A"+hc, panel );
            }
                break;
            case 6:
            {
                SettingPanel panel=new SettingPanel();
                panel.indexOfTab=MainFrame.mainFrame.addTab("Settings", "A"+hc, panel );
            }
                break;
            case 7:
            {
                StudentMigrationPanel panel=new StudentMigrationPanel();
                panel.indexOfTab=MainFrame.mainFrame.addTab("Student Migration", "A"+hc, panel );
            }
                break;
            case 8:
            {
                new CustomDialog(MainFrame.mainFrame, true, new SystemResetPanel(), "Preparing for new Session").setVisible(true);
                btn.deSelect();
            }
                break;
                
            default:
                throw new UnsupportedOperationException();
            }
        }else{
            JPanel panel=(JPanel)scrollPane.getViewport().getComponent(0);
            PanelInterface pi=(PanelInterface)panel.getComponent(0);
            if(pi.destroy()){
                btn.deSelect();
                tabPanel.remove(scrollPane);
                activeTabs.remove("A"+hc);
            }else{
                btn.Select();
            }
        }
    }

    private JPanel getClassCoordinatorPanel(){
        //String val[][]={{"1","A","B"},{"2","A"},{"3","B"},{"4","A","B","C"}};
        //CC=val;
        FacultySql sql=new FacultySql(fid);
        CC=sql.getClasses(Utility.CLASSCOORD);
        sql.close();
        ActionListener al=new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                classCoordinatorFuctions(e);
            }
        };

        return (CreateOptionPanel.getClassCoordinatorPanel(CC, al));
    }

    private void classCoordinatorFuctions(ActionEvent e){
        YToggleButton btn=(YToggleButton)e.getSource();
        String name=btn.getName();
        JScrollPane scrollPane;
        
        int i=0,j=0,sem=0,opcode=0;
        String section="";
        try{
            i=Integer.parseInt(name.charAt(0)+"")-1;
            j=Integer.parseInt(name.charAt(1)+"");
            sem=Integer.parseInt(CC[i][0]);
            section=CC[i][j];
            opcode=Integer.parseInt(name.substring(2));
        }catch(Exception ex){
            opcode=Integer.parseInt(name);
        }
        if((scrollPane=activeTabs.get("CC"+opcode))==null){
            btn.Select();
            switch(opcode){
                case 1://View Student List
                    ViewPanel panel1=new ViewPanel(sem,section,ViewPanel.STUDENTLISTVIEW);
                    panel1.indexOfTab=MainFrame.mainFrame.addTab("Student List", "CC"+opcode, panel1);
                    break;
                case 2://View Attendance
                    ViewPanel panel2=new ViewPanel(sem,section,ViewPanel.STUDENTATTENDANCE);
                    panel2.indexOfTab=MainFrame.mainFrame.addTab("Attendance List", "CC"+opcode, panel2);
                    break;
                case 3://Leave Details
                    ViewPanel panel=new ViewPanel(sem,section,ViewPanel.STUDENTLEAVE_ADD);
                    panel.indexOfTab=MainFrame.mainFrame.addTab("Add Leave", "CC"+opcode, panel);
                    break;
                case 41://Student List
                {    //new GenerateReport(null,btn,GenerateReport.ATTENDANCEREPORT).setVisible(true);
                    final int semf=sem;
                    final String sectionf=section;
                    final YToggleButton btnf=btn;
                    
                    new Thread(){

                        @Override
                        public void run() {
                            
                            String title=JOptionPane.showInputDialog(MainFrame.mainFrame, "Please enter heading/title:");
                            StudentReport sr=new StudentReport();
                            MainFrame.mainFrame.startProcessing("Generating");
                            btnf.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                            //btnf.setEnabled(false);
                            sr.studentList("MCA", semf, sectionf,title);
                            btnf.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                            //btnf.setEnabled(true);
                            btnf.deSelect();
                            MainFrame.mainFrame.stopProcessing();
                        }
                    }.start();
                    break;
                }
                case 42://Student Report
                {
                    //new GenerateReport(null,btn,GenerateReport.ATTENDANCEREPORT).setVisible(true);
                    final int semf=sem;
                    final String sectionf=section;
                    final YToggleButton btnf=btn;
                    
                    new Thread(){

                        @Override
                        public void run() {
                            
                            StudentReport sr=new StudentReport();
                            MainFrame.mainFrame.startProcessing("Generating");
                            btnf.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                            //btnf.setEnabled(false);
                            sr.studentReport("MCA", semf, sectionf);
                            btnf.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                            //btnf.setEnabled(true);
                            btnf.deSelect();
                            MainFrame.mainFrame.stopProcessing();
                        }
                    }.start();
                    //StudentReport sr=new StudentReport();
                    //sr.studentReport("MCA", sem, section);
                    //btn.deSelect();
                    break;
                }
                case 5:
                    CCCompiledAttendanceView view=new CCCompiledAttendanceView(fid);
                    view.indexOfTab=MainFrame.mainFrame.addTab("Attendance Summary", "CC"+opcode, view);
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }else{
            JPanel panel=(JPanel)scrollPane.getViewport().getComponent(0);
            PanelInterface pi=(PanelInterface)panel.getComponent(0);
            if(pi.destroy()){
                btn.deSelect();
                tabPanel.remove(scrollPane);
                activeTabs.remove("CC"+opcode);
            }else{
                btn.Select();
            }
        }
    }

    private JPanel getFacultyPanel(){
        //String val[][]={{"1","A","B"},{"2","A"},{"3","B"},{"4","A","B","C"}};
        
        FacultyHome panel2=new FacultyHome();
        panel2.indexOfTab=MainFrame.mainFrame.addTab("Home", "", panel2);
        ActionListener al=new ActionListener(){public void actionPerformed(ActionEvent e) {facultyFuctions(e);}};
        return (CreateOptionPanel.getFacultyPanel(null, al));
    }

    private void facultyFuctions(ActionEvent e){
        YToggleButton btn=(YToggleButton)e.getSource();
        String name=btn.getName();
        JScrollPane scrollPane;
        if(name.equalsIgnoreCase("1")){
            if((scrollPane=activeTabs.get("F"+91))==null){
                btn.Select();
                FacultyAttendanceView panel=new FacultyAttendanceView(fid);
                panel.indexOfTab=MainFrame.mainFrame.addTab("Attendance", "F"+91, panel);
            }else{
                JPanel panel=(JPanel)scrollPane.getViewport().getComponent(0);
                PanelInterface pi=(PanelInterface)panel.getComponent(0);
                if(pi.destroy()){
                    btn.deSelect();
                    tabPanel.remove(scrollPane);
                    activeTabs.remove("F"+91);
                }else{
                    btn.Select();
                }
            }
        }else if(name.equalsIgnoreCase("2")){
            if((scrollPane=activeTabs.get("F"+92))==null){
                btn.Select();
                FacultySchedule panel=new FacultySchedule(fid);
                panel.indexOfTab=MainFrame.mainFrame.addTab("Schedule", "F"+92, panel);
            }else{
                JPanel panel=(JPanel)scrollPane.getViewport().getComponent(0);
                PanelInterface pi=(PanelInterface)panel.getComponent(0);
                if(pi.destroy()){
                    btn.deSelect();
                    tabPanel.remove(scrollPane);
                    activeTabs.remove("F"+92);
                }else{
                    btn.Select();
                }
            }
        }else{
            btn.Select();
            new CustomDialog(MainFrame.mainFrame, true, new AttendanceSheetPanel(fid),"Attendance Sheet").setVisible(true);
            btn.deSelect();
        }
    }

    private JPanel getTimeTableCoordinatorPanel(){
        //String val[][]={{"1","A","B"},{"2","A"},{"3","B"},{"4","A","B","C"}};
        FacultySql sql=new FacultySql(fid);
        TTC=sql.getClasses(FacultySql.TIMETABLECOORD);
        sql.close();
        ActionListener al=new ActionListener(){public void actionPerformed(ActionEvent e) {TTCFuctions(e);}};
        return (CreateOptionPanel.getTimeTableCoordinatorPanel(TTC, al));
    }

    private void TTCFuctions(ActionEvent e){
        YToggleButton btn=(YToggleButton)e.getSource();
        String name=btn.getName();
        int hcode=Integer.parseInt(name);
        int semIndex=((hcode/10)%10)-1;
        int secIndex=hcode%10;
        int sem=Integer.parseInt(TTC[semIndex][0]);
        char sec=TTC[semIndex][secIndex].charAt(0);
        JScrollPane scrollPane;
        //SubjectSql sql=new SubjectSql();
        //boolean exist=sql.isScheduleExists("MCA", sem, sec+"");
        //sql.close();

        if((scrollPane=activeTabs.get("TC"+hcode))==null){
            SchedulePanelDnD panel=new SchedulePanelDnD(sem, sec+"");
            /*if(exist){
                panel=new SchedulePanel("MCA",sem,sec,false);
            }else{
                panel=new SchedulePanel("MCA",sem,sec,true);
            }*/
            btn.Select();
            new CustomDialog(MainFrame.mainFrame, true, panel,"Create Schedule").setVisible(true);
            btn.deSelect();
            //panel.indexOfTab=MainFrame.mainFrame.addTab("Manage Schedule", "TC"+hcode, panel);
        }else{
            JPanel panel=(JPanel)scrollPane.getViewport().getComponent(0);
            PanelInterface pi=(PanelInterface)panel.getComponent(0);
            if(pi.destroy()){
                btn.deSelect();
                tabPanel.remove(scrollPane);
                activeTabs.remove("TC"+hcode);
            }else{
                btn.Select();
            }
        }
    }

    private JPanel getProfileButton(){
        ActionListener al=new ActionListener(){
        public void actionPerformed(ActionEvent e) {profileFunction(e);}};
        return (CreateOptionPanel.getProfileButton(al));
    }

    private void profileFunction(ActionEvent e){
        YToggleButton btn=(YToggleButton)e.getSource();
        if(btn.isSelected()){
            btn.Select();
            FacultyPanel panel=new FacultyPanel(fid,null);
            panel.indexOfTab=MainFrame.mainFrame.addTab("My Profile", "PF"+1, panel );
        }else{
            JScrollPane scrollPane=activeTabs.get("PF"+1);
            JPanel panel=(JPanel)scrollPane.getViewport().getComponent(0);
            PanelInterface pi=(PanelInterface)panel.getComponent(0);
            if(pi.destroy()){
                btn.deSelect();
                tabPanel.remove(scrollPane);
                activeTabs.remove("PF"+1);
            }else{
                btn.Select();
            }
        }
    }
}
