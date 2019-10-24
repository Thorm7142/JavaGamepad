/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jinputtest;

import net.java.games.input.*;

/**
 *
 * @author alexa
 */
public class JinputTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
      Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
      Controller gamepad=null;
      for(int i=0;i<controllers.length && gamepad==null;i++) 
      {
        if(controllers[i].getType()==Controller.Type.GAMEPAD) 
        {
            System.out.println("MANETTE TROUVEE");
            gamepad = controllers[i];
        }
      }
        

        while(true)
        {
             
            gamepad.poll();
            
            EventQueue queue = gamepad.getEventQueue();

            Event event = new Event();

            while(queue.getNextEvent(event)) {
                StringBuffer buffer = new StringBuffer(gamepad.getName());
                
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
                System.out.println(buffer.toString());
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
