/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetest;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.ds.civil.LtiCivilDriver;
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
    private static final MediaListItem dev0 = new MediaListItem("HP HD Webcam [Fixed]", "dshow://", EMPTY);
    private static final MediaListItem dev1 = new MediaListItem("USB2.0 Camera", "dshow://", EMPTY);
    private static final MediaListItem dev2 = new MediaListItem("Logitech Webcam", "dshow://", EMPTY);

    static {
       Webcam.setDriver(new LtiCivilDriver());
    }

    public static void main(String[] args) {
        JFrame window = new JFrame("Webcam Panel");
        window.add(new WebcamPanel(Webcam.getDefault()));
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);
    }
}
