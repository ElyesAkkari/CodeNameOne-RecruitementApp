/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package com.codename1.uikit.cleanmodern;

import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mobilePIDEV.services.ServiceUtilisateur;
import com.mobilePIDEV.services.SessionManager;
import java.io.IOException;
import java.io.InputStream;

/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class ProfileForm extends BaseForm {

    public ProfileForm(Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Profile");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        
        tb.addSearchCommand(e -> {});
        
        
        Image img = res.getImage("profile-background.jpg");
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

     //   Label facebook = new Label("786 followers", res.getImage("facebook-logo.png"), "BottomPad");
     //   Label twitter = new Label("486 followers", res.getImage("twitter-logo.png"), "BottomPad");
     //   facebook.setTextPosition(BOTTOM);
     //   twitter.setTextPosition(BOTTOM);

        
        add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                    GridLayout.encloseIn(3, 
                            FlowLayout.encloseCenter(
                                new Label(res.getImage("profile-pic.jpg"), "PictureWhiteBackgrond"))
                    )
                )
                
        ));
        

        TextField username = new TextField(SessionManager.getUsername().toString());
        username.setUIID("TextFieldBlack");
        addStringValue("Nom", username);
        
         TextField prenom = new TextField(SessionManager.getPrenom().toString());
        prenom.setUIID("TextFieldBlack");
        addStringValue("Prenom", prenom);

        TextField email = new TextField(SessionManager.getEmail().toString(), "E-Mail", 20, TextField.EMAILADDR);
        email.setUIID("TextFieldBlack");
        addStringValue("E-Mail", email);
        
        
        TextField cin = new TextField(Integer.toString(SessionManager.getCin()), "Carte d'identité ", 20, TextField.ANY);
        cin.setUIID("TextFieldBlack");
        addStringValue("Carte d'identité ", cin);
        
        TextField num = new TextField(Integer.toString(SessionManager.getNum()), "Numéro de téléphone ", 20, TextArea.PHONENUMBER);

        num.setUIID("TextFieldBlack");
        addStringValue("Numéro de téléphone", num);
 
        Button confirme = new Button("Save Changes");

        Label lab = new Label("camera");
        
        Button changepass = new Button("Modifer password");
         /////////////////////////////////// CAMERA ////////////////
        Image icon = FontImage.createMaterial(FontImage.MATERIAL_CAMERA_ALT, UIManager.getInstance().getComponentStyle("Label"));
        Button cam = new Button(icon);
        
              //add an ActionListener to the cam Button
        cam.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                
                //This will trigger the native camera to display
                Capture.capturePhoto(new ActionListener() {

                    public void actionPerformed(final ActionEvent evt) {
                        //if a user cancels the camera the evt will be null
                        if (evt == null) {
                            ToastBar.Status s = ToastBar.getInstance().createStatus();
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
                         addComponent(BorderLayout.CENTER, imageCmp);
                           revalidate();

                    }
                });

            }
        });

      add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(2,lab,cam)
        ));
       // new CameraForm(res).show();
      //  add(BorderLayout.CENTER,confirme);
         
       add(LayeredLayout.encloseIn(
       GridLayout.encloseIn(2,confirme,changepass)
       ));
       
       confirme.requestFocus();
       confirme.addActionListener(e ->{
                      ServiceUtilisateur.getInstance().modifieProfil(username, prenom, email, cin, num, res, SessionManager.getId());
                                  Dialog.show("Success","Modification avec success","OK",null);
                               //   new ProfileForm(res).show();


       });
       
       changepass.addActionListener(e ->{
           new ChangePassForm(res).show();
        // new CameraForm(res).show();
        
       });


         
    }
    
    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
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
