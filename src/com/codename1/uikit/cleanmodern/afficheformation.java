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

import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SliderBridge;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.AutoCompleteTextField;
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
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Slider;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mobilePIDEV.entites.commantaire;
import com.mobilePIDEV.entites.formation;
import com.mobilePIDEV.services.servicecommantaire;
import com.mobilePIDEV.services.serviceformation;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The newsfeed form
 *
 * @author Shai Almog
 */
public class afficheformation extends BaseForm {

    
    ArrayList<formation> formations;
     public static ArrayList<commantaire> listeC;
     static int priceminA ;
              static      int  pricemaxA;
              static        boolean  enventeA= true;
static String  rechercher;

    public afficheformation(Resources res) {
      
        
        

        super("ResultsForm", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Formation Management");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        tb.addSearchCommand(e -> {
        });

        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
        Label spacer3 = new Label();
        Image im1 = res.getImage("profile-background.jpg");//.scaled(250, 250);
        if (im1.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            im1 = im1.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(im1);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
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
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton formation = RadioButton.createToggle("Formation", barGroup);
        formation.setUIID("SelectBar");
        

        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, formation),
                FlowLayout.encloseBottom(arrow)
        ));

        formation.setSelected(true);
      // question.addActionListener(l-> new QuestionForm(res).show());
       // rep.addActionListener(l -> new ReponseForm(res).show());
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(formation, arrow);
        });
        bindButtonSelection(formation, arrow);
      //  bindButtonSelection(question, arrow);
       // bindButtonSelection(rep, arrow);

        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        formations = serviceformation.getInstance().getformation();
        for (formation q : formations) {
            try {
                showformation(q, res);
            } catch (IOException ex) {
                System.out.println("eeee");
            }
        }
           
        Button addformation = new Button("ajouter une formation");
        addformation.addActionListener(l -> {
            new addfor(res).show();
      });
        this.add(addformation);
      
    
        
      //  this.add(listeC);
 
        
//        addButton(res.getImage("news-item-2.jpg"), "Fusce ornare cursus masspretium tortor integer placera.", true, 15, 21);
//        addButton(res.getImage("news-item-3.jpg"), "Maecenas eu risus blanscelerisque massa non amcorpe.", false, 36, 15);
//        addButton(res.getImage("news-item-4.jpg"), "Pellentesque non lorem diam. Proin at ex sollicia.", false, 11, 9);
//    
    }
   

    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();

    }

    private void addTab(Tabs swipe, Image img, Label spacer/*, String likesStr, String commentsStr, String text*/) {
//        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
//        if (img.getHeight() < size) {
//            img = img.scaledHeight(size);
//        }
//        Label likes = new Label(likesStr);
//        Style heartStyle = new Style(likes.getUnselectedStyle());
//        heartStyle.setFgColor(0xff2d55);
//        FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, heartStyle);
//        likes.setIcon(heartImage);
//        likes.setTextPosition(RIGHT);

//        Label comments = new Label(commentsStr);
//        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
//        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
//            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
//        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");

        Container page1
                = LayeredLayout.encloseIn(
                        image,
                        overlay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        /*             new SpanLabel(text, "LargeWhiteText"),
                            FlowLayout.encloseIn(likes, comments),
                                         */spacer
                                )
                        )
                );

        swipe.addTab("", page1);
    }

    private void showformation(formation q, Resources res) throws IOException {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
//        Date date = new Date();
//        //----- days count
//        int days = (int) (Math.abs(date.getTime() - p.getAdded().getTime()) / (1000 * 60 * 60 * 24));
//        //!---

        //Image img = res.getImage("news-item-2.jpg");
        // Button image = new Button(img.fill(width, height));
        // image.setUIID("Label");
        // Container cnt = BorderLayout.west(image);
        //cnt.setLeadComponent(image);
        Label nom = new Label("Nom : " + q.getNom(), "NewsTopLine2");
        Label prix = new Label("prix  : " + q.getPrix());
        Label nbrheures = new Label("Nombre d'heures : " + q.getNbrheures());
        Label nbrparticipants = new Label("Nombre de participants : " + q.getNbrparticipant());
        

            AutoCompleteTextField ac = new AutoCompleteTextField("", "Type here...","Short", "Shock", "Sholder", "Shrek" , "belle formation", "nice" ,"j'aime" , "hi" , "cool", "je" ,"suis","d'accord","participe","a","cette","formation");
        
                Image img;
                ImageViewer iv=new ImageViewer();
                EncodedImage ec;
                String url=q.getImage();
                ec=EncodedImage.create("/head.jpg");//image temporaire
           
                img =URLImage.createToStorage(ec, url, url,URLImage.RESIZE_SCALE_TO_FILL);
                iv.setImage(img);
                Form cn=new Form("caption"+q.getId()+":"+q.getImage());
                cn.setLayout(BoxLayout.y());
               
          
              //  cn.addAll(iv);
             //   this.add(iv);
            
            // SpanLabel posteListSP = new SpanLabel();
            // posteListSP.setText(p.toString());
           
        Container cnt = BorderLayout.west(
                BoxLayout.encloseY(
                  BoxLayout.encloseX(iv),

                        BoxLayout.encloseX(nom),
                        BoxLayout.encloseX(prix),
                         BoxLayout.encloseX(nbrparticipants),

                        BoxLayout.encloseX(nbrheures)
                     
                ));
        
           
     
        cnt.addPointerPressedListener(l -> {
            Dialog dlg = new Dialog("Formation");
            Button edit = new Button("modifier");
            Button delete = new Button("suprimer");

            delete.addActionListener(del -> {
                if (Dialog.show("Confirmation", "suprimer \" " + q.getNom() + "\" ?", "ok", "cancel")) {

                   if (serviceformation.getInstance().deleteformation(q)) {
                        Dialog.show("Success", "Formation Deleted", new Command("OK"));
                        new afficheformation(res).show();
                    } else {
                        Dialog.show("ERROR", "Server error", new Command("OK"));
                    }
                }
            });

            edit.addActionListener(ed -> {
              //  new EditQuizForm(res, q).show();
            });
            dlg.setLayout(BoxLayout.y());
         //   dlg.add(new SpanLabel("Titre : " + q.getTitre() + "\nCreated by : " + q.getOwner(), "DialogBody"));
            
            dlg.addAll(edit, delete);
             dlg.add(delete);
            int h = Display.getInstance().getDisplayHeight();
            dlg.setDisposeWhenPointerOutOfBounds(true);
            dlg.show(h / 11 * 7, 5, 5, 5);
        });
        add(cnt);
           // Form hi = new Form("Star Slider", new BoxLayout(BoxLayout.Y_AXIS));
            this.add(FlowLayout.encloseCenter(createStarRankSlider()));
              this.show();
             
               Button addformatio = new Button("Commenter");
        addformatio.addActionListener(l -> {
                                    String comment12 = ac.getText();

             commantaire c = new    commantaire(q.getId(),comment12);
         if(   serviceformation.getInstance().addCommentaire(c, q.getId()))
            Dialog.show("Success","commentaire ajouté",new Command("OK"));
          else
          Dialog.show("ERROR", "Server error", new Command("OK"));

            System.out.println("ggggggggggg");
        });
        this.add(addformatio);
          Button addformation = new Button("Participer");
        addformation.addActionListener(l -> {
            serviceformation.getInstance().addParticipation(q.getId());
            System.out.println("ggggggggggg");
        });
        this.add(ac);
        this.add(addformation);
     
       // this.add(affichecomm(q));
        //cnt.addActionListener(e -> ToastBar.showMessage(title, FontImage.MATERIAL_INFO));
        
    }

    private void addResult(Image img) {

    }

    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if (b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }
    private  void affichecomm(formation q){
        listeC=servicecommantaire.getInstance().getAllCommentsAction(q.getId());
 Style s3=getAllStyles();
   Container listss = new Container(BoxLayout.y());
        Container lists = new Container(BoxLayout.y());
           for(commantaire comm : listeC) {
            listss.add(createCoursContainer(comm));
        }
      /*************************/
            Style st=lists.getAllStyles();
        st.setMargin(Component.BOTTOM,900);
        st.setMargin(Component.TOP,50);
        Tabs t = new Tabs();
        Style s = UIManager.getInstance().getComponentStyle("Tab");
         t.setUIID("Tab");        
        t.addTab("Comments", listss);
        t.setScrollableY(true);
    }
    private Container createCoursContainer(commantaire commentaires) {
           
            Button bt=new Button("Like");
            Style butStylebn=bt.getAllStyles();
       
            //butStylebnm.setFgColor(0x000000);   
              butStylebn.setBgTransparency(255);
        butStylebn.setMarginUnit(Style.UNIT_TYPE_DIPS);
        butStylebn.setMargin(Component.BOTTOM, 50);
        butStylebn.setMargin(Component.TOP,30);
        butStylebn.setMargin(Component.LEFT,2);

            Label titre1 = new Label("Contenu:");
  
     
//SpanLabel sp = new SpanLabel(); pour le retour a la ligne
            SpanLabel titre =  new SpanLabel("");


           

           Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
           cnt.getStyle().setBgColor(0xFFFFFF);
        cnt.getStyle().setBgTransparency(255);
        titre.getAllStyles().setFgColor(0x000000);
      
         titre1.getAllStyles().setFgColor(0xff6600);
       
        
        
        
            cnt.add(titre1);
            cnt.add(titre);
           
            cnt.add(bt);
            

        Style st=cnt.getAllStyles();
        st.setMargin(Component.BOTTOM,2);


            titre.setText(commentaires.getBody());    
           
     
Container c4=BoxLayout.encloseX();
    //btm.addActionListener(e->{ b=products ;});
  //pur le bouton like:
//  bt.addActionListener(e->{ b=commentaire;new ShowClient(currentB).show(); });
       
            /***************************/
     
          
                   return BorderLayout.center(cnt).add(BorderLayout.EAST,c4);
                   
                   
        }
    private void initStarRankStyle(Style s, Image star) {
    s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
    s.setBorder(Border.createEmpty());
    s.setBgImage(star);
    s.setBgTransparency(0);
}

private Slider createStarRankSlider() {
    Slider starRank = new Slider();
    starRank.setEditable(true);
    starRank.setMinValue(0);
    starRank.setMaxValue(10);
Font fnt = Font.createTrueTypeFont(Font.NATIVE_MAIN_LIGHT, 15f).           derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);
    Style s = new Style(0xffff33, 0, fnt, (byte)0);
    Image fullStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
    s.setOpacity(100);
    s.setFgColor(0);
    Image emptyStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
    initStarRankStyle(starRank.getSliderEmptySelectedStyle(), emptyStar);
    initStarRankStyle(starRank.getSliderEmptyUnselectedStyle(), emptyStar);
    initStarRankStyle(starRank.getSliderFullSelectedStyle(), fullStar);
    initStarRankStyle(starRank.getSliderFullUnselectedStyle(), fullStar);
    starRank.setPreferredSize(new Dimension(fullStar.getWidth() * 5, fullStar.getHeight()));
    return starRank;
}
}
