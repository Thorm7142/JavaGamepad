/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Window;

import NetworkPackage.*;
import GamePadManager.*;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JSlider;
import net.java.games.input.Controller;
import net.java.games.input.Event;

/**
 *
 * @author alexa
 */
public class mw extends javax.swing.JFrame {

    Joystick joystick = null;
    
    OutputStream out;
    CommPortIdentifier portId;
    SerialPort serialPort;
    NetworkPackage.NetworkUtilities nu = new NetworkPackage.NetworkUtilities(); 
    NetworkPackage.BioloidFcts bf = null;
    
    /**
     * Creates new form mw
     */
    public mw() {
        
        initComponents();
        
        listePortsDispo();
        
        String rate = "57600";

        if (rate != null && !rate.trim().equals("")) {
            try {
                nu.rate = Integer.parseInt(rate);
            } catch (NumberFormatException nfe) {
                System.out.println("BAUDRATE n'est pas un nombre");
            }
        }
        try {
            nu.connect("COM10");
        } catch (Exception e) {
            System.out.println("EXCEPTION DE CONNECTION : " + e.getMessage());
        }
        
        bf = new NetworkPackage.BioloidFcts(nu);
        
        joystick = new Joystick(this);
        joystick.start();
        
        
        jl_carre.setForeground(Color.red);
        jl_croix.setForeground(Color.red);
        jl_rond.setForeground(Color.red);
        jl_triangle.setForeground(Color.red);
        
        jl_r1.setForeground(Color.red);
        jl_r2.setForeground(Color.red);
        jl_l1.setForeground(Color.red);
        jl_l2.setForeground(Color.red);
        jl_r3.setForeground(Color.red);
        jl_l3.setForeground(Color.red);
        
        jl_select.setForeground(Color.red);
        jl_screen.setForeground(Color.red);
        jl_home.setForeground(Color.red);
        jl_start.setForeground(Color.red);
        
        jl_zb.setForeground(Color.red);
        jl_zd.setForeground(Color.red);
        jl_zg.setForeground(Color.red);
        jl_zh.setForeground(Color.red);
        
        jl_croix_b.setForeground(Color.red);
        jl_croix_d.setForeground(Color.red);
        jl_croix_g.setForeground(Color.red);
        jl_croix_h.setForeground(Color.red);
        
        jl_x_d.setForeground(Color.red);
        jl_x_g.setForeground(Color.red);
        jl_y_b.setForeground(Color.red);
        jl_y_h.setForeground(Color.red);
        
    }

    public void setColorBool(Event e, JLabel jl)
    {
        if(e.getValue() == 1.0f)
        {
            jl.setForeground(Color.green);
        }
        else
        {
            jl.setForeground(Color.red);
        }
    }
    
    public void setColorJoy(Event e, JLabel jl1, JLabel jl2)
    {
        if(e.getValue() > 0)
        {
            jl1.setForeground(Color.green);
            jl2.setForeground(Color.red);
        }
        else if(e.getValue() < -0.01)
        {
            jl2.setForeground(Color.green);
            jl1.setForeground(Color.red);
        }
        else
        {
            jl2.setForeground(Color.red);
            jl1.setForeground(Color.red);
        }
    }
    
    public void listePortsDispo() {
        System.out.println("Recherche de Ports"); 
        Enumeration portList = CommPortIdentifier.getPortIdentifiers();
        if (portList == null) {
            System.out.println("Aucun port de communication détecté");
        }
        else {
            while (portList.hasMoreElements()) {
                portId = (CommPortIdentifier) portList.nextElement();
                System.out.println("* " + portId.getName());
            } 
        }
    } 
    
    public void UpdSlider(JSlider jsl, float val)
    {
        jsl.setValue((int)(val*100));
    }
    
    public void UpdSliderV(JSlider jsl, float val)
    {
        jsl.setValue((int)-(val*100));
    }
    
    public void Send(int val)
    {
        try {
            nu.out.write(val);
        } catch (IOException ex) {
            Logger.getLogger(mw.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Upd(Controller c, Event e)
    {
        
        switch(String.valueOf(e.getComponent().getIdentifier()))
        {
            case "0" :     setColorBool(e, jl_carre);
                        break;
                        
            case "1" :     setColorBool(e, jl_croix);
                            if(e.getValue() == 1.0f)
                            {
                                System.out.println("Avancer");
                                bf.Avancer();
                            }
                            else
                            {
                                System.out.println("Arret");
                                bf.Arret();
                            }
                        break;
                        
            case "2" :     setColorBool(e, jl_rond);
                        break;
                        
            case "3" :     setColorBool(e, jl_triangle);
                        break;
                        
            case "4" :     setColorBool(e, jl_l1);
                            if(e.getValue() == 1.0f)
                            {
                                System.out.println("PinceDown");
                                bf.PinceDown();
                            }
                            else
                            {
                                System.out.println("PinceStop");
                                bf.PinceStop();
                            }
                        break;
                        
            case "5" :     setColorBool(e, jl_r1);
                            if(e.getValue() == 1.0f)
                            {
                                System.out.println("PinceUp");
                                bf.PinceUp();
                            }
                            else
                            {
                                System.out.println("PinceStop");
                                bf.PinceStop();
                            }
                        break;
                        
            case "6" :     setColorBool(e, jl_l2);
                            if(e.getValue() == 1.0f)
                            {
                                System.out.println("PinceOpen");
                                bf.PinceOpen();
                            }
                            else
                            {
                                System.out.println("PinceStop");
                                bf.PinceStop();
                            }
                        break;
                        
            case "7" :     setColorBool(e, jl_r2);
                            if(e.getValue() == 1.0f)
                            {
                                System.out.println("PinceClose");
                                bf.PinceClose();
                            }
                            else
                            {
                                System.out.println("PinceStop");
                                bf.PinceStop();
                            }
                        break;
                        
            case "8" :     setColorBool(e, jl_select);
                            setColorBool(e, jl_r2);

                        break;
                        
            case "9" :     setColorBool(e, jl_start);
                        break;
                        
            case "10" :    setColorBool(e, jl_l3);
                        break;
                        
            case "11" :    setColorBool(e, jl_r3);
                            if(e.getValue() == 1.0f)
                            {
                                System.out.println("Klaxon");
                                bf.Klaxon();
                            }
                            else
                            {
                                System.out.println("PaxKlaxon ?");

                            }
                        break;
                        
            case "12" :    setColorBool(e, jl_home);
                        break;
                        
            case "13" :    setColorBool(e, jl_screen);
                        break;
                        
            case "z" :      setColorJoy(e, jl_zd, jl_zg);
                            UpdSlider(jsl, e.getValue());
                        break;
                        
            case "rz" :     setColorJoy(e, jl_zb, jl_zh);
                            UpdSliderV(jslv, e.getValue());
                        break;
                        
            case "x" :      setColorJoy(e, jl_x_d, jl_x_g);
                            UpdSlider(jslx, e.getValue());
                        break;
                        
            case "y" :      setColorJoy(e, jl_y_b, jl_y_h);
                            UpdSliderV(jsly, e.getValue());
                        break;
            case "pov" :    setColorBool(e, jl_croix_b);
                            setColorBool(e, jl_croix_g);
                            setColorBool(e, jl_croix_h);
                            setColorBool(e, jl_croix_d);
                        break;
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jl_carre = new javax.swing.JLabel();
        jl_croix = new javax.swing.JLabel();
        jl_rond = new javax.swing.JLabel();
        jl_triangle = new javax.swing.JLabel();
        jl_r1 = new javax.swing.JLabel();
        jl_r2 = new javax.swing.JLabel();
        jl_l1 = new javax.swing.JLabel();
        jl_l2 = new javax.swing.JLabel();
        jl_select = new javax.swing.JLabel();
        jl_screen = new javax.swing.JLabel();
        jl_home = new javax.swing.JLabel();
        jl_start = new javax.swing.JLabel();
        jl_zh = new javax.swing.JLabel();
        jl_zd = new javax.swing.JLabel();
        jl_zb = new javax.swing.JLabel();
        jl_zg = new javax.swing.JLabel();
        jl_croix_h = new javax.swing.JLabel();
        jl_croix_d = new javax.swing.JLabel();
        jl_croix_b = new javax.swing.JLabel();
        jl_croix_g = new javax.swing.JLabel();
        jl_y_h = new javax.swing.JLabel();
        jl_x_d = new javax.swing.JLabel();
        jl_y_b = new javax.swing.JLabel();
        jl_x_g = new javax.swing.JLabel();
        jl_l3 = new javax.swing.JLabel();
        jl_r3 = new javax.swing.JLabel();
        jsl = new javax.swing.JSlider();
        jslv = new javax.swing.JSlider();
        jslx = new javax.swing.JSlider();
        jsly = new javax.swing.JSlider();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jl_carre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_carre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_carre.setText("☐");

        jl_croix.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_croix.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_croix.setText("X");

        jl_rond.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_rond.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_rond.setText("O");

        jl_triangle.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_triangle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_triangle.setText("∆");
        jl_triangle.setToolTipText("");

        jl_r1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_r1.setText("R1");

        jl_r2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_r2.setText("R2");

        jl_l1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_l1.setText("L1");

        jl_l2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_l2.setText("L2");

        jl_select.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_select.setText("select");

        jl_screen.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_screen.setText("screen");

        jl_home.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_home.setText("home");

        jl_start.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_start.setText("start");

        jl_zh.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_zh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_zh.setText("↑");

        jl_zd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_zd.setText("→\t");

        jl_zb.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_zb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_zb.setText("↓");

        jl_zg.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_zg.setText("←\t");

        jl_croix_h.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_croix_h.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_croix_h.setText("↑");

        jl_croix_d.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_croix_d.setText("→\t");

        jl_croix_b.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_croix_b.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_croix_b.setText("↓");

        jl_croix_g.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_croix_g.setText("←\t");

        jl_y_h.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_y_h.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_y_h.setText("↑");

        jl_x_d.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_x_d.setText("→\t");

        jl_y_b.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_y_b.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_y_b.setText("↓");

        jl_x_g.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_x_g.setText("←\t");

        jl_l3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_l3.setText("O");

        jl_r3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_r3.setText("O");

        jsl.setMinimum(-100);
        jsl.setValue(0);

        jslv.setMinimum(-100);
        jslv.setOrientation(javax.swing.JSlider.VERTICAL);
        jslv.setValue(0);

        jslx.setMinimum(-100);
        jslx.setValue(0);

        jsly.setMinimum(-100);
        jsly.setOrientation(javax.swing.JSlider.VERTICAL);
        jsly.setValue(0);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(jl_l1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jl_r1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jl_l2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jl_r2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jl_y_b, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jl_y_h, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jl_x_g, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(jl_l3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jl_x_d, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jslx, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jl_triangle, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jl_croix, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(62, 62, 62))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(369, 369, 369)
                                .addComponent(jl_carre, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jl_rond, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jl_zh, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(81, 81, 81))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jl_zb, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(89, 89, 89)))
                                .addComponent(jslv, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(37, 37, 37)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jl_croix_h, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jl_croix_b, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jsly, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jl_croix_g, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(jl_croix_d, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 177, Short.MAX_VALUE)
                        .addComponent(jl_zg, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jl_r3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jl_zd, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jsl, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jl_select)
                        .addGap(89, 89, 89)
                        .addComponent(jl_start, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jl_screen, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jl_home)
                        .addGap(33, 33, 33)))
                .addGap(209, 209, 209))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jl_r2)
                    .addComponent(jl_l2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jl_r1)
                    .addComponent(jl_l1))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(171, 171, 171)
                        .addComponent(jslv, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(94, 94, 94)
                                .addComponent(jl_triangle))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(123, 123, 123)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jl_rond)
                                    .addComponent(jl_carre))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jl_croix))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(102, 102, 102)
                                .addComponent(jl_y_h)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jslx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jl_x_d)
                                            .addComponent(jl_x_g)
                                            .addComponent(jl_l3))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jl_y_b)))))
                        .addGap(56, 56, 56)
                        .addComponent(jsl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jl_select)
                            .addComponent(jl_start))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jl_screen)
                            .addComponent(jl_home))
                        .addGap(25, 25, 25)
                        .addComponent(jsly, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jl_zh)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jl_zg)
                                    .addComponent(jl_r3)
                                    .addComponent(jl_zd))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jl_zb))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jl_croix_h)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jl_croix_d)
                                    .addComponent(jl_croix_g))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jl_croix_b)))))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mw.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mw.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mw.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mw.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mw().setVisible(true);
            }
        });
        
        
        
        

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jl_carre;
    private javax.swing.JLabel jl_croix;
    private javax.swing.JLabel jl_croix_b;
    private javax.swing.JLabel jl_croix_d;
    private javax.swing.JLabel jl_croix_g;
    private javax.swing.JLabel jl_croix_h;
    private javax.swing.JLabel jl_home;
    private javax.swing.JLabel jl_l1;
    private javax.swing.JLabel jl_l2;
    private javax.swing.JLabel jl_l3;
    private javax.swing.JLabel jl_r1;
    private javax.swing.JLabel jl_r2;
    private javax.swing.JLabel jl_r3;
    private javax.swing.JLabel jl_rond;
    private javax.swing.JLabel jl_screen;
    private javax.swing.JLabel jl_select;
    private javax.swing.JLabel jl_start;
    private javax.swing.JLabel jl_triangle;
    private javax.swing.JLabel jl_x_d;
    private javax.swing.JLabel jl_x_g;
    private javax.swing.JLabel jl_y_b;
    private javax.swing.JLabel jl_y_h;
    private javax.swing.JLabel jl_zb;
    private javax.swing.JLabel jl_zd;
    private javax.swing.JLabel jl_zg;
    private javax.swing.JLabel jl_zh;
    private javax.swing.JSlider jsl;
    private javax.swing.JSlider jslv;
    private javax.swing.JSlider jslx;
    private javax.swing.JSlider jsly;
    // End of variables declaration//GEN-END:variables
}
