/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.ToastBar;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import static com.codename1.ui.CN.share;
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
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mobilePIDEV.entites.Participation;
import com.mobilePIDEV.entites.Quiz;
import com.mobilePIDEV.entites.commentaire;
import com.mobilePIDEV.entites.offre;
import com.mobilePIDEV.services.ServiceParticipation;
import com.mobilePIDEV.services.ServiceQuiz;
import com.mobilePIDEV.services.SessionManager;
import com.mobilePIDEV.services.servicecomment;
import com.mobilePIDEV.services.serviceoffre;

import java.util.ArrayList;
//import com.mycompany.myapp.utils.share;

/**
 *
 * @author malek
 */
public class Detailoffre extends BaseForm {

    BaseForm current;
    Quiz q = new Quiz();
    private static int id;
    //Resources res;
    //servicecomment cmt1;

    public Detailoffre(int id, Resources res) {

        super("Newsfeed", BoxLayout.y());
        this.id = id;
        servicecomment cmt = new servicecomment();
        ArrayList<commentaire> listcomment = new ArrayList<>();
        //listcomment = es.getAllComments(id);
        listcomment = cmt.getAllComments(id);

        serviceoffre es = new serviceoffre();
        offre o = es.affichoffre(id);
        Container c1 = new Container(BoxLayout.y());
        Image imgUrl;

        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Newsfeed");
        getContentPane().setScrollVisible(false);
        System.out.println(o.getId());
        super.addSideMenu(res);
        tb.addSearchCommand(e -> {
        });

        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
        Label spacer3 = new Label();
        Image im1 = res.getImage("d.png").scaled(250, 250);
        Image im2 = res.getImage("b.png").scaled(250, 250);

        System.out.println("height : " + res.getImage("dog.jpg").getHeight());
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
        RadioButton all = RadioButton.createToggle("Jobs", barGroup);
        all.setUIID("SelectBar");
        RadioButton featured = RadioButton.createToggle("Events", barGroup);
        featured.setUIID("SelectBar");
        RadioButton popular = RadioButton.createToggle("Formations", barGroup);
        popular.setUIID("SelectBar");
        RadioButton myFavorite = RadioButton.createToggle("Results", barGroup);
        myFavorite.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        all.addActionListener(e -> {
            new afficheroffre(res).show();
        });
        myFavorite.addActionListener(l
                -> new ResultsForm(res).show()
        );
        featured.addActionListener(l
                -> new EventForm(res).show()
        );
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

        //travaille de la details offre :
        Label Nomsociete = new Label(o.getNoms());
        Nomsociete.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        Nomsociete.setTextPosition(TOP);
        Label departement = new Label("apartien au departement  : " + o.getDepartement());
        Label type = new Label("c'est un : " + o.getType());
        //Label Nbparticipents  = new Label("Nombre des participants :  " +Integer.toString(e.getNbparticipent()));
        TextArea description = new TextArea(o.getDescription());
        description.getAllStyles().setFgColor(0x14024F);
        //description.getAllStyles(CENTER);
        description.setMaxSize(2147483647);
        description.setEditable(false);
        Image placeholder = Image.createImage(500, 120);
        EncodedImage encImage = EncodedImage.createFromImage(placeholder, false);

        imgUrl = URLImage.createToStorage(encImage, "file:/C:/Users/DELL/Desktop/WebPIDEV/public/assets/images/properties/" + o.getImage_name(), "file:/C:/Users/malek/WebPIDEV/public/assets/images/properties/" + o.getImage_name()).scaled(1200, 600);
        add(Nomsociete);
        add(imgUrl);
        addAll(description, departement, type);
        if (SessionManager.getRole().toLowerCase().equals("candidat")) {

            ArrayList<Quiz> quiz = ServiceQuiz.getInstance().getAllQuizs();
            int i = 0;
            boolean exist = false;
            Button btnajout = new Button("Postuler");
            do {
                if (quiz.get(i).getTitre().toLowerCase().equals(o.getDepartement().toLowerCase())) {
                    q = quiz.get(i);
                    exist = true;
                    btnajout.addActionListener(e -> new QuizTestForm(res, q).show());
                }
                i++;
            } while ((exist == false) && (i < quiz.size()));
            if (!exist) {
                Participation p = new Participation();
                p.setOffre_id(o.getId());
                p.setUser_id(SessionManager.getId());
                p.setNote(-1);
                btnajout.addActionListener(e -> {
                    ServiceParticipation.getInstance().addParticipation(p);
                    ToastBar.showMessage("Your participation has been added successfully !", FontImage.MATERIAL_INFO);
                    btnajout.remove();
                });

            }
            add(btnajout);
        }
        for (commentaire cm : listcomment) {

            addButton(cm.getUsername(), cm.getMessage(), res);

        }

    }

    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();

    }

    private void addTab(Tabs swipe, Image img, Label spacer/*, String likesStr, String commentsStr, String text*/) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if (img.getHeight() < size) {
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
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
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

    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if (b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "paddedLabel"))
                .add(BorderLayout.CENTER, v)
        );

        add(createLineSeparator(0xeeeeee));
    }

    private void addButton(String title, String type, Resources r) {

        Button image = new Button();

        Container cnt = BorderLayout.west(image);

        TextArea ta = new TextArea(title);
        ta.setUIID("News");
        ta.setEditable(false);

        Label likes = new Label(type + "  ", "NewsTopLine");
        likes.setTextPosition(RIGHT);
        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        ta,
                        BoxLayout.encloseXCenter(likes)
                ));
        add(cnt);

    }

    public Form getF() {
        return current;
    }

}
