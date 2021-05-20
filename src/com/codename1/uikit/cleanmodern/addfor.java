/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.components.MultiButton;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Storage;
import com.codename1.io.Util;
import com.codename1.l10n.SimpleDateFormat;
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
import com.mobilePIDEV.entites.formation;
import com.mobilePIDEV.services.serviceformation;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Form;
import com.codename1.ui.list.GenericListCellRenderer;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 *
 * @author 21628
 */
public class addfor extends BaseForm {
    
    public addfor(Resources res) {
        super("ResultsForm", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("ajouter formation");
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
        RadioButton formation = RadioButton.createToggle("ajouter formation", barGroup);
        formation.setUIID("SelectBar");
       

        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(1, formation),
                FlowLayout.encloseBottom(arrow)
        ));

       formation.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(formation, arrow);
        });
        bindButtonSelection(formation, arrow);
      
      //  quiz.addActionListener(l -> new QuizForm(res).showBack());
        //question.addActionListener(l -> new QuestionForm(res).showBack());
        //rep.addActionListener(l -> new ReponseForm(res).showBack());
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
       this.setTitle("ajouter formation");
       this.setLayout(BoxLayout.y());
        TextField nom = new TextField("","Nom");
        nom.setUIID("TextFieldBlack");
                 TextField tfprix = new TextField("", "prix");
        tfprix.setUIID("TextFieldBlack");

                 TextField tfparticipant = new TextField("", "nombre participant");
                         tfparticipant.setUIID("TextFieldBlack");
                 TextField tfheures = new TextField("", "nombre d'heures");
                         tfheures.setUIID("TextFieldBlack");



         TextField description = new TextField("","description");
          description.setUIID("TextFieldBlack");

          TextField tfY= new TextField("","datedeb");
          tfY.setUIID("TextFieldBlack");
        Label formateur = new Label("les formateur : ");

Picker datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");
        
        datePicker.setFormatter(dateFormatter);
          Form hi = new Form("les formateur", new BoxLayout(BoxLayout.Y_AXIS));

         ComboBox<Map<String, Object>> combo = new ComboBox<> (
          createListEntry("Amine msallem", "02/06/1996"),
          createListEntry("ahmed zaghdoudi", "01/05/1980"),
          createListEntry("wissem bettaieb", "01/03/1984"),
          createListEntry("houyem mkhinini", "06/08/1979"),
          createListEntry("ons ben fadhel", "04/09/1987"),
          createListEntry("elyes akari", "05/12/1990"),
          createListEntry("hazem ben salem", "04/09/1987"));
  
  combo.setRenderer(new GenericListCellRenderer<>(new MultiButton(), new MultiButton()));
         TextField image = new TextField("");
         image.setUIID("TextFieldBlack");

         Button uploadbtn=new Button("upload");
         uploadbtn.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent evt) {
               Display.getInstance().openGallery(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                if (ev != null && ev.getSource() != null) {
                    String filePath = (String) ev.getSource();
                    int fileNameIndex = filePath.lastIndexOf("/") + 1;
                    String fileName = filePath.substring(fileNameIndex);
                    

                    Image img = null;
                    try {
                        img = Image.createImage(FileSystemStorage.getInstance().openInputStream(filePath));
                         System.out.println("houyeeeeme");
                    System.out.println(filePath);
                    InputStream stream = FileSystemStorage.getInstance().openInputStream(filePath);
                  OutputStream out = Storage.getInstance().createOutputStream("MyImage");
                Util.copy(stream, out);
                Util.cleanup(stream);
                Util.cleanup(out);
        // System.out.println(stream);
                    image.setText(filePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //MultiList photoList = findPhotoList();
                    Hashtable tableItem = new Hashtable();
                    tableItem.put("icon", img.scaled(Display.getInstance().getDisplayHeight() / 10, -1));
                    tableItem.put("emblem", fileName);
                    //photoList.getModel().addItem(tableItem);
                    // Do something, add to List
                }
            }
        }, Display.GALLERY_IMAGE);
               
           }
       });
   
                 
          
       Button addBtn = new Button("add ");
       
       this.addAll(nom,tfprix,description,tfparticipant,tfheures,image,datePicker,formateur,combo,addBtn,uploadbtn);
       addBtn.addActionListener((evt) -> {
            
            if (nom.getText().length() ==0 || description.getText().length()==0|| image.getText().length()==0) {
                Dialog.show("Alert", "Textfields cannot be empty.",null, "OK");
            }else {
                
                                        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
                            float prix = Float.parseFloat(tfprix.getText());
                            float nbrparticipant = Float.parseFloat(tfparticipant.getText());
                            float nbrheures = Float.parseFloat(tfheures.getText());

                    
                formation f = new formation (nom.getText(), image.getText(),description.getText(),datePicker.getDate(), (int) prix, (int) nbrparticipant,(int)nbrheures);
                    if (serviceformation.getInstance().addformationAction(f)) {
                        Dialog.show("Success", "Task added successfully.",null, "OK");
                    }
                    
               new afficheformation(res).show();
                
                
                
            }
            
            
            
        });
     getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, ev-> new afficheroffre(res));
       
    
     
     
    }

    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();

    }

    private void addTab(Tabs swipe, Image img, Label spacer) {

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

    /*   private void addButton(Quiz q, Image img) {
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
        Label titre = new Label("Titre : " + q.getTitre(), "NewsTopLine2");
        Label owner = new Label("Created By : " + q.getOwner());
        Label nbrq = new Label("Question number : " + q.getNbr_quest());
        Container cnt = BorderLayout.west(
                BoxLayout.encloseY(
                        BoxLayout.encloseX(titre),
                         BoxLayout.encloseX(owner),
                         BoxLayout.encloseX(nbrq)
                ));
        add(cnt);

        //cnt.addActionListener(e -> ToastBar.showMessage(title, FontImage.MATERIAL_INFO));
    }

    private void addResult(Image img) {

    }
     */
    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if (b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }

    private void addStringValue(String str, Component c) {
        add(BorderLayout.west(new Label(str, "PaddedLabel"))
                .add(BorderLayout.CENTER, c));

        //add(createLineSeparator(0x33fec3));
    }
  
 


private Map<String, Object> createListEntry(String name, String date) {
    Map<String, Object> entry = new HashMap<>();
    entry.put("Line1", name);
    entry.put("Line2", date);
    return entry;
}
    
}
