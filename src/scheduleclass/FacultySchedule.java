/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduleclass;

import java.util.HashMap;
import sql.FacultySql;

/**
 *
 * @author yash
 */
public class FacultySchedule {
    
    
    private String id;
    public String name;
    private HashMap<Integer,String> schedule;
    private int[] noOfLec;
    private int total;
    private final int maxLec;
    private static Constraints cnst;
    public static String classCode; 
    
    public FacultySchedule(String id) throws Exception{
        //load();
        this.id=id;
        cnst=Constraints.getConstraints(0);
        FacultySql sql=new FacultySql(id);
        name=sql.getData()[FacultySql.FNAME];
        schedule=sql.getWeeklySchedule();
        noOfLec=new int[7];
        maxLec=cnst.getMaxLec(sql.getDesignation());
        String s;
        for(int i=ScheduleBase.M_LEC1;i<=ScheduleBase.S_LEC7;i++){
            s=schedule.get(i);
            if(s!=null && !s.equalsIgnoreCase("NA")){
                noOfLec[getDay(i)]++;
                total++;
            }
        }
        sql.close();
    }
    
    public static void destroy(){
        cnst=null;
        classCode=null;
    }
    
    public void save(){
        HashMap<Integer,String> sch=new HashMap<>(42);
        String temp;
        for(int i=ScheduleBase.M_LEC1;i<=ScheduleBase.S_LEC7;i++){
            temp=schedule.get(i);
            if(temp==null || temp.equalsIgnoreCase("NA")){
                sch.put(i, null);
            }else{
                //sch.put(i, classCode+":"+temp);
                sch.put(i, temp);
            }
        }
        FacultySql sql=new FacultySql(id);
        sql.setSchedule(sch);
        sql.close();
    }
    
    /*private void load(){
        FacultySql sql=new FacultySql(id);
        schedule=sql.getWeeklySchedule();
        noOfLec=new int[6];
        String s;
        for(int i=ScheduleBase.M_LEC1;i<=ScheduleBase.S_LEC6;i++){
            s=schedule.get(i);
            if(s!=null && s.equalsIgnoreCase("NA")){
                noOfLec[getDay(i)]++;
            }
        }
    }*/
    
    public boolean isVacant(int lecture,String subject){
        if(schedule.get(lecture)==null){
            if(noOfLec[getDay(lecture)]<cnst.MAX_DAILY_FAC_LEC){
                if(total<maxLec){
                    int cnt=this.getContinuesLecture(lecture, subject);
                    if(cnt<=cnst.MAX_CONT_FAC_LEC){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean addLecture(int lecture,String subject){
        if(isVacant(lecture,subject)){
            //schedule.put(lecture, subject);
            schedule.put(lecture, classCode+":"+subject);
            noOfLec[getDay(lecture)]++;
            //System.out.println("Faculty lecture added-"+noOfLec[getDay(lecture)]);
            total++;
            return true;
        }
        return false;
    }
    
    public void removeLecture(int lecture,String scode){
        try{
            String temp=schedule.get(lecture).split(":")[3];
            if(temp!=null && temp.equalsIgnoreCase(scode)){
                schedule.put(lecture, null);
                noOfLec[getDay(lecture)]--;
                total--;
            }
        }catch(ArrayIndexOutOfBoundsException e){
        }catch(NullPointerException e){
        }
    }
    
    private int getContinuesLecture(int lecture,String subject){
        int min=((lecture-1)/7)*7+1;
        int max=((lecture-1)/7+1)*7;
        int temp=lecture-1;
        int cnt=0;
        String tSub;
        while(temp>=min){
            tSub=schedule.get(temp);
            if(tSub==null || tSub.equalsIgnoreCase("NA")){
                break;
            }
            if(Subject.isTheory(tSub.split(":")[3])){
                cnt++;
            }
        temp--;
        }
        temp=lecture+1;
        while(temp<=max){
            tSub=schedule.get(temp);
            if(tSub==null || tSub.equalsIgnoreCase("NA")){
                break;
            }
            if(Subject.isTheory(tSub.split(":")[3])){
                cnt++;
            }
        temp++;
        }
        return cnt+1;
    }
    
    private int getDay(int lecture){
        if(lecture>=ScheduleBase.M_LEC1 && lecture<=ScheduleBase.M_LEC7){
                return ScheduleBase.MONDAY;
            }
            if(lecture>=ScheduleBase.T_LEC1 && lecture<=ScheduleBase.T_LEC7){
                return ScheduleBase.TUESDAY;
            }
            if(lecture>=ScheduleBase.W_LEC1 && lecture<=ScheduleBase.W_LEC7){
                return ScheduleBase.WEDNESDAY;
            }
            if(lecture>=ScheduleBase.TH_LEC1 && lecture<=ScheduleBase.TH_LEC7){
                return ScheduleBase.THURSDAY;
            }
            if(lecture>=ScheduleBase.F_LEC1 && lecture<=ScheduleBase.F_LEC7){
                return ScheduleBase.FRIDAY;
            }
            if(lecture>=ScheduleBase.S_LEC1 && lecture<=ScheduleBase.S_LEC7){
                return ScheduleBase.SATURDAY;
            }
            throw new IllegalArgumentException("Invalid Lecture code");
    }
    
    public int[] getLectureCount(){
        return noOfLec;
    }
    
    public HashMap<Integer,String> getSchedule(){
        return schedule;
    }
    
    public boolean isLectureExist(String scode,int lecture){
        String temp=schedule.get(lecture);
        if(temp==null && temp.equalsIgnoreCase("NA")){
            return false;
        }
        if(temp.split(":")[3].equalsIgnoreCase(scode)){
            return true;
        }
        return false;
    }
}
