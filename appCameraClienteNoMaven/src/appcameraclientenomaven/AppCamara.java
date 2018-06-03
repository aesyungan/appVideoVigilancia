/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appcameraclientenomaven;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.ds.fswebcam.FsWebcamDriver;
import com.github.sarxos.webcam.ds.v4l4j.V4l4jDriver;
import java.awt.Dimension;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

/**
 *
 * @author Alex
 */
public class AppCamara extends javax.swing.JFrame {

    static {
        try {
            //System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "info");
            //System.setProperty("org.slf4j.simpleLogger.log.com.github.sarxos.webcam.ds.v4l4j", "trace");
            //  Webcam.setDriver(new V4l4jDriver());
        } catch (Exception e) {
            System.out.println("error ->" + e.getMessage());
        }

    }
    WebSocketClient mWs = null;
    Webcam webcam = null;
    boolean activo = false;
    boolean isConnectSocket = false;
    Thread thread = null;

    /**
     * Creates new form AppCamara
     */
    public AppCamara() {
        initComponents();
        btnFinalizar.setEnabled(false);
    }

    public void conectSocket() {
        isConnectSocket = false;
        try {

            // mWs = new WebSocketClient(new URI("ws://localhost:8080/videovigilancia/livevideo")) {//seconecta al servidor de socket que esta en tomcat
            mWs = new WebSocketClient(new URI(txtServerSocket.getText())) {
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
            isConnectSocket = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error al connectar con el server socket",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnIniciar = new javax.swing.JButton();
        btnFinalizar = new javax.swing.JButton();
        labenCapture = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtServerSocket = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        rbMostrarImagen = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnIniciar.setText("Iniciar");
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        btnFinalizar.setText("finalizar");
        btnFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarActionPerformed(evt);
            }
        });

        jLabel1.setText("socket server:");

        txtServerSocket.setText("ws://192.168.43.105:8080/videovigilancia/livevideo");
        txtServerSocket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtServerSocketActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Video Vigilancia CAMARA 1");

        rbMostrarImagen.setText("Mostrar Imagen");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labenCapture, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnFinalizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
            .addGroup(layout.createSequentialGroup()
                .addGap(169, 169, 169)
                .addComponent(jLabel2)
                .addContainerGap(243, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txtServerSocket, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbMostrarImagen)
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtServerSocket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labenCapture, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnIniciar)
                                .addGap(34, 34, 34)
                                .addComponent(btnFinalizar)
                                .addGap(98, 98, 98))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(rbMostrarImagen)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        // TODO add your handling code here:
        // ImageIcon icon = new ImageIcon();
        //labenCapture.setIcon(icon);
        conectSocket();
        btnFinalizar.setEnabled(true);
        try {
            if (isConnectSocket) {

                webcam = Webcam.getDefault();
                webcam.setViewSize(new Dimension(176, 144));
                webcam.open();
                activo = true;
                btnIniciar.setEnabled(false);
                Camerathread cam = new Camerathread(webcam, mWs, labenCapture, rbMostrarImagen.isSelected());
                thread = new Thread(cam);
                thread.start();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error al connectar con el server socket",
                    JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_btnIniciarActionPerformed

    private void btnFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarActionPerformed
        // TODO add your handling code here:
        activo = false;

        mWs.close();
        mWs = null;

        webcam.close();
        webcam = null;

        btnIniciar.setEnabled(true);
        btnFinalizar.setEnabled(false);
        isConnectSocket = false;
        thread.stop();
        thread = null;

    }//GEN-LAST:event_btnFinalizarActionPerformed

    private void txtServerSocketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtServerSocketActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtServerSocketActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AppCamara.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AppCamara.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AppCamara.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AppCamara.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AppCamara().setVisible(true);
            }
        });
    }

    /*
    public void sendImage(Webcam webcam, WebSocketClient mWs) {
        try {
            System.out.println("enviando imagen");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(webcam.getImage(), "png", baos);
            byte[] res = baos.toByteArray();
            mWs.send(res);
            mostrarImagenDesktop(res);
        } catch (Exception e) {
            System.out.println("Error AL enviar una imagen->" + e.getMessage());
        }

    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFinalizar;
    private javax.swing.JButton btnIniciar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel labenCapture;
    private javax.swing.JRadioButton rbMostrarImagen;
    private javax.swing.JTextField txtServerSocket;
    // End of variables declaration//GEN-END:variables
}
