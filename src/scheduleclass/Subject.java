/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduleclass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import sql.ClassLabManagerSql;
import sql.ScheduleSql;
import sql.SubjectSql;

/**
 *
 * @author yash
 */
public class Subject {
    
    /*public static int THEORY1=0;
    public static int THEORY2=1;
    public static int THEORY3=2;
    public static int THEORY4=3;
    public static int THEORY5=4;
    public static int LAB1=5;
    public static int LAB2=6;
    */
    public static final int THEORY=0;
    public static final int LAB1=1;
    public static final int LAB2=2;
    public static final int ADD_THEORY=3;
    public static final int ADD_LAB=4;
    
    
    private HashMap<String,Integer> subjectType;
    private HashMap<String,LabSchedule> labSchedule;//<subject,LabSchedule>
    
    private static Subject subject;
    private static Constraints cnst;
    
    private Subject(int sem,String sec){
        labSchedule=new HashMap<>();
        cnst=Constraints.getConstraints(sem);
        load(sem,sec);
    }
    
    public static void destroy(){
        subject=null;
        cnst=null;
    }
    
    public void load(int sem,String sec){
        SubjectSql sql=new SubjectSql();
        subjectType=sql.getSubjectTypes(sem);
        Set<String> subjects=subjectType.keySet();
        String lab;
        LabSchedule temp;
        ArrayList<String> tLabName=new ArrayList<>();
        ArrayList<LabSchedule> tLabSchedule=new ArrayList<>();
        int indx;
        int i=0;
        ClassLabManagerSql csql=new ClassLabManagerSql();
        for(String s:subjects){
            lab=csql.getLabName(s,sem,sec);
            if(lab!=null){
                indx=tLabName.indexOf(lab);
                if(indx==-1){
                    temp=new LabSchedule(lab);
                    tLabName.add(i,lab);
                    tLabSchedule.add(i,temp);
                    i++;
                }else{
                    temp=tLabSchedule.get(indx);
                }
                labSchedule.put(s, temp);
            }
        }
        sql.close();
    }
    
    public ArrayList<String> getSubjectCodes(){
        ArrayList<String> subs=new ArrayList<>();
        Set<String> s=subjectType.keySet();
        for(String t:s){
            subs.add(t);
        }
        return subs;
    }
    
    public boolean add(int lecture,String scode){
        LabSchedule temp=labSchedule.get(scode);
        if(temp!=null){
            return temp.add(lecture, scode);
        }
        return true;
    }
    
    public void remove(int lecture,String scode){
        LabSchedule temp=labSchedule.get(scode);
        if(temp!=null){
            temp.remove(lecture);
        }
    }
    
    public boolean isAvailable(int lecture,String scode,int cnt){
        LabSchedule temp=labSchedule.get(scode);
        if(temp!=null){
            if(!temp.isVacant(lecture)){
                return false;
            }
        }
        int type=subjectType.get(scode);
        switch(type){
            case THEORY:
                return (cnt<cnst.MAX_WEEKLY_SUB_LEC);
            case LAB1:
                return (cnt<cnst.MAX_LAB1);
            case LAB2:
                return (cnt<cnst.MAX_LAB2);
            case ADD_THEORY:
                return (cnt<cnst.MAX_ADD_THEORY);
            case ADD_LAB:
                return (cnt<cnst.MAX_ADD_LAB);
                
        }
        throw new IllegalArgumentException("Subject not found");
    }
    
    public boolean isTheory2(String scode){
        //System.out.println(scode);
        int type=subjectType.get(scode);
        if(type==THEORY || type==ADD_THEORY){
            return true;
        }
        return false;
    }

    public Object[] getLabNames(){
        Set<String> set=labSchedule.keySet();
        HashSet<String> lNames=new HashSet<>();
        for(String s:set){
            lNames.add(labSchedule.get(s).name);
        }
        return lNames.toArray();
    }
    
    public HashMap<Integer,String> getLabSchedule(String labName){
            Set<String> set=labSchedule.keySet();
            LabSchedule temp;
            for(String s:set){
                temp=labSchedule.get(s);
                if(temp.name.equalsIgnoreCase(labName)){
                    return temp.getSchedule();
                }
            }
            throw new IllegalArgumentException("Computer Lab not found");
    }
    
    public boolean hasLab(String scode){
        return labSchedule.containsKey(scode);
    }
    
    public static Subject getInstance(int sem,String sec){
        if(subject==null){
            subject=new Subject(sem,sec);
        }
        return subject;
    }
    
    public static boolean isTheory(String scode){
        return subject.isTheory2(scode);
    }
    
    
    private class LabSchedule{
        
        private String name;
        private HashMap<Integer,String> schedule;

        public LabSchedule(String lab) {
            name=lab;
            ScheduleSql sql=new ScheduleSql();
            schedule=sql.getLabSchedule(lab);
            sql.close();
        }
        
        public String getLab(){
            return name;
        }
        
        public boolean isVacant(int lecture){
            return schedule.get(lecture)==null;
        }
        
        public boolean add(int lecture,String scode){
            if(isVacant(lecture)){
                schedule.put(lecture, scode);
            }
            return false;
        }
        
        public void remove(int lecture){
            schedule.put(lecture, null);
        }
        
        public HashMap<Integer,String> getSchedule(){
            return schedule;
        }
    }
    
    /*public static boolean isLab(String subject){
        //System.out.println(subject);
        if(!subject.equalsIgnoreCase("NA")){
            int code=Integer.parseInt(subject.charAt(subject.length()-1)+"");
            if(code==6 || code==7){
                return true;
            }
        }else{
            return true;
        }
        return false;
    }
    
    public static boolean isTheory(String subject){
        return !isLab(subject);
    }
    
    private static boolean isLab1(String subject){
        int code=Integer.parseInt(subject.charAt(subject.length()-1)+"");
        if(code==6){
            return true;
        }
        return false;
    }
    
    private static boolean isLab2(String subject){
        int code=Integer.parseInt(subject.charAt(subject.length()-1)+"");
        if(code==7){
            return true;
        }
        return false;
    }
    
    public static boolean isAvailable(String subject,int cnt){
        if(isLab1(subject)){
            return (cnt<cnst.MAX_LAB1);
        }
        if(isLab2(subject)){
            return (cnt<cnst.MAX_LAB2);
        }
        if(isTheory(subject)){
            return (cnt<cnst.MAX_WEEKLY_SUB_LEC);
        }
        throw new IllegalArgumentException("Subject not found");
    }*/
    
}
