/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkPackage;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 *
 * @author alexa
 */
public class NetworkUtilities {
    public int rate = 19200;
    public OutputStream out; 
    
    public NetworkUtilities() {
        super();
    }

    public void connect(String portName) throws Exception {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if (portIdentifier.isCurrentlyOwned()) {
            System.out.println("Error: Port is currently in use");
        } else {
            CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(rate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

                InputStream in = serialPort.getInputStream();
                if((out = serialPort.getOutputStream()) == null)
                {
                    System.out.println("C NUL");
                }

                (new Thread(new SerialReader(in))).start();
                (new Thread(new SerialWriter(out))).start();

            } else {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }
    }

    public static class SerialReader implements Runnable {

        InputStream in;

        public SerialReader(InputStream in) {
            this.in = in;
        }

        public void run() {
            PrintWriter out = null;
            byte[] buffer = new byte[8];
            int len = -1;
            try {
                out = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\Pierre\\Documents\\Ecole\\SysEmbarque\\DossierBioloid\\eck4.txt", true))); 

                while ((len = this.in.read(buffer)) > -1) {
                    String next = new String(buffer, 0, len);
                    System.out.print(next);
                    out.print(next);
                    out.flush();
                }
            } catch (IOException e) {
                if (out != null) {
                    out.close();
                }
            }
        }
    }

    public static class SerialWriter implements Runnable {

        OutputStream out;

        public SerialWriter(OutputStream out) {
            this.out = out;
        }

        public void run() {
            try {
                int c = 0;
                while ((c = System.in.read()) > -1) {
                    this.out.write(c);
                }
            } catch (IOException e) {}
        }
    }
}
