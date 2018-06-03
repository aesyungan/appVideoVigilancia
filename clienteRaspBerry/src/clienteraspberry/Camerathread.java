/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteraspberry;

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

    int count = 0;
    Webcam webcam = null;
    WebSocketClient mWs = null;
    JLabel labenCapture = null;
    boolean mostrarImagen = false;

    public Camerathread(Webcam webcam, WebSocketClient mWs, JLabel labenCapture, boolean mostrarImagen) {
        this.mWs = mWs;
        this.webcam = webcam;
        this.labenCapture = labenCapture;
        this.mostrarImagen = mostrarImagen;
    }

    @Override
    public void run() {
        try {
            while (true) {
                sendImage(webcam, mWs, mostrarImagen);
                Thread.sleep(30);
            }

        } catch (Exception e) {
            System.out.println("Error AL ejecutar el thread->" + e.getMessage());
        }

    }

    public void sendImage(Webcam webcam, WebSocketClient mWs, boolean mostrarImagen) {
        try {
            count++;
            Thread thread = new Thread() {
                public void run() {
                    try {
                        System.out.println("enviando imagen ->" + count);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(webcam.getImage(), "png", baos);
                        byte[] res = baos.toByteArray();
                        if (mostrarImagen) {
                            Thread thread = new Thread() {
                                public void run() {
                                    try {
                                        mostrarImagenDesktop(res);
                                    } catch (Exception e) {
                                    }

                                }
                            };
                            thread.start();
                        }

                        mWs.send(res);
                    } catch (Exception e) {
                    }

                }
            };
            thread.start();

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
