/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mobilePIDEV.services.ServiceUtilisateur;
import com.mobilePIDEV.services.SessionManager;

/**
 *
 * @author Ahmed
 */
public class ChangePassForm extends BaseForm {
    
    
    
    public ChangePassForm(Resources res){
       
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
       setTitle("Change Password");
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

      //  Label facebook = new Label("786 followers", res.getImage("facebook-logo.png"), "BottomPad");
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
        
        
        TextField oldpass = new TextField("","Old-Password",10, TextField.PASSWORD);
        oldpass.setUIID("TextFieldBlack");
        addStringValue("Old Password", oldpass);
        
         TextField newpass = new TextField("","New-Password",10,TextField.PASSWORD);
        newpass.setUIID("TextFieldBlack");
        addStringValue("New Password", newpass);

        TextField reppass = new TextField("", "E-Mail", 20, TextField.PASSWORD);
        reppass.setUIID("TextFieldBlack");
        addStringValue("Repeat Password", reppass);

        
                Button changepass = new Button("Change");

               add(LayeredLayout.encloseIn(changepass));
               
               
                      changepass.addActionListener(e ->{
                          
                          if(!newpass.getText().toString().equals(reppass.getText().toString())){
                                    Dialog.show("Erreur","password no identique","OK",null);

                          }
                          else if (newpass.getText().toString().equals("")|| reppass.getText().toString().equals("")|| oldpass.getText().toString().equals("") ){
                              
                           Dialog.show("Erreur","password no identique","OK",null);

                          }else{
                              ServiceUtilisateur.getInstance().ChangePassword(newpass, SessionManager.getId(), res);
                                  Dialog.show("Success","Modification password avec success","OK",null);
                                  new ProfileForm(res).show();

                          }
        
       });


        
    }
    
    
        private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }

    
}
