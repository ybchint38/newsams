/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package report.datasource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRRewindableDataSource;

/**
 *
 * @author yash
 */
public class CustomDataSource implements JRRewindableDataSource{

    private Object data[][];
    private String header[];
    private int indx;
    
    public CustomDataSource(String []head,Object dt[][]){
        header=head;
        data=dt;
        indx=-1;
    }
    
    @Override
    public boolean next() throws JRException {
        indx++;
        return (indx<data.length);
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        //System.out.println(jrf.getName());
        for(int i=0;i<header.length;i++){
            if(header[i].equalsIgnoreCase(jrf.getName())){
                return data[indx][i];
            }
        }
        System.out.println(jrf.getName());
        throw new IllegalArgumentException("Invalid field name.");
    }

    @Override
    public void moveFirst() throws JRException {
        indx=-1;
    }
}
