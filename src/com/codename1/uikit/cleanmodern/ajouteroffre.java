/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.ToastBar;
import com.codename1.notifications.LocalNotification;


import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextComponent;
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
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mobilePIDEV.entites.offre;
import com.mobilePIDEV.services.serviceoffre;
import com.sun.mail.smtp.SMTPTransport;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import rest.file.uploader.tn.FileUploader;

/**
 *
 * @author malek
 */
public class ajouteroffre extends BaseForm{
    TextField mailen ;
     TextField description;
       private FileUploader file ; 
        String FileNameInServer ; 
        private String imgPath ; 
    public ajouteroffre(Resources res){
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Newsfeed");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        tb.addSearchCommand(e -> {});
        
        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
        Label spacer3 = new Label();
        Image im1 = res.getImage("d.png").scaled(250, 250);
        Image im2 = res.getImage("b.png").scaled(250, 250);
        
        System.out.println("height : "+res.getImage("dog.jpg").getHeight());
        addTab(swipe, im2, spacer2/*, "", "", ""*/);
        addTab(swipe, im1, spacer3/*, "100 Likes  ", "66 Comments", "Dogs are cute: story at 11"*/);
                
        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();
        
        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for(int iter = 0 ; iter < rbs.length ; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }
                
        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if(!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });
        
        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));
        
        ButtonGroup barGroup = new ButtonGroup();
        RadioButton all = RadioButton.createToggle("Jobs", barGroup);
        all.setUIID("SelectBar");
        RadioButton featured = RadioButton.createToggle("Events", barGroup);
        featured.setUIID("SelectBar");
        RadioButton popular = RadioButton.createToggle("Formations", barGroup);
        popular.setUIID("SelectBar");
        RadioButton myFavorite = RadioButton.createToggle("Results", barGroup);
        myFavorite.setUIID("SelectBar");
         all.addActionListener(e -> {
            new afficheroffre(res).show();
        });
        myFavorite.addActionListener(l
                -> new ResultsForm(res).show()
        );
        featured.addActionListener(l
                -> new EventForm(res).show()
        );
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(4, all, featured, popular, myFavorite),
                FlowLayout.encloseBottom(arrow)
        ));
        
        all.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(all, arrow);
        });
        bindButtonSelection(all, arrow);
        bindButtonSelection(featured, arrow);
        bindButtonSelection(popular, arrow);
        bindButtonSelection(myFavorite, arrow);
        
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
          
        
        
        
        
    TextField type= new TextField("", "type", 20, TextArea.ANY);
    //TextComponent type = new TextComponent().label("type");
    type.setUIID("textFieldblack");
    addStringValue("type",type);
    
    TextField noms = new TextField("", "First Name", 20, TextArea.ANY);
    //TextField noms = new TextField("","entrer le nom de votre societe !!");
    noms.setUIID("textFieldblack");
    addStringValue("noms",noms); 
    
    
    description = new TextField("", "description", 20, TextArea.ANY);
    description.setUIID("textFieldblack");
    addStringValue("description",description); 
    
   
     
     
        //Container cnt3 = new Container(BoxLayout.x());
        //Label lbimg=new Label("image"); 
        Button picture  = new Button ("Parcourir ") ;
        picture.setMaterialIcon(FontImage.MATERIAL_CLOUD_UPLOAD);
        //cnt3.addAll(lbimg,picture) ; 
        add(picture);
        
         picture.addPointerReleasedListener(new ActionListener(){ 
        @Override
      public void actionPerformed (ActionEvent evt ) {
     try {
         imgPath =  Capture.capturePhoto(); 
         System.out.println(imgPath);
         String link  = imgPath.toString();
         int pod = link.indexOf("/",2) ; 
         String news = link.substring(pod +2 , link.length()) ;
         System.out.println(news);
         FileUploader fu =  new FileUploader("http://localhost/");
         FileNameInServer = fu.upload(news) ; 
         System.out.println(FileNameInServer);
          }
     catch (IOException ex ){
         ex.printStackTrace() ; 
     }
     catch (Exception ex ) {
 }
 }
 });
        
        
     
     
        Picker debut = new Picker();
        debut.setUIID("TextFieldBlack");
        addStringValue("date debut", debut);
      
  
         Picker fin  = new Picker();
        fin.setUIID("TextFieldBlack");
        addStringValue("date fin ", fin);
    
    
      mailen = new TextField("", "E-Mail", 20, TextArea.EMAILADDR);
       mailen.setUIID("textFieldblack");
       addStringValue("mail",mailen); 
    
       TextField departement = new TextField("", "departement", 20, TextArea.EMAILADDR);
       departement.setUIID("textFieldblack");
       addStringValue("departement",departement);
      
     
       Button btnAjouter = new Button ("ajouter");
       addStringValue("",btnAjouter);
    
       btnAjouter.addActionListener((e)->{
       try{
       
       if(type.getText() == "" ||description.getText()== ""){
        Dialog.show("veuillez vérifier les donnees ","","annuler","ok");
   
        /*LocalNotification n = new LocalNotification();
        n.setId("demo-notification");
        n.setAlertBody("It's time to take a break and look at me");
        n.setAlertTitle("Break Time!");
        n.setAlertSound("/notification_sound_beep-01a.mp3"); //file name must begin with notification_sound


        Display.getInstance().scheduleLocalNotification(
                n,
                System.currentTimeMillis() + 10 * 1000, // fire date/time
                LocalNotification.REPEAT_MINUTE  // Whether to repeat and what frequency
        );*/
     
       } else {
           InfiniteProgress ip = new InfiniteProgress();
           final Dialog  iDialog = ip.showInfiniteBlocking();
           offre of = new offre(String.valueOf(type.getText().toString()),String.valueOf(noms.getText().toString()),String.valueOf(description.getText().toString()),FileNameInServer,String.valueOf(mailen.getText().toString()),String.valueOf(departement.getText().toString()),debut.getDate(),fin.getDate());
           
           serviceoffre.getInstance().ajoutoffre(of);
           
           
           
           try {
                    sendMail(res);
                   iDialog.dispose();
                    Dialog.show("offre ","un mail vous a été envoye ",new Command("OK"));
                    
                } catch (MessagingException ex) {
                }
           
           
           new afficheroffre(res).show();
           refreshTheme();
       
       }
       
       }catch(Exception ex){
       
       ex.printStackTrace();
       }
     
     });
     
    
    
    
    
    }
    
    
    
     private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();
        
        
    }
    
    private void addTab(Tabs swipe, Image img, Label spacer/*, String likesStr, String commentsStr, String text*/) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if(img.getHeight() < size) {
            img = img.scaledHeight(size);
        }
//        Label likes = new Label(likesStr);
//        Style heartStyle = new Style(likes.getUnselectedStyle());
//        heartStyle.setFgColor(0xff2d55);
//        FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, heartStyle);
//        likes.setIcon(heartImage);
//        likes.setTextPosition(RIGHT);

//       Label comments = new Label(commentsStr);
//        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");
        
        Container page1 = 
            LayeredLayout.encloseIn(
                image,
                overlay,
                BorderLayout.south(
                    BoxLayout.encloseY(
               /*             new SpanLabel(text, "LargeWhiteText"),
                            FlowLayout.encloseIn(likes, comments),
                  */          spacer
                        )
                )
            );

        swipe.addTab("", page1);
    }
       private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if(b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }
       private void addStringValue(String s,Component v) {
       add(BorderLayout.west(new Label (s,"paddedLabel"))
       
       .add(BorderLayout.CENTER,v)
       );
       
       add(createLineSeparator(0xeeeeee));
    }
       
       
       
       public void sendMail(Resources res ) throws MessagingException{
       try{
           
       
           Properties props = new Properties();
       
         
		props.put("mail.transport.protocol", "smtp"); //SMTP Host
		props.put("mail.smtp.host", "gmail.com"); //TLS Port
		props.put("mail.smtp.auth", "true"); //enable authentication
                
                Session session=  Session.getInstance(props,null);
                MimeMessage msg = new MimeMessage(session);
                 
                 msg.setFrom(new InternetAddress("offre ajoute avec succès <nom@domaine.com>"));
                 
                 msg.setRecipients(Message.RecipientType.TO, mailen.getText().toString());// msg.setRecipients(Message.RecipientType.TO, email.getText().toString());
                 
                 msg.setSubject("application : confirmation");
                 msg.setSentDate(new Date(System.currentTimeMillis()));
                 
                // String code = ServiceUser.getInstance().getPasswordByEmaile(email.getText().toString(), res) ;
                 String text= "bonjour on vous a envoyé ce mail pour vous informer que votre offre a ete bien ajouter sur  notre site web  quia a comme description :  " +description.getText().toString() ;
                 msg.setText(text);
                 
                 SMTPTransport st = (SMTPTransport) session.getTransport("smtps");
                 st.connect("smtp.googlemail.com",465,"worldfriendship19@gmail.com","worldfriend2019");
                 st.sendMessage(msg,msg.getAllRecipients());
                 System.out.println("server response : "+st.getLastServerResponse());
    
    
       
       }catch(Exception e){
           e.printStackTrace();
       }        
    
    
}
       
       
       
    
    
}
