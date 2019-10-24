/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jinputtest;

/**
 *
 * @author alexa
 */
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class Joystick extends Thread
{
    private Controller    Joystick_Controller = null;

    private float[]       Joystick_oldpoll  = new float[20];
    
    private mw mainwin;

    public Joystick()
    {
        processfindJoystick();
        
    }
    
    public Joystick(mw mainwindow)
    {
        processfindJoystick();
        mainwin = mainwindow;
        
    }
    
    public void processfindJoystick()
    {
        this.Joystick_Controller = null;

        // poll a list of present controllers.
        Controller[] tempList = ControllerEnvironment.getDefaultEnvironment()
                .getControllers();

        for(Controller currentController: tempList)
            if(checkisJoystick(currentController))
            {
                setJoystick(currentController);
                return;
            }

        System.out.println("No joystick found.");
    }

    public void setJoystick(Controller newJoy)
    {
                this.Joystick_Controller = newJoy;

                System.out.println("Active Joystick set to: "
                        + this.Joystick_Controller.getName());               
    }

    public boolean checkisJoystick(Controller thisController)
    {
        if(thisController.getType() == Controller.Type.GAMEPAD ||
           thisController.getType() == Controller.Type.STICK)
            if(thisController.poll())
             return true;

        return false;
    }

    public void run()
    {
        while(true)
        {
Joystick_Controller.poll();
            
            EventQueue queue = Joystick_Controller.getEventQueue();

            Event event = new Event();

            while(queue.getNextEvent(event)) {
                StringBuffer buffer = new StringBuffer(Joystick_Controller.getName());
                
                buffer.append(" at ");
                buffer.append(event.getNanos()).append(", ");
                
                Component comp = event.getComponent();
                buffer.append(comp.getName()).append(" changed to ");
                float value = event.getValue(); 
                
                if(comp.isAnalog()) 
                {
                   buffer.append(value);
                } 
                else 
                {
                   if(value==1.0f) {
                      buffer.append("On");
                   } 
                   else 
                   {
                      buffer.append("Off");
                   }
                }
                buffer.append(" Identifier = " + comp.getIdentifier());
                
                System.out.println(buffer.toString());
                

                mainwin.Upd(Joystick_Controller, event);

            }
            
            try 
            {
                Thread.sleep(20);
            } 
            catch (InterruptedException e) 
            {

               e.printStackTrace();
            } 
        }
            
        
    }
    
}