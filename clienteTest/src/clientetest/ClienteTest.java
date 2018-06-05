/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetest;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.ds.gstreamer.GStreamerDriver;
import com.github.sarxos.webcam.ds.gstreamer.ScreenCaptureDriver;
import com.github.sarxos.webcam.ds.openimaj.OpenImajDriver;
import com.github.sarxos.webcam.ds.vlcj.VlcjDriver;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
    private static final MediaListItem dev0 = new MediaListItem("HP HD Webcam [Fixed]", "dshow://", EMPTY);
    private static final MediaListItem dev1 = new MediaListItem("USB2.0 Camera", "dshow://", EMPTY);
    private static final MediaListItem dev2 = new MediaListItem("Logitech Webcam", "dshow://", EMPTY);
//https://svn.code.sf.net/p/mjpg-streamer/code/ mjpg-streamer-code

    static {
        //  Webcam.setDriver(new VlcjDriver(Arrays.asList(dev0, dev1, dev2)));
        Webcam.setDriver(new ScreenCaptureDriver());
    }

    public static void main(String[] args) {
        final JFrame window = new JFrame("Screen Capture Example");
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setLayout(new FlowLayout());

        final Dimension resolution = WebcamResolution.QVGA.getSize();

        for (final Webcam webcam : Webcam.getWebcams()) {

            webcam.setCustomViewSizes(resolution);
            webcam.setViewSize(resolution);
            webcam.open();

            final WebcamPanel panel = new WebcamPanel(webcam);
            panel.setFPSDisplayed(true);
            panel.setDrawMode(WebcamPanel.DrawMode.FIT);
            panel.setImageSizeDisplayed(true);
            panel.setPreferredSize(resolution);

            window.getContentPane().add(panel);
        }

        window.pack();
        window.setVisible(true);
    }
}
