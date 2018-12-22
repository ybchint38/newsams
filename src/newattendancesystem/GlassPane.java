/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newattendancesystem;

import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.beans.PropertyVetoException;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 *
 * @author yash
 */
public class GlassPane extends JComponent{
    
    //private static int cnt=0;
    //private ArrayList<JInternalFrame> iFrames;
    private JInternalFrame iFrame;
   // private JPanel panel;
    
    public GlassPane() {
        this.setLayout(new GridBagLayout());
        this.addMouseListener(new MouseAdapter() {});//Blocking events
    }

    public void addFrame(JInternalFrame frm){
        iFrame=frm;
        //cnt++;
        this.add(iFrame);
        this.setVisible(true);
        try{
            iFrame.setSelected(true);
        }catch(PropertyVetoException e){}
        iFrame.addInternalFrameListener(new InternalFrameAdapter() {

            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                //GlassPane.this.remove(e.getInternalFrame());
                GlassPane.this.setVisible(false);
            }
        });
    }
    
    public void addPanel(JPanel panel){
        //this.panel=panel;
        this.add(panel);
        this.setVisible(true);
    }
    
    public void destroy(){
        this.removeAll();
        this.setVisible(false);
    }
}
