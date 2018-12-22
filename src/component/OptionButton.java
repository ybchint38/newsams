/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * OptionButton2.java
 *
 * Created on Sep 8, 2013, 12:55:12 PM
 */

package component;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import newattendancesystem.MainFrame;

/**
 *
 * @author Hemant Borade
 */
public class OptionButton extends javax.swing.JPanel {

    private static ImageIcon onIcon=new ImageIcon(MainFrame.class.getResource("/image/on.png"));
    private static ImageIcon offIcon=new ImageIcon(MainFrame.class.getResource("/image/off.png"));
    private static ImageIcon rightArrow=new ImageIcon(MainFrame.class.getResource("/image/2rightarrow.png"));
    private static ImageIcon leftArrow=new ImageIcon(MainFrame.class.getResource("/image/2leftarrow.png"));
    
    public YToggleButton mainButton;
    private Object[] button;
    //private Color pressed=Color.WHITE;
    //private Color unPressed=Color.GREEN;
    private ImageIcon releasedImage;
    private ImageIcon pressedImage;

    int hashCode;
    
    /** Creates new form OptionButton2 */
    /**public OptionButton2(String Test) {
        initComponents();
        mainButton=new JToggleButton("Main Button");
        class MyListener implements ActionListener{

            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(null,e.getSource().hashCode());
                int ch=(e.getSource()).hashCode();
                switch(ch){
                    case 0:
                        JOptionPane.showMessageDialog(null,"Button 1");
                        break;
                    case 1:
                        JOptionPane.showMessageDialog(null,"Button 2");
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(null,"Button 3");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null,"Default");
                }
            }
        }
        MyListener m=new MyListener();
        OptionButton2 []temp=new OptionButton2[]{new OptionButton2("Button1",m),new OptionButton2("Button2",m)};
        button=new Object[]{new OptionButton2("Parent",temp),new OptionButton2("Button 2",m)};
        setComponents(false);
        registerEvent();
    }**/

    public OptionButton(String label,OptionButton[] arr){
        initComponents();
        releasedImage=rightArrow;//"/image/2rightarrow.png";
        pressedImage=leftArrow;//"/image/2leftarrow.png";
        mainButton=new YToggleButton(label);
        mainButton.setHorizontalAlignment(JButton.LEFT);
        button=arr;
        /*ButtonGroup grp=new ButtonGroup();
        for(int i=0;i<arr.length;i++){
            grp.add(arr[i].getButton());
        }*/
        setComponents(false);
        registerEvent();
    }

    public OptionButton(String label,int hcode,ActionListener listener){
        initComponents();
        releasedImage=offIcon;//"/image/off.png";
        pressedImage=onIcon;//"/image/on.png";
        mainButton=new YToggleButton(label);
        mainButton.setHorizontalAlignment(JButton.LEFT);
        setComponents(false);
        hashCode=hcode;
        mainButton.setName(hcode+"");
        
        if(listener!=null){
            mainButton.addActionListener(listener);
        }//hashCode=hCode;
        //registerEvent2();
    }

    /**public OptionButton(String label,int hcode){
        initComponents();
        releasedImage="/Image/off.png";
        pressedImage="/Image/on.png";
        mainButton=new JToggleButton(label);
        mainButton.setHorizontalAlignment(JButton.LEFT);
        mainButton.setAlignmentY(JButton.LEFT);
        setComponents(false);
        hashCode=hcode;
        registerEvent2();
    }**/

    public void setListener(ActionListener listener){
        mainButton.addActionListener(listener);
    }

    private void setComponents(boolean pressed){
        GroupLayout gl=(GroupLayout)this.getLayout();
        //gl.setAutoCreateContainerGaps(true);
        //gl.setAutoCreateGaps(true);
        GroupLayout.ParallelGroup pgrp=gl.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.SequentialGroup sgrp=gl.createSequentialGroup();

        if(pressed){
            //mainButton.setBackground(this.pressed);
            mainButton.setIcon(pressedImage);
            if(button!=null){
                for(int i=0;i<button.length;i++){
                    //if(button[i]!=null){
                        pgrp.addComponent((Component)button[i],javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE);
                    //}
                }
                gl.setHorizontalGroup(
                gl.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(mainButton, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                .addGroup(gl.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(pgrp))
                );

                sgrp.addComponent(mainButton);
                for(int i=0;i<button.length;i++){
                    //if(button[i]!=null){
                        sgrp.addComponent((Component)button[i]);
                    //}
                }
                gl.setVerticalGroup(
                gl.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(sgrp)
                );
            }
        }else{
            //mainButton.setBackground(this.unPressed);
            mainButton.setIcon(releasedImage);
            gl.setHorizontalGroup(
            gl.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainButton, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
            );

            gl.setVerticalGroup(
            gl.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gl.createSequentialGroup()
                .addComponent(mainButton)
        ));
        }
        this.revalidate();
    }

    private void registerEvent(){
        mainButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                mainButtonPressed(e);
            }
        });

        /*mainButton.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
             //   throw new UnsupportedOperationException("Not supported yet.");
                setComponents(mainButton.isSelected());
            }
        });*/
    }

    private void  registerEvent2(){
        mainButton.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                //System.out.println("Clicked");
                if(mainButton.isSelected()){
                    mainButton.setIcon(releasedImage);
                }else{
                    mainButton.setIcon(pressedImage);
                }
            }
        });
    }
    
    private void mainButtonPressed(ActionEvent e){

        if(mainButton.isSelected()){
            setComponents(true);
        }else{
            setComponents(false);
        }
    }

    public YToggleButton getButton(){
        return mainButton;
    }

    public boolean isLeaf(){
        if(button==null){
            return true;
        }else{
            return false;
        }
    }


    public static OptionButton createGroup(String name,OptionButton ...grp){
        return new OptionButton(name,grp);
    }

    public static ButtonGroup group(OptionButton ...btn){
        ButtonGroup bg=new ButtonGroup();
        for(int i=0;i<btn.length;i++){
            if(btn[i].button!=null){
                throw new IllegalArgumentException();
            }
            bg.add(btn[i].mainButton);
        }
        return bg;
    }

    public static int[] getHashCodes(OptionButton ...btn){
        int[] val=new int[btn.length];
        for(int i=0;i<btn.length;i++){
            val[i]=btn[i].mainButton.hashCode();
        }
        return val;
    }

    //public int getHashCode(){
    //    return hashCode;
   //}


    public class YToggleButton extends JToggleButton{

        public YToggleButton(String lbl){
            super(lbl);
        }

        public void deSelect(){
            this.setSelected(false);
            this.setIcon(releasedImage);
        }

        public void Select(){
            this.setSelected(true);
            this.setIcon(pressedImage);
        }

    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    /*public static void main(String arg[]){
        JFrame f=new JFrame();
        f.setVisible(true);
        f.setSize(600, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel p=new JPanel();
        p.setBackground(Color.red);
        OptionButton2 []arr=new OptionButton2[2];
        //arr[0]=new OptionButton2("Button 1");
        //arr[1]=new OptionButton2("Button 2");
        
        OptionButton2 ob=new OptionButton2("Test");

        p.add(ob);
        //f.setLayout(new BoxLayout(f,BoxLayout.Y_AXIS));
        f.add(p);

    }*/


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


}
