/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteraspberryconsole;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.ds.v4l4j.V4l4jDriver;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.json.JsonObject;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

/**
 *
 * @author Alex
 */
public class ClientInConsoleApp {

    static {
        try {
            // System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "info");
            // System.setProperty("org.slf4j.simpleLogger.log.com.github.sarxos.webcam.ds.v4l4j", "trace");
            Webcam.setDriver(new V4l4jDriver());
        } catch (Exception e) {
            System.out.println("error ->" + e.getMessage());
        }

    }
    static int count = 0;
    final static CountDownLatch messageLatch = new CountDownLatch(1);

    public static void main(String[] args) {
        //acliacion de cliente socket cone sta app se puede enviar todas las imganes
        //al servidor de aplicaciones en micaso el servidor de tomcat es 8080
        Scanner s = new Scanner(System.in);
        System.out.println("Introduzca su host y puerto del servidor socket ejemplo 127.0.0.1:8080 ");
        String host = s.next();
        System.out.println("Host:" + host);
        WebSocketClient mWs = null;
        try {

            mWs = new WebSocketClient(new URI("ws://" + host + "/videovigilancia/livevideo")) {//seconecta al servidor de socket que esta en tomcat
                @Override
                public void onOpen(ServerHandshake sh) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    System.out.println("Se establecio la connecion con elservidor->" + sh.toString());
                }

                @Override
                public void onMessage(String string) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void onClose(int i, String string, boolean bln) {
                    // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    System.out.println("el cliente finalizo : " + i + " - " + string + "  " + bln);
                }

                @Override
                public void onError(Exception excptn) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    System.out.println("ocurrio un error en el cliente->" + excptn.getMessage());
                }
            };
            mWs.connect();///conecta a java web socktet
            //enviar aqui las imagenes que optiene el Raspberry  puede enviar las imagenes en fromato byte[] 
            //byte[]  bytes=los bytes de la imagen
            //mWs.send(bytes); 
            //enviar imagenes de la pc 
            //sendImage("E://img//assasing.png", mWs);
            Webcam webcam = Webcam.getDefault();
            webcam.setViewSize(new Dimension(160, 120));
            webcam.open();
            boolean ejecutar = true;

            while (ejecutar) {
                sendImage(webcam, mWs);
                Thread.sleep(30);
            }

            //sendImage(webcam, mWs);
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        } finally {
            // mWs.close();
        }

    }

    static public void sendImage(String ImageName, WebSocketClient mWs) throws IOException {
        try {
            // for (int i = 0; i < 10; i++) {
            BufferedImage image = ImageIO.read(new File(ImageName));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] res = baos.toByteArray();
            // count++;
            //System.out.println("enviando archivo ->" + count);
            mWs.send(res);//envia imagen en bytes
            //   Thread.sleep(2500);
            // }

        } catch (Exception e) {
            System.out.println("Error al envia el archivo");
        } finally {
            //mWs.close();s
        }

    }

    static public void sendImage(Webcam webcam, WebSocketClient mWs) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(webcam.getImage(), "png", baos);
            byte[] res = baos.toByteArray();
            count++;
            System.out.println("enviando archivo ->" + count);
            mWs.send(res);
        } catch (Exception e) {
            System.out.println("Error AL enviar una imagen->" + e.getMessage());
        }

    }
}
