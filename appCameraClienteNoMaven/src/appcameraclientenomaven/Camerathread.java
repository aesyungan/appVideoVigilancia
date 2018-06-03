/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appcameraclientenomaven;

import com.github.sarxos.webcam.Webcam;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.java_websocket.client.WebSocketClient;

/**
 *
 * @author Alex
 */
public class Camerathread implements Runnable {

    Webcam webcam = null;
    WebSocketClient mWs = null;
    JLabel labenCapture= null;

    public Camerathread(Webcam webcam, WebSocketClient mWs,JLabel labenCapture) {
        this.mWs = mWs;
        this.webcam = webcam;
        this.labenCapture=labenCapture;
    }

    @Override
    public void run() {
        try {
            while (true) {
                sendImage(webcam, mWs);
                Thread.sleep(50);
            }

        } catch (Exception e) {
            System.out.println("Error AL ejecutar el thread->" + e.getMessage());
        }

    }

    public void sendImage(Webcam webcam, WebSocketClient mWs) {
        try {
            System.out.println("enviando imagen");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(webcam.getImage(), "png", baos);
            byte[] res = baos.toByteArray();
            mostrarImagenDesktop(res);
            mWs.send(res);
            //mostrarImagenDesktop(res);
        } catch (Exception e) {
            System.out.println("Error AL enviar una imagen->" + e.getMessage());
        }

    }
     public void mostrarImagenDesktop(byte[] imageData) {
        ImageIcon icon = new ImageIcon(imageData);
        labenCapture.setIcon(icon);
    }
}
