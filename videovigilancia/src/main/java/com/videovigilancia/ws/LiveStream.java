/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.videovigilancia.ws;

/**
 *
 * @author Alex
 */
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.nio.ByteBuffer;

import java.util.Collections;

import java.util.HashSet;

import java.util.Set;

import javax.websocket.EncodeException;

import javax.websocket.OnClose;

import javax.websocket.OnMessage;

import javax.websocket.OnOpen;

import javax.websocket.Session;

import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/livevideo")
public class LiveStream {

    int count = 0;
    private static final Set<Session> sessions = Collections
            .synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void whenOpening(Session session) throws IOException, EncodeException {
        session.setMaxBinaryMessageBufferSize(1024 * 512);
        sessions.add(session);
        System.out.println("Client connect !");
    }

    @OnMessage
    public void processVideo(byte[] imageData, Session session) {
        count++;
        System.out.println("INsite process Video->" + count);

        /*OutputStream out = null;
         try {
            out = new BufferedOutputStream(new FileOutputStream("E:\\img\\video"+count+".mp4"));
            out.write(imageData);
             if (out != null) {
                out.close();
            }
        } catch (Exception e) {
        }*/
        try {

            // Wrap a byte array into a buffer
            ByteBuffer buf = ByteBuffer.wrap(imageData);
            try {
                int count=0;
                
                for (Session session2 : sessions) {
                    if (session.isOpen()) {

                        session2.getBasicRemote().sendBinary(buf);
                    }
                    count++;
                }
                System.out.println("count usuarios->"+sessions.size()+"<- datos a los q envia ->"+count);
            
            } catch (Exception e) {
                System.out.println("Error al Enviar");//como envia a todos va salir este error cuando envie datos a un suario que ya no este disponible
                sessions.clear();
            }

        } catch (Throwable ioe) {

            System.out.println("Error sending message " + ioe.getMessage());

        }

    }

    @OnClose
    public void whenClosing(Session session) {
        System.out.println("Goodbye !");
        sessions.remove(session);
    }
}
