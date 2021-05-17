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

import com.codename1.components.FloatingHint;
import com.codename1.facebook.FaceBookAccess;
import com.codename1.io.Oauth2;
import com.codename1.io.Storage;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mobilePIDEV.services.ServiceUtilisateur;
import javax.mail.MessagingException;

/**
 * Sign in UI
 *
 * @author Shai Almog
 */
public class SignInForm extends BaseForm {

    private Form main;
        
    public static String TOKEN;

    public SignInForm(Resources res) throws MessagingException {
        super(new BorderLayout());
        
        if(!Display.getInstance().isTablet()) {
            BorderLayout bl = (BorderLayout)getLayout();
            bl.defineLandscapeSwap(BorderLayout.NORTH, BorderLayout.EAST);
            bl.defineLandscapeSwap(BorderLayout.SOUTH, BorderLayout.CENTER);
        }
        getTitleArea().setUIID("Container");
        setUIID("SignIn");
        
        add(BorderLayout.NORTH, new Label(res.getImage("Logo.png"), "LogoLabel"));
        
        TextField email = new TextField("", "E-mail", 20, TextField.EMAILADDR);
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        email.setSingleLineTextArea(false);
        password.setSingleLineTextArea(false);
        Button signIn = new Button("Sign In");
        Button signUp = new Button("Sign Up");
      //  Button loginFb = new Button(FBDemo.getTheme().getImage("SignInFacebook.png_veryHigh.png"));

        Button SignInFb = new Button("Facebook");
        
        // Mot de passe oublié 
        
        Button mp = new Button("Oublier mot de passe?","CenterLabel");
        
        signUp.addActionListener(e -> new SignUpForm(res).show());
        signUp.setUIID("Link");
        Label doneHaveAnAccount = new Label("Don't have an account?");
        
        
        
        Container content = BoxLayout.encloseY(
                new FloatingHint(email),
                createLineSeparator(),
                new FloatingHint(password),
                createLineSeparator(),
                signIn,
                SignInFb,
                FlowLayout.encloseCenter(doneHaveAnAccount, signUp),mp
        );
        content.setScrollableY(true);
        add(BorderLayout.SOUTH, content);
        signIn.requestFocus();
        signIn.addActionListener(e -> {
            ServiceUtilisateur.getInstance().signin(email, password, res);
        });
                SignInFb.requestFocus();
                SignInFb.addActionListener(e->{
                    ServiceUtilisateur.getInstance().facebookLogin(res);
                });
    //Mp oublié event 
           mp.addActionListener((e)->{
            try {
                new ActivateForm(res).show();
            } catch (MessagingException ex) {
                System.out.println(ex);
            }
             });

                
    }
    
    private static void signIn(final Form main) {
        FaceBookAccess.setClientId("132970916828080");
        FaceBookAccess.setClientSecret("6aaf4c8ea791f08ea15735eb647becfe");
        FaceBookAccess.setRedirectURI("http://www.codenameone.com/");
        FaceBookAccess.setPermissions(new String[]{"user_location", "user_photos", "friends_photos", "publish_stream", "read_stream", "user_relationships", "user_birthday",
                    "friends_birthday", "friends_relationships", "read_mailbox", "user_events", "friends_events", "user_about_me"});
        
        FaceBookAccess.getInstance().showAuthentication(new ActionListener() {
            
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource() instanceof String) {
                    String token = (String) evt.getSource();
                    String expires = Oauth2.getExpires();
                    TOKEN = token;
                    System.out.println("recived a token " + token + " which expires on " + expires);
                    //store token for future queries.
                    Storage.getInstance().writeObject("token", token);
                    if (main != null) {
                        main.showBack();
                    }
                } else {
                    Exception err = (Exception) evt.getSource();
                    err.printStackTrace();
                    Dialog.show("Error", "An error occurred while logging in: " + err, "OK", null);
                }
            }
        });
    }
        
    public static boolean firstLogin() {
        return Storage.getInstance().readObject("token") == null;
    }
    


}
