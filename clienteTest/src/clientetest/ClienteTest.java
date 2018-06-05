/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetest;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.ds.civil.LtiCivilDriver;
import com.github.sarxos.webcam.ds.jmf.JmfDriver;
import com.github.sarxos.webcam.ds.v4l4j.V4l4jDriver;
import com.github.sarxos.webcam.ds.vlcj.VlcjDriver;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.util.Arrays;
import java.util.List;
import uk.co.caprica.vlcj.medialist.MediaListItem;

/**
 *
 * @author Alex
 */
public class ClienteTest {

    private static List<MediaListItem> EMPTY = new ArrayList<MediaListItem>();

    /* NOTE!
     * 
     * The vlclib does not implement video device discovery on Windows. 
     * Therefore, to make it working on this operating system one needs
     * to manually provide the list of media list items from vlcj. This
     * is not necessary on Linux and Mac.
     */
    static {
        Webcam.setDriver(new V4l4jDriver());
    }

    public static void main(String[] args) throws InterruptedException {

        Webcam webcam = Webcam.getWebcams().get(0);
        webcam.setViewSize(WebcamResolution.VGA.getSize());

        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setImageSizeDisplayed(true);

        JFrame window = new JFrame("Webcam Panel");
        window.add(panel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);
    }
}
