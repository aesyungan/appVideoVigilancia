/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.videovigilancia.bean;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Alex
 */
@ManagedBean
@SessionScoped
public class videoControllerApp implements Serializable {

    private StreamedContent media = null;

    public videoControllerApp() throws FileNotFoundException {
        load();
    }

    public StreamedContent load() {

        try {
            //		InputStream stream = this.getClass().getResourceAsStream("video.mp4");
            //ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            //String path = servletContext.getRealPath("/video/mov_bbb.mp4");
            //System.out.println(path);
            // URL url = getClass().getResource("video/mov_bbb.mp4");
            //    InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("./resourcesvideo/mov_bbb.mp4");
            //InputStream inputStream = new FileInputStream("E:\\img\\mov_bbb.mp4");
            // InputStream inputStream = url.openStream();
            //media = new DefaultStreamedContent(inputStream, "video/quicktime
            //File initialFile = new File("E:\\img\\mov_bbb.mp4");
            //InputStream inputStream = new FileInputStream(initialFile);
            //media = new DefaultStreamedContent(inputStream);
            InputStream inputStream = new FileInputStream("E://img//mov_bbb.mp4");
            media = new DefaultStreamedContent(inputStream, "video/quicktime");
            File targetFile = new File("src/main/resources/targetFile.tmp");
            OutputStream outStream = new FileOutputStream(targetFile);
            //outStream.write(buffer);
            System.out.println("data" + media.toString());
        } catch (Exception e) {
            System.out.println("error->" + e.getMessage());
        }
        return media;
    }

    public StreamedContent getMedia() {
        return media;
    }

    public void setMedia(StreamedContent media) {
        this.media = media;
    }

}
