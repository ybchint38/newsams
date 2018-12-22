/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newattendancesystem;

import java.awt.FlowLayout;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

/**
 *
 * @author yash
 */
public class InternalFrame extends JInternalFrame{

    JPanel child;
    
    public InternalFrame(JPanel child,String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
        super(title, resizable, closable, maximizable, iconifiable);
        this.child=child;
        initialize();
    }

    public InternalFrame(JPanel child, String title, boolean resizable, boolean closable) {
        this(child,title,resizable,closable,false,false);
    }
    
    private void initialize(){
        this.setLayout(new FlowLayout());
        this.add(child);
        this.pack();
        this.setVisible(true);
    }
}
