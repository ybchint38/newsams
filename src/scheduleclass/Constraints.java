/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduleclass;

import newattendancesystem.Utility;
import sql.ConstraintSql;
import sql.FacultySql;

/**
 *
 * @author yash
 */
public class Constraints {
    
    public static final int MAX_PROF_LEC_ID=1;
    public static final int MAX_ASSO_PROF_LEC_ID=2;
    public static final int MAX_ASST_PROF_LEC_ID=3;
    public static final int MAX_DAILY_FAC_LEC_ID=4;
    public static final int MAX_CONT_FAC_LEC_ID=5;
    
    public static final int MAX_WEEKLY_SUB_LEC_ID=1;
    public static final int MAX_LAB1_ID=2;
    public static final int MAX_LAB2_ID=3;
    //public static final int MAX_LEC_DAILY;
    public static final int MAX_ADD_THEORY_ID=4;
    public static final int MAX_ADD_LAB_ID=5;
    
    public final int MAX_PROF_LEC;
    public final int MAX_ASSO_PROF_LEC;
    public final int MAX_ASST_PROF_LEC;
    public final int MAX_DAILY_FAC_LEC;
    public final int MAX_CONT_FAC_LEC;
    
    public final int MAX_WEEKLY_SUB_LEC;
    public final int MAX_LAB1;
    public final int MAX_LAB2;
    public final int MAX_ADD_THEORY;
    public final int MAX_ADD_LAB;
    
    public final int MAX_LEC_DAILY;
    
    private static Constraints constraints;
    
    private Constraints(int sem){
        this.MAX_LEC_DAILY=7;
        ConstraintSql sql=new ConstraintSql();
        int cnst[]=sql.getConstraints();
        MAX_PROF_LEC=cnst[MAX_PROF_LEC_ID-1];
        MAX_ASSO_PROF_LEC=cnst[MAX_ASSO_PROF_LEC_ID-1];
        MAX_ASST_PROF_LEC=cnst[MAX_ASST_PROF_LEC_ID-1];
        MAX_DAILY_FAC_LEC=cnst[MAX_DAILY_FAC_LEC_ID-1];
        MAX_CONT_FAC_LEC=cnst[MAX_CONT_FAC_LEC_ID-1];
        
        cnst=sql.getConstraints(sem);
        MAX_WEEKLY_SUB_LEC=cnst[MAX_WEEKLY_SUB_LEC_ID-1];
        MAX_LAB1=cnst[MAX_LAB1_ID-1];
        MAX_LAB2=cnst[MAX_LAB2_ID-1];
        MAX_ADD_THEORY=cnst[MAX_ADD_THEORY_ID-1];
        MAX_ADD_LAB=cnst[MAX_ADD_LAB_ID-1];
        sql.close();
    }
    
    public static Constraints getConstraints(int sem){
        if(constraints==null){
            constraints=new Constraints(sem);
        }
        return constraints;
    }
    
    public int getMaxLec(String designation){
        if(designation!=null){
            if(designation.equals(Utility.DESG[0]) || designation.equals(Utility.DESG[1]) || designation.equals(Utility.DESG[2])){
                return MAX_PROF_LEC;
            }else if(designation.equals(Utility.DESG[3])){
                return MAX_ASSO_PROF_LEC;
            }else if(designation.equals(Utility.DESG[4])){
                return MAX_ASST_PROF_LEC;
            }
            throw new IllegalArgumentException("Designation not found.");
        }else{
            return 20;
        }
    }
}