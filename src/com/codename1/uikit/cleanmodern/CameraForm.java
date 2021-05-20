/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.components.ToastBar;
import com.codename1.components.ToastBar.Status;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.io.InputStream;


/**
 *
 * @author Ahmed
 */
public class CameraForm extends BaseForm {
    

    public CameraForm (Resources rs ){
             
        final Form f = new Form("Camera Demo");        
        f.setLayout(new BorderLayout());
        f.setScrollable(false);
        Image icon = FontImage.createMaterial(FontImage.MATERIAL_CAMERA_ALT, UIManager.getInstance().getComponentStyle("Label"));
        Button cam = new Button(icon);
        
        this.add(cam);

                //add an ActionListener to the cam Button
        cam.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                
                //This will trigger the native camera to display
                Capture.capturePhoto(new ActionListener() {

                    public void actionPerformed(final ActionEvent evt) {
                        //if a user cancels the camera the evt will be null
                        if (evt == null) {
                            Status s = ToastBar.getInstance().createStatus();
                            s.setMessage("User Cancelled Camera");             
                            s.setMessageUIID("Title");
                            Image i = FontImage.createMaterial(FontImage.MATERIAL_ERROR, UIManager.getInstance().getComponentStyle("Title"));
                            s.setIcon(i);
                            s.setExpires(2000);
                            s.show();
                            return;
                        }
                        
                        //Create a component to display from the image path
                        Component imageCmp = createImageComponent((String) evt.getSource());
                            f.addComponent(BorderLayout.CENTER, imageCmp);
                        f.revalidate();

                    }
                });

            }
        });

         
        
    }

  
    
     private Component createImageComponent(String path) {
        InputStream is = null;
        try {
            System.out.println("path " + path);
            is = com.codename1.io.FileSystemStorage.getInstance().openInputStream(path);
            Image i = Image.createImage(is);
            ImageViewer view = new ImageViewer(i.scaledWidth(Display.getInstance().getDisplayWidth()));
            return view;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;

    }
}




   
    

