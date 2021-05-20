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
import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.sun.mail.smtp.SMTPTransport;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * Account activation UI
 *
 * @author Shai Almog
 */
public class ActivateForm extends BaseForm {

    TextField email;
    TextField code ;
    
    int codem;
    
    public ActivateForm(Resources res) throws MessagingException {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("IMGLogin");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("Activate");
        
        add(BorderLayout.NORTH, 
                BoxLayout.encloseY(
                        new Label(res.getImage("smily.png"), "LogoLabel"),
                        new Label("Awsome Thanks!", "LogoLabel")
                )
        );
        
        email = new TextField("","Saisir votre mail",20,TextField.ANY);
        email.setSingleLineTextArea(false);
        
        code = new TextField("","Code",20,TextField.ANY);
        code.setSingleLineTextArea(false);

        Button valider = new Button("Valider");
          Button validercode = new Button("Valider Code");

        Label haveAnAcount = new Label("Retour au login?");
        Button SignIn = new Button("Renouveler votre mot de passe ");
        SignIn.addActionListener(e-> previous.showBack());
        SignIn.setUIID("CenterLink");
        
        Container content = BoxLayout.encloseY(
        new Label (res.getImage("oublier.png"),"CenterLabel"),
                new FloatingHint(email),
                createLineSeparator(),
                valider,
                new FloatingHint(code),
                validercode,
               
                FlowLayout.encloseCenter(haveAnAcount,SignIn)
                   
        );
        content.setScrollableY(true);
        
        add(BorderLayout.CENTER,content);
        
        valider.requestFocus();
        
        valider.addActionListener(e->{
            InfiniteProgress ip = new InfiniteProgress();
                
            final Dialog ipDialog = ip.showInfiniteBlocking();
           
            try {
                // Api SEND MAIL

                sendMail(res);
                ipDialog.dispose();
                Dialog.show("Mot de passe","Nous avons envoyé le mot de passe a votre e-mail. Veuillez vérifier votre boite de réception", new Command("OK"));
              //  new SignInForm(res).show();
             //   refreshTheme();
                
            } catch (MessagingException ex) {
                System.out.println(ex);
            }
            
            
        });
           validercode.addActionListener(e->{
               
               if(Integer.toString(codem).equals(code.getText().toString())){       
                   Dialog.show("Mot de passe","Code valable", new Command("OK"));
                   new NewPassForm(res,email.getText().toString()).show();
               }
               else{
             Dialog.show("Mot de passe","Verifier votre code , Incorrect", new Command("OK"));

               }
            
            });
      
    
}
    
    
    
        public  void sendMail(Resources rs) throws MessagingException  {
        // Authentication infos
       
        
        String myAccountEmail ="worldfriendship19@gmail.com";
        String password ="worldfriend2019";
      //  String toEmail ="azaghdoudi037@gmail.com";
 try{
        Properties props = new Properties();
        
        props.put("mail.smtp.auth", "true");
     //   props.put("mail.smtp.starttls.enable", "true");
     //   props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.trasport.protocol","smtp");
    //    props.put("mail.smtp.port", "587");
        
           Session session = Session.getInstance(props,null);
               
           MimeMessage msg = new MimeMessage(session);
           
           msg.setFrom(new InternetAddress("Réinisialisation mot de passe <monEmail@impacteers.tn>"));
           msg.setRecipients(Message.RecipientType.TO, email.getText().toString());
           msg.setSubject("[IMPORTANT] Réinitialisation Mot de passe");
           msg.setSentDate(new Date(System.currentTimeMillis()));
           

           
         //  String mp = ServiceUtilisateur.getInstance().getPasswordByEmail(email.getText().toString(), rs);

         
         Random r = new Random ();
       codem =r.nextInt(9999-1000+1);
           //     System.out.println(codem);
           String txt = "Bonjour: "
                    + "Il y a eu une demande de changement de mot de passe! "
                    + "Si vous n'avez pas fait cette demande,veuillez ignorer cet e-mail."
                    + "Sinon, veuillez entrer ce code pour réinitialiser votre mot de passe: "+codem+"";
          
           msg.setText(txt);
           
           SMTPTransport st = (SMTPTransport)session.getTransport("smtps");
           st.connect("smtp.gmail.com",465,myAccountEmail,password);
           
           st.sendMessage(msg,msg.getAllRecipients());
          
               
           } catch (MessagingException e) {
                           e.printStackTrace();
   
           }
} 
    

}

