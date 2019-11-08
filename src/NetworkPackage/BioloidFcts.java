/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkPackage;

import Window.mw;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author alexa
 */
public class BioloidFcts {
    
    /*int id;
    int adresse;
    int valeur;*/
    
    NetworkUtilities nu = null;
    
    public BioloidFcts(NetworkUtilities n)
    {
        nu = n;
    }
    
    
    
    public void Send(int id, int adresse, int val)
    {
        int valHigh = 0;
        int valLow = 0;
        
        valHigh = val/256;
        valLow = val&0xff;
        
        //System.out.println("valHigh = " + valHigh + " valLow = " + valLow);
        
        try 
        {
            nu.out.write(id);
            nu.out.write(adresse);
            nu.out.write(valHigh);
            nu.out.write(valLow);   
            
            //Thread.sleep(100);
            
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(mw.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception e)
        {
            System.out.println("Exception dans le send : " + e.getMessage());
        }
    }
    
            
    public void Avancer()
    {
        Send(1, 32, 2024);
        Send(2, 32, 1000);
        Send(3, 32, 2024);
        Send(4, 32, 1000);
        
        /*Send(1, 33, 2000);
        Send(3, 33, 1000);*/
    }
    
    public void Reculer()
    {
        Send(2, 32, 2024);
        Send(1, 32, 1000);
        Send(4, 32, 2024);
        Send(3, 32, 1000);
        
        /*Send(1, 33, 2000);
        Send(3, 33, 1000);*/
    }
    
    public void Arret()
    {
        Send(1, 32, 0);
        Send(2, 32, 0);
        Send(3, 32, 0);
        Send(4, 32, 0);
    }
    
    public void Gauche(float val)
    {
        Send(1, 32, (int)val);
        Send(2, 32, (int)val);
        Send(3, 32, (int)val);
        Send(4, 32, (int)val);
    }
    
    public void Droite(float val)
    {
        Send(1, 32, (int)val);
        Send(2, 32, (int)val);
        Send(3, 32, (int)val);
        Send(4, 32, (int)val);
    }
    
    public void PinceUp()
    {
        //Send(5, 24, 1);
        Send(5, 32, 150);
        Send(5, 30, 800);
       // Send(5, 31, 1023);
    }
    
    public void PinceStop()
    {
        Send(5, 24, 1);
        Send(5, 32, 0);
        Send(6, 32, 0);
        Send(7, 32, 0);
    }
    
    public void PinceDown()
    {
        Send(5, 32, 150);
        Send(5, 30, 100);
    }
    
    public void PinceClose()
    {
        Send(6, 32, 100);
        Send(7, 32, 100);
        
        Send(6, 30, 650);
        Send(7, 30, 350);
    }
    
    public void PinceOpen()
    {
        Send(6, 32, 100);
        Send(7, 32, 100);
        
        Send(6, 30, 350);
        Send(7, 30, 650);
    }
    
    public void Klaxon()
    {
        Send(100, 40, 39);
    }
    
    
    
    
}
