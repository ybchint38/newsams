/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduleclass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JOptionPane;
import newattendancesystem.MainFrame;
import sql.ScheduleSql;
import sql.SubjectSql;

/**
 *
 * @author yash
 */
public class ScheduleBase {
    
    public static final int DAILY_LEC=7;
    
    public static final int M_LEC1=1;
    public static final int M_LEC2=2;
    public static final int M_LEC3=3;
    public static final int M_LEC4=4;
    public static final int M_LEC5=5;
    public static final int M_LEC6=6;
    public static final int M_LEC7=7;
    
    public static final int T_LEC1=8;
    public static final int T_LEC2=9;
    public static final int T_LEC3=10;
    public static final int T_LEC4=11;
    public static final int T_LEC5=12;
    public static final int T_LEC6=13;
    public static final int T_LEC7=14;
    
    public static final int W_LEC1=15;
    public static final int W_LEC2=16;
    public static final int W_LEC3=17;
    public static final int W_LEC4=18;
    public static final int W_LEC5=19;
    public static final int W_LEC6=20;
    public static final int W_LEC7=21;
    
    public static final int TH_LEC1=22;
    public static final int TH_LEC2=23;
    public static final int TH_LEC3=24;
    public static final int TH_LEC4=25;
    public static final int TH_LEC5=26;
    public static final int TH_LEC6=27;
    public static final int TH_LEC7=28;
    
    public static final int F_LEC1=29;
    public static final int F_LEC2=30;
    public static final int F_LEC3=31;
    public static final int F_LEC4=32;
    public static final int F_LEC5=33;
    public static final int F_LEC6=34;
    public static final int F_LEC7=35;
    
    public static final int S_LEC1=36;
    public static final int S_LEC2=37;
    public static final int S_LEC3=38;
    public static final int S_LEC4=39;
    public static final int S_LEC5=40;
    public static final int S_LEC6=41;
    public static final int S_LEC7=42;
    
    public static final String LEC1_TIME="09:15-10:15";
    public static final String LEC2_TIME="10:15-11:15";
    public static final String LEC3_TIME="11:15-12:15";
    public static final String LUNCH_TIME="12:15-01:00";
    public static final String LEC4_TIME="01:00-02:00";
    public static final String LEC5_TIME="02:00-03:00";
    public static final String LEC6_TIME="03:00-04:00";
    public static final String LEC7_TIME="04:00-05:00";
    
    public static final String LEC_TIME[]=new String[]{"","09:15-10:15","10:15-11:15","11:15-12:15","01:00-02:00","02:00-03:00","03:00-04:00","04:00-05:00"};
    
    public static final int LEC_NO1=1;
    public static final int LEC_NO2=2;
    public static final int LEC_NO3=3;
    public static final int LEC_NO4=4;
    public static final int LEC_NO5=5;
    public static final int LEC_NO6=6;
    public static final int LEC_NO7=7;
    
    public static final int MONDAY=0;
    public static final int TUESDAY=1;
    public static final int WEDNESDAY=2;
    public static final int THURSDAY=3;
    public static final int FRIDAY=4;
    public static final int SATURDAY=5;
    
    public static final short TIME_SLOT1=0;//"09:00-04:00"
    public static final short TIME_SLOT2=1;//"10:00-05:00";
    
    public static final String SLOT_TIME[]=new String[]{"09:00-04:00","10:00-05:00"};
    
    private static ScheduleBase sb;
    
    private Subject subjectInstance;
    private int semester;
    private String section;
    private boolean isExist;
    
    private HashMap<Integer,String> schedule; // lecture id to Subject
    private HashMap<String,Integer> subLectureCnt;  //Subject to No of lecture
    private HashMap<String,FacultySchedule[]> faculty;// Subject to facultyMap
    private HashMap<String,FacultySchedule> facName;// Name to facultyMap
    
    private ScheduleBase(int semester, String section) throws Exception{
        this.semester = semester;
        this.section = section;
        subjectInstance=Subject.getInstance(semester,section);
        load();
    }
    
    public static ScheduleBase getInstance(int semester,String section) throws Exception{
        if(sb==null || (semester==sb.semester && section.equals(sb.section)) ){
            sb=new ScheduleBase(semester, section);
        }
        return sb;
    }
    
    public void destroy(){
        sb=null;
        FacultySchedule.destroy();
        Subject.destroy();
    }
    
    private void load() throws Exception{
        ScheduleSql ss=new ScheduleSql();
        isExist=ss.isScheduleExists(semester, section);
        schedule=ss.getSchedule(semester, section);
        subjectInstance.load(semester,section);
        initialize();
    }
    
    public void reIntialize() throws Exception{
        load();
    }
    
    private void initialize() throws Exception {
        subLectureCnt=new HashMap<>();
        faculty=new HashMap<>();
        facName=new HashMap<>();
                
        SubjectSql sql=new SubjectSql();
        ArrayList<String> sub=subjectInstance.getSubjectCodes();
        if(sub.isEmpty()){
            throw new Exception("No subject found.");
        }
        ArrayList<String[]> fac;
        for(String s:sub){
            //System.out.println("subjects: "+s);
            subLectureCnt.put(s, 0);
            fac=sql.getFacultiesFor(s, semester, section);
            FacultySchedule fs[];
            //if(Subject.isTheory(s)){
            //    fs=new FacultySchedule[fac.size()];
            //}else{
            //System.out.println(fac.size());
            fs=new FacultySchedule[fac.size()];
            //}
            //System.out.println(fs.length);
            for(int i=0;i<fs.length;i++){
                if(facName.containsKey(fac.get(i)[1])){
                    fs[i]=facName.get(fac.get(i)[1]);
                }else{
                    fs[i]=new FacultySchedule(fac.get(i)[0]);
                    //System.out.println("binded: "+fac.get(i)[1]);
                    facName.put(fac.get(i)[1], fs[i]);
                }
            }
            faculty.put(s, fs);
        }
        FacultySchedule.classCode="MCA:"+semester+":"+section;
        int cnt;
        String subject;
        for(int i=ScheduleBase.M_LEC1;i<=ScheduleBase.S_LEC7;i++){
            subject=schedule.get(i);
            if(subject!=null){
                cnt=subLectureCnt.get(subject);
                cnt++;
                subLectureCnt.put(subject, cnt);
            }
        }
        
    }
    
    public boolean validateSchedule(){
        Set<String> subject=faculty.keySet();
        if(subject.isEmpty()){
            JOptionPane.showMessageDialog(MainFrame.mainFrame, "No subjects found.\nPlease contact your Administrator.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        Iterator<String> it=subject.iterator();
        String temp;
        while(it.hasNext()){
            temp=it.next();
            if(faculty.get(temp).length==0){
                JOptionPane.showMessageDialog(MainFrame.mainFrame, "Faculty not found for some subjects.\nPlease contact your Administrator.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if(!Subject.isTheory(temp)){
                if(!subjectInstance.hasLab(temp)){
                    JOptionPane.showMessageDialog(MainFrame.mainFrame, "Computer lab not found for some subjects.\nPlease contact your Administrator.", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        return true;
    }
    
    public void reset() throws Exception{
        for(int i=ScheduleBase.M_LEC1;i<=ScheduleBase.S_LEC7;i++){
            schedule.put(i,null);
        }
        save();
        load();
    }
    
    public void save(){
        ScheduleSql ss=new ScheduleSql();
        ss.setAutoCommit(false);
        try{
            if(isExist){
                ss.updateSchedule(semester, section, schedule);
            }else{
                ss.insertSchedule(semester, section, schedule);
            }
            Iterator<String> it=facName.keySet().iterator();
            while(it.hasNext()){
                facName.get(it.next()).save();
            }
        }catch(Exception e){
            e.printStackTrace();
            ss.rollback();
        }
        ss.commit();
        ss.setAutoCommit(true);
        ss.close();
    }
    
    public boolean isVacant(int lecture,String subject){
        if(schedule.get(lecture)==null){
            if(subjectInstance.isAvailable(lecture,subject, subLectureCnt.get(subject))){
                //boolean  rtnVal=false;
                FacultySchedule fs[]=faculty.get(subject);
                for(int i=0;i<fs.length;i++){
                    //rtnVal=rtnVal || fs[i].isVacant(lecture, subject);
                    if(!fs[i].isVacant(lecture, subject)){
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    public boolean addLecture(int lecture,String subject){
        //if(isVacant(lecture,subject)){
            schedule.put(lecture, subject);
            FacultySchedule fs[]=faculty.get(subject);
                for(int i=0;i<fs.length;i++){
                    fs[i].addLecture(lecture, subject);
                }
            subjectInstance.add(lecture, subject);    
            subLectureCnt.put(subject, subLectureCnt.get(subject)+1);
            return true;
        //}
        //return false;
    }
    
    public void removeLecture(int lecture){
        String subject=schedule.get(lecture);
        schedule.put(lecture, null);
        if(subject!=null){
            FacultySchedule fs[]=faculty.get(subject);
                for(int i=0;i<fs.length;i++){
                    fs[i].removeLecture(lecture,subject);
                }
            subjectInstance.remove(lecture, subject);
            subLectureCnt.put(subject, subLectureCnt.get(subject)-1);
        }
    }
    
    public String getScheduledSubject(int lecture){
        return schedule.get(lecture);
    }
    
    public int getNoOfLecture(String subject){
        return subLectureCnt.get(subject);
    }
    
    public int[] getFacultyLecCount(String name){
        int val[]=new int[7];
        int total=0;
        //System.out.println(name);
        int n[]=facName.get(name).getLectureCount();
        for(int i=0;i<n.length;i++){
            val[i]=n[i];
            total=total+n[i];
        }
        val[6]=total;
        return val;
    }

    public HashMap<Integer,String> getSchedule(){
        return schedule;
    }
    
    public Object[] getLabNames(){
        return subjectInstance.getLabNames();
    }
    
    public HashMap<Integer,String> getLabSchedule(String labName){
        return subjectInstance.getLabSchedule(labName);
    }
    
    public ArrayList<String> getSubjects(){
        ArrayList<String> subjects=new ArrayList<>(subLectureCnt.size());
        Set<String> key=subLectureCnt.keySet();
        for(String s:key){
            subjects.add(s);
        }
        Collections.sort(subjects);
        return subjects;
    }
    
    public void activateFaculty(String scode,String fname[]){
        int n=fname.length;
        FacultySchedule fs[]=new FacultySchedule[n];
        for(int i=0;i<n;i++){
            fs[i]=facName.get(fname[i]);
        }
        faculty.put(scode, fs);
    }
    
    public void activateFaculty(String scode,String fname[],int lecture){
        ArrayList<FacultySchedule> fa=new ArrayList<>();
        int n=fname.length;
        FacultySchedule fs;
        for(int i=0;i<n;i++){
            fs=facName.get(fname[i]);
            if(fs.isLectureExist(scode, lecture)){
                fa.add(fs);
            }
        }
        FacultySchedule fsa[]=new FacultySchedule[fa.size()];
        n=fsa.length;
        for(int i=0;i<n;i++){
            fsa[i]=fa.get(i);
        }
        faculty.put(scode, fsa);
    }
    
    public String[] getActivatedFaculty(String scode){
        FacultySchedule fs[]=faculty.get(scode);
        int n=fs.length;
        String[] fnames=new String[n];
        for(int i=0;i<n;i++){
            fnames[i]=fs[i].name;
        }
        return fnames;
    }
    
    public String isFacultyAvailable(String fname,int lecno,String subject){
        if(facName.get(fname).isVacant(lecno, subject)){
            return "Available";
        }else{
            return "Not Available";
        }
    }
    
    public FacultySchedule getFaculty(String fname){
        return facName.get(fname);
    }
    
    public ArrayList<Integer> getLectureID(String scode){
        ArrayList<Integer> val=new ArrayList<>();
        Set<Integer> lecIDs=schedule.keySet();
        for(Integer id:lecIDs){
            if(scode.equals(schedule.get(id))){
                val.add(id);
            }
        }
        return val;
    }
}

