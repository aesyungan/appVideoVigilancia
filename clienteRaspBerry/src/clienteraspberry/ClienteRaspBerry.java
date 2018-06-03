/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteraspberry;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.ds.v4l4j.V4l4jDriver;
import javax.swing.JFrame;

/**
 *
 * @author Alex
 */
public class ClienteRaspBerry {

    static {
        Webcam.setDriver(new V4l4jDriver());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("V4L4J Webcam Capture Driver Demo");
        frame.add(new WebcamPanel(Webcam.getDefault()));
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
