/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetest;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.ds.vlcj.VlcjDriver;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author Alex
 */
public class ClienteTest {

    static {
        Webcam.setDriver(new VlcjDriver());
    }

    public static void main(String[] args)   {

        JFrame frame = new JFrame("GStreamer Webcam Capture Driver Demo");
	frame.add(new WebcamPanel(Webcam.getDefault()));
	frame.pack();
	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
